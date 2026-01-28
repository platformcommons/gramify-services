package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.PesticidesInDemandDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for PesticidesInDemand-related events. This component is responsible for
 * publishing PesticidesInDemand events to designated Kafka topics when PesticidesInDemands are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_PESTICIDESINDEMAND_CREATED} - For newly created PesticidesInDemands</li>
 *     <li>{@link #TOPIC_PESTICIDESINDEMAND_UPDATED} - For PesticidesInDemand updates</li>
 *     <li>{@link #TOPIC_PESTICIDESINDEMAND_DELETED} - For PesticidesInDemand deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class PesticidesInDemandProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_PESTICIDESINDEMAND_CREATED = "commons-blocks-profiles-service.pesticidesindemand-created";
    public static final String TOPIC_PESTICIDESINDEMAND_UPDATED = "commons-blocks-profiles-service.pesticidesindemand-updated";
    public static final String TOPIC_PESTICIDESINDEMAND_DELETED = "commons-blocks-profiles-service.pesticidesindemand-deleted";

    private final KafkaTemplate<String,PesticidesInDemandDTO> kafkaTemplate;

    /**
     * Constructs a new PesticidesInDemandProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public PesticidesInDemandProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the PesticidesInDemand-created topic when a new PesticidesInDemand is created.
     *
     * @param dto The PesticidesInDemandDTO containing the created PesticidesInDemand's information
     */
    public void created(PesticidesInDemandDTO dto) {
        log.debug("Entry created(PesticidesInDemand={})", dto);
        kafkaTemplate.send(TOPIC_PESTICIDESINDEMAND_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the PesticidesInDemand-updated topic when a PesticidesInDemand is modified.
     *
     * @param dto The PesticidesInDemandDTO containing the updated PesticidesInDemand's information
     */
    public void updated(PesticidesInDemandDTO dto) {
        log.debug("Entry updated(PesticidesInDemand={})", dto);
        kafkaTemplate.send(TOPIC_PESTICIDESINDEMAND_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the PesticidesInDemand-deleted topic when a PesticidesInDemand is removed.
     *
     * @param dto The PesticidesInDemandDTO containing the deleted PesticidesInDemand's information
     */
    public void deleted(PesticidesInDemandDTO dto){
        log.debug("Entry deleted(PesticidesInDemand={})", dto);
        kafkaTemplate.send(TOPIC_PESTICIDESINDEMAND_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
