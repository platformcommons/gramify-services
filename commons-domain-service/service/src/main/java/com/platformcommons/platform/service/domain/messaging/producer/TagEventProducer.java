package com.platformcommons.platform.service.domain.messaging.producer;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TagEventProducer {

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    public void  tagCreated(TagDTO tagDTO){
        EventDTO<TagDTO> eventDTO = EventDTO.<TagDTO>builder()
                .requestPayload(tagDTO)
                .build();
        kafkaTemplate.send(EventConstants.TAG_CREATED, eventDTO);
    }

    public void  tagUpdated(TagDTO tagDTO){
        EventDTO<TagDTO> eventDTO = EventDTO.<TagDTO>builder()
                .requestPayload(tagDTO)
                .build();
        kafkaTemplate.send(EventConstants.TAG_UPDATED, eventDTO);
    }

}
