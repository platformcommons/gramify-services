package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageRoadConnectivityProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageRoadConnectivityProfile-related events. This component is responsible for
 * publishing VillageRoadConnectivityProfile events to designated Kafka topics when VillageRoadConnectivityProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEROADCONNECTIVITYPROFILE_CREATED} - For newly created VillageRoadConnectivityProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEROADCONNECTIVITYPROFILE_UPDATED} - For VillageRoadConnectivityProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEROADCONNECTIVITYPROFILE_DELETED} - For VillageRoadConnectivityProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageRoadConnectivityProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEROADCONNECTIVITYPROFILE_CREATED = "commons-blocks-profiles-service.villageroadconnectivityprofile-created";
    public static final String TOPIC_VILLAGEROADCONNECTIVITYPROFILE_UPDATED = "commons-blocks-profiles-service.villageroadconnectivityprofile-updated";
    public static final String TOPIC_VILLAGEROADCONNECTIVITYPROFILE_DELETED = "commons-blocks-profiles-service.villageroadconnectivityprofile-deleted";

    private final KafkaTemplate<String,VillageRoadConnectivityProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageRoadConnectivityProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageRoadConnectivityProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageRoadConnectivityProfile-created topic when a new VillageRoadConnectivityProfile is created.
     *
     * @param dto The VillageRoadConnectivityProfileDTO containing the created VillageRoadConnectivityProfile's information
     */
    public void created(VillageRoadConnectivityProfileDTO dto) {
        log.debug("Entry created(VillageRoadConnectivityProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEROADCONNECTIVITYPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageRoadConnectivityProfile-updated topic when a VillageRoadConnectivityProfile is modified.
     *
     * @param dto The VillageRoadConnectivityProfileDTO containing the updated VillageRoadConnectivityProfile's information
     */
    public void updated(VillageRoadConnectivityProfileDTO dto) {
        log.debug("Entry updated(VillageRoadConnectivityProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEROADCONNECTIVITYPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageRoadConnectivityProfile-deleted topic when a VillageRoadConnectivityProfile is removed.
     *
     * @param dto The VillageRoadConnectivityProfileDTO containing the deleted VillageRoadConnectivityProfile's information
     */
    public void deleted(VillageRoadConnectivityProfileDTO dto){
        log.debug("Entry deleted(VillageRoadConnectivityProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEROADCONNECTIVITYPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
