package com.fmo.wom.integration.nabs.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DB2ConnectionUtil {
	
	private static EntityManagerFactory emFactory = null;
	
	static{
		emFactory = Persistence.createEntityManagerFactory("db2");
	}
	
	public static EntityManager getEntityManager(){
		return emFactory.createEntityManager();
	}

}
