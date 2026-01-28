package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.application.DomainService;
import com.platformcommons.platform.service.domain.application.PlatformService;
import com.platformcommons.platform.service.domain.application.UseCaseService;
import com.platformcommons.platform.service.domain.application.constant.AppConstant;
import com.platformcommons.platform.service.domain.application.constant.FeatureConstant;
import com.platformcommons.platform.service.domain.application.constant.PlatformConstant;
import com.platformcommons.platform.service.domain.application.utility.ExcelVOUtil;
import com.platformcommons.platform.service.domain.domain.*;
import com.platformcommons.platform.service.domain.domain.vo.ApplicationExcelVO;
import com.platformcommons.platform.service.domain.dto.AppDTO;
import com.platformcommons.platform.service.domain.facade.assembler.AppEventMapper;
import com.platformcommons.platform.service.domain.facade.mapper.DomainExcelMapper;
import com.platformcommons.platform.service.domain.messaging.producer.AppEventProducer;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BulkUploadRequest;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.PoiExcelHelper;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.assesmbler.BulkUploadRequestDTOAssembler;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import com.platformcommons.platform.service.sdk.bulkupload.service.BulkUploadService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.AppFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.AppService;
import com.platformcommons.platform.service.domain.facade.assembler.AppDTOAssembler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;


import java.util.*;

@Component
@Transactional
public class AppFacadeImpl implements AppFacade {


    @Autowired
    private AppService service;

    @Autowired
    private AppDTOAssembler assembler;

    @Autowired
    private BulkUploadRequestDTOAssembler bulkUploadRequestDTOAssembler;
    @Autowired
    private AppEventProducer appEventProducer;

    @Autowired
    private AppEventMapper appEventMapper;

    @Autowired
    private DomainService domainService;

    @Autowired
    private BulkUploadService bulkUploadService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private UseCaseService useCaseService;


    @Autowired
    private DomainExcelMapper domainExcelMapper;

    @Override
    public Long save(AppDTO appDTO ){
        App app=service.save(assembler.fromDTO(appDTO));
        appEventProducer.appCreated(appEventMapper.from(app));
        return  app.getId();
    }


    @Override
    public AppDTO update(AppDTO appDTO ){
        App app=service.update(assembler.fromDTO(appDTO));
        appEventProducer.appUpdated(appEventMapper.from(app));
        return assembler.toDTO(app);
    }

