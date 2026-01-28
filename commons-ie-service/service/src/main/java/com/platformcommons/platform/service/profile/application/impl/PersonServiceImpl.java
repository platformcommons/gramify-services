package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.Person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonService;
import com.platformcommons.platform.service.profile.domain.repo.PersonRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import com.platformcommons.platform.service.profile.domain.Address;
import com.platformcommons.platform.service.profile.domain.repo.AddressRepository;


import com.platformcommons.platform.service.profile.domain.Contact;
import com.platformcommons.platform.service.profile.domain.repo.ContactRepository;


import com.platformcommons.platform.service.profile.domain.PersonBankDetail;
import com.platformcommons.platform.service.profile.domain.repo.PersonBankDetailRepository;


import com.platformcommons.platform.service.profile.domain.PersonEducation;
import com.platformcommons.platform.service.profile.domain.repo.PersonEducationRepository;


import com.platformcommons.platform.service.profile.domain.PersonFamily;
import com.platformcommons.platform.service.profile.domain.repo.PersonFamilyRepository;


import com.platformcommons.platform.service.profile.domain.PersonIdentifier;
import com.platformcommons.platform.service.profile.domain.repo.PersonIdentifierRepository;


import com.platformcommons.platform.service.profile.domain.PersonInsurance;
import com.platformcommons.platform.service.profile.domain.repo.PersonInsuranceRepository;


import com.platformcommons.platform.service.profile.domain.PersonInterest;
import com.platformcommons.platform.service.profile.domain.repo.PersonInterestRepository;


import com.platformcommons.platform.service.profile.domain.PersonLanguage;
import com.platformcommons.platform.service.profile.domain.repo.PersonLanguageRepository;


import com.platformcommons.platform.service.profile.domain.PersonOrganization;
import com.platformcommons.platform.service.profile.domain.repo.PersonOrganizationRepository;


import com.platformcommons.platform.service.profile.domain.PersonPoc;
import com.platformcommons.platform.service.profile.domain.repo.PersonPocRepository;


import com.platformcommons.platform.service.profile.domain.PersonProfession;
import com.platformcommons.platform.service.profile.domain.repo.PersonProfessionRepository;


import com.platformcommons.platform.service.profile.domain.PersonSkill;
import com.platformcommons.platform.service.profile.domain.repo.PersonSkillRepository;




import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonServiceImpl implements PersonService {


    @Autowired
    private PersonRepository repository;



    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PersonBankDetailRepository personBankDetailRepository;
    @Autowired
    private PersonEducationRepository personEducationRepository;
    @Autowired
    private PersonFamilyRepository personFamilyRepository;
    @Autowired
    private PersonIdentifierRepository personIdentifierRepository;
    @Autowired
    private PersonInsuranceRepository personInsuranceRepository;
    @Autowired
    private PersonInterestRepository personInterestRepository;
    @Autowired
    private PersonLanguageRepository personLanguageRepository;
    @Autowired
    private PersonOrganizationRepository personOrganizationRepository;
    @Autowired
    private PersonPocRepository personPocRepository;
    @Autowired
    private PersonProfessionRepository personProfessionRepository;
    @Autowired
    private PersonSkillRepository personSkillRepository;

    @Override
    public Long save(Person person ){
        return repository.save(person).getId();
    }



    @Override
    public Person update(Person person) {
       Person fetchedPerson = repository.findById(person.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Person with  %d  not found",person.getId())));

       fetchedPerson.update(person);
       return repository.save(fetchedPerson);
    }


    @Override
    public Page<Person> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Person fetchedPerson = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Person with  %d  not found",id)));
        fetchedPerson.deactivate(reason);
        repository.save(fetchedPerson);
    }


    @Override
    public Person getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Person with  %d  not found",id)));
    }

 
    @Override
    public void addAddressToPerson(Long id, List<Address> addressList){
        Person fetchedPerson = this.getById(id);
        addressList.forEach(it->it.setPersonId(fetchedPerson));
        addressRepository.saveAll(addressList);

    }

 
    @Override
    public void addContactToPerson(Long id, List<Contact> contactList){
        Person fetchedPerson = this.getById(id);
        contactList.forEach(it->it.setPersonId(fetchedPerson));
        contactRepository.saveAll(contactList);

    }

 
    @Override
    public void addPersonBankDetailToPerson(Long id, List<PersonBankDetail> personBankDetailList){
        Person fetchedPerson = this.getById(id);
        personBankDetailList.forEach(it->it.setPersonId(fetchedPerson));
        personBankDetailRepository.saveAll(personBankDetailList);

    }

 
    @Override
    public void addPersonEducationToPerson(Long id, List<PersonEducation> personEducationList){
        Person fetchedPerson = this.getById(id);
        personEducationList.forEach(it->it.setPersonId(fetchedPerson));
        personEducationRepository.saveAll(personEducationList);

    }

 
    @Override
    public void addPersonFamilyToPerson(Long id, List<PersonFamily> personFamilyList){
        Person fetchedPerson = this.getById(id);
        personFamilyList.forEach(it->it.setPersonId(fetchedPerson));
        personFamilyRepository.saveAll(personFamilyList);

    }

 
    @Override
    public void addPersonIdentifierToPerson(Long id, List<PersonIdentifier> personIdentifierList){
        Person fetchedPerson = this.getById(id);
        personIdentifierList.forEach(it->it.setPersonId(fetchedPerson));
        personIdentifierRepository.saveAll(personIdentifierList);

    }

 
    @Override
    public void addPersonInsuranceToPerson(Long id, List<PersonInsurance> personInsuranceList){
        Person fetchedPerson = this.getById(id);
        personInsuranceList.forEach(it->it.setPersonId(fetchedPerson));
        personInsuranceRepository.saveAll(personInsuranceList);

    }

 
    @Override
    public void addPersonInterestToPerson(Long id, List<PersonInterest> personInterestList){
        Person fetchedPerson = this.getById(id);
        personInterestList.forEach(it->it.setPersonId(fetchedPerson));
        personInterestRepository.saveAll(personInterestList);

    }

 
    @Override
    public void addPersonLanguageToPerson(Long id, List<PersonLanguage> personLanguageList){
        Person fetchedPerson = this.getById(id);
        personLanguageList.forEach(it->it.setPersonId(fetchedPerson));
        personLanguageRepository.saveAll(personLanguageList);

    }

 
    @Override
    public void addPersonOrganizationToPerson(Long id, List<PersonOrganization> personOrganizationList){
        Person fetchedPerson = this.getById(id);
        personOrganizationList.forEach(it->it.setPersonId(fetchedPerson));
        personOrganizationRepository.saveAll(personOrganizationList);

    }

 
    @Override
    public void addPersonPocToPerson(Long id, List<PersonPoc> personPocList){
        Person fetchedPerson = this.getById(id);
        personPocList.forEach(it->it.setPersonId(fetchedPerson));
        personPocRepository.saveAll(personPocList);

    }

 
    @Override
    public void addPersonProfessionToPerson(Long id, List<PersonProfession> personProfessionList){
        Person fetchedPerson = this.getById(id);
        personProfessionList.forEach(it->it.setPersonId(fetchedPerson));
        personProfessionRepository.saveAll(personProfessionList);

    }

 
    @Override
    public void addPersonSkillToPerson(Long id, List<PersonSkill> personSkillList){
        Person fetchedPerson = this.getById(id);
        personSkillList.forEach(it->it.setPersonId(fetchedPerson));
        personSkillRepository.saveAll(personSkillList);

    }

 
}
