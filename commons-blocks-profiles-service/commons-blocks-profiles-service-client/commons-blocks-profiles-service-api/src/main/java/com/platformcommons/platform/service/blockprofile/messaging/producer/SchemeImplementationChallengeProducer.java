package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.SchemeImplementationChallengeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for SchemeImplementationChallenge-related events. This component is responsible for
 * publishing SchemeImplementationChallenge events to designated Kafka topics when SchemeImplementationChallenges are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_CREATED} - For newly created SchemeImplementationChallenges</li>
 *     <li>{@link #TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_UPDATED} - For SchemeImplementationChallenge updates</li>
 *     <li>{@link #TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_DELETED} - For SchemeImplementationChallenge deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class SchemeImplementationChallengeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_CREATED = "commons-blocks-profiles-service.schemeimplementationchallenge-created";
    public static final String TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_UPDATED = "commons-blocks-profiles-service.schemeimplementationchallenge-updated";
    public static final String TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_DELETED = "commons-blocks-profiles-service.schemeimplementationchallenge-deleted";

    private final KafkaTemplate<String,SchemeImplementationChallengeDTO> kafkaTemplate;

    /**
     * Constructs a new SchemeImplementationChallengeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public SchemeImplementationChallengeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the SchemeImplementationChallenge-created topic when a new SchemeImplementationChallenge is created.
     *
     * @param dto The SchemeImplementationChallengeDTO containing the created SchemeImplementationChallenge's information
     */
    public void created(SchemeImplementationChallengeDTO dto) {
        log.debug("Entry created(SchemeImplementationChallenge={})", dto);
        kafkaTemplate.send(TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the SchemeImplementationChallenge-updated topic when a SchemeImplementationChallenge is modified.
     *
     * @param dto The SchemeImplementationChallengeDTO containing the updated SchemeImplementationChallenge's information
     */
    public void updated(SchemeImplementationChallengeDTO dto) {
        log.debug("Entry updated(SchemeImplementationChallenge={})", dto);
        kafkaTemplate.send(TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the SchemeImplementationChallenge-deleted topic when a SchemeImplementationChallenge is removed.
     *
     * @param dto The SchemeImplementationChallengeDTO containing the deleted SchemeImplementationChallenge's information
     */
    public void deleted(SchemeImplementationChallengeDTO dto){
        log.debug("Entry deleted(SchemeImplementationChallenge={})", dto);
        kafkaTemplate.send(TOPIC_SCHEMEIMPLEMENTATIONCHALLENGE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
