package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.SurplusMovedThroughDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for SurplusMovedThrough-related events. This component is responsible for
 * publishing SurplusMovedThrough events to designated Kafka topics when SurplusMovedThroughs are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_SURPLUSMOVEDTHROUGH_CREATED} - For newly created SurplusMovedThroughs</li>
 *     <li>{@link #TOPIC_SURPLUSMOVEDTHROUGH_UPDATED} - For SurplusMovedThrough updates</li>
 *     <li>{@link #TOPIC_SURPLUSMOVEDTHROUGH_DELETED} - For SurplusMovedThrough deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class SurplusMovedThroughProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_SURPLUSMOVEDTHROUGH_CREATED = "commons-blocks-profiles-service.surplusmovedthrough-created";
    public static final String TOPIC_SURPLUSMOVEDTHROUGH_UPDATED = "commons-blocks-profiles-service.surplusmovedthrough-updated";
    public static final String TOPIC_SURPLUSMOVEDTHROUGH_DELETED = "commons-blocks-profiles-service.surplusmovedthrough-deleted";

    private final KafkaTemplate<String,SurplusMovedThroughDTO> kafkaTemplate;

    /**
     * Constructs a new SurplusMovedThroughProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public SurplusMovedThroughProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the SurplusMovedThrough-created topic when a new SurplusMovedThrough is created.
     *
     * @param dto The SurplusMovedThroughDTO containing the created SurplusMovedThrough's information
     */
    public void created(SurplusMovedThroughDTO dto) {
        log.debug("Entry created(SurplusMovedThrough={})", dto);
        kafkaTemplate.send(TOPIC_SURPLUSMOVEDTHROUGH_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the SurplusMovedThrough-updated topic when a SurplusMovedThrough is modified.
     *
     * @param dto The SurplusMovedThroughDTO containing the updated SurplusMovedThrough's information
     */
    public void updated(SurplusMovedThroughDTO dto) {
        log.debug("Entry updated(SurplusMovedThrough={})", dto);
        kafkaTemplate.send(TOPIC_SURPLUSMOVEDTHROUGH_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the SurplusMovedThrough-deleted topic when a SurplusMovedThrough is removed.
     *
     * @param dto The SurplusMovedThroughDTO containing the deleted SurplusMovedThrough's information
     */
    public void deleted(SurplusMovedThroughDTO dto){
        log.debug("Entry deleted(SurplusMovedThrough={})", dto);
        kafkaTemplate.send(TOPIC_SURPLUSMOVEDTHROUGH_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
