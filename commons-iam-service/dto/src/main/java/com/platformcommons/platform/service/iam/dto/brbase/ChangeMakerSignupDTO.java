package com.platformcommons.platform.service.iam.dto.brbase;

import com.mindtree.bridge.platform.dto.PersonDTO;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Locale;
import java.util.UUID;
import java.util.Random;

@Data
public class ChangeMakerSignupDTO {
    @NotNull
    private String tenantName;
    @NotNull
    private String tenantLoginName;

    @NotNull
    private String tenantEmail;
    private String tenantMobileNumber;
    @NotNull
    private String tenantAdminPassword;

    private String adminUserName;
    @NotNull
    private String verifiedOTPModKey;
    @NotNull
    private String otp;
    @NotNull
    private String marketUUID;

    private String tenantContactPersonName;

    private Boolean useMobileAsUserLogin;

    private PersonDTO linkedWorldPerson;

    public ChangeMakerSignupDTO(LeadDTO leadDTO, String tenantAdminPassword) {

        this.tenantName = leadDTO.getOrganizationName();
        this.tenantLoginName = tenantLogin(leadDTO.getOrganizationName());
        this.tenantEmail = leadDTO.getEmail();
        this.tenantMobileNumber = leadDTO.getMobile();
        this.tenantAdminPassword = tenantAdminPassword;
        this.verifiedOTPModKey = UUID.randomUUID().toString();
        this.otp = UUID.randomUUID().toString();
        this.marketUUID = UUID.randomUUID().toString();
        this.tenantContactPersonName = leadDTO.getLeadContactPersonName();
        this.useMobileAsUserLogin = leadDTO.getUseMobileAsUserLogin();
        if(leadDTO.getLeadContactPersonName() == null) {
            this.tenantContactPersonName =  StringUtils.trim((leadDTO.getFirstName() == null ? "" : leadDTO.getFirstName())
                     + " "
                     + (leadDTO.getLastName() == null ? "" : leadDTO.getLastName()));
        }
    }

    private String tenantLogin(String name) {
        name = name.replaceAll("[^a-zA-Z0-9\\- ]", "");
        String tenantLogin = name.toLowerCase(Locale.ROOT).replace(" ", "_");
        if (tenantLogin.length() > 40) {
            tenantLogin = getInitials(name);
        }
        if (tenantLogin.length() <= 3) {
            tenantLogin += "-" + getRandomTwoDigitNumber();
        }
        return  tenantLogin;
    }

    private String getInitials(String name) {
        StringBuilder initials = new StringBuilder();
        for (String part : name.split(" ")) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }
        String initialLogin= initials.toString().toLowerCase(Locale.ROOT);
        if(initialLogin.length() > 40){
            throw  new InvalidInputException("TenantLogin could not be casted to  40 characters");
        }
        return  initialLogin;
    }

    private String getRandomTwoDigitNumber() {
        return String.format("%02d", new Random().nextInt(100));
    }
}
