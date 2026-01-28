package com.platformcommons.platform.service.blockprofile.service_ext_impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service_ext.*;
import com.platformcommons.platform.service.blockprofile.service_impl.SourceOfRawMaterialServiceImpl;

import java.util.*;


@Service
@Slf4j
public class SourceOfRawMaterialServiceExtImpl extends SourceOfRawMaterialServiceImpl implements SourceOfRawMaterialServiceExt {

    public SourceOfRawMaterialServiceExtImpl(
                    SourceOfRawMaterialRepository repository, SourceOfRawMaterialDTOAssembler assembler) {
        super(
                repository, assembler);
    }

    /**
     * Pre-processing hook for save operation.
     *
     * @param dto the SourceOfRawMaterial DTO before save
     * @return the processed SourceOfRawMaterial DTO
     */
    @Override
    protected SourceOfRawMaterialDTO preHookSave(SourceOfRawMaterialDTO dto) {
        log.debug("Entry - preHookSave(dto={})", dto);
        SourceOfRawMaterialDTO result = super.preHookSave(dto);
        log.debug("Exit - preHookSave() with result: {}", result);
        return super.preHookSave(result);
    }

    /**
     * Post-processing hook for save operation.
     *
     * @param dto the SourceOfRawMaterial DTO after save
     * @return the processed SourceOfRawMaterial DTO
     */
    @Override
    protected SourceOfRawMaterialDTO postHookSave(SourceOfRawMaterialDTO dto) {
        log.debug("Entry - postHookSave(dto={})", dto);
        log.debug("Exit - postHookSave() with result: {}", dto);
        return super.postHookSave(dto);
    }

    /**
     * Pre-processing hook for update operation.
     *
     * @param dto the SourceOfRawMaterial DTO before update
     * @return the processed SourceOfRawMaterial DTO
     */
    @Override
    protected SourceOfRawMaterialDTO preHookUpdate(SourceOfRawMaterialDTO dto) {
        log.debug("Entry - preHookUpdate(dto={})", dto);
        log.debug("Exit - preHookUpdate() with result: {}", dto);
        return super.preHookUpdate(dto);
    }

    /**
     * Post-processing hook for update operation.
     *
     * @param dto the SourceOfRawMaterial DTO after update
     * @return the processed SourceOfRawMaterial DTO
     */
    @Override
    protected SourceOfRawMaterialDTO postHookUpdate(SourceOfRawMaterialDTO dto) {
        log.debug("Entry - postHookUpdate(dto={})", dto);
        log.debug("Exit - postHookUpdate() with result: {}", dto);
        return super.postHookUpdate(dto);
    }

    /**
     * Pre-processing hook for delete operation.
     *
     * @param id     the SourceOfRawMaterial ID
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
     * @param dto the SourceOfRawMaterial DTO after delete
     * @return the processed SourceOfRawMaterial DTO
     */
    @Override
    protected SourceOfRawMaterialDTO postHookDelete(SourceOfRawMaterialDTO dto) {
        log.debug("Entry - postHookDelete(dto={})", dto);
        log.debug("Exit - postHookDelete() with result: {}", dto);
        return super.postHookDelete(dto);
    }

    /**
     * Post-processing hook for getById operation.
     *
     * @param dto the SourceOfRawMaterial DTO after retrieval
     * @return the processed SourceOfRawMaterial DTO
     */
    @Override
    protected SourceOfRawMaterialDTO postHookGetById(SourceOfRawMaterialDTO dto) {
        log.debug("Entry - postHookGetById(dto={})", dto);
        log.debug("Exit - postHookGetById() with result: {}", dto);
        return super.postHookGetById(dto);
    }

    /**
     * Post-processing hook for getAll operation.
     *
     * @param result the page of SourceOfRawMaterial DTOs after retrieval
     * @return the processed page of SourceOfRawMaterial DTOs
     */
    @Override
    protected PageDTO<SourceOfRawMaterialDTO> postHookGetAll(PageDTO<SourceOfRawMaterialDTO> result) {
        log.debug("Entry - postHookGetAll(result={})", result);
        log.debug("Exit - postHookGetAll() with result: {}", result);
        return super.postHookGetAll(result);
    }
}
