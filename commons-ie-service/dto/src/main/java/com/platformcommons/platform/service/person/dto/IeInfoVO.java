package com.platformcommons.platform.service.person.dto;

import com.platformcommons.platform.service.profile.dto.DeliveryModeDTO;
import lombok.*;

import javax.validation.Valid;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class IeInfoVO {
    private Long id;
    private String name;
    private String mobile;
    private String email;
    private String iconPic;
    private Long onTraderCenterId;
    private Long taggedToWorknodeId;
    private Long taggedToWorkforceId;
    private String ieType;
    @Valid
    private Set<IeDeliveryModeDTO> deliveryModeList;

    @Builder
    public IeInfoVO(Long id, String name, String mobile, String email, String iconPic, Long onTraderCenterId,
                    Long taggedToWorknodeId, Long taggedToWorkforceId, String ieType, Set<IeDeliveryModeDTO> deliveryModeList) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.iconPic = iconPic;
        this.onTraderCenterId = onTraderCenterId;
        this.taggedToWorknodeId = taggedToWorknodeId;
        this.taggedToWorkforceId = taggedToWorkforceId;
        this.ieType = ieType;
        this.deliveryModeList = deliveryModeList;
    }
    public IeInfoVO(Long id, String name, String mobile, String email, String iconPic, Long onTraderCenterId,
                    Long taggedToWorknodeId, Long taggedToWorkforceId, String ieType) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.iconPic = iconPic;
        this.onTraderCenterId = onTraderCenterId;
        this.taggedToWorknodeId = taggedToWorknodeId;
        this.taggedToWorkforceId = taggedToWorkforceId;
        this.ieType = ieType;
    }
}
