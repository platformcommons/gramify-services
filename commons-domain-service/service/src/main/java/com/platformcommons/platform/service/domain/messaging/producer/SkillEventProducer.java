package com.platformcommons.platform.service.domain.messaging.producer;

import com.platformcommons.platform.service.domain.dto.SkillEventDTO;
import com.platformcommons.platform.service.domain.dto.SkillHierarchyEventDTO;
import com.platformcommons.platform.service.domain.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SkillEventProducer {

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    public void  skillCreated(SkillEventDTO skillEventDTO){
        EventDTO<SkillEventDTO> eventDTO = EventDTO.<SkillEventDTO>builder()
                .requestPayload(skillEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.SKILL_CREATED, eventDTO);
    }

    public void  skillUpdated(SkillEventDTO skillEventDTO){
        EventDTO<SkillEventDTO> eventDTO = EventDTO.<SkillEventDTO>builder()
                .requestPayload(skillEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.SKILL_UPDATED, eventDTO);
    }


    public void  skillHierarchyCreated(SkillHierarchyEventDTO hierarchyEventDTO){
        EventDTO<SkillHierarchyEventDTO> eventDTO = EventDTO.<SkillHierarchyEventDTO>builder()
                .requestPayload(hierarchyEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.SKILL_HIERARCHY_CREATED, eventDTO);
    }



    public void  skillHierarchyUpdated(SkillHierarchyEventDTO hierarchyEventDTO){
        EventDTO<SkillHierarchyEventDTO> eventDTO = EventDTO.<SkillHierarchyEventDTO>builder()
                .requestPayload(hierarchyEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.SKILL_HIERARCHY_UPDATED, eventDTO);
    }
}
