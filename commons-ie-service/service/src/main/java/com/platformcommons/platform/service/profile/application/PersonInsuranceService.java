package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonInsurance;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonInsuranceService {

    Long save(PersonInsurance personInsurance );

    PersonInsurance update(PersonInsurance personInsurance );

    Page<PersonInsurance> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonInsurance getById(Long id);


}
