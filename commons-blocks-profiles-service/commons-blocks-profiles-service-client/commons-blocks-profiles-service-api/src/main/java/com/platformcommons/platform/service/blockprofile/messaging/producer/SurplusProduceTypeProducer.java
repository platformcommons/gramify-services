package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.SurplusProduceTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for SurplusProduceType-related events. This component is responsible for
 * publishing SurplusProduceType events to designated Kafka topics when SurplusProduceTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_SURPLUSPRODUCETYPE_CREATED} - For newly created SurplusProduceTypes</li>
 *     <li>{@link #TOPIC_SURPLUSPRODUCETYPE_UPDATED} - For SurplusProduceType updates</li>
 *     <li>{@link #TOPIC_SURPLUSPRODUCETYPE_DELETED} - For SurplusProduceType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class SurplusProduceTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_SURPLUSPRODUCETYPE_CREATED = "commons-blocks-profiles-service.surplusproducetype-created";
    public static final String TOPIC_SURPLUSPRODUCETYPE_UPDATED = "commons-blocks-profiles-service.surplusproducetype-updated";
    public static final String TOPIC_SURPLUSPRODUCETYPE_DELETED = "commons-blocks-profiles-service.surplusproducetype-deleted";

    private final KafkaTemplate<String,SurplusProduceTypeDTO> kafkaTemplate;

    /**
     * Constructs a new SurplusProduceTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public SurplusProduceTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the SurplusProduceType-created topic when a new SurplusProduceType is created.
     *
     * @param dto The SurplusProduceTypeDTO containing the created SurplusProduceType's information
     */
    public void created(SurplusProduceTypeDTO dto) {
        log.debug("Entry created(SurplusProduceType={})", dto);
        kafkaTemplate.send(TOPIC_SURPLUSPRODUCETYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the SurplusProduceType-updated topic when a SurplusProduceType is modified.
     *
     * @param dto The SurplusProduceTypeDTO containing the updated SurplusProduceType's information
     */
    public void updated(SurplusProduceTypeDTO dto) {
        log.debug("Entry updated(SurplusProduceType={})", dto);
        kafkaTemplate.send(TOPIC_SURPLUSPRODUCETYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the SurplusProduceType-deleted topic when a SurplusProduceType is removed.
     *
     * @param dto The SurplusProduceTypeDTO containing the deleted SurplusProduceType's information
     */
    public void deleted(SurplusProduceTypeDTO dto){
        log.debug("Entry deleted(SurplusProduceType={})", dto);
        kafkaTemplate.send(TOPIC_SURPLUSPRODUCETYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
