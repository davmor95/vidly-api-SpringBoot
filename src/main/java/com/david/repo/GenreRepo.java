package com.david.repo;

import com.david.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepo extends MongoRepository<Genre, Long> {
    Boolean existsBy_idAndName(Long _id, String name);
}
