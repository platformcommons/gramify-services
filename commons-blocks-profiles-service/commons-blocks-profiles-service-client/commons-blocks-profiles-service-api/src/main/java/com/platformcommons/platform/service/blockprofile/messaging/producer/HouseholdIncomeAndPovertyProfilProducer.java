package com.platformcommons.platform.service.blockprofile.messaging.producer;


import com.platformcommons.platform.service.blockprofile.dto.HouseholdIncomeAndPovertyProfilDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Kafka message producer for HouseholdIncomeAndPovertyProfil-related events. This component is responsible for
 * publishing HouseholdIncomeAndPovertyProfil events to designated Kafka topics when HouseholdIncomeAndPovertyProfils are created, updated,
 * or deleted in the system.
 * <p>
 * The producer uses the following topics:
 * <ul>
 *     <li>{@link #TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_CREATED} - For newly created HouseholdIncomeAndPovertyProfils</li>
 *     <li>{@link #TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_UPDATED} - For HouseholdIncomeAndPovertyProfil updates</li>
 *     <li>{@link #TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_DELETED} - For HouseholdIncomeAndPovertyProfil deletions</li>
 * </ul>
 */
@Component
@Slf4j
public class HouseholdIncomeAndPovertyProfilProducer {

    // Kafka topic names extracted as public constants
    public static final String TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_CREATED = "commons-blocks-profiles-service.householdincomeandpovertyprofil-created";
    public static final String TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_UPDATED = "commons-blocks-profiles-service.householdincomeandpovertyprofil-updated";
    public static final String TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_DELETED = "commons-blocks-profiles-service.householdincomeandpovertyprofil-deleted";

    private final KafkaTemplate<String,HouseholdIncomeAndPovertyProfilDTO> kafkaTemplate;

    /**
     * Constructs a new HouseholdIncomeAndPovertyProfilProducer with the specified Kafka template.
     *
     * @param kafkaTemplate The Kafka template used for sending messages to Kafka topics
     */
    public HouseholdIncomeAndPovertyProfilProducer(@Qualifier("jsonMessageTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    /**
     * Publishes a message to the HouseholdIncomeAndPovertyProfil-created topic when a new HouseholdIncomeAndPovertyProfil is created.
     *
     * @param dto The HouseholdIncomeAndPovertyProfilDTO containing the created HouseholdIncomeAndPovertyProfil's information
     */
    public void created(HouseholdIncomeAndPovertyProfilDTO dto) {
        log.debug("Entry created(HouseholdIncomeAndPovertyProfil={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_CREATED, dto);
        log.debug("Exit created()");
    }

    /**
     * Publishes a message to the HouseholdIncomeAndPovertyProfil-updated topic when a HouseholdIncomeAndPovertyProfil is modified.
     *
     * @param dto The HouseholdIncomeAndPovertyProfilDTO containing the updated HouseholdIncomeAndPovertyProfil's information
     */
    public void updated(HouseholdIncomeAndPovertyProfilDTO dto) {
        log.debug("Entry updated(HouseholdIncomeAndPovertyProfil={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_UPDATED, dto);
        log.debug("Exit updated()");
    }

    /**
     * Publishes a message to the HouseholdIncomeAndPovertyProfil-deleted topic when a HouseholdIncomeAndPovertyProfil is removed.
     *
     * @param dto The HouseholdIncomeAndPovertyProfilDTO containing the deleted HouseholdIncomeAndPovertyProfil's information
     */
    public void deleted(HouseholdIncomeAndPovertyProfilDTO dto){
        log.debug("Entry deleted(HouseholdIncomeAndPovertyProfil={})", dto);
        kafkaTemplate.send(TOPIC_HOUSEHOLDINCOMEANDPOVERTYPROFIL_DELETED, dto);
        log.debug("Exit deleted()");
    }
}
