package com.platformcommons.platform.service.blockprofile.service_ext_impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service_ext.*;
import com.platformcommons.platform.service.blockprofile.service_impl.ConsumptionServiceImpl;

import java.util.*;


@Service
@Slf4j
public class ConsumptionServiceExtImpl extends ConsumptionServiceImpl implements ConsumptionServiceExt {

    public ConsumptionServiceExtImpl(
                    ConsumptionRepository repository, ConsumptionDTOAssembler assembler) {
        super(
                repository, assembler);
    }

    /**
     * Pre-processing hook for save operation.
     *
     * @param dto the Consumption DTO before save
     * @return the processed Consumption DTO
     */
    @Override
    protected ConsumptionDTO preHookSave(ConsumptionDTO dto) {
        log.debug("Entry - preHookSave(dto={})", dto);
        ConsumptionDTO result = super.preHookSave(dto);
        log.debug("Exit - preHookSave() with result: {}", result);
        return super.preHookSave(result);
    }

    /**
     * Post-processing hook for save operation.
     *
     * @param dto the Consumption DTO after save
     * @return the processed Consumption DTO
     */
    @Override
    protected ConsumptionDTO postHookSave(ConsumptionDTO dto) {
        log.debug("Entry - postHookSave(dto={})", dto);
        log.debug("Exit - postHookSave() with result: {}", dto);
        return super.postHookSave(dto);
    }

    /**
     * Pre-processing hook for update operation.
     *
     * @param dto the Consumption DTO before update
     * @return the processed Consumption DTO
     */
    @Override
    protected ConsumptionDTO preHookUpdate(ConsumptionDTO dto) {
        log.debug("Entry - preHookUpdate(dto={})", dto);
        log.debug("Exit - preHookUpdate() with result: {}", dto);
        return super.preHookUpdate(dto);
    }

    /**
     * Post-processing hook for update operation.
     *
     * @param dto the Consumption DTO after update
     * @return the processed Consumption DTO
     */
    @Override
    protected ConsumptionDTO postHookUpdate(ConsumptionDTO dto) {
        log.debug("Entry - postHookUpdate(dto={})", dto);
        log.debug("Exit - postHookUpdate() with result: {}", dto);
        return super.postHookUpdate(dto);
    }

    /**
     * Pre-processing hook for delete operation.
     *
     * @param id     the Consumption ID
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
     * @param dto the Consumption DTO after delete
     * @return the processed Consumption DTO
     */
    @Override
    protected ConsumptionDTO postHookDelete(ConsumptionDTO dto) {
        log.debug("Entry - postHookDelete(dto={})", dto);
        log.debug("Exit - postHookDelete() with result: {}", dto);
        return super.postHookDelete(dto);
    }

    /**
     * Post-processing hook for getById operation.
     *
     * @param dto the Consumption DTO after retrieval
     * @return the processed Consumption DTO
     */
    @Override
    protected ConsumptionDTO postHookGetById(ConsumptionDTO dto) {
        log.debug("Entry - postHookGetById(dto={})", dto);
        log.debug("Exit - postHookGetById() with result: {}", dto);
        return super.postHookGetById(dto);
    }

    /**
     * Post-processing hook for getAll operation.
     *
     * @param result the page of Consumption DTOs after retrieval
     * @return the processed page of Consumption DTOs
     */
    @Override
    protected PageDTO<ConsumptionDTO> postHookGetAll(PageDTO<ConsumptionDTO> result) {
        log.debug("Entry - postHookGetAll(result={})", result);
        log.debug("Exit - postHookGetAll() with result: {}", result);
        return super.postHookGetAll(result);
    }
}
