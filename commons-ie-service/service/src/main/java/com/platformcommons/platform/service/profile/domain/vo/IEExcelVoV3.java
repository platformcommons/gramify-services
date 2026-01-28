package com.platformcommons.platform.service.profile.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class IEExcelVoV3 extends BaseExcelVO {

    @ExcelColumn(name = "SERIAL_NUMBER")
    private String serialNumber;

    @ExcelColumn(name = "LS_MOBILE_NUMBER")
    private String lsMobileNumber;

    @ExcelColumn(name = "FARMER_UNIQUE_IDENTIFIER")
    private String farmerUniqueIdentifier;

    @ExcelColumn(name = "FARMER_NAME")
    private String farmerName;

    @ExcelColumn(name = "LFS_CODE")
    private String serviceAreaCode;

    @ExcelColumn(name = "VILLAGE")
    private String village;

    @ExcelColumn(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @ExcelColumn(name = "ON_BOARDED_DATE")
    private Date onBoardedDate;

    @ExcelColumn(name = "FARMER_TYPE")
    private String farmerType;

    @ExcelColumn(name = "GENDER")
    private String gender;

    @ExcelColumn(name = "AGE")
    private Long age;

    @ExcelColumn(name = "WORKFORCE_ID")
    private String workforceId;

    @ExcelColumn(name = "WORKNODE_ID")
    private String worknodeId;

    @ExcelColumn(name = "FARMER_DROP_OUT")
    private String farmerDropOut;

    @ExcelColumn(name = "tagged_to_service_area_type")
    private String serviceAreaType;

    @Builder
    public IEExcelVoV3(Long rowNumber, String serialNumber, String lsMobileNumber, String farmerUniqueIdentifier,
                       String farmerName, String serviceAreaCode, String village, String mobileNumber, Date onBoardedDate,
                               String farmerType, String gender, Long age, String workforceId, String worknodeId, String farmerDropOut, String serviceAreaType) {
        super(rowNumber);
        this.serialNumber = serialNumber;
        this.lsMobileNumber = lsMobileNumber;
        this.farmerUniqueIdentifier = farmerUniqueIdentifier;
        this.farmerName = farmerName;
        this.serviceAreaCode = serviceAreaCode;
        this.village=village;
        this.mobileNumber = mobileNumber;
        this.onBoardedDate = onBoardedDate;
        this.farmerType = farmerType;
        this.gender = gender;
        this.age = age;
        this.workforceId=workforceId;
        this.worknodeId=worknodeId;
        this.farmerDropOut = farmerDropOut;
        this.serviceAreaType = serviceAreaType;
    }

    public boolean hasFarmerName() {
        return farmerName != null && !farmerName.trim().isEmpty();
    }

    public boolean hasMobileNumber() {
        return mobileNumber != null && !mobileNumber.trim().isEmpty();
    }

    public boolean hasFarmerUniqueIdentifier() {
        return farmerUniqueIdentifier != null && !farmerUniqueIdentifier.trim().isEmpty();
    }
}

