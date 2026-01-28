package com.platformcommons.platform.service.iam.dto.brbase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserContactVO {

    private Integer userId;

    private GlobalRefDataDTO contactType;

    private String oldContactValue;

    private String newContactValue;

    @Builder
    public UserContactVO(Integer userId, GlobalRefDataDTO contactType, String oldContactValue, String newContactValue) {
        this.userId = userId;
        this.contactType = contactType;
        this.oldContactValue = oldContactValue;
        this.newContactValue = newContactValue;
    }
}
