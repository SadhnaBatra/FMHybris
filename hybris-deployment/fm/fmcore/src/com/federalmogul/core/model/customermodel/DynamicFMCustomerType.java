package com.federalmogul.core.model.customermodel;

import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMCustomerPartnerFunctionModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.log4j.Logger;
import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.util.FMUtils;

import javax.annotation.Resource;

import java.util.*;

public class DynamicFMCustomerType implements DynamicAttributeHandler<String, FMCustomerModel> {

    private static final Logger LOG = Logger.getLogger(DynamicFMCustomerType.class);

    @Resource
    protected FlexibleSearchService flexibleSearchService;

    @Override
    public String get(final FMCustomerModel model)
    {
        LOG.info("DynamicFMCustomerType..inside getter method");
        final List<String> groupUID = new ArrayList<String>();
        final Set<PrincipalGroupModel> groupss = model.getGroups();
        for (final PrincipalGroupModel groupModel : groupss)
        {
            final String groupId = groupModel.getUid();
            groupUID.add(groupId);
        }

        if (groupUID.contains(FmCoreConstants.FMB2BB)|| groupUID.contains(FmCoreConstants.FMViewOnlyGroup) || groupUID.contains(FmCoreConstants.FMNoInvoiceGroup)) {
            LOG.info("DynamicFMCustomerType generate new value");
            if(null!=model.getFmUnit())
            { 
              return checkSoldToShipto(model.getFmUnit().getUid());
            }
         }
        
        return FmCoreConstants.INVALID;
    }

    @Override
    public void set(final FMCustomerModel model, final String value)
    {
        throw new UnsupportedOperationException();
    }

   
    public String checkSoldToShipto(final String uid)
    {
        LOG.info("DynamicFMCustomerType entering checkSoldToShipto");

        String result = null;
        final StringBuilder query = new StringBuilder("SELECT {").append(FMCustomerPartnerFunctionModel.PK);
        query.append("} FROM {").append(FMCustomerPartnerFunctionModel._TYPECODE);
        query.append("} WHERE {").append(FMCustomerPartnerFunctionModel.CODE);
        query.append("} = ?").append(FMCustomerPartnerFunctionModel.CODE);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(FMCustomerPartnerFunctionModel.CODE, uid + uid);

        final SearchResult<FMCustomerPartnerFunctionModel> queryResult = flexibleSearchService.search(query.toString(), params);
        if (null != queryResult && queryResult.getResult().size() > 0)
        {
            LOG.info("DynamicFMCustomerType SoldTo");
            final List<FMCustomerPartnerFunctionModel> res = queryResult.getResult();
            for (final FMCustomerPartnerFunctionModel pfunc : res)
            {
               result = FmCoreConstants.SOLDTO;
               if(!FMUtils.isPartnerFunctionCodeValid(pfunc.getCode()))
               {	
            		result=FmCoreConstants.INVALID;
            		break;
               } 
             }
         }     
         else{
                LOG.info("DynamicFMCustomerType ShipTo");
                result = FmCoreConstants.SHIPTO;
         }
         LOG.info("DynamicFMCustomerType result was " + result);
         return result;
    }
    
}