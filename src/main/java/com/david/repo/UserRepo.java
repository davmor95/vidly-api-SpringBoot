package com.david.repo;

import com.david.model.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserDAO, Long> {
    Boolean existsByName(String name);
    Optional<UserDAO> findByEmail(String email);
}
