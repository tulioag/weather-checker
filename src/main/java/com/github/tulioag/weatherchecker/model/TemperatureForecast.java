package com.github.tulioag.weatherchecker.model;


import java.math.BigDecimal;

public class TemperatureForecast {
    private final long timestamp;
    private final BigDecimal temperature;

    public TemperatureForecast(long timestamp, BigDecimal temperature) {
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "TemperatureForecast{" + "timestamp=" + timestamp
            + ", temperature=" + temperature + '}';
    }
}
