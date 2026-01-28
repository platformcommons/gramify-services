package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageCommunicationInfrastructService;


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

public class VillageCommunicationInfrastructServiceImpl implements VillageCommunicationInfrastructService {

    private final VillageCommunicationInfrastructDTOAssembler assembler;
    private final VillageCommunicationInfrastructRepository repository;


    public VillageCommunicationInfrastructServiceImpl(
        VillageCommunicationInfrastructRepository repository, VillageCommunicationInfrastructDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageCommunicationInfrastruct.
     *
     * @param VillageCommunicationInfrastruct the VillageCommunicationInfrastruct DTO to save
     * @return the saved VillageCommunicationInfrastruct DTO
     */
    @Transactional
    @Override
    public VillageCommunicationInfrastructDTO save(VillageCommunicationInfrastructDTO VillageCommunicationInfrastruct) {
        log.debug("Entry - save(VillageCommunicationInfrastruct={})", VillageCommunicationInfrastruct);
        VillageCommunicationInfrastruct = preHookSave(VillageCommunicationInfrastruct);
        VillageCommunicationInfrastruct entity = assembler.fromDTO(VillageCommunicationInfrastruct);
        VillageCommunicationInfrastructDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageCommunicationInfrastruct.
     *
     * @param VillageCommunicationInfrastruct the VillageCommunicationInfrastruct DTO to update
     * @return the updated VillageCommunicationInfrastruct DTO
     * @throws NotFoundException if the VillageCommunicationInfrastruct is not found
     */
    @Transactional
    @Override
    public VillageCommunicationInfrastructDTO update(VillageCommunicationInfrastructDTO VillageCommunicationInfrastruct) {
        log.debug("Entry - update(VillageCommunicationInfrastruct={})", VillageCommunicationInfrastruct);
        VillageCommunicationInfrastruct = preHookUpdate(VillageCommunicationInfrastruct);
        VillageCommunicationInfrastruct saved = repository.findById(VillageCommunicationInfrastruct.getId()).orElseThrow(() -> new NotFoundException("VillageCommunicationInfrastruct not found"));
        saved.update(assembler.fromDTO(VillageCommunicationInfrastruct));
        saved = repository.save(saved);
        VillageCommunicationInfrastructDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageCommunicationInfrastructs.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageCommunicationInfrastruct DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageCommunicationInfrastructDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageCommunicationInfrastruct> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageCommunicationInfrastructDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageCommunicationInfrastructDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageCommunicationInfrastruct by ID.
     *
     * @param id     the ID of the VillageCommunicationInfrastruct to delete
     * @param reason the reason for deletion
     * @return the deleted VillageCommunicationInfrastruct DTO
     * @throws NotFoundException if the VillageCommunicationInfrastruct is not found
     */
    @Transactional
    @Override
    public VillageCommunicationInfrastructDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageCommunicationInfrastruct saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommunicationInfrastruct not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageCommunicationInfrastructDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageCommunicationInfrastruct by ID.
     *
     * @param id the ID of the VillageCommunicationInfrastruct to retrieve
     * @return the VillageCommunicationInfrastruct DTO
     * @throws NotFoundException if the VillageCommunicationInfrastruct is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageCommunicationInfrastructDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommunicationInfrastruct saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCommunicationInfrastruct not found"));
        VillageCommunicationInfrastructDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageCommunicationInfrastructDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageCommunicationInfrastruct> savedData = repository.findAllById(ids);
        Set<VillageCommunicationInfrastructDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommunicationInfrastructDTO postHookSave(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected VillageCommunicationInfrastructDTO preHookSave(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected VillageCommunicationInfrastructDTO postHookUpdate(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected VillageCommunicationInfrastructDTO preHookUpdate(VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO) {
        return VillageCommunicationInfrastructDTO;
    }

    protected VillageCommunicationInfrastructDTO postHookDelete(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommunicationInfrastructDTO postHookGetById(VillageCommunicationInfrastructDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommunicationInfrastructDTO> postHookGetAll(PageDTO<VillageCommunicationInfrastructDTO> result) {
        return result;
    }




}
