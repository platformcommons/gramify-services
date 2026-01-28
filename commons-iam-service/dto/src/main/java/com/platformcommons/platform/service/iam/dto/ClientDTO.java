package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ClientDTO extends AuthBaseDTO {

    private Integer id;
    private String clientName;
    private String clientSecret;
    private String clientId;
    private Set<String> redirectURIs;
    private String realmRepresentation;
    @JsonIgnore
    private Set<RoleDTO> roleDTOS;

    @Builder
    public ClientDTO(Long createdTimestamp, Long lastModifiedTimestamp, Integer id, String clientName,
                     String clientSecret, String clientId, Set<String> redirectURIs, String realmRepresentation, Set<RoleDTO> roleDTOS) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.clientName = clientName;
        this.clientSecret = clientSecret;
        this.clientId = clientId;
        this.redirectURIs = redirectURIs;
        this.realmRepresentation = realmRepresentation;
        this.roleDTOS = roleDTOS;
    }

    public void addClientRole(RoleDTO roleDTO) {
        if (this.roleDTOS == null) {
            this.roleDTOS = new HashSet<>();
        }
        this.roleDTOS.add(roleDTO);
    }
}
