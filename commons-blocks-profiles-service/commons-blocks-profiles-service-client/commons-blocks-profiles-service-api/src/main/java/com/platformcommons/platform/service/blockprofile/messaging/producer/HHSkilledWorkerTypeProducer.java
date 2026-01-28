package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHSkilledWorkerTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHSkilledWorkerType-related events. This component is responsible for
 * publishing HHSkilledWorkerType events to designated Kafka topics when HHSkilledWorkerTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHSKILLEDWORKERTYPE_CREATED} - For newly created HHSkilledWorkerTypes</li>
 *     <li>{@link #TOPIC_HHSKILLEDWORKERTYPE_UPDATED} - For HHSkilledWorkerType updates</li>
 *     <li>{@link #TOPIC_HHSKILLEDWORKERTYPE_DELETED} - For HHSkilledWorkerType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHSkilledWorkerTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHSKILLEDWORKERTYPE_CREATED = "commons-blocks-profiles-service.hhskilledworkertype-created";
    public static final String TOPIC_HHSKILLEDWORKERTYPE_UPDATED = "commons-blocks-profiles-service.hhskilledworkertype-updated";
    public static final String TOPIC_HHSKILLEDWORKERTYPE_DELETED = "commons-blocks-profiles-service.hhskilledworkertype-deleted";

    private final KafkaTemplate<String,HHSkilledWorkerTypeDTO> kafkaTemplate;

    /**
     * Constructs a new HHSkilledWorkerTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHSkilledWorkerTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHSkilledWorkerType-created topic when a new HHSkilledWorkerType is created.
     *
     * @param dto The HHSkilledWorkerTypeDTO containing the created HHSkilledWorkerType's information
     */
    public void created(HHSkilledWorkerTypeDTO dto) {
        log.debug("Entry created(HHSkilledWorkerType={})", dto);
        kafkaTemplate.send(TOPIC_HHSKILLEDWORKERTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHSkilledWorkerType-updated topic when a HHSkilledWorkerType is modified.
     *
     * @param dto The HHSkilledWorkerTypeDTO containing the updated HHSkilledWorkerType's information
     */
    public void updated(HHSkilledWorkerTypeDTO dto) {
        log.debug("Entry updated(HHSkilledWorkerType={})", dto);
        kafkaTemplate.send(TOPIC_HHSKILLEDWORKERTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHSkilledWorkerType-deleted topic when a HHSkilledWorkerType is removed.
     *
     * @param dto The HHSkilledWorkerTypeDTO containing the deleted HHSkilledWorkerType's information
     */
    public void deleted(HHSkilledWorkerTypeDTO dto){
        log.debug("Entry deleted(HHSkilledWorkerType={})", dto);
        kafkaTemplate.send(TOPIC_HHSKILLEDWORKERTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
