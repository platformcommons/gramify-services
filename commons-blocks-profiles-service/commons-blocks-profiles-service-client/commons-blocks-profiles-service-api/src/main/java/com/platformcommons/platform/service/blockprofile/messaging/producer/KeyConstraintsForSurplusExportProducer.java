package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.KeyConstraintsForSurplusExportDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for KeyConstraintsForSurplusExport-related events. This component is responsible for
 * publishing KeyConstraintsForSurplusExport events to designated Kafka topics when KeyConstraintsForSurplusExports are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_CREATED} - For newly created KeyConstraintsForSurplusExports</li>
 *     <li>{@link #TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_UPDATED} - For KeyConstraintsForSurplusExport updates</li>
 *     <li>{@link #TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_DELETED} - For KeyConstraintsForSurplusExport deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class KeyConstraintsForSurplusExportProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_CREATED = "commons-blocks-profiles-service.keyconstraintsforsurplusexport-created";
    public static final String TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_UPDATED = "commons-blocks-profiles-service.keyconstraintsforsurplusexport-updated";
    public static final String TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_DELETED = "commons-blocks-profiles-service.keyconstraintsforsurplusexport-deleted";

    private final KafkaTemplate<String,KeyConstraintsForSurplusExportDTO> kafkaTemplate;

    /**
     * Constructs a new KeyConstraintsForSurplusExportProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public KeyConstraintsForSurplusExportProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the KeyConstraintsForSurplusExport-created topic when a new KeyConstraintsForSurplusExport is created.
     *
     * @param dto The KeyConstraintsForSurplusExportDTO containing the created KeyConstraintsForSurplusExport's information
     */
    public void created(KeyConstraintsForSurplusExportDTO dto) {
        log.debug("Entry created(KeyConstraintsForSurplusExport={})", dto);
        kafkaTemplate.send(TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the KeyConstraintsForSurplusExport-updated topic when a KeyConstraintsForSurplusExport is modified.
     *
     * @param dto The KeyConstraintsForSurplusExportDTO containing the updated KeyConstraintsForSurplusExport's information
     */
    public void updated(KeyConstraintsForSurplusExportDTO dto) {
        log.debug("Entry updated(KeyConstraintsForSurplusExport={})", dto);
        kafkaTemplate.send(TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the KeyConstraintsForSurplusExport-deleted topic when a KeyConstraintsForSurplusExport is removed.
     *
     * @param dto The KeyConstraintsForSurplusExportDTO containing the deleted KeyConstraintsForSurplusExport's information
     */
    public void deleted(KeyConstraintsForSurplusExportDTO dto){
        log.debug("Entry deleted(KeyConstraintsForSurplusExport={})", dto);
        kafkaTemplate.send(TOPIC_KEYCONSTRAINTSFORSURPLUSEXPORT_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
