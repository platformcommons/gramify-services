package com.platformcommons.platform.service.post.messaging.producer;

import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.messaging.MessagingConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
public class PostProducer {

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;


    public void postCreated(PostDTO postDTO) {
        if (postDTO != null && postDTO.getPostedBy() != null && postDTO.getPostedBy().getActorId() != null ) {
            String key = postDTO.getPostedBy().getActorId().toString();
            EventDTO<PostDTO> eventDTO = EventDTO.<PostDTO>builder()
                    .requestPayload(postDTO)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(MessagingConstant.POST_CREATED,key, eventDTO);
            log.info("Messaging ----------> Created Post Event {}",  postDTO.getPostedBy().getActorId());
        }
    }

    public void postUpdated(PostDTO postDTO) {
        if (postDTO != null && postDTO.getPostedBy() != null && postDTO.getPostedBy().getActorId() != null ) {
            String key = postDTO.getPostedBy().getActorId().toString();
            EventDTO<PostDTO> eventDTO = EventDTO.<PostDTO>builder()
                    .requestPayload(postDTO)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(MessagingConstant.POST_UPDATED,key, eventDTO);
            log.info("Messaging ----------> Updated Post Event {}",  postDTO.getPostedBy().getActorId());
        }
    }

    public void postUpdatedBulk(List<PostDTO> updatedPostDTOList) {
        if (updatedPostDTOList != null && !updatedPostDTOList.isEmpty()) {
            EventDTO<List<PostDTO>> eventDTO = EventDTO.<List<PostDTO>>builder()
                    .requestPayload(updatedPostDTOList)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(MessagingConstant.POST_UPDATED_BULK, eventDTO);
            log.info("Messaging ----------> Updated Post Bulk Event");
        }
    }

    public void postDeleted(Long id) {
        String key = id.toString();
        EventDTO<Long> eventDTO = EventDTO.<Long>builder()
                .requestPayload(id)
                .withContext(Boolean.TRUE)
                .build();
        kafkaTemplate.send(MessagingConstant.POST_DELETED,key, eventDTO);
    }

    public void postSync(PostDTO postDTO) {
        if (postDTO != null && postDTO.getPostedBy() != null && postDTO.getPostedBy().getActorId() != null) {
            String key = postDTO.getPostedBy().getActorId().toString();
            EventDTO<PostDTO> eventDTO = EventDTO.<PostDTO>builder()
                    .requestPayload(postDTO)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(MessagingConstant.POST_SYNC, key, eventDTO);
            log.info("Messaging ----------> Sync Post Event {}",  postDTO.getPostedBy().getActorId());
        }
    }
}
