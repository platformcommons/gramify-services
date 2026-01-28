package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageElectricityInfrastructurService;


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

public class VillageElectricityInfrastructurServiceImpl implements VillageElectricityInfrastructurService {

    private final VillageElectricityInfrastructurDTOAssembler assembler;
    private final VillageElectricityInfrastructurRepository repository;


    private final PowerCutSeasonDTOAssembler assemblerPowerCutSeason;
    private final PowerCutSeasonRepository repositoryPowerCutSeason;


    private final RenewableInfraTypeDTOAssembler assemblerRenewableInfraType;
    private final RenewableInfraTypeRepository repositoryRenewableInfraType;


    public VillageElectricityInfrastructurServiceImpl(
        PowerCutSeasonDTOAssembler assemblerPowerCutSeason,  PowerCutSeasonRepository repositoryPowerCutSeason,
        RenewableInfraTypeDTOAssembler assemblerRenewableInfraType,  RenewableInfraTypeRepository repositoryRenewableInfraType,
        VillageElectricityInfrastructurRepository repository, VillageElectricityInfrastructurDTOAssembler assembler) {
        this.assemblerPowerCutSeason = assemblerPowerCutSeason;
        this.repositoryPowerCutSeason = repositoryPowerCutSeason;
        this.assemblerRenewableInfraType = assemblerRenewableInfraType;
        this.repositoryRenewableInfraType = repositoryRenewableInfraType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageElectricityInfrastructur.
     *
     * @param VillageElectricityInfrastructur the VillageElectricityInfrastructur DTO to save
     * @return the saved VillageElectricityInfrastructur DTO
     */
    @Transactional
    @Override
    public VillageElectricityInfrastructurDTO save(VillageElectricityInfrastructurDTO VillageElectricityInfrastructur) {
        log.debug("Entry - save(VillageElectricityInfrastructur={})", VillageElectricityInfrastructur);
        VillageElectricityInfrastructur = preHookSave(VillageElectricityInfrastructur);
        VillageElectricityInfrastructur entity = assembler.fromDTO(VillageElectricityInfrastructur);
        VillageElectricityInfrastructurDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageElectricityInfrastructur.
     *
     * @param VillageElectricityInfrastructur the VillageElectricityInfrastructur DTO to update
     * @return the updated VillageElectricityInfrastructur DTO
     * @throws NotFoundException if the VillageElectricityInfrastructur is not found
     */
    @Transactional
    @Override
    public VillageElectricityInfrastructurDTO update(VillageElectricityInfrastructurDTO VillageElectricityInfrastructur) {
        log.debug("Entry - update(VillageElectricityInfrastructur={})", VillageElectricityInfrastructur);
        VillageElectricityInfrastructur = preHookUpdate(VillageElectricityInfrastructur);
        VillageElectricityInfrastructur saved = repository.findById(VillageElectricityInfrastructur.getId()).orElseThrow(() -> new NotFoundException("VillageElectricityInfrastructur not found"));
        saved.update(assembler.fromDTO(VillageElectricityInfrastructur));
        saved = repository.save(saved);
        VillageElectricityInfrastructurDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageElectricityInfrastructurs.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageElectricityInfrastructur DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageElectricityInfrastructurDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageElectricityInfrastructur> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageElectricityInfrastructurDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageElectricityInfrastructurDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageElectricityInfrastructur by ID.
     *
     * @param id     the ID of the VillageElectricityInfrastructur to delete
     * @param reason the reason for deletion
     * @return the deleted VillageElectricityInfrastructur DTO
     * @throws NotFoundException if the VillageElectricityInfrastructur is not found
     */
    @Transactional
    @Override
    public VillageElectricityInfrastructurDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageElectricityInfrastructur saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageElectricityInfrastructur not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageElectricityInfrastructurDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageElectricityInfrastructur by ID.
     *
     * @param id the ID of the VillageElectricityInfrastructur to retrieve
     * @return the VillageElectricityInfrastructur DTO
     * @throws NotFoundException if the VillageElectricityInfrastructur is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageElectricityInfrastructurDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageElectricityInfrastructur saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageElectricityInfrastructur not found"));
        VillageElectricityInfrastructurDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageElectricityInfrastructurDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageElectricityInfrastructur> savedData = repository.findAllById(ids);
        Set<VillageElectricityInfrastructurDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of powerCutSeasonList to a VillageElectricityInfrastructur identified by their ID.
      *
      * @param id               The ID of the VillageElectricityInfrastructur to add hobbies to
      * @param powerCutSeasonList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addPowerCutSeasonToVillageElectricityInfrastructur(Long id, List<PowerCutSeasonDTO> powerCutSeasonList){
         VillageElectricityInfrastructur saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageElectricityInfrastructur not found"));
         if(powerCutSeasonList != null && !powerCutSeasonList.isEmpty()) {

             Set<PowerCutSeason> toBeSavedList = powerCutSeasonList.stream().map(it-> assemblerPowerCutSeason.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageElectricityInfrastructur(saved));
             repositoryPowerCutSeason.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of renewableInfraTypeList to a VillageElectricityInfrastructur identified by their ID.
      *
      * @param id               The ID of the VillageElectricityInfrastructur to add hobbies to
      * @param renewableInfraTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addRenewableInfraTypeToVillageElectricityInfrastructur(Long id, List<RenewableInfraTypeDTO> renewableInfraTypeList){
         VillageElectricityInfrastructur saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageElectricityInfrastructur not found"));
         if(renewableInfraTypeList != null && !renewableInfraTypeList.isEmpty()) {

             Set<RenewableInfraType> toBeSavedList = renewableInfraTypeList.stream().map(it-> assemblerRenewableInfraType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageElectricityInfrastructur(saved));
             repositoryRenewableInfraType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageElectricityInfrastructurDTO postHookSave(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected VillageElectricityInfrastructurDTO preHookSave(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected VillageElectricityInfrastructurDTO postHookUpdate(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected VillageElectricityInfrastructurDTO preHookUpdate(VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO) {
        return VillageElectricityInfrastructurDTO;
    }

    protected VillageElectricityInfrastructurDTO postHookDelete(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageElectricityInfrastructurDTO postHookGetById(VillageElectricityInfrastructurDTO dto) {
        return dto;
    }

    protected PageDTO<VillageElectricityInfrastructurDTO> postHookGetAll(PageDTO<VillageElectricityInfrastructurDTO> result) {
        return result;
    }




}
