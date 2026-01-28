package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.service.domain.domain.Testimonial;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.TestimonialService;
import com.platformcommons.platform.service.domain.domain.repo.TestimonialRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class TestimonialServiceImpl implements TestimonialService {


    @Autowired
    private TestimonialRepository repository;




    @Override
    public Long save(Testimonial testimonial ){
        return repository.save(testimonial).getId();
    }



    @Override
    public Testimonial update(Testimonial testimonial) {
       Testimonial fetchedTestimonial = repository.findById(testimonial.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Testimonial with  %d  not found",testimonial.getId())));

       fetchedTestimonial.update(testimonial);
       return repository.save(fetchedTestimonial);
    }


    @Override
    public Page<Testimonial> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Testimonial fetchedTestimonial = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Testimonial with  %d  not found",id)));
        fetchedTestimonial.deactivate(reason);
        repository.save(fetchedTestimonial);
    }


    @Override
    public Testimonial getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Testimonial with  %d  not found",id)));
    }


}
