package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.SequenceService;
import com.platformcommons.platform.service.iam.domain.Sequence;
import com.platformcommons.platform.service.iam.domain.repo.SequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Override
    public void createDefaultSequence(String name, Long nextVal) {
        if(!sequenceRepository.findById(name).isPresent()){
            sequenceRepository.save(Sequence.builder()
                                            .name(name)
                                            .nextVal(nextVal)
                                            .build());
        }
    }
}
