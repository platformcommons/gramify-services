package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.OtherCommunityGroupDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for OtherCommunityGroup-related events. This component is responsible for
 * publishing OtherCommunityGroup events to designated Kafka topics when OtherCommunityGroups are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_OTHERCOMMUNITYGROUP_CREATED} - For newly created OtherCommunityGroups</li>
 *     <li>{@link #TOPIC_OTHERCOMMUNITYGROUP_UPDATED} - For OtherCommunityGroup updates</li>
 *     <li>{@link #TOPIC_OTHERCOMMUNITYGROUP_DELETED} - For OtherCommunityGroup deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class OtherCommunityGroupProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_OTHERCOMMUNITYGROUP_CREATED = "commons-blocks-profiles-service.othercommunitygroup-created";
    public static final String TOPIC_OTHERCOMMUNITYGROUP_UPDATED = "commons-blocks-profiles-service.othercommunitygroup-updated";
    public static final String TOPIC_OTHERCOMMUNITYGROUP_DELETED = "commons-blocks-profiles-service.othercommunitygroup-deleted";

    private final KafkaTemplate<String,OtherCommunityGroupDTO> kafkaTemplate;

    /**
     * Constructs a new OtherCommunityGroupProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public OtherCommunityGroupProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the OtherCommunityGroup-created topic when a new OtherCommunityGroup is created.
     *
     * @param dto The OtherCommunityGroupDTO containing the created OtherCommunityGroup's information
     */
    public void created(OtherCommunityGroupDTO dto) {
        log.debug("Entry created(OtherCommunityGroup={})", dto);
        kafkaTemplate.send(TOPIC_OTHERCOMMUNITYGROUP_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the OtherCommunityGroup-updated topic when a OtherCommunityGroup is modified.
     *
     * @param dto The OtherCommunityGroupDTO containing the updated OtherCommunityGroup's information
     */
    public void updated(OtherCommunityGroupDTO dto) {
        log.debug("Entry updated(OtherCommunityGroup={})", dto);
        kafkaTemplate.send(TOPIC_OTHERCOMMUNITYGROUP_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the OtherCommunityGroup-deleted topic when a OtherCommunityGroup is removed.
     *
     * @param dto The OtherCommunityGroupDTO containing the deleted OtherCommunityGroup's information
     */
    public void deleted(OtherCommunityGroupDTO dto){
        log.debug("Entry deleted(OtherCommunityGroup={})", dto);
        kafkaTemplate.send(TOPIC_OTHERCOMMUNITYGROUP_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
