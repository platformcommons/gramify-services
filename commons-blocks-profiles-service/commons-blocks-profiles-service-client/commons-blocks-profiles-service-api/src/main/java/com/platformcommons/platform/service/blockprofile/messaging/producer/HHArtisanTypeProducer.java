package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHArtisanTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHArtisanType-related events. This component is responsible for
 * publishing HHArtisanType events to designated Kafka topics when HHArtisanTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHARTISANTYPE_CREATED} - For newly created HHArtisanTypes</li>
 *     <li>{@link #TOPIC_HHARTISANTYPE_UPDATED} - For HHArtisanType updates</li>
 *     <li>{@link #TOPIC_HHARTISANTYPE_DELETED} - For HHArtisanType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHArtisanTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHARTISANTYPE_CREATED = "commons-blocks-profiles-service.hhartisantype-created";
    public static final String TOPIC_HHARTISANTYPE_UPDATED = "commons-blocks-profiles-service.hhartisantype-updated";
    public static final String TOPIC_HHARTISANTYPE_DELETED = "commons-blocks-profiles-service.hhartisantype-deleted";

    private final KafkaTemplate<String,HHArtisanTypeDTO> kafkaTemplate;

    /**
     * Constructs a new HHArtisanTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHArtisanTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHArtisanType-created topic when a new HHArtisanType is created.
     *
     * @param dto The HHArtisanTypeDTO containing the created HHArtisanType's information
     */
    public void created(HHArtisanTypeDTO dto) {
        log.debug("Entry created(HHArtisanType={})", dto);
        kafkaTemplate.send(TOPIC_HHARTISANTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHArtisanType-updated topic when a HHArtisanType is modified.
     *
     * @param dto The HHArtisanTypeDTO containing the updated HHArtisanType's information
     */
    public void updated(HHArtisanTypeDTO dto) {
        log.debug("Entry updated(HHArtisanType={})", dto);
        kafkaTemplate.send(TOPIC_HHARTISANTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHArtisanType-deleted topic when a HHArtisanType is removed.
     *
     * @param dto The HHArtisanTypeDTO containing the deleted HHArtisanType's information
     */
    public void deleted(HHArtisanTypeDTO dto){
        log.debug("Entry deleted(HHArtisanType={})", dto);
        kafkaTemplate.send(TOPIC_HHARTISANTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
