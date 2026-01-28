package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageMarketProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageMarketProfile-related events. This component is responsible for
 * publishing VillageMarketProfile events to designated Kafka topics when VillageMarketProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEMARKETPROFILE_CREATED} - For newly created VillageMarketProfiles</li>
 *     <li>{@link #TOPIC_VILLAGEMARKETPROFILE_UPDATED} - For VillageMarketProfile updates</li>
 *     <li>{@link #TOPIC_VILLAGEMARKETPROFILE_DELETED} - For VillageMarketProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageMarketProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEMARKETPROFILE_CREATED = "commons-blocks-profiles-service.villagemarketprofile-created";
    public static final String TOPIC_VILLAGEMARKETPROFILE_UPDATED = "commons-blocks-profiles-service.villagemarketprofile-updated";
    public static final String TOPIC_VILLAGEMARKETPROFILE_DELETED = "commons-blocks-profiles-service.villagemarketprofile-deleted";

    private final KafkaTemplate<String,VillageMarketProfileDTO> kafkaTemplate;

    /**
     * Constructs a new VillageMarketProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageMarketProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageMarketProfile-created topic when a new VillageMarketProfile is created.
     *
     * @param dto The VillageMarketProfileDTO containing the created VillageMarketProfile's information
     */
    public void created(VillageMarketProfileDTO dto) {
        log.debug("Entry created(VillageMarketProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEMARKETPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageMarketProfile-updated topic when a VillageMarketProfile is modified.
     *
     * @param dto The VillageMarketProfileDTO containing the updated VillageMarketProfile's information
     */
    public void updated(VillageMarketProfileDTO dto) {
        log.debug("Entry updated(VillageMarketProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEMARKETPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageMarketProfile-deleted topic when a VillageMarketProfile is removed.
     *
     * @param dto The VillageMarketProfileDTO containing the deleted VillageMarketProfile's information
     */
    public void deleted(VillageMarketProfileDTO dto){
        log.debug("Entry deleted(VillageMarketProfile={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEMARKETPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
