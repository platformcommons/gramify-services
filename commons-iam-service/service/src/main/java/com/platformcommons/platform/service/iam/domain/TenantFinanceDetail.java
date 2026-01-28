package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_active = 1")
@Table(name = "tenant_finance_detail")
public class TenantFinanceDetail extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantFinanceDetail> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "bank_account_type")
    private String bankAccountType;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tenant_finance_detail_attachments",
            joinColumns = @JoinColumn(name = "tenant_finance_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<Attachment> attachments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_profile_id",nullable = false)
    private TenantProfile tenantProfile;

    @Builder
    public TenantFinanceDetail (String uuid, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                                Long id, String accountHolderName, String accountNumber, String ifscCode,
                                String bankName, Boolean isVerified, String bankAccountType, List<Attachment> attachments) {
        super(uuid, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
        this.isVerified = isVerified;
        this.bankAccountType = bankAccountType;
        this.attachments = attachments;
    }

    public void init(TenantProfile tenantProfile) {
        if (null!=this.id && this.id == 0) {
            this.id = null;
        }
        this.tenantProfile = tenantProfile;
    }

    public void putUpdate(TenantFinanceDetail tobeUpdated){
        this.accountHolderName = tobeUpdated.getAccountHolderName();
        this.accountNumber = tobeUpdated.getAccountNumber();
        this.ifscCode = tobeUpdated.getIfscCode();
        this.bankName = tobeUpdated.getBankName();
        this.isVerified = tobeUpdated.getIsVerified();
        this.bankAccountType = tobeUpdated.getBankAccountType();
        if (tobeUpdated.getAttachments() != null) {
            for (Attachment incoming : tobeUpdated.getAttachments()) {
                if(incoming.getId() != null) {
                    Attachment existing = this.attachments.stream()
                            .filter(a -> a.getId() != null && a.getId().equals(incoming.getId()))
                            .findFirst()
                            .orElse(null);

                    if (existing != null) {
                        existing.patchUpdate(incoming);
                    } else {
                        if (!Objects.equals(incoming.getIsActive(),Boolean.FALSE)) {
                            this.attachments.add(incoming);
                        }
                    }
                }
            }
        }
    }

    public void patchUpdate(TenantFinanceDetail tobeUpdated){
        if (tobeUpdated.getAccountHolderName() != null)
            this.accountHolderName = tobeUpdated.getAccountHolderName();
        if (tobeUpdated.getAccountNumber() != null)
            this.accountNumber = tobeUpdated.getAccountNumber();
        if (tobeUpdated.getIfscCode() != null)
            this.ifscCode = tobeUpdated.getIfscCode();
        if (tobeUpdated.getBankName() != null)
            this.bankName = tobeUpdated.getBankName();
        if (tobeUpdated.getIsVerified() != null)
            this.isVerified = tobeUpdated.getIsVerified();
        if (tobeUpdated.getBankAccountType() != null)
            this.bankAccountType = tobeUpdated.getBankAccountType();
        if (tobeUpdated.getAttachments() != null) {
            for (Attachment incoming : tobeUpdated.getAttachments()) {
                if(incoming.getId() != null) {
                    Attachment existing = this.attachments.stream()
                            .filter(a -> a.getId() != null && a.getId().equals(incoming.getId()))
                            .findFirst()
                            .orElse(null);

                    if (existing != null) {
                        existing.patchUpdate(incoming);
                    } else {
                        if (!Objects.equals(incoming.getIsActive(),Boolean.FALSE)) {
                            this.attachments.add(incoming);
                        }
                    }
                }
            }
        }
    }

}
