package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageIrrigationSystemTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageIrrigationSystemType-related events. This component is responsible for
 * publishing VillageIrrigationSystemType events to designated Kafka topics when VillageIrrigationSystemTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_CREATED} - For newly created VillageIrrigationSystemTypes</li>
 *     <li>{@link #TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_UPDATED} - For VillageIrrigationSystemType updates</li>
 *     <li>{@link #TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_DELETED} - For VillageIrrigationSystemType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageIrrigationSystemTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_CREATED = "commons-blocks-profiles-service.villageirrigationsystemtype-created";
    public static final String TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_UPDATED = "commons-blocks-profiles-service.villageirrigationsystemtype-updated";
    public static final String TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_DELETED = "commons-blocks-profiles-service.villageirrigationsystemtype-deleted";

    private final KafkaTemplate<String,VillageIrrigationSystemTypeDTO> kafkaTemplate;

    /**
     * Constructs a new VillageIrrigationSystemTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageIrrigationSystemTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageIrrigationSystemType-created topic when a new VillageIrrigationSystemType is created.
     *
     * @param dto The VillageIrrigationSystemTypeDTO containing the created VillageIrrigationSystemType's information
     */
    public void created(VillageIrrigationSystemTypeDTO dto) {
        log.debug("Entry created(VillageIrrigationSystemType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageIrrigationSystemType-updated topic when a VillageIrrigationSystemType is modified.
     *
     * @param dto The VillageIrrigationSystemTypeDTO containing the updated VillageIrrigationSystemType's information
     */
    public void updated(VillageIrrigationSystemTypeDTO dto) {
        log.debug("Entry updated(VillageIrrigationSystemType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageIrrigationSystemType-deleted topic when a VillageIrrigationSystemType is removed.
     *
     * @param dto The VillageIrrigationSystemTypeDTO containing the deleted VillageIrrigationSystemType's information
     */
    public void deleted(VillageIrrigationSystemTypeDTO dto){
        log.debug("Entry deleted(VillageIrrigationSystemType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEIRRIGATIONSYSTEMTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
