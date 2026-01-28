package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.CommonConsumerGoodsPurchasedService;


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

public class CommonConsumerGoodsPurchasedServiceImpl implements CommonConsumerGoodsPurchasedService {

    private final CommonConsumerGoodsPurchasedDTOAssembler assembler;
    private final CommonConsumerGoodsPurchasedRepository repository;


    public CommonConsumerGoodsPurchasedServiceImpl(
        CommonConsumerGoodsPurchasedRepository repository, CommonConsumerGoodsPurchasedDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new CommonConsumerGoodsPurchased.
     *
     * @param CommonConsumerGoodsPurchased the CommonConsumerGoodsPurchased DTO to save
     * @return the saved CommonConsumerGoodsPurchased DTO
     */
    @Transactional
    @Override
    public CommonConsumerGoodsPurchasedDTO save(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchased) {
        log.debug("Entry - save(CommonConsumerGoodsPurchased={})", CommonConsumerGoodsPurchased);
        CommonConsumerGoodsPurchased = preHookSave(CommonConsumerGoodsPurchased);
        CommonConsumerGoodsPurchased entity = assembler.fromDTO(CommonConsumerGoodsPurchased);
        CommonConsumerGoodsPurchasedDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing CommonConsumerGoodsPurchased.
     *
     * @param CommonConsumerGoodsPurchased the CommonConsumerGoodsPurchased DTO to update
     * @return the updated CommonConsumerGoodsPurchased DTO
     * @throws NotFoundException if the CommonConsumerGoodsPurchased is not found
     */
    @Transactional
    @Override
    public CommonConsumerGoodsPurchasedDTO update(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchased) {
        log.debug("Entry - update(CommonConsumerGoodsPurchased={})", CommonConsumerGoodsPurchased);
        CommonConsumerGoodsPurchased = preHookUpdate(CommonConsumerGoodsPurchased);
        CommonConsumerGoodsPurchased saved = repository.findById(CommonConsumerGoodsPurchased.getId()).orElseThrow(() -> new NotFoundException("CommonConsumerGoodsPurchased not found"));
        saved.update(assembler.fromDTO(CommonConsumerGoodsPurchased));
        saved = repository.save(saved);
        CommonConsumerGoodsPurchasedDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of CommonConsumerGoodsPurchaseds.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing CommonConsumerGoodsPurchased DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CommonConsumerGoodsPurchasedDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<CommonConsumerGoodsPurchased> result = repository.findAll(PageRequest.of(page, size));
        Set<CommonConsumerGoodsPurchasedDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<CommonConsumerGoodsPurchasedDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a CommonConsumerGoodsPurchased by ID.
     *
     * @param id     the ID of the CommonConsumerGoodsPurchased to delete
     * @param reason the reason for deletion
     * @return the deleted CommonConsumerGoodsPurchased DTO
     * @throws NotFoundException if the CommonConsumerGoodsPurchased is not found
     */
    @Transactional
    @Override
    public CommonConsumerGoodsPurchasedDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        CommonConsumerGoodsPurchased saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CommonConsumerGoodsPurchased not found"));
        saved.deactivate(reason);
        repository.save(saved);
        CommonConsumerGoodsPurchasedDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a CommonConsumerGoodsPurchased by ID.
     *
     * @param id the ID of the CommonConsumerGoodsPurchased to retrieve
     * @return the CommonConsumerGoodsPurchased DTO
     * @throws NotFoundException if the CommonConsumerGoodsPurchased is not found
     */
    @Transactional(readOnly = true)
    @Override
    public CommonConsumerGoodsPurchasedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        CommonConsumerGoodsPurchased saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CommonConsumerGoodsPurchased not found"));
        CommonConsumerGoodsPurchasedDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<CommonConsumerGoodsPurchasedDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<CommonConsumerGoodsPurchased> savedData = repository.findAllById(ids);
        Set<CommonConsumerGoodsPurchasedDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CommonConsumerGoodsPurchasedDTO postHookSave(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected CommonConsumerGoodsPurchasedDTO preHookSave(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected CommonConsumerGoodsPurchasedDTO postHookUpdate(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected CommonConsumerGoodsPurchasedDTO preHookUpdate(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO) {
        return CommonConsumerGoodsPurchasedDTO;
    }

    protected CommonConsumerGoodsPurchasedDTO postHookDelete(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CommonConsumerGoodsPurchasedDTO postHookGetById(CommonConsumerGoodsPurchasedDTO dto) {
        return dto;
    }

    protected PageDTO<CommonConsumerGoodsPurchasedDTO> postHookGetAll(PageDTO<CommonConsumerGoodsPurchasedDTO> result) {
        return result;
    }




}
