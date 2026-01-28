package com.platformcommons.platform.service.post.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.post.dto.*;
import com.platformcommons.platform.service.post.facade.PostFacade;
import com.platformcommons.platform.service.post.messaging.MessagingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProposalConsumer {

    @Autowired
    private PostFacade postFacade;


    @KafkaListener(topics = MessagingConstant.PROPOSAL_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public void  consumeBRBaseUserUpdate(@Payload EventDTO<ProposalDTO> eventDTO){

        try {
            ProposalDTO proposalDTO = eventDTO.getPayload(new TypeReference<ProposalDTO>() {});
            PlatformSecurityUtil.setEventContext(eventDTO.getUserContext(),eventDTO.getTenantContext(),eventDTO.getActorContext(),
                    null, null);
            PostDTO postDTO = fromProposal(proposalDTO);
            postFacade.savePostWithMultipleAttachments(postDTO,null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private PostDTO fromProposal(ProposalDTO proposalDTO){

        Set<PostContentDTO> postContentDTOSet = new LinkedHashSet<>();
        if(proposalDTO.getProblemStatement()!=null && !proposalDTO.getProblemStatement().isEmpty()){
            postContentDTOSet.add(buildPostContent(proposalDTO.getProblemStatement(),"PROPOSAL.PROBLEM_STATEMENT",null));
        }
        if(proposalDTO.getProposedSolution()!=null && !proposalDTO.getProposedSolution().isEmpty()){
            postContentDTOSet.add(buildPostContent(proposalDTO.getProposedSolution(),"PROPOSAL.PROPOSED_SOLUTION",null));
        }
        return PostDTO.builder()
                .id(0L)
                .title(proposalDTO.getTitle())
                .payload(proposalDTO.getProblemStatement())
                .appContext("COMMONS_APP.COMMONS_GROUND") //TODO
                .domainContext("DOMAIN.AGRICULTURE")
                .isPublic(Boolean.TRUE)
                .languageContext("ENG")
                .postType("FEED")
                .postSubType("POST_SUB_TYPE.PROPOSAL")
                .taggedToEntityId(proposalDTO.getId())
                .taggedToEntityType("ENTITY_TYPE.PROPOSAL") //TODO
                .postContents(!postContentDTOSet.isEmpty() ? postContentDTOSet:null)
                .tags(proposalDTO.getTags()!=null && !proposalDTO.getTags().isEmpty() ? proposalDTO.getTags().stream().map(this::fromProposalTag).collect(Collectors.toCollection(LinkedHashSet::new)):null)
                .build();

    }

    private PostContentDTO buildPostContent(String contentData,String contentType, String mimeType){
        if(contentData!=null && !contentData.isEmpty()){
            return PostContentDTO.builder()
                    .contentData(contentData)
                    .contentType(contentType)
                    .contentMimeType(mimeType)
                    .build();
        }
        else {
            return null;
        }
    }


    private PostTagDTO fromProposalTag(ProposalTagDTO proposalTagDTO){

        return PostTagDTO.builder()
                .id(0L)
                .tag(proposalTagDTO.getTag())
                .tagCode(proposalTagDTO.getTagCode())
                .type(proposalTagDTO.getType())
                .build();
    }

}
