package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.iam.application.LeadService;
import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.domain.repo.LeadRepository;
import com.platformcommons.platform.service.iam.dto.LeadVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;

    @Override
    public Lead getById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lead with id %d not found", id)));
    }

    @Override
    public Lead getByKey(String key) {
        return leadRepository.findByKey(key)
                .orElseThrow(() -> new NotFoundException(String.format("Lead with key %s not found", key)));
    }

    @Override
    public Lead saveLead(Lead lead) {
        lead.init();
        return leadRepository.save(lead);
    }

    @Override
    public void updateOrganisationLogo(String key, String completeUrl) {
        Lead lead = getByKey(key);
        lead.setOrganisationLogo(completeUrl);
        leadRepository.save(lead);
    }

    @Override
    public Lead saveLeadWithoutInit(Lead lead) {
        return leadRepository.save(lead);
    }

    @Override
    public Lead patchUpdate(Lead lead) {
        Lead fetchedLead = getById(lead.getId());
        fetchedLead.patchUpdate(lead);
        return leadRepository.save(fetchedLead);
    }

    @Override
    public void activateLead(Lead lead) {
        lead.activate();
        leadRepository.save(lead);
    }

    @Override
    public void leadToTenant(String key) {
        Lead lead = getByKey(key);
        lead.tenantCreated();
        leadRepository.save(lead);
    }

    @Override
    public void leadToUser(String key) {
        Lead lead = getByKey(key);
        lead.userCreated();
        leadRepository.save(lead);
    }

    @Override
    public List<Lead> existsLeadsWithEmail(String email, String appContext, String marketUUID) {
        return leadRepository.getLeadByEmail(email, appContext, Lead.STATUS_TENANT_CREATED, marketUUID);
    }

    @Override
    public String saveOrUpdateLead(Lead lead) {
        if (lead.getKey() == null) {
            return saveLead(lead).getKey();
        } else {
            String primaryLogin = lead.getUseMobileAsLogin() != null && lead.getUseMobileAsLogin()
                    ? lead.getMobile():lead.getEmail();
            Lead fetchedLead = leadRepository.getLeadByKeyAndTypeAndEmail(lead.getKey(),
                    primaryLogin, lead.getType()).orElseThrow(() -> new NotFoundException("Invalid Lead Details"));
            fetchedLead.patchUpdate(fetchedLead);
            return leadRepository.save(fetchedLead).getKey();
        }
    }

    @Override
    public List<Lead> existsLeadsWithMobile(String mobile, String appContext, String marketUUID) {
        return leadRepository.getLeadByMobile(mobile, appContext, Lead.STATUS_TENANT_CREATED, marketUUID);
    }

    @Override
    public List<LeadVO> existsLeadWithEmail(String email) {
        List<Lead> existingLead = leadRepository.getLeadByEmail(email, null, Lead.STATUS_TENANT_CREATED, null);
        return existingLead.stream().map(this::mapLeadToLeadVO)
                .collect(Collectors.toList());
    }

    @Override
    public Lead getLeadByKeyEmailAndActivationStatus(String key, String email, String activationStatus) {
        Optional<Lead> optionalLead = leadRepository.getLeadByKeyAndActivationStatusAndEmail
                (key, activationStatus, email);
        return optionalLead.orElse(null);
    }


    @Override
    public Lead getLeadByKeyMobileAndActivationStatus(String key, String mobile, String activationStatus) {
        Optional<Lead> optionalLead = leadRepository.getLeadByKeyAndActivationStatusAndMobile
                (key, activationStatus, mobile);
        return optionalLead.orElse(null);

    }

    @Override
    public Lead getLeadById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Invalid Lead Id"));
    }

    private LeadVO mapLeadToLeadVO(Lead lead) {
        return LeadVO.builder()
                .id(lead.getId())
                .key(lead.getKey())
                .organizationName(lead.getOrganizationName())
                .activationStatus(lead.getActivationStatus())
                .build();
    }

    @Override
    public void updateContactVerification(Lead lead) {
        Lead fetchedLead = getById(lead.getId());
        fetchedLead.updateContactVerification(lead);
        leadRepository.save(fetchedLead);
    }
}
