<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  >
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>	
	<xsl:variable name="allowedParties">
		BillToParty
		ShipToParty
	</xsl:variable>
	<xsl:variable name="upper">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable> 
	<xsl:variable name="lower">abcdefghijklmnopqrstuvwxyz</xsl:variable> 
	<xsl:variable name="ipoType"></xsl:variable>	
	<xsl:variable name="allowedIpoTypes">
		RequestForQuote
		PurchaseOrder
	</xsl:variable>

	<xsl:template match="@*|node()" >
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		  </xsl:copy>	
	</xsl:template>

	<xsl:template match="text()">
		<xsl:value-of select="normalize-space(.)"/>
	</xsl:template>

	<xsl:template match="*">
		<xsl:element name="{substring(name(), 1, string-length(name())-string-length(local-name()))}{translate(substring(local-name(), 1, 1),$upper,$lower)}{substring(local-name(), 2, string-length(local-name()) - 1)}">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

	<xsl:template name="FMRootNode">
		<OrderBO >	
		    <orderType>EMERGENCY</orderType>
		    <orderSource>IPO</orderSource>
			<xsl:call-template name="ApplicationArea" />
			<xsl:call-template name="Header" />
			<xsl:call-template name="Lines" />
		</OrderBO>
	</xsl:template>

	<xsl:template name="ApplicationArea">
	    <!--TODO . Area we using this-->
		<xsl:for-each select="default:ApplicationArea/descendant::*">				
			<xsl:choose>
				<xsl:when test="local-name(.)='FMIPOSecretKey'">
					<tpSecretKey>
						<xsl:value-of select="."/>
					</tpSecretKey>
				</xsl:when>
				<xsl:otherwise>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>

	<!-- Header START-->
	<xsl:template name="Header">
		<xsl:call-template name="ShipNote" />
		<dropShipInd>
			<xsl:choose>
				<xsl:when test="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DropShipInd" >
					<xsl:value-of select="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DropShipInd"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>0</xsl:text> 
				</xsl:otherwise>
			</xsl:choose>
		</dropShipInd>		
		<xsl:call-template name="custPoNbr"/>
		<!--	<xsl:call-template name="orderType" />-->
		<xsl:call-template name="Parties" />
	</xsl:template>

	<xsl:template name="ShipNote">
		<comment1>
			<xsl:choose>
				<xsl:when test="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:ShipNote" >
					<xsl:value-of select="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:ShipNote"/>
				</xsl:when>
				<xsl:otherwise>
				</xsl:otherwise>
			</xsl:choose>
		</comment1>
	</xsl:template>

<!--	
	<xsl:template name="orderType">
		<orderType><xsl:value-of select="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:ReasonCode"/></orderType>
	</xsl:template>
-->	

	<xsl:template name="custPoNbr">
		<custPoNbr>
			<xsl:value-of select="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds/default:CustomerDocumentId/default:Id" />
		</custPoNbr>
	</xsl:template>

	<xsl:template name="Parties">
		<xsl:for-each select="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:Parties/default:*[substring(name(),(string-length(name())-6))='ToParty']">
			<xsl:call-template name="endswith_ToParty"/>
		</xsl:for-each>		
	</xsl:template>

	<xsl:template name="endswith_ToParty">
		<xsl:element name="{translate(substring(local-name(), 1, 1),$upper,$lower)}{substring(local-name(),2,(string-length(local-name())-8)) }ToAcct">
			<xsl:element name="accountCode"><xsl:value-of select="default:PartyId/default:Id"/></xsl:element>
			<xsl:element name="accountName"><xsl:value-of select="substring(default:Name,1,30)"/></xsl:element>
			<xsl:element name="address">
				<xsl:element name="addrLine1"><xsl:value-of select="default:Addresses/default:Address/default:AddressLine[1]"/></xsl:element>
				<xsl:element name="addrLine2"><xsl:value-of select="default:Addresses/default:Address/default:AddressLine[2]"/></xsl:element>
				<xsl:element name="city"><xsl:value-of select="default:Addresses/default:Address/default:City"/></xsl:element>
				<xsl:element name="stateOrProv"><xsl:value-of select="default:Addresses/default:Address/default:StateOrProvince"/></xsl:element>
				<xsl:element name="country">
					<xsl:element name="isoCountryCode">
							<xsl:value-of select="default:Addresses/default:Address/default:Country"/>
					</xsl:element>
				</xsl:element>
				<xsl:element name="zipOrPostalCode"><xsl:value-of select="default:Addresses/default:Address/default:PostalCode"/></xsl:element>				
			</xsl:element>
		</xsl:element>
	</xsl:template>
<!-- Header END-->	

