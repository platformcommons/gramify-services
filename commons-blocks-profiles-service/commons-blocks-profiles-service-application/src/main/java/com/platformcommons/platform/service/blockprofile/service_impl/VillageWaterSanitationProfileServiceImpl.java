package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageWaterSanitationProfileService;


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

public class VillageWaterSanitationProfileServiceImpl implements VillageWaterSanitationProfileService {

    private final VillageWaterSanitationProfileDTOAssembler assembler;
    private final VillageWaterSanitationProfileRepository repository;


    public VillageWaterSanitationProfileServiceImpl(
        VillageWaterSanitationProfileRepository repository, VillageWaterSanitationProfileDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageWaterSanitationProfile.
     *
     * @param VillageWaterSanitationProfile the VillageWaterSanitationProfile DTO to save
     * @return the saved VillageWaterSanitationProfile DTO
     */
    @Transactional
    @Override
    public VillageWaterSanitationProfileDTO save(VillageWaterSanitationProfileDTO VillageWaterSanitationProfile) {
        log.debug("Entry - save(VillageWaterSanitationProfile={})", VillageWaterSanitationProfile);
        VillageWaterSanitationProfile = preHookSave(VillageWaterSanitationProfile);
        VillageWaterSanitationProfile entity = assembler.fromDTO(VillageWaterSanitationProfile);
        VillageWaterSanitationProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageWaterSanitationProfile.
     *
     * @param VillageWaterSanitationProfile the VillageWaterSanitationProfile DTO to update
     * @return the updated VillageWaterSanitationProfile DTO
     * @throws NotFoundException if the VillageWaterSanitationProfile is not found
     */
    @Transactional
    @Override
    public VillageWaterSanitationProfileDTO update(VillageWaterSanitationProfileDTO VillageWaterSanitationProfile) {
        log.debug("Entry - update(VillageWaterSanitationProfile={})", VillageWaterSanitationProfile);
        VillageWaterSanitationProfile = preHookUpdate(VillageWaterSanitationProfile);
        VillageWaterSanitationProfile saved = repository.findById(VillageWaterSanitationProfile.getId()).orElseThrow(() -> new NotFoundException("VillageWaterSanitationProfile not found"));
        saved.update(assembler.fromDTO(VillageWaterSanitationProfile));
        saved = repository.save(saved);
        VillageWaterSanitationProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageWaterSanitationProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageWaterSanitationProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageWaterSanitationProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageWaterSanitationProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageWaterSanitationProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageWaterSanitationProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageWaterSanitationProfile by ID.
     *
     * @param id     the ID of the VillageWaterSanitationProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageWaterSanitationProfile DTO
     * @throws NotFoundException if the VillageWaterSanitationProfile is not found
     */
    @Transactional
    @Override
    public VillageWaterSanitationProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageWaterSanitationProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageWaterSanitationProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageWaterSanitationProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageWaterSanitationProfile by ID.
     *
     * @param id the ID of the VillageWaterSanitationProfile to retrieve
     * @return the VillageWaterSanitationProfile DTO
     * @throws NotFoundException if the VillageWaterSanitationProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageWaterSanitationProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageWaterSanitationProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageWaterSanitationProfile not found"));
        VillageWaterSanitationProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageWaterSanitationProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageWaterSanitationProfile> savedData = repository.findAllById(ids);
        Set<VillageWaterSanitationProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageWaterSanitationProfileDTO postHookSave(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected VillageWaterSanitationProfileDTO preHookSave(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected VillageWaterSanitationProfileDTO postHookUpdate(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected VillageWaterSanitationProfileDTO preHookUpdate(VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO) {
        return VillageWaterSanitationProfileDTO;
    }

    protected VillageWaterSanitationProfileDTO postHookDelete(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageWaterSanitationProfileDTO postHookGetById(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageWaterSanitationProfileDTO> postHookGetAll(PageDTO<VillageWaterSanitationProfileDTO> result) {
        return result;
    }




}
