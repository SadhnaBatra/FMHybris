package com.fmo.wom.integration.nabs.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.fmo.wom.domain.nabs.ProblemPartBO;
import com.fmo.wom.integration.dao.nabs.ProblemPartDAO;
//org.hibernate.ejb.AbstractEntityManagerImpl
//SessionImpl
public class TestDB2Connectivity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestDB2Connectivity test = new TestDB2Connectivity();
		test.testDatabaseConnectivity();
	}
	
	public int testDatabaseConnectivity(){
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("db2");
		EntityManager entityManager = emFactory.createEntityManager();
		ProblemPartDAO problemPartDAO = new ProblemPartDAO();
		//problemPartDAO.setEntityManager(entityManager);
		List<ProblemPartBO> problemPartList = problemPartDAO.findAll();
		int size = -1;
		if(problemPartList != null){
			size = problemPartList.size();
		}
		return size;
	}
}
