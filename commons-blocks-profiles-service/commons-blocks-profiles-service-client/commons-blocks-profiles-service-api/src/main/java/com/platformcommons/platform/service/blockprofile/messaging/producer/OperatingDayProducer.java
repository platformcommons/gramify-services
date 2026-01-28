package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.OperatingDayDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for OperatingDay-related events. This component is responsible for
 * publishing OperatingDay events to designated Kafka topics when OperatingDays are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_OPERATINGDAY_CREATED} - For newly created OperatingDays</li>
 *     <li>{@link #TOPIC_OPERATINGDAY_UPDATED} - For OperatingDay updates</li>
 *     <li>{@link #TOPIC_OPERATINGDAY_DELETED} - For OperatingDay deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class OperatingDayProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_OPERATINGDAY_CREATED = "commons-blocks-profiles-service.operatingday-created";
    public static final String TOPIC_OPERATINGDAY_UPDATED = "commons-blocks-profiles-service.operatingday-updated";
    public static final String TOPIC_OPERATINGDAY_DELETED = "commons-blocks-profiles-service.operatingday-deleted";

    private final KafkaTemplate<String,OperatingDayDTO> kafkaTemplate;

    /**
     * Constructs a new OperatingDayProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public OperatingDayProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the OperatingDay-created topic when a new OperatingDay is created.
     *
     * @param dto The OperatingDayDTO containing the created OperatingDay's information
     */
    public void created(OperatingDayDTO dto) {
        log.debug("Entry created(OperatingDay={})", dto);
        kafkaTemplate.send(TOPIC_OPERATINGDAY_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the OperatingDay-updated topic when a OperatingDay is modified.
     *
     * @param dto The OperatingDayDTO containing the updated OperatingDay's information
     */
    public void updated(OperatingDayDTO dto) {
        log.debug("Entry updated(OperatingDay={})", dto);
        kafkaTemplate.send(TOPIC_OPERATINGDAY_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the OperatingDay-deleted topic when a OperatingDay is removed.
     *
     * @param dto The OperatingDayDTO containing the deleted OperatingDay's information
     */
    public void deleted(OperatingDayDTO dto){
        log.debug("Entry deleted(OperatingDay={})", dto);
        kafkaTemplate.send(TOPIC_OPERATINGDAY_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
