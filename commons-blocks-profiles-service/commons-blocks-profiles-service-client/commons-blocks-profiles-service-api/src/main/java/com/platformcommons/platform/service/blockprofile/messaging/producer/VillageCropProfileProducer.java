package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageCropProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageCropProfile-related events. This component is responsible for
 * publishing VillageCropProfile events to designated Kafka topics when VillageCropProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECROPPROFILE_CREATED} - For newly created VillageCropProfiles</li>
 *     <li>{@link #TOPIC_VILLAGECROPPROFILE_UPDATED} - For VillageCropProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGECROPPROFILE_DELETED} - For VillageCropProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageCropProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECROPPROFILE_CREATED = "commons-blocks-profiles-service.villagecropprofile-created";
    public static final String TOPIC_VILLAGECROPPROFILE_UPDATED = "commons-blocks-profiles-service.villagecropprofile-updated";
    public static final String TOPIC_VILLAGECROPPROFILE_DELETED = "commons-blocks-profiles-service.villagecropprofile-deleted";

    private final KafkaTemplate<String,VillageCropProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageCropProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageCropProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageCropProfile-created topic when a new VillageCropProfile is created.
     *
     * @param dto The VillageCropProfileDTO containing the created VillageCropProfile's information
     */
    public void created(VillageCropProfileDTO dto) {
        log.debug("Entry created(VillageCropProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECROPPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageCropProfile-updated topic when a VillageCropProfile is modified.
     *
     * @param dto The VillageCropProfileDTO containing the updated VillageCropProfile's information
     */
    public void updated(VillageCropProfileDTO dto) {
        log.debug("Entry updated(VillageCropProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECROPPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageCropProfile-deleted topic when a VillageCropProfile is removed.
     *
     * @param dto The VillageCropProfileDTO containing the deleted VillageCropProfile's information
     */
    public void deleted(VillageCropProfileDTO dto){
        log.debug("Entry deleted(VillageCropProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECROPPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
