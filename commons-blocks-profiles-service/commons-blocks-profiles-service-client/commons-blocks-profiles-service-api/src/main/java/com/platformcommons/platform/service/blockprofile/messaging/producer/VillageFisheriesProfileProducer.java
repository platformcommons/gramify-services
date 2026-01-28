package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageFisheriesProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageFisheriesProfile-related events. This component is responsible for
 * publishing VillageFisheriesProfile events to designated Kafka topics when VillageFisheriesProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEFISHERIESPROFILE_CREATED} - For newly created VillageFisheriesProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEFISHERIESPROFILE_UPDATED} - For VillageFisheriesProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEFISHERIESPROFILE_DELETED} - For VillageFisheriesProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageFisheriesProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEFISHERIESPROFILE_CREATED = "commons-blocks-profiles-service.villagefisheriesprofile-created";
    public static final String TOPIC_VILLAGEFISHERIESPROFILE_UPDATED = "commons-blocks-profiles-service.villagefisheriesprofile-updated";
    public static final String TOPIC_VILLAGEFISHERIESPROFILE_DELETED = "commons-blocks-profiles-service.villagefisheriesprofile-deleted";

    private final KafkaTemplate<String,VillageFisheriesProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageFisheriesProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageFisheriesProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageFisheriesProfile-created topic when a new VillageFisheriesProfile is created.
     *
     * @param dto The VillageFisheriesProfileDTO containing the created VillageFisheriesProfile's information
     */
    public void created(VillageFisheriesProfileDTO dto) {
        log.debug("Entry created(VillageFisheriesProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFISHERIESPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageFisheriesProfile-updated topic when a VillageFisheriesProfile is modified.
     *
     * @param dto The VillageFisheriesProfileDTO containing the updated VillageFisheriesProfile's information
     */
    public void updated(VillageFisheriesProfileDTO dto) {
        log.debug("Entry updated(VillageFisheriesProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFISHERIESPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageFisheriesProfile-deleted topic when a VillageFisheriesProfile is removed.
     *
     * @param dto The VillageFisheriesProfileDTO containing the deleted VillageFisheriesProfile's information
     */
    public void deleted(VillageFisheriesProfileDTO dto){
        log.debug("Entry deleted(VillageFisheriesProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFISHERIESPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
