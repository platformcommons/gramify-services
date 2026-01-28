package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "user")
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Field
    private Long userId;

    @Field
    private String userAliasId;

    @Field
    private Long tenantId;

    @Field
    private String tenantLogin;

    @Field
    private String displayName;

    @Field
    private String login;

    @Field
    private String headLine;

    @Field
    private String genderCode;

    @Field
    private String profilePic;

    @Field
    private String primaryEmail;

    @Field
    private String primaryContactNumber;

    @Field
    private String primaryMobileCountryCode;

    @Field
    private Long personId;

    @Field
    private Set<String> employmentDesignations;

    @Field
    private Set<String> employmentOrganisationNames;

    @Field
    private Set<String> employmentOrganisationCodes;

    @Field
    private Set<String> currentEmploymentOrganisationCodes;

    @Field
    private String currentStateCode;

    @Field
    private String currentCityCode;

    @Field
    private String currentCityLatLong;

    @Field
    private String currentCountryCode;

    @Field
    private String currentAddressLabel;

    @Field
    private String permanentStateCode;

    @Field
    private String permanentCityCode;

    @Field
    private String permanentCityLatLong;

    @Field
    private String permanentCountryCode;

    @Field
    private String permanentAddressLabel;

    @Field
    private Set<String> roleCodes;

    @Field
    private Set<String> educationInstituteCodes;

    @Field
    private Set<String> educationInstituteNames;

    @Field
    private Set<String> pathwayLabels;

    @Field
    private Set<String> pathwayCodes;

    @Field
    private String cohort;

    @Field
    private String placementCity;

    @Field
    private String currentProfessionalStatus;

    @Field
    private String currentProfessionalStatusVisibility;

    @Field
    private Date memberSince;

    @Field
    private Date dateOfBirth;

    @Field
    private Boolean isActive;

    @Field
    private Set<Long> worknodeIds;

    @Field
    private Double totalYearsOfExperience;

    @Field
    private Set<String> educationQualificationCodes;

    @Field
    private Set<String> relocationPreferenceSet;

    @Field
    private Set<String> selfDeclarationCodes;

    @Field
    private Set<String> educationDegreeCodes;

    @Field
    private Set<String> profileVisibilityCodes;

    @Version
    @Field("_version_")
    private String version;

    public void init() {
    }


    /**
     * This method is only to update the userFact flat data obtained from query service
     * The columns which does not exist in query service are ignored in the below method  and in userFactFacadeImpl
     * manual sync/update of those columns are done
     */

    public void putUpdate(User toBeUpdated) {
        this.userAliasId = toBeUpdated.getUserAliasId();
        this.displayName = toBeUpdated.getDisplayName();
        this.login = toBeUpdated.getLogin();
        this.headLine = toBeUpdated.getHeadLine();
        this.genderCode = toBeUpdated.getGenderCode();
        this.profilePic = toBeUpdated.getProfilePic();
        this.primaryEmail = toBeUpdated.getPrimaryEmail();
        this.primaryContactNumber = toBeUpdated.getPrimaryContactNumber();
        this.primaryMobileCountryCode = toBeUpdated.getPrimaryMobileCountryCode();
        this.personId = toBeUpdated.getPersonId();
        this.employmentDesignations = toBeUpdated.getEmploymentDesignations();
        this.employmentOrganisationNames = toBeUpdated.getEmploymentOrganisationNames();
        this.employmentOrganisationCodes = toBeUpdated.getEmploymentOrganisationCodes();
        this.currentEmploymentOrganisationCodes = toBeUpdated.getCurrentEmploymentOrganisationCodes();
        this.currentStateCode = toBeUpdated.getCurrentStateCode();
        this.currentCityCode = toBeUpdated.getCurrentCityCode();
        this.currentCityLatLong = toBeUpdated.getCurrentCityLatLong();
        this.currentCountryCode = toBeUpdated.getCurrentCountryCode();
        this.currentAddressLabel = toBeUpdated.getCurrentAddressLabel();
        this.permanentStateCode = toBeUpdated.getPermanentStateCode();
        this.permanentCityCode = toBeUpdated.getPermanentCityCode();
        this.permanentCityLatLong = toBeUpdated.getPermanentCityLatLong();
        this.permanentCountryCode = toBeUpdated.getPermanentCountryCode();
        this.permanentAddressLabel = toBeUpdated.getPermanentAddressLabel();
        this.educationInstituteCodes = toBeUpdated.getEducationInstituteCodes();
        this.educationInstituteNames = toBeUpdated.getEducationInstituteNames();
        this.pathwayLabels = toBeUpdated.getPathwayLabels();
        this.pathwayCodes = toBeUpdated.getPathwayCodes();
        this.currentProfessionalStatus = toBeUpdated.getCurrentProfessionalStatus();
        this.currentProfessionalStatusVisibility = toBeUpdated.getCurrentProfessionalStatusVisibility();
        this.memberSince = toBeUpdated.getMemberSince();
        this.dateOfBirth = toBeUpdated.getDateOfBirth();
        this.isActive = toBeUpdated.getIsActive();
        this.totalYearsOfExperience = toBeUpdated.getTotalYearsOfExperience();
        this.educationQualificationCodes = toBeUpdated.getEducationQualificationCodes();
        this.relocationPreferenceSet = toBeUpdated.getRelocationPreferenceSet();
        this.selfDeclarationCodes = toBeUpdated.getSelfDeclarationCodes();
        this.educationDegreeCodes = toBeUpdated.getEducationDegreeCodes();
        this.profileVisibilityCodes = toBeUpdated.getProfileVisibilityCodes();
    }

}
