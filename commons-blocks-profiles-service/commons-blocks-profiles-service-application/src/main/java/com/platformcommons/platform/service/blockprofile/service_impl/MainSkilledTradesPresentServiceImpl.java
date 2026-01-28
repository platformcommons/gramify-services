package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MainSkilledTradesPresentService;


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

public class MainSkilledTradesPresentServiceImpl implements MainSkilledTradesPresentService {

    private final MainSkilledTradesPresentDTOAssembler assembler;
    private final MainSkilledTradesPresentRepository repository;


    public MainSkilledTradesPresentServiceImpl(
        MainSkilledTradesPresentRepository repository, MainSkilledTradesPresentDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MainSkilledTradesPresent.
     *
     * @param MainSkilledTradesPresent the MainSkilledTradesPresent DTO to save
     * @return the saved MainSkilledTradesPresent DTO
     */
    @Transactional
    @Override
    public MainSkilledTradesPresentDTO save(MainSkilledTradesPresentDTO MainSkilledTradesPresent) {
        log.debug("Entry - save(MainSkilledTradesPresent={})", MainSkilledTradesPresent);
        MainSkilledTradesPresent = preHookSave(MainSkilledTradesPresent);
        MainSkilledTradesPresent entity = assembler.fromDTO(MainSkilledTradesPresent);
        MainSkilledTradesPresentDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MainSkilledTradesPresent.
     *
     * @param MainSkilledTradesPresent the MainSkilledTradesPresent DTO to update
     * @return the updated MainSkilledTradesPresent DTO
     * @throws NotFoundException if the MainSkilledTradesPresent is not found
     */
    @Transactional
    @Override
    public MainSkilledTradesPresentDTO update(MainSkilledTradesPresentDTO MainSkilledTradesPresent) {
        log.debug("Entry - update(MainSkilledTradesPresent={})", MainSkilledTradesPresent);
        MainSkilledTradesPresent = preHookUpdate(MainSkilledTradesPresent);
        MainSkilledTradesPresent saved = repository.findById(MainSkilledTradesPresent.getId()).orElseThrow(() -> new NotFoundException("MainSkilledTradesPresent not found"));
        saved.update(assembler.fromDTO(MainSkilledTradesPresent));
        saved = repository.save(saved);
        MainSkilledTradesPresentDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MainSkilledTradesPresents.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MainSkilledTradesPresent DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MainSkilledTradesPresentDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MainSkilledTradesPresent> result = repository.findAll(PageRequest.of(page, size));
        Set<MainSkilledTradesPresentDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MainSkilledTradesPresentDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MainSkilledTradesPresent by ID.
     *
     * @param id     the ID of the MainSkilledTradesPresent to delete
     * @param reason the reason for deletion
     * @return the deleted MainSkilledTradesPresent DTO
     * @throws NotFoundException if the MainSkilledTradesPresent is not found
     */
    @Transactional
    @Override
    public MainSkilledTradesPresentDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MainSkilledTradesPresent saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainSkilledTradesPresent not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MainSkilledTradesPresentDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MainSkilledTradesPresent by ID.
     *
     * @param id the ID of the MainSkilledTradesPresent to retrieve
     * @return the MainSkilledTradesPresent DTO
     * @throws NotFoundException if the MainSkilledTradesPresent is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MainSkilledTradesPresentDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MainSkilledTradesPresent saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainSkilledTradesPresent not found"));
        MainSkilledTradesPresentDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MainSkilledTradesPresentDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MainSkilledTradesPresent> savedData = repository.findAllById(ids);
        Set<MainSkilledTradesPresentDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainSkilledTradesPresentDTO postHookSave(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected MainSkilledTradesPresentDTO preHookSave(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected MainSkilledTradesPresentDTO postHookUpdate(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected MainSkilledTradesPresentDTO preHookUpdate(MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO) {
        return MainSkilledTradesPresentDTO;
    }

    protected MainSkilledTradesPresentDTO postHookDelete(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainSkilledTradesPresentDTO postHookGetById(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected PageDTO<MainSkilledTradesPresentDTO> postHookGetAll(PageDTO<MainSkilledTradesPresentDTO> result) {
        return result;
    }




}
