package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageInstitutionalResourceProDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageInstitutionalResourcePro-related events. This component is responsible for
 * publishing VillageInstitutionalResourcePro events to designated Kafka topics when VillageInstitutionalResourcePros are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_CREATED} - For newly created VillageInstitutionalResourcePros</li>
 *     <li>{@link #TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_UPDATED} - For VillageInstitutionalResourcePro updates</li>
 *     <li>{@link #TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_DELETED} - For VillageInstitutionalResourcePro deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageInstitutionalResourceProProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_CREATED = "commons-blocks-profiles-service.villageinstitutionalresourcepro-created";
    public static final String TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_UPDATED = "commons-blocks-profiles-service.villageinstitutionalresourcepro-updated";
    public static final String TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_DELETED = "commons-blocks-profiles-service.villageinstitutionalresourcepro-deleted";

    private final KafkaTemplate<String,VillageInstitutionalResourceProDTO> kafkaTemplate;

    /**
     * Constructs a new VillageInstitutionalResourceProProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageInstitutionalResourceProProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageInstitutionalResourcePro-created topic when a new VillageInstitutionalResourcePro is created.
     *
     * @param dto The VillageInstitutionalResourceProDTO containing the created VillageInstitutionalResourcePro's information
     */
    public void created(VillageInstitutionalResourceProDTO dto) {
        log.debug("Entry created(VillageInstitutionalResourcePro={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageInstitutionalResourcePro-updated topic when a VillageInstitutionalResourcePro is modified.
     *
     * @param dto The VillageInstitutionalResourceProDTO containing the updated VillageInstitutionalResourcePro's information
     */
    public void updated(VillageInstitutionalResourceProDTO dto) {
        log.debug("Entry updated(VillageInstitutionalResourcePro={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageInstitutionalResourcePro-deleted topic when a VillageInstitutionalResourcePro is removed.
     *
     * @param dto The VillageInstitutionalResourceProDTO containing the deleted VillageInstitutionalResourcePro's information
     */
    public void deleted(VillageInstitutionalResourceProDTO dto){
        log.debug("Entry deleted(VillageInstitutionalResourcePro={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEINSTITUTIONALRESOURCEPRO_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
