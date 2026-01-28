package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.KeyConstraintsForSurplusExportService;


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

public class KeyConstraintsForSurplusExportServiceImpl implements KeyConstraintsForSurplusExportService {

    private final KeyConstraintsForSurplusExportDTOAssembler assembler;
    private final KeyConstraintsForSurplusExportRepository repository;


    public KeyConstraintsForSurplusExportServiceImpl(
        KeyConstraintsForSurplusExportRepository repository, KeyConstraintsForSurplusExportDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new KeyConstraintsForSurplusExport.
     *
     * @param KeyConstraintsForSurplusExport the KeyConstraintsForSurplusExport DTO to save
     * @return the saved KeyConstraintsForSurplusExport DTO
     */
    @Transactional
    @Override
    public KeyConstraintsForSurplusExportDTO save(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExport) {
        log.debug("Entry - save(KeyConstraintsForSurplusExport={})", KeyConstraintsForSurplusExport);
        KeyConstraintsForSurplusExport = preHookSave(KeyConstraintsForSurplusExport);
        KeyConstraintsForSurplusExport entity = assembler.fromDTO(KeyConstraintsForSurplusExport);
        KeyConstraintsForSurplusExportDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing KeyConstraintsForSurplusExport.
     *
     * @param KeyConstraintsForSurplusExport the KeyConstraintsForSurplusExport DTO to update
     * @return the updated KeyConstraintsForSurplusExport DTO
     * @throws NotFoundException if the KeyConstraintsForSurplusExport is not found
     */
    @Transactional
    @Override
    public KeyConstraintsForSurplusExportDTO update(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExport) {
        log.debug("Entry - update(KeyConstraintsForSurplusExport={})", KeyConstraintsForSurplusExport);
        KeyConstraintsForSurplusExport = preHookUpdate(KeyConstraintsForSurplusExport);
        KeyConstraintsForSurplusExport saved = repository.findById(KeyConstraintsForSurplusExport.getId()).orElseThrow(() -> new NotFoundException("KeyConstraintsForSurplusExport not found"));
        saved.update(assembler.fromDTO(KeyConstraintsForSurplusExport));
        saved = repository.save(saved);
        KeyConstraintsForSurplusExportDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of KeyConstraintsForSurplusExports.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing KeyConstraintsForSurplusExport DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<KeyConstraintsForSurplusExportDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<KeyConstraintsForSurplusExport> result = repository.findAll(PageRequest.of(page, size));
        Set<KeyConstraintsForSurplusExportDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<KeyConstraintsForSurplusExportDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a KeyConstraintsForSurplusExport by ID.
     *
     * @param id     the ID of the KeyConstraintsForSurplusExport to delete
     * @param reason the reason for deletion
     * @return the deleted KeyConstraintsForSurplusExport DTO
     * @throws NotFoundException if the KeyConstraintsForSurplusExport is not found
     */
    @Transactional
    @Override
    public KeyConstraintsForSurplusExportDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        KeyConstraintsForSurplusExport saved = repository.findById(id).orElseThrow(() -> new NotFoundException("KeyConstraintsForSurplusExport not found"));
        saved.deactivate(reason);
        repository.save(saved);
        KeyConstraintsForSurplusExportDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a KeyConstraintsForSurplusExport by ID.
     *
     * @param id the ID of the KeyConstraintsForSurplusExport to retrieve
     * @return the KeyConstraintsForSurplusExport DTO
     * @throws NotFoundException if the KeyConstraintsForSurplusExport is not found
     */
    @Transactional(readOnly = true)
    @Override
    public KeyConstraintsForSurplusExportDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        KeyConstraintsForSurplusExport saved = repository.findById(id).orElseThrow(() -> new NotFoundException("KeyConstraintsForSurplusExport not found"));
        KeyConstraintsForSurplusExportDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<KeyConstraintsForSurplusExportDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<KeyConstraintsForSurplusExport> savedData = repository.findAllById(ids);
        Set<KeyConstraintsForSurplusExportDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected KeyConstraintsForSurplusExportDTO postHookSave(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected KeyConstraintsForSurplusExportDTO preHookSave(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected KeyConstraintsForSurplusExportDTO postHookUpdate(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected KeyConstraintsForSurplusExportDTO preHookUpdate(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO) {
        return KeyConstraintsForSurplusExportDTO;
    }

    protected KeyConstraintsForSurplusExportDTO postHookDelete(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected KeyConstraintsForSurplusExportDTO postHookGetById(KeyConstraintsForSurplusExportDTO dto) {
        return dto;
    }

    protected PageDTO<KeyConstraintsForSurplusExportDTO> postHookGetAll(PageDTO<KeyConstraintsForSurplusExportDTO> result) {
        return result;
    }




}
