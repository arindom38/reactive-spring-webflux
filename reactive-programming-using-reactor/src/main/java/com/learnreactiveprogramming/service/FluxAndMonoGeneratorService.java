package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("Aria", "rock","Nola"))
                .log();
    }

    public Flux<String> namesFluxMap(){
        return Flux.fromIterable(List.of("Aria", "rock","Nola"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFluxMap(int stringLength){
        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s-> s.length()+"-"+s)
                .log();
    }

    public Flux<String> namesFluxFlatMap(int stringLength){
        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s-> splitString(s))
                .log();
    }

    public Flux<String> namesFluxFlatMapAsync(int stringLength){
        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s-> splitStringAsync(s))
                .log();
    }

    public Flux<String>  splitString(String str){
        return Flux.fromArray(str.split(""));
    }

    public Flux<String>  splitStringAsync(String str){
        return Flux.fromArray(str.split(""))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }
    public Flux<String> namesFluxMapImmutable(){
        var namesFlux =  Flux.fromIterable(List.of("Aria", "rock","Nola"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Mono<String> nameMono(){
        return Mono.just("Dilip")
                .log();
    }
    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(name-> System.out.println("Name is: "+name));

        service.nameMono()
                .subscribe(name-> System.out.println("Mono Name is: "+name));
    }
}
