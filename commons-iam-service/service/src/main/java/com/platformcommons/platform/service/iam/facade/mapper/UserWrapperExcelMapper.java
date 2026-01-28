package com.platformcommons.platform.service.iam.facade.mapper;

import com.mindtree.bridge.platform.dto.*;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.utility.TLDUtil;
import com.platformcommons.platform.service.iam.domain.vo.UserSelfRegistrationExcelVO;
import com.platformcommons.platform.service.iam.facade.client.utility.CommonsReportUtil;
import com.platformcommons.platform.service.iam.dto.RefDataCodeAndLabelDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.platformcommons.platform.service.iam.application.constant.IAMConstant.*;

@Component
public class UserWrapperExcelMapper {

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    @Autowired
    private TLDUtil tldUtil;

    private static final Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern contactNumberPattern = Pattern.compile("\\d{10}");
    private static final Pattern pincodePattern = Pattern.compile("\\d{6}");
    private static final Pattern firstNamePattern = Pattern.compile("[a-zA-Z]*");
    private static final Pattern lastNamePattern = Pattern.compile("[a-zA-Z ]*");

    public UserWrapperDTO toUserWrapperDTO(UserSelfRegistrationExcelVO excelVO) {
        String firstName = makeFirstAlphabetOfEachWordsCapital(getStringValueFromFieldValue(excelVO.getFirstName()));
        String lastName = makeFirstAlphabetOfEachWordsCapital(getStringValueFromFieldValue(excelVO.getLastName()));
        String login = getStringValueFromFieldValue(excelVO.getLogin());
        String gender = getStringValueFromFieldValue(excelVO.getGender());
        String roleCode = getStringValueFromFieldValue(excelVO.getRoleCode());

        applyRegexForTheGivenFieldText(firstNamePattern, firstName, FIRST_NAME);
        applyRegexForTheGivenFieldText(lastNamePattern, lastName, LAST_NAME);

        List<ContactDTO> contactDTOList = fetchContactsFromExcelVO(excelVO);
        List<UserContactDTO> userContactDTOList = createUserContactDTOList(contactDTOList);

        PersonDTO personDTO = createPersonDTO(excelVO, firstName, lastName, gender, contactDTOList);

        // Build the UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setLogin(login);
        userDTO.setPerson(personDTO);

        // Build the UserWrapperDTO
        UserWrapperDTO userWrapperDTO = new UserWrapperDTO();
        userWrapperDTO.setPassword(generatePassword(firstName));
        userWrapperDTO.setUserAddressDTOList(Collections.emptyList());
        userWrapperDTO.setUserContactDTOList(userContactDTOList);
        userWrapperDTO.setFunctionName("VMS");
        userWrapperDTO.setUserDTO(userDTO);

        if(roleCode != null) {
            userWrapperDTO.setUserRoleCode(roleCode);
        }

        return userWrapperDTO;

    }

    private  static String generatePassword(String prefixString) {
        String randomPart = UUID.randomUUID().toString().replace("-","").substring(0,5);
        return prefixString + "@" + randomPart;
    }

    public PersonDTO createPersonDTO(UserSelfRegistrationExcelVO excelVO, String firstName, String lastName, String gender,
                                     List<ContactDTO> contactDTOList) {
        List<PersonAddressDTO> personAddressDTOList = fetchPersonAddressFromExcelVO(excelVO);
        List<PersonContactDTO> personContactDTOList = createPersonContactDTOFromContactDTO(contactDTOList);

        PersonProfileDTO personProfileDTO = new PersonProfileDTO();
        personProfileDTO.setId(0);
        personProfileDTO.setFirstName(firstName);
        personProfileDTO.setLastName(lastName);
        personProfileDTO.setGender(getGlobalRefDataByCode(gender));
        personProfileDTO.setDateOfBirth(excelVO.getDob());

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(0);
        personDTO.setPersonAddresses(personAddressDTOList);
        personDTO.setPersonContacts(personContactDTOList);
        personDTO.setPersonProfile(personProfileDTO);

        return personDTO;

    }

    public GlobalRefDataDTO getGlobalRefDataByLabelAndList(String label, String fieldType, List<GlobalRefDataDTO> globalRefDataDTOList) {
        GlobalRefDataDTO globalRefDataDTO = null;

        if(label != null && globalRefDataDTOList != null) {
            globalRefDataDTO = globalRefDataDTOList.stream()
                    .filter(it -> it != null && it.getLabel() != null)
                    .filter(it -> it.getLabel().equalsIgnoreCase(label))
                    .findFirst().orElseThrow(() -> new NotFoundException(
                            String.format("%s - %s is invalid",fieldType, label )));
        }

        return globalRefDataDTO;
    }

