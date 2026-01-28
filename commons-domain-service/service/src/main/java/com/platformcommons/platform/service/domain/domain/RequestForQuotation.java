package com.platformcommons.platform.service.domain.domain;

import lombok.*;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "request_for_quotation")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RequestForQuotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long  id;

    @Column(name = "first_name")
    private String firstName ;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "primary_contact_number",nullable = false)
    private String primaryContactNumber;

    @Column(name = "secondary_contact_number")
    private String secondaryContactNumber;

    @Column(name = "primary_email")
    private String primaryEmail;

    @Column(name = "secondary_email")
    private String secondaryEmail;

    @Column(name = "organization")
    private String organization;

    @Column(name = "designation")
    private String designation ;

    @Column(name = "for_entity_id")
    private String forEntityId ;

    @Column(name = "for_entity_type")
    private String forEntityType;

    @Column(name = "created_date" )
    private Date createdDate;
    @Column(name = "type")
    private String type;

    @Column(name = "requested_from_app")
    private String requestedFromApp;

    @Column(name = "status")
    private String status;

    @Builder
    public RequestForQuotation(Long id, String firstName, String middleName, String lastName,
                               String fullName, String primaryContactNumber, String secondaryContactNumber,
                               String primaryEmail, String secondaryEmail, String organization, String designation,
                               String forEntityId, String forEntityType, String type, Date createdDate,
                               String requestedFromApp, String status) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.primaryContactNumber = primaryContactNumber;
        this.secondaryContactNumber = secondaryContactNumber;
        this.primaryEmail = primaryEmail;
        this.secondaryEmail = secondaryEmail;
        this.organization = organization;
        this.designation = designation;
        this.forEntityId = forEntityId;
        this.forEntityType = forEntityType;
        this.type = type;
        this.createdDate = createdDate;
        this.requestedFromApp = requestedFromApp;
        this.status = status;
    }
}
