package com.platformcommons.platform.service.assessment.application.search.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceService;
import com.platformcommons.platform.service.assessment.application.constant.ServiceConstants;
import com.platformcommons.platform.service.assessment.application.search.SearchService;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.assessment.domain.QuestionPaperSection;
import com.platformcommons.platform.service.assessment.domain.repo.QuestionNonMTRepository;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceDetailVoAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.QuestionDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {


    private EntityManager entityManager;
    private final QuestionNonMTRepository questionNonMTRepository;
    private final QuestionDTOAssembler questionDTOAssembler;
    private AssessmentInstanceService instanceService;
    private final AssessmentInstanceDetailVoAssembler assessmentInstanceDetailVoAssembler;

    @Override
    public PageDTO<AssessmentInstanceDetailVO> searchByAssessmentName(String formName, String domain, String subDomain, String language, Integer page, Integer size, String sortBy, String sortOrder, String assessmentType, String context) {
        disableFilter();
        SearchSession searchSession = Search.session(entityManager);
        SearchScope<AssessmentInstance> searchScope = searchSession.scope(AssessmentInstance.class);
        BooleanPredicateClausesStep<?> boolPredicate = searchScope.predicate().bool();

        if(domain != null) {
            boolPredicate = boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("assessment.domain")
                    .matching(domain));
        }
        if(subDomain != null){
            boolPredicate = boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("assessment.subDomain")
                    .matching(subDomain));
        }
        boolPredicate = boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("scheduleStatus.code")
                    .matching(ServiceConstants.ASSESSMENT_RELEASE));
        if(context!=null){
            boolPredicate =  boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("assessment.context")
                    .matching(context));
        }
        if(language != null){
            boolPredicate = boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("assessment.assessmentName.languageCode")
                    .matching(language));
        }
        if(assessmentType != null){
            boolPredicate = boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("assessment.assessmentType.code")
                    .matching(assessmentType));
        }

        if (formName != null) {
            boolPredicate = boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("assessment.assessmentName.text")
                    .matching("*"+formName+"*"));
        }
        SearchResult<AssessmentInstance> result;
        if (sortBy != null ) {
            result = searchSession.search(AssessmentInstance.class )
                    .where(boolPredicate.toPredicate())
                    .sort(f-> f.field(sortBy).order(getHBSearchSortOrder(sortOrder)))
                    .fetch(page*size,size);
        }
        else{
            result = searchSession.search(AssessmentInstance.class )
                    .where(boolPredicate.toPredicate())
                    .sort(f-> f.field("createdTimestamp").desc())
                    .fetch(page*size,size);
        }
        long totalElement = result.total().hitCount();

        HashSet<AssessmentInstance> resultSet =new HashSet<>(result.hits());
        Map<Long,String> names = instanceService.getAssessmentInstanceCreatedByName(resultSet.stream().map( el -> el.getCreatedByUser().toString() ).collect(Collectors.toSet()));

        PageDTO<AssessmentInstanceDetailVO> x  = new PageDTO<>(
                resultSet.stream()
                        .map(instance->assessmentInstanceDetailVoAssembler.toAssessmentDetailVo(instance,language,names.get(instance.getCreatedByUser())))
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                ((long) page *size)<totalElement,
                totalElement);
        enableFilter();
        return x;
    }

    @Override
    public PageDTO<AssessmentInstanceDetailVO> searchByAssessmentName(String language, Integer page, Integer size, String sortBy, String sortOrder, String text, List<Long> resultSet) {
        if(resultSet.isEmpty()){
            return new PageDTO<>(new HashSet<>() ,false,0);
        }

        enableFilter();
        SearchSession searchSession = Search.session(entityManager);
        SearchScope<AssessmentInstance> searchScope = searchSession.scope(AssessmentInstance.class);

        BooleanPredicateClausesStep<?> boolPredicate = searchScope.predicate().bool();


        boolPredicate = boolPredicate.filter(searchScope.predicate()
                .terms()
                .field("id")
                .matchingAny(resultSet));
        if (text != null) {
            boolPredicate = boolPredicate.must(searchScope.predicate()
                    .match()
                    .field("assessment.assessmentName.text")
                    .matching("*"+text+"*"));
        }
        org.hibernate.search.engine.search.query.SearchResult<AssessmentInstance> result;

        if (sortBy != null ) {
            result = searchSession.search(AssessmentInstance.class )
                    .where(boolPredicate.toPredicate())
                    .sort(f-> f.field(sortBy).order(getHBSearchSortOrder(sortOrder)))
                    .fetch(page*size,size);
        }
        else{
            result = searchSession.search(AssessmentInstance.class )
                    .where(boolPredicate.toPredicate())
                    .sort(f-> f.field("createdTimestamp").desc())
                    .fetch(page*size,size);
        }

        long count = result.total().hitCount();
        int totalPages = (int) Math.ceil((double) count /size);

        Set<AssessmentInstanceDetailVO> assessmentInstanceDetailVO = result.hits().stream().map(instance -> {


                    AtomicReference<String> lang = new AtomicReference<>(language);
                    String name = null;
                    if (language != null) {
                        name = instance.getAsmtInstName()
                                .stream()
                                .filter(mlText -> mlText.getLanguageCode().equals(lang.get()))
                                .findAny()
                                .orElse(MLText.builder().build())
                                .getText();
                    }
                    if (name == null) {
                        lang.set(instance.getAssessment().getBaseLanguage() == null ? "ENG" : instance.getAssessment().getBaseLanguage());
                        name = instance.getAsmtInstName()
                                .stream()
                                .filter(mlText -> mlText.getLanguageCode().equals(lang.get()))
                                .findAny()
                                .orElse(MLText.builder().build()).getText();
                    }

                    return AssessmentInstanceDetailVO.builder()
                            .assessmentInstanceId(instance.getId())
                            .assessmentName(name)
                            .assessmentImage(instance.getImageURL())
                            .author(instance.getCreatedByUser().toString())
                            .domainCode(instance.getAssessment().getDomain())
                            .subDomainCode(instance.getAssessment().getSubDomain())
                            .noOfQuestion(instance.getAssessment()
                                    .getAssessmentQuestionPaperList().stream()
                                    .map(AssessmentQuestionPaper::getQuestionpapersectionList).flatMap(Collection::stream)
                                    .map(QuestionPaperSection::getSectionquestionsList).mapToLong(Collection::size)
                                    .sum())
                            .editable(Objects.equals(PlatformSecurityUtil.getCurrentUserId(), instance.getCreatedByUser()))
                            .build();
                }
        ).collect(Collectors.toCollection(LinkedHashSet::new));

        disableFilter();
        return new PageDTO<>(assessmentInstanceDetailVO ,page<totalPages, count);
    }

    @Override
    public PageDTO<QuestionDTO> searchByQuestionByQuestionText(String domain, String subDomain, String questionText, String questionType, String questionSubType, String sortBy, String sortOrder, String language, Integer page, Integer size) {

        disableFilter();
        SearchSession searchSession = Search.session(entityManager);
        SearchScope<Question> searchScope = searchSession.scope(Question.class);
        BooleanPredicateClausesStep<?> booleanPredicate = searchScope.predicate().bool();

        final Long currentTenantId = PlatformSecurityUtil.getCurrentTenantId();

        booleanPredicate = booleanPredicate.must(searchScope.predicate()
                .match()
                .field("tenantId")
                .matching(currentTenantId)
                .toPredicate());
        booleanPredicate = booleanPredicate.must(searchScope.predicate()
                .match()
                .field("questionText.languageCode")
                .matching(language==null?"ENG":language)
                .toPredicate());

        booleanPredicate = booleanPredicate.filter(searchScope.predicate()
                .terms()
                .field("id")
                .matchingAny(
                        questionNonMTRepository.getProjectionQuestion()
                ));

        if(domain != null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("domain")
                    .matching(domain)
                    .toPredicate());
        }
        if(subDomain != null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("subDomain")
                    .matching(subDomain)
                    .toPredicate());
        }
        if(questionText != null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("questionText.text")
                    .matching("*"+questionText+"*"));



        }
        if(questionType != null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("questionType.code")
                    .matching(questionType)
                    .toPredicate());
        }
        if(questionSubType != null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("questionSubtype.code")
                    .matching(questionSubType)
                    .toPredicate());
        }


        SearchResult<Question> result;
        if (sortBy != null ) {
            result = searchSession
                    .search(Question.class)
                    .where(booleanPredicate.toPredicate())
                    .sort(f-> f.field(sortBy).order(getHBSearchSortOrder(sortOrder)))
                    .fetch(page*size,size);
        }
        else{
            result = searchSession
                    .search(Question.class)
                    .where(booleanPredicate.toPredicate())
                    .sort(f-> f.field("createdTimestamp").desc())
                    .fetch(page*size,size);
        }


        long totalElement = result.total().hitCount();
        int totalPages =  (int)totalElement / size;
        totalPages= totalElement%size==0 ? totalPages-1:totalPages;
        PageDTO<QuestionDTO> questions = new PageDTO<>(result.hits().stream().map(questionDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)), page<totalPages,totalElement);
        enableFilter();
        //linked hash set to maintain the order
        return questions;

    }

    public void disableFilter(){
        Session session = this.entityManager.unwrap(Session.class);
        session.disableFilter("tenantFilter");
    }
    public void enableFilter(){
        Session session = this.entityManager.unwrap(Session.class);
        session.enableFilter("tenantFilter").setParameter("tenantId",PlatformSecurityUtil.getCurrentTenantId());
    }
    private SortOrder getHBSearchSortOrder(String order){
        if(order!=null){
            return order.equalsIgnoreCase("ASC")?SortOrder.ASC:SortOrder.DESC;
        }
        return SortOrder.DESC;
    }
    @Autowired
    private void setEntityManager(EntityManager entityManager){
        this.entityManager=entityManager;
    }
    @Autowired
    private void setInstanceService(AssessmentInstanceService service){
        this.instanceService = service;
    }
}

