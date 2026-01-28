package com.platformcommons.platform.service.post.messaging.producer;

import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.post.dto.ProposalDTO;
import com.platformcommons.platform.service.post.messaging.MessagingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProposalProducer {

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;


    public void proposalCreated(ProposalDTO proposalDTO) {
        EventDTO<ProposalDTO> eventDTO = EventDTO.<ProposalDTO>builder()
                .requestPayload(proposalDTO)
                .withContext(Boolean.TRUE)
                .build();
        kafkaTemplate.send(MessagingConstant.PROPOSAL_CREATED, eventDTO);
    }

}
