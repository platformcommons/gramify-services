package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.Person;

import org.springframework.data.domain.Page;
import com.platformcommons.platform.service.profile.domain.Address;
import com.platformcommons.platform.service.profile.domain.Contact;
import com.platformcommons.platform.service.profile.domain.PersonBankDetail;
import com.platformcommons.platform.service.profile.domain.PersonEducation;
import com.platformcommons.platform.service.profile.domain.PersonFamily;
import com.platformcommons.platform.service.profile.domain.PersonIdentifier;
import com.platformcommons.platform.service.profile.domain.PersonInsurance;
import com.platformcommons.platform.service.profile.domain.PersonInterest;
import com.platformcommons.platform.service.profile.domain.PersonLanguage;
import com.platformcommons.platform.service.profile.domain.PersonOrganization;
import com.platformcommons.platform.service.profile.domain.PersonPoc;
import com.platformcommons.platform.service.profile.domain.PersonProfession;
import com.platformcommons.platform.service.profile.domain.PersonSkill;

import java.util.*;

public interface PersonService {

    Long save(Person person );

    Person update(Person person );

    Page<Person> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Person getById(Long id);


    void addAddressToPerson(Long id, List<Address> addressList);


    void addContactToPerson(Long id, List<Contact> contactList);


    void addPersonBankDetailToPerson(Long id, List<PersonBankDetail> personBankDetailList);


    void addPersonEducationToPerson(Long id, List<PersonEducation> personEducationList);


    void addPersonFamilyToPerson(Long id, List<PersonFamily> personFamilyList);


    void addPersonIdentifierToPerson(Long id, List<PersonIdentifier> personIdentifierList);


    void addPersonInsuranceToPerson(Long id, List<PersonInsurance> personInsuranceList);


    void addPersonInterestToPerson(Long id, List<PersonInterest> personInterestList);


    void addPersonLanguageToPerson(Long id, List<PersonLanguage> personLanguageList);


    void addPersonOrganizationToPerson(Long id, List<PersonOrganization> personOrganizationList);


    void addPersonPocToPerson(Long id, List<PersonPoc> personPocList);


    void addPersonProfessionToPerson(Long id, List<PersonProfession> personProfessionList);


    void addPersonSkillToPerson(Long id, List<PersonSkill> personSkillList);


}
