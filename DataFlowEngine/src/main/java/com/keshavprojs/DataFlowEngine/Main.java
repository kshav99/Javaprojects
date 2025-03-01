package com.keshavprojs.DataFlowEngine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Starting DataFlowEngine application...");

            // Create the shared BlockingQueue
            BlockingQueue<DataPoint> dataQueue = new LinkedBlockingQueue<>(100);
            System.out.println("BlockingQueue created with capacity: 100");

            // Instantiate components
            DataGenerator generator = new DataGenerator(dataQueue);
            RuleEngine ruleEngine = new SimpleValueThresholdRule(50); // Use threshold of 50
            DataProcessor processor = new DataProcessor(dataQueue, ruleEngine);
            System.out.println("Components instantiated");

            // Create threads
            Thread generatorThread = new Thread(generator, "GeneratorThread");
            Thread processorThread = new Thread(processor, "ProcessorThread");
            System.out.println("Threads created");

            // Start threads
            generatorThread.start();
            processorThread.start();
            System.out.println("Threads started");

            // Let them run for 10 seconds
            Thread.sleep(10000);

            // Stop threads gracefully
            System.out.println("Stopping threads...");
            generator.stop();
            processor.stop();
            generatorThread.interrupt();
            processorThread.interrupt();

            // Wait for threads to finish
            generatorThread.join();
            processorThread.join();
            System.out.println("Application shutdown complete");
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}