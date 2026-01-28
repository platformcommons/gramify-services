package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.domain.dto.AppEventDTO;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.AppService;
import com.platformcommons.platform.service.search.facade.assembler.AppEventDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;

@Component
public class AppConsumer {


    @Autowired
    private AppEventDTOMapper appEventDTOMapper;

    @Autowired
    private AppService appService;

    @Transactional
    @KafkaListener(topics = MessagingConstant.APP_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void appCreated(@Payload EventDTO<AppEventDTO> eventDTO){
        AppEventDTO appEventDTO = eventDTO.getPayload(new TypeReference<AppEventDTO>() {});
        appService.createApp(appEventDTOMapper.fromDTO(appEventDTO));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.APP_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void appUpdated(@Payload EventDTO<AppEventDTO> eventDTO){
        AppEventDTO appEventDTO = eventDTO.getPayload(new TypeReference<AppEventDTO>() {});
        appService.updateApp(appEventDTOMapper.fromDTO(appEventDTO));
    }
}
