package com.platformcommons.platform.service.domain.domain.vo;


import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class DomainExcelVOV2 extends BaseExcelVO {

    @ExcelColumn(name = "ThemeName")
    private String name;

    @ExcelColumn(name = "ThemeDescription")
    private String description;



    @ExcelColumn(name = "SDGs")
    private String sdgs;

    @ExcelColumn(name = "keyPoints")
    private String keyPoints;

    @ExcelColumn(name = "tags")
    private String tags;


    @Builder
    public DomainExcelVOV2(Long rowNumber, String name, String description, String sdgs, String keyPoints,String tags) {
        super(rowNumber);
        this.name = name;
        this.description = description;
        this.sdgs = sdgs;
        this.keyPoints = keyPoints;
        this.tags = tags;
    }
}
