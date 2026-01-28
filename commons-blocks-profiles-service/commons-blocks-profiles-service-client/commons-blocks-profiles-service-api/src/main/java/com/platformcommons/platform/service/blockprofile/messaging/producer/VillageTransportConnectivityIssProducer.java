package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageTransportConnectivityIssDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageTransportConnectivityIss-related events. This component is responsible for
 * publishing VillageTransportConnectivityIss events to designated Kafka topics when VillageTransportConnectivityIsss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_CREATED} - For newly created VillageTransportConnectivityIsss</li>
 *     <li>{@link #TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_UPDATED} - For VillageTransportConnectivityIss updates</li>
 *     <li>{@link #TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_DELETED} - For VillageTransportConnectivityIss deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageTransportConnectivityIssProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_CREATED = "commons-blocks-profiles-service.villagetransportconnectivityiss-created";
    public static final String TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_UPDATED = "commons-blocks-profiles-service.villagetransportconnectivityiss-updated";
    public static final String TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_DELETED = "commons-blocks-profiles-service.villagetransportconnectivityiss-deleted";

    private final KafkaTemplate<String,VillageTransportConnectivityIssDTO> kafkaTemplate;

    /**
     * Constructs a new VillageTransportConnectivityIssProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageTransportConnectivityIssProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageTransportConnectivityIss-created topic when a new VillageTransportConnectivityIss is created.
     *
     * @param dto The VillageTransportConnectivityIssDTO containing the created VillageTransportConnectivityIss's information
     */
    public void created(VillageTransportConnectivityIssDTO dto) {
        log.debug("Entry created(VillageTransportConnectivityIss={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageTransportConnectivityIss-updated topic when a VillageTransportConnectivityIss is modified.
     *
     * @param dto The VillageTransportConnectivityIssDTO containing the updated VillageTransportConnectivityIss's information
     */
    public void updated(VillageTransportConnectivityIssDTO dto) {
        log.debug("Entry updated(VillageTransportConnectivityIss={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageTransportConnectivityIss-deleted topic when a VillageTransportConnectivityIss is removed.
     *
     * @param dto The VillageTransportConnectivityIssDTO containing the deleted VillageTransportConnectivityIss's information
     */
    public void deleted(VillageTransportConnectivityIssDTO dto){
        log.debug("Entry deleted(VillageTransportConnectivityIss={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGETRANSPORTCONNECTIVITYISS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
