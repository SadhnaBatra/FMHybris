/**
 * 
 */
package com.fmo.wom.integration.nabs.ims;

import java.util.List;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.integration.nabs.NabsPropertiesUtil;

/**
 * Implements a pool of NabsUsers
 * usage:
 * NabsUsers nabsUsers = null;
 * try {
 *    nabsUsers = NabsUserPool.getInstance().borrow();
 *    //...use the NabsUser...
 * } catch(Exception e) {
 *    //...handle any exceptions...
 * } finally {
 *    // make sure the object is returned to the pool
 *    if(nabsUser != null) {
 *       NabsUserPool.getInstance().returnObject(nabsUsers);
 *    }
 * }
 *
 */
public class NabsUserPool {

    private static GenericObjectPool pool = null;
    private static NabsUserPool me = null;
    private static Logger logger = Logger.getLogger(NabsUserPool.class);

    /** 
     * creatableUsers - Holds the id and pwd of NabsSystemUsers that can be 
     * placed in the pool but aren't yet
     **/
    //JAK NOTE: This is a bit strange but there is no init() method on the Pool where
    //I can load it with an initial list. I have to let the pool call the factory.
    //private static Hashtable<String, String> creatableUsers = null;
    private static List<NabsIMSUser> nabsIMSUsersList = null;
    /** Creates a new instance of Pool */
    private NabsUserPool() {
        super();
        createPool();
    }
        
    /** get instance of UserPool */
    public static NabsUserPool getInstance(){
        if (me == null){
        	synchronized (NabsUserPool.class) {
        		if(me == null){
        			me = new NabsUserPool();
        		}
			}
        }
        return me;
    }

    public static void destroyInstance(){
        me = null;
    }

    /**
     * Creates the user factory and the user pool
     *
     */
    private synchronized void createPool() {
    	
    	nabsIMSUsersList = NabsPropertiesUtil.getNABSIMSUsersList();
        if(nabsIMSUsersList !=null && (! nabsIMSUsersList.isEmpty())){
        	int poolSize = nabsIMSUsersList.size();
            logger.info(" create NABS users pool createPool() IMS Users "+nabsIMSUsersList+" poolSize "+poolSize);
            pool = new GenericObjectPool(new NabsUserFactory());
            pool.setMaxActive(poolSize);
            pool.setMaxIdle(poolSize);
            pool.setMaxWait(0);//wait forever
            pool.setMinEvictableIdleTimeMillis(-1);//never run
            pool.setNumTestsPerEvictionRun(0);//never run
            pool.setTestOnBorrow(false);
            pool.setTestOnReturn(false);
            pool.setTestWhileIdle(false);
            pool.setTimeBetweenEvictionRunsMillis(-1); //never run
            pool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
        
        } else{
        	logger.error("createPool() : error in create NABS users pool ");
        }
    }

    /**
     * Returns a NabsIMSUser from the pool
     * 
     * @return  a <code>NabsIMSUser</code> object
     * @throws  a <code>java.lang.Exception</code> object
     * @exception   Exception
     */
    public NabsIMSUser borrow() throws Exception
    {
    	NabsIMSUser userFromPool = (NabsIMSUser)pool.borrowObject(); 
    	logger.info(" Using user "+userFromPool+" from pool");
    	return userFromPool;
    }
    /**
     * Returns a user to the pool
     * 
     * @param   object
     * @return  none
     * @throws  a <code>java.lang.Exception</code> object
     * @exception   Exception
     */
    public void returnObject(Object aUser) throws Exception
    {
        if (aUser instanceof NabsIMSUser)
        {
        	logger.info(" Returning user "+aUser+" to pool");
            pool.returnObject(aUser);
        }
    }
    /**
     * This function is shutting down the whole connection pool
     * 
     * @exception   WOMTransactionException
     */
    public void shutdown() throws Exception
    {
        pool.close();
        pool.clear();
    }
    
    /**
     * get the number of active users in the pool
     * 
     */
    public int getNumActive() 
    {
        return pool.getNumActive();
    }
    /**
     * get the number of idle users of the pool
     * 
     */
    public int getNumIdle() 
    {
        return pool.getNumIdle();
    }

    class NabsUserFactory extends BasePoolableObjectFactory{

        /**
         * Create an instance that can be served by the pool.
         * @return an instance that can be served by the pool.
         */

    	public synchronized Object makeObject() throws Exception {
            /*if (creatableUsers.size() <= 0)
            {
                throw new Exception("Attempt was made to add more elements to NabsUserFactory but no more IDs exist");
            }
            String id = creatableUsers.keys().nextElement();
            String pwd = creatableUsers.get(id);
            creatableUsers.remove(id);
            
            logger.info(" makeObject() "+id+" "+pwd);
            return new NabsIMSUser(id, pwd);*/
        	
        	if(nabsIMSUsersList == null || nabsIMSUsersList.isEmpty()){
        		throw new Exception("Attempt was made to add more elements to NabsUserFactory but no more IDs exist");
        	}
        	//return the first available user
        	return nabsIMSUsersList.remove(0);
        }
    }

}


