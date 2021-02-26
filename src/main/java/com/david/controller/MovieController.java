package com.david.controller;

import com.david.exceptions.ResourceAlreadyExistsException;
import com.david.exceptions.ResourceNotFoundException;
import com.david.model.Movie;
import com.david.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class MovieController {
    @Autowired
    MovieRepo service;

    @Autowired
    private MongoOperations mongoTemplate;

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return service.findAll();
    }

    @GetMapping("/movies/{id}")
    public Movie getMovieById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Movie> found = service.findById(id);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with id= " + id + " is not found");
        }
        return found.get();
    }


    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie newMovie) throws ResourceAlreadyExistsException {
        if(service.existsBy_idAndTitle(newMovie.getId(), newMovie.getTitle())) {
            throw new ResourceAlreadyExistsException("This movie with title " + newMovie.getTitle() + " and id " + newMovie.getId() + " already exists.");
        } else {
            Movie added = service.insert(newMovie);
            return ResponseEntity.status(201).body(added);
        }
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Movie> found = service.findById(id);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("Movie with id " + id + " does not exist");
        }
        service.deleteById(id);
        return new ResponseEntity<String>("Deleted movie", HttpStatus.ACCEPTED);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> putMovie(@PathVariable Long id, @RequestBody Movie movieDetails) throws ResourceNotFoundException {
        Optional<Movie> found = service.findById(id);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("Movie with id " + id + " does not exist");
        }

        Movie updated = found.get();
        updated.setTitle(movieDetails.getTitle());
        updated.setNumberInStock(movieDetails.getNumberInStock());
        updated.setDailyRentalRate(movieDetails.getDailyRentalRate());
        System.out.println("movie details ------------------------------------------------------------------------------------------------------------------" + movieDetails.getGenre());
        updated.setGenre(movieDetails.getGenre());

        service.save(updated);
        return ResponseEntity.ok(updated);

    }
}
