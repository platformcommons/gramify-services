package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonalityDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for ProductionSeasonality-related events. This component is responsible for
 * publishing ProductionSeasonality events to designated Kafka topics when ProductionSeasonalitys are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_PRODUCTIONSEASONALITY_CREATED} - For newly created ProductionSeasonalitys</li>
 *     <li>{@link #TOPIC_PRODUCTIONSEASONALITY_UPDATED} - For ProductionSeasonality updates</li>
 *     <li>{@link #TOPIC_PRODUCTIONSEASONALITY_DELETED} - For ProductionSeasonality deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class ProductionSeasonalityProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_PRODUCTIONSEASONALITY_CREATED = "commons-blocks-profiles-service.productionseasonality-created";
    public static final String TOPIC_PRODUCTIONSEASONALITY_UPDATED = "commons-blocks-profiles-service.productionseasonality-updated";
    public static final String TOPIC_PRODUCTIONSEASONALITY_DELETED = "commons-blocks-profiles-service.productionseasonality-deleted";

    private final KafkaTemplate<String,ProductionSeasonalityDTO> kafkaTemplate;

    /**
     * Constructs a new ProductionSeasonalityProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public ProductionSeasonalityProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the ProductionSeasonality-created topic when a new ProductionSeasonality is created.
     *
     * @param dto The ProductionSeasonalityDTO containing the created ProductionSeasonality's information
     */
    public void created(ProductionSeasonalityDTO dto) {
        log.debug("Entry created(ProductionSeasonality={})", dto);
        kafkaTemplate.send(TOPIC_PRODUCTIONSEASONALITY_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the ProductionSeasonality-updated topic when a ProductionSeasonality is modified.
     *
     * @param dto The ProductionSeasonalityDTO containing the updated ProductionSeasonality's information
     */
    public void updated(ProductionSeasonalityDTO dto) {
        log.debug("Entry updated(ProductionSeasonality={})", dto);
        kafkaTemplate.send(TOPIC_PRODUCTIONSEASONALITY_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the ProductionSeasonality-deleted topic when a ProductionSeasonality is removed.
     *
     * @param dto The ProductionSeasonalityDTO containing the deleted ProductionSeasonality's information
     */
    public void deleted(ProductionSeasonalityDTO dto){
        log.debug("Entry deleted(ProductionSeasonality={})", dto);
        kafkaTemplate.send(TOPIC_PRODUCTIONSEASONALITY_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
