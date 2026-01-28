package com.platformcommons.platform.service.person.dto.ptld;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter  @Setter
public class PersonDTO extends BaseDTO implements Serializable {

    private Integer id;

    private PersonProfileDTO personProfile;
    private List<UserContactDTO> personContacts;

    @Builder
    public PersonDTO(String uuid, Integer id, PersonProfileDTO personProfile, List<UserContactDTO> personContacts) {
        super(uuid);
        this.id = id;
        this.personProfile = personProfile;
        this.personContacts = personContacts;
    }
}