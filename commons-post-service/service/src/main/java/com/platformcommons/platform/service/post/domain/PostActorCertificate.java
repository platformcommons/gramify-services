package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Where(clause = "is_active = 1")
@Table(name = "post_actor_certificate")
public class PostActorCertificate extends BaseTransactionalEntity implements DomainEntity<PostActorCertificate> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_actor_id",referencedColumnName = "id")
    private PostActor postActor;

    @NotNull(message = "url must not be null")
    @Column(name = "url",nullable = false)
    private String url;

    @Column(name = "title")
    private String title;

    @NotNull(message = "certificateTemplateCode must not be null")
    @Column(name = "certificate_template_code",nullable = false)
    private String certificateTemplateCode;

    @Column(name = "awarded_by")
    private Long awardedBy;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "issued_date",nullable = false)
    private Date issuedDate;

    @Column(name = "valid_till_date")
    private Date validTillDate;

    @Column(name = "mail_sent")
    private Boolean mailSent;

    @Column(name = "mail_sent_date")
    private Date mailSentDate;

    @Builder
    public PostActorCertificate(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                                Long id, Post post, PostActor postActor, String url, String title, String certificateTemplateCode,
                                Long awardedBy, Boolean isPublic, Date issuedDate, Date validTillDate, Boolean mailSent, Date mailSentDate) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.post = post;
        this.postActor = postActor;
        this.url = url;
        this.title = title;
        this.certificateTemplateCode = certificateTemplateCode;
        this.awardedBy = awardedBy;
        this.isPublic = isPublic;
        this.issuedDate = issuedDate;
        this.validTillDate = validTillDate;
        this.mailSent = mailSent;
        this.mailSentDate = mailSentDate;
    }

    public void init(Post post,PostActor postActor) {
        this.id = 0L;
        this.awardedBy = PlatformSecurityUtil.getCurrentUserId();
        this.issuedDate = new Date();
        this.post = post;
        this.postActor = postActor;
    }

    public void patchUpdate(PostActorCertificate toBeUpdated) {
        if(!StringUtils.isBlank(toBeUpdated.getUrl())) {
            this.url = toBeUpdated.getUrl();
        }
        if(!StringUtils.isBlank(toBeUpdated.getTitle())) {
            this.title = toBeUpdated.getTitle();
        }
        if(!StringUtils.isBlank(toBeUpdated.getCertificateTemplateCode())) {
            this.certificateTemplateCode = toBeUpdated.getCertificateTemplateCode();
        }
        if(toBeUpdated.getIsPublic() != null) {
            this.isPublic = toBeUpdated.getIsPublic();
        }
        if(toBeUpdated.getIssuedDate() != null) {
            this.issuedDate = toBeUpdated.getIssuedDate();
        }
        if(toBeUpdated.getValidTillDate() != null) {
            this.validTillDate = toBeUpdated.getValidTillDate();
        }
        if(Objects.equals(toBeUpdated.getMailSent(),Boolean.TRUE)) {
            this.mailSent =Boolean.TRUE;
            this.mailSentDate = new Date();
        }

    }

    public void updateFieldsForMailSent() {
        this.mailSent =Boolean.TRUE;
        this.mailSentDate = new Date();
    }
}
