package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.LocalFestivalService;


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

public class LocalFestivalServiceImpl implements LocalFestivalService {

    private final LocalFestivalDTOAssembler assembler;
    private final LocalFestivalRepository repository;


    public LocalFestivalServiceImpl(
        LocalFestivalRepository repository, LocalFestivalDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new LocalFestival.
     *
     * @param LocalFestival the LocalFestival DTO to save
     * @return the saved LocalFestival DTO
     */
    @Transactional
    @Override
    public LocalFestivalDTO save(LocalFestivalDTO LocalFestival) {
        log.debug("Entry - save(LocalFestival={})", LocalFestival);
        LocalFestival = preHookSave(LocalFestival);
        LocalFestival entity = assembler.fromDTO(LocalFestival);
        LocalFestivalDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing LocalFestival.
     *
     * @param LocalFestival the LocalFestival DTO to update
     * @return the updated LocalFestival DTO
     * @throws NotFoundException if the LocalFestival is not found
     */
    @Transactional
    @Override
    public LocalFestivalDTO update(LocalFestivalDTO LocalFestival) {
        log.debug("Entry - update(LocalFestival={})", LocalFestival);
        LocalFestival = preHookUpdate(LocalFestival);
        LocalFestival saved = repository.findById(LocalFestival.getId()).orElseThrow(() -> new NotFoundException("LocalFestival not found"));
        saved.update(assembler.fromDTO(LocalFestival));
        saved = repository.save(saved);
        LocalFestivalDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of LocalFestivals.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing LocalFestival DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<LocalFestivalDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<LocalFestival> result = repository.findAll(PageRequest.of(page, size));
        Set<LocalFestivalDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<LocalFestivalDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a LocalFestival by ID.
     *
     * @param id     the ID of the LocalFestival to delete
     * @param reason the reason for deletion
     * @return the deleted LocalFestival DTO
     * @throws NotFoundException if the LocalFestival is not found
     */
    @Transactional
    @Override
    public LocalFestivalDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        LocalFestival saved = repository.findById(id).orElseThrow(() -> new NotFoundException("LocalFestival not found"));
        saved.deactivate(reason);
        repository.save(saved);
        LocalFestivalDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a LocalFestival by ID.
     *
     * @param id the ID of the LocalFestival to retrieve
     * @return the LocalFestival DTO
     * @throws NotFoundException if the LocalFestival is not found
     */
    @Transactional(readOnly = true)
    @Override
    public LocalFestivalDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        LocalFestival saved = repository.findById(id).orElseThrow(() -> new NotFoundException("LocalFestival not found"));
        LocalFestivalDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<LocalFestivalDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<LocalFestival> savedData = repository.findAllById(ids);
        Set<LocalFestivalDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected LocalFestivalDTO postHookSave(LocalFestivalDTO dto) {
        return dto;
    }

    protected LocalFestivalDTO preHookSave(LocalFestivalDTO dto) {
        return dto;
    }

    protected LocalFestivalDTO postHookUpdate(LocalFestivalDTO dto) {
        return dto;
    }

    protected LocalFestivalDTO preHookUpdate(LocalFestivalDTO LocalFestivalDTO) {
        return LocalFestivalDTO;
    }

    protected LocalFestivalDTO postHookDelete(LocalFestivalDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected LocalFestivalDTO postHookGetById(LocalFestivalDTO dto) {
        return dto;
    }

    protected PageDTO<LocalFestivalDTO> postHookGetAll(PageDTO<LocalFestivalDTO> result) {
        return result;
    }




}
