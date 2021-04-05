package com.ksug;

import java.util.Locale;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ProcessorHandler {

    @Bean
    public Function<String, String> uppercase() {
        log.info("Process Start!!!");
        return message -> {
            log.info("message : {}", message);
            return message.toUpperCase(Locale.ROOT);
        };
    }
}
