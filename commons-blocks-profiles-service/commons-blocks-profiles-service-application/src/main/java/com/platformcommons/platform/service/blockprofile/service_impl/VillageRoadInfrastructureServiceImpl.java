package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageRoadInfrastructureService;


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

public class VillageRoadInfrastructureServiceImpl implements VillageRoadInfrastructureService {

    private final VillageRoadInfrastructureDTOAssembler assembler;
    private final VillageRoadInfrastructureRepository repository;


    public VillageRoadInfrastructureServiceImpl(
        VillageRoadInfrastructureRepository repository, VillageRoadInfrastructureDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageRoadInfrastructure.
     *
     * @param VillageRoadInfrastructure the VillageRoadInfrastructure DTO to save
     * @return the saved VillageRoadInfrastructure DTO
     */
    @Transactional
    @Override
    public VillageRoadInfrastructureDTO save(VillageRoadInfrastructureDTO VillageRoadInfrastructure) {
        log.debug("Entry - save(VillageRoadInfrastructure={})", VillageRoadInfrastructure);
        VillageRoadInfrastructure = preHookSave(VillageRoadInfrastructure);
        VillageRoadInfrastructure entity = assembler.fromDTO(VillageRoadInfrastructure);
        VillageRoadInfrastructureDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageRoadInfrastructure.
     *
     * @param VillageRoadInfrastructure the VillageRoadInfrastructure DTO to update
     * @return the updated VillageRoadInfrastructure DTO
     * @throws NotFoundException if the VillageRoadInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageRoadInfrastructureDTO update(VillageRoadInfrastructureDTO VillageRoadInfrastructure) {
        log.debug("Entry - update(VillageRoadInfrastructure={})", VillageRoadInfrastructure);
        VillageRoadInfrastructure = preHookUpdate(VillageRoadInfrastructure);
        VillageRoadInfrastructure saved = repository.findById(VillageRoadInfrastructure.getId()).orElseThrow(() -> new NotFoundException("VillageRoadInfrastructure not found"));
        saved.update(assembler.fromDTO(VillageRoadInfrastructure));
        saved = repository.save(saved);
        VillageRoadInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageRoadInfrastructures.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageRoadInfrastructure DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageRoadInfrastructureDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageRoadInfrastructure> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageRoadInfrastructureDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageRoadInfrastructureDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageRoadInfrastructure by ID.
     *
     * @param id     the ID of the VillageRoadInfrastructure to delete
     * @param reason the reason for deletion
     * @return the deleted VillageRoadInfrastructure DTO
     * @throws NotFoundException if the VillageRoadInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageRoadInfrastructureDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageRoadInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageRoadInfrastructure not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageRoadInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageRoadInfrastructure by ID.
     *
     * @param id the ID of the VillageRoadInfrastructure to retrieve
     * @return the VillageRoadInfrastructure DTO
     * @throws NotFoundException if the VillageRoadInfrastructure is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageRoadInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageRoadInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageRoadInfrastructure not found"));
        VillageRoadInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageRoadInfrastructureDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageRoadInfrastructure> savedData = repository.findAllById(ids);
        Set<VillageRoadInfrastructureDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageRoadInfrastructureDTO postHookSave(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected VillageRoadInfrastructureDTO preHookSave(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected VillageRoadInfrastructureDTO postHookUpdate(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected VillageRoadInfrastructureDTO preHookUpdate(VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO) {
        return VillageRoadInfrastructureDTO;
    }

    protected VillageRoadInfrastructureDTO postHookDelete(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageRoadInfrastructureDTO postHookGetById(VillageRoadInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageRoadInfrastructureDTO> postHookGetAll(PageDTO<VillageRoadInfrastructureDTO> result) {
        return result;
    }




}
