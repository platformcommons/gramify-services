package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.ItemsUsuallyBoughtFromOutsideDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for ItemsUsuallyBoughtFromOutside-related events. This component is responsible for
 * publishing ItemsUsuallyBoughtFromOutside events to designated Kafka topics when ItemsUsuallyBoughtFromOutsides are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_CREATED} - For newly created ItemsUsuallyBoughtFromOutsides</li>
 *     <li>{@link #TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_UPDATED} - For ItemsUsuallyBoughtFromOutside updates</li>
 *     <li>{@link #TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_DELETED} - For ItemsUsuallyBoughtFromOutside deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class ItemsUsuallyBoughtFromOutsideProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_CREATED = "commons-blocks-profiles-service.itemsusuallyboughtfromoutside-created";
    public static final String TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_UPDATED = "commons-blocks-profiles-service.itemsusuallyboughtfromoutside-updated";
    public static final String TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_DELETED = "commons-blocks-profiles-service.itemsusuallyboughtfromoutside-deleted";

    private final KafkaTemplate<String,ItemsUsuallyBoughtFromOutsideDTO> kafkaTemplate;

    /**
     * Constructs a new ItemsUsuallyBoughtFromOutsideProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public ItemsUsuallyBoughtFromOutsideProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the ItemsUsuallyBoughtFromOutside-created topic when a new ItemsUsuallyBoughtFromOutside is created.
     *
     * @param dto The ItemsUsuallyBoughtFromOutsideDTO containing the created ItemsUsuallyBoughtFromOutside's information
     */
    public void created(ItemsUsuallyBoughtFromOutsideDTO dto) {
        log.debug("Entry created(ItemsUsuallyBoughtFromOutside={})", dto);
        kafkaTemplate.send(TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the ItemsUsuallyBoughtFromOutside-updated topic when a ItemsUsuallyBoughtFromOutside is modified.
     *
     * @param dto The ItemsUsuallyBoughtFromOutsideDTO containing the updated ItemsUsuallyBoughtFromOutside's information
     */
    public void updated(ItemsUsuallyBoughtFromOutsideDTO dto) {
        log.debug("Entry updated(ItemsUsuallyBoughtFromOutside={})", dto);
        kafkaTemplate.send(TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the ItemsUsuallyBoughtFromOutside-deleted topic when a ItemsUsuallyBoughtFromOutside is removed.
     *
     * @param dto The ItemsUsuallyBoughtFromOutsideDTO containing the deleted ItemsUsuallyBoughtFromOutside's information
     */
    public void deleted(ItemsUsuallyBoughtFromOutsideDTO dto){
        log.debug("Entry deleted(ItemsUsuallyBoughtFromOutside={})", dto);
        kafkaTemplate.send(TOPIC_ITEMSUSUALLYBOUGHTFROMOUTSIDE_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
