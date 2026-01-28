package com.platformcommons.platform.service.domain.domain.vo;


import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class DomainExcelVO extends BaseExcelVO {

    @ExcelColumn(name="sl_no")
    private Long serialNo;

    @ExcelColumn(name="code")
    private String code;

    @ExcelColumn(name = "name")
    private String name;

    @ExcelColumn(name = "description")
    private String description;

    @ExcelColumn(name = "icon")
    private String icon;

    @ExcelColumn(name = "banner")
    private String banner;

    @ExcelColumn(name = "context")
    private String context;

    @ExcelColumn(name = "SDGTagCodes")
    private String sdgTagCode;

    @ExcelColumn(name = "keypoint1")
    private String keyPoint1;

    @ExcelColumn(name = "keypoint2")
    private String keyPoint2;

    @ExcelColumn(name = "keypoint3")
    private String keyPoint3;

    @ExcelColumn(name = "keypoint4")
    private String keyPoint4;

    @ExcelColumn(name = "keypoint5")
    private String keyPoint5;

    @ExcelColumn(name = "keypoint6")
    private String keyPoint6;

    @Builder
    public DomainExcelVO(Long serialNo, String code, String name, String description, String icon, String banner,
                         String sdgTagCode, String keyPoint1, String keyPoint2, String keyPoint3, String keyPoint4,
                         String keyPoint5,String context,String keyPoint6) {
        this.serialNo = serialNo;
        this.code = code;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.banner = banner;
        this.sdgTagCode = sdgTagCode;
        this.keyPoint1 = keyPoint1;
        this.keyPoint2 = keyPoint2;
        this.keyPoint3 = keyPoint3;
        this.keyPoint4 = keyPoint4;
        this.keyPoint5 = keyPoint5;
        this.context = context;
        this.keyPoint6=keyPoint6;
    }
}
