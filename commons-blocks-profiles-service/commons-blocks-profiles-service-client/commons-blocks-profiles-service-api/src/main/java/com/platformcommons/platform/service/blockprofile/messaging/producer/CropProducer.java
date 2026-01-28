package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.CropDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for Crop-related events. This component is responsible for
 * publishing Crop events to designated Kafka topics when Crops are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_CROP_CREATED} - For newly created Crops</li>
 *     <li>{@link #TOPIC_CROP_UPDATED} - For Crop updates</li>
 *     <li>{@link #TOPIC_CROP_DELETED} - For Crop deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class CropProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_CROP_CREATED = "commons-blocks-profiles-service.crop-created";
    public static final String TOPIC_CROP_UPDATED = "commons-blocks-profiles-service.crop-updated";
    public static final String TOPIC_CROP_DELETED = "commons-blocks-profiles-service.crop-deleted";

    private final KafkaTemplate<String,CropDTO> kafkaTemplate;

    /**
     * Constructs a new CropProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public CropProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the Crop-created topic when a new Crop is created.
     *
     * @param dto The CropDTO containing the created Crop's information
     */
    public void created(CropDTO dto) {
        log.debug("Entry created(Crop={})", dto);
        kafkaTemplate.send(TOPIC_CROP_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the Crop-updated topic when a Crop is modified.
     *
     * @param dto The CropDTO containing the updated Crop's information
     */
    public void updated(CropDTO dto) {
        log.debug("Entry updated(Crop={})", dto);
        kafkaTemplate.send(TOPIC_CROP_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the Crop-deleted topic when a Crop is removed.
     *
     * @param dto The CropDTO containing the deleted Crop's information
     */
    public void deleted(CropDTO dto){
        log.debug("Entry deleted(Crop={})", dto);
        kafkaTemplate.send(TOPIC_CROP_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
