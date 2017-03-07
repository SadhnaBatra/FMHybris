<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  exclude-result-prefixes="aaia default xsi ">

	<xsl:import href="IncomingRequest.xsl"/>	
	
	<xsl:variable name="ipoType"><xsl:text>RequestForQuote</xsl:text></xsl:variable>
	
	<xsl:template match="/aaia:AddRequestForQuote">
		<xsl:call-template name="FMRootNode" >
		</xsl:call-template>
	</xsl:template>
	<!--	As per discussion in daily status meeting, om7 supporting GC customer sending customerItemId with more than 35 chars.So WOM8 also needs to support
		Per email from Renny on 5/22/2012, read only first 20 chars
	-->	
	<xsl:template name="custPoNbr">
		<custPoNbr>
			<xsl:choose>
				<xsl:when test="string-length(default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds/default:CustomerDocumentId/default:Id)>20">
					<xsl:value-of select="substring(default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds/default:CustomerDocumentId/default:Id,1,20)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds/default:CustomerDocumentId/default:Id" />
				</xsl:otherwise>
			</xsl:choose>
		</custPoNbr>
	</xsl:template>

	<xsl:template match="default:DataArea/default:*/aaia:Line/default:Parties/default:ShipFromParty">
	</xsl:template>

</xsl:stylesheet>
