package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.VillageFinancialInstitutionProfDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for VillageFinancialInstitutionProf-related events. This component is responsible for
 * publishing VillageFinancialInstitutionProf events to designated Kafka topics when VillageFinancialInstitutionProfs are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_CREATED} - For newly created VillageFinancialInstitutionProfs</li>
 *     <li>{@link #TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_UPDATED} - For VillageFinancialInstitutionProf updates</li>
 *     <li>{@link #TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_DELETED} - For VillageFinancialInstitutionProf deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class VillageFinancialInstitutionProfProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_CREATED = "commons-blocks-profiles-service.villagefinancialinstitutionprof-created";
    public static final String TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_UPDATED = "commons-blocks-profiles-service.villagefinancialinstitutionprof-updated";
    public static final String TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_DELETED = "commons-blocks-profiles-service.villagefinancialinstitutionprof-deleted";

    private final KafkaTemplate<String,VillageFinancialInstitutionProfDTO> kafkaTemplate;

    /**
     * Constructs a new VillageFinancialInstitutionProfProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public VillageFinancialInstitutionProfProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the VillageFinancialInstitutionProf-created topic when a new VillageFinancialInstitutionProf is created.
     *
     * @param dto The VillageFinancialInstitutionProfDTO containing the created VillageFinancialInstitutionProf's information
     */
    public void created(VillageFinancialInstitutionProfDTO dto) {
        log.debug("Entry created(VillageFinancialInstitutionProf={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the VillageFinancialInstitutionProf-updated topic when a VillageFinancialInstitutionProf is modified.
     *
     * @param dto The VillageFinancialInstitutionProfDTO containing the updated VillageFinancialInstitutionProf's information
     */
    public void updated(VillageFinancialInstitutionProfDTO dto) {
        log.debug("Entry updated(VillageFinancialInstitutionProf={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the VillageFinancialInstitutionProf-deleted topic when a VillageFinancialInstitutionProf is removed.
     *
     * @param dto The VillageFinancialInstitutionProfDTO containing the deleted VillageFinancialInstitutionProf's information
     */
    public void deleted(VillageFinancialInstitutionProfDTO dto){
        log.debug("Entry deleted(VillageFinancialInstitutionProf={})", dto);
        kafkaTemplate.send(TOPIC_VILLAGEFINANCIALINSTITUTIONPROF_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
