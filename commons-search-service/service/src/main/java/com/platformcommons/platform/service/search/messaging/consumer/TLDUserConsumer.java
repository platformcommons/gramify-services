package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.bridge.platform.dto.UserDTO;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.TenantUserService;
import com.platformcommons.platform.service.search.domain.TenantUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TLDUserConsumer {

    @Autowired
    TenantUserService tenantUserService;

//    @KafkaListener(topics = MessagingConstant.BRBASE_USER_CREATED,groupId = MessagingConstant.GROUP_ID,
//            containerFactory = ServiceConstant.KAFKA_STRING_LISTENER_FACTORY)
    public void userCreated(@Payload String payload){
        try{
            System.out.println(payload);
            String tenantContext = (String)new ObjectMapper().readValue(payload,Map.class).get("tenantContext");
            String userContext = (String)new ObjectMapper().readValue(payload,Map.class).get("userContext");
            PlatformSecurityUtil.setEventTLDContext(userContext,tenantContext,"ACTOR_TYPE.BRIDGE_USER",null);

            String  userPayload = (String)new ObjectMapper().readValue(payload, Map.class).get("requestPayload");
            UserDTO userDTO=new ObjectMapper().readValue(userPayload,UserDTO.class);
            System.out.println(userDTO);
            System.out.println("+++++++++++++++++++++++++++++++++++");

            TenantUser user= new TenantUser();
            user.setId((long) userDTO.getId());
            user.setUser_login(userDTO.getLogin());
            user.setCompleteName(userDTO.getFirstName()+" "+userDTO.getLastName());

            if(!(userDTO.getPerson().getPersonContacts()==null)){
                if(userDTO.getPerson().getPersonContacts().get(0).getContact().getContactType().getLabel().equals("Mobile")){
                    user.setMobile(Long.parseLong(userDTO.getPerson().getPersonContacts().get(0).getContact().getContactValue()));
                }
                else{
                    user.setEmail(userDTO.getPerson().getPersonContacts().get(0).getContact().getContactValue());
                }
            }
            if(userDTO.getPerson().getPersonContacts().size()==2){
                if(userDTO.getPerson().getPersonContacts().get(1).getContact().getContactType().getLabel().equals("Mobile")){
                    user.setMobile(Long.parseLong(userDTO.getPerson().getPersonContacts().get(1 ).getContact().getContactValue()));
                }
                else{
                    user.setEmail(userDTO.getPerson().getPersonContacts().get(1).getContact().getContactValue());
                }
            }

            if(userDTO.getPerson().getPersonProfile().getGender()!=null){
                user.setGender(userDTO.getPerson().getPersonProfile().getGender().getLabel());
            }
            if(!(userDTO.getPerson().getPersonAddresses()==null)){
                user.setCity(userDTO.getPerson().getPersonAddresses().get(0).getAddress().getCity().getLabel());
                user.setState(userDTO.getPerson().getPersonAddresses().get(0).getAddress().getState().getLabel());
                user.setPincode(Long.parseLong(userDTO.getPerson().getPersonAddresses().get(0).getAddress().getPinCode()));
            }

            user.setTenant_id(PlatformSecurityUtil.getCurrentTenantId());
            user.setTenant_login(PlatformSecurityUtil.getCurrentTenantLogin());
            //user.setRoles_codes(String.join(",",PlatformSecurityUtil.getCurrentUserContext().getAuthorities()));

            tenantUserService.saveTenantUser(user);

        }
        catch(JsonProcessingException ex){
            ex.printStackTrace();
        }
    }

//    @KafkaListener(topics = MessagingConstant.BRBASE_USER_UPDATED,groupId = MessagingConstant.GROUP_ID,
//            containerFactory = ServiceConstant.KAFKA_STRING_LISTENER_FACTORY)
    public void updated(@Payload String payload){
        try{
            System.out.println(payload);
            String tenantContext = (String)new ObjectMapper().readValue(payload,Map.class).get("tenantContext");
            String userContext = (String)new ObjectMapper().readValue(payload,Map.class).get("userContext");
            PlatformSecurityUtil.setEventTLDContext(userContext,tenantContext,"ACTOR_TYPE.BRIDGE_USER",null);

            String  userPayload = (String)new ObjectMapper().readValue(payload, Map.class).get("requestPayload");
            UserDTO userDTO=new ObjectMapper().readValue(userPayload,UserDTO.class);
            System.out.println(userDTO);
            System.out.println("+++++++++++++++++++++++++++++++++++");

            TenantUser user= new TenantUser();
            user.setId((long) userDTO.getId());
            user.setUser_login(userDTO.getLogin());
            user.setCompleteName(userDTO.getFirstName()+" "+userDTO.getLastName());

            if(!(userDTO.getPerson().getPersonContacts()==null)){
                if(userDTO.getPerson().getPersonContacts().get(0).getContact().getContactType().getLabel().equals("Mobile")){
                    user.setMobile(Long.parseLong(userDTO.getPerson().getPersonContacts().get(0).getContact().getContactValue()));
                }
                else{
                    user.setEmail(userDTO.getPerson().getPersonContacts().get(0).getContact().getContactValue());
                }
            }

            if(userDTO.getPerson().getPersonContacts().size()==2){
                if(userDTO.getPerson().getPersonContacts().get(1).getContact().getContactType().getLabel().equals("Mobile")){
                    user.setMobile(Long.parseLong(userDTO.getPerson().getPersonContacts().get(1).getContact().getContactValue()));
                }
                else{
                    user.setEmail(userDTO.getPerson().getPersonContacts().get(1).getContact().getContactValue());
                }
            }

            if(userDTO.getPerson().getPersonProfile().getGender()!=null){
                user.setGender(userDTO.getPerson().getPersonProfile().getGender().getLabel());
            }
            if(!(userDTO.getPerson().getPersonAddresses()==null)){
                user.setCity(userDTO.getPerson().getPersonAddresses().get(0).getAddress().getCity().getLabel());
                user.setState(userDTO.getPerson().getPersonAddresses().get(0).getAddress().getState().getLabel());
                user.setPincode(Long.parseLong(userDTO.getPerson().getPersonAddresses().get(0).getAddress().getPinCode()));
            }

            user.setTenant_id(PlatformSecurityUtil.getCurrentTenantId());
            user.setTenant_login(PlatformSecurityUtil.getCurrentTenantLogin());
            //user.setRoles_codes(String.join(",",PlatformSecurityUtil.getCurrentUserContext().getAuthorities()));

            tenantUserService.updateTenantUser(user);

        }
        catch(JsonProcessingException ex){
            ex.printStackTrace();
        }
    }
}
