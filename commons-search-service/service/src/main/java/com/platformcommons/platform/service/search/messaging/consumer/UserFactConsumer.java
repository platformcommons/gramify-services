package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.UserService;
import com.platformcommons.platform.service.search.domain.User;
import com.platformcommons.platform.service.search.dto.UserDTO;
import com.platformcommons.platform.service.search.facade.UserFacade;
import com.platformcommons.platform.service.search.messaging.mapper.UserFactEventMapper;
import com.platformcommons.platform.service.vms.reporting.dto.UserFactDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
@Slf4j
public class UserFactConsumer {

    @Autowired
    private UserFactEventMapper userFactEventMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.USER_FACT_SYNC,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void userFactSync(@Payload EventDTO<UserFactDTO> eventDTO){
        UserFactDTO userFactDTO = eventDTO.getPayload(new TypeReference<UserFactDTO>() {
        });
        UserDTO userDTO = userFactEventMapper.getUserDTOFromUserFactDTO(userFactDTO);
        userFacade.sync(userDTO);
        log.info(String.format("UserFact Sync Consumed with id >>>>>>>>>>>>>>>>>> %d ",userDTO.getUserId()));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.USER_FACT_CREATED_OR_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void userFactCreatedOrUpdated(@Payload EventDTO<UserFactDTO> eventDTO){
        UserFactDTO userFactDTO = eventDTO.getPayload(new TypeReference<UserFactDTO>() {
        });
        UserDTO userDTO = userFactEventMapper.getUserDTOFromUserFactDTO(userFactDTO);
        userFacade.saveOrPutUpdate(userDTO);
        log.info(String.format("UserFact Create/Update Consumed with id >>>>>>>>>>>>>>>>>> %d ",userDTO.getUserId()));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.USER_FACT_DELETED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void userFactDeleted(@Payload EventDTO<Long> payload){
        Long userId = payload.getPayload(new TypeReference<Long>() {
        });
        userService.delete(userId);
        log.info(String.format("UserFact Delete Consumed with id >>>>>>>>>>>>>>>>>> %d ",userId));
    }

}
