/**
 * 
 */
package com.federalmogul.facades.account.impl;

import de.hybris.platform.commercefacades.order.converters.populator.AbstractOrderPopulator;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import com.federalmogul.facades.account.FMCustomerAccountReturnOrderFacade;
import com.federalmogul.facades.order.data.FMReturnItemsData;
import com.federalmogul.facades.order.data.FMReturnOrderData;
import com.fm.falcon.webservices.dto.ReturnItemsDTO;
import com.fm.falcon.webservices.dto.ReturnsRequestDTO;
import com.fm.falcon.webservices.dto.ReturnsResponseDTO;
import com.fm.falcon.webservices.soap.helper.ReturnsHelper;


/**
 * @author Balaji
 * 
 */
public class FMCustomerAccountReturnOrderFacadeImpl extends AbstractOrderPopulator<OrderModel, OrderData> implements
		FMCustomerAccountReturnOrderFacade
{

	/*
	 * @Resource private UserService userService;
	 * 
	 * @Resource(name = "customerData") CustomerData customerData = new CustomerData();
	 * 
	 * @Autowired private UserService userservice;
	 */

	private static final Logger LOG = Logger.getLogger(FMLeadGenerationCallBackFacadeImpl.class);

	final static HashMap RETURNTYPES = new HashMap();

	@Override
	public FMReturnOrderData createReturn(final FMReturnOrderData fmReturnOrderData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException
	{
		//validateParameterNotNullStandardMessage("fmReturnOrderData", fmReturnOrderData);
		//Assert.hasText(fmReturnOrderData.getFirstName(), "The field [FirstName] cannot be empty");
		//Assert.hasText(fmReturnOrderData.getLastName(), "The field [LastName] cannot be empty");

		final FMReturnOrderData fMReturnOrderData = createCrmReturnOrder(fmReturnOrderData);

		return fMReturnOrderData;

	}


	/**
	 * User defined method for saving the response Return ID in model which returns the FMReturnItemsModel
	 * 
	 * @return FMCustomerModel
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */

	@Override
	public FMReturnOrderData createCrmReturnOrder(final FMReturnOrderData fmReturnItemsData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException
	{
		//LOG.info("Emntered createCrmReturnOrder In FacadeImpl");

		//final FMReturnItemsModel fmReturnItemsModel = getModelService().create(FMReturnItemsModel.class);
		//final FMReturnOrderModel fMReturnOrderModel = getModelService().create(FMReturnOrderModel.class);
		final ReturnsHelper returnsHelper = new ReturnsHelper();
		ReturnsResponseDTO regResponse = null;

		//validateParameterNotNullStandardMessage("fmReturnItemsData", fmReturnItemsData);
		//Assert.hasText(fmReturnItemsData.getSapAccountCodeData(), "The field [SapAccountCode] cannot be empty");
		//Assert.hasText(fmReturnItemsData.getPurchaseOrderNumber(), "The field [PurchaseOrderNumber] cannot be empty");
		//Assert.hasText(fmReturnItemsData.getAccountNumberData(), "The field [AccountNumber] cannot be empty");
		//Assert.hasText(fmReturnItemsData.getOriginalOrderNumber(), "The field [OriginalOrderNumber] cannot be empty");
		//Assert.hasText(fmReturnItemsData.getReturnReason(), "The field [ReturnReason] cannot be empty");

		//if (fmReturnItemsData.getSapAccountCodeData() != null)
		//{

		final FMReturnOrderData returnOrder = new FMReturnOrderData();
		regResponse = returnsHelper.sendSOAPMessage(convertDataToDTO(fmReturnItemsData));

		if ("000".equals(regResponse.getCode()))
		{
			returnOrder.setReturnId(regResponse.getReturnId());
			returnOrder.setReturnCode(regResponse.getCode());
		}
		else
		{
			returnOrder.setReturnCode(regResponse.getCode());
			//returnOrder.setReturnId("999999999");
			//throw new IOException(regResponse.getResponseCode());
		}
		return returnOrder;
	}




	static
	{
		RETURNTYPES.put("Stock Lift", "ZR02");
		RETURNTYPES.put("Warranty Return", "ZR03");
		RETURNTYPES.put("Warranty w/Labour Claim", "ZR04");
		RETURNTYPES.put("Shipping Discrepancy", "ZR05");

		RETURNTYPES.put("Damaged", "ZR06");

		RETURNTYPES.put("Obsolete", "ZR07");
		RETURNTYPES.put("Return due to Late Delivery", "ZR08");
		RETURNTYPES.put("Warranty Return Destroy in field", "ZR09");
		RETURNTYPES.put("Product Recall", "ZR10");

		RETURNTYPES.put("Return Customer Error", "ZR11");
		RETURNTYPES.put("Customer Service Error", "ZR12");

	}


	private ReturnsRequestDTO convertDataToDTO(final FMReturnOrderData fmReturnOrderData)
	{

		LOG.info("inside convertDataToDTO Method to set the Values in Helper class ");

		final ReturnsRequestDTO reqDTO = new ReturnsRequestDTO();

		//reqDTO.setServiceName("MT_RETURNSREQUEST_HYBRIS_SEND");
		reqDTO.setServiceName("MT_RETURNSREQUEST_HYBRIS_SEND");
		reqDTO.setSapAccountCode(fmReturnOrderData.getBillToAccount());
		LOG.info("Inside convertDataToDTO method  BillToAccount::" + fmReturnOrderData.getBillToAccount());

		reqDTO.setOriginalOrderNumber(fmReturnOrderData.getSalesOrderNumber());
		LOG.info("Inside convertDataToDTO method  SalesOrderNumber:" + fmReturnOrderData.getSalesOrderNumber());

		reqDTO.setAccountNumber(fmReturnOrderData.getShiftToAccount());
		LOG.info("Inside convertDataToDTO method  ShiftToAccount::" + fmReturnOrderData.getShiftToAccount());

		//reqDTO.setPurchaseOrderNumber("WOM8201T10008");

		reqDTO.setReasonOfReturn("" + RETURNTYPES.get(fmReturnOrderData.getReasonReturn()));//AMG6197
		LOG.info("Inside convertDataToDTO method  ReasonReturn::" + fmReturnOrderData.getReasonReturn());
		LOG.info("" + RETURNTYPES.get(fmReturnOrderData.getReasonReturn()));

		reqDTO.setReturnDescription(fmReturnOrderData.getReturnDescription());
		LOG.info("Inside convertDataToDTO method  ReturnDescription::" + fmReturnOrderData.getReturnDescription());

		final List<FMReturnItemsData> fmRtn = fmReturnOrderData.getReturnItems();

		final List<ReturnItemsDTO> retunItems = new ArrayList<ReturnItemsDTO>();

		if (fmRtn != null)
		{
			LOG.info("Inside if (fmRtn != null)" + fmRtn.size());
			final Iterator itr = fmRtn.iterator();
			while (itr.hasNext())
			{
				LOG.info("Inside while (itr.hasNext())" + fmRtn.size());
				final FMReturnItemsData o = (FMReturnItemsData) itr.next();
				final ReturnItemsDTO rIDTO = new ReturnItemsDTO();

				rIDTO.setItemDescription(o.getItemDescription());
				LOG.info("Inside while (itr.hasNext())" + o.getItemDescription());
				rIDTO.setBrandPart(o.getPartNumber());
				LOG.info("Inside while (itr.hasNext())" + o.getPartNumber());
				rIDTO.setReturnqty(o.getReturnqty());
				retunItems.add(rIDTO);
			}
			reqDTO.setReturnItems(retunItems);

		}
		else
		{
			LOG.info("Inside else (fmRtn != null)");
			final ReturnItemsDTO rIDTO = new ReturnItemsDTO();

			rIDTO.setItemDescription("NNT Bearing ");
			rIDTO.setBrandPart("NNT201CC");
			rIDTO.setReturnqty("1");
			rIDTO.setUnit("EAU");
			retunItems.add(rIDTO);

			final ReturnItemsDTO rIDTO1 = new ReturnItemsDTO();

			rIDTO1.setItemDescription("NNT Bearing 1111");
			rIDTO1.setBrandPart("NNT202CC");
			rIDTO1.setReturnqty("2");
			rIDTO1.setUnit("EAU");
			retunItems.add(rIDTO1);

			reqDTO.setReturnItems(retunItems);

		}
		return reqDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final OrderModel source, final OrderData target) throws ConversionException
	{
		// YTODO Auto-generated method stub

	}


}
