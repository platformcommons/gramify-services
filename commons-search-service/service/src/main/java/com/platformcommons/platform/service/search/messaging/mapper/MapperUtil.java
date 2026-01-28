package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.product.dto.ClassificationDTO;
import com.platformcommons.platform.service.product.dto.ProductSKUTagDTO;
import com.platformcommons.platform.service.product.dto.SolutionTagDTO;

import java.util.*;
import java.util.stream.Collectors;

public class MapperUtil {

    public  static Set<String> getClassificationCodes(Set<ClassificationDTO> classificationDTOS){
        return classificationDTOS!=null ? classificationDTOS.stream().map(ClassificationDTO::getCode)
                .collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

    public static Set<String> getClassificationNames(Set<ClassificationDTO> classificationDTOS) {
        if (classificationDTOS == null || classificationDTOS.isEmpty()) {
            return null;
        }
        Set<String> classificationNames = new LinkedHashSet<>();
        for (ClassificationDTO classificationDTO : classificationDTOS) {
            if (classificationDTO != null && classificationDTO.getName() != null) {
                classificationDTO.getName().stream()
                        .filter(Objects::nonNull)
                        .map(MLTextDTO::getText)
                        .filter(text -> text != null && !text.isEmpty())
                        .forEach(classificationNames::add);
            }
        }
        return classificationNames.isEmpty() ? null : classificationNames;
    }


    private static Set<TagDTO> getTagDTOFromSolutionTagDTOs(Set<SolutionTagDTO> solutionTagDTOS){
        Set<TagDTO> tagDTOs = new LinkedHashSet<>();
        if(solutionTagDTOS==null || solutionTagDTOS.isEmpty()){
            return  null;
        }
        solutionTagDTOS.stream().filter(solutionTagDTO -> solutionTagDTO!=null && solutionTagDTO.getTag()!=null)
                .forEach(solutionTagDTO -> tagDTOs.add(solutionTagDTO.getTag()));

        if(!tagDTOs.isEmpty()){
            return  tagDTOs;
        }
        else {
            return null;
        }
    }

    private static Set<TagDTO> getTagDTOFromProductSKUTagDTOs(Set<ProductSKUTagDTO> productSKUTagDTOS){
        Set<TagDTO> tagDTOs = new LinkedHashSet<>();
        if(productSKUTagDTOS==null || productSKUTagDTOS.isEmpty()){
            return  null;
        }
        productSKUTagDTOS.stream().filter(productSKUTagDTO -> productSKUTagDTO!=null && productSKUTagDTO.getTag()!=null)
                .forEach(productSKUTagDTO -> tagDTOs.add(productSKUTagDTO.getTag()));

        if(!tagDTOs.isEmpty()){
            return  tagDTOs;
        }
        else {
            return null;
        }
    }


    public static Set<String> getTagCodes(Set<SolutionTagDTO> solutionTagDTOS){
        Set<TagDTO> tagDTOs = getTagDTOFromSolutionTagDTOs(solutionTagDTOS);
        if(tagDTOs==null || tagDTOs.isEmpty()){
            return  null;
        }
        Set<String> tagCodes= tagDTOs.stream().filter(Objects::nonNull).map(TagDTO::getCode).collect(Collectors.toCollection(LinkedHashSet::new));
        if(!tagCodes.isEmpty()){
            return  tagCodes;
        }
        else {
            return  null;
        }
    }


    public static Set<String> getTagLabels(Set<SolutionTagDTO> solutionTagDTOS){
        Set<TagDTO> tagDTOs = getTagDTOFromSolutionTagDTOs(solutionTagDTOS);
        if(tagDTOs==null || tagDTOs.isEmpty()){
            return  null;
        }
        Set<String> taglLabels = new LinkedHashSet<>();
        tagDTOs.forEach(it->{
            if(it!=null){
                if(it.getName()!=null) taglLabels.add(it.getName());
                if(it.getNameML()!=null && !it.getNameML().isEmpty()){
                    it.getNameML().stream().filter(Objects::nonNull).filter(nameMl->nameMl.getText()!=null)
                            .forEach(nameMl->taglLabels.add(nameMl.getText()));
                }
            }
        });
        if(!taglLabels.isEmpty()){
            return taglLabels;
        }
        else {
            return  null;
        }
    }

    public static Set<String> getProductSKUTagCodes(Set<ProductSKUTagDTO> productSKUTagDTOS){
        Set<TagDTO> tagDTOs = getTagDTOFromProductSKUTagDTOs(productSKUTagDTOS);
        if(tagDTOs==null || tagDTOs.isEmpty()){
            return  null;
        }
        Set<String> tagCodes= tagDTOs.stream().filter(Objects::nonNull).map(TagDTO::getCode).collect(Collectors.toCollection(LinkedHashSet::new));
        if(!tagCodes.isEmpty()){
            return  tagCodes;
        }
        else {
            return  null;
        }
    }


    public static Set<String> getProductSKUTagLabels(Set<ProductSKUTagDTO> productSKUTagDTOS){
        Set<TagDTO> tagDTOs = getTagDTOFromProductSKUTagDTOs(productSKUTagDTOS);
        if(tagDTOs==null || tagDTOs.isEmpty()){
            return  null;
        }
        Set<String> taglLabels = new LinkedHashSet<>();
        tagDTOs.forEach(it->{
            if(it!=null){
                if(it.getName()!=null) taglLabels.add(it.getName());
                if(it.getNameML()!=null && !it.getNameML().isEmpty()){
                    it.getNameML().stream().filter(Objects::nonNull).filter(nameMl->nameMl.getText()!=null)
                            .forEach(nameMl->taglLabels.add(nameMl.getText()));
                }
            }
        });
        if(!taglLabels.isEmpty()){
            return taglLabels;
        }
        else {
            return  null;
        }
    }

    public static String getCodeFromRefDataDTO(RefDataDTO refDataDTO) {
        if(refDataDTO == null) {
            return null;
        }
        return refDataDTO.getCode();
    }

    public static String getRefDataLabel(RefDataDTO refDataDTO) {
        if(refDataDTO == null) {
            return null;
        }
        String label;
        label = getEngLabel(refDataDTO.getLabels());
        return label != null ? label : refDataDTO.getName();
    }

    public static String getEngLabel(Set<MLTextDTO> mlTextDTOs){
        MLTextDTO mlTextDTO= mlTextDTOs!=null ? mlTextDTOs.stream().filter(it-> it.getLanguageCode().equals("ENG"))
                .findFirst().orElse(null): null;
        return mlTextDTO!=null ? mlTextDTO.getText(): null;
    }

    public static Map<String, List<String>> getTagCodesByTypeForSolutionTag(Set<SolutionTagDTO> solutionTagDTOS) {
        Set<TagDTO> tagDTOs = getTagDTOFromSolutionTagDTOs(solutionTagDTOS);
        return getTagCodesByType(tagDTOs);
    }

    public static Map<String, List<String>> getTagCodesByTypeForProductSkuTag(Set<ProductSKUTagDTO> productSKUTagDTOS) {
        Set<TagDTO> tagDTOs = getTagDTOFromProductSKUTagDTOs(productSKUTagDTOS);
        return getTagCodesByType(tagDTOs);
    }

    public static Map<String, List<String>> getTagLabelsByLangForSolutionTag(Set<SolutionTagDTO> solutionTagDTOS) {
        Set<TagDTO> tagDTOs = getTagDTOFromSolutionTagDTOs(solutionTagDTOS);
        return getTagLabelsByLang(tagDTOs);
    }

    public static Map<String, List<String>> getTagLabelsByLangForProductSkuTag(Set<ProductSKUTagDTO> productSKUTagDTOS) {
        Set<TagDTO> tagDTOs = getTagDTOFromProductSKUTagDTOs(productSKUTagDTOS);
        return getTagLabelsByLang(tagDTOs);
    }

    public static Map<String, List<String>> getTagLabelsByTypeForSolutionTag(Set<SolutionTagDTO> solutionTagDTOS) {
        Set<TagDTO> tagDTOs = getTagDTOFromSolutionTagDTOs(solutionTagDTOS);
        return getTagLabelsByType(tagDTOs);
    }

    public static Map<String, List<String>> getTagLabelsByTypeForProductSkuTag(Set<ProductSKUTagDTO> productSKUTagDTOS) {
        Set<TagDTO> tagDTOs = getTagDTOFromProductSKUTagDTOs(productSKUTagDTOS);
        return getTagLabelsByType(tagDTOs);
    }


    // tagCodesByType: { "CATEGORY": ["CAT01","CAT02"], "FEATURE":["FEA01"] }
    public static Map<String, List<String>> getTagCodesByType(Set<TagDTO> tagDTOs) {
        if (tagDTOs == null || tagDTOs.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Set<String>> tempMap = new HashMap<>();

        for (TagDTO tag : tagDTOs) {
            String rawType = (tag.getType() != null) ? tag.getType() : "TAG_TYPE.UNKNOWN";
            String typeKey = solrSafeKey(rawType);
            tempMap.computeIfAbsent(typeKey, k -> new LinkedHashSet<>())
                    .add(tag.getCode());
        }

        return tempMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new ArrayList<>(e.getValue())
                ));
    }


