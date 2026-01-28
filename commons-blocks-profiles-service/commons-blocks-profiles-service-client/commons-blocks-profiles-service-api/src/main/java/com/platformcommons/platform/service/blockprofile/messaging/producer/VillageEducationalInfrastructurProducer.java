package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageEducationalInfrastructurDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageEducationalInfrastructur-related events. This component is responsible for
 * publishing VillageEducationalInfrastructur events to designated Kafka topics when VillageEducationalInfrastructurs are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_CREATED} - For newly created VillageEducationalInfrastructurs</li>
 *     <li>{@link #TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_UPDATED} - For VillageEducationalInfrastructur updates</li>
 *     <li>{@link #TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_DELETED} - For VillageEducationalInfrastructur deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageEducationalInfrastructurProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_CREATED = "commons-blocks-profiles-service.villageeducationalinfrastructur-created";
    public static final String TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_UPDATED = "commons-blocks-profiles-service.villageeducationalinfrastructur-updated";
    public static final String TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_DELETED = "commons-blocks-profiles-service.villageeducationalinfrastructur-deleted";

    private final KafkaTemplate<String,VillageEducationalInfrastructurDTO> kafkaTemplate;

    /**
     * Constructs a new VillageEducationalInfrastructurProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageEducationalInfrastructurProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageEducationalInfrastructur-created topic when a new VillageEducationalInfrastructur is created.
     *
     * @param dto The VillageEducationalInfrastructurDTO containing the created VillageEducationalInfrastructur's information
     */
    public void created(VillageEducationalInfrastructurDTO dto) {
        log.debug("Entry created(VillageEducationalInfrastructur={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageEducationalInfrastructur-updated topic when a VillageEducationalInfrastructur is modified.
     *
     * @param dto The VillageEducationalInfrastructurDTO containing the updated VillageEducationalInfrastructur's information
     */
    public void updated(VillageEducationalInfrastructurDTO dto) {
        log.debug("Entry updated(VillageEducationalInfrastructur={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageEducationalInfrastructur-deleted topic when a VillageEducationalInfrastructur is removed.
     *
     * @param dto The VillageEducationalInfrastructurDTO containing the deleted VillageEducationalInfrastructur's information
     */
    public void deleted(VillageEducationalInfrastructurDTO dto){
        log.debug("Entry deleted(VillageEducationalInfrastructur={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEEDUCATIONALINFRASTRUCTUR_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
