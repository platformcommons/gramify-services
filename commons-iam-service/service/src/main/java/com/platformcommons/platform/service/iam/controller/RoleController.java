package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.iam.dto.RoleDTO;
import com.platformcommons.platform.service.iam.dto.RoleHierarchyStructureDTO;
import com.platformcommons.platform.service.iam.facade.RoleFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/roles")
@Slf4j
@Tag(name = "role-controller")
public class RoleController {

    @Autowired
    private RoleFacade roleFacade;

    @PostMapping
    public ResponseEntity<Long> addRole(@RequestBody RoleDTO roleDTO,
                                          @RequestParam(value = "parentRoleCode", required = false)
                                          String parentRoleCode,
                                          @RequestParam(value = "functionName", required = false)
                                          String functionName,
                                          @RequestParam(value = "authorityFromRoleCode", required = false)
                                          String authorityFromRoleCode) {
        log.info("RoleController.addRole() .. {}", roleDTO);
        return new ResponseEntity<>(roleFacade.addRole(roleDTO,
                parentRoleCode, functionName, authorityFromRoleCode), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Void> updateRole(@RequestBody RoleDTO roleDTO) {
        log.info("RoleController.updateRole() .. {}", roleDTO);
        roleFacade.updateRole(roleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<RoleDTO>> getAllRoles() {
        log.info("RoleController.getAllRoles() ..");
        return new ResponseEntity<>(roleFacade.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping(value = "/hierarchy-structure")
    public ResponseEntity<Set<RoleHierarchyStructureDTO>> getRoleHierarchyStructure(
            @RequestParam(value = "functionName", required = false) String functionName
    ) {
        log.info("RoleController.getRoleHierarchyStructure() .. {}", functionName);
        return new ResponseEntity<>(roleFacade.getRoleHierarchyStructure(functionName), HttpStatus.OK);
    }

    @PostMapping(value = "{roleCode}/authorities")
    public ResponseEntity<Void> addAuthoritiesToRoleCode(
            @PathParam(value = "roleCode") String roleCode,
            @RequestParam(value = "authorities") Set<String> authorities) {
        log.info("RoleController.addAuthorities() .. {}..{}", roleCode, authorities);
        roleFacade.addAuthorities(roleCode, authorities);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
