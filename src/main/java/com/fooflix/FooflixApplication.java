package com.fooflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class FooflixApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FooflixApplication.class, args);
    }
}