
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.aaiasoa.net/IPOv2" xmlns:default="http://www.aaiasoa.net/IPOv2" xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fn="http://www.w3.org/2006/xpath-functions">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>
	<xsl:template match="/default:GetShipment">
		<xsl:call-template name="FMRootNode"/>
	</xsl:template>
	<xsl:template name="FMRootNode">
		<GetShipment>
			<billToAccountCode>
				<xsl:value-of select="/default:GetShipment/default:ApplicationArea/cmn:UserArea/cmn:Any/default:BillToParty/default:PartyId"/>
			</billToAccountCode>
			<mstrOrderNumber>
				<xsl:value-of select="/default:GetShipment/default:DataArea/default:Shipment/default:Header/default:DocumentReferenceIds/cmn:SupplierSalesOrderDocumentReference/cmn:SupplierDocumentId"/>
			</mstrOrderNumber>
			<custPoNumber>
				<xsl:value-of select="/default:GetShipment/default:DataArea/default:Shipment/default:Header/default:DocumentReferenceIds/cmn:CustomerPODocumentReference/cmn:CustomerDocumentId"/>
			</custPoNumber>
		</GetShipment>
	</xsl:template>
</xsl:stylesheet>
