package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ProductionSeasonService;


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

public class ProductionSeasonServiceImpl implements ProductionSeasonService {

    private final ProductionSeasonDTOAssembler assembler;
    private final ProductionSeasonRepository repository;


    public ProductionSeasonServiceImpl(
        ProductionSeasonRepository repository, ProductionSeasonDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ProductionSeason.
     *
     * @param ProductionSeason the ProductionSeason DTO to save
     * @return the saved ProductionSeason DTO
     */
    @Transactional
    @Override
    public ProductionSeasonDTO save(ProductionSeasonDTO ProductionSeason) {
        log.debug("Entry - save(ProductionSeason={})", ProductionSeason);
        ProductionSeason = preHookSave(ProductionSeason);
        ProductionSeason entity = assembler.fromDTO(ProductionSeason);
        ProductionSeasonDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ProductionSeason.
     *
     * @param ProductionSeason the ProductionSeason DTO to update
     * @return the updated ProductionSeason DTO
     * @throws NotFoundException if the ProductionSeason is not found
     */
    @Transactional
    @Override
    public ProductionSeasonDTO update(ProductionSeasonDTO ProductionSeason) {
        log.debug("Entry - update(ProductionSeason={})", ProductionSeason);
        ProductionSeason = preHookUpdate(ProductionSeason);
        ProductionSeason saved = repository.findById(ProductionSeason.getId()).orElseThrow(() -> new NotFoundException("ProductionSeason not found"));
        saved.update(assembler.fromDTO(ProductionSeason));
        saved = repository.save(saved);
        ProductionSeasonDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ProductionSeasons.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ProductionSeason DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ProductionSeasonDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ProductionSeason> result = repository.findAll(PageRequest.of(page, size));
        Set<ProductionSeasonDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ProductionSeasonDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ProductionSeason by ID.
     *
     * @param id     the ID of the ProductionSeason to delete
     * @param reason the reason for deletion
     * @return the deleted ProductionSeason DTO
     * @throws NotFoundException if the ProductionSeason is not found
     */
    @Transactional
    @Override
    public ProductionSeasonDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ProductionSeason saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ProductionSeason not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ProductionSeasonDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ProductionSeason by ID.
     *
     * @param id the ID of the ProductionSeason to retrieve
     * @return the ProductionSeason DTO
     * @throws NotFoundException if the ProductionSeason is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ProductionSeasonDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ProductionSeason saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ProductionSeason not found"));
        ProductionSeasonDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ProductionSeasonDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ProductionSeason> savedData = repository.findAllById(ids);
        Set<ProductionSeasonDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ProductionSeasonDTO postHookSave(ProductionSeasonDTO dto) {
        return dto;
    }

    protected ProductionSeasonDTO preHookSave(ProductionSeasonDTO dto) {
        return dto;
    }

    protected ProductionSeasonDTO postHookUpdate(ProductionSeasonDTO dto) {
        return dto;
    }

    protected ProductionSeasonDTO preHookUpdate(ProductionSeasonDTO ProductionSeasonDTO) {
        return ProductionSeasonDTO;
    }

    protected ProductionSeasonDTO postHookDelete(ProductionSeasonDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ProductionSeasonDTO postHookGetById(ProductionSeasonDTO dto) {
        return dto;
    }

    protected PageDTO<ProductionSeasonDTO> postHookGetAll(PageDTO<ProductionSeasonDTO> result) {
        return result;
    }




}
