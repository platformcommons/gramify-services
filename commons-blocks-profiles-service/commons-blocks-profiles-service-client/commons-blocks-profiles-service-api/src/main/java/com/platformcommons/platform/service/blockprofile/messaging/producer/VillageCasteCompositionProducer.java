package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageCasteCompositionDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageCasteComposition-related events. This component is responsible for
 * publishing VillageCasteComposition events to designated Kafka topics when VillageCasteCompositions are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECASTECOMPOSITION_CREATED} - For newly created VillageCasteCompositions</li>
 *     <li>{@link #TOPIC_VILLAGECASTECOMPOSITION_UPDATED} - For VillageCasteComposition updates</li>
 *     <li>{@link #TOPIC_VILLAGECASTECOMPOSITION_DELETED} - For VillageCasteComposition deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageCasteCompositionProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECASTECOMPOSITION_CREATED = "commons-blocks-profiles-service.villagecastecomposition-created";
    public static final String TOPIC_VILLAGECASTECOMPOSITION_UPDATED = "commons-blocks-profiles-service.villagecastecomposition-updated";
    public static final String TOPIC_VILLAGECASTECOMPOSITION_DELETED = "commons-blocks-profiles-service.villagecastecomposition-deleted";

    private final KafkaTemplate<String,VillageCasteCompositionDTO> kafkaTemplate;

    /**
     * Constructs a new VillageCasteCompositionProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageCasteCompositionProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageCasteComposition-created topic when a new VillageCasteComposition is created.
     *
     * @param dto The VillageCasteCompositionDTO containing the created VillageCasteComposition's information
     */
    public void created(VillageCasteCompositionDTO dto) {
        log.debug("Entry created(VillageCasteComposition={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECASTECOMPOSITION_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageCasteComposition-updated topic when a VillageCasteComposition is modified.
     *
     * @param dto The VillageCasteCompositionDTO containing the updated VillageCasteComposition's information
     */
    public void updated(VillageCasteCompositionDTO dto) {
        log.debug("Entry updated(VillageCasteComposition={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECASTECOMPOSITION_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageCasteComposition-deleted topic when a VillageCasteComposition is removed.
     *
     * @param dto The VillageCasteCompositionDTO containing the deleted VillageCasteComposition's information
     */
    public void deleted(VillageCasteCompositionDTO dto){
        log.debug("Entry deleted(VillageCasteComposition={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECASTECOMPOSITION_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
