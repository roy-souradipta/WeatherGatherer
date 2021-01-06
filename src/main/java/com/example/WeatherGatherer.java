package com.example;

//import io.micronaut.http.HttpRequest;
//import io.micronaut.http.client.HttpClient;
//import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.scheduling.annotation.Scheduled;
import io.mikael.urlbuilder.UrlBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Singleton
public class WeatherGatherer {

    @Inject
    private CurrentWeatherInfoSender sender;


    public String getWeather(String name) {
        URI uri = UrlBuilder.fromString("https://api.openweathermap.org/data/2.5/weather")
                .addParameter("q",name)
                .addParameter("appid", "38a4567441f042ab278cea2a133d62b8")
                .toUri();
        var client = HttpClient.newHttpClient();
        String responseBody = null;

        var httpRequest = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("response is" + response.body());
            responseBody = response.body();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            //HttpRequest httpRequest = HttpRequest.newBuilder().
        /*Map<String,Object> response = this.httpClient.toBlocking().retrieve(HttpRequest.GET(
                "/weather?q=Gurugram&appid=38a4567441f042ab278cea2a133d62b8"), Map.class);*/
        }
        this.sender.sendDailyInfo(responseBody);
        return response.body();

    }

    @Scheduled(cron = "0/2 * * * *")
    public void scheduleCallToGetWeather(){
        getWeather("Gurugram");
    }

}
