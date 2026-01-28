package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.dto.ClientDTO;

public interface ClientFacade {

    ClientDTO getClientForTenantIdAndClientId(Long tenantId, String clientId);
}
