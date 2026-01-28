package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdLivelihoodProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HouseholdLivelihoodProfile-related events. This component is responsible for
 * publishing HouseholdLivelihoodProfile events to designated Kafka topics when HouseholdLivelihoodProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLDLIVELIHOODPROFILE_CREATED} - For newly created HouseholdLivelihoodProfiles</li>
 *     <li>{@link #TOPIC_HOUSEHOLDLIVELIHOODPROFILE_UPDATED} - For HouseholdLivelihoodProfile updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLDLIVELIHOODPROFILE_DELETED} - For HouseholdLivelihoodProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdLivelihoodProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLDLIVELIHOODPROFILE_CREATED = "commons-blocks-profiles-service.householdlivelihoodprofile-created";
    public static final String TOPIC_HOUSEHOLDLIVELIHOODPROFILE_UPDATED = "commons-blocks-profiles-service.householdlivelihoodprofile-updated";
    public static final String TOPIC_HOUSEHOLDLIVELIHOODPROFILE_DELETED = "commons-blocks-profiles-service.householdlivelihoodprofile-deleted";

    private final KafkaTemplate<String,HouseholdLivelihoodProfileDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdLivelihoodProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdLivelihoodProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HouseholdLivelihoodProfile-created topic when a new HouseholdLivelihoodProfile is created.
     *
     * @param dto The HouseholdLivelihoodProfileDTO containing the created HouseholdLivelihoodProfile's information
     */
    public void created(HouseholdLivelihoodProfileDTO dto) {
        log.debug("Entry created(HouseholdLivelihoodProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDLIVELIHOODPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HouseholdLivelihoodProfile-updated topic when a HouseholdLivelihoodProfile is modified.
     *
     * @param dto The HouseholdLivelihoodProfileDTO containing the updated HouseholdLivelihoodProfile's information
     */
    public void updated(HouseholdLivelihoodProfileDTO dto) {
        log.debug("Entry updated(HouseholdLivelihoodProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDLIVELIHOODPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HouseholdLivelihoodProfile-deleted topic when a HouseholdLivelihoodProfile is removed.
     *
     * @param dto The HouseholdLivelihoodProfileDTO containing the deleted HouseholdLivelihoodProfile's information
     */
    public void deleted(HouseholdLivelihoodProfileDTO dto){
        log.debug("Entry deleted(HouseholdLivelihoodProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDLIVELIHOODPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
