package com.platformcommons.platform.service.person.dto.ptld;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ContactDTO extends BaseDTO implements Serializable {
    private Integer id;
    private GlobalRefDataDTO contactType;
    private String contactValue;
    private Boolean verified;

    @Builder
    public ContactDTO(String uuid, Integer id, GlobalRefDataDTO contactType, String contactValue, Boolean verified) {
        super(uuid);
        this.id = id;
        this.contactType = contactType;
        this.contactValue = contactValue;
        this.verified = verified;
    }
}