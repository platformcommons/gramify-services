package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for ProductionSeason-related events. This component is responsible for
 * publishing ProductionSeason events to designated Kafka topics when ProductionSeasons are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_PRODUCTIONSEASON_CREATED} - For newly created ProductionSeasons</li>
 *     <li>{@link #TOPIC_PRODUCTIONSEASON_UPDATED} - For ProductionSeason updates</li>
 *     <li>{@link #TOPIC_PRODUCTIONSEASON_DELETED} - For ProductionSeason deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class ProductionSeasonProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_PRODUCTIONSEASON_CREATED = "commons-blocks-profiles-service.productionseason-created";
    public static final String TOPIC_PRODUCTIONSEASON_UPDATED = "commons-blocks-profiles-service.productionseason-updated";
    public static final String TOPIC_PRODUCTIONSEASON_DELETED = "commons-blocks-profiles-service.productionseason-deleted";

    private final KafkaTemplate<String,ProductionSeasonDTO> kafkaTemplate;

    /**
     * Constructs a new ProductionSeasonProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public ProductionSeasonProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the ProductionSeason-created topic when a new ProductionSeason is created.
     *
     * @param dto The ProductionSeasonDTO containing the created ProductionSeason's information
     */
    public void created(ProductionSeasonDTO dto) {
        log.debug("Entry created(ProductionSeason={})", dto);
        kafkaTemplate.send(TOPIC_PRODUCTIONSEASON_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the ProductionSeason-updated topic when a ProductionSeason is modified.
     *
     * @param dto The ProductionSeasonDTO containing the updated ProductionSeason's information
     */
    public void updated(ProductionSeasonDTO dto) {
        log.debug("Entry updated(ProductionSeason={})", dto);
        kafkaTemplate.send(TOPIC_PRODUCTIONSEASON_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the ProductionSeason-deleted topic when a ProductionSeason is removed.
     *
     * @param dto The ProductionSeasonDTO containing the deleted ProductionSeason's information
     */
    public void deleted(ProductionSeasonDTO dto){
        log.debug("Entry deleted(ProductionSeason={})", dto);
        kafkaTemplate.send(TOPIC_PRODUCTIONSEASON_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
