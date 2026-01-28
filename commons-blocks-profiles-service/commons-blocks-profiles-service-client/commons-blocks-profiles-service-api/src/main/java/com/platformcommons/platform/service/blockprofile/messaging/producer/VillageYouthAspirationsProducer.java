package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageYouthAspirationsDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageYouthAspirations-related events. This component is responsible for
 * publishing VillageYouthAspirations events to designated Kafka topics when VillageYouthAspirationss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEYOUTHASPIRATIONS_CREATED} - For newly created VillageYouthAspirationss</li>
 *     <li>{@link #TOPIC_VILLAGEYOUTHASPIRATIONS_UPDATED} - For VillageYouthAspirations updates</li>
 *     <li>{@link #TOPIC_VILLAGEYOUTHASPIRATIONS_DELETED} - For VillageYouthAspirations deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageYouthAspirationsProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEYOUTHASPIRATIONS_CREATED = "commons-blocks-profiles-service.villageyouthaspirations-created";
    public static final String TOPIC_VILLAGEYOUTHASPIRATIONS_UPDATED = "commons-blocks-profiles-service.villageyouthaspirations-updated";
    public static final String TOPIC_VILLAGEYOUTHASPIRATIONS_DELETED = "commons-blocks-profiles-service.villageyouthaspirations-deleted";

    private final KafkaTemplate<String,VillageYouthAspirationsDTO> kafkaTemplate;

    /**
     * Constructs a new VillageYouthAspirationsProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageYouthAspirationsProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageYouthAspirations-created topic when a new VillageYouthAspirations is created.
     *
     * @param dto The VillageYouthAspirationsDTO containing the created VillageYouthAspirations's information
     */
    public void created(VillageYouthAspirationsDTO dto) {
        log.debug("Entry created(VillageYouthAspirations={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEYOUTHASPIRATIONS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageYouthAspirations-updated topic when a VillageYouthAspirations is modified.
     *
     * @param dto The VillageYouthAspirationsDTO containing the updated VillageYouthAspirations's information
     */
    public void updated(VillageYouthAspirationsDTO dto) {
        log.debug("Entry updated(VillageYouthAspirations={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEYOUTHASPIRATIONS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageYouthAspirations-deleted topic when a VillageYouthAspirations is removed.
     *
     * @param dto The VillageYouthAspirationsDTO containing the deleted VillageYouthAspirations's information
     */
    public void deleted(VillageYouthAspirationsDTO dto){
        log.debug("Entry deleted(VillageYouthAspirations={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEYOUTHASPIRATIONS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
