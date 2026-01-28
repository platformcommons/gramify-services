package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdHumanResourceProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HouseholdHumanResourceProfile-related events. This component is responsible for
 * publishing HouseholdHumanResourceProfile events to designated Kafka topics when HouseholdHumanResourceProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_CREATED} - For newly created HouseholdHumanResourceProfiles</li>
 *     <li>{@link #TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_UPDATED} - For HouseholdHumanResourceProfile updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_DELETED} - For HouseholdHumanResourceProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdHumanResourceProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_CREATED = "commons-blocks-profiles-service.householdhumanresourceprofile-created";
    public static final String TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_UPDATED = "commons-blocks-profiles-service.householdhumanresourceprofile-updated";
    public static final String TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_DELETED = "commons-blocks-profiles-service.householdhumanresourceprofile-deleted";

    private final KafkaTemplate<String,HouseholdHumanResourceProfileDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdHumanResourceProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdHumanResourceProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HouseholdHumanResourceProfile-created topic when a new HouseholdHumanResourceProfile is created.
     *
     * @param dto The HouseholdHumanResourceProfileDTO containing the created HouseholdHumanResourceProfile's information
     */
    public void created(HouseholdHumanResourceProfileDTO dto) {
        log.debug("Entry created(HouseholdHumanResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HouseholdHumanResourceProfile-updated topic when a HouseholdHumanResourceProfile is modified.
     *
     * @param dto The HouseholdHumanResourceProfileDTO containing the updated HouseholdHumanResourceProfile's information
     */
    public void updated(HouseholdHumanResourceProfileDTO dto) {
        log.debug("Entry updated(HouseholdHumanResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HouseholdHumanResourceProfile-deleted topic when a HouseholdHumanResourceProfile is removed.
     *
     * @param dto The HouseholdHumanResourceProfileDTO containing the deleted HouseholdHumanResourceProfile's information
     */
    public void deleted(HouseholdHumanResourceProfileDTO dto){
        log.debug("Entry deleted(HouseholdHumanResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDHUMANRESOURCEPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
