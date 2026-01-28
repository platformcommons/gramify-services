package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.CommonRepairNeedDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for CommonRepairNeed-related events. This component is responsible for
 * publishing CommonRepairNeed events to designated Kafka topics when CommonRepairNeeds are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_COMMONREPAIRNEED_CREATED} - For newly created CommonRepairNeeds</li>
 *     <li>{@link #TOPIC_COMMONREPAIRNEED_UPDATED} - For CommonRepairNeed updates</li>
 *     <li>{@link #TOPIC_COMMONREPAIRNEED_DELETED} - For CommonRepairNeed deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class CommonRepairNeedProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_COMMONREPAIRNEED_CREATED = "commons-blocks-profiles-service.commonrepairneed-created";
    public static final String TOPIC_COMMONREPAIRNEED_UPDATED = "commons-blocks-profiles-service.commonrepairneed-updated";
    public static final String TOPIC_COMMONREPAIRNEED_DELETED = "commons-blocks-profiles-service.commonrepairneed-deleted";

    private final KafkaTemplate<String,CommonRepairNeedDTO> kafkaTemplate;

    /**
     * Constructs a new CommonRepairNeedProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public CommonRepairNeedProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the CommonRepairNeed-created topic when a new CommonRepairNeed is created.
     *
     * @param dto The CommonRepairNeedDTO containing the created CommonRepairNeed's information
     */
    public void created(CommonRepairNeedDTO dto) {
        log.debug("Entry created(CommonRepairNeed={})", dto);
        kafkaTemplate.send(TOPIC_COMMONREPAIRNEED_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the CommonRepairNeed-updated topic when a CommonRepairNeed is modified.
     *
     * @param dto The CommonRepairNeedDTO containing the updated CommonRepairNeed's information
     */
    public void updated(CommonRepairNeedDTO dto) {
        log.debug("Entry updated(CommonRepairNeed={})", dto);
        kafkaTemplate.send(TOPIC_COMMONREPAIRNEED_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the CommonRepairNeed-deleted topic when a CommonRepairNeed is removed.
     *
     * @param dto The CommonRepairNeedDTO containing the deleted CommonRepairNeed's information
     */
    public void deleted(CommonRepairNeedDTO dto){
        log.debug("Entry deleted(CommonRepairNeed={})", dto);
        kafkaTemplate.send(TOPIC_COMMONREPAIRNEED_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
