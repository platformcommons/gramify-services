package com.platformcommons.platform.service.domain.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
public class ApplicationExcelVO extends BaseExcelVO {

    @ExcelColumn(name = "name")
    private String name;
    @ExcelColumn(name = "shortDescription")
    private String shortDescription;
    @ExcelColumn(name = "longDescription")
    private String longDescription;
    @ExcelColumn(name = "logo")
    private String logo;
    @ExcelColumn(name = "website")
    private String website;
    @ExcelColumn(name = "downloadCount")
    private Long downloadCount;
    @ExcelColumn(name = "currentVersion")
    private String currentVersion;
    @ExcelColumn(name = "averageRating")
    private Double averageRating;
    @ExcelColumn(name = "domainCodes")
    private String domainCodes;
    @ExcelColumn(name = "subDomainCodes")
    private String subDomainCodes;
    @ExcelColumn(name = "useCaseNames")
    private String useCaseCodes;
    @ExcelColumn(name = "platformNames")
    private String platformNames;
    @ExcelColumn(name = "features")
    private String features;
    @ExcelColumn(name = "keywords")
    private String keywords;

    @ExcelColumn(name = "multilingual")
    private String multilingual;
    @ExcelColumn(name = "offline")
    private String offline;
    @ExcelColumn(name = "whitelabel")
    private String whitelabel;


}
