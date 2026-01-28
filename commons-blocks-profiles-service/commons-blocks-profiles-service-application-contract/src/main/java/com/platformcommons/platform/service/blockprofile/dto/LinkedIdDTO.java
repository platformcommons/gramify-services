package com.platformcommons.platform.service.blockprofile.dto;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LinkedIdDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long linkedId;

    private String linkedEntityType;


    @Builder
    public LinkedIdDTO(String uuid, Long linkedId, String linkedEntityType) {
        super(uuid);
        this.linkedId = linkedId;
        this.linkedEntityType = linkedEntityType;
    }
}
