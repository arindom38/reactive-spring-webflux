package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;


class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService serviceTest = new FluxAndMonoGeneratorService();

    @Test
    void nameFlux() {

        var namesFlux = serviceTest.namesFlux();

        StepVerifier.create(namesFlux)
//                .expectNext("Aria", "rock","Nola")
//                .expectNextCount(3)
                .expectNextCount(2)
                .expectNext("Rock")
                .verifyComplete();
    }
}