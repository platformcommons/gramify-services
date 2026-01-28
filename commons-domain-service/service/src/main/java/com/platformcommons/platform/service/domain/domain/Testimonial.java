package com.platformcommons.platform.service.domain.domain;

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
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "testimonial")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Testimonial extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<Testimonial> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "appId must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", nullable = false, updatable = false)
    private App appId;

    @Column(name = "text",columnDefinition = "TEXT")
    private String text;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "by_user")
    private Long byUser;

    @Column(name = "by_user_name")
    private String byUserName;

    @Transient
    private boolean isNew;

    @Builder
    public Testimonial(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, App appId, String text, Double rating, Long byUser, String byUserName) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.appId = appId;
        this.text = text;
        this.rating = rating;
        this.byUser = byUser;
        this.byUserName = byUserName;
    }

    public void init() {
    }

    public void update(Testimonial toBeUpdated) {
    }
}