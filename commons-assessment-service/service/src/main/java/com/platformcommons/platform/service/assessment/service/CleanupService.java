package com.platformcommons.platform.service.assessment.service;

import com.platformcommons.platform.service.assessment.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CleanupService {
    private static final Logger log = LoggerFactory.getLogger(CleanupService.class);

    @Autowired
    private EntityManager entityManager;

    // Self-injection to ensure @Transactional on internal calls is applied via proxy
    @Autowired @Lazy
    private CleanupService self;

    public void deleteQuestionsInBatchesNewTx(List<Long> questionIds, int batchSize) {
        if (questionIds == null || questionIds.isEmpty()) return;
        if (batchSize <= 0) batchSize = 100;

        for (int start = 0; start < questionIds.size(); start += batchSize) {
            int end = Math.min(start + batchSize, questionIds.size());
            // Copy to avoid backing list view issues when nested calls occur
            List<Long> slice = new ArrayList<>(questionIds.subList(start, end));
            log.error("Scheduling deleteQuestionBatchNewTx for {} ids (range {}-{})", slice.size(), start, end - 1);
            self.deleteQuestionBatchNewTx(slice);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteQuestionBatchNewTx(List<Long> ids) {
        String txId = UUID.randomUUID().toString();
        long t0 = System.currentTimeMillis();
        MDC.put("txId", txId);
        try {
            if (ids == null || ids.isEmpty()) {
                log.error("[txId={}] deleteQuestionBatchNewTx called with empty ids, skipping.", txId);
                return;
            }
            int removed = 0;
            // Fetch managed Question entities and remove them to trigger JPA cascades to child entities
            List<Question> questions = entityManager.createQuery(
                            "select q from Question q where q.id in (:ids)", Question.class)
                    .setParameter("ids", ids)
                    .getResultList();
            for (Question q : questions) {
                entityManager.remove(q);
                removed++;
            }
            entityManager.flush();
            entityManager.clear();
            long t1 = System.currentTimeMillis();
            log.error("[txId={}] deleteQuestionBatchNewTx removed {} questions (cascade) in {} ms", txId, removed, (t1 - t0));
        } catch (RuntimeException ex) {
            long t1 = System.currentTimeMillis();
            log.error("[txId={}] deleteQuestionBatchNewTx failed in {} ms (idsCount={})", txId, (t1 - t0), (ids == null ? 0 : ids.size()), ex);
            throw ex;
        } finally {
            MDC.remove("txId");
        }
    }


    public void cleanUpDanglingQuestion(Long userId, long tenantId) {
        log.error("===>Cleaning up Dangling for userId {} and tenantId {}",
                userId, tenantId);
        long startTime = System.currentTimeMillis();
       /* TypedQuery<Long> qIdsQuery = entityManager.createQuery(
                "select distinct q.id from Question q " +
                        " left join SectionQuestions  sq ON sq.questionId=q.id " +
                        " left join DefaultOptions do ON do.question.id=q.id"+
                        " left join Options o ON o.id=do.options.id"+
                        " left join AiaDefaultResponse aia ON aia.optionId.id=o.id"+
                        " left join DrObjectiveResponse dro ON dro.defaultOption.id=do.id"+
                        " where q.createdByUser=:userId AND q.tenantId=:tenantId AND sq.id IS NULL AND aia.optionId IS NULL AND dro.defaultOption IS NULL",
                Long.class);*/

        TypedQuery<Long> qIdsQuery = entityManager.createQuery(
                "select q.id " +
                        "from Question q " +
                        "where q.createdByUser = :userId " +
                        "  and q.tenantId = :tenantId " +
                        "  and not exists ( " +
                        "      select 1 from SectionQuestions sq " +
                        "      where sq.questionId = q.id " +
                        "  ) " +
                        "  and not exists ( " +
                        "      select 1 from DefaultOptions d " +
                        "      join DrObjectiveResponse dro on dro.defaultOption.id = d.id " +
                        "      where d.question.id = q.id " +
                        "  ) " +
                        "  and not exists ( " +
                        "      select 1 from DefaultOptions d2 " +
                        "      join AiaDefaultResponse aia on aia.optionId.id = d2.options.id " +
                        "      where d2.question.id = q.id " +
                        "  )",
                Long.class
        );
        qIdsQuery.setParameter("userId", userId);
        qIdsQuery.setParameter("tenantId", tenantId);

        List<Long> questionIds = qIdsQuery.getResultList();
        System.out.println(questionIds.size());

        if (!questionIds.isEmpty()) {
            self.deleteQuestionsInBatchesNewTx(questionIds, 100);
        }
        long endTime = System.currentTimeMillis();
        log.error("===>Cleanup completed in {} ms", (endTime - startTime));
    }
}
