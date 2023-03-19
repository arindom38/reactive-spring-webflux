package com.reactivspring.moviesinfoservice.controller;

import com.reactivspring.moviesinfoservice.domain.MovieInfo;
import com.reactivspring.moviesinfoservice.service.MoviesInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MoviesInfoController {

    private final MoviesInfoService moviesInfoService;

    @GetMapping("/movieinfos")
    public Flux<MovieInfo> getAllMovieInfos(){
        return moviesInfoService.getAllMovieInfos();
    }

    @GetMapping("/movieinfos/{id}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id){
        return moviesInfoService.getMovieInfoById(id);
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
        return moviesInfoService.addMovieInfo(movieInfo);
    }

    @PutMapping("/movieinfos/{id}")
    public Mono<MovieInfo> updateMovieInfo(@RequestBody MovieInfo updatedMovieInfo,@PathVariable String id){
        return moviesInfoService.updateMovieInfo(updatedMovieInfo,id);
    }
}
