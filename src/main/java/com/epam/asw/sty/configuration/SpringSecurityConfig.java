package com.epam.asw.sty.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger  = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/" , "/login").permitAll();

        http
                .authorizeRequests()
                .antMatchers("*//**").hasRole("USER")
                .and().formLogin()
                .defaultSuccessUrl("/channelsForUser");
        http
                .authorizeRequests()
                .antMatchers("*//**", "/admin").hasRole("ADMIN")
                .and().formLogin()
                .defaultSuccessUrl("/channelsForAdmin")
                .failureUrl("/403")
                .and().csrf()
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
                //.and().exceptionHandling().accessDeniedPage("/403");
                //https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/
        http
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        // add this line to use H2 web console
        http.headers().frameOptions().disable();
        http.csrf()
                .csrfTokenRepository(csrfTokenRepository());
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }

    private OncePerRequestFilter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                        .getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null
                            && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void handle(
                HttpServletRequest request,
                HttpServletResponse response,
                AccessDeniedException exc) throws IOException, ServletException {

            Authentication auth
                    = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                logger.warn("User: " + auth.getName()
                        + " attempted to access the protected URL: "
                        + request.getMethod() + ":"
                        + request.getRequestURI());
            }
            response.sendRedirect(request.getContextPath() + "/accessDenied");
        }
    }
}