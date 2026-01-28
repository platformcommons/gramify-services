package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.WherePeopleGoForRepairsDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for WherePeopleGoForRepairs-related events. This component is responsible for
 * publishing WherePeopleGoForRepairs events to designated Kafka topics when WherePeopleGoForRepairss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_WHEREPEOPLEGOFORREPAIRS_CREATED} - For newly created WherePeopleGoForRepairss</li>
 *     <li>{@link #TOPIC_WHEREPEOPLEGOFORREPAIRS_UPDATED} - For WherePeopleGoForRepairs updates</li>
 *     <li>{@link #TOPIC_WHEREPEOPLEGOFORREPAIRS_DELETED} - For WherePeopleGoForRepairs deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class WherePeopleGoForRepairsProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_WHEREPEOPLEGOFORREPAIRS_CREATED = "commons-blocks-profiles-service.wherepeoplegoforrepairs-created";
    public static final String TOPIC_WHEREPEOPLEGOFORREPAIRS_UPDATED = "commons-blocks-profiles-service.wherepeoplegoforrepairs-updated";
    public static final String TOPIC_WHEREPEOPLEGOFORREPAIRS_DELETED = "commons-blocks-profiles-service.wherepeoplegoforrepairs-deleted";

    private final KafkaTemplate<String,WherePeopleGoForRepairsDTO> kafkaTemplate;

    /**
     * Constructs a new WherePeopleGoForRepairsProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public WherePeopleGoForRepairsProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the WherePeopleGoForRepairs-created topic when a new WherePeopleGoForRepairs is created.
     *
     * @param dto The WherePeopleGoForRepairsDTO containing the created WherePeopleGoForRepairs's information
     */
    public void created(WherePeopleGoForRepairsDTO dto) {
        log.debug("Entry created(WherePeopleGoForRepairs={})", dto);
        kafkaTemplate.send(TOPIC_WHEREPEOPLEGOFORREPAIRS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the WherePeopleGoForRepairs-updated topic when a WherePeopleGoForRepairs is modified.
     *
     * @param dto The WherePeopleGoForRepairsDTO containing the updated WherePeopleGoForRepairs's information
     */
    public void updated(WherePeopleGoForRepairsDTO dto) {
        log.debug("Entry updated(WherePeopleGoForRepairs={})", dto);
        kafkaTemplate.send(TOPIC_WHEREPEOPLEGOFORREPAIRS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the WherePeopleGoForRepairs-deleted topic when a WherePeopleGoForRepairs is removed.
     *
     * @param dto The WherePeopleGoForRepairsDTO containing the deleted WherePeopleGoForRepairs's information
     */
    public void deleted(WherePeopleGoForRepairsDTO dto){
        log.debug("Entry deleted(WherePeopleGoForRepairs={})", dto);
        kafkaTemplate.send(TOPIC_WHEREPEOPLEGOFORREPAIRS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
