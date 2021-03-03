package com.david.controller;

import com.david.exceptions.ResourceAlreadyExistsException;
import com.david.exceptions.ResourceNotFoundException;
import com.david.model.Genre;
import com.david.model.Movie;
import com.david.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

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
    public ResponseEntity<Movie> addMovie(@RequestBody Map<String, Object> newMovie) throws ResourceAlreadyExistsException {
        System.out.println("------------------Post method--------------------");
        System.out.println(newMovie);
        System.out.println("------------------Post method--------------------");
        String title = (String) newMovie.get("title");
        Integer numberInStock = Integer.parseInt((String) newMovie.get("numberInStock"));
        Integer dailyRentalRate = Integer.parseInt((String) newMovie.get("dailyRentalRate"));
        String genreName = (String) newMovie.get("genreId");
        Genre genreObj = new Genre(genreName);
        if(service.existsByTitle(title)) {
            throw new ResourceAlreadyExistsException("This movie with title " + title + " already exists.");
        } else {
            Movie newMovie1 = new Movie(title, numberInStock, dailyRentalRate, genreObj);
            Movie added = service.insert(newMovie1);
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
        updated.setGenre(movieDetails.getGenre());

        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(updated.getTitle()));

        Update update = new Update();
        update.set("title", updated.getTitle());
        update.set("numberInStock", updated.getNumberInStock());
        update.set("dailyRentalRate", updated.getDailyRentalRate());
        update.set("genre", updated.getGenre());

        updated = mongoTemplate.findAndModify(query, update, options().returnNew(true).upsert(true), Movie.class);
        assert updated != null;
        return ResponseEntity.ok(updated);

    }
}
