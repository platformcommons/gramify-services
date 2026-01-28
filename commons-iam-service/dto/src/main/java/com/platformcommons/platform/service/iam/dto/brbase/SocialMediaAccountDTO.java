package com.platformcommons.platform.service.iam.dto.brbase;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SocialMediaAccountDTO extends BaseDTO implements Serializable {

    private Integer id;

    @Valid
    @ApiModelProperty(required = true, value = "classCode=GREF.SOCIAL_MEDIA_TYPE")
    @NotNull(message = "SocialMediaAccountDTO# Social media type cannot be null")
    private GlobalRefDataDTO SocialMediaType;

    @NotNull(message = "SocialMediaAccountDTO# SocialMediaAccount value cannot be null")
    @ApiModelProperty(required = true, value = "Value of SocialMediaAccount is link of your social Media Account")
    private String socialMediaValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GlobalRefDataDTO getSocialMediaType() {
        return SocialMediaType;
    }

    public void setSocialMediaType(GlobalRefDataDTO socialMediaType) {
        SocialMediaType = socialMediaType;
    }

    public String getSocialMediaValue() {
        return socialMediaValue;
    }

    public void setSocialMediaValue(String socialMediaValue) {
        this.socialMediaValue = socialMediaValue;
    }
}
