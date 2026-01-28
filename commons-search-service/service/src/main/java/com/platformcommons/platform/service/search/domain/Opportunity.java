package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "opportunity")
public class Opportunity {

    @Id
    @Field
    private Long id;

    @Field
    private String title;

    @Field
    private String visibility;

    @Field
    private String longDescription;

    @Field
    private String type;

    @Field
    private String typeLabel;

    @Field
    private String subType;

    @Field
    private Date scheduledStartDate;

    @Field
    private Date scheduledEndDate;

    @Field
    private Date applicationStartDate;

    @Field
    private Date visibilityDateTime;

    @Field
    private String marketUuid;

    @Field
    private Date applicationEndDate;

    @Field
    private String keyPoints;

    @Field
    private String expectedOutcome;

    @Field
    private String iconPic;

    @Field
    private String bannerImage;

    @Field
    private Long tenant;

    @Field
    private String tenantLogin;

    @Field
    private String tenantName;

    @Field
    private String tenantIconPic;

    @Field
    private String tenantDescription;

    @Field
    private String tenantSlug;

    @Field
    private String tenantLink;

    @Field
    private String status;

    @Field
    private Long sequence;

    @Field
    private Long opportunityApplicantCount;

    @Field
    private Integer slots;

    @Field
    private Long minimumExperienceDays;

    @Field
    private Long maximumExperienceDays;

    @Field
    private String marketPlaceVisibility;

    @Field
    private Set<String> cityCodes;

    @Field
    private Set<String> stateCodes;

    @Field
    private Set<String> countryCodes;

    @Field
    private Set<String> addresses;

    @Field
    private Set<String> addressesText;

    @Field
    private Set<String> opportunityCauseList;

    @Field
    private Set<String> opportunitySkillList;

    @Field
    private Set<String> opportunityRoleTypeCodes;

    @Field
    private Set<String> opportunityRoleTypeNames;

    @Field
    private Set<String> opportunitySectorCodes;

    @Field
    private Set<String> opportunitySectorNames;

    @Field
    private Set<String> opportunityPathwayCodes;

    @Field
    private Set<String> opportunityPathwayNames;

    @Field
    private Set<String> visibleToRoles;

    @Field
    private String workPlaceType;

    @Field
    private String workPlaceName;

    @Field
    private String jobType;

    @Field
    private String jobTypeName;

    @Field
    private Double minimumCompensation;

    @Field
    private Double maximumCompensation;

    @Field
    private String organisationCode;

    @Field
    private String organisationName;

    @Field
    private String organisationLogoUrl;

    @Field
    private String organisationType;

    @Field
    private Long createdByUser;

    @Field
    private Date createdDateTime;

    @Field
    private Boolean isActive;

    @Field
    private Boolean isRecommended;

    @Field
    private Boolean restrictApplication;

    @Field
    private String commitment;

    @Field
    private Set<Long> stakeHolderUserIds;

    @Field
    private Set<String> opportunityCategoryCodes;

    @Field
    private Set<String> opportunitySubCategoryCodes;

    @Field
    private Set<String> tags;

    @Field
    private String timezone;

    @Field
    private String flowVersion;

    @Field
    private String locationType;

    @Field
    private Set<String> targetGroups;

    @Field
    private Set<String> educationQualificationCodes;

    @Field
    private Set<String> educationDegreeCodes;

    @Field
    private Double targetAmount;

    @Field
    private Double amountCollected;

    @Field
    private String targetAmountCurrency;

    @Field
    private Boolean displayTarget;

    @Field
    private Double manualAmountCollected;


