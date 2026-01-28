package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.PowerCutSeasonDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for PowerCutSeason-related events. This component is responsible for
 * publishing PowerCutSeason events to designated Kafka topics when PowerCutSeasons are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_POWERCUTSEASON_CREATED} - For newly created PowerCutSeasons</li>
 *     <li>{@link #TOPIC_POWERCUTSEASON_UPDATED} - For PowerCutSeason updates</li>
 *     <li>{@link #TOPIC_POWERCUTSEASON_DELETED} - For PowerCutSeason deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class PowerCutSeasonProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_POWERCUTSEASON_CREATED = "commons-blocks-profiles-service.powercutseason-created";
    public static final String TOPIC_POWERCUTSEASON_UPDATED = "commons-blocks-profiles-service.powercutseason-updated";
    public static final String TOPIC_POWERCUTSEASON_DELETED = "commons-blocks-profiles-service.powercutseason-deleted";

    private final KafkaTemplate<String,PowerCutSeasonDTO> kafkaTemplate;

    /**
     * Constructs a new PowerCutSeasonProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public PowerCutSeasonProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the PowerCutSeason-created topic when a new PowerCutSeason is created.
     *
     * @param dto The PowerCutSeasonDTO containing the created PowerCutSeason's information
     */
    public void created(PowerCutSeasonDTO dto) {
        log.debug("Entry created(PowerCutSeason={})", dto);
        kafkaTemplate.send(TOPIC_POWERCUTSEASON_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the PowerCutSeason-updated topic when a PowerCutSeason is modified.
     *
     * @param dto The PowerCutSeasonDTO containing the updated PowerCutSeason's information
     */
    public void updated(PowerCutSeasonDTO dto) {
        log.debug("Entry updated(PowerCutSeason={})", dto);
        kafkaTemplate.send(TOPIC_POWERCUTSEASON_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the PowerCutSeason-deleted topic when a PowerCutSeason is removed.
     *
     * @param dto The PowerCutSeasonDTO containing the deleted PowerCutSeason's information
     */
    public void deleted(PowerCutSeasonDTO dto){
        log.debug("Entry deleted(PowerCutSeason={})", dto);
        kafkaTemplate.send(TOPIC_POWERCUTSEASON_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
