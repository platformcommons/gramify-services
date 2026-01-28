package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageCommunicationInfrastructDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageCommunicationInfrastruct-related events. This component is responsible for
 * publishing VillageCommunicationInfrastruct events to designated Kafka topics when VillageCommunicationInfrastructs are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_CREATED} - For newly created VillageCommunicationInfrastructs</li>
 *     <li>{@link #TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_UPDATED} - For VillageCommunicationInfrastruct updates</li>
 *     <li>{@link #TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_DELETED} - For VillageCommunicationInfrastruct deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageCommunicationInfrastructProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_CREATED = "commons-blocks-profiles-service.villagecommunicationinfrastruct-created";
    public static final String TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_UPDATED = "commons-blocks-profiles-service.villagecommunicationinfrastruct-updated";
    public static final String TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_DELETED = "commons-blocks-profiles-service.villagecommunicationinfrastruct-deleted";

    private final KafkaTemplate<String,VillageCommunicationInfrastructDTO> kafkaTemplate;

    /**
     * Constructs a new VillageCommunicationInfrastructProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageCommunicationInfrastructProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageCommunicationInfrastruct-created topic when a new VillageCommunicationInfrastruct is created.
     *
     * @param dto The VillageCommunicationInfrastructDTO containing the created VillageCommunicationInfrastruct's information
     */
    public void created(VillageCommunicationInfrastructDTO dto) {
        log.debug("Entry created(VillageCommunicationInfrastruct={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageCommunicationInfrastruct-updated topic when a VillageCommunicationInfrastruct is modified.
     *
     * @param dto The VillageCommunicationInfrastructDTO containing the updated VillageCommunicationInfrastruct's information
     */
    public void updated(VillageCommunicationInfrastructDTO dto) {
        log.debug("Entry updated(VillageCommunicationInfrastruct={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageCommunicationInfrastruct-deleted topic when a VillageCommunicationInfrastruct is removed.
     *
     * @param dto The VillageCommunicationInfrastructDTO containing the deleted VillageCommunicationInfrastruct's information
     */
    public void deleted(VillageCommunicationInfrastructDTO dto){
        log.debug("Entry deleted(VillageCommunicationInfrastruct={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMUNICATIONINFRASTRUCT_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
