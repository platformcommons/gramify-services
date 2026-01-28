package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.Benefit;

import org.springframework.data.domain.Page;

import java.util.*;

public interface BenefitService {

    Long save(Benefit benefit );

    Benefit update(Benefit benefit );

    Page<Benefit> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Benefit getById(Long id);


}
