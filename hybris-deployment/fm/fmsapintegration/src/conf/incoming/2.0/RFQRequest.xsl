<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns="http://www.aaiasoa.net/IPOv2" xmlns:default="http://www.aaiasoa.net/IPOv2" xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  exclude-result-prefixes="cmn default xsi ">

	<xsl:import href="Common.xsl"/>	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:variable name="ipoType"><xsl:text>RequestForQuote</xsl:text></xsl:variable>
	<xsl:variable name="rootNode"><xsl:text>AddRequestForQuote</xsl:text></xsl:variable>
		
	<xsl:template match="/default:AddRequestForQuote">
		<xsl:call-template name="FMRootNode" >
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="custPoNbr">
		<custPoNbr>
			<xsl:choose>
				<xsl:when test="string-length(/default:AddRequestForQuote/default:DataArea/default:RequestForQuote/default:Header/default:DocumentIds/cmn:CustomerRFQDocumentId/cmn:CustomerDocumentId)>20">
					<xsl:value-of select="substring(/default:AddRequestForQuote/default:DataArea/default:RequestForQuote/default:Header/default:DocumentIds/cmn:CustomerRFQDocumentId/cmn:CustomerDocumentId,1,20)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="/default:AddRequestForQuote/default:DataArea/default:RequestForQuote/default:Header/default:DocumentIds/cmn:CustomerRFQDocumentId/cmn:CustomerDocumentId" />
				</xsl:otherwise>
			</xsl:choose>
		</custPoNbr>
	</xsl:template>

</xsl:stylesheet>
