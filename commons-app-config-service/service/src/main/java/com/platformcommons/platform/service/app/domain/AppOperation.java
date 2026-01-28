package com.platformcommons.platform.service.app.domain;

import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Table(name = "app_operation")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class AppOperation extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<AppOperation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_rbac",updatable = false)
    private AppRbac appRbac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_rbac_meta")
    private AppRbacMeta appRbacMeta;

    @NotNull(message = "entity must not be null")
    @Column(name = "entity")
    private String entity;

    @Column(name = "create_operation")
    private Boolean create;

    @Column(name = "update_operation")
    private Boolean update;

    @Column(name = "read_operation")
    private Boolean read;

    @Column(name = "delete_operation")
    private Boolean delete;

    @Column(name = "is_direct")
    private Boolean isDirect;

    @Column(name = "download")
    private Boolean download;

    @Column(name = "linked_menu_code")
    private String linkedMenuCode;

    @Column(name = "group_name")
    private String group;

    @Column(name = "label")
    private String label;

    @Transient
    private boolean isNew;

    @Builder
    public AppOperation(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                        AppRbac appRbac, String entity, Boolean create, Boolean update, Boolean read, Boolean delete,
                        Boolean isDirect,Boolean download, String linkedMenuCode, String group, String label, boolean isNew,
                        AppRbacMeta appRbacMeta) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.appRbac = appRbac;
        this.appRbacMeta = appRbacMeta;
        this.entity = entity;
        this.create = create;
        this.update = update;
        this.read = read;
        this.delete = delete;
        this.isDirect = isDirect;
        this.download = download;
        this.linkedMenuCode = linkedMenuCode;
        this.group = group;
        this.label=label;
        this.isNew = isNew;
    }

    public void init(AppRbac appRbac, AppRbacMeta appRbacMeta) {
        this.id = 0L;
        this.appRbac = appRbac;
        this.appRbacMeta = appRbacMeta;
        this.read = this.read != null ? this.read : Boolean.FALSE;
        this.create = this.create != null ? this.create : Boolean.FALSE;
        this.update = this.update != null ? this.update : Boolean.FALSE;
        this.download = this.download != null ? this.download : Boolean.FALSE;
        this.delete = this.delete != null ? this.delete : Boolean.FALSE;
    }

    public void update(AppOperation toBeUpdated) {
        PlatformUtil.validateLoginTenant(this.getCreatedByTenant());
        this.setUpdate(toBeUpdated.getUpdate());
        this.setDelete(toBeUpdated.getDelete());
        this.setDownload(toBeUpdated.getDownload());
        this.setEntity(toBeUpdated.getEntity());
        this.setGroup(toBeUpdated.getGroup());
        this.setIsDirect(toBeUpdated.getIsDirect());
        this.setLinkedMenuCode(toBeUpdated.getLinkedMenuCode());
        this.setRead(toBeUpdated.getRead());
        this.setCreate(toBeUpdated.getCreate());
        this.setLabel(toBeUpdated.getLabel());
    }

    public void patch(AppOperation toBeUpdated) {
        PlatformUtil.validateLoginTenantAndAdmin(this.getTenantId());
        if(toBeUpdated.getUpdate()!=null)
            this.setUpdate(toBeUpdated.getUpdate());
        if(toBeUpdated.getDelete()!=null)
            this.setDelete(toBeUpdated.getDelete());
        if(toBeUpdated.getDownload()!=null)
            this.setDownload(toBeUpdated.getDownload());
        if(toBeUpdated.getEntity()!=null)
            this.setEntity(toBeUpdated.getEntity());
        if(toBeUpdated.getGroup()!=null)
            this.setGroup(toBeUpdated.getGroup());
        if(toBeUpdated.getIsDirect()!=null)
            this.setIsDirect(toBeUpdated.getIsDirect());
        if(toBeUpdated.getLinkedMenuCode()!=null)
            this.setLinkedMenuCode(toBeUpdated.getLinkedMenuCode());
        if(toBeUpdated.getRead()!=null)
            this.setRead(toBeUpdated.getRead());
        if(toBeUpdated.getCreate()!=null)
            this.setCreate(toBeUpdated.getCreate());
        if(toBeUpdated.getLabel()!=null)
            this.setLabel(toBeUpdated.getLabel());
    }
}