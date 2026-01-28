package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.SeedsInDemandDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for SeedsInDemand-related events. This component is responsible for
 * publishing SeedsInDemand events to designated Kafka topics when SeedsInDemands are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_SEEDSINDEMAND_CREATED} - For newly created SeedsInDemands</li>
 *     <li>{@link #TOPIC_SEEDSINDEMAND_UPDATED} - For SeedsInDemand updates</li>
 *     <li>{@link #TOPIC_SEEDSINDEMAND_DELETED} - For SeedsInDemand deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class SeedsInDemandProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_SEEDSINDEMAND_CREATED = "commons-blocks-profiles-service.seedsindemand-created";
    public static final String TOPIC_SEEDSINDEMAND_UPDATED = "commons-blocks-profiles-service.seedsindemand-updated";
    public static final String TOPIC_SEEDSINDEMAND_DELETED = "commons-blocks-profiles-service.seedsindemand-deleted";

    private final KafkaTemplate<String,SeedsInDemandDTO> kafkaTemplate;

    /**
     * Constructs a new SeedsInDemandProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public SeedsInDemandProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the SeedsInDemand-created topic when a new SeedsInDemand is created.
     *
     * @param dto The SeedsInDemandDTO containing the created SeedsInDemand's information
     */
    public void created(SeedsInDemandDTO dto) {
        log.debug("Entry created(SeedsInDemand={})", dto);
        kafkaTemplate.send(TOPIC_SEEDSINDEMAND_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the SeedsInDemand-updated topic when a SeedsInDemand is modified.
     *
     * @param dto The SeedsInDemandDTO containing the updated SeedsInDemand's information
     */
    public void updated(SeedsInDemandDTO dto) {
        log.debug("Entry updated(SeedsInDemand={})", dto);
        kafkaTemplate.send(TOPIC_SEEDSINDEMAND_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the SeedsInDemand-deleted topic when a SeedsInDemand is removed.
     *
     * @param dto The SeedsInDemandDTO containing the deleted SeedsInDemand's information
     */
    public void deleted(SeedsInDemandDTO dto){
        log.debug("Entry deleted(SeedsInDemand={})", dto);
        kafkaTemplate.send(TOPIC_SEEDSINDEMAND_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
