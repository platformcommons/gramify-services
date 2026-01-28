package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MainSurplusDestinationService;


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

public class MainSurplusDestinationServiceImpl implements MainSurplusDestinationService {

    private final MainSurplusDestinationDTOAssembler assembler;
    private final MainSurplusDestinationRepository repository;


    public MainSurplusDestinationServiceImpl(
        MainSurplusDestinationRepository repository, MainSurplusDestinationDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MainSurplusDestination.
     *
     * @param MainSurplusDestination the MainSurplusDestination DTO to save
     * @return the saved MainSurplusDestination DTO
     */
    @Transactional
    @Override
    public MainSurplusDestinationDTO save(MainSurplusDestinationDTO MainSurplusDestination) {
        log.debug("Entry - save(MainSurplusDestination={})", MainSurplusDestination);
        MainSurplusDestination = preHookSave(MainSurplusDestination);
        MainSurplusDestination entity = assembler.fromDTO(MainSurplusDestination);
        MainSurplusDestinationDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MainSurplusDestination.
     *
     * @param MainSurplusDestination the MainSurplusDestination DTO to update
     * @return the updated MainSurplusDestination DTO
     * @throws NotFoundException if the MainSurplusDestination is not found
     */
    @Transactional
    @Override
    public MainSurplusDestinationDTO update(MainSurplusDestinationDTO MainSurplusDestination) {
        log.debug("Entry - update(MainSurplusDestination={})", MainSurplusDestination);
        MainSurplusDestination = preHookUpdate(MainSurplusDestination);
        MainSurplusDestination saved = repository.findById(MainSurplusDestination.getId()).orElseThrow(() -> new NotFoundException("MainSurplusDestination not found"));
        saved.update(assembler.fromDTO(MainSurplusDestination));
        saved = repository.save(saved);
        MainSurplusDestinationDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MainSurplusDestinations.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MainSurplusDestination DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MainSurplusDestinationDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MainSurplusDestination> result = repository.findAll(PageRequest.of(page, size));
        Set<MainSurplusDestinationDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MainSurplusDestinationDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MainSurplusDestination by ID.
     *
     * @param id     the ID of the MainSurplusDestination to delete
     * @param reason the reason for deletion
     * @return the deleted MainSurplusDestination DTO
     * @throws NotFoundException if the MainSurplusDestination is not found
     */
    @Transactional
    @Override
    public MainSurplusDestinationDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MainSurplusDestination saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainSurplusDestination not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MainSurplusDestinationDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MainSurplusDestination by ID.
     *
     * @param id the ID of the MainSurplusDestination to retrieve
     * @return the MainSurplusDestination DTO
     * @throws NotFoundException if the MainSurplusDestination is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MainSurplusDestinationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MainSurplusDestination saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainSurplusDestination not found"));
        MainSurplusDestinationDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MainSurplusDestinationDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MainSurplusDestination> savedData = repository.findAllById(ids);
        Set<MainSurplusDestinationDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainSurplusDestinationDTO postHookSave(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected MainSurplusDestinationDTO preHookSave(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected MainSurplusDestinationDTO postHookUpdate(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected MainSurplusDestinationDTO preHookUpdate(MainSurplusDestinationDTO MainSurplusDestinationDTO) {
        return MainSurplusDestinationDTO;
    }

    protected MainSurplusDestinationDTO postHookDelete(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainSurplusDestinationDTO postHookGetById(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected PageDTO<MainSurplusDestinationDTO> postHookGetAll(PageDTO<MainSurplusDestinationDTO> result) {
        return result;
    }




}
