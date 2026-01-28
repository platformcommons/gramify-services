package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageHorticultureProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageHorticultureProfile-related events. This component is responsible for
 * publishing VillageHorticultureProfile events to designated Kafka topics when VillageHorticultureProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEHORTICULTUREPROFILE_CREATED} - For newly created VillageHorticultureProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEHORTICULTUREPROFILE_UPDATED} - For VillageHorticultureProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEHORTICULTUREPROFILE_DELETED} - For VillageHorticultureProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageHorticultureProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEHORTICULTUREPROFILE_CREATED = "commons-blocks-profiles-service.villagehorticultureprofile-created";
    public static final String TOPIC_VILLAGEHORTICULTUREPROFILE_UPDATED = "commons-blocks-profiles-service.villagehorticultureprofile-updated";
    public static final String TOPIC_VILLAGEHORTICULTUREPROFILE_DELETED = "commons-blocks-profiles-service.villagehorticultureprofile-deleted";

    private final KafkaTemplate<String,VillageHorticultureProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageHorticultureProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageHorticultureProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageHorticultureProfile-created topic when a new VillageHorticultureProfile is created.
     *
     * @param dto The VillageHorticultureProfileDTO containing the created VillageHorticultureProfile's information
     */
    public void created(VillageHorticultureProfileDTO dto) {
        log.debug("Entry created(VillageHorticultureProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHORTICULTUREPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageHorticultureProfile-updated topic when a VillageHorticultureProfile is modified.
     *
     * @param dto The VillageHorticultureProfileDTO containing the updated VillageHorticultureProfile's information
     */
    public void updated(VillageHorticultureProfileDTO dto) {
        log.debug("Entry updated(VillageHorticultureProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHORTICULTUREPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageHorticultureProfile-deleted topic when a VillageHorticultureProfile is removed.
     *
     * @param dto The VillageHorticultureProfileDTO containing the deleted VillageHorticultureProfile's information
     */
    public void deleted(VillageHorticultureProfileDTO dto){
        log.debug("Entry deleted(VillageHorticultureProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHORTICULTUREPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
