package com.platformcommons.platform.service.assessment.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class EmbeddableAssessmentActor {

    private String actorId;

    private String actorType;

    private Long groupId;
    private String groupCode;

    private String actorIeId;

    private String name;

    @Builder
    public EmbeddableAssessmentActor(String actorId, String actorType,  Long groupId, String actorIeId, String name, String groupCode) {
        this.name = name;
        this.actorId = actorId;
        this.actorType = actorType;
        this.groupId = groupId;
        this.actorIeId = actorIeId;
        this.groupCode = groupCode;
    }

}