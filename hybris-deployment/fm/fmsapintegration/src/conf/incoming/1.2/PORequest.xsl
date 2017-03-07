<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  exclude-result-prefixes="aaia default xsi ">

	<xsl:import href="IncomingRequest.xsl"/>	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:variable name="ipoType"><xsl:text>PurchaseOrder</xsl:text></xsl:variable>

	<xsl:template match="/aaia:ProcessPurchaseOrder">
		<xsl:call-template name="FMRootNode" >
		</xsl:call-template>
	</xsl:template>
	
</xsl:stylesheet>
