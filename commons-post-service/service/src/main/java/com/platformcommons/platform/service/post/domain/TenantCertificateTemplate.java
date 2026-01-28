package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Where(clause = "is_active = 1")
@Table(
        name = "tenant_certificate_template",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="UNIQUE_CODE",
                        columnNames = {"code","is_active"}
                ),
                @UniqueConstraint(
                        name="UNIQUE_CAUSE",
                        columnNames = {"cause","tenant_id","is_active"}
        )})
public class TenantCertificateTemplate extends BaseTransactionalEntity implements DomainEntity<TenantCertificateTemplate> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "certificate_uuid_format")
    private String certificateUUIDFormat;

    @Column(name = "title")
    private String title;

    @Column(name = "template_url")
    private String templateUrl;

    @Column(name = "json_data_for_position",columnDefinition = "LONGTEXT")
    private String jsonDataForPosition;

    @Column(name = "cause")
    private String cause;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "tenantCertificateTemplate")
    private Set<CertificateTextDetails> certificateTextDetailsSet;

    @Builder
    public TenantCertificateTemplate(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                                     Long id, String code, String certificateUUIDFormat, String title, String templateUrl,
                                     String jsonDataForPosition, String cause, Set<CertificateTextDetails> certificateTextDetailsSet) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.code = code;
        this.certificateUUIDFormat = certificateUUIDFormat;
        this.title = title;
        this.templateUrl = templateUrl;
        this.jsonDataForPosition = jsonDataForPosition;
        this.cause = cause;
        this.certificateTextDetailsSet = certificateTextDetailsSet;
        if(certificateTextDetailsSet != null) {
            certificateTextDetailsSet.forEach(it->it.setTenantCertificateTemplate(this));
        }

    }

    public void init() {
        this.id = 0L;
        this.code  = createCode(this.title);
    }

    public void patchUpdate(TenantCertificateTemplate toBeUpdated) {
        if(!StringUtils.isBlank(toBeUpdated.getCode())) {
            this.code = toBeUpdated.getCode();
        }
        if(!StringUtils.isBlank(toBeUpdated.getTitle())) {
            this.title = toBeUpdated.getTitle();
        }
        if(!StringUtils.isBlank(toBeUpdated.getTemplateUrl())) {
            this.templateUrl = toBeUpdated.getTemplateUrl();
        }
        if(!StringUtils.isBlank(toBeUpdated.getJsonDataForPosition())) {
            this.jsonDataForPosition = toBeUpdated.getJsonDataForPosition();
        }
        if(!StringUtils.isBlank(toBeUpdated.getCertificateUUIDFormat())) {
            this.certificateUUIDFormat = toBeUpdated.getCertificateUUIDFormat();
        }
        if(!StringUtils.isBlank(toBeUpdated.getCause())) {
            this.cause = toBeUpdated.getCause();
        }
        if(toBeUpdated.getCertificateTextDetailsSet() != null) {
            this.certificateTextDetailsSet.forEach(certificateTextDetails -> {
                toBeUpdated.getCertificateTextDetailsSet().forEach(toBeUpdatedText->{
                    if(Objects.equals(certificateTextDetails.getId(),toBeUpdatedText.getId())) {
                        certificateTextDetails.patchUpdate(toBeUpdatedText);
                    }
                });
            });
            toBeUpdated.getCertificateTextDetailsSet().forEach(certificateTextDetails -> {
                if(certificateTextDetails.getId() == null || certificateTextDetails.getId() == 0L) {
                    certificateTextDetails.init(this);
                    this.getCertificateTextDetailsSet().add(certificateTextDetails);
                }
            });
        }

    }

    private String createCode(String title) {
        String text = "";
        if(title != null) {
            text = title.replace(" ","_");
            int considerLength = Math.min(text.length(),5);
            text = text.substring(0,considerLength).toUpperCase();
        }
        return text + (new Date().getTime());
    }
}
