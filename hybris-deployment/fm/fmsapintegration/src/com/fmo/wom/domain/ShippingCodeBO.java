package com.fmo.wom.domain;

public class ShippingCodeBO {
	
	private NabsShippingCodeBO nabsShippingCode = null;
	private SapShippingCodeBO sapShippingCode = null;
	private BpcsShippingCodeBO bpcsShippingCode = null;
    
	
	public NabsShippingCodeBO getNabsShippingCode() {
		return nabsShippingCode;
	}
	public void setNabsShippingCode(NabsShippingCodeBO nabsShippingCode) {
		this.nabsShippingCode = nabsShippingCode;
	}
	public SapShippingCodeBO getSapShippingCode() {
		return sapShippingCode;
	}
	public void setSapShippingCode(SapShippingCodeBO sapShippingCode) {
		this.sapShippingCode = sapShippingCode;
	}
    public BpcsShippingCodeBO getBpcsShippingCode() {
        return bpcsShippingCode;
    }
    public void setBpcsShippingCode(BpcsShippingCodeBO bpcsShippingCode) {
        this.bpcsShippingCode = bpcsShippingCode;
    }	
	
}
