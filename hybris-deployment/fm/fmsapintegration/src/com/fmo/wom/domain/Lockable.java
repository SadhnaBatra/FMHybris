package com.fmo.wom.domain;


public abstract class Lockable extends WOMBaseBO {
    
    public abstract String getLockName();
    public abstract String getServiceBaseName();
    
    /*
     * These 4 methods can be overridden to whatever keys you want to make Lockable specific
     * I18N messages
     */
    public String getLockFoundKey() {
        return LockExceptionType.LOCK_FOUND.name();
    }
    
    public String getLockNotFoundOnUnlockKey() {
        return LockExceptionType.LOCK_NOT_FOUND_ON_UNLOCK.name();
    }
    
    public String getDirtyLockOnUnlockKey() {
        return LockExceptionType.DIRTY_LOCK_ON_UNLOCK.name();
    }
    
    public String getLockConflictFoundKey() {
        return LockExceptionType.LOCK_CONFLICT_FOUND.name();
    }
    
    /**
     * Explicit decl of the types of exceptions we'll throw for I18N.  These will be the look up keys for
     * default messages as well as definitions of what needs to be overridden by Lockables to make their
     * own customized messages.
     * @author hawked73
     *
     */
    private enum LockExceptionType {
        
        LOCK_FOUND,
        LOCK_NOT_FOUND_ON_UNLOCK,
        DIRTY_LOCK_ON_UNLOCK,
        LOCK_CONFLICT_FOUND;
        
    }
    
    
}
