package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.market.dto.TraderDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.facade.TMAChannelProductFacade;
import com.platformcommons.platform.service.search.facade.TMAChannelProductSKUFacade;
import com.platformcommons.platform.service.search.facade.TMAChannelSolutionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class TraderEventConsumer {

    @Autowired
    private TMAChannelProductFacade tmaChannelProductFacade;

    @Autowired
    private TMAChannelProductSKUFacade tmaChannelProductSKUFacade;

    @Autowired
    private TMAChannelSolutionFacade tmaChannelSolutionFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.TRADER_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void traderCreated(@Payload EventDTO<TraderDTO> eventDTO){
        TraderDTO payload = eventDTO.getPayload(new TypeReference<TraderDTO>() {
        });
    }
    @Transactional
    @KafkaListener(topics = MessagingConstant.TRADER_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void traderUpdated(@Payload EventDTO<TraderDTO> eventDTO){
        TraderDTO payload = eventDTO.getPayload(new TypeReference<TraderDTO>() {
        });
        tmaChannelProductFacade.updateWithTrader(payload.getId(),payload.getTraderDisplayName(),payload.getIconPic());
        tmaChannelProductSKUFacade.updateWithTrader(payload.getId(),payload.getTraderDisplayName(),payload.getIconPic());
        tmaChannelSolutionFacade.updateWithTrader(payload.getId(), payload.getTraderDisplayName(), payload.getIconPic());
    }
}
