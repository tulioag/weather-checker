package com.github.tulioag.weatherchecker.service;

import com.github.tulioag.weatherchecker.model.TemperatureForecast;

import java.io.IOException;
import java.util.List;

/**
 * Interface for a weather api.
 */
public interface WeatherAPI {
    /**
     * The api should receive a single place and return a list of forecasts for the next 5 days.
     *
     * @param place, not null
     * @return List of forecasts
     * @throws IOException In case of connection error.
     */
    List<TemperatureForecast> callApi(String place) throws IOException;
}
