package com.platformcommons.platform.service.assessment.reporting.application.context;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.reporting.application.constant.LinkedSystem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class EventContext {

    private AssessmentReportSyncContext assessmentReportSyncContext;
    private String linkedSystem = LinkedSystem.COMMONS;
    private AssessmentSyncContext assessmentSyncContext;

    private final static ThreadLocal<EventContext> event = ThreadLocal.withInitial(EventContext::new);

    public static void setEvent(EventContext event) {
        EventContext.event.set(event);
    }

    public static void removeEvent() {
        event.remove();
    }

    public static EventContext getEvent() {
        return event.get();
    }

    public static void setSystemEvent(String linkedSystem) {
        if(!LinkedSystem.SYSTEMS.contains(linkedSystem)) throw new InvalidInputException("Invalid linked system");
        EventContext.getEvent()
                .setLinkedSystem(linkedSystem);
    }

    public static String getSystemEvent() {
        return EventContext.getEvent()
                .getLinkedSystem();
    }

    public static void setAssessmentContext(AssessmentSyncContext assessmentSyncContext) {
        EventContext.getEvent()
                .setAssessmentSyncContext(assessmentSyncContext);
    }

    public static void setAssessmentReportContext(AssessmentReportSyncContext assessmentReportSyncContext) {
        EventContext.getEvent()
                .setAssessmentReportSyncContext(assessmentReportSyncContext);
    }

    public static AssessmentSyncContext getAssessmentContext() {
        return EventContext.getEvent()
                .getAssessmentSyncContext();
    }

    public static AssessmentReportSyncContext getAssessmentReportContext() {
        return EventContext.getEvent()
                .getAssessmentReportSyncContext();
    }

}
