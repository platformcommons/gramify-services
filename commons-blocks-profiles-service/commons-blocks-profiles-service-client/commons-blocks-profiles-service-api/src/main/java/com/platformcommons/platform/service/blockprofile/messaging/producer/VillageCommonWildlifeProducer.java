package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageCommonWildlifeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageCommonWildlife-related events. This component is responsible for
 * publishing VillageCommonWildlife events to designated Kafka topics when VillageCommonWildlifes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECOMMONWILDLIFE_CREATED} - For newly created VillageCommonWildlifes</li>
 *     <li>{@link #TOPIC_VILLAGECOMMONWILDLIFE_UPDATED} - For VillageCommonWildlife updates</li>
 *     <li>{@link #TOPIC_VILLAGECOMMONWILDLIFE_DELETED} - For VillageCommonWildlife deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageCommonWildlifeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECOMMONWILDLIFE_CREATED = "commons-blocks-profiles-service.villagecommonwildlife-created";
    public static final String TOPIC_VILLAGECOMMONWILDLIFE_UPDATED = "commons-blocks-profiles-service.villagecommonwildlife-updated";
    public static final String TOPIC_VILLAGECOMMONWILDLIFE_DELETED = "commons-blocks-profiles-service.villagecommonwildlife-deleted";

    private final KafkaTemplate<String,VillageCommonWildlifeDTO> kafkaTemplate;

    /**
     * Constructs a new VillageCommonWildlifeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageCommonWildlifeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageCommonWildlife-created topic when a new VillageCommonWildlife is created.
     *
     * @param dto The VillageCommonWildlifeDTO containing the created VillageCommonWildlife's information
     */
    public void created(VillageCommonWildlifeDTO dto) {
        log.debug("Entry created(VillageCommonWildlife={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONWILDLIFE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageCommonWildlife-updated topic when a VillageCommonWildlife is modified.
     *
     * @param dto The VillageCommonWildlifeDTO containing the updated VillageCommonWildlife's information
     */
    public void updated(VillageCommonWildlifeDTO dto) {
        log.debug("Entry updated(VillageCommonWildlife={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONWILDLIFE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageCommonWildlife-deleted topic when a VillageCommonWildlife is removed.
     *
     * @param dto The VillageCommonWildlifeDTO containing the deleted VillageCommonWildlife's information
     */
    public void deleted(VillageCommonWildlifeDTO dto){
        log.debug("Entry deleted(VillageCommonWildlife={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONWILDLIFE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
