package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.LocalFestivalDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for LocalFestival-related events. This component is responsible for
 * publishing LocalFestival events to designated Kafka topics when LocalFestivals are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_LOCALFESTIVAL_CREATED} - For newly created LocalFestivals</li>
 *     <li>{@link #TOPIC_LOCALFESTIVAL_UPDATED} - For LocalFestival updates</li>
 *     <li>{@link #TOPIC_LOCALFESTIVAL_DELETED} - For LocalFestival deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class LocalFestivalProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_LOCALFESTIVAL_CREATED = "commons-blocks-profiles-service.localfestival-created";
    public static final String TOPIC_LOCALFESTIVAL_UPDATED = "commons-blocks-profiles-service.localfestival-updated";
    public static final String TOPIC_LOCALFESTIVAL_DELETED = "commons-blocks-profiles-service.localfestival-deleted";

    private final KafkaTemplate<String,LocalFestivalDTO> kafkaTemplate;

    /**
     * Constructs a new LocalFestivalProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public LocalFestivalProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the LocalFestival-created topic when a new LocalFestival is created.
     *
     * @param dto The LocalFestivalDTO containing the created LocalFestival's information
     */
    public void created(LocalFestivalDTO dto) {
        log.debug("Entry created(LocalFestival={})", dto);
        kafkaTemplate.send(TOPIC_LOCALFESTIVAL_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the LocalFestival-updated topic when a LocalFestival is modified.
     *
     * @param dto The LocalFestivalDTO containing the updated LocalFestival's information
     */
    public void updated(LocalFestivalDTO dto) {
        log.debug("Entry updated(LocalFestival={})", dto);
        kafkaTemplate.send(TOPIC_LOCALFESTIVAL_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the LocalFestival-deleted topic when a LocalFestival is removed.
     *
     * @param dto The LocalFestivalDTO containing the deleted LocalFestival's information
     */
    public void deleted(LocalFestivalDTO dto){
        log.debug("Entry deleted(LocalFestival={})", dto);
        kafkaTemplate.send(TOPIC_LOCALFESTIVAL_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
