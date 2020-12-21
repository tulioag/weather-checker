package com.github.tulioag.weatherchecker.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.github.tulioag.weatherchecker.model.TemperatureReport.Status;
import static com.github.tulioag.weatherchecker.model.TemperatureReport.Status.WITHIN_LIMITS;
import static com.github.tulioag.weatherchecker.model.TemperatureReport.Status.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureReportStatusTest {

    @Test
    void testGet() {
        assertEquals(WITHIN_LIMITS,
            get(BigDecimal.ONE, Optional.of(BigDecimal.ZERO),
                Optional.of(BigDecimal.ONE)));
        assertEquals(WITHIN_LIMITS,
            get(BigDecimal.ONE, Optional.empty(),
                Optional.of(BigDecimal.ONE)));

        assertEquals(Status.TOO_COLD,
            get(BigDecimal.ZERO, Optional.of(BigDecimal.valueOf(0.5)),
                Optional.of(BigDecimal.ONE)));
        assertEquals(Status.TOO_HOT,
            get(BigDecimal.ONE, Optional.of(BigDecimal.ZERO),
                Optional.of(BigDecimal.valueOf(0.4))));
    }
}