package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HyperLocalMarketProfileDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HyperLocalMarketProfile-related events. This component is responsible for
 * publishing HyperLocalMarketProfile events to designated Kafka topics when HyperLocalMarketProfiles are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HYPERLOCALMARKETPROFILE_CREATED} - For newly created HyperLocalMarketProfiles</li>
 *     <li>{@link #TOPIC_HYPERLOCALMARKETPROFILE_UPDATED} - For HyperLocalMarketProfile updates</li>
 *     <li>{@link #TOPIC_HYPERLOCALMARKETPROFILE_DELETED} - For HyperLocalMarketProfile deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HyperLocalMarketProfileProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HYPERLOCALMARKETPROFILE_CREATED = "commons-blocks-profiles-service.hyperlocalmarketprofile-created";
    public static final String TOPIC_HYPERLOCALMARKETPROFILE_UPDATED = "commons-blocks-profiles-service.hyperlocalmarketprofile-updated";
    public static final String TOPIC_HYPERLOCALMARKETPROFILE_DELETED = "commons-blocks-profiles-service.hyperlocalmarketprofile-deleted";

    private final KafkaTemplate<String,HyperLocalMarketProfileDTO> kafkaTemplate;

    /**
     * Constructs a new HyperLocalMarketProfileProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HyperLocalMarketProfileProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HyperLocalMarketProfile-created topic when a new HyperLocalMarketProfile is created.
     *
     * @param dto The HyperLocalMarketProfileDTO containing the created HyperLocalMarketProfile's information
     */
    public void created(HyperLocalMarketProfileDTO dto) {
        log.debug("Entry created(HyperLocalMarketProfile={})", dto);
        kafkaTemplate.send(TOPIC_HYPERLOCALMARKETPROFILE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HyperLocalMarketProfile-updated topic when a HyperLocalMarketProfile is modified.
     *
     * @param dto The HyperLocalMarketProfileDTO containing the updated HyperLocalMarketProfile's information
     */
    public void updated(HyperLocalMarketProfileDTO dto) {
        log.debug("Entry updated(HyperLocalMarketProfile={})", dto);
        kafkaTemplate.send(TOPIC_HYPERLOCALMARKETPROFILE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HyperLocalMarketProfile-deleted topic when a HyperLocalMarketProfile is removed.
     *
     * @param dto The HyperLocalMarketProfileDTO containing the deleted HyperLocalMarketProfile's information
     */
    public void deleted(HyperLocalMarketProfileDTO dto){
        log.debug("Entry deleted(HyperLocalMarketProfile={})", dto);
        kafkaTemplate.send(TOPIC_HYPERLOCALMARKETPROFILE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
