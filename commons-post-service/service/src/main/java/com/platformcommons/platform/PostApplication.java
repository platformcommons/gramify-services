package com.platformcommons.platform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PostApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("PostApplication started..");
    }
}