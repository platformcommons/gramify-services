package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.OtherIndustryTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for OtherIndustryType-related events. This component is responsible for
 * publishing OtherIndustryType events to designated Kafka topics when OtherIndustryTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_OTHERINDUSTRYTYPE_CREATED} - For newly created OtherIndustryTypes</li>
 *     <li>{@link #TOPIC_OTHERINDUSTRYTYPE_UPDATED} - For OtherIndustryType updates</li>
 *     <li>{@link #TOPIC_OTHERINDUSTRYTYPE_DELETED} - For OtherIndustryType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class OtherIndustryTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_OTHERINDUSTRYTYPE_CREATED = "commons-blocks-profiles-service.otherindustrytype-created";
    public static final String TOPIC_OTHERINDUSTRYTYPE_UPDATED = "commons-blocks-profiles-service.otherindustrytype-updated";
    public static final String TOPIC_OTHERINDUSTRYTYPE_DELETED = "commons-blocks-profiles-service.otherindustrytype-deleted";

    private final KafkaTemplate<String,OtherIndustryTypeDTO> kafkaTemplate;

    /**
     * Constructs a new OtherIndustryTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public OtherIndustryTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the OtherIndustryType-created topic when a new OtherIndustryType is created.
     *
     * @param dto The OtherIndustryTypeDTO containing the created OtherIndustryType's information
     */
    public void created(OtherIndustryTypeDTO dto) {
        log.debug("Entry created(OtherIndustryType={})", dto);
        kafkaTemplate.send(TOPIC_OTHERINDUSTRYTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the OtherIndustryType-updated topic when a OtherIndustryType is modified.
     *
     * @param dto The OtherIndustryTypeDTO containing the updated OtherIndustryType's information
     */
    public void updated(OtherIndustryTypeDTO dto) {
        log.debug("Entry updated(OtherIndustryType={})", dto);
        kafkaTemplate.send(TOPIC_OTHERINDUSTRYTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the OtherIndustryType-deleted topic when a OtherIndustryType is removed.
     *
     * @param dto The OtherIndustryTypeDTO containing the deleted OtherIndustryType's information
     */
    public void deleted(OtherIndustryTypeDTO dto){
        log.debug("Entry deleted(OtherIndustryType={})", dto);
        kafkaTemplate.send(TOPIC_OTHERINDUSTRYTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
