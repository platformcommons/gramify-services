package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdFinancialBehaviourDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HouseholdFinancialBehaviour-related events. This component is responsible for
 * publishing HouseholdFinancialBehaviour events to designated Kafka topics when HouseholdFinancialBehaviours are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_CREATED} - For newly created HouseholdFinancialBehaviours</li>
 *     <li>{@link #TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_UPDATED} - For HouseholdFinancialBehaviour updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_DELETED} - For HouseholdFinancialBehaviour deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdFinancialBehaviourProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_CREATED = "commons-blocks-profiles-service.householdfinancialbehaviour-created";
    public static final String TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_UPDATED = "commons-blocks-profiles-service.householdfinancialbehaviour-updated";
    public static final String TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_DELETED = "commons-blocks-profiles-service.householdfinancialbehaviour-deleted";

    private final KafkaTemplate<String,HouseholdFinancialBehaviourDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdFinancialBehaviourProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdFinancialBehaviourProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HouseholdFinancialBehaviour-created topic when a new HouseholdFinancialBehaviour is created.
     *
     * @param dto The HouseholdFinancialBehaviourDTO containing the created HouseholdFinancialBehaviour's information
     */
    public void created(HouseholdFinancialBehaviourDTO dto) {
        log.debug("Entry created(HouseholdFinancialBehaviour={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HouseholdFinancialBehaviour-updated topic when a HouseholdFinancialBehaviour is modified.
     *
     * @param dto The HouseholdFinancialBehaviourDTO containing the updated HouseholdFinancialBehaviour's information
     */
    public void updated(HouseholdFinancialBehaviourDTO dto) {
        log.debug("Entry updated(HouseholdFinancialBehaviour={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HouseholdFinancialBehaviour-deleted topic when a HouseholdFinancialBehaviour is removed.
     *
     * @param dto The HouseholdFinancialBehaviourDTO containing the deleted HouseholdFinancialBehaviour's information
     */
    public void deleted(HouseholdFinancialBehaviourDTO dto){
        log.debug("Entry deleted(HouseholdFinancialBehaviour={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDFINANCIALBEHAVIOUR_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
