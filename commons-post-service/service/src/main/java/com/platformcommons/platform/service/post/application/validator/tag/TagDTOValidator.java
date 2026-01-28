package com.platformcommons.platform.service.post.application.validator.tag;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.domain.client.TagClientV2;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TagDTOValidator {

    private final Optional<TagCacheManager> tagCacheManager;
    private final TagClientV2 tagClient;

    public String validateCode(String code) {
        if (code != null) {
            return this.tagCacheManager.flatMap(cacheManager -> Optional.ofNullable(cacheManager.getValueForKey(code)).map(TagDTO::getCode))
                    .orElseGet(() -> this.getTagFromClient(code).getCode());
        } else {
            return null;
        }
    }

    public TagDTO toDTO(String code) {
        TagDTO tagDTO = null;
        if (Objects.nonNull(code)) {
            tagDTO=this.tagCacheManager.flatMap(cacheManager -> Optional.ofNullable(cacheManager.getValueForKey(code)))
                    .orElseGet(() -> this.getTagFromClient(code));
        }
        return tagDTO;
    }


    private TagDTO getTagFromClient(String code){
        try {
            Set<TagDTO> tagDTOPage = tagClient.getTagByCodes(Collections.singleton(code)).getBody();
            if(tagDTOPage==null)  throw  new RuntimeException("Tag is Null or empty");
            return tagDTOPage.stream().findFirst().orElseThrow(()-> new NotFoundException(String.format("Tag with code %s not found",code)));
        }catch (FeignException feignException){
            throw  new RuntimeException(feignException.getMessage());
        }
    }
}
