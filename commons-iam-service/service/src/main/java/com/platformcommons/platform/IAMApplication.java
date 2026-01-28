package com.platformcommons.platform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;


@EnableFeignClients
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties
@EnableAsync
public class IAMApplication implements CommandLineRunner {
    public static void main(String[] args) {
        System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY,SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        SpringApplication.run(IAMApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("IAMApplication started");
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}