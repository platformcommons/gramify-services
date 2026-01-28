package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HorticultureProductService;


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

public class HorticultureProductServiceImpl implements HorticultureProductService {

    private final HorticultureProductDTOAssembler assembler;
    private final HorticultureProductRepository repository;


    public HorticultureProductServiceImpl(
        HorticultureProductRepository repository, HorticultureProductDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HorticultureProduct.
     *
     * @param HorticultureProduct the HorticultureProduct DTO to save
     * @return the saved HorticultureProduct DTO
     */
    @Transactional
    @Override
    public HorticultureProductDTO save(HorticultureProductDTO HorticultureProduct) {
        log.debug("Entry - save(HorticultureProduct={})", HorticultureProduct);
        HorticultureProduct = preHookSave(HorticultureProduct);
        HorticultureProduct entity = assembler.fromDTO(HorticultureProduct);
        HorticultureProductDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HorticultureProduct.
     *
     * @param HorticultureProduct the HorticultureProduct DTO to update
     * @return the updated HorticultureProduct DTO
     * @throws NotFoundException if the HorticultureProduct is not found
     */
    @Transactional
    @Override
    public HorticultureProductDTO update(HorticultureProductDTO HorticultureProduct) {
        log.debug("Entry - update(HorticultureProduct={})", HorticultureProduct);
        HorticultureProduct = preHookUpdate(HorticultureProduct);
        HorticultureProduct saved = repository.findById(HorticultureProduct.getId()).orElseThrow(() -> new NotFoundException("HorticultureProduct not found"));
        saved.update(assembler.fromDTO(HorticultureProduct));
        saved = repository.save(saved);
        HorticultureProductDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HorticultureProducts.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HorticultureProduct DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HorticultureProductDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HorticultureProduct> result = repository.findAll(PageRequest.of(page, size));
        Set<HorticultureProductDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HorticultureProductDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HorticultureProduct by ID.
     *
     * @param id     the ID of the HorticultureProduct to delete
     * @param reason the reason for deletion
     * @return the deleted HorticultureProduct DTO
     * @throws NotFoundException if the HorticultureProduct is not found
     */
    @Transactional
    @Override
    public HorticultureProductDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HorticultureProduct saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HorticultureProduct not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HorticultureProductDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HorticultureProduct by ID.
     *
     * @param id the ID of the HorticultureProduct to retrieve
     * @return the HorticultureProduct DTO
     * @throws NotFoundException if the HorticultureProduct is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HorticultureProductDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HorticultureProduct saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HorticultureProduct not found"));
        HorticultureProductDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HorticultureProductDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HorticultureProduct> savedData = repository.findAllById(ids);
        Set<HorticultureProductDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HorticultureProductDTO postHookSave(HorticultureProductDTO dto) {
        return dto;
    }

    protected HorticultureProductDTO preHookSave(HorticultureProductDTO dto) {
        return dto;
    }

    protected HorticultureProductDTO postHookUpdate(HorticultureProductDTO dto) {
        return dto;
    }

    protected HorticultureProductDTO preHookUpdate(HorticultureProductDTO HorticultureProductDTO) {
        return HorticultureProductDTO;
    }

    protected HorticultureProductDTO postHookDelete(HorticultureProductDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HorticultureProductDTO postHookGetById(HorticultureProductDTO dto) {
        return dto;
    }

    protected PageDTO<HorticultureProductDTO> postHookGetAll(PageDTO<HorticultureProductDTO> result) {
        return result;
    }




}
