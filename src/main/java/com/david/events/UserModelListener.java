package com.david.events;

import com.david.model.UserDAO;
import com.david.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UserModelListener extends AbstractMongoEventListener<UserDAO> {
    private SequenceGeneratorService sequenceGenerator;

    /**
     * Overloaded Constructor
     * @author David Morales
     * @param sequenceGenerator a sequence generator service instance
     */

    @Autowired
    public UserModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * Auto incremenents the address id before converting
     * @author David Morales
     * @param event the event that occurs before converting for the Enrollee Model
     */

    @Override
    public void onBeforeConvert(BeforeConvertEvent<UserDAO> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(UserDAO.SEQUENCE_NAME));
    }
}
