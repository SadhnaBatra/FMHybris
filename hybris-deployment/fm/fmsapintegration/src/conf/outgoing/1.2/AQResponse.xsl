<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  exclude-result-prefixes="aaia default xsi ">

	<xsl:import href="Outgoing.xsl"/>	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>	
	<xsl:param name="environment"/>
	<xsl:variable name="ipoType"><xsl:text>Quote</xsl:text></xsl:variable>
	<xsl:variable name="rootNode"><xsl:text>aaia:AddRequestForQuote</xsl:text></xsl:variable>

	<xsl:template match="/OrderBO">
		<aaia:AddQuote  xmlns:aaia="http://www.aftermarket.org/oagis"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.aftermarket.org/oagis ../BODs/AddQuote.xsd" revision="1.2.1" lang="en">
			<xsl:attribute name="environment" ><xsl:value-of select="$environment"/></xsl:attribute>
			<xsl:call-template name="FMRootNode" />
		</aaia:AddQuote>		
	</xsl:template>

	<xsl:template name="DataAreaCustom" >
		<Add confirm="Always" />
	</xsl:template>

<!--	<xsl:template match="*[local-name()='Header']/default:DocumentIds/default:CustomerDocumentId">
		<SupplierDocumentId>
				<Id><xsl:value-of select="/OrderBO/mstrOrdNbr" /></Id>
		</SupplierDocumentId>
	</xsl:template>
-->	
	<xsl:template match="*[local-name()='Header']/default:DocumentIds/default:CustomerDocumentId">
	</xsl:template>
	
	<xsl:template name="LineUserArea">
		<xsl:param name="index"  />
		<xsl:element name="UserArea">
			<xsl:if test="/OrderBO/parts[number($index)]/inventory/selectedLocation='true' ">
				<xsl:for-each select="/OrderBO/parts[number($index)]/inventory[selectedLocation='true']" >
					<xsl:call-template name="ShipFromPartyCustom_LineUserArea_DC">
						<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:if>

			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/additionalInventory/selectedLocation='false' ">
					<xsl:for-each select="/OrderBO/parts[number($index)]/additionalInventory[selectedLocation='false']" >
					<xsl:call-template name="ShipFromPartyCustom_LineUserArea_TSC">
						<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/aaia:Line[number($index)]/default:UserArea/child::*" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>
