package com.platformcommons.platform.service.domain.domain;


import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
@Entity
@NoArgsConstructor
@Getter @Setter
@Indexed
@Table(name = "skill",indexes ={@Index(name = "skill_code_context_tenantId_index",columnList = "skill_code,context,tenant_id",unique = true)})
public class Skill extends BaseTransactionalEntity implements DomainEntity<Skill> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @GenericField
    private Long id;
    @NotNull
    @Column(name = "skill_code",nullable = false,unique = true)
    @KeywordField
    private String skillCode;
    @NotNull
    @Column(name = "domain_code",nullable = false)
    @KeywordField
    private String domainCode;
    @Column(name = "skill_name")
    private String skillName;
    @Column(name = "skill_desc")
    private String skillDesc;
    @Column(name = "skill_type")
    private String skillType;
    @NotNull
    @Column(name = "is_root",nullable = false)
    @GenericField
    private Boolean isRoot;
    @NotNull
    @Column(name = "context",nullable = false)
    @KeywordField
    private String context;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    private Set<MLText> name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MLText> description;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "skill_has_tags", joinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    @BatchSize(size = 20)
    private Set<Tag> tags;
    @Builder
    public Skill(String uuid,Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String skillCode, String domainCode, String skillName, String skillDesc, String skillType, Boolean isRoot, String context, Set<MLText> name, Set<MLText> description, Set<Tag> tags) {
        super(uuid,tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.skillCode = skillCode;
        this.domainCode = domainCode;
        this.skillName = skillName;
        this.skillDesc = skillDesc;
        this.skillType = skillType;
        this.isRoot = isRoot;
        this.context = context;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public void update(Skill skill) {
        if(skill.getSkillCode()!=null)
            this.skillCode = skill.getSkillCode();
        if(skill.getDomainCode()!=null)
            this.domainCode = skill.getDomainCode();
        if(skill.getSkillName()!=null)
            this.skillName = skill.getSkillName();
        if(skill.getSkillDesc()!=null)
            this.skillDesc = skill.getSkillDesc();
        if(skill.getSkillType()!=null)
            this.skillType = skill.getSkillType();
        if(skill.getIsRoot()!=null)
            this.isRoot = skill.getIsRoot();
        if(skill.getContext()!=null)
            this.context = skill.getContext();
        if(skill.getName()!=null)
            this.name = skill.getName();
        if(skill.getDescription()!=null)
            this.description = skill.getDescription();
        if (skill.getTags() != null && !skill.getTags().isEmpty()) {
            this.getTags().forEach(tag -> skill.getTags().forEach(tagToBeUpdated->{
                if(tag.getId().equals(skill.getId())){
                    tag.patch(tagToBeUpdated);
                }
            }));
            skill.getTags().forEach(tag -> {
                if (tag.getId().equals(0L)) {
                    tag.setId(null);
                    this.getTags().add(tag);
                }
            });
        }
    }
}
