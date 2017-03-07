<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:oa="http://www.openapplications.org/oagis" 
xmlns:fn="http://www.w3.org/2006/xpath-functions">

	<xsl:import href="PORequest.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

	<xsl:variable name="frieghtType" select="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Header/default:TransportationTerm/default:FreightTerms"/>

	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Header/default:Parties/default:BillToParty/default:TaxExemptInd"  />
	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Header/default:Parties/default:BillToParty/default:TaxId"  />
	<xsl:template name="OrderItemCustom">
		<xsl:element name="scacCode">
			<xsl:value-of select="$frieghtType" />
		</xsl:element>		
	</xsl:template>
	
	<!--
	<xsl:template match="/aaia:AddRequestForQuote/default:DataArea/default:RequestForQuote/aaia:Line/aaia:OrderItem" >
		<xsl:element name="PartNumber">
			<xsl:value-of   select="aaia:ItemIds/default:CustomerItemId/default:Id"/>
		</xsl:element>
		<xsl:element name="AaiaBrand">
			<xsl:value-of   select="aaia:ItemIds/aaia:SupplierCode"/>
		</xsl:element>		
		<xsl:element name="ScacCode">
			<xsl:value-of select="$frieghtType" />
		</xsl:element>

	</xsl:template>
-->
</xsl:stylesheet>

