package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;

    @Builder
    public UserDTO(Long id, String login, String email, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getUserName() {
        return firstName + " " + (lastName==null?"":" "+lastName);
    }
}