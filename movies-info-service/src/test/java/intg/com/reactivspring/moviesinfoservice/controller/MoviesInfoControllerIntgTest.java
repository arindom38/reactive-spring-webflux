package com.reactivspring.moviesinfoservice.controller;

import com.reactivspring.moviesinfoservice.domain.MovieInfo;
import com.reactivspring.moviesinfoservice.repository.MovieInfoRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //this reload spring context with different port
@ActiveProfiles("test") //this profile must be different from all the profiles that are used for this project
@AutoConfigureWebTestClient //includes WebTestClient for test
class MoviesInfoControllerIntgTest {

    DateTimeFormatter dayMonthYearFrmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String MOVIE_INFO_POST_URI = "/v1/movieinfos";

    @Autowired
    MovieInfoRepo movieInfoRepo;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        var moviesInfo = List.of(
                new MovieInfo(null, "John Wick : chapter 1", 2014,
                        List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne", "Michael Nyqvist"), LocalDate.parse("24-10-2014", dayMonthYearFrmt)),
                new MovieInfo(null, "John Wick : chapter 2", 2017,
                        List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne","Ruby Rose"), LocalDate.parse("09-02-2017",dayMonthYearFrmt)),
                new MovieInfo(null, "John Wick : chapter 3", 2019,
                        List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne","Halle Berry"), LocalDate.parse("17-05-2019",dayMonthYearFrmt)),
                new MovieInfo("1001", "John Wick : chapter 4", 2023,
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
    void addMovieInfo() {
        var movieInfo =  new MovieInfo("2001", "John Wick : chapter 4", 2023,
                List.of("Keanu Reeves", "Lance Reddick", "Ian Mcsahne","Scott Adkins"), LocalDate.parse("24-03-2023",dayMonthYearFrmt));

        webTestClient
                .post()
                .uri(MOVIE_INFO_POST_URI)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                   var reponse = movieInfoEntityExchangeResult.getResponseBody();
                   assert reponse != null;
                   assert  reponse.getMovieInfoId().equals("2001");
                });
    }

    @Test
    void getAllMovieInfos() {

        webTestClient
                .get()
                .uri(MOVIE_INFO_POST_URI)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(4);

    }
}