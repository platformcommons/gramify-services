package com.platformcommons.platform.service.search.domain.base;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class ElasticBaseEntity implements Serializable {

    @Field
    private Long tenantId;

    @Field
    private Boolean isActive;

    private SeqNoPrimaryTerm SeqNoPrimaryTerm;

}
