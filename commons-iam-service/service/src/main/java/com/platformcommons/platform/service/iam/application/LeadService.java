package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.LeadVO;

import java.util.List;

public interface LeadService {

    Lead getById(Long id);

    Lead getByKey(String key);

    Lead saveLead(Lead lead);

    void updateOrganisationLogo(String key,String completeUrl);

    Lead saveLeadWithoutInit(Lead lead) ;

    Lead patchUpdate(Lead lead);

    void activateLead(Lead lead);

    List<LeadVO> existsLeadWithEmail(String email);

    Lead getLeadByKeyEmailAndActivationStatus(String key, String email,String activationStatus);

    void leadToTenant(String key);

    void leadToUser(String key);

    List<Lead> existsLeadsWithEmail(String email,String appContext,String marketUUID);

    String saveOrUpdateLead(Lead fromDTO);

    List<Lead> existsLeadsWithMobile(String mobile, String appContext,String marketUUID);

    Lead getLeadByKeyMobileAndActivationStatus(String key, String mobile, String activationStatus);

    void updateContactVerification(Lead lead);

    Lead getLeadById(Long id);
}
