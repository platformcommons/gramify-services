package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.LocalArtFormTypeService;


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

public class LocalArtFormTypeServiceImpl implements LocalArtFormTypeService {

    private final LocalArtFormTypeDTOAssembler assembler;
    private final LocalArtFormTypeRepository repository;


    public LocalArtFormTypeServiceImpl(
        LocalArtFormTypeRepository repository, LocalArtFormTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new LocalArtFormType.
     *
     * @param LocalArtFormType the LocalArtFormType DTO to save
     * @return the saved LocalArtFormType DTO
     */
    @Transactional
    @Override
    public LocalArtFormTypeDTO save(LocalArtFormTypeDTO LocalArtFormType) {
        log.debug("Entry - save(LocalArtFormType={})", LocalArtFormType);
        LocalArtFormType = preHookSave(LocalArtFormType);
        LocalArtFormType entity = assembler.fromDTO(LocalArtFormType);
        LocalArtFormTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing LocalArtFormType.
     *
     * @param LocalArtFormType the LocalArtFormType DTO to update
     * @return the updated LocalArtFormType DTO
     * @throws NotFoundException if the LocalArtFormType is not found
     */
    @Transactional
    @Override
    public LocalArtFormTypeDTO update(LocalArtFormTypeDTO LocalArtFormType) {
        log.debug("Entry - update(LocalArtFormType={})", LocalArtFormType);
        LocalArtFormType = preHookUpdate(LocalArtFormType);
        LocalArtFormType saved = repository.findById(LocalArtFormType.getId()).orElseThrow(() -> new NotFoundException("LocalArtFormType not found"));
        saved.update(assembler.fromDTO(LocalArtFormType));
        saved = repository.save(saved);
        LocalArtFormTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of LocalArtFormTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing LocalArtFormType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<LocalArtFormTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<LocalArtFormType> result = repository.findAll(PageRequest.of(page, size));
        Set<LocalArtFormTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<LocalArtFormTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a LocalArtFormType by ID.
     *
     * @param id     the ID of the LocalArtFormType to delete
     * @param reason the reason for deletion
     * @return the deleted LocalArtFormType DTO
     * @throws NotFoundException if the LocalArtFormType is not found
     */
    @Transactional
    @Override
    public LocalArtFormTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        LocalArtFormType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("LocalArtFormType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        LocalArtFormTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a LocalArtFormType by ID.
     *
     * @param id the ID of the LocalArtFormType to retrieve
     * @return the LocalArtFormType DTO
     * @throws NotFoundException if the LocalArtFormType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public LocalArtFormTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        LocalArtFormType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("LocalArtFormType not found"));
        LocalArtFormTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<LocalArtFormTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<LocalArtFormType> savedData = repository.findAllById(ids);
        Set<LocalArtFormTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected LocalArtFormTypeDTO postHookSave(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected LocalArtFormTypeDTO preHookSave(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected LocalArtFormTypeDTO postHookUpdate(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected LocalArtFormTypeDTO preHookUpdate(LocalArtFormTypeDTO LocalArtFormTypeDTO) {
        return LocalArtFormTypeDTO;
    }

    protected LocalArtFormTypeDTO postHookDelete(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected LocalArtFormTypeDTO postHookGetById(LocalArtFormTypeDTO dto) {
        return dto;
    }

    protected PageDTO<LocalArtFormTypeDTO> postHookGetAll(PageDTO<LocalArtFormTypeDTO> result) {
        return result;
    }




}
