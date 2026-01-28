package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.domain.Enum.Priority;
import com.platformcommons.platform.service.entity.base.BaseEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "platform_app_version" ,
        uniqueConstraints = @UniqueConstraint(columnNames = {"platform", "version_code", "identifier"}))
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PlatformAppVersion extends BaseEntity implements DomainEntity<PlatformAppVersion> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "app_code")
    private String appCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private Platform platform;

    @Column(name = "version_name")
    private String versionName;

    @Column(name = "version_code")
    private String versionCode;

    @Column(name = "version_sequence")
    private String versionSequence;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "deprecated_date")
    private Date deprecatedDate;

    @Column(name = "is_deprecated")
    private Boolean isDeprecated;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Column(name = "release_note",columnDefinition = "MEDIUMTEXT")
    private String releaseNote;

    @Builder
    public PlatformAppVersion(Long id, String appCode, Platform platform, String versionName, String versionCode,
                              String versionSequence, String identifier, Date releaseDate, Date deprecatedDate,
                              Boolean isDeprecated, Priority priority, String releaseNote) {
        this.id = id;
        this.appCode = appCode;
        this.platform = platform;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.versionSequence = versionSequence;
        this.identifier = identifier;
        this.releaseDate = releaseDate;
        this.deprecatedDate = deprecatedDate;
        this.isDeprecated = isDeprecated;
        this.priority = priority;
        this.releaseNote = releaseNote;
    }

    public Date getRelease_date() {
        return new Date(releaseDate != null ? releaseDate.getTime() : new Date().getTime());
    }

    public void setRelease_date(Date releaseDate) {
        this.releaseDate = new Date(releaseDate != null ? releaseDate.getTime() : new Date().getTime());
    }

    public Date getDeprecated_date() {
        return new Date(deprecatedDate != null ? deprecatedDate.getTime() : new Date().getTime());
    }

    public void setDeprecated_date(Date deprecatedDate) {
        this.deprecatedDate = new Date(deprecatedDate != null ? deprecatedDate.getTime() : new Date().getTime());
    }
}