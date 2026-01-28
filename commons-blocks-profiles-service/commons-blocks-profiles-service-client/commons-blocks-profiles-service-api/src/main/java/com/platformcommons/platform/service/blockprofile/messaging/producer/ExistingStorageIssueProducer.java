package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.ExistingStorageIssueDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for ExistingStorageIssue-related events. This component is responsible for
 * publishing ExistingStorageIssue events to designated Kafka topics when ExistingStorageIssues are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_EXISTINGSTORAGEISSUE_CREATED} - For newly created ExistingStorageIssues</li>
 *     <li>{@link #TOPIC_EXISTINGSTORAGEISSUE_UPDATED} - For ExistingStorageIssue updates</li>
 *     <li>{@link #TOPIC_EXISTINGSTORAGEISSUE_DELETED} - For ExistingStorageIssue deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class ExistingStorageIssueProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_EXISTINGSTORAGEISSUE_CREATED = "commons-blocks-profiles-service.existingstorageissue-created";
    public static final String TOPIC_EXISTINGSTORAGEISSUE_UPDATED = "commons-blocks-profiles-service.existingstorageissue-updated";
    public static final String TOPIC_EXISTINGSTORAGEISSUE_DELETED = "commons-blocks-profiles-service.existingstorageissue-deleted";

    private final KafkaTemplate<String,ExistingStorageIssueDTO> kafkaTemplate;

    /**
     * Constructs a new ExistingStorageIssueProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public ExistingStorageIssueProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the ExistingStorageIssue-created topic when a new ExistingStorageIssue is created.
     *
     * @param dto The ExistingStorageIssueDTO containing the created ExistingStorageIssue's information
     */
    public void created(ExistingStorageIssueDTO dto) {
        log.debug("Entry created(ExistingStorageIssue={})", dto);
        kafkaTemplate.send(TOPIC_EXISTINGSTORAGEISSUE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the ExistingStorageIssue-updated topic when a ExistingStorageIssue is modified.
     *
     * @param dto The ExistingStorageIssueDTO containing the updated ExistingStorageIssue's information
     */
    public void updated(ExistingStorageIssueDTO dto) {
        log.debug("Entry updated(ExistingStorageIssue={})", dto);
        kafkaTemplate.send(TOPIC_EXISTINGSTORAGEISSUE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the ExistingStorageIssue-deleted topic when a ExistingStorageIssue is removed.
     *
     * @param dto The ExistingStorageIssueDTO containing the deleted ExistingStorageIssue's information
     */
    public void deleted(ExistingStorageIssueDTO dto){
        log.debug("Entry deleted(ExistingStorageIssue={})", dto);
        kafkaTemplate.send(TOPIC_EXISTINGSTORAGEISSUE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
