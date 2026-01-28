package com.platformcommons.platform.service.domain.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app", indexes = {
        @Index(columnList = "slug,is_active", unique = true)
})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class App extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<App> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "slug")
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = true, updatable = false)
    private Author author;

    @NotNull(message = "name must not be null")
    @Column(name = "name")
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "website")
    private String website;

    @Column(name = "short_description",columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "long_description",columnDefinition = "TEXT")
    private String longDescription;

    @Column(name = "download_count")
    private Long downloadCount;

    @Column(name = "current_version")
    private String currentVersion;

    @Column(name = "average_rating")
    private Double averageRating;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AppHasDomain", joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"))
    private Set<Domain> domainList;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AppHasSubDomain", joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"))
    private Set<Domain> subDomainList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AppHasTag", joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tagList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AppHasUseCase", joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "use_case_id", referencedColumnName = "id"))
    private Set<UseCase> useCaseList;

    @Transient
    private boolean isNew;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appId")
    @BatchSize(size = 20)
    private Set<Benefit> benefitList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appId")
    @BatchSize(size = 20)
    private Set<Feature> featureList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appId")
    @BatchSize(size = 20)
    private Set<Platform> platformList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appId")
    @BatchSize(size = 20)
    private Set<Testimonial> testimonialList;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="app_id", referencedColumnName="id")
    private Set<SearchKeyword> searchKeywordList;

    @Builder
    public App(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
               Long id,String slug ,Author author, String name, String logo, String website, String shortDescription, String longDescription,
               Long downloadCount, String currentVersion, Double averageRating, Set<Domain> domainList,Set<Domain> subDomainList, Set<Tag> tagList, Set<UseCase> useCaseList,
               Set<Benefit> benefitList, Set<Feature> featureList, Set<Platform> platformList, Set<Testimonial> testimonialList,Set<SearchKeyword> searchKeywordList) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.slug = slug;
        this.author = author;
        this.name = name;
        this.logo = logo;
        this.website = website;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.downloadCount = downloadCount;
        this.currentVersion = currentVersion;
        this.averageRating = averageRating;
        this.domainList = domainList;
        this.tagList = tagList;
        this.useCaseList = useCaseList;
        this.benefitList = benefitList;
        this.featureList = featureList;
        this.platformList = platformList;
        this.testimonialList = testimonialList;
        this.subDomainList = subDomainList;
        this.searchKeywordList = searchKeywordList;
        if (null != benefitList) {
            this.benefitList.forEach(it -> it.setAppId(this));
        }
        if (null != featureList) {
            this.featureList.forEach(it -> it.setAppId(this));
        }
        if (null != platformList) {
            this.platformList.forEach(it -> it.setAppId(this));
        }
        if (null != testimonialList) {
            this.testimonialList.forEach(it -> it.setAppId(this));
        }
    }

    public void init(Author author) {
        this.author = author;
        this.downloadCount = this.downloadCount!=null? this.downloadCount:0L;
        this.averageRating = this.averageRating!=null? this.averageRating:0.0D;
    }

    public void update(App toBeUpdated) {
        if(toBeUpdated.getSlug()!=null){
            this.slug = toBeUpdated.getSlug();
        }
        if(toBeUpdated.getName()!=null){
            this.name = toBeUpdated.getName();
        }
        if(toBeUpdated.getLogo()!=null){
            this.logo = toBeUpdated.getLogo();
        }
        if(toBeUpdated.getWebsite()!=null){
            this.website = toBeUpdated.getWebsite();
        }
        if(toBeUpdated.getShortDescription()!=null){
            this.shortDescription = toBeUpdated.getShortDescription();
        }
        if(toBeUpdated.getLongDescription()!=null){
            this.longDescription = toBeUpdated.getLongDescription();
        }
        if(toBeUpdated.getDownloadCount()!=null){
            this.downloadCount = toBeUpdated.getDownloadCount();
        }
        if(toBeUpdated.getAverageRating()!=null){
            this.averageRating = toBeUpdated.getAverageRating();
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