package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.CroppingSeasonDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for CroppingSeason-related events. This component is responsible for
 * publishing CroppingSeason events to designated Kafka topics when CroppingSeasons are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_CROPPINGSEASON_CREATED} - For newly created CroppingSeasons</li>
 *     <li>{@link #TOPIC_CROPPINGSEASON_UPDATED} - For CroppingSeason updates</li>
 *     <li>{@link #TOPIC_CROPPINGSEASON_DELETED} - For CroppingSeason deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class CroppingSeasonProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_CROPPINGSEASON_CREATED = "commons-blocks-profiles-service.croppingseason-created";
    public static final String TOPIC_CROPPINGSEASON_UPDATED = "commons-blocks-profiles-service.croppingseason-updated";
    public static final String TOPIC_CROPPINGSEASON_DELETED = "commons-blocks-profiles-service.croppingseason-deleted";

    private final KafkaTemplate<String,CroppingSeasonDTO> kafkaTemplate;

    /**
     * Constructs a new CroppingSeasonProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public CroppingSeasonProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the CroppingSeason-created topic when a new CroppingSeason is created.
     *
     * @param dto The CroppingSeasonDTO containing the created CroppingSeason's information
     */
    public void created(CroppingSeasonDTO dto) {
        log.debug("Entry created(CroppingSeason={})", dto);
        kafkaTemplate.send(TOPIC_CROPPINGSEASON_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the CroppingSeason-updated topic when a CroppingSeason is modified.
     *
     * @param dto The CroppingSeasonDTO containing the updated CroppingSeason's information
     */
    public void updated(CroppingSeasonDTO dto) {
        log.debug("Entry updated(CroppingSeason={})", dto);
        kafkaTemplate.send(TOPIC_CROPPINGSEASON_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the CroppingSeason-deleted topic when a CroppingSeason is removed.
     *
     * @param dto The CroppingSeasonDTO containing the deleted CroppingSeason's information
     */
    public void deleted(CroppingSeasonDTO dto){
        log.debug("Entry deleted(CroppingSeason={})", dto);
        kafkaTemplate.send(TOPIC_CROPPINGSEASON_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
