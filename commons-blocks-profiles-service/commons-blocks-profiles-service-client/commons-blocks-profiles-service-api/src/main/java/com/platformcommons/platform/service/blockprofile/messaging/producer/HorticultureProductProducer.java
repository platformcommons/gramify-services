package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HorticultureProductDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HorticultureProduct-related events. This component is responsible for
 * publishing HorticultureProduct events to designated Kafka topics when HorticultureProducts are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HORTICULTUREPRODUCT_CREATED} - For newly created HorticultureProducts</li>
 *     <li>{@link #TOPIC_HORTICULTUREPRODUCT_UPDATED} - For HorticultureProduct updates</li>
 *     <li>{@link #TOPIC_HORTICULTUREPRODUCT_DELETED} - For HorticultureProduct deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HorticultureProductProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HORTICULTUREPRODUCT_CREATED = "commons-blocks-profiles-service.horticultureproduct-created";
    public static final String TOPIC_HORTICULTUREPRODUCT_UPDATED = "commons-blocks-profiles-service.horticultureproduct-updated";
    public static final String TOPIC_HORTICULTUREPRODUCT_DELETED = "commons-blocks-profiles-service.horticultureproduct-deleted";

    private final KafkaTemplate<String,HorticultureProductDTO> kafkaTemplate;

    /**
     * Constructs a new HorticultureProductProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HorticultureProductProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HorticultureProduct-created topic when a new HorticultureProduct is created.
     *
     * @param dto The HorticultureProductDTO containing the created HorticultureProduct's information
     */
    public void created(HorticultureProductDTO dto) {
        log.debug("Entry created(HorticultureProduct={})", dto);
        kafkaTemplate.send(TOPIC_HORTICULTUREPRODUCT_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HorticultureProduct-updated topic when a HorticultureProduct is modified.
     *
     * @param dto The HorticultureProductDTO containing the updated HorticultureProduct's information
     */
    public void updated(HorticultureProductDTO dto) {
        log.debug("Entry updated(HorticultureProduct={})", dto);
        kafkaTemplate.send(TOPIC_HORTICULTUREPRODUCT_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HorticultureProduct-deleted topic when a HorticultureProduct is removed.
     *
     * @param dto The HorticultureProductDTO containing the deleted HorticultureProduct's information
     */
    public void deleted(HorticultureProductDTO dto){
        log.debug("Entry deleted(HorticultureProduct={})", dto);
        kafkaTemplate.send(TOPIC_HORTICULTUREPRODUCT_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
