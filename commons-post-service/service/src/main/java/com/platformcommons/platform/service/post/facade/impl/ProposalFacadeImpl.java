package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.service.post.application.ProposalService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.domain.Proposal;
import com.platformcommons.platform.service.post.dto.*;
import com.platformcommons.platform.service.post.facade.PostFacade;
import com.platformcommons.platform.service.post.facade.ProposalFacade;
import com.platformcommons.platform.service.post.facade.ResponseFacade;
import com.platformcommons.platform.service.post.facade.assembler.ProposalDTOAssembler;
import com.platformcommons.platform.service.post.messaging.producer.ProposalProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class ProposalFacadeImpl implements ProposalFacade {


    @Autowired
    private ProposalService service;

    @Autowired
    private ResponseFacade responseFacade;

    @Autowired
    private ProposalDTOAssembler assembler;

    @Autowired
    private ProposalProducer proposalProducer;

    @Autowired
    private PostFacade postFacade;

    public static  String RESPONSE_TYPE_POLL_OPTION = "RESPONSE_TYPE.POLL_OPTION";

    @Override
    public Long save(ProposalDTO proposalDTO) {
        Proposal proposal = assembler.fromDTO(proposalDTO);
        Proposal savedProposal = service.save(proposal);
      //  proposalProducer.proposalCreated(assembler.toDTO(savedProposal));


        Set<AuthorResponseDTO> authorResponseDTOS = new LinkedHashSet<>();

        AuthorResponseDTO upVoteOption=AuthorResponseDTO.builder()
                .type(RESPONSE_TYPE_POLL_OPTION)
                .payload("UP_VOTE")
                .build();
        AuthorResponseDTO downVoteOption = AuthorResponseDTO.builder()
                .type(RESPONSE_TYPE_POLL_OPTION)
                .payload("DOWN_VOTE")
                .build();
        authorResponseDTOS.add(upVoteOption);
        authorResponseDTOS.add(downVoteOption);
        responseFacade.createAuthorResponses(savedProposal.getId(), PostConstant.ENTITY_TYPE_PROPOSAL, authorResponseDTOS);

        PostDTO postDTO = fromProposal(assembler.toDTO(savedProposal));
        postFacade.savePostWithMultipleAttachments(postDTO,null);
        return savedProposal.getId();
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
