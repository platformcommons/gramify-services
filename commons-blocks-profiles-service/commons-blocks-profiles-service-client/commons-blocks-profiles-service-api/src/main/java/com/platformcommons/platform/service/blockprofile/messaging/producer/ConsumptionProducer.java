package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.ConsumptionDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for Consumption-related events. This component is responsible for
 * publishing Consumption events to designated Kafka topics when Consumptions are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_CONSUMPTION_CREATED} - For newly created Consumptions</li>
 *     <li>{@link #TOPIC_CONSUMPTION_UPDATED} - For Consumption updates</li>
 *     <li>{@link #TOPIC_CONSUMPTION_DELETED} - For Consumption deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class ConsumptionProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_CONSUMPTION_CREATED = "commons-blocks-profiles-service.consumption-created";
    public static final String TOPIC_CONSUMPTION_UPDATED = "commons-blocks-profiles-service.consumption-updated";
    public static final String TOPIC_CONSUMPTION_DELETED = "commons-blocks-profiles-service.consumption-deleted";

    private final KafkaTemplate<String,ConsumptionDTO> kafkaTemplate;

    /**
     * Constructs a new ConsumptionProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public ConsumptionProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the Consumption-created topic when a new Consumption is created.
     *
     * @param dto The ConsumptionDTO containing the created Consumption's information
     */
    public void created(ConsumptionDTO dto) {
        log.debug("Entry created(Consumption={})", dto);
        kafkaTemplate.send(TOPIC_CONSUMPTION_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the Consumption-updated topic when a Consumption is modified.
     *
     * @param dto The ConsumptionDTO containing the updated Consumption's information
     */
    public void updated(ConsumptionDTO dto) {
        log.debug("Entry updated(Consumption={})", dto);
        kafkaTemplate.send(TOPIC_CONSUMPTION_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the Consumption-deleted topic when a Consumption is removed.
     *
     * @param dto The ConsumptionDTO containing the deleted Consumption's information
     */
    public void deleted(ConsumptionDTO dto){
        log.debug("Entry deleted(Consumption={})", dto);
        kafkaTemplate.send(TOPIC_CONSUMPTION_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
