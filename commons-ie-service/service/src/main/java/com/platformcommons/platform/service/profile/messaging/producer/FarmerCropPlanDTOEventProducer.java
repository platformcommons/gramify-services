package com.platformcommons.platform.service.profile.messaging.producer;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.person.dto.FarmerCropPlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FarmerCropPlanDTOEventProducer {

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    public  static  final String FARMER_CROP_PLAN_CREATED = "COMMONS_PERSON_SERVICE.FARMER_CROP_PLAN_CREATED";


    public void  farmerCropPlanCreated(FarmerCropPlanDTO farmerCropPlanDTO){
        EventDTO<FarmerCropPlanDTO> farmerCropPlanEventDTO = EventDTO.<FarmerCropPlanDTO>builder()
                .requestPayload(farmerCropPlanDTO)
                .platformToken(getPlatformToken())
                .build();
        kafkaTemplate.send(FARMER_CROP_PLAN_CREATED,farmerCropPlanEventDTO);
    }


    private PlatformToken getPlatformToken() {
        try {
            return (PlatformToken) SecurityContextHolder.getContext().getAuthentication();
        } catch (ClassCastException exception) {
            throw new RuntimeException("Unable to cast userPrincipal");
        }

    }
}
