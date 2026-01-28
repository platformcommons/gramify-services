package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.domain.application.DomainService;
import com.platformcommons.platform.service.domain.application.constant.DomainConstant;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.repo.DomainHierarchyRepository;
import com.platformcommons.platform.service.domain.domain.vo.DomainVO;
import com.platformcommons.platform.service.domain.domain.repo.DomainRepository;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    private DomainRepository repository;

    @Autowired
    private DomainHierarchyRepository domainHierarchyRepository;

    @Autowired
    private AttachmentService attachmentService;


    @Override
    public Domain save(Domain domain ){
        domain =repository.save(domain);
        return  domain;
    }


    @Override
    public Domain update(Domain domain) {
       Domain fetchedDomain = repository.findById(domain.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Domain with  %d  not found",domain.getId())));

       fetchedDomain.update(domain);
       return repository.save(fetchedDomain);
    }


    @Override
    public Page<Domain> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Domain fetchedDomain = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Domain with  %d  not found",id)));
        fetchedDomain.deactivate(reason);
        repository.save(fetchedDomain);
    }


    @Override
    public Domain getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Domain with  %d  not found",id)));
    }

    @Override
    public Set<Domain> getDomainsByIds(Set<Long> ids) {
        return repository.getDomainsByIds(ids);
    }

    @Override
    public Page<Domain> getSubDomains(Integer page, Integer size, String sortBy, String direction, String parentDomainCode, Integer depth, String context) {
        return domainHierarchyRepository.findSubDomains(parentDomainCode,depth!=null?depth.longValue():null,context,PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
    }

    @Override
    public Page<Domain> getDomains(Integer page, Integer size, String context, Boolean isRoot) {
        return repository.getDomains(context,isRoot,PageRequest.of(page,size));
    }

    @Override
    public Domain getDomainByCode(String domainCode, String context) {
        return repository.getDomainByCode(domainCode,context).orElseThrow(()->
                new NotFoundException(String.format("Domain with code %s not found",domainCode)));
    }

    @Override
    public List<DomainVO> getByCodes(List<String> domainCodes) {
        return repository.findByCodes(domainCodes);
    }


    @Override
    public List<Domain> getDomainsByCodes(List<String> domainCodes, String context) {
        return repository.findByDomainsByCode(domainCodes,context);
    }



    @Override
    public Attachment createAttachment(Long domainId, MultipartFile file) throws IOException {
        Domain domain = getById(domainId);
        Attachment attachment = new Attachment();
        attachment.setEntityId(domainId);
        attachment.setEntityType(DomainConstant.ENTITY_TYPE_DOMAIN);
        attachment.setPublic(Boolean.TRUE);
        return attachmentService.uploadAttachment(file,attachment);
    }

    @Override
    public List<AttachmentDTO> getAttachments(Long domainId) {
        existsById(domainId);
        return attachmentService.getAttachmentsByEntityIdAndEntityType(domainId,DomainConstant.ENTITY_TYPE_DOMAIN);
    }

    public void existsById(Long domainId) {
        if(!repository.existsById(domainId)) {
            throw new NotFoundException(String.format("Domain with id %d not found",domainId));
        }
    }

    @Override
    public Page<Tag> getTagsForDomain(String domainCode, Integer page, Integer size) {
        List<Long> subdomainIds=domainHierarchyRepository.getSubDomainIds(domainCode);
        return repository.findTagsByDomainIds(subdomainIds,DomainConstant.TAG_TYPE_SDG,PageRequest.of(page,size));
    }

    @Override
    public List<Domain> createBatch(LinkedList<Domain> domains) {
        return repository.saveAll(domains);
    }
    @Override
    public List<Domain> getFindAllDomainsByCodes(Set<String> sets) {
        return repository.findAllByCode(sets);
    }


}
