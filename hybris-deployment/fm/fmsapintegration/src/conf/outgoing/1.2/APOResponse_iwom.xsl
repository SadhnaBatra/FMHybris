<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis"
xmlns:default="http://www.openapplications.org/oagis">
	<xsl:import href="APOResponse.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/default:Header/default:Parties/default:HostParty"  />
	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/default:Header/default:Parties/default:SoldToParty"  />

	<xsl:template name="LineUserArea">
		<xsl:param name="index"  />
		<xsl:element name="CarrierParty" >
			<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/aaia:Line[number($index)]/default:UserArea/default:CarrierParty/child::*"  />			
		</xsl:element>
	</xsl:template>



</xsl:stylesheet>
