package com.ironhack.ironboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Unchanged from previous steps.
@SpringBootApplication
public class IronboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronboardApplication.class, args);
        System.out.println("IronBoard Application started successfully!");
    }
}
