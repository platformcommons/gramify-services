package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageInfrastructureConstraintDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageInfrastructureConstraint-related events. This component is responsible for
 * publishing VillageInfrastructureConstraint events to designated Kafka topics when VillageInfrastructureConstraints are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_CREATED} - For newly created VillageInfrastructureConstraints</li>
 *     <li>{@link #TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_UPDATED} - For VillageInfrastructureConstraint updates</li>
 *     <li>{@link #TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_DELETED} - For VillageInfrastructureConstraint deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageInfrastructureConstraintProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_CREATED = "commons-blocks-profiles-service.villageinfrastructureconstraint-created";
    public static final String TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_UPDATED = "commons-blocks-profiles-service.villageinfrastructureconstraint-updated";
    public static final String TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_DELETED = "commons-blocks-profiles-service.villageinfrastructureconstraint-deleted";

    private final KafkaTemplate<String,VillageInfrastructureConstraintDTO> kafkaTemplate;

    /**
     * Constructs a new VillageInfrastructureConstraintProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageInfrastructureConstraintProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageInfrastructureConstraint-created topic when a new VillageInfrastructureConstraint is created.
     *
     * @param dto The VillageInfrastructureConstraintDTO containing the created VillageInfrastructureConstraint's information
     */
    public void created(VillageInfrastructureConstraintDTO dto) {
        log.debug("Entry created(VillageInfrastructureConstraint={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageInfrastructureConstraint-updated topic when a VillageInfrastructureConstraint is modified.
     *
     * @param dto The VillageInfrastructureConstraintDTO containing the updated VillageInfrastructureConstraint's information
     */
    public void updated(VillageInfrastructureConstraintDTO dto) {
        log.debug("Entry updated(VillageInfrastructureConstraint={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageInfrastructureConstraint-deleted topic when a VillageInfrastructureConstraint is removed.
     *
     * @param dto The VillageInfrastructureConstraintDTO containing the deleted VillageInfrastructureConstraint's information
     */
    public void deleted(VillageInfrastructureConstraintDTO dto){
        log.debug("Entry deleted(VillageInfrastructureConstraint={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEINFRASTRUCTURECONSTRAINT_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
