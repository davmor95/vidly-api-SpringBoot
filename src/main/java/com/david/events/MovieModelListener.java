package com.david.events;


import com.david.model.Movie;
import com.david.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * Model Listener for Enrollee
 * @author David Morales
 * @version v1 (10/14/20)
 */

@Component
public class MovieModelListener extends AbstractMongoEventListener<Movie> {
    private SequenceGeneratorService sequenceGenerator;

    /**
     * Overloaded Constructor
     * @author David Morales
     * @param sequenceGenerator a sequence generator service instance
     */

    @Autowired
    public MovieModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * Auto incremenents the address id before converting
     * @author David Morales
     * @param event the event that occurs before converting for the Enrollee Model
     */

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Movie> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Movie.SEQUENCE_NAME));
    }
}
