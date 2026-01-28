package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefDataCodeAndLabelDTO {

    private String refDataLabel;

    private String refDataCode;

    @Builder
    public RefDataCodeAndLabelDTO(String refDataLabel, String refDataCode) {
        this.refDataLabel = refDataLabel;
        this.refDataCode = refDataCode;
    }
}
