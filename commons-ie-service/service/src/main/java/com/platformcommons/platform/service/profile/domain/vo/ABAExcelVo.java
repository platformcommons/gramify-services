package com.platformcommons.platform.service.profile.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ABAExcelVo extends BaseExcelVO {

    @ExcelColumn(name="SERIAL_NUMBER")
    private String serialNumber;
    @ExcelColumn(name="FIRST_NAME")
    private String firstName;
    @ExcelColumn(name="LAST_NAME")
    private String lastName;
    @ExcelColumn(name="CONTACT")
    private String contact;
    @ExcelColumn(name="PASSWORD")
    private String password;
    @ExcelColumn(name="FPO_NAME")
    private String fpoName;

    @Builder
    public ABAExcelVo(Long rowNumber, String serialNumber, String firstName, String lastName, String contact, String fpoName,String password) {
        super(rowNumber);
        this.serialNumber = serialNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.fpoName = fpoName;
        this.password = password;
    }
}