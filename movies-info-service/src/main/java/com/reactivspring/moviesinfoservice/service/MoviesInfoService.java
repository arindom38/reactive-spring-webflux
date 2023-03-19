package com.reactivspring.moviesinfoservice.service;

import com.reactivspring.moviesinfoservice.domain.MovieInfo;
import com.reactivspring.moviesinfoservice.repository.MovieInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MoviesInfoService {

    private final MovieInfoRepo movieInfoRepo;
    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
       return movieInfoRepo.save(movieInfo).log();
    }

    public Flux<MovieInfo> getAllMovieInfos() {
        return movieInfoRepo.findAll().log();
    }
}
