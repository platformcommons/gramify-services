package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "component_config")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
@BatchSize(size = 20)
public class ComponentConfig extends BaseTransactionalEntity implements DomainEntity<ComponentConfig> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_component_config_id", nullable = true, updatable = false)
    private ComponentConfig parentComponentConfig;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "component_code")
    private String componentCode;

    @Column(name = "component_name")
    private String componentName;

    @Column(name = "description")
    private String description;

    @Column(name = "visibility")
    private Boolean visibility;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "component_config_attributes",
            joinColumns = @JoinColumn(name = "component_config_id")
    )
    @Column(name = "attribute")
    private Set<String> attributes;

    @Column(name = "information")
    private String information;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "is_editable")
    private Boolean isEditable;

    @Column(name = "label")
    private String label;

    @Column(name = "place_holder")
    private String placeHolder;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentComponentConfig")
    @BatchSize(size = 20)
    private Set<ComponentConfig> subComponentConfigSet;

    @Builder
    public ComponentConfig(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp,
                           Long appLastModifiedTimestamp, Long id, ComponentConfig parentComponentConfig, String type, String componentCode,
                           String componentName, String description, Boolean visibility, Boolean isMandatory, Set<String> attributes,
                           String information, Integer sequence, Boolean isEditable, String label, String placeHolder,
                           Set<ComponentConfig> subComponentConfigSet) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.parentComponentConfig = parentComponentConfig;
        this.type = type;
        this.componentCode = componentCode;
        this.componentName = componentName;
        this.description = description;
        this.visibility = visibility;
        this.isMandatory = isMandatory;
        this.attributes = attributes;
        this.information = information;
        this.sequence = sequence;
        this.isEditable = isEditable;
        this.label = label;
        this.placeHolder = placeHolder;
        this.subComponentConfigSet = subComponentConfigSet;
    }

    public void init(ComponentConfig parentComponentConfig) {
        this.id = null;
        this.parentComponentConfig = parentComponentConfig;
        if(this.getSubComponentConfigSet() != null) {
            this.getSubComponentConfigSet().forEach(it-> {
                if (it != null) it.init(this);
            });
        }
    }

    public void patchUpdate(ComponentConfig toBeUpdated) {
        if(toBeUpdated.getComponentCode() != null) {
            this.componentCode = toBeUpdated.getComponentCode();
        }
        if(toBeUpdated.getComponentName() != null) {
            this.componentName = toBeUpdated.getComponentName();
        }
        if(toBeUpdated.getVisibility() != null) {
            this.visibility = toBeUpdated.getVisibility();
        }
        if(toBeUpdated.getIsMandatory() != null) {
            this.isMandatory = toBeUpdated.getIsMandatory();
        }
        if(toBeUpdated.getAttributes() != null) {
            this.attributes = toBeUpdated.getAttributes();
        }
        if(toBeUpdated.getInformation() != null) {
            this.information = toBeUpdated.getInformation();
        }
        if(toBeUpdated.getSequence() != null) {
            this.sequence = toBeUpdated.getSequence();
        }
        if(toBeUpdated.getIsEditable() != null) {
            this.isEditable = toBeUpdated.getIsEditable();
        }
        if(toBeUpdated.getLabel() != null) {
            this.label = toBeUpdated.getLabel();
        }
        if(toBeUpdated.getDescription() != null) {
            this.description = toBeUpdated.getDescription();
        }
        if(toBeUpdated.getPlaceHolder() != null) {
            this.placeHolder = toBeUpdated.getPlaceHolder();
        }
        if(toBeUpdated.getSubComponentConfigSet() != null) {
            this.subComponentConfigSet.forEach(subComponentConfig -> {
                toBeUpdated.getSubComponentConfigSet().forEach(toBeUpdatedSubComponentConfig -> {
                    if(Objects.equals(subComponentConfig.getId(),toBeUpdatedSubComponentConfig.getId())) {
                        subComponentConfig.patchUpdate(toBeUpdatedSubComponentConfig);
                    }
                });
            });
            toBeUpdated.getSubComponentConfigSet().forEach(toBeSaved -> {
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init(this);
                    this.subComponentConfigSet.add(toBeSaved);
                }
            });
        }

        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());
    }
}
