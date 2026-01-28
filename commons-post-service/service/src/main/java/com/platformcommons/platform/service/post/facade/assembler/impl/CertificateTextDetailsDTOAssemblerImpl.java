package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.post.domain.CertificateTextDetails;
import com.platformcommons.platform.service.post.domain.TenantCertificateTemplate;
import com.platformcommons.platform.service.post.dto.CertificateTextDetailsDTO;
import com.platformcommons.platform.service.post.facade.assembler.CertificateTextDetailsDTOAssembler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CertificateTextDetailsDTOAssemblerImpl implements CertificateTextDetailsDTOAssembler {

    @Override
    public CertificateTextDetailsDTO toDTO(CertificateTextDetails entity) {
        if ( entity == null ) {
            return null;
        }

        CertificateTextDetailsDTO.CertificateTextDetailsDTOBuilder certificateTextDetailsDTO = CertificateTextDetailsDTO.builder();

        certificateTextDetailsDTO.uuid( entity.getUuid() );
        certificateTextDetailsDTO.id( entity.getId() );
        certificateTextDetailsDTO.tenantCertificateTemplateId( entity.getTenantCertificateTemplate().getId());
        certificateTextDetailsDTO.textCode( entity.getTextCode() );
        certificateTextDetailsDTO.fontSize( entity.getFontSize() );
        certificateTextDetailsDTO.fontStyle( entity.getFontStyle() );
        certificateTextDetailsDTO.fontName( entity.getFontName() );
        certificateTextDetailsDTO.colorCode( entity.getColorCode() );
        certificateTextDetailsDTO.justification( entity.getJustification() );
        certificateTextDetailsDTO.pointX( entity.getPointX() );
        certificateTextDetailsDTO.pointY( entity.getPointY() );

        return certificateTextDetailsDTO.build();
    }

    @Override
    public Set<CertificateTextDetailsDTO> toDTOs(Set<CertificateTextDetails> entities) {
        if ( entities == null ) {
            return null;
        }

        Set<CertificateTextDetailsDTO> set = new HashSet<CertificateTextDetailsDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );
        for ( CertificateTextDetails certificateTextDetails : entities ) {
            set.add( toDTO( certificateTextDetails ) );
        }

        return set;
    }

    @Override
    public CertificateTextDetails fromDTO(CertificateTextDetailsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CertificateTextDetails.CertificateTextDetailsBuilder certificateTextDetails = CertificateTextDetails.builder();

        certificateTextDetails.uuid( dto.getUuid() );
        certificateTextDetails.id( dto.getId() );
        certificateTextDetails.tenantCertificateTemplate(TenantCertificateTemplate.builder().id(dto.getTenantCertificateTemplateId()).build());
        certificateTextDetails.textCode( dto.getTextCode() );
        certificateTextDetails.fontSize( dto.getFontSize() );
        certificateTextDetails.fontStyle( dto.getFontStyle() );
        certificateTextDetails.fontName( dto.getFontName() );
        certificateTextDetails.colorCode( dto.getColorCode() );
        certificateTextDetails.justification( dto.getJustification() );
        certificateTextDetails.pointX( dto.getPointX() );
        certificateTextDetails.pointY( dto.getPointY() );

        return certificateTextDetails.build();
    }

    @Override
    public Set<CertificateTextDetails> fromDTOs(Set<CertificateTextDetailsDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<CertificateTextDetails> set = new HashSet<CertificateTextDetails>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( CertificateTextDetailsDTO certificateTextDetailsDTO : dtos ) {
            set.add( fromDTO( certificateTextDetailsDTO ) );
        }

        return set;
    }
}
