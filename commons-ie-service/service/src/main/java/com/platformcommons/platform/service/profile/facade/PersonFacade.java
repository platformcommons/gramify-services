package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.Person;
import com.platformcommons.platform.service.profile.dto.PersonDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.dto.AddressDTO;
import com.platformcommons.platform.service.profile.dto.ContactDTO;
import com.platformcommons.platform.service.profile.dto.PersonBankDetailDTO;
import com.platformcommons.platform.service.profile.dto.PersonEducationDTO;
import com.platformcommons.platform.service.profile.dto.PersonFamilyDTO;
import com.platformcommons.platform.service.profile.dto.PersonIdentifierDTO;
import com.platformcommons.platform.service.profile.dto.PersonInsuranceDTO;
import com.platformcommons.platform.service.profile.dto.PersonInterestDTO;
import com.platformcommons.platform.service.profile.dto.PersonLanguageDTO;
import com.platformcommons.platform.service.profile.dto.PersonOrganizationDTO;
import com.platformcommons.platform.service.profile.dto.PersonPocDTO;
import com.platformcommons.platform.service.profile.dto.PersonProfessionDTO;
import com.platformcommons.platform.service.profile.dto.PersonSkillDTO;

import java.util.*;

public interface PersonFacade {

    Long save(PersonDTO personDTO );

    PersonDTO update(PersonDTO personDTO );

    PageDTO<PersonDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonDTO getById(Long id);


    void addAddressToPerson(Long id, Set<AddressDTO> addressList);


    void addContactToPerson(Long id, Set<ContactDTO> contactList);


    void addPersonBankDetailToPerson(Long id, Set<PersonBankDetailDTO> personBankDetailList);


    void addPersonEducationToPerson(Long id, Set<PersonEducationDTO> personEducationList);


    void addPersonFamilyToPerson(Long id, Set<PersonFamilyDTO> personFamilyList);


    void addPersonIdentifierToPerson(Long id, Set<PersonIdentifierDTO> personIdentifierList);


    void addPersonInsuranceToPerson(Long id, Set<PersonInsuranceDTO> personInsuranceList);


    void addPersonInterestToPerson(Long id, Set<PersonInterestDTO> personInterestList);


    void addPersonLanguageToPerson(Long id, Set<PersonLanguageDTO> personLanguageList);


    void addPersonOrganizationToPerson(Long id, Set<PersonOrganizationDTO> personOrganizationList);


    void addPersonPocToPerson(Long id, Set<PersonPocDTO> personPocList);


    void addPersonProfessionToPerson(Long id, Set<PersonProfessionDTO> personProfessionList);


    void addPersonSkillToPerson(Long id, Set<PersonSkillDTO> personSkillList);


}
