package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.IssuesInHigherEducationAccessDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for IssuesInHigherEducationAccess-related events. This component is responsible for
 * publishing IssuesInHigherEducationAccess events to designated Kafka topics when IssuesInHigherEducationAccesss are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_ISSUESINHIGHEREDUCATIONACCESS_CREATED} - For newly created IssuesInHigherEducationAccesss</li>
 *     <li>{@link #TOPIC_ISSUESINHIGHEREDUCATIONACCESS_UPDATED} - For IssuesInHigherEducationAccess updates</li>
 *     <li>{@link #TOPIC_ISSUESINHIGHEREDUCATIONACCESS_DELETED} - For IssuesInHigherEducationAccess deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class IssuesInHigherEducationAccessProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_ISSUESINHIGHEREDUCATIONACCESS_CREATED = "commons-blocks-profiles-service.issuesinhighereducationaccess-created";
    public static final String TOPIC_ISSUESINHIGHEREDUCATIONACCESS_UPDATED = "commons-blocks-profiles-service.issuesinhighereducationaccess-updated";
    public static final String TOPIC_ISSUESINHIGHEREDUCATIONACCESS_DELETED = "commons-blocks-profiles-service.issuesinhighereducationaccess-deleted";

    private final KafkaTemplate<String,IssuesInHigherEducationAccessDTO> kafkaTemplate;

    /**
     * Constructs a new IssuesInHigherEducationAccessProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public IssuesInHigherEducationAccessProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the IssuesInHigherEducationAccess-created topic when a new IssuesInHigherEducationAccess is created.
     *
     * @param dto The IssuesInHigherEducationAccessDTO containing the created IssuesInHigherEducationAccess's information
     */
    public void created(IssuesInHigherEducationAccessDTO dto) {
        log.debug("Entry created(IssuesInHigherEducationAccess={})", dto);
        kafkaTemplate.send(TOPIC_ISSUESINHIGHEREDUCATIONACCESS_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the IssuesInHigherEducationAccess-updated topic when a IssuesInHigherEducationAccess is modified.
     *
     * @param dto The IssuesInHigherEducationAccessDTO containing the updated IssuesInHigherEducationAccess's information
     */
    public void updated(IssuesInHigherEducationAccessDTO dto) {
        log.debug("Entry updated(IssuesInHigherEducationAccess={})", dto);
        kafkaTemplate.send(TOPIC_ISSUESINHIGHEREDUCATIONACCESS_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the IssuesInHigherEducationAccess-deleted topic when a IssuesInHigherEducationAccess is removed.
     *
     * @param dto The IssuesInHigherEducationAccessDTO containing the deleted IssuesInHigherEducationAccess's information
     */
    public void deleted(IssuesInHigherEducationAccessDTO dto){
        log.debug("Entry deleted(IssuesInHigherEducationAccess={})", dto);
        kafkaTemplate.send(TOPIC_ISSUESINHIGHEREDUCATIONACCESS_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
