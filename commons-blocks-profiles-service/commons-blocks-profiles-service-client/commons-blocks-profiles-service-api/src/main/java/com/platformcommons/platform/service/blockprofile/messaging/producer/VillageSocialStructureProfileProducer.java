package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageSocialStructureProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageSocialStructureProfile-related events. This component is responsible for
 * publishing VillageSocialStructureProfile events to designated Kafka topics when VillageSocialStructureProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_CREATED} - For newly created VillageSocialStructureProfiles</li>
 *     <li>{@link #TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_UPDATED} - For VillageSocialStructureProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_DELETED} - For VillageSocialStructureProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageSocialStructureProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_CREATED = "commons-blocks-profiles-service.villagesocialstructureprofile-created";
    public static final String TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_UPDATED = "commons-blocks-profiles-service.villagesocialstructureprofile-updated";
    public static final String TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_DELETED = "commons-blocks-profiles-service.villagesocialstructureprofile-deleted";

    private final KafkaTemplate<String,VillageSocialStructureProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageSocialStructureProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageSocialStructureProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageSocialStructureProfile-created topic when a new VillageSocialStructureProfile is created.
     *
     * @param dto The VillageSocialStructureProfileDTO containing the created VillageSocialStructureProfile's information
     */
    public void created(VillageSocialStructureProfileDTO dto) {
        log.debug("Entry created(VillageSocialStructureProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageSocialStructureProfile-updated topic when a VillageSocialStructureProfile is modified.
     *
     * @param dto The VillageSocialStructureProfileDTO containing the updated VillageSocialStructureProfile's information
     */
    public void updated(VillageSocialStructureProfileDTO dto) {
        log.debug("Entry updated(VillageSocialStructureProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageSocialStructureProfile-deleted topic when a VillageSocialStructureProfile is removed.
     *
     * @param dto The VillageSocialStructureProfileDTO containing the deleted VillageSocialStructureProfile's information
     */
    public void deleted(VillageSocialStructureProfileDTO dto){
        log.debug("Entry deleted(VillageSocialStructureProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESOCIALSTRUCTUREPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
