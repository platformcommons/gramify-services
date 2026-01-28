package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.RenewableInfraTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for RenewableInfraType-related events. This component is responsible for
 * publishing RenewableInfraType events to designated Kafka topics when RenewableInfraTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_RENEWABLEINFRATYPE_CREATED} - For newly created RenewableInfraTypes</li>
 *     <li>{@link #TOPIC_RENEWABLEINFRATYPE_UPDATED} - For RenewableInfraType updates</li>
 *     <li>{@link #TOPIC_RENEWABLEINFRATYPE_DELETED} - For RenewableInfraType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class RenewableInfraTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_RENEWABLEINFRATYPE_CREATED = "commons-blocks-profiles-service.renewableinfratype-created";
    public static final String TOPIC_RENEWABLEINFRATYPE_UPDATED = "commons-blocks-profiles-service.renewableinfratype-updated";
    public static final String TOPIC_RENEWABLEINFRATYPE_DELETED = "commons-blocks-profiles-service.renewableinfratype-deleted";

    private final KafkaTemplate<String,RenewableInfraTypeDTO> kafkaTemplate;

    /**
     * Constructs a new RenewableInfraTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public RenewableInfraTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the RenewableInfraType-created topic when a new RenewableInfraType is created.
     *
     * @param dto The RenewableInfraTypeDTO containing the created RenewableInfraType's information
     */
    public void created(RenewableInfraTypeDTO dto) {
        log.debug("Entry created(RenewableInfraType={})", dto);
        kafkaTemplate.send(TOPIC_RENEWABLEINFRATYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the RenewableInfraType-updated topic when a RenewableInfraType is modified.
     *
     * @param dto The RenewableInfraTypeDTO containing the updated RenewableInfraType's information
     */
    public void updated(RenewableInfraTypeDTO dto) {
        log.debug("Entry updated(RenewableInfraType={})", dto);
        kafkaTemplate.send(TOPIC_RENEWABLEINFRATYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the RenewableInfraType-deleted topic when a RenewableInfraType is removed.
     *
     * @param dto The RenewableInfraTypeDTO containing the deleted RenewableInfraType's information
     */
    public void deleted(RenewableInfraTypeDTO dto){
        log.debug("Entry deleted(RenewableInfraType={})", dto);
        kafkaTemplate.send(TOPIC_RENEWABLEINFRATYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
