package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.WhereFarmersBuyInputService;


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

public class WhereFarmersBuyInputServiceImpl implements WhereFarmersBuyInputService {

    private final WhereFarmersBuyInputDTOAssembler assembler;
    private final WhereFarmersBuyInputRepository repository;


    public WhereFarmersBuyInputServiceImpl(
        WhereFarmersBuyInputRepository repository, WhereFarmersBuyInputDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new WhereFarmersBuyInput.
     *
     * @param WhereFarmersBuyInput the WhereFarmersBuyInput DTO to save
     * @return the saved WhereFarmersBuyInput DTO
     */
    @Transactional
    @Override
    public WhereFarmersBuyInputDTO save(WhereFarmersBuyInputDTO WhereFarmersBuyInput) {
        log.debug("Entry - save(WhereFarmersBuyInput={})", WhereFarmersBuyInput);
        WhereFarmersBuyInput = preHookSave(WhereFarmersBuyInput);
        WhereFarmersBuyInput entity = assembler.fromDTO(WhereFarmersBuyInput);
        WhereFarmersBuyInputDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing WhereFarmersBuyInput.
     *
     * @param WhereFarmersBuyInput the WhereFarmersBuyInput DTO to update
     * @return the updated WhereFarmersBuyInput DTO
     * @throws NotFoundException if the WhereFarmersBuyInput is not found
     */
    @Transactional
    @Override
    public WhereFarmersBuyInputDTO update(WhereFarmersBuyInputDTO WhereFarmersBuyInput) {
        log.debug("Entry - update(WhereFarmersBuyInput={})", WhereFarmersBuyInput);
        WhereFarmersBuyInput = preHookUpdate(WhereFarmersBuyInput);
        WhereFarmersBuyInput saved = repository.findById(WhereFarmersBuyInput.getId()).orElseThrow(() -> new NotFoundException("WhereFarmersBuyInput not found"));
        saved.update(assembler.fromDTO(WhereFarmersBuyInput));
        saved = repository.save(saved);
        WhereFarmersBuyInputDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of WhereFarmersBuyInputs.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing WhereFarmersBuyInput DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<WhereFarmersBuyInputDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<WhereFarmersBuyInput> result = repository.findAll(PageRequest.of(page, size));
        Set<WhereFarmersBuyInputDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<WhereFarmersBuyInputDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a WhereFarmersBuyInput by ID.
     *
     * @param id     the ID of the WhereFarmersBuyInput to delete
     * @param reason the reason for deletion
     * @return the deleted WhereFarmersBuyInput DTO
     * @throws NotFoundException if the WhereFarmersBuyInput is not found
     */
    @Transactional
    @Override
    public WhereFarmersBuyInputDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        WhereFarmersBuyInput saved = repository.findById(id).orElseThrow(() -> new NotFoundException("WhereFarmersBuyInput not found"));
        saved.deactivate(reason);
        repository.save(saved);
        WhereFarmersBuyInputDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a WhereFarmersBuyInput by ID.
     *
     * @param id the ID of the WhereFarmersBuyInput to retrieve
     * @return the WhereFarmersBuyInput DTO
     * @throws NotFoundException if the WhereFarmersBuyInput is not found
     */
    @Transactional(readOnly = true)
    @Override
    public WhereFarmersBuyInputDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        WhereFarmersBuyInput saved = repository.findById(id).orElseThrow(() -> new NotFoundException("WhereFarmersBuyInput not found"));
        WhereFarmersBuyInputDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<WhereFarmersBuyInputDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<WhereFarmersBuyInput> savedData = repository.findAllById(ids);
        Set<WhereFarmersBuyInputDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected WhereFarmersBuyInputDTO postHookSave(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected WhereFarmersBuyInputDTO preHookSave(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected WhereFarmersBuyInputDTO postHookUpdate(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected WhereFarmersBuyInputDTO preHookUpdate(WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO) {
        return WhereFarmersBuyInputDTO;
    }

    protected WhereFarmersBuyInputDTO postHookDelete(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected WhereFarmersBuyInputDTO postHookGetById(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected PageDTO<WhereFarmersBuyInputDTO> postHookGetAll(PageDTO<WhereFarmersBuyInputDTO> result) {
        return result;
    }




}
