package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageCulturalResourceProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageCulturalResourceProfile-related events. This component is responsible for
 * publishing VillageCulturalResourceProfile events to designated Kafka topics when VillageCulturalResourceProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECULTURALRESOURCEPROFILE_CREATED} - For newly created VillageCulturalResourceProfiles</li>
 *     <li>{@link #TOPIC_VILLAGECULTURALRESOURCEPROFILE_UPDATED} - For VillageCulturalResourceProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGECULTURALRESOURCEPROFILE_DELETED} - For VillageCulturalResourceProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageCulturalResourceProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECULTURALRESOURCEPROFILE_CREATED = "commons-blocks-profiles-service.villageculturalresourceprofile-created";
    public static final String TOPIC_VILLAGECULTURALRESOURCEPROFILE_UPDATED = "commons-blocks-profiles-service.villageculturalresourceprofile-updated";
    public static final String TOPIC_VILLAGECULTURALRESOURCEPROFILE_DELETED = "commons-blocks-profiles-service.villageculturalresourceprofile-deleted";

    private final KafkaTemplate<String,VillageCulturalResourceProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageCulturalResourceProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageCulturalResourceProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageCulturalResourceProfile-created topic when a new VillageCulturalResourceProfile is created.
     *
     * @param dto The VillageCulturalResourceProfileDTO containing the created VillageCulturalResourceProfile's information
     */
    public void created(VillageCulturalResourceProfileDTO dto) {
        log.debug("Entry created(VillageCulturalResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECULTURALRESOURCEPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageCulturalResourceProfile-updated topic when a VillageCulturalResourceProfile is modified.
     *
     * @param dto The VillageCulturalResourceProfileDTO containing the updated VillageCulturalResourceProfile's information
     */
    public void updated(VillageCulturalResourceProfileDTO dto) {
        log.debug("Entry updated(VillageCulturalResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECULTURALRESOURCEPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageCulturalResourceProfile-deleted topic when a VillageCulturalResourceProfile is removed.
     *
     * @param dto The VillageCulturalResourceProfileDTO containing the deleted VillageCulturalResourceProfile's information
     */
    public void deleted(VillageCulturalResourceProfileDTO dto){
        log.debug("Entry deleted(VillageCulturalResourceProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECULTURALRESOURCEPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
