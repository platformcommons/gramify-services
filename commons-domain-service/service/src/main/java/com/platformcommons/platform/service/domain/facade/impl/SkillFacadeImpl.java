package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.application.SkillHierarchyService;
import com.platformcommons.platform.service.domain.application.SkillService;
import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.domain.SkillHierarchy;
import com.platformcommons.platform.service.domain.domain.vo.SkillVO;
import com.platformcommons.platform.service.domain.dto.*;
import com.platformcommons.platform.service.domain.exception.DetectedLoopException;
import com.platformcommons.platform.service.domain.facade.SkillFacade;
import com.platformcommons.platform.service.domain.facade.assembler.SkillDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.SkillEventMapper;
import com.platformcommons.platform.service.domain.facade.assembler.SkillVOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import com.platformcommons.platform.service.domain.messaging.producer.SkillEventProducer;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class SkillFacadeImpl implements SkillFacade {
    @Autowired
    private SkillHierarchyService skillHierarchyService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private SkillDTOAssembler skillDTOAssembler;
    @Autowired
    private SkillEventProducer skillEventProducer;
    @Autowired
    private SkillEventMapper skillEventMapper;
    @Autowired
    private TagDTOAssembler tagDTOAssembler;
    @Autowired
    private SkillVOAssembler skillVOAssembler;
    @Override
    public Long createSkill(SkillDTO skillDTO, String parentSkillCode) {

        Skill skill=skillService.save(skillDTOAssembler.fromDTO(skillDTO));
        Long skillID = skill.getId();
        Long parentSkillId  =null;
        if(parentSkillCode!=null){
            Skill parentSkill = skillService.getSkillByCode(parentSkillCode,skillDTO.getContext());
            parentSkillId = parentSkill.getId();
            skillHierarchyService.createHierarchy(skillID,parentSkillId);
        }
        skillEventProducer.skillCreated(skillEventMapper.from(skill));
        if(parentSkillId!=null){
            skillEventProducer.skillHierarchyCreated(SkillHierarchyEventDTO.builder().parentId(parentSkillId).childId(skillID).build());
        }
        return skillID;
    }

    @Override
    public SkillDTO update(SkillDTO skillDTO) {

        Skill toBeUpdated =skillDTOAssembler.fromDTO(skillDTO);
        Skill skill=skillService.update(toBeUpdated);
        skillEventProducer.skillUpdated(skillEventMapper.from(skill));
        return skillDTOAssembler.toDTO(skill);

    }

    @Override
    public void createSkillBatch(List<SkillDTO> skillDTOs, String parentSkillCode) {
        for (SkillDTO skillDTO : skillDTOs) {
            createSkill(skillDTO,parentSkillCode);
        }
    }

    @Override
    public void addBatchSkillHierarchyBatch(List<Long> childSkillIds, Long parentSkillId) {
        childSkillIds.forEach(it->{
            skillHierarchyService.createHierarchy(it,parentSkillId);
            skillEventProducer.skillHierarchyCreated(SkillHierarchyEventDTO.builder().parentId(parentSkillId).childId(it).build());
        });
    }

    @Override
    public PageDTO<SkillDTO> getSkills(Integer page, Integer size, String context, Boolean isRoot) {
        Page<Skill> skills = skillService.getSkills(page,size,context,isRoot);
        return  new PageDTO<>(skills.stream().map(skillDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),skills.hasNext());
    }



    @Override
    public PageDTO<SkillDTO> getSubSkills(Integer page, Integer size, String sortBy, String direction, String parentSkillCode, Long depth, String context, String domainCode) {
        Page<Skill> skills = skillService.getSubSkills(page,size,sortBy,direction,parentSkillCode,depth,context,domainCode);
        return  new PageDTO<>(skills.stream().map(skillDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),skills.hasNext());
    }

    @Override
    public PageDTO<SkillDTO> getParentSkills(Integer page, Integer size, String context, String domainCode) {
        Page<Skill> skills = skillService.getSkillsByDomain(page,size,true,context,domainCode);
        return new PageDTO<>( skills.stream().map(skillDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),skills.hasNext());
    }

    @Override
    public Set<SkillVO> getSkillHierarchyThroughIds(Set<Long> id) {
        List<Skill> skills=skillService.getSkillHierarchyThroughIds(id);
        List<SkillHierarchy> skillHierarchies=skillHierarchyService.getChildSkill(id);

        MultiValueMap<Long,Skill> map = new LinkedMultiValueMap<>();

        for (SkillHierarchy skillHierarchy : skillHierarchies) {
            map.put(skillHierarchy.getParentSkill().getId(),map.getOrDefault(skillHierarchy.getParentSkill().getId(),new ArrayList<>()));
            map.get(skillHierarchy.getParentSkill().getId()).add(skillHierarchy.getSkill());
        }
        Set<SkillVO> skillVOS = skills.stream().map(skillVOAssembler::toVO).collect(Collectors.toSet());
        Set<Long> v = new HashSet<>();
        mapChildSkills(skillVOS,map,v);
        return skillVOS;

    }

    @Override
    public PageDTO<SkillDTO> searchParentSkills(Integer page, Integer size, String context, String domainCode, String text) {
        SearchResult<Skill> pages = skillService.searchParentSkills(page,size,context,domainCode,text);
        long totalElement = pages.total().hitCount();
        int totalPages =  (int)totalElement / size;
        totalPages= totalElement%size==0 ? totalPages-1:totalPages;
        return new PageDTO<>(pages.hits().stream().map(skillDTOAssembler::toDTO).collect(Collectors.toSet()),page<totalPages,totalElement);
    }

    @Override
    public PageDTO<SkillDTO> searchChildSkills(Integer page, Integer size, String parentSkill, String context, String domainCode, String text) {
        SearchResult<SkillHierarchy> pages = skillService.searchChildSkills(page,size,parentSkill,context,domainCode,text);
        long totalElement = pages.total().hitCount();
        int totalPages =  (int)totalElement / size;
        totalPages= totalElement%size==0 ? totalPages-1:totalPages;
        return new PageDTO<>(pages.hits().stream().map(skillHierarchy->skillDTOAssembler.toDTO(skillHierarchy.getSkill())).collect(Collectors.toSet()),page<totalPages,totalElement);
    }

    @Override
    public boolean checkSkillExist(String skillCode, String context) {
        return skillService.checkSkillExist(skillCode,context);
    }

    private void mapChildSkills(Set<SkillVO> skillVOS, MultiValueMap<Long, Skill> map,Set<Long> v) {

        if(skillVOS==null) return;

        for (SkillVO skillVO : skillVOS) {
            List<Skill> childSkills=map.get(skillVO.getId());
            if(childSkills!=null) {
                skillVO.setSubSkills(childSkills.stream().map(skillVOAssembler::toVO).collect(Collectors.toSet()));
                for (SkillVO subSkill : skillVO.getSubSkills()) {
                    if(v.contains(skillVO.getId())){
                        throw new DetectedLoopException("Loop Found in Hierarchy");
                    }
                    v.add(subSkill.getId());
                }
                mapChildSkills(skillVO.getSubSkills(),map,v);
            }
        }

    }

    @Override
    public PageDTO<SkillDTO> getSkillsByDomain(Integer page, Integer size, String domainCode) {
        Page<Skill> skills = skillService.getSkillsByDomain(page,size,null,null, domainCode);
        return new PageDTO<>( skills.stream().map(skillDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),skills.hasNext());
    }

}