    @Override
    public PageDTO<AppDTO> getAllPage(Integer page, Integer size){
        Page<App> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public AppDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public AppDTO getBySlug(String slug) {
        return assembler.toDTO(service.getBySlug(slug));
    }

    @Override
    public AttachmentDTO createAttachment(Long appId, MultipartFile file) throws IOException {
        return assembler.toDTO(service.createAttachment(appId,file));
    }

    @Override
    public List<AttachmentDTO> getAttachments(Long appId) {
        return service.getAttachments(appId);
    }

    @Override
    public BulkUploadRequestDTO uploadApplications(MultipartFile file) throws Exception {

        BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload( file , AppConstant.ENTITY_TYPE_APP_BULK );

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<ApplicationExcelVO> applicationExcelVOS = PoiExcelHelper.sheetToPOJO(  workbook.getSheetAt(0), ApplicationExcelVO.class);
        bulkUploadService.operate( applicationExcelVOS , applicationExcelVO -> {

                    List<String> useCaseNames =Arrays.asList(applicationExcelVO.getUseCaseCodes().split("--"));
                    Set<String> useCaseCodes = useCaseNames.stream().map(it->domainExcelMapper.convertTOCode(it,"USE_CASE"))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    List<UseCase> appUseCases = useCaseService.findAllByCode(useCaseCodes);



                    Set<Feature> features=ExcelVOUtil.getSets(applicationExcelVO.getFeatures(),"--").stream()
                            .map(feature -> Feature.builder()
                                    .onAppVersion(applicationExcelVO.getCurrentVersion())
                                    .name(feature)
                                    .code(domainExcelMapper.convertTOCode(feature,"FEATURE"))
                                    .id(0L)
                                    .type(FeatureConstant.FEATURE_TYPE_KEY)
                                    .build())
                            .collect(Collectors.toSet());

                    if(applicationExcelVO.getMultilingual()!=null && !applicationExcelVO.getMultilingual().isEmpty() &&
                            applicationExcelVO.getMultilingual().equalsIgnoreCase("yes")){
                        features.add(Feature.builder()
                                .onAppVersion(applicationExcelVO.getCurrentVersion())
                                .code(FeatureConstant.FEATURE_MULTILINGUAL_CODE)
                                .name(FeatureConstant.FEATURE_MULTILINGUAL)
                                .id(0L)
                                .type(FeatureConstant.FEATURE_TYPE_USP)
                                .build());

                    }

                    if(applicationExcelVO.getOffline()!=null && !applicationExcelVO.getOffline().isEmpty() &&
                            applicationExcelVO.getOffline().equalsIgnoreCase("yes")) {
                        features.add(Feature.builder()
                                .onAppVersion(applicationExcelVO.getCurrentVersion())
                                .code(FeatureConstant.FEATURE_OFFLINE_CODE)
                                .name(FeatureConstant.FEATURE_OFFLINE)
                                .id(0L)
                                .type(FeatureConstant.FEATURE_TYPE_USP)
                                .build());
                    }

                    if(applicationExcelVO.getWhitelabel()!=null && !applicationExcelVO.getWhitelabel().isEmpty() &&
                            applicationExcelVO.getWhitelabel().equalsIgnoreCase("yes")){
                        features.add(Feature.builder()
                                .onAppVersion(applicationExcelVO.getCurrentVersion())
                                .code(FeatureConstant.FEATURE_MULTILINGUAL_CODE)
                                .name(FeatureConstant.FEATURE_WHITELABEL)
                                .id(0L)
                                .type(FeatureConstant.FEATURE_TYPE_USP)
                                .build());
                    }

                    App app = App.builder()
                            .id(0L)
                            .name(applicationExcelVO.getName())
                            .shortDescription(applicationExcelVO.getShortDescription())
                            .longDescription(applicationExcelVO.getLongDescription())
                            .logo(applicationExcelVO.getLogo())
                             .website(applicationExcelVO.getWebsite())
                            .downloadCount(applicationExcelVO.getDownloadCount())
                            .currentVersion(applicationExcelVO.getCurrentVersion())
                            .averageRating(applicationExcelVO.getAverageRating())
                            .domainList(ExcelVOUtil.getSets(domainService.getFindAllDomainsByCodes(ExcelVOUtil.getSets(applicationExcelVO.getDomainCodes(),","))))
                            .subDomainList(ExcelVOUtil.getSets(domainService.getFindAllDomainsByCodes(ExcelVOUtil.getSets(applicationExcelVO.getSubDomainCodes(),","))))
                            .useCaseList(ExcelVOUtil.getSets(appUseCases))
                            .platformList(ExcelVOUtil.getSets(applicationExcelVO.getPlatformNames(),",").stream()
                                    .map( platformName -> Platform.builder()
                                            .name( platformName )
                                            .code( PlatformConstant.TEMPLATE + platformName.toUpperCase() )
                                            .id(0L)
                                            .build()
                                    ).collect(Collectors.toSet())
                            )
                            .featureList(features)
                            .searchKeywordList(ExcelVOUtil.getSets(applicationExcelVO.getKeywords(),",").stream()
                                    .map(el -> SearchKeyword.builder().textValue(el).build())
                                    .collect(Collectors.toSet())
                            ).build();

                    app.getFeatureList().forEach(feature -> feature.setAppId(app));
                    app.getPlatformList().forEach(platform -> platform.setAppId(app));
                    //Variable in Lambda expression should be final or effectively final
                    App savedApp = service.save(app);
                    appEventProducer.appCreated(appEventMapper.from(savedApp));
                },
                bulkUploadRequest
            );
        return bulkUploadRequestDTOAssembler.toDTO(bulkUploadRequest);

    }

    @Override
    public AppDTO getByIdOrSlug(String id) {
        try{
            Long appId = Long.parseLong(id);
            return  this.getById(appId);
        }
        catch (NumberFormatException ex){
            return getBySlug(id);
        }
    }

}
