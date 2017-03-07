<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="aaia default xsi ">
	<xsl:import href="../../CommonRoutines_12.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="environment"/>
	<xsl:variable  name="requestSource" select="document('referenceDocument')" />
	<xsl:template match="/">
		<aaia:ShowShipment xmlns:oa="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.aftermarket.org/oagis ../BODs/ShowShipment.xsd" revision="1.2.1" lang="en">
			<xsl:attribute name="environment" ><xsl:value-of select="$environment"/></xsl:attribute>
			<xsl:call-template name="FMRootNode"/>
		</aaia:ShowShipment>
	</xsl:template>
	<xsl:template name="FMRootNode">
		<xsl:call-template name="ApplicationArea"/>
		<xsl:call-template name="DataArea"/>
	</xsl:template>
	<xsl:template name="ApplicationArea">
		<ApplicationArea xmlns="">
			<Sender>
				<xsl:choose>
					<xsl:when test="$requestSource/aaia:GetShipment/default:ApplicationArea/default:Sender/default:ReferenceId">
						<ReferenceId>
							<xsl:value-of select="$requestSource/aaia:GetShipment/default:ApplicationArea/default:Sender/default:ReferenceId"/>
						</ReferenceId>
					</xsl:when>
				</xsl:choose>
				<Confirmation>0</Confirmation>
			</Sender>
			<CreationDateTime>
				<xsl:value-of select="$currentTimeStamp"/>
			</CreationDateTime>
			<BODId>
				<xsl:value-of select="$BODId "/>
			</BODId>
		</ApplicationArea>
	</xsl:template>
	<xsl:template name="DataArea">
		<DataArea>
			<Show confirm="Always"/>
			<xsl:for-each select="/ListItems/shippedOrderBO">
				<Shipment>
					<xsl:call-template name="HeaderArea"/>
					
					<xsl:variable name="TotalShippingUnits">
						<xsl:value-of select="count(orderUnitList/shippingUnitList)"/>
					</xsl:variable>
					
					<xsl:for-each select="orderUnitList/shippingUnitList">
						<xsl:call-template name="ShipUnitArea">
							<xsl:with-param name="index" select="position()"/>
							<xsl:with-param name="TotalShippingUnits" select="$TotalShippingUnits"/>
						</xsl:call-template>
					</xsl:for-each>
					
					
				</Shipment>
			</xsl:for-each>
		</DataArea>
	</xsl:template>
	<xsl:template name="HeaderArea">
		<aaia:Header>
			<xsl:call-template name="HeaderDocumentIds"/>
			<xsl:call-template name="HeaderAlternateDocumentIds"/>
			<xsl:call-template name="HeaderTotalAmount"/>
			<xsl:call-template name="HeaderFreightCost"/>
			<xsl:call-template name="HeaderPaymentTerms"/>
			<xsl:call-template name="HeaderActualShippedDate"/>			
			<xsl:call-template name="HeaderParties"/>
			<xsl:call-template name="HeaderTransportationTerm"/>
		</aaia:Header>
	</xsl:template>
	<xsl:template name="HeaderActualShippedDate"/>

	<xsl:template name="ShipUnitArea">
		<xsl:param name="TotalShippingUnits"  />
		<xsl:param name="index"  />
		<ShipUnit>
			<ShippingTrackingId> 
				<xsl:choose>
					<xsl:when test="trackingNumber!=''">
						<xsl:value-of select="trackingNumber" />
					</xsl:when>
					<xsl:otherwise>Not Available</xsl:otherwise>
				</xsl:choose>
			</ShippingTrackingId>
			<ShipUnitSequence><xsl:value-of select="$index"/></ShipUnitSequence>
			<ShipUnitTotalId><xsl:value-of select="$TotalShippingUnits"/></ShipUnitTotalId>
			<ContainerID><xsl:value-of select="packingSlip" /></ContainerID>
			<xsl:call-template name="createnameValueElement">
					<xsl:with-param name="elementName">ActualShippedDateTime</xsl:with-param>
					<xsl:with-param name="elementValue"><xsl:value-of select="shipDate"/></xsl:with-param>
			</xsl:call-template>

