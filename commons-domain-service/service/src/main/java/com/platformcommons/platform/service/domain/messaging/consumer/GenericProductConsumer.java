package com.platformcommons.platform.service.domain.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.domain.dto.GenericProductTagEventDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.facade.TagFacade;
import com.platformcommons.platform.service.domain.messaging.constants.EventConstants;
import com.platformcommons.platform.service.domain.messaging.mapper.TagEventMapper;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenericProductConsumer {

    @Autowired
    private TagFacade tagFacade;

    @Autowired
    private TagEventMapper tagEventMapper;

    @Transactional
    @KafkaListener(
            topics = {EventConstants.GENERIC_PRODUCT_TAG_CREATED_OR_UPDATED,EventConstants.GENERIC_PRODUCT_TAG_SYNCED},
            groupId = EventConstants.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY
    )
    public void genericProductCreatedAndUpdated(@Payload EventDTO<GenericProductTagEventDTO> eventDTO) {
        GenericProductTagEventDTO gpEventDTO =
                eventDTO.getPayload(new TypeReference<GenericProductTagEventDTO>() {
                });
        PlatformSecurityUtil.setEventContext(eventDTO.getUserContext(),eventDTO.getTenantContext(),eventDTO.getActorContext(),eventDTO.getAppContext(),null);
        if(gpEventDTO !=null && gpEventDTO.getMarketCode() != null
                && gpEventDTO.getGenericProductCode() != null && gpEventDTO.getTagIdentifier() != null){
            TagDTO tagDTO = tagEventMapper.buildTagFromGenericProduct(gpEventDTO);
            TagDTO fetchedtTagDTO = tagFacade.getTagByCodeV2(tagDTO.getCode());
            if(fetchedtTagDTO == null){
                tagFacade.save(tagDTO);
            }
            else {
                tagDTO.setId(fetchedtTagDTO.getId());
                tagFacade.update(tagDTO);
            }

        }
    }

    @Transactional
    @KafkaListener(
            topics = {EventConstants.GENERIC_PRODUCT_TAG_DELETED},
            groupId = EventConstants.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY
    )
    public void genericProductDeleted(@Payload EventDTO<GenericProductTagEventDTO> eventDTO) {
        GenericProductTagEventDTO gpEventDTO =
                eventDTO.getPayload(new TypeReference<GenericProductTagEventDTO>() {
                });
        PlatformSecurityUtil.setEventContext(eventDTO.getUserContext(),eventDTO.getTenantContext(),eventDTO.getActorContext(),eventDTO.getAppContext(),null);
        if(gpEventDTO !=null && gpEventDTO.getMarketCode() != null
                && gpEventDTO.getGenericProductCode() != null && gpEventDTO.getTagIdentifier() != null){
            
            TagDTO gpTagDTO = tagFacade.getTagByCodeV2(gpEventDTO.getTagIdentifier());
            if (gpTagDTO != null) {
                tagFacade.delete(gpTagDTO.getId(), "deleted By Admin");
            }
        }
    }
}


