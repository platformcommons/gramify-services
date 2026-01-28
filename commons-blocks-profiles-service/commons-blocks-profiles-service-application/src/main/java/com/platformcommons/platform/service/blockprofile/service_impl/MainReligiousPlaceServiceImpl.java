package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MainReligiousPlaceService;


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

public class MainReligiousPlaceServiceImpl implements MainReligiousPlaceService {

    private final MainReligiousPlaceDTOAssembler assembler;
    private final MainReligiousPlaceRepository repository;


    public MainReligiousPlaceServiceImpl(
        MainReligiousPlaceRepository repository, MainReligiousPlaceDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MainReligiousPlace.
     *
     * @param MainReligiousPlace the MainReligiousPlace DTO to save
     * @return the saved MainReligiousPlace DTO
     */
    @Transactional
    @Override
    public MainReligiousPlaceDTO save(MainReligiousPlaceDTO MainReligiousPlace) {
        log.debug("Entry - save(MainReligiousPlace={})", MainReligiousPlace);
        MainReligiousPlace = preHookSave(MainReligiousPlace);
        MainReligiousPlace entity = assembler.fromDTO(MainReligiousPlace);
        MainReligiousPlaceDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MainReligiousPlace.
     *
     * @param MainReligiousPlace the MainReligiousPlace DTO to update
     * @return the updated MainReligiousPlace DTO
     * @throws NotFoundException if the MainReligiousPlace is not found
     */
    @Transactional
    @Override
    public MainReligiousPlaceDTO update(MainReligiousPlaceDTO MainReligiousPlace) {
        log.debug("Entry - update(MainReligiousPlace={})", MainReligiousPlace);
        MainReligiousPlace = preHookUpdate(MainReligiousPlace);
        MainReligiousPlace saved = repository.findById(MainReligiousPlace.getId()).orElseThrow(() -> new NotFoundException("MainReligiousPlace not found"));
        saved.update(assembler.fromDTO(MainReligiousPlace));
        saved = repository.save(saved);
        MainReligiousPlaceDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MainReligiousPlaces.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MainReligiousPlace DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MainReligiousPlaceDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MainReligiousPlace> result = repository.findAll(PageRequest.of(page, size));
        Set<MainReligiousPlaceDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MainReligiousPlaceDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MainReligiousPlace by ID.
     *
     * @param id     the ID of the MainReligiousPlace to delete
     * @param reason the reason for deletion
     * @return the deleted MainReligiousPlace DTO
     * @throws NotFoundException if the MainReligiousPlace is not found
     */
    @Transactional
    @Override
    public MainReligiousPlaceDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MainReligiousPlace saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainReligiousPlace not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MainReligiousPlaceDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MainReligiousPlace by ID.
     *
     * @param id the ID of the MainReligiousPlace to retrieve
     * @return the MainReligiousPlace DTO
     * @throws NotFoundException if the MainReligiousPlace is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MainReligiousPlaceDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MainReligiousPlace saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainReligiousPlace not found"));
        MainReligiousPlaceDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MainReligiousPlaceDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MainReligiousPlace> savedData = repository.findAllById(ids);
        Set<MainReligiousPlaceDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainReligiousPlaceDTO postHookSave(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected MainReligiousPlaceDTO preHookSave(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected MainReligiousPlaceDTO postHookUpdate(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected MainReligiousPlaceDTO preHookUpdate(MainReligiousPlaceDTO MainReligiousPlaceDTO) {
        return MainReligiousPlaceDTO;
    }

    protected MainReligiousPlaceDTO postHookDelete(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainReligiousPlaceDTO postHookGetById(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected PageDTO<MainReligiousPlaceDTO> postHookGetAll(PageDTO<MainReligiousPlaceDTO> result) {
        return result;
    }




}
