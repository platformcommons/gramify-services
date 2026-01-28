package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageNicheProductProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageNicheProductProfile-related events. This component is responsible for
 * publishing VillageNicheProductProfile events to designated Kafka topics when VillageNicheProductProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGENICHEPRODUCTPROFILE_CREATED} - For newly created VillageNicheProductProfiles</li>
 *     <li>{@link #TOPIC_VILLAGENICHEPRODUCTPROFILE_UPDATED} - For VillageNicheProductProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGENICHEPRODUCTPROFILE_DELETED} - For VillageNicheProductProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageNicheProductProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGENICHEPRODUCTPROFILE_CREATED = "commons-blocks-profiles-service.villagenicheproductprofile-created";
    public static final String TOPIC_VILLAGENICHEPRODUCTPROFILE_UPDATED = "commons-blocks-profiles-service.villagenicheproductprofile-updated";
    public static final String TOPIC_VILLAGENICHEPRODUCTPROFILE_DELETED = "commons-blocks-profiles-service.villagenicheproductprofile-deleted";

    private final KafkaTemplate<String,VillageNicheProductProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageNicheProductProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageNicheProductProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageNicheProductProfile-created topic when a new VillageNicheProductProfile is created.
     *
     * @param dto The VillageNicheProductProfileDTO containing the created VillageNicheProductProfile's information
     */
    public void created(VillageNicheProductProfileDTO dto) {
        log.debug("Entry created(VillageNicheProductProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGENICHEPRODUCTPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageNicheProductProfile-updated topic when a VillageNicheProductProfile is modified.
     *
     * @param dto The VillageNicheProductProfileDTO containing the updated VillageNicheProductProfile's information
     */
    public void updated(VillageNicheProductProfileDTO dto) {
        log.debug("Entry updated(VillageNicheProductProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGENICHEPRODUCTPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageNicheProductProfile-deleted topic when a VillageNicheProductProfile is removed.
     *
     * @param dto The VillageNicheProductProfileDTO containing the deleted VillageNicheProductProfile's information
     */
    public void deleted(VillageNicheProductProfileDTO dto){
        log.debug("Entry deleted(VillageNicheProductProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGENICHEPRODUCTPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
