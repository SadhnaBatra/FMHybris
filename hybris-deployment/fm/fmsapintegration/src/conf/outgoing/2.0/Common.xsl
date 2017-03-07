<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common"  xmlns:default="http://www.aaiasoa.net/IPOv2">
	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>

	<xsl:variable  name="requestSource" select="document('referenceDocument')" />
	<xsl:variable name="upper">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable> 
	<xsl:variable name="lower">abcdefghijklmnopqrstuvwxyz</xsl:variable> 
	<xsl:variable name="allowedIpoTypes"><xsl:text>RequestForQuote,PurchaseOrder</xsl:text></xsl:variable>
	<xsl:variable name="allowedRootNodes"><xsl:text>aaia:ProcessPurchaseOrder,default:AddRequestForQuote</xsl:text></xsl:variable>	
	<xsl:variable name="HeaderNodeTag">Header</xsl:variable>
	<xsl:variable name="EffectivePeriodTag">EffectivePeriod</xsl:variable>
	<xsl:variable name="rootNode"></xsl:variable>
	<xsl:variable name="ipoType"></xsl:variable>

	<xsl:param name="currentTimeStamp" ></xsl:param>
	<xsl:param name="BODId"   />
	<xsl:param name="EffectivePeriod"   />
	<xsl:param name="nextBusinessdayFromTomorrow"  />
		<xsl:param name="SevenDaysFromToday"  />

	

	
	<xsl:template match="@*|node()" >
			<xsl:copy >
				<xsl:apply-templates select="@*|node()"  />
			  </xsl:copy>	
	</xsl:template>
	<xsl:template match="*">
		<xsl:element name="{substring(name(), 1, string-length(name())-string-length(local-name()))}{translate(substring(local-name(), 1, 1),$lower,$upper)}{substring(local-name(), 2, string-length(local-name()) - 1)}">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

	<xsl:template name="populateValue">
		<xsl:param name="value"  />
		<xsl:choose>
			<xsl:when test="$value !=''">
				<xsl:element name="{local-name()}">
					<xsl:value-of select="$value"/>
				</xsl:element>
			</xsl:when>
		</xsl:choose>		
	</xsl:template>
	<xsl:template name="rename">
		<xsl:param name="elementName"  />
		<xsl:element name="{$elementName}">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	<xsl:template name="createnameValueElement">
		<xsl:param name="elementName"  />
		<xsl:param name="elementValue"  />
		<xsl:choose>
			<xsl:when test="$elementValue !='' " >
				<xsl:element name="{$elementName}">
					<xsl:value-of select="$elementValue"/>
				</xsl:element>
			</xsl:when>
		</xsl:choose>		
	</xsl:template>
	<xsl:template name="AccountBO">
		<xsl:choose>
			<xsl:when test="accountCode !='' ">
				<cmn:PartyId>
						<xsl:value-of select="accountCode"/>
				</cmn:PartyId>			
			</xsl:when>
		</xsl:choose>
		<cmn:Name><xsl:value-of select="accountName"/></cmn:Name>
		<xsl:for-each select="address">
			<xsl:call-template name="AddressBO"/>
		</xsl:for-each>
		
	</xsl:template>
	<xsl:template name="AddressBO">
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">cmn:AddressLine</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="addrLine1"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">cmn:AddressLine</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="addrLine2"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">cmn:City</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="city"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">cmn:StateOrProvince</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="stateOrProv"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">cmn:PostalCode</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="zipOrPostalCode"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">cmn:Country</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="country/isoCountryCode"/></xsl:with-param>
		</xsl:call-template>
	
		
	</xsl:template>
	<xsl:template match="/OrderBO/parts/backorder">
		<xsl:call-template name="rename">
			<xsl:with-param name="elementName" >BackOrderedInd</xsl:with-param>
		</xsl:call-template>
	</xsl:template>	

	
	<xsl:template name="FMRootNode">
		<xsl:call-template name="ApplicationArea"/>
		<xsl:call-template name="DataArea"/>
	</xsl:template>
	<xsl:template name="ApplicationArea">
		<ApplicationArea>
			<cmn:DocumentGUID><xsl:value-of select="$BODId" /></cmn:DocumentGUID>		
			<cmn:Sender>
				<cmn:ReferenceId><xsl:value-of select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:ApplicationArea/cmn:Sender/cmn:ReferenceId" /></cmn:ReferenceId>
				<cmn:Confirmation>0</cmn:Confirmation>
			</cmn:Sender>
			<cmn:CreationDateTime><xsl:value-of select="$currentTimeStamp" /></cmn:CreationDateTime>
		</ApplicationArea>
	</xsl:template>
	<xsl:template name="DataArea"/>
		
	<xsl:template name="WeightUOMType">
			<WeightUOM>
				<xsl:value-of select="/OrderBO/weightUOM" />
			</WeightUOM>			
	</xsl:template>
	
	<xsl:template name="DocumentIdsType">	
		<DocumentIds>
			<xsl:call-template name="DocumentIdsTypeCustom"/>
		</DocumentIds>
	</xsl:template>
	<xsl:template name="DocumentIdsTypeCustom"/>
	
	<xsl:template name="DocumentReferenceIdsType">
		<DocumentReferenceIds>
			<xsl:call-template name="DocumentReferenceIdsTypeCustom"/>
		</DocumentReferenceIds>
	</xsl:template>
	<xsl:template name="DocumentReferenceIdsTypeCustom"/>
	<xsl:template name="NoteType">
		<xsl:choose>
			<xsl:when test="/OrderBO/comment1 or /OrderBO/comment2 or /OrderBO/comment3">
				<Note>
					<xsl:value-of select="/OrderBO/comment1"/><xsl:value-of select="/OrderBO/comment2"/><xsl:value-of select="/OrderBO/comment3"/>
				</Note>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="PromisedShipDateType">
		<xsl:for-each select="/OrderBO/parts/inventory[selectedLocation='true']" >
			<xsl:choose>
				<xsl:when test="position() = 1">
					<PromisedShipDate>
						<xsl:value-of select="../promisedShipDateIsoDateTime"/>
					</PromisedShipDate>
				</xsl:when>
			</xsl:choose>					
			</xsl:for-each>
