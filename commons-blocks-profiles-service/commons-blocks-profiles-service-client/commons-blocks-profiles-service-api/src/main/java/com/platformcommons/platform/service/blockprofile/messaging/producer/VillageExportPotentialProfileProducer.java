package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageExportPotentialProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageExportPotentialProfile-related events. This component is responsible for
 * publishing VillageExportPotentialProfile events to designated Kafka topics when VillageExportPotentialProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_CREATED} - For newly created VillageExportPotentialProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_UPDATED} - For VillageExportPotentialProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_DELETED} - For VillageExportPotentialProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageExportPotentialProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_CREATED = "commons-blocks-profiles-service.villageexportpotentialprofile-created";
    public static final String TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_UPDATED = "commons-blocks-profiles-service.villageexportpotentialprofile-updated";
    public static final String TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_DELETED = "commons-blocks-profiles-service.villageexportpotentialprofile-deleted";

    private final KafkaTemplate<String,VillageExportPotentialProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageExportPotentialProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageExportPotentialProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageExportPotentialProfile-created topic when a new VillageExportPotentialProfile is created.
     *
     * @param dto The VillageExportPotentialProfileDTO containing the created VillageExportPotentialProfile's information
     */
    public void created(VillageExportPotentialProfileDTO dto) {
        log.debug("Entry created(VillageExportPotentialProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageExportPotentialProfile-updated topic when a VillageExportPotentialProfile is modified.
     *
     * @param dto The VillageExportPotentialProfileDTO containing the updated VillageExportPotentialProfile's information
     */
    public void updated(VillageExportPotentialProfileDTO dto) {
        log.debug("Entry updated(VillageExportPotentialProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageExportPotentialProfile-deleted topic when a VillageExportPotentialProfile is removed.
     *
     * @param dto The VillageExportPotentialProfileDTO containing the deleted VillageExportPotentialProfile's information
     */
    public void deleted(VillageExportPotentialProfileDTO dto){
        log.debug("Entry deleted(VillageExportPotentialProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEXPORTPOTENTIALPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
