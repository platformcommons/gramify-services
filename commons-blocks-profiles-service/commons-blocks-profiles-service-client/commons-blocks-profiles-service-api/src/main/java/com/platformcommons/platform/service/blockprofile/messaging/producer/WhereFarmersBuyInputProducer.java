package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.WhereFarmersBuyInputDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for WhereFarmersBuyInput-related events. This component is responsible for
 * publishing WhereFarmersBuyInput events to designated Kafka topics when WhereFarmersBuyInputs are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_WHEREFARMERSBUYINPUT_CREATED} - For newly created WhereFarmersBuyInputs</li>
 *     <li>{@link #TOPIC_WHEREFARMERSBUYINPUT_UPDATED} - For WhereFarmersBuyInput updates</li>
 *     <li>{@link #TOPIC_WHEREFARMERSBUYINPUT_DELETED} - For WhereFarmersBuyInput deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class WhereFarmersBuyInputProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_WHEREFARMERSBUYINPUT_CREATED = "commons-blocks-profiles-service.wherefarmersbuyinput-created";
    public static final String TOPIC_WHEREFARMERSBUYINPUT_UPDATED = "commons-blocks-profiles-service.wherefarmersbuyinput-updated";
    public static final String TOPIC_WHEREFARMERSBUYINPUT_DELETED = "commons-blocks-profiles-service.wherefarmersbuyinput-deleted";

    private final KafkaTemplate<String,WhereFarmersBuyInputDTO> kafkaTemplate;

    /**
     * Constructs a new WhereFarmersBuyInputProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public WhereFarmersBuyInputProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the WhereFarmersBuyInput-created topic when a new WhereFarmersBuyInput is created.
     *
     * @param dto The WhereFarmersBuyInputDTO containing the created WhereFarmersBuyInput's information
     */
    public void created(WhereFarmersBuyInputDTO dto) {
        log.debug("Entry created(WhereFarmersBuyInput={})", dto);
        kafkaTemplate.send(TOPIC_WHEREFARMERSBUYINPUT_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the WhereFarmersBuyInput-updated topic when a WhereFarmersBuyInput is modified.
     *
     * @param dto The WhereFarmersBuyInputDTO containing the updated WhereFarmersBuyInput's information
     */
    public void updated(WhereFarmersBuyInputDTO dto) {
        log.debug("Entry updated(WhereFarmersBuyInput={})", dto);
        kafkaTemplate.send(TOPIC_WHEREFARMERSBUYINPUT_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the WhereFarmersBuyInput-deleted topic when a WhereFarmersBuyInput is removed.
     *
     * @param dto The WhereFarmersBuyInputDTO containing the deleted WhereFarmersBuyInput's information
     */
    public void deleted(WhereFarmersBuyInputDTO dto){
        log.debug("Entry deleted(WhereFarmersBuyInput={})", dto);
        kafkaTemplate.send(TOPIC_WHEREFARMERSBUYINPUT_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
