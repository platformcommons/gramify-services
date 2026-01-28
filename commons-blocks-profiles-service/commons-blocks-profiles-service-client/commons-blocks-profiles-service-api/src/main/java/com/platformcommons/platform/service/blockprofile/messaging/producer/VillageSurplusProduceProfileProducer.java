package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageSurplusProduceProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageSurplusProduceProfile-related events. This component is responsible for
 * publishing VillageSurplusProduceProfile events to designated Kafka topics when VillageSurplusProduceProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGESURPLUSPRODUCEPROFILE_CREATED} - For newly created VillageSurplusProduceProfiles</li>
 *     <li>{@link #TOPIC_VILLAGESURPLUSPRODUCEPROFILE_UPDATED} - For VillageSurplusProduceProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGESURPLUSPRODUCEPROFILE_DELETED} - For VillageSurplusProduceProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageSurplusProduceProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGESURPLUSPRODUCEPROFILE_CREATED = "commons-blocks-profiles-service.villagesurplusproduceprofile-created";
    public static final String TOPIC_VILLAGESURPLUSPRODUCEPROFILE_UPDATED = "commons-blocks-profiles-service.villagesurplusproduceprofile-updated";
    public static final String TOPIC_VILLAGESURPLUSPRODUCEPROFILE_DELETED = "commons-blocks-profiles-service.villagesurplusproduceprofile-deleted";

    private final KafkaTemplate<String,VillageSurplusProduceProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageSurplusProduceProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageSurplusProduceProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageSurplusProduceProfile-created topic when a new VillageSurplusProduceProfile is created.
     *
     * @param dto The VillageSurplusProduceProfileDTO containing the created VillageSurplusProduceProfile's information
     */
    public void created(VillageSurplusProduceProfileDTO dto) {
        log.debug("Entry created(VillageSurplusProduceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESURPLUSPRODUCEPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageSurplusProduceProfile-updated topic when a VillageSurplusProduceProfile is modified.
     *
     * @param dto The VillageSurplusProduceProfileDTO containing the updated VillageSurplusProduceProfile's information
     */
    public void updated(VillageSurplusProduceProfileDTO dto) {
        log.debug("Entry updated(VillageSurplusProduceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESURPLUSPRODUCEPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageSurplusProduceProfile-deleted topic when a VillageSurplusProduceProfile is removed.
     *
     * @param dto The VillageSurplusProduceProfileDTO containing the deleted VillageSurplusProduceProfile's information
     */
    public void deleted(VillageSurplusProduceProfileDTO dto){
        log.debug("Entry deleted(VillageSurplusProduceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESURPLUSPRODUCEPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
