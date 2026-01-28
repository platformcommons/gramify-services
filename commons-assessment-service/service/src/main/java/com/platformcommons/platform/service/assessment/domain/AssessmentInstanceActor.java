package com.platformcommons.platform.service.assessment.domain;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class AssessmentInstanceActor {


    @Column(name = "actor_id")
    private String actorId;

    @Column(name = "actor_type")
    private String actorType;


    @Builder
    public AssessmentInstanceActor(String actorId, String actorType) {
        this.actorId = actorId;
        this.actorType = actorType;
    }

}