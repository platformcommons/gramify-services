package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.FertilizersInDemandDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for FertilizersInDemand-related events. This component is responsible for
 * publishing FertilizersInDemand events to designated Kafka topics when FertilizersInDemands are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_FERTILIZERSINDEMAND_CREATED} - For newly created FertilizersInDemands</li>
 *     <li>{@link #TOPIC_FERTILIZERSINDEMAND_UPDATED} - For FertilizersInDemand updates</li>
 *     <li>{@link #TOPIC_FERTILIZERSINDEMAND_DELETED} - For FertilizersInDemand deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class FertilizersInDemandProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_FERTILIZERSINDEMAND_CREATED = "commons-blocks-profiles-service.fertilizersindemand-created";
    public static final String TOPIC_FERTILIZERSINDEMAND_UPDATED = "commons-blocks-profiles-service.fertilizersindemand-updated";
    public static final String TOPIC_FERTILIZERSINDEMAND_DELETED = "commons-blocks-profiles-service.fertilizersindemand-deleted";

    private final KafkaTemplate<String,FertilizersInDemandDTO> kafkaTemplate;

    /**
     * Constructs a new FertilizersInDemandProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public FertilizersInDemandProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the FertilizersInDemand-created topic when a new FertilizersInDemand is created.
     *
     * @param dto The FertilizersInDemandDTO containing the created FertilizersInDemand's information
     */
    public void created(FertilizersInDemandDTO dto) {
        log.debug("Entry created(FertilizersInDemand={})", dto);
        kafkaTemplate.send(TOPIC_FERTILIZERSINDEMAND_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the FertilizersInDemand-updated topic when a FertilizersInDemand is modified.
     *
     * @param dto The FertilizersInDemandDTO containing the updated FertilizersInDemand's information
     */
    public void updated(FertilizersInDemandDTO dto) {
        log.debug("Entry updated(FertilizersInDemand={})", dto);
        kafkaTemplate.send(TOPIC_FERTILIZERSINDEMAND_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the FertilizersInDemand-deleted topic when a FertilizersInDemand is removed.
     *
     * @param dto The FertilizersInDemandDTO containing the deleted FertilizersInDemand's information
     */
    public void deleted(FertilizersInDemandDTO dto){
        log.debug("Entry deleted(FertilizersInDemand={})", dto);
        kafkaTemplate.send(TOPIC_FERTILIZERSINDEMAND_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
