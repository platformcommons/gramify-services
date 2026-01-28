package com.platformcommons.platform.service.profile.messaging.producer;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.profile.dto.IeDTO;
import com.platformcommons.platform.service.profile.messaging.MessagingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class IeDTOEventProducer {

    @Autowired
    private KafkaTemplate<String,EventDTO> kafkaTemplate;

    public void  createdIeDto(IeDTO ieDTO){
        EventDTO<IeDTO> eventDTO = EventDTO.<IeDTO>builder()
                .requestPayload(ieDTO)
                .platformToken(getPlatformToken())
                .withContext(Boolean.TRUE)
                .build();
        kafkaTemplate.send(MessagingConstants.IE_CREATED, eventDTO);
    }

    public void  updatedIeDto(IeDTO ieDTO){
        EventDTO<IeDTO> eventDTO = EventDTO.<IeDTO>builder()
                .requestPayload(ieDTO)
                .platformToken(getPlatformToken())
                .withContext(Boolean.TRUE)
                .build();
        kafkaTemplate.send(MessagingConstants.IE_UPDATED, eventDTO);
    }

    private PlatformToken getPlatformToken(){
        try{
            return (PlatformToken) SecurityContextHolder.getContext().getAuthentication();
        }
        catch (ClassCastException exception){
            throw  new RuntimeException("Unable to cast userPrincipal");
        }
    }

}
