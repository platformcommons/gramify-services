package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.domain.dto.UseCaseEventDTO;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.UseCaseService;
import com.platformcommons.platform.service.search.facade.assembler.UseCaseEventDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UseCaseConsumer {

    @Autowired
    private UseCaseService useCaseService;

    @Autowired
    private UseCaseEventDTOMapper useCaseEventDTOMapper;

    @Transactional
    @KafkaListener(topics = MessagingConstant.USE_CASE_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void useCaseCreated(@Payload EventDTO<UseCaseEventDTO> eventDTO){
        UseCaseEventDTO useCaseEventDTO = eventDTO.getPayload(new TypeReference<UseCaseEventDTO>() {});
        useCaseService.createUseCase(useCaseEventDTOMapper.fromDTO(useCaseEventDTO));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.USE_CASE_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void useCaseUpdated(@Payload EventDTO<UseCaseEventDTO> eventDTO){
        UseCaseEventDTO useCaseEventDTO = eventDTO.getPayload(new TypeReference<UseCaseEventDTO>() {});
        useCaseService.updateUseCase(useCaseEventDTOMapper.fromDTO(useCaseEventDTO));
    }
}
