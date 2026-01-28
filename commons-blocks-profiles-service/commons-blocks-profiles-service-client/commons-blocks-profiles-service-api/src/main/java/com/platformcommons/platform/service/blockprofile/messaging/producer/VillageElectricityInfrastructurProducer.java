package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageElectricityInfrastructurDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageElectricityInfrastructur-related events. This component is responsible for
 * publishing VillageElectricityInfrastructur events to designated Kafka topics when VillageElectricityInfrastructurs are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_CREATED} - For newly created VillageElectricityInfrastructurs</li>
 *     <li>{@link #TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_UPDATED} - For VillageElectricityInfrastructur updates</li>
 *     <li>{@link #TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_DELETED} - For VillageElectricityInfrastructur deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageElectricityInfrastructurProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_CREATED = "commons-blocks-profiles-service.villageelectricityinfrastructur-created";
    public static final String TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_UPDATED = "commons-blocks-profiles-service.villageelectricityinfrastructur-updated";
    public static final String TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_DELETED = "commons-blocks-profiles-service.villageelectricityinfrastructur-deleted";

    private final KafkaTemplate<String,VillageElectricityInfrastructurDTO> kafkaTemplate;

    /**
     * Constructs a new VillageElectricityInfrastructurProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageElectricityInfrastructurProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageElectricityInfrastructur-created topic when a new VillageElectricityInfrastructur is created.
     *
     * @param dto The VillageElectricityInfrastructurDTO containing the created VillageElectricityInfrastructur's information
     */
    public void created(VillageElectricityInfrastructurDTO dto) {
        log.debug("Entry created(VillageElectricityInfrastructur={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageElectricityInfrastructur-updated topic when a VillageElectricityInfrastructur is modified.
     *
     * @param dto The VillageElectricityInfrastructurDTO containing the updated VillageElectricityInfrastructur's information
     */
    public void updated(VillageElectricityInfrastructurDTO dto) {
        log.debug("Entry updated(VillageElectricityInfrastructur={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageElectricityInfrastructur-deleted topic when a VillageElectricityInfrastructur is removed.
     *
     * @param dto The VillageElectricityInfrastructurDTO containing the deleted VillageElectricityInfrastructur's information
     */
    public void deleted(VillageElectricityInfrastructurDTO dto){
        log.debug("Entry deleted(VillageElectricityInfrastructur={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEELECTRICITYINFRASTRUCTUR_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
