package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageForestProduceTypeService;


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

public class VillageForestProduceTypeServiceImpl implements VillageForestProduceTypeService {

    private final VillageForestProduceTypeDTOAssembler assembler;
    private final VillageForestProduceTypeRepository repository;


    public VillageForestProduceTypeServiceImpl(
        VillageForestProduceTypeRepository repository, VillageForestProduceTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageForestProduceType.
     *
     * @param VillageForestProduceType the VillageForestProduceType DTO to save
     * @return the saved VillageForestProduceType DTO
     */
    @Transactional
    @Override
    public VillageForestProduceTypeDTO save(VillageForestProduceTypeDTO VillageForestProduceType) {
        log.debug("Entry - save(VillageForestProduceType={})", VillageForestProduceType);
        VillageForestProduceType = preHookSave(VillageForestProduceType);
        VillageForestProduceType entity = assembler.fromDTO(VillageForestProduceType);
        VillageForestProduceTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageForestProduceType.
     *
     * @param VillageForestProduceType the VillageForestProduceType DTO to update
     * @return the updated VillageForestProduceType DTO
     * @throws NotFoundException if the VillageForestProduceType is not found
     */
    @Transactional
    @Override
    public VillageForestProduceTypeDTO update(VillageForestProduceTypeDTO VillageForestProduceType) {
        log.debug("Entry - update(VillageForestProduceType={})", VillageForestProduceType);
        VillageForestProduceType = preHookUpdate(VillageForestProduceType);
        VillageForestProduceType saved = repository.findById(VillageForestProduceType.getId()).orElseThrow(() -> new NotFoundException("VillageForestProduceType not found"));
        saved.update(assembler.fromDTO(VillageForestProduceType));
        saved = repository.save(saved);
        VillageForestProduceTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageForestProduceTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageForestProduceType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageForestProduceTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageForestProduceType> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageForestProduceTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageForestProduceTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageForestProduceType by ID.
     *
     * @param id     the ID of the VillageForestProduceType to delete
     * @param reason the reason for deletion
     * @return the deleted VillageForestProduceType DTO
     * @throws NotFoundException if the VillageForestProduceType is not found
     */
    @Transactional
    @Override
    public VillageForestProduceTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageForestProduceType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageForestProduceType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageForestProduceTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageForestProduceType by ID.
     *
     * @param id the ID of the VillageForestProduceType to retrieve
     * @return the VillageForestProduceType DTO
     * @throws NotFoundException if the VillageForestProduceType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageForestProduceTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageForestProduceType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageForestProduceType not found"));
        VillageForestProduceTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageForestProduceTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageForestProduceType> savedData = repository.findAllById(ids);
        Set<VillageForestProduceTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageForestProduceTypeDTO postHookSave(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected VillageForestProduceTypeDTO preHookSave(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected VillageForestProduceTypeDTO postHookUpdate(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected VillageForestProduceTypeDTO preHookUpdate(VillageForestProduceTypeDTO VillageForestProduceTypeDTO) {
        return VillageForestProduceTypeDTO;
    }

    protected VillageForestProduceTypeDTO postHookDelete(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageForestProduceTypeDTO postHookGetById(VillageForestProduceTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageForestProduceTypeDTO> postHookGetAll(PageDTO<VillageForestProduceTypeDTO> result) {
        return result;
    }




}
