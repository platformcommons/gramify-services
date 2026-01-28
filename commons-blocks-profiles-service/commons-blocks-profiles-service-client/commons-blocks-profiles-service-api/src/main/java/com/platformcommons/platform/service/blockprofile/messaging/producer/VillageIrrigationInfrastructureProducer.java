package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageIrrigationInfrastructureDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageIrrigationInfrastructure-related events. This component is responsible for
 * publishing VillageIrrigationInfrastructure events to designated Kafka topics when VillageIrrigationInfrastructures are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_CREATED} - For newly created VillageIrrigationInfrastructures</li>
 *     <li>{@link #TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_UPDATED} - For VillageIrrigationInfrastructure updates</li>
 *     <li>{@link #TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_DELETED} - For VillageIrrigationInfrastructure deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageIrrigationInfrastructureProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_CREATED = "commons-blocks-profiles-service.villageirrigationinfrastructure-created";
    public static final String TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_UPDATED = "commons-blocks-profiles-service.villageirrigationinfrastructure-updated";
    public static final String TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_DELETED = "commons-blocks-profiles-service.villageirrigationinfrastructure-deleted";

    private final KafkaTemplate<String,VillageIrrigationInfrastructureDTO> kafkaTemplate;

    /**
     * Constructs a new VillageIrrigationInfrastructureProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageIrrigationInfrastructureProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageIrrigationInfrastructure-created topic when a new VillageIrrigationInfrastructure is created.
     *
     * @param dto The VillageIrrigationInfrastructureDTO containing the created VillageIrrigationInfrastructure's information
     */
    public void created(VillageIrrigationInfrastructureDTO dto) {
        log.debug("Entry created(VillageIrrigationInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageIrrigationInfrastructure-updated topic when a VillageIrrigationInfrastructure is modified.
     *
     * @param dto The VillageIrrigationInfrastructureDTO containing the updated VillageIrrigationInfrastructure's information
     */
    public void updated(VillageIrrigationInfrastructureDTO dto) {
        log.debug("Entry updated(VillageIrrigationInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageIrrigationInfrastructure-deleted topic when a VillageIrrigationInfrastructure is removed.
     *
     * @param dto The VillageIrrigationInfrastructureDTO containing the deleted VillageIrrigationInfrastructure's information
     */
    public void deleted(VillageIrrigationInfrastructureDTO dto){
        log.debug("Entry deleted(VillageIrrigationInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEIRRIGATIONINFRASTRUCTURE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
