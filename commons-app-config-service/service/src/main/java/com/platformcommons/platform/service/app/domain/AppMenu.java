package com.platformcommons.platform.service.app.domain;

import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Table(name = "app_menu")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class AppMenu extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<AppMenu> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu", nullable = true, updatable = false)
    private AppMenu parentMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_rbac")
    private AppRbac appRbac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_rbac_meta")
    private AppRbacMeta appRbacMeta;

    @NotNull(message = "menuCode must not be null")
    @Column(name = "menu_code")
    private String menuCode;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "is_root")
    private Boolean isRoot;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "label")
    private String label;

    @Column(name = "route")
    private String route;

    @Column(name = "custom_link")
    private String customLink;

    @Column(name = "menu_type")
    private String menuType;

    @Column(name = "icon")
    private String icon;

    @Column(name = "can_edit")
    private Boolean canEdit;

    @Column(name = "display_app_menu")
    private Boolean displayAppMenu;

    @Column(name = "display_sub_menus")
    private Boolean displaySubMenus;

    @Column(name = "active_icon")
    private String activeIcon;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentMenu")
    @BatchSize(size = 20)
    private Set<AppMenu> subMenu;

    @Transient
    private boolean isNew;

    @Transient
    private boolean isUpdated;

    @Builder
    public AppMenu(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                   AppMenu parentMenu, AppRbac appRbac, AppRbacMeta appRbacMeta, String menuCode, Long sequence, Boolean isRoot,
                   Boolean isEnabled, boolean isNew, String label,String route,String menuType,String icon,Set<AppMenu> subMenu,
                   Boolean canEdit, Boolean displayAppMenu, Boolean displaySubMenus, String activeIcon,String customLink, boolean isUpdated,
                   String description) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.parentMenu = parentMenu;
        this.appRbac = appRbac;
        this.appRbacMeta = appRbacMeta;
        this.menuCode = menuCode;
        this.sequence = sequence;
        this.isRoot = isRoot;
        this.isEnabled = isEnabled;
        this.isNew = isNew;
        this.subMenu = subMenu;
        this.route = route;
        this.label = label;
        this.menuType =  menuType;
        this.icon = icon;
        this.canEdit = canEdit;
        this.displayAppMenu = displayAppMenu;
        this.displaySubMenus = displaySubMenus;
        if(this.subMenu !=null){
            this.subMenu.forEach(it-> it.setParentMenu(this));
        }
        this.activeIcon = activeIcon;
        this.customLink = customLink;
        this.isUpdated = isUpdated;
        this.description = description;
    }

    public void init(AppRbac appRbac, AppRbacMeta appRbacMeta, AppMenu parentMenu) {
        this.id = 0L;
        this.appRbac = appRbac;
        this.appRbacMeta = appRbacMeta;
        this.parentMenu = parentMenu;
        if(subMenu != null) {
            subMenu.forEach(it->it.init(null,null, this));
        }
    }

    public void update(AppMenu toBeUpdated) {
        PlatformUtil.validateLoginTenant(this.getCreatedByTenant());
        this.menuCode = toBeUpdated.getMenuCode();
        this.sequence = toBeUpdated.getSequence();
        this.isRoot = toBeUpdated.getIsRoot();
        this.isEnabled = toBeUpdated.getIsEnabled();
        this.label = toBeUpdated.getLabel();
        this.route = toBeUpdated.getRoute();
        this.menuType = toBeUpdated.getMenuType();
        this.icon = toBeUpdated.getIcon();
        this.canEdit = toBeUpdated.getCanEdit();
        this.displayAppMenu = toBeUpdated.getDisplayAppMenu();
        this.displaySubMenus = toBeUpdated.getDisplaySubMenus();
        this.activeIcon =  toBeUpdated.getActiveIcon();
        this.description = description;
    }
    public void patch(AppMenu toBeUpdated) {
        PlatformUtil.validateLoginTenantAndAdmin(this.getTenantId());
        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());

        if(toBeUpdated.getMenuCode()!=null)
            this.menuCode = toBeUpdated.getMenuCode();
        if(toBeUpdated.getSequence()!=null)
            this.sequence = toBeUpdated.getSequence();
        if(toBeUpdated.getIsRoot()!=null)
            this.isRoot = toBeUpdated.getIsRoot();
        if(toBeUpdated.getIsEnabled()!=null)
            this.isEnabled = toBeUpdated.getIsEnabled();
        if(toBeUpdated.getLabel()!=null)
            this.label = toBeUpdated.getLabel();
        if(toBeUpdated.getRoute()!=null)
            this.route = toBeUpdated.getRoute();
        if(toBeUpdated.getMenuType()!=null)
            this.menuType = toBeUpdated.getMenuType();
        if(toBeUpdated.getIcon()!=null)
            this.icon = toBeUpdated.getIcon();
        if(toBeUpdated.getCanEdit()!=null)
            this.canEdit = toBeUpdated.getCanEdit();
        if(toBeUpdated.getDisplayAppMenu()!=null)
            this.displayAppMenu = toBeUpdated.getDisplayAppMenu();
        if(toBeUpdated.getDisplaySubMenus()!=null)
            this.displaySubMenus = toBeUpdated.getDisplaySubMenus();
        if(toBeUpdated.getActiveIcon()!=null)
            this.activeIcon = toBeUpdated.getActiveIcon();
        if(toBeUpdated.getCustomLink() != null)
            this.customLink = toBeUpdated.getCustomLink();
        if(toBeUpdated.getDescription() != null)
            this.description = toBeUpdated.getDescription();
        if(toBeUpdated.getSubMenu() != null ) {
            this.getSubMenu().forEach(subMenu -> {
                toBeUpdated.getSubMenu().forEach(toBeUpdatedSubMenu -> {
                    if(Objects.equals(subMenu.getId(),toBeUpdatedSubMenu.getId())) {
                        subMenu.patch(toBeUpdatedSubMenu);
                    }
                });
            });
            toBeUpdated.getSubMenu().forEach(toBeSaved -> {
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init(null,null,this);
                    this.getSubMenu().add(toBeSaved);
                }
            });
        }
    }

    public void patchUpdateForToggle(AppMenu toBeUpdated) {
        if (toBeUpdated.getIsEnabled() != null) {
            this.isEnabled = toBeUpdated.getIsEnabled();
        }
        if(toBeUpdated.getSubMenu() != null && !toBeUpdated.getSubMenu().isEmpty()) {
            this.subMenu.forEach(appMenu -> {
                toBeUpdated.getSubMenu().forEach(toBeUpdatedAppMenu -> {
                    if(Objects.equals(appMenu.getId(),toBeUpdatedAppMenu.getId())) {
                        appMenu.patchUpdateForToggle(toBeUpdatedAppMenu);
                    }
                });
            });
        }
    }
}