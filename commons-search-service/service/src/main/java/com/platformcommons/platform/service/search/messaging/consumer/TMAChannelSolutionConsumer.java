package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.TMAChannelSolutionService;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelSolutionMapper;
import com.platformcommons.platform.service.market.dto.DeleteTraderSolutionRequestDTO;
import com.platformcommons.platform.service.market.dto.PublishedSolutionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TMAChannelSolutionConsumer {


    @Autowired
    private TMAChannelSolutionMapper tmaChannelSolutionMapper;

    @Autowired
    private TMAChannelSolutionService tmaChannelSolutionService;

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    @Transactional
    @KafkaListener(topics = MessagingConstant.TMA_CHANNEL_SOLUTION_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void consumePublishSolution(EventDTO<PublishedSolutionDTO> eventDTO) {
        PublishedSolutionDTO publishedSolutionDTO = eventDTO.getPayload(new TypeReference<PublishedSolutionDTO>() {});
        TMAChannelSolution tmaChannelSolution = tmaChannelSolutionMapper.map(publishedSolutionDTO);
        tmaChannelSolutionService.addOrUpdate(tmaChannelSolution);
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.TMA_CHANNEL_SOLUTION_DELETED_FOR_SEARCH_SERVICE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void consumeTmaChannelSolutionDeletion(EventDTO<DeleteTraderSolutionRequestDTO> eventDTO) {
        EventDTO<DeleteTraderSolutionRequestDTO> deleteTraderSolutionRequestDTOEventDTO = null;
        DeleteTraderSolutionRequestDTO deleteTraderSolutionRequestDTO = eventDTO.getPayloadAndInitializeContext(new TypeReference<DeleteTraderSolutionRequestDTO>() {});
        try {
            tmaChannelSolutionService.deleteTmaChannelSolution(deleteTraderSolutionRequestDTO.getSolutionId(), deleteTraderSolutionRequestDTO.getTraderId(), deleteTraderSolutionRequestDTO.getChannelId(), deleteTraderSolutionRequestDTO.getMarketId());
            deleteTraderSolutionRequestDTOEventDTO = EventDTO.<DeleteTraderSolutionRequestDTO>builder()
                    .requestPayload(deleteTraderSolutionRequestDTO)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(eventDTO.getSuccessEventCode(), deleteTraderSolutionRequestDTOEventDTO);
        } catch (Exception e) {
            kafkaTemplate.send(eventDTO.getFailureEventEventCode(), deleteTraderSolutionRequestDTOEventDTO);
        }
    }
}
