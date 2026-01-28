
package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserExistResponse {
    private Boolean isUserPresent;
    private String userStatus;
    private Boolean isMobileUsed;
    private Boolean isEmailUsed;

    @Builder
    public UserExistResponse(Boolean isUserPresent, String userStatus, Boolean isMobileUsed, Boolean isEmailUsed) {
        this.isUserPresent = isUserPresent;
        this.userStatus = userStatus;
        this.isMobileUsed = isMobileUsed;
        this.isEmailUsed = isEmailUsed;
    }
}
