
package com.fmo.wom.integration.nabs.action.kitcomponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.ComponentBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.OversizeBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.WeightBO;
import com.fmo.wom.domain.enums.CustomerBusinessType;
import com.fmo.wom.domain.enums.KitType;
import com.fmo.wom.domain.nabs.KitCompOversizeDtlBO;
import com.fmo.wom.domain.nabs.KitComponentDtlBO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryBO;
import com.fmo.wom.integration.dao.nabs.KitCompOversizeDtlDAO;
import com.fmo.wom.integration.dao.nabs.KitCompOversizeDtlDAOImpl;
import com.fmo.wom.integration.dao.nabs.KitComponentDtlDAO;
import com.fmo.wom.integration.dao.nabs.KitComponentDtlDAOImpl;
import com.fmo.wom.integration.dao.nabs.PartResolutionDAO;
import com.fmo.wom.integration.dao.nabs.PartResolutionDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;

public class KitComponentUtil {

    private static Logger logger = Logger.getLogger(KitComponentUtil.class);
    
    private PartResolutionDAO partResolutionDAO;
   
    private KitComponentDtlDAO kitComponentDtlDAO; 
    
    private KitCompOversizeDtlDAO kitComponentOversizeDtlDAO;
   
    
    public KitComponentUtil() {
		partResolutionDAO = new PartResolutionDAOImpl();
		kitComponentDtlDAO = new KitComponentDtlDAOImpl();
		kitComponentOversizeDtlDAO = new KitCompOversizeDtlDAOImpl();
	}
 
    /**
     * Load the response data from the NABS output tables and construct the KitBO it
     * represents
     * @return KitBO from NABS kit component detail call
     */
    //@Transactional(propagation=Propagation.REQUIRES_NEW)
    public KitBO loadKitComponentResponseData(int transactionId, String transactionCod) throws WOMTransactionException {

        // first let's load our input record
        List<PartResolutionInventoryBO> inputRecords = partResolutionDAO.findByTransactionId(transactionId, NabsConstants.NABS_KIT_COMPONENTS_TRANS_CODE);
        if (inputRecords == null || inputRecords.size() == 0) {
            throw new WOMTransactionException("Unable to find data for tranId" + transactionId);
        }
        
        if (inputRecords.size() > 1) {
            throw new WOMTransactionException("Too many records found for tranId" + transactionId);
        }
        
        PartResolutionInventoryBO inputBO = inputRecords.get(0);
        KitBO aKit = loadKitInfo(inputBO);
        loadKitComponents(aKit, inputBO);
        return aKit;
    }

    private KitBO loadKitInfo( PartResolutionInventoryBO aPartResolution) {

        // fill out the kit
        KitBO aKit = new KitBO();
        
        aKit.setDisplayPartNumber(trim(aPartResolution.getPartNbr()));
        aKit.setLineNumber(new Integer(aPartResolution.getId().getLineSeqNbr()));
        aKit.setPartNumber(trim(aPartResolution.getPartNbr()));
        aKit.setProductFlag(trim(aPartResolution.getProdFlg()));
        aKit.setBrandState(trim(aPartResolution.getBrandState()));
        aKit.setShortShipAllowed(aPartResolution.isShortShipAllowed());
        aKit.setAddOtherComponentsAllowed(aPartResolution.isAddOtherComponentsAllowed());
        aKit.setEngExpressAllowed(aPartResolution.isEngKitExpress());
        aKit.setItemQty(createKitQuantity(aKit, aPartResolution));
        if (aPartResolution.getNbrOfCategories() == 1) {
            aKit.setType(KitType.PRESELECT);
        } else {
            aKit.setType(KitType.COMPONENT);
        }
        
        return aKit;
    }