    @Builder
    public Opportunity(Long id, String title, String visibility, String type, String subType, Date scheduledStartDate,
                       Date scheduledEndDate, Date applicationStartDate, String marketUuid, Date applicationEndDate, String keyPoints,
                       String expectedOutcome, String iconPic, String bannerImage, Long tenant, String tenantLogin, String tenantName,
                       String tenantIconPic, String tenantDescription, String status, Long opportunityApplicantCount, Integer slots,
                       Long minimumExperienceDays, Long maximumExperienceDays, String marketPlaceVisibility, Set<String> cityCodes,
                       Set<String> stateCodes, Set<String> countryCodes, Set<String> addresses, Set<String> opportunityCauseList,
                       Set<String> opportunitySkillList, Set<String> opportunityRoleTypeCodes, Set<String> opportunityRoleTypeNames,
                       Set<String> opportunitySectorCodes, Set<String> opportunitySectorNames, Set<String> opportunityPathwayCodes,
                       Set<String> opportunityPathwayNames, String workPlaceType, String workPlaceName, String jobType, String jobTypeName,
                       Double minimumCompensation, Double maximumCompensation, String organisationCode, String organisationName, Long createdByUser,
                       Date createdDateTime, Boolean isActive,String organisationLogoUrl,Long sequence,String organisationType,
                       Set<String> visibleToRoles,Boolean isRecommended,Boolean restrictApplication,Set<Long> stakeHolderUserIds,
                       Set<String> opportunityCategoryCodes,String commitment, Set<String> opportunitySubCategoryCodes,
                       String longDescription,Set<String> tags ,String timezone,
                       String flowVersion,String locationType,Date visibilityDateTime,String tenantSlug,String tenantLink,
                       Set<String> targetGroups,Set<String> educationQualificationCodes,Set<String> educationDegreeCodes,
                       Set<String> addressesText,String typeLabel, Double targetAmount, Double amountCollected, String targetAmountCurrency,
                       Double manualAmountCollected, Boolean displayTarget) {
        this.id = id;
        this.title = title;
        this.visibility = visibility;
        this.type = type;
        this.typeLabel = typeLabel;
        this.subType = subType;
        this.scheduledStartDate = scheduledStartDate;
        this.scheduledEndDate = scheduledEndDate;
        this.applicationStartDate = applicationStartDate;
        this.marketUuid = marketUuid;
        this.applicationEndDate = applicationEndDate;
        this.keyPoints = keyPoints;
        this.expectedOutcome = expectedOutcome;
        this.iconPic = iconPic;
        this.bannerImage = bannerImage;
        this.tenant = tenant;
        this.tenantLogin = tenantLogin;
        this.tenantName = tenantName;
        this.tenantIconPic = tenantIconPic;
        this.tenantDescription = tenantDescription;
        this.tenantLink = tenantLink;
        this.tenantSlug = tenantSlug;
        this.status = status;
        this.opportunityApplicantCount = opportunityApplicantCount;
        this.slots = slots;
        this.minimumExperienceDays = minimumExperienceDays;
        this.maximumExperienceDays = maximumExperienceDays;
        this.marketPlaceVisibility = marketPlaceVisibility;
        this.cityCodes = cityCodes;
        this.stateCodes = stateCodes;
        this.countryCodes = countryCodes;
        this.addresses = addresses;
        this.opportunityCauseList = opportunityCauseList;
        this.opportunitySkillList = opportunitySkillList;
        this.opportunityRoleTypeCodes = opportunityRoleTypeCodes;
        this.opportunityRoleTypeNames = opportunityRoleTypeNames;
        this.opportunitySectorCodes = opportunitySectorCodes;
        this.opportunitySectorNames = opportunitySectorNames;
        this.opportunityPathwayCodes = opportunityPathwayCodes;
        this.opportunityPathwayNames = opportunityPathwayNames;
        this.workPlaceType = workPlaceType;
        this.workPlaceName = workPlaceName;
        this.jobType = jobType;
        this.jobTypeName = jobTypeName;
        this.minimumCompensation = minimumCompensation;
        this.maximumCompensation = maximumCompensation;
        this.organisationCode = organisationCode;
        this.organisationName = organisationName;
        this.organisationLogoUrl = organisationLogoUrl;
        this.createdByUser = createdByUser;
        this.createdDateTime = createdDateTime;
        this.isActive = isActive;
        this.sequence = sequence;
        this.organisationType = organisationType;
        this.visibleToRoles = visibleToRoles;
        this.isRecommended = isRecommended;
        this.restrictApplication = restrictApplication;
        this.stakeHolderUserIds = stakeHolderUserIds;
        this.opportunityCategoryCodes = opportunityCategoryCodes;
        this.opportunitySubCategoryCodes = opportunitySubCategoryCodes;
        this.longDescription = longDescription;
        this.tags = tags;
        this.commitment = commitment;
        this.timezone = timezone;
        this.flowVersion = flowVersion;
        this.locationType = locationType;
        this.visibilityDateTime = visibilityDateTime;
        this.targetGroups = targetGroups;
        this.educationQualificationCodes = educationQualificationCodes;
        this.educationDegreeCodes = educationDegreeCodes;
        this.addressesText = addressesText;
        this.targetAmount = targetAmount;
        this.amountCollected = amountCollected;
        this.targetAmountCurrency = targetAmountCurrency;
        this.displayTarget = displayTarget;
        this.manualAmountCollected = manualAmountCollected;
    }

    public void init() {
    }
}
