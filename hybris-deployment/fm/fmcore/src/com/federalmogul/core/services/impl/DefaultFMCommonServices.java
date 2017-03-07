/**
 * 
 */
package com.federalmogul.core.services.impl;

import de.hybris.platform.acceleratorservices.payment.PaymentService;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.dao.FMCommonDAO;
import com.federalmogul.core.model.BrandInformationModel;
import com.federalmogul.core.services.FMCommonServices;


/**
 * @author SU276498
 * 
 */
public class DefaultFMCommonServices implements FMCommonServices
{

	private FMCommonDAO fmCommonDAO;

	/**
	 * @return the fmCommonDAO
	 */
	public FMCommonDAO getFmCommonDAO()
	{
		return fmCommonDAO;
	}

	/**
	 * @param fmCommonDAO
	 *           the fmCommonDAO to set
	 */
	public void setFmCommonDAO(final FMCommonDAO fmCommonDAO)
	{
		this.fmCommonDAO = fmCommonDAO;
	}

	@Autowired
	private ModelService modelService;

	@Autowired
	private PaymentService paymentService;

	@Override
	public List<RegionModel> getStateForCountry(final String country)
	{
		final List<RegionModel> states = fmCommonDAO.getStateForCountry(country);
		return states;
	}

	@Override
	public List<CustomerModel> getCustomerForEmailId(final String email)
	{
		final List<CustomerModel> customerModel = fmCommonDAO.getCustomerForEmailId(email);
		return customerModel;
	}


	/**
	 * 
	 */
	@Override
	public Map<String, Object> modifyOrderStatus(final List<PaymentTransactionModel> transactions,
			final Map<String, String> requestMap)
	{

		final Map<String, Object> modificationDetailsResult = new HashMap<String, Object>();
		final List<OrderModel> modifiedOrdersWithSubscriptionId = new ArrayList<OrderModel>();
		final List<OrderModel> modifiedOrdersWithoutSubscriptionId = new ArrayList<OrderModel>();
		try
		{
			for (int index = 0; index < transactions.size(); index++)
			{
				final String newDecision = requestMap.get(transactions.get(index).getRequestId());

				final OrderModel currentOrder = (OrderModel) transactions.get(index).getOrder();
				//final PaymentTransactionModel currentTransaction = transactions.get(index);
				if (currentOrder == null)
				{

					continue;
				}
				if ("ACCEPT".equalsIgnoreCase(newDecision) && "INREVIEW".equals(currentOrder.getStatus()))
				{
					//TO DO
					//currentOrder.setStatus("SUBMITTED");
					//  
					try
					{
						/*
						 * final NewSubscription subscriptonResult =
						 * getPaymentService().createSubscription(currentTransaction, currentOrder.getPaymentAddress(), null);
						 * if (subscriptonResult.getSubscriptionID() == null) {
						 * modifiedOrdersWithoutSubscriptionId.add(currentOrder); } else {
						 * modifiedOrdersWithSubscriptionId.add(currentOrder); }
						 */
					}
					catch (final Exception e)
					{
						modifiedOrdersWithoutSubscriptionId.add(currentOrder);
					}
				}
				else if ("REJECT".equalsIgnoreCase(newDecision) && "INREVIEW".equals(currentOrder.getStatus()))
				{
					//TO DO
					//currentOrder.setStatus(OrderStatus.CANCELLED);
					modifiedOrdersWithoutSubscriptionId.add(currentOrder);
				}

			}
			modelService.saveAll(modifiedOrdersWithSubscriptionId);
			modelService.saveAll(modifiedOrdersWithoutSubscriptionId);

			modificationDetailsResult.put("OrdersWithSubscriptionId", modifiedOrdersWithSubscriptionId);
			modificationDetailsResult.put("OrdersWithoutSubscriptionId", modifiedOrdersWithoutSubscriptionId);

			return modificationDetailsResult;
		}
		catch (final Exception e)
		{
			modificationDetailsResult.put("errors", e);
			return modificationDetailsResult;
		}
	}


	/**
	 * @return the paymentService
	 */
	public PaymentService getPaymentService()
	{
		return paymentService;
	}

	/**
	 * @param paymentService
	 *           the paymentService to set
	 */
	public void setPaymentService(final PaymentService paymentService)
	{
		this.paymentService = paymentService;
	}

	@Override
	public List<BrandInformationModel> getBrandInformationByName(String brand) {
		// TODO Auto-generated method stub
		return fmCommonDAO.getBrandInformationByName(brand);
	}



}
