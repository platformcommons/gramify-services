package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MachinesInDemandService;


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

public class MachinesInDemandServiceImpl implements MachinesInDemandService {

    private final MachinesInDemandDTOAssembler assembler;
    private final MachinesInDemandRepository repository;


    public MachinesInDemandServiceImpl(
        MachinesInDemandRepository repository, MachinesInDemandDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MachinesInDemand.
     *
     * @param MachinesInDemand the MachinesInDemand DTO to save
     * @return the saved MachinesInDemand DTO
     */
    @Transactional
    @Override
    public MachinesInDemandDTO save(MachinesInDemandDTO MachinesInDemand) {
        log.debug("Entry - save(MachinesInDemand={})", MachinesInDemand);
        MachinesInDemand = preHookSave(MachinesInDemand);
        MachinesInDemand entity = assembler.fromDTO(MachinesInDemand);
        MachinesInDemandDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MachinesInDemand.
     *
     * @param MachinesInDemand the MachinesInDemand DTO to update
     * @return the updated MachinesInDemand DTO
     * @throws NotFoundException if the MachinesInDemand is not found
     */
    @Transactional
    @Override
    public MachinesInDemandDTO update(MachinesInDemandDTO MachinesInDemand) {
        log.debug("Entry - update(MachinesInDemand={})", MachinesInDemand);
        MachinesInDemand = preHookUpdate(MachinesInDemand);
        MachinesInDemand saved = repository.findById(MachinesInDemand.getId()).orElseThrow(() -> new NotFoundException("MachinesInDemand not found"));
        saved.update(assembler.fromDTO(MachinesInDemand));
        saved = repository.save(saved);
        MachinesInDemandDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MachinesInDemands.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MachinesInDemand DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MachinesInDemandDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MachinesInDemand> result = repository.findAll(PageRequest.of(page, size));
        Set<MachinesInDemandDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MachinesInDemandDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MachinesInDemand by ID.
     *
     * @param id     the ID of the MachinesInDemand to delete
     * @param reason the reason for deletion
     * @return the deleted MachinesInDemand DTO
     * @throws NotFoundException if the MachinesInDemand is not found
     */
    @Transactional
    @Override
    public MachinesInDemandDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MachinesInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MachinesInDemand not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MachinesInDemandDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MachinesInDemand by ID.
     *
     * @param id the ID of the MachinesInDemand to retrieve
     * @return the MachinesInDemand DTO
     * @throws NotFoundException if the MachinesInDemand is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MachinesInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MachinesInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MachinesInDemand not found"));
        MachinesInDemandDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MachinesInDemandDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MachinesInDemand> savedData = repository.findAllById(ids);
        Set<MachinesInDemandDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MachinesInDemandDTO postHookSave(MachinesInDemandDTO dto) {
        return dto;
    }

    protected MachinesInDemandDTO preHookSave(MachinesInDemandDTO dto) {
        return dto;
    }

    protected MachinesInDemandDTO postHookUpdate(MachinesInDemandDTO dto) {
        return dto;
    }

    protected MachinesInDemandDTO preHookUpdate(MachinesInDemandDTO MachinesInDemandDTO) {
        return MachinesInDemandDTO;
    }

    protected MachinesInDemandDTO postHookDelete(MachinesInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MachinesInDemandDTO postHookGetById(MachinesInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<MachinesInDemandDTO> postHookGetAll(PageDTO<MachinesInDemandDTO> result) {
        return result;
    }




}
