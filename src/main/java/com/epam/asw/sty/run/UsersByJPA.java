package com.epam.asw.sty.run;

import com.epam.asw.sty.model.User;
import com.epam.asw.sty.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.boot.context.config.ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY;

@SpringBootApplication
public class UsersByJPA {

    private static final Logger log = LoggerFactory.getLogger(UsersByJPA.class);

    public static void main(String[] args) {
        //SpringApplication.run(UsersByJPA.class);
        SpringApplication app = new SpringApplication(UsersByJPA.class);
        addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        try {
            log.info("\n----------------------------------------------------------\n\t" +
                            "Application '{}' is running! Access URLs:\n\t" +
                            "Local: \t\thttp://127.0.0.1:{}\n\t" +
                            "External: \thttp://{}:{}\n----------------------------------------------------------",
                    env.getProperty("spring.application.name"),
                    env.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    protected static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties =  new HashMap<>();
        /*
        * The default profile to use when no other profiles are defined
        * This cannot be set in the `application.yml` file.
        * See https://github.com/spring-projects/spring-boot/issues/1219
        */
        //defProperties.put("spring.profiles.default", Constants.SPRING_PROFILE_DEVELOPMENT);
        //The above line was causing another issue, so trying the below.
        //The intention is to always have a default profile if nothing is set in the application.yml or environment
        defProperties.put("spring.profiles.active", "dev");
        app.setDefaultProperties(defProperties);
    }
    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new User("Jack", "Bauer", "q", "123"));
            repository.save(new User("Chloe", "O'Brian", "q", "123"));
            repository.save(new User("Kim", "Bauer", "q", "123"));

            // fetch all customers
            log.info("User found with findAll():");
            log.info("-------------------------------");
            for (User item : repository.findAll()) {
                log.info(item.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            User user = repository.findOne(1L);
            log.info("User found with findOne(1L):");
            log.info("--------------------------------");
            log.info(user.toString());
            log.info("");

            // fetch customers by last name
            log.info("User found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            for (User bauer : repository.findByLastName("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }

}