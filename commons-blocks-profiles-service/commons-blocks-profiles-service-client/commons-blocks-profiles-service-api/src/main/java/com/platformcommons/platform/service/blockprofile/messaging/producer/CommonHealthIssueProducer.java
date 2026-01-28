package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.CommonHealthIssueDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for CommonHealthIssue-related events. This component is responsible for
 * publishing CommonHealthIssue events to designated Kafka topics when CommonHealthIssues are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_COMMONHEALTHISSUE_CREATED} - For newly created CommonHealthIssues</li>
 *     <li>{@link #TOPIC_COMMONHEALTHISSUE_UPDATED} - For CommonHealthIssue updates</li>
 *     <li>{@link #TOPIC_COMMONHEALTHISSUE_DELETED} - For CommonHealthIssue deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class CommonHealthIssueProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_COMMONHEALTHISSUE_CREATED = "commons-blocks-profiles-service.commonhealthissue-created";
    public static final String TOPIC_COMMONHEALTHISSUE_UPDATED = "commons-blocks-profiles-service.commonhealthissue-updated";
    public static final String TOPIC_COMMONHEALTHISSUE_DELETED = "commons-blocks-profiles-service.commonhealthissue-deleted";

    private final KafkaTemplate<String,CommonHealthIssueDTO> kafkaTemplate;

    /**
     * Constructs a new CommonHealthIssueProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public CommonHealthIssueProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the CommonHealthIssue-created topic when a new CommonHealthIssue is created.
     *
     * @param dto The CommonHealthIssueDTO containing the created CommonHealthIssue's information
     */
    public void created(CommonHealthIssueDTO dto) {
        log.debug("Entry created(CommonHealthIssue={})", dto);
        kafkaTemplate.send(TOPIC_COMMONHEALTHISSUE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the CommonHealthIssue-updated topic when a CommonHealthIssue is modified.
     *
     * @param dto The CommonHealthIssueDTO containing the updated CommonHealthIssue's information
     */
    public void updated(CommonHealthIssueDTO dto) {
        log.debug("Entry updated(CommonHealthIssue={})", dto);
        kafkaTemplate.send(TOPIC_COMMONHEALTHISSUE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the CommonHealthIssue-deleted topic when a CommonHealthIssue is removed.
     *
     * @param dto The CommonHealthIssueDTO containing the deleted CommonHealthIssue's information
     */
    public void deleted(CommonHealthIssueDTO dto){
        log.debug("Entry deleted(CommonHealthIssue={})", dto);
        kafkaTemplate.send(TOPIC_COMMONHEALTHISSUE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
