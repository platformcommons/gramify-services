package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHSocialConstraintsDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHSocialConstraints-related events. This component is responsible for
 * publishing HHSocialConstraints events to designated Kafka topics when HHSocialConstraintss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHSOCIALCONSTRAINTS_CREATED} - For newly created HHSocialConstraintss</li>
 *     <li>{@link #TOPIC_HHSOCIALCONSTRAINTS_UPDATED} - For HHSocialConstraints updates</li>
 *     <li>{@link #TOPIC_HHSOCIALCONSTRAINTS_DELETED} - For HHSocialConstraints deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHSocialConstraintsProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHSOCIALCONSTRAINTS_CREATED = "commons-blocks-profiles-service.hhsocialconstraints-created";
    public static final String TOPIC_HHSOCIALCONSTRAINTS_UPDATED = "commons-blocks-profiles-service.hhsocialconstraints-updated";
    public static final String TOPIC_HHSOCIALCONSTRAINTS_DELETED = "commons-blocks-profiles-service.hhsocialconstraints-deleted";

    private final KafkaTemplate<String,HHSocialConstraintsDTO> kafkaTemplate;

    /**
     * Constructs a new HHSocialConstraintsProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHSocialConstraintsProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHSocialConstraints-created topic when a new HHSocialConstraints is created.
     *
     * @param dto The HHSocialConstraintsDTO containing the created HHSocialConstraints's information
     */
    public void created(HHSocialConstraintsDTO dto) {
        log.debug("Entry created(HHSocialConstraints={})", dto);
        kafkaTemplate.send(TOPIC_HHSOCIALCONSTRAINTS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHSocialConstraints-updated topic when a HHSocialConstraints is modified.
     *
     * @param dto The HHSocialConstraintsDTO containing the updated HHSocialConstraints's information
     */
    public void updated(HHSocialConstraintsDTO dto) {
        log.debug("Entry updated(HHSocialConstraints={})", dto);
        kafkaTemplate.send(TOPIC_HHSOCIALCONSTRAINTS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHSocialConstraints-deleted topic when a HHSocialConstraints is removed.
     *
     * @param dto The HHSocialConstraintsDTO containing the deleted HHSocialConstraints's information
     */
    public void deleted(HHSocialConstraintsDTO dto){
        log.debug("Entry deleted(HHSocialConstraints={})", dto);
        kafkaTemplate.send(TOPIC_HHSOCIALCONSTRAINTS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
