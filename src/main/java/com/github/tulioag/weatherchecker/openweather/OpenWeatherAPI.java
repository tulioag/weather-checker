package com.github.tulioag.weatherchecker.openweather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tulioag.weatherchecker.model.TemperatureForecast;
import com.github.tulioag.weatherchecker.service.WeatherAPI;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OpenWeatherAPI implements WeatherAPI {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";

    private final String apiKey;
    private final String urlTemplate;

    public OpenWeatherAPI(String apiKey) {
        this.apiKey = Objects.requireNonNull(apiKey, "API key not provided");
        this.urlTemplate =
            BASE_URL + "?units=metric&appid=" + encodeURIComponent(this.apiKey)
                + "&q=";
    }

    private static String encodeURIComponent(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static List<TemperatureForecast> getForecasts(JsonNode jsonNode) {
        final List<TemperatureForecast> result = new ArrayList<>();
        for (var element : jsonNode.get("list")) {
            final long timestamp = element.get("dt").asLong();
            final var main = element.get("main");
            final var temperature = new BigDecimal(main.get("temp").asText());
            result.add(new TemperatureForecast(timestamp,temperature));
        }
        return result;
    }

    @Override
    public List<TemperatureForecast> callApi(String city) throws IOException {
        final String url = urlTemplate + encodeURIComponent(city);
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(new URL(url));
        return getForecasts(jsonNode);
    }
}

