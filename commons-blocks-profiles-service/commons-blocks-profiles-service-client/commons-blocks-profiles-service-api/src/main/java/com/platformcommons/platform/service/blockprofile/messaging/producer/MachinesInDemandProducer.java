package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.MachinesInDemandDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for MachinesInDemand-related events. This component is responsible for
 * publishing MachinesInDemand events to designated Kafka topics when MachinesInDemands are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_MACHINESINDEMAND_CREATED} - For newly created MachinesInDemands</li>
 *     <li>{@link #TOPIC_MACHINESINDEMAND_UPDATED} - For MachinesInDemand updates</li>
 *     <li>{@link #TOPIC_MACHINESINDEMAND_DELETED} - For MachinesInDemand deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class MachinesInDemandProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_MACHINESINDEMAND_CREATED = "commons-blocks-profiles-service.machinesindemand-created";
    public static final String TOPIC_MACHINESINDEMAND_UPDATED = "commons-blocks-profiles-service.machinesindemand-updated";
    public static final String TOPIC_MACHINESINDEMAND_DELETED = "commons-blocks-profiles-service.machinesindemand-deleted";

    private final KafkaTemplate<String,MachinesInDemandDTO> kafkaTemplate;

    /**
     * Constructs a new MachinesInDemandProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public MachinesInDemandProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the MachinesInDemand-created topic when a new MachinesInDemand is created.
     *
     * @param dto The MachinesInDemandDTO containing the created MachinesInDemand's information
     */
    public void created(MachinesInDemandDTO dto) {
        log.debug("Entry created(MachinesInDemand={})", dto);
        kafkaTemplate.send(TOPIC_MACHINESINDEMAND_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the MachinesInDemand-updated topic when a MachinesInDemand is modified.
     *
     * @param dto The MachinesInDemandDTO containing the updated MachinesInDemand's information
     */
    public void updated(MachinesInDemandDTO dto) {
        log.debug("Entry updated(MachinesInDemand={})", dto);
        kafkaTemplate.send(TOPIC_MACHINESINDEMAND_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the MachinesInDemand-deleted topic when a MachinesInDemand is removed.
     *
     * @param dto The MachinesInDemandDTO containing the deleted MachinesInDemand's information
     */
    public void deleted(MachinesInDemandDTO dto){
        log.debug("Entry deleted(MachinesInDemand={})", dto);
        kafkaTemplate.send(TOPIC_MACHINESINDEMAND_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
