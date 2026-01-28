package com.platformcommons.platform.service.search.domain;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SolrBaseEntity implements Serializable {

    @Field
    private Long createdTimestamp;

    @Field
    private Long lastModifiedTimestamp;

    public SolrBaseEntity(Long createdTimestamp, Long lastModifiedTimestamp) {
        this.createdTimestamp = createdTimestamp;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public void setCreatedTimestamp(){
        this.createdTimestamp = new Date().getTime();
    }

    public void setLastModifiedTimestamp(){
        this.lastModifiedTimestamp = new Date().getTime();
    }
}
