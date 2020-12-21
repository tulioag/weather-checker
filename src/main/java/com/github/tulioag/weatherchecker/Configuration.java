package com.github.tulioag.weatherchecker;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Configuration {
    private final int checkingFrequency;
    private final Optional<BigDecimal> lowerLimit;
    private final Optional<BigDecimal> upperLimit;
    private List<String> places;

    @ConstructorProperties({ "checkingFrequency", "lowerLimit", "upperLimit",
        "places" })
    public Configuration(int checkingFrequency, BigDecimal low, BigDecimal high,
        List<String> places) {
        this.checkingFrequency = checkingFrequency;
        this.lowerLimit = Optional.ofNullable(low);
        this.upperLimit = Optional.ofNullable(high);
        this.places = List.copyOf(places);
    }

    public int getCheckingFrequency() {
        return checkingFrequency;
    }

    public Optional<BigDecimal> getLowerLimit() {
        return lowerLimit;
    }

    public Optional<BigDecimal> getUpperLimit() {
        return upperLimit;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }
}
