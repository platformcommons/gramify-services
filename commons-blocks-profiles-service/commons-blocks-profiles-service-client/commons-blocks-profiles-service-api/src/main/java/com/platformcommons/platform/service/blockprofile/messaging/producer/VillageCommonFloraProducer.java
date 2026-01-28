package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFloraDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageCommonFlora-related events. This component is responsible for
 * publishing VillageCommonFlora events to designated Kafka topics when VillageCommonFloras are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECOMMONFLORA_CREATED} - For newly created VillageCommonFloras</li>
 *     <li>{@link #TOPIC_VILLAGECOMMONFLORA_UPDATED} - For VillageCommonFlora updates</li>
 *     <li>{@link #TOPIC_VILLAGECOMMONFLORA_DELETED} - For VillageCommonFlora deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageCommonFloraProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECOMMONFLORA_CREATED = "commons-blocks-profiles-service.villagecommonflora-created";
    public static final String TOPIC_VILLAGECOMMONFLORA_UPDATED = "commons-blocks-profiles-service.villagecommonflora-updated";
    public static final String TOPIC_VILLAGECOMMONFLORA_DELETED = "commons-blocks-profiles-service.villagecommonflora-deleted";

    private final KafkaTemplate<String,VillageCommonFloraDTO> kafkaTemplate;

    /**
     * Constructs a new VillageCommonFloraProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageCommonFloraProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageCommonFlora-created topic when a new VillageCommonFlora is created.
     *
     * @param dto The VillageCommonFloraDTO containing the created VillageCommonFlora's information
     */
    public void created(VillageCommonFloraDTO dto) {
        log.debug("Entry created(VillageCommonFlora={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONFLORA_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageCommonFlora-updated topic when a VillageCommonFlora is modified.
     *
     * @param dto The VillageCommonFloraDTO containing the updated VillageCommonFlora's information
     */
    public void updated(VillageCommonFloraDTO dto) {
        log.debug("Entry updated(VillageCommonFlora={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONFLORA_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageCommonFlora-deleted topic when a VillageCommonFlora is removed.
     *
     * @param dto The VillageCommonFloraDTO containing the deleted VillageCommonFlora's information
     */
    public void deleted(VillageCommonFloraDTO dto){
        log.debug("Entry deleted(VillageCommonFlora={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONFLORA_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
