package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;


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

    @Test
    void nameMonoFlatMap() {

        var nameMonoFlatMap = serviceTest.nameMonoFlatMap(3);

        StepVerifier.create(nameMonoFlatMap)
                .expectNext(List.of("D","I","L","I","P"))
                .verifyComplete();
    }

    @Test
    void nameMonoFlatMapMany() {

        var nameMonoFlatMapMany = serviceTest.nameMonoFlatMapMany(3);

        StepVerifier.create(nameMonoFlatMapMany)
                .expectNextSequence(List.of("D","I","L","I","P"))
                .verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        var nameFluxTransform = serviceTest.namesFluxTransform(3);
        StepVerifier.create(nameFluxTransform)
                .expectNext("A","R","I","A","N","O","L","A")
                .verifyComplete();
    }

    @Test
    void namesFluxTransformDefaultIfEmpty() {
        var nameFluxTransform = serviceTest.namesFluxTransformDefaultIfEmpty(6);
        StepVerifier.create(nameFluxTransform)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFluxTransformSwitchIfEmpty() {
        var nameFluxTransform = serviceTest.namesFluxTransformSwitchIfEmpty(6);
        StepVerifier.create(nameFluxTransform)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void namesFluxConcat() {

        var nameFlux = serviceTest.namesFluxConcat();

        StepVerifier.create(nameFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void namesFluxConcatWith() {
        var nameFlux = serviceTest.namesFluxConcatWith();

        StepVerifier.create(nameFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void namesMonoToFluxConcatWith() {

        var nameFlux = serviceTest.namesMonoToFluxConcatWith();

        StepVerifier.create(nameFlux)
                .expectNext("A","D")
                .verifyComplete();
    }

    @Test
    void exploreMerge() {
        var nameFlux = serviceTest.exploreMerge();

        StepVerifier.create(nameFlux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    void exploreMergeWith() {
        var nameFlux = serviceTest.exploreMergeWith();

        StepVerifier.create(nameFlux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    void exploreMonoToFluxMergeWith() {

        var nameFlux = serviceTest.exploreMonoToFluxMergeWith();

        StepVerifier.create(nameFlux)
                .expectNext("D","A")
                .verifyComplete();
    }

    @Test
    void exploreMergeSequential() {

        var nameFlux = serviceTest.exploreMergeSequential();

        StepVerifier.create(nameFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }
}