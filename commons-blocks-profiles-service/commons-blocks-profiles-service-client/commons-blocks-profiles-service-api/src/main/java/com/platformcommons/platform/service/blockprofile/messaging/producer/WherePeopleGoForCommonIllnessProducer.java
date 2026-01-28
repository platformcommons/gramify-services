package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.WherePeopleGoForCommonIllnessDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for WherePeopleGoForCommonIllness-related events. This component is responsible for
 * publishing WherePeopleGoForCommonIllness events to designated Kafka topics when WherePeopleGoForCommonIllnesss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_CREATED} - For newly created WherePeopleGoForCommonIllnesss</li>
 *     <li>{@link #TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_UPDATED} - For WherePeopleGoForCommonIllness updates</li>
 *     <li>{@link #TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_DELETED} - For WherePeopleGoForCommonIllness deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class WherePeopleGoForCommonIllnessProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_CREATED = "commons-blocks-profiles-service.wherepeoplegoforcommonillness-created";
    public static final String TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_UPDATED = "commons-blocks-profiles-service.wherepeoplegoforcommonillness-updated";
    public static final String TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_DELETED = "commons-blocks-profiles-service.wherepeoplegoforcommonillness-deleted";

    private final KafkaTemplate<String,WherePeopleGoForCommonIllnessDTO> kafkaTemplate;

    /**
     * Constructs a new WherePeopleGoForCommonIllnessProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public WherePeopleGoForCommonIllnessProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the WherePeopleGoForCommonIllness-created topic when a new WherePeopleGoForCommonIllness is created.
     *
     * @param dto The WherePeopleGoForCommonIllnessDTO containing the created WherePeopleGoForCommonIllness's information
     */
    public void created(WherePeopleGoForCommonIllnessDTO dto) {
        log.debug("Entry created(WherePeopleGoForCommonIllness={})", dto);
        kafkaTemplate.send(TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the WherePeopleGoForCommonIllness-updated topic when a WherePeopleGoForCommonIllness is modified.
     *
     * @param dto The WherePeopleGoForCommonIllnessDTO containing the updated WherePeopleGoForCommonIllness's information
     */
    public void updated(WherePeopleGoForCommonIllnessDTO dto) {
        log.debug("Entry updated(WherePeopleGoForCommonIllness={})", dto);
        kafkaTemplate.send(TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the WherePeopleGoForCommonIllness-deleted topic when a WherePeopleGoForCommonIllness is removed.
     *
     * @param dto The WherePeopleGoForCommonIllnessDTO containing the deleted WherePeopleGoForCommonIllness's information
     */
    public void deleted(WherePeopleGoForCommonIllnessDTO dto){
        log.debug("Entry deleted(WherePeopleGoForCommonIllness={})", dto);
        kafkaTemplate.send(TOPIC_WHEREPEOPLEGOFORCOMMONILLNESS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
