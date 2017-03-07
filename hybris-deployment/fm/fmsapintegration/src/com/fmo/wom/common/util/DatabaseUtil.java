package com.fmo.wom.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

import com.ibm.db2.jcc.DB2SimpleDataSource;

public class DatabaseUtil {

	private static String ORACLE_DB_URL 		= "oracle.db.url";
	private static String ORACLE_DB_USERNAME	= "oracle.db.username";
	private static String ORACLE_DB_PASSWORD 	="oracle.db.password";		
	
	private static String DB2_DB_USERNAME		= "db2.db.username";
	private static String DB2_DB_PASSWORD 		="db2.db.password";
	private static String DB2_DB_SERVER_NAME    = "db2.db.servername";
	private static String DB2_DB_PORT_NUMBER	= "db2.db.portnumber";
	private static String DB2_DB_DATABASE_NAME  = "db2.db.databasename";
	
	private static Logger logger = Logger.getLogger(DatabaseUtil.class);
	
	private static DatabaseUtil dataBaseUtil = null;
	
	public DatabaseUtil getInstance(){
		if(dataBaseUtil == null){
			dataBaseUtil = new DatabaseUtil();
		}
		return dataBaseUtil;
	}
	
	
	public static void main(String[] args) {
		//Test Oracle Connection
		Properties dbProperties = getDBConnectionProperties();
		
		Connection oracleConnection = null;
		Statement oraclestmt = null;
		ResultSet oraclers = null;
				
		try {
			oracleConnection = getOracleDataSource(dbProperties).getConnection();
			oraclestmt = oracleConnection.createStatement();
			oraclers = oraclestmt.executeQuery("select count(*) as count from property");
			while(oraclers.next()){
				int count = oraclers.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(oraclers != null) oraclers.close(); 
				if(oraclestmt!=null) oraclestmt.close();
				if(oracleConnection!=null)oracleConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		//Test DB2 Connection
		Connection db2Connection = null;
		Statement db2stmt = null;
		ResultSet db2rs = null;
		try {
			db2Connection = getDB2DataSource(dbProperties).getConnection(dbProperties.getProperty(DB2_DB_USERNAME), dbProperties.getProperty(DB2_DB_PASSWORD));
		} catch (SQLException e) {
			logger.error("", e);
		}
	}
	
	
	private static Properties getDBConnectionProperties(){
		Properties dbProperties = new Properties();
		URL clsLoader = DatabaseUtil.class.getClassLoader().getResource("db.properties");
		String dbPropertiesPath = clsLoader.getPath();
		
		try {
			dbProperties.load(new FileReader(new File(dbPropertiesPath)));
		} catch (FileNotFoundException fnfe) {
			logger.error("db.properties file not found ",fnfe);
		} catch (IOException ioe) {
			logger.error("IO Exception while reading from db.properties file ",ioe);
		}
		return dbProperties;
	}
	
	/**
	 * Refer to http://docs.oracle.com/cd/E19798-01/821-1751/beanh/index.html
	 * OracleDataSource.java
	 */
		
	private static DataSource getOracleDataSource(Properties aDbProperties){
		OracleDataSource oracleDataSource =  null;
		try {
			oracleDataSource = new OracleDataSource();
			oracleDataSource.setUser(aDbProperties.getProperty(ORACLE_DB_USERNAME));
			oracleDataSource.setPassword(aDbProperties.getProperty(ORACLE_DB_PASSWORD));
			oracleDataSource.setURL(aDbProperties.getProperty(ORACLE_DB_URL));
		} catch (SQLException sqle) {
			logger.error("SQLException while creating OracleDataSource ",sqle);
		} catch (Exception e) {
			logger.error("Exception while creating OracleDataSource ",e);
		}
		return oracleDataSource;
	}
	
	private static DataSource getDB2DataSource(Properties aDbProperties){
		DB2SimpleDataSource db2DataSource = null;
		db2DataSource = new DB2SimpleDataSource();
		db2DataSource.setServerName(aDbProperties.getProperty(DB2_DB_SERVER_NAME));
		db2DataSource.setPortNumber(Integer.valueOf(aDbProperties.getProperty(DB2_DB_PORT_NUMBER)).intValue());
		db2DataSource.setDatabaseName(aDbProperties.getProperty(DB2_DB_DATABASE_NAME));
		db2DataSource.setDriverType(4);
		return db2DataSource;
	}

}
