package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.market.dto.TraderDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.facade.TMAChannelProductFacade;
import com.platformcommons.platform.service.search.facade.TMAChannelProductSKUFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductEventConsumer {
    @Autowired
    private TMAChannelProductFacade tmaChannelProductFacade;

    @Autowired
    private TMAChannelProductSKUFacade tmaChannelProductSKUFacade;
    @Transactional
    @KafkaListener(topics = MessagingConstant.CREATE_PRODUCT_DTO,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void productCreated(@Payload EventDTO<ProductDTO> eventDTO){
        ProductDTO payload = eventDTO.getPayload(new TypeReference<ProductDTO>() {
        });
    }
    @Transactional
    @KafkaListener(topics = MessagingConstant.CREATE_PRODUCT_DTO,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void productUpdated(@Payload EventDTO<ProductDTO> eventDTO){
        ProductDTO payload = eventDTO.getPayload(new TypeReference<ProductDTO>() {
        });
        tmaChannelProductFacade.updateWithProduct(payload);
        tmaChannelProductSKUFacade.updateWithProduct(payload);
    }
}
