package com.platformcommons.platform.service.domain.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UseCaseExcelVO extends BaseExcelVO  {

    @ExcelColumn(name = "id")
    private Long id;
    @ExcelColumn(name = "name")
    private String name;
    @ExcelColumn(name = "shortDescription")
    private String shortDescription;
    @ExcelColumn(name = "longDescription")
    private String longDescription;
    @ExcelColumn(name = "website")
    private String website;
    @ExcelColumn(name = "domainNames")
    private String domainNames;
    @ExcelColumn(name = "subDomainNames")
    private String subDomainNames;

    @Builder
    public UseCaseExcelVO(Long id, String name, String shortDescription, String longDescription, String website, String domainNames, String subDomainNames) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.website = website;
        this.domainNames = domainNames;
        this.subDomainNames = subDomainNames;
    }
}
