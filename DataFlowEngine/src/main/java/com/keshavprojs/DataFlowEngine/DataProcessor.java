package com.keshavprojs.DataFlowEngine;

import java.util.concurrent.BlockingQueue;

public class DataProcessor implements Runnable {
    private final BlockingQueue<DataPoint> dataQueue;
    private final RuleEngine ruleEngine; // Use RuleEngine instead of hardcoded rule
    private volatile boolean running = true;

    public DataProcessor(BlockingQueue<DataPoint> dataQueue, RuleEngine ruleEngine) {
        this.dataQueue = dataQueue;
        this.ruleEngine = ruleEngine;
        System.out.println("DataProcessor initialized");
    }

    @Override
    public void run() {
        System.out.println("DataProcessor started on thread: " + Thread.currentThread().getName());
        try {
            while (running) {
                System.out.println("Waiting for data... (Queue size: " + dataQueue.size() + ")");
                DataPoint dataPoint = dataQueue.take(); // Blocks until data is available
                System.out.println("Consumed: " + dataPoint);
                processDataPoint(dataPoint);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("DataProcessor interrupted: " + e.getMessage());
        }
    }

    private void processDataPoint(DataPoint dataPoint) {
        if (ruleEngine.evaluate(dataPoint)) {
            System.out.println("Processed DataPoint: " + dataPoint);
        } else {
            System.out.println("Filtered out DataPoint: " + dataPoint);
        }
    }

    public void stop() {
        running = false;
    }
}