package com.conceal.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties
@ComponentScan("com.conceal.plaid")
public class ConcealApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcealApplication.class, args);
    }

}
