package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.OpportunityDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.PostService;
import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.domain.Post;
import com.platformcommons.platform.service.search.messaging.mapper.PostEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class PostConsumer {

    @Autowired
    private PostService postService;

    @Autowired
    private PostEventMapper postEventMapper;


    @Transactional
    @KafkaListener(topics = MessagingConstant.POST_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void postCreated(@Payload EventDTO<PostDTO> eventDTO){
        PostDTO postDTO = eventDTO.getPayloadAndInitializeContext(new TypeReference<PostDTO>() {});
        Post post = postEventMapper.fromEventDTO(postDTO);
        postService.save(post);
        log.info(String.format("Post Created Consumed with id >>>>>>>>>>>>>>>>>> %d ",post.getId()));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.POST_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void postUpdated(@Payload EventDTO<PostDTO> eventDTO){
        PostDTO postDTO = eventDTO.getPayloadAndInitializeContext(new TypeReference<PostDTO>() {});
        Post post = postEventMapper.fromEventDTO(postDTO);
        postService.save(post);
        log.info(String.format("Post Update Consumed with id >>>>>>>>>>>>>>>>>> %d ",post.getId()));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.POST_UPDATED_BULK, groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void postUpdatedBulk(@Payload EventDTO<List<PostDTO>> eventDTO) {
        List<PostDTO> postDTOList = eventDTO.getPayloadAndInitializeContext(new TypeReference<List<PostDTO>>() {});
        for (PostDTO postDTO : postDTOList) {
            Post post = postEventMapper.fromEventDTO(postDTO);
            postService.save(post);
            log.info(String.format("Post Update Consumed with id >>>>>>>>>>>>>>>>>> %d ", post.getId()));
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.POST_DELETED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void postDeleted(@Payload EventDTO<Long> id){
        Long postId = id.getPayloadAndInitializeContext(new TypeReference<Long>() {});
        if (postId != null){
            postService.delete(postId);
            log.info(String.format("Post Delete Consumed with id >>>>>>>>>>>>>>>>>> %d ",id));
        }else {
            log.warn("Null postId for deletion");
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.POST_SYNC,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void postSync(@Payload EventDTO<PostDTO> eventDTO){
        PostDTO postDTO = eventDTO.getPayloadAndInitializeContext(new TypeReference<PostDTO>() {});
        Post post = postEventMapper.fromEventDTO(postDTO);
        postService.save(post);
        log.info("PostDTO with id >>>>>>>>>>>>>>>>>>> " + post.getId()+" >>>>>>>>>>> consumed and synced in solr");
    }


}
