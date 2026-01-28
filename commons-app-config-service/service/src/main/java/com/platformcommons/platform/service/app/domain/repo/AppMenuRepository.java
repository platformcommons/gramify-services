package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AppMenuRepository extends NonMultiTenantBaseRepository<AppMenu, Long> {

    @Query("SELECT am FROM #{#entityName} am WHERE am.appRbac.appCode = :appCode " +
            "AND ( am.appRbac.role = :role OR :role is NULL ) AND ( am.menuCode = :menuCode OR :menuCode is NULL ) " +
            "AND am.isActive = 1")
    Page<AppMenu> findByAppCodeAppRoleAndMenuCode(String appCode, String role, String menuCode, Pageable pageable);

    @Query("SELECT DISTINCT am FROM AppMenu am JOIN AppRbac rb ON rb.id = am.appRbac " +
            "JOIN AppMenu asm ON asm.parentMenu=am.id WHERE rb.appCode = :appCode " +
            "AND ( rb.role = :role OR :role is NULL ) AND (am.menuCode = :menuCode OR :menuCode is NULL ) " +
            "AND (asm.menuCode = :subMenuCode OR :subMenuCode is NULL ) " +
            "AND am.isActive = 1")
    Page<AppMenu> findByAppCodeAppRoleAndMenuCodeAndSubMenu(String appCode, String role, String menuCode, String subMenuCode, Pageable pageable);

    @Query("SELECT am FROM #{#entityName} am WHERE am.appRbac.id = :rbacId AND am.isActive = 1 ")
    Page<AppMenu> findByIdOfAppRbac(Long rbacId, Pageable pageable);

    @Query("SELECT am FROM #{#entityName} am WHERE am.appRbac.id = :rbacId AND am.menuCode = :menuCode AND am.isActive = 1 ")
    Optional<AppMenu> findByIdOfAppRbacAndMenuCode(Long rbacId, String menuCode);

    @Query("SELECT am FROM #{#entityName} am WHERE am.appRbac.appCode = :appCode AND am.appRbac.role = :role " +
            " and am.menuCode = :menuCode AND am.isActive = 1 ")
    Optional<AppMenu> findByAppCodeAndRoleOfAppRbacAndMenuCode(String appCode, String role,String menuCode);

    @Query("SELECT am FROM #{#entityName} am WHERE am.parentMenu.id = :parentId AND am.menuCode = :subMenuCode " +
            " AND am.isActive = 1")
    Optional<AppMenu> findByIdAndMenuCode(Long parentId, String subMenuCode);

    @Query("SELECT am FROM #{#entityName} am WHERE am.appRbac.appCode = :appCode AND am.appRbac.role = :role " +
            " and am.menuCode IN (:menuCodes) AND am.isActive = 1")
    Set<AppMenu> findByAppCodeAndRoleAndTopMostMenuCodes(String appCode, String role, Set<String> menuCodes);
}