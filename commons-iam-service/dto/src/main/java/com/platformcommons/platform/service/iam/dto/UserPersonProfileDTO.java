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
public class UserPersonProfileDTO {

    private Long userId;

    private Long personId;

    private Long personProfileId;

    @Builder
    public UserPersonProfileDTO(Long userId, Long personId, Long personProfileId) {
        this.userId = userId;
        this.personId = personId;
        this.personProfileId = personProfileId;
    }
}