package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageForestProduceTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageForestProduceType-related events. This component is responsible for
 * publishing VillageForestProduceType events to designated Kafka topics when VillageForestProduceTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEFORESTPRODUCETYPE_CREATED} - For newly created VillageForestProduceTypes</li>
 *     <li>{@link #TOPIC_VILLAGEFORESTPRODUCETYPE_UPDATED} - For VillageForestProduceType updates</li>
 *     <li>{@link #TOPIC_VILLAGEFORESTPRODUCETYPE_DELETED} - For VillageForestProduceType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageForestProduceTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEFORESTPRODUCETYPE_CREATED = "commons-blocks-profiles-service.villageforestproducetype-created";
    public static final String TOPIC_VILLAGEFORESTPRODUCETYPE_UPDATED = "commons-blocks-profiles-service.villageforestproducetype-updated";
    public static final String TOPIC_VILLAGEFORESTPRODUCETYPE_DELETED = "commons-blocks-profiles-service.villageforestproducetype-deleted";

    private final KafkaTemplate<String,VillageForestProduceTypeDTO> kafkaTemplate;

    /**
     * Constructs a new VillageForestProduceTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageForestProduceTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageForestProduceType-created topic when a new VillageForestProduceType is created.
     *
     * @param dto The VillageForestProduceTypeDTO containing the created VillageForestProduceType's information
     */
    public void created(VillageForestProduceTypeDTO dto) {
        log.debug("Entry created(VillageForestProduceType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFORESTPRODUCETYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageForestProduceType-updated topic when a VillageForestProduceType is modified.
     *
     * @param dto The VillageForestProduceTypeDTO containing the updated VillageForestProduceType's information
     */
    public void updated(VillageForestProduceTypeDTO dto) {
        log.debug("Entry updated(VillageForestProduceType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFORESTPRODUCETYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageForestProduceType-deleted topic when a VillageForestProduceType is removed.
     *
     * @param dto The VillageForestProduceTypeDTO containing the deleted VillageForestProduceType's information
     */
    public void deleted(VillageForestProduceTypeDTO dto){
        log.debug("Entry deleted(VillageForestProduceType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFORESTPRODUCETYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
