package com.keshavprojs.DataFlowEngine;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class DataGenerator implements Runnable {
    private final BlockingQueue<DataPoint> dataQueue;
    private final Random random = new Random();
    private static int sensorIdCounter = 1;
    private volatile boolean running = true;

    public DataGenerator(BlockingQueue<DataPoint> dataQueue) {
        this.dataQueue = dataQueue;
        System.out.println("DataGenerator initialized");
    }

    @Override
    public void run() {
        System.out.println("DataGenerator started on thread: " + Thread.currentThread().getName());
        try {
            while (running) {
                DataPoint dataPoint = generateDataPoint();
                System.out.println("Generated: " + dataPoint);
                dataQueue.put(dataPoint);
                System.out.println("Added to queue: " + dataPoint + " (Queue size: " + dataQueue.size() + ")");
                Thread.sleep(1000); // Simulate delay of 1 second per data point
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("DataGenerator interrupted: " + e.getMessage());
        }
    }

    private DataPoint generateDataPoint() {
        String sensorId = "sensor-" + sensorIdCounter++;
        int value = random.nextInt(100); // Random value between 0-99
        return new DataPoint(sensorId, value);
    }

    public void stop() {
        running = false;
    }
}