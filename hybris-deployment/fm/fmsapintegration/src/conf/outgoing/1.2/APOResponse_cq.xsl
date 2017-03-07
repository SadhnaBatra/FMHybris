<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
 xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis"
xmlns:default="http://www.openapplications.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >
	<xsl:import href="APOResponse.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

	<xsl:template name="LineSupplierCode">
		<xsl:param name="index"  />
		<aaia:ManufacturerItemId><Id><xsl:value-of select="/OrderBO/parts[number($index)]/aaiaBrand"/></Id></aaia:ManufacturerItemId>
	</xsl:template>

<!--	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Line/default:OrderItem/aaia:ItemIds/aaia:ManufacturerItemId">
		<xsl:element name="aaia:SupplierCode">
			<xsl:value-of select="default:Id"/>
		</xsl:element>		
	</xsl:template>
-->	
<!--	<xsl:template name="PromisedShipDateValueCustom">
		<xsl:param name="index"  />
		<xsl:value-of select="$SevenDaysFromToday"/>

	</xsl:template>
-->	

</xsl:stylesheet>
