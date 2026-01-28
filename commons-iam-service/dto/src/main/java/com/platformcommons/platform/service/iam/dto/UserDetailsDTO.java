package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDTO {
    private Long userId;
    private String fullName;
    private String emailAddress;
    private String userLogin;
    private String iconPic;
    private String contactNumber;
    private Long contactCountryCode;
    private String whatsappContactNumber;
    private Long whatsappNumberCountryCode;

    @Builder
    public UserDetailsDTO(Long userId, String emailAddress, String iconPic, String fullName, String contactNumber,
                          String userLogin,Long contactCountryCode,String whatsappContactNumber,Long whatsappNumberCountryCode) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.iconPic = iconPic;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.userLogin = userLogin;
        this.contactCountryCode = contactCountryCode;
        this.whatsappContactNumber = whatsappContactNumber;
        this.whatsappNumberCountryCode = whatsappNumberCountryCode;
    }

    @Override
    public String toString() {
        return "UserDetailsDTO{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", iconPic='" + iconPic + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", contactCountryCode=" + contactCountryCode +
                ", whatsappContactNumber='" + whatsappContactNumber + '\'' +
                ", whatsappNumberCountryCode=" + whatsappNumberCountryCode +
                '}';
    }
}