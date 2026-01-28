package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageSkillShortageTypeService;


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

public class VillageSkillShortageTypeServiceImpl implements VillageSkillShortageTypeService {

    private final VillageSkillShortageTypeDTOAssembler assembler;
    private final VillageSkillShortageTypeRepository repository;


    public VillageSkillShortageTypeServiceImpl(
        VillageSkillShortageTypeRepository repository, VillageSkillShortageTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageSkillShortageType.
     *
     * @param VillageSkillShortageType the VillageSkillShortageType DTO to save
     * @return the saved VillageSkillShortageType DTO
     */
    @Transactional
    @Override
    public VillageSkillShortageTypeDTO save(VillageSkillShortageTypeDTO VillageSkillShortageType) {
        log.debug("Entry - save(VillageSkillShortageType={})", VillageSkillShortageType);
        VillageSkillShortageType = preHookSave(VillageSkillShortageType);
        VillageSkillShortageType entity = assembler.fromDTO(VillageSkillShortageType);
        VillageSkillShortageTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageSkillShortageType.
     *
     * @param VillageSkillShortageType the VillageSkillShortageType DTO to update
     * @return the updated VillageSkillShortageType DTO
     * @throws NotFoundException if the VillageSkillShortageType is not found
     */
    @Transactional
    @Override
    public VillageSkillShortageTypeDTO update(VillageSkillShortageTypeDTO VillageSkillShortageType) {
        log.debug("Entry - update(VillageSkillShortageType={})", VillageSkillShortageType);
        VillageSkillShortageType = preHookUpdate(VillageSkillShortageType);
        VillageSkillShortageType saved = repository.findById(VillageSkillShortageType.getId()).orElseThrow(() -> new NotFoundException("VillageSkillShortageType not found"));
        saved.update(assembler.fromDTO(VillageSkillShortageType));
        saved = repository.save(saved);
        VillageSkillShortageTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageSkillShortageTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageSkillShortageType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageSkillShortageTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageSkillShortageType> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageSkillShortageTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageSkillShortageTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageSkillShortageType by ID.
     *
     * @param id     the ID of the VillageSkillShortageType to delete
     * @param reason the reason for deletion
     * @return the deleted VillageSkillShortageType DTO
     * @throws NotFoundException if the VillageSkillShortageType is not found
     */
    @Transactional
    @Override
    public VillageSkillShortageTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageSkillShortageType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSkillShortageType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageSkillShortageTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageSkillShortageType by ID.
     *
     * @param id the ID of the VillageSkillShortageType to retrieve
     * @return the VillageSkillShortageType DTO
     * @throws NotFoundException if the VillageSkillShortageType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageSkillShortageTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageSkillShortageType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSkillShortageType not found"));
        VillageSkillShortageTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageSkillShortageTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageSkillShortageType> savedData = repository.findAllById(ids);
        Set<VillageSkillShortageTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageSkillShortageTypeDTO postHookSave(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected VillageSkillShortageTypeDTO preHookSave(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected VillageSkillShortageTypeDTO postHookUpdate(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected VillageSkillShortageTypeDTO preHookUpdate(VillageSkillShortageTypeDTO VillageSkillShortageTypeDTO) {
        return VillageSkillShortageTypeDTO;
    }

    protected VillageSkillShortageTypeDTO postHookDelete(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSkillShortageTypeDTO postHookGetById(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSkillShortageTypeDTO> postHookGetAll(PageDTO<VillageSkillShortageTypeDTO> result) {
        return result;
    }




}
