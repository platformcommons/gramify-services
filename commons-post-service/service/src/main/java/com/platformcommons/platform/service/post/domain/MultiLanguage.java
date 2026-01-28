package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table( name = "multi_language",
        indexes = {
                @Index( name = "IDX_LANGUAGE_CONTEXT",
                        columnList = "language_context")
})
public class MultiLanguage extends BaseTransactionalEntity implements DomainEntity<MultiLanguage> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "text",nullable = false, columnDefinition = "LONGTEXT")
    @FullTextField(analyzer = "post_indexing",searchAnalyzer = "post_searching")
    private String text;

    @KeywordField
    @Column(name = "language_context",nullable = false)
    private String languageContext;

    @Builder
    public MultiLanguage(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                         Long id, String text, String languageContext) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.text = text;
        this.languageContext = languageContext;
    }

    public void init() {
        this.id = 0L;
    }

    public void patchUpdate(MultiLanguage toBeUpdated) {
        if (!StringUtils.isEmpty(toBeUpdated.getText())) {
            this.text = toBeUpdated.getText();
        }

        if (!StringUtils.isEmpty(toBeUpdated.getLanguageContext())) {
            this.languageContext = toBeUpdated.getLanguageContext();
        }

    }
}
