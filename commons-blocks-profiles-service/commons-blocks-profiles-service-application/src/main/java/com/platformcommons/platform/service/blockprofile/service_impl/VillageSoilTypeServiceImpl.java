package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageSoilTypeService;


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

public class VillageSoilTypeServiceImpl implements VillageSoilTypeService {

    private final VillageSoilTypeDTOAssembler assembler;
    private final VillageSoilTypeRepository repository;


    public VillageSoilTypeServiceImpl(
        VillageSoilTypeRepository repository, VillageSoilTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageSoilType.
     *
     * @param VillageSoilType the VillageSoilType DTO to save
     * @return the saved VillageSoilType DTO
     */
    @Transactional
    @Override
    public VillageSoilTypeDTO save(VillageSoilTypeDTO VillageSoilType) {
        log.debug("Entry - save(VillageSoilType={})", VillageSoilType);
        VillageSoilType = preHookSave(VillageSoilType);
        VillageSoilType entity = assembler.fromDTO(VillageSoilType);
        VillageSoilTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageSoilType.
     *
     * @param VillageSoilType the VillageSoilType DTO to update
     * @return the updated VillageSoilType DTO
     * @throws NotFoundException if the VillageSoilType is not found
     */
    @Transactional
    @Override
    public VillageSoilTypeDTO update(VillageSoilTypeDTO VillageSoilType) {
        log.debug("Entry - update(VillageSoilType={})", VillageSoilType);
        VillageSoilType = preHookUpdate(VillageSoilType);
        VillageSoilType saved = repository.findById(VillageSoilType.getId()).orElseThrow(() -> new NotFoundException("VillageSoilType not found"));
        saved.update(assembler.fromDTO(VillageSoilType));
        saved = repository.save(saved);
        VillageSoilTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageSoilTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageSoilType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageSoilTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageSoilType> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageSoilTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageSoilTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageSoilType by ID.
     *
     * @param id     the ID of the VillageSoilType to delete
     * @param reason the reason for deletion
     * @return the deleted VillageSoilType DTO
     * @throws NotFoundException if the VillageSoilType is not found
     */
    @Transactional
    @Override
    public VillageSoilTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageSoilType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSoilType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageSoilTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageSoilType by ID.
     *
     * @param id the ID of the VillageSoilType to retrieve
     * @return the VillageSoilType DTO
     * @throws NotFoundException if the VillageSoilType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageSoilTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageSoilType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSoilType not found"));
        VillageSoilTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageSoilTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageSoilType> savedData = repository.findAllById(ids);
        Set<VillageSoilTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageSoilTypeDTO postHookSave(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected VillageSoilTypeDTO preHookSave(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected VillageSoilTypeDTO postHookUpdate(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected VillageSoilTypeDTO preHookUpdate(VillageSoilTypeDTO VillageSoilTypeDTO) {
        return VillageSoilTypeDTO;
    }

    protected VillageSoilTypeDTO postHookDelete(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSoilTypeDTO postHookGetById(VillageSoilTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSoilTypeDTO> postHookGetAll(PageDTO<VillageSoilTypeDTO> result) {
        return result;
    }




}
