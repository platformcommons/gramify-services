package com.platformcommons.platform.service.person.dto;

import com.platformcommons.platform.service.profile.dto.DeliveryModeDTO;
import lombok.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class FarmerCropPlanDTO {
    private Long portfolioId;
    private Long portfolioLineItemId;
    private Long portfolioProductLineItemId;
    private Long genericProductVarietyId;
    private String genericProductVarietyCode;
    private String quantityUoMCode;
    private Double quantity;
    private Date harvestStartDate;
    private Date hardvestEndDate;
    private String practicesFollowed;
    private Long ieId;
    private String ieName;
    private String mobile;
    private String email;
    private String iconPic;
    private Long onTraderCenterId;
    private Long taggedToWorknodeId;
    private Long taggedToWorkforceId;
    @Valid
    private Set<DeliveryModeDTO> deliveryModeList;

    @Builder
    public FarmerCropPlanDTO(Long portfolioId, Long portfolioLineItemId, Long portfolioProductLineItemId,
                             Long genericProductVarietyId, String genericProductVarietyCode, String quantityUoMCode,
                             Double quantity, Date harvestStartDate, Date hardvestEndDate, String practicesFollowed,
                             Long ieId, String ieName, String mobile, String email, String iconPic, Long onTraderCenterId,
                             Long taggedToWorknodeId, Long taggedToWorkforceId, Set<DeliveryModeDTO> deliveryModeList) {
        this.portfolioId = portfolioId;
        this.portfolioLineItemId = portfolioLineItemId;
        this.portfolioProductLineItemId = portfolioProductLineItemId;
        this.genericProductVarietyId = genericProductVarietyId;
        this.genericProductVarietyCode = genericProductVarietyCode;
        this.quantityUoMCode = quantityUoMCode;
        this.quantity = quantity;
        this.harvestStartDate = harvestStartDate;
        this.hardvestEndDate = hardvestEndDate;
        this.practicesFollowed = practicesFollowed;
        this.ieId = ieId;
        this.ieName = ieName;
        this.mobile = mobile;
        this.email = email;
        this.iconPic = iconPic;
        this.onTraderCenterId = onTraderCenterId;
        this.taggedToWorknodeId = taggedToWorknodeId;
        this.taggedToWorkforceId = taggedToWorkforceId;
        this.deliveryModeList = deliveryModeList;
    }
}
