<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  exclude-result-prefixes="aaia default xsi " >
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>	
	<xsl:template match="/aaia:GetShipment">
		<xsl:call-template name="FMRootNode" >
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="FMRootNode">
		<GetShipment>
			<billToAccountCode><xsl:value-of select="/aaia:GetShipment/aaia:DataArea/default:Shipment/default:Header/default:Parties/default:BillToParty/default:PartyId/default:Id" /></billToAccountCode>
			<mstrOrderNumber><xsl:value-of select="/aaia:GetShipment/aaia:DataArea/default:Shipment/default:Header/default:AlternateDocumentIds/default:SupplierDocumentId/default:Id"/></mstrOrderNumber>
			<custPoNumber><xsl:value-of select="/aaia:GetShipment/aaia:DataArea/default:Shipment/default:Header/default:AlternateDocumentIds/default:CustomerDocumentId/default:Id"/></custPoNumber>
		</GetShipment>
	</xsl:template>
	
	
</xsl:stylesheet>
