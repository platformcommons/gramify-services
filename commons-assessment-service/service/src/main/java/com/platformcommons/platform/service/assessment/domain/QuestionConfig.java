package com.platformcommons.platform.service.assessment.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "question_config")
public class QuestionConfig extends BaseTransactionalEntity implements DomainEntity<QuestionConfig> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question", nullable = false, updatable = false)
    private Question question;

    @Column(name = "config_key")
    @NotNull
    private String configKey;

    @Column(name = "config_value")
    private String configValue;

    public QuestionConfig(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Question question, String configKey, String configValue) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.question = question;
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public void init(Question question) {
        this.id=null;
        this.question=question;
    }
}
