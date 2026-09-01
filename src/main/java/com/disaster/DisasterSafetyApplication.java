package com.disaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisasterSafetyApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                DisasterSafetyApplication.class,
                args
        );
    }
}