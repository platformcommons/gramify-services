package com.platformcommons.platform.service.iam.facade.mapper;

import com.mindtree.bridge.platform.dto.*;
import com.mindtree.bridge.platform.dto.AddressDTO;
import com.mindtree.bridge.platform.dto.SocialMediaAccountDTO;
import com.mindtree.bridge.platform.dto.UserDTO;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.utility.CompanyMasterDataUtil;
import com.platformcommons.platform.service.iam.application.utility.DateUtility;
import com.platformcommons.platform.service.iam.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserSyncCustomDTOMapper {

    @Autowired
    private CompanyMasterDataUtil companyMasterDataUtil;

    public UserSyncCustomDTO convertUserDTOToUserSyncCustomDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserSyncCustomDTO.UserSyncCustomDTOBuilder builder = UserSyncCustomDTO.builder();
        builder.userId(Long.valueOf(userDTO.getId()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName());

        if (userDTO.getPerson() != null) {
            PersonDTO personDTO = userDTO.getPerson();
            builder.preferredEmail(getPersonContactFieldValue(personDTO.getPersonContacts(), IAMConstant.CONTACT_TYPE_MAIL, "contact.contactValue"))
                    .contactNumber(getPersonContactFieldValue(personDTO.getPersonContacts(), IAMConstant.CONTACT_TYPE_MOBILE, "contact.contactValue"))
                    .whatsappNumber(getPersonContactFieldValue(personDTO.getPersonContacts(), IAMConstant.CONTACT_TYPE_WHATSAPP_NUMBER, "contact.contactValue"))
                    .pathwayCode(fetchPathwayCodeFromPersonDTO(personDTO.getPersonHobbies()))
                    .socialMedia(fetchSocialMedia(personDTO.getPersonSocialMedia()))
                    .addressList(fetchAddressList(personDTO.getPersonAddresses()))
                    .professionalHistoryList(fetchProfessionalHistory(personDTO.getPersonProfessionalHistoryList()))
                    .educationList(fetchEducationList(personDTO.getPersonEducationList()));

            if (personDTO.getPersonProfile() != null) {
                PersonProfileDTO personProfileDTO = personDTO.getPersonProfile();
                builder.genderCode(getRefDataCode(personProfileDTO.getGender()))
                        .dateOfBirth(personProfileDTO.getDateOfBirth())
                        .occupation(personProfileDTO.getOccupation());
            }
        }
        return builder.build();
    }

    public void syncUserSyncCustomDTOChangesIntoUserDTO(UserDTO userDTO, UserSyncCustomDTO userSyncCustomDTO) {
        if (userDTO == null || userDTO.getId() == null || userSyncCustomDTO == null || userSyncCustomDTO.getUserId() == null) {
            return;
        }
        if (userDTO.getPerson() == null) {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(0);
            userDTO.setPerson(personDTO);
        }
        if (userDTO.getPerson().getPersonProfile() == null) {
            PersonProfileDTO personProfileDTO = new PersonProfileDTO();
            personProfileDTO.setId(0);
            userDTO.getPerson().setPersonProfile(personProfileDTO);
        }
        if (userSyncCustomDTO.getFirstName() != null) {
            userDTO.setFirstName(userSyncCustomDTO.getFirstName());
        }
        if (userSyncCustomDTO.getLastName() != null) {
            userDTO.setLastName(userSyncCustomDTO.getLastName());
        }
        PersonDTO personDTO = userDTO.getPerson();
        syncUserContactsIntoPerson(personDTO, userSyncCustomDTO);
        syncPersonProfileIntoPersonDTO(personDTO, userSyncCustomDTO);
        syncPersonAddressIntoPersonDTO(personDTO, userSyncCustomDTO);
        syncPersonSocialMediaIntoPersonDTO(personDTO, userSyncCustomDTO);
        syncPersonProfessionalHistoryIntoPersonDTO(personDTO, userSyncCustomDTO);
        syncPersonEducationIntPersonDTO(personDTO, userSyncCustomDTO);
        syncPersonPathwayIntoPersonDTO(personDTO, userSyncCustomDTO);

    }

    public void syncPersonProfileIntoPersonDTO(PersonDTO personDTO, UserSyncCustomDTO userSyncCustomDTO) {
        PersonProfileDTO personProfileDTO = personDTO.getPersonProfile();
        if (userSyncCustomDTO.getGenderCode() != null) {
            personProfileDTO.setGender(buildGlobalRefDataFromCode(userSyncCustomDTO.getGenderCode()));
        }
        if (userSyncCustomDTO.getDateOfBirth() != null) {
            personProfileDTO.setDateOfBirth(userSyncCustomDTO.getDateOfBirth());
        }
        if (userSyncCustomDTO.getFirstName() != null) {
            personProfileDTO.setFirstName(userSyncCustomDTO.getFirstName());
        }
        if (userSyncCustomDTO.getLastName() != null) {
            personProfileDTO.setLastName(userSyncCustomDTO.getLastName());
        }
        if(userSyncCustomDTO.getOccupation() != null) {
            personProfileDTO.setOccupation(userSyncCustomDTO.getOccupation());
        }
    }

    public void syncUserContactsIntoPerson(PersonDTO personDTO, UserSyncCustomDTO userSyncCustomDTO) {
        List<PersonContactDTO> personContactDTOList = personDTO.getPersonContacts() == null ? new ArrayList<>() : personDTO.getPersonContacts();
        if (userSyncCustomDTO.getContactNumber() != null) {
            PersonContactDTO personContactDTO = getPersonContactDTOByContactType(personContactDTOList, IAMConstant.CONTACT_TYPE_MOBILE);
            if (personContactDTO != null) {
                personContactDTO.getContact().setContactValue(userSyncCustomDTO.getContactNumber());
            } else {
                personContactDTO = new PersonContactDTO();
                personContactDTO.setId(0);
                personContactDTO.setIsPrimary(Boolean.TRUE);
                personContactDTO.setContact(createContactDTO("+91", userSyncCustomDTO.getContactNumber(), IAMConstant.CONTACT_TYPE_MOBILE));
                personContactDTOList.add(personContactDTO);
            }
        }
        if (userSyncCustomDTO.getWhatsappNumber() != null) {
            PersonContactDTO personContactDTO = getPersonContactDTOByContactType(personContactDTOList, IAMConstant.CONTACT_TYPE_WHATSAPP_NUMBER);
            if (personContactDTO != null) {
                personContactDTO.getContact().setContactValue(userSyncCustomDTO.getWhatsappNumber());
            } else {
                personContactDTO = new PersonContactDTO();
                personContactDTO.setId(0);
                personContactDTO.setIsPrimary(Boolean.FALSE);
                personContactDTO.setContact(createContactDTO("+91", userSyncCustomDTO.getContactNumber(), IAMConstant.CONTACT_TYPE_WHATSAPP_NUMBER));
                personContactDTOList.add(personContactDTO);
            }
        }
        personDTO.setPersonContacts(personContactDTOList);
    }

    public void syncPersonProfessionalHistoryIntoPersonDTO(PersonDTO personDTO, UserSyncCustomDTO userSyncCustomDTO) {
        if (userSyncCustomDTO.getProfessionalHistoryList() != null && !userSyncCustomDTO.getProfessionalHistoryList().isEmpty()) {
            Set<ProfessionalHistorySyncCustomDTO> workExperienceSyncCustomDTOSet = userSyncCustomDTO.getProfessionalHistoryList();
            Map<String, ProfessionalHistorySyncCustomDTO> workExperienceSyncCustomDTOMap = workExperienceSyncCustomDTOSet.stream()
                    .filter(it-> !StringUtils.isEmpty(it.getCompanyName()) && !StringUtils.isEmpty(it.getDesignation()))
                    .collect(Collectors.toMap(it -> StringUtils.trim(it.getDesignation().toLowerCase() + it.getCompanyName().toLowerCase())
                            , Function.identity(), (a, b) -> a));
            List<PersonProfessionalHistoryDTO> personProfessionalHistoryDTOList = personDTO.getPersonProfessionalHistoryList() == null
                    ? new ArrayList<>() : personDTO.getPersonProfessionalHistoryList();
            Map<String, PersonProfessionalHistoryDTO> workExperienceDTOMap = personProfessionalHistoryDTOList.stream()
                    .filter(it -> it.getWorkType() != null && it.getWorkType().getDataCode().equals(IAMConstant.WORK_TYPE_EMPLOYMENT))
                    .filter(it->Objects.equals(it.getIsActive(),Boolean.TRUE))
                    .filter(it-> !StringUtils.isEmpty(it.getWorkPlace()) && !StringUtils.isEmpty(it.getDesignation()))
                    .collect(Collectors.toMap(it -> StringUtils.trim(it.getDesignation().toLowerCase() + it.getWorkPlace().toLowerCase()),
                            Function.identity(), (a, b) -> a));

            workExperienceSyncCustomDTOMap.forEach((uniqueKey, professionalHistorySyncCustomDTO) -> {
                if (workExperienceDTOMap.containsKey(uniqueKey)) {
                    PersonProfessionalHistoryDTO professionalHistoryDTO = workExperienceDTOMap.get(uniqueKey);
                    if (professionalHistorySyncCustomDTO.getToDate() != null) {
                        professionalHistoryDTO.setFromDate(professionalHistorySyncCustomDTO.getToDate());
                    }
                    if (professionalHistorySyncCustomDTO.getFromDate() != null) {
                        professionalHistoryDTO.setFromDate(professionalHistorySyncCustomDTO.getFromDate());
                    }
                } else {
                    CompanyDTO companyDTO = companyMasterDataUtil.getCompanyDTOByCompanyName(professionalHistorySyncCustomDTO.getCompanyName());

                    PersonProfessionalHistoryDTO professionalHistoryDTO = new PersonProfessionalHistoryDTO();
                    professionalHistoryDTO.setId(0);
                    professionalHistoryDTO.setWorkPlace(professionalHistorySyncCustomDTO.getCompanyName());
                    professionalHistoryDTO.setWorkType(buildGlobalRefDataFromCode(IAMConstant.WORK_TYPE_EMPLOYMENT));
                    professionalHistoryDTO.setDesignation(professionalHistorySyncCustomDTO.getDesignation());
                    professionalHistoryDTO.setFromDate(professionalHistorySyncCustomDTO.getFromDate());
                    professionalHistoryDTO.setToDate(professionalHistorySyncCustomDTO.getToDate());

                    List<ExtraAttributeDTO> extraAttributeDTOList = new ArrayList<>();
                    if (companyDTO != null) {
                        if (companyDTO.getCode() != null) {
                            extraAttributeDTOList.add(buildExtraAttributeDTO(IAMConstant.PERSON_WORK_ORGANISATION_CODE, companyDTO.getCode()));
                        }
                        if (companyDTO.getLogo() != null) {
                            extraAttributeDTOList.add(buildExtraAttributeDTO(IAMConstant.PERSON_WORK_LOGO_URL, companyDTO.getLogo()));
                        }
                    }
                    professionalHistoryDTO.setExtraAttributeList(extraAttributeDTOList);
                    personProfessionalHistoryDTOList.add(professionalHistoryDTO);
                }
            });
            personDTO.setPersonProfessionalHistoryList(personProfessionalHistoryDTOList);
        }
    }


    public void syncPersonEducationIntPersonDTO(PersonDTO personDTO, UserSyncCustomDTO userSyncCustomDTO) {
        if (userSyncCustomDTO.getEducationList() != null && !userSyncCustomDTO.getEducationList().isEmpty()) {
            Set<EducationSyncCustomDTO> educationSyncCustomDTOSet = userSyncCustomDTO.getEducationList();
            Map<String, EducationSyncCustomDTO> educationSyncCustomDTOMap = educationSyncCustomDTOSet.stream()
                    .collect(Collectors.toMap(it -> {
                        if (it.getDegree() != null && it.getQualificationCode() != null) {
                            return StringUtils.trim(it.getDegree().toLowerCase() + it.getQualificationCode().toLowerCase());
                        } else {
                            return null;
                        }
                    }, Function.identity(), (a, b) -> a));
            List<PersonEducationDTO> personEducationDTOList = personDTO.getPersonEducationList() == null
                    ? new ArrayList<>() : personDTO.getPersonEducationList();
            Map<String, PersonEducationDTO> personEducationDTOMap = personEducationDTOList.stream()
                    .filter(Objects::nonNull)
                    .filter(it->Objects.equals(it.getIsActive(),Boolean.TRUE))
                    .collect(Collectors.toMap(it -> {
                                String degree = getExtraAttributeValue(it.getExtraAttributeList(), IAMConstant.PERSON_EDUCATION_DEGREE);
                                String qualificationCode = getRefDataCode(it.getEducationCategory());
                                if (degree != null && qualificationCode != null) {
                                    return StringUtils.trim(degree.toLowerCase() + qualificationCode.toLowerCase());
                                } else {
                                    return null;
                                }
                            },
                            Function.identity(), (a, b) -> a));

            educationSyncCustomDTOMap.forEach((uniqueKey, educationSyncCustomDTO) -> {
                if (personEducationDTOMap.containsKey(uniqueKey)) {
                    PersonEducationDTO personEducationDTO = personEducationDTOMap.get(uniqueKey);
                    if (educationSyncCustomDTO.getInstitution() != null) {
                        personEducationDTO.setInstitution(educationSyncCustomDTO.getInstitution());
                    }
                    if (educationSyncCustomDTO.getFromDate() != null) {
                        personEducationDTO.setNotes(DateUtility.convertDateObjectToStringInUTCZone(educationSyncCustomDTO.getFromDate()));
                    }
                    if (educationSyncCustomDTO.getToDate() != null) {
                        personEducationDTO.setYearOfPassing(educationSyncCustomDTO.getToDate());
                    }
                    List<ExtraAttributeDTO> extraAttributeDTOList = personEducationDTO.getExtraAttributeList() == null
                            ? new ArrayList<>() : personEducationDTO.getExtraAttributeList();
                    if (educationSyncCustomDTO.getSpecialisation() != null) {
                        ExtraAttributeDTO extraAttributeDTO = getExtraAttributeByMetaDataCode(extraAttributeDTOList, IAMConstant.PERSON_EDUCATION_SPECIALISATION);
                        if (extraAttributeDTO != null) {
                            extraAttributeDTO.setAttributeValue(educationSyncCustomDTO.getSpecialisation());
                        } else {
                            extraAttributeDTOList.add(buildExtraAttributeDTO(IAMConstant.PERSON_EDUCATION_SPECIALISATION,
                                    educationSyncCustomDTO.getSpecialisation()));
                        }
                    }
                    personEducationDTO.setExtraAttributeList(extraAttributeDTOList);
                } else {
                    PersonEducationDTO personEducationDTO = new PersonEducationDTO();
                    personEducationDTO.setId(0);
                    personEducationDTO.setInstitution(educationSyncCustomDTO.getInstitution());
                    personEducationDTO.setEducationCategory(buildGlobalRefDataFromCode(educationSyncCustomDTO.getQualificationCode()));
                    personEducationDTO.setNotes(DateUtility.convertDateObjectToStringInUTCZone(educationSyncCustomDTO.getFromDate()));
                    personEducationDTO.setYearOfPassing(educationSyncCustomDTO.getToDate());

                    List<ExtraAttributeDTO> extraAttributeDTOList = new ArrayList<>();
                    if (educationSyncCustomDTO.getDegree() != null) {
                        extraAttributeDTOList.add(buildExtraAttributeDTO(IAMConstant.PERSON_EDUCATION_DEGREE, educationSyncCustomDTO.getDegree()));
                    }
                    if (educationSyncCustomDTO.getSpecialisation() != null) {
                        extraAttributeDTOList.add(buildExtraAttributeDTO(IAMConstant.PERSON_EDUCATION_SPECIALISATION, educationSyncCustomDTO.getSpecialisation()));
                    }

                    personEducationDTO.setExtraAttributeList(extraAttributeDTOList);
                    personEducationDTOList.add(personEducationDTO);
                }
            });
            personDTO.setPersonEducationList(personEducationDTOList);
        }
    }

    public void syncPersonAddressIntoPersonDTO(PersonDTO personDTO, UserSyncCustomDTO userSyncCustomDTO) {
        if (userSyncCustomDTO.getAddressList() != null && !userSyncCustomDTO.getAddressList().isEmpty()) {
            Map<String, AddressSyncCustomDTO> addressSyncCustomDTOMap = userSyncCustomDTO.getAddressList().stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(AddressSyncCustomDTO::getAddressTypeCode, Function.identity(), (a, b) -> a));
            List<PersonAddressDTO> personAddressDTOList = personDTO.getPersonAddresses() == null ? new ArrayList<>() : personDTO.getPersonAddresses();
            Map<String, PersonAddressDTO> personAddressDTOMap = personAddressDTOList.stream()
                    .filter(Objects::nonNull)
                    .filter(it-> Objects.equals(it.getIsActive(),Boolean.TRUE)
                                 && it.getAddress() != null && Objects.equals(it.getAddress().getIsActive(),Boolean.TRUE))
                    .sorted((o1, o2) -> -(o1.getCreatedDateTime().compareTo(o2.getCreatedDateTime())))
                    .collect(Collectors.toMap(it -> getRefDataCode(it.getAddressType()), Function.identity(), (a, b) -> a));

            syncPersonAddressFromAddressSyncCustomToAddressDTO(addressSyncCustomDTOMap,
                    personAddressDTOMap, IAMConstant.ADDRESS_TYPE_TEMPORARY, personAddressDTOList);
            syncPersonAddressFromAddressSyncCustomToAddressDTO(addressSyncCustomDTOMap,
                    personAddressDTOMap, IAMConstant.ADDRESS_TYPE_PERMANENT, personAddressDTOList);

            personDTO.setPersonAddresses(personAddressDTOList);
        }
    }

    public void syncPersonAddressFromAddressSyncCustomToAddressDTO(Map<String, AddressSyncCustomDTO> addressSyncCustomDTOMap,
                                                         Map<String, PersonAddressDTO> personAddressDTOMap, String addressType,
                                                         List<PersonAddressDTO> personAddressDTOList) {
        if (addressSyncCustomDTOMap.containsKey(addressType)) {
            AddressSyncCustomDTO addressSyncCustomDTO = addressSyncCustomDTOMap.get(addressType);
            if (personAddressDTOMap.containsKey(addressType)) {
                PersonAddressDTO personAddressDTO = personAddressDTOMap.get(addressType);
                AddressDTO addressDTO = personAddressDTO.getAddress();
                if (addressSyncCustomDTO.getAddressLine1() != null) {
                    addressDTO.setAddressLine1(addressSyncCustomDTO.getAddressLine1());
                }
                if (addressSyncCustomDTO.getAddressLine2() != null) {
                    addressDTO.setAddressLine2(addressSyncCustomDTO.getAddressLine2());
                }
                if (addressSyncCustomDTO.getStateCode() != null) {
                    addressDTO.setState(buildGlobalRefDataFromCode(addressSyncCustomDTO.getStateCode()));
                }
                if (addressSyncCustomDTO.getCountryCode() != null) {
                    addressDTO.setCountry(buildGlobalRefDataFromCode(addressSyncCustomDTO.getCountryCode()));
                }
                if (addressSyncCustomDTO.getCityCode() != null) {
                    addressDTO.setCity(buildGlobalRefDataFromCode(addressSyncCustomDTO.getCityCode()));
                }
                if (addressSyncCustomDTO.getPinCode() != null) {
                    addressDTO.setPinCode(addressSyncCustomDTO.getPinCode());
                }
                if (addressSyncCustomDTO.getAddressNotes() != null) {
                    addressDTO.setNotes(addressSyncCustomDTO.getAddressNotes());
                }
            } else {
                PersonAddressDTO personAddressDTO = new PersonAddressDTO();
                personAddressDTO.setId(0);
                personAddressDTO.setAddressType(buildGlobalRefDataFromCode(addressType));

                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setId(0);
                addressDTO.setAddressLine1(addressSyncCustomDTO.getAddressLine1());
                addressDTO.setAddressLine2(addressSyncCustomDTO.getAddressLine2());
                addressDTO.setCity(buildGlobalRefDataFromCode(addressSyncCustomDTO.getCityCode()));
                addressDTO.setState(buildGlobalRefDataFromCode(addressSyncCustomDTO.getStateCode()));
                addressDTO.setCountry(buildGlobalRefDataFromCode(addressSyncCustomDTO.getCountryCode()));
                addressDTO.setNotes(addressSyncCustomDTO.getAddressNotes());
                addressDTO.setPinCode(addressSyncCustomDTO.getPinCode());

                personAddressDTO.setAddress(addressDTO);
                personAddressDTOList.add(personAddressDTO);
            }
        }
    }

    public void syncPersonSocialMediaIntoPersonDTO(PersonDTO personDTO, UserSyncCustomDTO userSyncCustomDTO) {
        if (userSyncCustomDTO.getSocialMedia() != null) {
            SocialMediaSyncCodeDTO socialMediaSyncCodeDTO = userSyncCustomDTO.getSocialMedia();
            List<PersonSocialMediaDTO> personSocialMediaDTOList = personDTO.getPersonSocialMedia() == null ? new ArrayList<>()
                    : personDTO.getPersonSocialMedia();

            if (socialMediaSyncCodeDTO.getLinkedIn() != null) {
                createOrUpdatePersonSocialMediaDTO(personSocialMediaDTOList, IAMConstant.PERSON_SOCIAL_MEDIA_LINKEDIN, socialMediaSyncCodeDTO.getLinkedIn());
            }
            if (socialMediaSyncCodeDTO.getFacebook() != null) {
                createOrUpdatePersonSocialMediaDTO(personSocialMediaDTOList, IAMConstant.PERSON_SOCIAL_MEDIA_FACEBOOK, socialMediaSyncCodeDTO.getFacebook());
            }
            if (socialMediaSyncCodeDTO.getInstagram() != null) {
                createOrUpdatePersonSocialMediaDTO(personSocialMediaDTOList, IAMConstant.PERSON_SOCIAL_MEDIA_INSTAGRAM, socialMediaSyncCodeDTO.getInstagram());
            }
            if (socialMediaSyncCodeDTO.getTwitter() != null) {
                createOrUpdatePersonSocialMediaDTO(personSocialMediaDTOList, IAMConstant.PERSON_SOCIAL_MEDIA_TWITTER, socialMediaSyncCodeDTO.getTwitter());
            }

            personDTO.setPersonSocialMedia(personSocialMediaDTOList);
        }
    }

    public void createOrUpdatePersonSocialMediaDTO(List<PersonSocialMediaDTO> personSocialMediaDTOList, String socialMediaType, String updatedSocialMediaValue) {
        PersonSocialMediaDTO personSocialMediaDTO = getPersonSocialMediaDTO(personSocialMediaDTOList, socialMediaType);
        if (personSocialMediaDTO != null) {
            personSocialMediaDTO.getSocialMediaAccount().setSocialMediaValue(updatedSocialMediaValue);
        } else {
            personSocialMediaDTO = new PersonSocialMediaDTO();
            personSocialMediaDTO.setId(0);

            SocialMediaAccountDTO socialMediaAccountDTO = new SocialMediaAccountDTO();
            socialMediaAccountDTO.setId(0);
            socialMediaAccountDTO.setSocialMediaValue(updatedSocialMediaValue);
            socialMediaAccountDTO.setSocialMediaType(buildGlobalRefDataFromCode(socialMediaType));
            personSocialMediaDTO.setSocialMediaAccount(socialMediaAccountDTO);

            personSocialMediaDTOList.add(personSocialMediaDTO);
        }
    }


    public ContactDTO createContactDTO(String aliasId, String contactValue, String contactType) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(0);
        contactDTO.setAliasId(aliasId);
        contactDTO.setContactValue(contactValue);
        contactDTO.setContactType(buildGlobalRefDataFromCode(contactType));
        return contactDTO;
    }

    public Set<EducationSyncCustomDTO> fetchEducationList(List<PersonEducationDTO> personEducationDTOList) {
        Set<EducationSyncCustomDTO> educationSyncCustomDTOSet = new HashSet<>();
        if (personEducationDTOList != null && !personEducationDTOList.isEmpty()) {
            personEducationDTOList = personEducationDTOList.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                    .collect(Collectors.toList());
            personEducationDTOList.forEach(personEducationDTO -> {
                EducationSyncCustomDTO customDTO = EducationSyncCustomDTO.builder()
                        .degree(getExtraAttributeValue(personEducationDTO.getExtraAttributeList(), IAMConstant.PERSON_EDUCATION_DEGREE))
                        .specialisation(getExtraAttributeValue(personEducationDTO.getExtraAttributeList(), IAMConstant.PERSON_EDUCATION_SPECIALISATION))
                        .institution(personEducationDTO.getInstitution())
                        .qualificationCode(getRefDataCode(personEducationDTO.getEducationCategory()))
                        .fromDate(DateUtility.convertUTCStringToUTCZoneDate(personEducationDTO.getNotes()))
                        .toDate(personEducationDTO.getYearOfPassing())
                        .currentlyStudying(personEducationDTO.getYearOfPassing() != null && new Date().before(personEducationDTO.getYearOfPassing())
                                ? Boolean.TRUE : Boolean.FALSE)
                        .build();
                educationSyncCustomDTOSet.add(customDTO);
            });
        }
        return educationSyncCustomDTOSet;
    }

    public Set<ProfessionalHistorySyncCustomDTO> fetchProfessionalHistory(List<PersonProfessionalHistoryDTO> personProfessionalHistoryDTOList) {
        Set<ProfessionalHistorySyncCustomDTO> professionalHistorySyncCustomDTOSet = new HashSet<>();
        if (personProfessionalHistoryDTOList != null && !personProfessionalHistoryDTOList.isEmpty()) {
            personProfessionalHistoryDTOList = personProfessionalHistoryDTOList.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                    .filter(it -> it.getWorkType() != null && it.getWorkType().getDataCode().equals(IAMConstant.WORK_TYPE_EMPLOYMENT))
                    .collect(Collectors.toList());
            personProfessionalHistoryDTOList.forEach(professionalHistoryDTO -> {
                ProfessionalHistorySyncCustomDTO customDTO = ProfessionalHistorySyncCustomDTO.builder()
                        .designation(professionalHistoryDTO.getDesignation())
                        .companyName(professionalHistoryDTO.getWorkPlace())
                        .fromDate(professionalHistoryDTO.getFromDate())
                        .toDate(professionalHistoryDTO.getToDate())
                        .currentCompany((professionalHistoryDTO.getFromDate() != null && professionalHistoryDTO.getToDate() != null
                                && professionalHistoryDTO.getFromDate().equals(professionalHistoryDTO.getToDate()))
                                || (professionalHistoryDTO.getFromDate() != null && professionalHistoryDTO.getToDate() == null)
                                ? Boolean.TRUE : Boolean.FALSE)
                        .build();

                professionalHistorySyncCustomDTOSet.add(customDTO);
            });
        }
        return professionalHistorySyncCustomDTOSet;
    }

    public Set<AddressSyncCustomDTO> fetchAddressList(List<PersonAddressDTO> personAddresses) {
        Set<AddressSyncCustomDTO> addressSyncCustomDTOSet = new HashSet<>();
        if (personAddresses != null && !personAddresses.isEmpty()) {
            AddressDTO permanentAddress = getAddress(personAddresses, IAMConstant.ADDRESS_TYPE_PERMANENT);
            AddressDTO currentAddress = getAddress(personAddresses, IAMConstant.ADDRESS_TYPE_TEMPORARY);
            if (permanentAddress != null) {
                addressSyncCustomDTOSet.add(convertAddressDTOToSyncCustomDTO(permanentAddress, IAMConstant.ADDRESS_TYPE_PERMANENT));
            }
            if (currentAddress != null) {
                addressSyncCustomDTOSet.add(convertAddressDTOToSyncCustomDTO(currentAddress, IAMConstant.ADDRESS_TYPE_TEMPORARY));
            }
        }
        return addressSyncCustomDTOSet;
    }

    public AddressDTO getAddress(List<PersonAddressDTO> personAddresses, String addressType) {
        return personAddresses.stream()
                .filter(Objects::nonNull)
                .filter(it -> it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                .filter(personAddressDTO -> personAddressDTO.getAddressType() != null &&
                        personAddressDTO.getAddressType().getDataCode().equals(addressType)
                )
                .sorted((o1, o2) -> -(o1.getCreatedDateTime().compareTo(o2.getCreatedDateTime())))
                .map(PersonAddressDTO::getAddress)
                .filter(it -> it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                .findFirst()
                .orElse(null);

    }

    public AddressSyncCustomDTO convertAddressDTOToSyncCustomDTO(AddressDTO addressDTO, String addressType) {
        return AddressSyncCustomDTO.builder()
                .addressLine1(addressDTO.getAddressLine1())
                .addressLine2(addressDTO.getAddressLine2())
                .countryCode(getRefDataCode(addressDTO.getCountry()))
                .stateCode(getRefDataCode(addressDTO.getState()))
                .cityCode(getRefDataCode(addressDTO.getCity()))
                .cityLabel(getRefDataCodeLabel(addressDTO.getCity()))
                .pinCode(addressDTO.getPinCode())
                .addressNotes(addressDTO.getNotes())
                .addressTypeCode(addressType)
                .build();
    }

    public SocialMediaSyncCodeDTO fetchSocialMedia(List<PersonSocialMediaDTO> personSocialMediaList) {
        SocialMediaSyncCodeDTO socialMediaSyncCodeDTO = null;
        if (personSocialMediaList != null && !personSocialMediaList.isEmpty()) {
            socialMediaSyncCodeDTO = SocialMediaSyncCodeDTO.builder()
                    .linkedIn(getPersonSocialMediaFieldValue(personSocialMediaList, IAMConstant.PERSON_SOCIAL_MEDIA_LINKEDIN, "socialMedia.socialMediaValue"))
                    .instagram(getPersonSocialMediaFieldValue(personSocialMediaList, IAMConstant.PERSON_SOCIAL_MEDIA_INSTAGRAM, "socialMedia.socialMediaValue"))
                    .facebook(getPersonSocialMediaFieldValue(personSocialMediaList, IAMConstant.PERSON_SOCIAL_MEDIA_FACEBOOK, "socialMedia.socialMediaValue"))
                    .twitter(getPersonSocialMediaFieldValue(personSocialMediaList, IAMConstant.PERSON_SOCIAL_MEDIA_TWITTER, "socialMedia.socialMediaValue"))
                    .build();
        }
        return socialMediaSyncCodeDTO;
    }

    public String getPersonContactFieldValue(List<PersonContactDTO> personContactDTOS, String contactType, String field) {
        String personContactFieldValue = null;
        if (personContactDTOS != null && !personContactDTOS.isEmpty() && contactType != null && field != null) {
            PersonContactDTO personContactDTO = getPersonContactDTOByContactType(personContactDTOS, contactType);
            if (personContactDTO != null) {
                String value = null;
                if (field.equalsIgnoreCase("contact.contactvalue")) {
                    value = personContactDTO.getContact().getContactValue();
                }
                personContactFieldValue = Objects.equals(value, "null") ? null : value;
            }
        }
        return personContactFieldValue;
    }

    public PersonContactDTO getPersonContactDTOByContactType(List<PersonContactDTO> personContactDTOS, String contactType) {
        PersonContactDTO personContactDTO = null;
        if (personContactDTOS != null && !personContactDTOS.isEmpty()) {
            personContactDTO = personContactDTOS.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                    .filter(it -> it.getContact() != null
                            && it.getContact().getIsActive() != null
                            && it.getContact().getIsActive().equals(Boolean.TRUE)
                            && it.getContact().getContactType() != null
                            && it.getContact().getContactType().getDataCode().equals(contactType))
                    .findFirst()
                    .orElse(null);
        }
        return personContactDTO;
    }

    public String getPersonSocialMediaFieldValue(List<PersonSocialMediaDTO> personSocialMediaDTOS, String socialMediaType, String field) {
        String socialMediaFieldValue = null;
        if (personSocialMediaDTOS != null && !personSocialMediaDTOS.isEmpty() && socialMediaType != null && field != null) {
            PersonSocialMediaDTO personSocialMediaDTO = getPersonSocialMediaDTO(personSocialMediaDTOS, socialMediaType);
            if (personSocialMediaDTO != null) {
                String value = null;
                if (field.equalsIgnoreCase("socialmedia.socialmediavalue")) {
                    value = personSocialMediaDTO.getSocialMediaAccount().getSocialMediaValue();
                }
                socialMediaFieldValue = Objects.equals(value, "null") ? null : value;
            }
        }
        return socialMediaFieldValue;
    }

    public PersonSocialMediaDTO getPersonSocialMediaDTO(List<PersonSocialMediaDTO> personSocialMediaDTOS, String socialMediaType) {
        PersonSocialMediaDTO personSocialMediaDTO = null;
        if (personSocialMediaDTOS != null && !personSocialMediaDTOS.isEmpty()) {
            personSocialMediaDTO = personSocialMediaDTOS.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                    .filter(it -> it.getSocialMediaAccount() != null
                            && it.getSocialMediaAccount().getIsActive() != null
                            && it.getSocialMediaAccount().getIsActive().equals(Boolean.TRUE)
                            && it.getSocialMediaAccount().getSocialMediaType() != null
                            && it.getSocialMediaAccount().getSocialMediaType().getDataCode().equals(socialMediaType)
                    )
                    .findFirst()
                    .orElse(null);
        }
        return personSocialMediaDTO;
    }

    public void syncPersonPathwayIntoPersonDTO(PersonDTO personDTO, UserSyncCustomDTO userSyncCustomDTO) {
        if (userSyncCustomDTO.getPathwayCode() != null) {
            List<PersonHobbyDTO> personHobbyDTOList = personDTO.getPersonHobbies() == null ? new ArrayList<>() : personDTO.getPersonHobbies();
            PersonHobbyDTO personHobbyDTO = getPersonPathway(personHobbyDTOList);

            if (personHobbyDTO != null) {
                personHobbyDTO.setHobby(userSyncCustomDTO.getPathwayCode());
            } else {
                personHobbyDTO = new PersonHobbyDTO();
                personHobbyDTO.setId(0);
                personHobbyDTO.setNotes(IAMConstant.HOBBY_PATHWAY);
                personHobbyDTO.setHobby(userSyncCustomDTO.getPathwayCode());
                personHobbyDTOList.add(personHobbyDTO);
            }
            personDTO.setPersonHobbies(personHobbyDTOList);
        }
    }

    public String fetchPathwayCodeFromPersonDTO(List<PersonHobbyDTO> personHobbyDTOList) {
        String pathwayCode = null;
        PersonHobbyDTO personHobbyDTO = getPersonPathway(personHobbyDTOList);
        if(personHobbyDTO != null) {
            pathwayCode = personHobbyDTO.getHobby();
        }
        return pathwayCode;
    }

    public PersonHobbyDTO getPersonPathway(List<PersonHobbyDTO> personHobbyDTOList) {
        PersonHobbyDTO personHobbyDTO = null;
        if (personHobbyDTOList != null && !personHobbyDTOList.isEmpty()) {
            personHobbyDTO = personHobbyDTOList.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> Objects.equals(it.getIsActive(), Boolean.TRUE))
                    .filter(it -> Objects.equals(it.getNotes(), IAMConstant.HOBBY_PATHWAY))
                    .findFirst()
                    .orElse(null);
        }
        return personHobbyDTO;
    }

    public String getExtraAttributeValue(List<ExtraAttributeDTO> extraAttributeDTOS, String metadata) {
        String attributeValue = null;
        ExtraAttributeDTO extraAttributeDTO = getExtraAttributeByMetaDataCode(extraAttributeDTOS, metadata);
        if (extraAttributeDTO != null) {
            attributeValue = extraAttributeDTO.getAttributeValue();
        }
        return attributeValue;
    }

    public ExtraAttributeDTO getExtraAttributeByMetaDataCode(List<ExtraAttributeDTO> extraAttributeDTOS, String metadata) {
        ExtraAttributeDTO extraAttributeDTO = null;
        if (extraAttributeDTOS != null && !extraAttributeDTOS.isEmpty()) {
            extraAttributeDTO = extraAttributeDTOS.stream()
                    .filter(Objects::nonNull)
                    .filter(it -> it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                    .filter(it -> it.getMetadata() != null
                            && it.getMetadata().equals(metadata))
                    .min((o1, o2) -> -(o1.getCreatedDateTime().compareTo(o2.getCreatedDateTime())))
                    .orElse(null);
        }
        return extraAttributeDTO;
    }

    public static ExtraAttributeDTO buildExtraAttributeDTO(String metadata, String attributeValue) {
        ExtraAttributeDTO extraAttributeDTO = null;
        if (metadata != null && attributeValue != null) {
            extraAttributeDTO = new ExtraAttributeDTO();
            extraAttributeDTO.setId(0);
            extraAttributeDTO.setAttributeValue(attributeValue);
            extraAttributeDTO.setAttributeType(IAMConstant.ATTRIBUTE_TYPE_GLOBAL);
            extraAttributeDTO.setMetadata(metadata);
        }
        return extraAttributeDTO;
    }


    public String getRefDataCode(GlobalRefDataDTO globalRefDataDTO) {
        if (globalRefDataDTO != null && globalRefDataDTO.getDataCode() != null) {
            return globalRefDataDTO.getDataCode();
        }
        return null;
    }

    public String getRefDataCodeLabel(GlobalRefDataDTO globalRefDataDTO) {
        if (globalRefDataDTO != null) {
            return globalRefDataDTO.getLabel();
        }
        return null;
    }

    public GlobalRefDataDTO buildGlobalRefDataFromCode(String dataCode) {
        GlobalRefDataDTO globalRefDataDTO = null;
        if (dataCode != null) {
            globalRefDataDTO = new GlobalRefDataDTO();
            globalRefDataDTO.setDataCode(dataCode);
        }
        return globalRefDataDTO;
    }
}
