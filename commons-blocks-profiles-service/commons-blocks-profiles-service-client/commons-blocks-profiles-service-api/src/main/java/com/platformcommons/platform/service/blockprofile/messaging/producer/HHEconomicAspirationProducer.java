package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHEconomicAspirationDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHEconomicAspiration-related events. This component is responsible for
 * publishing HHEconomicAspiration events to designated Kafka topics when HHEconomicAspirations are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHECONOMICASPIRATION_CREATED} - For newly created HHEconomicAspirations</li>
 *     <li>{@link #TOPIC_HHECONOMICASPIRATION_UPDATED} - For HHEconomicAspiration updates</li>
 *     <li>{@link #TOPIC_HHECONOMICASPIRATION_DELETED} - For HHEconomicAspiration deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHEconomicAspirationProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHECONOMICASPIRATION_CREATED = "commons-blocks-profiles-service.hheconomicaspiration-created";
    public static final String TOPIC_HHECONOMICASPIRATION_UPDATED = "commons-blocks-profiles-service.hheconomicaspiration-updated";
    public static final String TOPIC_HHECONOMICASPIRATION_DELETED = "commons-blocks-profiles-service.hheconomicaspiration-deleted";

    private final KafkaTemplate<String,HHEconomicAspirationDTO> kafkaTemplate;

    /**
     * Constructs a new HHEconomicAspirationProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHEconomicAspirationProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHEconomicAspiration-created topic when a new HHEconomicAspiration is created.
     *
     * @param dto The HHEconomicAspirationDTO containing the created HHEconomicAspiration's information
     */
    public void created(HHEconomicAspirationDTO dto) {
        log.debug("Entry created(HHEconomicAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHECONOMICASPIRATION_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHEconomicAspiration-updated topic when a HHEconomicAspiration is modified.
     *
     * @param dto The HHEconomicAspirationDTO containing the updated HHEconomicAspiration's information
     */
    public void updated(HHEconomicAspirationDTO dto) {
        log.debug("Entry updated(HHEconomicAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHECONOMICASPIRATION_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHEconomicAspiration-deleted topic when a HHEconomicAspiration is removed.
     *
     * @param dto The HHEconomicAspirationDTO containing the deleted HHEconomicAspiration's information
     */
    public void deleted(HHEconomicAspirationDTO dto){
        log.debug("Entry deleted(HHEconomicAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHECONOMICASPIRATION_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
