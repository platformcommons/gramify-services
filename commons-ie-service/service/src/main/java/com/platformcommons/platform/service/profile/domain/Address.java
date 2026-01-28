package com.platformcommons.platform.service.profile.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "address")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address extends BaseTransactionalEntity implements DomainEntity<Address> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "village")
    private String village;

    @Column(name = "taluk")
    private String taluk;

    @Column(name = "panchayat")
    private String panchayat;

    @Column(name = "mandal")
    private String mandal;

    @Column(name = "block")
    private String block;

    @Column(name = "town")
    private String town;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "verified_by")
    private Long verifiedBy;

    @Column(name = "verified_date_time")
    private Date verifiedDateTime;

    @Column(name = "address_validity")
    private Boolean addressValidity;

    @Column(name = "geo_url")
    private String geoUrl;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Transient
    private boolean isNew;

    @Builder
    public Address(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                   Long id, Person personId, String addressType, String addressLine1, String addressLine2, String landmark,
                   String village, String taluk, String panchayat,String block, String town, String district, String state, String pinCode,
                   BigDecimal latitude, BigDecimal longitude, Boolean verified, Long verifiedBy, Date verifiedDateTime,
                   Boolean addressValidity, String geoUrl, Boolean isPrimary, String mandal) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.addressType = addressType;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.landmark = landmark;
        this.village = village;
        this.taluk = taluk;
        this.panchayat = panchayat;
        this.mandal = mandal;
        this.block=block;
        this.town = town;
        this.district = district;
        this.state = state;
        this.pinCode = pinCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.verified = verified;
        this.verifiedBy = verifiedBy;
        this.verifiedDateTime = verifiedDateTime;
        this.addressValidity = addressValidity;
        this.geoUrl = geoUrl;
        this.isPrimary = isPrimary;
    }

    public Date getVerified_date_time() {
        return new Date(verifiedDateTime != null ? verifiedDateTime.getTime() : new Date().getTime());
    }

    public void setVerified_date_time(Date verifiedDateTime) {
        this.verifiedDateTime = new Date(verifiedDateTime != null ? verifiedDateTime.getTime() : new Date().getTime());
    }

    public void init() {
    }

    public void update(Address toBeUpdated) {
    }

    public void patch(Address toBeUpdated) {
        if (toBeUpdated.getAddressType() != null) {
            this.setAddressType(toBeUpdated.getAddressType());
        }
        if (toBeUpdated.getAddressLine1() != null) {
            this.setAddressLine1(toBeUpdated.getAddressLine1());
        }
        if (toBeUpdated.getAddressLine2() != null) {
            this.setAddressLine2(toBeUpdated.getAddressLine2());
        }
        if (toBeUpdated.getLandmark() != null) {
            this.setLandmark(toBeUpdated.getLandmark());
        }
        if (toBeUpdated.getVillage() != null) {
            this.setVillage(toBeUpdated.getVillage());
        }
        if (toBeUpdated.getTaluk() != null) {
            this.setTaluk(toBeUpdated.getTaluk());
        }
        if (toBeUpdated.getPanchayat() != null) {
            this.setPanchayat(toBeUpdated.getPanchayat());
        }
        if (toBeUpdated.getBlock() != null) {
            this.setBlock(toBeUpdated.getBlock());
        }
        if (toBeUpdated.getTown() != null) {
            this.setTown(toBeUpdated.getTown());
        }
        if (toBeUpdated.getDistrict() != null) {
            this.setDistrict(toBeUpdated.getDistrict());
        }
        if (toBeUpdated.getState() != null) {
            this.setState(toBeUpdated.getState());
        }
        if (toBeUpdated.getPinCode() != null) {
            this.setPinCode(toBeUpdated.getPinCode());
        }
        if (toBeUpdated.getLatitude() != null) {
            this.setLatitude(toBeUpdated.getLatitude());
        }
        if (toBeUpdated.getLongitude() != null) {
            this.setLongitude(toBeUpdated.getLongitude());
        }
        if (toBeUpdated.getVerified() != null) {
            this.setVerified(toBeUpdated.getVerified());
        }
        if (toBeUpdated.getVerifiedBy() != null) {
            this.setVerifiedBy(toBeUpdated.getVerifiedBy());
        }
        if (toBeUpdated.getVerifiedDateTime() != null) {
            this.setVerifiedDateTime(toBeUpdated.getVerifiedDateTime());
        }
        if (toBeUpdated.getAddressValidity() != null) {
            this.setAddressValidity(toBeUpdated.getAddressValidity());
        }
        if (toBeUpdated.getGeoUrl() != null) {
            this.setGeoUrl(toBeUpdated.getGeoUrl());
        }
        if (toBeUpdated.getIsPrimary() != null) {
            this.setIsPrimary(toBeUpdated.getIsPrimary());
        }
    }
}