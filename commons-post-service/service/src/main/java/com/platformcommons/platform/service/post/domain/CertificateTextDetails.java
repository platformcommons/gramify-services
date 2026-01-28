package com.platformcommons.platform.service.post.domain;


import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "certificate_text_details")
@Where(clause = "is_active = 1")
public class CertificateTextDetails extends BaseTransactionalEntity implements DomainEntity<CertificateTextDetails> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_certificate_template_id",referencedColumnName = "id")
    private TenantCertificateTemplate tenantCertificateTemplate;

    @Column(name = "text_code",nullable = false)
    private String textCode;

    @Column(name = "font_size")
    private Integer fontSize;

    @Column(name = "font_style")
    private Integer fontStyle;

    @Column(name = "font_name")
    private String fontName;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "point_x")
    private Integer pointX;

    @Column(name = "point_y")
    private Integer pointY;

    @Column(name = "justification")
    private Integer justification;

    @Builder
    public CertificateTextDetails(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                                  Long id, TenantCertificateTemplate tenantCertificateTemplate, String textCode, Integer fontSize,
                                  Integer fontStyle, String fontName, String colorCode, Integer pointX, Integer pointY, Integer justification) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.tenantCertificateTemplate = tenantCertificateTemplate;
        this.textCode = textCode;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.fontName = fontName;
        this.colorCode = colorCode;
        this.pointX = pointX;
        this.pointY = pointY;
        this.justification = justification;
    }

    public void init(TenantCertificateTemplate tenantCertificateTemplate) {
        this.id = 0L;
        this.tenantCertificateTemplate = tenantCertificateTemplate;
    }

    public void patchUpdate(CertificateTextDetails toBeUpdated) {
        if(toBeUpdated.getTextCode() != null) {
            this.textCode = toBeUpdated.getTextCode();
        }
        if(toBeUpdated.getFontSize() != null) {
            this.fontSize = toBeUpdated.getFontSize();
        }
        if(toBeUpdated.getFontStyle() != null) {
            this.fontStyle = toBeUpdated.getFontStyle();
        }
        if(toBeUpdated.getFontName() != null) {
            this.fontName = toBeUpdated.getFontName();
        }
        if(toBeUpdated.getColorCode() != null) {
            this.colorCode = toBeUpdated.getColorCode();
        }
        if(toBeUpdated.getPointX() != null) {
            this.pointX = toBeUpdated.getPointX();
        }
        if(toBeUpdated.getPointY() != null) {
            this.pointY = toBeUpdated.getPointY();
        }
        if(toBeUpdated.getJustification() != null) {
            this.justification = toBeUpdated.getJustification();
        }
    }
}