    // tagLabelsByLang: { "ENG": ["Agriculture","Farming","Organic"], "HIN":["कृषि","खेती","जैविक"] }
    public static Map<String, List<String>> getTagLabelsByLang(Set<TagDTO> tagDTOs) {
        if (tagDTOs == null || tagDTOs.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Set<String>> tempMap = new HashMap<>();

        for (TagDTO tag : tagDTOs) {
            if (tag.getNameML() != null) {
                for (MLTextDTO mlText : tag.getNameML()) {
                    String rawLang = (mlText.getLanguageCode() != null) ? mlText.getLanguageCode() : "UNKNOWN";
                    String lang = solrSafeKey(rawLang);
                    tempMap.computeIfAbsent(lang, k -> new LinkedHashSet<>())
                            .add(mlText.getText());
                }
            }
        }

        return tempMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new ArrayList<>(e.getValue())
                ));
    }

    // tagLabelsByType: { "CATEGORY": ["Agriculture","कृषि"], "FEATURE":["Organic","जैविक"] }
    public static Map<String, List<String>> getTagLabelsByType(Set<TagDTO> tagDTOs) {
        if (tagDTOs == null || tagDTOs.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Set<String>> tempMap = new HashMap<>();

        for (TagDTO tag : tagDTOs) {
            if (tag == null) continue;

            String rawType = (tag.getType() != null) ? tag.getType() : "TAG_TYPE.UNKNOWN";
            String typeKey = solrSafeKey(rawType);

            Set<String> labels = tempMap.computeIfAbsent(typeKey, k -> new LinkedHashSet<>());

            // Add default name if available
            if (tag.getName() != null) {
                labels.add(tag.getName());
            }

            // Add multilingual names if available
            if (tag.getNameML() != null && !tag.getNameML().isEmpty()) {
                tag.getNameML().stream()
                        .filter(Objects::nonNull)
                        .map(MLTextDTO::getText)
                        .filter(Objects::nonNull)
                        .forEach(labels::add);
            }
        }

        return tempMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new ArrayList<>(e.getValue())
                ));
    }



    // Make keys safe for Solr dynamic fields
    private static String solrSafeKey(String key) {
        // Ensure only letters, numbers, underscore — no spaces or special chars
        return key.trim().replaceAll("[^A-Za-z0-9_]", "_").toUpperCase();
    }

}
