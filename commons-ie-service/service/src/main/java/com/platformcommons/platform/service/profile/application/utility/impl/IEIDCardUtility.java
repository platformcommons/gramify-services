package com.platformcommons.platform.service.profile.application.utility.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.profile.domain.Address;
import com.platformcommons.platform.service.profile.domain.Contact;
import com.platformcommons.platform.service.profile.domain.PersonProfile;
import com.platformcommons.platform.service.worknode.dto.WorknodeAddressDTO;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class IEIDCardUtility {

    public static final String PROFILE_PIC = "profilePic";
    public static final String ID_NUMBER = "idNumber";
    public static final String NAME = "farmerName";
    public static final String FARMER_SHORT_ADDRESS = "farmerShortAddress";
    public static final String FARMER_FULL_ADDRESS = "farmerFullAddress";
    public static final String MOBILE_NUMBER = "mobileNo";;
    public static final String GENDER = "gender";
    public static final String QR_CODE = "qrCode";
    public static final String OFFICE_ADDRESS = "officeAddress";
    public static final String DEFAULT_PROFILE_PIC = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqafzhnwwYzuOTjTlaYMeQ7hxQLy_Wq8dnQg&s";
    public static final String BANNER_IMAGE = "bannerimage";


    public String getIeName(PersonProfile personProfile){
        if(personProfile != null){
            return personProfile.getFirstName() != null ? personProfile.getFirstName() : "";
        }else{
            throw new InvalidInputException("Person name not found");
        }
    }

    public String getGender(PersonProfile personProfile) {
        if(personProfile != null){
            String gender = personProfile.getGender() != null ?
                    personProfile.getGender().replaceFirst("GENDER", "").replaceAll("[._]", "")
                            .toString().toLowerCase() : "";
            return gender != null ? gender.substring(0, 1).toUpperCase() + gender.substring(1) : "";
        }else{
            throw new InvalidInputException("Gender not found");
        }
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString().trim();
    }


    public String getFarmerShortAddress(Set<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            return "";
        }
        Address address = addresses.stream()
                .filter(it -> "ADDRESS_TYPE.PERMANENT".equals(it.getAddressType()))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("Address not found"));

        StringBuilder shortAddress = new StringBuilder();
        if (address.getVillage() != null && !address.getVillage().isEmpty()) {
            shortAddress.append(address.getVillage().replaceFirst("REGION_VILLAGE", "").replaceAll("[._]", " ")).append(", ");
        }
        if (address.getDistrict() != null && !address.getDistrict().isEmpty()) {
            shortAddress.append(address.getDistrict().replaceFirst("REGION_DISTRICT", "").replaceAll("[._]", " ")).append(", ");
        }
        if (address.getState() != null && !address.getState().isEmpty()) {
            shortAddress.append(address.getState().replaceFirst("REGION_STATE", "").replaceAll("[._]", " ")).append(" ");
        }
        return capitalizeFirstLetter(shortAddress.toString().trim());
    }




//    public String getHomeAddress(Set<Address> addresses) {
//        if(addresses != null && !addresses.isEmpty()){
//            Address address = addresses.stream().filter(it -> it.getAddressType().equals("ADDRESS_TYPE.HOME")).findFirst()
//                    .orElseThrow(() -> new InvalidInputException("Address not found"));
//            return address != null ? address.getTown() != null ? address.getTown().replaceFirst("REGION_", "").replaceAll("[._]", " ") : "" +
//                    " " + address.getPinCode() != null ? address.getPinCode() : "" + " " +
//                    address.getState() != null ? address.getState().replaceFirst("REGION_", "").replaceAll("[._]", " ") : "" : "";
//        }else{
//            return "";
//        }
//    }

    public String getFarmerFullAddress(Set<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            return "";
        }
        Address address = addresses.stream()
                .filter(it -> "ADDRESS_TYPE.PERMANENT".equals(it.getAddressType()))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("Address not found"));

        StringBuilder fullAddress = new StringBuilder();
        if (address.getVillage() != null && !address.getVillage().isEmpty()) {
            fullAddress.append(address.getVillage().replaceFirst("REGION_VILLAGE", "").replaceAll("[._]", " ")).append(", ");
        }
        if (address.getPanchayat() != null && !address.getPanchayat().isEmpty()) {
            fullAddress.append(address.getPanchayat().replaceFirst("REGION_PANCHAYAT", "").replaceAll("[._]", " ")).append(", ");
        }
        if (address.getDistrict() != null && !address.getDistrict().isEmpty()) {
            fullAddress.append(address.getDistrict().replaceFirst("REGION_DISTRICT", "").replaceAll("[._]", " ")).append(", ");
        }
        if (address.getState() != null && !address.getState().isEmpty()) {
            fullAddress.append(address.getState().replaceFirst("REGION_STATE", "").replaceAll("[._]", " ")).append(",  ");
        }
        if (address.getPinCode() != null && !address.getPinCode().isEmpty()) {
            fullAddress.append(address.getPinCode()).append(" ");
        }

        return capitalizeFirstLetter(fullAddress.toString().trim());
    }



    public String getOfficeAddress(Set<WorknodeAddressDTO> worknodeAddressDTOS) {
        if (worknodeAddressDTOS == null || worknodeAddressDTOS.isEmpty()) {
            return "";
        }
        WorknodeAddressDTO officeAddressDTO = worknodeAddressDTOS.stream()
                .filter(it -> "ADDRESS_TYPE.PERMANENT".equals(it.getAddressType()))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("Office Address not found"));

        StringBuilder address = new StringBuilder();
        if (officeAddressDTO.getVillage() != null && !officeAddressDTO.getVillage().isEmpty()) {
            address.append(officeAddressDTO.getVillage().replaceFirst("REGION_VILLAGE", "").replaceAll("[._]", " ")).append(", ");
        }
        if (officeAddressDTO.getCity() != null && !officeAddressDTO.getCity().isEmpty()) {
            address.append(officeAddressDTO.getCity().replaceFirst("REGION_CITY", "").replaceAll("[._]", " ")).append(", ");
        }
        if (officeAddressDTO.getDistrict() != null && !officeAddressDTO.getDistrict().isEmpty()) {
            address.append(officeAddressDTO.getDistrict().replaceFirst("REGION_DISTRICT", "").replaceAll("[._]", " ")).append(", ");
        }
        if (officeAddressDTO.getState() != null && !officeAddressDTO.getState().isEmpty()) {
            address.append(officeAddressDTO.getState().replaceFirst("REGION_STATE", "").replaceAll("[._]", " ")).append(", ");
        }
        if (officeAddressDTO.getPincode() != null) {
            address.append(officeAddressDTO.getPincode()).append(" ");
        }

        return capitalizeFirstLetter(address.toString().trim());
    }



    public String getMobileNumber(Set<Contact> contacts) {
        if(contacts != null && !contacts.isEmpty()){
            Contact contact = contacts.stream().filter(it -> it.getContactType().equals("CONTACT_TYPE.MOBILE")).findFirst()
                    .orElseThrow(() -> new InvalidInputException("Mobile Number not found"));
            return contact != null ? contact.getContactValue() != null ? contact.getContactValue() : "" : "";
        }else{
            return "";
        }
    }

}
