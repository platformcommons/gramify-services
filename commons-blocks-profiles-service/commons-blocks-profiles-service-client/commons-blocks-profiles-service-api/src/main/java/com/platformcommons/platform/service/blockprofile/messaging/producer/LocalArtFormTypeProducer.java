package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.LocalArtFormTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for LocalArtFormType-related events. This component is responsible for
 * publishing LocalArtFormType events to designated Kafka topics when LocalArtFormTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_LOCALARTFORMTYPE_CREATED} - For newly created LocalArtFormTypes</li>
 *     <li>{@link #TOPIC_LOCALARTFORMTYPE_UPDATED} - For LocalArtFormType updates</li>
 *     <li>{@link #TOPIC_LOCALARTFORMTYPE_DELETED} - For LocalArtFormType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class LocalArtFormTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_LOCALARTFORMTYPE_CREATED = "commons-blocks-profiles-service.localartformtype-created";
    public static final String TOPIC_LOCALARTFORMTYPE_UPDATED = "commons-blocks-profiles-service.localartformtype-updated";
    public static final String TOPIC_LOCALARTFORMTYPE_DELETED = "commons-blocks-profiles-service.localartformtype-deleted";

    private final KafkaTemplate<String,LocalArtFormTypeDTO> kafkaTemplate;

    /**
     * Constructs a new LocalArtFormTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public LocalArtFormTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the LocalArtFormType-created topic when a new LocalArtFormType is created.
     *
     * @param dto The LocalArtFormTypeDTO containing the created LocalArtFormType's information
     */
    public void created(LocalArtFormTypeDTO dto) {
        log.debug("Entry created(LocalArtFormType={})", dto);
        kafkaTemplate.send(TOPIC_LOCALARTFORMTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the LocalArtFormType-updated topic when a LocalArtFormType is modified.
     *
     * @param dto The LocalArtFormTypeDTO containing the updated LocalArtFormType's information
     */
    public void updated(LocalArtFormTypeDTO dto) {
        log.debug("Entry updated(LocalArtFormType={})", dto);
        kafkaTemplate.send(TOPIC_LOCALARTFORMTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the LocalArtFormType-deleted topic when a LocalArtFormType is removed.
     *
     * @param dto The LocalArtFormTypeDTO containing the deleted LocalArtFormType's information
     */
    public void deleted(LocalArtFormTypeDTO dto){
        log.debug("Entry deleted(LocalArtFormType={})", dto);
        kafkaTemplate.send(TOPIC_LOCALARTFORMTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
