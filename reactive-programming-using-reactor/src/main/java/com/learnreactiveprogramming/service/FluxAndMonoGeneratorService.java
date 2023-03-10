package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
                .log();
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
