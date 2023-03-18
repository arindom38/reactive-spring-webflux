package com.reactivspring.moviesinfoservice.repository;

import com.reactivspring.moviesinfoservice.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
* Each time a test case is run embedded mongo is started with dynamic port unless we define profile specific
* A cluster is created with such port before each test and connection is closed after test
* This embedded mongo act as a mock database
* */
@DataMongoTest //embedded mongo db is connected and only repo context are refreshed not the entire spring context
@ActiveProfiles("test")
class MovieInfoRepoIntgTest {
    @Autowired
    MovieInfoRepo movieInfoRepo;

    @BeforeEach
    void setUp() {
        DateTimeFormatter dayMonthYearFrmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var moviesInfo = List.of(
                new MovieInfo(null, "John Wick : chapter 1", 2014,
                        List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne", "Michael Nyqvist"), LocalDate.parse("24-10-2014", dayMonthYearFrmt)),
                new MovieInfo(null, "John Wick : chapter 2", 2017,
                        List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne","Ruby Rose"), LocalDate.parse("09-02-2017",dayMonthYearFrmt)),
                new MovieInfo(null, "John Wick : chapter 3", 2019,
                        List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne","Halle Berry"), LocalDate.parse("17-05-2019",dayMonthYearFrmt)),
                new MovieInfo(null, "John Wick : chapter 4", 2023,
                        List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne","Scott Adkins"), LocalDate.parse("24-03-2023",dayMonthYearFrmt))
        );

        movieInfoRepo.saveAll(moviesInfo)
                .blockLast(); // must use block last or it will be asynchronous call so date may be fetched before even persist.
    }

    @AfterEach
    void tearDown() {
        movieInfoRepo.deleteAll().block();
    }

    @Test
    void findAll() {

        var moviesInfo = movieInfoRepo.findAll().log();

        StepVerifier.create(moviesInfo)
                .expectNextCount(4)
                .verifyComplete();
    }
}