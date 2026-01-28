package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.CommonConsumerGoodsPurchasedDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for CommonConsumerGoodsPurchased-related events. This component is responsible for
 * publishing CommonConsumerGoodsPurchased events to designated Kafka topics when CommonConsumerGoodsPurchaseds are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_COMMONCONSUMERGOODSPURCHASED_CREATED} - For newly created CommonConsumerGoodsPurchaseds</li>
 *     <li>{@link #TOPIC_COMMONCONSUMERGOODSPURCHASED_UPDATED} - For CommonConsumerGoodsPurchased updates</li>
 *     <li>{@link #TOPIC_COMMONCONSUMERGOODSPURCHASED_DELETED} - For CommonConsumerGoodsPurchased deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class CommonConsumerGoodsPurchasedProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_COMMONCONSUMERGOODSPURCHASED_CREATED = "commons-blocks-profiles-service.commonconsumergoodspurchased-created";
    public static final String TOPIC_COMMONCONSUMERGOODSPURCHASED_UPDATED = "commons-blocks-profiles-service.commonconsumergoodspurchased-updated";
    public static final String TOPIC_COMMONCONSUMERGOODSPURCHASED_DELETED = "commons-blocks-profiles-service.commonconsumergoodspurchased-deleted";

    private final KafkaTemplate<String,CommonConsumerGoodsPurchasedDTO> kafkaTemplate;

    /**
     * Constructs a new CommonConsumerGoodsPurchasedProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public CommonConsumerGoodsPurchasedProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the CommonConsumerGoodsPurchased-created topic when a new CommonConsumerGoodsPurchased is created.
     *
     * @param dto The CommonConsumerGoodsPurchasedDTO containing the created CommonConsumerGoodsPurchased's information
     */
    public void created(CommonConsumerGoodsPurchasedDTO dto) {
        log.debug("Entry created(CommonConsumerGoodsPurchased={})", dto);
        kafkaTemplate.send(TOPIC_COMMONCONSUMERGOODSPURCHASED_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the CommonConsumerGoodsPurchased-updated topic when a CommonConsumerGoodsPurchased is modified.
     *
     * @param dto The CommonConsumerGoodsPurchasedDTO containing the updated CommonConsumerGoodsPurchased's information
     */
    public void updated(CommonConsumerGoodsPurchasedDTO dto) {
        log.debug("Entry updated(CommonConsumerGoodsPurchased={})", dto);
        kafkaTemplate.send(TOPIC_COMMONCONSUMERGOODSPURCHASED_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the CommonConsumerGoodsPurchased-deleted topic when a CommonConsumerGoodsPurchased is removed.
     *
     * @param dto The CommonConsumerGoodsPurchasedDTO containing the deleted CommonConsumerGoodsPurchased's information
     */
    public void deleted(CommonConsumerGoodsPurchasedDTO dto){
        log.debug("Entry deleted(CommonConsumerGoodsPurchased={})", dto);
        kafkaTemplate.send(TOPIC_COMMONCONSUMERGOODSPURCHASED_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
