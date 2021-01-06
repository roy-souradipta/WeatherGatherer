package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;

@Controller("/weather")
public class WeatherGathererController {
    @Inject
    private WeatherGatherer weatherGatherer;

    @Get("/trigger")
    public HttpResponse<String> triggerWeather() {
        String response = this.weatherGatherer.getWeather("Gurugram");
        return HttpResponse.status(HttpStatus.OK).body(response);
    }

}
