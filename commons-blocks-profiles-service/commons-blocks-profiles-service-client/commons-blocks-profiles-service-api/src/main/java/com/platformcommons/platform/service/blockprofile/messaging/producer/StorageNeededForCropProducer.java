package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.StorageNeededForCropDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for StorageNeededForCrop-related events. This component is responsible for
 * publishing StorageNeededForCrop events to designated Kafka topics when StorageNeededForCrops are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_STORAGENEEDEDFORCROP_CREATED} - For newly created StorageNeededForCrops</li>
 *     <li>{@link #TOPIC_STORAGENEEDEDFORCROP_UPDATED} - For StorageNeededForCrop updates</li>
 *     <li>{@link #TOPIC_STORAGENEEDEDFORCROP_DELETED} - For StorageNeededForCrop deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class StorageNeededForCropProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_STORAGENEEDEDFORCROP_CREATED = "commons-blocks-profiles-service.storageneededforcrop-created";
    public static final String TOPIC_STORAGENEEDEDFORCROP_UPDATED = "commons-blocks-profiles-service.storageneededforcrop-updated";
    public static final String TOPIC_STORAGENEEDEDFORCROP_DELETED = "commons-blocks-profiles-service.storageneededforcrop-deleted";

    private final KafkaTemplate<String,StorageNeededForCropDTO> kafkaTemplate;

    /**
     * Constructs a new StorageNeededForCropProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public StorageNeededForCropProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the StorageNeededForCrop-created topic when a new StorageNeededForCrop is created.
     *
     * @param dto The StorageNeededForCropDTO containing the created StorageNeededForCrop's information
     */
    public void created(StorageNeededForCropDTO dto) {
        log.debug("Entry created(StorageNeededForCrop={})", dto);
        kafkaTemplate.send(TOPIC_STORAGENEEDEDFORCROP_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the StorageNeededForCrop-updated topic when a StorageNeededForCrop is modified.
     *
     * @param dto The StorageNeededForCropDTO containing the updated StorageNeededForCrop's information
     */
    public void updated(StorageNeededForCropDTO dto) {
        log.debug("Entry updated(StorageNeededForCrop={})", dto);
        kafkaTemplate.send(TOPIC_STORAGENEEDEDFORCROP_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the StorageNeededForCrop-deleted topic when a StorageNeededForCrop is removed.
     *
     * @param dto The StorageNeededForCropDTO containing the deleted StorageNeededForCrop's information
     */
    public void deleted(StorageNeededForCropDTO dto){
        log.debug("Entry deleted(StorageNeededForCrop={})", dto);
        kafkaTemplate.send(TOPIC_STORAGENEEDEDFORCROP_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
