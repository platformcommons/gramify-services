package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.application.LeadService;
import com.platformcommons.platform.service.iam.application.utility.TriggerNotification;
import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.LeadOTPResponseDTO;
import com.platformcommons.platform.service.iam.dto.LeadVO;
import com.platformcommons.platform.service.iam.dto.OTPResponse;
import com.platformcommons.platform.service.iam.facade.AttachmentFacade;
import com.platformcommons.platform.service.iam.facade.LeadFacade;
import com.platformcommons.platform.service.iam.facade.assembler.LeadDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class LeadFacadeImpl implements LeadFacade {

    private final LeadDTOAssembler leadDTOAssembler;
    private final LeadService leadService;
    private final TriggerNotification triggerNotification;
    private final AttachmentFacade attachmentFacade;

    @Override
    public LeadDTO getByKey(String key) {
        return leadDTOAssembler.toDTO(leadService.getByKey(key));
    }

    @Override
    public OTPResponse saveLead(LeadDTO leadDTO, Boolean sendEmailOTP, Boolean sendMobileOTP) {
        leadDTO = leadDTOAssembler.toDTO(leadService.saveLead(leadDTOAssembler.fromDTO(leadDTO)));
        return generateMessageIdForOTP(leadDTO,sendEmailOTP,sendMobileOTP);
    }

    @Override
    public LeadDTO getLeadByKey(String key) {
        return leadDTOAssembler.toDTO(leadService.getByKey(key));
    }

    @Override
    public OTPResponse patchUpdate(LeadDTO leadDTO, Boolean sendEmailOTP, Boolean sendMobileOTP) {
        leadDTO = leadDTOAssembler.toDTO(leadService.patchUpdate(leadDTOAssembler.fromDTO(leadDTO)));
        return generateMessageIdForOTP(leadDTO,sendEmailOTP,sendMobileOTP);
    }

    public LeadDTO getLeadByKeyEmailAndActivationStatus(String key, String email, String activationStatus) {
        Lead lead = leadService.getLeadByKeyEmailAndActivationStatus(key, email,activationStatus);
        return leadDTOAssembler.toDTO(lead);
    }


    @Override
    public LeadDTO getLeadByKeyMobileAndActivationStatus(String key, String mobile, String activationStatus) {
        Lead lead = leadService.getLeadByKeyMobileAndActivationStatus(key, mobile,activationStatus);
        return leadDTOAssembler.toDTO(lead);
    }

    @Override
    public void activateLead(LeadDTO leadDTO) {
        leadService.activateLead(leadDTOAssembler.fromDTO(leadDTO));
    }

    @Override
    public void leadToUser(String key) {
        leadService.leadToUser(key);
    }

    @Override
    public void leadToTenant(String key) {
        leadService.leadToTenant(key);
    }

    @Override
    public String saveOrUpdateLead(LeadDTO leadDTO) {
        return leadService.saveOrUpdateLead(leadDTOAssembler.fromDTO(leadDTO));
    }

    @Override
    public List<LeadDTO> existsLeadDTOWithMobile(String mobile, String appContext,String marketUUID) {
        List<Lead> leads = leadService.existsLeadsWithMobile(mobile,appContext,marketUUID);
        return  leads.stream().map(leadDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<LeadVO> existsLeadWithEmail(String email) {
        return leadService.existsLeadWithEmail(email);
    }

    @Override
    public List<LeadDTO> existsLeadDTOWithEmail(String email,String appContext,String marketUUID) {
        List<Lead> leads = leadService.existsLeadsWithEmail(email,appContext,marketUUID);
        return  leads.stream().map(leadDTOAssembler::toMaskedDTO).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public OTPResponse reSendOTP(String key, Boolean reSendEmailOTP, Boolean reSendMobileOTP) {
        LeadDTO leadDTO = getByKey(key);
        if(Objects.equals(leadDTO.getActivationStatus(),Lead.STATUS_REGISTERED)) {
            return generateMessageIdForOTP(leadDTO,reSendEmailOTP,reSendMobileOTP);
        }
        else {
            throw new InvalidInputException("Lead is not in REGISTERED status");
        }
    }

    public OTPResponse generateMessageIdForOTP(LeadDTO leadDTO,Boolean sendEmailOTP,Boolean sendMobileOTP) {
        OTPResponse otpResponse = new OTPResponse();
        otpResponse.setKey(leadDTO.getKey());
        String messageId = null;
        if(Objects.equals(sendEmailOTP,Boolean.TRUE)) {
            messageId = triggerNotification.sendMailOTPNotificationForLead(leadDTO);
            otpResponse.setMessageIdForEmail(messageId);
        }
        if(Objects.equals(sendMobileOTP,Boolean.TRUE)) {
            messageId = triggerNotification.sendOTPNotificationSMS(leadDTO.getMobile(),leadDTO.getOrganizationName());
            otpResponse.setMessageIdForMobile(messageId);
        }
        return otpResponse;
    }

    @Override
    public LeadOTPResponseDTO changeContactAndSendOTP(String key, String newEmail, String newMobileNumber) {
        Lead lead = leadService.getByKey(key);
        LeadDTO leadDTO = leadDTOAssembler.toDTO(lead);
        LeadOTPResponseDTO leadOTPResponseDTO = new LeadOTPResponseDTO();
        OTPResponse otpResponse = new OTPResponse();
        if(Objects.equals(lead.getActivationStatus(),Lead.STATUS_REGISTERED)) {
            otpResponse.setKey(lead.getKey());
            String messageId = null;

            if(!StringUtils.isBlank(newEmail)) {
                lead.setEmail(newEmail);
                messageId = triggerNotification.sendMailOTPNotificationForLead(leadDTO);
                otpResponse.setMessageIdForEmail(messageId);
                lead.setIsEmailVerified(Boolean.FALSE);
            }

            if(!StringUtils.isBlank(newMobileNumber)) {
                lead.setMobile(newMobileNumber);
                messageId = triggerNotification.sendOTPNotificationSMS(lead.getMobile(),lead.getOrganizationName());
                otpResponse.setMessageIdForMobile(messageId);
                lead.setIsMobileVerified(Boolean.FALSE);
            }

            lead = leadService.saveLeadWithoutInit(lead);
            leadOTPResponseDTO.setLead(leadDTOAssembler.toDTO(lead));
            leadOTPResponseDTO.setOtpResponse(otpResponse);
        }
        else {
            throw new InvalidInputException("Lead is not in REGISTERED status");
        }
        return leadOTPResponseDTO;
    }

    @Override
    public Set<LeadDTO> getLeadsInPendingState(String email, String mobile, String appContext, String marketUUID) {
        Set<LeadDTO> leadDTOSet = new HashSet<>();
        if(StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)){
            throw  new InvalidInputException("Either Email Or mobile should be provided");
        }
        else if(!StringUtils.isEmpty(email)) {
            List<LeadDTO> leads = existsLeadDTOWithEmail(email,appContext,marketUUID);
            leadDTOSet.addAll(leads);
        }
        else {
            List<LeadDTO> leads = existsLeadDTOWithMobile(mobile,appContext,marketUUID);
            leadDTOSet.addAll(leads);
        }
        return leadDTOSet;
    }

    @Override
    public AttachmentDTO addLeadOrganisationLogo(MultipartFile file, String leadKey) {
        PlatformSecurityUtil.mimicTenant(1L,1L);
        AttachmentDTO attachmentDTO = attachmentFacade.createAttachment(null,"ENTITY_TYPE.LEAD_ORGANISATION_LOGO",
                null,null,file,Boolean.TRUE, null,null);
        leadService.updateOrganisationLogo(leadKey,attachmentDTO.getCompleteURL());
        return attachmentDTO;
    }

    @Override
    public void updateContactVerification(LeadDTO leadDTO) {
        leadService.updateContactVerification(leadDTOAssembler.fromDTO(leadDTO));
    }

    @Override
    public LeadDTO fetchLeadForTenantCreation(String leadKey) {
        LeadDTO leadDTO  = getByKey(leadKey);
        if (!Lead.STATUS_REGISTERED.equals(leadDTO.getActivationStatus())) {
            throw new InvalidInputException("Lead must be in Registered phase");
        }
        return leadDTO;
    }
}
