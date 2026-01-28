package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.domain.application.AppService;
import com.platformcommons.platform.service.domain.application.AttachmentServiceNonMT;
import com.platformcommons.platform.service.domain.application.AuthorService;
import com.platformcommons.platform.service.domain.application.constant.DomainConstant;
import com.platformcommons.platform.service.domain.domain.*;
import com.platformcommons.platform.service.domain.domain.repo.*;
import com.platformcommons.platform.service.domain.domain.vo.ApplicationExcelVO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.service.BulkUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AppServiceImpl implements AppService {


    @Autowired
    private AppRepository appRepository;

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private UseCaseRepository useCaseRepository;

    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentServiceNonMT attachmentServiceNonMT;

    @Autowired
    private FeatureRepository featureRepository;



    @Override
    public App save(App app ){
        Author author = authorService.getCurrentTenantAuthor();
        app.init(author);
        app = appRepository.save(app);
        return app;
    }



    @Override
    public App update(App app) {
       App fetchedApp = appRepository.findById(app.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request App with  %d  not found",app.getId())));

       fetchedApp.update(app);
       return appRepository.save(fetchedApp);
    }


    @Override
    public Page<App> getByPage(Integer page, Integer size) {
        return appRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        App fetchedApp = appRepository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request App with  %d  not found",id)));
        fetchedApp.deactivate(reason);
        appRepository.save(fetchedApp);
    }


    @Override
    public App getById(Long id) {
       return appRepository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request App with  %d  not found",id)));
    }

    @Override
    public App getBySlug(String slug) {
        return appRepository.findBySlug(slug)
                .orElseThrow(()-> new NotFoundException(String.format("Request App with   slug %s  not found",slug)));
    }

    @Override
    public Attachment createAttachment(Long appId, MultipartFile file) throws IOException {
        App app = getById(appId);
        Attachment attachment = new Attachment();
        attachment.setEntityId(appId);
        attachment.setEntityType(DomainConstant.ENTITY_TYPE_APP);
        attachment.setPublic(Boolean.TRUE);
        return attachmentService.uploadAttachment(file,attachment);
    }

    @Override
    public List<AttachmentDTO> getAttachments(Long appId) {
        existsById(appId);
        return attachmentServiceNonMT.getAttachmentsByEntityIdAndEntityType(appId,DomainConstant.ENTITY_TYPE_APP);
    }


    public void existsById(Long appId) {
        if(!appRepository.existsById(appId)) {
            throw new NotFoundException(String.format("App with id %d not found",appId));
        }
    }

}
