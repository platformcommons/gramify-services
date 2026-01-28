package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.NicheProductsAvailabilityDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for NicheProductsAvailability-related events. This component is responsible for
 * publishing NicheProductsAvailability events to designated Kafka topics when NicheProductsAvailabilitys are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_NICHEPRODUCTSAVAILABILITY_CREATED} - For newly created NicheProductsAvailabilitys</li>
 *     <li>{@link #TOPIC_NICHEPRODUCTSAVAILABILITY_UPDATED} - For NicheProductsAvailability updates</li>
 *     <li>{@link #TOPIC_NICHEPRODUCTSAVAILABILITY_DELETED} - For NicheProductsAvailability deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class NicheProductsAvailabilityProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_NICHEPRODUCTSAVAILABILITY_CREATED = "commons-blocks-profiles-service.nicheproductsavailability-created";
    public static final String TOPIC_NICHEPRODUCTSAVAILABILITY_UPDATED = "commons-blocks-profiles-service.nicheproductsavailability-updated";
    public static final String TOPIC_NICHEPRODUCTSAVAILABILITY_DELETED = "commons-blocks-profiles-service.nicheproductsavailability-deleted";

    private final KafkaTemplate<String,NicheProductsAvailabilityDTO> kafkaTemplate;

    /**
     * Constructs a new NicheProductsAvailabilityProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public NicheProductsAvailabilityProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the NicheProductsAvailability-created topic when a new NicheProductsAvailability is created.
     *
     * @param dto The NicheProductsAvailabilityDTO containing the created NicheProductsAvailability's information
     */
    public void created(NicheProductsAvailabilityDTO dto) {
        log.debug("Entry created(NicheProductsAvailability={})", dto);
        kafkaTemplate.send(TOPIC_NICHEPRODUCTSAVAILABILITY_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the NicheProductsAvailability-updated topic when a NicheProductsAvailability is modified.
     *
     * @param dto The NicheProductsAvailabilityDTO containing the updated NicheProductsAvailability's information
     */
    public void updated(NicheProductsAvailabilityDTO dto) {
        log.debug("Entry updated(NicheProductsAvailability={})", dto);
        kafkaTemplate.send(TOPIC_NICHEPRODUCTSAVAILABILITY_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the NicheProductsAvailability-deleted topic when a NicheProductsAvailability is removed.
     *
     * @param dto The NicheProductsAvailabilityDTO containing the deleted NicheProductsAvailability's information
     */
    public void deleted(NicheProductsAvailabilityDTO dto){
        log.debug("Entry deleted(NicheProductsAvailability={})", dto);
        kafkaTemplate.send(TOPIC_NICHEPRODUCTSAVAILABILITY_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
