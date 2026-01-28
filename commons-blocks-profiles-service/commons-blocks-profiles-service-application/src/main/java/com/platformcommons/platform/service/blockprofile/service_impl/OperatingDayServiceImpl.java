package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.OperatingDayService;


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

public class OperatingDayServiceImpl implements OperatingDayService {

    private final OperatingDayDTOAssembler assembler;
    private final OperatingDayRepository repository;


    public OperatingDayServiceImpl(
        OperatingDayRepository repository, OperatingDayDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new OperatingDay.
     *
     * @param OperatingDay the OperatingDay DTO to save
     * @return the saved OperatingDay DTO
     */
    @Transactional
    @Override
    public OperatingDayDTO save(OperatingDayDTO OperatingDay) {
        log.debug("Entry - save(OperatingDay={})", OperatingDay);
        OperatingDay = preHookSave(OperatingDay);
        OperatingDay entity = assembler.fromDTO(OperatingDay);
        OperatingDayDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing OperatingDay.
     *
     * @param OperatingDay the OperatingDay DTO to update
     * @return the updated OperatingDay DTO
     * @throws NotFoundException if the OperatingDay is not found
     */
    @Transactional
    @Override
    public OperatingDayDTO update(OperatingDayDTO OperatingDay) {
        log.debug("Entry - update(OperatingDay={})", OperatingDay);
        OperatingDay = preHookUpdate(OperatingDay);
        OperatingDay saved = repository.findById(OperatingDay.getId()).orElseThrow(() -> new NotFoundException("OperatingDay not found"));
        saved.update(assembler.fromDTO(OperatingDay));
        saved = repository.save(saved);
        OperatingDayDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of OperatingDays.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing OperatingDay DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<OperatingDayDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<OperatingDay> result = repository.findAll(PageRequest.of(page, size));
        Set<OperatingDayDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<OperatingDayDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a OperatingDay by ID.
     *
     * @param id     the ID of the OperatingDay to delete
     * @param reason the reason for deletion
     * @return the deleted OperatingDay DTO
     * @throws NotFoundException if the OperatingDay is not found
     */
    @Transactional
    @Override
    public OperatingDayDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        OperatingDay saved = repository.findById(id).orElseThrow(() -> new NotFoundException("OperatingDay not found"));
        saved.deactivate(reason);
        repository.save(saved);
        OperatingDayDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a OperatingDay by ID.
     *
     * @param id the ID of the OperatingDay to retrieve
     * @return the OperatingDay DTO
     * @throws NotFoundException if the OperatingDay is not found
     */
    @Transactional(readOnly = true)
    @Override
    public OperatingDayDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        OperatingDay saved = repository.findById(id).orElseThrow(() -> new NotFoundException("OperatingDay not found"));
        OperatingDayDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<OperatingDayDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<OperatingDay> savedData = repository.findAllById(ids);
        Set<OperatingDayDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected OperatingDayDTO postHookSave(OperatingDayDTO dto) {
        return dto;
    }

    protected OperatingDayDTO preHookSave(OperatingDayDTO dto) {
        return dto;
    }

    protected OperatingDayDTO postHookUpdate(OperatingDayDTO dto) {
        return dto;
    }

    protected OperatingDayDTO preHookUpdate(OperatingDayDTO OperatingDayDTO) {
        return OperatingDayDTO;
    }

    protected OperatingDayDTO postHookDelete(OperatingDayDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected OperatingDayDTO postHookGetById(OperatingDayDTO dto) {
        return dto;
    }

    protected PageDTO<OperatingDayDTO> postHookGetAll(PageDTO<OperatingDayDTO> result) {
        return result;
    }




}
