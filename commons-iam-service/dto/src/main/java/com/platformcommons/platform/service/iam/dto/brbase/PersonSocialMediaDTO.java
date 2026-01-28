package com.platformcommons.platform.service.iam.dto.brbase;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import java.io.Serializable;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PersonSocialMediaDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

   // @NotNull(message = "PersonSocialMedia#Id is required")
    //@ApiModelProperty(required=true)
    private Integer id;

    //@NotNull(message="Person#SocialMediaAccount is required")
    @Valid
   // @ApiModelProperty(required=true)
    private SocialMediaAccountDTO socialMediaAccount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SocialMediaAccountDTO getSocialMediaAccount() {
        return socialMediaAccount;
    }

    public void setSocialMediaAccount(SocialMediaAccountDTO socialMediaAccount) {
        this.socialMediaAccount = socialMediaAccount;
    }
}
