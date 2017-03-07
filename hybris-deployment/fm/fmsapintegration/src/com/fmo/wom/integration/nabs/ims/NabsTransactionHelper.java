package com.fmo.wom.integration.nabs.ims;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.integration.nabs.NabsPropertiesUtil;

public class NabsTransactionHelper {
	
	private static Logger logger = Logger.getLogger(NabsTransactionHelper.class);
	
	/***/
    private static NabsTransactionHelper me = new NabsTransactionHelper();

    /**
     * Singleton Pattern
     * 
     */
    public static NabsTransactionHelper getInstance() {

        return me;
    }
    
    /**
     * Calls a NABS transaction with a code and text. The text portion of the response is returned.
     *  Rewritten to be simpler and more explicit error handling
     * @param   transCode
     * @param   inputText
     * @exception   HostSystemException
     */
    public String callNabsTransaction(String transCode, String inputText) throws WOMTransactionException, WOMExternalSystemException {
    	logger.info("NabsTransactionHelper.callNabsTransaction() >> transCode="+ transCode+ ", inputText= "+ inputText);
    	String[] output = callNabsTransactionNoCheck(transCode, inputText);
        checkReceivedText(transCode, output);
        // the output array is good - return the only thing we should have
        return output[0];
    }
    
    /**
     * Calls a NABS transaction with a code and text. The array of response strings is returned
     * @param   transCode
     * @param   inputText
     * @exception   HostSystemException
     */
    public String[] callNabsTransactionNoCheck(String transCode, String inputText) throws WOMTransactionException, WOMExternalSystemException {
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append(".callNabsTransaction(").append(transCode).append(", ").append(inputText).append("): ");

        NabsSocketCommunicator socketCommunicator = new NabsSocketCommunicator();
        String[] output = null;
        long totalMilliSec = 0;
        Socket socket = null;
        
        Date startDate = new Date();
        Date endDate = null;
        try {
           // synchronized (this) {

                endDate = new Date();
                totalMilliSec = endDate.getTime() - startDate.getTime();
                logger.warn(createTimeString("callNabsTransaction: NABS Synchronization wait was ", transCode, totalMilliSec));
                
                startDate = new Date();

                String imsHost = NabsPropertiesUtil.getHostname();
                String imsPort = NabsPropertiesUtil.getPort();
                socket = new Socket(imsHost,Integer.parseInt(imsPort));

                logger.info("NABS communication socket "+socket);
                Date socketEndDate = new Date();
                totalMilliSec = socketEndDate.getTime() - startDate.getTime();
                if (totalMilliSec / 1000 >= 20) {
                    logger.warn(createTimeString("callNabsTransaction: Getting the socket & user took > 20 sec.", transCode, totalMilliSec));
                }
                
                try {
                    socketCommunicator.sendData(socket, transCode, inputText);
                } catch (IOException ioe) {
                    endDate = new Date();
                    totalMilliSec = endDate.getTime() - startDate.getTime();

                    sb.append(createLogString("Unable to send", transCode, totalMilliSec, inputText, ioe.getLocalizedMessage()));
                    String s = new String(sb.toString());
                    throw new WOMTransactionException(s, ioe);
                }
                try {
                    output = socketCommunicator.receiveData(socket);
                    if (null == output || 0 == output.length) {
                        throw new WOMTransactionException("** receiveData returned null **");
                    }
                } catch (IOException ioe) {
                    endDate = new Date();
                    totalMilliSec = endDate.getTime() - startDate.getTime();

                    sb.append(createLogString("IO Exception in receiveData", transCode, totalMilliSec, inputText, ioe.getLocalizedMessage()));
                    String s = new String(sb.toString());
                    throw new WOMTransactionException(s, ioe);
                }
          //  }
            endDate = new Date();
            totalMilliSec = endDate.getTime() - startDate.getTime();
            if (totalMilliSec / 1000 >= 20) {
                logger.warn(createTimeString("Entire Transaction took > 20 sec.", transCode, totalMilliSec, inputText));
            } else {
                if (logger.isDebugEnabled()){  // the next stmt wastes time
                    logger.info(createTimeString("Entire Transaction timing", transCode, totalMilliSec, inputText));
                }
            }
        } catch (Exception e) {
            if (endDate == null) {
                endDate = new Date();
            }
            totalMilliSec = endDate.getTime() - startDate.getTime();

            sb.append(createLogString("General Exception handler - Abort transaction.", transCode, totalMilliSec, inputText, e.getLocalizedMessage())).append("]");
            String s = new String(sb.toString());
            throw new WOMTransactionException(s, e);
        } finally { // Always return these to the pool
            if (null != socket){
            	try {
					socket.close();
				} catch (IOException ioe) {
					logger.error("Error while closing socket to IMS host "+socket, ioe);
				}
            }
                
        }
        
       return output;
    }
    
