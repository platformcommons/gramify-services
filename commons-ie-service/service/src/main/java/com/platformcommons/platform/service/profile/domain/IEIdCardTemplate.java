package com.platformcommons.platform.service.profile.domain;

import javax.persistence.*;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ie_id_card_template")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class IEIdCardTemplate extends BaseTransactionalEntity implements DomainEntity<IEIdCardTemplate> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "template_data", nullable = false,columnDefinition = "LONGTEXT")
    private String templateData;

    @Column(name = "template_type")
    private String templateType;

    @Builder
    public IEIdCardTemplate(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String templateData, String templateType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.templateData = templateData;
        this.templateType = templateType;
    }

    public void init() {
        this.id = 0L;
    }

    public void patch(IEIdCardTemplate toBeUpdated) {
        if (toBeUpdated.getTemplateData() != null) {
            this.setTemplateData(toBeUpdated.getTemplateData());
        }
        if (toBeUpdated.getTemplateType() != null) {
            this.setTemplateType(toBeUpdated.getTemplateType());
        }
    }
}
