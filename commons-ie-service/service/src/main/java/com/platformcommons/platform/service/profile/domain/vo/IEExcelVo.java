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
public class IEExcelVo extends BaseExcelVO {

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

    @ExcelColumn(name = "VILLAGE")
    private String village;

    @ExcelColumn(name = "PANCHAYAT")
    private String panchayat;

    @ExcelColumn(name = "MANDAL")
    private String mandal;

    @ExcelColumn(name = "DISTRICT")
    private String district;

    @ExcelColumn(name = "STATE")
    private String state;

    @ExcelColumn(name = "PINCODE")
    private String pincode;

    @ExcelColumn(name = "DELIVERY_MODES")
    private String deliveryModes;

    @ExcelColumn(name = "ABA_CONTACT")
    private String abaLogin;

    @Builder
    public IEExcelVo(Long rowNumber, String serialNumber, String firstName, String contactNumber, String gender, String village, String panchayat, String block, String district, String pincode, String deliveryModes, String fatherName, String dataOfBirth, String abaLogin, String mandal) {
        super(rowNumber);
        this.serialNumber = serialNumber;
        this.firstName = firstName;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.village = village;
        this.panchayat = panchayat;
        this.district = district;
        this.pincode = pincode;
        this.deliveryModes = deliveryModes;
        this.fatherName = fatherName;
        this.abaLogin = abaLogin;
        this.mandal = mandal;
    }



    public String getSerialNumber(){
        return serialNumber!=null && serialNumber.isEmpty()?null:this.serialNumber;
    }


    public boolean hasContact() {
        return contactNumber!=null && !contactNumber.isEmpty();
    }

}