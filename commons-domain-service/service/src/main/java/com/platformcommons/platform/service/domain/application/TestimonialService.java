package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.Testimonial;

import org.springframework.data.domain.Page;

import java.util.*;

public interface TestimonialService {

    Long save(Testimonial testimonial );

    Testimonial update(Testimonial testimonial );

    Page<Testimonial> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Testimonial getById(Long id);


}