<!-- Line START -->	
	<xsl:template name="Lines">
		<xsl:for-each select="default:DataArea/default:*[contains($allowedIpoTypes,$ipoType)]/aaia:Line">			
			<xsl:apply-templates select="." />
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="default:DataArea/default:*/aaia:Line" >
		<xsl:element name="parts">
			<xsl:element name="backOrderInd">0</xsl:element>	 <!-- TODO read from inbound BOD-->
			<xsl:call-template name="shipFromScacCode" />
			<xsl:for-each select="default:Parties/default:ShipFromParty">
				<requestedInventory>
					<distributionCenter>
						<code><xsl:value-of select="default:PartyId/default:Id"/></code>
						<name><xsl:value-of select="default:Name"/></name>
<!-- 						<xsl:apply-templates select="default:Addresses/default:Address"/> -->						
						<xsl:element name="address">
							<xsl:element name="addrLine1"><xsl:value-of select="default:Addresses/default:Address/default:AddressLine[1]"/></xsl:element>
							<xsl:element name="addrLine2"><xsl:value-of select="default:Addresses/default:Address/default:AddressLine[2]"/></xsl:element>
							<xsl:element name="city"><xsl:value-of select="default:Addresses/default:Address/default:City"/></xsl:element>
							<xsl:element name="stateOrProv"><xsl:value-of select="default:Addresses/default:Address/default:StateOrProvince"/></xsl:element>
							<xsl:element name="country">
								<xsl:element name="isoCountryCode">
										<xsl:value-of select="default:Addresses/default:Address/default:Country"/>
								</xsl:element>
							</xsl:element>
							<xsl:element name="zipOrPostalCode"><xsl:value-of select="default:Addresses/default:Address/default:PostalCode"/></xsl:element>				
						</xsl:element>
					</distributionCenter>
				</requestedInventory>
 			</xsl:for-each>
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>

	<xsl:template match="default:DataArea/default:*/aaia:Line/default:LineNumber">
		<xsl:element name="lineNumber">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="default:DataArea/default:*/aaia:Line/default:OrderItem">
		<xsl:call-template name="OrderItem"/>
	</xsl:template>

	<xsl:template match="default:DataArea/default:*/aaia:Line/aaia:OrderItem">
			<xsl:call-template name="OrderItem"/>
	</xsl:template>

	<xsl:template name="OrderItem">
		<xsl:call-template name="displayPartNumber"/>
		<xsl:call-template name="aaiaBrand"/>
		<xsl:call-template name="OrderItemCustom"/>
	</xsl:template>

	<xsl:template name="displayPartNumber">
		<xsl:element name="displayPartNumber">
			<xsl:choose>
				<xsl:when test="aaia:ItemIds/default:CustomerItemId/default:Id">
					<xsl:value-of select="aaia:ItemIds/default:CustomerItemId/default:Id"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="aaia:ItemIds/aaia:SupplierItemId/default:Id"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>

	<xsl:template name="aaiaBrand">
		<xsl:element name="aaiaBrand">
			<xsl:value-of select="aaia:ItemIds/aaia:SupplierCode"/>
		</xsl:element>			
	</xsl:template>

	<xsl:template name="OrderItemCustom"></xsl:template>

	<xsl:template match="default:DataArea/default:*/aaia:Line/default:OrderQuantity">
		<xsl:element name="itemQty">
			<xsl:element name="requestedQuantity"><xsl:value-of select="number(.)"/></xsl:element>			
			<xsl:element name="qtyUomCd"><xsl:value-of select="@uom"/></xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template match="default:DataArea/default:*/aaia:Line/default:BackOrderedInd" />

	<xsl:template match="default:DataArea/default:*/aaia:Line/default:Note">
		<comment><xsl:value-of select="."/></comment>
	</xsl:template>

<!-- 
	<xsl:template match="default:DataArea/default:*/aaia:Line/default:Parties/default:ShipFromParty">
	</xsl:template>
 -->
  
	<xsl:template match="default:DataArea/default:*/aaia:Line/default:UserArea" >
		<xsl:choose>
			<xsl:when test="default:CarrierParty/default:PartyId/default:SCAC">
				<xsl:element name="scacCode">
					<xsl:value-of select="default:CarrierParty/default:PartyId/default:SCAC" />
				</xsl:element>		
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="shipFromScacCode">
		<xsl:choose>
			<xsl:when test="default:Parties/default:ShipFromParty/default:PartyId/default:SCAC">
				<xsl:element name="scacCode">
					<xsl:value-of select="default:Parties/default:ShipFromParty/default:PartyId/default:SCAC" />
				</xsl:element>		
			</xsl:when>
		</xsl:choose>
	
	</xsl:template>
	<!-- Line END-->
</xsl:stylesheet>
