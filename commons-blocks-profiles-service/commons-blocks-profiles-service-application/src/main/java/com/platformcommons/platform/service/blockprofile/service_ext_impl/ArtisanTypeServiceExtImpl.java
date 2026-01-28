package com.platformcommons.platform.service.blockprofile.service_ext_impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service_ext.*;
import com.platformcommons.platform.service.blockprofile.service_impl.ArtisanTypeServiceImpl;

import java.util.*;


@Service
@Slf4j
public class ArtisanTypeServiceExtImpl extends ArtisanTypeServiceImpl implements ArtisanTypeServiceExt {

    public ArtisanTypeServiceExtImpl(
                    ArtisanTypeRepository repository, ArtisanTypeDTOAssembler assembler) {
        super(
                repository, assembler);
    }

    /**
     * Pre-processing hook for save operation.
     *
     * @param dto the ArtisanType DTO before save
     * @return the processed ArtisanType DTO
     */
    @Override
    protected ArtisanTypeDTO preHookSave(ArtisanTypeDTO dto) {
        log.debug("Entry - preHookSave(dto={})", dto);
        ArtisanTypeDTO result = super.preHookSave(dto);
        log.debug("Exit - preHookSave() with result: {}", result);
        return super.preHookSave(result);
    }

    /**
     * Post-processing hook for save operation.
     *
     * @param dto the ArtisanType DTO after save
     * @return the processed ArtisanType DTO
     */
    @Override
    protected ArtisanTypeDTO postHookSave(ArtisanTypeDTO dto) {
        log.debug("Entry - postHookSave(dto={})", dto);
        log.debug("Exit - postHookSave() with result: {}", dto);
        return super.postHookSave(dto);
    }

    /**
     * Pre-processing hook for update operation.
     *
     * @param dto the ArtisanType DTO before update
     * @return the processed ArtisanType DTO
     */
    @Override
    protected ArtisanTypeDTO preHookUpdate(ArtisanTypeDTO dto) {
        log.debug("Entry - preHookUpdate(dto={})", dto);
        log.debug("Exit - preHookUpdate() with result: {}", dto);
        return super.preHookUpdate(dto);
    }

    /**
     * Post-processing hook for update operation.
     *
     * @param dto the ArtisanType DTO after update
     * @return the processed ArtisanType DTO
     */
    @Override
    protected ArtisanTypeDTO postHookUpdate(ArtisanTypeDTO dto) {
        log.debug("Entry - postHookUpdate(dto={})", dto);
        log.debug("Exit - postHookUpdate() with result: {}", dto);
        return super.postHookUpdate(dto);
    }

    /**
     * Pre-processing hook for delete operation.
     *
     * @param id     the ArtisanType ID
     * @param reason the reason for deletion
     */
    @Override
    protected void preHookDelete(Long id, String reason) {
        log.debug("Entry - preHookDelete(id={}, reason={})", id, reason);
        log.debug("Exit - preHookDelete()");
        super.preHookDelete(id, reason);
    }

    /**
     * Post-processing hook for delete operation.
     *
     * @param dto the ArtisanType DTO after delete
     * @return the processed ArtisanType DTO
     */
    @Override
    protected ArtisanTypeDTO postHookDelete(ArtisanTypeDTO dto) {
        log.debug("Entry - postHookDelete(dto={})", dto);
        log.debug("Exit - postHookDelete() with result: {}", dto);
        return super.postHookDelete(dto);
    }

    /**
     * Post-processing hook for getById operation.
     *
     * @param dto the ArtisanType DTO after retrieval
     * @return the processed ArtisanType DTO
     */
    @Override
    protected ArtisanTypeDTO postHookGetById(ArtisanTypeDTO dto) {
        log.debug("Entry - postHookGetById(dto={})", dto);
        log.debug("Exit - postHookGetById() with result: {}", dto);
        return super.postHookGetById(dto);
    }

    /**
     * Post-processing hook for getAll operation.
     *
     * @param result the page of ArtisanType DTOs after retrieval
     * @return the processed page of ArtisanType DTOs
     */
    @Override
    protected PageDTO<ArtisanTypeDTO> postHookGetAll(PageDTO<ArtisanTypeDTO> result) {
        log.debug("Entry - postHookGetAll(result={})", result);
        log.debug("Exit - postHookGetAll() with result: {}", result);
        return super.postHookGetAll(result);
    }
}
