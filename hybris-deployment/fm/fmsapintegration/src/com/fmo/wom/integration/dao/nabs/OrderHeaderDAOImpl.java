package com.fmo.wom.integration.dao.nabs;


import com.fmo.wom.common.dao.JpaDAODB2;
import com.fmo.wom.domain.nabs.OrderHeaderBO;
import com.fmo.wom.domain.nabs.OrderHeaderPK;

public class OrderHeaderDAOImpl extends JpaDAODB2<OrderHeaderPK, OrderHeaderBO>  implements OrderHeaderDAO {

    /*private static Logger logger = Logger.getLogger(OrderHeaderDAOImpl.class);*/

    public OrderHeaderDAOImpl() {
		super();
		//setEntityManager(DB2ConnectionUtil.getEntityManager());
	}

	/*public String findErrorMessage(String masterOrderNumber) throws WOMNonUniqueResultException, WOMNoResultException {
        
        logger.debug("Getting error code for master order number: "+masterOrderNumber);
        EntityManager anEntityManager = getEntityManager();
        Query query = anEntityManager.createNamedQuery("findErrorMessage");
        query.setParameter("masterOrderNumber", masterOrderNumber);
        anEntityManager.getTransaction().begin();
        String errorMessage = (String) getSingleResult(query);
        anEntityManager.getTransaction().commit();
        logger.debug("Found error message "+ errorMessage);
        return errorMessage;
    }*/
    
    @Override
    public OrderHeaderBO findByMasterOrderNumber(String masterOrderNumber) {
    	if (masterOrderNumber == null) return null;
        OrderHeaderPK aPK = new OrderHeaderPK();
        aPK.setMasterOrderNumber(masterOrderNumber);
        return findById(aPK);
    }
    
    //@Transactional(propagation=Propagation.REQUIRES_NEW)
    /*public void storeAutoCommit(OrderHeaderBO entity) {
        this.merge(entity);
    }*/
    
}
