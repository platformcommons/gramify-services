package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageEnvironmentalRiskProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageEnvironmentalRiskProfile-related events. This component is responsible for
 * publishing VillageEnvironmentalRiskProfile events to designated Kafka topics when VillageEnvironmentalRiskProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_CREATED} - For newly created VillageEnvironmentalRiskProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_UPDATED} - For VillageEnvironmentalRiskProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_DELETED} - For VillageEnvironmentalRiskProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageEnvironmentalRiskProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_CREATED = "commons-blocks-profiles-service.villageenvironmentalriskprofile-created";
    public static final String TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_UPDATED = "commons-blocks-profiles-service.villageenvironmentalriskprofile-updated";
    public static final String TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_DELETED = "commons-blocks-profiles-service.villageenvironmentalriskprofile-deleted";

    private final KafkaTemplate<String,VillageEnvironmentalRiskProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageEnvironmentalRiskProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageEnvironmentalRiskProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageEnvironmentalRiskProfile-created topic when a new VillageEnvironmentalRiskProfile is created.
     *
     * @param dto The VillageEnvironmentalRiskProfileDTO containing the created VillageEnvironmentalRiskProfile's information
     */
    public void created(VillageEnvironmentalRiskProfileDTO dto) {
        log.debug("Entry created(VillageEnvironmentalRiskProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageEnvironmentalRiskProfile-updated topic when a VillageEnvironmentalRiskProfile is modified.
     *
     * @param dto The VillageEnvironmentalRiskProfileDTO containing the updated VillageEnvironmentalRiskProfile's information
     */
    public void updated(VillageEnvironmentalRiskProfileDTO dto) {
        log.debug("Entry updated(VillageEnvironmentalRiskProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageEnvironmentalRiskProfile-deleted topic when a VillageEnvironmentalRiskProfile is removed.
     *
     * @param dto The VillageEnvironmentalRiskProfileDTO containing the deleted VillageEnvironmentalRiskProfile's information
     */
    public void deleted(VillageEnvironmentalRiskProfileDTO dto){
        log.debug("Entry deleted(VillageEnvironmentalRiskProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEENVIRONMENTALRISKPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
