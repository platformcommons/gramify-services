package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.Address;

import org.springframework.data.domain.Page;

import java.util.*;

public interface AddressService {

    Long save(Address address );

    Address update(Address address );

    Page<Address> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Address getById(Long id);


}
