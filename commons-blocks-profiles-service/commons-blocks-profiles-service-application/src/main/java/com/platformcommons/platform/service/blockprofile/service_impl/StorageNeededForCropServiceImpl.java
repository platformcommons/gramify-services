package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.StorageNeededForCropService;


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

public class StorageNeededForCropServiceImpl implements StorageNeededForCropService {

    private final StorageNeededForCropDTOAssembler assembler;
    private final StorageNeededForCropRepository repository;


    public StorageNeededForCropServiceImpl(
        StorageNeededForCropRepository repository, StorageNeededForCropDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new StorageNeededForCrop.
     *
     * @param StorageNeededForCrop the StorageNeededForCrop DTO to save
     * @return the saved StorageNeededForCrop DTO
     */
    @Transactional
    @Override
    public StorageNeededForCropDTO save(StorageNeededForCropDTO StorageNeededForCrop) {
        log.debug("Entry - save(StorageNeededForCrop={})", StorageNeededForCrop);
        StorageNeededForCrop = preHookSave(StorageNeededForCrop);
        StorageNeededForCrop entity = assembler.fromDTO(StorageNeededForCrop);
        StorageNeededForCropDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing StorageNeededForCrop.
     *
     * @param StorageNeededForCrop the StorageNeededForCrop DTO to update
     * @return the updated StorageNeededForCrop DTO
     * @throws NotFoundException if the StorageNeededForCrop is not found
     */
    @Transactional
    @Override
    public StorageNeededForCropDTO update(StorageNeededForCropDTO StorageNeededForCrop) {
        log.debug("Entry - update(StorageNeededForCrop={})", StorageNeededForCrop);
        StorageNeededForCrop = preHookUpdate(StorageNeededForCrop);
        StorageNeededForCrop saved = repository.findById(StorageNeededForCrop.getId()).orElseThrow(() -> new NotFoundException("StorageNeededForCrop not found"));
        saved.update(assembler.fromDTO(StorageNeededForCrop));
        saved = repository.save(saved);
        StorageNeededForCropDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of StorageNeededForCrops.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing StorageNeededForCrop DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<StorageNeededForCropDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<StorageNeededForCrop> result = repository.findAll(PageRequest.of(page, size));
        Set<StorageNeededForCropDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<StorageNeededForCropDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a StorageNeededForCrop by ID.
     *
     * @param id     the ID of the StorageNeededForCrop to delete
     * @param reason the reason for deletion
     * @return the deleted StorageNeededForCrop DTO
     * @throws NotFoundException if the StorageNeededForCrop is not found
     */
    @Transactional
    @Override
    public StorageNeededForCropDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        StorageNeededForCrop saved = repository.findById(id).orElseThrow(() -> new NotFoundException("StorageNeededForCrop not found"));
        saved.deactivate(reason);
        repository.save(saved);
        StorageNeededForCropDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a StorageNeededForCrop by ID.
     *
     * @param id the ID of the StorageNeededForCrop to retrieve
     * @return the StorageNeededForCrop DTO
     * @throws NotFoundException if the StorageNeededForCrop is not found
     */
    @Transactional(readOnly = true)
    @Override
    public StorageNeededForCropDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        StorageNeededForCrop saved = repository.findById(id).orElseThrow(() -> new NotFoundException("StorageNeededForCrop not found"));
        StorageNeededForCropDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<StorageNeededForCropDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<StorageNeededForCrop> savedData = repository.findAllById(ids);
        Set<StorageNeededForCropDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected StorageNeededForCropDTO postHookSave(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected StorageNeededForCropDTO preHookSave(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected StorageNeededForCropDTO postHookUpdate(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected StorageNeededForCropDTO preHookUpdate(StorageNeededForCropDTO StorageNeededForCropDTO) {
        return StorageNeededForCropDTO;
    }

    protected StorageNeededForCropDTO postHookDelete(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected StorageNeededForCropDTO postHookGetById(StorageNeededForCropDTO dto) {
        return dto;
    }

    protected PageDTO<StorageNeededForCropDTO> postHookGetAll(PageDTO<StorageNeededForCropDTO> result) {
        return result;
    }




}
