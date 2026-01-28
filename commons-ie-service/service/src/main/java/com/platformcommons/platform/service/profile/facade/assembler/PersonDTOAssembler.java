package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.Person;
import com.platformcommons.platform.service.profile.dto.PersonDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          AddressDTOAssembler.class,
          ContactDTOAssembler.class,
          PersonBankDetailDTOAssembler.class,
          PersonEducationDTOAssembler.class,
          PersonFamilyDTOAssembler.class,
          PersonIdentifierDTOAssembler.class,
          PersonInsuranceDTOAssembler.class,
          PersonInterestDTOAssembler.class,
          PersonLanguageDTOAssembler.class,
          PersonOrganizationDTOAssembler.class,
          PersonPocDTOAssembler.class,
          PersonProfessionDTOAssembler.class,
          PersonProfileDTOAssembler.class,
          PersonSkillDTOAssembler.class,
          PersonSocialMediaDTOAssembler.class,
    })
public interface PersonDTOAssembler {

    PersonDTO toDTO(Person entity);

    Person fromDTO(PersonDTO dto);
}