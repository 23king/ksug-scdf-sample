package com.ksug;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.integration.util.IntegrationReactiveUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class SourceHandler {

    private final StreamBridge streamBridge;

    Sinks.Many<String> messageProcessor = Sinks.many().multicast().onBackpressureBuffer();

    @GetMapping(value = "/sample/{message}")
    public Mono<Void> messageTestApi(@PathVariable String message) {
        log.info("source message : {}", message);
        return Mono.just(message)
                   .doOnNext(s -> messageProcessor.tryEmitNext(s))
//                .doOnNext(s -> directProcessor.onNext(s))
                   .then();
    }

    @Bean
    public Supplier<Flux<String>> message() {
        log.info("directProcessor!!");
        return () -> this.messageProcessor.asFlux();
    }
}
