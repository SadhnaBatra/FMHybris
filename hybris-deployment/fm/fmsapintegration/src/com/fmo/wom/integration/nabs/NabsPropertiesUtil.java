package com.fmo.wom.integration.nabs;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import com.fmo.wom.common.util.PropertiesUtil;
import com.fmo.wom.integration.nabs.ims.NabsIMSUser;

public class NabsPropertiesUtil {

    public static final String USER_POOL_USERS_KEY = "user_pool_users";
    public static final String IMS_GROUP_KEY = "ims_group";
    public static final String IMS_HOSTNAME_KEY = "ims_hostname";
    public static final String IMS_PORT_KEY = "ims_port";
    public static final String IMS_DATASTORE_KEY = "ims_datastore";

    /*
     * Socket & SocketPool settings keys stored in the database.  Shamelessly taken
     * from WOM6 as this is pretty standard.
     */
    
    /**
     * maxActive controls the maximum number of objects (per key) that can be borrowed
     * from the pool at one time. When non-positive, there is no limit to the number of
     * objects that may be active at one time. When maxActive is exceeded, the pool is
     * said to be exhausted.
     **/
    public static final String SOCKETPOOL_MAXACTIVE_KEY = "socketpool.maxActive";
    /**
    * maxIdle controls the maximum number of objects that can sit idle in the pool
    * (per key) at any time. When non-positive, there is no limit to the number of objects
    * that may be idle at one time.
    **/
    public static final String SOCKETPOOL_MAXIDLE_KEY = "socketpool.maxIdle";
    /**
     * The maximum amount of time (in millis) the borrowObject(java.lang.Object)
     * method should block before throwing an exception when the pool is exhausted and the
     * "when exhausted" action is WHEN_EXHAUSTED_BLOCK
     **/
    public static final String SOCKETPOOL_MAXWAIT_KEY = "socketpool.maxWait";
    /**
     * The number of objects to examine per run in the idle object evictor
     **/
    public static final String SOCKETPOOL_NUM_TESTS_PER_EVICTION_RUN_KEY = "socketpool.numTestsPerEvictionRun";
    /**
     * When testOnBorrow is set, the pool will attempt to validate each object before it is
     * returned from the borrowObject(java.lang.Object) method. (Using the provided factory's
     * PoolableObjectFactory.validateObject(java.lang.Object) method.) Objects that fail to
     * validate will be dropped from the pool, and a different object will be borrowed.
     **/
    public static final String SOCKETPOOL_TEST_ON_BORROW_KEY = "socketpool.testOnBorrow";
    /**
     * When testOnReturn is set, the pool will attempt to validate each object before it is
     * returned to the pool in the returnObject(java.lang.Object, java.lang.Object) method.
     * (Using the provided factory's PoolableObjectFactory.validateObject(java.lang.Object)
     * method.) Objects that fail to validate will be dropped from the pool.
     **/
    public static final String SOCKETPOOL_TEST_ON_RETURN_KEY = "socketpool.testOnReturn";
    /**
     * whenExhaustedAction specifies the behaviour of the borrowObject(java.lang.Object)
     * method when the pool is exhausted:
     * <br>When whenExhaustedAction is WHEN_EXHAUSTED_FAIL, borrowObject(java.lang.Object) will
     * throw a NoSuchElementException
     * <br>When whenExhaustedAction is WHEN_EXHAUSTED_GROW, borrowObject(java.lang.Object) will
     * block (invoke Object.wait(long) until a new or idle object is available. If a positive
     * maxWait value is supplied, the borrowObject(java.lang.Object) will block for at most that
     * many milliseconds, after which a NoSuchElementException will be thrown. If maxWait is
     * non-positive, the borrowObject(java.lang.Object) method will block indefinitely.
     * <br><br>valid values fail, grow, block
     **/
    public static final String SOCKETPOOL_WHEN_EXHAUSTED_ACTION_KEY = "socketpool.whenExhaustedAction";
    /**
     * Optionally, one may configure the pool to examine and possibly evict objects as they
     * sit idle in the pool. This is performed by an "idle object eviction" thread, which runs
     * asynchronously. The idle object eviction thread may be configured using the following attributes:
     **/
    public static final String SOCKETPOOL_TEST_WHILE_IDLE_KEY = "socketpool.testWhileIdle";
    /**
     * minEvictableIdleTimeMillis specifies the minimum amount of time that an object may sit idle in the
     * pool before it is eligable for eviction due to idle time. When non-positive, no object will be
     * dropped from the pool due to idle time alone.
     **/
    public static final String SOCKETPOOL_MIN_EVICTABLEIDLE_TIME_MILLIS_KEY = "socketpool.minEvictableIdleTimeMillis";
    /**
     * timeBetweenEvictionRunsMillis indicates how long the eviction thread should sleep before "runs"
     * of examining idle objects. When non-positive, no eviction thread will be launched.
     **/
    public static final String SOCKETPOOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS_KEY = "socketpool.timeBetweenEvictionRunsMillis";
    /**
     * Time in milliseconds a socket will block before timing out
     **/
    public static final String SOCKET_TIMEOUT_KEY = "socket.timeout";
    public static final int DEFAULT_SOCKET_TIMEOUT_MILLIS = 180000;

