<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >
	<xsl:import href="PORequest.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<!--
	<xsl:template match="/aaia:AddRequestForQuote/default:DataArea/default:RequestForQuote/aaia:Line/aaia:OrderItem" >
		<xsl:element name="PartNumber">
			<xsl:value-of   select="aaia:ItemIds/default:CustomerItemId/default:Id"/>
		</xsl:element>
		<xsl:element name="AaiaBrand">
			<xsl:value-of   select="aaia:ItemIds/aaia:SupplierCode"/>
		</xsl:element>		

	</xsl:template>
-->
</xsl:stylesheet>
