package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.CropService;


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

public class CropServiceImpl implements CropService {

    private final CropDTOAssembler assembler;
    private final CropRepository repository;


    public CropServiceImpl(
        CropRepository repository, CropDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new Crop.
     *
     * @param Crop the Crop DTO to save
     * @return the saved Crop DTO
     */
    @Transactional
    @Override
    public CropDTO save(CropDTO Crop) {
        log.debug("Entry - save(Crop={})", Crop);
        Crop = preHookSave(Crop);
        Crop entity = assembler.fromDTO(Crop);
        CropDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing Crop.
     *
     * @param Crop the Crop DTO to update
     * @return the updated Crop DTO
     * @throws NotFoundException if the Crop is not found
     */
    @Transactional
    @Override
    public CropDTO update(CropDTO Crop) {
        log.debug("Entry - update(Crop={})", Crop);
        Crop = preHookUpdate(Crop);
        Crop saved = repository.findById(Crop.getId()).orElseThrow(() -> new NotFoundException("Crop not found"));
        saved.update(assembler.fromDTO(Crop));
        saved = repository.save(saved);
        CropDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of Crops.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing Crop DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CropDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<Crop> result = repository.findAll(PageRequest.of(page, size));
        Set<CropDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<CropDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a Crop by ID.
     *
     * @param id     the ID of the Crop to delete
     * @param reason the reason for deletion
     * @return the deleted Crop DTO
     * @throws NotFoundException if the Crop is not found
     */
    @Transactional
    @Override
    public CropDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        Crop saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Crop not found"));
        saved.deactivate(reason);
        repository.save(saved);
        CropDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a Crop by ID.
     *
     * @param id the ID of the Crop to retrieve
     * @return the Crop DTO
     * @throws NotFoundException if the Crop is not found
     */
    @Transactional(readOnly = true)
    @Override
    public CropDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        Crop saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Crop not found"));
        CropDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<CropDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<Crop> savedData = repository.findAllById(ids);
        Set<CropDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CropDTO postHookSave(CropDTO dto) {
        return dto;
    }

    protected CropDTO preHookSave(CropDTO dto) {
        return dto;
    }

    protected CropDTO postHookUpdate(CropDTO dto) {
        return dto;
    }

    protected CropDTO preHookUpdate(CropDTO CropDTO) {
        return CropDTO;
    }

    protected CropDTO postHookDelete(CropDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CropDTO postHookGetById(CropDTO dto) {
        return dto;
    }

    protected PageDTO<CropDTO> postHookGetAll(PageDTO<CropDTO> result) {
        return result;
    }




}
