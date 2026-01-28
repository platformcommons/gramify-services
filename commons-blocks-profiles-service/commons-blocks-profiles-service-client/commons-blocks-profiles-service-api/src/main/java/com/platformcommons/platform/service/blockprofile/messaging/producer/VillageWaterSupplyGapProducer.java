package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageWaterSupplyGapDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageWaterSupplyGap-related events. This component is responsible for
 * publishing VillageWaterSupplyGap events to designated Kafka topics when VillageWaterSupplyGaps are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEWATERSUPPLYGAP_CREATED} - For newly created VillageWaterSupplyGaps</li>
 *     <li>{@link #TOPIC_VILLAGEWATERSUPPLYGAP_UPDATED} - For VillageWaterSupplyGap updates</li>
 *     <li>{@link #TOPIC_VILLAGEWATERSUPPLYGAP_DELETED} - For VillageWaterSupplyGap deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageWaterSupplyGapProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEWATERSUPPLYGAP_CREATED = "commons-blocks-profiles-service.villagewatersupplygap-created";
    public static final String TOPIC_VILLAGEWATERSUPPLYGAP_UPDATED = "commons-blocks-profiles-service.villagewatersupplygap-updated";
    public static final String TOPIC_VILLAGEWATERSUPPLYGAP_DELETED = "commons-blocks-profiles-service.villagewatersupplygap-deleted";

    private final KafkaTemplate<String,VillageWaterSupplyGapDTO> kafkaTemplate;

    /**
     * Constructs a new VillageWaterSupplyGapProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageWaterSupplyGapProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageWaterSupplyGap-created topic when a new VillageWaterSupplyGap is created.
     *
     * @param dto The VillageWaterSupplyGapDTO containing the created VillageWaterSupplyGap's information
     */
    public void created(VillageWaterSupplyGapDTO dto) {
        log.debug("Entry created(VillageWaterSupplyGap={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERSUPPLYGAP_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageWaterSupplyGap-updated topic when a VillageWaterSupplyGap is modified.
     *
     * @param dto The VillageWaterSupplyGapDTO containing the updated VillageWaterSupplyGap's information
     */
    public void updated(VillageWaterSupplyGapDTO dto) {
        log.debug("Entry updated(VillageWaterSupplyGap={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERSUPPLYGAP_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageWaterSupplyGap-deleted topic when a VillageWaterSupplyGap is removed.
     *
     * @param dto The VillageWaterSupplyGapDTO containing the deleted VillageWaterSupplyGap's information
     */
    public void deleted(VillageWaterSupplyGapDTO dto){
        log.debug("Entry deleted(VillageWaterSupplyGap={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERSUPPLYGAP_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
