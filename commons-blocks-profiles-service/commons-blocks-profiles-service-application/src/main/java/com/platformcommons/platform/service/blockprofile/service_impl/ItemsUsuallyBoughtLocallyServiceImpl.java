package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ItemsUsuallyBoughtLocallyService;


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

public class ItemsUsuallyBoughtLocallyServiceImpl implements ItemsUsuallyBoughtLocallyService {

    private final ItemsUsuallyBoughtLocallyDTOAssembler assembler;
    private final ItemsUsuallyBoughtLocallyRepository repository;


    public ItemsUsuallyBoughtLocallyServiceImpl(
        ItemsUsuallyBoughtLocallyRepository repository, ItemsUsuallyBoughtLocallyDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ItemsUsuallyBoughtLocally.
     *
     * @param ItemsUsuallyBoughtLocally the ItemsUsuallyBoughtLocally DTO to save
     * @return the saved ItemsUsuallyBoughtLocally DTO
     */
    @Transactional
    @Override
    public ItemsUsuallyBoughtLocallyDTO save(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocally) {
        log.debug("Entry - save(ItemsUsuallyBoughtLocally={})", ItemsUsuallyBoughtLocally);
        ItemsUsuallyBoughtLocally = preHookSave(ItemsUsuallyBoughtLocally);
        ItemsUsuallyBoughtLocally entity = assembler.fromDTO(ItemsUsuallyBoughtLocally);
        ItemsUsuallyBoughtLocallyDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ItemsUsuallyBoughtLocally.
     *
     * @param ItemsUsuallyBoughtLocally the ItemsUsuallyBoughtLocally DTO to update
     * @return the updated ItemsUsuallyBoughtLocally DTO
     * @throws NotFoundException if the ItemsUsuallyBoughtLocally is not found
     */
    @Transactional
    @Override
    public ItemsUsuallyBoughtLocallyDTO update(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocally) {
        log.debug("Entry - update(ItemsUsuallyBoughtLocally={})", ItemsUsuallyBoughtLocally);
        ItemsUsuallyBoughtLocally = preHookUpdate(ItemsUsuallyBoughtLocally);
        ItemsUsuallyBoughtLocally saved = repository.findById(ItemsUsuallyBoughtLocally.getId()).orElseThrow(() -> new NotFoundException("ItemsUsuallyBoughtLocally not found"));
        saved.update(assembler.fromDTO(ItemsUsuallyBoughtLocally));
        saved = repository.save(saved);
        ItemsUsuallyBoughtLocallyDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ItemsUsuallyBoughtLocallys.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ItemsUsuallyBoughtLocally DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ItemsUsuallyBoughtLocallyDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ItemsUsuallyBoughtLocally> result = repository.findAll(PageRequest.of(page, size));
        Set<ItemsUsuallyBoughtLocallyDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ItemsUsuallyBoughtLocallyDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ItemsUsuallyBoughtLocally by ID.
     *
     * @param id     the ID of the ItemsUsuallyBoughtLocally to delete
     * @param reason the reason for deletion
     * @return the deleted ItemsUsuallyBoughtLocally DTO
     * @throws NotFoundException if the ItemsUsuallyBoughtLocally is not found
     */
    @Transactional
    @Override
    public ItemsUsuallyBoughtLocallyDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ItemsUsuallyBoughtLocally saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ItemsUsuallyBoughtLocally not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ItemsUsuallyBoughtLocallyDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ItemsUsuallyBoughtLocally by ID.
     *
     * @param id the ID of the ItemsUsuallyBoughtLocally to retrieve
     * @return the ItemsUsuallyBoughtLocally DTO
     * @throws NotFoundException if the ItemsUsuallyBoughtLocally is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ItemsUsuallyBoughtLocallyDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ItemsUsuallyBoughtLocally saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ItemsUsuallyBoughtLocally not found"));
        ItemsUsuallyBoughtLocallyDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ItemsUsuallyBoughtLocallyDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ItemsUsuallyBoughtLocally> savedData = repository.findAllById(ids);
        Set<ItemsUsuallyBoughtLocallyDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ItemsUsuallyBoughtLocallyDTO postHookSave(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtLocallyDTO preHookSave(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtLocallyDTO postHookUpdate(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtLocallyDTO preHookUpdate(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO) {
        return ItemsUsuallyBoughtLocallyDTO;
    }

    protected ItemsUsuallyBoughtLocallyDTO postHookDelete(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ItemsUsuallyBoughtLocallyDTO postHookGetById(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected PageDTO<ItemsUsuallyBoughtLocallyDTO> postHookGetAll(PageDTO<ItemsUsuallyBoughtLocallyDTO> result) {
        return result;
    }




}
