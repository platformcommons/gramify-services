package com.platformcommons.platform.service.domain.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.domain.dto.GenericProductVarietyTagEventDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.facade.TagFacade;
import com.platformcommons.platform.service.domain.messaging.constants.EventConstants;
import com.platformcommons.platform.service.domain.messaging.mapper.TagEventMapper;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenericProductVarietyConsumer {

    @Autowired
    private TagFacade tagFacade;

    @Autowired
    private TagEventMapper tagEventMapper;

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    @Transactional
    @KafkaListener(
            topics = {EventConstants.GENERIC_PRODUCT_VARIETY_TAG_CREATED_OR_UPDATED,EventConstants.GENERIC_PRODUCT_VARIETY_TAG_SYNCED},
            groupId = EventConstants.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY
    )
    public void genericProductVarietyCreatedAndUpdated(@Payload EventDTO<GenericProductVarietyTagEventDTO> eventDTO) {
        GenericProductVarietyTagEventDTO gpvEventDTO =
                eventDTO.getPayload(new TypeReference<GenericProductVarietyTagEventDTO>() {
                });
        PlatformSecurityUtil.setEventContext(eventDTO.getUserContext(),eventDTO.getTenantContext(),eventDTO.getActorContext(),eventDTO.getAppContext(),null);
        if(gpvEventDTO !=null && gpvEventDTO.getMarketCode() != null
                && gpvEventDTO.getGenericProductVarietyCode() != null  && gpvEventDTO.getTagIdentifier() != null){
            TagDTO tagDTO = tagEventMapper.buildTagFromGenericProductVariety(gpvEventDTO);
            TagDTO fetchedTagDTO = tagFacade.getTagByCodeV2(tagDTO.getCode());

            if (fetchedTagDTO == null) {
                Long id = tagFacade.save(tagDTO);
                TagDTO fetchedParentTagDTO = tagFacade.getTagByCodeV2("TAG."+gpvEventDTO.getGenericProductCode());
                if(fetchedParentTagDTO != null && fetchedParentTagDTO.getId() != null && id != null){
                    tagFacade.addTagHierarchyBatch(Collections.singletonList(id), fetchedParentTagDTO.getId());
                }
                log.info("Created new tag with id {}", id);
            } else {
                tagDTO.setId(fetchedTagDTO.getId());
                tagFacade.update(tagDTO);
            }

        }
    }

    @Transactional
    @KafkaListener(
            topics = {EventConstants.GENERIC_PRODUCT_VARIETY_TAG_DELETED},
            groupId = EventConstants.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY
    )
    public void genericProductVarietyDeleted(@Payload EventDTO<GenericProductVarietyTagEventDTO> eventDTO) {
        GenericProductVarietyTagEventDTO gpvEventDTO =
                eventDTO.getPayload(new TypeReference<GenericProductVarietyTagEventDTO>() {
                });
        PlatformSecurityUtil.setEventContext(eventDTO.getUserContext(),eventDTO.getTenantContext(),eventDTO.getActorContext(),eventDTO.getAppContext(),null);
        if(gpvEventDTO !=null && gpvEventDTO.getMarketCode() != null
                && gpvEventDTO.getGenericProductVarietyCode() != null  && gpvEventDTO.getTagIdentifier() != null){

            TagDTO gpvTagDTO = tagFacade.getTagByCodeV2(gpvEventDTO.getTagIdentifier());
            if (gpvTagDTO != null) {
                tagFacade.delete(gpvTagDTO.getId(), "deleted By Admin");

                TagDTO gpTagDTO = tagFacade.getTagByCodeV2("TAG." + gpvEventDTO.getGenericProductCode());

                if (gpTagDTO != null) {
                    tagFacade.deleteTagHierarchy(gpTagDTO.getId(), gpvTagDTO.getId());
                }
            }
        }
    }

    @Transactional
    @KafkaListener(
            topics = {EventConstants.GENERIC_PRODUCT_VARIETY_RETAGGED_FOR_DOMAIN_SERVICE},
            groupId = EventConstants.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY
    )
    public void genericProductVarietyRetagged(@Payload EventDTO<GenericProductVarietyTagEventDTO> eventDTO) {
        GenericProductVarietyTagEventDTO gpvEventDTO =
                eventDTO.getPayload(new TypeReference<GenericProductVarietyTagEventDTO>() {
                });
        PlatformSecurityUtil.setEventContext(eventDTO.getUserContext(),eventDTO.getTenantContext(),eventDTO.getActorContext(),eventDTO.getAppContext(),null);
        
        try {
            if(gpvEventDTO != null && gpvEventDTO.getMarketCode() != null
                    && gpvEventDTO.getGenericProductVarietyCode() != null && gpvEventDTO.getTagIdentifier() != null) {

                TagDTO gpvTagDTO = tagFacade.getTagByCodeV2(gpvEventDTO.getTagIdentifier());
                if (gpvTagDTO != null) {
                    // Get old GP tag and remove hierarchy
                    TagDTO oldGpTagDTO = tagFacade.getTagByCodeV2("TAG." + gpvEventDTO.getOldGenericProductCode());
                    if (oldGpTagDTO != null) {
                        tagFacade.deleteTagHierarchy(oldGpTagDTO.getId(), gpvTagDTO.getId());
                    }
                    
                    // Get new GP tag and create hierarchy
                    TagDTO newGpTagDTO = tagFacade.getTagByCodeV2("TAG." + gpvEventDTO.getGenericProductCode());
                    if (newGpTagDTO != null) {
                        tagFacade.addTagHierarchyBatch(Collections.singletonList(gpvTagDTO.getId()), newGpTagDTO.getId());
                    }
                }
            }
            kafkaTemplate.send(eventDTO.getSuccessEventCode(), eventDTO);
        } catch (Exception e) {
            log.error("Error processing GPV retag event", e);
            kafkaTemplate.send(eventDTO.getFailureEventEventCode(), eventDTO);
        }
    }
}


