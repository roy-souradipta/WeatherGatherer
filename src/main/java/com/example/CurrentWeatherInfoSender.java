package com.example;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;

@KafkaClient
public interface CurrentWeatherInfoSender {

    @Topic("First")
    void sendDailyInfo(@Body String currentWeatherInfo);
}
