package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageHumanCapitalProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageHumanCapitalProfile-related events. This component is responsible for
 * publishing VillageHumanCapitalProfile events to designated Kafka topics when VillageHumanCapitalProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEHUMANCAPITALPROFILE_CREATED} - For newly created VillageHumanCapitalProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEHUMANCAPITALPROFILE_UPDATED} - For VillageHumanCapitalProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEHUMANCAPITALPROFILE_DELETED} - For VillageHumanCapitalProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageHumanCapitalProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEHUMANCAPITALPROFILE_CREATED = "commons-blocks-profiles-service.villagehumancapitalprofile-created";
    public static final String TOPIC_VILLAGEHUMANCAPITALPROFILE_UPDATED = "commons-blocks-profiles-service.villagehumancapitalprofile-updated";
    public static final String TOPIC_VILLAGEHUMANCAPITALPROFILE_DELETED = "commons-blocks-profiles-service.villagehumancapitalprofile-deleted";

    private final KafkaTemplate<String,VillageHumanCapitalProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageHumanCapitalProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageHumanCapitalProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageHumanCapitalProfile-created topic when a new VillageHumanCapitalProfile is created.
     *
     * @param dto The VillageHumanCapitalProfileDTO containing the created VillageHumanCapitalProfile's information
     */
    public void created(VillageHumanCapitalProfileDTO dto) {
        log.debug("Entry created(VillageHumanCapitalProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHUMANCAPITALPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageHumanCapitalProfile-updated topic when a VillageHumanCapitalProfile is modified.
     *
     * @param dto The VillageHumanCapitalProfileDTO containing the updated VillageHumanCapitalProfile's information
     */
    public void updated(VillageHumanCapitalProfileDTO dto) {
        log.debug("Entry updated(VillageHumanCapitalProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHUMANCAPITALPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageHumanCapitalProfile-deleted topic when a VillageHumanCapitalProfile is removed.
     *
     * @param dto The VillageHumanCapitalProfileDTO containing the deleted VillageHumanCapitalProfile's information
     */
    public void deleted(VillageHumanCapitalProfileDTO dto){
        log.debug("Entry deleted(VillageHumanCapitalProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEHUMANCAPITALPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
