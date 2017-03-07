package com.fmo.wom.common.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.fmo.wom.domain.UserAccountBO;
import com.fmo.wom.domain.WOMBaseBO;

public class AuditInterceptor extends EmptyInterceptor {

    private static final String CREATE_USERID_FIELD = "createUserId";
    private static final String CREATE_TS_FIELD = "createTimestamp";
    private static final String UPDATE_USERID_FIELD = "updateUserId";
    private static final String UPDATE_TS_FIELD = "updateTimestamp";
    
    private static ThreadLocal<UserAccountBO> userAccount = new ThreadLocal<UserAccountBO>();
           
    public static void setUserAccount(UserAccountBO aUser) {
        userAccount.set(aUser);
    }
           
    public static UserAccountBO getUserAccount() {
        return userAccount.get();
    }
    
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
                                   Object[] previousState, String[] propertyNames, Type[] types) {
        
        if (entity instanceof WOMBaseBO) {
            Date now = new Date();
            setValue(currentState, propertyNames, UPDATE_TS_FIELD, now);
            setValue(currentState, propertyNames, UPDATE_USERID_FIELD, getUserAccount().getUserID());
        }
        return true;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        
        if (entity instanceof WOMBaseBO) {
    
            Date now = new Date();
            setValue(state, propertyNames, CREATE_TS_FIELD, now);
            setValue(state, propertyNames, CREATE_USERID_FIELD, getUserAccount().getUserID());
            setValue(state, propertyNames, UPDATE_TS_FIELD, now);
            setValue(state, propertyNames, UPDATE_USERID_FIELD, getUserAccount().getUserID());
        }

        return true;
    }

    /**
     * Find the propertyToSet in the propertyNames array, then set the value to it.
     * @param currentState
     * @param propertyNames
     * @param propertyToSet
     * @param value
     */
    private void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value) {
        
        List<String> propNamesAsList = Arrays.asList(propertyNames);
        int index = propNamesAsList.indexOf(propertyToSet);
        if (index >= 0) {
            currentState[index] = value;
        }
    }
    
}
