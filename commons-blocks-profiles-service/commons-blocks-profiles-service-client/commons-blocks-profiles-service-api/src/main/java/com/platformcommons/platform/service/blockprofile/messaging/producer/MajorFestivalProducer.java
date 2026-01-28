package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MajorFestivalDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MajorFestival-related events. This component is responsible for
 * publishing MajorFestival events to designated Kafka topics when MajorFestivals are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MAJORFESTIVAL_CREATED} - For newly created MajorFestivals</li>
 *     <li>{@link #TOPIC_MAJORFESTIVAL_UPDATED} - For MajorFestival updates</li>
 *     <li>{@link #TOPIC_MAJORFESTIVAL_DELETED} - For MajorFestival deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MajorFestivalProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MAJORFESTIVAL_CREATED = "commons-blocks-profiles-service.majorfestival-created";
    public static final String TOPIC_MAJORFESTIVAL_UPDATED = "commons-blocks-profiles-service.majorfestival-updated";
    public static final String TOPIC_MAJORFESTIVAL_DELETED = "commons-blocks-profiles-service.majorfestival-deleted";

    private final KafkaTemplate<String,MajorFestivalDTO> kafkaTemplate;

    /**
     * Constructs a new MajorFestivalProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MajorFestivalProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MajorFestival-created topic when a new MajorFestival is created.
     *
     * @param dto The MajorFestivalDTO containing the created MajorFestival's information
     */
    public void created(MajorFestivalDTO dto) {
        log.debug("Entry created(MajorFestival={})", dto);
        kafkaTemplate.send(TOPIC_MAJORFESTIVAL_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MajorFestival-updated topic when a MajorFestival is modified.
     *
     * @param dto The MajorFestivalDTO containing the updated MajorFestival's information
     */
    public void updated(MajorFestivalDTO dto) {
        log.debug("Entry updated(MajorFestival={})", dto);
        kafkaTemplate.send(TOPIC_MAJORFESTIVAL_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MajorFestival-deleted topic when a MajorFestival is removed.
     *
     * @param dto The MajorFestivalDTO containing the deleted MajorFestival's information
     */
    public void deleted(MajorFestivalDTO dto){
        log.debug("Entry deleted(MajorFestival={})", dto);
        kafkaTemplate.send(TOPIC_MAJORFESTIVAL_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
