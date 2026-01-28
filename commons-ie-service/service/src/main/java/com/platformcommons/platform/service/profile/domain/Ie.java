package com.platformcommons.platform.service.profile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.profile.facade.assembler.PersonBankDetailDTOAssembler;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "ie")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Ie extends BaseTransactionalEntity implements DomainEntity<Ie> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "person must not be null")
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "person",updatable = false,nullable = false)
    private Person person;

    @Column(name = "care_taker_tenant_id")
    private Long careTakerTenantId;

    @Column(name = "on_boarded_tenant_id")
    private Long onBoardedTenantId;

    @Column(name = "ie_status")
    private String ieStatus;

    @Column(name = "creation_mode")
    private String creationMode;

    @Column(name = "on_boarded_user_id")
    private Long onBoardedUserId;

    @Column(name = "on_boarded_ie_id")
    private Long onBoardedIeId;

    @Column(name = "ie_type")
    private String ieType;

    @Column(name = "ie_sub_type")
    private String ieSubType;

    @Column(name = "on_board_date_time")
    private Date onBoardDateTime;

    @Column(name = "updated_venture")
    private Long updatedVenture;

    @Column(name = "notes")
    private String notes;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "interested_in_trade")
    private Boolean interestedInTrade;

    @Column(name = "alias_id")
    private String aliasId;

    @Column(name = "icon_pic")
    private String iconPic;

    @Column(name = "created_venture")
    private Long createdVenture;

    @Column(name = "on_trader_center_id")
    private Long onTraderCenterId;

    @Column(name = "tagged_to_worknode_id")
    private Long taggedToWorknodeId;

    @Column(name = "tagged_to_workforce_id")
    private Long taggedToWorkforceId;


    @Column(name = "qr_code_attachment_path")
    private String qrCodeAttachmentPath;

    @Column(name = "id_card_attachment_path")
    private String idCardAttachmentPath;

    @Column(name = "tagged_to_serviceArea_type")
    private String taggedToServiceAreaType;

    @Column(name ="ie_unique_identifier",unique = true)
    private String ieUniqueIdentifier;

    @Column(name = "tagged_to_serviceArea_code")
    private String taggedToServiceAreaCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "ie",nullable = false,updatable = false)
    @BatchSize(size = 20)
    private Set<DeliveryMode> deliveryModeList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "ie",nullable = false,updatable = false)
    @BatchSize(size = 20)
    private Set<IeSubType> ieSubTypeList;

    @Column(name = "linked_ie_id")
    private Long linkedIeId;


    @Transient
    private boolean isNew;

    @Builder
    public Ie(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
              Long id, Person person, Long careTakerTenantId, Long onBoardedTenantId, String ieStatus, String creationMode,
              Long onBoardedUserId, Long onBoardedIeId, String ieType, String ieSubType, Date onBoardDateTime, Long updatedVenture, String notes,
              String loginName, Boolean interestedInTrade, String aliasId, String iconPic, Long createdVenture,
              Long onTraderCenterId, Long taggedToWorknodeId,Long taggedToWorkforceId, String qrCodeAttachmentPath, String taggedToServiceAreaType,
              String ieUniqueIdentifier, String taggedToServiceAreaCode, Set<DeliveryMode> deliveryModeList, Set<IeSubType> ieSubTypeList, Long linkedIeId) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.person = person;
        this.careTakerTenantId = careTakerTenantId;
        this.onBoardedTenantId = onBoardedTenantId;
        this.ieStatus = ieStatus;
        this.creationMode = creationMode;
        this.onBoardedUserId = onBoardedUserId;
        this.onBoardedIeId = onBoardedIeId;
        this.ieType = ieType;
        this.ieSubType=ieSubType;
        this.onBoardDateTime = onBoardDateTime;
        this.updatedVenture = updatedVenture;
        this.notes = notes;
        this.loginName = loginName;
        this.interestedInTrade = interestedInTrade;
        this.aliasId = aliasId;
        this.iconPic = iconPic;
        this.createdVenture = createdVenture;
        this.onTraderCenterId = onTraderCenterId;
        this.taggedToWorknodeId = taggedToWorknodeId;
        this.taggedToWorkforceId = taggedToWorkforceId;
        this.qrCodeAttachmentPath = qrCodeAttachmentPath;
        this.taggedToServiceAreaType=taggedToServiceAreaType;
        this.ieUniqueIdentifier=ieUniqueIdentifier;
        this.taggedToServiceAreaCode=taggedToServiceAreaCode;
        this.deliveryModeList = deliveryModeList;
        this.ieSubTypeList = ieSubTypeList;
        this.linkedIeId = linkedIeId;

    }

    public Date getOn_board_date_time() {
        return new Date(onBoardDateTime != null ? onBoardDateTime.getTime() : new Date().getTime());
    }

    public void setOn_board_date_time(Date onBoardDateTime) {
        this.onBoardDateTime = new Date(onBoardDateTime != null ? onBoardDateTime.getTime() : new Date().getTime());
    }

    public void init() {
    }

    public void update(Ie toBeUpdated) {
        if (toBeUpdated.getCareTakerTenantId() != null) {
            this.setCareTakerTenantId(toBeUpdated.getCareTakerTenantId());
        }
        if (toBeUpdated.getOnBoardedTenantId() != null) {
            this.setOnBoardedTenantId(toBeUpdated.getOnBoardedTenantId());
        }
        if (toBeUpdated.getIeStatus() != null) {
            this.setIeStatus(toBeUpdated.getIeStatus());
        }
        if (toBeUpdated.getCreationMode() != null) {
            this.setCreationMode(toBeUpdated.getCreationMode());
        }
        if (toBeUpdated.getOnBoardedUserId() != null) {
            this.setOnBoardedUserId(toBeUpdated.getOnBoardedUserId());
        }
        if (toBeUpdated.getOnBoardedIeId() != null) {
            this.setOnBoardedIeId(toBeUpdated.getOnBoardedIeId());
        }
        if (toBeUpdated.getIeType() != null) {
            this.setIeType(toBeUpdated.getIeType());
        }
        if (toBeUpdated.getIeSubType() != null) {
            this.setIeSubType(toBeUpdated.getIeSubType());
        }
        if (toBeUpdated.getOnBoardDateTime() != null) {
            this.setOnBoardDateTime(toBeUpdated.getOnBoardDateTime());
        }
        if (toBeUpdated.getUpdatedVenture() != null) {
            this.setUpdatedVenture(toBeUpdated.getUpdatedVenture());
        }
        if (toBeUpdated.getNotes() != null) {
            this.setNotes(toBeUpdated.getNotes());
        }
        if (toBeUpdated.getLoginName() != null) {
            this.setLoginName(toBeUpdated.getLoginName());
        }
        if (toBeUpdated.getInterestedInTrade() != null) {
            this.setInterestedInTrade(toBeUpdated.getInterestedInTrade());
        }
        if (toBeUpdated.getAliasId() != null) {
            this.setAliasId(toBeUpdated.getAliasId());
        }
        if (toBeUpdated.getIconPic() != null) {
            this.setIconPic(toBeUpdated.getIconPic());
        }
        if (toBeUpdated.getCareTakerTenantId() != null) {
            this.setCareTakerTenantId(toBeUpdated.getCareTakerTenantId());
        }
        if (toBeUpdated.getCreatedVenture() != null) {
            this.setCreatedVenture(toBeUpdated.getCreatedVenture());
        }
        if (toBeUpdated.getOnTraderCenterId() != null) {
            this.setOnTraderCenterId(toBeUpdated.getOnTraderCenterId());
        }
        if (toBeUpdated.getTaggedToWorknodeId()!= null) {
            this.setTaggedToWorknodeId(toBeUpdated.getTaggedToWorknodeId());
        }
        if (toBeUpdated.getTaggedToWorkforceId()!= null) {
            this.setTaggedToWorkforceId(toBeUpdated.getTaggedToWorkforceId());
        }
        if (toBeUpdated.getQrCodeAttachmentPath()!= null) {
            this.setQrCodeAttachmentPath(toBeUpdated.getQrCodeAttachmentPath());
        }
        if(toBeUpdated.getTaggedToServiceAreaType()!=null){
            this.setTaggedToServiceAreaType(toBeUpdated.getTaggedToServiceAreaType());
        }
        if(toBeUpdated.getIeUniqueIdentifier()!=null){
            this.setIeUniqueIdentifier(toBeUpdated.getIeUniqueIdentifier());
        }
        if(toBeUpdated.getTaggedToServiceAreaCode()!=null){
            this.setTaggedToServiceAreaCode(toBeUpdated.getTaggedToServiceAreaCode());
        }
        if(toBeUpdated.getLinkedIeId()!=null){
            this.setLinkedIeId(toBeUpdated.getLinkedIeId());
        }
    }

    public void patch(Ie toBeUpdated) {
        if (toBeUpdated.getCareTakerTenantId() != null) {
            this.setCareTakerTenantId(toBeUpdated.getCareTakerTenantId());
        }
        if (toBeUpdated.getOnBoardedTenantId() != null) {
            this.setOnBoardedTenantId(toBeUpdated.getOnBoardedTenantId());
        }
        if (toBeUpdated.getIeStatus() != null) {
            this.setIeStatus(toBeUpdated.getIeStatus());
        }
        if (toBeUpdated.getCreationMode() != null) {
            this.setCreationMode(toBeUpdated.getCreationMode());
        }
        if (toBeUpdated.getOnBoardedUserId() != null) {
            this.setOnBoardedUserId(toBeUpdated.getOnBoardedUserId());
        }
        if (toBeUpdated.getOnBoardedIeId() != null) {
            this.setOnBoardedIeId(toBeUpdated.getOnBoardedIeId());
        }
        if (toBeUpdated.getIeType() != null) {
            this.setIeType(toBeUpdated.getIeType());
        }
        if (toBeUpdated.getIeSubType() != null) {
            this.setIeSubType(toBeUpdated.getIeSubType());
        }
        if (toBeUpdated.getOnBoardDateTime() != null) {
            this.setOnBoardDateTime(toBeUpdated.getOnBoardDateTime());
        }
        if (toBeUpdated.getUpdatedVenture() != null) {
            this.setUpdatedVenture(toBeUpdated.getUpdatedVenture());
        }
        if (toBeUpdated.getNotes() != null) {
            this.setNotes(toBeUpdated.getNotes());
        }
        if (toBeUpdated.getLoginName() != null) {
            this.setLoginName(toBeUpdated.getLoginName());
        }
        if (toBeUpdated.getInterestedInTrade() != null) {
            this.setInterestedInTrade(toBeUpdated.getInterestedInTrade());
        }
        if (toBeUpdated.getAliasId() != null) {
            this.setAliasId(toBeUpdated.getAliasId());
        }
        if (toBeUpdated.getIconPic() != null) {
            this.setIconPic(toBeUpdated.getIconPic());
        }
        if (toBeUpdated.getCareTakerTenantId() != null) {
            this.setCareTakerTenantId(toBeUpdated.getCareTakerTenantId());
        }
        if (toBeUpdated.getCreatedVenture() != null) {
            this.setCreatedVenture(toBeUpdated.getCreatedVenture());
        }
        if (toBeUpdated.getOnTraderCenterId() != null) {
            this.setOnTraderCenterId(toBeUpdated.getOnTraderCenterId());
        }
        if (toBeUpdated.getTaggedToWorknodeId()!= null) {
            this.setTaggedToWorknodeId(toBeUpdated.getTaggedToWorknodeId());
        }
        if (toBeUpdated.getTaggedToWorkforceId()!= null) {
            this.setTaggedToWorkforceId(toBeUpdated.getTaggedToWorkforceId());
        }
        if (toBeUpdated.getQrCodeAttachmentPath()!= null) {
            this.setQrCodeAttachmentPath(toBeUpdated.getQrCodeAttachmentPath());
        }
        if(toBeUpdated.getTaggedToServiceAreaType()!=null){
            this.setTaggedToServiceAreaType(toBeUpdated.getTaggedToServiceAreaType());
        }
        if(toBeUpdated.getIeUniqueIdentifier()!=null){
            this.setIeUniqueIdentifier(toBeUpdated.getIeUniqueIdentifier());
        }
        if(toBeUpdated.getTaggedToServiceAreaCode()!=null){
            this.setTaggedToServiceAreaCode(toBeUpdated.getTaggedToServiceAreaCode());
        }
        if (toBeUpdated.getDeliveryModeList() != null ) {
            this.getDeliveryModeList().clear();
            this.getDeliveryModeList().addAll(toBeUpdated.getDeliveryModeList());
        }
        if (toBeUpdated.getIeSubTypeList() != null ) {
            this.getIeSubTypeList().clear();
            this.getIeSubTypeList().addAll(toBeUpdated.getIeSubTypeList());
        }
        if (toBeUpdated.getPerson() != null) {
            this.getPerson().patch(toBeUpdated.getPerson());
        }
        if(toBeUpdated.getLinkedIeId()!=null){
            this.setLinkedIeId(toBeUpdated.getLinkedIeId());
        }
    }
}