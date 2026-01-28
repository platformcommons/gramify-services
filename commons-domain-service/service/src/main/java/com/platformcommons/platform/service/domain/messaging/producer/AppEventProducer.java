package com.platformcommons.platform.service.domain.messaging.producer;

import com.platformcommons.platform.service.domain.dto.AppEventDTO;
import com.platformcommons.platform.service.domain.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppEventProducer {

    @Autowired
    private KafkaTemplate<String,EventDTO> kafkaTemplate;

    public void  appCreated(AppEventDTO appEventDTO){
        EventDTO<AppEventDTO> eventDTO = EventDTO.<AppEventDTO>builder()
                .requestPayload(appEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.APP_CREATED, eventDTO);
    }

    public void  appUpdated(AppEventDTO appEventDTO){
        EventDTO<AppEventDTO> eventDTO = EventDTO.<AppEventDTO>builder()
                .requestPayload(appEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.APP_UPDATED, eventDTO);
    }
}
