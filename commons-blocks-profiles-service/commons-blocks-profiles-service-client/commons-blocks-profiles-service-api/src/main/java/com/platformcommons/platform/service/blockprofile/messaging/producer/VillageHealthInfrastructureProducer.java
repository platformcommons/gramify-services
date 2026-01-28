package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageHealthInfrastructureDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageHealthInfrastructure-related events. This component is responsible for
 * publishing VillageHealthInfrastructure events to designated Kafka topics when VillageHealthInfrastructures are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEHEALTHINFRASTRUCTURE_CREATED} - For newly created VillageHealthInfrastructures</li>
 *     <li>{@link #TOPIC_VILLAGEHEALTHINFRASTRUCTURE_UPDATED} - For VillageHealthInfrastructure updates</li>
 *     <li>{@link #TOPIC_VILLAGEHEALTHINFRASTRUCTURE_DELETED} - For VillageHealthInfrastructure deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageHealthInfrastructureProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEHEALTHINFRASTRUCTURE_CREATED = "commons-blocks-profiles-service.villagehealthinfrastructure-created";
    public static final String TOPIC_VILLAGEHEALTHINFRASTRUCTURE_UPDATED = "commons-blocks-profiles-service.villagehealthinfrastructure-updated";
    public static final String TOPIC_VILLAGEHEALTHINFRASTRUCTURE_DELETED = "commons-blocks-profiles-service.villagehealthinfrastructure-deleted";

    private final KafkaTemplate<String,VillageHealthInfrastructureDTO> kafkaTemplate;

    /**
     * Constructs a new VillageHealthInfrastructureProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageHealthInfrastructureProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageHealthInfrastructure-created topic when a new VillageHealthInfrastructure is created.
     *
     * @param dto The VillageHealthInfrastructureDTO containing the created VillageHealthInfrastructure's information
     */
    public void created(VillageHealthInfrastructureDTO dto) {
        log.debug("Entry created(VillageHealthInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHEALTHINFRASTRUCTURE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageHealthInfrastructure-updated topic when a VillageHealthInfrastructure is modified.
     *
     * @param dto The VillageHealthInfrastructureDTO containing the updated VillageHealthInfrastructure's information
     */
    public void updated(VillageHealthInfrastructureDTO dto) {
        log.debug("Entry updated(VillageHealthInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHEALTHINFRASTRUCTURE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageHealthInfrastructure-deleted topic when a VillageHealthInfrastructure is removed.
     *
     * @param dto The VillageHealthInfrastructureDTO containing the deleted VillageHealthInfrastructure's information
     */
    public void deleted(VillageHealthInfrastructureDTO dto){
        log.debug("Entry deleted(VillageHealthInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHEALTHINFRASTRUCTURE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
