package com.keshavprojs.DataFlowEngine;

import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import org.springframework.beans.factory.annotation.Autowired; // Import for Autowired - although constructor injection is preferred.

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import org.springframework.boot.CommandLineRunner; // Alternative if ApplicationRunner not preferred

@Component
@ConditionalOnBean(BlockingQueue.class) // Only create this bean if BlockingQueue bean is present
public class DataGenerator implements CommandLineRunner { // Implements CommandLineRunner

    private final BlockingQueue<DataPoint> dataQueue;
    private final Random random = new Random();
    private static int sensorIdCounter = 1;

    @Autowired // Explicit Autowired is often optional with constructor injection in recent Spring versions, but good to be explicit here for clarity.
    public DataGenerator(BlockingQueue<DataPoint> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run(String... args) throws Exception { // Implement CommandLineRunner's run method
        try {
            while (true) {
                DataPoint dataPoint = generateDataPoint();
                dataQueue.put(dataPoint);
                System.out.println("Generated and added to queue: " + dataPoint + " (Thread: " + Thread.currentThread().getName() + ")");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Data Generator interrupted: " + e.getMessage());
        }
    }

    private DataPoint generateDataPoint() {
        String sensorId = "sensor-" + sensorIdCounter++;
        int value = random.nextInt(100);
        return new DataPoint(sensorId, value);
    }
}