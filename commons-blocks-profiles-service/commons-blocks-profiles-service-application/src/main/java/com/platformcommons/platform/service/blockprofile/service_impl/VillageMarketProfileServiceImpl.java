package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageMarketProfileService;


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

public class VillageMarketProfileServiceImpl implements VillageMarketProfileService {

    private final VillageMarketProfileDTOAssembler assembler;
    private final VillageMarketProfileRepository repository;


    private final OperatingDayDTOAssembler assemblerOperatingDay;
    private final OperatingDayRepository repositoryOperatingDay;


    public VillageMarketProfileServiceImpl(
        OperatingDayDTOAssembler assemblerOperatingDay,  OperatingDayRepository repositoryOperatingDay,
        VillageMarketProfileRepository repository, VillageMarketProfileDTOAssembler assembler) {
        this.assemblerOperatingDay = assemblerOperatingDay;
        this.repositoryOperatingDay = repositoryOperatingDay;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageMarketProfile.
     *
     * @param VillageMarketProfile the VillageMarketProfile DTO to save
     * @return the saved VillageMarketProfile DTO
     */
    @Transactional
    @Override
    public VillageMarketProfileDTO save(VillageMarketProfileDTO VillageMarketProfile) {
        log.debug("Entry - save(VillageMarketProfile={})", VillageMarketProfile);
        VillageMarketProfile = preHookSave(VillageMarketProfile);
        VillageMarketProfile entity = assembler.fromDTO(VillageMarketProfile);
        VillageMarketProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageMarketProfile.
     *
     * @param VillageMarketProfile the VillageMarketProfile DTO to update
     * @return the updated VillageMarketProfile DTO
     * @throws NotFoundException if the VillageMarketProfile is not found
     */
    @Transactional
    @Override
    public VillageMarketProfileDTO update(VillageMarketProfileDTO VillageMarketProfile) {
        log.debug("Entry - update(VillageMarketProfile={})", VillageMarketProfile);
        VillageMarketProfile = preHookUpdate(VillageMarketProfile);
        VillageMarketProfile saved = repository.findById(VillageMarketProfile.getId()).orElseThrow(() -> new NotFoundException("VillageMarketProfile not found"));
        saved.update(assembler.fromDTO(VillageMarketProfile));
        saved = repository.save(saved);
        VillageMarketProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageMarketProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageMarketProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageMarketProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageMarketProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageMarketProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageMarketProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageMarketProfile by ID.
     *
     * @param id     the ID of the VillageMarketProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageMarketProfile DTO
     * @throws NotFoundException if the VillageMarketProfile is not found
     */
    @Transactional
    @Override
    public VillageMarketProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageMarketProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageMarketProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageMarketProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageMarketProfile by ID.
     *
     * @param id the ID of the VillageMarketProfile to retrieve
     * @return the VillageMarketProfile DTO
     * @throws NotFoundException if the VillageMarketProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageMarketProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageMarketProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageMarketProfile not found"));
        VillageMarketProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageMarketProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageMarketProfile> savedData = repository.findAllById(ids);
        Set<VillageMarketProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of operatingDayList to a VillageMarketProfile identified by their ID.
      *
      * @param id               The ID of the VillageMarketProfile to add hobbies to
      * @param operatingDayList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addOperatingDayToVillageMarketProfile(Long id, List<OperatingDayDTO> operatingDayList){
         VillageMarketProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageMarketProfile not found"));
         if(operatingDayList != null && !operatingDayList.isEmpty()) {

             Set<OperatingDay> toBeSavedList = operatingDayList.stream().map(it-> assemblerOperatingDay.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageMarketProfile(saved));
             repositoryOperatingDay.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageMarketProfileDTO postHookSave(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected VillageMarketProfileDTO preHookSave(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected VillageMarketProfileDTO postHookUpdate(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected VillageMarketProfileDTO preHookUpdate(VillageMarketProfileDTO VillageMarketProfileDTO) {
        return VillageMarketProfileDTO;
    }

    protected VillageMarketProfileDTO postHookDelete(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageMarketProfileDTO postHookGetById(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageMarketProfileDTO> postHookGetAll(PageDTO<VillageMarketProfileDTO> result) {
        return result;
    }




}
