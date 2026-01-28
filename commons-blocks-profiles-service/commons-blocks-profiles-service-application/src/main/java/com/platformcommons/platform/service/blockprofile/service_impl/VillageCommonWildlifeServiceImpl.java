package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageCommonWildlifeService;


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

public class VillageCommonWildlifeServiceImpl implements VillageCommonWildlifeService {

    private final VillageCommonWildlifeDTOAssembler assembler;
    private final VillageCommonWildlifeRepository repository;


    public VillageCommonWildlifeServiceImpl(
        VillageCommonWildlifeRepository repository, VillageCommonWildlifeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageCommonWildlife.
     *
     * @param VillageCommonWildlife the VillageCommonWildlife DTO to save
     * @return the saved VillageCommonWildlife DTO
     */
    @Transactional
    @Override
    public VillageCommonWildlifeDTO save(VillageCommonWildlifeDTO VillageCommonWildlife) {
        log.debug("Entry - save(VillageCommonWildlife={})", VillageCommonWildlife);
        VillageCommonWildlife = preHookSave(VillageCommonWildlife);
        VillageCommonWildlife entity = assembler.fromDTO(VillageCommonWildlife);
        VillageCommonWildlifeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageCommonWildlife.
     *
     * @param VillageCommonWildlife the VillageCommonWildlife DTO to update
     * @return the updated VillageCommonWildlife DTO
     * @throws NotFoundException if the VillageCommonWildlife is not found
     */
    @Transactional
    @Override
    public VillageCommonWildlifeDTO update(VillageCommonWildlifeDTO VillageCommonWildlife) {
        log.debug("Entry - update(VillageCommonWildlife={})", VillageCommonWildlife);
        VillageCommonWildlife = preHookUpdate(VillageCommonWildlife);
        VillageCommonWildlife saved = repository.findById(VillageCommonWildlife.getId()).orElseThrow(() -> new NotFoundException("VillageCommonWildlife not found"));
        saved.update(assembler.fromDTO(VillageCommonWildlife));
        saved = repository.save(saved);
        VillageCommonWildlifeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageCommonWildlifes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageCommonWildlife DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageCommonWildlifeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageCommonWildlife> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageCommonWildlifeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageCommonWildlifeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageCommonWildlife by ID.
     *
     * @param id     the ID of the VillageCommonWildlife to delete
     * @param reason the reason for deletion
     * @return the deleted VillageCommonWildlife DTO
     * @throws NotFoundException if the VillageCommonWildlife is not found
     */
    @Transactional
    @Override
    public VillageCommonWildlifeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageCommonWildlife saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommonWildlife not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageCommonWildlifeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageCommonWildlife by ID.
     *
     * @param id the ID of the VillageCommonWildlife to retrieve
     * @return the VillageCommonWildlife DTO
     * @throws NotFoundException if the VillageCommonWildlife is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageCommonWildlifeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommonWildlife saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommonWildlife not found"));
        VillageCommonWildlifeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageCommonWildlifeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageCommonWildlife> savedData = repository.findAllById(ids);
        Set<VillageCommonWildlifeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommonWildlifeDTO postHookSave(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected VillageCommonWildlifeDTO preHookSave(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected VillageCommonWildlifeDTO postHookUpdate(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected VillageCommonWildlifeDTO preHookUpdate(VillageCommonWildlifeDTO VillageCommonWildlifeDTO) {
        return VillageCommonWildlifeDTO;
    }

    protected VillageCommonWildlifeDTO postHookDelete(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommonWildlifeDTO postHookGetById(VillageCommonWildlifeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommonWildlifeDTO> postHookGetAll(PageDTO<VillageCommonWildlifeDTO> result) {
        return result;
    }




}
