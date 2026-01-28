package com.platformcommons.platform.service.iam.facade.assembler.obo;

import com.platformcommons.platform.service.iam.dto.RoleDTO;
import com.platformcommons.platform.service.iam.dto.brbase.TLDRoleDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TLDRoleDTOAssembler {

    public RoleDTO from(TLDRoleDTO tldRoleDTO) {
        return RoleDTO.builder()
                .code(tldRoleDTO.getCode())
                .roleName(tldRoleDTO.getRoleName())
                .build();
    }

    public Set<RoleDTO> from(List<TLDRoleDTO> tldRoleDTOList) {
        return tldRoleDTOList.stream().map(this::from).collect(Collectors.toSet());
    }
}
