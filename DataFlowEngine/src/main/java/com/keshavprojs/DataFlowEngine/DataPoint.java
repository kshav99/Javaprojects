package com.keshavprojs.DataFlowEngine;

public class DataPoint {
    private String sensorId;
    private int value;

    public DataPoint(String sensorId, int value) {
        this.sensorId = sensorId;
        this.value = value;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "sensorId='" + sensorId + '\'' +
                ", value=" + value +
                '}';
    }
}