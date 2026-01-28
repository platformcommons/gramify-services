package com.platformcommons.platform.service.iam.application.config;

import com.platformcommons.platform.service.iam.application.SequenceService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final SequenceService sequenceService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        sequenceService.createDefaultSequence(IAMConstant.USER_SEQUENCE,5000000L);
        sequenceService.createDefaultSequence(IAMConstant.TENANT_SEQUENCE,50000L);
        sequenceService.createDefaultSequence(IAMConstant.ROLE_MAP_SEQUENCE,10000000L);
    }

}