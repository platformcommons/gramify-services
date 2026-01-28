package com.platformcommons.platform.service.search.messaging.consumer;


import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.market.dto.TMAChannelProductDTO;
import com.platformcommons.platform.service.market.dto.UpdateTmaChannelProductGpGpvRequestDTO;
import com.platformcommons.platform.service.product.dto.DeleteTMAChannelProductRequestDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductSKUService;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelProductSKUEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TMAChannelProductEventConsumer {

    @Autowired
    private TMAChannelProductSKUService tmaChannelProductSKUService;

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    @Autowired
    private TMAChannelProductSKUEventMapper tmaChannelProductSKUEventMapper;

    @Transactional
    @KafkaListener(topics = MessagingConstant.TMA_CHANNEL_PRODUCT_DELETED_FOR_SEARCH_SERVICE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void consumeTmaChannelProductDeletion(EventDTO<DeleteTMAChannelProductRequestDTO> eventDTO) {
        EventDTO<DeleteTMAChannelProductRequestDTO> deleteTMAChannelProductRequestEventDTO = null;
        DeleteTMAChannelProductRequestDTO deleteTMAChannelProductRequestDTO = eventDTO.getPayloadAndInitializeContext(new TypeReference<DeleteTMAChannelProductRequestDTO>() {});
        try {
            tmaChannelProductSKUService.deleteTmaChannelProductSKU(deleteTMAChannelProductRequestDTO.getProductSKUId(), deleteTMAChannelProductRequestDTO.getProductId(), deleteTMAChannelProductRequestDTO.getTraderId(), deleteTMAChannelProductRequestDTO.getChannelId(), deleteTMAChannelProductRequestDTO.getMarketId());
            deleteTMAChannelProductRequestEventDTO = EventDTO.<DeleteTMAChannelProductRequestDTO>builder()
                    .requestPayload(deleteTMAChannelProductRequestDTO)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(eventDTO.getSuccessEventCode(), deleteTMAChannelProductRequestEventDTO);
        } catch (Exception e) {
            kafkaTemplate.send(eventDTO.getFailureEventEventCode(), deleteTMAChannelProductRequestEventDTO);
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.TMA_CHANNEL_PRODUCT_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void consumeTmaChannelProductUpdate(EventDTO<TMAChannelProductDTO> eventDTO) {
        TMAChannelProductDTO tmaChannelProductDTO = eventDTO.getPayloadAndInitializeContext(new TypeReference<TMAChannelProductDTO>() {});
        TMAChannelProductSKU tmaChannelProductSKU = tmaChannelProductSKUEventMapper.mapFromTmaChannelProductDTO(tmaChannelProductDTO);
        tmaChannelProductSKUService.updateByTmaChannelProductId(tmaChannelProductSKU);
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.PRODUCT_GP_GPV_UPDATED_FOR_SEARCH_SERVICE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void consumeUpdateTmaChannelProductGpGpv(EventDTO<UpdateTmaChannelProductGpGpvRequestDTO> eventDTO) {
        EventDTO<UpdateTmaChannelProductGpGpvRequestDTO> updateTmaChannelProductGpGpvRequestEventDTO = null;
        UpdateTmaChannelProductGpGpvRequestDTO updateTmaChannelProductGpGpvRequestDTO = eventDTO.getPayloadAndInitializeContext(new TypeReference<UpdateTmaChannelProductGpGpvRequestDTO>() {});
        try {
            tmaChannelProductSKUService.updateGpAndGpv(updateTmaChannelProductGpGpvRequestDTO);
            updateTmaChannelProductGpGpvRequestEventDTO = EventDTO.<UpdateTmaChannelProductGpGpvRequestDTO>builder()
                    .requestPayload(updateTmaChannelProductGpGpvRequestDTO)
                    .withContext(Boolean.TRUE)
                    .build();
            kafkaTemplate.send(eventDTO.getSuccessEventCode(), updateTmaChannelProductGpGpvRequestEventDTO);
        } catch (Exception e) {
            kafkaTemplate.send(eventDTO.getFailureEventEventCode(), updateTmaChannelProductGpGpvRequestEventDTO);
        }
    }

}
