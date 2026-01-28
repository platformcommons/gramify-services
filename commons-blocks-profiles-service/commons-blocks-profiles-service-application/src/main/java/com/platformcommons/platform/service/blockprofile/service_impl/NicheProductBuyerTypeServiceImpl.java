package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.NicheProductBuyerTypeService;


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

public class NicheProductBuyerTypeServiceImpl implements NicheProductBuyerTypeService {

    private final NicheProductBuyerTypeDTOAssembler assembler;
    private final NicheProductBuyerTypeRepository repository;


    public NicheProductBuyerTypeServiceImpl(
        NicheProductBuyerTypeRepository repository, NicheProductBuyerTypeDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new NicheProductBuyerType.
     *
     * @param NicheProductBuyerType the NicheProductBuyerType DTO to save
     * @return the saved NicheProductBuyerType DTO
     */
    @Transactional
    @Override
    public NicheProductBuyerTypeDTO save(NicheProductBuyerTypeDTO NicheProductBuyerType) {
        log.debug("Entry - save(NicheProductBuyerType={})", NicheProductBuyerType);
        NicheProductBuyerType = preHookSave(NicheProductBuyerType);
        NicheProductBuyerType entity = assembler.fromDTO(NicheProductBuyerType);
        NicheProductBuyerTypeDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing NicheProductBuyerType.
     *
     * @param NicheProductBuyerType the NicheProductBuyerType DTO to update
     * @return the updated NicheProductBuyerType DTO
     * @throws NotFoundException if the NicheProductBuyerType is not found
     */
    @Transactional
    @Override
    public NicheProductBuyerTypeDTO update(NicheProductBuyerTypeDTO NicheProductBuyerType) {
        log.debug("Entry - update(NicheProductBuyerType={})", NicheProductBuyerType);
        NicheProductBuyerType = preHookUpdate(NicheProductBuyerType);
        NicheProductBuyerType saved = repository.findById(NicheProductBuyerType.getId()).orElseThrow(() -> new NotFoundException("NicheProductBuyerType not found"));
        saved.update(assembler.fromDTO(NicheProductBuyerType));
        saved = repository.save(saved);
        NicheProductBuyerTypeDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of NicheProductBuyerTypes.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing NicheProductBuyerType DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<NicheProductBuyerTypeDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<NicheProductBuyerType> result = repository.findAll(PageRequest.of(page, size));
        Set<NicheProductBuyerTypeDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<NicheProductBuyerTypeDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a NicheProductBuyerType by ID.
     *
     * @param id     the ID of the NicheProductBuyerType to delete
     * @param reason the reason for deletion
     * @return the deleted NicheProductBuyerType DTO
     * @throws NotFoundException if the NicheProductBuyerType is not found
     */
    @Transactional
    @Override
    public NicheProductBuyerTypeDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        NicheProductBuyerType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NicheProductBuyerType not found"));
        saved.deactivate(reason);
        repository.save(saved);
        NicheProductBuyerTypeDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a NicheProductBuyerType by ID.
     *
     * @param id the ID of the NicheProductBuyerType to retrieve
     * @return the NicheProductBuyerType DTO
     * @throws NotFoundException if the NicheProductBuyerType is not found
     */
    @Transactional(readOnly = true)
    @Override
    public NicheProductBuyerTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        NicheProductBuyerType saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NicheProductBuyerType not found"));
        NicheProductBuyerTypeDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<NicheProductBuyerTypeDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<NicheProductBuyerType> savedData = repository.findAllById(ids);
        Set<NicheProductBuyerTypeDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected NicheProductBuyerTypeDTO postHookSave(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected NicheProductBuyerTypeDTO preHookSave(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected NicheProductBuyerTypeDTO postHookUpdate(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected NicheProductBuyerTypeDTO preHookUpdate(NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO) {
        return NicheProductBuyerTypeDTO;
    }

    protected NicheProductBuyerTypeDTO postHookDelete(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NicheProductBuyerTypeDTO postHookGetById(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected PageDTO<NicheProductBuyerTypeDTO> postHookGetAll(PageDTO<NicheProductBuyerTypeDTO> result) {
        return result;
    }




}
