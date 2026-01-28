package com.platformcommons.platform.service.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDTO {

    private Long userId;

    private String fullName;

    private String emailAddress;

    private String iconPic;

    private String contactNumber;

    private String username;

    @Builder
    public UserDetailsDTO(Long userId, String emailAddress, String iconPic,String fullName,String contactNumber,String username) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.iconPic = iconPic;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.username = username;
    }
}
