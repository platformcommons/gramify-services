package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.RoleHierarchyStructure;
import com.platformcommons.platform.service.iam.dto.RoleHierarchyStructureDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(
        componentModel = "spring")
public interface RoleHierarchyStructureDTOAssembler {

    RoleHierarchyStructure fromDTO(RoleHierarchyStructureDTO dto);

    RoleHierarchyStructureDTO toDTO(RoleHierarchyStructure entity);

    Set<RoleHierarchyStructureDTO> toDTO(Set<RoleHierarchyStructure> entities);
}
