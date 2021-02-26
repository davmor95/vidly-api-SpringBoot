package com.david.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "genres")
public class Genre implements Serializable {
    private static final long serialVersionUID = -4458870115303573931L;

    @Transient
    public static final String SEQUENCE_NAME = "genre_sequence";

    @Id
    private Long _id;
    private String name;

    public Genre() {
        this("N/A");
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }
}
