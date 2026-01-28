package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.StapleFoodsConsumedDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for StapleFoodsConsumed-related events. This component is responsible for
 * publishing StapleFoodsConsumed events to designated Kafka topics when StapleFoodsConsumeds are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_STAPLEFOODSCONSUMED_CREATED} - For newly created StapleFoodsConsumeds</li>
 *     <li>{@link #TOPIC_STAPLEFOODSCONSUMED_UPDATED} - For StapleFoodsConsumed updates</li>
 *     <li>{@link #TOPIC_STAPLEFOODSCONSUMED_DELETED} - For StapleFoodsConsumed deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class StapleFoodsConsumedProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_STAPLEFOODSCONSUMED_CREATED = "commons-blocks-profiles-service.staplefoodsconsumed-created";
    public static final String TOPIC_STAPLEFOODSCONSUMED_UPDATED = "commons-blocks-profiles-service.staplefoodsconsumed-updated";
    public static final String TOPIC_STAPLEFOODSCONSUMED_DELETED = "commons-blocks-profiles-service.staplefoodsconsumed-deleted";

    private final KafkaTemplate<String,StapleFoodsConsumedDTO> kafkaTemplate;

    /**
     * Constructs a new StapleFoodsConsumedProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public StapleFoodsConsumedProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the StapleFoodsConsumed-created topic when a new StapleFoodsConsumed is created.
     *
     * @param dto The StapleFoodsConsumedDTO containing the created StapleFoodsConsumed's information
     */
    public void created(StapleFoodsConsumedDTO dto) {
        log.debug("Entry created(StapleFoodsConsumed={})", dto);
        kafkaTemplate.send(TOPIC_STAPLEFOODSCONSUMED_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the StapleFoodsConsumed-updated topic when a StapleFoodsConsumed is modified.
     *
     * @param dto The StapleFoodsConsumedDTO containing the updated StapleFoodsConsumed's information
     */
    public void updated(StapleFoodsConsumedDTO dto) {
        log.debug("Entry updated(StapleFoodsConsumed={})", dto);
        kafkaTemplate.send(TOPIC_STAPLEFOODSCONSUMED_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the StapleFoodsConsumed-deleted topic when a StapleFoodsConsumed is removed.
     *
     * @param dto The StapleFoodsConsumedDTO containing the deleted StapleFoodsConsumed's information
     */
    public void deleted(StapleFoodsConsumedDTO dto){
        log.debug("Entry deleted(StapleFoodsConsumed={})", dto);
        kafkaTemplate.send(TOPIC_STAPLEFOODSCONSUMED_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
