package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageOtherInfrastructureDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageOtherInfrastructure-related events. This component is responsible for
 * publishing VillageOtherInfrastructure events to designated Kafka topics when VillageOtherInfrastructures are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEOTHERINFRASTRUCTURE_CREATED} - For newly created VillageOtherInfrastructures</li>
 *     <li>{@link #TOPIC_VILLAGEOTHERINFRASTRUCTURE_UPDATED} - For VillageOtherInfrastructure updates</li>
 *     <li>{@link #TOPIC_VILLAGEOTHERINFRASTRUCTURE_DELETED} - For VillageOtherInfrastructure deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageOtherInfrastructureProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEOTHERINFRASTRUCTURE_CREATED = "commons-blocks-profiles-service.villageotherinfrastructure-created";
    public static final String TOPIC_VILLAGEOTHERINFRASTRUCTURE_UPDATED = "commons-blocks-profiles-service.villageotherinfrastructure-updated";
    public static final String TOPIC_VILLAGEOTHERINFRASTRUCTURE_DELETED = "commons-blocks-profiles-service.villageotherinfrastructure-deleted";

    private final KafkaTemplate<String,VillageOtherInfrastructureDTO> kafkaTemplate;

    /**
     * Constructs a new VillageOtherInfrastructureProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageOtherInfrastructureProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageOtherInfrastructure-created topic when a new VillageOtherInfrastructure is created.
     *
     * @param dto The VillageOtherInfrastructureDTO containing the created VillageOtherInfrastructure's information
     */
    public void created(VillageOtherInfrastructureDTO dto) {
        log.debug("Entry created(VillageOtherInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEOTHERINFRASTRUCTURE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageOtherInfrastructure-updated topic when a VillageOtherInfrastructure is modified.
     *
     * @param dto The VillageOtherInfrastructureDTO containing the updated VillageOtherInfrastructure's information
     */
    public void updated(VillageOtherInfrastructureDTO dto) {
        log.debug("Entry updated(VillageOtherInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEOTHERINFRASTRUCTURE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageOtherInfrastructure-deleted topic when a VillageOtherInfrastructure is removed.
     *
     * @param dto The VillageOtherInfrastructureDTO containing the deleted VillageOtherInfrastructure's information
     */
    public void deleted(VillageOtherInfrastructureDTO dto){
        log.debug("Entry deleted(VillageOtherInfrastructure={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEOTHERINFRASTRUCTURE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
