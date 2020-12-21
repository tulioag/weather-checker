package com.github.tulioag.weatherchecker.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TemperatureReportDao {
    private ConcurrentHashMap<String, List<TemperatureReport>> reportsByPlace = new ConcurrentHashMap<>();

    public void save(String place, List<TemperatureReport> forecasts) {
        reportsByPlace.put(place, List.copyOf(forecasts));
    }

    public List<TemperatureReport> getReport(String place) {
        return reportsByPlace.getOrDefault(place, Collections.emptyList());
    }

    public Collection<String> getPlaces() {
        return reportsByPlace.keySet();
    }
}
