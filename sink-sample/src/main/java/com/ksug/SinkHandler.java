package com.ksug;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SinkHandler {

    @Bean
    public Consumer<String> messageSink() {
        return message -> {
            if (!message.contains("E")) {
                log.info("Result : {}", message);
            } else {
                throw new RuntimeException("Error Test");
            }
        };
    }

    @ServiceActivator(inputChannel = "errorChannel")
    public void errorNotification(ErrorMessage message){
        final Throwable errorPayload = message.getPayload();
        log.error("Error Message: {}", errorPayload.getMessage());
    }
}
