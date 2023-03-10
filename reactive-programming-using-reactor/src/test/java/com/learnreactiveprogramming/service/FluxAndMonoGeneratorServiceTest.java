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
                .expectNext("4-ARIA","4-NOLA")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMap() {
        var namesFluxMap = serviceTest.namesFluxFlatMap(3);

        StepVerifier.create(namesFluxMap)
                .expectNext("A","R","I","A","N","O","L","A")
                .verifyComplete();

    }

    @Test
    void namesFluxFlatMapAsync() {
            var namesFluxMap = serviceTest.namesFluxFlatMapAsync(3);

            StepVerifier.create(namesFluxMap)
                    //.expectNext("A","R","I","A","N","O","L","A") //makes test fails as data is coming asynchronously , no ordering is maintained
                    .expectNextCount(8)
                    .verifyComplete();

    }

    @Test
    void namesFluxConcatMap() {
        var namesFluxMap = serviceTest.namesFluxConcatMap(3);

        StepVerifier.create(namesFluxMap)
                .expectNext("A","R","I","A","N","O","L","A")
                .verifyComplete();

    }
}