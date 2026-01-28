package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageInfrastructureConstraintService;


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

public class VillageInfrastructureConstraintServiceImpl implements VillageInfrastructureConstraintService {

    private final VillageInfrastructureConstraintDTOAssembler assembler;
    private final VillageInfrastructureConstraintRepository repository;


    private final VillageTopInfrastructureNeedDTOAssembler assemblerVillageTopInfrastructureNeed;
    private final VillageTopInfrastructureNeedRepository repositoryVillageTopInfrastructureNeed;


    private final VillageTransportConnectivityIssDTOAssembler assemblerVillageTransportConnectivityIss;
    private final VillageTransportConnectivityIssRepository repositoryVillageTransportConnectivityIss;


    private final VillageWaterSupplyGapDTOAssembler assemblerVillageWaterSupplyGap;
    private final VillageWaterSupplyGapRepository repositoryVillageWaterSupplyGap;


    public VillageInfrastructureConstraintServiceImpl(
        VillageTopInfrastructureNeedDTOAssembler assemblerVillageTopInfrastructureNeed,  VillageTopInfrastructureNeedRepository repositoryVillageTopInfrastructureNeed,
        VillageTransportConnectivityIssDTOAssembler assemblerVillageTransportConnectivityIss,  VillageTransportConnectivityIssRepository repositoryVillageTransportConnectivityIss,
        VillageWaterSupplyGapDTOAssembler assemblerVillageWaterSupplyGap,  VillageWaterSupplyGapRepository repositoryVillageWaterSupplyGap,
        VillageInfrastructureConstraintRepository repository, VillageInfrastructureConstraintDTOAssembler assembler) {
        this.assemblerVillageTopInfrastructureNeed = assemblerVillageTopInfrastructureNeed;
        this.repositoryVillageTopInfrastructureNeed = repositoryVillageTopInfrastructureNeed;
        this.assemblerVillageTransportConnectivityIss = assemblerVillageTransportConnectivityIss;
        this.repositoryVillageTransportConnectivityIss = repositoryVillageTransportConnectivityIss;
        this.assemblerVillageWaterSupplyGap = assemblerVillageWaterSupplyGap;
        this.repositoryVillageWaterSupplyGap = repositoryVillageWaterSupplyGap;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageInfrastructureConstraint.
     *
     * @param VillageInfrastructureConstraint the VillageInfrastructureConstraint DTO to save
     * @return the saved VillageInfrastructureConstraint DTO
     */
    @Transactional
    @Override
    public VillageInfrastructureConstraintDTO save(VillageInfrastructureConstraintDTO VillageInfrastructureConstraint) {
        log.debug("Entry - save(VillageInfrastructureConstraint={})", VillageInfrastructureConstraint);
        VillageInfrastructureConstraint = preHookSave(VillageInfrastructureConstraint);
        VillageInfrastructureConstraint entity = assembler.fromDTO(VillageInfrastructureConstraint);
        VillageInfrastructureConstraintDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageInfrastructureConstraint.
     *
     * @param VillageInfrastructureConstraint the VillageInfrastructureConstraint DTO to update
     * @return the updated VillageInfrastructureConstraint DTO
     * @throws NotFoundException if the VillageInfrastructureConstraint is not found
     */
    @Transactional
    @Override
    public VillageInfrastructureConstraintDTO update(VillageInfrastructureConstraintDTO VillageInfrastructureConstraint) {
        log.debug("Entry - update(VillageInfrastructureConstraint={})", VillageInfrastructureConstraint);
        VillageInfrastructureConstraint = preHookUpdate(VillageInfrastructureConstraint);
        VillageInfrastructureConstraint saved = repository.findById(VillageInfrastructureConstraint.getId()).orElseThrow(() -> new NotFoundException("VillageInfrastructureConstraint not found"));
        saved.update(assembler.fromDTO(VillageInfrastructureConstraint));
        saved = repository.save(saved);
        VillageInfrastructureConstraintDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageInfrastructureConstraints.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageInfrastructureConstraint DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageInfrastructureConstraintDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageInfrastructureConstraint> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageInfrastructureConstraintDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageInfrastructureConstraintDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageInfrastructureConstraint by ID.
     *
     * @param id     the ID of the VillageInfrastructureConstraint to delete
     * @param reason the reason for deletion
     * @return the deleted VillageInfrastructureConstraint DTO
     * @throws NotFoundException if the VillageInfrastructureConstraint is not found
     */
    @Transactional
    @Override
    public VillageInfrastructureConstraintDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageInfrastructureConstraint saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInfrastructureConstraint not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageInfrastructureConstraintDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageInfrastructureConstraint by ID.
     *
     * @param id the ID of the VillageInfrastructureConstraint to retrieve
     * @return the VillageInfrastructureConstraint DTO
     * @throws NotFoundException if the VillageInfrastructureConstraint is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageInfrastructureConstraintDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageInfrastructureConstraint saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInfrastructureConstraint not found"));
        VillageInfrastructureConstraintDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageInfrastructureConstraintDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageInfrastructureConstraint> savedData = repository.findAllById(ids);
        Set<VillageInfrastructureConstraintDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of villageTopInfrastructureNeedList to a VillageInfrastructureConstraint identified by their ID.
      *
      * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
      * @param villageTopInfrastructureNeedList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageTopInfrastructureNeedToVillageInfrastructureConstraint(Long id, List<VillageTopInfrastructureNeedDTO> villageTopInfrastructureNeedList){
         VillageInfrastructureConstraint saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInfrastructureConstraint not found"));
         if(villageTopInfrastructureNeedList != null && !villageTopInfrastructureNeedList.isEmpty()) {

             Set<VillageTopInfrastructureNeed> toBeSavedList = villageTopInfrastructureNeedList.stream().map(it-> assemblerVillageTopInfrastructureNeed.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageInfrastructureConstraint(saved));
             repositoryVillageTopInfrastructureNeed.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of villageTransportConnectivityIssList to a VillageInfrastructureConstraint identified by their ID.
      *
      * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
      * @param villageTransportConnectivityIssList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageTransportConnectivityIssToVillageInfrastructureConstraint(Long id, List<VillageTransportConnectivityIssDTO> villageTransportConnectivityIssList){
         VillageInfrastructureConstraint saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInfrastructureConstraint not found"));
         if(villageTransportConnectivityIssList != null && !villageTransportConnectivityIssList.isEmpty()) {

             Set<VillageTransportConnectivityIss> toBeSavedList = villageTransportConnectivityIssList.stream().map(it-> assemblerVillageTransportConnectivityIss.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageInfrastructureConstraint(saved));
             repositoryVillageTransportConnectivityIss.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of villageWaterSupplyGapList to a VillageInfrastructureConstraint identified by their ID.
      *
      * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
      * @param villageWaterSupplyGapList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageWaterSupplyGapToVillageInfrastructureConstraint(Long id, List<VillageWaterSupplyGapDTO> villageWaterSupplyGapList){
         VillageInfrastructureConstraint saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInfrastructureConstraint not found"));
         if(villageWaterSupplyGapList != null && !villageWaterSupplyGapList.isEmpty()) {

             Set<VillageWaterSupplyGap> toBeSavedList = villageWaterSupplyGapList.stream().map(it-> assemblerVillageWaterSupplyGap.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageInfrastructureConstraint(saved));
             repositoryVillageWaterSupplyGap.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageInfrastructureConstraintDTO postHookSave(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected VillageInfrastructureConstraintDTO preHookSave(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected VillageInfrastructureConstraintDTO postHookUpdate(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected VillageInfrastructureConstraintDTO preHookUpdate(VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO) {
        return VillageInfrastructureConstraintDTO;
    }

    protected VillageInfrastructureConstraintDTO postHookDelete(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageInfrastructureConstraintDTO postHookGetById(VillageInfrastructureConstraintDTO dto) {
        return dto;
    }

    protected PageDTO<VillageInfrastructureConstraintDTO> postHookGetAll(PageDTO<VillageInfrastructureConstraintDTO> result) {
        return result;
    }




}
