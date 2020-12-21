package com.github.tulioag.weatherchecker.service;

import com.github.tulioag.weatherchecker.Configuration;
import com.github.tulioag.weatherchecker.model.TemperatureReport;
import com.github.tulioag.weatherchecker.model.TemperatureReportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.stream.Collectors;

public class UpdaterService implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(UpdaterService.class);

    private final Configuration configuration;
    private final WeatherAPI api;
    private final TemperatureReportDao dao;

    public UpdaterService(Configuration configuration, WeatherAPI api,
        TemperatureReportDao dao) {
        this.configuration = Objects.requireNonNull(configuration);
        this.api = api;
        this.dao = dao;
    }

    @Override
    public void run() {
        for (var place : configuration.getPlaces()) {
            try {
                final var apiResponse = api.callApi(place);
                final var reports = apiResponse.stream().map(
                    forecast -> new TemperatureReport(forecast,
                        TemperatureReport.Status.get(forecast.getTemperature(),
                            configuration.getLowerLimit(),
                            configuration.getUpperLimit())));
                dao.save(place, reports.collect(Collectors.toList()));
                logger.info("saved {}", place);
                //Be nice and avoid being blocked by the api
                Thread.sleep(1000);
            } catch (Exception e) {
                // Error accessing API. Log and try again next time.
                logger.info("Error accessing api for " + place, e);
            }
        }
    }
}
