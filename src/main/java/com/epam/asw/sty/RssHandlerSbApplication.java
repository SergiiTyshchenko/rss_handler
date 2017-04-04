package com.epam.asw.sty;

import com.epam.asw.sty.service.YmlImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class RssHandlerSbApplication extends SpringBootServletInitializer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(RssHandlerSbApplication.class, args);
	}

	@Resource
	private YmlImportService ymlImportService;

	//for tomcat and jetty - WARs, JAR was working
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RssHandlerSbApplication.class);
	}


	//https://spring.io/guides/gs/spring-boot/
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				logger.info(beanName);
			}

			logger.info("AutoConfiguration should have wired up our stuff");
			logger.info("Let's see if we are yml-based...");
			if (ymlImportService.requiresYml()) {
				logger.info(ymlImportService.gettingYml());
			} else {
				logger.info("No Yml for us :(");
			}

		};
	}


	@Bean
	public WebMvcConfigurerAdapter forwardToIndex() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("helloPage");
				registry.addViewController("/channelsForUser").setViewName("channelManagementForUser");
				registry.addViewController("/channelsForAmin").setViewName("channelManagementForAmin");
				registry.addViewController("/accessDenied").setViewName("accessDenied");
				registry.addViewController("/alreadyExist").setViewName("accessDenied");
			}

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

		};

	}

	@ConfigurationProperties(prefix="my")
	public class Config {

		private List<String> servers = new ArrayList<String>();

		public List<String> getServers() {
			return this.servers;
		}
	}
}
