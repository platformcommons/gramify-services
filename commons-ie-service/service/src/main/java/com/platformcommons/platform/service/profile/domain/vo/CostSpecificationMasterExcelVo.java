package com.platformcommons.platform.service.profile.domain.vo;


import com.platformcommons.platform.service.entity.common.MLText;
import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.*;

import javax.persistence.Column;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class CostSpecificationMasterExcelVo extends BaseExcelVO {

    @ExcelColumn(name = "SERIAL_NUMBER")
    private Long serialNumber;

    @ExcelColumn(name = "LABEL")
    private String label;

    @ExcelColumn(name = "INPUT_TYPE")
    private String inputType;

    @ExcelColumn(name = "CODE")
    private String code;

    @ExcelColumn(name = "FORMULA")
    private String formula;

    @ExcelColumn(name = "FIELD_TYPE")
    private String fieldType;

    @ExcelColumn(name = "FOR_ENTITY_TYPE")
    private String forEntityType;

    @ExcelColumn(name = "FOR_ENTITY_ID")
    private String forEntityId;

    @ExcelColumn(name = "PURPOSE")
    private String purpose;

    @ExcelColumn(name = "CONTEXT")
    private String context;

    @ExcelColumn(name = "CONTEXT_ID")
    private String contextId;

    @ExcelColumn(name = "SEQUENCE")
    private Long sequence;

    @ExcelColumn(name = "INPUT_TYPE_LABEL")
    private String inputTypeLabel;

    @ExcelColumn(name = "GROUPING_CODE")
    private String groupingCode;

    @Builder
    public CostSpecificationMasterExcelVo(Long rowNumber, Long serialNumber, String label, String inputType,
                                          String code, String formula, String fieldType, String forEntityType,
                                          String forEntityId, String purpose, String context, String contextId,
                                          Long sequence, String inputTypeLabel, String groupingCode) {
        super(rowNumber);
        this.serialNumber = serialNumber;
        this.label = label;
        this.inputType = inputType;
        this.code = code;
        this.formula = formula;
        this.fieldType = fieldType;
        this.forEntityType = forEntityType;
        this.forEntityId = forEntityId;
        this.purpose = purpose;
        this.context = context;
        this.contextId = contextId;
        this.sequence = sequence;
        this.inputTypeLabel=inputTypeLabel;
        this.groupingCode=groupingCode;
    }
}


