package com.platformcommons.platform.service.blockprofile.dto;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LinkedCodeDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String linkedCode;

    private String linkedEntityType;


    @Builder
    public LinkedCodeDTO(String uuid, String linkedCode, String linkedEntityType) {
        super(uuid);
        this.linkedCode = linkedCode;
        this.linkedEntityType = linkedEntityType;
    }
}
