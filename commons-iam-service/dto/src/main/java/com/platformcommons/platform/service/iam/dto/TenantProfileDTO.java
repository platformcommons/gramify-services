package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import lombok.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TenantProfileDTO extends BaseTransactionalDTO {

    private Long id;

    private RefDataDTO type;

    private Set<RefDataDTO> industries;

    private RefDataDTO size;

    private String headline;

    private Integer yearOfEstablishment;

    private String coverImage;

    private String panNumber;

    private String registrationNumber;

    private String legalName;

    @Valid
    private List<TenantAddressDTO> tenantAddressDTOS;

    @Valid
    private List<TenantSocialMediaDTO> tenantSocialMediaDTOS;

    @Valid
    private List<AttachmentDTO> attachments;

    @Valid
    private Set<ExtraAttributesDTO> extraAttributes;


    @Builder
    public TenantProfileDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive, String inactiveReason,
                            Long id, RefDataDTO type, Set<RefDataDTO> industries, RefDataDTO size, String headline, Integer yearOfEstablishment,
                            String coverImage, String panNumber, String registrationNumber, String legalName,
                            List<TenantAddressDTO> tenantAddressDTOS, List<TenantSocialMediaDTO> tenantSocialMediaDTOS, List<AttachmentDTO> attachments,
                            Set<ExtraAttributesDTO> extraAttributes) {
        super(uuid, appCreatedAt, appLastModifiedAt, isActive, inactiveReason);
        this.id = id;
        this.type = type;
        this.industries = industries;
        this.size = size;
        this.headline = headline;
        this.yearOfEstablishment = yearOfEstablishment;
        this.coverImage = coverImage;
        this.panNumber = panNumber;
        this.registrationNumber = registrationNumber;
        this.legalName = legalName;
        this.tenantAddressDTOS = tenantAddressDTOS;
        this.tenantSocialMediaDTOS = tenantSocialMediaDTOS;
        this.attachments = attachments;
        this.extraAttributes = extraAttributes;
    }
}
