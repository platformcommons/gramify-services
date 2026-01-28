package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ExportPotentialSurplusProduceTyService;


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

public class ExportPotentialSurplusProduceTyServiceImpl implements ExportPotentialSurplusProduceTyService {

    private final ExportPotentialSurplusProduceTyDTOAssembler assembler;
    private final ExportPotentialSurplusProduceTyRepository repository;


    public ExportPotentialSurplusProduceTyServiceImpl(
        ExportPotentialSurplusProduceTyRepository repository, ExportPotentialSurplusProduceTyDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ExportPotentialSurplusProduceTy.
     *
     * @param ExportPotentialSurplusProduceTy the ExportPotentialSurplusProduceTy DTO to save
     * @return the saved ExportPotentialSurplusProduceTy DTO
     */
    @Transactional
    @Override
    public ExportPotentialSurplusProduceTyDTO save(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTy) {
        log.debug("Entry - save(ExportPotentialSurplusProduceTy={})", ExportPotentialSurplusProduceTy);
        ExportPotentialSurplusProduceTy = preHookSave(ExportPotentialSurplusProduceTy);
        ExportPotentialSurplusProduceTy entity = assembler.fromDTO(ExportPotentialSurplusProduceTy);
        ExportPotentialSurplusProduceTyDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ExportPotentialSurplusProduceTy.
     *
     * @param ExportPotentialSurplusProduceTy the ExportPotentialSurplusProduceTy DTO to update
     * @return the updated ExportPotentialSurplusProduceTy DTO
     * @throws NotFoundException if the ExportPotentialSurplusProduceTy is not found
     */
    @Transactional
    @Override
    public ExportPotentialSurplusProduceTyDTO update(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTy) {
        log.debug("Entry - update(ExportPotentialSurplusProduceTy={})", ExportPotentialSurplusProduceTy);
        ExportPotentialSurplusProduceTy = preHookUpdate(ExportPotentialSurplusProduceTy);
        ExportPotentialSurplusProduceTy saved = repository.findById(ExportPotentialSurplusProduceTy.getId()).orElseThrow(() -> new NotFoundException("ExportPotentialSurplusProduceTy not found"));
        saved.update(assembler.fromDTO(ExportPotentialSurplusProduceTy));
        saved = repository.save(saved);
        ExportPotentialSurplusProduceTyDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ExportPotentialSurplusProduceTys.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ExportPotentialSurplusProduceTy DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ExportPotentialSurplusProduceTyDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ExportPotentialSurplusProduceTy> result = repository.findAll(PageRequest.of(page, size));
        Set<ExportPotentialSurplusProduceTyDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ExportPotentialSurplusProduceTyDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ExportPotentialSurplusProduceTy by ID.
     *
     * @param id     the ID of the ExportPotentialSurplusProduceTy to delete
     * @param reason the reason for deletion
     * @return the deleted ExportPotentialSurplusProduceTy DTO
     * @throws NotFoundException if the ExportPotentialSurplusProduceTy is not found
     */
    @Transactional
    @Override
    public ExportPotentialSurplusProduceTyDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ExportPotentialSurplusProduceTy saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ExportPotentialSurplusProduceTy not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ExportPotentialSurplusProduceTyDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ExportPotentialSurplusProduceTy by ID.
     *
     * @param id the ID of the ExportPotentialSurplusProduceTy to retrieve
     * @return the ExportPotentialSurplusProduceTy DTO
     * @throws NotFoundException if the ExportPotentialSurplusProduceTy is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ExportPotentialSurplusProduceTyDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ExportPotentialSurplusProduceTy saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ExportPotentialSurplusProduceTy not found"));
        ExportPotentialSurplusProduceTyDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ExportPotentialSurplusProduceTyDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ExportPotentialSurplusProduceTy> savedData = repository.findAllById(ids);
        Set<ExportPotentialSurplusProduceTyDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ExportPotentialSurplusProduceTyDTO postHookSave(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected ExportPotentialSurplusProduceTyDTO preHookSave(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected ExportPotentialSurplusProduceTyDTO postHookUpdate(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected ExportPotentialSurplusProduceTyDTO preHookUpdate(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO) {
        return ExportPotentialSurplusProduceTyDTO;
    }

    protected ExportPotentialSurplusProduceTyDTO postHookDelete(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ExportPotentialSurplusProduceTyDTO postHookGetById(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected PageDTO<ExportPotentialSurplusProduceTyDTO> postHookGetAll(PageDTO<ExportPotentialSurplusProduceTyDTO> result) {
        return result;
    }




}
