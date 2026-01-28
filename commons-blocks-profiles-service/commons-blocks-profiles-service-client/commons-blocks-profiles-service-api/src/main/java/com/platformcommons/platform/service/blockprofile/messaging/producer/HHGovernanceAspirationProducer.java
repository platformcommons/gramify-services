package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHGovernanceAspirationDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHGovernanceAspiration-related events. This component is responsible for
 * publishing HHGovernanceAspiration events to designated Kafka topics when HHGovernanceAspirations are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHGOVERNANCEASPIRATION_CREATED} - For newly created HHGovernanceAspirations</li>
 *     <li>{@link #TOPIC_HHGOVERNANCEASPIRATION_UPDATED} - For HHGovernanceAspiration updates</li>
 *     <li>{@link #TOPIC_HHGOVERNANCEASPIRATION_DELETED} - For HHGovernanceAspiration deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHGovernanceAspirationProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHGOVERNANCEASPIRATION_CREATED = "commons-blocks-profiles-service.hhgovernanceaspiration-created";
    public static final String TOPIC_HHGOVERNANCEASPIRATION_UPDATED = "commons-blocks-profiles-service.hhgovernanceaspiration-updated";
    public static final String TOPIC_HHGOVERNANCEASPIRATION_DELETED = "commons-blocks-profiles-service.hhgovernanceaspiration-deleted";

    private final KafkaTemplate<String,HHGovernanceAspirationDTO> kafkaTemplate;

    /**
     * Constructs a new HHGovernanceAspirationProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHGovernanceAspirationProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHGovernanceAspiration-created topic when a new HHGovernanceAspiration is created.
     *
     * @param dto The HHGovernanceAspirationDTO containing the created HHGovernanceAspiration's information
     */
    public void created(HHGovernanceAspirationDTO dto) {
        log.debug("Entry created(HHGovernanceAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHGOVERNANCEASPIRATION_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHGovernanceAspiration-updated topic when a HHGovernanceAspiration is modified.
     *
     * @param dto The HHGovernanceAspirationDTO containing the updated HHGovernanceAspiration's information
     */
    public void updated(HHGovernanceAspirationDTO dto) {
        log.debug("Entry updated(HHGovernanceAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHGOVERNANCEASPIRATION_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHGovernanceAspiration-deleted topic when a HHGovernanceAspiration is removed.
     *
     * @param dto The HHGovernanceAspirationDTO containing the deleted HHGovernanceAspiration's information
     */
    public void deleted(HHGovernanceAspirationDTO dto){
        log.debug("Entry deleted(HHGovernanceAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHGOVERNANCEASPIRATION_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
