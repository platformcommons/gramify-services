package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.market.dto.PublishedSolutionDTO;
import com.platformcommons.platform.service.product.dto.SolutionResourceMapDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductService;
import com.platformcommons.platform.service.search.application.service.TMAChannelSolutionService;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelSolutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class SolutionResourceMapConsumer {

    @Autowired
    private TMAChannelProductService tmaChannelProductService;

    @Transactional
    @KafkaListener(topics = MessagingConstant.CREATE_SOLUTION_RESOURCE_MAP_DTO,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void consumeSolutionResourceMap(EventDTO<SolutionResourceMapDTO> eventDTO) {
        SolutionResourceMapDTO solutionResourceMapDTO = eventDTO.getPayload(new TypeReference<SolutionResourceMapDTO>() {});
        Set<TMAChannelProduct> fetchedTmaChannelProductSet = tmaChannelProductService.getByDefaultSkuId(solutionResourceMapDTO.getProductSKUId());

        if(fetchedTmaChannelProductSet !=null && !fetchedTmaChannelProductSet.isEmpty()){
            fetchedTmaChannelProductSet.forEach(tmaChannelProduct -> {
                if(tmaChannelProduct.getLinkedSolutionResourceIds() !=null && !tmaChannelProduct.getLinkedSolutionResourceIds().isEmpty() ){
                    tmaChannelProduct.getLinkedSolutionResourceIds().add(solutionResourceMapDTO.getSolutionResourceId());
                }
                else{
                    tmaChannelProduct.setLinkedSolutionResourceIds(Collections.singleton(solutionResourceMapDTO.getSolutionResourceId()));
                }
            });
        }
        tmaChannelProductService.saveAll(fetchedTmaChannelProductSet);
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.DELETE_SOLUTION_RESOURCE_MAP_DTO,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void consumeSolutionResourceMapDelete(EventDTO<SolutionResourceMapDTO> eventDTO) {
        SolutionResourceMapDTO solutionResourceMapDTO = eventDTO.getPayload(new TypeReference<SolutionResourceMapDTO>() {});
        Set<TMAChannelProduct> fetchedTmaChannelProductSet = tmaChannelProductService.getByDefaultSkuId(solutionResourceMapDTO.getProductSKUId());

        if(fetchedTmaChannelProductSet !=null && !fetchedTmaChannelProductSet.isEmpty()){
            fetchedTmaChannelProductSet.forEach(tmaChannelProduct ->{
                Set<Long> existingLinkedSolutionResourceIds = tmaChannelProduct.getLinkedSolutionResourceIds();
                Set<Long> newLinkedSolutionResourceIds = existingLinkedSolutionResourceIds.stream()
                        .filter(id -> !id.equals(solutionResourceMapDTO.getSolutionResourceId()))
                        .collect(Collectors.toSet());

                tmaChannelProduct.getLinkedSolutionResourceIds().clear();
                tmaChannelProduct.setLinkedSolutionResourceIds(newLinkedSolutionResourceIds);
            });
        }
        tmaChannelProductService.saveAll(fetchedTmaChannelProductSet);
    }
}
