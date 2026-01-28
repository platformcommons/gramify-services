package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.service.iam.application.ClientService;
import com.platformcommons.platform.service.iam.domain.Client;
import com.platformcommons.platform.service.iam.dto.ClientDTO;
import com.platformcommons.platform.service.iam.facade.ClientFacade;
import com.platformcommons.platform.service.iam.facade.TenantFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientFacadeImpl implements ClientFacade {
    @Autowired
    private ClientService clientService;
    @Autowired
    private TenantFacade tenantFacade;

    @Override
    public ClientDTO getClientForTenantIdAndClientId(Long tenantId, String clientId) {
        Client client = clientService.getClientByTenantIdAndClientId(tenantId, clientId);
        return ClientDTO.builder()
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .build();
    }
}
