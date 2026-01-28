package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.domain.application.AuthorService;
import com.platformcommons.platform.service.domain.application.constant.DomainConstant;
import com.platformcommons.platform.service.domain.domain.*;


import com.platformcommons.platform.service.domain.domain.repo.AppRepository;
import com.platformcommons.platform.service.domain.domain.repo.DomainRepository;
import com.platformcommons.platform.service.domain.domain.repo.TagRepository;
import com.platformcommons.platform.service.domain.domain.vo.UseCaseVO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.UseCaseService;
import com.platformcommons.platform.service.domain.domain.repo.UseCaseRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.*;


@Service
public class UseCaseServiceImpl implements UseCaseService {


    @Autowired
    private UseCaseRepository repository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AttachmentService attachmentService;


    @Override
    public UseCase save(UseCase useCase){
        Author author = authorService.getCurrentTenantAuthor();
        useCase.init(author);
        useCase=repository.save(useCase);
        return useCase;
    }


    @Override
    public UseCase saveWithoutInit(UseCase useCase){
        useCase=repository.save(useCase);
        return useCase;
    }


    @Override
    public UseCase update(UseCase useCase) {
       UseCase fetchedUseCase = repository.findById(useCase.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request UseCase with  %d  not found",useCase.getId())));

       fetchedUseCase.update(useCase);
       return repository.save(fetchedUseCase);
    }


    @Override
    public Page<UseCase> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        UseCase fetchedUseCase = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request UseCase with  %d  not found",id)));
        fetchedUseCase.deactivate(reason);
        repository.save(fetchedUseCase);
    }


    @Override
    public UseCase getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request UseCase with  %d  not found",id)));
    }

    @Override
    public void mapUseCaseToDomains(Long useCaseId, List<String> domainCodes) {
        UseCase useCase=repository.findById(useCaseId)
                .orElseThrow(()-> new NotFoundException(String.format("Request UseCase with  %d  not found",useCaseId)));
        useCase.setDomainList(new HashSet<>(domainRepository.findAllByCode(domainCodes)));;
        repository.save(useCase);
    }

    @Override
    public void mapUseCaseToTags(Long useCaseId, List<Long> tagIds) {
        UseCase useCase=repository.findById(useCaseId)
                .orElseThrow(()-> new NotFoundException(String.format("Request UseCase with  %d  not found",useCaseId)));
        useCase.getTagList().addAll(new HashSet<>(tagRepository.findAllById(tagIds)));
        repository.save(useCase);

    }

    @Override
    public void mapUseCaseToApps(Long useCaseId, List<Long> appIds) {
        UseCase useCase=repository.findById(useCaseId)
                .orElseThrow(()-> new NotFoundException(String.format("Request UseCase with  %d  not found",useCaseId)));
        useCase.setAppList(appRepository.findAllById(appIds).stream().collect(Collectors.toSet()));;
        repository.save(useCase);
    }

    @Override
    public Page<Domain> getDomainsByUseCaseId(Long useCaseId,Integer page, Integer size) {
        return repository.getDomainsByUseCaseId(useCaseId,PageRequest.of(page,size));
    }

    @Override
    public List<UseCaseVO> getUseCaseByIds(List<Long> useCaseIds) {
        return repository.findByIds(useCaseIds);
    }

    @Override
    public Attachment createAttachment(Long useCaseId, MultipartFile file) throws IOException {
        UseCase useCase = getById(useCaseId);
        Attachment attachment = new Attachment();
        attachment.setEntityId(useCaseId);
        attachment.setEntityType(DomainConstant.ENTITY_TYPE_USE_CASE);
        attachment.setPublic(Boolean.TRUE);
        return attachmentService.uploadAttachment(file,attachment);
    }

    @Override
    public List<AttachmentDTO> getAttachments(Long useCaseId) {
        existsById(useCaseId);
        return attachmentService.getAttachmentsByEntityIdAndEntityType(useCaseId,DomainConstant.ENTITY_TYPE_USE_CASE);
    }

    public void existsById(Long useCaseId) {
        if(!repository.existsById(useCaseId)) {
            throw new NotFoundException(String.format("UseCase with id %d not found",useCaseId));
        }
    }

    @Override
    public Page<Tag> getTagsForUseCase(Long useCaseId, Integer page, Integer size, Sort sort) {
        return repository.getTagsByUseCaseId(useCaseId,PageRequest.of(page,size,sort));
    }

    @Override
    public List<UseCase> createUseCaseBatch(LinkedList<UseCase> useCases) {
        Author author = authorService.getCurrentTenantAuthor();
        useCases.forEach(it-> it.init(author));
        return repository.saveAll(useCases);
    }

    @Override
    public List<UseCase> findAllByCode(Set<String> sets) {
        return repository.findAllByCode(sets);
    }

    @Override
    public UseCase findByCode(String code) {
        return  repository.findByCode(code).stream().findFirst()
                .orElseThrow(()-> new NotFoundException(String.format("UseCase with code %s not found",code)));
    }

    @Override
    public List<UseCase> findAllById(Set<Long> collect) {
        return repository.findAllById( collect );
    }


}
