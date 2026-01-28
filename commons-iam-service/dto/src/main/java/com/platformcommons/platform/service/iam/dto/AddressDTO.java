package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class AddressDTO extends BaseTransactionalDTO {

        private Long id;

        private String addressLine1;

        private String addressLine2;

        private String cityCode;

        private String stateCode;

        private String countryCode;

        private String pinCode;

        private String address;

        private String type;

        private String placeId;

        private Double latitude;

        private Double longitude;

        private String locationName;

}
