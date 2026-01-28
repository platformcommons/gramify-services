package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MainSurplusDestinationDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MainSurplusDestination-related events. This component is responsible for
 * publishing MainSurplusDestination events to designated Kafka topics when MainSurplusDestinations are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MAINSURPLUSDESTINATION_CREATED} - For newly created MainSurplusDestinations</li>
 *     <li>{@link #TOPIC_MAINSURPLUSDESTINATION_UPDATED} - For MainSurplusDestination updates</li>
 *     <li>{@link #TOPIC_MAINSURPLUSDESTINATION_DELETED} - For MainSurplusDestination deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MainSurplusDestinationProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MAINSURPLUSDESTINATION_CREATED = "commons-blocks-profiles-service.mainsurplusdestination-created";
    public static final String TOPIC_MAINSURPLUSDESTINATION_UPDATED = "commons-blocks-profiles-service.mainsurplusdestination-updated";
    public static final String TOPIC_MAINSURPLUSDESTINATION_DELETED = "commons-blocks-profiles-service.mainsurplusdestination-deleted";

    private final KafkaTemplate<String,MainSurplusDestinationDTO> kafkaTemplate;

    /**
     * Constructs a new MainSurplusDestinationProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MainSurplusDestinationProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MainSurplusDestination-created topic when a new MainSurplusDestination is created.
     *
     * @param dto The MainSurplusDestinationDTO containing the created MainSurplusDestination's information
     */
    public void created(MainSurplusDestinationDTO dto) {
        log.debug("Entry created(MainSurplusDestination={})", dto);
        kafkaTemplate.send(TOPIC_MAINSURPLUSDESTINATION_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MainSurplusDestination-updated topic when a MainSurplusDestination is modified.
     *
     * @param dto The MainSurplusDestinationDTO containing the updated MainSurplusDestination's information
     */
    public void updated(MainSurplusDestinationDTO dto) {
        log.debug("Entry updated(MainSurplusDestination={})", dto);
        kafkaTemplate.send(TOPIC_MAINSURPLUSDESTINATION_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MainSurplusDestination-deleted topic when a MainSurplusDestination is removed.
     *
     * @param dto The MainSurplusDestinationDTO containing the deleted MainSurplusDestination's information
     */
    public void deleted(MainSurplusDestinationDTO dto){
        log.debug("Entry deleted(MainSurplusDestination={})", dto);
        kafkaTemplate.send(TOPIC_MAINSURPLUSDESTINATION_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
