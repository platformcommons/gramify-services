package com.platformcommons.platform.service.domain.facade.mapper;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.domain.application.TagService;
import com.platformcommons.platform.service.domain.application.constant.DomainConstant;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.vo.DomainExcelVO;
import com.platformcommons.platform.service.domain.domain.vo.DomainExcelVOV2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DomainExcelMapper {

    @Autowired
    private TagService tagService;


    @Value("${commons.platform.service.attachment.base-url:https://dev-attachment-ms.s3.ap-south-1.amazonaws.com}")
    private String attachmentBaseURL;

    public Domain fromDomainExcelVO(DomainExcelVO domainExcelVO, Domain parentDomain) {
       return Domain.builder()
               .id(0L)
               .code(StringUtils.isEmpty(domainExcelVO.getCode()) ? generateDomainCode(domainExcelVO.getName(),parentDomain.getCode()) : domainExcelVO.getCode())
               .name(StringUtils.isEmpty(domainExcelVO.getName()) ? null : domainExcelVO.getName())
               .description(StringUtils.isEmpty(domainExcelVO.getDescription()) ? null : domainExcelVO.getDescription())
               .icon(StringUtils.isEmpty(domainExcelVO.getIcon()) ? null : domainExcelVO.getIcon())
               .banner(StringUtils.isEmpty(domainExcelVO.getBanner()) ? null : domainExcelVO.getBanner())
               .context(domainExcelVO.getContext())
               .tagList(setTagList(domainExcelVO))
               .isRoot(Boolean.FALSE)
               .build();
    }


    private Set<Tag> setTagList(DomainExcelVO domainExcelVO) {
        Set<String> preDefinedTagCodes = Arrays.stream(domainExcelVO.getSdgTagCode().split(",")).collect(Collectors.toSet());
        Set<Tag> preSavedTagList = new HashSet<>();
        if(!preDefinedTagCodes.isEmpty()) {
            preSavedTagList = tagService.getByCodes(preDefinedTagCodes);
        }
        Set<Tag> allTagsMappedToDomain = new HashSet<>(preSavedTagList);
        allTagsMappedToDomain.add(getTagObjectFromString(domainExcelVO.getKeyPoint1()));
        allTagsMappedToDomain.add(getTagObjectFromString(domainExcelVO.getKeyPoint2()));
        allTagsMappedToDomain.add(getTagObjectFromString(domainExcelVO.getKeyPoint3()));
        allTagsMappedToDomain.add(getTagObjectFromString(domainExcelVO.getKeyPoint4()));
        allTagsMappedToDomain.add(getTagObjectFromString(domainExcelVO.getKeyPoint5()));
        allTagsMappedToDomain.add(getTagObjectFromString(domainExcelVO.getKeyPoint6()));
        allTagsMappedToDomain.remove(null);
        return allTagsMappedToDomain;
    }

    private Set<Tag> setTagList(DomainExcelVOV2 domainExcelVO) {
        Set<Tag> allTagsMappedToDomain = new LinkedHashSet<>();
        Set<String> preDefinedTagCodes = Arrays.stream(domainExcelVO.getSdgs().split(","))
                .filter(it->it!=null && !it.isEmpty()).map(it->"TAG."+it).collect(Collectors.toSet());
        Set<Tag> preSavedTagList = new HashSet<>();
        if(!preDefinedTagCodes.isEmpty()) {
            preSavedTagList = tagService.getByCodes(preDefinedTagCodes);
        }
        allTagsMappedToDomain.addAll(preSavedTagList);
        Set<String> keyPoints = Arrays.stream(domainExcelVO.getKeyPoints().split("--")).collect(Collectors.toSet());
        Set<Tag> keyPointsTags =buildKeyPointsTags(keyPoints);
        allTagsMappedToDomain.addAll(keyPointsTags);


        Set<String> tagsNames = Arrays.stream(domainExcelVO.getTags().split("--")).collect(Collectors.toSet());
        Set<Tag> tags =buildTags(tagsNames);
        allTagsMappedToDomain.addAll(tags);
        return allTagsMappedToDomain;
    }

    private Set<Tag> buildKeyPointsTags(Set<String> keyPoints) {

        Set<Tag> tags = new LinkedHashSet<>();
        keyPoints.stream().filter(it -> it != null && !it.isEmpty())
                .forEach(it -> tags.add(Tag.builder()
                        .id(0L)
                        .code(convertTOCode(it, "TAG"))
                        .name(it)
                        .banner(convertNameToAttachmentURL(it, "keypoint"))
                        .icon(convertNameToAttachmentURL(it, "keypoint"))
                        .type(DomainConstant.TAG_TYPE_KEY_POINT)
                        .build()));
        return tags;
    }


    private Set<Tag> buildTags(Set<String> tagList) {

        Set<Tag> tags = new LinkedHashSet<>();
        tagList.stream().filter(it->it!=null && !it.isEmpty())
                .forEach(it->tags.add( Tag.builder()
                        .id(0L)
                        .code(convertTOCode(it,"TAG"))
                        .name(it)
                        .type(DomainConstant.TAG_TYPE_TAG)
                        .build()));
        return tags;
    }

    public  String convertTOCode(String name,String prefix) {
        if(name!=null && !name.isEmpty()) {
            // Replace non-alphanumeric characters with underscores
            name =name.trim().replaceAll("[^a-zA-Z0-9]+", "_");
            // Replace spaces with underscores
            name = name.replace(" ", "_");
            // Convert to uppercase
            name = name.toUpperCase();
            // Add prefix
            name = prefix + "." + name;
            return name;
        }
        else {
            throw  new InvalidInputException("Not a valid Name");
        }
    }

    public  String convertNameToAttachmentURL(String name,String path) {
        if(name!=null && !name.isEmpty()) {
            // Replace non-alphanumeric characters with underscores
            name = name.trim().replaceAll("[^a-zA-Z0-9]+", "_");
            // Replace spaces with underscores
            name = name.replace(" ", "_");
            // Convert to uppercase
            name = name.toLowerCase();
            // Add prefix
            return  attachmentBaseURL+"/"+path+"/"+name+".png";
        }
        else {
            throw  new InvalidInputException("Not a valid Name");
        }
    }

    private Tag getTagObjectFromString(String keyPoint) {
        if(StringUtils.isEmpty(keyPoint)) {
            return null;
        }
        List<String> listOfValues = Arrays
                .stream(keyPoint.split("@@"))
                .collect(Collectors.toCollection(LinkedList::new));
        return Tag.builder()
                .id(0L)
                .code(getTagCode(listOfValues))
                .name(listOfValues.size()>=1 ? listOfValues.get(0) : null)
                .type(DomainConstant.TAG_TYPE_KEY_POINT)
                .description(listOfValues.size()>=2 ? listOfValues.get(1) : null)
                .icon(listOfValues.size()>=3 ? listOfValues.get(2) : null)
                .banner(listOfValues.size()>=4 ? listOfValues.get(3) : null)
                .sequence(listOfValues.size()>=5 ? Long.parseLong(listOfValues.get(4)) : null)
                .color(listOfValues.size()>=6 ? listOfValues.get(5) : null)
                .backgroundColor(listOfValues.size()>=7 ? listOfValues.get(6) : null)
                .build();
    }

    private String getTagCode(List<String> listOfValues) {
        if(listOfValues.isEmpty()) {
            return null;
        }
        String tagNameModified = listOfValues.get(0)
                .replaceAll(" ","_")
                .replaceAll("[^a-zA-Z0-9_]","")
                .toUpperCase();
        return "TAG." + tagNameModified + new Date().getTime();
    }

    public String generateDomainCode(String subDomainName, String parentDomainCode) {
        return convertTOCode(subDomainName,parentDomainCode);
    }

    public Domain fromDomainExcelVO(DomainExcelVOV2 domainExcelVO, Domain parentDomain) {
        return Domain.builder()
                .id(0L)
                .code(generateDomainCode(domainExcelVO.getName(),parentDomain.getCode()))
                .name(StringUtils.isEmpty(domainExcelVO.getName()) ? null : domainExcelVO.getName())
                .description(StringUtils.isEmpty(domainExcelVO.getDescription()) ? null : domainExcelVO.getDescription())
                .icon(convertNameToAttachmentURL(domainExcelVO.getName(),"theme"))
                .banner(convertNameToAttachmentURL(domainExcelVO.getName(),"theme"))
                .context(parentDomain.getContext())
                .tagList(setTagList(domainExcelVO))
                .isRoot(Boolean.FALSE)
                .build();
    }
}
