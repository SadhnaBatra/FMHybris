<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common"  xmlns:default="http://www.aaiasoa.net/IPOv2"  xmlns="http://www.aaiasoa.net/IPOv2"
exclude-result-prefixes="default xsi ">
	<xsl:import href="../../CommonRoutines_21.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="environment"/>
	<xsl:variable  name="requestSource" select="document('referenceDocument')" />
	<xsl:template match="/">
		<ShowShipment xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common" xmlns="http://www.aaiasoa.net/IPOv2" xsi:schemaLocation="http://www.aaiasoa.net/IPOv2 ../BODs/ShowShipment.xsd"  >
			<xsl:attribute name="environment" ><xsl:value-of select="$environment"/></xsl:attribute>
			<xsl:call-template name="FMRootNode"/>
		</ShowShipment>
	</xsl:template>
	<xsl:template name="FMRootNode">
		<xsl:call-template name="ApplicationArea"/>
		<xsl:call-template name="DataArea"/>
	</xsl:template>
	<xsl:template name="ApplicationArea">
		<ApplicationArea>
			<cmn:DocumentGUID><xsl:value-of select="$BODId" /></cmn:DocumentGUID>		
			<cmn:Sender>
				<cmn:ReferenceId><xsl:value-of select="$requestSource/default:ShowShipment/default:ApplicationArea/cmn:Sender/cmn:ReferenceId" /></cmn:ReferenceId>
				<cmn:Confirmation>0</cmn:Confirmation>
			</cmn:Sender>
			<cmn:CreationDateTime><xsl:value-of select="$currentTimeStamp" /></cmn:CreationDateTime>
		</ApplicationArea>
	</xsl:template>
	<xsl:template name="DataArea">
		<DataArea>			
			<xsl:for-each select="/ListItems/shippedOrderBO">
				<Shipment>
					<xsl:call-template name="HeaderArea"/>					
					<xsl:variable name="TotalShippingUnits">
						<xsl:value-of select="count(orderUnitList/shippingUnitList)"/>
					</xsl:variable>
					<ShipUnits>
						<xsl:for-each select="orderUnitList/shippingUnitList">
							<xsl:call-template name="ShipUnitArea">
								<xsl:with-param name="index" select="position()"/>
								<xsl:with-param name="TotalShippingUnits" select="$TotalShippingUnits"/>
							</xsl:call-template>
						</xsl:for-each>
					</ShipUnits>
				</Shipment>
			</xsl:for-each>
		</DataArea>
	</xsl:template>
	<xsl:template name="HeaderArea">
		<Header>
			<xsl:call-template name="HeaderDocumentIds"/>
			<xsl:call-template name="HeaderDocumentReferenceIds"/>
			<xsl:call-template name="HeaderAlternateDocumentIds"/>
			<xsl:call-template name="HeaderTransportationTerm"/>			
			<xsl:call-template name="HeaderTotalAmount"/>
			<xsl:call-template name="HeaderFreightCost"/>
			<xsl:call-template name="HeaderActualShippedDate"/>
			<xsl:call-template name="HeaderParties"/>
		</Header>
	</xsl:template>
	<xsl:template name="HeaderActualShippedDate">		
		<xsl:call-template name="createnameValueElement">
				<xsl:with-param name="elementName">ActualShippedDateTime</xsl:with-param>
				<xsl:with-param name="elementValue"><xsl:value-of select="orderUnitList/shippingUnitList/shipDate"/></xsl:with-param>
		</xsl:call-template>	
	</xsl:template>

	<xsl:template name="ShipUnitArea">
		<xsl:param name="TotalShippingUnits"  />
		<xsl:param name="index"  />
		<ShipUnit>
			<ShippingTrackingID>
				<xsl:choose>
					<xsl:when test="trackingNumber!=''">
						<xsl:value-of select="trackingNumber" />
					</xsl:when>
					<xsl:otherwise>Not Available</xsl:otherwise>
				</xsl:choose>
			</ShippingTrackingID>
			<ShipUnitSequence><xsl:value-of select="$index"/></ShipUnitSequence>
			<ShipUnitTotalId><xsl:value-of select="$TotalShippingUnits"/></ShipUnitTotalId>
			<TotalAmount><xsl:value-of select="format-number(totalAmount,'0.00')"/></TotalAmount>
			<FreightCost><xsl:value-of select="format-number(freightCost,'0.00')"/></FreightCost>
			<xsl:call-template name="ShipUnitFrieightTerms"/>
			<xsl:call-template name="createnameValueElement">
					<xsl:with-param name="elementName">ActualShippedDateTime</xsl:with-param>
					<xsl:with-param name="elementValue"><xsl:value-of select="shipDate"/></xsl:with-param>
			</xsl:call-template>

