package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.iam.application.UserVerificationService;
import com.platformcommons.platform.service.iam.application.constant.VerificationConstant;
import com.platformcommons.platform.service.iam.domain.UserVerification;
import com.platformcommons.platform.service.iam.domain.repo.UserVerificationRepository;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserVerificationServiceImpl implements UserVerificationService {

    @Autowired
    private UserVerificationRepository repository;

    @Autowired
    @Lazy
    private OBOFacade oboFacade;

    public UserVerification getById(Long id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException(String.format("User Verification of id %d not found",id)));
    }

    @Override
    public UserVerification getByUserIdAndAppContext(Long userId, String appContext, String marketContext) {
        return getOptionalByUserIdAndAppContext(userId,appContext,marketContext).orElseThrow(
                ()-> new NotFoundException(String.format("User Verification not found for user with id %d",userId)));
    }

    @Override
    public Optional<UserVerification> getOptionalByUserIdAndAppContext(Long userId, String appContext, String marketContext) {
        return repository.findByUserIdAndAppContext(userId,appContext,marketContext);
    }

    @Override
    public void createUserVerificationOnRegistration(Long userId,String appContext, String marketContext) {
        Boolean exists = repository.existsByUserIdAndAppContext(userId,appContext);
        if(!exists) {
            UserVerification userVerification = UserVerification.builder()
                    .id(0L)
                    .userId(userId)
                    .verificationStatus(VerificationConstant.VERIFICATION_STATUS_NOT_REVIEWED)
                    .verificationRequestMessage("Signed Up for Verification")
                    .appContext(appContext)
                    .marketContext(marketContext)
                    .build();
            repository.save(userVerification);
        }
    }

    @Override
    public UserVerification userVerificationStatusUpdate(UserVerification toBeUpdated, Boolean deleteAllAssignedRoles) {
        UserVerification fetchedUserVerification = getById(toBeUpdated.getId());
        fetchedUserVerification.statusChange(toBeUpdated);
        modifyRolesOnStatusChange(fetchedUserVerification,deleteAllAssignedRoles);
        repository.save(fetchedUserVerification);
        return fetchedUserVerification;
    }

    public void modifyRolesOnStatusChange(UserVerification userVerification,Boolean deleteAllAssignedRoles) {
        String status = userVerification.getVerificationStatus();
        Long userId = userVerification.getUserId();
        if(Objects.equals(status,VerificationConstant.VERIFICATION_STATUS_VERIFIED)) {
            //TODO: TLD dependency to be configured.
            oboFacade.assignRolesToUser(userId,userVerification.getAssignedRoles());
        }
        else if( (Objects.equals(status,VerificationConstant.VERIFICATION_STATUS_ON_HOLD)
                || Objects.equals(status,VerificationConstant.VERIFICATION_STATUS_REJECTED))
                && Objects.equals(deleteAllAssignedRoles,Boolean.TRUE)) {
                oboFacade.deActivateRolesFromAnUser(userId,userVerification.getAssignedRoles(),"User Verification",Boolean.TRUE);
                userVerification.setAssignedRoles(null);
        }
    }
}
