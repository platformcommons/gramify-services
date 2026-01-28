package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MainSurplusMarketsOutsideBlockService;


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

public class MainSurplusMarketsOutsideBlockServiceImpl implements MainSurplusMarketsOutsideBlockService {

    private final MainSurplusMarketsOutsideBlockDTOAssembler assembler;
    private final MainSurplusMarketsOutsideBlockRepository repository;


    public MainSurplusMarketsOutsideBlockServiceImpl(
        MainSurplusMarketsOutsideBlockRepository repository, MainSurplusMarketsOutsideBlockDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MainSurplusMarketsOutsideBlock.
     *
     * @param MainSurplusMarketsOutsideBlock the MainSurplusMarketsOutsideBlock DTO to save
     * @return the saved MainSurplusMarketsOutsideBlock DTO
     */
    @Transactional
    @Override
    public MainSurplusMarketsOutsideBlockDTO save(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlock) {
        log.debug("Entry - save(MainSurplusMarketsOutsideBlock={})", MainSurplusMarketsOutsideBlock);
        MainSurplusMarketsOutsideBlock = preHookSave(MainSurplusMarketsOutsideBlock);
        MainSurplusMarketsOutsideBlock entity = assembler.fromDTO(MainSurplusMarketsOutsideBlock);
        MainSurplusMarketsOutsideBlockDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MainSurplusMarketsOutsideBlock.
     *
     * @param MainSurplusMarketsOutsideBlock the MainSurplusMarketsOutsideBlock DTO to update
     * @return the updated MainSurplusMarketsOutsideBlock DTO
     * @throws NotFoundException if the MainSurplusMarketsOutsideBlock is not found
     */
    @Transactional
    @Override
    public MainSurplusMarketsOutsideBlockDTO update(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlock) {
        log.debug("Entry - update(MainSurplusMarketsOutsideBlock={})", MainSurplusMarketsOutsideBlock);
        MainSurplusMarketsOutsideBlock = preHookUpdate(MainSurplusMarketsOutsideBlock);
        MainSurplusMarketsOutsideBlock saved = repository.findById(MainSurplusMarketsOutsideBlock.getId()).orElseThrow(() -> new NotFoundException("MainSurplusMarketsOutsideBlock not found"));
        saved.update(assembler.fromDTO(MainSurplusMarketsOutsideBlock));
        saved = repository.save(saved);
        MainSurplusMarketsOutsideBlockDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MainSurplusMarketsOutsideBlocks.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MainSurplusMarketsOutsideBlock DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MainSurplusMarketsOutsideBlockDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MainSurplusMarketsOutsideBlock> result = repository.findAll(PageRequest.of(page, size));
        Set<MainSurplusMarketsOutsideBlockDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MainSurplusMarketsOutsideBlockDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MainSurplusMarketsOutsideBlock by ID.
     *
     * @param id     the ID of the MainSurplusMarketsOutsideBlock to delete
     * @param reason the reason for deletion
     * @return the deleted MainSurplusMarketsOutsideBlock DTO
     * @throws NotFoundException if the MainSurplusMarketsOutsideBlock is not found
     */
    @Transactional
    @Override
    public MainSurplusMarketsOutsideBlockDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MainSurplusMarketsOutsideBlock saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainSurplusMarketsOutsideBlock not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MainSurplusMarketsOutsideBlockDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MainSurplusMarketsOutsideBlock by ID.
     *
     * @param id the ID of the MainSurplusMarketsOutsideBlock to retrieve
     * @return the MainSurplusMarketsOutsideBlock DTO
     * @throws NotFoundException if the MainSurplusMarketsOutsideBlock is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MainSurplusMarketsOutsideBlockDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MainSurplusMarketsOutsideBlock saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainSurplusMarketsOutsideBlock not found"));
        MainSurplusMarketsOutsideBlockDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MainSurplusMarketsOutsideBlockDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MainSurplusMarketsOutsideBlock> savedData = repository.findAllById(ids);
        Set<MainSurplusMarketsOutsideBlockDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainSurplusMarketsOutsideBlockDTO postHookSave(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected MainSurplusMarketsOutsideBlockDTO preHookSave(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected MainSurplusMarketsOutsideBlockDTO postHookUpdate(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected MainSurplusMarketsOutsideBlockDTO preHookUpdate(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO) {
        return MainSurplusMarketsOutsideBlockDTO;
    }

    protected MainSurplusMarketsOutsideBlockDTO postHookDelete(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainSurplusMarketsOutsideBlockDTO postHookGetById(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected PageDTO<MainSurplusMarketsOutsideBlockDTO> postHookGetAll(PageDTO<MainSurplusMarketsOutsideBlockDTO> result) {
        return result;
    }




}
