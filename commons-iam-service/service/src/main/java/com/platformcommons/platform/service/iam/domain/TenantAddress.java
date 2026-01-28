package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import com.platformcommons.platform.service.sdk.person.domain.Address;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenant_address")
public class TenantAddress extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantAddress> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "addressType")
    private String addressType;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_profile_id")
    private TenantProfile tenantProfile;

    @Builder
    public TenantAddress(String uuid, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                         Long id, String addressType, Address address, TenantProfile tenantProfile) {
        super(uuid, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.addressType = addressType;
        this.address = address;
        this.tenantProfile = tenantProfile;
    }

    public void init(TenantProfile tenantProfile){
        this.id = 0L;
        if (this.address != null) {
            this.address.init();
        }
        this.tenantProfile = tenantProfile;
    }

    public void patchUpdate(TenantAddress toBeUpdated){
        if (toBeUpdated.getAddressType() != null) {
            this.addressType = toBeUpdated.getAddressType();
        }
        if (toBeUpdated.getAddress() != null) {
            if (this.address != null
                    && this.address.getId().equals(toBeUpdated.getAddress().getId())) {
                this.address.patch(toBeUpdated.getAddress());
            } else if (toBeUpdated.getAddress().getId() == null || toBeUpdated.getAddress().getId().equals(0L)) {
                this.setAddress(toBeUpdated.getAddress());
                this.address.init();
            }
        }
        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());
    }


    public void putUpdate(TenantAddress address) {
        this.addressType = address.getAddressType();
        if (address.getAddress() != null) {
            if (this.address != null  && this.address.getId().equals(address.getAddress().getId())) {
                this.address.update(address.getAddress());
            } else if (address.getAddress().getId() == null || address.getAddress().getId().equals(0L)) {
                this.setAddress(address.getAddress());
                this.address.init();
            }
        }
        PlatformUtil.deactivateAnObject(this,address.getIsActive(),address.getInactiveReason());
    }
}
