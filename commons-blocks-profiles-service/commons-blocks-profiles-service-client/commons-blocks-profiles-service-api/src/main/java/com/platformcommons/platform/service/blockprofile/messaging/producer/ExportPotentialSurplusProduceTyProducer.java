package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialSurplusProduceTyDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for ExportPotentialSurplusProduceTy-related events. This component is responsible for
 * publishing ExportPotentialSurplusProduceTy events to designated Kafka topics when ExportPotentialSurplusProduceTys are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_CREATED} - For newly created ExportPotentialSurplusProduceTys</li>
 *     <li>{@link #TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_UPDATED} - For ExportPotentialSurplusProduceTy updates</li>
 *     <li>{@link #TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_DELETED} - For ExportPotentialSurplusProduceTy deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class ExportPotentialSurplusProduceTyProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_CREATED = "commons-blocks-profiles-service.exportpotentialsurplusproducety-created";
    public static final String TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_UPDATED = "commons-blocks-profiles-service.exportpotentialsurplusproducety-updated";
    public static final String TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_DELETED = "commons-blocks-profiles-service.exportpotentialsurplusproducety-deleted";

    private final KafkaTemplate<String,ExportPotentialSurplusProduceTyDTO> kafkaTemplate;

    /**
     * Constructs a new ExportPotentialSurplusProduceTyProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public ExportPotentialSurplusProduceTyProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the ExportPotentialSurplusProduceTy-created topic when a new ExportPotentialSurplusProduceTy is created.
     *
     * @param dto The ExportPotentialSurplusProduceTyDTO containing the created ExportPotentialSurplusProduceTy's information
     */
    public void created(ExportPotentialSurplusProduceTyDTO dto) {
        log.debug("Entry created(ExportPotentialSurplusProduceTy={})", dto);
        kafkaTemplate.send(TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the ExportPotentialSurplusProduceTy-updated topic when a ExportPotentialSurplusProduceTy is modified.
     *
     * @param dto The ExportPotentialSurplusProduceTyDTO containing the updated ExportPotentialSurplusProduceTy's information
     */
    public void updated(ExportPotentialSurplusProduceTyDTO dto) {
        log.debug("Entry updated(ExportPotentialSurplusProduceTy={})", dto);
        kafkaTemplate.send(TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the ExportPotentialSurplusProduceTy-deleted topic when a ExportPotentialSurplusProduceTy is removed.
     *
     * @param dto The ExportPotentialSurplusProduceTyDTO containing the deleted ExportPotentialSurplusProduceTy's information
     */
    public void deleted(ExportPotentialSurplusProduceTyDTO dto){
        log.debug("Entry deleted(ExportPotentialSurplusProduceTy={})", dto);
        kafkaTemplate.send(TOPIC_EXPORTPOTENTIALSURPLUSPRODUCETY_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
