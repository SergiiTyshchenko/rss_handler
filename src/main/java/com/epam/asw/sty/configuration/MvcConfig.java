package com.epam.asw.sty.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.asw.sty")
public class MvcConfig extends WebMvcConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);

/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/myhome").setViewName("myhome");
        registry.addViewController("/").setViewName("myhome");
        registry.addViewController("/myhello").setViewName("myhello");
        registry.addViewController("/mylogin").setViewName("mylogin");
    }*/

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}