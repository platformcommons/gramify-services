package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.CurrentStorageMethodDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for CurrentStorageMethod-related events. This component is responsible for
 * publishing CurrentStorageMethod events to designated Kafka topics when CurrentStorageMethods are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_CURRENTSTORAGEMETHOD_CREATED} - For newly created CurrentStorageMethods</li>
 *     <li>{@link #TOPIC_CURRENTSTORAGEMETHOD_UPDATED} - For CurrentStorageMethod updates</li>
 *     <li>{@link #TOPIC_CURRENTSTORAGEMETHOD_DELETED} - For CurrentStorageMethod deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class CurrentStorageMethodProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_CURRENTSTORAGEMETHOD_CREATED = "commons-blocks-profiles-service.currentstoragemethod-created";
    public static final String TOPIC_CURRENTSTORAGEMETHOD_UPDATED = "commons-blocks-profiles-service.currentstoragemethod-updated";
    public static final String TOPIC_CURRENTSTORAGEMETHOD_DELETED = "commons-blocks-profiles-service.currentstoragemethod-deleted";

    private final KafkaTemplate<String,CurrentStorageMethodDTO> kafkaTemplate;

    /**
     * Constructs a new CurrentStorageMethodProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public CurrentStorageMethodProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the CurrentStorageMethod-created topic when a new CurrentStorageMethod is created.
     *
     * @param dto The CurrentStorageMethodDTO containing the created CurrentStorageMethod's information
     */
    public void created(CurrentStorageMethodDTO dto) {
        log.debug("Entry created(CurrentStorageMethod={})", dto);
        kafkaTemplate.send(TOPIC_CURRENTSTORAGEMETHOD_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the CurrentStorageMethod-updated topic when a CurrentStorageMethod is modified.
     *
     * @param dto The CurrentStorageMethodDTO containing the updated CurrentStorageMethod's information
     */
    public void updated(CurrentStorageMethodDTO dto) {
        log.debug("Entry updated(CurrentStorageMethod={})", dto);
        kafkaTemplate.send(TOPIC_CURRENTSTORAGEMETHOD_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the CurrentStorageMethod-deleted topic when a CurrentStorageMethod is removed.
     *
     * @param dto The CurrentStorageMethodDTO containing the deleted CurrentStorageMethod's information
     */
    public void deleted(CurrentStorageMethodDTO dto){
        log.debug("Entry deleted(CurrentStorageMethod={})", dto);
        kafkaTemplate.send(TOPIC_CURRENTSTORAGEMETHOD_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