<!--		<PromisedShipDate><xsl:value-of select="$currentTimeStamp"/></PromisedShipDate>-->
	</xsl:template>
	<xsl:template name="TransportationTermType">
		<xsl:apply-templates  select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:TransportationTermCode" />
	</xsl:template>	
	<xsl:template name="FreightTermsType">
	
			<xsl:choose>
				<xsl:when test="$requestSource/*[local-name()=$rootNode ]/default:DataArea/*[local-name()=$ipoType ]/default:Header/default:FreightTerms">
					<xsl:apply-templates  select="$requestSource/*[local-name()=$rootNode ]/default:DataArea/*[local-name()=$ipoType ]/default:Header/default:FreightTerms"/>
<!--					<FreightTerms>
						<cmn:FreightTerm>
							<cmn:CommonCarrier freightTermCode=""><xsl:value-of select="commomCarrier"/></cmn:CommonCarrier>
							<cmn:ShippingMethod freightTermCode=""><xsl:value-of select="shipMethod"/></cmn:ShippingMethod>
							<cmn:TransportationMethod freightTermCode=""><xsl:value-of select="transportationMethod"/></cmn:TransportationMethod>			
						</cmn:FreightTerm>
					</FreightTerms>
-->				</xsl:when>
			</xsl:choose>
	
	</xsl:template>
	<xsl:template name="HeaderExtendedPriceType">

			<xsl:choose>
				<xsl:when test="number(/OrderBO/totalItemPrice) > 0">
					<ExtendedPrice>
						<xsl:value-of select="/OrderBO/totalItemPrice" />
					</ExtendedPrice>		
				</xsl:when>
			</xsl:choose>			

	</xsl:template>
	<xsl:template name="HeaderTotalPriceType">		
			<xsl:choose>
				<xsl:when test="number(/OrderBO/totalItemPrice) > 0">
					<TotalAmount>
						<xsl:value-of select="/OrderBO/totalItemPrice" />
					</TotalAmount>		
				</xsl:when>
			</xsl:choose>			
		
	</xsl:template>
		<xsl:template name="PaymentTermsType">	
	</xsl:template>
	<xsl:template name="HeaderBillToPartyType">
			<xsl:for-each select="/OrderBO/billToAcct">
				<BillToParty>
					<xsl:call-template name="AccountBO" />
				</BillToParty>
			</xsl:for-each>			
		
	</xsl:template>
	<xsl:template name="HeaderShipToPartyType">
		<xsl:for-each select="/OrderBO/shipToAcct">	
			<ShipToParty>
				<xsl:choose>
					<xsl:when test="not(accountCode) or accountCode = ''">
						<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:ShipFromParty/default:PartyId"/>
					</xsl:when>
				</xsl:choose>
				<xsl:call-template name="AccountBO" />
			</ShipToParty>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="HeaderSoldToPartyType">
		<xsl:apply-templates  select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/cmn:SoldToParty"/>
	</xsl:template>
	<xsl:template name="HeaderHostToPartyType">
			<xsl:apply-templates  select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/cmn:HostToParty"/>
	</xsl:template>
	<xsl:template name="HeaderUserArea"></xsl:template>
	<!--	Header Data area sub templates END-->
	<!--Data area Line area START-->
	
	
	<xsl:template name="WeightType">
		<xsl:param name="index"  />
		
			<xsl:choose>
				<xsl:when test="number(/OrderBO/parts[number($index)]/itemWeight/weight) > 0">
					<Weight>
						<xsl:value-of select="format-number(/OrderBO/parts[number($index)]/itemWeight/weight * /OrderBO/parts[number($index)]/orderedQty,'0.00') "/>
					</Weight>
				</xsl:when>
			</xsl:choose>
	</xsl:template>
	
	
	
	
	<xsl:template name="OrderItemType">
		<xsl:param name="index"  />
		<OrderItem>
			
			<cmn:CustomerItemId><xsl:value-of select="/OrderBO/parts[number($index)]/displayPartNumber"/></cmn:CustomerItemId>
		<xsl:call-template name="LineSupplierItemId">
				<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>	
