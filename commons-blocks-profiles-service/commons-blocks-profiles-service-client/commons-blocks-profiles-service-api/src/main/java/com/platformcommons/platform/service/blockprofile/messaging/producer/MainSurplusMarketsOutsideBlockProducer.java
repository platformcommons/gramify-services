package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MainSurplusMarketsOutsideBlockDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MainSurplusMarketsOutsideBlock-related events. This component is responsible for
 * publishing MainSurplusMarketsOutsideBlock events to designated Kafka topics when MainSurplusMarketsOutsideBlocks are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_CREATED} - For newly created MainSurplusMarketsOutsideBlocks</li>
 *     <li>{@link #TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_UPDATED} - For MainSurplusMarketsOutsideBlock updates</li>
 *     <li>{@link #TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_DELETED} - For MainSurplusMarketsOutsideBlock deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MainSurplusMarketsOutsideBlockProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_CREATED = "commons-blocks-profiles-service.mainsurplusmarketsoutsideblock-created";
    public static final String TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_UPDATED = "commons-blocks-profiles-service.mainsurplusmarketsoutsideblock-updated";
    public static final String TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_DELETED = "commons-blocks-profiles-service.mainsurplusmarketsoutsideblock-deleted";

    private final KafkaTemplate<String,MainSurplusMarketsOutsideBlockDTO> kafkaTemplate;

    /**
     * Constructs a new MainSurplusMarketsOutsideBlockProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MainSurplusMarketsOutsideBlockProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MainSurplusMarketsOutsideBlock-created topic when a new MainSurplusMarketsOutsideBlock is created.
     *
     * @param dto The MainSurplusMarketsOutsideBlockDTO containing the created MainSurplusMarketsOutsideBlock's information
     */
    public void created(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry created(MainSurplusMarketsOutsideBlock={})", dto);
        kafkaTemplate.send(TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MainSurplusMarketsOutsideBlock-updated topic when a MainSurplusMarketsOutsideBlock is modified.
     *
     * @param dto The MainSurplusMarketsOutsideBlockDTO containing the updated MainSurplusMarketsOutsideBlock's information
     */
    public void updated(MainSurplusMarketsOutsideBlockDTO dto) {
        log.debug("Entry updated(MainSurplusMarketsOutsideBlock={})", dto);
        kafkaTemplate.send(TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MainSurplusMarketsOutsideBlock-deleted topic when a MainSurplusMarketsOutsideBlock is removed.
     *
     * @param dto The MainSurplusMarketsOutsideBlockDTO containing the deleted MainSurplusMarketsOutsideBlock's information
     */
    public void deleted(MainSurplusMarketsOutsideBlockDTO dto){
        log.debug("Entry deleted(MainSurplusMarketsOutsideBlock={})", dto);
        kafkaTemplate.send(TOPIC_MAINSURPLUSMARKETSOUTSIDEBLOCK_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
