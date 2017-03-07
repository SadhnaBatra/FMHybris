package com.fmo.wom.integration.nabs.ims;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.common.util.UUID;
import com.fmo.wom.integration.nabs.NabsPropertiesUtil;

public class NabsSocketCommunicator {
    
    private static final byte SYNC_LEVEL_NONE = 0x00;
    private static final byte MODE_SEND_COMMIT_1 = 0x20;
    private static final byte SOCKET_PERSISTENT = 0x10;
    private static final char MODE_NO_REQUEST = ' ';
    private static final String EXIT_SAMPLE = "*SAMPLE*";
    private static final int PREFIX_LENGTH = 80;
    private static final String NABS_IMS_LTERM = "        ";
    private static final String REQUEST_MOD = "*REQMOD*";
    private static final String REQUEST_STATUS = "*REQSTS*";
    private static final String CSMOKEY = "*CSMOKY*";
    private String clientId ="";
    
    private static Logger logger = Logger.getLogger(NabsSocketCommunicator.class);
    
	/**
     * Formats and sends a transaction request across the socket.
     * @throws WOMExternalSystemException 
    */
    public void sendData(Socket socket, String transCode, String inputText)
                   throws IOException, WOMExternalSystemException {
    	long startTime = new Date().getTime();
    	logger.info("Time taken to complete: NabsSocketCommunicator.sendData::Start:"+(new Date().getTime()-startTime));
    	startTime = new Date().getTime();

        NabsIMSUser nabsUser = nabsGetUser();
        // generate unique client id: wom + last 5 digits of time in millisecs
        String uniqueStr = getUniqueNumber();
        clientId = StringUtil.stringPad(uniqueStr,' ',8);

        String sendDataParams =  "transCode=" + transCode + " inputText=" + inputText + " nabsUser="+nabsUser + " clientId=" + clientId;
        logger.info("NABS socket connection details: "+ sendDataParams);

        String dataStore = SocketInputFields.DATASTORE.getPaddedValue(NabsPropertiesUtil.getDataStore()); 
        String username = SocketInputFields.USERNAME.getPaddedValue(nabsUser.getId());
        String password = SocketInputFields.PASSWORD.getPaddedValue(nabsUser.getPassword());
        String group =  SocketInputFields.GROUP.getPaddedValue(NabsPropertiesUtil.getGroup()); 
        String transCodePad = SocketInputFields.TRANSACTION_CODE.getPaddedValue(transCode);
        short recLength;

        
        String dataStoreOld = StringUtil.stringPad("IMS2", ' ', 8);
        String usernameOld = StringUtil.stringPad(nabsUser.getId(), ' ', 8);
        String passwordOld = StringUtil.stringPad(nabsUser.getPassword(), ' ', 8);
        String groupOld = StringUtil.stringPad("IMS2", ' ', 8);
        String transCodePadOld = StringUtil.stringPad(transCode, ' ', 8);
       
        
        
        //figure out how long this message is
        // +4 for first LL, ZZ and +4 for final LL, ZZ
        int totalLength = 4 + PREFIX_LENGTH + 4;

        if (inputText.length() > 0)
        { // have input text plus a length indicator
            totalLength += inputText.length() + 4;
        }

        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        out.writeInt(totalLength); // total message length
        out.writeShort(PREFIX_LENGTH); // LL
        out.writeShort((short)0); // ZZ
        out.writeBytes(EXIT_SAMPLE); // identifier
        out.writeInt(0); // Reserved for future use
        out.writeByte(0); // Input message type
        out.writeByte(0); // time delay
        out.writeByte(SOCKET_PERSISTENT); // socket connection type
        out.writeByte(0); // reserved for future use
        out.writeBytes(clientId); // client id
        out.writeByte(0); // FLG 1
        out.write(MODE_SEND_COMMIT_1); // FLG 2 - commit mode

        //out.write(SYNC_LEVEL_CONFIRM);  // FLG 3 - sync level
        out.write(SYNC_LEVEL_NONE); // FLG 3 - sync level
        out.writeByte(MODE_NO_REQUEST); // FLG 4 - ack/nack/dealloc
        out.writeBytes(transCodePad); // transaction code
        out.writeBytes(dataStore); // datastore id
        out.writeBytes(NABS_IMS_LTERM); // lterm name
        out.writeBytes(username); // RACF id
        out.writeBytes(group); // RACF group
        out.writeBytes(password); // password

        if (inputText.length() > 0)
        {
            recLength = (short)(inputText.length() + 4);
            out.writeShort(recLength); // LL
            out.writeShort((short)0); // ZZ
            out.writeBytes(inputText); // segment
        }

        out.writeShort(4); // LL
        out.writeShort((short)0); // ZZ
        out.flush();
        
        nabsReturnUser(nabsUser);
        logger.info("Time taken to complete: NabsSocketCommunicator.sendData::END:"+(new Date().getTime()-startTime));
		startTime = new Date().getTime();
    }
    
