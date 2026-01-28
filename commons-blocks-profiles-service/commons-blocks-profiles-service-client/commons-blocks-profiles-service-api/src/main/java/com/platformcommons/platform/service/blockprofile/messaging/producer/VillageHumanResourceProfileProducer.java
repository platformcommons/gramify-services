package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageHumanResourceProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageHumanResourceProfile-related events. This component is responsible for
 * publishing VillageHumanResourceProfile events to designated Kafka topics when VillageHumanResourceProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEHUMANRESOURCEPROFILE_CREATED} - For newly created VillageHumanResourceProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEHUMANRESOURCEPROFILE_UPDATED} - For VillageHumanResourceProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEHUMANRESOURCEPROFILE_DELETED} - For VillageHumanResourceProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageHumanResourceProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEHUMANRESOURCEPROFILE_CREATED = "commons-blocks-profiles-service.villagehumanresourceprofile-created";
    public static final String TOPIC_VILLAGEHUMANRESOURCEPROFILE_UPDATED = "commons-blocks-profiles-service.villagehumanresourceprofile-updated";
    public static final String TOPIC_VILLAGEHUMANRESOURCEPROFILE_DELETED = "commons-blocks-profiles-service.villagehumanresourceprofile-deleted";

    private final KafkaTemplate<String,VillageHumanResourceProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageHumanResourceProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageHumanResourceProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageHumanResourceProfile-created topic when a new VillageHumanResourceProfile is created.
     *
     * @param dto The VillageHumanResourceProfileDTO containing the created VillageHumanResourceProfile's information
     */
    public void created(VillageHumanResourceProfileDTO dto) {
        log.debug("Entry created(VillageHumanResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHUMANRESOURCEPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageHumanResourceProfile-updated topic when a VillageHumanResourceProfile is modified.
     *
     * @param dto The VillageHumanResourceProfileDTO containing the updated VillageHumanResourceProfile's information
     */
    public void updated(VillageHumanResourceProfileDTO dto) {
        log.debug("Entry updated(VillageHumanResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHUMANRESOURCEPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageHumanResourceProfile-deleted topic when a VillageHumanResourceProfile is removed.
     *
     * @param dto The VillageHumanResourceProfileDTO containing the deleted VillageHumanResourceProfile's information
     */
    public void deleted(VillageHumanResourceProfileDTO dto){
        log.debug("Entry deleted(VillageHumanResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHUMANRESOURCEPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
