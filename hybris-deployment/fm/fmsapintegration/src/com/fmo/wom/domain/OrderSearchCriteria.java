package com.fmo.wom.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderSearchFilter;

public class OrderSearchCriteria {
	
	private AccountBO billToAccount;
	
	private String poNumber;
	
	private String confirmationOrOrderNumber;
	
	private String orderNumber;
	
	private ExternalSystem externalSystem; 
	
	private Date searchFrom;
	
	private Date searchTo;
	
	private OrderSearchFilter orderStatus;
	
	private String orderOrReturns;
	
	/*START : For ship to login*/
	private boolean isShipToLogin = false;
	
	private AccountBO shipToAccount;
	
	private List<AccountBO> billToAccounts;
	
	/*END :For ShipTo login*/
	
	
	//START : Constructors for Order Header
	//For BillTo Login
	public OrderSearchCriteria(AccountBO aBillToAccount, String aOrderOrConfirmNumber,
							   String aPoNumber, Date from, Date to,
			                   OrderSearchFilter anOrderStatus, String orderOrReturnsStr) {
		super();
		this.billToAccount = aBillToAccount;
		this.confirmationOrOrderNumber = aOrderOrConfirmNumber;
		this.poNumber = aPoNumber;
		this.searchFrom = from == null?getDefaultStartOrderSearchDate():from;
		this.searchTo = to == null?getDefaultEndOrderSearchDate():to;
		this.orderStatus = anOrderStatus == null ?OrderSearchFilter.ALL : anOrderStatus;
		this.orderOrReturns = orderOrReturnsStr == null ? "ORDERS":orderOrReturnsStr;
		this.isShipToLogin = false;
	}

	//For ShipTo Login
	public OrderSearchCriteria(List<AccountBO> billToAccountList, AccountBO shipToAccount,
							   String aOrderOrConfirmNumber, String aPoNumber, 
							   Date from, Date to,
							   OrderSearchFilter anOrderStatus, String orderOrReturnsStr){
		super();
		this.billToAccounts = billToAccountList;
		this.shipToAccount = shipToAccount;
		this.confirmationOrOrderNumber = aOrderOrConfirmNumber;
		this.poNumber = aPoNumber;
		this.searchFrom = from == null?getDefaultStartOrderSearchDate():from;
		this.searchTo = to == null?getDefaultEndOrderSearchDate():to;
		this.orderStatus = anOrderStatus == null ?OrderSearchFilter.ALL : anOrderStatus;
		this.orderOrReturns = orderOrReturnsStr == null ? "ORDERS":orderOrReturnsStr;
		this.isShipToLogin = true;
	}
	//END : Constructors for Order Header
	
	
	//START : Constructors for Order Details
	//For BillTo Login
	public OrderSearchCriteria(AccountBO anAccount, String aMasterOrderNumber, 
							   String anOrderNumber, String anOrderOrReturnsStr, ExternalSystem aExternalSystem){
		super();
		this.isShipToLogin = false;
		this.billToAccount = anAccount;
		this.confirmationOrOrderNumber = aMasterOrderNumber;
		this.orderNumber = anOrderNumber;
		this.orderStatus = OrderSearchFilter.ALL;
		this.orderOrReturns = anOrderOrReturnsStr == null ? "ORDERS":anOrderOrReturnsStr;
		this.externalSystem = aExternalSystem;
	}
	
	//For ShipTo Login
	public OrderSearchCriteria(List<AccountBO> billToAccountList, AccountBO shipToAccount, String aMasterOrderNumber, 
			   						String anOrderNumber, String anOrderOrReturnsStr, ExternalSystem aExternalSystem){
		super();
		this.isShipToLogin = true;
		this.billToAccounts = billToAccountList;
		this.shipToAccount = shipToAccount;
		this.confirmationOrOrderNumber = aMasterOrderNumber;
		this.orderNumber = anOrderNumber;
		this.orderStatus = OrderSearchFilter.ALL;
		this.orderOrReturns = anOrderOrReturnsStr == null ? "ORDERS":anOrderOrReturnsStr;
		this.externalSystem = aExternalSystem;
	}

	//END : Constructors for Order Details 
	
	private Date getDefaultStartOrderSearchDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDefaultEndOrderSearchDate());
		cal.add(Calendar.DATE, -8);
		return cal.getTime();
	} 
	
	private Date getDefaultEndOrderSearchDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	public AccountBO getBillToAccount() {
		return billToAccount;
	}

	public String getNabsAccountCode(){
		return isShipToLogin?this.shipToAccount.getAccountCode():this.billToAccount.getAccountCode();
	}
	
	public String getECCAccountCode(){
		return isShipToLogin?this.shipToAccount.getSapAccount().getSapAccountCode():this.billToAccount.getSapAccount().getSapAccountCode();
	}
	
	public void setBillToAccount(AccountBO billToAccount) {
		this.billToAccount = billToAccount;
	}

	public boolean isShipToLogin() {
		return isShipToLogin;
	}

	public void setShipToLogin(boolean isShipToLogin) {
		this.isShipToLogin = isShipToLogin;
	}

	public List<AccountBO> getBillToAccounts() {
		return billToAccounts;
	}

	public void setBillToAccounts(List<AccountBO> billToAccounts) {
		this.billToAccounts = billToAccounts;
	}

	public String getPoNumber() {
		return poNumber==null?"":poNumber.trim();
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getConfirmationOrOrderNumber() {
		return confirmationOrOrderNumber==null?"":confirmationOrOrderNumber.trim();
	}

	public void setConfirmationOrOrderNumber(String confirmationOrOrderNumber) {
		this.confirmationOrOrderNumber = confirmationOrOrderNumber;
	}

	public Date getSearchFrom() {
		return searchFrom;
	}

	public void setSearchFrom(Date searchFrom) {
		this.searchFrom = searchFrom;
	}

	public Date getSearchTo() {
		return searchTo;
	}

	public void setSearchTo(Date searchTo) {
		this.searchTo = searchTo;
	}

	public OrderSearchFilter getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderSearchFilter orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderOrReturns() {
		return orderOrReturns == null?"ORDERS":orderOrReturns.trim();
	}

	public void setOrderOrReturns(String orderOrReturns) {
		this.orderOrReturns = orderOrReturns;
	}

	public AccountBO getShipToAccount() {
		return shipToAccount;
	}

	public void setShipToAccount(AccountBO shipToAccount) {
		this.shipToAccount = shipToAccount;
	}

	public String getOrderNumber() {
		return orderNumber==null?"":orderNumber.trim();
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public ExternalSystem getExternalSystem() {
		return externalSystem;
	}

	public void setExternalSystem(ExternalSystem externalSystem) {
		this.externalSystem = externalSystem;
	}

	@Override
	public String toString() {
		
		String nabsAccountCode = null;
		String eccAccountCode  = null;

		StringBuilder toString = new StringBuilder();
		
		if(this.isShipToLogin){
			
			if(this.shipToAccount != null){
				nabsAccountCode = shipToAccount.getAccountCode(); 
				eccAccountCode  = shipToAccount.getSapAccount().getSapAccountCode();
			}
			
			toString.append("OrderSearchCriteria [Ship To AccountCode= ").append(nabsAccountCode).append("/").append(eccAccountCode);
			int billToCount = 1;
			toString.append("Bill To Accounts [");
			for(AccountBO billTo:billToAccounts){
				toString.append(billToCount).append(": ").append(billTo.getAccountCode()).append("/").append(billTo.getSapAccount().getSapAccountCode());
				if(billToCount < billToAccounts.size()){
					toString.append(", ");
				}
				billToCount++;
			}
			toString.append("]");
		} else {
			if(this.billToAccount != null){
				nabsAccountCode = billToAccount.getAccountCode(); 
				eccAccountCode  = billToAccount.getSapAccount().getSapAccountCode();
			}
			toString.append("OrderSearchCriteria [Bill To accountCode= ").append(nabsAccountCode).append("/").append(eccAccountCode);
		}	
		toString.append(", isShipToLogin=").append(isShipToLogin);	
		toString.append(", poNumber=").append(poNumber);
		toString.append(", confirmationOrOrderNumber=").append(confirmationOrOrderNumber);
		toString.append(", orderNumber=").append(orderNumber);
		toString.append(", External System=").append(externalSystem);
		toString.append(", searchFrom= ").append(searchFrom);
		toString.append(", searchTo= ").append(searchTo);
		toString.append(", orderStatus= ").append(orderStatus);
		toString.append(", orderOrReturns= ").append(orderOrReturns).append("]");
			
		return toString.toString();
	}
}
