package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import lombok.*;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TenantFinanceDetailDTO extends BaseTransactionalDTO {

    private Long id;

    private String accountHolderName;

    private String accountNumber;

    private String ifscCode;

    private String bankName;

    private Boolean isVerified;

    private String bankAccountType;

    @Valid
    private List<AttachmentDTO> attachments;


    @Builder
    public TenantFinanceDetailDTO (String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive,String inactiveReason,
                                Long id, String accountHolderName, String accountNumber, String ifscCode,
                                String bankName, Boolean isVerified, String bankAccountType, List<AttachmentDTO> attachments) {
        super(uuid, appCreatedAt, appLastModifiedAt, isActive, inactiveReason);
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
        this.isVerified = isVerified;
        this.bankAccountType = bankAccountType;
        this.attachments = attachments;
    }
}
