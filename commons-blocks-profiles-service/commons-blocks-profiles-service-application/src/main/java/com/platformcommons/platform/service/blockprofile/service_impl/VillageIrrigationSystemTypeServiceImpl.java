package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageIrrigationSystemTypeService;


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

public class VillageIrrigationSystemTypeServiceImpl implements VillageIrrigationSystemTypeService {

    private final VillageIrrigationSystemTypeDTOAssembler assembler;
    private final VillageIrrigationSystemTypeRepository repository;


    public VillageIrrigationSystemTypeServiceImpl(
        VillageIrrigationSystemTypeRepository repository, VillageIrrigationSystemTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageIrrigationSystemType.
     *
     * @param VillageIrrigationSystemType the VillageIrrigationSystemType DTO to save
     * @return the saved VillageIrrigationSystemType DTO
     */
    @Transactional
    @Override
    public VillageIrrigationSystemTypeDTO save(VillageIrrigationSystemTypeDTO VillageIrrigationSystemType) {
        log.debug("Entry - save(VillageIrrigationSystemType={})", VillageIrrigationSystemType);
        VillageIrrigationSystemType = preHookSave(VillageIrrigationSystemType);
        VillageIrrigationSystemType entity = assembler.fromDTO(VillageIrrigationSystemType);
        VillageIrrigationSystemTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageIrrigationSystemType.
     *
     * @param VillageIrrigationSystemType the VillageIrrigationSystemType DTO to update
     * @return the updated VillageIrrigationSystemType DTO
     * @throws NotFoundException if the VillageIrrigationSystemType is not found
     */
    @Transactional
    @Override
    public VillageIrrigationSystemTypeDTO update(VillageIrrigationSystemTypeDTO VillageIrrigationSystemType) {
        log.debug("Entry - update(VillageIrrigationSystemType={})", VillageIrrigationSystemType);
        VillageIrrigationSystemType = preHookUpdate(VillageIrrigationSystemType);
        VillageIrrigationSystemType saved = repository.findById(VillageIrrigationSystemType.getId()).orElseThrow(() -> new NotFoundException("VillageIrrigationSystemType not found"));
        saved.update(assembler.fromDTO(VillageIrrigationSystemType));
        saved = repository.save(saved);
        VillageIrrigationSystemTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageIrrigationSystemTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageIrrigationSystemType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageIrrigationSystemTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageIrrigationSystemType> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageIrrigationSystemTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageIrrigationSystemTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageIrrigationSystemType by ID.
     *
     * @param id     the ID of the VillageIrrigationSystemType to delete
     * @param reason the reason for deletion
     * @return the deleted VillageIrrigationSystemType DTO
     * @throws NotFoundException if the VillageIrrigationSystemType is not found
     */
    @Transactional
    @Override
    public VillageIrrigationSystemTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageIrrigationSystemType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageIrrigationSystemType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageIrrigationSystemTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageIrrigationSystemType by ID.
     *
     * @param id the ID of the VillageIrrigationSystemType to retrieve
     * @return the VillageIrrigationSystemType DTO
     * @throws NotFoundException if the VillageIrrigationSystemType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageIrrigationSystemTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageIrrigationSystemType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageIrrigationSystemType not found"));
        VillageIrrigationSystemTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageIrrigationSystemTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageIrrigationSystemType> savedData = repository.findAllById(ids);
        Set<VillageIrrigationSystemTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageIrrigationSystemTypeDTO postHookSave(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected VillageIrrigationSystemTypeDTO preHookSave(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected VillageIrrigationSystemTypeDTO postHookUpdate(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected VillageIrrigationSystemTypeDTO preHookUpdate(VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO) {
        return VillageIrrigationSystemTypeDTO;
    }

    protected VillageIrrigationSystemTypeDTO postHookDelete(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageIrrigationSystemTypeDTO postHookGetById(VillageIrrigationSystemTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageIrrigationSystemTypeDTO> postHookGetAll(PageDTO<VillageIrrigationSystemTypeDTO> result) {
        return result;
    }




}
