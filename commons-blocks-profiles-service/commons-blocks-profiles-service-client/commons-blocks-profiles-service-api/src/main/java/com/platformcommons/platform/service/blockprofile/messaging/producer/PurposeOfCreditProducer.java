package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.PurposeOfCreditDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for PurposeOfCredit-related events. This component is responsible for
 * publishing PurposeOfCredit events to designated Kafka topics when PurposeOfCredits are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_PURPOSEOFCREDIT_CREATED} - For newly created PurposeOfCredits</li>
 *     <li>{@link #TOPIC_PURPOSEOFCREDIT_UPDATED} - For PurposeOfCredit updates</li>
 *     <li>{@link #TOPIC_PURPOSEOFCREDIT_DELETED} - For PurposeOfCredit deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class PurposeOfCreditProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_PURPOSEOFCREDIT_CREATED = "commons-blocks-profiles-service.purposeofcredit-created";
    public static final String TOPIC_PURPOSEOFCREDIT_UPDATED = "commons-blocks-profiles-service.purposeofcredit-updated";
    public static final String TOPIC_PURPOSEOFCREDIT_DELETED = "commons-blocks-profiles-service.purposeofcredit-deleted";

    private final KafkaTemplate<String,PurposeOfCreditDTO> kafkaTemplate;

    /**
     * Constructs a new PurposeOfCreditProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public PurposeOfCreditProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the PurposeOfCredit-created topic when a new PurposeOfCredit is created.
     *
     * @param dto The PurposeOfCreditDTO containing the created PurposeOfCredit's information
     */
    public void created(PurposeOfCreditDTO dto) {
        log.debug("Entry created(PurposeOfCredit={})", dto);
        kafkaTemplate.send(TOPIC_PURPOSEOFCREDIT_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the PurposeOfCredit-updated topic when a PurposeOfCredit is modified.
     *
     * @param dto The PurposeOfCreditDTO containing the updated PurposeOfCredit's information
     */
    public void updated(PurposeOfCreditDTO dto) {
        log.debug("Entry updated(PurposeOfCredit={})", dto);
        kafkaTemplate.send(TOPIC_PURPOSEOFCREDIT_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the PurposeOfCredit-deleted topic when a PurposeOfCredit is removed.
     *
     * @param dto The PurposeOfCreditDTO containing the deleted PurposeOfCredit's information
     */
    public void deleted(PurposeOfCreditDTO dto){
        log.debug("Entry deleted(PurposeOfCredit={})", dto);
        kafkaTemplate.send(TOPIC_PURPOSEOFCREDIT_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
