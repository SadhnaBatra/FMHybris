<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  exclude-result-prefixes="aaia default xsi ">

	<xsl:import href="Outgoing.xsl"/>	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>	
	<xsl:param name="environment"/>
	<xsl:variable name="ipoType"><xsl:text>PurchaseOrder</xsl:text></xsl:variable>
	<xsl:variable name="rootNode">aaia:ProcessPurchaseOrder</xsl:variable>
	<!--<xsl:variable name="dataHeaderTag">default:Header</xsl:variable>-->
	<xsl:variable name="HeaderNodeTag">aaia:Header</xsl:variable>
	<xsl:variable name="EffectivePeriodTag">aaia:EffectivePeriod</xsl:variable>
	
	<xsl:template match="/OrderBO">
		<aaia:AcknowledgePurchaseOrder  xmlns:aaia="http://www.aftermarket.org/oagis"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.aftermarket.org/oagis ../BODs/AcknowledgePurchaseOrder.xsd" revision="1.2.1" lang="en">
			<xsl:attribute name="environment" ><xsl:value-of select="$environment"/></xsl:attribute>
			<xsl:call-template name="FMRootNode" />
		</aaia:AcknowledgePurchaseOrder>		
	</xsl:template>
	<xsl:template name="DataAreaCustom" >
		<Acknowledge>
			<Code>Accepted</Code>
			<Mode>FullDetail</Mode>
		</Acknowledge>
	</xsl:template>
	
<!--	<xsl:template match="*[local-name()='Header']/default:DocumentIds/default:SupplierDocumentId">
		<SupplierDocumentId>
				<Id><xsl:value-of select="/OrderBO/mstrOrdNbr" /></Id>
		</SupplierDocumentId>
	</xsl:template>
-->	
	<xsl:template name="DocumentReferences">
		<xsl:apply-templates select="$requestSource/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/*[local-name()='Header']/default:DocumentReferences"/>
	</xsl:template>
<!--	<xsl:template name="LineSupplierItemId">
		<xsl:param name="index"  />
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/problemItem/ipoMessage = '' or not (/OrderBO/parts[number($index)]/problemItem/ipoMessage) "  >
					<aaia:SupplierItemId>
						<xsl:choose>
							<xsl:when test="/OrderBO/parts[number($index)]/externalSystem='EVRST' ">
								<xsl:call-template name="createnameValueElement">
									<xsl:with-param name="elementName">Id</xsl:with-param>
									<xsl:with-param name="elementValue"><xsl:value-of select="/OrderBO/parts[number($index)]/productFlag"/><xsl:value-of select="/OrderBO/parts[number($index)]/displayPartNumber"/></xsl:with-param>
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="createnameValueElement">
									<xsl:with-param name="elementName">Id</xsl:with-param>
									<xsl:with-param name="elementValue"><xsl:value-of select="/OrderBO/parts[number($index)]/displayPartNumber"/></xsl:with-param>
								</xsl:call-template>
								<xsl:call-template name="createnameValueElement">
									<xsl:with-param name="elementName">aaia:Code</xsl:with-param>
									<xsl:with-param name="elementValue"><xsl:value-of select="/OrderBO/parts[number($index)]/productFlag"/></xsl:with-param>
								</xsl:call-template>			
							</xsl:otherwise>
						</xsl:choose>
					</aaia:SupplierItemId>
				</xsl:when>
			</xsl:choose>
	</xsl:template>
-->	
	<xsl:template name="ShipFromPartyCustom">		
		<xsl:param name="index"  />
		<ShipFromParty>
			<PartyId>
				<xsl:apply-templates select="$requestSource/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Line[number($index)]/default:Parties/default:ShipFromParty/default:PartyId/default:Id"/>
				<xsl:call-template name="ShipFromPartyScacCodeCustom">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>			
			</PartyId>
			<xsl:apply-templates select="$requestSource/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Line[number($index)]/default:Parties/default:ShipFromParty/default:Name"/>
			<xsl:apply-templates select="$requestSource/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Line[number($index)]/default:Parties/default:ShipFromParty/default:Addresses"/>
		</ShipFromParty>						
	</xsl:template>
	
	
</xsl:stylesheet>
