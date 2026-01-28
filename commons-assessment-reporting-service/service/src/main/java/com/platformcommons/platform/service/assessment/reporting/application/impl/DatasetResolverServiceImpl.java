package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.ResolvedHierarchyDTO;
import com.platformcommons.platform.service.assessment.reporting.application.DatasetResolverService;
import com.platformcommons.platform.service.assessment.reporting.application.utility.DatasetUtil;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessseHierarchyResolver;
import com.platformcommons.platform.service.assessment.reporting.facade.client.DatasetClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatasetResolverServiceImpl implements DatasetResolverService {

    private final DatasetClient datasetClient;




    @Override
    public String resolveValue(AssessseHierarchyResolver resolver, AssesseDim assesseDim) {

        String datasetCode = resolver.getDatasetCode();
        String defaultParams = resolver.getDefaultParams();

        if (datasetCode == null) {
            return null;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("ACTOR_ID", assesseDim.getAssesseeActorId());

        String datasetParams = DatasetUtil.createParams(params);


        List<ResolvedHierarchyDTO> resolvedHierarchyDTOS = datasetClient.resolvedValue(datasetCode, datasetParams, PlatformSecurityUtil.getToken()).getBody();
        return resolvedHierarchyDTOS.stream().findAny().map(ResolvedHierarchyDTO::getResolvedValue).orElse(null);

    }



}