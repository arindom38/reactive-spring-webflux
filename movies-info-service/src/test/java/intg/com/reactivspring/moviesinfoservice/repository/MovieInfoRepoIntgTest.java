package com.reactivspring.moviesinfoservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest //embedded mongo db is connected and only repo context are refreshed not the entire spring context
@ActiveProfiles("sit")
class MovieInfoRepoIntgTest {
    @Autowired
    MovieInfoRepo movieInfoRepo;
}