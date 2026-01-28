package com.platformcommons.platform.service.iam.facade.impl.obo;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.RoleDTO;
import com.platformcommons.platform.service.iam.dto.brbase.TLDRoleDTO;
import com.platformcommons.platform.service.iam.facade.assembler.obo.TLDRoleDTOAssembler;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.iam.facade.obo.OBORoleFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class OBORoleFacadeImpl implements OBORoleFacade {

    @Autowired
    private TLDClient tldClient;

    @Autowired
    private TLDRoleDTOAssembler tldRoleDTOAssembler;

    @Override
    public Set<RoleDTO> getRolesFromLinkedSystem() {
        List<TLDRoleDTO> tldRoleDTOList = tldClient.getAllTenantRoles(PlatformSecurityUtil.getToken());
        return tldRoleDTOAssembler.from(tldRoleDTOList);
    }

    @Override
    public void addRoleAndHierarchy(RoleDTO roleDTO, String parentRoleCode, String functionName) {
        //todo
    }
}
