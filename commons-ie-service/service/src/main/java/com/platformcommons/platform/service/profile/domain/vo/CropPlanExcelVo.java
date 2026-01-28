package com.platformcommons.platform.service.profile.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CropPlanExcelVo extends BaseExcelVO {

    @ExcelColumn(name = "SERIAL_NUMBER")
    private String serialNumber;
    @ExcelColumn(name = "FARMER_SERIAL_NUMBER")
    private String farmerSerialNumber;
    @ExcelColumn(name = "GENERIC_PRODUCT_VARIETY_CODE")
    private String genericProductVarietyCode;
    @ExcelColumn(name = "PRACTICE_CODES")
    private String practiceCodes;
    @ExcelColumn(name = "EXPECTED_HARVEST_DATE")
    private String expectedHarvestDate;
    @ExcelColumn(name = "LAND_SERIAL_NUMBER")
    private String landSerialNumber;
    @ExcelColumn(name = "ACERS_USED")
    private Double acersUsed;
    @ExcelColumn(name = "TOTAL_QUANTITY")
    private Double totalQuantity;
    @ExcelColumn(name = "TOTAL_QUANTITY_UOM")
    private String totalQuantityUOM;
    @ExcelColumn(name = "INTENT_TO_SELL")
    private Double intentToSell;
    @ExcelColumn(name = "INTENT_TO_SELL_UOM")
    private String intentToSellUOM;

    @Builder
    public CropPlanExcelVo(Long rowNumber, String serialNumber, String farmerSerialNumber, String genericProductVarietyCode, String expectedHarvestDate, String landSerialNumber, Double acersUsed, Double totalQuantity, Double intentToSell,String totalQuantityUOM,String intentToSellUOM) {
        super(rowNumber);
        this.serialNumber = serialNumber;
        this.farmerSerialNumber = farmerSerialNumber;
        this.genericProductVarietyCode = genericProductVarietyCode;
        this.expectedHarvestDate = expectedHarvestDate;
        this.landSerialNumber = landSerialNumber;
        this.acersUsed = acersUsed;
        this.totalQuantity = totalQuantity;
        this.intentToSell = intentToSell;
        this.totalQuantityUOM = totalQuantityUOM;
        this.intentToSellUOM = intentToSellUOM;
    }

    public Date getExpectedHarvestDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(this.expectedHarvestDate);
        } catch (ParseException e) {
            return null;
        }
    }
}