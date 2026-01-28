package com.platformcommons.platform.service.search.messaging.mapper;

import com.mindtree.bridge.platform.dto.CurrencyDTO;

import com.platformcommons.platform.service.dto.commons.CurrencyValueDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.opportunity.dto.*;
import com.platformcommons.platform.service.search.domain.Opportunity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OpportunityEventMapper {

    private static String ATTRIBUTE_VALUE_TYPE_CODE = "ATTRIBUTE_VALUE_TYPE.CODE";

    private static String CHANGEMAKER_META_CODE_OPPORTUNITY_EDUCATION_QUALIFICATION = "CHANGEMAKER_META_CODE.OPPORTUNITY_EDUCATION_QUALIFICATION";

    private static String CHANGEMAKER_META_CODE_OPPORTUNITY_EDUCATION_DEGREE = "CHANGEMAKER_META_CODE.OPPORTUNITY_EDUCATION_DEGREE";

    public Opportunity fromEventDTO(OpportunityDTO opportunityDTO){

        assert opportunityDTO != null;

        Opportunity opportunity =  Opportunity.builder()
                .id(opportunityDTO.getId())
                .title(opportunityDTO.getTitle())
                .visibility(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getVisibility()))
                .visibilityDateTime(opportunityDTO.getVisibilityDateTime())
                .type(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getType()))
                .typeLabel(MapperUtil.getRefDataLabel(opportunityDTO.getSubType()))
                .subType(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getSubType()))
                .scheduledStartDate(opportunityDTO.getScheduledStartDate())
                .scheduledEndDate(opportunityDTO.getScheduledEndDate())
                .applicationStartDate(opportunityDTO.getApplicationStartDate())
                .applicationEndDate(opportunityDTO.getApplicationEndDate())
                .marketUuid(opportunityDTO.getMarketUuid())
                .keyPoints(opportunityDTO.getKeyPoints())
                .expectedOutcome(opportunityDTO.getExpectedOutcome())
                .iconPic(opportunityDTO.getIconPic())
                .bannerImage(opportunityDTO.getBannerImage())
                .tenant(opportunityDTO.getTenant())
                .tenantLogin(opportunityDTO.getTenantLogin())
                .tenantName(opportunityDTO.getTenantName())
                .tenantIconPic(opportunityDTO.getTenantIconPic())
                .tenantDescription(opportunityDTO.getTenantDescription())
                .tenantSlug(opportunityDTO.getTenantSlug())
                .tenantLink(opportunityDTO.getTenantLink())
                .status(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getStatus()))
                .opportunityApplicantCount(opportunityDTO.getOpportunityApplicantCount())
                .slots(opportunityDTO.getSlots())
                .minimumExperienceDays(getMinimumExperienceDays(opportunityDTO.getOpportunityEligibilityCriteria()))
                .maximumExperienceDays(getMaximumExperienceDays(opportunityDTO.getOpportunityEligibilityCriteria()))
                .marketPlaceVisibility(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getMarketPlaceVisibility()))
                .cityCodes(getCityCodes(opportunityDTO.getOpportunityLocationList()))
                .stateCodes(getStateCodes(opportunityDTO.getOpportunityLocationList()))
                .countryCodes(getCountryCodes(opportunityDTO.getOpportunityLocationList()))
                .addressesText(getAddresses(opportunityDTO.getOpportunityLocationList()))
                .opportunityCauseList(getCauseCodes(opportunityDTO.getOpportunityCauseList()))
                .opportunitySkillList(getSkillCodes(opportunityDTO.getOpportunitySkillList()))
                .opportunityRoleTypeCodes(getRoleTypeCodes(opportunityDTO.getOpportunityRoleTypeList()))
                .opportunityRoleTypeNames(getRoleTypeNames(opportunityDTO.getOpportunityRoleTypeList()))
                .opportunitySectorCodes(getSectorCodes(opportunityDTO.getOpportunitySectorList()))
                .opportunitySectorNames(getSectorNames(opportunityDTO.getOpportunitySectorList()))
                .opportunityPathwayCodes(getPathwayCodes(opportunityDTO.getOpportunityPathwayList()))
                .opportunityPathwayNames(getPathwayNames(opportunityDTO.getOpportunityPathwayList()))
                .workPlaceType(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getWorkPlaceType()))
                .workPlaceName(MapperUtil.getRefDataLabel(opportunityDTO.getWorkPlaceType()))
                .jobType(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getJobType()))
                .jobTypeName(MapperUtil.getRefDataLabel(opportunityDTO.getJobType()))
                .minimumCompensation(getMinimumCompensation(opportunityDTO.getOpportunityCompensation()))
                .maximumCompensation(getMaximumCompensation(opportunityDTO.getOpportunityCompensation()))
                .organisationCode(opportunityDTO.getOpportunityOrgDetails() != null ? opportunityDTO.getOpportunityOrgDetails().getOrganisationCode() : null)
                .organisationName(opportunityDTO.getOpportunityOrgDetails() != null ? opportunityDTO.getOpportunityOrgDetails().getOrganisationName() : null)
                .organisationLogoUrl(opportunityDTO.getOpportunityOrgDetails() != null ? opportunityDTO.getOpportunityOrgDetails().getOrganisationLogoUrl() : null)
                .organisationType(opportunityDTO.getOpportunityOrgDetails() != null ? MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getOpportunityOrgDetails().getOrganisationType()) : null)
                .visibleToRoles(getVisibleToRoles(opportunityDTO))
                .opportunityCategoryCodes(getOpportunityCategoryCodes(opportunityDTO.getOpportunityCategorySet()))
                .opportunitySubCategoryCodes(getOpportunitySubCategoryCodes(opportunityDTO.getOpportunitySubCategorySet()))
                .createdByUser(opportunityDTO.getCreatedByUser())
                .createdDateTime(opportunityDTO.getCreatedDateTime())
                .isRecommended(opportunityDTO.getIsRecommended())
                .restrictApplication(opportunityDTO.getRestrictApplication())
                .stakeHolderUserIds(opportunityDTO.getStakeHolderUserIds())
                .longDescription(opportunityDTO.getLongDescription())
                .tags(getTags(opportunityDTO.getTagSet()))
                .commitment(opportunityDTO.getOpportunityEligibilityCriteria()!=null?opportunityDTO.getOpportunityEligibilityCriteria().getCommitment():null)
                .timezone(opportunityDTO.getTimezone())
                .flowVersion(opportunityDTO.getFlowVersion())
                .locationType(MapperUtil.getCodeFromRefDataDTO(opportunityDTO.getLocationType()))
                .targetGroups(getTargetGroups(opportunityDTO.getOpportunityEligibilityCriteria()))
                .educationQualificationCodes(getAllExtraAttributeValueCodes(CHANGEMAKER_META_CODE_OPPORTUNITY_EDUCATION_QUALIFICATION,
                        opportunityDTO.getExtraAttributeSet()))
                .educationDegreeCodes(getAllExtraAttributeValueCodes(CHANGEMAKER_META_CODE_OPPORTUNITY_EDUCATION_DEGREE,
                        opportunityDTO.getExtraAttributeSet()))
                .isActive(opportunityDTO.getIsActive() == null ? Boolean.TRUE : opportunityDTO.getIsActive())
                //.amountCollected(extractAmountCollected(opportunityDTO))
                //.manualAmountCollected(getManualAmountCollected(opportunityDTO))
                //.displayTarget(getDisplayTarget(opportunityDTO))
                .build();

        CurrencyValueDTO targetAmount = extractTargetAmount(opportunityDTO);
        if (targetAmount != null) {
            opportunity.setTargetAmount(targetAmount.getValue());
            opportunity.setTargetAmountCurrency(targetAmount.getCurrency());
        }

        return opportunity;
    }

    public Set<String> getTargetGroups(OpportunityEligibilityCriteriaDTO opportunityEligibilityCriteria) {
        Set<String> targetGroups = null;
        if (opportunityEligibilityCriteria != null && opportunityEligibilityCriteria.getTargetGroups() != null
                && !opportunityEligibilityCriteria.getTargetGroups().isEmpty()) {
            targetGroups = opportunityEligibilityCriteria.getTargetGroups();
        }
        return targetGroups;
    }

    public Set<String> getTags(Set<TagDTO> tags) {
        if(tags == null || tags.isEmpty()) {
            return null;
        }
        return tags.stream()
                .filter(Objects::nonNull)
                .map(tagDTO-> {
                    if(tagDTO.getCode() != null) {
                        return tagDTO.getCode().getCode();
                    }
                    else if(tagDTO.getText() != null) {
                        return tagDTO.getText();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

    }

    private Set<String> getVisibleToRoles(OpportunityDTO opportunityDTO) {
        Set<String> result = null;
        if(opportunityDTO.getOpportunityConfig() != null) {
            result = opportunityDTO.getOpportunityConfig().getVisibleToRoles();
        }
        return result;
    }

    private String getEngLabel(Set<MLTextDTO> mlTextDTOs){
        MLTextDTO mlTextDTO= mlTextDTOs!=null ? mlTextDTOs.stream().filter(it-> it.getLanguageCode().equals("ENG"))
                .findFirst().orElse(null): null;
        return mlTextDTO!=null ? mlTextDTO.getText(): null;
    }

    private Double getMinimumCompensation(OpportunityCompensationDTO opportunityCompensationDTO) {
        if(opportunityCompensationDTO == null) {
            return (double) 0;
        }
        return opportunityCompensationDTO.getMinimumCompensation() != null
                ? opportunityCompensationDTO.getMinimumCompensation().getValue() : 0;
    }

    private Double getMaximumCompensation(OpportunityCompensationDTO opportunityCompensationDTO) {
        if(opportunityCompensationDTO == null) {
            return (double) 0;
        }
        return opportunityCompensationDTO.getMaximumCompensation() != null
                ? opportunityCompensationDTO.getMaximumCompensation().getValue() : 0;
    }

    private Long getMinimumExperienceDays(OpportunityEligibilityCriteriaDTO opportunityEligibilityCriteriaDTO) {
        if(opportunityEligibilityCriteriaDTO == null) {
            return 0L;
        }
        return opportunityEligibilityCriteriaDTO.getMinimumExperienceDays() != null
                ? opportunityEligibilityCriteriaDTO.getMinimumExperienceDays() : 0;
    }

    private Long getMaximumExperienceDays(OpportunityEligibilityCriteriaDTO opportunityEligibilityCriteriaDTO) {
        if(opportunityEligibilityCriteriaDTO == null) {
            return 0L;
        }
        return opportunityEligibilityCriteriaDTO.getMinimumExperienceDays() != null
                ? opportunityEligibilityCriteriaDTO.getMinimumExperienceDays() : 0;
    }

    private Set<String> getCityCodes(Set<OpportunityLocationDTO> opportunityLocationDTOS) {
        return opportunityLocationDTOS != null ? opportunityLocationDTOS.stream().map(OpportunityLocationDTO::getCity)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getStateCodes(Set<OpportunityLocationDTO> opportunityLocationDTOS) {
        return opportunityLocationDTOS != null ? opportunityLocationDTOS.stream().map(OpportunityLocationDTO::getState)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getCountryCodes(Set<OpportunityLocationDTO> opportunityLocationDTOS) {
        return opportunityLocationDTOS != null ? opportunityLocationDTOS.stream().map(OpportunityLocationDTO::getCountry)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getAddresses(Set<OpportunityLocationDTO> opportunityLocationList) {
        Set<String> locationSet = null;
        if(opportunityLocationList != null && !opportunityLocationList.isEmpty()) {
            locationSet = opportunityLocationList.stream()
                    .map(OpportunityLocationDTO::getAddress)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return locationSet;

    }

    private Set<String> getCauseCodes(Set<OpportunityCauseDTO> opportunityCauseDTOS) {
        return opportunityCauseDTOS != null ? opportunityCauseDTOS.stream().map(OpportunityCauseDTO::getCauseCode)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getSkillCodes(Set<OpportunitySkillDTO> opportunitySkillDTOS) {
        return opportunitySkillDTOS != null ? opportunitySkillDTOS.stream().map(OpportunitySkillDTO::getSkillCode)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getOpportunityCategoryCodes(Set<OpportunityCategoryDTO> opportunityCategoryDTOS) {
        return opportunityCategoryDTOS != null ? opportunityCategoryDTOS.stream()
                .filter(it-> it != null && it.getCategoryCode() != null)
                .map(OpportunityCategoryDTO::getCategoryCode)
                .map(RefDataDTO::getCode)
                .collect(Collectors.toSet()) :null;
    }

    private Set<String> getOpportunitySubCategoryCodes(Set<OpportunitySubCategoryDTO> opportunitySubCategoryDTOS) {
        return opportunitySubCategoryDTOS != null ? opportunitySubCategoryDTOS.stream()
                .filter(it-> it != null && it.getSubCategoryCode() != null)
                .map(OpportunitySubCategoryDTO::getSubCategoryCode)
                .map(RefDataDTO::getCode)
                .collect(Collectors.toSet()) :null;
    }

    private Set<String> getRoleTypeCodes(Set<OpportunityRoleTypeDTO> opportunityRoleTypeDTOS) {
        return opportunityRoleTypeDTOS != null ? opportunityRoleTypeDTOS.stream()
                .map(OpportunityRoleTypeDTO::getRoleType)
                .filter(Objects::nonNull)
                .map(RefDataDTO::getCode)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getRoleTypeNames(Set<OpportunityRoleTypeDTO> opportunityRoleTypeDTOS) {
        return opportunityRoleTypeDTOS != null ? opportunityRoleTypeDTOS.stream()
                .map(OpportunityRoleTypeDTO::getRoleType)
                .filter(Objects::nonNull)
                .map(MapperUtil::getRefDataLabel)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getSectorCodes(Set<OpportunitySectorDTO> opportunitySectorDTOS) {
        return opportunitySectorDTOS != null ? opportunitySectorDTOS.stream()
                .map(OpportunitySectorDTO::getSectorCode)
                .filter(Objects::nonNull)
                .map(RefDataDTO::getCode)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getSectorNames(Set<OpportunitySectorDTO> opportunitySectorDTOS) {
        return opportunitySectorDTOS != null ? opportunitySectorDTOS.stream()
                .map(OpportunitySectorDTO::getSectorCode)
                .filter(Objects::nonNull)
                .map(MapperUtil::getRefDataLabel)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getPathwayCodes(Set<OpportunityPathwayDTO> opportunityPathwayDTOS) {
        return opportunityPathwayDTOS != null ? opportunityPathwayDTOS.stream()
                .map(OpportunityPathwayDTO::getPathwayCode)
                .filter(Objects::nonNull)
                .map(RefDataDTO::getCode)
                .collect(Collectors.toSet()):null;
    }

    private Set<String> getPathwayNames(Set<OpportunityPathwayDTO> opportunityPathwayDTOS) {
        return opportunityPathwayDTOS != null ? opportunityPathwayDTOS.stream()
                .map(OpportunityPathwayDTO::getPathwayCode)
                .filter(Objects::nonNull)
                .map(MapperUtil::getRefDataLabel)
                .collect(Collectors.toSet()):null;
    }

    public Set<String> getAllExtraAttributeValueCodes(String metaDataCode, Set<ExtraAttributeDTO> extraAttributeDTOSet){
        if(extraAttributeDTOSet == null || extraAttributeDTOSet.isEmpty() || metaDataCode == null) {
            return null;
        }
        return  extraAttributeDTOSet.stream()
                .filter(Objects::nonNull)
                .filter(it-> Objects.equals(it.getIsActive(),Boolean.TRUE))
                .filter(it -> Objects.equals(it.getAttributeValueType(),ATTRIBUTE_VALUE_TYPE_CODE) )
                .filter(it -> it.getMetaDataCode() != null
                          && Objects.equals(MapperUtil.getCodeFromRefDataDTO(it.getMetaDataCode()),metaDataCode))
                .map(it -> MapperUtil.getCodeFromRefDataDTO(it.getAttributeValueCode()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public CurrencyValueDTO extractTargetAmount(OpportunityDTO opportunityDTO){
        if (opportunityDTO.getOpportunityPaymentConfig() != null) {
            OpportunityPaymentConfigDTO paymentConfig = opportunityDTO.getOpportunityPaymentConfig();
            if (paymentConfig.getTargetAmount() != null) {
                return paymentConfig.getTargetAmount();
            }
        }
        return null;
    }

    public Double extractAmountCollected (OpportunityDTO opportunityDTO) {
        if (opportunityDTO.getOpportunityPaymentConfig() != null) {
            OpportunityPaymentConfigDTO paymentConfig = opportunityDTO.getOpportunityPaymentConfig();
            if (paymentConfig.getAmountCollected() != null) {
                return paymentConfig.getAmountCollected().getValue();
            }
        }
        return null;
    }

    public Boolean getDisplayTarget(OpportunityDTO opportunityDTO) {
        if (opportunityDTO.getOpportunityPaymentConfig() != null) {
            OpportunityPaymentConfigDTO paymentConfig = opportunityDTO.getOpportunityPaymentConfig();
            if (paymentConfig.getDisplayTarget() != null) {
                return paymentConfig.getDisplayTarget();
            }
        }
        return null;
    }

//    public Double getManualAmountCollected(OpportunityDTO opportunityDTO) {
//        if (opportunityDTO.getOpportunityPaymentConfig() != null) {
//            OpportunityPaymentConfigDTO paymentConfig = opportunityDTO.getOpportunityPaymentConfig();
//            if (paymentConfig.getManualAmountCollected() != null) {
//                return paymentConfig.getManualAmountCollected();
//            }
//        }
//        return null;
//    }



}