    /**
     * Parses the transaction response from the socket and returns the text portion.
     * 
     * @param   socket
     * @exception   HostSystemException
     * @exception   IOException
     */
    public String[] receiveData(Socket socket)
                          throws WOMTransactionException, IOException
    {
    	long startTime = new Date().getTime();;
    	logger.info("Time taken to complete: NabsSocketCommunicator.receiveData::Start:"+(new Date().getTime()-startTime));
		startTime = new Date().getTime();
        short recLength;
        int totalLength;
        byte[] data = new byte[8];
        String tempID;
        String tempSeg = "";
        ArrayList<String> segments = new ArrayList<String>();

        DataInputStream in = new DataInputStream(socket.getInputStream());
        recLength = in.readShort(); // read total length
        totalLength = (int)recLength; // convert short to int
        short sh = in.readShort(); // read ZZ

            //read id : id length == min of 8 or rec length -4
        if (recLength < 12) {
            byte[] tdata = new byte[recLength - 4];
            in.readFully(tdata);
            tempID = new String(tdata);
        }
        else {
            in.readFully(data); // read identifier
            tempID = new String(data);
        }
        //what to do
        if (REQUEST_MOD.equals(tempID)) {
            in.readFully(data); // read MOD name
        }
        else if (REQUEST_STATUS.equals(tempID)) {

            int returnCode = in.readInt(); // read return code
            int reasonCode = in.readInt(); // read reason code
            throw new WOMTransactionException("Problem in receiveData.  " + 
                                          "Return Code=" + returnCode + 
                                          " Reason Code=" + reasonCode);
        }
        else {
            tempSeg = new String(tempID); //copy the string

            if (totalLength > 12)
            { //data available so add it on

                byte[] buff = new byte[totalLength - 12];
                in.readFully(buff);
                tempSeg += new String(buff);
            }

            segments.add(tempSeg);
        }

        //look for still more records
        tempSeg = "";

        while (true) { // read output text
            recLength = in.readShort(); // read LL
            sh = in.readShort(); // read ZZ

            byte[] buff = new byte[recLength - 4];
            in.readFully(buff); // read segment
            tempSeg = new String(buff);

            if (CSMOKEY.equals(tempSeg)) { // Read end of message

                break;
            }
            else {
                segments.add(tempSeg);
            }
        }

        String[] segmentArray = new String[segments.size()];
        segments.toArray(segmentArray);
        logger.info("Time taken to complete: NabsSocketCommunicator.ReceiveData::End:"+(new Date().getTime()-startTime));
		startTime = new Date().getTime();
        return (segmentArray);
    }
    
    /**
     * Parses the transaction response from the socket and returns the text portion.
     */
    private synchronized String getUniqueNumber() {
    
        return UUID.getUUID();
    }
	
    /**
     * Returns NabsUser from the pool.
     */
    private NabsIMSUser nabsGetUser() throws WOMExternalSystemException {
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append(".nabsGetUser()").append("): ");
        
        NabsIMSUser nabsUser = null;
        try {
            nabsUser = NabsUserPool.getInstance().borrow();
        } catch (Exception e) {
            sb.append("Unable to borrow NabsImsUser from pool [");
            sb.append(e.getLocalizedMessage()).append("]");
            String s = new String(sb.toString());
            throw new WOMExternalSystemException(s, e);
        }
        return nabsUser;
    }
    
    /**
     * Returns NabsUser to the pool.
     */
    private void nabsReturnUser(NabsIMSUser nabsUser) {
        // make sure the object is returned to the pool
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append(".nabsReturnUser()").append("): ");

        if (null != nabsUser) {
            try {
                NabsUserPool.getInstance().returnObject(nabsUser);
            } catch (Exception e) { 
                sb.append("Unable to return NabsImsUser to pool [");
                sb.append(e.getLocalizedMessage()).append("]");
                String s = new String(sb.toString());
                logger.error(s, e);
            }
        }
    }
    
    private enum SocketInputFields
    {
        DATASTORE            (8, ' '),
        USERNAME             (8, ' '),
        PASSWORD             (8, ' '),
        GROUP                (8, ' '),
        TRANSACTION_CODE     (8, ' ');
        
       private int fieldSize;
       private char padder;
       
       private SocketInputFields(int fieldSize, char padder) {
           this.fieldSize = fieldSize;
           this.padder = padder;
       }
       
       public int getFieldSize() {
           return fieldSize;
       }
       
       public char getPadder() {
           return padder;
       }
       
       public String getPaddedValue(String value) {
           return StringUtil.stringPad(value, padder, fieldSize);
       }
     }
	

}
