package com.david.repo;

import com.david.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends MongoRepository<Movie, Long> {
    Boolean existsBy_idAndTitle(Long _id, String title);
    Boolean existsByTitle(String title);
}
