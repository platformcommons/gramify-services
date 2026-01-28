package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.AssessmentService;
import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.domain.AssessmentConfig;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentNonMTRepository;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository repository;

    private final AssessmentNonMTRepository assessmentNonMTRepository;

    @Override
    public Assessment createAssessment(Assessment assessment) {
        return repository.save(assessment.init());
    }
    @Override
    public Assessment updateAssessmentV2(Assessment assessment) {
        Assessment toBeUpdated=repository.findByIdAndUserId(assessment.getId(),PlatformSecurityUtil.getCurrentUserId())
                                         .orElseThrow(()->new NotFoundException("Assessment not found"));
        return repository.save(toBeUpdated.patch(assessment));
    }

    @Override
    public Map<String, String> getConfigs(Long assessmentInstanceId) {
        return assessmentNonMTRepository.getAssessmentConfig(assessmentInstanceId).stream().collect(Collectors.toMap(AssessmentConfig::getConfigKey,AssessmentConfig::getConfigValue));
    }

    @Override
    public Set<Long> getAssessmentIdsByUUIDsAndCodes(Set<String> uuids, Set<String> codes) {
        return repository.getAssessmentIdsByUUIDsOrCodes(uuids,codes);
    }

    @Override
    public Assessment addAssessmentConfig(Long assessmentId, AssessmentConfig assessmentConfig) {
        Assessment assessment = repository.findById(assessmentId)
                .orElseThrow(() -> new NotFoundException("Assessment not found with id: " + assessmentId));
        assessment.addAssessmentConfig(assessmentConfig);
        return repository.save(assessment);
    }

    @Override
    public Assessment getAssessmentById(Long id) {
        return repository.findById(id).orElseThrow(()->new NotFoundException("Assessment not found"));
    }

    @Override
    public Assessment getAssessmentByCode(String code) {
        return repository.findByAssessmentCode(code).stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Assessment not found with code " + code));

    }
}