<!--			<ActualShippedDateTime><xsl:value-of select="shipDate"/></ActualShippedDateTime>-->
			<TotalAmount><xsl:value-of select="format-number(totalAmount,'0.00')"/></TotalAmount>
			<FreightCost><xsl:value-of select="format-number(freightCost,'0.00')"/></FreightCost>
			<xsl:for-each select="shippedItemsList">
				<xsl:call-template name="ShipInventoryItem"/>
			</xsl:for-each>				
		</ShipUnit>
		
	</xsl:template>
	<xsl:template name="HeaderDocumentIds">
				<DocumentId>
					<Id><xsl:value-of select="customerPurchaseOrderNum"/></Id>
				</DocumentId>
	</xsl:template>
	<xsl:template name="HeaderAlternateDocumentIds">
		<AlternateDocumentIds>
			<CustomerDocumentId>
				<Id>
					<xsl:value-of select="customerPurchaseOrderNum"/>
				</Id>
			</CustomerDocumentId>
			<SupplierDocumentId>
				<Id>
					<xsl:value-of select="masterOrderNumber"/>
				</Id>
			</SupplierDocumentId>
		</AlternateDocumentIds>
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
	<xsl:template name="HeaderPaymentTerms">		
		<aaia:PaymentTerms>
			<TermId>2% Net 25th Prox</TermId>
		</aaia:PaymentTerms>
	</xsl:template>
	<xsl:template name="HeaderParties">
		<Parties>
			<xsl:call-template name="HeaderShipFromParty"/>
			<xsl:for-each select="shipToAccount">
				<ShipToParty>
					<xsl:call-template name="AccountBO"/>
				</ShipToParty>
			</xsl:for-each>
		
			<xsl:for-each select="billToAccount">
				<BillToParty>
					<xsl:call-template name="AccountBO"/>
				</BillToParty>
			</xsl:for-each>
			
			<!--			ShipVia party needs to added for CQ-->
		</Parties>
	</xsl:template>
	<xsl:template name="HeaderShipFromParty">
		<xsl:for-each select="orderUnitList/shippingUnitList/shipFrom " >
				<xsl:choose>
					<xsl:when test="position() = 1 ">
						<xsl:call-template name="HeaderCarrierParty"/>
						<ShipFromParty>
							<PartyId><Id><xsl:value-of select="code"/></Id></PartyId>
							<SCAC><xsl:value-of select="../scacCode"/></SCAC>
							<Name><xsl:value-of select="name"/></Name>
							<xsl:for-each select="address">
								<xsl:call-template name="AddressBO"/>
							</xsl:for-each>
						</ShipFromParty>
					</xsl:when>
				</xsl:choose>			
			</xsl:for-each>	
	
	</xsl:template>
	<xsl:template name="HeaderCarrierParty">
	</xsl:template>
	<xsl:template name="HeaderTransportationTerm">
		<xsl:for-each select="orderUnitList/shippingUnitList " >
				<xsl:choose>
					<xsl:when test="position() = 1 ">
						<TransportationTerm>
							<FreightTerms>
								<xsl:value-of  select="scacCode"/>
							</FreightTerms>
						</TransportationTerm>	
						<TransportationMethod><xsl:value-of  select="carrierName"/></TransportationMethod>
					</xsl:when>
				</xsl:choose>			
			</xsl:for-each>	
		</xsl:template>
	<xsl:template name="ShipInventoryItem">
		<aaia:ShipmentInventoryItem>
			<xsl:call-template name="OrderItem"/>
			<Description>
				<xsl:value-of select="description"/>
			</Description>			
			<xsl:call-template name="ItemQuantity"/>
			<xsl:call-template name="OrderQuantity"/>
			<xsl:call-template name="BackOrderQuantity"/>
			<xsl:call-template name="LineItemParties"/>						
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
		</aaia:ShipmentInventoryItem>
	</xsl:template>
	<xsl:template name="OrderItem">
			<aaia:ItemIds>
				<xsl:call-template name="LineCustomerItemId"/>
				<xsl:call-template name="LineSupplierCode"/>
				<xsl:call-template name="LineManufacturerCode"/>
				<xsl:call-template name="LineSupplierItemId"/>				

			</aaia:ItemIds>
	</xsl:template>
	<xsl:template name="LineCustomerItemId">
		<CustomerItemId>
				<Id><xsl:value-of select="displayPartNumber" /></Id>
		</CustomerItemId>	
	</xsl:template>
	<xsl:template name="LineSupplierCode">
		<xsl:call-template name="createnameValueElement">
				<xsl:with-param name="elementName">aaia:SupplierCode</xsl:with-param>
				<xsl:with-param name="elementValue"><xsl:value-of select="aaiaBrand"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="LineSupplierItemId">
		<aaia:SupplierItemId><Id><xsl:value-of select="partNumber" /></Id></aaia:SupplierItemId>
	</xsl:template>
	<xsl:template name="LineManufacturerCode">
		
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
							<PartyId>
								<Id><xsl:value-of select="code"/> </Id>
								<xsl:call-template name="ShipFromPartyScacCodeCustom">
								</xsl:call-template>								
							</PartyId>
							<Name><xsl:value-of select="name"/></Name>
							<Addresses>
								<xsl:for-each select="address">
									<Address>
										<xsl:call-template name="AddressBO"/>
									</Address>
								</xsl:for-each>
							</Addresses>							
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
			<PartyId>
				<xsl:call-template name="ShipFromPartyScacCodeCustom">
				</xsl:call-template>			
			</PartyId>
		</ShipFromParty>				
		
	</xsl:template>
	
	<xsl:template name="ShipFromPartyScacCodeCustom">
		<SCAC>
			<xsl:value-of select="../scacCode"/>
		</SCAC>		
	</xsl:template>
	
</xsl:stylesheet>
