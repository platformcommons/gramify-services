package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.application.DomainHierarchyService;
import com.platformcommons.platform.service.domain.application.DomainService;
import com.platformcommons.platform.service.domain.application.UseCaseService;
import com.platformcommons.platform.service.domain.application.constant.DomainConstant;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.SearchKeyword;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.UseCase;
import com.platformcommons.platform.service.domain.domain.vo.DomainExcelVO;
import com.platformcommons.platform.service.domain.domain.vo.DomainExcelVOV2;
import com.platformcommons.platform.service.domain.domain.vo.UseCaseExcelVOV2;
import com.platformcommons.platform.service.domain.dto.DomainDTO;
import com.platformcommons.platform.service.domain.dto.DomainEventDTO;
import com.platformcommons.platform.service.domain.dto.DomainHierarchyEventDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.facade.DomainFacade;
import com.platformcommons.platform.service.domain.facade.assembler.DomainDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.DomainEventMapper;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.UseCaseEventMapper;
import com.platformcommons.platform.service.domain.facade.mapper.DomainExcelMapper;
import com.platformcommons.platform.service.domain.messaging.producer.DomainEventProducer;
import com.platformcommons.platform.service.domain.messaging.producer.UseCaseEventProducer;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
public class DomainFacadeImpl implements DomainFacade {


    @Autowired
    private DomainService service;

    @Autowired
    private DomainHierarchyService domainHierarchyService;

    @Autowired
    private DomainDTOAssembler assembler;

    @Autowired
    private DomainEventProducer domainEventProducer;

    @Autowired
    private DomainEventMapper domainEventMapper;

    @Autowired
    private TagDTOAssembler tagDTOAssembler;

    @Autowired
    private BulkUploadService bulkUploadService;


    @Autowired
    private UseCaseService useCaseService;

    @Autowired
    private UseCaseEventProducer useCaseEventProducer;

    @Autowired
    private UseCaseEventMapper useCaseEventMapper;


    @Autowired
    private BulkUploadRequestDTOAssembler bulkUploadRequestDTOAssembler;

    @Autowired
    private DomainExcelMapper domainExcelMapper;


    @Override
    public Long save(DomainDTO domainDTO){
        Domain domain=service.save(assembler.fromDTO(domainDTO));
        domainEventProducer.domainCreated(domainEventMapper.from(domain));
        return domain.getId();
    }


    @Override
    public DomainDTO update(DomainDTO domainDTO ){
        Domain toBeUpdated =assembler.fromDTO(domainDTO);
        Domain domain=service.update(toBeUpdated);
        domainEventProducer.domainUpdated(domainEventMapper.from(domain));
        return assembler.toDTO(domain);
    }

    @Override
    public PageDTO<DomainDTO> getAllPage(Integer page, Integer size){
        Page<Domain> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public DomainDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public Long createDomain(DomainDTO domainDTO, String parentDomainCode) {
         Domain domain=service.save(assembler.fromDTO(domainDTO));
         Long domainID = domain.getId();
         Long parentDomainId  =null;
         if(parentDomainCode!=null){
             Domain parentDomain = service.getDomainByCode(parentDomainCode,domainDTO.getContext());
             parentDomainId = parentDomain.getId();
             domainHierarchyService.createHierarchy(domainID,parentDomainId);
         }
         domainEventProducer.domainCreated(domainEventMapper.from(domain));
         if(parentDomainId!=null){
            domainEventProducer.domainHierarchyCreated(DomainHierarchyEventDTO.builder().parentId(parentDomainId).childId(domainID).build());
         }
         return domainID;
    }


    @Override
    public PageDTO<DomainDTO> getSubDomains(Integer page, Integer size, String sortBy,
                                            String direction, String parentDomainCode,
                                            Integer depth, String context) {

        Page<Domain> domains = service.getSubDomains(page,size,sortBy,direction,parentDomainCode,depth,context);
        return  new PageDTO<>(domains.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),domains.hasNext(),domains.getTotalElements());
    }

