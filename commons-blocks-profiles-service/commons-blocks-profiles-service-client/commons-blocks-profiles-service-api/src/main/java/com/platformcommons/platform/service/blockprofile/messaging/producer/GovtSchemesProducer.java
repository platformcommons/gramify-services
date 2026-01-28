package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.GovtSchemesDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for GovtSchemes-related events. This component is responsible for
 * publishing GovtSchemes events to designated Kafka topics when GovtSchemess are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_GOVTSCHEMES_CREATED} - For newly created GovtSchemess</li>
 *     <li>{@link #TOPIC_GOVTSCHEMES_UPDATED} - For GovtSchemes updates</li>
 *     <li>{@link #TOPIC_GOVTSCHEMES_DELETED} - For GovtSchemes deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class GovtSchemesProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_GOVTSCHEMES_CREATED = "commons-blocks-profiles-service.govtschemes-created";
    public static final String TOPIC_GOVTSCHEMES_UPDATED = "commons-blocks-profiles-service.govtschemes-updated";
    public static final String TOPIC_GOVTSCHEMES_DELETED = "commons-blocks-profiles-service.govtschemes-deleted";

    private final KafkaTemplate<String,GovtSchemesDTO> kafkaTemplate;

    /**
     * Constructs a new GovtSchemesProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public GovtSchemesProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the GovtSchemes-created topic when a new GovtSchemes is created.
     *
     * @param dto The GovtSchemesDTO containing the created GovtSchemes's information
     */
    public void created(GovtSchemesDTO dto) {
        log.debug("Entry created(GovtSchemes={})", dto);
        kafkaTemplate.send(TOPIC_GOVTSCHEMES_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the GovtSchemes-updated topic when a GovtSchemes is modified.
     *
     * @param dto The GovtSchemesDTO containing the updated GovtSchemes's information
     */
    public void updated(GovtSchemesDTO dto) {
        log.debug("Entry updated(GovtSchemes={})", dto);
        kafkaTemplate.send(TOPIC_GOVTSCHEMES_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the GovtSchemes-deleted topic when a GovtSchemes is removed.
     *
     * @param dto The GovtSchemesDTO containing the deleted GovtSchemes's information
     */
    public void deleted(GovtSchemesDTO dto){
        log.debug("Entry deleted(GovtSchemes={})", dto);
        kafkaTemplate.send(TOPIC_GOVTSCHEMES_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