    public GlobalRefDataDTO getGlobalRefDataByCode(String dataCode) {
        GlobalRefDataDTO globalRefDataDTO = null;
        if(dataCode != null) {
            globalRefDataDTO = new GlobalRefDataDTO();
            globalRefDataDTO.setDataCode(dataCode);
        }
        return globalRefDataDTO;
    }

    public ContactDTO createContactDTO(String aliasId, String contactValue, String contactType) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(0);
        contactDTO.setAliasId(StringUtils.isBlank(aliasId) ? null : aliasId);
        contactDTO.setContactValue(StringUtils.isBlank(contactValue) ? null : contactValue);
        contactDTO.setContactType(getGlobalRefDataByCode(contactType));

        return contactDTO;
    }

    public AddressDTO createAddressDTO(String countryLabel, String stateLabel, String cityLabel, String pinCode) {
        String countryCode = null;
        String stateCode = null;
        String cityCode = null;
        List<String> countryClassCode = new ArrayList<>(Arrays.asList("GREF.REGION_COUNTRY"));
        List<String> stateAndUTClassCodes = new ArrayList<>(Arrays.asList("GREF.REGION_STATE", "GREF.REGION_UNION_TERRITORY"));
        List<String> cityClassCode = new ArrayList<>(Arrays.asList("GREF.REGION_CITY"));

        RefDataCodeAndLabelDTO countryRefDataDTO = commonsReportUtil.getRefDataCodeFromLabel(countryLabel, countryClassCode)
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(String.format("CountryCode for Country label - %s not found", countryLabel)));
        countryCode = countryRefDataDTO.getRefDataCode();

        if (stateLabel != null) {
            RefDataCodeAndLabelDTO stateRefDataDTO = commonsReportUtil.getRefDataCodeFromLabel(stateLabel, stateAndUTClassCodes)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new InvalidInputException(String.format("StateCode for State label - %s not found", stateLabel)));
            stateCode = stateRefDataDTO.getRefDataCode();
        }

        if (cityLabel != null) {
            RefDataCodeAndLabelDTO cityRefDataDTO = commonsReportUtil.getRefDataCodeFromLabel(cityLabel, cityClassCode)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new InvalidInputException(String.format("CityCode for City label - %s not found", cityLabel)));
            cityCode = cityRefDataDTO.getRefDataCode();
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(0);
        addressDTO.setState(stateCode == null ? null : getGlobalRefDataByCode(stateCode));
        addressDTO.setCountry(countryCode == null ? null : getGlobalRefDataByCode(countryCode));
        addressDTO.setCity(cityCode == null ? null : getGlobalRefDataByCode(cityCode));
        addressDTO.setPinCode(pinCode);

        return addressDTO;
    }

    public List<PersonAddressDTO> fetchPersonAddressFromExcelVO(UserSelfRegistrationExcelVO excelVO) {
        List<PersonAddressDTO> personAddressDTOList = new ArrayList<>();

        if (!StringUtils.isBlank(excelVO.getCurrentAddressCountry())) {
            applyRegexForTheGivenFieldText(pincodePattern, excelVO.getCurrentAddressPinCode(), PIN_CODE);
            String countryLabel = getStringValueFromFieldValue(excelVO.getCurrentAddressCountry());
            String stateLabel = getStringValueFromFieldValue(excelVO.getCurrentAddressState());
            String cityLabel = getStringValueFromFieldValue(excelVO.getCurrentAddressCity());
            String pinCode = getStringValueFromFieldValue(excelVO.getCurrentAddressPinCode());

            PersonAddressDTO personAddressDTO = new PersonAddressDTO();
            personAddressDTO.setId(0);
            personAddressDTO.setAddressType(getGlobalRefDataByCode(IAMConstant.USER_ADDRESS_TYPE_TEMPORARY));
            personAddressDTO.setAddress(createAddressDTO(countryLabel, stateLabel, cityLabel, pinCode));

            personAddressDTOList.add(personAddressDTO);
        }

        return personAddressDTOList;
    }

    public List<PersonContactDTO> createPersonContactDTOFromContactDTO(List<ContactDTO> contactDTOList) {
        List<PersonContactDTO> personContactDTOList = new ArrayList<>();

        if (contactDTOList != null && !contactDTOList.isEmpty()) {
            for (ContactDTO contactDTO : contactDTOList) {
                PersonContactDTO personContactDTO = new PersonContactDTO();
                personContactDTO.setId(0);
                personContactDTO.setContact(contactDTO);
                personContactDTOList.add(personContactDTO);
            }
        }

        return personContactDTOList;
    }

    public List<UserContactDTO> createUserContactDTOList(List<ContactDTO> contactDTOList) {
        List<UserContactDTO> userContactDTOList = new ArrayList<>();

        if (contactDTOList != null && !contactDTOList.isEmpty()) {
            Map<String, ContactDTO> contactDTOMap = contactDTOList.stream()
                    .filter(it -> it.getContactType().getDataCode().equals(CONTACT_TYPE_MAIL)
                            || it.getContactType().getDataCode().equals(CONTACT_TYPE_MOBILE))
                    .collect(Collectors.toMap(it -> it.getContactType().getDataCode(), Function.identity()));

            for (Map.Entry<String, ContactDTO> entry : contactDTOMap.entrySet()) {
                UserContactDTO userContactDTO = new UserContactDTO();
                userContactDTO.setId(0);
                userContactDTO.setContact(entry.getValue());
                userContactDTOList.add(userContactDTO);
            }
        }

        return userContactDTOList;
    }

    public List<ContactDTO> fetchContactsFromExcelVO(UserSelfRegistrationExcelVO excelVO) {
        List<ContactDTO> contactDTOList = new ArrayList<>();

        String primaryContactNumber = getStringValueFromFieldValue(excelVO.getPrimaryContactNumber());
        String secondaryContactNumber = getStringValueFromFieldValue(excelVO.getSecondaryContactNumber());
        String whatsappContactNumber = getStringValueFromFieldValue(excelVO.getWhatsappContactNumber());
        String primaryEmailAddress = getStringValueFromFieldValue(excelVO.getPrimaryEmailAddress());
        String secondaryEmailAddress = getStringValueFromFieldValue(excelVO.getSecondaryEmailAddress());

        if (primaryContactNumber != null) {
            applyRegexForTheGivenFieldText(contactNumberPattern, primaryContactNumber, CONTACT_NUMBER);

            String contactCountryCode = getStringValueFromFieldValue(excelVO.getPrimaryContactCountryCode());
            ContactDTO contactDTO = createContactDTO(contactCountryCode, primaryContactNumber, IAMConstant.CONTACT_TYPE_MOBILE);
            contactDTOList.add(contactDTO);
        }

        if (secondaryContactNumber != null) {
            applyRegexForTheGivenFieldText(contactNumberPattern, secondaryContactNumber, CONTACT_NUMBER);

            String contactCountryCode = getStringValueFromFieldValue(excelVO.getSecondaryContactCountryCode());
            ContactDTO contactDTO = createContactDTO(contactCountryCode, secondaryContactNumber, IAMConstant.CONTACT_TYPE_SECONDARY_MOBILE);
            contactDTOList.add(contactDTO);
        }

        if (whatsappContactNumber != null) {
            applyRegexForTheGivenFieldText(contactNumberPattern, whatsappContactNumber, CONTACT_NUMBER);

            String contactCountryCode = getStringValueFromFieldValue(excelVO.getWhatsappContactCountryCode());
            ContactDTO contactDTO = createContactDTO(contactCountryCode, whatsappContactNumber, IAMConstant.CONTACT_TYPE_WHATSAPP_NUMBER);
            contactDTOList.add(contactDTO);
        }

        if (primaryEmailAddress != null) {
            applyRegexForTheGivenFieldText(emailPattern, primaryEmailAddress, EMAIL_ADDRESS);

            ContactDTO contactDTO = createContactDTO(null, primaryEmailAddress, IAMConstant.CONTACT_TYPE_MAIL);
            contactDTOList.add(contactDTO);
        }

        if (secondaryEmailAddress != null) {
            applyRegexForTheGivenFieldText(emailPattern, secondaryEmailAddress, EMAIL_ADDRESS);

            ContactDTO contactDTO = createContactDTO(null, secondaryEmailAddress, IAMConstant.CONTACT_TYPE_SECONDARY_MAIL);
            contactDTOList.add(contactDTO);
        }

        return contactDTOList;
    }

    public static void applyRegexForTheGivenFieldText(Pattern pattern, String fieldValue, String fieldKey) {
        if (fieldValue != null) {
            Matcher matcher = pattern.matcher(fieldValue);
            if (!matcher.matches()) {
                throw new InvalidInputException(String.format("%s - %s is not of required pattern. ", fieldKey,fieldValue));
            }
        }
    }

    public String getStringValueFromFieldValue(String fieldValue) {
        if (StringUtils.isBlank(fieldValue) || fieldValue.equals("null")) {
            fieldValue = null;
        } else {
            fieldValue = StringUtils.trim(fieldValue);
        }
        return fieldValue;
    }

    public String makeFirstAlphabetOfEachWordsCapital(String input) {
        String result = null;
        if (input != null) {
            StringBuilder manipulatedInput = new StringBuilder();
            String[] wordsArray = input.split("\\s+");
            for (String word : wordsArray) {
                if (!word.isEmpty()) {
                    manipulatedInput.append(Character.toUpperCase(word.charAt(0)));
                    if (word.length() > 1) {
                        manipulatedInput.append(word.substring(1).toLowerCase());
                    }
                    manipulatedInput.append(" ");
                }
            }
            result = manipulatedInput.toString().trim();
        }
        return result;
    }
}