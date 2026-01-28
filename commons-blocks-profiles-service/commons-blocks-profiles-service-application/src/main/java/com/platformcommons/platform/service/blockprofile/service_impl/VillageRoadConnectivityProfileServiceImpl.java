package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageRoadConnectivityProfileService;


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

public class VillageRoadConnectivityProfileServiceImpl implements VillageRoadConnectivityProfileService {

    private final VillageRoadConnectivityProfileDTOAssembler assembler;
    private final VillageRoadConnectivityProfileRepository repository;


    public VillageRoadConnectivityProfileServiceImpl(
        VillageRoadConnectivityProfileRepository repository, VillageRoadConnectivityProfileDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageRoadConnectivityProfile.
     *
     * @param VillageRoadConnectivityProfile the VillageRoadConnectivityProfile DTO to save
     * @return the saved VillageRoadConnectivityProfile DTO
     */
    @Transactional
    @Override
    public VillageRoadConnectivityProfileDTO save(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfile) {
        log.debug("Entry - save(VillageRoadConnectivityProfile={})", VillageRoadConnectivityProfile);
        VillageRoadConnectivityProfile = preHookSave(VillageRoadConnectivityProfile);
        VillageRoadConnectivityProfile entity = assembler.fromDTO(VillageRoadConnectivityProfile);
        VillageRoadConnectivityProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageRoadConnectivityProfile.
     *
     * @param VillageRoadConnectivityProfile the VillageRoadConnectivityProfile DTO to update
     * @return the updated VillageRoadConnectivityProfile DTO
     * @throws NotFoundException if the VillageRoadConnectivityProfile is not found
     */
    @Transactional
    @Override
    public VillageRoadConnectivityProfileDTO update(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfile) {
        log.debug("Entry - update(VillageRoadConnectivityProfile={})", VillageRoadConnectivityProfile);
        VillageRoadConnectivityProfile = preHookUpdate(VillageRoadConnectivityProfile);
        VillageRoadConnectivityProfile saved = repository.findById(VillageRoadConnectivityProfile.getId()).orElseThrow(() -> new NotFoundException("VillageRoadConnectivityProfile not found"));
        saved.update(assembler.fromDTO(VillageRoadConnectivityProfile));
        saved = repository.save(saved);
        VillageRoadConnectivityProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageRoadConnectivityProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageRoadConnectivityProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageRoadConnectivityProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageRoadConnectivityProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageRoadConnectivityProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageRoadConnectivityProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageRoadConnectivityProfile by ID.
     *
     * @param id     the ID of the VillageRoadConnectivityProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageRoadConnectivityProfile DTO
     * @throws NotFoundException if the VillageRoadConnectivityProfile is not found
     */
    @Transactional
    @Override
    public VillageRoadConnectivityProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageRoadConnectivityProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageRoadConnectivityProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageRoadConnectivityProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageRoadConnectivityProfile by ID.
     *
     * @param id the ID of the VillageRoadConnectivityProfile to retrieve
     * @return the VillageRoadConnectivityProfile DTO
     * @throws NotFoundException if the VillageRoadConnectivityProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageRoadConnectivityProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageRoadConnectivityProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageRoadConnectivityProfile not found"));
        VillageRoadConnectivityProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageRoadConnectivityProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageRoadConnectivityProfile> savedData = repository.findAllById(ids);
        Set<VillageRoadConnectivityProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageRoadConnectivityProfileDTO postHookSave(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected VillageRoadConnectivityProfileDTO preHookSave(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected VillageRoadConnectivityProfileDTO postHookUpdate(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected VillageRoadConnectivityProfileDTO preHookUpdate(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO) {
        return VillageRoadConnectivityProfileDTO;
    }

    protected VillageRoadConnectivityProfileDTO postHookDelete(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageRoadConnectivityProfileDTO postHookGetById(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageRoadConnectivityProfileDTO> postHookGetAll(PageDTO<VillageRoadConnectivityProfileDTO> result) {
        return result;
    }




}
