package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageOtherInfrastructureService;


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

public class VillageOtherInfrastructureServiceImpl implements VillageOtherInfrastructureService {

    private final VillageOtherInfrastructureDTOAssembler assembler;
    private final VillageOtherInfrastructureRepository repository;


    public VillageOtherInfrastructureServiceImpl(
        VillageOtherInfrastructureRepository repository, VillageOtherInfrastructureDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageOtherInfrastructure.
     *
     * @param VillageOtherInfrastructure the VillageOtherInfrastructure DTO to save
     * @return the saved VillageOtherInfrastructure DTO
     */
    @Transactional
    @Override
    public VillageOtherInfrastructureDTO save(VillageOtherInfrastructureDTO VillageOtherInfrastructure) {
        log.debug("Entry - save(VillageOtherInfrastructure={})", VillageOtherInfrastructure);
        VillageOtherInfrastructure = preHookSave(VillageOtherInfrastructure);
        VillageOtherInfrastructure entity = assembler.fromDTO(VillageOtherInfrastructure);
        VillageOtherInfrastructureDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageOtherInfrastructure.
     *
     * @param VillageOtherInfrastructure the VillageOtherInfrastructure DTO to update
     * @return the updated VillageOtherInfrastructure DTO
     * @throws NotFoundException if the VillageOtherInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageOtherInfrastructureDTO update(VillageOtherInfrastructureDTO VillageOtherInfrastructure) {
        log.debug("Entry - update(VillageOtherInfrastructure={})", VillageOtherInfrastructure);
        VillageOtherInfrastructure = preHookUpdate(VillageOtherInfrastructure);
        VillageOtherInfrastructure saved = repository.findById(VillageOtherInfrastructure.getId()).orElseThrow(() -> new NotFoundException("VillageOtherInfrastructure not found"));
        saved.update(assembler.fromDTO(VillageOtherInfrastructure));
        saved = repository.save(saved);
        VillageOtherInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageOtherInfrastructures.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageOtherInfrastructure DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageOtherInfrastructureDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageOtherInfrastructure> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageOtherInfrastructureDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageOtherInfrastructureDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageOtherInfrastructure by ID.
     *
     * @param id     the ID of the VillageOtherInfrastructure to delete
     * @param reason the reason for deletion
     * @return the deleted VillageOtherInfrastructure DTO
     * @throws NotFoundException if the VillageOtherInfrastructure is not found
     */
    @Transactional
    @Override
    public VillageOtherInfrastructureDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageOtherInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageOtherInfrastructure not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageOtherInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageOtherInfrastructure by ID.
     *
     * @param id the ID of the VillageOtherInfrastructure to retrieve
     * @return the VillageOtherInfrastructure DTO
     * @throws NotFoundException if the VillageOtherInfrastructure is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageOtherInfrastructureDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageOtherInfrastructure saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageOtherInfrastructure not found"));
        VillageOtherInfrastructureDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageOtherInfrastructureDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageOtherInfrastructure> savedData = repository.findAllById(ids);
        Set<VillageOtherInfrastructureDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageOtherInfrastructureDTO postHookSave(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected VillageOtherInfrastructureDTO preHookSave(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected VillageOtherInfrastructureDTO postHookUpdate(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected VillageOtherInfrastructureDTO preHookUpdate(VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO) {
        return VillageOtherInfrastructureDTO;
    }

    protected VillageOtherInfrastructureDTO postHookDelete(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageOtherInfrastructureDTO postHookGetById(VillageOtherInfrastructureDTO dto) {
        return dto;
    }

    protected PageDTO<VillageOtherInfrastructureDTO> postHookGetAll(PageDTO<VillageOtherInfrastructureDTO> result) {
        return result;
    }




}
