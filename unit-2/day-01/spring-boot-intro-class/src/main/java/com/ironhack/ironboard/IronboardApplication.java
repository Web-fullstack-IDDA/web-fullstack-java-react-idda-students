package com.ironhack.ironboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// =============================================
// IRONBOARD APPLICATION — ENTRY POINT
// =============================================
// This is the main class that bootstraps the
// entire Spring Boot application.
//
// PATTERN: @SpringBootApplication is a convenience
// annotation that combines three annotations:
//   1. @Configuration -- marks this as a config class
//   2. @EnableAutoConfiguration -- auto-configures beans
//      based on the dependencies in pom.xml
//   3. @ComponentScan -- scans this package and
//      sub-packages for @Component, @Service,
//      @Controller, @RestController, etc.
//
// WHY: Spring Boot eliminates boilerplate setup.
// Without it, you'd need XML config files, manual
// servlet registration, and a separate Tomcat install.
//
// =============================================
@SpringBootApplication
public class IronboardApplication {

    public static void main(String[] args) {
        // SpringApplication.run() starts the embedded
        // Tomcat server and initializes the Spring context.
        SpringApplication.run(IronboardApplication.class, args);
        System.out.println("IronBoard Application started successfully!");
    }
}