    private void loadKitComponents( KitBO theKit, PartResolutionInventoryBO aPartResolution) {

        // first let's load all the kit component details for the input part
        List<KitComponentDtlBO> kitComponentDtls = kitComponentDtlDAO.findByParentPart(aPartResolution.getId());
        
        Map<String, List<ComponentBO>> kitComponentCategoryMap = new HashMap<String, List<ComponentBO>>();
        
        // load all the oversize details for this call and create a map keyed by the component sequence number.
        Map<Integer, List<KitCompOversizeDtlBO>> oversizeDetailMap = createOversizeDetailMap(aPartResolution);
        
        // go through and create kit components for each category
        for (KitComponentDtlBO aKitComponentDtl : kitComponentDtls) {
            
            String category = trim(aKitComponentDtl.getCategory());
            List<ComponentBO> categoryComponents =  kitComponentCategoryMap.get(category);
            if (categoryComponents == null) {
                // didn't find it so create and put in the map
                categoryComponents = new ArrayList<ComponentBO>();
                kitComponentCategoryMap.put(category, categoryComponents);
            }
            
            ComponentBO component = new ComponentBO();
            
            // why do i need to check the part kit type?  WOM6 does it like this.
            if (theKit.getType() == KitType.COMPONENT) {
                component.setComponentType(trim(aKitComponentDtl.getCategory()));
            }
         
            component.setDisplayPartNumber(trim(aKitComponentDtl.getPartNbr()));
            component.setPartNumber(trim(aKitComponentDtl.getPartNbr()));
            component.setProductFlag(trim(aKitComponentDtl.getProdFlg()));
            component.setBrandState(trim(aKitComponentDtl.getBrandState()));
            component.setDescription(trim(aKitComponentDtl.getComments()));
            component.setDefaultQuantity(aKitComponentDtl.getQtyPerAssembly());
            component.setAvailableQuantity(aKitComponentDtl.getAvailableQuantity());
            component.setParentPartNumber(trim(aKitComponentDtl.getParentPartNbr()));
            component.setLineNumber(aKitComponentDtl.getId().getCompSeqNbr());
            
            component.setItemPrice( createPrice(aKitComponentDtl));
            component.setItemQty( createComponentQuantity(theKit, aKitComponentDtl));
            component.setItemWeight( createWeight(aKitComponentDtl));
            
            logger.debug("created kit component:" + component);

            // Now load all the kit component oversize records for this kit component
            List<KitCompOversizeDtlBO> kitComponentOversizeDtls = oversizeDetailMap.get(aKitComponentDtl.getId().getCompSeqNbr());
            
            List<OversizeBO> oversizeList = new ArrayList<OversizeBO>();
            
            // go through and create kit components for each
            if (kitComponentOversizeDtls != null) {
                for (KitCompOversizeDtlBO anOversizeDtl : kitComponentOversizeDtls) {
                    
                    OversizeBO oversize = new OversizeBO();
                    oversize.setSize(trim(anOversizeDtl.getOversizeText()));
                    // should this be higher in our object model?
                    oversize.setMetric(aKitComponentDtl.isMetric());
                    oversize.setDisplayPartNumber(trim(anOversizeDtl.getOversizeCtlgPartNbr()));
                    oversize.setAvailableQuantity(anOversizeDtl.getAvailableQuantity());
                    logger.debug("created kit oversize component:" + oversize);
                    oversizeList.add(oversize);
                }
            }
            component.setOversizeSpecList(oversizeList);
            categoryComponents.add(component);
        }
        theKit.setKitComponentCategoryMap(kitComponentCategoryMap);
    }
        
    
    private Map<Integer, List<KitCompOversizeDtlBO>> createOversizeDetailMap(PartResolutionInventoryBO aPartResolution) {
        Map<Integer, List<KitCompOversizeDtlBO>> oversizeDetailMap  = new HashMap<Integer, List<KitCompOversizeDtlBO>>();
    
        // issue a query to get all oversize details for this part
        List<KitCompOversizeDtlBO> allKitComponentOversizeDtls = kitComponentOversizeDtlDAO.findByParentKit(aPartResolution.getId());
        
        if (allKitComponentOversizeDtls == null || allKitComponentOversizeDtls.size() == 0) {
            return oversizeDetailMap;
        }
        
        // now go through and group by component sequence number
        for (KitCompOversizeDtlBO anOversizeDtl : allKitComponentOversizeDtls) {
            
            Integer componentSequenceNumber = anOversizeDtl.getId().getCompSeqNbr();
            List<KitCompOversizeDtlBO> oversizeDetailListForComponent = oversizeDetailMap.get(componentSequenceNumber);
            if (oversizeDetailListForComponent == null) {
                oversizeDetailListForComponent = new ArrayList<KitCompOversizeDtlBO>();
                oversizeDetailMap.put(componentSequenceNumber, oversizeDetailListForComponent);
            }
            
            oversizeDetailListForComponent.add(anOversizeDtl);
        }
        
        return oversizeDetailMap;
    }

    private WeightBO createWeight(KitComponentDtlBO aKitComponentDtl) {
        WeightBO weight = new WeightBO();
        weight.setWeight(aKitComponentDtl.getPceWeightLbs());      
        return weight;
    }

    private PriceBO createPrice(KitComponentDtlBO aKitComponentDtl) {
        
        PriceBO price = new PriceBO();
        price.setPriceType(CustomerBusinessType.getFromCode(aKitComponentDtl.getPriceTypeCd()));
        price.setDisplayPrice(aKitComponentDtl.getDisplayPrice());
        price.setFreightPrice(aKitComponentDtl.getFrghtNetPrice());
        return price;
    }
    
    private QuantityBO createComponentQuantity(KitBO theKit, KitComponentDtlBO aKitComponentDtl) {
        
        int requestedQuantityForKit = theKit.getItemQty().getRequestedQuantity();
        
        QuantityBO quantity = new QuantityBO();
        quantity.setRequestedQuantity(aKitComponentDtl.getQtyPerAssembly() * requestedQuantityForKit);
        return quantity;
    }
    
    private QuantityBO createKitQuantity(KitBO theKit, PartResolutionInventoryBO aPartResolution) {
        
        QuantityBO quantity = new QuantityBO();
        quantity.setRequestedQuantity((int)aPartResolution.getOrdQty());
        return quantity;
    }

    /**
     * I hate typing StringUtils each time
     * @param in
     * @return
     */
    private String trim(String in) {
        return StringUtils.trim(in);
    }
    

    public void releaseResource(){
    	if(partResolutionDAO != null){
    		partResolutionDAO.releaseEntityManager();
    	}
    	
    	if(kitComponentDtlDAO !=null){
    		kitComponentDtlDAO.releaseEntityManager();
    	}
    	if(kitComponentOversizeDtlDAO !=null){
    		kitComponentOversizeDtlDAO.releaseEntityManager();
    	}
    }
}
    
       
    
    