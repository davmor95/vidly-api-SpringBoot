package com.david.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "movies")
public class Movie implements Serializable {
    private static final long serialVersionUID = -4458870115303573931L;

    @Transient
    public static final String SEQUENCE_NAME = "movie_sequence";

    @Id
    private Long _id;

    private String title;
    private Integer numberInStock;
    private Integer dailyRentalRate;
    private Genre genre;

    public Movie() {
        this("N/A", 0, 0, new Genre());
    }

    public Movie(String title, Integer numberInStock, Integer dailyRentalRate, Genre genre) {
        this.title = title;
        this.numberInStock = numberInStock;
        this.dailyRentalRate = dailyRentalRate;
        this.genre = genre;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(Integer numberInStock) {
        this.numberInStock = numberInStock;
    }

    public Integer getDailyRentalRate() {
        return dailyRentalRate;
    }

    public void setDailyRentalRate(Integer dailyRentalRate) {
        this.dailyRentalRate = dailyRentalRate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", numberInStock=" + numberInStock +
                ", dailyRentalRate=" + dailyRentalRate +
                ", genre=" + genre +
                '}';
    }
}
