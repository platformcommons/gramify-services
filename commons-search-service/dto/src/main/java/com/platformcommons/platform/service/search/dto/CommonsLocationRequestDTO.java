package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class CommonsLocationRequestDTO {

    @NotNull(message = "datacodes must not be null")
    private Set<String> dataCodes;

    @NotNull(message = "dataCodesField must not be null")
    private String dataCodesField;

    private String sortBy;

    private String direction;
}
