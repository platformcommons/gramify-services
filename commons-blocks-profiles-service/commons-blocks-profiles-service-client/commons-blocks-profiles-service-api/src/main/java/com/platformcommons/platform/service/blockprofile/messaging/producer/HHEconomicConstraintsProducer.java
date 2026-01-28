package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHEconomicConstraintsDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHEconomicConstraints-related events. This component is responsible for
 * publishing HHEconomicConstraints events to designated Kafka topics when HHEconomicConstraintss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHECONOMICCONSTRAINTS_CREATED} - For newly created HHEconomicConstraintss</li>
 *     <li>{@link #TOPIC_HHECONOMICCONSTRAINTS_UPDATED} - For HHEconomicConstraints updates</li>
 *     <li>{@link #TOPIC_HHECONOMICCONSTRAINTS_DELETED} - For HHEconomicConstraints deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHEconomicConstraintsProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHECONOMICCONSTRAINTS_CREATED = "commons-blocks-profiles-service.hheconomicconstraints-created";
    public static final String TOPIC_HHECONOMICCONSTRAINTS_UPDATED = "commons-blocks-profiles-service.hheconomicconstraints-updated";
    public static final String TOPIC_HHECONOMICCONSTRAINTS_DELETED = "commons-blocks-profiles-service.hheconomicconstraints-deleted";

    private final KafkaTemplate<String,HHEconomicConstraintsDTO> kafkaTemplate;

    /**
     * Constructs a new HHEconomicConstraintsProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHEconomicConstraintsProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHEconomicConstraints-created topic when a new HHEconomicConstraints is created.
     *
     * @param dto The HHEconomicConstraintsDTO containing the created HHEconomicConstraints's information
     */
    public void created(HHEconomicConstraintsDTO dto) {
        log.debug("Entry created(HHEconomicConstraints={})", dto);
        kafkaTemplate.send(TOPIC_HHECONOMICCONSTRAINTS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHEconomicConstraints-updated topic when a HHEconomicConstraints is modified.
     *
     * @param dto The HHEconomicConstraintsDTO containing the updated HHEconomicConstraints's information
     */
    public void updated(HHEconomicConstraintsDTO dto) {
        log.debug("Entry updated(HHEconomicConstraints={})", dto);
        kafkaTemplate.send(TOPIC_HHECONOMICCONSTRAINTS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHEconomicConstraints-deleted topic when a HHEconomicConstraints is removed.
     *
     * @param dto The HHEconomicConstraintsDTO containing the deleted HHEconomicConstraints's information
     */
    public void deleted(HHEconomicConstraintsDTO dto){
        log.debug("Entry deleted(HHEconomicConstraints={})", dto);
        kafkaTemplate.send(TOPIC_HHECONOMICCONSTRAINTS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
