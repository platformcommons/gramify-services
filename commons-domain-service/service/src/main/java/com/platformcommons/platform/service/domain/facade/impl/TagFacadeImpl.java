package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.domain.application.TagHierarchyService;
import com.platformcommons.platform.service.domain.application.utility.PageUtil;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.TagV2DTO;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import com.platformcommons.platform.service.domain.facade.TagHierarchyDTOAssembler;
import com.platformcommons.platform.service.domain.messaging.producer.TagEventProducer;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.TagFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.TagService;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;

import java.util.stream.Collectors;


import java.util.*;

@Component
@Transactional
@RequiredArgsConstructor
public class TagFacadeImpl implements TagFacade {


    private final TagService service;

    private final TagDTOAssembler assembler;
    private final TagHierarchyDTOAssembler tagHierarchyDTOAssembler;

    @Autowired
    private TagEventProducer tagEventProducer;
    private final TagHierarchyService tagHierarchyService;


    @Override
    public Long save(TagDTO tagDTO ){
        TagDTO savedTagDTO = assembler.toDTO(service.save(assembler.fromDTO(tagDTO)));
        tagEventProducer.tagCreated(savedTagDTO);
        return savedTagDTO.getId();
    }


    @Override
    public TagDTO update(TagDTO tagDTO ){
        TagDTO updatedTagDTO = assembler.toDTO(service.update(assembler.fromDTO(tagDTO)));
        tagEventProducer.tagUpdated(updatedTagDTO);
        return updatedTagDTO;
    }

    @Override
    public PageDTO<TagDTO> getAllPage(Integer page, Integer size,String context, Boolean includeRefLabels){
        includeRefLabels= includeRefLabels!=null ? includeRefLabels: Boolean.FALSE;
        Page<Tag> result= service.getByPage(page,size,context);
        Boolean finalIncludeRefLabels = includeRefLabels;
        return new PageDTO<>(result.stream().map(it-> assembler.toDTO(it, finalIncludeRefLabels))
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public TagDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public TagDTO getTagByCode(String code) {
        return assembler.toDTO(service.getByCodes(Collections.singleton(code)).stream()
                .findFirst().orElseThrow((()-> new NotFoundException("Tag not found for code "+code))));
    }

    @Override
    public TagDTO getTagByCodeV2(String code) {
        return service.getByCode(code).stream()
                .findFirst().map(assembler::toDTO).orElse(null);
    }


    @Override
    public PageDTO<TagDTO> getTagByCodes(Set<String> codes){
        Set<Tag> tags =  service.getByCodes(codes);
        return new PageDTO<>(tags.stream().map(it->assembler.toDTO(it)).collect(Collectors.toCollection(LinkedHashSet::new)), Boolean.FALSE);
    }

    @Override
    public void saveAll(List<TagDTO> body) {
        List<TagDTO> savedTagDTOS = assembler.toDTOs(service.saveAll(body.stream().map(assembler::fromDTO)
                .collect(Collectors.toCollection(LinkedList::new))));
        savedTagDTOS.forEach(tagDTO -> tagEventProducer.tagCreated(tagDTO));
    }

    @Override
    public void addTagHierarchyBatch(List<Long> childTagIds, Long parentTagId) {
        childTagIds.forEach(childTagId->{
            tagHierarchyService.createHierarchy(childTagId,parentTagId);
        });
    }

    @Override
    public void deleteTagHierarchy(Long parentTagId, Long childTagId) {
        tagHierarchyService.deleteHierarchy(parentTagId, childTagId);
    }

    @Override
    public PageDTO<TagDTO> getTags(Integer page, Integer size, String context, Boolean isRoot, String type) {
        return PageUtil.getPage(service.getTags(page,size,context,isRoot,type),assembler::toDTO);
    }

    @Override
    public Set<TagHierarchyDTO> getSubTags(Set<Long> parentTagIds, Long depth, String context, String type) {
        return tagHierarchyDTOAssembler.toDTOs(service.getSubTags(parentTagIds, depth, context, type));
    }

    @Override
    public Set<TagDTO> getSubTagsByParentId(Long parentTagId, Long depth, String context, String type) {
        Set<TagHierarchy> tagHierarchies = service.getSubTagsByExactDepth(parentTagId, depth, context, type);
        return tagHierarchies.stream()
                .map(th -> assembler.toDTO(th.getTag()))
                .collect(Collectors.toSet());
    }


    @Override
    public TagV2DTO attachDomainsToTag(Long tagId, Set<Long> domainIds) {
        return assembler.toV2DTO(service.attachDomainsToTag(tagId,domainIds));
    }

    @Override
    public PageDTO<TagV2DTO> getTagsByType(String type, Integer page, Integer size) {
        Page<Tag> result=service.getTagsByType(type,page,size);
        return new PageDTO<>(result.stream().map(assembler::toV2DTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public TagDTO saveTagV2(TagDTO tagDTO) {
        TagDTO savedTagDTO = assembler.toDTO(service.save(assembler.fromDTO(tagDTO)));
        tagEventProducer.tagCreated(savedTagDTO);
        return savedTagDTO;
    }

}
