package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ItemsUsuallyBoughtFromOutsideService;


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

public class ItemsUsuallyBoughtFromOutsideServiceImpl implements ItemsUsuallyBoughtFromOutsideService {

    private final ItemsUsuallyBoughtFromOutsideDTOAssembler assembler;
    private final ItemsUsuallyBoughtFromOutsideRepository repository;


    public ItemsUsuallyBoughtFromOutsideServiceImpl(
        ItemsUsuallyBoughtFromOutsideRepository repository, ItemsUsuallyBoughtFromOutsideDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ItemsUsuallyBoughtFromOutside.
     *
     * @param ItemsUsuallyBoughtFromOutside the ItemsUsuallyBoughtFromOutside DTO to save
     * @return the saved ItemsUsuallyBoughtFromOutside DTO
     */
    @Transactional
    @Override
    public ItemsUsuallyBoughtFromOutsideDTO save(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutside) {
        log.debug("Entry - save(ItemsUsuallyBoughtFromOutside={})", ItemsUsuallyBoughtFromOutside);
        ItemsUsuallyBoughtFromOutside = preHookSave(ItemsUsuallyBoughtFromOutside);
        ItemsUsuallyBoughtFromOutside entity = assembler.fromDTO(ItemsUsuallyBoughtFromOutside);
        ItemsUsuallyBoughtFromOutsideDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ItemsUsuallyBoughtFromOutside.
     *
     * @param ItemsUsuallyBoughtFromOutside the ItemsUsuallyBoughtFromOutside DTO to update
     * @return the updated ItemsUsuallyBoughtFromOutside DTO
     * @throws NotFoundException if the ItemsUsuallyBoughtFromOutside is not found
     */
    @Transactional
    @Override
    public ItemsUsuallyBoughtFromOutsideDTO update(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutside) {
        log.debug("Entry - update(ItemsUsuallyBoughtFromOutside={})", ItemsUsuallyBoughtFromOutside);
        ItemsUsuallyBoughtFromOutside = preHookUpdate(ItemsUsuallyBoughtFromOutside);
        ItemsUsuallyBoughtFromOutside saved = repository.findById(ItemsUsuallyBoughtFromOutside.getId()).orElseThrow(() -> new NotFoundException("ItemsUsuallyBoughtFromOutside not found"));
        saved.update(assembler.fromDTO(ItemsUsuallyBoughtFromOutside));
        saved = repository.save(saved);
        ItemsUsuallyBoughtFromOutsideDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ItemsUsuallyBoughtFromOutsides.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ItemsUsuallyBoughtFromOutside DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ItemsUsuallyBoughtFromOutsideDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ItemsUsuallyBoughtFromOutside> result = repository.findAll(PageRequest.of(page, size));
        Set<ItemsUsuallyBoughtFromOutsideDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ItemsUsuallyBoughtFromOutsideDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ItemsUsuallyBoughtFromOutside by ID.
     *
     * @param id     the ID of the ItemsUsuallyBoughtFromOutside to delete
     * @param reason the reason for deletion
     * @return the deleted ItemsUsuallyBoughtFromOutside DTO
     * @throws NotFoundException if the ItemsUsuallyBoughtFromOutside is not found
     */
    @Transactional
    @Override
    public ItemsUsuallyBoughtFromOutsideDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ItemsUsuallyBoughtFromOutside saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ItemsUsuallyBoughtFromOutside not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ItemsUsuallyBoughtFromOutsideDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ItemsUsuallyBoughtFromOutside by ID.
     *
     * @param id the ID of the ItemsUsuallyBoughtFromOutside to retrieve
     * @return the ItemsUsuallyBoughtFromOutside DTO
     * @throws NotFoundException if the ItemsUsuallyBoughtFromOutside is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ItemsUsuallyBoughtFromOutsideDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ItemsUsuallyBoughtFromOutside saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ItemsUsuallyBoughtFromOutside not found"));
        ItemsUsuallyBoughtFromOutsideDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ItemsUsuallyBoughtFromOutsideDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ItemsUsuallyBoughtFromOutside> savedData = repository.findAllById(ids);
        Set<ItemsUsuallyBoughtFromOutsideDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ItemsUsuallyBoughtFromOutsideDTO postHookSave(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO preHookSave(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO postHookUpdate(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO preHookUpdate(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO) {
        return ItemsUsuallyBoughtFromOutsideDTO;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO postHookDelete(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ItemsUsuallyBoughtFromOutsideDTO postHookGetById(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected PageDTO<ItemsUsuallyBoughtFromOutsideDTO> postHookGetAll(PageDTO<ItemsUsuallyBoughtFromOutsideDTO> result) {
        return result;
    }




}
