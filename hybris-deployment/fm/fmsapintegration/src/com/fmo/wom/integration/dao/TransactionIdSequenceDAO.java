package com.fmo.wom.integration.dao;




public class TransactionIdSequenceDAO {

	private static int transactionIdFiledSize = 8;
	
	/*private OracleSequenceMaxValueIncrementer db2TransIdSequenceIncrementer;

    public OracleSequenceMaxValueIncrementer getDb2TransIdSequenceIncrementer() {
        return db2TransIdSequenceIncrementer;
    }

    public void setDb2TransIdSequenceIncrementer(
            OracleSequenceMaxValueIncrementer db2TransIdSequenceIncrementer) {
        this.db2TransIdSequenceIncrementer = db2TransIdSequenceIncrementer;
    }
    
    public int nextVal() {
        return getDb2TransIdSequenceIncrementer().nextIntValue();
    }*/
    
    public int nextVal() {
    	
    	String str = ""+System.nanoTime();
    	int retValue;
    	if(str.length()>transactionIdFiledSize){
    		//Extract the last nine digits.
    		int beginIndex = (str.length() - transactionIdFiledSize);
    		String _9digit = str.substring(beginIndex);
    		retValue = Integer.valueOf(_9digit).intValue()+1;
    	} else {
			retValue = Integer.valueOf(str).intValue();
		}
        return retValue;

        /* START : Read from Oracle Sequence 
         * Rem : Change below line 
         * Nabsconstants.java - public static final String HYBRIS_TRANSACTION_IDENTIFIER = "";
         * */
        
/*        String sql = "SELECT SEQ_DB2_TRANS_SEQ_ID.NEXTVAL FROM DUAL";

    	int sequenceVal = 0;
    	EntityManager em = null;
    	try{
    		em = OracleConnectionUtil.getEntityManager();
    		em.getTransaction().begin();
    		Query sequenceQuery = em.createNativeQuery(sql);
    		java.math.BigDecimal value = (java.math.BigDecimal)sequenceQuery.getSingleResult();
    		sequenceVal = value.intValue();
    		em.getTransaction().commit();
    	} finally{
    		if(em != null && em.isOpen()){
    			em.close();
    			em = null;
    		}
		}
    	return sequenceVal;*/
    	/* END : Read from Oracle Sequesnce */
    }

    /*public static void main(String[] args) {
    	
    	final List<Runnable> runnablesList = new ArrayList<Runnable>();
    	for(int i=0;i<=10;i++){
	    	Runnable r = new Runnable() {
				@Override
				public void run() {
					TransactionIdSequenceDAO dao = new TransactionIdSequenceDAO();
			    	try {
						System.out.println(dao.nextVal());
					} catch (Exception e) {
						e.printStackTrace();
					} 				
				}
			};
			runnablesList.add(r);
    	}
    	
    	for(Runnable r:runnablesList){
    		new Thread(r).start();
    	}
	}*/
    
}
