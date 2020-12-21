package com.github.tulioag.weatherchecker.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class TemperatureReport {
    private final TemperatureForecast forecast;
    private final Status status;

    public TemperatureReport(TemperatureForecast forecast, Status status) {
        this.forecast = Objects.requireNonNull(forecast);
        this.status = Objects.requireNonNull(status);
    }

    public TemperatureForecast getForecast() {
        return forecast;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Temperature status.
     * 3 possible values:
     * <ul>
     * <li>TOO_COLD: Temperature below lower limit.
     * <li>TOO_HOT: Temperature above upper limit.
     * <li>WITHIN_LIMITS: Other cases.
     *</ul>
     */
    public enum Status {
        TOO_COLD, WITHIN_LIMITS, TOO_HOT;

        public static Status get(BigDecimal temperature,
            Optional<BigDecimal> lowerLimit, Optional<BigDecimal> upperLimit) {
            Function<BigDecimal, Integer> compare = limit -> limit
                .compareTo(temperature);
            if (lowerLimit.map(compare).map(value -> value > 0).orElse(false)) {
                return TOO_COLD;
            } else if (upperLimit.map(compare).map(value -> value < 0)
                .orElse(false)) {
                return TOO_HOT;
            }
            return WITHIN_LIMITS;
        }
    }

}
