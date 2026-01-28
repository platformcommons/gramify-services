package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.product.dto.SolutionDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.GenericSolutionService;
import com.platformcommons.platform.service.search.messaging.mapper.GenericSolutionDTOEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GenericSolutionConsumer {


    @Autowired
    private GenericSolutionService genericSolutionSearchService;

    @Autowired
    private GenericSolutionDTOEventMapper genericSolutionDTOEventMapper;


    @Transactional
    @KafkaListener(topics = MessagingConstant.CREATE_GENERIC_SOLUTION_DTO,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void genericSolutionCreated(@Payload EventDTO<SolutionDTO> eventDTO){
        SolutionDTO solutionDTO = eventDTO.getPayload(new TypeReference<SolutionDTO>() {});
        genericSolutionSearchService.createGenericSolution(genericSolutionDTOEventMapper.fromDTO(solutionDTO));
    }
}
