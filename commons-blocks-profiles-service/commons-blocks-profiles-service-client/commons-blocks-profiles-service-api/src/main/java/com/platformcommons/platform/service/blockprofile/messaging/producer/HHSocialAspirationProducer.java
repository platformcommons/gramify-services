package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHSocialAspirationDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHSocialAspiration-related events. This component is responsible for
 * publishing HHSocialAspiration events to designated Kafka topics when HHSocialAspirations are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHSOCIALASPIRATION_CREATED} - For newly created HHSocialAspirations</li>
 *     <li>{@link #TOPIC_HHSOCIALASPIRATION_UPDATED} - For HHSocialAspiration updates</li>
 *     <li>{@link #TOPIC_HHSOCIALASPIRATION_DELETED} - For HHSocialAspiration deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHSocialAspirationProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHSOCIALASPIRATION_CREATED = "commons-blocks-profiles-service.hhsocialaspiration-created";
    public static final String TOPIC_HHSOCIALASPIRATION_UPDATED = "commons-blocks-profiles-service.hhsocialaspiration-updated";
    public static final String TOPIC_HHSOCIALASPIRATION_DELETED = "commons-blocks-profiles-service.hhsocialaspiration-deleted";

    private final KafkaTemplate<String,HHSocialAspirationDTO> kafkaTemplate;

    /**
     * Constructs a new HHSocialAspirationProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHSocialAspirationProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHSocialAspiration-created topic when a new HHSocialAspiration is created.
     *
     * @param dto The HHSocialAspirationDTO containing the created HHSocialAspiration's information
     */
    public void created(HHSocialAspirationDTO dto) {
        log.debug("Entry created(HHSocialAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHSOCIALASPIRATION_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHSocialAspiration-updated topic when a HHSocialAspiration is modified.
     *
     * @param dto The HHSocialAspirationDTO containing the updated HHSocialAspiration's information
     */
    public void updated(HHSocialAspirationDTO dto) {
        log.debug("Entry updated(HHSocialAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHSOCIALASPIRATION_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHSocialAspiration-deleted topic when a HHSocialAspiration is removed.
     *
     * @param dto The HHSocialAspirationDTO containing the deleted HHSocialAspiration's information
     */
    public void deleted(HHSocialAspirationDTO dto){
        log.debug("Entry deleted(HHSocialAspiration={})", dto);
        kafkaTemplate.send(TOPIC_HHSOCIALASPIRATION_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
