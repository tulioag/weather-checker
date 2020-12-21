package com.github.tulioag.weatherchecker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tulioag.weatherchecker.model.TemperatureReportDao;
import spark.Response;

import static spark.Spark.get;

public class RestService {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final TemperatureReportDao dao;

    public RestService(TemperatureReportDao dao) {
        this.dao = dao;
    }

    public void configure() {

        get("/",
            (request, response) -> jsonResponse(response, dao.getPlaces()));
        get("/:place", (request, response) -> {
            final String place = request.params(":place");
            final var forecast = dao.getReport(place);
            if (forecast.isEmpty()) {
                response.status(404);
                response.type("text/plain; charset=UTF-8");
                return "No forecast for " + place;
            } else {
                return jsonResponse(response, forecast);
            }
        });
    }

    private String jsonResponse(Response response, Object data)
        throws JsonProcessingException {
        response.status(200);
        response.type("application/json");
        return jsonMapper.writeValueAsString(data);
    }
}
