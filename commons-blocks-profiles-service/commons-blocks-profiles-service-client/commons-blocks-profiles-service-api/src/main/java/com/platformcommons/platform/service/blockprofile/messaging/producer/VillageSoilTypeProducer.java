package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageSoilTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageSoilType-related events. This component is responsible for
 * publishing VillageSoilType events to designated Kafka topics when VillageSoilTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGESOILTYPE_CREATED} - For newly created VillageSoilTypes</li>
 *     <li>{@link #TOPIC_VILLAGESOILTYPE_UPDATED} - For VillageSoilType updates</li>
 *     <li>{@link #TOPIC_VILLAGESOILTYPE_DELETED} - For VillageSoilType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageSoilTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGESOILTYPE_CREATED = "commons-blocks-profiles-service.villagesoiltype-created";
    public static final String TOPIC_VILLAGESOILTYPE_UPDATED = "commons-blocks-profiles-service.villagesoiltype-updated";
    public static final String TOPIC_VILLAGESOILTYPE_DELETED = "commons-blocks-profiles-service.villagesoiltype-deleted";

    private final KafkaTemplate<String,VillageSoilTypeDTO> kafkaTemplate;

    /**
     * Constructs a new VillageSoilTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageSoilTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageSoilType-created topic when a new VillageSoilType is created.
     *
     * @param dto The VillageSoilTypeDTO containing the created VillageSoilType's information
     */
    public void created(VillageSoilTypeDTO dto) {
        log.debug("Entry created(VillageSoilType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESOILTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageSoilType-updated topic when a VillageSoilType is modified.
     *
     * @param dto The VillageSoilTypeDTO containing the updated VillageSoilType's information
     */
    public void updated(VillageSoilTypeDTO dto) {
        log.debug("Entry updated(VillageSoilType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESOILTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageSoilType-deleted topic when a VillageSoilType is removed.
     *
     * @param dto The VillageSoilTypeDTO containing the deleted VillageSoilType's information
     */
    public void deleted(VillageSoilTypeDTO dto){
        log.debug("Entry deleted(VillageSoilType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESOILTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
