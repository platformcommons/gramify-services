package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.EmergingEnterpriseTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for EmergingEnterpriseType-related events. This component is responsible for
 * publishing EmergingEnterpriseType events to designated Kafka topics when EmergingEnterpriseTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_EMERGINGENTERPRISETYPE_CREATED} - For newly created EmergingEnterpriseTypes</li>
 *     <li>{@link #TOPIC_EMERGINGENTERPRISETYPE_UPDATED} - For EmergingEnterpriseType updates</li>
 *     <li>{@link #TOPIC_EMERGINGENTERPRISETYPE_DELETED} - For EmergingEnterpriseType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class EmergingEnterpriseTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_EMERGINGENTERPRISETYPE_CREATED = "commons-blocks-profiles-service.emergingenterprisetype-created";
    public static final String TOPIC_EMERGINGENTERPRISETYPE_UPDATED = "commons-blocks-profiles-service.emergingenterprisetype-updated";
    public static final String TOPIC_EMERGINGENTERPRISETYPE_DELETED = "commons-blocks-profiles-service.emergingenterprisetype-deleted";

    private final KafkaTemplate<String,EmergingEnterpriseTypeDTO> kafkaTemplate;

    /**
     * Constructs a new EmergingEnterpriseTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public EmergingEnterpriseTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the EmergingEnterpriseType-created topic when a new EmergingEnterpriseType is created.
     *
     * @param dto The EmergingEnterpriseTypeDTO containing the created EmergingEnterpriseType's information
     */
    public void created(EmergingEnterpriseTypeDTO dto) {
        log.debug("Entry created(EmergingEnterpriseType={})", dto);
        kafkaTemplate.send(TOPIC_EMERGINGENTERPRISETYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the EmergingEnterpriseType-updated topic when a EmergingEnterpriseType is modified.
     *
     * @param dto The EmergingEnterpriseTypeDTO containing the updated EmergingEnterpriseType's information
     */
    public void updated(EmergingEnterpriseTypeDTO dto) {
        log.debug("Entry updated(EmergingEnterpriseType={})", dto);
        kafkaTemplate.send(TOPIC_EMERGINGENTERPRISETYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the EmergingEnterpriseType-deleted topic when a EmergingEnterpriseType is removed.
     *
     * @param dto The EmergingEnterpriseTypeDTO containing the deleted EmergingEnterpriseType's information
     */
    public void deleted(EmergingEnterpriseTypeDTO dto){
        log.debug("Entry deleted(EmergingEnterpriseType={})", dto);
        kafkaTemplate.send(TOPIC_EMERGINGENTERPRISETYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
