package com.ironhack.filehandling;

/**
 * Standard Input and File Handling — entry point for all demos.
 * Domain: Library / Book Inventory.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Scanner Demo ===");
        ScannerDemo.run();

        System.out.println("\n=== Output Streams Demo ===");
        OutputStreamsDemo.run();

        System.out.println("\n=== File Writer Demo ===");
        FileWriterDemo.run();

        System.out.println("\n=== File Reader Demo ===");
        FileReaderDemo.run();

        System.out.println("\n=== Write & Read Demo ===");
        WriteReadDemo.run();
    }
}
