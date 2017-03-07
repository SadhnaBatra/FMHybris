/**
 * 
 */
package com.fm.falcon.webservices.dto;

import java.util.List;


/**
 * @author SR279690
 * 
 */
public class LoyaltyOrderConfirmRequestDTO extends CRMRequestDTO

{


	private String memberShipId;
	private String sapAccountCode;//soldto

	private String sapAccount_Code;//ship to


	private String sapAccCodeAddress1;
	private String sapAccCodeAddress2;
	private String sapAccCodeCity;
	private String sapAccCodeState;
	private String sapAccCodeCountry;
	private String sapAccCodeZipCode;

	private List<LoyaltyOrderRedemptionProductInfobean> productInfo;

	/**
	 * @return the sapAccount_Code
	 */
	public String getSapAccount_Code()
	{
		return sapAccount_Code;
	}

	/**
	 * @param sapAccount_Code
	 *           the sapAccount_Code to set
	 */
	public void setSapAccount_Code(final String sapAccount_Code)
	{
		this.sapAccount_Code = sapAccount_Code;
	}

	/**
	 * @return the memberShipId
	 */
	public String getMemberShipId()
	{
		return memberShipId;
	}

	/**
	 * @param memberShipId
	 *           the memberShipId to set
	 */
	public void setMemberShipId(final String memberShipId)
	{
		this.memberShipId = memberShipId;
	}

	/**
	 * @return the sapAccountCode
	 */
	public String getSapAccountCode()
	{
		return sapAccountCode;
	}

	/**
	 * @param sapAccountCode
	 *           the sapAccountCode to set
	 */
	public void setSapAccountCode(final String sapAccountCode)
	{
		this.sapAccountCode = sapAccountCode;
	}



	/**
	 * @return the sapAccCodeAddress1
	 */
	public String getSapAccCodeAddress1()
	{
		return sapAccCodeAddress1;
	}

	/**
	 * @param sapAccCodeAddress1
	 *           the sapAccCodeAddress1 to set
	 */
	public void setSapAccCodeAddress1(final String sapAccCodeAddress1)
	{
		this.sapAccCodeAddress1 = sapAccCodeAddress1;
	}

	/**
	 * @return the sapAccCodeAddress2
	 */
	public String getSapAccCodeAddress2()
	{
		return sapAccCodeAddress2;
	}

	/**
	 * @param sapAccCodeAddress2
	 *           the sapAccCodeAddress2 to set
	 */
	public void setSapAccCodeAddress2(final String sapAccCodeAddress2)
	{
		this.sapAccCodeAddress2 = sapAccCodeAddress2;
	}

	/**
	 * @return the sapAccCodeCity
	 */
	public String getSapAccCodeCity()
	{
		return sapAccCodeCity;
	}

	/**
	 * @param sapAccCodeCity
	 *           the sapAccCodeCity to set
	 */
	public void setSapAccCodeCity(final String sapAccCodeCity)
	{
		this.sapAccCodeCity = sapAccCodeCity;
	}

	/**
	 * @return the sapAccCodeState
	 */
	public String getSapAccCodeState()
	{
		return sapAccCodeState;
	}

	/**
	 * @param sapAccCodeState
	 *           the sapAccCodeState to set
	 */
	public void setSapAccCodeState(final String sapAccCodeState)
	{
		this.sapAccCodeState = sapAccCodeState;
	}

	/**
	 * @return the sapAccCodeCountry
	 */
	public String getSapAccCodeCountry()
	{
		return sapAccCodeCountry;
	}

	/**
	 * @param sapAccCodeCountry
	 *           the sapAccCodeCountry to set
	 */
	public void setSapAccCodeCountry(final String sapAccCodeCountry)
	{
		this.sapAccCodeCountry = sapAccCodeCountry;
	}

	/**
	 * @return the sapAccCodeZipCode
	 */
	public String getSapAccCodeZipCode()
	{
		return sapAccCodeZipCode;
	}

	/**
	 * @param sapAccCodeZipCode
	 *           the sapAccCodeZipCode to set
	 */
	public void setSapAccCodeZipCode(final String sapAccCodeZipCode)
	{
		this.sapAccCodeZipCode = sapAccCodeZipCode;
	}

	/**
	 * @return the productInfo
	 */
	public List<LoyaltyOrderRedemptionProductInfobean> getProductInfo()
	{
		return productInfo;
	}

	/**
	 * @param productInfo the productInfo to set
	 */
	public void setProductInfo(List<LoyaltyOrderRedemptionProductInfobean> productInfo)
	{
		this.productInfo = productInfo;
	}


}