<!--			<xsl:call-template name="LineSupplierCode">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
-->		<xsl:call-template name="LineBrandAAIAId">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
			<!--		<xsl:call-template name="LineManufacturerItemId">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="LineManufacturerCode">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="LineManufacturerName">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
	-->
		<cmn:Description><xsl:value-of select="/OrderBO/parts[number($index)]/fullDescription"/></cmn:Description>
			
	<!--
			<xsl:call-template name="LinePartTypes">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="LineNPDCode">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
	-->		
		</OrderItem>
	</xsl:template>
	<xsl:template name="ItemStatusChangesType">
		<xsl:param name="index"  />	
		<xsl:choose>
			<xsl:when test="/OrderBO/parts[number($index)]/problemItem/ipoMessage != '' or (/OrderBO/parts[number($index)]/problemItem/ipoMessage) "  >
				<ItemStatusChanges>
					<cmn:ItemStatusChange>
							<cmn:To>
								<xsl:value-of select="/OrderBO/parts[number($index)]/problemItem/ipoCode"/>
							</cmn:To>
							<cmn:Description>
								<xsl:value-of select="/OrderBO/parts[number($index)]/problemItem/ipoMessage"/>
							</cmn:Description>
					</cmn:ItemStatusChange>
				</ItemStatusChanges>
			</xsl:when>
		</xsl:choose>
		
	</xsl:template>
	<xsl:template name="OrderQuantityType">
		<xsl:param name="index"  />
		<OrderQuantity>
			<xsl:attribute name="uom">
				<xsl:choose>
					<xsl:when test="/OrderBO/parts[number($index)]/itemQty/qtyUOM!=''">
						<xsl:value-of select="/OrderBO/parts[number($index)]/itemQty/qtyUOM" />
					</xsl:when>
					<xsl:otherwise>EA</xsl:otherwise>
				</xsl:choose>
				
			</xsl:attribute>
			<xsl:value-of select="/OrderBO/parts[number($index)]/orderedQty" />
		</OrderQuantity>
	</xsl:template>
	<xsl:template name="BackOrderQuantityType">
		<xsl:param name="index"  />
			<BackOrderedQuantity>
				<xsl:attribute name="uom">
					<xsl:choose>
						<xsl:when test="/OrderBO/parts[number($index)]/itemQty/qtyUOM!=''">
							<xsl:value-of select="/OrderBO/parts[number($index)]/itemQty/qtyUOM" />
						</xsl:when>
						<xsl:otherwise>EA</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				<xsl:choose>
					<xsl:when test="/OrderBO/parts[number($index)]/itemQty/backorderedQty !='' ">
						<xsl:value-of select="/OrderBO/parts[number($index)]/itemQty/backorderedQty" />
					</xsl:when>
					<xsl:otherwise>0</xsl:otherwise>
				</xsl:choose>
				
			</BackOrderedQuantity>		
	</xsl:template>
	<xsl:template name="LinePricesType">
		<xsl:param name="index"  />
		<xsl:choose>
			<xsl:when test="number(/OrderBO/parts[number($index)]/itemPrice/displayPrice) > 0">
				<Prices>
					<cmn:Pricing>
						<cmn:PriceType><xsl:value-of select="/OrderBO/parts[number($index)]/itemPrice/priceType" /></cmn:PriceType>
						<cmn:Price>
							<xsl:value-of select="format-number(/OrderBO/parts[number($index)]/itemPrice/displayPrice,'0.00')"/>
						</cmn:Price>
					</cmn:Pricing>
				</Prices>
			</xsl:when>
		</xsl:choose>											
	</xsl:template>
	<xsl:template name="LineFreightTerms">
		<xsl:param name="index"  />
		<!--todo. only if the value is not null-->
		<FreightTerms>
			<cmn:FreightTerm>
				<cmn:CommonCarrier freightTermCode=""><xsl:value-of select="/OrderBO/parts[number($index)]/carrierCode"/></cmn:CommonCarrier>
				<cmn:ShippingMethod freightTermCode=""><xsl:value-of select="/OrderBO/parts[number($index)]/shippingMethodCode"/></cmn:ShippingMethod>
				<cmn:TransportationMethod freightTermCode=""><xsl:value-of select="/OrderBO/parts[number($index)]/transportationMethodCode"/></cmn:TransportationMethod>
			</cmn:FreightTerm>
		</FreightTerms>
	</xsl:template>
	<xsl:template name="LineExtendedPrice">
		<xsl:param name="index"  />
		
			<xsl:choose>
				<xsl:when test="number(/OrderBO/parts[number($index)]/itemPrice/displayPrice) > 0">
					<ExtendedPrice>
						<xsl:value-of select="format-number(/OrderBO/parts[number($index)]/itemPrice/displayPrice * /OrderBO/parts[number($index)]/orderedQty,'0.00') "/>
					</ExtendedPrice>
				</xsl:when>
			</xsl:choose>
	</xsl:template>
	<xsl:template name="LineAdditionalCharges">
		<xsl:param name="index"  />
	</xsl:template>
	<xsl:template name="LineTotalAmount">
		<xsl:param name="index"  />		
			<xsl:choose>
				<xsl:when test="number(/OrderBO/parts[number($index)]/itemPrice/displayPrice) > 0">
					<TotalAmount>
						<xsl:value-of select="format-number(/OrderBO/parts[number($index)]/itemPrice/displayPrice * /OrderBO/parts[number($index)]/orderedQty,'0.00') "/>
					</TotalAmount>
				</xsl:when>
			</xsl:choose>
	</xsl:template>
	<xsl:template name="LinePromisedShipDate">		
		<xsl:param name="index"  />
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/inventory/selectedLocation='true' ">
					<PromisedShipDate>
						<xsl:value-of select="/OrderBO/parts[number($index)]/promisedShipDateIsoDateTime"/>
					</PromisedShipDate>
				</xsl:when>
			</xsl:choose>					
	</xsl:template>
	<xsl:template name="LineNote">
		<xsl:param name="index"  />
		<xsl:choose>
			<xsl:when test="/OrderBO/parts[number($index)]/comment">
				<Note><xsl:value-of select=" /OrderBO/parts[number($index)]/comment"/></Note>
			</xsl:when>
		</xsl:choose>		
	</xsl:template>
	<xsl:template name="LineShipFromParty">
		<xsl:param name="index"  />
		<!--todo. only if the value is not null-->		
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/inventory/selectedLocation='true' ">
					<xsl:for-each select="/OrderBO/parts[number($index)]/inventory[selectedLocation='true']" >
						<ShipFromParty>
							<cmn:PartyId><xsl:value-of select="distributionCenter/code"/></cmn:PartyId>
							<cmn:Name><xsl:value-of select="distributionCenter/name"/></cmn:Name>
							<xsl:for-each select="distributionCenter/address">
								<xsl:call-template name="AddressBO"/>
							</xsl:for-each>
							
							
						</ShipFromParty>
					</xsl:for-each>
				</xsl:when>						
			</xsl:choose>
	</xsl:template>
	<xsl:template name="LineSupplierItemId">
		<xsl:param name="index"  />
		<cmn:SupplierItemId><xsl:value-of select="/OrderBO/parts[number($index)]/partNumber" /></cmn:SupplierItemId>
	</xsl:template>
	
	<xsl:template name="LineBrandAAIAId">
		<xsl:param name="index"  />
		<xsl:call-template name="createnameValueElement">
				<xsl:with-param name="elementName">cmn:BrandAAIAId</xsl:with-param>
				<xsl:with-param name="elementValue"><xsl:value-of select="/OrderBO/parts[number($index)]/aaiaBrand"/></xsl:with-param>
		</xsl:call-template>		
	</xsl:template>
<!--	<xsl:template name="LineSupplierCode">
		<xsl:param name="index"  />
		<xsl:call-template name="createnameValueElement">
				<xsl:with-param name="elementName">cmn:SupplierCode</xsl:with-param>
				<xsl:with-param name="elementValue"><xsl:value-of select="/OrderBO/parts[number($index)]/aaiaBrand"/></xsl:with-param>
		</xsl:call-template>		
	</xsl:template>
-->	<xsl:template name="LineUserArea">
		<xsl:param name="index"  />
	</xsl:template>
	<!--Data area Line area END-->
	
</xsl:stylesheet>

