package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageDigitalConnectivityProfiDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageDigitalConnectivityProfi-related events. This component is responsible for
 * publishing VillageDigitalConnectivityProfi events to designated Kafka topics when VillageDigitalConnectivityProfis are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_CREATED} - For newly created VillageDigitalConnectivityProfis</li>
 *     <li>{@link #TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_UPDATED} - For VillageDigitalConnectivityProfi updates</li>
 *     <li>{@link #TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_DELETED} - For VillageDigitalConnectivityProfi deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageDigitalConnectivityProfiProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_CREATED = "commons-blocks-profiles-service.villagedigitalconnectivityprofi-created";
    public static final String TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_UPDATED = "commons-blocks-profiles-service.villagedigitalconnectivityprofi-updated";
    public static final String TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_DELETED = "commons-blocks-profiles-service.villagedigitalconnectivityprofi-deleted";

    private final KafkaTemplate<String,VillageDigitalConnectivityProfiDTO> kafkaTemplate;

    /**
     * Constructs a new VillageDigitalConnectivityProfiProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageDigitalConnectivityProfiProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageDigitalConnectivityProfi-created topic when a new VillageDigitalConnectivityProfi is created.
     *
     * @param dto The VillageDigitalConnectivityProfiDTO containing the created VillageDigitalConnectivityProfi's information
     */
    public void created(VillageDigitalConnectivityProfiDTO dto) {
        log.debug("Entry created(VillageDigitalConnectivityProfi={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageDigitalConnectivityProfi-updated topic when a VillageDigitalConnectivityProfi is modified.
     *
     * @param dto The VillageDigitalConnectivityProfiDTO containing the updated VillageDigitalConnectivityProfi's information
     */
    public void updated(VillageDigitalConnectivityProfiDTO dto) {
        log.debug("Entry updated(VillageDigitalConnectivityProfi={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageDigitalConnectivityProfi-deleted topic when a VillageDigitalConnectivityProfi is removed.
     *
     * @param dto The VillageDigitalConnectivityProfiDTO containing the deleted VillageDigitalConnectivityProfi's information
     */
    public void deleted(VillageDigitalConnectivityProfiDTO dto){
        log.debug("Entry deleted(VillageDigitalConnectivityProfi={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEDIGITALCONNECTIVITYPROFI_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
