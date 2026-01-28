package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdDemographicsDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HouseholdDemographics-related events. This component is responsible for
 * publishing HouseholdDemographics events to designated Kafka topics when HouseholdDemographicss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLDDEMOGRAPHICS_CREATED} - For newly created HouseholdDemographicss</li>
 *     <li>{@link #TOPIC_HOUSEHOLDDEMOGRAPHICS_UPDATED} - For HouseholdDemographics updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLDDEMOGRAPHICS_DELETED} - For HouseholdDemographics deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdDemographicsProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLDDEMOGRAPHICS_CREATED = "commons-blocks-profiles-service.householddemographics-created";
    public static final String TOPIC_HOUSEHOLDDEMOGRAPHICS_UPDATED = "commons-blocks-profiles-service.householddemographics-updated";
    public static final String TOPIC_HOUSEHOLDDEMOGRAPHICS_DELETED = "commons-blocks-profiles-service.householddemographics-deleted";

    private final KafkaTemplate<String,HouseholdDemographicsDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdDemographicsProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdDemographicsProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HouseholdDemographics-created topic when a new HouseholdDemographics is created.
     *
     * @param dto The HouseholdDemographicsDTO containing the created HouseholdDemographics's information
     */
    public void created(HouseholdDemographicsDTO dto) {
        log.debug("Entry created(HouseholdDemographics={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDDEMOGRAPHICS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HouseholdDemographics-updated topic when a HouseholdDemographics is modified.
     *
     * @param dto The HouseholdDemographicsDTO containing the updated HouseholdDemographics's information
     */
    public void updated(HouseholdDemographicsDTO dto) {
        log.debug("Entry updated(HouseholdDemographics={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDDEMOGRAPHICS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HouseholdDemographics-deleted topic when a HouseholdDemographics is removed.
     *
     * @param dto The HouseholdDemographicsDTO containing the deleted HouseholdDemographics's information
     */
    public void deleted(HouseholdDemographicsDTO dto){
        log.debug("Entry deleted(HouseholdDemographics={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDDEMOGRAPHICS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
