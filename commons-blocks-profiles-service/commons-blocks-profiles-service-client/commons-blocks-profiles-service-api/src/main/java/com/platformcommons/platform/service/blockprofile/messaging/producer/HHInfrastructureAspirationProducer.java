package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHInfrastructureAspirationDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHInfrastructureAspiration-related events. This component is responsible for
 * publishing HHInfrastructureAspiration events to designated Kafka topics when HHInfrastructureAspirations are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHINFRASTRUCTUREASPIRATION_CREATED} - For newly created HHInfrastructureAspirations</li>
 *     <li>{@link #TOPIC_HHINFRASTRUCTUREASPIRATION_UPDATED} - For HHInfrastructureAspiration updates</li>
 *     <li>{@link #TOPIC_HHINFRASTRUCTUREASPIRATION_DELETED} - For HHInfrastructureAspiration deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHInfrastructureAspirationProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHINFRASTRUCTUREASPIRATION_CREATED = "commons-blocks-profiles-service.hhinfrastructureaspiration-created";
    public static final String TOPIC_HHINFRASTRUCTUREASPIRATION_UPDATED = "commons-blocks-profiles-service.hhinfrastructureaspiration-updated";
    public static final String TOPIC_HHINFRASTRUCTUREASPIRATION_DELETED = "commons-blocks-profiles-service.hhinfrastructureaspiration-deleted";

    private final KafkaTemplate<String,HHInfrastructureAspirationDTO> kafkaTemplate;

    /**
     * Constructs a new HHInfrastructureAspirationProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHInfrastructureAspirationProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHInfrastructureAspiration-created topic when a new HHInfrastructureAspiration is created.
     *
     * @param dto The HHInfrastructureAspirationDTO containing the created HHInfrastructureAspiration's information
     */
    public void created(HHInfrastructureAspirationDTO dto) {
        log.debug("Entry created(HHInfrastructureAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHINFRASTRUCTUREASPIRATION_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHInfrastructureAspiration-updated topic when a HHInfrastructureAspiration is modified.
     *
     * @param dto The HHInfrastructureAspirationDTO containing the updated HHInfrastructureAspiration's information
     */
    public void updated(HHInfrastructureAspirationDTO dto) {
        log.debug("Entry updated(HHInfrastructureAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHINFRASTRUCTUREASPIRATION_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHInfrastructureAspiration-deleted topic when a HHInfrastructureAspiration is removed.
     *
     * @param dto The HHInfrastructureAspirationDTO containing the deleted HHInfrastructureAspiration's information
     */
    public void deleted(HHInfrastructureAspirationDTO dto){
        log.debug("Entry deleted(HHInfrastructureAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHINFRASTRUCTUREASPIRATION_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
