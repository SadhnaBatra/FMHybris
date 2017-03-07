
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  
 xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis"
xmlns:default="http://www.openapplications.org/oagis">
	<xsl:import href="APOResponse.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/default:Header/default:Parties/default:HostParty"  />
	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/default:Header/default:Parties/default:SoldToParty"  />
	
	
<!--	<xsl:template match="/OrderResponse/Lines/Line/OrderItem/ItemIds/ManufacturerCode"  />
-->	
<!--	<xsl:template name="ShipFromPartyCustom"></xsl:template>
	<xsl:template name="ShipFromPartyScacCodeCustom"></xsl:template>
-->
</xsl:stylesheet>
