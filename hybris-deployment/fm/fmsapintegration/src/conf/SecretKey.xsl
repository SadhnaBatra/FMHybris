<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis"
xmlns:default_IPO2="http://www.aaiasoa.net/IPOv2" xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common"
>
	<xsl:output method="xml"  omit-xml-declaration="yes"/>

	<xsl:template match="/">     	
		<xsl:choose>
			<xsl:when test="namespace-uri(/*)='http://www.aaiasoa.net/IPOv2'">
				<xsl:value-of select="/default_IPO2:AddRequestForQuote/default_IPO2:ApplicationArea/default_IPO2:UserArea/cmn:Any/default_IPO2:FMIPOSecretKey"/>
				<xsl:value-of select="/default_IPO2:AddRequestForQuote/default_IPO2:ApplicationArea/cmn:UserArea/cmn:Any/default_IPO2:FMIPOSecretKey"/>
				<xsl:value-of select="/default_IPO2:ProcessPurchaseOrder/default_IPO2:ApplicationArea/cmn:UserArea/cmn:Any/default_IPO2:FMIPOSecretKey"/>
				<xsl:value-of select="/default_IPO2:GetShipment/default_IPO2:ApplicationArea/cmn:UserArea/cmn:Any/default_IPO2:FMIPOSecretKey"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="/aaia:AddRequestForQuote/default:ApplicationArea/default:UserArea/default:FMIPOSecretKey"/>
				<xsl:value-of select="/aaia:AddRequestForQuote/default:ApplicationArea/aaia:UserArea/default:FMIPOSecretKey"/>
				<xsl:value-of select="/aaia:ProcessPurchaseOrder/default:ApplicationArea/default:UserArea/default:FMIPOSecretKey"/>
				<xsl:value-of select="/aaia:GetShipment/default:ApplicationArea/default:UserArea/default:FMIPOSecretKey"/>
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>
	
</xsl:stylesheet>
