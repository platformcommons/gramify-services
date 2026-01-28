package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageRoadInfrastructureDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageRoadInfrastructure-related events. This component is responsible for
 * publishing VillageRoadInfrastructure events to designated Kafka topics when VillageRoadInfrastructures are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEROADINFRASTRUCTURE_CREATED} - For newly created VillageRoadInfrastructures</li>
 *     <li>{@link #TOPIC_VILLAGEROADINFRASTRUCTURE_UPDATED} - For VillageRoadInfrastructure updates</li>
 *     <li>{@link #TOPIC_VILLAGEROADINFRASTRUCTURE_DELETED} - For VillageRoadInfrastructure deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageRoadInfrastructureProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEROADINFRASTRUCTURE_CREATED = "commons-blocks-profiles-service.villageroadinfrastructure-created";
    public static final String TOPIC_VILLAGEROADINFRASTRUCTURE_UPDATED = "commons-blocks-profiles-service.villageroadinfrastructure-updated";
    public static final String TOPIC_VILLAGEROADINFRASTRUCTURE_DELETED = "commons-blocks-profiles-service.villageroadinfrastructure-deleted";

    private final KafkaTemplate<String,VillageRoadInfrastructureDTO> kafkaTemplate;

    /**
     * Constructs a new VillageRoadInfrastructureProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageRoadInfrastructureProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageRoadInfrastructure-created topic when a new VillageRoadInfrastructure is created.
     *
     * @param dto The VillageRoadInfrastructureDTO containing the created VillageRoadInfrastructure's information
     */
    public void created(VillageRoadInfrastructureDTO dto) {
        log.debug("Entry created(VillageRoadInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEROADINFRASTRUCTURE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageRoadInfrastructure-updated topic when a VillageRoadInfrastructure is modified.
     *
     * @param dto The VillageRoadInfrastructureDTO containing the updated VillageRoadInfrastructure's information
     */
    public void updated(VillageRoadInfrastructureDTO dto) {
        log.debug("Entry updated(VillageRoadInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEROADINFRASTRUCTURE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageRoadInfrastructure-deleted topic when a VillageRoadInfrastructure is removed.
     *
     * @param dto The VillageRoadInfrastructureDTO containing the deleted VillageRoadInfrastructure's information
     */
    public void deleted(VillageRoadInfrastructureDTO dto){
        log.debug("Entry deleted(VillageRoadInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEROADINFRASTRUCTURE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
