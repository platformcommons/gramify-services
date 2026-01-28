package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdAspirationsProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HouseholdAspirationsProfile-related events. This component is responsible for
 * publishing HouseholdAspirationsProfile events to designated Kafka topics when HouseholdAspirationsProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLDASPIRATIONSPROFILE_CREATED} - For newly created HouseholdAspirationsProfiles</li>
 *     <li>{@link #TOPIC_HOUSEHOLDASPIRATIONSPROFILE_UPDATED} - For HouseholdAspirationsProfile updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLDASPIRATIONSPROFILE_DELETED} - For HouseholdAspirationsProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdAspirationsProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLDASPIRATIONSPROFILE_CREATED = "commons-blocks-profiles-service.householdaspirationsprofile-created";
    public static final String TOPIC_HOUSEHOLDASPIRATIONSPROFILE_UPDATED = "commons-blocks-profiles-service.householdaspirationsprofile-updated";
    public static final String TOPIC_HOUSEHOLDASPIRATIONSPROFILE_DELETED = "commons-blocks-profiles-service.householdaspirationsprofile-deleted";

    private final KafkaTemplate<String,HouseholdAspirationsProfileDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdAspirationsProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdAspirationsProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HouseholdAspirationsProfile-created topic when a new HouseholdAspirationsProfile is created.
     *
     * @param dto The HouseholdAspirationsProfileDTO containing the created HouseholdAspirationsProfile's information
     */
    public void created(HouseholdAspirationsProfileDTO dto) {
        log.debug("Entry created(HouseholdAspirationsProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDASPIRATIONSPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HouseholdAspirationsProfile-updated topic when a HouseholdAspirationsProfile is modified.
     *
     * @param dto The HouseholdAspirationsProfileDTO containing the updated HouseholdAspirationsProfile's information
     */
    public void updated(HouseholdAspirationsProfileDTO dto) {
        log.debug("Entry updated(HouseholdAspirationsProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDASPIRATIONSPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HouseholdAspirationsProfile-deleted topic when a HouseholdAspirationsProfile is removed.
     *
     * @param dto The HouseholdAspirationsProfileDTO containing the deleted HouseholdAspirationsProfile's information
     */
    public void deleted(HouseholdAspirationsProfileDTO dto){
        log.debug("Entry deleted(HouseholdAspirationsProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDASPIRATIONSPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
