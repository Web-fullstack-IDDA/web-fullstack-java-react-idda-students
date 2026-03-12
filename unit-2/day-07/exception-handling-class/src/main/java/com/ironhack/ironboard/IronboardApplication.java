// =============================================
// IronboardApplication - Spring Boot entry point (unchanged from previous steps)
// =============================================
/**
 * IronBoard Application - Step 04: Full CRUD + Exceptions
 *
 * Same entry point as previous steps. No changes needed here.
 * Spring Boot automatically scans all sub-packages for @Service, @Controller,
 * @ControllerAdvice, etc.
 */
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
