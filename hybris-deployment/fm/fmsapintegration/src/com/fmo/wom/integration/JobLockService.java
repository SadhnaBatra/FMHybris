package com.fmo.wom.integration;



//import org.springframework.beans.factory.annotation.Autowired;

import com.fmo.wom.common.exception.WOMLockConflictException;
import com.fmo.wom.common.exception.WOMLockFoundException;
import com.fmo.wom.common.exception.WOMLockNotFoundException;
//import com.fmo.wom.common.util.WomWebProfiling;
import com.fmo.wom.domain.JobLockBO;
import com.fmo.wom.domain.Lockable;
//import com.fmo.wom.integration.util.JobLockServiceUtil;

//WomWebProfiling
public class JobLockService {
	/*
    private static Logger logger = Logger.getLogger(JobLockService.class);

    //Autowired 
    private JobLockServiceUtil jobLockServiceUtil;
    
    public JobLockBO lock(Lockable lockable, String userId) 
                throws WOMLockFoundException, WOMLockNotFoundException, WOMLockConflictException  {

        logger.debug("User {} attempting to lock service {}", userId, lockable.getLockName());
        
        // create a new lock.  Will be unlocked
        JobLockBO jobLock = createNewJobLock(lockable, userId);
        
        // insert a lock to the database
        jobLockServiceUtil.insert(jobLock);
        
        // now try to lock it
        return lock(lockable, jobLock);
    } 
    
    public void unlock(Lockable lockable, JobLockBO aLock, String userId) throws WOMLockConflictException, WOMLockNotFoundException {

        logger.debug("User {} attempting to unlock service {}", userId, aLock.getName());

        // now try to unlock it  This can conflict with other threads, who may be locking or unlocking
        // this service, so if we encounter a lock conflict, we'll retry it 3 times just to be safe before exploding
        int retries = 3;
        for (int i = 0; i < retries; i++) {
            try {
                jobLockServiceUtil.unlock(lockable, aLock, userId);
                break;
            } catch (WOMLockConflictException lce) {
                logger.debug("Lock conflict encountered for lock={}", aLock.getName());
                if (i == retries - 1) {
                    throw lce;
                }
            } 
        }
    }
    */

    /**
     * Attempt to lock it.  This can conflict with other threads, who may be locking or unlocking
     * this service, so if we encounter a lock conflict, we'll retry it 3 times just to be safe before exploding
     * Other lock problems will generate an exception 
     */ 
   /* private JobLockBO lock(Lockable lockable, JobLockBO jobLock)  throws WOMLockConflictException, WOMLockFoundException, WOMLockNotFoundException {
        
        int retries = 3;
        for (int i = 0; i < retries; i++) {
            try {
                jobLock = jobLockServiceUtil.lock(lockable, jobLock);
                break;
            } catch (WOMLockConflictException lce) {
                logger.debug("Lock conflict encountered for lock={}", jobLock.getName());
                if (i == retries - 1) {
                    throw lce;
                }
            } catch (WOMLockNotFoundException lnfe) {
                // this should never happen as we just inserted it, so its bad.
                logger.error("Lock name={} not found in lock attempt. Abort.", jobLock.getName());
                throw lnfe;
            } catch (WOMLockFoundException lfe) {
                // lock found means someone else has it
                logger.error("Lock name={} already locked!! Abort.", jobLock.getName());
                throw lfe;
            }
        }
        
        return jobLock;
    }
    
    
    private JobLockBO createNewJobLock(Lockable lockable, String userId) {
        
        JobLockBO jobLock = new JobLockBO();
        jobLock.setName(lockable.getLockName());
        jobLock.setLocked(false);
        jobLock.setLockedBy(userId);
        return jobLock;
        
    }
    */
}