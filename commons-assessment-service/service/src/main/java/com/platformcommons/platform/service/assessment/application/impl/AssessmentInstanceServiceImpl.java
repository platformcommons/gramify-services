package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceAccessorService;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceService;
import com.platformcommons.platform.service.assessment.application.constant.OwnedBy;
import com.platformcommons.platform.service.assessment.application.constant.ServiceConstants;
import com.platformcommons.platform.service.assessment.application.search.SearchService;
import com.platformcommons.platform.service.assessment.application.utility.PageUtil;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentInstanceNontMTRepository;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentInstanceRepository;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceAccessibilityVO;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceSearchDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.client.DatasetClient;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AssessmentInstanceServiceImpl implements AssessmentInstanceService {

    @Value("${assessment.service.appKey}")
    private String APP_KEY;
    private static String GET_USER;

    private final AssessmentInstanceRepository repository;
    private final AssessmentInstanceNontMTRepository notMTRepository;
    private AssessmentInstanceAccessorService assessmentInstanceAccessorService;
    private final DatasetClient datasetClient;
    private SearchService assessmentSearchService;
    private final AssessmentInstanceDTOAssembler assemlber;
    private final EntityManager entityManager;

    public AssessmentInstanceServiceImpl(AssessmentInstanceRepository repository, AssessmentInstanceNontMTRepository notMTRepository, DatasetClient datasetClient,@Value("${commons-assessment-service.datasets.get-assessment-author-name:COMMONS_ASSESSMENT_ORG-GetUserName}") String dataSetName,AssessmentInstanceDTOAssembler assemlber, EntityManager entityManager) {
        this.repository = repository;
        this.notMTRepository = notMTRepository;
        this.datasetClient = datasetClient;
        AssessmentInstanceServiceImpl.GET_USER = dataSetName;
        this.assemlber = assemlber;
        this.entityManager = entityManager;
    }

    @SuppressWarnings("CommentedOutCode")
    @Override
    public AssessmentInstance createAssessmentInstance(AssessmentInstance assessmentInstance) {
//        if(!assessmentQuestionPaperService.checkQuestionPaperForAssessment(assessmentInstance.getAssessment().getId()))
//            throw new UnprocessableEntity("Assessment Question Paper must exist to create and instance");
//        if(repository.existsByAssessment_Id(assessmentInstance.getAssessment().getId()))
//            throw new DuplicateResourceException("Assessment Instance Already exists");
        return repository.save(assessmentInstance.init());
    }
    @Override
    public Page<AssessmentInstance> getAllAssessmentInstances(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }
    @Override
    public AssessmentInstance getAssessmentInstanceByIdv2(Long assessmentInstanceId) {
        AssessmentInstance assessmentInstance = notMTRepository.findByIdFetchAssessment(assessmentInstanceId)
                .orElseThrow(()->new NotFoundException("ERR_SVC_AIN_NOT_FOUND"));
        if(visibilityCheck(assessmentInstance.getIsPublic(),
                           assessmentInstance.getSpecificVisibility(),
                           assessmentInstance.getCreatedByUser(),
                           assessmentInstance.getTenantId(),
                           assessmentInstance.getId())) {
            return assessmentInstance;
        }
        throw new UnAuthorizedAccessException("Cannot access private assessment");
    }

    @Override
    public AssessmentInstanceAccessibilityVO getAssessmentInstanceAccessibilityVOById(Long assessmentInstanceId) {
        AssessmentInstanceAccessibilityVO assessmentInstance = notMTRepository.findAccessibilityVOById(assessmentInstanceId)
                .orElseThrow(()->new NotFoundException("ERR_SVC_AIN_NOT_FOUND"));
        if(visibilityCheck(assessmentInstance.getIsPublic(),
                assessmentInstance.getSpecificVisibility(),
                assessmentInstance.getCreatedUserId(),
                assessmentInstance.getTenantId(),
                assessmentInstance.getAssessmentInstanceId())) {
            return assessmentInstance;
        }
        return null;
    }

    @Override
    public boolean getAssessmentInstanceByLinkedSystemIdAndLinkedSystemType(Long linkedSystemId, String linkedSystemType) {
        return repository.existsByLinkedSystemIdAndLinkedSystemType(String.valueOf(linkedSystemId),linkedSystemType);
    }

    private boolean visibilityCheck(Boolean isPublic, Boolean specificVisibility, Long createdByUser, Long tenantId, Long instanceId) {

        if(PlatformSecurityUtil.isPlatformAppToken()){
            return nullCheckBool(isPublic) && !nullCheckBool(specificVisibility);
        }
        if(PlatformSecurityUtil.isTenantAdmin()){
            return true;
        }
        if(createdByUser.equals(PlatformSecurityUtil.getCurrentUserId())){
            return true;
        }
        if( nullCheckBool(isPublic) && nullCheckBool(specificVisibility)){
            appTokenCheck();
            return assessmentInstanceAccessorService.accessibleInstance(instanceId);
        }
        if(!nullCheckBool(isPublic) && !nullCheckBool(specificVisibility)){
            appTokenCheck();
            return tenantId.equals(PlatformSecurityUtil.getCurrentTenantId());
        }
        if(!nullCheckBool(isPublic) && nullCheckBool(specificVisibility)){
            appTokenCheck();
            return assessmentInstanceAccessorService.accessibleInstance(instanceId) && tenantId.equals(PlatformSecurityUtil.getCurrentTenantId());
        }
        return nullCheckBool(isPublic) && !nullCheckBool(specificVisibility);
    }
    private void appTokenCheck() {
        if(PlatformSecurityUtil.isPlatformAppToken()){
            throw new UnAuthorizedAccessException("Not authorized to access the resource");
        }
    }

    private boolean nullCheckBool(Boolean b) {
        return b!=null && b;
    }

    @Override
    public PageDTO<AssessmentInstanceDetailVO> getAssessmentInstanceInfo(String domain, OwnedBy ownedBy, String subdomain, String status, String sortBy, String sortOrder, Integer page, Integer size, String language, String text, String context) {
        if(text!=null){
            return searchByAssessmentNameForWorkSpace(domain,ownedBy,subdomain,status,sortBy,sortOrder,page,size,language,text,context);
        }
        if(!sortOrder.equalsIgnoreCase("asc")) sortOrder="desc";
        List<Map<String,Object>> resultSet= notMTRepository.getAssessmentInstanceInfoForAll(domain,PlatformSecurityUtil.getCurrentUserId(),
                PlatformSecurityUtil.getCurrentTenantId(),subdomain,
                status,language,PlatformSecurityUtil.getCurrentUserLogin(),ownedBy.name(),
                sortBy,sortOrder,page*size,size,context);
        int count = notMTRepository.getAssessmentInstanceInfoForAllCount(domain,PlatformSecurityUtil.getCurrentUserId(),
                PlatformSecurityUtil.getCurrentTenantId(),subdomain,
                status,language,PlatformSecurityUtil.getCurrentUserLogin(),ownedBy.name(),context);
        int totalPages = (int)Math.ceil((double)count/size);
        PageDTO<AssessmentInstanceDetailVO> pageDTO = new PageDTO<>(resultSet.stream().map(AssessmentInstanceDetailVO::mapToAssessmentInstanceDetailVO).collect(Collectors.toCollection(LinkedHashSet::new)),page<totalPages,count);
        pageDTO.getElements().forEach(instance->{
            Long createdBy = Long.parseLong(instance.getAuthor());
            instance.setEditable(createdBy.equals(PlatformSecurityUtil.getCurrentUserId()));
        });
        return pageDTO;
    }
    private PageDTO<AssessmentInstanceDetailVO> searchByAssessmentNameForWorkSpace(String domain, OwnedBy ownedBy, String subdomain, String status, String sortBy, String sortOrder, Integer page, Integer size, String language, String text, String context) {

        List<Long> resultSet= notMTRepository.getAssessmentInstanceInfoIdForAll(domain,PlatformSecurityUtil.getCurrentUserId(),
                PlatformSecurityUtil.getCurrentTenantId(),subdomain,
                status,language,PlatformSecurityUtil.getCurrentUserLogin(),ownedBy.name(),context);

        return assessmentSearchService.searchByAssessmentName(language,page,size,sortBy,sortOrder,text,resultSet);

    }
    @Override
    public AssessmentInstance updateAssessmentInstanceV2(AssessmentInstance assessmentInstance) {
        Boolean verified = Boolean.TRUE/*AssessmentSecurityUtil.isVerified()*/;
        if(!verified){
            if(assessmentInstance.getScheduleStatus()!=null && (assessmentInstance.getScheduleStatus().equals(ServiceConstants.PUBLISH) ||
                    assessmentInstance.getScheduleStatus().equals(ServiceConstants.ASSESSMENT_RELEASE)) ){
                throw new UnAuthorizedAccessException("User must be verified to publish an assessment!");
            }
        }

        AssessmentInstance assessmentInstanceExists = repository.findByIdAndUserId(assessmentInstance.getId(),PlatformSecurityUtil.getCurrentUserId())
                                                                .orElseThrow(() -> new NotFoundException("ERR_SVC_AIN_NOT_FOUND"));
        assessmentInstanceExists.update(assessmentInstance);
        return repository.save(assessmentInstanceExists);
    }
    @Override
    public Long deleteAssessmentInstanceV2(Long id, String reason) {
        AssessmentInstance ai = repository.findById(id).orElseThrow(()->new NotFoundException("ERR_SVC_AIN_NOT_FOUND"));
        ai.deactivate(reason);
        repository.save(ai);
        return id;
    }
    @Override
    public Map<Long, String> getAssessmentInstanceCreatedByName(Set<String> userIds) {

        String params="IN_PARAM_USER_ID="+ String.join(",", userIds);
        List<Map<String,Object>> result;
        if(!PlatformSecurityUtil.isPlatformAppToken()){
            if(PlatformSecurityUtil.getActorContext().getActorContext().equals("ACTOR_TYPE.BRIDGE_USER")) {
                result = datasetClient.executeQueryV3(GET_USER, params, 0, userIds.size(), PlatformSecurityUtil.getToken());
            }
            else {
                return new LinkedHashMap<>();
            }
        }
        else {
            result= datasetClient.executeQueryV3AppKey(GET_USER,params,0,userIds.size(),String.format("Appkey %s",APP_KEY));
        }

        return result!=null ?result.stream().collect(Collectors.toMap( el -> Long.parseLong(el.get("id").toString()),
                el -> el.get("name").toString() )): null;
    }

    @Override
    public AssessmentInstance getAssessmentInstanceByAssessmentId(Long asssessmentId) {
        return repository.findByAssessmentId(asssessmentId).orElseThrow(()->new NotFoundException("ERR_SVC_AIN_NOT_FOUND"));
    }

    @Override
    public Set<AssessmentInstance> getAssessmentInstanceByAssessmentType(String assessmentType, Boolean assessmentIsActive, Boolean isActive) {
        return repository.findByIsActiveAndAndAssessment_IsActiveAndAssessment_AssessmentType_Code(isActive,assessmentIsActive,assessmentType,PlatformSecurityUtil.getCurrentTenantId());
    }

    @Override
    public Page<AssessmentInstanceSearchDTO> getAllAssessmentInstanceSearchDTO(Integer page, Integer size) {
        return repository.findAllSearchDTO(PageRequest.of(page, size));
    }

    @Override
    public PageDTO<AssessmentInstanceDTO> getAssessmentInstanceByAssessmentTypes(Set<String> assessmentTypes, Set<String> assessmentKind, String assessmentSubType, Boolean assessmentIsActive, String context, Boolean isActive, Integer page, Integer size, String forEntityId, String forEntityType) {
        Long count = repository.findByAssessment_AssessmentTypeInAndAssessment_IsActiveAndIsActiveCount(assessmentTypes,assessmentKind,assessmentSubType,context,assessmentIsActive,isActive,forEntityId,forEntityType);
        Set<AssessmentInstance> entity = repository.findByAssessment_AssessmentTypeInAndAssessment_IsActiveAndIsActive(assessmentTypes,assessmentKind,assessmentSubType,context,assessmentIsActive,isActive,forEntityId,forEntityType,page*size,size);
        return PageUtil.getPage(entity,count,page,size,assessmentInstance -> assemlber.toDTO(assessmentInstance,null));
    }

    @Override
    public Long getAssessmentInstanceIdByAssessmentId(Long assessmentId) {
        return notMTRepository.getAssessmentInstanceIdByAssessmentId(assessmentId);
    }



    @Autowired
    private void setAssessmentSearchService(SearchService service){
        this.assessmentSearchService=service;
    }
    @Autowired
    public void setAssessmentInstanceAccessorService(AssessmentInstanceAccessorService assessmentInstanceAccessorService) {
        this.assessmentInstanceAccessorService = assessmentInstanceAccessorService;
    }
}
