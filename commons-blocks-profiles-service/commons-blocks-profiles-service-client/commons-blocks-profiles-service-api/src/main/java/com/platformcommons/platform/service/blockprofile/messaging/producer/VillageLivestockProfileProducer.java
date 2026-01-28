package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageLivestockProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageLivestockProfile-related events. This component is responsible for
 * publishing VillageLivestockProfile events to designated Kafka topics when VillageLivestockProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGELIVESTOCKPROFILE_CREATED} - For newly created VillageLivestockProfiles</li>
 *     <li>{@link #TOPIC_VILLAGELIVESTOCKPROFILE_UPDATED} - For VillageLivestockProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGELIVESTOCKPROFILE_DELETED} - For VillageLivestockProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageLivestockProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGELIVESTOCKPROFILE_CREATED = "commons-blocks-profiles-service.villagelivestockprofile-created";
    public static final String TOPIC_VILLAGELIVESTOCKPROFILE_UPDATED = "commons-blocks-profiles-service.villagelivestockprofile-updated";
    public static final String TOPIC_VILLAGELIVESTOCKPROFILE_DELETED = "commons-blocks-profiles-service.villagelivestockprofile-deleted";

    private final KafkaTemplate<String,VillageLivestockProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageLivestockProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageLivestockProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageLivestockProfile-created topic when a new VillageLivestockProfile is created.
     *
     * @param dto The VillageLivestockProfileDTO containing the created VillageLivestockProfile's information
     */
    public void created(VillageLivestockProfileDTO dto) {
        log.debug("Entry created(VillageLivestockProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGELIVESTOCKPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageLivestockProfile-updated topic when a VillageLivestockProfile is modified.
     *
     * @param dto The VillageLivestockProfileDTO containing the updated VillageLivestockProfile's information
     */
    public void updated(VillageLivestockProfileDTO dto) {
        log.debug("Entry updated(VillageLivestockProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGELIVESTOCKPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageLivestockProfile-deleted topic when a VillageLivestockProfile is removed.
     *
     * @param dto The VillageLivestockProfileDTO containing the deleted VillageLivestockProfile's information
     */
    public void deleted(VillageLivestockProfileDTO dto){
        log.debug("Entry deleted(VillageLivestockProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGELIVESTOCKPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
