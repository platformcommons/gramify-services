package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",uses = AuthorityDTOAssembler.class)
public interface RoleDTOAssembler {

    @Mapping(source = "roleType",target = "roleType",resultType = Role.RoleType.class)
    Role fromDTO(RoleDTO roleDTO);
    List<Role> fromDTO(Set<RoleDTO> roleDTOList);

    @Mapping(source = "roleType",target = "roleType",resultType = String.class)
    RoleDTO toDTO(Role role);

    Set<RoleDTO> toDTO(Set<Role> roleList);
}
