package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.Client;

public interface ClientService {

    Client getClientByTenantIdAndClientId(Long tenantId, String clientId);
}
