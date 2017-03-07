<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:import href="AQResponse.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

	<xsl:template match="/AddRequestForQuote/DataArea/RequestForQuote/Header/Parties/HostParty"  />
	<xsl:template match="/AddRequestForQuote/DataArea/RequestForQuote/Header/Parties/SoldToParty"  />
	<xsl:template match="/OrderResponse/Lines/Line/OrderItem/ItemIds/ManufacturerCode"  />

	<xsl:template name="ShipFromPartyCustom"></xsl:template>
	<xsl:template name="ShipFromPartyScacCodeCustom"></xsl:template>

	<xsl:template name="LineUserArea">
		<xsl:param name="index"  />
		<xsl:element name="UserArea">
			<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/aaia:Line[number($index)]/default:UserArea/child::*" />
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
