package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;

import java.util.List;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("Aria", "rock","Nola"));
    }
    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(name-> System.out.println("Name is: "+name));
    }
}
