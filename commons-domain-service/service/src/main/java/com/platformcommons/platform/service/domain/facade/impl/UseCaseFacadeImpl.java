package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.application.DomainService;
import com.platformcommons.platform.service.domain.application.constant.UsecaseConstant;
import com.platformcommons.platform.service.domain.application.utility.ExcelVOUtil;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.UseCase;
import com.platformcommons.platform.service.domain.domain.vo.UseCaseExcelVO;
import com.platformcommons.platform.service.domain.domain.vo.UseCaseVO;
import com.platformcommons.platform.service.domain.dto.DomainDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.UseCaseDTO;
import com.platformcommons.platform.service.domain.dto.UseCaseEventDTO;
import com.platformcommons.platform.service.domain.facade.assembler.DomainDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.UseCaseEventMapper;
import com.platformcommons.platform.service.domain.messaging.producer.UseCaseEventProducer;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BulkUploadRequest;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.PoiExcelHelper;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.assesmbler.BulkUploadRequestDTOAssembler;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import com.platformcommons.platform.service.sdk.bulkupload.service.BulkUploadService;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.UseCaseFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.UseCaseService;
import com.platformcommons.platform.service.domain.facade.assembler.UseCaseDTOAssembler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;


import java.util.*;

@Component
@Transactional
public class UseCaseFacadeImpl implements UseCaseFacade {


    @Autowired
    private UseCaseService service;

    @Autowired
    private UseCaseDTOAssembler assembler;

    @Autowired
    private DomainDTOAssembler domainDTOassembler;

    @Autowired
    private TagDTOAssembler tagDTOAssembler;
    @Autowired
    private BulkUploadRequestDTOAssembler bulkUploadRequestDTOAssembler;

    @Autowired
    private UseCaseEventProducer useCaseEventProducer;

    @Autowired
    private UseCaseEventMapper useCaseEventMapper;

    @Autowired
    private BulkUploadService bulkUploadService;
    @Autowired
    private DomainService domainService;

    @Override
    public Long save(UseCaseDTO useCaseDTO ){
        UseCase useCase= service.save(assembler.fromDTO(useCaseDTO));
        useCaseEventProducer.useCaseCreated(useCaseEventMapper.from(useCase));
        return useCase.getId();
    }


    @Override
    public UseCaseDTO update(UseCaseDTO useCaseDTO ){
        UseCase useCase=service.update(assembler.fromDTO(useCaseDTO));
        useCaseEventProducer.useCaseUpdated(useCaseEventMapper.from(useCase));
        return assembler.toDTO(useCase);
    }

    @Override
    public PageDTO<UseCaseDTO> getAllPage(Integer page, Integer size){
        Page<UseCase> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public UseCaseDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public void mapUseCaseToDomains(Long useCaseId, List<String> domainCodes) {
        service.mapUseCaseToDomains(useCaseId, domainCodes);
    }

    @Override
    public void mapUseCaseToTags(Long useCaseId, List<Long> tagIds) {
        service.mapUseCaseToTags(useCaseId, tagIds);
    }

    @Override
    public void mapUseCaseToApps(Long useCaseId, List<Long> appIds) {
        service.mapUseCaseToApps(useCaseId,appIds);
    }

    @Override
    public PageDTO<DomainDTO> getDomainsByUseCaseId(Long useCaseId,Integer page, Integer size) {
        Page<Domain> domains=service.getDomainsByUseCaseId(useCaseId,page,size);
        return  new PageDTO<>(domains.stream().map(domainDTOassembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),domains.hasNext());

    }

    @Override
    public List<UseCaseVO> getUseCaseByIds(List<Long> useCaseIds) {
        return service.getUseCaseByIds(useCaseIds);
    }

    @Override
    public AttachmentDTO createAttachment(Long useCaseId, MultipartFile file) throws IOException {
        return assembler.toDTO(service.createAttachment(useCaseId,file));
    }

    @Override
    public List<AttachmentDTO> getAttachments(Long useCaseId) {
        return service.getAttachments(useCaseId);
    }

    @Override
    public PageDTO<TagDTO> getTagsForUseCase(Long useCaseId, Integer page, Integer size, String sortBy, String direction) {
        Page<Tag> result= service.getTagsForUseCase(useCaseId,page,size, JPAUtility.convertToSort(sortBy,direction));
        return new PageDTO<>(result.stream().map(tagDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public void createUseCaseBatch(List<UseCaseDTO> useCaseDTOs) {
       List<UseCase>  savedUseCases =service.createUseCaseBatch(useCaseDTOs.stream().map(assembler::fromDTO)
               .collect(Collectors.toCollection(LinkedList::new)));
       List<UseCaseEventDTO> useCaseEventDTOS = savedUseCases.stream().map(useCaseEventMapper::from)
               .collect(Collectors.toCollection(LinkedList::new));
       useCaseEventDTOS.forEach(it->{
           useCaseEventProducer.useCaseCreated(it);
       });
    }

    @Override
    public BulkUploadRequestDTO uploadUsecase(MultipartFile file) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<UseCaseExcelVO> useCaseExcelVOS = PoiExcelHelper.sheetToPOJO(  workbook.getSheetAt(0), UseCaseExcelVO.class);

        BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload( file , UsecaseConstant.ENTITY_TYPE );

        bulkUploadService.operate( useCaseExcelVOS , useCaseExcelVO -> {

            UseCase useCase = service.save(
                    UseCase.builder()
                            .id(null)
                            .name(useCaseExcelVO.getName())
                            .shortDescription(useCaseExcelVO.getShortDescription())
                            .longDescription( useCaseExcelVO.getLongDescription() )
                            .domainList(ExcelVOUtil.getSets(domainService.getFindAllDomainsByCodes(ExcelVOUtil.getSets(useCaseExcelVO.getDomainNames(),","))))
                            .subDomainList(ExcelVOUtil.getSets(domainService.getFindAllDomainsByCodes(ExcelVOUtil.getSets(useCaseExcelVO.getDomainNames(),","))))
                            .website( useCaseExcelVO.getWebsite() )
                            .build()
            );
            useCaseEventProducer.useCaseCreated(useCaseEventMapper.from(useCase));

        } , bulkUploadRequest );

        return bulkUploadRequestDTOAssembler.toDTO(bulkUploadRequest);
    }


}
