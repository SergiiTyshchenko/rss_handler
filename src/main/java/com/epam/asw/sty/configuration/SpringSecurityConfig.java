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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

/*    @Autowired
    CustomSuccessHandler customSuccessHandler;*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {


/*        http.authorizeRequests()
                .antMatchers("/" , "/login").permitAll()
                .antMatchers("*//**//**").access("hasRole('USER')")
                .antMatchers("/admin*//**").access("hasRole('ADMIN')")
                .and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
                //.usernameParameter("ssoId").passwordParameter("password")
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/403");*/

 /*       http.authorizeRequests().antMatchers("/register*", "/login", "/lostPassword").anonymous()
                .antMatchers("/admin","/admin**").hasRole("ADMIN")
                .antMatchers("/user", "/user*//**", "/page", "/page*//**").hasAnyRole("USER", "COMPANY_USER", "COMPANY_MASTER", "ADMIN")
                .antMatchers("/company", "/company*//**").hasAnyRole("COMPANY_USER", "COMPANY_MASTER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error=true").usernameParameter("username").passwordParameter("password").loginProcessingUrl("/security_check")
                .successHandler(new MyAuthenticationSuccessHandler(this.mongoTemplate));
        */


        http.authorizeRequests()
                .antMatchers("/" , "/login").permitAll()
                .antMatchers("*//**", "/admin").hasRole("ADMIN")
                .antMatchers("*//**").hasAnyRole("USER", "SERG", "ADMIN")
                .and().formLogin()
                .defaultSuccessUrl("/channelsForUser")
                //.failureUrl("/403")
                .and().csrf()
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and().headers().frameOptions().disable();
        //.and().exceptionHandling().accessDeniedPage("/403");

        // add this line to use H2 web console


/*
        http
                .authorizeRequests()
                .antMatchers("/" , "/login").permitAll()
                .antMatchers("*//**//**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().defaultSuccessUrl("/channelsForUser")//.successHandler(customSuccessHandler) //.loginPage("/login")
                .and().csrf().csrfTokenRepository(csrfTokenRepository()).and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and().headers().frameOptions().disable(); // add this line to use H2 web console*/

                // .failureUrl("/403")
                //.defaultSuccessUrl("/channelsForUser")
                //.and().exceptionHandling().accessDeniedPage("/403");
                //https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/

    }


    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN")
                .and()
                .withUser("serg").password("password").roles("SERG");
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
                logger.info("User: " + auth.getName()
                        + " attempted to access the protected URL: "
                        + request.getMethod() + ":"
                        + request.getRequestURI());
            }
            response.sendRedirect(request.getContextPath() + "/accessDenied");
        }
    }

    @Component
    public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException {
            String targetUrl = determineTargetUrl(authentication);

            if (response.isCommitted()) {
                logger.warn("Can't redirect");
                return;
            }

            logger.info("Request shouild be redirected to " + targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        /*
         * This method extracts the roles of currently logged-in user and returns
         * appropriate URL according to his/her role.
         */
        protected String determineTargetUrl(Authentication authentication) {
            String url = "";

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            List<String> roles = new ArrayList<String>();

            int i= 0;
            for (GrantedAuthority a : authorities) {
                roles.add(a.getAuthority());
                logger.warn("Added Role " + roles.get(i) + " for " + a.getAuthority());
                i++;
            }

            if (isAdmin(roles)) {
                url = "/channelsForAdmin";
            } else if (isUser(roles)) {
                url = "/channelsForUser";
            } else {
                url = "/403";
            }

            return url;
        }

        private boolean isUser(List<String> roles) {
            if (roles.contains("ROLE_USER")) {
                return true;
            }
            return false;
        }

        private boolean isAdmin(List<String> roles) {
            if (roles.contains("ROLE_ADMIN")) {
                return true;
            }
            return false;
        }

        public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
            this.redirectStrategy = redirectStrategy;
        }

        protected RedirectStrategy getRedirectStrategy() {
            return redirectStrategy;
        }

    }
}