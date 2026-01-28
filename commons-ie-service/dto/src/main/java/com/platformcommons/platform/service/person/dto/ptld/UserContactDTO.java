package com.platformcommons.platform.service.person.dto.ptld;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class UserContactDTO extends BaseDTO implements Serializable {
    private Integer id;
    private Boolean primaryContact;
    private ContactDTO contact;

    @Builder
    public UserContactDTO(String uuid, Integer id, Boolean primaryContact, ContactDTO contact) {
        super(uuid);
        this.id = id;
        this.primaryContact = primaryContact;
        this.contact = contact;
    }
}