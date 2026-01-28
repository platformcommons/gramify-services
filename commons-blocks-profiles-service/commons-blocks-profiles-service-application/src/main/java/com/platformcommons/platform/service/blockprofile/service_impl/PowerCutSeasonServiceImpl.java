package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.PowerCutSeasonService;


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

public class PowerCutSeasonServiceImpl implements PowerCutSeasonService {

    private final PowerCutSeasonDTOAssembler assembler;
    private final PowerCutSeasonRepository repository;


    public PowerCutSeasonServiceImpl(
        PowerCutSeasonRepository repository, PowerCutSeasonDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new PowerCutSeason.
     *
     * @param PowerCutSeason the PowerCutSeason DTO to save
     * @return the saved PowerCutSeason DTO
     */
    @Transactional
    @Override
    public PowerCutSeasonDTO save(PowerCutSeasonDTO PowerCutSeason) {
        log.debug("Entry - save(PowerCutSeason={})", PowerCutSeason);
        PowerCutSeason = preHookSave(PowerCutSeason);
        PowerCutSeason entity = assembler.fromDTO(PowerCutSeason);
        PowerCutSeasonDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing PowerCutSeason.
     *
     * @param PowerCutSeason the PowerCutSeason DTO to update
     * @return the updated PowerCutSeason DTO
     * @throws NotFoundException if the PowerCutSeason is not found
     */
    @Transactional
    @Override
    public PowerCutSeasonDTO update(PowerCutSeasonDTO PowerCutSeason) {
        log.debug("Entry - update(PowerCutSeason={})", PowerCutSeason);
        PowerCutSeason = preHookUpdate(PowerCutSeason);
        PowerCutSeason saved = repository.findById(PowerCutSeason.getId()).orElseThrow(() -> new NotFoundException("PowerCutSeason not found"));
        saved.update(assembler.fromDTO(PowerCutSeason));
        saved = repository.save(saved);
        PowerCutSeasonDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of PowerCutSeasons.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing PowerCutSeason DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<PowerCutSeasonDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<PowerCutSeason> result = repository.findAll(PageRequest.of(page, size));
        Set<PowerCutSeasonDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<PowerCutSeasonDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a PowerCutSeason by ID.
     *
     * @param id     the ID of the PowerCutSeason to delete
     * @param reason the reason for deletion
     * @return the deleted PowerCutSeason DTO
     * @throws NotFoundException if the PowerCutSeason is not found
     */
    @Transactional
    @Override
    public PowerCutSeasonDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        PowerCutSeason saved = repository.findById(id).orElseThrow(() -> new NotFoundException("PowerCutSeason not found"));
        saved.deactivate(reason);
        repository.save(saved);
        PowerCutSeasonDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a PowerCutSeason by ID.
     *
     * @param id the ID of the PowerCutSeason to retrieve
     * @return the PowerCutSeason DTO
     * @throws NotFoundException if the PowerCutSeason is not found
     */
    @Transactional(readOnly = true)
    @Override
    public PowerCutSeasonDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        PowerCutSeason saved = repository.findById(id).orElseThrow(() -> new NotFoundException("PowerCutSeason not found"));
        PowerCutSeasonDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<PowerCutSeasonDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<PowerCutSeason> savedData = repository.findAllById(ids);
        Set<PowerCutSeasonDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected PowerCutSeasonDTO postHookSave(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PowerCutSeasonDTO preHookSave(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PowerCutSeasonDTO postHookUpdate(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PowerCutSeasonDTO preHookUpdate(PowerCutSeasonDTO PowerCutSeasonDTO) {
        return PowerCutSeasonDTO;
    }

    protected PowerCutSeasonDTO postHookDelete(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected PowerCutSeasonDTO postHookGetById(PowerCutSeasonDTO dto) {
        return dto;
    }

    protected PageDTO<PowerCutSeasonDTO> postHookGetAll(PageDTO<PowerCutSeasonDTO> result) {
        return result;
    }




}
