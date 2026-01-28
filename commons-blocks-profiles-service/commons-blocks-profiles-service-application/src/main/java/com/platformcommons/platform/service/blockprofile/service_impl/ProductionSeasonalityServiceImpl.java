package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ProductionSeasonalityService;


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

public class ProductionSeasonalityServiceImpl implements ProductionSeasonalityService {

    private final ProductionSeasonalityDTOAssembler assembler;
    private final ProductionSeasonalityRepository repository;


    public ProductionSeasonalityServiceImpl(
        ProductionSeasonalityRepository repository, ProductionSeasonalityDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ProductionSeasonality.
     *
     * @param ProductionSeasonality the ProductionSeasonality DTO to save
     * @return the saved ProductionSeasonality DTO
     */
    @Transactional
    @Override
    public ProductionSeasonalityDTO save(ProductionSeasonalityDTO ProductionSeasonality) {
        log.debug("Entry - save(ProductionSeasonality={})", ProductionSeasonality);
        ProductionSeasonality = preHookSave(ProductionSeasonality);
        ProductionSeasonality entity = assembler.fromDTO(ProductionSeasonality);
        ProductionSeasonalityDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ProductionSeasonality.
     *
     * @param ProductionSeasonality the ProductionSeasonality DTO to update
     * @return the updated ProductionSeasonality DTO
     * @throws NotFoundException if the ProductionSeasonality is not found
     */
    @Transactional
    @Override
    public ProductionSeasonalityDTO update(ProductionSeasonalityDTO ProductionSeasonality) {
        log.debug("Entry - update(ProductionSeasonality={})", ProductionSeasonality);
        ProductionSeasonality = preHookUpdate(ProductionSeasonality);
        ProductionSeasonality saved = repository.findById(ProductionSeasonality.getId()).orElseThrow(() -> new NotFoundException("ProductionSeasonality not found"));
        saved.update(assembler.fromDTO(ProductionSeasonality));
        saved = repository.save(saved);
        ProductionSeasonalityDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ProductionSeasonalitys.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ProductionSeasonality DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ProductionSeasonalityDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ProductionSeasonality> result = repository.findAll(PageRequest.of(page, size));
        Set<ProductionSeasonalityDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ProductionSeasonalityDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ProductionSeasonality by ID.
     *
     * @param id     the ID of the ProductionSeasonality to delete
     * @param reason the reason for deletion
     * @return the deleted ProductionSeasonality DTO
     * @throws NotFoundException if the ProductionSeasonality is not found
     */
    @Transactional
    @Override
    public ProductionSeasonalityDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ProductionSeasonality saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ProductionSeasonality not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ProductionSeasonalityDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ProductionSeasonality by ID.
     *
     * @param id the ID of the ProductionSeasonality to retrieve
     * @return the ProductionSeasonality DTO
     * @throws NotFoundException if the ProductionSeasonality is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ProductionSeasonalityDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ProductionSeasonality saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ProductionSeasonality not found"));
        ProductionSeasonalityDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ProductionSeasonalityDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ProductionSeasonality> savedData = repository.findAllById(ids);
        Set<ProductionSeasonalityDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ProductionSeasonalityDTO postHookSave(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected ProductionSeasonalityDTO preHookSave(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected ProductionSeasonalityDTO postHookUpdate(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected ProductionSeasonalityDTO preHookUpdate(ProductionSeasonalityDTO ProductionSeasonalityDTO) {
        return ProductionSeasonalityDTO;
    }

    protected ProductionSeasonalityDTO postHookDelete(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ProductionSeasonalityDTO postHookGetById(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected PageDTO<ProductionSeasonalityDTO> postHookGetAll(PageDTO<ProductionSeasonalityDTO> result) {
        return result;
    }




}
