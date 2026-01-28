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
public class TFIResponseDTO {

    private String ReturnStatus;

    private String ReturnCode;

    private String ErrorMessage;

    private String ErrorCode;

    @Builder
    public TFIResponseDTO(String ReturnStatus, String ReturnCode, String ErrorMessage, String ErrorCode) {
        this.ReturnStatus = ReturnStatus;
        this.ReturnCode = ReturnCode;
        this.ErrorMessage = ErrorMessage;
        this.ErrorCode = ErrorCode;
    }
}
