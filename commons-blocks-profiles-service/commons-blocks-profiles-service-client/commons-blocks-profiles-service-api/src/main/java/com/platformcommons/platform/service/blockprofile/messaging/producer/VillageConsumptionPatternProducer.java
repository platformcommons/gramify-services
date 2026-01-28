package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageConsumptionPatternDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageConsumptionPattern-related events. This component is responsible for
 * publishing VillageConsumptionPattern events to designated Kafka topics when VillageConsumptionPatterns are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECONSUMPTIONPATTERN_CREATED} - For newly created VillageConsumptionPatterns</li>
 *     <li>{@link #TOPIC_VILLAGECONSUMPTIONPATTERN_UPDATED} - For VillageConsumptionPattern updates</li>
 *     <li>{@link #TOPIC_VILLAGECONSUMPTIONPATTERN_DELETED} - For VillageConsumptionPattern deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageConsumptionPatternProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECONSUMPTIONPATTERN_CREATED = "commons-blocks-profiles-service.villageconsumptionpattern-created";
    public static final String TOPIC_VILLAGECONSUMPTIONPATTERN_UPDATED = "commons-blocks-profiles-service.villageconsumptionpattern-updated";
    public static final String TOPIC_VILLAGECONSUMPTIONPATTERN_DELETED = "commons-blocks-profiles-service.villageconsumptionpattern-deleted";

    private final KafkaTemplate<String,VillageConsumptionPatternDTO> kafkaTemplate;

    /**
     * Constructs a new VillageConsumptionPatternProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageConsumptionPatternProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageConsumptionPattern-created topic when a new VillageConsumptionPattern is created.
     *
     * @param dto The VillageConsumptionPatternDTO containing the created VillageConsumptionPattern's information
     */
    public void created(VillageConsumptionPatternDTO dto) {
        log.debug("Entry created(VillageConsumptionPattern={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECONSUMPTIONPATTERN_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageConsumptionPattern-updated topic when a VillageConsumptionPattern is modified.
     *
     * @param dto The VillageConsumptionPatternDTO containing the updated VillageConsumptionPattern's information
     */
    public void updated(VillageConsumptionPatternDTO dto) {
        log.debug("Entry updated(VillageConsumptionPattern={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECONSUMPTIONPATTERN_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageConsumptionPattern-deleted topic when a VillageConsumptionPattern is removed.
     *
     * @param dto The VillageConsumptionPatternDTO containing the deleted VillageConsumptionPattern's information
     */
    public void deleted(VillageConsumptionPatternDTO dto){
        log.debug("Entry deleted(VillageConsumptionPattern={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECONSUMPTIONPATTERN_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
