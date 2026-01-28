package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHEnterpriseTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHEnterpriseType-related events. This component is responsible for
 * publishing HHEnterpriseType events to designated Kafka topics when HHEnterpriseTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHENTERPRISETYPE_CREATED} - For newly created HHEnterpriseTypes</li>
 *     <li>{@link #TOPIC_HHENTERPRISETYPE_UPDATED} - For HHEnterpriseType updates</li>
 *     <li>{@link #TOPIC_HHENTERPRISETYPE_DELETED} - For HHEnterpriseType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHEnterpriseTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHENTERPRISETYPE_CREATED = "commons-blocks-profiles-service.hhenterprisetype-created";
    public static final String TOPIC_HHENTERPRISETYPE_UPDATED = "commons-blocks-profiles-service.hhenterprisetype-updated";
    public static final String TOPIC_HHENTERPRISETYPE_DELETED = "commons-blocks-profiles-service.hhenterprisetype-deleted";

    private final KafkaTemplate<String,HHEnterpriseTypeDTO> kafkaTemplate;

    /**
     * Constructs a new HHEnterpriseTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHEnterpriseTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHEnterpriseType-created topic when a new HHEnterpriseType is created.
     *
     * @param dto The HHEnterpriseTypeDTO containing the created HHEnterpriseType's information
     */
    public void created(HHEnterpriseTypeDTO dto) {
        log.debug("Entry created(HHEnterpriseType={})", dto);
        kafkaTemplate.send(TOPIC_HHENTERPRISETYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHEnterpriseType-updated topic when a HHEnterpriseType is modified.
     *
     * @param dto The HHEnterpriseTypeDTO containing the updated HHEnterpriseType's information
     */
    public void updated(HHEnterpriseTypeDTO dto) {
        log.debug("Entry updated(HHEnterpriseType={})", dto);
        kafkaTemplate.send(TOPIC_HHENTERPRISETYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHEnterpriseType-deleted topic when a HHEnterpriseType is removed.
     *
     * @param dto The HHEnterpriseTypeDTO containing the deleted HHEnterpriseType's information
     */
    public void deleted(HHEnterpriseTypeDTO dto){
        log.debug("Entry deleted(HHEnterpriseType={})", dto);
        kafkaTemplate.send(TOPIC_HHENTERPRISETYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
