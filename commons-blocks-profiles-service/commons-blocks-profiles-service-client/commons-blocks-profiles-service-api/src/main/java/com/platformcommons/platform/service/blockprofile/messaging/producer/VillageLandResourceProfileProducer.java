package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageLandResourceProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageLandResourceProfile-related events. This component is responsible for
 * publishing VillageLandResourceProfile events to designated Kafka topics when VillageLandResourceProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGELANDRESOURCEPROFILE_CREATED} - For newly created VillageLandResourceProfiles</li>
 *     <li>{@link #TOPIC_VILLAGELANDRESOURCEPROFILE_UPDATED} - For VillageLandResourceProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGELANDRESOURCEPROFILE_DELETED} - For VillageLandResourceProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageLandResourceProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGELANDRESOURCEPROFILE_CREATED = "commons-blocks-profiles-service.villagelandresourceprofile-created";
    public static final String TOPIC_VILLAGELANDRESOURCEPROFILE_UPDATED = "commons-blocks-profiles-service.villagelandresourceprofile-updated";
    public static final String TOPIC_VILLAGELANDRESOURCEPROFILE_DELETED = "commons-blocks-profiles-service.villagelandresourceprofile-deleted";

    private final KafkaTemplate<String,VillageLandResourceProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageLandResourceProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageLandResourceProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageLandResourceProfile-created topic when a new VillageLandResourceProfile is created.
     *
     * @param dto The VillageLandResourceProfileDTO containing the created VillageLandResourceProfile's information
     */
    public void created(VillageLandResourceProfileDTO dto) {
        log.debug("Entry created(VillageLandResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGELANDRESOURCEPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageLandResourceProfile-updated topic when a VillageLandResourceProfile is modified.
     *
     * @param dto The VillageLandResourceProfileDTO containing the updated VillageLandResourceProfile's information
     */
    public void updated(VillageLandResourceProfileDTO dto) {
        log.debug("Entry updated(VillageLandResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGELANDRESOURCEPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageLandResourceProfile-deleted topic when a VillageLandResourceProfile is removed.
     *
     * @param dto The VillageLandResourceProfileDTO containing the deleted VillageLandResourceProfile's information
     */
    public void deleted(VillageLandResourceProfileDTO dto){
        log.debug("Entry deleted(VillageLandResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGELANDRESOURCEPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
