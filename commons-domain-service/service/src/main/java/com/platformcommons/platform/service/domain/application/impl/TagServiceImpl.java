package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.service.domain.application.DomainService;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;


import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.domain.domain.repo.TagHierarchyRepository;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.TagService;
import com.platformcommons.platform.service.domain.domain.repo.TagRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;


import java.util.*;


@Service
public class TagServiceImpl implements TagService {


    @Autowired
    private TagRepository repository;
    @Autowired
    private TagHierarchyRepository tagHierarchyRepository;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private DomainService domainService;


    @Override
    public Tag save(Tag tag ){
        return repository.save(tag);
    }



    @Override
    public Tag update(Tag tag) {
       Tag fetchedTag = repository.findById(tag.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Tag with  %d  not found",tag.getId())));

       fetchedTag.update(tag);
       return repository.save(fetchedTag);
    }


    @Override
    public Page<Tag> getByPage(Integer page, Integer size,String context) {
        return repository.findAllWithContext(context,PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Tag fetchedTag = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Tag with  %d  not found",id)));
        fetchedTag.deactivate(reason);
        repository.save(fetchedTag);
    }


    @Override
    public Tag getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Tag with  %d  not found",id)));
    }

    @Override
    public List<Tag> saveAll(LinkedList<Tag> tags) {
        return repository.saveAll(tags);
    }

    @Override
    public Set<Tag> getByCodes(Set<String> codeList) {
        return repository.findByCodes(codeList);
    }

    @Override
    public Set<Tag> getByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public Page<Tag> getTags(Integer page, Integer size, String context, Boolean isRoot, String type) {
        return repository.getTags(context,isRoot,type,PageRequest.of(page,size));
    }
    @Override
    public Tag attachDomainsToTag(Long tagId, Set<Long> domainIds) {
        Tag tag = repository.findById(tagId)
                .orElseThrow(()-> new NotFoundException(String.format("Request Tag with  %d  not found",tagId)));
        Map<Long, Domain> domainMap = tag.getDomainList().stream()
                                          .collect(Collectors.toMap(Domain::getId, Function.identity()));
        domainIds.removeAll(domainMap.keySet());
        Set<Domain> domains = domainService.getDomainsByIds(domainIds);
        tag.getDomainList().addAll(domains);

        return repository.save(tag);
    }

    @Override
    public Page<Tag> getTagsByType(String type, Integer page, Integer size) {
        return repository.getTagsByType(type,PageRequest.of(page,size));
    }


    @Override
    public Set<TagHierarchy> getSubTags(Set<Long> parentTagIds, Long depth, String context, String type) {
        if(depth==null) depth = 100L;
        Set<Long> currentSubTagIds = new HashSet<>(parentTagIds);
        for (long currentDepth = 1L; currentDepth<depth; currentDepth++){
            Set<Long> subTagIds= tagHierarchyRepository.findSubTagsIds(currentSubTagIds, 1L, context, type);
            if(subTagIds.isEmpty()) {
                break;
            }
            parentTagIds.addAll(currentSubTagIds);
            currentSubTagIds.clear();
            currentSubTagIds.addAll(subTagIds);
        }
        return tagHierarchyRepository.findSubTags(parentTagIds, 1L, context, type);
    }

    @Override
    public Set<TagHierarchy> getSubTagsByExactDepth(Long parentTagId, Long depth, String context, String type) {
        return tagHierarchyRepository.findByParentTagIdAndExactDepth(parentTagId, depth, context, type);
    }
}
