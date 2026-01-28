package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.app.application.InstanceService;
import com.platformcommons.platform.service.app.domain.Instance;
import com.platformcommons.platform.service.app.domain.repo.InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    private InstanceRepository instanceRepository;

    @Override
    public Long createInstance(Instance instance) {
        return instanceRepository.save(instance).getId();
    }

    @Override
    public Page<Instance> getAllInstances(Integer page, Integer size) {
        return instanceRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public Instance getInstanceById(Long id) {
        return instanceRepository.findById(id).orElseThrow(()-> new NotFoundException(
                String.format("Request Instance with id %s not found",id)));
    }

    @Override
    public void deleteInstance(Long id) {
    }

    @Override
    public Instance update(Instance instance) {
        Instance fetchedInstance = instanceRepository.findById(instance.getId()).orElseThrow(()-> new NotFoundException(
                String.format("Request Instance with id %s not found",instance.getId())));
        fetchedInstance.update(instance);
        return instanceRepository.save(fetchedInstance);
    }
}
