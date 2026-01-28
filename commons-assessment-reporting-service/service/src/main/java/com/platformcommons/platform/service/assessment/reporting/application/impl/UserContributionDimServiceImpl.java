package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.UserContributionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.domain.UserContributionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.UserContributionDimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserContributionDimServiceImpl implements UserContributionDimService {

    private final UserContributionDimRepository repository;

    @Override
    public void createQuestionContributions(QuestionDTO questionDTO) {
        createQuestionContribution(DimType.NONE,null);
        createQuestionContribution(DimType.DOMAIN,questionDTO.getDomain());
        createQuestionContribution(DimType.SUB_DOMAIN,questionDTO.getSubDomain());
    }

    @Override
    public void createAssessmentContributions(AssessmentDTO assessmentDTO) {
        createAssessmentContribution(DimType.NONE,null);
        createAssessmentContribution(DimType.DOMAIN,assessmentDTO.getDomain());
        createAssessmentContribution(DimType.SUB_DOMAIN,assessmentDTO.getSubDomain());
    }

    private void createAssessmentContribution(String dimType, String dimValue) {
        Set<UserContributionDim> userContributionDomainDim;
        if(!dimType.equals(DimType.NONE) && dimValue==null) return;
        if(dimType.equals(DimType.NONE) && dimValue==null) {
            userContributionDomainDim = repository.findByFilterFindByType(dimType,PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getCurrentTenantId());
        }
        else {
            userContributionDomainDim = repository.findByFilterFindByTypeAndValue(dimType, dimValue,PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getCurrentTenantId());
        }
        if(userContributionDomainDim.isEmpty()){
            createDefaultAssessmentContribution(dimType,dimValue);
        }
        else{
            repository.updateAssessmentCountByDimTypeAndUserIdAndTenantId(dimType, PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getCurrentTenantId());
        }
    }
    private void createQuestionContribution(String dimType, String dimValue) {
        Set<UserContributionDim> userContributionDomainDim;

        if(!dimType.equals(DimType.NONE) && dimValue==null) return;
        if(dimType.equals(DimType.NONE) && dimValue==null) {
            userContributionDomainDim = repository.findByFilterFindByType(dimType,PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getCurrentTenantId());
        }
        else {
            userContributionDomainDim = repository.findByFilterFindByTypeAndValue(dimType, dimValue,PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getCurrentTenantId());
        }

        if(userContributionDomainDim.isEmpty()){
            createDefaultQuestionContribution(dimType,dimValue);
        }
        else{
            repository.updateQuestionCountByDimTypeAndUserIdAndTenantId(dimType, PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getCurrentTenantId());
        }
    }
    private void createDefaultAssessmentContribution(String dimType, String dimValue) {
        UserContributionDim contributionDim = UserContributionDim.builder()
                .assessmentCount(1L)
                .questionCount(0L)
                .dimType(dimType)
                .dimValue(dimValue)
                .build();
        contributionDim.init();
        repository.save(contributionDim);
    }
    private void createDefaultQuestionContribution(String dimType, String dimValue){
        UserContributionDim contributionDim = UserContributionDim.builder()
                .assessmentCount(0L)
                .questionCount(1L)
                .dimType(dimType)
                .dimValue(dimValue)
                .build();
        contributionDim.init();
        repository.save(contributionDim);
    }


}
