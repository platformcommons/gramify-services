package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonBankDetail;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonBankDetailService {

    Long save(PersonBankDetail personBankDetail );

    PersonBankDetail update(PersonBankDetail personBankDetail );

    Page<PersonBankDetail> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonBankDetail getById(Long id);


}
