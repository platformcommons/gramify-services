package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Testimonial;
import com.platformcommons.platform.service.domain.dto.TestimonialDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface TestimonialDTOAssembler {

    TestimonialDTO toDTO(Testimonial entity);

    Testimonial fromDTO(TestimonialDTO dto);
}