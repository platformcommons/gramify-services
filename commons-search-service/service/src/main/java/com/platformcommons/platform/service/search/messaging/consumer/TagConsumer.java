package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.TagSearchService;
import com.platformcommons.platform.service.search.messaging.mapper.TagDTOEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TagConsumer {


    @Autowired
    private TagSearchService tagSearchService;

    @Autowired
    private TagDTOEventMapper tagDTOEventMapper;


    @Transactional
    @KafkaListener(topics = MessagingConstant.TAG_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void tagCreated(@Payload EventDTO<TagDTO> eventDTO){
        TagDTO tagDTO = eventDTO.getPayload(new TypeReference<TagDTO>() {});
        tagSearchService.createTagSearch(tagDTOEventMapper.fromDTO(tagDTO));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.TAG_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void tagUpdated(@Payload EventDTO<TagDTO> eventDTO){
        TagDTO tagDTO = eventDTO.getPayload(new TypeReference<TagDTO>() {});
        tagSearchService.updateTagSearch(tagDTOEventMapper.fromDTO(tagDTO));
    }
}
