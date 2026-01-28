package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageAgriInputDemandProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageAgriInputDemandProfile-related events. This component is responsible for
 * publishing VillageAgriInputDemandProfile events to designated Kafka topics when VillageAgriInputDemandProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_CREATED} - For newly created VillageAgriInputDemandProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_UPDATED} - For VillageAgriInputDemandProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_DELETED} - For VillageAgriInputDemandProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageAgriInputDemandProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_CREATED = "commons-blocks-profiles-service.villageagriinputdemandprofile-created";
    public static final String TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_UPDATED = "commons-blocks-profiles-service.villageagriinputdemandprofile-updated";
    public static final String TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_DELETED = "commons-blocks-profiles-service.villageagriinputdemandprofile-deleted";

    private final KafkaTemplate<String,VillageAgriInputDemandProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageAgriInputDemandProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageAgriInputDemandProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageAgriInputDemandProfile-created topic when a new VillageAgriInputDemandProfile is created.
     *
     * @param dto The VillageAgriInputDemandProfileDTO containing the created VillageAgriInputDemandProfile's information
     */
    public void created(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry created(VillageAgriInputDemandProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageAgriInputDemandProfile-updated topic when a VillageAgriInputDemandProfile is modified.
     *
     * @param dto The VillageAgriInputDemandProfileDTO containing the updated VillageAgriInputDemandProfile's information
     */
    public void updated(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry updated(VillageAgriInputDemandProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageAgriInputDemandProfile-deleted topic when a VillageAgriInputDemandProfile is removed.
     *
     * @param dto The VillageAgriInputDemandProfileDTO containing the deleted VillageAgriInputDemandProfile's information
     */
    public void deleted(VillageAgriInputDemandProfileDTO dto){
        log.debug("Entry deleted(VillageAgriInputDemandProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEAGRIINPUTDEMANDPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
