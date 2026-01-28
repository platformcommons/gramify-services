package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.search.application.service.GenericProductVarietyService;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductSKUService;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductService;
import com.platformcommons.platform.service.search.application.service.TMAChannelSolutionService;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.messaging.mapper.GenericProductVarietyEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Component
public class GenericProductVarietyRetagConsumer {

    @Autowired
    private GenericProductVarietyEventMapper genericProductVarietyEventMapper;

    @Autowired
    private GenericProductVarietyService genericProductVarietyService;

    @Autowired
    private TMAChannelSolutionService tmaChannelSolutionService;

    @Autowired
    private TMAChannelProductService tmaChannelProductService;

    @Autowired
    private TMAChannelProductSKUService tmaChannelProductSKUService;

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    @Transactional
    @KafkaListener(topics = MessagingConstant.GENERIC_PRODUCT_VARIETY_RETAGGED_FOR_SEARCH_SERVICE,
            groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void genericProductVarietyRetaggedForSearchService(@Payload EventDTO<GenericProductVarietyEventDTO> eventDTO) {
        EventDTO<GenericProductVarietyEventDTO> genericProductVarietyEventDTO = null;
        try {
            GenericProductVarietyEventDTO payload = eventDTO.getPayloadAndInitializeContext(new TypeReference<GenericProductVarietyEventDTO>() {});
            processGenericProductVarietyRetag(payload);

            genericProductVarietyEventDTO = EventDTO.<GenericProductVarietyEventDTO>builder()
                    .requestPayload(payload)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(eventDTO.getSuccessEventCode(), genericProductVarietyEventDTO);
            
        } catch (Exception e) {
            kafkaTemplate.send(eventDTO.getFailureEventEventCode(), genericProductVarietyEventDTO);
        }
    }

    private void processGenericProductVarietyRetag(GenericProductVarietyEventDTO payload) {
        if (payload.getGenericProductVarietyId() == null || payload.getMarketId() == null) {
            log.warn("Invalid payload - missing genericProductVarietyId or marketId: {}", payload);
            return;
        }

        Set<GenericProductVariety> existingGPVs = genericProductVarietyService
                .getByMarketIdAndGenericProductVarietyId(payload.getMarketId(), payload.getGenericProductVarietyId());

        if (existingGPVs != null && !existingGPVs.isEmpty()) {

            GenericProductVariety updatedGPV = genericProductVarietyEventMapper
                    .buildGenericProductVarietyForDefaultChannel(payload);
            existingGPVs.forEach(gpv -> gpv.updateBasicDetails(updatedGPV));
            genericProductVarietyService.saveAll(existingGPVs);

            // Update all related core documents
            updateRelatedCoreDocuments(updatedGPV);
            
            log.info("Successfully updated {} GPV entities and related documents for GPV ID: {}, Market ID: {}", 
                    existingGPVs.size(), payload.getGenericProductVarietyId(), payload.getMarketId());
        } else {
            log.warn("No existing GPV found for GPV ID: {}, Market ID: {}", 
                    payload.getGenericProductVarietyId(), payload.getMarketId());
        }
    }


    private void updateRelatedCoreDocuments(GenericProductVariety updatedGPV) {
        try {
            // Update TMAChannelSolution documents
            tmaChannelSolutionService.updateGenericProductVarietyDetails(updatedGPV);
            log.debug("Updated TMAChannelSolution documents for GPV ID: {}", updatedGPV.getGenericProductVarietyId());
            
            // Update TMAChannelProduct documents  
            tmaChannelProductService.updateGenericProductVarietyDetails(updatedGPV);
            log.debug("Updated TMAChannelProduct documents for GPV ID: {}", updatedGPV.getGenericProductVarietyId());
            
            // Update TMAChannelProductSKU documents
            tmaChannelProductSKUService.updateGenericProductVarietyDetails(updatedGPV);
            log.debug("Updated TMAChannelProductSKU documents for GPV ID: {}", updatedGPV.getGenericProductVarietyId());
            
        } catch (Exception e) {
            log.error("Error updating related core documents for GPV ID: {}", updatedGPV.getGenericProductVarietyId(), e);
            throw e;
        }
    }
}