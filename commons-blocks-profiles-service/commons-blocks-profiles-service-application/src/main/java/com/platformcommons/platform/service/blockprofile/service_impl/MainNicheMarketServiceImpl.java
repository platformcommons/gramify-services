package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MainNicheMarketService;


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

public class MainNicheMarketServiceImpl implements MainNicheMarketService {

    private final MainNicheMarketDTOAssembler assembler;
    private final MainNicheMarketRepository repository;


    public MainNicheMarketServiceImpl(
        MainNicheMarketRepository repository, MainNicheMarketDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MainNicheMarket.
     *
     * @param MainNicheMarket the MainNicheMarket DTO to save
     * @return the saved MainNicheMarket DTO
     */
    @Transactional
    @Override
    public MainNicheMarketDTO save(MainNicheMarketDTO MainNicheMarket) {
        log.debug("Entry - save(MainNicheMarket={})", MainNicheMarket);
        MainNicheMarket = preHookSave(MainNicheMarket);
        MainNicheMarket entity = assembler.fromDTO(MainNicheMarket);
        MainNicheMarketDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MainNicheMarket.
     *
     * @param MainNicheMarket the MainNicheMarket DTO to update
     * @return the updated MainNicheMarket DTO
     * @throws NotFoundException if the MainNicheMarket is not found
     */
    @Transactional
    @Override
    public MainNicheMarketDTO update(MainNicheMarketDTO MainNicheMarket) {
        log.debug("Entry - update(MainNicheMarket={})", MainNicheMarket);
        MainNicheMarket = preHookUpdate(MainNicheMarket);
        MainNicheMarket saved = repository.findById(MainNicheMarket.getId()).orElseThrow(() -> new NotFoundException("MainNicheMarket not found"));
        saved.update(assembler.fromDTO(MainNicheMarket));
        saved = repository.save(saved);
        MainNicheMarketDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MainNicheMarkets.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MainNicheMarket DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MainNicheMarketDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MainNicheMarket> result = repository.findAll(PageRequest.of(page, size));
        Set<MainNicheMarketDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MainNicheMarketDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MainNicheMarket by ID.
     *
     * @param id     the ID of the MainNicheMarket to delete
     * @param reason the reason for deletion
     * @return the deleted MainNicheMarket DTO
     * @throws NotFoundException if the MainNicheMarket is not found
     */
    @Transactional
    @Override
    public MainNicheMarketDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MainNicheMarket saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainNicheMarket not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MainNicheMarketDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MainNicheMarket by ID.
     *
     * @param id the ID of the MainNicheMarket to retrieve
     * @return the MainNicheMarket DTO
     * @throws NotFoundException if the MainNicheMarket is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MainNicheMarketDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MainNicheMarket saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainNicheMarket not found"));
        MainNicheMarketDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MainNicheMarketDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MainNicheMarket> savedData = repository.findAllById(ids);
        Set<MainNicheMarketDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainNicheMarketDTO postHookSave(MainNicheMarketDTO dto) {
        return dto;
    }

    protected MainNicheMarketDTO preHookSave(MainNicheMarketDTO dto) {
        return dto;
    }

    protected MainNicheMarketDTO postHookUpdate(MainNicheMarketDTO dto) {
        return dto;
    }

    protected MainNicheMarketDTO preHookUpdate(MainNicheMarketDTO MainNicheMarketDTO) {
        return MainNicheMarketDTO;
    }

    protected MainNicheMarketDTO postHookDelete(MainNicheMarketDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainNicheMarketDTO postHookGetById(MainNicheMarketDTO dto) {
        return dto;
    }

    protected PageDTO<MainNicheMarketDTO> postHookGetAll(PageDTO<MainNicheMarketDTO> result) {
        return result;
    }




}
