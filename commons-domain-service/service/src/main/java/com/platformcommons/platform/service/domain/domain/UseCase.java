package com.platformcommons.platform.service.domain.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Table(name = "use_case",indexes = {
        @Index(columnList = "code")
})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class UseCase extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<UseCase> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "authorId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    private Author authorId;

    @Column(name = "name")
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "short_description",columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "long_description",columnDefinition = "TEXT")
    private String longDescription;

    @Column(name = "website")
    private String website;

    @NotNull(message = "code must not be null")
    @Column(name = "code")
    private String code;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AppHasUseCase", joinColumns = @JoinColumn(name = "use_case_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"))
    private Set<App> appList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UseCaseHasDomain", joinColumns = @JoinColumn(name = "use_case_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"))
    private Set<Domain> domainList;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UseCaseHasSubDomain", joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"))
    private Set<Domain> subDomainList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "UseCaseHasTag", joinColumns = @JoinColumn(name = "use_case_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tagList;

    @Transient
    private boolean isNew;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "useCaseId")
    @BatchSize(size = 20)
    private Set<Feature> featureList;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="use_case_id", referencedColumnName="id")
    private Set<SearchKeyword> searchKeywordList;

    @Column(name = "sequence")
    private Long sequence;


    @Builder
    public UseCase(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                   Author authorId, String name,String code, String logo, String shortDescription, String longDescription,
                   String website, Set<App> appList, Set<Domain> domainList, Set<Domain> subDomainList ,Set<Tag> tagList,
                   Set<Feature> featureList,Set<SearchKeyword> searchKeywordList,Long sequence) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.logo = logo;
        this.code = code;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.website = website;
        this.appList = appList;
        this.domainList = domainList;
        this.subDomainList = subDomainList;
        this.tagList = tagList;
        this.sequence = sequence;
    //    this.attachmentList = attachmentList;
        this.searchKeywordList = searchKeywordList;
        this.featureList = featureList;

//        if (null != attachmentList) {
//            this.attachmentList.forEach(it -> it.setUseCaseId(this));
//        }
        if (null != featureList) {
            this.featureList.forEach(it -> it.setUseCaseId(this));
        }

    }

    public void init(Author author) {
        this.authorId = author;
    }

    public void update(UseCase toBeUpdated) {
        if(toBeUpdated.getName()!=null){
            this.name = toBeUpdated.getName();
        }
        if(toBeUpdated.getLogo()!=null){
            this.logo = toBeUpdated.getLogo();
        }
        if(toBeUpdated.getShortDescription()!=null){
            this.shortDescription = toBeUpdated.getShortDescription();
        }
        if(toBeUpdated.getLongDescription()!=null){
            this.longDescription = toBeUpdated.getLongDescription();
        }
        if(toBeUpdated.getWebsite()!=null){
            this.website = toBeUpdated.getWebsite();
        }
        if (toBeUpdated.getSearchKeywordList() != null && !toBeUpdated.getSearchKeywordList().isEmpty()) {
            this.getSearchKeywordList().forEach(searchKeyword -> toBeUpdated.getSearchKeywordList().forEach(keywordToBeUpdated->{
                if(searchKeyword.getId().equals(keywordToBeUpdated.getId())){
                    searchKeyword.patch(keywordToBeUpdated);
                }
            }));
            toBeUpdated.getSearchKeywordList().forEach(searchKeyword -> {
                if (searchKeyword.getId().equals(0L)) {
                    searchKeyword.setId(null);
                    this.getSearchKeywordList().add(searchKeyword);
                }
            });
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
    }
}