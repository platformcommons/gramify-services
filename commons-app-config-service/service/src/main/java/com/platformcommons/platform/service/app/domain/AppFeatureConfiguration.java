package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_feature_configuration")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class AppFeatureConfiguration extends BaseTransactionalEntity implements DomainEntity<AppFeatureConfiguration> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who owns the config: TENANT / MARKET / PLATFORM
    @Column(name = "owner_entity_type",nullable = false)
    private String ownerEntityType;

    // id of tenant or market (can be null for platform default)
    @Column(name = "owner_entity_id")
    private String ownerEntityId;

    @Column(name = "for_entity_type",nullable = false)
    private String forEntityType;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "app_feature_configuration_component_config",
            joinColumns = @JoinColumn(name = "app_feature_configuration_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "component_config_id",referencedColumnName = "id"))
    private Set<ComponentConfig> componentConfigSet;

    @Transient
    private boolean isNew;

    // flag to indicate if this config is a fallback
    @Transient
    private Boolean isFallback;

    @Builder
    public AppFeatureConfiguration(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp,
                                   Long appLastModifiedTimestamp, Long id, String ownerEntityType, String ownerEntityId,
                                   String forEntityType, Set<ComponentConfig> componentConfigSet, boolean isNew, Boolean isFallback) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.ownerEntityType = ownerEntityType;
        this.ownerEntityId = ownerEntityId;
        this.forEntityType = forEntityType;
        this.componentConfigSet = componentConfigSet;
        this.isNew = isNew;
        this.isFallback = isFallback;
    }

    public void init() {
        this.id = null;
        if(componentConfigSet != null){
            componentConfigSet.forEach(config -> config.init(null));
        }
    }

    public void update(AppFeatureConfiguration toBeUpdated) {
        if(toBeUpdated.getComponentConfigSet() != null && !toBeUpdated.getComponentConfigSet().isEmpty()){
            this.componentConfigSet.forEach(current ->{
                toBeUpdated.componentConfigSet.forEach(updated ->{
                    if(Objects.equals(current.getId(),updated.getId())){
                        current.patchUpdate(updated);
                    }
                });
            });
            toBeUpdated.getComponentConfigSet().forEach(toBeSaved -> {
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)){
                    toBeSaved.init(null);
                    this.componentConfigSet.add(toBeSaved);
                }
            });
        }
    }
}