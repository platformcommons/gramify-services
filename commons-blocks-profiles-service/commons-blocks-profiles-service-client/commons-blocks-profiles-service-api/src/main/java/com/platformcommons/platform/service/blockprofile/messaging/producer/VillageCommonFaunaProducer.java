package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFaunaDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageCommonFauna-related events. This component is responsible for
 * publishing VillageCommonFauna events to designated Kafka topics when VillageCommonFaunas are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGECOMMONFAUNA_CREATED} - For newly created VillageCommonFaunas</li>
 *     <li>{@link #TOPIC_VILLAGECOMMONFAUNA_UPDATED} - For VillageCommonFauna updates</li>
 *     <li>{@link #TOPIC_VILLAGECOMMONFAUNA_DELETED} - For VillageCommonFauna deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageCommonFaunaProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGECOMMONFAUNA_CREATED = "commons-blocks-profiles-service.villagecommonfauna-created";
    public static final String TOPIC_VILLAGECOMMONFAUNA_UPDATED = "commons-blocks-profiles-service.villagecommonfauna-updated";
    public static final String TOPIC_VILLAGECOMMONFAUNA_DELETED = "commons-blocks-profiles-service.villagecommonfauna-deleted";

    private final KafkaTemplate<String,VillageCommonFaunaDTO> kafkaTemplate;

    /**
     * Constructs a new VillageCommonFaunaProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageCommonFaunaProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageCommonFauna-created topic when a new VillageCommonFauna is created.
     *
     * @param dto The VillageCommonFaunaDTO containing the created VillageCommonFauna's information
     */
    public void created(VillageCommonFaunaDTO dto) {
        log.debug("Entry created(VillageCommonFauna={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONFAUNA_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageCommonFauna-updated topic when a VillageCommonFauna is modified.
     *
     * @param dto The VillageCommonFaunaDTO containing the updated VillageCommonFauna's information
     */
    public void updated(VillageCommonFaunaDTO dto) {
        log.debug("Entry updated(VillageCommonFauna={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONFAUNA_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageCommonFauna-deleted topic when a VillageCommonFauna is removed.
     *
     * @param dto The VillageCommonFaunaDTO containing the deleted VillageCommonFauna's information
     */
    public void deleted(VillageCommonFaunaDTO dto){
        log.debug("Entry deleted(VillageCommonFauna={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGECOMMONFAUNA_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
