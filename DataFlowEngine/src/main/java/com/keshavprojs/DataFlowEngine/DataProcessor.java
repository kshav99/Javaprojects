package com.keshavprojs.DataFlowEngine;

import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.beans.factory.annotation.Autowired; // Import for Autowired

import java.util.concurrent.BlockingQueue;
import org.springframework.boot.CommandLineRunner; // Alternative if ApplicationRunner not preferred


@Component
@ConditionalOnBean(BlockingQueue.class) // Ensure BlockingQueue bean is present
public class DataProcessor implements CommandLineRunner { // Implements CommandLineRunner

    private final BlockingQueue<DataPoint> dataQueue;
    private final int valueThreshold = 50;

    @Autowired // Explicit Autowired
    public DataProcessor(BlockingQueue<DataPoint> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run(String... args) throws Exception { // Implement CommandLineRunner's run method
        try {
            while (true) {
                DataPoint dataPoint = dataQueue.take();
                processDataPoint(dataPoint);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Data Processor interrupted: " + e.getMessage());
        }
    }

    private void processDataPoint(DataPoint dataPoint) {
        if (applyRule(dataPoint)) {
            System.out.println("Processed DataPoint (Value > " + valueThreshold + "): " + dataPoint + " (Thread: " + Thread.currentThread().getName() + ")");
        } else {
            System.out.println("Filtered out DataPoint (Value <= " + valueThreshold + "): " + dataPoint + " (Thread: " + Thread.currentThread().getName() + ")");
        }
    }

    private boolean applyRule(DataPoint dataPoint) {
        return dataPoint.getValue() > valueThreshold;
    }
}