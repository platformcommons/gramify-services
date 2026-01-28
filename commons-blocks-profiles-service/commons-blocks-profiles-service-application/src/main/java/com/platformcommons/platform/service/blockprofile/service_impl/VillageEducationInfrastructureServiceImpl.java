package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageEducationInfrastructureService;


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

public class VillageEducationInfrastructureServiceImpl implements VillageEducationInfrastructureService {

    private final VillageEducationInfrastructureDTOAssembler assembler;
    private final VillageEducationInfrastructureRepository repository;


    private final IssuesInHigherEducationAccessDTOAssembler assemblerIssuesInHigherEducationAccess;
    private final IssuesInHigherEducationAccessRepository repositoryIssuesInHigherEducationAccess;


    public VillageEducationInfrastructureServiceImpl(
        IssuesInHigherEducationAccessDTOAssembler assemblerIssuesInHigherEducationAccess,  IssuesInHigherEducationAccessRepository repositoryIssuesInHigherEducationAccess,
        VillageEducationInfrastructureRepository repository, VillageEducationInfrastructureDTOAssembler assembler) {
        this.assemblerIssuesInHigherEducationAccess = assemblerIssuesInHigherEducationAccess;
        this.repositoryIssuesInHigherEducationAccess = repositoryIssuesInHigherEducationAccess;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageEducationInfrastructure.
     *
     * @param VillageEducationInfrastructure the VillageEducationInfrastructure DTO to save
     * @return the saved VillageEducationInfrastructure DTO
     */
    @Transactional
    @Override
    public VillageEducationInfrastructureDTO save(VillageEducationInfrastructureDTO VillageEducationInfrastructure) {
        log.debug("Entry - save(VillageEducationInfrastructure={})", VillageEducationInfrastructure);
        VillageEducationInfrastructure = preHookSave(VillageEducationInfrastructure);
        VillageEducationInfrastructure entity = assembler.fromDTO(VillageEducationInfrastructure);
        VillageEducationInfrastructureDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageEducationInfrastructure.
     *
     * @param VillageEducationInfrastructure the VillageEducationInfrastructure DTO to update
     * @return the updated VillageEducationInfrastructure DTO
     * @throws NotFoundException if the VillageEducationInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageEducationInfrastructureDTO update(VillageEducationInfrastructureDTO VillageEducationInfrastructure) {
        log.debug("Entry - update(VillageEducationInfrastructure={})", VillageEducationInfrastructure);
        VillageEducationInfrastructure = preHookUpdate(VillageEducationInfrastructure);
        VillageEducationInfrastructure saved = repository.findById(VillageEducationInfrastructure.getId()).orElseThrow(() -> new NotFoundException("VillageEducationInfrastructure not found"));
        saved.update(assembler.fromDTO(VillageEducationInfrastructure));
        saved = repository.save(saved);
        VillageEducationInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageEducationInfrastructures.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageEducationInfrastructure DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageEducationInfrastructureDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageEducationInfrastructure> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageEducationInfrastructureDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageEducationInfrastructureDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageEducationInfrastructure by ID.
     *
     * @param id     the ID of the VillageEducationInfrastructure to delete
     * @param reason the reason for deletion
     * @return the deleted VillageEducationInfrastructure DTO
     * @throws NotFoundException if the VillageEducationInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageEducationInfrastructureDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageEducationInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageEducationInfrastructure not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageEducationInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageEducationInfrastructure by ID.
     *
     * @param id the ID of the VillageEducationInfrastructure to retrieve
     * @return the VillageEducationInfrastructure DTO
     * @throws NotFoundException if the VillageEducationInfrastructure is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageEducationInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageEducationInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageEducationInfrastructure not found"));
        VillageEducationInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageEducationInfrastructureDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageEducationInfrastructure> savedData = repository.findAllById(ids);
        Set<VillageEducationInfrastructureDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of issuesInHigherEducationAccessList to a VillageEducationInfrastructure identified by their ID.
      *
      * @param id               The ID of the VillageEducationInfrastructure to add hobbies to
      * @param issuesInHigherEducationAccessList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addIssuesInHigherEducationAccessToVillageEducationInfrastructure(Long id, List<IssuesInHigherEducationAccessDTO> issuesInHigherEducationAccessList){
         VillageEducationInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageEducationInfrastructure not found"));
         if(issuesInHigherEducationAccessList != null && !issuesInHigherEducationAccessList.isEmpty()) {

             Set<IssuesInHigherEducationAccess> toBeSavedList = issuesInHigherEducationAccessList.stream().map(it-> assemblerIssuesInHigherEducationAccess.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageEducationInfrastructure(saved));
             repositoryIssuesInHigherEducationAccess.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageEducationInfrastructureDTO postHookSave(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageEducationInfrastructureDTO preHookSave(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageEducationInfrastructureDTO postHookUpdate(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageEducationInfrastructureDTO preHookUpdate(VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO) {
        return VillageEducationInfrastructureDTO;
    }

    protected VillageEducationInfrastructureDTO postHookDelete(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageEducationInfrastructureDTO postHookGetById(VillageEducationInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageEducationInfrastructureDTO> postHookGetAll(PageDTO<VillageEducationInfrastructureDTO> result) {
        return result;
    }




}
