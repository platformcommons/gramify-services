package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageTopInfrastructureNeedDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageTopInfrastructureNeed-related events. This component is responsible for
 * publishing VillageTopInfrastructureNeed events to designated Kafka topics when VillageTopInfrastructureNeeds are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGETOPINFRASTRUCTURENEED_CREATED} - For newly created VillageTopInfrastructureNeeds</li>
 *     <li>{@link #TOPIC_VILLAGETOPINFRASTRUCTURENEED_UPDATED} - For VillageTopInfrastructureNeed updates</li>
 *     <li>{@link #TOPIC_VILLAGETOPINFRASTRUCTURENEED_DELETED} - For VillageTopInfrastructureNeed deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageTopInfrastructureNeedProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGETOPINFRASTRUCTURENEED_CREATED = "commons-blocks-profiles-service.villagetopinfrastructureneed-created";
    public static final String TOPIC_VILLAGETOPINFRASTRUCTURENEED_UPDATED = "commons-blocks-profiles-service.villagetopinfrastructureneed-updated";
    public static final String TOPIC_VILLAGETOPINFRASTRUCTURENEED_DELETED = "commons-blocks-profiles-service.villagetopinfrastructureneed-deleted";

    private final KafkaTemplate<String,VillageTopInfrastructureNeedDTO> kafkaTemplate;

    /**
     * Constructs a new VillageTopInfrastructureNeedProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageTopInfrastructureNeedProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageTopInfrastructureNeed-created topic when a new VillageTopInfrastructureNeed is created.
     *
     * @param dto The VillageTopInfrastructureNeedDTO containing the created VillageTopInfrastructureNeed's information
     */
    public void created(VillageTopInfrastructureNeedDTO dto) {
        log.debug("Entry created(VillageTopInfrastructureNeed={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGETOPINFRASTRUCTURENEED_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageTopInfrastructureNeed-updated topic when a VillageTopInfrastructureNeed is modified.
     *
     * @param dto The VillageTopInfrastructureNeedDTO containing the updated VillageTopInfrastructureNeed's information
     */
    public void updated(VillageTopInfrastructureNeedDTO dto) {
        log.debug("Entry updated(VillageTopInfrastructureNeed={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGETOPINFRASTRUCTURENEED_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageTopInfrastructureNeed-deleted topic when a VillageTopInfrastructureNeed is removed.
     *
     * @param dto The VillageTopInfrastructureNeedDTO containing the deleted VillageTopInfrastructureNeed's information
     */
    public void deleted(VillageTopInfrastructureNeedDTO dto){
        log.debug("Entry deleted(VillageTopInfrastructureNeed={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGETOPINFRASTRUCTURENEED_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
