package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageIrrigationInfrastructureService;


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

public class VillageIrrigationInfrastructureServiceImpl implements VillageIrrigationInfrastructureService {

    private final VillageIrrigationInfrastructureDTOAssembler assembler;
    private final VillageIrrigationInfrastructureRepository repository;


    public VillageIrrigationInfrastructureServiceImpl(
        VillageIrrigationInfrastructureRepository repository, VillageIrrigationInfrastructureDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageIrrigationInfrastructure.
     *
     * @param VillageIrrigationInfrastructure the VillageIrrigationInfrastructure DTO to save
     * @return the saved VillageIrrigationInfrastructure DTO
     */
    @Transactional
    @Override
    public VillageIrrigationInfrastructureDTO save(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructure) {
        log.debug("Entry - save(VillageIrrigationInfrastructure={})", VillageIrrigationInfrastructure);
        VillageIrrigationInfrastructure = preHookSave(VillageIrrigationInfrastructure);
        VillageIrrigationInfrastructure entity = assembler.fromDTO(VillageIrrigationInfrastructure);
        VillageIrrigationInfrastructureDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageIrrigationInfrastructure.
     *
     * @param VillageIrrigationInfrastructure the VillageIrrigationInfrastructure DTO to update
     * @return the updated VillageIrrigationInfrastructure DTO
     * @throws NotFoundException if the VillageIrrigationInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageIrrigationInfrastructureDTO update(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructure) {
        log.debug("Entry - update(VillageIrrigationInfrastructure={})", VillageIrrigationInfrastructure);
        VillageIrrigationInfrastructure = preHookUpdate(VillageIrrigationInfrastructure);
        VillageIrrigationInfrastructure saved = repository.findById(VillageIrrigationInfrastructure.getId()).orElseThrow(() -> new NotFoundException("VillageIrrigationInfrastructure not found"));
        saved.update(assembler.fromDTO(VillageIrrigationInfrastructure));
        saved = repository.save(saved);
        VillageIrrigationInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageIrrigationInfrastructures.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageIrrigationInfrastructure DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageIrrigationInfrastructureDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageIrrigationInfrastructure> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageIrrigationInfrastructureDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageIrrigationInfrastructureDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageIrrigationInfrastructure by ID.
     *
     * @param id     the ID of the VillageIrrigationInfrastructure to delete
     * @param reason the reason for deletion
     * @return the deleted VillageIrrigationInfrastructure DTO
     * @throws NotFoundException if the VillageIrrigationInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageIrrigationInfrastructureDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageIrrigationInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageIrrigationInfrastructure not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageIrrigationInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageIrrigationInfrastructure by ID.
     *
     * @param id the ID of the VillageIrrigationInfrastructure to retrieve
     * @return the VillageIrrigationInfrastructure DTO
     * @throws NotFoundException if the VillageIrrigationInfrastructure is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageIrrigationInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageIrrigationInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageIrrigationInfrastructure not found"));
        VillageIrrigationInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageIrrigationInfrastructureDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageIrrigationInfrastructure> savedData = repository.findAllById(ids);
        Set<VillageIrrigationInfrastructureDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageIrrigationInfrastructureDTO postHookSave(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageIrrigationInfrastructureDTO preHookSave(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageIrrigationInfrastructureDTO postHookUpdate(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected VillageIrrigationInfrastructureDTO preHookUpdate(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO) {
        return VillageIrrigationInfrastructureDTO;
    }

    protected VillageIrrigationInfrastructureDTO postHookDelete(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageIrrigationInfrastructureDTO postHookGetById(VillageIrrigationInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageIrrigationInfrastructureDTO> postHookGetAll(PageDTO<VillageIrrigationInfrastructureDTO> result) {
        return result;
    }




}
