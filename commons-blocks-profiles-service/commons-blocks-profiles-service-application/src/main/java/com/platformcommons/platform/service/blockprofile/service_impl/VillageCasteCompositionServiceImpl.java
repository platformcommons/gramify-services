package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageCasteCompositionService;


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

public class VillageCasteCompositionServiceImpl implements VillageCasteCompositionService {

    private final VillageCasteCompositionDTOAssembler assembler;
    private final VillageCasteCompositionRepository repository;


    public VillageCasteCompositionServiceImpl(
        VillageCasteCompositionRepository repository, VillageCasteCompositionDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageCasteComposition.
     *
     * @param VillageCasteComposition the VillageCasteComposition DTO to save
     * @return the saved VillageCasteComposition DTO
     */
    @Transactional
    @Override
    public VillageCasteCompositionDTO save(VillageCasteCompositionDTO VillageCasteComposition) {
        log.debug("Entry - save(VillageCasteComposition={})", VillageCasteComposition);
        VillageCasteComposition = preHookSave(VillageCasteComposition);
        VillageCasteComposition entity = assembler.fromDTO(VillageCasteComposition);
        VillageCasteCompositionDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageCasteComposition.
     *
     * @param VillageCasteComposition the VillageCasteComposition DTO to update
     * @return the updated VillageCasteComposition DTO
     * @throws NotFoundException if the VillageCasteComposition is not found
     */
    @Transactional
    @Override
    public VillageCasteCompositionDTO update(VillageCasteCompositionDTO VillageCasteComposition) {
        log.debug("Entry - update(VillageCasteComposition={})", VillageCasteComposition);
        VillageCasteComposition = preHookUpdate(VillageCasteComposition);
        VillageCasteComposition saved = repository.findById(VillageCasteComposition.getId()).orElseThrow(() -> new NotFoundException("VillageCasteComposition not found"));
        saved.update(assembler.fromDTO(VillageCasteComposition));
        saved = repository.save(saved);
        VillageCasteCompositionDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageCasteCompositions.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageCasteComposition DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageCasteCompositionDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageCasteComposition> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageCasteCompositionDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageCasteCompositionDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageCasteComposition by ID.
     *
     * @param id     the ID of the VillageCasteComposition to delete
     * @param reason the reason for deletion
     * @return the deleted VillageCasteComposition DTO
     * @throws NotFoundException if the VillageCasteComposition is not found
     */
    @Transactional
    @Override
    public VillageCasteCompositionDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageCasteComposition saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCasteComposition not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageCasteCompositionDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageCasteComposition by ID.
     *
     * @param id the ID of the VillageCasteComposition to retrieve
     * @return the VillageCasteComposition DTO
     * @throws NotFoundException if the VillageCasteComposition is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageCasteCompositionDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCasteComposition saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCasteComposition not found"));
        VillageCasteCompositionDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageCasteCompositionDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageCasteComposition> savedData = repository.findAllById(ids);
        Set<VillageCasteCompositionDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCasteCompositionDTO postHookSave(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected VillageCasteCompositionDTO preHookSave(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected VillageCasteCompositionDTO postHookUpdate(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected VillageCasteCompositionDTO preHookUpdate(VillageCasteCompositionDTO VillageCasteCompositionDTO) {
        return VillageCasteCompositionDTO;
    }

    protected VillageCasteCompositionDTO postHookDelete(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCasteCompositionDTO postHookGetById(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCasteCompositionDTO> postHookGetAll(PageDTO<VillageCasteCompositionDTO> result) {
        return result;
    }




}
