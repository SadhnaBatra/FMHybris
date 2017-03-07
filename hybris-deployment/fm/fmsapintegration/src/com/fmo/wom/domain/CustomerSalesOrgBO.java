package com.fmo.wom.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the CUSTOMER_SALES_INFO database table.
 * 
 * NOTE: The 'createUserId' and 'updateUserId' have been mapped to arbitrary columns of 'String' type,
 * 			while the 'updateTimestamp' has been mapped to an already mapped column of 'Date' type since 
 * 			the underlying 'CUSTOMER_SALES_INFO' table does not have corresponding columns AND currently 
 * 			there is no way in JPA to suppress extra attributes passed on from the '@MappedSuperclass'. 
 * 			Absence of these mappings will result in those columns being included in SQL statements being 
 * 			sent to the database, resulting in errors. 
 * 
 * @author vishws74
 */
public class CustomerSalesOrgBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private CustomerSalesOrgPK id;

	private String distributionChannel;

	private String division;

	//bi-directional many-to-one association to SalesOrganizationBOTest

	private SalesOrganizationBO salesOrganization;

    public CustomerSalesOrgBO() {
    }

	public CustomerSalesOrgPK getId() {
		return this.id;
	}

	public void setId(CustomerSalesOrgPK id) {
		this.id = id;
	}
	
	public String getDistributionChannel() {
		return this.distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	@XmlTransient
	public SalesOrganizationBO getSalesOrganization() {
		return this.salesOrganization;
	}

	public void setSalesOrganization(SalesOrganizationBO salesOrganization) {
		this.salesOrganization = salesOrganization;
	}

    @Override
    public String toString() {
        return "CustomerSalesOrgBO [id=" + id + ", distributionChannel="
                + distributionChannel + ", division=" + division
                + ", salesOrganization=" + salesOrganization + "]";
    }
	
}