package com.platformcommons.platform.service.person.dto.ptld;


import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter  @Setter
public class GlobalRefDataDTO extends BaseDTO implements Serializable {
    private String dataCode;

    @Builder
    public GlobalRefDataDTO(String uuid, String dataCode) {
        super(uuid);
        this.dataCode = dataCode;
    }
}