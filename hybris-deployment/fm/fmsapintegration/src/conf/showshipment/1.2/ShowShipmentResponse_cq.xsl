<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  exclude-result-prefixes="aaia default xsi ">
	<xsl:import href="ShowShipmentResponse.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template name="HeaderDocumentIds">
		<DocumentId><Id><xsl:value-of select="customerPurchaseOrderNum" /></Id></DocumentId>
	</xsl:template>
	<xsl:template name="HeaderCarrierParty">
		<CarrierParty>
			<PartyId><SCAC><xsl:value-of select="../scacCode"/></SCAC></PartyId>
		</CarrierParty>

	</xsl:template>
	
	<xsl:template name="LineSupplierCode">
		<xsl:param name="index"  />
		<aaia:ManufacturerItemId><Id><xsl:value-of select="aaiaBrand"/></Id></aaia:ManufacturerItemId>
	</xsl:template>
	<xsl:template name="LineManufacturerCode">
		<aaia:ManufacturerCode><xsl:value-of select="aaiaBrand"/></aaia:ManufacturerCode>		
	</xsl:template>	
	<xsl:template name="HeaderActualShippedDate">
		<xsl:call-template name="createnameValueElement">
				<xsl:with-param name="elementName">ActualShippedDateTime</xsl:with-param>
				<xsl:with-param name="elementValue"><xsl:value-of select="orderUnitList/shippingUnitList/shipDate"/></xsl:with-param>
		</xsl:call-template>	
	</xsl:template>
	

</xsl:stylesheet>
