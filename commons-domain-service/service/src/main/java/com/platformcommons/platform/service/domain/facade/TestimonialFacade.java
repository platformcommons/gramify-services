package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.Testimonial;
import com.platformcommons.platform.service.domain.dto.TestimonialDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface TestimonialFacade {

    Long save(TestimonialDTO testimonialDTO );

    TestimonialDTO update(TestimonialDTO testimonialDTO );

    PageDTO<TestimonialDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    TestimonialDTO getById(Long id);


}
