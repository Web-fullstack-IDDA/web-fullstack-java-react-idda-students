package com.ironhack.ironboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// =============================================
// IRONBOARD APPLICATION — ENTRY POINT
// =============================================
// Unchanged from Step 01. @SpringBootApplication
// combines @Configuration + @EnableAutoConfiguration
// + @ComponentScan. The entry point is always the same.
// =============================================
@SpringBootApplication
public class IronboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronboardApplication.class, args);
        System.out.println("IronBoard Application started successfully!");
    }
}
