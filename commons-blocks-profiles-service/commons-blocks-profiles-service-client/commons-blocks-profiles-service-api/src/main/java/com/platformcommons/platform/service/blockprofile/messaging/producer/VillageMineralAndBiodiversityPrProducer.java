package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageMineralAndBiodiversityPrDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageMineralAndBiodiversityPr-related events. This component is responsible for
 * publishing VillageMineralAndBiodiversityPr events to designated Kafka topics when VillageMineralAndBiodiversityPrs are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_CREATED} - For newly created VillageMineralAndBiodiversityPrs</li>
 *     <li>{@link #TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_UPDATED} - For VillageMineralAndBiodiversityPr updates</li>
 *     <li>{@link #TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_DELETED} - For VillageMineralAndBiodiversityPr deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageMineralAndBiodiversityPrProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_CREATED = "commons-blocks-profiles-service.villagemineralandbiodiversitypr-created";
    public static final String TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_UPDATED = "commons-blocks-profiles-service.villagemineralandbiodiversitypr-updated";
    public static final String TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_DELETED = "commons-blocks-profiles-service.villagemineralandbiodiversitypr-deleted";

    private final KafkaTemplate<String,VillageMineralAndBiodiversityPrDTO> kafkaTemplate;

    /**
     * Constructs a new VillageMineralAndBiodiversityPrProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageMineralAndBiodiversityPrProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageMineralAndBiodiversityPr-created topic when a new VillageMineralAndBiodiversityPr is created.
     *
     * @param dto The VillageMineralAndBiodiversityPrDTO containing the created VillageMineralAndBiodiversityPr's information
     */
    public void created(VillageMineralAndBiodiversityPrDTO dto) {
        log.debug("Entry created(VillageMineralAndBiodiversityPr={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageMineralAndBiodiversityPr-updated topic when a VillageMineralAndBiodiversityPr is modified.
     *
     * @param dto The VillageMineralAndBiodiversityPrDTO containing the updated VillageMineralAndBiodiversityPr's information
     */
    public void updated(VillageMineralAndBiodiversityPrDTO dto) {
        log.debug("Entry updated(VillageMineralAndBiodiversityPr={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageMineralAndBiodiversityPr-deleted topic when a VillageMineralAndBiodiversityPr is removed.
     *
     * @param dto The VillageMineralAndBiodiversityPrDTO containing the deleted VillageMineralAndBiodiversityPr's information
     */
    public void deleted(VillageMineralAndBiodiversityPrDTO dto){
        log.debug("Entry deleted(VillageMineralAndBiodiversityPr={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEMINERALANDBIODIVERSITYPR_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
