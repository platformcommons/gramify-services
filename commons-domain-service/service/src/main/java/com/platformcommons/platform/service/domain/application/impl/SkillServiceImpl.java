package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.application.SkillService;
import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.domain.SkillHierarchy;
import com.platformcommons.platform.service.domain.domain.repo.SkillHierarchyRepository;
import com.platformcommons.platform.service.domain.domain.repo.SkillRepository;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService{

    @Autowired
    private SkillRepository repository;
    @Autowired
    private SkillHierarchyRepository skillHierarchyRepository;
    @Autowired
    private EntityManager entityManager;
    @Override
    public Skill save(Skill skill) {
        skill=repository.save(skill);
        return skill;
    }

    @Override
    public Skill getSkillByCode(String skillCode, String context) {
        return repository.findBySkillCodeAndContext(skillCode,context).orElseThrow(()->new NotFoundException("Skill not found"));
    }

    @Override
    public Skill update(Skill skill) {
        Skill fetchedSkill = repository.findById(skill.getId())
                .orElseThrow(()-> new NotFoundException(String.format("Request Skill with  %d  not found",skill.getId())));

        fetchedSkill.update(skill);
        return repository.save(fetchedSkill);
    }

    @Override
    public List<Skill> createBatch(Collection<Skill> collect) {
        return repository.saveAll(collect);
    }

    @Override
    public Page<Skill> getSkills(Integer page, Integer size, String context, Boolean isRoot) {
        return repository.getSkills(context,isRoot, PageRequest.of(page,size));
    }

    @Override
    public Page<Skill> getSubSkills(Integer page, Integer size, String sortBy, String direction, String parentSkillCode, Long depth, String context, String domainCode) {
        return skillHierarchyRepository.findSubDomains(parentSkillCode,depth,context,domainCode,PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
    }

    @Override
    public Page<Skill> getSkillsByDomain(Integer page, Integer size, Boolean isRoot, String context, String domainCode) {
        return repository.findByDomainCode(domainCode,isRoot,context,PageRequest.of(page,size));
    }

    @Override
    public List<Skill> getSkillHierarchyThroughIds(Set<Long> id) {
        return repository.getParentSkills(id);
    }

    @Override
    public SearchResult<Skill> searchParentSkills(Integer page, Integer size, String context, String domainCode, String text) {

        SearchSession searchSession = Search.session(entityManager);

        SearchScope<Skill> searchScope = searchSession.scope(Skill.class);
        BooleanPredicateClausesStep<?> booleanPredicate = searchScope.predicate().bool();

        booleanPredicate = booleanPredicate.must(searchScope.predicate()
                .match()
                .field("isRoot")
                .matching(Boolean.TRUE)
                .toPredicate());
        if(context!=null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("context")
                    .matching(context)
                    .toPredicate());
        }
        if(domainCode!=null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("domainCode")
                    .matching(domainCode)
                    .toPredicate());
        }
        if(text != null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("name.text")
                    .matching("*"+text+"*"));



        }

        return searchSession.search(Skill.class)
                                                  .where(booleanPredicate.toPredicate())
                                                  .fetch(page*size,size);
    }

    @Override
    public SearchResult<SkillHierarchy> searchChildSkills(Integer page, Integer size, String parentSkill, String context, String domainCode, String text) {


        SearchSession searchSession = Search.session(entityManager);

        SearchScope<SkillHierarchy> searchScope = searchSession.scope(SkillHierarchy.class);
        BooleanPredicateClausesStep<?> booleanPredicate = searchScope.predicate().bool();

        booleanPredicate = booleanPredicate.must(searchScope.predicate()
                .match()
                .field("parentSkill.skillCode")
                .matching(parentSkill)
                .toPredicate());

        if(context!=null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("skill.context")
                    .matching(context)
                    .toPredicate());
        }
        if(domainCode!=null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("skill.domainCode")
                    .matching(domainCode)
                    .toPredicate());
        }
        if(text != null) {
            booleanPredicate = booleanPredicate.must(searchScope.predicate()
                    .match()
                    .field("skill.name.text")
                    .matching("*"+text+"*"));
        }

        return searchSession.search(SkillHierarchy.class)
                .where(booleanPredicate.toPredicate())
                .fetch(page*size,size);

    }

    @Override
    public boolean checkSkillExist(String skillCode, String context) {
        return repository.existsBySkillCodeAndContextAndTenantId(skillCode,context, PlatformSecurityUtil.getCurrentTenantId());
    }

}