    private void nabsReturnSocket(Socket socket) {
        // make sure the object is returned to the pool
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append(".nabsReturnSocket()").append("): ");

        if (null != socket) {
            try {
                /*SocketPool.getInstance().returnSocket(SocketPool.NABS_SOCKET_POOL,socket);*/
            } catch (Exception e) { //Error while returning socket
                sb.append("Unable to return Socket to pool: ");
                String s = new String(sb.toString());
                logger.error(s, e);
            }
        }
    }
    
    private Socket nabsGetSocket() throws WOMExternalSystemException {
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append(".nabsGetUser()").append("): ");

        Socket socket = null;
        try {
            /*socket = SocketPool.getInstance().getSocket(SocketPool.NABS_SOCKET_POOL);*/
        } catch (Exception e) {
            sb.append("Unable to get Socket from pool [");
            sb.append(e.getLocalizedMessage()).append("]");
            String s = new String(sb.toString());
            throw new WOMExternalSystemException(s, e);
        }
        return socket;
    }
    
    private String createTimeString(String msg, String transCode, long totalMilliSec) {
        return createTimeString(msg, transCode, totalMilliSec, null);
    }

    private String createTimeString(String msg, String transCode, long totalMilliSec, String inputText) {
        StringBuffer errMsg = new StringBuffer(msg);
        errMsg.append("  TransCode[").append(transCode).append("]");
        errMsg.append(createTimeSB(totalMilliSec));
        if (null != inputText) {
            errMsg.append(" With input text of: [").append(inputText).append("]");
        }
        return errMsg.toString();
    }
    
    private String createLogString(String msg, String transCode, long totalMilliSec, String inputText, String exceptionMessage) {
        StringBuffer errMsg = new StringBuffer(msg);
        errMsg.append(" TransCode [").append(transCode).append("] due to [").append(inputText).append("]");
        errMsg.append(createTimeSB(totalMilliSec));
        if (null != exceptionMessage)
            errMsg.append("\nAborting Transaction. Exception [").append(exceptionMessage).append("]");
        return errMsg.toString();
    }
    
    private StringBuffer createTimeSB(long totalMilliSec) {
        StringBuffer timeString = new StringBuffer();
        if (-1 < totalMilliSec) {
            timeString.append(" Transaction time = ");
            timeString.append(totalMilliSec / 1000).append(" seconds ");
            timeString.append(totalMilliSec % 1000).append(" milliseconds.");
        }
        return timeString;
    }
    
    /**
     * Utility method to ensure that only a single message was returned, as expected.
     **/
    private void checkReceivedText(String caller, String[] outputText) throws WOMTransactionException {
        if (outputText.length != 1){

            String endln = System.getProperty("line.separator");
            StringBuffer errMsg = new StringBuffer();
            errMsg.append(
                    "The call to " + caller +
                    " returned multiple ("+ outputText.length + ") strings.");

            for (int i = 0; i < outputText.length; i++){
                errMsg.append(endln);
                errMsg.append(outputText[i]);
            }

            errMsg.append(endln);
            throw new WOMTransactionException(errMsg.toString());
        }
    }

}
