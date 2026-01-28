package com.platformcommons.platform.service.assessment.domain;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "options")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Options extends BaseTransactionalEntity implements DomainEntity<Options> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "option_code")
    private String optionCode;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "option_imageurl")
    private String optionImageurl;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "option_name", joinColumns = @JoinColumn(name = "options_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "option_name_id", referencedColumnName = "id", unique = true))
    @BatchSize(size = 100)
    private Set<MLText> optionName;

    @Transient
    private boolean isNew;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optionId")
    @BatchSize(size = 20)
    private Set<AiaDefaultResponse> aiaDefaultResponseList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "options")
    @BatchSize(size = 20)
    private Set<DefaultOptions> defaultOptionsList;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;

    @Transient
    private Map<Long, MLText> optionNameMap;
    @Transient
    private Long optionCount;

    @Builder
    public Options(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String optionCode, Long tenant, String optionImageurl, Set<MLText> optionName, Set<AiaDefaultResponse> aiaDefaultResponseList, Set<DefaultOptions> defaultOptionsList, String linkedSystemId, String linkedSystemType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.optionCode = optionCode;
        this.tenant = tenant;
        this.optionImageurl = optionImageurl;
        this.optionName = optionName;
        this.aiaDefaultResponseList = aiaDefaultResponseList;
        this.defaultOptionsList = defaultOptionsList;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
        if (null != aiaDefaultResponseList) {
            this.aiaDefaultResponseList.forEach(it -> it.setOptionId(this));
        }
        if (null != defaultOptionsList) {
            this.defaultOptionsList.forEach(it -> it.setOptions(this));
        }
    }

    public void patchUpdate(Options toBeUpdated) {
        if (toBeUpdated.getOptionCode() != null) this.optionCode = toBeUpdated.getOptionCode();
        if (toBeUpdated.getTenant() != null) this.tenant = toBeUpdated.getTenant();
        if (toBeUpdated.getOptionImageurl() != null) this.optionImageurl = toBeUpdated.getOptionImageurl();
        if (toBeUpdated.getOptionName() != null) {
            toBeUpdated.getOptionName().forEach(it -> {
                if (this.getOptionNameMap().containsKey(it.getId())) this.optionNameMap.get(it.getId()).patchUpdate(it);
                else this.getOptionName().add(it);
            });
        }
    }

    public Map<Long, MLText> getOptionNameMap() {
        return this.optionNameMap = this.optionNameMap == null ? this.optionName.stream().collect(Collectors.toMap(MLText::getId, Function.identity())) : this.optionNameMap;
    }


    public void init() {
        this.id = null;
    }

    public Long getOptionCount() {
        return this.optionCount == null ? 0L : this.optionCount;
    }
}