<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.aaiasoa.net/IPOv2"
xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common"
exclude-result-prefixes="default">

	<xsl:import href="Common.xsl"/>	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>	
	<xsl:param name="environment"/>
	<xsl:variable name="ipoType"><xsl:text>RequestForQuote</xsl:text></xsl:variable>
	<xsl:variable name="rootNode"><xsl:text>default:AddRequestForQuote</xsl:text></xsl:variable>
	
	
	<xsl:template match="/OrderBO">
		<AddQuote  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common" xmlns="http://www.aaiasoa.net/IPOv2" xsi:schemaLocation="http://www.aaiasoa.net/IPOv2 ../BODs/AddQuote.xsd">
			<xsl:attribute name="environment" ><xsl:value-of select="$environment"/></xsl:attribute>
			<xsl:call-template name="FMRootNode" />
		</AddQuote>		
	</xsl:template>
	<xsl:template name="DataArea">
		<DataArea >
			<Quote>
				<xsl:call-template name="HeaderType"/>
				<xsl:call-template name="LinesType"/>
			</Quote>
		</DataArea>
	</xsl:template>
<!--	Header Data area sub templates START-->
	<xsl:template name="HeaderType" >
	<!--TODO move this to common area-->
		<Header>
			<xsl:call-template name="WeightUOMType"/>
			<xsl:call-template name="DocumentIdsType"/>
			<xsl:call-template name="DocumentReferenceIdsType"/>
			<xsl:call-template name="NoteType"/>
			<xsl:call-template name="PromisedShipDateType"/>
			<xsl:call-template name="TransportationTermType"/>			
			<xsl:call-template name="FreightTermsType"/>			
			<xsl:call-template name="HeaderExtendedPriceType"/>
			<xsl:call-template name="HeaderTotalPriceType"/>
			<xsl:call-template name="PaymentTermsType"/>
			<xsl:call-template name="HeaderBillToPartyType"/>
			<xsl:call-template name="HeaderShipToPartyType"/>
			<xsl:call-template name="HeaderSoldToPartyType"/>
			<xsl:call-template name="HeaderHostToPartyType"/>
			<xsl:call-template name="HeaderUserArea"/>
	</Header>
	</xsl:template>
	<xsl:template name="LinesType">
		<Lines>
			<xsl:for-each select="/OrderBO/parts">
				<xsl:variable name="index" select="position()"/>
				<Line>
					<LineNumber><xsl:value-of select="/OrderBO/parts[$index]/lineNumber"/> </LineNumber>
					<xsl:call-template name="WeightType">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>						
					<xsl:call-template name="OrderItemType">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="ItemStatusChangesType">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="OrderQuantityType">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="BackOrderQuantityType">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LinePricesType">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LineFreightTerms">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LineExtendedPrice">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LineAdditionalCharges">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LineTotalAmount">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>				
					</xsl:call-template>
					<xsl:call-template name="LinePromisedShipDate">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LineNote">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LineShipFromParty">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="LineUserArea">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>
				</Line>
			</xsl:for-each>
		</Lines>
	</xsl:template>	
	<xsl:template name="DocumentIdsTypeCustom">
<!--		<xsl:choose>
			<xsl:when test="$requestSource/default:AddRequestForQuote/default:DataArea/default:RequestForQuote/default:Header/default:DocumentIds/cmn:CustomerRFQDocumentId">
					<cmn:CustomerRFQDocumentId>
						<cmn:CustomerDocumentId>
							<xsl:value-of select="$requestSource/default:AddRequestForQuote/default:DataArea/default:RequestForQuote/default:Header/default:DocumentIds/cmn:CustomerRFQDocumentId"/>
						</cmn:CustomerDocumentId>
					</cmn:CustomerRFQDocumentId>
			</xsl:when>		
		</xsl:choose>
-->		<cmn:SupplierQuoteDocumentId>
				<cmn:SupplierDocumentId><xsl:value-of select="/OrderBO/mstrOrdNbr" /></cmn:SupplierDocumentId>
		</cmn:SupplierQuoteDocumentId>				
		
	</xsl:template>
	<xsl:template name="DocumentReferenceIdsTypeCustom">
		<xsl:choose>
			<xsl:when test="$requestSource/default:AddRequestForQuote/default:DataArea/default:RequestForQuote/default:Header/default:DocumentIds/cmn:CustomerRFQDocumentId/cmn:CustomerDocumentId">
					<cmn:CustomerRFQDocumentReference>
						<cmn:CustomerDocumentId>
							<xsl:value-of select="$requestSource/default:AddRequestForQuote/default:DataArea/default:RequestForQuote/default:Header/default:DocumentIds/cmn:CustomerRFQDocumentId/cmn:CustomerDocumentId"/>
						</cmn:CustomerDocumentId>
					</cmn:CustomerRFQDocumentReference>
			</xsl:when>		
		</xsl:choose>
<!--		<cmn:SupplierQuoteDocumentReference>
			<cmn:SupplierDocumentId><xsl:value-of select="/OrderBO/mstrOrdNbr" /></cmn:SupplierDocumentId>
		</cmn:SupplierQuoteDocumentReference>
-->	</xsl:template>	
	
</xsl:stylesheet>
