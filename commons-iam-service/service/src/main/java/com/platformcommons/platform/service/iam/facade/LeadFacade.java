package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.LeadOTPResponseDTO;
import com.platformcommons.platform.service.iam.dto.LeadVO;
import com.platformcommons.platform.service.iam.dto.OTPResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface LeadFacade {

    OTPResponse saveLead(LeadDTO leadDTO, Boolean sendEmailOTP, Boolean sendMobileOTP);

    LeadDTO getLeadByKey(String key);

    OTPResponse patchUpdate(LeadDTO leadDTO, Boolean sendEmailOTP, Boolean sendMobileOTP);

    LeadDTO getByKey(String key);

    LeadDTO getLeadByKeyEmailAndActivationStatus(String key, String email, String activationStatus);

    List<LeadVO> existsLeadWithEmail(String email);

    List<LeadDTO> existsLeadDTOWithEmail(String email,String appContext,String marketUUID);

    LeadDTO getLeadByKeyMobileAndActivationStatus(String key, String mobile, String activationStatus);

    void activateLead(LeadDTO lead);

    void leadToTenant(String key);

    void leadToUser(String key);

    String saveOrUpdateLead(LeadDTO leadDTO);

    List<LeadDTO> existsLeadDTOWithMobile(String mobile, String appContext,String marketUUID);

    OTPResponse reSendOTP(String key, Boolean reSendEmailOTP, Boolean reSendMobileOTP);

    LeadOTPResponseDTO changeContactAndSendOTP(String key, String newEmail, String newMobileNumber);

    Set<LeadDTO> getLeadsInPendingState(String email, String mobile, String appContext, String marketUUID);

    AttachmentDTO addLeadOrganisationLogo(MultipartFile file,String leadKey);

    void updateContactVerification(LeadDTO leadDTO);

    LeadDTO fetchLeadForTenantCreation(String key);
}
