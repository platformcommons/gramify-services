package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdConstraintsProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HouseholdConstraintsProfile-related events. This component is responsible for
 * publishing HouseholdConstraintsProfile events to designated Kafka topics when HouseholdConstraintsProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_CREATED} - For newly created HouseholdConstraintsProfiles</li>
 *     <li>{@link #TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_UPDATED} - For HouseholdConstraintsProfile updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_DELETED} - For HouseholdConstraintsProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdConstraintsProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_CREATED = "commons-blocks-profiles-service.householdconstraintsprofile-created";
    public static final String TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_UPDATED = "commons-blocks-profiles-service.householdconstraintsprofile-updated";
    public static final String TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_DELETED = "commons-blocks-profiles-service.householdconstraintsprofile-deleted";

    private final KafkaTemplate<String,HouseholdConstraintsProfileDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdConstraintsProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdConstraintsProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HouseholdConstraintsProfile-created topic when a new HouseholdConstraintsProfile is created.
     *
     * @param dto The HouseholdConstraintsProfileDTO containing the created HouseholdConstraintsProfile's information
     */
    public void created(HouseholdConstraintsProfileDTO dto) {
        log.debug("Entry created(HouseholdConstraintsProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HouseholdConstraintsProfile-updated topic when a HouseholdConstraintsProfile is modified.
     *
     * @param dto The HouseholdConstraintsProfileDTO containing the updated HouseholdConstraintsProfile's information
     */
    public void updated(HouseholdConstraintsProfileDTO dto) {
        log.debug("Entry updated(HouseholdConstraintsProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HouseholdConstraintsProfile-deleted topic when a HouseholdConstraintsProfile is removed.
     *
     * @param dto The HouseholdConstraintsProfileDTO containing the deleted HouseholdConstraintsProfile's information
     */
    public void deleted(HouseholdConstraintsProfileDTO dto){
        log.debug("Entry deleted(HouseholdConstraintsProfile={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDCONSTRAINTSPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
