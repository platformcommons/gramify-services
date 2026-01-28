package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHSavingsModeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHSavingsMode-related events. This component is responsible for
 * publishing HHSavingsMode events to designated Kafka topics when HHSavingsModes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHSAVINGSMODE_CREATED} - For newly created HHSavingsModes</li>
 *     <li>{@link #TOPIC_HHSAVINGSMODE_UPDATED} - For HHSavingsMode updates</li>
 *     <li>{@link #TOPIC_HHSAVINGSMODE_DELETED} - For HHSavingsMode deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHSavingsModeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHSAVINGSMODE_CREATED = "commons-blocks-profiles-service.hhsavingsmode-created";
    public static final String TOPIC_HHSAVINGSMODE_UPDATED = "commons-blocks-profiles-service.hhsavingsmode-updated";
    public static final String TOPIC_HHSAVINGSMODE_DELETED = "commons-blocks-profiles-service.hhsavingsmode-deleted";

    private final KafkaTemplate<String,HHSavingsModeDTO> kafkaTemplate;

    /**
     * Constructs a new HHSavingsModeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHSavingsModeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHSavingsMode-created topic when a new HHSavingsMode is created.
     *
     * @param dto The HHSavingsModeDTO containing the created HHSavingsMode's information
     */
    public void created(HHSavingsModeDTO dto) {
        log.debug("Entry created(HHSavingsMode={})", dto);
        kafkaTemplate.send(TOPIC_HHSAVINGSMODE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHSavingsMode-updated topic when a HHSavingsMode is modified.
     *
     * @param dto The HHSavingsModeDTO containing the updated HHSavingsMode's information
     */
    public void updated(HHSavingsModeDTO dto) {
        log.debug("Entry updated(HHSavingsMode={})", dto);
        kafkaTemplate.send(TOPIC_HHSAVINGSMODE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHSavingsMode-deleted topic when a HHSavingsMode is removed.
     *
     * @param dto The HHSavingsModeDTO containing the deleted HHSavingsMode's information
     */
    public void deleted(HHSavingsModeDTO dto){
        log.debug("Entry deleted(HHSavingsMode={})", dto);
        kafkaTemplate.send(TOPIC_HHSAVINGSMODE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
