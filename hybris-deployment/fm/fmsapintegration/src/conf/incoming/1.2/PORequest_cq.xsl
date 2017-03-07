<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >
	<xsl:import href="PORequest.xsl"/>
	<xsl:output method="xml" indent="yes"/>
<!--
	<xsl:template match="/aaia:AddRequestForQuote/default:DataArea/default:RequestForQuote/aaia:Line/default:OrderItem" >
		<xsl:element name="partNumber">
			<xsl:value-of   select="aaia:ItemIds/default:CustomerItemId/default:Id"/>
		</xsl:element>
		<xsl:element name="aaiaBrand">
			<xsl:value-of   select="aaia:ItemIds/aaia:ManufacturerItemId/default:Id"/>
		</xsl:element>
	</xsl:template>
	-->
	<xsl:template name="aaiaBrand">
		<xsl:element name="aaiaBrand">
			<xsl:value-of   select="aaia:ItemIds/aaia:ManufacturerItemId/default:Id"/>
		</xsl:element>
	</xsl:template>	
<!--	<xsl:template name="LineUserArea">
		<xsl:param name="index"  />
		<xsl:choose>
			<xsl:when test="$requestSource/aaia:ProcessPurchaseOrder/default:DataArea/default:*/aaia:Line[number($index)]/default:Parties/default:ShipFromParty/default:PartyId/default:SCAC">
				<UserArea>
					<xsl:call-template name="LineHostParty">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
				</UserArea>
			</xsl:when>
			<xsl:otherwise>
					<xsl:call-template name="LineHostParty">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
-->
</xsl:stylesheet>