<!--			<ActualShippedDateTime><xsl:value-of select="shipDate"/></ActualShippedDateTime>-->
			<ShipmentInventoryItems>
				<xsl:for-each select="shippedItemsList">
					<xsl:call-template name="ShipInventoryItem"/>
				</xsl:for-each>				
			</ShipmentInventoryItems>
			<xsl:call-template name="ShipUnitShipFrom"/>
		</ShipUnit>
		
	</xsl:template>
	<xsl:template name="HeaderDocumentIds">
		<xsl:apply-templates select="$requestSource/default:GetShipment/default:DataArea/default:Shipment/default:Header/default:DocumentIds" />
	</xsl:template>
	<xsl:template name="HeaderDocumentReferenceIds">
		<xsl:apply-templates select="$requestSource/default:GetShipment/default:DataArea/default:Shipment/default:Header/default:DocumentReferenceIds" />
	</xsl:template>
	<xsl:template name="HeaderAlternateDocumentIds">
		<xsl:apply-templates select="$requestSource/default:GetShipment/default:DataArea/default:Shipment/default:Header/default:AlternateDocumentIds" />
<!--		<AlternateDocumentIds>
			<cmn:CustomerDocumentId>
					<xsl:value-of select="customerPurchaseOrderNum"/>
			</cmn:CustomerDocumentId>
			<cmn:SupplierDocumentId>
					<xsl:value-of select="masterOrderNumber"/>
			</cmn:SupplierDocumentId>
		</AlternateDocumentIds>
-->	
	</xsl:template>
	<xsl:template name="HeaderTotalAmount">
		<TotalAmount>
			<xsl:value-of select="format-number(sum(orderUnitList/shippingUnitList/totalAmount),'0.00')"/>
		</TotalAmount>
	</xsl:template>
	<xsl:template name="HeaderFreightCost">
		<FreightCost>
			<xsl:value-of select="format-number(sum(orderUnitList/shippingUnitList/freightCost),'0.00')"/>
		</FreightCost>
	</xsl:template>
	<xsl:template name="HeaderParties">
		<xsl:for-each select="shipToAccount">
			<ShipToParty>
				<xsl:call-template name="AccountBO"/>
			</ShipToParty>
		</xsl:for-each>
		<xsl:call-template name="HeaderShipFromParty"/>	
	</xsl:template>
	<xsl:template name="HeaderShipFromParty">
		<xsl:for-each select="orderUnitList/shippingUnitList/shipFrom " >
				<xsl:choose>
					<xsl:when test="position() = 1 ">
						<ShipFromParty>
							<cmn:PartyId><xsl:value-of select="code"/></cmn:PartyId>
							<cmn:Name><xsl:value-of select="name"/></cmn:Name>
							<xsl:for-each select="address">
								<xsl:call-template name="AddressBO"/>
							</xsl:for-each>
						</ShipFromParty>
					</xsl:when>
				</xsl:choose>			
			</xsl:for-each>	
	
	</xsl:template>
	<xsl:template name="HeaderTransportationTerm">
		<xsl:for-each select="orderUnitList/shippingUnitList" >
				<xsl:choose>
					<xsl:when test="position() = 1 ">
						<FreightTerms>
							<cmn:FreightTerm>
								<cmn:CommonCarrier freightTermCode=""><xsl:value-of  select="carrierCode"/></cmn:CommonCarrier>
								<cmn:ShippingMethod freightTermCode=""><xsl:value-of  select="shippingMethodCode"/></cmn:ShippingMethod>
								<cmn:TransportationMethod freightTermCode=""><xsl:value-of  select="transportationMethodCode"/></cmn:TransportationMethod>
							</cmn:FreightTerm>
						</FreightTerms>
					</xsl:when>
				</xsl:choose>			
			</xsl:for-each>	
	</xsl:template>
	<xsl:template name="ShipInventoryItem">
		<ShipmentInventoryItem>
			<xsl:call-template name="OrderItem"/>
			<ItemDescription>
				<xsl:value-of select="description"/>
			</ItemDescription>			
			<xsl:call-template name="OrderQuantity"/>			
			<xsl:call-template name="ItemQuantity"/>			
			<xsl:call-template name="BackOrderQuantity"/>
<!--			<xsl:call-template name="LineItemParties"/>-->
			
			
				<!--				
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
          <ItemDescription>Oil Seal                                                    </ItemDescription>
          <ItemQuantity uom="ea">0</ItemQuantity>

				<xsl:call-template name="OrderQuantity">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="BackOrderQuantity">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>

				<xsl:call-template name="Parties">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="LineUserArea">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>

