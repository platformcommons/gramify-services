package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageWaterSanitationProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageWaterSanitationProfile-related events. This component is responsible for
 * publishing VillageWaterSanitationProfile events to designated Kafka topics when VillageWaterSanitationProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEWATERSANITATIONPROFILE_CREATED} - For newly created VillageWaterSanitationProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEWATERSANITATIONPROFILE_UPDATED} - For VillageWaterSanitationProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEWATERSANITATIONPROFILE_DELETED} - For VillageWaterSanitationProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageWaterSanitationProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEWATERSANITATIONPROFILE_CREATED = "commons-blocks-profiles-service.villagewatersanitationprofile-created";
    public static final String TOPIC_VILLAGEWATERSANITATIONPROFILE_UPDATED = "commons-blocks-profiles-service.villagewatersanitationprofile-updated";
    public static final String TOPIC_VILLAGEWATERSANITATIONPROFILE_DELETED = "commons-blocks-profiles-service.villagewatersanitationprofile-deleted";

    private final KafkaTemplate<String,VillageWaterSanitationProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageWaterSanitationProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageWaterSanitationProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageWaterSanitationProfile-created topic when a new VillageWaterSanitationProfile is created.
     *
     * @param dto The VillageWaterSanitationProfileDTO containing the created VillageWaterSanitationProfile's information
     */
    public void created(VillageWaterSanitationProfileDTO dto) {
        log.debug("Entry created(VillageWaterSanitationProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERSANITATIONPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageWaterSanitationProfile-updated topic when a VillageWaterSanitationProfile is modified.
     *
     * @param dto The VillageWaterSanitationProfileDTO containing the updated VillageWaterSanitationProfile's information
     */
    public void updated(VillageWaterSanitationProfileDTO dto) {
        log.debug("Entry updated(VillageWaterSanitationProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERSANITATIONPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageWaterSanitationProfile-deleted topic when a VillageWaterSanitationProfile is removed.
     *
     * @param dto The VillageWaterSanitationProfileDTO containing the deleted VillageWaterSanitationProfile's information
     */
    public void deleted(VillageWaterSanitationProfileDTO dto){
        log.debug("Entry deleted(VillageWaterSanitationProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEWATERSANITATIONPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
