package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.market.dto.CustomProductSKUDTO;
import com.platformcommons.platform.service.market.dto.PublishProductDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.product.dto.ProductSKUTagDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.GenericProductVarietyService;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductSKUService;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductService;
import com.platformcommons.platform.service.search.application.service.TMAChannelSolutionService;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.facade.client.GenericProductVarietyClientV2;
import com.platformcommons.platform.service.search.messaging.mapper.GenericProductVarietyEventMapper;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelProductEventMapper;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelProductSKUEventMapper;
import com.platformcommons.platform.service.search.messaging.utility.MessagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GenericProductVarietyConsumer {

    @Autowired
    private GenericProductVarietyEventMapper genericProductVarietyEventMapper;


    @Autowired
    private GenericProductVarietyService genericProductVarietyService;

    @Autowired
    private GenericProductVarietyClientV2 genericProductVarietyClient;

    @Autowired
    private TMAChannelProductEventMapper tmaChannelProductEventMapper;

    @Autowired
    private TMAChannelProductService tmaChannelProductService;

    @Autowired
    private TMAChannelSolutionService tmaChannelSolutionService;

    @Autowired
    private TMAChannelProductSKUEventMapper tmaChannelProductSKUEventMapper;

    @Autowired
    private TMAChannelProductSKUService tmaChannelProductSKUService;

    @Autowired
    private MessagingUtil messagingUtil;


    @Value("${commons.platform.service.search-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;

    @Transactional
    @KafkaListener(topics = MessagingConstant.TMA_CHANNEL_PRODUCT_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void consumePublishProduct(EventDTO<PublishProductDTO> publishProductEventDTO){

        PublishProductDTO publishProductDTO = publishProductEventDTO.getPayload(new TypeReference<PublishProductDTO>() {});

        Set<String> gpvTagCodes = publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs().stream()
                .flatMap(sku -> sku.getProductSKUTagList().stream())
                .filter(tag -> "TAG_TYPE.GENERIC_PRODUCT_VARIETY".equalsIgnoreCase(tag.getTag().getType()))
                .map(ProductSKUTagDTO::getTagCode)
                .collect(Collectors.toSet());

        // 1. Fetch GPVs by tag codes (if any)
        Set<GenericProductVarietyEventDTO> gpvByTagCodeSet = new HashSet<>();
        if (!gpvTagCodes.isEmpty()) {
            gpvByTagCodeSet.addAll(
                    Objects.requireNonNull(genericProductVarietyClient.getCustomForGenericProductVarietyV2(gpvTagCodes, "Appkey " + appKey).getBody())
            );
        }

        // 2. Extract primaryVarietyId from product
        Long primaryVarietyId = publishProductDTO.getCustomProductDTO().getVarietyId();
        GenericProductVarietyEventDTO primaryGenericProductVarietyEventDTO;

        // 3. Check if primary GPV already exists in gpvByCodeSet
        if (primaryVarietyId != null) {
            Optional<GenericProductVarietyEventDTO> existingPrimary =
                    gpvByTagCodeSet.stream()
                            .filter(gpv -> primaryVarietyId.equals(gpv.getGenericProductVarietyId()))
                            .findFirst();

            if (existingPrimary.isPresent()) {
                primaryGenericProductVarietyEventDTO = existingPrimary.get();
            } else {
                primaryGenericProductVarietyEventDTO = genericProductVarietyClient
                        .getCustomForGenericProductVariety(primaryVarietyId, "Appkey " + appKey).getBody();
            }
        } else {
            primaryGenericProductVarietyEventDTO = null;
        }

        // 4. Secondary GPVs = all GPVs from codes except the primary
        Set<GenericProductVarietyEventDTO> secondaryGPVs = gpvByTagCodeSet.stream()
                .filter(gpv -> {
                    assert primaryGenericProductVarietyEventDTO != null;
                    return !gpv.getGenericProductVarietyId().equals(primaryGenericProductVarietyEventDTO.getGenericProductVarietyId());
                })
                .collect(Collectors.toSet());


        assert primaryGenericProductVarietyEventDTO != null;

        Set<TMAChannelProductSKU> tmaChannelProductSKUSet = tmaChannelProductSKUEventMapper.map(publishProductDTO,primaryGenericProductVarietyEventDTO);
        Set<TMAChannelProductSKU> savedTmaChannelProductSKUSet = tmaChannelProductSKUService.saveAll(tmaChannelProductSKUSet); //

        Set<TMAChannelProductSKU> fetchedProductSKUS=tmaChannelProductSKUService.getAllTMAChannelProductSKUByProductIds(publishProductDTO
                .getCustomProductDTO().getId(),publishProductDTO.getMarketId(),publishProductDTO.getChannelId());

        TMAChannelProduct tmaChannelProduct = tmaChannelProductEventMapper.fromPublishProductDTO(publishProductDTO,primaryGenericProductVarietyEventDTO);
        tmaChannelProduct.updateInfo(fetchedProductSKUS);

        CustomProductSKUDTO defaultSKU =  messagingUtil.getDefaultSKU(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs());

        Optional<TMAChannelProduct> optionalTMAChannelProduct = tmaChannelProductService.getByOptional(tmaChannelProduct.getId());
        if(optionalTMAChannelProduct.isPresent()){
            TMAChannelProduct fetched = optionalTMAChannelProduct.get();
            fetched.update(tmaChannelProduct,defaultSKU!=null);
            tmaChannelProductService.update(fetched);
        }
        else {
            if(defaultSKU==null){
                CustomProductSKUDTO defaultDefaultSKU = messagingUtil.getAnyDefaultSKU(publishProductDTO.getCustomProductDTO().getCustomProductSKUDTOs());
                addDefaultDetails(tmaChannelProduct,defaultDefaultSKU);
            }
            tmaChannelProductService.save(tmaChannelProduct);
        }

        Set<GenericProductVarietyEventDTO> bothGpvEventDTO =
                Stream.concat(
                        secondaryGPVs.stream(), Stream.of(primaryGenericProductVarietyEventDTO)).collect(Collectors.toSet());

        for (GenericProductVarietyEventDTO genericProductVarietyEventDTO : bothGpvEventDTO) {
            Optional<GenericProductVariety> genericProductVarietyOptional = genericProductVarietyService.getByIdOptional(genericProductVarietyEventMapper
                    .generateGenericProductVarietySearchId(genericProductVarietyEventDTO.getGenericProductVarietyId(),
                            publishProductDTO.getChannelId(),publishProductDTO.getMarketId()));
            GenericProductVariety genericProductVariety = genericProductVarietyEventMapper.fromEventDTO(publishProductDTO,genericProductVarietyEventDTO);
//            Set<TMAChannelProduct> fetchedTMAChannelProducts = tmaChannelProductService.getByGenericProductVarietyId(genericProductVariety.getGenericProductVarietyId()
//                    ,publishProductDTO.getMarketId(),publishProductDTO.getChannelId()); //get all by Tag code also
//            genericProductVariety.updateInfo(fetchedTMAChannelProducts);  //this method call should be deleted
            if(genericProductVarietyOptional.isPresent()){
                GenericProductVariety fetched = genericProductVarietyOptional.get();
                fetched.update(genericProductVariety);
                genericProductVarietyService.update(fetched);
            }
            else {
                genericProductVarietyService.save(genericProductVariety);
            }
        }


        savedTmaChannelProductSKUSet.forEach(tmaChannelProductSKU -> {
            String status = tmaChannelProductSKU.getSkuTmaChannelProductStatus();
            if (!"TMA_CHANNEL_PRODUCT_PUBLISH_STATUS.PUBLISHED".equalsIgnoreCase(status != null ? status : "")) {
                tmaChannelProductSKUService.updateByTmaChannelProductId(tmaChannelProductSKU);
            }
        });
    }


    private void addDefaultDetails(TMAChannelProduct tmaChannelProduct, CustomProductSKUDTO defaultSKU) {
        tmaChannelProduct.setDefaultSKUId(defaultSKU != null ? defaultSKU.getId() : null);
        tmaChannelProduct.setDefaultSKUName(messagingUtil.getDefaultSKUName(defaultSKU));
        tmaChannelProduct.setDefaultSKUPrice(defaultSKU != null && defaultSKU.getMrp()!=null ? defaultSKU.getMrp().getValue() :null);
        tmaChannelProduct.setDefaultSKUAvailableQuantity(defaultSKU != null && defaultSKU.getQuantity()!=null ? defaultSKU.getQuantity().getValue() :null);
        tmaChannelProduct.setDefaultSKUTmaChannelProductId(defaultSKU != null ? defaultSKU.getTmaChannelProductId() : null);
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.GENERIC_PRODUCT_VARIETY_CREATED_OR_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void genericProductVarietyCreatedAndUpdated(@Payload EventDTO<GenericProductVarietyEventDTO> eventDTO){
        GenericProductVarietyEventDTO genericProductVarietyEventDTO = eventDTO.getPayload(new TypeReference<GenericProductVarietyEventDTO>() {});

        if(genericProductVarietyEventDTO.getMarketId() != null && genericProductVarietyEventDTO.getIsDefaultChannel()
                && genericProductVarietyEventDTO.getChannelId() != null && genericProductVarietyEventDTO.getGenericProductVarietyId() != null){
            Set<GenericProductVariety> fetchedGenericProductVarieties = genericProductVarietyService.getByMarketIdAndGenericProductVarietyId(genericProductVarietyEventDTO.getMarketId(),
                    genericProductVarietyEventDTO.getGenericProductVarietyId());
            if(fetchedGenericProductVarieties == null || fetchedGenericProductVarieties.isEmpty()){
                GenericProductVariety newGenericProductVariety = genericProductVarietyEventMapper.buildGenericProductVarietyForDefaultChannel(genericProductVarietyEventDTO);
                genericProductVarietyService.save(newGenericProductVariety);
            }
            else{
                GenericProductVariety newGenericProductVariety = genericProductVarietyEventMapper.buildGenericProductVarietyForDefaultChannel(genericProductVarietyEventDTO);
                fetchedGenericProductVarieties.forEach(genericProductVariety -> genericProductVariety.updateBasicDetails(newGenericProductVariety) );
                genericProductVarietyService.saveAll(fetchedGenericProductVarieties);

                tmaChannelSolutionService.updateGenericProductVarietyDetails(newGenericProductVariety);
                tmaChannelProductService.updateGenericProductVarietyDetails(newGenericProductVariety);
                tmaChannelProductSKUService.updateGenericProductVarietyDetails(newGenericProductVariety);
            }
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.GENERIC_PRODUCT_VARIETY_DELETED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void genericProductVarietyDeleted(@Payload EventDTO<GenericProductVarietyEventDTO> eventDTO){
        GenericProductVarietyEventDTO genericProductVarietyEventDTO = eventDTO.getPayload(new TypeReference<GenericProductVarietyEventDTO>() {});

        if(genericProductVarietyEventDTO.getMarketId() != null && genericProductVarietyEventDTO.getIsDefaultChannel()
                && genericProductVarietyEventDTO.getChannelId() != null && genericProductVarietyEventDTO.getGenericProductVarietyId() != null){
            Set<GenericProductVariety> fetchedGenericProductVarieties = genericProductVarietyService.getByMarketIdAndGenericProductVarietyId(genericProductVarietyEventDTO.getMarketId(),
                    genericProductVarietyEventDTO.getGenericProductVarietyId());
            if(fetchedGenericProductVarieties != null && !fetchedGenericProductVarieties.isEmpty()){
                fetchedGenericProductVarieties.forEach(genericProductVariety -> genericProductVariety.setIsActive(Boolean.FALSE) );
                genericProductVarietyService.saveAll(fetchedGenericProductVarieties);
            }
        }
    }

}
