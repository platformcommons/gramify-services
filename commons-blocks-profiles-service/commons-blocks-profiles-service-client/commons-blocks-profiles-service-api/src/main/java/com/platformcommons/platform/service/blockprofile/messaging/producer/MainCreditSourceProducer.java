package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MainCreditSourceDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MainCreditSource-related events. This component is responsible for
 * publishing MainCreditSource events to designated Kafka topics when MainCreditSources are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MAINCREDITSOURCE_CREATED} - For newly created MainCreditSources</li>
 *     <li>{@link #TOPIC_MAINCREDITSOURCE_UPDATED} - For MainCreditSource updates</li>
 *     <li>{@link #TOPIC_MAINCREDITSOURCE_DELETED} - For MainCreditSource deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MainCreditSourceProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MAINCREDITSOURCE_CREATED = "commons-blocks-profiles-service.maincreditsource-created";
    public static final String TOPIC_MAINCREDITSOURCE_UPDATED = "commons-blocks-profiles-service.maincreditsource-updated";
    public static final String TOPIC_MAINCREDITSOURCE_DELETED = "commons-blocks-profiles-service.maincreditsource-deleted";

    private final KafkaTemplate<String,MainCreditSourceDTO> kafkaTemplate;

    /**
     * Constructs a new MainCreditSourceProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MainCreditSourceProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MainCreditSource-created topic when a new MainCreditSource is created.
     *
     * @param dto The MainCreditSourceDTO containing the created MainCreditSource's information
     */
    public void created(MainCreditSourceDTO dto) {
        log.debug("Entry created(MainCreditSource={})", dto);
        kafkaTemplate.send(TOPIC_MAINCREDITSOURCE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MainCreditSource-updated topic when a MainCreditSource is modified.
     *
     * @param dto The MainCreditSourceDTO containing the updated MainCreditSource's information
     */
    public void updated(MainCreditSourceDTO dto) {
        log.debug("Entry updated(MainCreditSource={})", dto);
        kafkaTemplate.send(TOPIC_MAINCREDITSOURCE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MainCreditSource-deleted topic when a MainCreditSource is removed.
     *
     * @param dto The MainCreditSourceDTO containing the deleted MainCreditSource's information
     */
    public void deleted(MainCreditSourceDTO dto){
        log.debug("Entry deleted(MainCreditSource={})", dto);
        kafkaTemplate.send(TOPIC_MAINCREDITSOURCE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
