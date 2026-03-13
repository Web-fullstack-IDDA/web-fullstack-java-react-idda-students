// Spring Boot application entry point.
package com.ironhack.ironboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IronboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronboardApplication.class, args);
        System.out.println("IronBoard Application started successfully!");
    }
}
