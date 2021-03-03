package com.david.controller;

import com.david.exceptions.ResourceAlreadyExistsException;
import com.david.model.UserDAO;
import com.david.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    UserRepo service;

    @Autowired
    private MongoOperations mongoTemplate;

    @GetMapping("/users")
    public List<UserDAO> getUsers() {
        return service.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody UserDAO newUser) throws ResourceAlreadyExistsException {
        if (service.existsByName(newUser.getName())) {
//            throw new ResourceAlreadyExistsException("User with name " + newUser.getName() + " already exists");
            return ResponseEntity.badRequest().body("User with name " + newUser.getName() + " already exists");
        } else {
            UserDAO added = service.insert(newUser);
            return ResponseEntity.ok("New user " + newUser.getName() + " added.");
        }
    }
}
