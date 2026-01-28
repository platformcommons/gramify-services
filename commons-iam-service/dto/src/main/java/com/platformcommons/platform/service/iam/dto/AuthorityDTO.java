package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class AuthorityDTO extends AuthBaseDTO {

    @Pattern(regexp = "^[A-Z-.]*$")
    private String code;

    @Pattern(regexp = "^[A-Z-.]*$")
    @Min(3)
    @Max(45)
    private String process;

    private String authorityDescription;

    @Builder
    public AuthorityDTO(String code, String authorityDescription, String process) {
        this.code = code;
        this.authorityDescription = authorityDescription;
        this.process = process;
    }


}