-->	
		</ShipmentInventoryItem>
	</xsl:template>
	<xsl:template name="OrderItem">
			<ShipmentInventoryItemIds>
				<xsl:call-template name="LineCustomerItemId"/>
				<!--<xsl:call-template name="LineSupplierCode"/>-->
				<xsl:call-template name="LineSupplierItemId"/>
			</ShipmentInventoryItemIds>
	</xsl:template>
	<xsl:template name="LineCustomerItemId">
		<cmn:CustomerItemId><xsl:value-of select="displayPartNumber" /></cmn:CustomerItemId>	
	</xsl:template>
	<xsl:template name="LineSupplierCode">
		<xsl:call-template name="createnameValueElement">
				<xsl:with-param name="elementName">cmn:SupplierCode</xsl:with-param>
				<xsl:with-param name="elementValue"><xsl:value-of select="aaiaBrand"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="LineSupplierItemId">
		<cmn:SupplierItemId><xsl:value-of select="partNumber" /></cmn:SupplierItemId>
	</xsl:template>
	<xsl:template name="ItemQuantity">
		<ItemQuantity>
			<xsl:attribute name="uom">
				<xsl:choose>
					<xsl:when test="itemQty/qtyUOM!=''">
						<xsl:value-of select="itemQty/qtyUOM" />
					</xsl:when>
					<xsl:otherwise>EA</xsl:otherwise>
				</xsl:choose>
				
			</xsl:attribute>
			<xsl:value-of select="shippedQuantity" />
		</ItemQuantity>
	</xsl:template>
	
	<xsl:template name="OrderQuantity">
		<OrderQuantity>
			<xsl:attribute name="uom">
				<xsl:choose>
					<xsl:when test="itemQty/qtyUOM!=''">
						<xsl:value-of select="itemQty/qtyUOM" />
					</xsl:when>
					<xsl:otherwise>EA</xsl:otherwise>
				</xsl:choose>
				
			</xsl:attribute>
			<xsl:value-of select="assignedQuantity" />
		</OrderQuantity>
	</xsl:template>
	<xsl:template name="BackOrderQuantity">
		<xsl:choose >
			<xsl:when test="number(itemQty/backorderedQty) >0 ">
				<BackOrderedQuantity>
					<xsl:attribute name="uom">
						<xsl:choose>
							<xsl:when test="itemQty/qtyUOM!=''">
								<xsl:value-of select="itemQty/qtyUOM" />
							</xsl:when>
							<xsl:otherwise>EA</xsl:otherwise>
						</xsl:choose>
					</xsl:attribute>
					<xsl:value-of select="backorderedQuantity" />
				</BackOrderedQuantity>		
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="LineItemParties">
		<Parties>
			<xsl:choose>
				<xsl:when test="../shipFrom/address">
					<xsl:for-each select="../shipFrom" >
						<ShipFromParty>
							<cmn:PartyId>
								<xsl:value-of select="code"/>
								<xsl:call-template name="ShipFromPartyScacCodeCustom">
								</xsl:call-template>								
							</cmn:PartyId>
							<cmn:Name><xsl:value-of select="name"/></cmn:Name>
							<xsl:for-each select="address">
								<xsl:call-template name="AddressBO"/>
							</xsl:for-each>							
						</ShipFromParty>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:for-each select="../shipFrom" >
						<xsl:call-template name="ShipFromPartyCustom"/>
					</xsl:for-each>
				</xsl:otherwise>
			</xsl:choose>
		</Parties>
	</xsl:template>
	<xsl:template name="ShipFromPartyCustom">
		<ShipFromParty>
			<cmn:PartyId>
				<xsl:call-template name="ShipFromPartyScacCodeCustom"/>
			</cmn:PartyId>
		</ShipFromParty>				
		
	</xsl:template>
	
	<xsl:template name="ShipFromPartyScacCodeCustom">
		<SCAC>
			<xsl:value-of select="scacCode"/>
		</SCAC>		
	</xsl:template>
	<xsl:template name="ShipUnitFrieightTerms">
		<xsl:choose>
			<xsl:when test="position() = 1 ">
				<FreightTerms>
					<cmn:FreightTerm>
						<cmn:CommonCarrier freightTermCode=""><xsl:value-of  select="carrierCode"/></cmn:CommonCarrier>
						<cmn:ShippingMethod freightTermCode=""><xsl:value-of  select="shippingMethodCode"/></cmn:ShippingMethod>
						<cmn:TransportationMethod freightTermCode=""><xsl:value-of  select="transportationMethodCode"/></cmn:TransportationMethod>
					</cmn:FreightTerm>
				</FreightTerms>
			</xsl:when>
		</xsl:choose>			
	
	</xsl:template>
	<xsl:template name="ShipUnitShipFrom">
			<ShipFrom><xsl:value-of select="shipFrom/name"/></ShipFrom>	
	</xsl:template>
	
</xsl:stylesheet>
