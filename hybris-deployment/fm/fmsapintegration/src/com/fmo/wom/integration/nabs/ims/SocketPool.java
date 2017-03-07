package com.fmo.wom.integration.nabs.ims;

import java.net.Socket;

import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.log4j.Logger;




import com.fmo.wom.integration.nabs.NabsPropertiesUtil;

public class SocketPool {
	
	private GenericKeyedObjectPool pool = null;
	private static SocketPool me = null;
	private static SocketPoolKey NABS_SOCKET_POOL = new SocketPoolKey("localhost", 4444);

	private static Logger logger = Logger.getLogger(SocketPool.class);
	 
	 /** Creates a new instance of ConnectionPool */
    private SocketPool() {
        super();
        //createPool();
    }
	        
    /** get instance of SocketPool */
    public static SocketPool getInstance() {
        if (me == null) {
        	synchronized (SocketPool.class) {
        		if(me == null){
        			logger.info(" START SocketPool getInstance() ");
        			me = new SocketPool();
                    //Props props = Props.getInstance();
                    String host = NabsPropertiesUtil.getHostname();
                    String port = NabsPropertiesUtil.getPort();
                    try {
                        NABS_SOCKET_POOL = new SocketPoolKey(host, Integer.parseInt(port));
                    }
                    catch (Throwable th) {
                        logger.error("Unable to create NABS_SOCKET_POOL:"+th);
                    }
                    logger.info(" END SocketPool getInstance() ");
        		}
			}
        }
        return me;
    }
    
    /**
     * Creates the socket factory and the socket pool
     *
     */
    /*private void createPool() {
    	logger.info("Creating NABS scoket pool");
        pool = new GenericKeyedObjectPool(new SocketPool.SocketFactory());
        pool.setMaxActive(NabsPropertiesUtil.getMaxActive());
        pool.setMaxIdle(NabsPropertiesUtil.getMaxIdle());
        pool.setMaxWait(NabsPropertiesUtil.getMaxWait());
        pool.setMinEvictableIdleTimeMillis(NabsPropertiesUtil.getMinEvictableIdelTime());
        pool.setNumTestsPerEvictionRun(NabsPropertiesUtil.getNumTestsPerEvictionRun());
        pool.setTestOnBorrow(NabsPropertiesUtil.getTestOnBorrow());
        pool.setTestOnReturn(NabsPropertiesUtil.getTestOnReturn());
        pool.setTestWhileIdle(NabsPropertiesUtil.getTestWhileIdle());
        pool.setTimeBetweenEvictionRunsMillis(NabsPropertiesUtil.getTimeBetweenEvictionRuns());
        pool.setWhenExhaustedAction(NabsPropertiesUtil.getWhenExhaustedAction());
    }*/
    
    /**
     * Returns a Socket from the pool
     * 
     * @param   key into the pool
     * @param   a <code>com.fmo.util.SocketPoolKey</code> object
     * @return  a <code>java.net.Socket</code> object
     * @throws  a <code>java.lang.Exception</code> object
     * @exception   Exception
     */
    public Socket getSocket(SocketPoolKey key) throws Exception {
    	Socket socket = (Socket) pool.borrowObject(key);
    	logger.info("got socket from pool "+socket);
        return socket;
    }
    
    /**
     * Returns a Socket to the pool
     * 
     * @param   key ?a? <code>com.fmo.util.SocketPoolKey</code> object
     * @param   aSocket ?a? <code>java.net.Socket</code> object
     * @return  none
     * @throws  a <code>java.lang.Exception</code> object
     * @exception   Exception
     */
    public void returnSocket(SocketPoolKey key, Socket aSocket) throws Exception {
    	logger.info("returning socket to pool "+aSocket);
        pool.returnObject(key, aSocket);
    }
    /**
     * This function is shutting down the whole connection pool
     * 
     * @exception   Exception
     */
    public void shutdown() throws Exception {
        pool.close();
    }
    
    /**
     * get the number of active sockets for this key
     * 
     * @param   key
     */
    public int getNumActive(SocketPoolKey key) {
        return pool.getNumActive(key);
    }
    /**
     * get the number of idle sockets for this key
     * 
     * @param   key
     */
    public int getNumIdle(SocketPoolKey key) {
        return pool.getNumIdle(key);
    }
    public class SocketFactory implements KeyedPoolableObjectFactory 
    {

        /** Creates a new instance of SocketFactory */
        public SocketFactory(){}

        /**
         * Create an instance that can be served by the pool.
         * @param key the key used when constructing the object
         * @return an instance that can be served by the pool.
         */
        public Object makeObject(Object key) throws Exception {
            SocketPoolKey aSocketPoolKey = (SocketPoolKey)key;
            Socket aSocket;
            aSocket = new Socket(aSocketPoolKey.getHostName(), aSocketPoolKey.getPortNumber());
            aSocket.setSoTimeout(NabsPropertiesUtil.getSocketTimeout());
            aSocket.setKeepAlive(true);
            logger.info("creating NABS socket "+aSocket);
            return aSocket;
        }

        /**
         * Destroy an instance no longer needed by the pool.
         * @param key the key used when selecting the instance
         * @param obj the instance to be destroyed
         */
        public void destroyObject(Object key, Object obj) throws Exception {
        	
            if(obj == null) return; //shortcut
            logger.info("Closing NABS socket .. "+obj);
            Socket socket = (Socket)obj;
            socket.close();
        }

        /**
         * Ensures that the instance is safe to be returned by the pool.
         * Returns <tt>false</tt> if this instance should be destroyed.
         * @param key the key used when selecting the object
         * @param obj the instance to be validated
         * @return <tt>false</tt> if this <i>obj</i> is not valid and should
         *         be dropped from the pool, <tt>true</tt> otherwise.
         */
        public boolean validateObject(Object key, Object obj) {
        	return true;
        }

        /**
         * Reinitialize an instance to be returned by the pool.
         * @param key the key used when selecting the object
         * @param obj the instance to be activated
         */
        public void activateObject(Object key, Object obj) throws Exception {
        }
        
        /**
         * Passivate an instance when returned to the pool.
         * @param key the key used when selecting the object
         * @param obj the instance to be activated
         */
         public void passivateObject(Object key, Object obj) throws Exception {
         }
    }

}
