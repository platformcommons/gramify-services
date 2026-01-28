package com.platformcommons.platform.service.assessment.reporting.application.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class PerformanceMonitor {

    @Around("execution(* com.platformcommons.platform.service.assessment.reporting.facade.AssessmentInstanceDimFacade.getAssessmentInstanceById(..)) || " +
            "execution(* com.platformcommons.platform.service.assessment.reporting.facade.AssessmentInstanceDimFacade.getSyncContext(..)) || " +
            "execution(* com.platformcommons.platform.service.assessment.reporting.application.SectionQuestionDimService.getSequencedSectionQuestion(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        log.error("Performance Metric: Method [{}] executed in [{} ms]",
                joinPoint.getSignature().toShortString(),
                stopWatch.getTotalTimeMillis());

        return result;
    }
}
