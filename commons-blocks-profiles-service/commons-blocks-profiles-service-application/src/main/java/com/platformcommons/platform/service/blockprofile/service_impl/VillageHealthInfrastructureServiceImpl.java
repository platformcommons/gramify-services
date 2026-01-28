package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageHealthInfrastructureService;


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

public class VillageHealthInfrastructureServiceImpl implements VillageHealthInfrastructureService {

    private final VillageHealthInfrastructureDTOAssembler assembler;
    private final VillageHealthInfrastructureRepository repository;


    private final CommonHealthIssueDTOAssembler assemblerCommonHealthIssue;
    private final CommonHealthIssueRepository repositoryCommonHealthIssue;


    public VillageHealthInfrastructureServiceImpl(
        CommonHealthIssueDTOAssembler assemblerCommonHealthIssue,  CommonHealthIssueRepository repositoryCommonHealthIssue,
        VillageHealthInfrastructureRepository repository, VillageHealthInfrastructureDTOAssembler assembler) {
        this.assemblerCommonHealthIssue = assemblerCommonHealthIssue;
        this.repositoryCommonHealthIssue = repositoryCommonHealthIssue;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageHealthInfrastructure.
     *
     * @param VillageHealthInfrastructure the VillageHealthInfrastructure DTO to save
     * @return the saved VillageHealthInfrastructure DTO
     */
    @Transactional
    @Override
    public VillageHealthInfrastructureDTO save(VillageHealthInfrastructureDTO VillageHealthInfrastructure) {
        log.debug("Entry - save(VillageHealthInfrastructure={})", VillageHealthInfrastructure);
        VillageHealthInfrastructure = preHookSave(VillageHealthInfrastructure);
        VillageHealthInfrastructure entity = assembler.fromDTO(VillageHealthInfrastructure);
        VillageHealthInfrastructureDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageHealthInfrastructure.
     *
     * @param VillageHealthInfrastructure the VillageHealthInfrastructure DTO to update
     * @return the updated VillageHealthInfrastructure DTO
     * @throws NotFoundException if the VillageHealthInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageHealthInfrastructureDTO update(VillageHealthInfrastructureDTO VillageHealthInfrastructure) {
        log.debug("Entry - update(VillageHealthInfrastructure={})", VillageHealthInfrastructure);
        VillageHealthInfrastructure = preHookUpdate(VillageHealthInfrastructure);
        VillageHealthInfrastructure saved = repository.findById(VillageHealthInfrastructure.getId()).orElseThrow(() -> new NotFoundException("VillageHealthInfrastructure not found"));
        saved.update(assembler.fromDTO(VillageHealthInfrastructure));
        saved = repository.save(saved);
        VillageHealthInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageHealthInfrastructures.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageHealthInfrastructure DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageHealthInfrastructureDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageHealthInfrastructure> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageHealthInfrastructureDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageHealthInfrastructureDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageHealthInfrastructure by ID.
     *
     * @param id     the ID of the VillageHealthInfrastructure to delete
     * @param reason the reason for deletion
     * @return the deleted VillageHealthInfrastructure DTO
     * @throws NotFoundException if the VillageHealthInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageHealthInfrastructureDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageHealthInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHealthInfrastructure not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageHealthInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageHealthInfrastructure by ID.
     *
     * @param id the ID of the VillageHealthInfrastructure to retrieve
     * @return the VillageHealthInfrastructure DTO
     * @throws NotFoundException if the VillageHealthInfrastructure is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageHealthInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageHealthInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHealthInfrastructure not found"));
        VillageHealthInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageHealthInfrastructureDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageHealthInfrastructure> savedData = repository.findAllById(ids);
        Set<VillageHealthInfrastructureDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of commonHealthIssueList to a VillageHealthInfrastructure identified by their ID.
      *
      * @param id               The ID of the VillageHealthInfrastructure to add hobbies to
      * @param commonHealthIssueList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addCommonHealthIssueToVillageHealthInfrastructure(Long id, List<CommonHealthIssueDTO> commonHealthIssueList){
         VillageHealthInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHealthInfrastructure not found"));
         if(commonHealthIssueList != null && !commonHealthIssueList.isEmpty()) {

             Set<CommonHealthIssue> toBeSavedList = commonHealthIssueList.stream().map(it-> assemblerCommonHealthIssue.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHealthInfrastructure(saved));
             repositoryCommonHealthIssue.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageHealthInfrastructureDTO postHookSave(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected VillageHealthInfrastructureDTO preHookSave(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected VillageHealthInfrastructureDTO postHookUpdate(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected VillageHealthInfrastructureDTO preHookUpdate(VillageHealthInfrastructureDTO VillageHealthInfrastructureDTO) {
        return VillageHealthInfrastructureDTO;
    }

    protected VillageHealthInfrastructureDTO postHookDelete(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHealthInfrastructureDTO postHookGetById(VillageHealthInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHealthInfrastructureDTO> postHookGetAll(PageDTO<VillageHealthInfrastructureDTO> result) {
        return result;
    }




}
