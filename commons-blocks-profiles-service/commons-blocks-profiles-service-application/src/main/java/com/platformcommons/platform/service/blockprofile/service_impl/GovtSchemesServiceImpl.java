package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.GovtSchemesService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j

public class GovtSchemesServiceImpl implements GovtSchemesService {

    private final GovtSchemesDTOAssembler assembler;
    private final GovtSchemesRepository repository;


    private final SchemeImplementationChallengeDTOAssembler assemblerSchemeImplementationChallenge;
    private final SchemeImplementationChallengeRepository repositorySchemeImplementationChallenge;


    private final CentralSchemeListDTOAssembler assemblerCentralSchemeList;
    private final CentralSchemeListRepository repositoryCentralSchemeList;


    public GovtSchemesServiceImpl(
        SchemeImplementationChallengeDTOAssembler assemblerSchemeImplementationChallenge,  SchemeImplementationChallengeRepository repositorySchemeImplementationChallenge,
        CentralSchemeListDTOAssembler assemblerCentralSchemeList,  CentralSchemeListRepository repositoryCentralSchemeList,
        GovtSchemesRepository repository, GovtSchemesDTOAssembler assembler) {
        this.assemblerSchemeImplementationChallenge = assemblerSchemeImplementationChallenge;
        this.repositorySchemeImplementationChallenge = repositorySchemeImplementationChallenge;
        this.assemblerCentralSchemeList = assemblerCentralSchemeList;
        this.repositoryCentralSchemeList = repositoryCentralSchemeList;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new GovtSchemes.
     *
     * @param GovtSchemes the GovtSchemes DTO to save
     * @return the saved GovtSchemes DTO
     */
    @Transactional
    @Override
    public GovtSchemesDTO save(GovtSchemesDTO GovtSchemes) {
        log.debug("Entry - save(GovtSchemes={})", GovtSchemes);
        GovtSchemes = preHookSave(GovtSchemes);
        GovtSchemes entity = assembler.fromDTO(GovtSchemes);
        GovtSchemesDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing GovtSchemes.
     *
     * @param GovtSchemes the GovtSchemes DTO to update
     * @return the updated GovtSchemes DTO
     * @throws NotFoundException if the GovtSchemes is not found
     */
    @Transactional
    @Override
    public GovtSchemesDTO update(GovtSchemesDTO GovtSchemes) {
        log.debug("Entry - update(GovtSchemes={})", GovtSchemes);
        GovtSchemes = preHookUpdate(GovtSchemes);
        GovtSchemes saved = repository.findById(GovtSchemes.getId()).orElseThrow(() -> new NotFoundException("GovtSchemes not found"));
        saved.update(assembler.fromDTO(GovtSchemes));
        saved = repository.save(saved);
        GovtSchemesDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of GovtSchemess.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing GovtSchemes DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<GovtSchemesDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<GovtSchemes> result = repository.findAll(PageRequest.of(page, size));
        Set<GovtSchemesDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<GovtSchemesDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a GovtSchemes by ID.
     *
     * @param id     the ID of the GovtSchemes to delete
     * @param reason the reason for deletion
     * @return the deleted GovtSchemes DTO
     * @throws NotFoundException if the GovtSchemes is not found
     */
    @Transactional
    @Override
    public GovtSchemesDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        GovtSchemes saved = repository.findById(id).orElseThrow(() -> new NotFoundException("GovtSchemes not found"));
        saved.deactivate(reason);
        repository.save(saved);
        GovtSchemesDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a GovtSchemes by ID.
     *
     * @param id the ID of the GovtSchemes to retrieve
     * @return the GovtSchemes DTO
     * @throws NotFoundException if the GovtSchemes is not found
     */
    @Transactional(readOnly = true)
    @Override
    public GovtSchemesDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        GovtSchemes saved = repository.findById(id).orElseThrow(() -> new NotFoundException("GovtSchemes not found"));
        GovtSchemesDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<GovtSchemesDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<GovtSchemes> savedData = repository.findAllById(ids);
        Set<GovtSchemesDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of schemeImplementationChallengeList to a GovtSchemes identified by their ID.
      *
      * @param id               The ID of the GovtSchemes to add hobbies to
      * @param schemeImplementationChallengeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSchemeImplementationChallengeToGovtSchemes(Long id, List<SchemeImplementationChallengeDTO> schemeImplementationChallengeList){
         GovtSchemes saved = repository.findById(id).orElseThrow(() -> new NotFoundException("GovtSchemes not found"));
         if(schemeImplementationChallengeList != null && !schemeImplementationChallengeList.isEmpty()) {

             Set<SchemeImplementationChallenge> toBeSavedList = schemeImplementationChallengeList.stream().map(it-> assemblerSchemeImplementationChallenge.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setGovtSchemes(saved));
             repositorySchemeImplementationChallenge.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of centralSchemeListList to a GovtSchemes identified by their ID.
      *
      * @param id               The ID of the GovtSchemes to add hobbies to
      * @param centralSchemeListList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addCentralSchemeListToGovtSchemes(Long id, List<CentralSchemeListDTO> centralSchemeListList){
         GovtSchemes saved = repository.findById(id).orElseThrow(() -> new NotFoundException("GovtSchemes not found"));
         if(centralSchemeListList != null && !centralSchemeListList.isEmpty()) {

             Set<CentralSchemeList> toBeSavedList = centralSchemeListList.stream().map(it-> assemblerCentralSchemeList.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setGovtSchemes(saved));
             repositoryCentralSchemeList.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected GovtSchemesDTO postHookSave(GovtSchemesDTO dto) {
        return dto;
    }

    protected GovtSchemesDTO preHookSave(GovtSchemesDTO dto) {
        return dto;
    }

    protected GovtSchemesDTO postHookUpdate(GovtSchemesDTO dto) {
        return dto;
    }

    protected GovtSchemesDTO preHookUpdate(GovtSchemesDTO GovtSchemesDTO) {
        return GovtSchemesDTO;
    }

    protected GovtSchemesDTO postHookDelete(GovtSchemesDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected GovtSchemesDTO postHookGetById(GovtSchemesDTO dto) {
        return dto;
    }

    protected PageDTO<GovtSchemesDTO> postHookGetAll(PageDTO<GovtSchemesDTO> result) {
        return result;
    }




}
