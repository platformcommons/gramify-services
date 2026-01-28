package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSyncCustomDTO {

    @NotNull(message = "userId must not be null")
    private Long userId;

    private String firstName;

    private String lastName;

    private String preferredEmail;

    private String contactNumber;

    private String whatsappNumber;

    private String genderCode;

    private String occupation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date dateOfBirth;

    private String pathwayCode;

    private Set<AddressSyncCustomDTO> addressList;

    private SocialMediaSyncCodeDTO socialMedia;

    private Set<ProfessionalHistorySyncCustomDTO> professionalHistoryList;

    private Set<EducationSyncCustomDTO> educationList;

}
