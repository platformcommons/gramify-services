package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHArtisanTypeService;


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

public class HHArtisanTypeServiceImpl implements HHArtisanTypeService {

    private final HHArtisanTypeDTOAssembler assembler;
    private final HHArtisanTypeRepository repository;


    public HHArtisanTypeServiceImpl(
        HHArtisanTypeRepository repository, HHArtisanTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHArtisanType.
     *
     * @param HHArtisanType the HHArtisanType DTO to save
     * @return the saved HHArtisanType DTO
     */
    @Transactional
    @Override
    public HHArtisanTypeDTO save(HHArtisanTypeDTO HHArtisanType) {
        log.debug("Entry - save(HHArtisanType={})", HHArtisanType);
        HHArtisanType = preHookSave(HHArtisanType);
        HHArtisanType entity = assembler.fromDTO(HHArtisanType);
        HHArtisanTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHArtisanType.
     *
     * @param HHArtisanType the HHArtisanType DTO to update
     * @return the updated HHArtisanType DTO
     * @throws NotFoundException if the HHArtisanType is not found
     */
    @Transactional
    @Override
    public HHArtisanTypeDTO update(HHArtisanTypeDTO HHArtisanType) {
        log.debug("Entry - update(HHArtisanType={})", HHArtisanType);
        HHArtisanType = preHookUpdate(HHArtisanType);
        HHArtisanType saved = repository.findById(HHArtisanType.getId()).orElseThrow(() -> new NotFoundException("HHArtisanType not found"));
        saved.update(assembler.fromDTO(HHArtisanType));
        saved = repository.save(saved);
        HHArtisanTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHArtisanTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHArtisanType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHArtisanTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHArtisanType> result = repository.findAll(PageRequest.of(page, size));
        Set<HHArtisanTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHArtisanTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHArtisanType by ID.
     *
     * @param id     the ID of the HHArtisanType to delete
     * @param reason the reason for deletion
     * @return the deleted HHArtisanType DTO
     * @throws NotFoundException if the HHArtisanType is not found
     */
    @Transactional
    @Override
    public HHArtisanTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHArtisanType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHArtisanType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHArtisanTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHArtisanType by ID.
     *
     * @param id the ID of the HHArtisanType to retrieve
     * @return the HHArtisanType DTO
     * @throws NotFoundException if the HHArtisanType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHArtisanTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHArtisanType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHArtisanType not found"));
        HHArtisanTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHArtisanTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHArtisanType> savedData = repository.findAllById(ids);
        Set<HHArtisanTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHArtisanTypeDTO postHookSave(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected HHArtisanTypeDTO preHookSave(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected HHArtisanTypeDTO postHookUpdate(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected HHArtisanTypeDTO preHookUpdate(HHArtisanTypeDTO HHArtisanTypeDTO) {
        return HHArtisanTypeDTO;
    }

    protected HHArtisanTypeDTO postHookDelete(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHArtisanTypeDTO postHookGetById(HHArtisanTypeDTO dto) {
        return dto;
    }

    protected PageDTO<HHArtisanTypeDTO> postHookGetAll(PageDTO<HHArtisanTypeDTO> result) {
        return result;
    }




}
