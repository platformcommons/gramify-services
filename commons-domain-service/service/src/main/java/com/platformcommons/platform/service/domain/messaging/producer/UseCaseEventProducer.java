package com.platformcommons.platform.service.domain.messaging.producer;

import com.platformcommons.platform.service.domain.dto.UseCaseEventDTO;
import com.platformcommons.platform.service.domain.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UseCaseEventProducer {


    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    public void  useCaseCreated(UseCaseEventDTO useCaseEventDTO){
        EventDTO<UseCaseEventDTO> eventDTO = EventDTO.<UseCaseEventDTO>builder()
                .requestPayload(useCaseEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.USE_CASE_CREATED, eventDTO);
    }

    public void  useCaseUpdated(UseCaseEventDTO useCaseEventDTO){
        EventDTO<UseCaseEventDTO> eventDTO = EventDTO.<UseCaseEventDTO>builder()
                .requestPayload(useCaseEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.USE_CASE_UPDATED, eventDTO);
    }
}
