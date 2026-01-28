package com.platformcommons.platform.service.domain.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "domain",indexes = {
        @Index(columnList = "code,context",unique = true)
})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Domain extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<Domain> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id must not be null")
    @Column(name = "id")
    private Long id;

    @NotNull(message = "code must not be null")
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<MLText> names;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MLText> descriptions;

    @NotNull(message = "isRoot must not be null")
    @Column(name = "is_root")
    private Boolean isRoot;


    @Column(name = "context", nullable = false)
    private String context;


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "TagHasDomain", joinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tagList;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "domain_suggested_apps", joinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"))
    private Set<App> suggestedAppList;

    @Column(name = "icon")
    private String icon;

    @Column(name = "banner")
    private String banner;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "type")
    private String type;

    @Transient
    private boolean isNew;



    @Builder
    public Domain(String uuid,  Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                  Long id, String code, String name, String description, Boolean isRoot,String context,String icon,
                  String banner,Set<Tag> tagList,Set<App> suggestedAppList,Set<MLText> names,Set<MLText> descriptions,
                  String colorCode, String type) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.isRoot = isRoot!=null? isRoot : Boolean.FALSE;
        this.context = context;
        this.tagList =tagList;
        this.icon  = icon;
        this.banner = banner;
        this.suggestedAppList = suggestedAppList;
        this.names = names;
        this.descriptions = descriptions;
        this.colorCode=colorCode;
        this.type = type;
    }

    public void init() {
    }

    public void update(Domain toBeUpdated) {
        if(toBeUpdated.getCode()!=null){
            this.code = toBeUpdated.getCode();
        }
        if(toBeUpdated.getName()!=null){
            this.name = toBeUpdated.getName();
        }
        if(toBeUpdated.getContext()!=null){
            this.context = toBeUpdated.getContext();
        }
        if(toBeUpdated.getDescription()!=null){
            this.description = toBeUpdated.getDescription();
        }
        if(toBeUpdated.getBanner()!=null){
            this.banner = toBeUpdated.getBanner();
        }
        if(toBeUpdated.getIcon()!=null){
            this.icon = toBeUpdated.getIcon();
        }
        if(toBeUpdated.getColorCode()!=null){
            this.colorCode = toBeUpdated.getColorCode();
        }
        if(toBeUpdated.getType()!=null){
            this.type = toBeUpdated.getType();
        }
        if (toBeUpdated.getTagList() != null && !toBeUpdated.getTagList().isEmpty()) {
            this.getTagList().forEach(tag -> toBeUpdated.getTagList().forEach(tagToBeUpdated->{
                if(tag.getId().equals(toBeUpdated.getId())){
                    tag.patch(tagToBeUpdated);
                }
            }));
            toBeUpdated.getTagList().forEach(tag -> {
                if (tag.getId().equals(0L)) {
                    tag.setId(null);
                    this.getTagList().add(tag);
                }
            });
        }
        if(toBeUpdated.getNames()!=null){
            this.names = toBeUpdated.getNames();
        }
        if(toBeUpdated.getDescriptions()!=null){
            this.descriptions = toBeUpdated.getDescriptions();
        }

    }
}