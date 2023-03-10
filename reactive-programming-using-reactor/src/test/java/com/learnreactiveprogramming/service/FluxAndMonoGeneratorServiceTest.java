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
                .expectNext("Nola")
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        var namesFluxMap = serviceTest.namesFluxMap();

        StepVerifier.create(namesFluxMap)
                .expectNext("ARIA" , "ROCK","NOLA")
                .verifyComplete();
    }

    @Test
    void namesFluxMapImmutable() {

        var namesFluxMap = serviceTest.namesFluxMapImmutable();

        StepVerifier.create(namesFluxMap)
                .expectNext("Aria", "rock","Nola")
                .verifyComplete();
    }

    @Test
    void testNamesFluxMap() {

        var namesFluxMap = serviceTest.namesFluxMap(3);

        StepVerifier.create(namesFluxMap)
                .expectNext("ARIA","NOLA")
                .verifyComplete();
    }
}