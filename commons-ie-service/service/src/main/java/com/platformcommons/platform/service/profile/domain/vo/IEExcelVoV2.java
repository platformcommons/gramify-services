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
public class IEExcelVoV2 extends BaseExcelVO {

    @ExcelColumn(name = "SERIAL_NUMBER")
    private String serialNumber;

    @ExcelColumn(name = "FARMER_NAME")
    private String firstName;

    @ExcelColumn(name = "MOBILE_NUMBER")
    private String contactNumber;

    @ExcelColumn(name = "GENDER")
    private String gender;

    @ExcelColumn(name = "FATHER_NAME")
    private String fatherName;

    @ExcelColumn(name = "SPOUSE_NAME")
    private String spouseName;

    @ExcelColumn(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @ExcelColumn(name = "DISTRICT")
    private String district;

    @ExcelColumn(name = "STATE")
    private String state;

    @ExcelColumn(name = "PINCODE")
    private String pincode;

    @ExcelColumn(name = "DELIVERY_MODES")
    private String deliveryModes;

    @ExcelColumn(name = "WORKFORCE_ID")
    private String workforceId;

    @ExcelColumn(name = "WORKNODE_ID")
    private String worknodeId;

    @ExcelColumn(name = "IE_REFERENCE_ID")
    private String ieReferenceId;


    @ExcelColumn(name = "IE_TYPE")
    private String ieType;

    @ExcelColumn(name = "IE_SUB_TYPE")
    private String ieSubType;

    @ExcelColumn(name = "SERVICE_AREA_TYPE")
    private String taggedToServiceAreaType;

    @ExcelColumn(name = "SERVICE_AREA_CODE")
    private String taggedToServiceAreaCode;





    @Builder
    public IEExcelVoV2(Long rowNumber, String serialNumber, String firstName, String contactNumber, String gender, String district,
                       String pincode, String deliveryModes, String fatherName, String spouseName ,String workforceId, String worknodeId,
                       String addressLine1, String ieReferenceId, String ieType, String ieSubType,String serviceAreaType,String serviceAreaCode) {
        super(rowNumber);
        this.serialNumber = serialNumber;
        this.firstName = firstName;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.district = district;
        this.pincode = pincode;
        this.deliveryModes = deliveryModes;
        this.fatherName = fatherName;
        this.spouseName = spouseName;
        this.workforceId = workforceId;
        this.worknodeId = worknodeId;
        this.addressLine1= addressLine1;
        this.ieReferenceId = ieReferenceId;
        this.ieType = ieType;
        this.ieSubType = ieSubType;
        this.taggedToServiceAreaType = serviceAreaType;
        this.taggedToServiceAreaCode = serviceAreaCode;
    }



    public String getSerialNumber(){
        return serialNumber!=null && serialNumber.isEmpty()?null:this.serialNumber;
    }


    public boolean hasContact() {
        return contactNumber!=null && !contactNumber.isEmpty();
    }

}