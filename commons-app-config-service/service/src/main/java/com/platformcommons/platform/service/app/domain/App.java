package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.entity.base.BaseEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class App extends BaseEntity implements DomainEntity<App> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "api_key", unique = true)
    @NotNull(message = "apiKey must not be null")
    private String apiKey;

    @Column(name = "code", unique = true)
    @NotNull(message = "code must not be null")
    private String code;

    @NotNull(message = "name must not be null")
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "appId")
    @BatchSize(size = 20)
    private Set<AppVersion> appVersionList;

    @ManyToMany
    @JoinTable(
            name = "app_instances",
            joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "instance_id", referencedColumnName = "id")
    )
    private Set<Instance> instances;

    @Builder
    public App(
               Long id, String apiKey, String code, String name, Set<AppVersion> appVersionList,Set<Instance> instances) {
        this.id = id;
        this.apiKey = apiKey;
        this.code = code;
        this.name = name;
        this.appVersionList = appVersionList;
        if (null != appVersionList) {
            this.appVersionList.forEach(it -> it.setAppId(this));
        }
        this.instances = instances;
    }

    public void init(){
        this.id=null;
    }
}