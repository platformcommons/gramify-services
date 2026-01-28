package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MajorFestivalService;


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

public class MajorFestivalServiceImpl implements MajorFestivalService {

    private final MajorFestivalDTOAssembler assembler;
    private final MajorFestivalRepository repository;


    public MajorFestivalServiceImpl(
        MajorFestivalRepository repository, MajorFestivalDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MajorFestival.
     *
     * @param MajorFestival the MajorFestival DTO to save
     * @return the saved MajorFestival DTO
     */
    @Transactional
    @Override
    public MajorFestivalDTO save(MajorFestivalDTO MajorFestival) {
        log.debug("Entry - save(MajorFestival={})", MajorFestival);
        MajorFestival = preHookSave(MajorFestival);
        MajorFestival entity = assembler.fromDTO(MajorFestival);
        MajorFestivalDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MajorFestival.
     *
     * @param MajorFestival the MajorFestival DTO to update
     * @return the updated MajorFestival DTO
     * @throws NotFoundException if the MajorFestival is not found
     */
    @Transactional
    @Override
    public MajorFestivalDTO update(MajorFestivalDTO MajorFestival) {
        log.debug("Entry - update(MajorFestival={})", MajorFestival);
        MajorFestival = preHookUpdate(MajorFestival);
        MajorFestival saved = repository.findById(MajorFestival.getId()).orElseThrow(() -> new NotFoundException("MajorFestival not found"));
        saved.update(assembler.fromDTO(MajorFestival));
        saved = repository.save(saved);
        MajorFestivalDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MajorFestivals.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MajorFestival DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MajorFestivalDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MajorFestival> result = repository.findAll(PageRequest.of(page, size));
        Set<MajorFestivalDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MajorFestivalDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MajorFestival by ID.
     *
     * @param id     the ID of the MajorFestival to delete
     * @param reason the reason for deletion
     * @return the deleted MajorFestival DTO
     * @throws NotFoundException if the MajorFestival is not found
     */
    @Transactional
    @Override
    public MajorFestivalDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MajorFestival saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MajorFestival not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MajorFestivalDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MajorFestival by ID.
     *
     * @param id the ID of the MajorFestival to retrieve
     * @return the MajorFestival DTO
     * @throws NotFoundException if the MajorFestival is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MajorFestivalDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MajorFestival saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MajorFestival not found"));
        MajorFestivalDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MajorFestivalDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MajorFestival> savedData = repository.findAllById(ids);
        Set<MajorFestivalDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MajorFestivalDTO postHookSave(MajorFestivalDTO dto) {
        return dto;
    }

    protected MajorFestivalDTO preHookSave(MajorFestivalDTO dto) {
        return dto;
    }

    protected MajorFestivalDTO postHookUpdate(MajorFestivalDTO dto) {
        return dto;
    }

    protected MajorFestivalDTO preHookUpdate(MajorFestivalDTO MajorFestivalDTO) {
        return MajorFestivalDTO;
    }

    protected MajorFestivalDTO postHookDelete(MajorFestivalDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MajorFestivalDTO postHookGetById(MajorFestivalDTO dto) {
        return dto;
    }

    protected PageDTO<MajorFestivalDTO> postHookGetAll(PageDTO<MajorFestivalDTO> result) {
        return result;
    }




}
