package com.fmo.wom.integration.dao;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import com.fmo.wom.common.dao.JpaDAOOracle;
import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;
import com.fmo.wom.domain.TpAccountBO;
import com.fmo.wom.domain.TradingPartnerBO;
import com.fmo.wom.model.FMIPOTPACCOUNTModel;
import com.fmo.wom.model.FMIPOTRADINGPARTNERModel;


public class TpAccountDAOImpl extends JpaDAOOracle<Long, TpAccountBO> implements TpAccountDAO
{

	public TpAccountDAOImpl()
	{
		super();
	}

	@Override
	public TpAccountBO findTradingPartnerAccountByAcct(final String accountCode) throws WOMNonUniqueResultException,
			WOMNoResultException
	{
		final Query query = entityManager.createNamedQuery("findByFmBillToAccountCode");
		query.setParameter("fmBillToAcctCd", accountCode);
		final List<Boolean> activeFlags = Arrays.asList(true, false);
		query.setParameter("activeFlags", activeFlags);
		final TpAccountBO tpAcctBO = (TpAccountBO) getSingleResult(query);
		return tpAcctBO;
	}

	@Override
	public TpAccountBO findActiveTradingPartnerAccountByAcct(final String accountCode) throws WOMNonUniqueResultException,
			WOMNoResultException
	{
		final TpAccountBO tpAcctBO = getTradingAccount(accountCode);
		final TradingPartnerBO tradingPartner = getTradingPartner(accountCode);
		final List<TpAccountBO> tpAccounts = new ArrayList<TpAccountBO>();
		tpAccounts.add(tpAcctBO);
		tradingPartner.setTpAccounts(tpAccounts);
		tpAcctBO.setTradingPartner(tradingPartner);
		return tpAcctBO;
	}

	@Override
	public TpAccountBO findInactiveTradingPartnerAccountByAcct(final String accountCode) throws WOMNonUniqueResultException,
			WOMNoResultException
	{
		final Query query = entityManager.createNamedQuery("findByFmBillToAccountCode");
		query.setParameter("fmBillToAcctCd", accountCode);
		final List<Boolean> activeFlags = Arrays.asList(false);
		query.setParameter("activeFlags", activeFlags);
		final TpAccountBO tpAcctBO = (TpAccountBO) getSingleResult(query);
		return tpAcctBO;
	}

	private TpAccountBO getTradingAccount(final String billToAcc) throws WOMNoResultException
	{

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {ta:" + FMIPOTPACCOUNTModel.PK + "} ");
		query.append("FROM  {" + FMIPOTPACCOUNTModel._TYPECODE + " AS ta } ");
		query.append(" WHERE {ta:" + FMIPOTPACCOUNTModel.ACTIVE_FLG + "} = 'Y'");
		query.append(" AND {ta." + FMIPOTPACCOUNTModel.FM_BILLTO_ACCT_CD + "}=?" + FMIPOTPACCOUNTModel.FM_BILLTO_ACCT_CD);
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMIPOTPACCOUNTModel.FM_BILLTO_ACCT_CD, billToAcc);
		final SearchResult<FMIPOTPACCOUNTModel> result = getFlexibleSearchService().search(flexibleSearchQuery);

		if (null != result && result.getResult().size() > 0)
		{
			final FMIPOTPACCOUNTModel tpAccount = result.getResult().get(0);
			final TpAccountBO tpAcctBO = new TpAccountBO();
			tpAcctBO.setTpAcctId(Long.parseLong(tpAccount.getTP_ACCT_ID()));
			tpAcctBO
					.setActive((tpAccount.getACTIVE_FLG() != null && tpAccount.getACTIVE_FLG().equalsIgnoreCase("y")) ? true : false);
			tpAcctBO.setFmBillToAcctCd(tpAccount.getFM_BILLTO_ACCT_CD());

			return tpAcctBO;
		}
		else
		{
			throw new WOMNoResultException("No Record found for the Account in TP_ACCOUNT Table ::" + billToAcc);

		}

	}

	private TradingPartnerBO getTradingPartner(final String billToAcc) throws WOMNoResultException
	{

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {tp:" + FMIPOTRADINGPARTNERModel.PK + "} ");
		query.append(" FROM  {" + FMIPOTRADINGPARTNERModel._TYPECODE + " AS tp  ");
		query.append(" JOIN " + FMIPOTPACCOUNTModel._TYPECODE + " AS ta");
		query.append(" ON {ta." + FMIPOTPACCOUNTModel.TP_ID + "} = {tp." + FMIPOTRADINGPARTNERModel.TP_ID + "} }");
		query.append(" WHERE {ta:" + FMIPOTPACCOUNTModel.ACTIVE_FLG + "} = 'Y'");
		query.append(" AND {ta." + FMIPOTPACCOUNTModel.FM_BILLTO_ACCT_CD + "}=?" + FMIPOTPACCOUNTModel.FM_BILLTO_ACCT_CD);
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMIPOTPACCOUNTModel.FM_BILLTO_ACCT_CD, billToAcc);

		final SearchResult<FMIPOTRADINGPARTNERModel> result = getFlexibleSearchService().search(flexibleSearchQuery);

		if (null != result && result.getResult().size() > 0)
		{
			final FMIPOTRADINGPARTNERModel tpAccountPartner = result.getResult().get(0);
			final TradingPartnerBO tradingPartner = new TradingPartnerBO();

			tradingPartner.setTpId(Long.parseLong(tpAccountPartner.getTP_ID()));
			tradingPartner.setActive((tpAccountPartner.getACTIVE_FLG() != null && tpAccountPartner.getACTIVE_FLG().equalsIgnoreCase(
					"y")) ? true : false);
			tradingPartner.setInactiveFromDate(tpAccountPartner.getINACTIVE_FROM_DATE());
			tradingPartner.setIpoSecurityKey(tpAccountPartner.getIPO_SECURITY_KEY());
			tradingPartner.setTpName(tpAccountPartner.getTP_NAME());
			tradingPartner.setGroupCode(tpAccountPartner.getTP_GRP_CD());
			tradingPartner.setEnableShippingFromTsc((tpAccountPartner.getENABLE_SHIPPING_FROM_TSC_FLG() != null && tpAccountPartner.getENABLE_SHIPPING_FROM_TSC_FLG().equalsIgnoreCase(
					"y")) ? true : false);

			return tradingPartner;
		}
		else
		{
			throw new WOMNoResultException("No Record found for the Account in TP_PARTNER Table ::" + billToAcc);

		}
	}

	private FlexibleSearchService getFlexibleSearchService()
	{
		final FlexibleSearchService flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext().getBean(
				"flexibleSearchService");
		return flexibleSearchService;
	}

}