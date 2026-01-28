package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ConsumptionService;


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

public class ConsumptionServiceImpl implements ConsumptionService {

    private final ConsumptionDTOAssembler assembler;
    private final ConsumptionRepository repository;


    public ConsumptionServiceImpl(
        ConsumptionRepository repository, ConsumptionDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new Consumption.
     *
     * @param Consumption the Consumption DTO to save
     * @return the saved Consumption DTO
     */
    @Transactional
    @Override
    public ConsumptionDTO save(ConsumptionDTO Consumption) {
        log.debug("Entry - save(Consumption={})", Consumption);
        Consumption = preHookSave(Consumption);
        Consumption entity = assembler.fromDTO(Consumption);
        ConsumptionDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing Consumption.
     *
     * @param Consumption the Consumption DTO to update
     * @return the updated Consumption DTO
     * @throws NotFoundException if the Consumption is not found
     */
    @Transactional
    @Override
    public ConsumptionDTO update(ConsumptionDTO Consumption) {
        log.debug("Entry - update(Consumption={})", Consumption);
        Consumption = preHookUpdate(Consumption);
        Consumption saved = repository.findById(Consumption.getId()).orElseThrow(() -> new NotFoundException("Consumption not found"));
        saved.update(assembler.fromDTO(Consumption));
        saved = repository.save(saved);
        ConsumptionDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of Consumptions.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing Consumption DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ConsumptionDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<Consumption> result = repository.findAll(PageRequest.of(page, size));
        Set<ConsumptionDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ConsumptionDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a Consumption by ID.
     *
     * @param id     the ID of the Consumption to delete
     * @param reason the reason for deletion
     * @return the deleted Consumption DTO
     * @throws NotFoundException if the Consumption is not found
     */
    @Transactional
    @Override
    public ConsumptionDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        Consumption saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Consumption not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ConsumptionDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a Consumption by ID.
     *
     * @param id the ID of the Consumption to retrieve
     * @return the Consumption DTO
     * @throws NotFoundException if the Consumption is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ConsumptionDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        Consumption saved = repository.findById(id).orElseThrow(() -> new NotFoundException("Consumption not found"));
        ConsumptionDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ConsumptionDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<Consumption> savedData = repository.findAllById(ids);
        Set<ConsumptionDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ConsumptionDTO postHookSave(ConsumptionDTO dto) {
        return dto;
    }

    protected ConsumptionDTO preHookSave(ConsumptionDTO dto) {
        return dto;
    }

    protected ConsumptionDTO postHookUpdate(ConsumptionDTO dto) {
        return dto;
    }

    protected ConsumptionDTO preHookUpdate(ConsumptionDTO ConsumptionDTO) {
        return ConsumptionDTO;
    }

    protected ConsumptionDTO postHookDelete(ConsumptionDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ConsumptionDTO postHookGetById(ConsumptionDTO dto) {
        return dto;
    }

    protected PageDTO<ConsumptionDTO> postHookGetAll(PageDTO<ConsumptionDTO> result) {
        return result;
    }




}
