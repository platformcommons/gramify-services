package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.SeasonalityOfSurplusService;


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

public class SeasonalityOfSurplusServiceImpl implements SeasonalityOfSurplusService {

    private final SeasonalityOfSurplusDTOAssembler assembler;
    private final SeasonalityOfSurplusRepository repository;


    public SeasonalityOfSurplusServiceImpl(
        SeasonalityOfSurplusRepository repository, SeasonalityOfSurplusDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new SeasonalityOfSurplus.
     *
     * @param SeasonalityOfSurplus the SeasonalityOfSurplus DTO to save
     * @return the saved SeasonalityOfSurplus DTO
     */
    @Transactional
    @Override
    public SeasonalityOfSurplusDTO save(SeasonalityOfSurplusDTO SeasonalityOfSurplus) {
        log.debug("Entry - save(SeasonalityOfSurplus={})", SeasonalityOfSurplus);
        SeasonalityOfSurplus = preHookSave(SeasonalityOfSurplus);
        SeasonalityOfSurplus entity = assembler.fromDTO(SeasonalityOfSurplus);
        SeasonalityOfSurplusDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing SeasonalityOfSurplus.
     *
     * @param SeasonalityOfSurplus the SeasonalityOfSurplus DTO to update
     * @return the updated SeasonalityOfSurplus DTO
     * @throws NotFoundException if the SeasonalityOfSurplus is not found
     */
    @Transactional
    @Override
    public SeasonalityOfSurplusDTO update(SeasonalityOfSurplusDTO SeasonalityOfSurplus) {
        log.debug("Entry - update(SeasonalityOfSurplus={})", SeasonalityOfSurplus);
        SeasonalityOfSurplus = preHookUpdate(SeasonalityOfSurplus);
        SeasonalityOfSurplus saved = repository.findById(SeasonalityOfSurplus.getId()).orElseThrow(() -> new NotFoundException("SeasonalityOfSurplus not found"));
        saved.update(assembler.fromDTO(SeasonalityOfSurplus));
        saved = repository.save(saved);
        SeasonalityOfSurplusDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of SeasonalityOfSurpluss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing SeasonalityOfSurplus DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<SeasonalityOfSurplusDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<SeasonalityOfSurplus> result = repository.findAll(PageRequest.of(page, size));
        Set<SeasonalityOfSurplusDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<SeasonalityOfSurplusDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a SeasonalityOfSurplus by ID.
     *
     * @param id     the ID of the SeasonalityOfSurplus to delete
     * @param reason the reason for deletion
     * @return the deleted SeasonalityOfSurplus DTO
     * @throws NotFoundException if the SeasonalityOfSurplus is not found
     */
    @Transactional
    @Override
    public SeasonalityOfSurplusDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        SeasonalityOfSurplus saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SeasonalityOfSurplus not found"));
        saved.deactivate(reason);
        repository.save(saved);
        SeasonalityOfSurplusDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a SeasonalityOfSurplus by ID.
     *
     * @param id the ID of the SeasonalityOfSurplus to retrieve
     * @return the SeasonalityOfSurplus DTO
     * @throws NotFoundException if the SeasonalityOfSurplus is not found
     */
    @Transactional(readOnly = true)
    @Override
    public SeasonalityOfSurplusDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        SeasonalityOfSurplus saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SeasonalityOfSurplus not found"));
        SeasonalityOfSurplusDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<SeasonalityOfSurplusDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<SeasonalityOfSurplus> savedData = repository.findAllById(ids);
        Set<SeasonalityOfSurplusDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SeasonalityOfSurplusDTO postHookSave(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected SeasonalityOfSurplusDTO preHookSave(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected SeasonalityOfSurplusDTO postHookUpdate(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected SeasonalityOfSurplusDTO preHookUpdate(SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO) {
        return SeasonalityOfSurplusDTO;
    }

    protected SeasonalityOfSurplusDTO postHookDelete(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SeasonalityOfSurplusDTO postHookGetById(SeasonalityOfSurplusDTO dto) {
        return dto;
    }

    protected PageDTO<SeasonalityOfSurplusDTO> postHookGetAll(PageDTO<SeasonalityOfSurplusDTO> result) {
        return result;
    }




}
