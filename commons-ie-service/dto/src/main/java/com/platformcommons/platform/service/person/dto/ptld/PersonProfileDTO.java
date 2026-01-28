package com.platformcommons.platform.service.person.dto.ptld;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class PersonProfileDTO extends BaseDTO implements Serializable {
    private Integer id;
    private String firstName;
    @Builder
    public PersonProfileDTO(String uuid, Integer id, String firstName) {
        super(uuid);
        this.id = id;
        this.firstName = firstName;
    }
}