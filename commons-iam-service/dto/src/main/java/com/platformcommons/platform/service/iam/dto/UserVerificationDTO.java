package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVerificationDTO extends BaseTransactionalDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    private String verificationStatus;

    private String verificationRequestMessage;

    private Long reviewedByUser;

    private String remarks;

    private Date reviewedOnDate;

    private String appContext;

    private String marketContext;

    private Set<String> assignedRoles;

}
