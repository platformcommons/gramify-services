package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.SourceOfRawMaterialDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for SourceOfRawMaterial-related events. This component is responsible for
 * publishing SourceOfRawMaterial events to designated Kafka topics when SourceOfRawMaterials are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_SOURCEOFRAWMATERIAL_CREATED} - For newly created SourceOfRawMaterials</li>
 *     <li>{@link #TOPIC_SOURCEOFRAWMATERIAL_UPDATED} - For SourceOfRawMaterial updates</li>
 *     <li>{@link #TOPIC_SOURCEOFRAWMATERIAL_DELETED} - For SourceOfRawMaterial deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class SourceOfRawMaterialProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_SOURCEOFRAWMATERIAL_CREATED = "commons-blocks-profiles-service.sourceofrawmaterial-created";
    public static final String TOPIC_SOURCEOFRAWMATERIAL_UPDATED = "commons-blocks-profiles-service.sourceofrawmaterial-updated";
    public static final String TOPIC_SOURCEOFRAWMATERIAL_DELETED = "commons-blocks-profiles-service.sourceofrawmaterial-deleted";

    private final KafkaTemplate<String,SourceOfRawMaterialDTO> kafkaTemplate;

    /**
     * Constructs a new SourceOfRawMaterialProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public SourceOfRawMaterialProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the SourceOfRawMaterial-created topic when a new SourceOfRawMaterial is created.
     *
     * @param dto The SourceOfRawMaterialDTO containing the created SourceOfRawMaterial's information
     */
    public void created(SourceOfRawMaterialDTO dto) {
        log.debug("Entry created(SourceOfRawMaterial={})", dto);
        kafkaTemplate.send(TOPIC_SOURCEOFRAWMATERIAL_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the SourceOfRawMaterial-updated topic when a SourceOfRawMaterial is modified.
     *
     * @param dto The SourceOfRawMaterialDTO containing the updated SourceOfRawMaterial's information
     */
    public void updated(SourceOfRawMaterialDTO dto) {
        log.debug("Entry updated(SourceOfRawMaterial={})", dto);
        kafkaTemplate.send(TOPIC_SOURCEOFRAWMATERIAL_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the SourceOfRawMaterial-deleted topic when a SourceOfRawMaterial is removed.
     *
     * @param dto The SourceOfRawMaterialDTO containing the deleted SourceOfRawMaterial's information
     */
    public void deleted(SourceOfRawMaterialDTO dto){
        log.debug("Entry deleted(SourceOfRawMaterial={})", dto);
        kafkaTemplate.send(TOPIC_SOURCEOFRAWMATERIAL_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
