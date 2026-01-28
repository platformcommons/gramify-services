package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialNicheProductBuyeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for ExportPotentialNicheProductBuye-related events. This component is responsible for
 * publishing ExportPotentialNicheProductBuye events to designated Kafka topics when ExportPotentialNicheProductBuyes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_CREATED} - For newly created ExportPotentialNicheProductBuyes</li>
 *     <li>{@link #TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_UPDATED} - For ExportPotentialNicheProductBuye updates</li>
 *     <li>{@link #TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_DELETED} - For ExportPotentialNicheProductBuye deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class ExportPotentialNicheProductBuyeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_CREATED = "commons-blocks-profiles-service.exportpotentialnicheproductbuye-created";
    public static final String TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_UPDATED = "commons-blocks-profiles-service.exportpotentialnicheproductbuye-updated";
    public static final String TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_DELETED = "commons-blocks-profiles-service.exportpotentialnicheproductbuye-deleted";

    private final KafkaTemplate<String,ExportPotentialNicheProductBuyeDTO> kafkaTemplate;

    /**
     * Constructs a new ExportPotentialNicheProductBuyeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public ExportPotentialNicheProductBuyeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the ExportPotentialNicheProductBuye-created topic when a new ExportPotentialNicheProductBuye is created.
     *
     * @param dto The ExportPotentialNicheProductBuyeDTO containing the created ExportPotentialNicheProductBuye's information
     */
    public void created(ExportPotentialNicheProductBuyeDTO dto) {
        log.debug("Entry created(ExportPotentialNicheProductBuye={})", dto);
        kafkaTemplate.send(TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the ExportPotentialNicheProductBuye-updated topic when a ExportPotentialNicheProductBuye is modified.
     *
     * @param dto The ExportPotentialNicheProductBuyeDTO containing the updated ExportPotentialNicheProductBuye's information
     */
    public void updated(ExportPotentialNicheProductBuyeDTO dto) {
        log.debug("Entry updated(ExportPotentialNicheProductBuye={})", dto);
        kafkaTemplate.send(TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the ExportPotentialNicheProductBuye-deleted topic when a ExportPotentialNicheProductBuye is removed.
     *
     * @param dto The ExportPotentialNicheProductBuyeDTO containing the deleted ExportPotentialNicheProductBuye's information
     */
    public void deleted(ExportPotentialNicheProductBuyeDTO dto){
        log.debug("Entry deleted(ExportPotentialNicheProductBuye={})", dto);
        kafkaTemplate.send(TOPIC_EXPORTPOTENTIALNICHEPRODUCTBUYE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
