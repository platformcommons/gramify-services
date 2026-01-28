package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHEmploymentTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHEmploymentType-related events. This component is responsible for
 * publishing HHEmploymentType events to designated Kafka topics when HHEmploymentTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHEMPLOYMENTTYPE_CREATED} - For newly created HHEmploymentTypes</li>
 *     <li>{@link #TOPIC_HHEMPLOYMENTTYPE_UPDATED} - For HHEmploymentType updates</li>
 *     <li>{@link #TOPIC_HHEMPLOYMENTTYPE_DELETED} - For HHEmploymentType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHEmploymentTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHEMPLOYMENTTYPE_CREATED = "commons-blocks-profiles-service.hhemploymenttype-created";
    public static final String TOPIC_HHEMPLOYMENTTYPE_UPDATED = "commons-blocks-profiles-service.hhemploymenttype-updated";
    public static final String TOPIC_HHEMPLOYMENTTYPE_DELETED = "commons-blocks-profiles-service.hhemploymenttype-deleted";

    private final KafkaTemplate<String,HHEmploymentTypeDTO> kafkaTemplate;

    /**
     * Constructs a new HHEmploymentTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHEmploymentTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHEmploymentType-created topic when a new HHEmploymentType is created.
     *
     * @param dto The HHEmploymentTypeDTO containing the created HHEmploymentType's information
     */
    public void created(HHEmploymentTypeDTO dto) {
        log.debug("Entry created(HHEmploymentType={})", dto);
        kafkaTemplate.send(TOPIC_HHEMPLOYMENTTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHEmploymentType-updated topic when a HHEmploymentType is modified.
     *
     * @param dto The HHEmploymentTypeDTO containing the updated HHEmploymentType's information
     */
    public void updated(HHEmploymentTypeDTO dto) {
        log.debug("Entry updated(HHEmploymentType={})", dto);
        kafkaTemplate.send(TOPIC_HHEMPLOYMENTTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHEmploymentType-deleted topic when a HHEmploymentType is removed.
     *
     * @param dto The HHEmploymentTypeDTO containing the deleted HHEmploymentType's information
     */
    public void deleted(HHEmploymentTypeDTO dto){
        log.debug("Entry deleted(HHEmploymentType={})", dto);
        kafkaTemplate.send(TOPIC_HHEMPLOYMENTTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
