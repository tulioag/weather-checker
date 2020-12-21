package com.github.tulioag.weatherchecker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.tulioag.weatherchecker.model.TemperatureReport;
import com.github.tulioag.weatherchecker.model.TemperatureReportDao;
import com.github.tulioag.weatherchecker.openweather.OpenWeatherAPI;
import com.github.tulioag.weatherchecker.service.RestService;
import com.github.tulioag.weatherchecker.service.UpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Response;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args)
        throws IOException, InvalidConfigurationException {
        final String openWeatherApiKey = System.getenv("OPENWEATHER_KEY");
        Objects.requireNonNull(openWeatherApiKey, "Environment variable 'OPENWEATHER_KEY' is not specified");
        final Configuration configuration = readConfiguration(
            new File("configuration.yaml"));
        final OpenWeatherAPI api = new OpenWeatherAPI(openWeatherApiKey);
        final TemperatureReportDao dao = new TemperatureReportDao();
        final ScheduledExecutorService scheduler = Executors
            .newScheduledThreadPool(1);

        // Configure update
        final UpdaterService updater = new UpdaterService(configuration,api,dao);
        scheduler.scheduleAtFixedRate(updater, 0,
            configuration.getCheckingFrequency(), TimeUnit.SECONDS);

        // configure rest service
        final RestService restService = new RestService(dao);
        restService.configure();
    }


    private static Configuration readConfiguration(File file)
        throws IOException, InvalidConfigurationException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(file, Configuration.class);
        } catch (ValueInstantiationException e) {
            throw new InvalidConfigurationException(e);
        }
    }

}

class InvalidConfigurationException extends Exception {
    public InvalidConfigurationException(Throwable cause) {
        super("Configuration file is invalid", cause);
    }
}