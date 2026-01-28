package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyCodesRequestDTO {

    @NotNull(message = "companyCodes must not be null")
    private Set<String> companyCodes;

    private String sortBy;

    private String direction;
}
