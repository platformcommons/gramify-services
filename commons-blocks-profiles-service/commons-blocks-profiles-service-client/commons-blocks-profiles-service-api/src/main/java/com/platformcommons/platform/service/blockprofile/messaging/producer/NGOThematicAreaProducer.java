package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.NGOThematicAreaDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for NGOThematicArea-related events. This component is responsible for
 * publishing NGOThematicArea events to designated Kafka topics when NGOThematicAreas are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_NGOTHEMATICAREA_CREATED} - For newly created NGOThematicAreas</li>
 *     <li>{@link #TOPIC_NGOTHEMATICAREA_UPDATED} - For NGOThematicArea updates</li>
 *     <li>{@link #TOPIC_NGOTHEMATICAREA_DELETED} - For NGOThematicArea deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class NGOThematicAreaProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_NGOTHEMATICAREA_CREATED = "commons-blocks-profiles-service.ngothematicarea-created";
    public static final String TOPIC_NGOTHEMATICAREA_UPDATED = "commons-blocks-profiles-service.ngothematicarea-updated";
    public static final String TOPIC_NGOTHEMATICAREA_DELETED = "commons-blocks-profiles-service.ngothematicarea-deleted";

    private final KafkaTemplate<String,NGOThematicAreaDTO> kafkaTemplate;

    /**
     * Constructs a new NGOThematicAreaProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public NGOThematicAreaProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the NGOThematicArea-created topic when a new NGOThematicArea is created.
     *
     * @param dto The NGOThematicAreaDTO containing the created NGOThematicArea's information
     */
    public void created(NGOThematicAreaDTO dto) {
        log.debug("Entry created(NGOThematicArea={})", dto);
        kafkaTemplate.send(TOPIC_NGOTHEMATICAREA_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the NGOThematicArea-updated topic when a NGOThematicArea is modified.
     *
     * @param dto The NGOThematicAreaDTO containing the updated NGOThematicArea's information
     */
    public void updated(NGOThematicAreaDTO dto) {
        log.debug("Entry updated(NGOThematicArea={})", dto);
        kafkaTemplate.send(TOPIC_NGOTHEMATICAREA_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the NGOThematicArea-deleted topic when a NGOThematicArea is removed.
     *
     * @param dto The NGOThematicAreaDTO containing the deleted NGOThematicArea's information
     */
    public void deleted(NGOThematicAreaDTO dto){
        log.debug("Entry deleted(NGOThematicArea={})", dto);
        kafkaTemplate.send(TOPIC_NGOTHEMATICAREA_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
