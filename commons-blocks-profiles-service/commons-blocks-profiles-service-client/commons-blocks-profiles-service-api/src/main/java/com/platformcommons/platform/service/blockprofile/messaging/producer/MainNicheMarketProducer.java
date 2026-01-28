package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MainNicheMarketDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MainNicheMarket-related events. This component is responsible for
 * publishing MainNicheMarket events to designated Kafka topics when MainNicheMarkets are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MAINNICHEMARKET_CREATED} - For newly created MainNicheMarkets</li>
 *     <li>{@link #TOPIC_MAINNICHEMARKET_UPDATED} - For MainNicheMarket updates</li>
 *     <li>{@link #TOPIC_MAINNICHEMARKET_DELETED} - For MainNicheMarket deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MainNicheMarketProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MAINNICHEMARKET_CREATED = "commons-blocks-profiles-service.mainnichemarket-created";
    public static final String TOPIC_MAINNICHEMARKET_UPDATED = "commons-blocks-profiles-service.mainnichemarket-updated";
    public static final String TOPIC_MAINNICHEMARKET_DELETED = "commons-blocks-profiles-service.mainnichemarket-deleted";

    private final KafkaTemplate<String,MainNicheMarketDTO> kafkaTemplate;

    /**
     * Constructs a new MainNicheMarketProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MainNicheMarketProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MainNicheMarket-created topic when a new MainNicheMarket is created.
     *
     * @param dto The MainNicheMarketDTO containing the created MainNicheMarket's information
     */
    public void created(MainNicheMarketDTO dto) {
        log.debug("Entry created(MainNicheMarket={})", dto);
        kafkaTemplate.send(TOPIC_MAINNICHEMARKET_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MainNicheMarket-updated topic when a MainNicheMarket is modified.
     *
     * @param dto The MainNicheMarketDTO containing the updated MainNicheMarket's information
     */
    public void updated(MainNicheMarketDTO dto) {
        log.debug("Entry updated(MainNicheMarket={})", dto);
        kafkaTemplate.send(TOPIC_MAINNICHEMARKET_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MainNicheMarket-deleted topic when a MainNicheMarket is removed.
     *
     * @param dto The MainNicheMarketDTO containing the deleted MainNicheMarket's information
     */
    public void deleted(MainNicheMarketDTO dto){
        log.debug("Entry deleted(MainNicheMarket={})", dto);
        kafkaTemplate.send(TOPIC_MAINNICHEMARKET_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
