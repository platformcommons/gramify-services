package com.platformcommons.platform.service.domain.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UseCaseExcelVOV2 extends BaseExcelVO  {


    @ExcelColumn(name = "Theme")
    private String theme;
    @ExcelColumn(name = "UseCaseName")
    private String useCaseName;
    @ExcelColumn(name = "Description")
    private String description;
    @ExcelColumn(name = "Keywords")
    private String keywords;

    @ExcelColumn(name = "sequence")
    private String sequence;

    @Builder
    public UseCaseExcelVOV2(Long rowNumber, String theme, String useCaseName, String description, String keywords,String sequence) {
        super(rowNumber);
        this.theme = theme;
        this.useCaseName = useCaseName;
        this.description = description;
        this.keywords = keywords;
        this.sequence = sequence;
    }
}
