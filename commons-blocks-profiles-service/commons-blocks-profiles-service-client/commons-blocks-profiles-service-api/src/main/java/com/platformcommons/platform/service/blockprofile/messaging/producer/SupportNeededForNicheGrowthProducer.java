package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.SupportNeededForNicheGrowthDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for SupportNeededForNicheGrowth-related events. This component is responsible for
 * publishing SupportNeededForNicheGrowth events to designated Kafka topics when SupportNeededForNicheGrowths are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_CREATED} - For newly created SupportNeededForNicheGrowths</li>
 *     <li>{@link #TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_UPDATED} - For SupportNeededForNicheGrowth updates</li>
 *     <li>{@link #TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_DELETED} - For SupportNeededForNicheGrowth deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class SupportNeededForNicheGrowthProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_CREATED = "commons-blocks-profiles-service.supportneededfornichegrowth-created";
    public static final String TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_UPDATED = "commons-blocks-profiles-service.supportneededfornichegrowth-updated";
    public static final String TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_DELETED = "commons-blocks-profiles-service.supportneededfornichegrowth-deleted";

    private final KafkaTemplate<String,SupportNeededForNicheGrowthDTO> kafkaTemplate;

    /**
     * Constructs a new SupportNeededForNicheGrowthProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public SupportNeededForNicheGrowthProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the SupportNeededForNicheGrowth-created topic when a new SupportNeededForNicheGrowth is created.
     *
     * @param dto The SupportNeededForNicheGrowthDTO containing the created SupportNeededForNicheGrowth's information
     */
    public void created(SupportNeededForNicheGrowthDTO dto) {
        log.debug("Entry created(SupportNeededForNicheGrowth={})", dto);
        kafkaTemplate.send(TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the SupportNeededForNicheGrowth-updated topic when a SupportNeededForNicheGrowth is modified.
     *
     * @param dto The SupportNeededForNicheGrowthDTO containing the updated SupportNeededForNicheGrowth's information
     */
    public void updated(SupportNeededForNicheGrowthDTO dto) {
        log.debug("Entry updated(SupportNeededForNicheGrowth={})", dto);
        kafkaTemplate.send(TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the SupportNeededForNicheGrowth-deleted topic when a SupportNeededForNicheGrowth is removed.
     *
     * @param dto The SupportNeededForNicheGrowthDTO containing the deleted SupportNeededForNicheGrowth's information
     */
    public void deleted(SupportNeededForNicheGrowthDTO dto){
        log.debug("Entry deleted(SupportNeededForNicheGrowth={})", dto);
        kafkaTemplate.send(TOPIC_SUPPORTNEEDEDFORNICHEGROWTH_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
