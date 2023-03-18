package com.reactivspring.moviesinfoservice.repository;

import com.reactivspring.moviesinfoservice.domain.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepo extends ReactiveMongoRepository<MovieInfo,String> {
}
