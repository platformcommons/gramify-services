package com.platformcommons.platform.service.iam.dto.brbase;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TLDRoleHierarchyStructureDTO {

    @NotNull
    private Integer id;
    // Purpose of RoleHierarchyStructure
    private String purpose;
    // Name of RoleHierarchyStructure
    private String name;
    @NotNull
    private String function;

    // roleHierarchyList of RoleHierarchyStructure
    @Valid
    private List<TLDRoleHierarchyDTO> roleHierarchyList;
}
