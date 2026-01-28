package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageSkillShortageTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageSkillShortageType-related events. This component is responsible for
 * publishing VillageSkillShortageType events to designated Kafka topics when VillageSkillShortageTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGESKILLSHORTAGETYPE_CREATED} - For newly created VillageSkillShortageTypes</li>
 *     <li>{@link #TOPIC_VILLAGESKILLSHORTAGETYPE_UPDATED} - For VillageSkillShortageType updates</li>
 *     <li>{@link #TOPIC_VILLAGESKILLSHORTAGETYPE_DELETED} - For VillageSkillShortageType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageSkillShortageTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGESKILLSHORTAGETYPE_CREATED = "commons-blocks-profiles-service.villageskillshortagetype-created";
    public static final String TOPIC_VILLAGESKILLSHORTAGETYPE_UPDATED = "commons-blocks-profiles-service.villageskillshortagetype-updated";
    public static final String TOPIC_VILLAGESKILLSHORTAGETYPE_DELETED = "commons-blocks-profiles-service.villageskillshortagetype-deleted";

    private final KafkaTemplate<String,VillageSkillShortageTypeDTO> kafkaTemplate;

    /**
     * Constructs a new VillageSkillShortageTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageSkillShortageTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageSkillShortageType-created topic when a new VillageSkillShortageType is created.
     *
     * @param dto The VillageSkillShortageTypeDTO containing the created VillageSkillShortageType's information
     */
    public void created(VillageSkillShortageTypeDTO dto) {
        log.debug("Entry created(VillageSkillShortageType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESKILLSHORTAGETYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageSkillShortageType-updated topic when a VillageSkillShortageType is modified.
     *
     * @param dto The VillageSkillShortageTypeDTO containing the updated VillageSkillShortageType's information
     */
    public void updated(VillageSkillShortageTypeDTO dto) {
        log.debug("Entry updated(VillageSkillShortageType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESKILLSHORTAGETYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageSkillShortageType-deleted topic when a VillageSkillShortageType is removed.
     *
     * @param dto The VillageSkillShortageTypeDTO containing the deleted VillageSkillShortageType's information
     */
    public void deleted(VillageSkillShortageTypeDTO dto){
        log.debug("Entry deleted(VillageSkillShortageType={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGESKILLSHORTAGETYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
