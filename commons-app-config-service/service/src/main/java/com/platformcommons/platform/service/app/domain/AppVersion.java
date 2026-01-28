package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.entity.base.BaseEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_version")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AppVersion extends BaseEntity implements DomainEntity<AppVersion> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "appId must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "app_id", nullable = false, updatable = false)
    private App appId;

    @Column(name = "version")
    private String version;

    @Column(name = "version_sequence")
    private String versionSequence;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "is_deprecated")
    private Integer isDeprecated;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appVersion")
    @BatchSize(size = 20)
    private Set<AppConfig> appConfigList;

    @Builder
    public AppVersion(
            Long id, App appId, String version, String versionSequence,
            Date releaseDate, Integer isDeprecated, Set<AppConfig> appConfigList) {
        this.id = id;
        this.appId = appId;
        this.version = version;
        this.versionSequence = versionSequence;
        this.releaseDate = releaseDate;
        this.isDeprecated = isDeprecated;
        this.appConfigList = appConfigList;
        if (null != appConfigList) {
            this.appConfigList.forEach(it -> it.setAppVersion(this));
        }
    }

    public Date getRelease_date() {
        return new Date(releaseDate != null ? releaseDate.getTime() : new Date().getTime());
    }

    public void setRelease_date(Date releaseDate) {
        this.releaseDate = new Date(releaseDate != null ? releaseDate.getTime() : new Date().getTime());
    }
}