package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MainReligiousPlaceDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MainReligiousPlace-related events. This component is responsible for
 * publishing MainReligiousPlace events to designated Kafka topics when MainReligiousPlaces are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MAINRELIGIOUSPLACE_CREATED} - For newly created MainReligiousPlaces</li>
 *     <li>{@link #TOPIC_MAINRELIGIOUSPLACE_UPDATED} - For MainReligiousPlace updates</li>
 *     <li>{@link #TOPIC_MAINRELIGIOUSPLACE_DELETED} - For MainReligiousPlace deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MainReligiousPlaceProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MAINRELIGIOUSPLACE_CREATED = "commons-blocks-profiles-service.mainreligiousplace-created";
    public static final String TOPIC_MAINRELIGIOUSPLACE_UPDATED = "commons-blocks-profiles-service.mainreligiousplace-updated";
    public static final String TOPIC_MAINRELIGIOUSPLACE_DELETED = "commons-blocks-profiles-service.mainreligiousplace-deleted";

    private final KafkaTemplate<String,MainReligiousPlaceDTO> kafkaTemplate;

    /**
     * Constructs a new MainReligiousPlaceProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MainReligiousPlaceProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MainReligiousPlace-created topic when a new MainReligiousPlace is created.
     *
     * @param dto The MainReligiousPlaceDTO containing the created MainReligiousPlace's information
     */
    public void created(MainReligiousPlaceDTO dto) {
        log.debug("Entry created(MainReligiousPlace={})", dto);
        kafkaTemplate.send(TOPIC_MAINRELIGIOUSPLACE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MainReligiousPlace-updated topic when a MainReligiousPlace is modified.
     *
     * @param dto The MainReligiousPlaceDTO containing the updated MainReligiousPlace's information
     */
    public void updated(MainReligiousPlaceDTO dto) {
        log.debug("Entry updated(MainReligiousPlace={})", dto);
        kafkaTemplate.send(TOPIC_MAINRELIGIOUSPLACE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MainReligiousPlace-deleted topic when a MainReligiousPlace is removed.
     *
     * @param dto The MainReligiousPlaceDTO containing the deleted MainReligiousPlace's information
     */
    public void deleted(MainReligiousPlaceDTO dto){
        log.debug("Entry deleted(MainReligiousPlace={})", dto);
        kafkaTemplate.send(TOPIC_MAINRELIGIOUSPLACE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
