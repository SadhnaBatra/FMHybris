package com.fmo.wom.transformation.util;


public class TransformationConstants {
	public static enum IpoVersion{        
		VERSION1_2(TransformationConstants.VERSION1_2,TransformationConstants.NS_OPENAPPLICATION_OAGIS,TransformationConstants.NS_AFTERMARKET_OAGIS)
		,VERSION2_0(TransformationConstants.VERSION2_0,TransformationConstants.NS_IPOV2,TransformationConstants.NS_IPOV2_COMMON);        
		private String value;
		private String defaultNs;
		private String aaiaNs;
		private IpoVersion(String value,String defaultNs,String aaiaNs){                
			this.value = value;
			this.defaultNs = defaultNs;
			this.aaiaNs = aaiaNs;
		}
		public String getValue(){
			return value;
		}
		public String getDefaultNs() {
			return defaultNs;
		}
		public String getAaiaNs() {
			return aaiaNs;
		}
		
	};
	
	public static enum XSL_ROOT_FOLDER{RFQ,AQ,CONFIRM, INCOMING, OUTGOING,SCHEMAS,GETSHIPMENT,SHOWSHIPMENT};
	public static enum XSL_FILENAME{RFQRequest,AQResponse,AqTemplate,PORequest, APOResponse,GetShipmentRequest,ShowShipmentResponse};
	public static final String UNDERSCORE_STR="_";
	public static final String BLANK_STR="";
	public static final String XSL_FILE_EXTENSION=".xsl";
	public static final String XML_FILE_EXTENSION=".xml";
	public static final String VERSION1_2="1.2";
	public static final String VERSION2_0="2.0";
	public static final String NS_OPENAPPLICATION_OAGIS="http://www.openapplications.org/oagis";
	public static final String NS_AFTERMARKET_OAGIS="http://www.aftermarket.org/oagis";
	public static final String NS_IPOV2="http://www.aaiasoa.net/IPOv2";
	public static final String NS_IPOV2_COMMON="http://www.aaiasoa.net/IPOv2/Common";
	public static final String DOMAIN_ROOT_PACKAGE="com.fmo.wom.domain";
	public static final String FILE_PATH_SEPERATOR="/";//File.separator causing issues in transformation
	public static final String IPOVERSION_XSLFILE="IpoVersion.xsl"; 
	public static final String SECRET_KEY_XSLFILE="SecretKey.xsl";
	public static final String BILL_TO_ACCOUNT_CODE_XSLFILE="BillToAccountCode.xsl";

}
