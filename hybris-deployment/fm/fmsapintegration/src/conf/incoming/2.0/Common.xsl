<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
		xmlns="http://www.aaiasoa.net/IPOv2" xmlns:default="http://www.aaiasoa.net/IPOv2" xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fn="http://www.w3.org/2006/xpath-functions" >
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

	<xsl:variable name="allowedParties">
		BillToParty
		ShipToParty
	</xsl:variable>
	<xsl:variable name="upper">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable> 
	<xsl:variable name="lower">abcdefghijklmnopqrstuvwxyz</xsl:variable> 
	<xsl:variable name="rootNode"></xsl:variable>
	<xsl:variable name="ipoType"></xsl:variable>

	<xsl:template match="@*|node()" >
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>	
	</xsl:template>

	<xsl:template match="text()">
		<xsl:value-of select="normalize-space(.)"/>
	</xsl:template>

	<xsl:template match="*">
		<xsl:element name="{translate(substring(local-name(), 1, 1),$upper,$lower)}{substring(local-name(), 2, string-length(local-name()) - 1)}">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="rename">
		<xsl:param name="elementName"  />
		<xsl:element name="{$elementName}">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="FMRootNode">
		<OrderBO>	
			<orderType>EMERGENCY</orderType>
			<orderSource>IPO</orderSource>
			<xsl:call-template name="ApplicationArea" />
			<xsl:call-template name="Header" />
			<xsl:call-template name="Lines" />
		</OrderBO>
	</xsl:template>

	<xsl:template name="ApplicationArea">
		<xsl:element name="tpSecretKey">
			<xsl:value-of select="/*[local-name()=$rootNode ]/default:ApplicationArea/cmn:UserArea/cmn:Any/default:FMIPOSecretKey"/>
		</xsl:element>
	</xsl:template>

	<xsl:template name="Header">
		<xsl:element name="dropShipInd">0</xsl:element>
		<xsl:call-template name="custPoNbr"/>
		<xsl:call-template name="Parties"/>
	</xsl:template>	

	<xsl:template name="custPoNbr"/>

	<xsl:template name="Parties">
		<xsl:for-each select="/*[local-name()=$rootNode ]/default:DataArea/*[local-name()=$ipoType ]/default:Header/default:*[ substring(local-name(),string-length(local-name())-6)  ='ToParty']">
			<xsl:call-template name="endswith_ToParty"/>
		</xsl:for-each>		
	</xsl:template>

	<xsl:template name="endswith_ToParty">
		<xsl:element name="{translate(substring(local-name(), 1, 1),$upper,$lower)}{substring(local-name(),2,(string-length(local-name())-8)) }ToAcct">
			<xsl:element name="accountCode"><xsl:value-of select="cmn:PartyId"/></xsl:element>
			<xsl:element name="accountName"><xsl:value-of select="substring(cmn:Name,1,30)"/></xsl:element>
			<xsl:element name="address">
				<xsl:element name="addrLine1"><xsl:value-of select="cmn:AddressLine[1]"/></xsl:element>
				<xsl:element name="addrLine2"><xsl:value-of select="cmn:AddressLine[2]"/></xsl:element>
				<xsl:element name="city"><xsl:value-of select="cmn:City"/></xsl:element>
				<xsl:element name="stateOrProv"><xsl:value-of select="cmn:StateOrProvince"/></xsl:element>
				<xsl:element name="country">
					<xsl:element name="isoCountryCode">
							<xsl:value-of select="cmn:Country"/>
					</xsl:element>
<!--				<xsl:value-of select="cmn:Country"/>-->
				</xsl:element>
				<xsl:element name="zipOrPostalCode"><xsl:value-of select="cmn:PostalCode"/></xsl:element>				
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="Lines">
		<xsl:for-each select="/*[local-name()=$rootNode ]/default:DataArea/*[local-name()=$ipoType ]/default:Lines/default:Line">
			<xsl:element name="parts">
				<backOrderInd>0</backOrderInd>
				<xsl:call-template name="OrderItem"/>
				<xsl:call-template name="OrderQuantity"/>
				<xsl:call-template name="LineAny"/>
				<xsl:call-template name="FreightTerms"/>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>		

    <xsl:template name="OrderItem">
		<xsl:element name="lineNumber">
			<xsl:value-of select="default:LineNumber"/>
		</xsl:element>
		<xsl:element name="displayPartNumber">
			<xsl:value-of select="default:OrderItem/cmn:CustomerItemId"/>
		</xsl:element>
		<xsl:element name="aaiaBrand">
			<xsl:value-of select="default:OrderItem/cmn:SupplierCode"/>
		</xsl:element>		
    </xsl:template>

    <xsl:template name="OrderQuantity">
		<xsl:element name="itemQty">
			<xsl:element name="requestedQuantity"><xsl:value-of select="default:OrderQuantity"/></xsl:element>
			<xsl:element name="qtyUOM"><xsl:value-of select="default:OrderQuantity/@uom"/></xsl:element>
		</xsl:element>
    </xsl:template>

    <xsl:template name="LineAny">
	</xsl:template>
    
    <xsl:template name="FreightTerms">
		<xsl:choose>
			<xsl:when test="default:FreightTerms">
				<xsl:call-template name="LineFreightTerms"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="HeaderFreightTerms"/>
			</xsl:otherwise>
		</xsl:choose>
    </xsl:template>

    <xsl:template name="LineFreightTerms">
		<shippingMethodCode><xsl:value-of select="default:FreightTerms/cmn:FreightTerm/cmn:ShippingMethod"/></shippingMethodCode>
		<transportationMethodCode><xsl:value-of select="default:FreightTerms/cmn:FreightTerm/cmn:TransportationMethod"/></transportationMethodCode>
		<carrierCode><xsl:value-of select="default:FreightTerms/cmn:FreightTerm/cmn:CommonCarrier"/></carrierCode>
    </xsl:template>

    <xsl:template name="HeaderFreightTerms">
		<shippingMethodCode><xsl:value-of select="/*[local-name()=$rootNode ]/default:DataArea/*[local-name()=$ipoType ]/default:Header/default:FreightTerms/cmn:FreightTerm/cmn:ShippingMethod"/></shippingMethodCode>
		<transportationMethodCode><xsl:value-of select="/*[local-name()=$rootNode ]/default:DataArea/*[local-name()=$ipoType ]/default:Header/default:FreightTerms/cmn:FreightTerm/cmn:TransportationMethod"/></transportationMethodCode>
		<carrierCode><xsl:value-of select="/*[local-name()=$rootNode ]/default:DataArea/*[local-name()=$ipoType ]/default:Header/default:FreightTerms/cmn:FreightTerm/cmn:CommonCarrier"/></carrierCode>
    </xsl:template>
</xsl:stylesheet>
