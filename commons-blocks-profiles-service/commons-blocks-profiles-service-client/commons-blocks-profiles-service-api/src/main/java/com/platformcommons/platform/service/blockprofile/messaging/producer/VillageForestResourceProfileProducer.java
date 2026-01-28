package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageForestResourceProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageForestResourceProfile-related events. This component is responsible for
 * publishing VillageForestResourceProfile events to designated Kafka topics when VillageForestResourceProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEFORESTRESOURCEPROFILE_CREATED} - For newly created VillageForestResourceProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEFORESTRESOURCEPROFILE_UPDATED} - For VillageForestResourceProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEFORESTRESOURCEPROFILE_DELETED} - For VillageForestResourceProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageForestResourceProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEFORESTRESOURCEPROFILE_CREATED = "commons-blocks-profiles-service.villageforestresourceprofile-created";
    public static final String TOPIC_VILLAGEFORESTRESOURCEPROFILE_UPDATED = "commons-blocks-profiles-service.villageforestresourceprofile-updated";
    public static final String TOPIC_VILLAGEFORESTRESOURCEPROFILE_DELETED = "commons-blocks-profiles-service.villageforestresourceprofile-deleted";

    private final KafkaTemplate<String,VillageForestResourceProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageForestResourceProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageForestResourceProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageForestResourceProfile-created topic when a new VillageForestResourceProfile is created.
     *
     * @param dto The VillageForestResourceProfileDTO containing the created VillageForestResourceProfile's information
     */
    public void created(VillageForestResourceProfileDTO dto) {
        log.debug("Entry created(VillageForestResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFORESTRESOURCEPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageForestResourceProfile-updated topic when a VillageForestResourceProfile is modified.
     *
     * @param dto The VillageForestResourceProfileDTO containing the updated VillageForestResourceProfile's information
     */
    public void updated(VillageForestResourceProfileDTO dto) {
        log.debug("Entry updated(VillageForestResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFORESTRESOURCEPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageForestResourceProfile-deleted topic when a VillageForestResourceProfile is removed.
     *
     * @param dto The VillageForestResourceProfileDTO containing the deleted VillageForestResourceProfile's information
     */
    public void deleted(VillageForestResourceProfileDTO dto){
        log.debug("Entry deleted(VillageForestResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFORESTRESOURCEPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
