package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ArtisanTypeService;


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

public class ArtisanTypeServiceImpl implements ArtisanTypeService {

    private final ArtisanTypeDTOAssembler assembler;
    private final ArtisanTypeRepository repository;


    public ArtisanTypeServiceImpl(
        ArtisanTypeRepository repository, ArtisanTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ArtisanType.
     *
     * @param ArtisanType the ArtisanType DTO to save
     * @return the saved ArtisanType DTO
     */
    @Transactional
    @Override
    public ArtisanTypeDTO save(ArtisanTypeDTO ArtisanType) {
        log.debug("Entry - save(ArtisanType={})", ArtisanType);
        ArtisanType = preHookSave(ArtisanType);
        ArtisanType entity = assembler.fromDTO(ArtisanType);
        ArtisanTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ArtisanType.
     *
     * @param ArtisanType the ArtisanType DTO to update
     * @return the updated ArtisanType DTO
     * @throws NotFoundException if the ArtisanType is not found
     */
    @Transactional
    @Override
    public ArtisanTypeDTO update(ArtisanTypeDTO ArtisanType) {
        log.debug("Entry - update(ArtisanType={})", ArtisanType);
        ArtisanType = preHookUpdate(ArtisanType);
        ArtisanType saved = repository.findById(ArtisanType.getId()).orElseThrow(() -> new NotFoundException("ArtisanType not found"));
        saved.update(assembler.fromDTO(ArtisanType));
        saved = repository.save(saved);
        ArtisanTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ArtisanTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ArtisanType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ArtisanTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ArtisanType> result = repository.findAll(PageRequest.of(page, size));
        Set<ArtisanTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ArtisanTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ArtisanType by ID.
     *
     * @param id     the ID of the ArtisanType to delete
     * @param reason the reason for deletion
     * @return the deleted ArtisanType DTO
     * @throws NotFoundException if the ArtisanType is not found
     */
    @Transactional
    @Override
    public ArtisanTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ArtisanType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ArtisanType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ArtisanTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ArtisanType by ID.
     *
     * @param id the ID of the ArtisanType to retrieve
     * @return the ArtisanType DTO
     * @throws NotFoundException if the ArtisanType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ArtisanTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ArtisanType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ArtisanType not found"));
        ArtisanTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ArtisanTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ArtisanType> savedData = repository.findAllById(ids);
        Set<ArtisanTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ArtisanTypeDTO postHookSave(ArtisanTypeDTO dto) {
        return dto;
    }

    protected ArtisanTypeDTO preHookSave(ArtisanTypeDTO dto) {
        return dto;
    }

    protected ArtisanTypeDTO postHookUpdate(ArtisanTypeDTO dto) {
        return dto;
    }

    protected ArtisanTypeDTO preHookUpdate(ArtisanTypeDTO ArtisanTypeDTO) {
        return ArtisanTypeDTO;
    }

    protected ArtisanTypeDTO postHookDelete(ArtisanTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ArtisanTypeDTO postHookGetById(ArtisanTypeDTO dto) {
        return dto;
    }

    protected PageDTO<ArtisanTypeDTO> postHookGetAll(PageDTO<ArtisanTypeDTO> result) {
        return result;
    }




}
