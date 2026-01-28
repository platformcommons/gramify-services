package com.platformcommons.platform.service.domain.domain;

import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "search_keyword")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class SearchKeyword  extends NonMultiTenantBaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String textValue;

    @Builder
    public SearchKeyword(String uuid, Boolean isActive, Long appCreatedTimestamp,
                         Long appLastModifiedTimestamp, Long id, String textValue) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.textValue = textValue;
    }

    public void patch(SearchKeyword keywordToBeUpdated) {
        if(keywordToBeUpdated.getTextValue()!=null){
            this.textValue = textValue;
        }
    }
}
