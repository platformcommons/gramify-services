package com.platformcommons.platform.service.search.facade.assembler.impl;

import com.platformcommons.platform.service.search.domain.User;
import com.platformcommons.platform.service.search.dto.UserDTO;
import com.platformcommons.platform.service.search.facade.assembler.UserDTOAssembler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDTOAssemblerImpl implements UserDTOAssembler {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.userId( user.getUserId() );
        userDTO.isActive(user.getIsActive());
        userDTO.tenantId( user.getTenantId() );
        userDTO.displayName( user.getDisplayName() );
        userDTO.login( user.getLogin() );
        userDTO.headLine( user.getHeadLine() );
        userDTO.genderCode( user.getGenderCode() );
        userDTO.profilePic( user.getProfilePic() );
        userDTO.primaryEmail( user.getPrimaryEmail() );
        userDTO.primaryContactNumber( user.getPrimaryContactNumber() );
        userDTO.primaryMobileCountryCode( user.getPrimaryMobileCountryCode() );
        userDTO.personId( user.getPersonId() );
        userDTO.memberSince( user.getMemberSince() );
        Set<String> set = user.getEmploymentDesignations();
        if ( set != null ) {
            userDTO.employmentDesignations( new HashSet<String>( set ) );
        }
        Set<String> set1 = user.getEmploymentOrganisationNames();
        if ( set1 != null ) {
            userDTO.employmentOrganisationNames( new HashSet<String>( set1 ) );
        }
        Set<String> set2 = user.getEducationInstituteNames();
        if ( set2 != null ) {
            userDTO.educationInstituteNames( new HashSet<String>( set2 ) );
        }
        userDTO.currentStateCode( user.getCurrentStateCode() );
        userDTO.currentCityCode( user.getCurrentCityCode() );
        userDTO.currentCountryCode( user.getCurrentCountryCode() );
        userDTO.currentAddressLabel( user.getCurrentAddressLabel() );
        userDTO.permanentStateCode( user.getPermanentStateCode() );
        userDTO.permanentCityCode( user.getPermanentCityCode() );
        userDTO.permanentCountryCode( user.getPermanentCountryCode() );
        userDTO.permanentAddressLabel( user.getPermanentAddressLabel() );
        userDTO.cohort( user.getCohort() );
        userDTO.placementCity( user.getPlacementCity() );
        userDTO.userAliasId( user.getUserAliasId() );
        userDTO.currentProfessionalStatus(user.getCurrentProfessionalStatus());
        userDTO.dateOfBirth(user.getDateOfBirth());
        userDTO.roleCodes(user.getRoleCodes());
        userDTO.pathwayCodes(user.getPathwayCodes());
        userDTO.totalYearsOfExperience(user.getTotalYearsOfExperience());
        userDTO.educationQualificationCodes(user.getEducationQualificationCodes());
        userDTO.educationDegreeCodes(user.getEducationDegreeCodes());
        userDTO.relocationPreferenceSet(user.getRelocationPreferenceSet());
        userDTO.selfDeclarationCodes(user.getSelfDeclarationCodes());
        userDTO.profileVisibilityCodes(user.getProfileVisibilityCodes());

        return userDTO.build();
    }

    public User fromDTO(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setUserId(dto.getUserId());
        user.setIsActive(dto.getIsActive());
        user.setUserAliasId(dto.getUserAliasId());
        user.setTenantId(dto.getTenantId());
        user.setTenantLogin(dto.getTenantLogin());
        user.setDisplayName(dto.getDisplayName());
        user.setLogin(dto.getLogin());
        user.setHeadLine(dto.getHeadLine());
        user.setGenderCode(dto.getGenderCode());
        user.setProfilePic(dto.getProfilePic());
        user.setPrimaryEmail(dto.getPrimaryEmail());
        user.setPrimaryContactNumber(dto.getPrimaryContactNumber());
        user.setPrimaryMobileCountryCode(dto.getPrimaryMobileCountryCode());
        user.setPersonId(dto.getPersonId());
        user.setEmploymentDesignations(dto.getEmploymentDesignations());
        user.setEmploymentOrganisationNames(dto.getEmploymentOrganisationNames());
        user.setEmploymentOrganisationCodes(dto.getEmploymentOrganisationCodes());
        user.setCurrentEmploymentOrganisationCodes(dto.getCurrentEmploymentOrganisationCodes());
        user.setCurrentStateCode(dto.getCurrentStateCode());
        user.setCurrentCityCode(dto.getCurrentCityCode());
        user.setCurrentCityLatLong(dto.getCurrentCityLatLong());
        user.setCurrentCountryCode(dto.getCurrentCountryCode());
        user.setCurrentAddressLabel(dto.getCurrentAddressLabel());
        user.setPermanentStateCode(dto.getPermanentStateCode());
        user.setPermanentCityCode(dto.getPermanentCityCode());
        user.setPermanentCityLatLong(dto.getPermanentCityLatLong());
        user.setPermanentCountryCode(dto.getPermanentCountryCode());
        user.setPermanentAddressLabel(dto.getPermanentAddressLabel());
        user.setRoleCodes(dto.getRoleCodes());
        user.setEducationInstituteCodes(dto.getEducationInstituteCodes());
        user.setEducationInstituteNames(dto.getEducationInstituteNames());
        user.setPathwayLabels(dto.getPathwayLabels());
        user.setPathwayCodes(dto.getPathwayCodes());
        user.setCohort(dto.getCohort());
        user.setPlacementCity(dto.getPlacementCity());
        user.setCurrentProfessionalStatus(dto.getCurrentProfessionalStatus());
        user.setCurrentProfessionalStatusVisibility(dto.getCurrentProfessionalStatusVisibility());
        user.setMemberSince(dto.getMemberSince());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setIsActive(dto.getIsActive());
        user.setWorknodeIds(dto.getWorknodeIds());
        user.setTotalYearsOfExperience(dto.getTotalYearsOfExperience());
        user.setEducationQualificationCodes(dto.getEducationQualificationCodes());
        user.setRelocationPreferenceSet(dto.getRelocationPreferenceSet());
        user.setSelfDeclarationCodes(dto.getSelfDeclarationCodes());
        user.setEducationDegreeCodes(dto.getEducationDegreeCodes());
        user.setProfileVisibilityCodes(dto.getProfileVisibilityCodes());

        return user;
    }



    @Override
    public UserDTO toCustomDTOIgnoringSensitiveInfo(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.userId( user.getUserId() );
        userDTO.isActive(user.getIsActive());
        userDTO.tenantId( user.getTenantId() );
        userDTO.displayName( user.getDisplayName() );
        userDTO.headLine( user.getHeadLine() );
        userDTO.genderCode( user.getGenderCode() );
        userDTO.profilePic( user.getProfilePic() );
        userDTO.personId( user.getPersonId() );
        userDTO.memberSince( user.getMemberSince() );
        Set<String> set = user.getEmploymentDesignations();
        if ( set != null ) {
            userDTO.employmentDesignations( new HashSet<String>( set ) );
        }
        Set<String> set1 = user.getEmploymentOrganisationNames();
        if ( set1 != null ) {
            userDTO.employmentOrganisationNames( new HashSet<String>( set1 ) );
        }
        Set<String> set2 = user.getEducationInstituteNames();
        if ( set2 != null ) {
            userDTO.educationInstituteNames( new HashSet<String>( set2 ) );
        }
        userDTO.currentAddressLabel( user.getCurrentAddressLabel() );
        userDTO.permanentAddressLabel( user.getPermanentAddressLabel() );
        userDTO.cohort( user.getCohort() );
        userDTO.placementCity( user.getPlacementCity() );
        userDTO.userAliasId( user.getUserAliasId() );
        userDTO.currentProfessionalStatus(user.getCurrentProfessionalStatus());
        userDTO.currentProfessionalStatusVisibility(user.getCurrentProfessionalStatusVisibility());
        userDTO.dateOfBirth(user.getDateOfBirth());
        userDTO.pathwayCodes(user.getPathwayCodes());
        userDTO.totalYearsOfExperience(user.getTotalYearsOfExperience());
        userDTO.educationQualificationCodes(user.getEducationQualificationCodes());
        userDTO.educationDegreeCodes(user.getEducationDegreeCodes());
        userDTO.relocationPreferenceSet(user.getRelocationPreferenceSet());
        userDTO.selfDeclarationCodes(user.getSelfDeclarationCodes());

        return userDTO.build();
    }


}