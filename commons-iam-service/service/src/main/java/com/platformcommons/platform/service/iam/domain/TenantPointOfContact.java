package com.platformcommons.platform.service.iam.domain;


import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active=1")
@Table(name = "tenant_point_of_contact")
public class TenantPointOfContact extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantPointOfContact> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_profile_id",nullable = false)
    private TenantProfile tenantProfile;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "designation")
    private String designation;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "contact_number_country_code")
    private String contactNumberCountryCode;

    @Builder
    public TenantPointOfContact(String uuid, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                                Long id, String name, String email, String designation, String contactNumber, String contactNumberCountryCode){
        super(uuid, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.contactNumber = contactNumber;
        this.contactNumberCountryCode = contactNumberCountryCode;
    }


    public void init(TenantProfile tenantProfile){
        if (null!=this.id && this.id == 0) {
            this.id = null;
        }
        this.tenantProfile = tenantProfile;
    }

    public void patchUpdate(TenantPointOfContact toBeUpdated){
        if (toBeUpdated.getName() != null){
            this.name = toBeUpdated.getName();
        }
        if (toBeUpdated.getEmail() != null){
            this.email = toBeUpdated.getEmail();
        }
        if (toBeUpdated.getDesignation() != null){
            this.designation = toBeUpdated.getDesignation();
        }
        if (toBeUpdated.getContactNumber() != null){
            this.contactNumber = toBeUpdated.getContactNumber();
        }
        if (toBeUpdated.getContactNumberCountryCode() != null){
            this.contactNumberCountryCode = toBeUpdated.getContactNumberCountryCode();
        }
        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());
    }

    public void putUpdate(TenantPointOfContact toBeUpdated) {
        this.name = toBeUpdated.getName();
        this.email = toBeUpdated.getEmail();
        this.designation = toBeUpdated.getDesignation();
        this.contactNumber = toBeUpdated.getContactNumber();
        this.contactNumberCountryCode = toBeUpdated.getContactNumberCountryCode();
        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());
    }
}
