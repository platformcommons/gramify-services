package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.ClientService;
import com.platformcommons.platform.service.iam.domain.Client;
import com.platformcommons.platform.service.iam.domain.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

   @Autowired
   private ClientRepository clientRepository;

    @Override
    public Client getClientByTenantIdAndClientId(Long tenantId, String clientId) {
        return clientRepository.findByTenantIdAndClientId(tenantId, clientId);
    }
}
