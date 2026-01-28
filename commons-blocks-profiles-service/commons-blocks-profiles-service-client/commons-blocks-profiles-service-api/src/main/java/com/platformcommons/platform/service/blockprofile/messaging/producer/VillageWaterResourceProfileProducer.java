package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageWaterResourceProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageWaterResourceProfile-related events. This component is responsible for
 * publishing VillageWaterResourceProfile events to designated Kafka topics when VillageWaterResourceProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEWATERRESOURCEPROFILE_CREATED} - For newly created VillageWaterResourceProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEWATERRESOURCEPROFILE_UPDATED} - For VillageWaterResourceProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEWATERRESOURCEPROFILE_DELETED} - For VillageWaterResourceProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageWaterResourceProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEWATERRESOURCEPROFILE_CREATED = "commons-blocks-profiles-service.villagewaterresourceprofile-created";
    public static final String TOPIC_VILLAGEWATERRESOURCEPROFILE_UPDATED = "commons-blocks-profiles-service.villagewaterresourceprofile-updated";
    public static final String TOPIC_VILLAGEWATERRESOURCEPROFILE_DELETED = "commons-blocks-profiles-service.villagewaterresourceprofile-deleted";

    private final KafkaTemplate<String,VillageWaterResourceProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageWaterResourceProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageWaterResourceProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageWaterResourceProfile-created topic when a new VillageWaterResourceProfile is created.
     *
     * @param dto The VillageWaterResourceProfileDTO containing the created VillageWaterResourceProfile's information
     */
    public void created(VillageWaterResourceProfileDTO dto) {
        log.debug("Entry created(VillageWaterResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERRESOURCEPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageWaterResourceProfile-updated topic when a VillageWaterResourceProfile is modified.
     *
     * @param dto The VillageWaterResourceProfileDTO containing the updated VillageWaterResourceProfile's information
     */
    public void updated(VillageWaterResourceProfileDTO dto) {
        log.debug("Entry updated(VillageWaterResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERRESOURCEPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageWaterResourceProfile-deleted topic when a VillageWaterResourceProfile is removed.
     *
     * @param dto The VillageWaterResourceProfileDTO containing the deleted VillageWaterResourceProfile's information
     */
    public void deleted(VillageWaterResourceProfileDTO dto){
        log.debug("Entry deleted(VillageWaterResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERRESOURCEPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
