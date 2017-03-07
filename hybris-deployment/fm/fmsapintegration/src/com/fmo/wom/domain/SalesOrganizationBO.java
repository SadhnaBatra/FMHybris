package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.List;

//import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the SALES_ORGANIZATION database table.
 * 
 * NOTE: The 'createUserId' and 'updateUserId' have been mapped to arbitrary columns of 'String' type,
 * 			while the 'updateTimestamp' has been mapped to an already mapped column of 'Date' type since 
 * 			the underlying 'SALES_ORGANIZATION' table does not have corresponding columns AND currently 
 * 			there is no way in JPA to suppress extra attributes passed on from the '//MappedSuperclass'. 
 * 			Absence of these mappings will result in those columns being included in SQL statements being 
 * 			sent to the database, resulting in errors. 
 * 
 */
/*//Entity
//Table(name = "SALES_ORGANIZATION")
//AttributeOverrides({ 
	//AttributeOverride( name="createUserId", column = //Column(name="SALES_ORG_DESC", insertable=false, updatable=false) ),
	//AttributeOverride( name="createTimestamp", column = //Column(name="CREATED_DATE") ),
	//AttributeOverride( name="updateUserId", column = //Column(name="SALES_ORG_DESC", insertable=false, updatable=false) ),
	//AttributeOverride( name="updateTimestamp", column = //Column(name="CREATED_DATE", insertable=false, updatable=false) )
})*/
public class SalesOrganizationBO extends WOMCodeDescBO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Id
	//Column(name = "SALES_ORG_ID")
	private long id;
	
	//Column(name = "SALES_ORG_CODE")
	private String code;
	
	//Column(name = "EXTERNAL_SYSTEM_CODE")
	private String externalSystemCd;

	//Column(name = "SALES_ORG_DESC")
	private String desc;
	
	// bi-directional many-to-one association to CustomerSalesInfoBOTest
	//OneToMany(mappedBy = "salesOrganization")
	private List<CustomerSalesOrgBO> customerSalesInfos;

	public SalesOrganizationBO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public String getSalesOrgCode() {
//		return salesOrgCode;
//	}
//
//	public void setSalesOrgCode(String salesOrgCode) {
//		this.salesOrgCode = salesOrgCode;
//	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExternalSystemCd() {
		return this.externalSystemCd;
	}

	public void setExternalSystemCd(String externalSystemCd) {
		this.externalSystemCd = externalSystemCd;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	@XmlTransient
	public List<CustomerSalesOrgBO> getCustomerSalesInfos() {
		return this.customerSalesInfos;
	}

	public void setCustomerSalesInfos(
			List<CustomerSalesOrgBO> customerSalesInfos) {
		this.customerSalesInfos = customerSalesInfos;
	}

    @Override
    public String toString() {
        return "SalesOrganizationBO [id=" + id + ", code=" + code
                + ", externalSystemCd=" + externalSystemCd + ", desc=" + desc+ "]";
    }

	
}