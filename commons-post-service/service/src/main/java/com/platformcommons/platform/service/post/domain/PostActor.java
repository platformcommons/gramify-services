package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "post_actor",
        indexes = {
                @Index(name = "IDX_actor_type",
                        columnList = "actor_type"),
                @Index(name = "IDX_actor_id",
                        columnList = "actor_id")
        })
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostActor extends BaseTransactionalEntity implements DomainEntity<PostActor> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @FullTextField
    @NotNull(message = "name must not be null")
    @Column(name = "name")
    private String name;

    @Column(name = "iconpic")
    private String iconpic;

    @KeywordField
    @NotNull(message = "actorType must not be null")
    @Column(name = "actor_type", updatable = false)
    private String actorType;

    @GenericField
    @NotNull(message = "actorId must not be null")
    @Column(name = "actor_id", updatable = false)
    private Long actorId;

    @GenericField
    @Column(name = "email_address")
    private String emailAddress;

    @KeywordField
    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "count_of_post_type_difference_click")
    private Integer countOfPostTypeDifferenceClick;


    @Transient
    private boolean isNew;


    @Builder
    public PostActor(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp,
                     Long appLastModifiedTimestamp, Long id, String name, String iconpic, String actorType,
                     Long actorId, boolean isNew,String emailAddress,Integer countOfPostTypeDifferenceClick,String contactNumber,String username) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.iconpic = iconpic;
        this.actorType = actorType;
        this.actorId = actorId;
        this.isNew = isNew;
        this.emailAddress = emailAddress;
        this.countOfPostTypeDifferenceClick = countOfPostTypeDifferenceClick;
        this.contactNumber = contactNumber;
        this.username = username;

    }

    public void patchUpdateForClickCount(PostActor toBeUpdated) {
        if(toBeUpdated.getCountOfPostTypeDifferenceClick() != null) {
            this.countOfPostTypeDifferenceClick = toBeUpdated.getCountOfPostTypeDifferenceClick();
        }
    }
}