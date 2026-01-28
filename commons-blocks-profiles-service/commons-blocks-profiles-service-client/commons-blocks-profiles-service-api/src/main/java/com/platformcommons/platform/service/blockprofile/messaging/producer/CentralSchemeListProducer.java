package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.CentralSchemeListDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for CentralSchemeList-related events. This component is responsible for
 * publishing CentralSchemeList events to designated Kafka topics when CentralSchemeLists are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_CENTRALSCHEMELIST_CREATED} - For newly created CentralSchemeLists</li>
 *     <li>{@link #TOPIC_CENTRALSCHEMELIST_UPDATED} - For CentralSchemeList updates</li>
 *     <li>{@link #TOPIC_CENTRALSCHEMELIST_DELETED} - For CentralSchemeList deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class CentralSchemeListProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_CENTRALSCHEMELIST_CREATED = "commons-blocks-profiles-service.centralschemelist-created";
    public static final String TOPIC_CENTRALSCHEMELIST_UPDATED = "commons-blocks-profiles-service.centralschemelist-updated";
    public static final String TOPIC_CENTRALSCHEMELIST_DELETED = "commons-blocks-profiles-service.centralschemelist-deleted";

    private final KafkaTemplate<String,CentralSchemeListDTO> kafkaTemplate;

    /**
     * Constructs a new CentralSchemeListProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public CentralSchemeListProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the CentralSchemeList-created topic when a new CentralSchemeList is created.
     *
     * @param dto The CentralSchemeListDTO containing the created CentralSchemeList's information
     */
    public void created(CentralSchemeListDTO dto) {
        log.debug("Entry created(CentralSchemeList={})", dto);
        kafkaTemplate.send(TOPIC_CENTRALSCHEMELIST_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the CentralSchemeList-updated topic when a CentralSchemeList is modified.
     *
     * @param dto The CentralSchemeListDTO containing the updated CentralSchemeList's information
     */
    public void updated(CentralSchemeListDTO dto) {
        log.debug("Entry updated(CentralSchemeList={})", dto);
        kafkaTemplate.send(TOPIC_CENTRALSCHEMELIST_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the CentralSchemeList-deleted topic when a CentralSchemeList is removed.
     *
     * @param dto The CentralSchemeListDTO containing the deleted CentralSchemeList's information
     */
    public void deleted(CentralSchemeListDTO dto){
        log.debug("Entry deleted(CentralSchemeList={})", dto);
        kafkaTemplate.send(TOPIC_CENTRALSCHEMELIST_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
