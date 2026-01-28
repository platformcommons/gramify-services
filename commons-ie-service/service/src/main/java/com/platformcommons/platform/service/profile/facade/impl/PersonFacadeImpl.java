package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.Person;
import com.platformcommons.platform.service.profile.dto.PersonDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonDTOAssembler;
import java.util.stream.Collectors;
import com.platformcommons.platform.service.profile.dto.AddressDTO;
import com.platformcommons.platform.service.profile.facade.assembler.AddressDTOAssembler;


import com.platformcommons.platform.service.profile.dto.ContactDTO;
import com.platformcommons.platform.service.profile.facade.assembler.ContactDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonBankDetailDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonBankDetailDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonEducationDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonEducationDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonFamilyDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonFamilyDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonIdentifierDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonIdentifierDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonInsuranceDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonInsuranceDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonInterestDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonInterestDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonLanguageDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonLanguageDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonOrganizationDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonOrganizationDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonPocDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonPocDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonProfessionDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonProfessionDTOAssembler;


import com.platformcommons.platform.service.profile.dto.PersonSkillDTO;
import com.platformcommons.platform.service.profile.facade.assembler.PersonSkillDTOAssembler;




import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonFacadeImpl implements PersonFacade {


    @Autowired
    private PersonService service;

    @Autowired
    private PersonDTOAssembler assembler;

    @Autowired
    private AddressDTOAssembler addressDTOAssembler;
    @Autowired
    private ContactDTOAssembler contactDTOAssembler;
    @Autowired
    private PersonBankDetailDTOAssembler personBankDetailDTOAssembler;
    @Autowired
    private PersonEducationDTOAssembler personEducationDTOAssembler;
    @Autowired
    private PersonFamilyDTOAssembler personFamilyDTOAssembler;
    @Autowired
    private PersonIdentifierDTOAssembler personIdentifierDTOAssembler;
    @Autowired
    private PersonInsuranceDTOAssembler personInsuranceDTOAssembler;
    @Autowired
    private PersonInterestDTOAssembler personInterestDTOAssembler;
    @Autowired
    private PersonLanguageDTOAssembler personLanguageDTOAssembler;
    @Autowired
    private PersonOrganizationDTOAssembler personOrganizationDTOAssembler;
    @Autowired
    private PersonPocDTOAssembler personPocDTOAssembler;
    @Autowired
    private PersonProfessionDTOAssembler personProfessionDTOAssembler;
    @Autowired
    private PersonSkillDTOAssembler personSkillDTOAssembler;

    @Override
    public Long save(PersonDTO personDTO ){
        return service.save(assembler.fromDTO(personDTO));
    }


    @Override
    public PersonDTO update(PersonDTO personDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personDTO)));
    }

    @Override
    public PageDTO<PersonDTO> getAllPage(Integer page, Integer size){
        Page<Person> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
    @Override
    public void addAddressToPerson(Long id, Set<AddressDTO> addressList){
        service.addAddressToPerson(id,addressList.stream().map(addressDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addContactToPerson(Long id, Set<ContactDTO> contactList){
        service.addContactToPerson(id,contactList.stream().map(contactDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonBankDetailToPerson(Long id, Set<PersonBankDetailDTO> personBankDetailList){
        service.addPersonBankDetailToPerson(id,personBankDetailList.stream().map(personBankDetailDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonEducationToPerson(Long id, Set<PersonEducationDTO> personEducationList){
        service.addPersonEducationToPerson(id,personEducationList.stream().map(personEducationDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonFamilyToPerson(Long id, Set<PersonFamilyDTO> personFamilyList){
        service.addPersonFamilyToPerson(id,personFamilyList.stream().map(personFamilyDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonIdentifierToPerson(Long id, Set<PersonIdentifierDTO> personIdentifierList){
        service.addPersonIdentifierToPerson(id,personIdentifierList.stream().map(personIdentifierDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonInsuranceToPerson(Long id, Set<PersonInsuranceDTO> personInsuranceList){
        service.addPersonInsuranceToPerson(id,personInsuranceList.stream().map(personInsuranceDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonInterestToPerson(Long id, Set<PersonInterestDTO> personInterestList){
        service.addPersonInterestToPerson(id,personInterestList.stream().map(personInterestDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonLanguageToPerson(Long id, Set<PersonLanguageDTO> personLanguageList){
        service.addPersonLanguageToPerson(id,personLanguageList.stream().map(personLanguageDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonOrganizationToPerson(Long id, Set<PersonOrganizationDTO> personOrganizationList){
        service.addPersonOrganizationToPerson(id,personOrganizationList.stream().map(personOrganizationDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonPocToPerson(Long id, Set<PersonPocDTO> personPocList){
        service.addPersonPocToPerson(id,personPocList.stream().map(personPocDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonProfessionToPerson(Long id, Set<PersonProfessionDTO> personProfessionList){
        service.addPersonProfessionToPerson(id,personProfessionList.stream().map(personProfessionDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
    @Override
    public void addPersonSkillToPerson(Long id, Set<PersonSkillDTO> personSkillList){
        service.addPersonSkillToPerson(id,personSkillList.stream().map(personSkillDTOAssembler::fromDTO)
          .collect(Collectors.toCollection(LinkedList::new)));

    }

 
}
