package com.platformcommons.platform.service.person.dto.ptld;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class UserDTO extends BaseDTO implements Serializable {

    private String firstName;
    private Long id;
    private String lastName;
    private String login;
    private PersonDTO person;

    @Builder
    public UserDTO(String uuid, String firstName, Long id, String lastName, String login, PersonDTO person) {
        super(uuid);
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.login = login;
        this.person = person;
    }
}