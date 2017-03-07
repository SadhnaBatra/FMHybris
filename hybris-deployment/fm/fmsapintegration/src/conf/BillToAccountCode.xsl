<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:v2default="http://www.aaiasoa.net/IPOv2"  xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common"
xmlns:v12default="http://www.openapplications.org/oagis"  xmlns:aaia="http://www.aftermarket.org/oagis" >
	<xsl:output method="xml"  omit-xml-declaration="yes"/>

	<xsl:template match="/">     
		<xsl:choose>
			<xsl:when test="namespace-uri(/*)='http://www.aaiasoa.net/IPOv2'">
				<xsl:value-of select="/*/v2default:DataArea/*/v2default:Header/v2default:BillToParty/cmn:PartyId"/>
				<xsl:value-of select="/*/v2default:ApplicationArea/cmn:UserArea/cmn:Any/v2default:BillToParty/v2default:PartyId"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="/*/v12default:DataArea/*/v12default:Header/v12default:Parties/v12default:BillToParty/v12default:PartyId/v12default:Id"/>
				<xsl:value-of select="/*/v12default:DataArea/*/aaia:Header/v12default:Parties/v12default:BillToParty/v12default:PartyId/v12default:Id"/>
				<xsl:value-of select="/*/aaia:DataArea/*/v12default:Header/v12default:Parties/v12default:BillToParty/v12default:PartyId/v12default:Id"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
