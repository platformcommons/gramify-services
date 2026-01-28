package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.vo.SkillVO;
import com.platformcommons.platform.service.domain.dto.SkillDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.List;
import java.util.Set;

public interface SkillFacade {

    Long createSkill(SkillDTO skillDTO, String parentSkillCode);

    SkillDTO update(SkillDTO skillDTO);

    void createSkillBatch(List<SkillDTO> skillDTOs, String parentSkillCode);

    void addBatchSkillHierarchyBatch(List<Long> childSkillIds, Long parentSkillId);

    PageDTO<SkillDTO> getSkills(Integer page, Integer size, String context, Boolean isRoot);
    PageDTO<SkillDTO> getSkillsByDomain(Integer page, Integer size, String domainCode);

    PageDTO<SkillDTO> getSubSkills(Integer page, Integer size, String sortBy, String direction, String parentSkillCode, Long depth, String context, String domainCode);


    PageDTO<SkillDTO> getParentSkills(Integer page, Integer size, String contex, String domainCode);

    Set<SkillVO> getSkillHierarchyThroughIds(Set<Long> id);

    PageDTO<SkillDTO> searchParentSkills(Integer page, Integer size, String context, String domainCode, String text);

    PageDTO<SkillDTO> searchChildSkills(Integer page, Integer size, String parentSkill, String context, String domainCode, String text);

    boolean checkSkillExist(String skillCode, String context);
}
