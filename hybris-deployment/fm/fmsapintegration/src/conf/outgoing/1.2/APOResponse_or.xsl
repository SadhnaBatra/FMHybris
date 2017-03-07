<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:default="http://www.openapplications.org/oagis">
	<xsl:import href="APOResponse.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Header/default:Parties"  />
<!--	<xsl:template name="TransportationTerm">		
		<TransportationTerm>
			<FreightTerms >						
				<xsl:value-of select="$requestSource/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Header/default:TransportationTerm/default:FreightTerms" />
			</FreightTerms>			
		</TransportationTerm>
	</xsl:template>
-->	<xsl:template name="LineUserArea">
		<xsl:param name="index"  />
		<xsl:element name="CarrierParty">
			<xsl:apply-templates select="$requestSource/aaia:ProcessPurchaseOrder/default:DataArea/default:PurchaseOrder/aaia:Line[number($index)]/default:UserArea/child::*"  />			
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>