    /*public static Hashtable<String, String> getUserMap() {
        
        Hashtable<String, String> map =  new Hashtable<String, String>();
        String users = PropertiesUtil.getNabsProperty(USER_POOL_USERS_KEY);  
//        
//        users = "WOM8001 IMSWEB,WOM8002 IMSWEB,WOM8003 IMSWEB,WOM8004 IMSWEB,WOM8005 IMSWEB,WOM8006 IMSWEB,WOM8007 IMSWEB";
//        
        StringTokenizer st = new StringTokenizer(users, ",");
        while (st.hasMoreTokens()) 
        {
            String thisUser = st.nextToken();
            int delimit = thisUser.indexOf(" ");
            if ((delimit > 0) &&
                (delimit < thisUser.length()-1))
            {
                map.put(thisUser.substring(0, delimit), 
                        thisUser.substring(delimit+1));
            }
        }
        
        return map;
    }*/
    
    public static List<NabsIMSUser> getNABSIMSUsersList(){
    	List<NabsIMSUser> nabsIMSUsersList = new ArrayList<NabsIMSUser>();
    	String users = PropertiesUtil.getNabsProperty(USER_POOL_USERS_KEY);
    	StringTokenizer st = new StringTokenizer(users, ",");
        
    	while (st.hasMoreTokens()){
            String thisUser = st.nextToken();
            int delimit = thisUser.indexOf(" ");
            if ((delimit > 0) && (delimit < thisUser.length()-1)){
            	String imsUserId = thisUser.substring(0, delimit).trim();
            	String imsPassword = thisUser.substring(delimit+1).trim(); 
                NabsIMSUser nabsIMSUser = new NabsIMSUser(imsUserId, imsPassword);
                nabsIMSUsersList.add(nabsIMSUser);        
            }
        }
    	return nabsIMSUsersList;
    }


    public static String getDataStore() {
        return PropertiesUtil.getNabsProperty(IMS_DATASTORE_KEY);  
    }
    
    public static String getPort() {
        return PropertiesUtil.getNabsProperty(IMS_PORT_KEY);  
    }
    
    
    public static String getHostname() {
        return PropertiesUtil.getNabsProperty(IMS_HOSTNAME_KEY);  
    }
    
    public static String getGroup() {
        return PropertiesUtil.getNabsProperty(IMS_GROUP_KEY);  
    }
    
    public static boolean isWom6Mode() {
        return true;
    }
    
    public static int getMaxActive() {
        //  This appears to be hard coded to 0 all the time but I'll put this method here 
        // in case this needs to be configurable in the future.
        // SOCKETPOOL_MAXACTIVE
        return 0;
    }
    
    public static boolean getTestOnReturn() {
        //  This appears to be hard coded to true all the time but I'll put this method here 
        // in case this needs to be configurable in the future
        // SOCKETPOOL_TEST_ON_RETURN
        return true;
    }
    
    
    public static int getMaxIdle() {
         return getIntProperty(SOCKETPOOL_MAXIDLE_KEY, GenericKeyedObjectPool.DEFAULT_MAX_IDLE);
    }

    public static long getMaxWait() {
        return getLongProperty(SOCKETPOOL_MAXWAIT_KEY, GenericKeyedObjectPool.DEFAULT_MAX_WAIT);
    }
    
    public static long getMinEvictableIdelTime() {
        return getLongProperty(SOCKETPOOL_MIN_EVICTABLEIDLE_TIME_MILLIS_KEY, 
                GenericKeyedObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
    }

    public static int getNumTestsPerEvictionRun() {
        return getIntProperty(SOCKETPOOL_NUM_TESTS_PER_EVICTION_RUN_KEY, 
                                GenericKeyedObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
    }
    
   public static boolean getTestOnBorrow() {
        return getBooleanProperty(SOCKETPOOL_TEST_ON_BORROW_KEY, 
                                    GenericKeyedObjectPool.DEFAULT_TEST_ON_BORROW);
    }
    
    public static boolean getTestWhileIdle() {
        return getBooleanProperty(SOCKETPOOL_TEST_WHILE_IDLE_KEY, 
                                    GenericKeyedObjectPool.DEFAULT_TEST_WHILE_IDLE);
    }
    
    public static long getTimeBetweenEvictionRuns() {
        return getLongProperty(SOCKETPOOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS_KEY, 
                                GenericKeyedObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
    }     
    
    public static byte getWhenExhaustedAction() {
        return GenericKeyedObjectPool.WHEN_EXHAUSTED_GROW;
    } 
    
    public static int getSocketTimeout() {
        return getIntProperty(SOCKET_TIMEOUT_KEY, DEFAULT_SOCKET_TIMEOUT_MILLIS);
    } 
    /**
     * Some basic stuff to do boolean processing.  If this gets to be needed elsewhere, should probably 
     * go to the PropertiesUtil
     */
    private static boolean getBooleanProperty(String key, boolean defaultVal) {
        String valStr = PropertiesUtil.getNabsProperty(key);
        if (valStr == null) return defaultVal;
        return ("true".equalsIgnoreCase(valStr)||"yes".equalsIgnoreCase(valStr));
    }

    /**
     * Some basic stuff to do int processing.  If this gets to be needed elsewhere, should probably 
     * go to the PropertiesUtil
     */
    private static int getIntProperty(String key, int defaultVal) {
        try {
            String valStr = PropertiesUtil.getNabsProperty(key);
            if (valStr == null) return defaultVal;
            return Integer.parseInt(valStr);
        } 
        catch (NumberFormatException e) {}
        return defaultVal;
    }

    /**
     * Some basic stuff to do long processing.  If this gets to be needed elsewhere, should probably 
     * go to the PropertiesUtil
     */
    private static long getLongProperty(String key, long defaultVal) {
        try {
            String valStr = PropertiesUtil.getNabsProperty(key);
            if (valStr == null) return defaultVal;
            return Long.parseLong(valStr);
        } 
        catch (Throwable th) {}
        return defaultVal;
    }
        
   

}
