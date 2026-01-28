package com.platformcommons.platform.service.iam.application.config;

import java.io.Serializable;

import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.UserRoleMap;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class TLDIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        if (object instanceof User) {
            User user = (User) object;
            return user.getId()==null || user.getId()==0?getSequence(IAMConstant.USER_SEQUENCE,session):user.getId();
        }
        else if(object instanceof Tenant){
            Tenant tenant = (Tenant) object;
            return tenant.getId()==null || tenant.getId()==0?getSequence(IAMConstant.TENANT_SEQUENCE,session):tenant.getId();
        }
        else if(object instanceof UserRoleMap){
            UserRoleMap userRoleMap = (UserRoleMap) object;
            return userRoleMap.getId()==null || userRoleMap.getId()==0?getSequence(IAMConstant.ROLE_MAP_SEQUENCE,session):userRoleMap.getId();
        }
        throw new HibernateException("Unable to generate department ID");
    }

    private Long getSequence(String sequenceName, SharedSessionContractImplementor session) {
        final boolean not_active = session.getTransaction().getStatus()== TransactionStatus.NOT_ACTIVE;
        if(not_active)  session.beginTransaction();
        Object obj = session.getNamedQuery("Sequence.getSequence")
                                 .setParameter("name", sequenceName)
                                 .uniqueResult();
        Long sequence = (Long) obj;
        session.getNamedQuery("Sequence.updateNameByNextVal")
               .setParameter("name", sequenceName)
               .executeUpdate();
        if(not_active)  session.getTransaction().commit();
        return sequence;
    }

}