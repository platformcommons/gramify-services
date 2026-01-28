package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for Household-related events. This component is responsible for
 * publishing Household events to designated Kafka topics when Households are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLD_CREATED} - For newly created Households</li>
 *     <li>{@link #TOPIC_HOUSEHOLD_UPDATED} - For Household updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLD_DELETED} - For Household deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLD_CREATED = "commons-blocks-profiles-service.household-created";
    public static final String TOPIC_HOUSEHOLD_UPDATED = "commons-blocks-profiles-service.household-updated";
    public static final String TOPIC_HOUSEHOLD_DELETED = "commons-blocks-profiles-service.household-deleted";

    private final KafkaTemplate<String,HouseholdDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the Household-created topic when a new Household is created.
     *
     * @param dto The HouseholdDTO containing the created Household's information
     */
    public void created(HouseholdDTO dto) {
        log.debug("Entry created(Household={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLD_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the Household-updated topic when a Household is modified.
     *
     * @param dto The HouseholdDTO containing the updated Household's information
     */
    public void updated(HouseholdDTO dto) {
        log.debug("Entry updated(Household={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLD_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the Household-deleted topic when a Household is removed.
     *
     * @param dto The HouseholdDTO containing the deleted Household's information
     */
    public void deleted(HouseholdDTO dto){
        log.debug("Entry deleted(Household={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLD_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
