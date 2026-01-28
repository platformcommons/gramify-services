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
public class AssetExcelVO extends BaseExcelVO {


    @ExcelColumn(name="SERIAL_NUMBER")
    private String serialNumber;

    @ExcelColumn(name="FARMER_SERIAL_NUMBER")
    private String farmerSerialNumber;

    @ExcelColumn(name="LAND_OWNED_TYPE")
    private String landOwnedType;

    @ExcelColumn(name = "LAND_NAME")
    private String name;

    @ExcelColumn(name = "LAND_IDENTIFIER")
    private String code;

    @ExcelColumn(name="ACRE")
    private Double acre;

    @ExcelColumn(name="LAND_HOLDER_TYPE")
    private String landHolderType;

    @Builder
    public AssetExcelVO(Long rowNumber, String serialNumber, String farmerSerialNumber, String landOwnedType, String name, String code, Double acre, String landHolderType) {
        super(rowNumber);
        this.serialNumber = serialNumber;
        this.farmerSerialNumber = farmerSerialNumber;
        this.landOwnedType = landOwnedType;
        this.name = name;
        this.code = code;
        this.acre = acre;
        this.landHolderType = landHolderType;
    }
}