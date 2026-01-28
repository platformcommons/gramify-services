package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMetaDataDTO {

    private Long id;

    private Long userId;

    @NotNull(message = "metaKey must not be null")
    private String metaKey;

    @NotNull(message = "metaValue must not be null")
    private String metaValue;

    @Builder
    public UserMetaDataDTO( Long id, Long userId, String metaKey, String metaValue) {
        this.id = id;
        this.userId = userId;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
    }
}
