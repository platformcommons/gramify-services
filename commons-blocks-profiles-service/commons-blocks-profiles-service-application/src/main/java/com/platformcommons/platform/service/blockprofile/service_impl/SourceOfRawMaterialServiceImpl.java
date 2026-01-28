package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.SourceOfRawMaterialService;


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

public class SourceOfRawMaterialServiceImpl implements SourceOfRawMaterialService {

    private final SourceOfRawMaterialDTOAssembler assembler;
    private final SourceOfRawMaterialRepository repository;


    public SourceOfRawMaterialServiceImpl(
        SourceOfRawMaterialRepository repository, SourceOfRawMaterialDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new SourceOfRawMaterial.
     *
     * @param SourceOfRawMaterial the SourceOfRawMaterial DTO to save
     * @return the saved SourceOfRawMaterial DTO
     */
    @Transactional
    @Override
    public SourceOfRawMaterialDTO save(SourceOfRawMaterialDTO SourceOfRawMaterial) {
        log.debug("Entry - save(SourceOfRawMaterial={})", SourceOfRawMaterial);
        SourceOfRawMaterial = preHookSave(SourceOfRawMaterial);
        SourceOfRawMaterial entity = assembler.fromDTO(SourceOfRawMaterial);
        SourceOfRawMaterialDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing SourceOfRawMaterial.
     *
     * @param SourceOfRawMaterial the SourceOfRawMaterial DTO to update
     * @return the updated SourceOfRawMaterial DTO
     * @throws NotFoundException if the SourceOfRawMaterial is not found
     */
    @Transactional
    @Override
    public SourceOfRawMaterialDTO update(SourceOfRawMaterialDTO SourceOfRawMaterial) {
        log.debug("Entry - update(SourceOfRawMaterial={})", SourceOfRawMaterial);
        SourceOfRawMaterial = preHookUpdate(SourceOfRawMaterial);
        SourceOfRawMaterial saved = repository.findById(SourceOfRawMaterial.getId()).orElseThrow(() -> new NotFoundException("SourceOfRawMaterial not found"));
        saved.update(assembler.fromDTO(SourceOfRawMaterial));
        saved = repository.save(saved);
        SourceOfRawMaterialDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of SourceOfRawMaterials.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing SourceOfRawMaterial DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<SourceOfRawMaterialDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<SourceOfRawMaterial> result = repository.findAll(PageRequest.of(page, size));
        Set<SourceOfRawMaterialDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<SourceOfRawMaterialDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a SourceOfRawMaterial by ID.
     *
     * @param id     the ID of the SourceOfRawMaterial to delete
     * @param reason the reason for deletion
     * @return the deleted SourceOfRawMaterial DTO
     * @throws NotFoundException if the SourceOfRawMaterial is not found
     */
    @Transactional
    @Override
    public SourceOfRawMaterialDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        SourceOfRawMaterial saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SourceOfRawMaterial not found"));
        saved.deactivate(reason);
        repository.save(saved);
        SourceOfRawMaterialDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a SourceOfRawMaterial by ID.
     *
     * @param id the ID of the SourceOfRawMaterial to retrieve
     * @return the SourceOfRawMaterial DTO
     * @throws NotFoundException if the SourceOfRawMaterial is not found
     */
    @Transactional(readOnly = true)
    @Override
    public SourceOfRawMaterialDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        SourceOfRawMaterial saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SourceOfRawMaterial not found"));
        SourceOfRawMaterialDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<SourceOfRawMaterialDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<SourceOfRawMaterial> savedData = repository.findAllById(ids);
        Set<SourceOfRawMaterialDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SourceOfRawMaterialDTO postHookSave(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected SourceOfRawMaterialDTO preHookSave(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected SourceOfRawMaterialDTO postHookUpdate(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected SourceOfRawMaterialDTO preHookUpdate(SourceOfRawMaterialDTO SourceOfRawMaterialDTO) {
        return SourceOfRawMaterialDTO;
    }

    protected SourceOfRawMaterialDTO postHookDelete(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SourceOfRawMaterialDTO postHookGetById(SourceOfRawMaterialDTO dto) {
        return dto;
    }

    protected PageDTO<SourceOfRawMaterialDTO> postHookGetAll(PageDTO<SourceOfRawMaterialDTO> result) {
        return result;
    }




}