    @Override
    public PageDTO<DomainDTO> getDomains(Integer page, Integer size, String context, Boolean isRoot) {
        Page<Domain> domains = service.getDomains(page,size,context,isRoot);
        return  new PageDTO<>(domains.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),domains.hasNext(),domains.getTotalElements());
    }

    @Override
    public List<DomainDTO> getByCodes(List<String> domainCodes, String context) {
        return service.getDomainsByCodes(domainCodes,context).stream().map(it-> assembler.toDTOWithSuggestedApp(it,Boolean.TRUE)).collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Map<String,DomainDTO> getDomainByCodes(List<String> domainCodes) {
        return service.getDomainsByCodes(domainCodes,null).stream().map(it-> assembler.toDTOWithSuggestedApp(it,Boolean.TRUE)).collect(Collectors.toMap(DomainDTO::getCode, Function.identity()));
    }

    @Override
    public AttachmentDTO createAttachment(Long domainId, MultipartFile file) throws IOException {
        return assembler.toDTO(service.createAttachment(domainId,file));
    }

    @Override
    public List<AttachmentDTO> getAttachments(Long domainId) {
        return service.getAttachments(domainId);
    }

    @Override
    public PageDTO<TagDTO> getTagsForDomain(String domainCode, Integer page, Integer size) {
        Page<Tag> tags=service. getTagsForDomain(domainCode, page, size);
        return new PageDTO<>(tags.stream().map(tagDTOAssembler::toDTO).collect(Collectors.toSet()),tags.hasNext(),
                tags.getTotalElements() );
    }

    @Override
    public void createDomainBatch(List<DomainDTO> domainDTOs) {
      List<Domain> savedDomains=  service.createBatch(domainDTOs.stream().map(assembler::fromDTO).collect(Collectors.toCollection(LinkedList::new)));
      List<DomainEventDTO> savedDomainEventDTOs = savedDomains.stream().map(domainEventMapper::from).collect(Collectors.toCollection(LinkedList::new));
      savedDomainEventDTOs.forEach(it->domainEventProducer.domainCreated(it));
    }

    @Override
    public void addBatchDomainHierarchyBatch(List<Long> childDomainIds, Long parentDomainId) {
        childDomainIds.forEach(it->{
            domainHierarchyService.createHierarchy(it,parentDomainId);
            domainEventProducer.domainHierarchyCreated(DomainHierarchyEventDTO.builder().parentId(parentDomainId).childId(it).build());
        });
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public BulkUploadRequestDTO uploadSubDomainDataFromExcelFile(Long parentDomainId, MultipartFile file) throws Exception {
        if(!PlatformSecurityUtil.isTenantAdmin()) {
            throw new UnAuthorizedAccessException("User is not Authorized to perform this operation");
        }
        BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload(file, DomainConstant.ENTITY_TYPE_DOMAIN);
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<DomainExcelVO> domainExcelVOList = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), DomainExcelVO.class);
        Domain parentDomain = service.getById(parentDomainId);

        bulkUploadService.operate(domainExcelVOList,domainExcelVO -> {
            Domain domain = service.save(domainExcelMapper.fromDomainExcelVO(domainExcelVO,parentDomain));
            domainHierarchyService.createHierarchy(domain.getId(),parentDomainId);
            domainEventProducer.domainCreated(domainEventMapper.from(domain));
            domainEventProducer.domainHierarchyCreated(DomainHierarchyEventDTO.builder()
                    .parentId(parentDomainId)
                    .childId(domain.getId())
                    .build());
        },bulkUploadRequest);
        return bulkUploadRequestDTOAssembler.toDTO(bulkUploadRequest);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public BulkUploadRequestDTO uploadThemesWithUseCases(Long parentDomainId, MultipartFile file) throws Exception {
        if(!PlatformSecurityUtil.isTenantAdmin()) {
            throw new UnAuthorizedAccessException("User is not Authorized to perform this operation");
        }
        BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload(file, DomainConstant.ENTITY_TYPE_DOMAIN_BULK);
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        List<DomainExcelVOV2> domainExcelVOList = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), DomainExcelVOV2.class);

        Domain parentDomain = service.getById(parentDomainId);
        bulkUploadService.operate(domainExcelVOList,domainExcelVO -> {
            Domain domain = service.save(domainExcelMapper.fromDomainExcelVO(domainExcelVO,parentDomain));
            domainHierarchyService.createHierarchy(domain.getId(),parentDomainId);
            domainEventProducer.domainCreated(domainEventMapper.from(domain));
            domainEventProducer.domainHierarchyCreated(DomainHierarchyEventDTO.builder()
                    .parentId(parentDomainId)
                    .childId(domain.getId())
                    .build());
        },bulkUploadRequest);
        List<UseCaseExcelVOV2> useCaseExcelVOV2List = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(1), UseCaseExcelVOV2.class);
        bulkUploadService.operate(useCaseExcelVOV2List, useCaseExcelVOV2 -> {
            UseCase useCase = useCaseService.save(
                    UseCase.builder()
                            .id(null)
                            .code(domainExcelMapper.convertTOCode(useCaseExcelVOV2.getUseCaseName(),"USE_CASE"))
                            .name(useCaseExcelVOV2.getUseCaseName().trim())
                            .shortDescription(useCaseExcelVOV2.getDescription())
                            .domainList(Collections.singleton(parentDomain))
                            .subDomainList( new LinkedHashSet<>( service.getDomainsByCodes(Collections.singletonList(domainExcelMapper
                                            .generateDomainCode(useCaseExcelVOV2.getTheme(),parentDomain.getCode())),null )))
                            .searchKeywordList(useCaseExcelVOV2.getKeywords()!=null && !useCaseExcelVOV2.getKeywords().isEmpty()?
                                              Arrays.stream(useCaseExcelVOV2.getKeywords().split(",")).filter(it->it!=null & !it.isEmpty())
                                                      .map(String::trim).map(it-> SearchKeyword.builder()
                                                      .id(null)
                                                      .textValue(it)
                                                      .build())
                                                      .collect(Collectors.toCollection(LinkedHashSet::new))
                                    : null)
                            .sequence(Long.valueOf(useCaseExcelVOV2.getSequence()))
                            .build()

            );
            useCaseEventProducer.useCaseCreated(useCaseEventMapper.from(useCase));
        },bulkUploadRequest);

        return bulkUploadRequestDTOAssembler.toDTO(bulkUploadRequest);
    }

    @Override
    public BulkUploadRequestDTO uploadUseCases(Long parentDomainId, MultipartFile file) throws Exception {
        if(!PlatformSecurityUtil.isTenantAdmin()) {
            throw new UnAuthorizedAccessException("User is not Authorized to perform this operation");
        }
        BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload(file, DomainConstant.ENTITY_TYPE_DOMAIN_BULK);
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());


        Domain parentDomain = service.getById(parentDomainId);
        List<UseCaseExcelVOV2> useCaseExcelVOV2List = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(1), UseCaseExcelVOV2.class);
        bulkUploadService.operate(useCaseExcelVOV2List, useCaseExcelVOV2 -> {
            UseCase useCase = useCaseService.save(
                    UseCase.builder()
                            .id(null)
                            .code(domainExcelMapper.convertTOCode(useCaseExcelVOV2.getUseCaseName(),"USE_CASE"))
                            .name(useCaseExcelVOV2.getUseCaseName().trim())
                            .shortDescription(useCaseExcelVOV2.getDescription())
                            .domainList(Collections.singleton(parentDomain))
                            .subDomainList( new LinkedHashSet<>( service.getDomainsByCodes(Collections.singletonList(domainExcelMapper
                                    .generateDomainCode(useCaseExcelVOV2.getTheme(),parentDomain.getCode())),null )))
                            .searchKeywordList(useCaseExcelVOV2.getKeywords()!=null && !useCaseExcelVOV2.getKeywords().isEmpty()?
                                    Arrays.stream(useCaseExcelVOV2.getKeywords().split(","))
                                            .map(String::trim).map(it-> SearchKeyword.builder()
                                                    .id(null)
                                                    .textValue(it)
                                                    .build())
                                            .collect(Collectors.toCollection(LinkedHashSet::new))
                                    : null)
                            .sequence(Long.valueOf(useCaseExcelVOV2.getSequence()))
                            .build()

            );
            useCaseEventProducer.useCaseCreated(useCaseEventMapper.from(useCase));
        },bulkUploadRequest);
        return bulkUploadRequestDTOAssembler.toDTO(bulkUploadRequest);
    }


    @Override
    public BulkUploadRequestDTO updateUseCaseSequence(Long parentDomainId, MultipartFile file) throws Exception {
        if(!PlatformSecurityUtil.isTenantAdmin()) {
            throw new UnAuthorizedAccessException("User is not Authorized to perform this operation");
        }
        BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload(file, DomainConstant.ENTITY_TYPE_DOMAIN_BULK);
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        List<UseCaseExcelVOV2> useCaseExcelVOV2List = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(1), UseCaseExcelVOV2.class);
        bulkUploadService.operate(useCaseExcelVOV2List, useCaseExcelVOV2 -> {
            UseCase useCase = useCaseService.findByCode(domainExcelMapper.convertTOCode(useCaseExcelVOV2.getUseCaseName(),"USE_CASE"));
            useCase.setSequence(Long.valueOf(useCaseExcelVOV2.getSequence()));
            useCaseService.saveWithoutInit(useCase);
            useCaseEventProducer.useCaseUpdated(useCaseEventMapper.from(useCase));
        },bulkUploadRequest);
        return bulkUploadRequestDTOAssembler.toDTO(bulkUploadRequest);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public BulkUploadRequestDTO uploadThemes(Long parentDomainId, MultipartFile file) throws Exception {
        if(!PlatformSecurityUtil.isTenantAdmin()) {
            throw new UnAuthorizedAccessException("User is not Authorized to perform this operation");
        }
        BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload(file, DomainConstant.ENTITY_TYPE_DOMAIN_BULK);
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        List<DomainExcelVOV2> domainExcelVOList = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), DomainExcelVOV2.class);

        Domain parentDomain = service.getById(parentDomainId);
        bulkUploadService.operate(domainExcelVOList,domainExcelVO -> {
            Domain domain = service.save(domainExcelMapper.fromDomainExcelVO(domainExcelVO,parentDomain));
            domainHierarchyService.createHierarchy(domain.getId(),parentDomainId);
            domainEventProducer.domainCreated(domainEventMapper.from(domain));
            domainEventProducer.domainHierarchyCreated(DomainHierarchyEventDTO.builder()
                    .parentId(parentDomainId)
                    .childId(domain.getId())
                    .build());
        },bulkUploadRequest);

        return bulkUploadRequestDTOAssembler.toDTO(bulkUploadRequest);
    }

}
