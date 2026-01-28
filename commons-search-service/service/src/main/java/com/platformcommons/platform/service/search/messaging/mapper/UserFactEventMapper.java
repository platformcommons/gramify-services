package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.dto.GlobalRefDataDTO;
import com.platformcommons.platform.service.search.dto.UserDTO;
import com.platformcommons.platform.service.search.facade.cache.validator.GlobalRefDataCacheValidator;
import com.platformcommons.platform.service.search.messaging.mapper.constant.MapperConstant;
import com.platformcommons.platform.service.search.facade.client.utility.CommonsReportUtil;
import com.platformcommons.platform.service.vms.reporting.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class UserFactEventMapper {

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    @Autowired
    private GlobalRefDataCacheValidator globalRefDataCacheValidator;

    public UserDTO getUserDTOFromUserFactDTO(UserFactDTO userFactDTO) {
        UserDTO userDTO = null;
        if (userFactDTO != null && userFactDTO.getPersonFact() != null) {
            PersonFactDTO personFactDTO = userFactDTO.getPersonFact();
            Set<PersonWorkHistoryDTO> personWorkHistorySet = userFactDTO.getPersonWorkHistorySet();
            Set<PersonEducationDTO> personEducationSet = userFactDTO.getPersonEducationSet();
            Set<PersonPreferenceDTO> personPreferenceDTOSet = userFactDTO.getPersonPreferenceSet();
            Set<PersonExtraAttributeDTO> personExtraAttributeDTOSet = userFactDTO.getPersonExtraAttributeSet();

            UserDTO.UserDTOBuilder builder = UserDTO.builder()
                    .userId(personFactDTO.getUserID())
                    .userAliasId(personFactDTO.getUserAliasID())
                    .tenantId(personFactDTO.getUserTenantId())
                    .tenantLogin(personFactDTO.getUserTenantLogin())
                    .login(personFactDTO.getUserLogin())
                    .displayName(personFactDTO.getUserDisplayName())
                    .personId(personFactDTO.getPersonId())
                    .headLine(personFactDTO.getPersonProfileNotes())
                    .genderCode(personFactDTO.getGenderCode())
                    .profilePic(personFactDTO.getProfilePic())
                    .primaryEmail(personFactDTO.getPrimaryEmail())
                    .primaryContactNumber(personFactDTO.getPrimaryMobileNumber())
                    .primaryMobileCountryCode(personFactDTO.getPrimaryMobileCountryCode())
                    .totalYearsOfExperience(personFactDTO.getTotalYearOfExperience() != null
                            ? Double.parseDouble(personFactDTO.getTotalYearOfExperience()) : null )
                    .educationQualificationCodes(getEducationQualifications(personEducationSet))
                    .educationDegreeCodes(getEducationDegrees(personEducationSet))
                    .relocationPreferenceSet(getPersonPreferenceValues(personPreferenceDTOSet,ServiceConstant.PREFERENCE_TYPE_JOB_PREFERENCE_LOCATION))
                    .selfDeclarationCodes(getPersonExtraAttributeValues(personExtraAttributeDTOSet,MapperConstant.PERSON_PROFILE_SELF_DECLARATION))
                    .employmentDesignations(getEmploymentDesignations(personWorkHistorySet))
                    .employmentOrganisationNames(getEmploymentOrganisationNames(personWorkHistorySet))
                    .employmentOrganisationCodes(getOrganisationCodes(personWorkHistorySet))
                    .currentEmploymentOrganisationCodes(getCurrentOrganisationCodes(personWorkHistorySet))
                    .currentStateCode(personFactDTO.getCurrentState())
                    .currentCityCode(personFactDTO.getCurrentCity())
                    .currentCountryCode(personFactDTO.getCurrentCountry())
                    .currentAddressLabel(getCurrentAddressLabel(personFactDTO))
                    .permanentStateCode(personFactDTO.getPermanentState())
                    .permanentCityCode(personFactDTO.getPermanentCity())
                    .permanentCountryCode(personFactDTO.getPermanentCountry())
                    .permanentAddressLabel(getPermanentAddressLabel(personFactDTO))
                    .educationInstituteCodes(getEductionInstituteCodes(userFactDTO.getPersonEducationSet()))
                    .educationInstituteNames(getEductionInstituteNames(userFactDTO.getPersonEducationSet()))
                    .pathwayCodes(getPathwayCodes(userFactDTO.getPersonHobbySet()))
                    .pathwayLabels(getPathwayLabels(userFactDTO.getPersonHobbySet()))
                    .memberSince(personFactDTO.getMemberSince())
                    .dateOfBirth(personFactDTO.getDateOfBirth())
                    .isActive(personFactDTO.getIsActive() == null ? Boolean.TRUE : personFactDTO.getIsActive())
                    .currentProfessionalStatus(userFactDTO.getPersonFact().getCurrentProfessionalStatus())
                    .currentProfessionalStatusVisibility(userFactDTO.getPersonFact().getCurrentProfessionalStatusVisibility())
                    .profileVisibilityCodes(getPersonPreferenceValues(personPreferenceDTOSet,ServiceConstant.PREFERENCE_TYPE_PROFILE_VISIBILITY));

            userDTO = builder.build();
            addLocationCoordinates(userDTO);
        }
        return userDTO;
    }

    public Set<String> getPersonExtraAttributeValues(Set<PersonExtraAttributeDTO> personExtraAttributeDTOSet, String metaData) {
        Set<String> values = null;
        if (personExtraAttributeDTOSet != null && metaData != null) {
            values = personExtraAttributeDTOSet.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> Objects.equals(it.getMetadata(),metaData))
                    .map(PersonExtraAttributeDTO::getValueArray)
                    .flatMap(it-> Arrays.stream(it.split(",")))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        }
        return values;
    }

    public Set<String> getPersonPreferenceValues(Set<PersonPreferenceDTO> personPreferenceList,String prefType) {
        Set<String> preferenceValues = new HashSet<>();

        if (personPreferenceList != null && prefType != null) {
            personPreferenceList.stream()
                    .filter(Objects::nonNull)
                    .filter(dto -> prefType.equals(dto.getPrefType()))
                    .filter(dto -> dto.getPrefValue() != null)
                    .flatMap(dto -> Arrays.stream(dto.getPrefValue().split(",")))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(preferenceValues::add);
        }
        return preferenceValues;
    }

    public void addLocationCoordinates(UserDTO userDTO) {
        if (userDTO.getCurrentCityCode() != null) {
            GlobalRefDataDTO globalRefDataDTO = globalRefDataCacheValidator.getValueForCode(userDTO.getCurrentCityCode());
            if(globalRefDataDTO.getNotes() != null && globalRefDataDTO.getAliasId() != null) {
                userDTO.setCurrentCityLatLong(String.join(",", globalRefDataDTO.getNotes(), globalRefDataDTO.getAliasId()));
            }
        }
        if (userDTO.getPermanentCityCode() != null) {
            GlobalRefDataDTO globalRefDataDTO = globalRefDataCacheValidator.getValueForCode(userDTO.getPermanentCityCode());
            if(globalRefDataDTO.getNotes() != null && globalRefDataDTO.getAliasId() != null) {
                userDTO.setPermanentCityLatLong(String.join(",", globalRefDataDTO.getNotes(), globalRefDataDTO.getAliasId()));
            }
        }
    }


    public Set<String> getPathwayCodes(Set<PersonHobbyDTO> personHobbySet) {
        Set<String> pathwayCodes = null;
        if (personHobbySet != null) {
            pathwayCodes = personHobbySet
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(it -> Objects.equals(it.getNotes(), MapperConstant.HOBBY_PATHWAY))
                    .map(PersonHobbyDTO::getHobby)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return pathwayCodes;
    }

    public Set<String> getPathwayLabels(Set<PersonHobbyDTO> personHobbySet) {
        Set<String> pathwayLabels = null;
        Set<String> pathwayCodes = getPathwayCodes(personHobbySet);
        if (pathwayCodes != null && !pathwayCodes.isEmpty()) {
            pathwayLabels = commonsReportUtil.getChangemakerRefDataLabelsFromCodes(pathwayCodes);
        }
        return pathwayLabels;
    }


    public String getCurrentAddressLabel(PersonFactDTO personFactDTO) {
        StringBuilder completeAddress = new StringBuilder();
        if (personFactDTO.getCurrentCity() != null && personFactDTO.getCurrentCity().contains(".OTHERS")
                && personFactDTO.getCurrentState() != null && personFactDTO.getCurrentState().contains(".OTHERS")
                && personFactDTO.getCurrentCountry() != null && personFactDTO.getCurrentCountry().contains(".OTHERS")) {
            if (personFactDTO.getCurrentAddressNotes() != null) {
                completeAddress.append(personFactDTO.getCurrentAddressNotes());
            }
        } else {
            if (personFactDTO.getCurrentCityLabel() != null) {
                completeAddress.append(personFactDTO.getCurrentCityLabel()).append(", ");
            }
            if (personFactDTO.getCurrentStateLabel() != null) {
                completeAddress.append(personFactDTO.getCurrentStateLabel()).append(", ");
            }
            if (personFactDTO.getCurrentCountryLabel() != null) {
                completeAddress.append(personFactDTO.getCurrentCountryLabel());
            }
        }
        return completeAddress.length() >= 1 ? completeAddress.toString().trim() : null;
    }

    public String getPermanentAddressLabel(PersonFactDTO personFactDTO) {
        StringBuilder completeAddress = new StringBuilder();
        if (personFactDTO.getPermanentCity() != null && personFactDTO.getPermanentCity().contains(".OTHERS")
                && personFactDTO.getPermanentState() != null && personFactDTO.getPermanentState().contains(".OTHERS")
                && personFactDTO.getPermanentCountry() != null && personFactDTO.getPermanentCountry().contains(".OTHERS")) {
            if (personFactDTO.getPermanentAddressNotes() != null) {
                completeAddress.append(personFactDTO.getPermanentAddressNotes());
            }
        } else {
            if (personFactDTO.getPermanentCityLabel() != null) {
                completeAddress.append(personFactDTO.getPermanentCityLabel()).append(", ");
            }
            if (personFactDTO.getPermanentStateLabel() != null) {
                completeAddress.append(personFactDTO.getPermanentStateLabel()).append(", ");
            }
            if (personFactDTO.getPermanentCountryLabel() != null) {
                completeAddress.append(personFactDTO.getPermanentCountryLabel());
            }
        }
        return completeAddress.length() >= 1 ? completeAddress.toString().trim() : null;
    }

    public Set<String> getOrganisationCodes(Set<PersonWorkHistoryDTO> personWorkHistorySet) {
        Set<String> organisationCodes = null;
        if (personWorkHistorySet != null) {
            organisationCodes = personWorkHistorySet.stream()
                    .filter(Objects::nonNull)
                    .map(PersonWorkHistoryDTO::getOrganisationCode)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return organisationCodes;
    }

    public Set<String> getCurrentOrganisationCodes(Set<PersonWorkHistoryDTO> personWorkHistorySet) {
        Set<String> organisationCodes = null;
        if (personWorkHistorySet != null) {
            organisationCodes = personWorkHistorySet.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> (it.getFromDate() == null && it.getToDate() == null) || Objects.equals(it.getFromDate(), it.getToDate()))
                    .map(PersonWorkHistoryDTO::getOrganisationCode)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return organisationCodes;
    }

    public Set<String> getEductionInstituteCodes(Set<PersonEducationDTO> personEducationDTOSet) {
        Set<String> educationInstituteCodes = null;
        if (personEducationDTOSet != null) {
            educationInstituteCodes = personEducationDTOSet.stream()
                    .filter(Objects::nonNull)
                    .map(PersonEducationDTO::getInstitutionCode)
                    .collect(Collectors.toSet());
        }
        return educationInstituteCodes;
    }

    public Set<String> getEductionInstituteNames(Set<PersonEducationDTO> personEducationDTOSet) {
        Set<String> educationInstituteCodes = null;
        if (personEducationDTOSet != null) {
            educationInstituteCodes = personEducationDTOSet.stream()
                    .filter(Objects::nonNull)
                    .map(PersonEducationDTO::getInstitution)
                    .collect(Collectors.toSet());
        }
        return educationInstituteCodes;
    }

    private Set<String> getEducationQualifications(Set<PersonEducationDTO> personEducationSet) {
        Set<String> educationQualifications = null;
        if (personEducationSet != null) {
            educationQualifications = personEducationSet.stream()
                    .filter(Objects::nonNull)
                    .map(PersonEducationDTO::getEducationCategory)
                    .collect(Collectors.toSet());
        }
        return educationQualifications;
    }

    private Set<String> getEducationDegrees(Set<PersonEducationDTO> personEducationSet) {
        Set<String> educationDegrees = null;
        if (personEducationSet != null) {
            educationDegrees = personEducationSet.stream()
                    .filter(Objects::nonNull)
                    .map(PersonEducationDTO::getDegree)
                    .collect(Collectors.toSet());
        }
        return educationDegrees;
    }


    public Set<String> getEmploymentOrganisationNames(Set<PersonWorkHistoryDTO> personWorkHistorySet) {
        Set<String> organisationNames = null;
        if (personWorkHistorySet != null) {
            organisationNames = personWorkHistorySet.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> Objects.equals(it.getIsActive(), Boolean.TRUE))
                    .map(PersonWorkHistoryDTO::getWorkPlace)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return organisationNames;
    }

    public Set<String> getEmploymentDesignations(Set<PersonWorkHistoryDTO> personWorkHistorySet) {
        Set<String> employmentDesignations = null;
        if (personWorkHistorySet != null) {
            employmentDesignations = personWorkHistorySet.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> Objects.equals(it.getIsActive(), Boolean.TRUE))
                    .map(PersonWorkHistoryDTO::getDesignation)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        return employmentDesignations;
    }
}