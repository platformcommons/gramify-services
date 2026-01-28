package com.platformcommons.platform.service.domain.domain.vo;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DomainVO extends BaseDTO {

    private Long id;

    private String name;

    private String code;

    private String description;

    @Builder
    public DomainVO(Long id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }
}
