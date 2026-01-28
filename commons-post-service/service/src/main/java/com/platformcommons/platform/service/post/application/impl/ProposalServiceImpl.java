package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.service.post.application.ProposalService;
import com.platformcommons.platform.service.post.domain.Proposal;
import com.platformcommons.platform.service.post.domain.repo.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalServiceImpl implements ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Override
    public Proposal save(Proposal proposal) {
        return proposalRepository.save(proposal);
    }
}
