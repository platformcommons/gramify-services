package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.BlockVillageDemographicsProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for BlockVillageDemographicsProfile-related events. This component is responsible for
 * publishing BlockVillageDemographicsProfile events to designated Kafka topics when BlockVillageDemographicsProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_CREATED} - For newly created BlockVillageDemographicsProfiles</li>
 *     <li>{@link #TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_UPDATED} - For BlockVillageDemographicsProfile updates</li>
 *     <li>{@link #TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_DELETED} - For BlockVillageDemographicsProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class BlockVillageDemographicsProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_CREATED = "commons-blocks-profiles-service.blockvillagedemographicsprofile-created";
    public static final String TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_UPDATED = "commons-blocks-profiles-service.blockvillagedemographicsprofile-updated";
    public static final String TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_DELETED = "commons-blocks-profiles-service.blockvillagedemographicsprofile-deleted";

    private final KafkaTemplate<String,BlockVillageDemographicsProfileDTO> kafkaTemplate;

    /**
     * Constructs a new BlockVillageDemographicsProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public BlockVillageDemographicsProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the BlockVillageDemographicsProfile-created topic when a new BlockVillageDemographicsProfile is created.
     *
     * @param dto The BlockVillageDemographicsProfileDTO containing the created BlockVillageDemographicsProfile's information
     */
    public void created(BlockVillageDemographicsProfileDTO dto) {
        log.debug("Entry created(BlockVillageDemographicsProfile={})", dto);
        kafkaTemplate.send(TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the BlockVillageDemographicsProfile-updated topic when a BlockVillageDemographicsProfile is modified.
     *
     * @param dto The BlockVillageDemographicsProfileDTO containing the updated BlockVillageDemographicsProfile's information
     */
    public void updated(BlockVillageDemographicsProfileDTO dto) {
        log.debug("Entry updated(BlockVillageDemographicsProfile={})", dto);
        kafkaTemplate.send(TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the BlockVillageDemographicsProfile-deleted topic when a BlockVillageDemographicsProfile is removed.
     *
     * @param dto The BlockVillageDemographicsProfileDTO containing the deleted BlockVillageDemographicsProfile's information
     */
    public void deleted(BlockVillageDemographicsProfileDTO dto){
        log.debug("Entry deleted(BlockVillageDemographicsProfile={})", dto);
        kafkaTemplate.send(TOPIC_BLOCKVILLAGEDEMOGRAPHICSPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
