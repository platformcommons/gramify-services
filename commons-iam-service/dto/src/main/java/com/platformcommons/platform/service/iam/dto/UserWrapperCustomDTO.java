package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindtree.bridge.platform.dto.UserWrapperDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWrapperCustomDTO {

    @NotNull(message = "userWrapper must not be null")
    private UserWrapperDTO userWrapper;

    private Long cohortId;

    private Long orgVerticalId;

    private Date cohortStartDate;

    private Date cohortEndDate;

}
