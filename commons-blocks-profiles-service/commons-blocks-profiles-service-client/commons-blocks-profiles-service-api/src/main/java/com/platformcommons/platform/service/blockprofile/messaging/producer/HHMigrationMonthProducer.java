package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HHMigrationMonthDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HHMigrationMonth-related events. This component is responsible for
 * publishing HHMigrationMonth events to designated Kafka topics when HHMigrationMonths are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HHMIGRATIONMONTH_CREATED} - For newly created HHMigrationMonths</li>
 *     <li>{@link #TOPIC_HHMIGRATIONMONTH_UPDATED} - For HHMigrationMonth updates</li>
 *     <li>{@link #TOPIC_HHMIGRATIONMONTH_DELETED} - For HHMigrationMonth deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HHMigrationMonthProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HHMIGRATIONMONTH_CREATED = "commons-blocks-profiles-service.hhmigrationmonth-created";
    public static final String TOPIC_HHMIGRATIONMONTH_UPDATED = "commons-blocks-profiles-service.hhmigrationmonth-updated";
    public static final String TOPIC_HHMIGRATIONMONTH_DELETED = "commons-blocks-profiles-service.hhmigrationmonth-deleted";

    private final KafkaTemplate<String,HHMigrationMonthDTO> kafkaTemplate;

    /**
     * Constructs a new HHMigrationMonthProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HHMigrationMonthProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HHMigrationMonth-created topic when a new HHMigrationMonth is created.
     *
     * @param dto The HHMigrationMonthDTO containing the created HHMigrationMonth's information
     */
    public void created(HHMigrationMonthDTO dto) {
        log.debug("Entry created(HHMigrationMonth={})", dto);
        kafkaTemplate.send(TOPIC_HHMIGRATIONMONTH_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HHMigrationMonth-updated topic when a HHMigrationMonth is modified.
     *
     * @param dto The HHMigrationMonthDTO containing the updated HHMigrationMonth's information
     */
    public void updated(HHMigrationMonthDTO dto) {
        log.debug("Entry updated(HHMigrationMonth={})", dto);
        kafkaTemplate.send(TOPIC_HHMIGRATIONMONTH_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HHMigrationMonth-deleted topic when a HHMigrationMonth is removed.
     *
     * @param dto The HHMigrationMonthDTO containing the deleted HHMigrationMonth's information
     */
    public void deleted(HHMigrationMonthDTO dto){
        log.debug("Entry deleted(HHMigrationMonth={})", dto);
        kafkaTemplate.send(TOPIC_HHMIGRATIONMONTH_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
