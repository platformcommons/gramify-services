package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.SeasonalityOfSurplusDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for SeasonalityOfSurplus-related events. This component is responsible for
 * publishing SeasonalityOfSurplus events to designated Kafka topics when SeasonalityOfSurpluss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_SEASONALITYOFSURPLUS_CREATED} - For newly created SeasonalityOfSurpluss</li>
 *     <li>{@link #TOPIC_SEASONALITYOFSURPLUS_UPDATED} - For SeasonalityOfSurplus updates</li>
 *     <li>{@link #TOPIC_SEASONALITYOFSURPLUS_DELETED} - For SeasonalityOfSurplus deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class SeasonalityOfSurplusProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_SEASONALITYOFSURPLUS_CREATED = "commons-blocks-profiles-service.seasonalityofsurplus-created";
    public static final String TOPIC_SEASONALITYOFSURPLUS_UPDATED = "commons-blocks-profiles-service.seasonalityofsurplus-updated";
    public static final String TOPIC_SEASONALITYOFSURPLUS_DELETED = "commons-blocks-profiles-service.seasonalityofsurplus-deleted";

    private final KafkaTemplate<String,SeasonalityOfSurplusDTO> kafkaTemplate;

    /**
     * Constructs a new SeasonalityOfSurplusProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public SeasonalityOfSurplusProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the SeasonalityOfSurplus-created topic when a new SeasonalityOfSurplus is created.
     *
     * @param dto The SeasonalityOfSurplusDTO containing the created SeasonalityOfSurplus's information
     */
    public void created(SeasonalityOfSurplusDTO dto) {
        log.debug("Entry created(SeasonalityOfSurplus={})", dto);
        kafkaTemplate.send(TOPIC_SEASONALITYOFSURPLUS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the SeasonalityOfSurplus-updated topic when a SeasonalityOfSurplus is modified.
     *
     * @param dto The SeasonalityOfSurplusDTO containing the updated SeasonalityOfSurplus's information
     */
    public void updated(SeasonalityOfSurplusDTO dto) {
        log.debug("Entry updated(SeasonalityOfSurplus={})", dto);
        kafkaTemplate.send(TOPIC_SEASONALITYOFSURPLUS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the SeasonalityOfSurplus-deleted topic when a SeasonalityOfSurplus is removed.
     *
     * @param dto The SeasonalityOfSurplusDTO containing the deleted SeasonalityOfSurplus's information
     */
    public void deleted(SeasonalityOfSurplusDTO dto){
        log.debug("Entry deleted(SeasonalityOfSurplus={})", dto);
        kafkaTemplate.send(TOPIC_SEASONALITYOFSURPLUS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
