package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.NicheProductBuyerTypeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for NicheProductBuyerType-related events. This component is responsible for
 * publishing NicheProductBuyerType events to designated Kafka topics when NicheProductBuyerTypes are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_NICHEPRODUCTBUYERTYPE_CREATED} - For newly created NicheProductBuyerTypes</li>
 *     <li>{@link #TOPIC_NICHEPRODUCTBUYERTYPE_UPDATED} - For NicheProductBuyerType updates</li>
 *     <li>{@link #TOPIC_NICHEPRODUCTBUYERTYPE_DELETED} - For NicheProductBuyerType deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class NicheProductBuyerTypeProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_NICHEPRODUCTBUYERTYPE_CREATED = "commons-blocks-profiles-service.nicheproductbuyertype-created";
    public static final String TOPIC_NICHEPRODUCTBUYERTYPE_UPDATED = "commons-blocks-profiles-service.nicheproductbuyertype-updated";
    public static final String TOPIC_NICHEPRODUCTBUYERTYPE_DELETED = "commons-blocks-profiles-service.nicheproductbuyertype-deleted";

    private final KafkaTemplate<String,NicheProductBuyerTypeDTO> kafkaTemplate;

    /**
     * Constructs a new NicheProductBuyerTypeProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public NicheProductBuyerTypeProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the NicheProductBuyerType-created topic when a new NicheProductBuyerType is created.
     *
     * @param dto The NicheProductBuyerTypeDTO containing the created NicheProductBuyerType's information
     */
    public void created(NicheProductBuyerTypeDTO dto) {
        log.debug("Entry created(NicheProductBuyerType={})", dto);
        kafkaTemplate.send(TOPIC_NICHEPRODUCTBUYERTYPE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the NicheProductBuyerType-updated topic when a NicheProductBuyerType is modified.
     *
     * @param dto The NicheProductBuyerTypeDTO containing the updated NicheProductBuyerType's information
     */
    public void updated(NicheProductBuyerTypeDTO dto) {
        log.debug("Entry updated(NicheProductBuyerType={})", dto);
        kafkaTemplate.send(TOPIC_NICHEPRODUCTBUYERTYPE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the NicheProductBuyerType-deleted topic when a NicheProductBuyerType is removed.
     *
     * @param dto The NicheProductBuyerTypeDTO containing the deleted NicheProductBuyerType's information
     */
    public void deleted(NicheProductBuyerTypeDTO dto){
        log.debug("Entry deleted(NicheProductBuyerType={})", dto);
        kafkaTemplate.send(TOPIC_NICHEPRODUCTBUYERTYPE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
