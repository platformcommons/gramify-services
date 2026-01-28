package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageEnvironmentalRiskProfileService;


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

public class VillageEnvironmentalRiskProfileServiceImpl implements VillageEnvironmentalRiskProfileService {

    private final VillageEnvironmentalRiskProfileDTOAssembler assembler;
    private final VillageEnvironmentalRiskProfileRepository repository;


    public VillageEnvironmentalRiskProfileServiceImpl(
        VillageEnvironmentalRiskProfileRepository repository, VillageEnvironmentalRiskProfileDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageEnvironmentalRiskProfile.
     *
     * @param VillageEnvironmentalRiskProfile the VillageEnvironmentalRiskProfile DTO to save
     * @return the saved VillageEnvironmentalRiskProfile DTO
     */
    @Transactional
    @Override
    public VillageEnvironmentalRiskProfileDTO save(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfile) {
        log.debug("Entry - save(VillageEnvironmentalRiskProfile={})", VillageEnvironmentalRiskProfile);
        VillageEnvironmentalRiskProfile = preHookSave(VillageEnvironmentalRiskProfile);
        VillageEnvironmentalRiskProfile entity = assembler.fromDTO(VillageEnvironmentalRiskProfile);
        VillageEnvironmentalRiskProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageEnvironmentalRiskProfile.
     *
     * @param VillageEnvironmentalRiskProfile the VillageEnvironmentalRiskProfile DTO to update
     * @return the updated VillageEnvironmentalRiskProfile DTO
     * @throws NotFoundException if the VillageEnvironmentalRiskProfile is not found
     */
    @Transactional
    @Override
    public VillageEnvironmentalRiskProfileDTO update(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfile) {
        log.debug("Entry - update(VillageEnvironmentalRiskProfile={})", VillageEnvironmentalRiskProfile);
        VillageEnvironmentalRiskProfile = preHookUpdate(VillageEnvironmentalRiskProfile);
        VillageEnvironmentalRiskProfile saved = repository.findById(VillageEnvironmentalRiskProfile.getId()).orElseThrow(() -> new NotFoundException("VillageEnvironmentalRiskProfile not found"));
        saved.update(assembler.fromDTO(VillageEnvironmentalRiskProfile));
        saved = repository.save(saved);
        VillageEnvironmentalRiskProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageEnvironmentalRiskProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageEnvironmentalRiskProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageEnvironmentalRiskProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageEnvironmentalRiskProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageEnvironmentalRiskProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageEnvironmentalRiskProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageEnvironmentalRiskProfile by ID.
     *
     * @param id     the ID of the VillageEnvironmentalRiskProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageEnvironmentalRiskProfile DTO
     * @throws NotFoundException if the VillageEnvironmentalRiskProfile is not found
     */
    @Transactional
    @Override
    public VillageEnvironmentalRiskProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageEnvironmentalRiskProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageEnvironmentalRiskProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageEnvironmentalRiskProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageEnvironmentalRiskProfile by ID.
     *
     * @param id the ID of the VillageEnvironmentalRiskProfile to retrieve
     * @return the VillageEnvironmentalRiskProfile DTO
     * @throws NotFoundException if the VillageEnvironmentalRiskProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageEnvironmentalRiskProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageEnvironmentalRiskProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageEnvironmentalRiskProfile not found"));
        VillageEnvironmentalRiskProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageEnvironmentalRiskProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageEnvironmentalRiskProfile> savedData = repository.findAllById(ids);
        Set<VillageEnvironmentalRiskProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageEnvironmentalRiskProfileDTO postHookSave(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected VillageEnvironmentalRiskProfileDTO preHookSave(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected VillageEnvironmentalRiskProfileDTO postHookUpdate(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected VillageEnvironmentalRiskProfileDTO preHookUpdate(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO) {
        return VillageEnvironmentalRiskProfileDTO;
    }

    protected VillageEnvironmentalRiskProfileDTO postHookDelete(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageEnvironmentalRiskProfileDTO postHookGetById(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageEnvironmentalRiskProfileDTO> postHookGetAll(PageDTO<VillageEnvironmentalRiskProfileDTO> result) {
        return result;
    }




}
