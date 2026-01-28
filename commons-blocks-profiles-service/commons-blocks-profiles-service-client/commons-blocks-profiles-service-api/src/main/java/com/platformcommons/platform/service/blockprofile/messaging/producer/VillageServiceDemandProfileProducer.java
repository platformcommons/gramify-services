package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageServiceDemandProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageServiceDemandProfile-related events. This component is responsible for
 * publishing VillageServiceDemandProfile events to designated Kafka topics when VillageServiceDemandProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGESERVICEDEMANDPROFILE_CREATED} - For newly created VillageServiceDemandProfiles</li>
 *     <li>{@link #TOPIC_VILLAGESERVICEDEMANDPROFILE_UPDATED} - For VillageServiceDemandProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGESERVICEDEMANDPROFILE_DELETED} - For VillageServiceDemandProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageServiceDemandProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGESERVICEDEMANDPROFILE_CREATED = "commons-blocks-profiles-service.villageservicedemandprofile-created";
    public static final String TOPIC_VILLAGESERVICEDEMANDPROFILE_UPDATED = "commons-blocks-profiles-service.villageservicedemandprofile-updated";
    public static final String TOPIC_VILLAGESERVICEDEMANDPROFILE_DELETED = "commons-blocks-profiles-service.villageservicedemandprofile-deleted";

    private final KafkaTemplate<String,VillageServiceDemandProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageServiceDemandProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageServiceDemandProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageServiceDemandProfile-created topic when a new VillageServiceDemandProfile is created.
     *
     * @param dto The VillageServiceDemandProfileDTO containing the created VillageServiceDemandProfile's information
     */
    public void created(VillageServiceDemandProfileDTO dto) {
        log.debug("Entry created(VillageServiceDemandProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESERVICEDEMANDPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageServiceDemandProfile-updated topic when a VillageServiceDemandProfile is modified.
     *
     * @param dto The VillageServiceDemandProfileDTO containing the updated VillageServiceDemandProfile's information
     */
    public void updated(VillageServiceDemandProfileDTO dto) {
        log.debug("Entry updated(VillageServiceDemandProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESERVICEDEMANDPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageServiceDemandProfile-deleted topic when a VillageServiceDemandProfile is removed.
     *
     * @param dto The VillageServiceDemandProfileDTO containing the deleted VillageServiceDemandProfile's information
     */
    public void deleted(VillageServiceDemandProfileDTO dto){
        log.debug("Entry deleted(VillageServiceDemandProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESERVICEDEMANDPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
