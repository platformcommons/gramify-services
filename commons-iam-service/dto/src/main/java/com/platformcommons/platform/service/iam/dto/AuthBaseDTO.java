package com.platformcommons.platform.service.iam.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public  class AuthBaseDTO implements Serializable {

    private Long createdTimestamp;

    private Long lastModifiedTimestamp;

    public AuthBaseDTO(Long createdTimestamp, Long lastModifiedTimestamp) {
        this.createdTimestamp = createdTimestamp;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }
}
