package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TFIUserVerificationDTO {

    @NotNull(message = "id must not be null")
    private Long id;

    @NotNull(message = "login must not be null")
    private String login;

    private String firstName;

    @JsonProperty("fellowship_id")
    @NotNull(message = "fellowshipId must not be null")
    private String fellowShipId;

    @NotNull(message = "role must not be null")
    private String role;

    public TFIUserVerificationDTO(Long id, String login, String firstName, String fellowShipId, String role) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.fellowShipId = fellowShipId;
        this.role = role;
    }
}
