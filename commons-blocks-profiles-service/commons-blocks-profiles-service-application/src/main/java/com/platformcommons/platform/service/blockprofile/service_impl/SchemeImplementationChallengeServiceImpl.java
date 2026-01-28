package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.SchemeImplementationChallengeService;


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

public class SchemeImplementationChallengeServiceImpl implements SchemeImplementationChallengeService {

    private final SchemeImplementationChallengeDTOAssembler assembler;
    private final SchemeImplementationChallengeRepository repository;


    public SchemeImplementationChallengeServiceImpl(
        SchemeImplementationChallengeRepository repository, SchemeImplementationChallengeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new SchemeImplementationChallenge.
     *
     * @param SchemeImplementationChallenge the SchemeImplementationChallenge DTO to save
     * @return the saved SchemeImplementationChallenge DTO
     */
    @Transactional
    @Override
    public SchemeImplementationChallengeDTO save(SchemeImplementationChallengeDTO SchemeImplementationChallenge) {
        log.debug("Entry - save(SchemeImplementationChallenge={})", SchemeImplementationChallenge);
        SchemeImplementationChallenge = preHookSave(SchemeImplementationChallenge);
        SchemeImplementationChallenge entity = assembler.fromDTO(SchemeImplementationChallenge);
        SchemeImplementationChallengeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing SchemeImplementationChallenge.
     *
     * @param SchemeImplementationChallenge the SchemeImplementationChallenge DTO to update
     * @return the updated SchemeImplementationChallenge DTO
     * @throws NotFoundException if the SchemeImplementationChallenge is not found
     */
    @Transactional
    @Override
    public SchemeImplementationChallengeDTO update(SchemeImplementationChallengeDTO SchemeImplementationChallenge) {
        log.debug("Entry - update(SchemeImplementationChallenge={})", SchemeImplementationChallenge);
        SchemeImplementationChallenge = preHookUpdate(SchemeImplementationChallenge);
        SchemeImplementationChallenge saved = repository.findById(SchemeImplementationChallenge.getId()).orElseThrow(() -> new NotFoundException("SchemeImplementationChallenge not found"));
        saved.update(assembler.fromDTO(SchemeImplementationChallenge));
        saved = repository.save(saved);
        SchemeImplementationChallengeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of SchemeImplementationChallenges.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing SchemeImplementationChallenge DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<SchemeImplementationChallengeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<SchemeImplementationChallenge> result = repository.findAll(PageRequest.of(page, size));
        Set<SchemeImplementationChallengeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<SchemeImplementationChallengeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a SchemeImplementationChallenge by ID.
     *
     * @param id     the ID of the SchemeImplementationChallenge to delete
     * @param reason the reason for deletion
     * @return the deleted SchemeImplementationChallenge DTO
     * @throws NotFoundException if the SchemeImplementationChallenge is not found
     */
    @Transactional
    @Override
    public SchemeImplementationChallengeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        SchemeImplementationChallenge saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SchemeImplementationChallenge not found"));
        saved.deactivate(reason);
        repository.save(saved);
        SchemeImplementationChallengeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a SchemeImplementationChallenge by ID.
     *
     * @param id the ID of the SchemeImplementationChallenge to retrieve
     * @return the SchemeImplementationChallenge DTO
     * @throws NotFoundException if the SchemeImplementationChallenge is not found
     */
    @Transactional(readOnly = true)
    @Override
    public SchemeImplementationChallengeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        SchemeImplementationChallenge saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SchemeImplementationChallenge not found"));
        SchemeImplementationChallengeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<SchemeImplementationChallengeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<SchemeImplementationChallenge> savedData = repository.findAllById(ids);
        Set<SchemeImplementationChallengeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SchemeImplementationChallengeDTO postHookSave(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected SchemeImplementationChallengeDTO preHookSave(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected SchemeImplementationChallengeDTO postHookUpdate(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected SchemeImplementationChallengeDTO preHookUpdate(SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO) {
        return SchemeImplementationChallengeDTO;
    }

    protected SchemeImplementationChallengeDTO postHookDelete(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SchemeImplementationChallengeDTO postHookGetById(SchemeImplementationChallengeDTO dto) {
        return dto;
    }

    protected PageDTO<SchemeImplementationChallengeDTO> postHookGetAll(PageDTO<SchemeImplementationChallengeDTO> result) {
        return result;
    }




}
