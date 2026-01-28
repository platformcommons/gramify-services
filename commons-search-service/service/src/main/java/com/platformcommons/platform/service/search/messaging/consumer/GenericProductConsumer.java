package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.product.dto.GenericProductEventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.*;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.messaging.mapper.GenericProductEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class GenericProductConsumer {

    @Autowired
    private GenericProductEventMapper genericProductEventMapper;

    @Autowired
    private GenericProductService genericProductService;

    @Autowired
    private GenericProductVarietyService genericProductVarietyService;

    @Autowired
    private TMAChannelSolutionService tmaChannelSolutionService;

    @Autowired
    private TMAChannelProductService tmaChannelProductService;

    @Autowired
    private TMAChannelProductSKUService tmaChannelProductSKUService;


    @Transactional
    @KafkaListener(topics = MessagingConstant.GENERIC_PRODUCT_CREATED_OR_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void genericProductCreatedAndUpdated(@Payload EventDTO<GenericProductEventDTO> eventDTO){
        GenericProductEventDTO genericProductEventDTO = eventDTO.getPayload(new TypeReference<GenericProductEventDTO>() {});

        if(genericProductEventDTO.getMarketId() != null && genericProductEventDTO.getGenericProductId() != null){
            Set<GenericProduct> fetchedGenericProducts = genericProductService.getByMarketIdAndGenericProductId(genericProductEventDTO.getMarketId(),
                    genericProductEventDTO.getGenericProductId());
            if(fetchedGenericProducts == null || fetchedGenericProducts.isEmpty()){
                GenericProduct newGenericProduct = genericProductEventMapper.buildGenericProduct(genericProductEventDTO);
                genericProductService.save(newGenericProduct);
            }
            else{
                GenericProduct newGenericProduct = genericProductEventMapper.buildGenericProduct(genericProductEventDTO);
                fetchedGenericProducts.forEach(genericProduct -> genericProduct.updateBasicDetails(newGenericProduct) );
                genericProductService.saveAll(fetchedGenericProducts);

                genericProductVarietyService.updateGenericProductDetails(newGenericProduct);
                tmaChannelSolutionService.updateGenericProductDetails(newGenericProduct);
                tmaChannelProductService.updateGenericProductDetails(newGenericProduct);
                tmaChannelProductSKUService.updateGenericProductDetails(newGenericProduct);
            }
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.GENERIC_PRODUCT_DELETED_V2,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void genericProductDeleted(@Payload EventDTO<GenericProductEventDTO> eventDTO){
        GenericProductEventDTO genericProductEventDTO = eventDTO.getPayload(new TypeReference<GenericProductEventDTO>() {});

        if(genericProductEventDTO.getMarketId() != null && genericProductEventDTO.getGenericProductId() != null){
            Set<GenericProduct> fetchedGenericProducts = genericProductService.getByMarketIdAndGenericProductId(genericProductEventDTO.getMarketId(),
                    genericProductEventDTO.getGenericProductId());
            if(fetchedGenericProducts != null && !fetchedGenericProducts.isEmpty()){
                fetchedGenericProducts.forEach(genericProduct -> genericProduct.setIsActive(Boolean.FALSE) );
                genericProductService.saveAll(fetchedGenericProducts);

            }
        }
    }

}
