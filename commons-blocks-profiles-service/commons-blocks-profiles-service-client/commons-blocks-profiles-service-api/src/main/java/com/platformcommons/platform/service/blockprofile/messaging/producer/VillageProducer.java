package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for Village-related events. This component is responsible for
 * publishing Village events to designated Kafka topics when Villages are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGE_CREATED} - For newly created Villages</li>
 *     <li>{@link #TOPIC_VILLAGE_UPDATED} - For Village updates</li>
 *     <li>{@link #TOPIC_VILLAGE_DELETED} - For Village deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGE_CREATED = "commons-blocks-profiles-service.village-created";
    public static final String TOPIC_VILLAGE_UPDATED = "commons-blocks-profiles-service.village-updated";
    public static final String TOPIC_VILLAGE_DELETED = "commons-blocks-profiles-service.village-deleted";

    private final KafkaTemplate<String,VillageDTO> kafkaTemplate;

    /**
     * Constructs a new VillageProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the Village-created topic when a new Village is created.
     *
     * @param dto The VillageDTO containing the created Village's information
     */
    public void created(VillageDTO dto) {
        log.debug("Entry created(Village={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the Village-updated topic when a Village is modified.
     *
     * @param dto The VillageDTO containing the updated Village's information
     */
    public void updated(VillageDTO dto) {
        log.debug("Entry updated(Village={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the Village-deleted topic when a Village is removed.
     *
     * @param dto The VillageDTO containing the deleted Village's information
     */
    public void deleted(VillageDTO dto){
        log.debug("Entry deleted(Village={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
