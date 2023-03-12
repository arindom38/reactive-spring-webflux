package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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

    //user to transform form one type to  another and can reuse this functional interface
    public Flux<String> namesFluxTransform(int stringLength){

        Function<Flux<String>,Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength);

        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .transform(filterMap)
                .flatMap(s-> splitString(s))
                .log();
    }

    public Flux<String> namesFluxTransformDefaultIfEmpty(int stringLength){

        Function<Flux<String>,Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength);

        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .transform(filterMap)
                .defaultIfEmpty("default")
                .log();
    }

    //difference between switch if empty and default if empty is switch empty take a publisher as arguement
    public Flux<String> namesFluxTransformSwitchIfEmpty(int stringLength){

        Function<Flux<String>,Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength)
                        .flatMap(this::splitString);

        var defaultFlux = Flux.just("default")
                .transform(filterMap);

        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .transform(filterMap)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> namesFluxFlatMapAsync(int stringLength){
        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s-> splitStringWithDelay(s))
                .log();
    }

    //Asynchronous call with maintaining ordering , but take more processing time than flat map async call
    public Flux<String> namesFluxConcatMap(int stringLength){
        return Flux.fromIterable(List.of("Aria", "ben","Nola"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap(s-> splitStringWithFixedDelay(s))
                .log();
    }


    public Flux<String> namesFluxConcat(){
        var flux1 = Flux.just("A","B","C");

        var flux2 = Flux.just("D","E","F");

        //using static concat method in flux (not available for mono)
        return Flux.concat(flux1,flux2);

    }

    //concatWith is an instance method
    public Flux<String> namesFluxConcatWith(){
        var flux1 = Flux.just("A","B","C");

        var flux2 = Flux.just("D","E","F");

        //using static concat method
        return flux1.concatWith(flux2);

    }

    public Flux<String> namesMonoToFluxConcatWith(){
        var mono1 = Mono.just("A");

        var mono2 = Mono.just("D");

        //using static concat method
        return mono1.concatWith(mono2);

    }

    //merger doesn't execute in sequence , it runs at same time
    public Flux<String> exploreMerge(){
        var flux1 = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));

        var flux2 = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(120));

        //100 > A > 120 > D > 200 > B > 220 > E ..........
        return Flux.merge(flux1,flux2).log();

    }

    //concatWith is an instance method
    public Flux<String> exploreMergeWith(){
        var flux1 = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));

        var flux2 = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(120));

        //using static concat method
        return flux1.mergeWith(flux2).log();

    }

    public Flux<String> exploreMonoToFluxMergeWith(){
        var mono1 = Mono.just("A")
                .delayElement(Duration.ofMillis(120));

        var mono2 = Mono.just("D")
                .delayElement(Duration.ofMillis(90));

        //using static concat method
        return mono1.mergeWith(mono2).log();

    }



    public Flux<String>  splitString(String str){
        return Flux.fromArray(str.split(""));
    }

    public Flux<String> splitStringWithDelay(String str){
        return Flux.fromArray(str.split(""))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }

    public Flux<String> splitStringWithFixedDelay(String str){
        return Flux.fromArray(str.split(""))
                .delayElements(Duration.ofMillis(1000));
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

    public Mono<List<String>> nameMonoFlatMap(int stringLength){
        return Mono.just("Dilip")
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                .flatMap(this::splitStringMono)
                .log();
    }


    //flatMapMany is used when a mono is transformed into a flux
    public Flux<String> nameMonoFlatMapMany(int stringLength){
        return Mono.just("Dilip")
                .map(String::toUpperCase)
                .filter(s-> s.length() > stringLength)
                .flatMapMany(this::splitString)
                .log();
    }

    public Mono<List<String>> splitStringMono(String str){
        return Mono.just(List.of(str.split("")));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(name-> System.out.println("Name is: "+name));

        service.nameMono()
                .subscribe(name-> System.out.println("Mono Name is: "+name));
    }
}
