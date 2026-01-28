package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.RawMaterialsNeededForIndustryDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for RawMaterialsNeededForIndustry-related events. This component is responsible for
 * publishing RawMaterialsNeededForIndustry events to designated Kafka topics when RawMaterialsNeededForIndustrys are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_CREATED} - For newly created RawMaterialsNeededForIndustrys</li>
 *     <li>{@link #TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_UPDATED} - For RawMaterialsNeededForIndustry updates</li>
 *     <li>{@link #TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_DELETED} - For RawMaterialsNeededForIndustry deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class RawMaterialsNeededForIndustryProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_CREATED = "commons-blocks-profiles-service.rawmaterialsneededforindustry-created";
    public static final String TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_UPDATED = "commons-blocks-profiles-service.rawmaterialsneededforindustry-updated";
    public static final String TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_DELETED = "commons-blocks-profiles-service.rawmaterialsneededforindustry-deleted";

    private final KafkaTemplate<String,RawMaterialsNeededForIndustryDTO> kafkaTemplate;

    /**
     * Constructs a new RawMaterialsNeededForIndustryProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public RawMaterialsNeededForIndustryProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the RawMaterialsNeededForIndustry-created topic when a new RawMaterialsNeededForIndustry is created.
     *
     * @param dto The RawMaterialsNeededForIndustryDTO containing the created RawMaterialsNeededForIndustry's information
     */
    public void created(RawMaterialsNeededForIndustryDTO dto) {
        log.debug("Entry created(RawMaterialsNeededForIndustry={})", dto);
        kafkaTemplate.send(TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the RawMaterialsNeededForIndustry-updated topic when a RawMaterialsNeededForIndustry is modified.
     *
     * @param dto The RawMaterialsNeededForIndustryDTO containing the updated RawMaterialsNeededForIndustry's information
     */
    public void updated(RawMaterialsNeededForIndustryDTO dto) {
        log.debug("Entry updated(RawMaterialsNeededForIndustry={})", dto);
        kafkaTemplate.send(TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the RawMaterialsNeededForIndustry-deleted topic when a RawMaterialsNeededForIndustry is removed.
     *
     * @param dto The RawMaterialsNeededForIndustryDTO containing the deleted RawMaterialsNeededForIndustry's information
     */
    public void deleted(RawMaterialsNeededForIndustryDTO dto){
        log.debug("Entry deleted(RawMaterialsNeededForIndustry={})", dto);
        kafkaTemplate.send(TOPIC_RAWMATERIALSNEEDEDFORINDUSTRY_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
