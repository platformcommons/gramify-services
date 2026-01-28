package com.platformcommons.platform.service.domain.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tag",indexes = {
        @Index(columnList = "code"),
        @Index(columnList = "type")
})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class Tag extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<Tag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TagHasDomain", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"))
    private Set<Domain> domainList;


    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(name = "UseCaseHasTag", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "use_case_id", referencedColumnName = "id"))
    private Set<UseCase> useCasesList;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AppHasTag", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"))
    private Set<App> appList;


    @NotNull(message = "code must not be null")
    @Column(name = "code")
    private String code;

    @NotNull(message = "name must not be null")
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon")
    private String icon;

    @Column(name = "banner_icon")
    private String bannerIcon;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "banner")
    private String banner;

    @Column(name = "sequence")
    private Long sequence;


    @Column(name="color")
    private String color;


    @Column(name="background_color")
    private String backgroundColor;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<MLText> nameML;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<MLText> descriptionML;

    @Column(name="category_code")
    private String categoryCode;

    @Column(name="sub_category_code")
    private String subCategoryCode;

    @Column(name="context")
    private String context;

    @Column(name="is_root")
    private Boolean isRoot;

    @Transient
    private boolean isNew;

    @Builder
    public Tag(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
               Set<Domain> domainList, String code, String name, String type, String description,String icon , String banner,
               Long sequence,String bannerIcon,String identifier,String color,String backgroundColor, Set<MLText> nameML,
               Set<MLText> descriptionML, String categoryCode, String subCategoryCode, String context, Boolean isRoot) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.domainList = domainList;
        this.code = code;
        this.name = name;
        this.type = type;
        this.isRoot = isRoot;
        this.description = description;
        this.icon = icon;
        this.banner = banner;
        this.sequence = sequence;
        this.bannerIcon = bannerIcon;
        this.identifier = identifier;
        this.color=color;
        this.backgroundColor=backgroundColor;
        this.nameML = nameML;
        this.descriptionML = descriptionML;
        this.categoryCode = categoryCode;
        this.subCategoryCode = subCategoryCode;
        this.context = context;
    }

    public void init() {
    }

    public void update(Tag tagToBeUpdated) {
        if(tagToBeUpdated.getName()!=null){
            this.name = tagToBeUpdated.getName();
        }
        if(tagToBeUpdated.getType()!=null){
            this.type = tagToBeUpdated.getType();
        }
        if(tagToBeUpdated.getBanner()!=null){
            this.banner = tagToBeUpdated.getBanner();
        }
        if(tagToBeUpdated.getIcon()!=null){
            this.icon = tagToBeUpdated.getIcon();
        }
        if(tagToBeUpdated.getSequence()!=null){
            this.sequence = tagToBeUpdated.getSequence();
        }
        if(tagToBeUpdated.getBannerIcon()!=null){
            this.bannerIcon = tagToBeUpdated.getBannerIcon();
        }
        if(tagToBeUpdated.getIdentifier()!=null){
            this.identifier = tagToBeUpdated.getIdentifier();
        }
        if(tagToBeUpdated.getColor()!=null){
            this.color = tagToBeUpdated.getColor();
        }
        if(tagToBeUpdated.getBackgroundColor()!=null){
            this.backgroundColor = tagToBeUpdated.getBackgroundColor();
        }
        if(tagToBeUpdated.getNameML()!=null && !tagToBeUpdated.getNameML().isEmpty()){
            Set<MLText> temp = new HashSet<>();
            tagToBeUpdated.getNameML().forEach(i -> {
                boolean flag = false;

                for (MLText j : this.nameML) {
                    if (j.getLanguageCode().equals(i.getLanguageCode())) {
                        j.patchUpdate(i);
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    temp.add(i);
                }
            });
            this.nameML.addAll(temp);
        }
        if(tagToBeUpdated.getDescriptionML()!=null && !tagToBeUpdated.getDescriptionML().isEmpty()){
            Set<MLText> temp = new HashSet<>();
            tagToBeUpdated.getDescriptionML().forEach(i -> {
                boolean flag = false;

                for (MLText j : this.descriptionML) {
                    if (j.getLanguageCode().equals(i.getLanguageCode())) {
                        j.patchUpdate(i);
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    temp.add(i);
                }
            });
            this.descriptionML.addAll(temp);
        }
        if (tagToBeUpdated.getCategoryCode() != null) {
            this.categoryCode = tagToBeUpdated.getCategoryCode();
        }
        if (tagToBeUpdated.getSubCategoryCode() != null) {
            this.subCategoryCode = tagToBeUpdated.getSubCategoryCode();
        }
        if (tagToBeUpdated.getContext() != null) {
            this.context = tagToBeUpdated.getContext();
        }
        if(tagToBeUpdated.getIsRoot()!=null){
            this.isRoot = tagToBeUpdated.getIsRoot();
        }
    }

    public void patch(Tag tagToBeUpdated) {
        if(tagToBeUpdated.getName()!=null){
            this.name = tagToBeUpdated.getName();
        }
        if(tagToBeUpdated.getType()!=null){
            this.type = tagToBeUpdated.getType();
        }
        if(tagToBeUpdated.getBanner()!=null){
            this.banner = tagToBeUpdated.getBanner();
        }
        if(tagToBeUpdated.getIcon()!=null){
            this.icon = tagToBeUpdated.getIcon();
        }
        if(tagToBeUpdated.getSequence()!=null){
            this.sequence = tagToBeUpdated.getSequence();
        }
        if(tagToBeUpdated.getBannerIcon()!=null){
            this.bannerIcon = tagToBeUpdated.getBannerIcon();
        }
        if(tagToBeUpdated.getIdentifier()!=null){
            this.identifier = tagToBeUpdated.getIdentifier();
        }
        if(tagToBeUpdated.getColor()!=null){
            this.color = tagToBeUpdated.getColor();
        }
        if(tagToBeUpdated.getBackgroundColor()!=null){
            this.backgroundColor = tagToBeUpdated.getBackgroundColor();
        }
        if(tagToBeUpdated.getNameML()!=null && !tagToBeUpdated.getNameML().isEmpty()){
            Set<MLText> temp = new HashSet<>();
            tagToBeUpdated.getNameML().forEach(i -> {
                boolean flag = false;

                for (MLText j : this.nameML) {
                    if (j.getLanguageCode().equals(i.getLanguageCode())) {
                        j.patchUpdate(i);
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    temp.add(i);
                }
            });
            this.nameML.addAll(temp);
        }
        if(tagToBeUpdated.getDescriptionML()!=null && !tagToBeUpdated.getDescriptionML().isEmpty()){
            Set<MLText> temp = new HashSet<>();
            tagToBeUpdated.getDescriptionML().forEach(i -> {
                boolean flag = false;

                for (MLText j : this.descriptionML) {
                    if (j.getLanguageCode().equals(i.getLanguageCode())) {
                        j.patchUpdate(i);
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    temp.add(i);
                }
            });
            this.descriptionML.addAll(temp);
        }
        if (tagToBeUpdated.getCategoryCode() != null) {
            this.categoryCode = tagToBeUpdated.getCategoryCode();
        }
        if (tagToBeUpdated.getSubCategoryCode() != null) {
            this.subCategoryCode = tagToBeUpdated.getSubCategoryCode();
        }
        if (tagToBeUpdated.getContext() != null) {
            this.context = tagToBeUpdated.getContext();
        }
        if(tagToBeUpdated.getIsRoot()!=null){
            this.isRoot = tagToBeUpdated.getIsRoot();
        }
    }
}