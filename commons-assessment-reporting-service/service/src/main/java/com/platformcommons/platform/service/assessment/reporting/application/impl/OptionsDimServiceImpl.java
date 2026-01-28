package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.DefaultOptionsDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.OptionsDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.OptionsDimRepository;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.QuestionDimRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.OptionsDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionsDimServiceImpl implements OptionsDimService {

    private final OptionsDimRepository repository;
    private final OptionsDimAssembler optionsDimAssembler;
    private final QuestionDimRepository questionDimRepository;
    private final UtilityAssembler utilityAssembler;
    @Override
    public void createOptionDim(Set<OptionsDim> optionDims) {
        repository.saveAll(optionDims);
    }

    @Override
    public Set<OptionsDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentDTO dto) {
        final String baseLanguage = utilityAssembler.getBaseLanguage(dto);
        return getByQuestionIds(distinctQuestionIds,baseLanguage);
    }
    @Override
    public Set<OptionsDim> getByQuestionIds(Set<Long> distinctQuestionIds, String baseLanguage) {
        Set<OptionsDim> optionsDims = repository.findByQuestionIdIn(distinctQuestionIds, EventContext.getSystemEvent());
        while(!distinctQuestionIds.isEmpty()){
            distinctQuestionIds = questionDimRepository.findByParentQuestionIdIn(distinctQuestionIds,baseLanguage,EventContext.getSystemEvent()).stream().map(QuestionDim::getQuestionId).collect(Collectors.toSet());
            Set<OptionsDim> childOptionsDims = repository.findByQuestionIdIn(distinctQuestionIds, EventContext.getSystemEvent());
            if(childOptionsDims!=null && !childOptionsDims.isEmpty())
                optionsDims.addAll(childOptionsDims);
        }
        return optionsDims;
    }

    @Override
    public void updateOptionDim(QuestionDTO questionDTO) {
        if (questionDTO.getDefaultOptionsList() == null || questionDTO.getDefaultOptionsList().isEmpty()) {
            repository.deleteByQuestionIdForDefaultOptionId(questionDTO.getId(), EventContext.getSystemEvent());
        }
        if(questionDTO.getMtfoptionList() == null || questionDTO.getMtfoptionList().isEmpty()){
            repository.deleteByQuestionIdForMtfOptionId(questionDTO.getId(), EventContext.getSystemEvent());
        }
        if(
                (questionDTO.getDefaultOptionsList() == null || questionDTO.getDefaultOptionsList().isEmpty()) ||
                (questionDTO.getMtfoptionList() == null || questionDTO.getMtfoptionList().isEmpty())
        ){
            return;
        }

        Set<OptionsDim> currentOptionDims = optionsDimAssembler.toOptionDims(questionDTO);
        Set<OptionsDim> optionDims = repository.findByQuestionId(questionDTO.getId(), EventContext.getSystemEvent());

        Map<Long, Map<String,OptionsDim>> currentOptionsDimsMap = getDefaultOptionMap(currentOptionDims);
        Map<Long, OptionsDim> currentMtfOptionDimsMap = getMtfOptionMap(currentOptionDims);


        Map<Long, Map<String,OptionsDim>> dbOptionsDimsMap = getDefaultOptionMap(optionDims);
        Map<Long, OptionsDim> dbMtfOptionDimsMap = getMtfOptionMap(optionDims);


        List<OptionsDim> toUpdate = new ArrayList<>();
        List<OptionsDim> toSave = new ArrayList<>();
        List<OptionsDim> toDelete = new ArrayList<>();

        for (Map.Entry<Long, Map<String, OptionsDim>> entry : currentOptionsDimsMap.entrySet()) {
            Long defaultOptionId = entry.getKey();
            Map<String,OptionsDim> currentOptionsDimMap = entry.getValue();
            if(!dbMtfOptionDimsMap.containsKey(defaultOptionId)){
                toSave.addAll(currentOptionsDimMap.values());
                currentOptionsDimsMap.remove(defaultOptionId);
            }
            else{
                Map<String,OptionsDim> dbOptionsDimMap = dbOptionsDimsMap.get(defaultOptionId);
                for (Map.Entry<String, OptionsDim> currentOptionDimEntry : currentOptionsDimMap.entrySet()) {
                    String language = currentOptionDimEntry.getKey();
                    OptionsDim currentOptionDim = currentOptionDimEntry.getValue();

                    if(!dbOptionsDimMap.containsKey(language)){
                        toSave.add(currentOptionDim);
                        currentOptionsDimMap.remove(language);
                    }
                    else{
                        OptionsDim toUpdateOptionDim = dbOptionsDimMap.remove(language);
                        toUpdateOptionDim.update(currentOptionDim);
                        toUpdate.add(toUpdateOptionDim);
                    }
                }
                if(!dbOptionsDimMap.isEmpty()) toDelete.addAll(dbOptionsDimMap.values());
                dbOptionsDimsMap.remove(defaultOptionId);
                currentOptionsDimsMap.remove(defaultOptionId);
            }
        }
        for (Map.Entry<Long, OptionsDim> entry : currentMtfOptionDimsMap.entrySet()) {
            Long mtfOptionId = entry.getKey();
            OptionsDim currentMtfOptionDim = entry.getValue();
            if(!dbMtfOptionDimsMap.containsKey(mtfOptionId)){
                toSave.add(currentMtfOptionDim);
                currentMtfOptionDimsMap.remove(mtfOptionId);
            }
            else{
                OptionsDim toUpdateOptionDim = dbMtfOptionDimsMap.remove(mtfOptionId);
                toUpdateOptionDim.update(currentMtfOptionDim);
                toUpdate.add(toUpdateOptionDim);
            }
        }
        if(!dbMtfOptionDimsMap.isEmpty())
            toDelete.addAll(dbMtfOptionDimsMap.values());

        if (!toSave.isEmpty()) repository.saveAll(toSave);
        if (!toUpdate.isEmpty()) repository.saveAll(toUpdate);
        if (!toDelete.isEmpty())
            repository.deleteByDefaultOptionIdIn(toDelete.stream().map(OptionsDim::getDefaultOptionId).collect(Collectors.toSet()), EventContext.getSystemEvent());


    }

    private Map<Long, OptionsDim> getMtfOptionMap(Set<OptionsDim> optionsDims) {
        if(optionsDims==null || optionsDims.isEmpty()) return new HashMap<>();
        return optionsDims.stream()
                .filter(dim -> dim.getMtfOptionId()!=null)
                .collect(Collectors.toMap(OptionsDim::getMtfOptionId, Function.identity()));
    }

    private Map<Long, Map<String, OptionsDim>> getDefaultOptionMap(Set<OptionsDim> optionsDims) {
        Map<Long, Map<String, OptionsDim>> map = new HashMap<>();
        if(optionsDims==null || optionsDims.isEmpty()) return map;
        for (OptionsDim dim : optionsDims) {
            if(dim.getDefaultOptionId()!=null){
                Map<String, OptionsDim> langMap = map.getOrDefault(dim.getDefaultOptionId(),new HashMap<>());
                map.put(dim.getDefaultOptionId(),langMap);
                langMap.put(dim.getLanguage(),dim);
            }
        }
        return map;
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        Set<Long> distinctQuestionIds = syncContext.getQuestionDTOMap().keySet();
        repository.deleteByQuestionIdIn(distinctQuestionIds, EventContext.getSystemEvent());
        Collection<QuestionDTO> dtos = syncContext.getQuestionDTOMap().values();
        Set<OptionsDim> optionsDims = dtos.parallelStream()
                .map(optionsDimAssembler::toOptionDims)
                .flatMap(Collection::stream)
                .peek(OptionsDim::init)
                .collect(Collectors.toSet());
        repository.saveAll(optionsDims);
        for (QuestionDTO dto : dtos) {
            if (dto.getDefaultOptionsList() != null) {
                for (DefaultOptionsDTO defaultOptionsDTO : dto.getDefaultOptionsList()) {
                    if (defaultOptionsDTO.getChildQuestionList() != null && !defaultOptionsDTO.getChildQuestionList().isEmpty()) {
                        repository.updateParentQuestionIdAndDefaultOptionIdByQuestionIdIn(dto.getId(),defaultOptionsDTO.getId(),defaultOptionsDTO.getChildQuestionList(),EventContext.getSystemEvent());
                    }
                }
            }
        }
    }

    @Override
    public Set<OptionsDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentInstanceDim assessmentInstanceDim) {
        return repository.findByQuestionIdIn(distinctQuestionIds, EventContext.getSystemEvent());
    }

    @Override
    public List<Map<String, Object>> getOptionResponseByAssessmentInstanceId(Long assessmentInstanceID) {
        return repository.getOptionResponse(assessmentInstanceID, EventContext.getSystemEvent());
    }

    @Override
    public Set<OptionsDim> getByQuestionId(Long childQuestionId, String baseLanguage) {
        return repository.findByQuestionIdAndLanguageAndLinkedSystem(childQuestionId, baseLanguage, EventContext.getSystemEvent());
    }

    @Override
    public void createParentLink(QuestionDTO questionDTO) {
        if(questionDTO.getDefaultOptionsList()!=null && !questionDTO.getDefaultOptionsList().isEmpty()){
            for (DefaultOptionsDTO defaultOptionsDTO : questionDTO.getDefaultOptionsList()) {
                if(defaultOptionsDTO.getChildQuestionList()!=null && !defaultOptionsDTO.getChildQuestionList().isEmpty()){
                    repository.updateParentQuestionIdAndDefaultOptionId(defaultOptionsDTO.getChildQuestionList(), questionDTO.getId(), defaultOptionsDTO.getId(),EventContext.getSystemEvent());
                }
            }
        }
    }

    @Override
    public void unlink(Long id) {
        repository.unlink(id,EventContext.getSystemEvent());
    }

    @Override
    public Set<OptionsDim> getOptionsDimByQuestionIds(Set<Long> singleton) {
        return repository.findByParentQuestionIdIn(singleton, EventContext.getSystemEvent());
    }
}
