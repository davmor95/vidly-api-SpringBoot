package com.david.controller;

import com.david.exceptions.ResourceAlreadyExistsException;
import com.david.model.Genre;
import com.david.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class GenreController {
    @Autowired
    GenreRepo service;

    @Autowired
    private MongoOperations mongoTemplate;
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return service.findAll();
    }

    @PostMapping("/genres")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre addGenre) throws ResourceAlreadyExistsException {
        if(service.existsBy_idAndName( addGenre.getId(), addGenre.getName())) {
            throw new ResourceAlreadyExistsException("This genre with name " + addGenre.getName() + " with id: " + " " + addGenre.getId() + " already exists.");
        } else {
            Genre added = service.insert(addGenre);
            return ResponseEntity.status(200).body(added);
        }
    }

}
