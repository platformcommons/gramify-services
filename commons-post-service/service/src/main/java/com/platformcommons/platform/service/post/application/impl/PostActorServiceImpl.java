package com.platformcommons.platform.service.post.application.impl;

import com.mindtree.bridge.platform.dto.PersonContactDTO;
import com.mindtree.bridge.platform.dto.PersonDTO;
import com.mindtree.bridge.platform.dto.UserDTO;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.PostActorService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.application.utility.CommonsReportUtil;
import com.platformcommons.platform.service.post.domain.PostActor;
import com.platformcommons.platform.service.post.domain.repo.PostActorRepository;
import com.platformcommons.platform.service.post.dto.UserDetailsDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostActorServiceImpl implements PostActorService {

    @Autowired
    public PostActorRepository postactorRepository;

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    public PostActor getByActorIdAndActorType(Long actorId,String actorType) {
        return postactorRepository.getOrAddForCurrentUser(actorId,actorType).orElseThrow(
                ()->new NotFoundException(String.format("Post Actor Not found for user with actorId as %d",actorId)));
    }
    @Override
    public PostActor getOrAddForCurrentUser() {
        Optional<PostActor> postActorOptional = postactorRepository.
                getOrAddForCurrentUser(PlatformSecurityUtil.getCurrentUserId(),
                        PlatformSecurityUtil.getActorContext().getActorContext());

        PostActor postActor;
        if (postActorOptional.isPresent()) {
            postActor = postActorOptional.get();
        }
        else {
            if(PlatformSecurityUtil.getActorContext().getActorContext().equals("ACTOR_TYPE.BRIDGE_USER")) {
                UserDetailsDTO userDetailsDTO = commonsReportUtil.getUserDetails(PlatformSecurityUtil.getCurrentUserId()).stream()
                        .findFirst()
                        .orElse(null);
                postActor = PostActor.builder()
                        .id(null)
                        .actorId(PlatformSecurityUtil.getCurrentUserId())
                        .actorType(PlatformSecurityUtil.getActorContext().getActorContext())
                        .name(userDetailsDTO != null ? userDetailsDTO.getFullName() : PlatformSecurityUtil.getCurrentUserName())
                        .iconpic(userDetailsDTO != null ? userDetailsDTO.getIconPic() : null)
                        .emailAddress(userDetailsDTO != null ? userDetailsDTO.getEmailAddress() : null)
                        .contactNumber(userDetailsDTO != null ? userDetailsDTO.getContactNumber() : null)
                        .username(userDetailsDTO != null ? userDetailsDTO.getUsername() : null)
                        .build();
                postActor = postactorRepository.save(postActor);
            } else if (PlatformSecurityUtil.getActorContext().getActorContext().equals("ACTOR_TYPE.IAM_USER")) {
                //TODO to fetch name ,icon pic etc of logged in user
                postActor = PostActor.builder()
                        .id(null)
                        .actorId(PlatformSecurityUtil.getCurrentUserId())
                        .actorType(PlatformSecurityUtil.getActorContext().getActorContext())
                        .username(PlatformSecurityUtil.getCurrentUserName())
                        .name(PlatformSecurityUtil.getCurrentUserName()) //TODO imp name to be fetched
                        .build();
                postActor = postactorRepository.save(postActor);
            }
            else {
                throw  new InvalidInputException("Invalid actor Type");
            }
        }
        return postActor;
    }

    @Transactional
    @Override
    public void updatePostActorDetails(UserDTO userDTO) {
        Long actorId = Long.valueOf(userDTO.getId());
        String displayName = fetchDisplayName(userDTO);
        String actorType = PlatformSecurityUtil.getActorContext().getActorContext();
        String iconpic = userDTO.getIconpic() != null ? userDTO.getIconpic() : userDTO.getPerson() != null ? userDTO.getPerson().getIconpic() : null;
        String contactNumber = getContactValueFromUserDTO(userDTO).get(PostConstant.CONTACT_TYPE_MOBILE);
        String username = userDTO.getAliasId();
        postactorRepository.updatePostActorDetails(actorId,actorType,displayName,iconpic,contactNumber,username);
    }

    public String fetchDisplayName(UserDTO userDTO) {
        String displayName = "";
        if(userDTO.getFirstName() != null) {
            displayName = displayName.concat(userDTO.getFirstName()).concat(" ");
        }
        if(userDTO.getLastName() != null) {
            displayName = displayName.concat(userDTO.getLastName());
        }
        return StringUtils.trim(displayName);
    }

    @Override
    public PostActor patchUpdateForLoggedInUser(PostActor postActor) {
        PostActor fetchedPostActor = getByActorIdAndActorType(PlatformSecurityUtil.getCurrentUserId(),
                PlatformSecurityUtil.getActorContext().getActorContext());
        fetchedPostActor.patchUpdateForClickCount(postActor);
        return postactorRepository.save(fetchedPostActor);
    }

    public Map<String,String> getContactValueFromUserDTO(UserDTO userDTO) {
        Map<String, String> contactValue = new LinkedHashMap<>();
        if(userDTO!=null){
            PersonDTO personDTO = userDTO.getPerson();
            if(personDTO!=null){
                List<PersonContactDTO> personContactDTOS = personDTO.getPersonContacts();
                if(personContactDTOS!=null && !personContactDTOS.isEmpty()){
                    contactValue= convertToMap(personContactDTOS);
                }
            }
        }
        return contactValue;
    }

    public Map<String, String> convertToMap(List<PersonContactDTO> personContacts){
        Map<String,String> contactValues = new LinkedHashMap<>();
        personContacts.forEach(it-> {
            if(it.getContact()!=null) {
                contactValues.put(it.getContact().getContactType().getDataCode(),it.getContact().getContactValue());
            }
        });
        return  contactValues;
    }

}