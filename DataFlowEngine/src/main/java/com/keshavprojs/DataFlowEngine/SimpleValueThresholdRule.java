package com.keshavprojs.DataFlowEngine;

public class SimpleValueThresholdRule implements RuleEngine {
    private final int thresholdValue;

    public SimpleValueThresholdRule(int thresholdValue) {
        this.thresholdValue = thresholdValue;
        System.out.println("SimpleValueThresholdRule initialized with threshold: " + thresholdValue);
    }

    @Override
    public boolean evaluate(DataPoint dataPoint) {
        boolean result = dataPoint.getValue() > thresholdValue;
        System.out.println("Evaluated DataPoint: " + dataPoint + ", Result: " + result + " (Threshold: " + thresholdValue + ")");
        return result;
    }
}