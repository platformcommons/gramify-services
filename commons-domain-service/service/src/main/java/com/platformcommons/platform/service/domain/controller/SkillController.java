package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.domain.vo.SkillVO;
import com.platformcommons.platform.service.domain.dto.SkillDTO;
import com.platformcommons.platform.service.domain.facade.SkillFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "Skill")
@RequestMapping("api/v1/skills")
public class SkillController {

    @Autowired
    private SkillFacade facade;
    @ApiOperation(value = "createSkill", nickname = "createSkill", tags={ "Skill"})
    @PostMapping
    public ResponseEntity<Long> createSkill(@RequestBody SkillDTO skillDTO ,
                                            @RequestParam(value = "parentSkillCode",required = false) String parentSkillCode){
        return ResponseEntity.ok(facade.createSkill(skillDTO,parentSkillCode));
    }

    @ApiOperation(value = "updateSkill", nickname = "updateSkill", tags={ "Skill"})
    @PatchMapping
    public ResponseEntity<Void> updateSkill(@RequestBody SkillDTO skillDTO){
        facade.update(skillDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "createSkillBatch", nickname = "createSkill", tags={ "Skill"})
    @PostMapping("/batch")
    public ResponseEntity<Void> createSkillBatch(@RequestBody List<SkillDTO> skillDTOs, @RequestParam(value = "parentSkillCode",required = false) String parentSkillCode){
        facade.createSkillBatch(skillDTOs,parentSkillCode);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ApiOperation(value = "createSkillHierarchy", nickname = "createSkillHierarchy", tags={ "Skill"})
    @PatchMapping("/batch")
    public ResponseEntity<Void> addSkillHierarchyBatch(@RequestParam List<Long> childSkillIds,@RequestParam Long parentSkillId){
        facade.addBatchSkillHierarchyBatch(childSkillIds,parentSkillId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ApiOperation(value = "getSkills", nickname = "getSkills", tags={ "Skill"})
    @GetMapping
    public ResponseEntity<PageDTO<SkillDTO>> getSkills(@RequestParam Integer page,
                                                         @RequestParam Integer size,
                                                         @RequestParam String context,
                                                         @RequestParam(required = false) Boolean isRoot){

        PageDTO<SkillDTO> results = facade.getSkills(page,size,context,isRoot);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "getSkills", nickname = "getSkills", tags={ "Skill"})
    @GetMapping("/domain")
    public ResponseEntity<PageDTO<SkillDTO>> getSkills(@RequestParam Integer page,
                                                       @RequestParam Integer size,
                                                       @RequestParam String domainCode){

        PageDTO<SkillDTO> results = facade.getSkillsByDomain(page,size,domainCode);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "getParentSkills", nickname = "getParentSkills", tags={ "Skill"})
    @GetMapping("/domain/parent-skills")
    public ResponseEntity<PageDTO<SkillDTO>> getParentSkills(@RequestParam Integer page,
                                                       @RequestParam Integer size,
                                                       @RequestParam String context,
                                                       @RequestParam String domainCode){

        PageDTO<SkillDTO> results = facade.getParentSkills(page,size,context,domainCode);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }






    @ApiOperation(value = "getSubSkills", nickname = "getSubSkills", tags={ "Skill"})
    @GetMapping("/domain/sub-skills")
    public ResponseEntity<PageDTO<SkillDTO>> getSubSkills(@RequestParam Integer page,
                                                            @RequestParam Integer size,
                                                            @RequestParam String context,
                                                            @RequestParam(required = false)  String domainCode,
                                                            @RequestParam(required = false) String sortBy,
                                                            @RequestParam(required = false) String direction,
                                                            @RequestParam(required = false) String parentSkillCode,
                                                            @RequestParam(required = false) Long depth
    ){
        PageDTO<SkillDTO> results = facade.getSubSkills(page,size,sortBy,direction,parentSkillCode,depth,context,domainCode);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }
    @ApiOperation(value = "getSkillThroughIds", nickname = "getSkillThroughIds", tags={ "Skill"})
    @GetMapping("/domain/skills/hierarchy")
    public ResponseEntity<Set<SkillVO>> getSkillThroughIds(@RequestParam Set<Long> id ) {
        return ResponseEntity.ok().body(facade.getSkillHierarchyThroughIds(id));
    }

    @ApiOperation(value = "searchParentSkills", nickname = "searchParentSkills", tags={ "Skill"})
    @GetMapping("/search/domain/parent/skills")
    public ResponseEntity<PageDTO<SkillDTO>> searchParentSkills(@RequestParam Integer page,
                                                             @RequestParam Integer size,
                                                             @RequestParam(required = false) String context,
                                                             @RequestParam(required = false) String domainCode,
                                                             @RequestParam(required = false) String text) {
        return ResponseEntity.ok().body(facade.searchParentSkills(page,size,context,domainCode,text));
    }

    @ApiOperation(value = "searchChildSkills", nickname = "searchChildSkills", tags={ "Skill"})
    @GetMapping("/search/domain/child/skills")
    public ResponseEntity<PageDTO<SkillDTO>> searchChildSkills(@RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                               @RequestParam String parentSkill,
                                                               @RequestParam(required = false) String context,
                                                               @RequestParam(required = false) String domainCode,
                                                               @RequestParam(required = false) String text) {
        return ResponseEntity.ok().body(facade.searchChildSkills(page,size,parentSkill,context,domainCode,text));
    }

    @ApiOperation(value = "checkSkillExist", nickname = "checkSkillExist", tags={ "Skill"})
    @GetMapping("/exist/{skillCode}")
    public ResponseEntity<Boolean> checkSkillExist(@PathVariable String skillCode, @RequestParam(required = false) String context){
        return ResponseEntity.ok(facade.checkSkillExist(skillCode,context));
    }


}
