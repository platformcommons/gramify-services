package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import com.platformcommons.platform.service.profile.application.CostSpecificationMasterService;
import com.platformcommons.platform.service.profile.domain.CostSpecificationMaster;
import com.platformcommons.platform.service.profile.domain.Enum.FieldType;
import com.platformcommons.platform.service.profile.domain.Enum.InputType;
import com.platformcommons.platform.service.profile.domain.vo.CostSpecificationMasterExcelVo;
import com.platformcommons.platform.service.profile.dto.CostSpecificationMasterDTO;
import com.platformcommons.platform.service.profile.facade.CostSpecificationMasterFacade;
import com.platformcommons.platform.service.profile.facade.assembler.CostSpecificationMasterDTOAssembler;
import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BulkUploadRequest;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.PoiExcelHelper;
import com.platformcommons.platform.service.sdk.bulkupload.service.BulkUploadService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class CostSpecificationMasterFacadeImpl implements CostSpecificationMasterFacade {

    @Autowired
    private CostSpecificationMasterService costSpecificationMasterService;

    @Autowired
    private CostSpecificationMasterDTOAssembler costSpecificationMasterDTOAssembler;

    @Autowired
    private BulkUploadService bulkUploadService;

    @Override
    public void delete(Long id, String reason) {
        costSpecificationMasterService.deleteById(id, reason);
    }

    @Override
    public CostSpecificationMasterDTO getById(Long id) {
        return costSpecificationMasterDTOAssembler.toDTO(costSpecificationMasterService.getById(id));
    }

    @Override
    public PageDTO<CostSpecificationMasterDTO> getAllPage(Integer page, Integer size) {
        Page<CostSpecificationMaster> result = costSpecificationMasterService.getByPage(page, size);
        return new PageDTO<>(result.stream()
                .map(costSpecificationMasterDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public Long save(CostSpecificationMasterDTO body) {
        CostSpecificationMaster costSpecificationMaster = costSpecificationMasterService.save(costSpecificationMasterDTOAssembler.fromDTO(body));
        return costSpecificationMaster.getId();
    }

    @Override
    public CostSpecificationMasterDTO update(CostSpecificationMasterDTO body) {
        return costSpecificationMasterDTOAssembler.toDTO(costSpecificationMasterService.update(costSpecificationMasterDTOAssembler.fromDTO(body)));
    }

    @Override
    public PageDTO<CostSpecificationMasterDTO> getCostSpecificationMasterByContext(String context, String contextId, Integer page, Integer size) {
        Page<CostSpecificationMaster> result = costSpecificationMasterService.getCostSpecificationMasterByContext(context,contextId,page, size);
        return new PageDTO<>(result.stream()
                .map(costSpecificationMasterDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void createBulkCostSpecificationMaster(MultipartFile file) throws Exception{
        if(file!=null){
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            List<CostSpecificationMasterExcelVo> specificationMasterVoList = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0),CostSpecificationMasterExcelVo.class);
            BulkUploadRequest bulkUploadRequest = bulkUploadService.requestBulkUpload(file,"ENTITY_TYPE.COST_SPECIFICATION_MASTER");
            bulkUploadService.operate(specificationMasterVoList, it->this.operate(it),bulkUploadRequest);
        }
    }

    private void operate(CostSpecificationMasterExcelVo vo) {

        Set<MLText> labels = parseLabels(vo.getLabel());

        costSpecificationMasterService.save(
                CostSpecificationMaster.builder()
                        .code(vo.getCode())
                        .inputType(InputType.valueOf(vo.getInputType()))
                        .formula(vo.getFormula())
                        .fieldType(FieldType.valueOf(vo.getFieldType()))
                        .forEntityType(vo.getForEntityType())
                        .forEntityId(vo.getForEntityId())
                        .purpose(vo.getPurpose())
                        .context(vo.getContext())
                        .contextId(vo.getContextId())
                        .sequence(vo.getSequence())
                        .inputTypeLabel(vo.getInputTypeLabel())
                        .groupingCode(vo.getGroupingCode())
                        .label(labels)
                        .build()
        );
    }

    private Set<MLText> parseLabels(String labelData) {
        if (labelData == null || labelData.trim().isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(labelData.split(","))
                .map(label -> label.split(":", 2))
                .filter(parts -> parts.length == 2 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty())
                .map(parts -> MLText.builder()
                        .languageCode(parts[0].trim())
                        .text(parts[1].trim())
                        .build())
                .collect(Collectors.toSet());
    }
}
