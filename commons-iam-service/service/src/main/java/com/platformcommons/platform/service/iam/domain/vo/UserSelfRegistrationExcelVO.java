package com.platformcommons.platform.service.iam.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSelfRegistrationExcelVO extends BaseExcelVO {

    @ExcelColumn(name = "id")
    private Long id;

    @ExcelColumn(name = "FirstName")
    private String firstName;

    @ExcelColumn(name = "LastName")
    private String lastName;

    @ExcelColumn(name = "DOB")
    private Date dob;

    @ExcelColumn(name = "Gender")
    private String gender;

    @ExcelColumn(name = "Login")
    private String login;

    @ExcelColumn(name = "PrimaryEmailAddress")
    private String primaryEmailAddress;

    @ExcelColumn(name = "SecondaryEmailAddress")
    private String secondaryEmailAddress;

    @ExcelColumn(name = "PrimaryContactCountryCode")
    private String primaryContactCountryCode;

    @ExcelColumn(name = "PrimaryContactNumber")
    private String primaryContactNumber;

    @ExcelColumn(name = "SecondaryContactCountryCode")
    private String secondaryContactCountryCode;

    @ExcelColumn(name = "SecondaryContactNumber")
    private String secondaryContactNumber;

    @ExcelColumn(name = "WhatsappContactCountryCode")
    private String whatsappContactCountryCode;

    @ExcelColumn(name = "WhatsappContactNumber")
    private String whatsappContactNumber;

    @ExcelColumn(name = "CurrentAddressCountry")
    private String currentAddressCountry;

    @ExcelColumn(name = "CurrentAddressState")
    private String currentAddressState;

    @ExcelColumn(name = "CurrentAddressCity")
    private String currentAddressCity;

    @ExcelColumn(name = "CurrentAddressPinCode")
    private String currentAddressPinCode;

    @ExcelColumn(name = "RoleCode")
    private String roleCode;

    @ExcelColumn(name = "CohortId")
    private Long cohortId;

    @ExcelColumn(name = "OrgVerticalId")
    private Long orgVerticalId;

    @ExcelColumn(name = "CohortStartDate")
    private Date cohortStartDate;

    @ExcelColumn(name = "CohortEndDate")
    private Date cohortEndDate;

    @Override
    public String toString() {
        return "UserSelfRegistrationExcelVO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", login='" + login + '\'' +
                ", primaryEmailAddress='" + primaryEmailAddress + '\'' +
                ", secondaryEmailAddress='" + secondaryEmailAddress + '\'' +
                ", primaryContactCountryCode='" + primaryContactCountryCode + '\'' +
                ", primaryContactNumber='" + primaryContactNumber + '\'' +
                ", secondaryContactCountryCode='" + secondaryContactCountryCode + '\'' +
                ", secondaryContactNumber='" + secondaryContactNumber + '\'' +
                ", whatsappContactCountryCode='" + whatsappContactCountryCode + '\'' +
                ", whatsappContactNumber='" + whatsappContactNumber + '\'' +
                ", currentAddressCountry='" + currentAddressCountry + '\'' +
                ", currentAddressState='" + currentAddressState + '\'' +
                ", currentAddressCity='" + currentAddressCity + '\'' +
                ", currentAddressPinCode='" + currentAddressPinCode + '\'' +
                '}';
    }

    @Builder
    public UserSelfRegistrationExcelVO(Long rowNumber, Long id, String firstName, String lastName, Date dob, String gender,
                                       String login, String primaryEmailAddress, String secondaryEmailAddress, String primaryContactCountryCode,
                                       String primaryContactNumber, String secondaryContactCountryCode, String secondaryContactNumber,
                                       String whatsappContactCountryCode, String whatsappContactNumber, String currentAddressCountry,
                                       String currentAddressState, String currentAddressCity, String currentAddressPinCode) {
        super(rowNumber);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.login = login;
        this.primaryEmailAddress = primaryEmailAddress;
        this.secondaryEmailAddress = secondaryEmailAddress;
        this.primaryContactCountryCode = primaryContactCountryCode;
        this.primaryContactNumber = primaryContactNumber;
        this.secondaryContactCountryCode = secondaryContactCountryCode;
        this.secondaryContactNumber = secondaryContactNumber;
        this.whatsappContactCountryCode = whatsappContactCountryCode;
        this.whatsappContactNumber = whatsappContactNumber;
        this.currentAddressCountry = currentAddressCountry;
        this.currentAddressState = currentAddressState;
        this.currentAddressCity = currentAddressCity;
        this.currentAddressPinCode = currentAddressPinCode;
    }
}
