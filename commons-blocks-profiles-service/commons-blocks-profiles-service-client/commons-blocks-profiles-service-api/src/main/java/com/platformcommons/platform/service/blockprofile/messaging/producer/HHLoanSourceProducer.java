package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHLoanSourceDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHLoanSource-related events. This component is responsible for
 * publishing HHLoanSource events to designated Kafka topics when HHLoanSources are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHLOANSOURCE_CREATED} - For newly created HHLoanSources</li>
 *     <li>{@link #TOPIC_HHLOANSOURCE_UPDATED} - For HHLoanSource updates</li>
 *     <li>{@link #TOPIC_HHLOANSOURCE_DELETED} - For HHLoanSource deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHLoanSourceProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHLOANSOURCE_CREATED = "commons-blocks-profiles-service.hhloansource-created";
    public static final String TOPIC_HHLOANSOURCE_UPDATED = "commons-blocks-profiles-service.hhloansource-updated";
    public static final String TOPIC_HHLOANSOURCE_DELETED = "commons-blocks-profiles-service.hhloansource-deleted";

    private final KafkaTemplate<String,HHLoanSourceDTO> kafkaTemplate;

    /**
     * Constructs a new HHLoanSourceProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHLoanSourceProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHLoanSource-created topic when a new HHLoanSource is created.
     *
     * @param dto The HHLoanSourceDTO containing the created HHLoanSource's information
     */
    public void created(HHLoanSourceDTO dto) {
        log.debug("Entry created(HHLoanSource={})", dto);
        kafkaTemplate.send(TOPIC_HHLOANSOURCE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHLoanSource-updated topic when a HHLoanSource is modified.
     *
     * @param dto The HHLoanSourceDTO containing the updated HHLoanSource's information
     */
    public void updated(HHLoanSourceDTO dto) {
        log.debug("Entry updated(HHLoanSource={})", dto);
        kafkaTemplate.send(TOPIC_HHLOANSOURCE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHLoanSource-deleted topic when a HHLoanSource is removed.
     *
     * @param dto The HHLoanSourceDTO containing the deleted HHLoanSource's information
     */
    public void deleted(HHLoanSourceDTO dto){
        log.debug("Entry deleted(HHLoanSource={})", dto);
        kafkaTemplate.send(TOPIC_HHLOANSOURCE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
