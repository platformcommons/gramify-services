package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.NonAgriculturalMarketActiviesDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for NonAgriculturalMarketActivies-related events. This component is responsible for
 * publishing NonAgriculturalMarketActivies events to designated Kafka topics when NonAgriculturalMarketActiviess are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_NONAGRICULTURALMARKETACTIVIES_CREATED} - For newly created NonAgriculturalMarketActiviess</li>
 *     <li>{@link #TOPIC_NONAGRICULTURALMARKETACTIVIES_UPDATED} - For NonAgriculturalMarketActivies updates</li>
 *     <li>{@link #TOPIC_NONAGRICULTURALMARKETACTIVIES_DELETED} - For NonAgriculturalMarketActivies deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class NonAgriculturalMarketActiviesProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_NONAGRICULTURALMARKETACTIVIES_CREATED = "commons-blocks-profiles-service.nonagriculturalmarketactivies-created";
    public static final String TOPIC_NONAGRICULTURALMARKETACTIVIES_UPDATED = "commons-blocks-profiles-service.nonagriculturalmarketactivies-updated";
    public static final String TOPIC_NONAGRICULTURALMARKETACTIVIES_DELETED = "commons-blocks-profiles-service.nonagriculturalmarketactivies-deleted";

    private final KafkaTemplate<String,NonAgriculturalMarketActiviesDTO> kafkaTemplate;

    /**
     * Constructs a new NonAgriculturalMarketActiviesProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public NonAgriculturalMarketActiviesProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the NonAgriculturalMarketActivies-created topic when a new NonAgriculturalMarketActivies is created.
     *
     * @param dto The NonAgriculturalMarketActiviesDTO containing the created NonAgriculturalMarketActivies's information
     */
    public void created(NonAgriculturalMarketActiviesDTO dto) {
        log.debug("Entry created(NonAgriculturalMarketActivies={})", dto);
        kafkaTemplate.send(TOPIC_NONAGRICULTURALMARKETACTIVIES_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the NonAgriculturalMarketActivies-updated topic when a NonAgriculturalMarketActivies is modified.
     *
     * @param dto The NonAgriculturalMarketActiviesDTO containing the updated NonAgriculturalMarketActivies's information
     */
    public void updated(NonAgriculturalMarketActiviesDTO dto) {
        log.debug("Entry updated(NonAgriculturalMarketActivies={})", dto);
        kafkaTemplate.send(TOPIC_NONAGRICULTURALMARKETACTIVIES_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the NonAgriculturalMarketActivies-deleted topic when a NonAgriculturalMarketActivies is removed.
     *
     * @param dto The NonAgriculturalMarketActiviesDTO containing the deleted NonAgriculturalMarketActivies's information
     */
    public void deleted(NonAgriculturalMarketActiviesDTO dto){
        log.debug("Entry deleted(NonAgriculturalMarketActivies={})", dto);
        kafkaTemplate.send(TOPIC_NONAGRICULTURALMARKETACTIVIES_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
