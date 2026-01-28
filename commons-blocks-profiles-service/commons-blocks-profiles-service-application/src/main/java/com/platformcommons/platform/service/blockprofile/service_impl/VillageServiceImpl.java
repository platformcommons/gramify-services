package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageService;


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

public class VillageServiceImpl implements VillageService {

    private final VillageDTOAssembler assembler;
    private final VillageRepository repository;


    public VillageServiceImpl(
        VillageRepository repository, VillageDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new Village.
     *
     * @param Village the Village DTO to save
     * @return the saved Village DTO
     */
    @Transactional
    @Override
    public VillageDTO save(VillageDTO Village) {
        log.debug("Entry - save(Village={})", Village);
        Village = preHookSave(Village);
        Village entity = assembler.fromDTO(Village);
        VillageDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing Village.
     *
     * @param Village the Village DTO to update
     * @return the updated Village DTO
     * @throws NotFoundException if the Village is not found
     */
    @Transactional
    @Override
    public VillageDTO update(VillageDTO Village) {
        log.debug("Entry - update(Village={})", Village);
        Village = preHookUpdate(Village);
        Village saved = repository.findById(Village.getId()).orElseThrow(() -> new NotFoundException("Village not found"));
        saved.update(assembler.fromDTO(Village));
        saved = repository.save(saved);
        VillageDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of Villages.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing Village DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<Village> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a Village by ID.
     *
     * @param id     the ID of the Village to delete
     * @param reason the reason for deletion
     * @return the deleted Village DTO
     * @throws NotFoundException if the Village is not found
     */
    @Transactional
    @Override
    public VillageDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        Village saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Village not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a Village by ID.
     *
     * @param id the ID of the Village to retrieve
     * @return the Village DTO
     * @throws NotFoundException if the Village is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        Village saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Village not found"));
        VillageDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<Village> savedData = repository.findAllById(ids);
        Set<VillageDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageDTO postHookSave(VillageDTO dto) {
        return dto;
    }

    protected VillageDTO preHookSave(VillageDTO dto) {
        return dto;
    }

    protected VillageDTO postHookUpdate(VillageDTO dto) {
        return dto;
    }

    protected VillageDTO preHookUpdate(VillageDTO VillageDTO) {
        return VillageDTO;
    }

    protected VillageDTO postHookDelete(VillageDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageDTO postHookGetById(VillageDTO dto) {
        return dto;
    }

    protected PageDTO<VillageDTO> postHookGetAll(PageDTO<VillageDTO> result) {
        return result;
    }




}
