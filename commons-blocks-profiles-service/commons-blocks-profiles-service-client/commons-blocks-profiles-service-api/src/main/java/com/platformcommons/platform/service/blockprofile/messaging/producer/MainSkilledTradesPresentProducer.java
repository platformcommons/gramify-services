package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MainSkilledTradesPresentDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MainSkilledTradesPresent-related events. This component is responsible for
 * publishing MainSkilledTradesPresent events to designated Kafka topics when MainSkilledTradesPresents are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MAINSKILLEDTRADESPRESENT_CREATED} - For newly created MainSkilledTradesPresents</li>
 *     <li>{@link #TOPIC_MAINSKILLEDTRADESPRESENT_UPDATED} - For MainSkilledTradesPresent updates</li>
 *     <li>{@link #TOPIC_MAINSKILLEDTRADESPRESENT_DELETED} - For MainSkilledTradesPresent deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MainSkilledTradesPresentProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MAINSKILLEDTRADESPRESENT_CREATED = "commons-blocks-profiles-service.mainskilledtradespresent-created";
    public static final String TOPIC_MAINSKILLEDTRADESPRESENT_UPDATED = "commons-blocks-profiles-service.mainskilledtradespresent-updated";
    public static final String TOPIC_MAINSKILLEDTRADESPRESENT_DELETED = "commons-blocks-profiles-service.mainskilledtradespresent-deleted";

    private final KafkaTemplate<String,MainSkilledTradesPresentDTO> kafkaTemplate;

    /**
     * Constructs a new MainSkilledTradesPresentProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MainSkilledTradesPresentProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MainSkilledTradesPresent-created topic when a new MainSkilledTradesPresent is created.
     *
     * @param dto The MainSkilledTradesPresentDTO containing the created MainSkilledTradesPresent's information
     */
    public void created(MainSkilledTradesPresentDTO dto) {
        log.debug("Entry created(MainSkilledTradesPresent={})", dto);
        kafkaTemplate.send(TOPIC_MAINSKILLEDTRADESPRESENT_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MainSkilledTradesPresent-updated topic when a MainSkilledTradesPresent is modified.
     *
     * @param dto The MainSkilledTradesPresentDTO containing the updated MainSkilledTradesPresent's information
     */
    public void updated(MainSkilledTradesPresentDTO dto) {
        log.debug("Entry updated(MainSkilledTradesPresent={})", dto);
        kafkaTemplate.send(TOPIC_MAINSKILLEDTRADESPRESENT_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MainSkilledTradesPresent-deleted topic when a MainSkilledTradesPresent is removed.
     *
     * @param dto The MainSkilledTradesPresentDTO containing the deleted MainSkilledTradesPresent's information
     */
    public void deleted(MainSkilledTradesPresentDTO dto){
        log.debug("Entry deleted(MainSkilledTradesPresent={})", dto);
        kafkaTemplate.send(TOPIC_MAINSKILLEDTRADESPRESENT_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
