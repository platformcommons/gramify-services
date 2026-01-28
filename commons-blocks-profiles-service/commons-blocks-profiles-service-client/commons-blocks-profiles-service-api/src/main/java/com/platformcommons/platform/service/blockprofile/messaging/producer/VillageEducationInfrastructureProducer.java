package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageEducationInfrastructureDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageEducationInfrastructure-related events. This component is responsible for
 * publishing VillageEducationInfrastructure events to designated Kafka topics when VillageEducationInfrastructures are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_CREATED} - For newly created VillageEducationInfrastructures</li>
 *     <li>{@link #TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_UPDATED} - For VillageEducationInfrastructure updates</li>
 *     <li>{@link #TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_DELETED} - For VillageEducationInfrastructure deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageEducationInfrastructureProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_CREATED = "commons-blocks-profiles-service.villageeducationinfrastructure-created";
    public static final String TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_UPDATED = "commons-blocks-profiles-service.villageeducationinfrastructure-updated";
    public static final String TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_DELETED = "commons-blocks-profiles-service.villageeducationinfrastructure-deleted";

    private final KafkaTemplate<String,VillageEducationInfrastructureDTO> kafkaTemplate;

    /**
     * Constructs a new VillageEducationInfrastructureProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageEducationInfrastructureProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageEducationInfrastructure-created topic when a new VillageEducationInfrastructure is created.
     *
     * @param dto The VillageEducationInfrastructureDTO containing the created VillageEducationInfrastructure's information
     */
    public void created(VillageEducationInfrastructureDTO dto) {
        log.debug("Entry created(VillageEducationInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageEducationInfrastructure-updated topic when a VillageEducationInfrastructure is modified.
     *
     * @param dto The VillageEducationInfrastructureDTO containing the updated VillageEducationInfrastructure's information
     */
    public void updated(VillageEducationInfrastructureDTO dto) {
        log.debug("Entry updated(VillageEducationInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageEducationInfrastructure-deleted topic when a VillageEducationInfrastructure is removed.
     *
     * @param dto The VillageEducationInfrastructureDTO containing the deleted VillageEducationInfrastructure's information
     */
    public void deleted(VillageEducationInfrastructureDTO dto){
        log.debug("Entry deleted(VillageEducationInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEDUCATIONINFRASTRUCTURE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
