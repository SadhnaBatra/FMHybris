<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis"
xmlns:default="http://www.openapplications.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="default">

<!--	<xsl:import href="../RFQCommonObjectDataRetrieval.xsl"/>	-->

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>

	<xsl:variable  name="requestSource" select="document('referenceDocument')" />
	<xsl:variable name="upper">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable> 
	<xsl:variable name="lower">abcdefghijklmnopqrstuvwxyz</xsl:variable> 
	<xsl:variable name="allowedIpoTypes"><xsl:text>Quote,PurchaseOrder</xsl:text></xsl:variable>
	<xsl:variable name="allowedRootNodes"><xsl:text>aaia:ProcessPurchaseOrder,aaia:AddRequestForQuote</xsl:text></xsl:variable>	
	<xsl:variable name="HeaderNodeTag">Header</xsl:variable>
	<xsl:variable name="EffectivePeriodTag">EffectivePeriod</xsl:variable>

	<xsl:param name="currentTimeStamp"   />
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

<!--	
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5 )) ='ToAcct']/accountCode">
		<xsl:choose>
			<xsl:when test="text()!=''">
				<PartyId>
					<Id>
						<xsl:value-of select="."/>
					</Id>
				</PartyId>
			
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/accountName">
		<xsl:call-template name="rename">
			<xsl:with-param name="elementName" >Name</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/address">
		<Addresses>
			<xsl:call-template name="rename">
				<xsl:with-param name="elementName" >Address</xsl:with-param>
			</xsl:call-template>
		</Addresses>
	</xsl:template>
	<xsl:template match="address/addrLine1">
		<xsl:call-template name="rename">
			<xsl:with-param name="elementName" >AddressLine</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template match="address/addrLine2">
		<xsl:choose>
			<xsl:when test=". != ''">
				<xsl:call-template name="rename">
					<xsl:with-param name="elementName" >AddressLine</xsl:with-param>
				</xsl:call-template>
			
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="address/country">
		<Country>
			<xsl:value-of select="isoCountryCode"/>
		</Country>
	</xsl:template>
	<xsl:template match="address/zipOrPostalCode">
		<xsl:call-template name="rename">
			<xsl:with-param name="elementName" >PostalCode</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="address/stateOrProv">
		<xsl:call-template name="rename">
			<xsl:with-param name="elementName" >StateOrProvince</xsl:with-param>
		</xsl:call-template>
	</xsl:template>	

	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/viewInvoiceFlag"/>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/creditCardCustFlag"/>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/cdreditRestrictFlag"/>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/exemptFromEmergencyFlag"/>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/minFreeFreightAmt"/>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/accountType"/>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/creditRestrictFlag"/>
	<xsl:template match="/OrderBO/*[substring(name(),(string-length(name())-5) ) ='ToAcct']/customerId"/>

	<xsl:template match="/OrderBO/parts/backorder">
		<xsl:call-template name="rename">
			<xsl:with-param name="elementName" >BackOrderedInd</xsl:with-param>
		</xsl:call-template>
	</xsl:template>	
-->	

	<xsl:template name="AccountBO">
		<xsl:choose>
			<xsl:when test="accountCode !='' ">
				<PartyId>
					<Id>
						<xsl:value-of select="accountCode"/>
					</Id>
				</PartyId>			
			</xsl:when>
		</xsl:choose>
		<Name><xsl:value-of select="accountName"/></Name>
		<Addresses>
			<xsl:for-each select="address">
				<Address>
					<xsl:call-template name="AddressBO"/>
				</Address>
			</xsl:for-each>
		</Addresses>
	</xsl:template>

	<xsl:template name="AddressBO">
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">AddressLine</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="addrLine1"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">AddressLine</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="addrLine2"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">City</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="city"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">StateOrProvince</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="stateOrProv"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">Country</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="country/isoCountryCode"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">PostalCode</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="zipOrPostalCode"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="FMRootNode">
		<xsl:call-template name="ApplicationArea"/>
		<xsl:call-template name="DataArea"/>
	</xsl:template>
	
	<xsl:template name="ApplicationArea">
		<!--TODO move this to common area-->
		<ApplicationArea xmlns="" >
			<Sender>
				<xsl:choose>
					<xsl:when test="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:ApplicationArea/default:Sender/default:ReferenceId">
						<ReferenceId><xsl:value-of select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:ApplicationArea/default:Sender/default:ReferenceId" /></ReferenceId>
					</xsl:when>
				</xsl:choose>
				<Confirmation>0</Confirmation>
			</Sender>
			<CreationDateTime><xsl:value-of select="$currentTimeStamp" /></CreationDateTime>
			<BODId><xsl:value-of select="$BODId "/></BODId>
		</ApplicationArea>
	</xsl:template>

	<xsl:template name="DataArea">
		<DataArea xmlns="">
			<xsl:call-template name="DataAreaCustom"/>
			<xsl:element name="{$ipoType}">
				<xsl:call-template name="DataAreaHeader"/>
				<xsl:call-template name="DataAreaLine"/>
			</xsl:element>
		</DataArea>
	</xsl:template>

<!--	Header Data area sub templates START-->
	<xsl:template name="DataAreaCustom" />

	<xsl:template name="DataAreaHeader" >
	<!--TODO move this to common area-->
		<xsl:element name="{$HeaderNodeTag}">
			<xsl:call-template name="DocumentIds"/>
			<xsl:call-template name="AlternateDocumentIds"/>
			<xsl:call-template name="DocumentReferences"/>
			<xsl:call-template name="EffectivePeriod"/>
			<xsl:call-template name="HeaderExtendedPrice"/>
			<xsl:call-template name="HeaderTotalPrice"/>
			<xsl:call-template name="PaymentTerms"/>
			<xsl:call-template name="DropShipInd"/>
			<xsl:call-template name="BackOrderedInd"/>
			<xsl:call-template name="HeaderParties"/>
			<xsl:call-template name="TransportationTerm"/>			
			<xsl:call-template name="HeaderReasonCode"/>
		</xsl:element>
	</xsl:template>

	<xsl:template name="DocumentIds">
		<xsl:choose>
			<xsl:when test="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds">
				<DocumentIds>
					<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds/child::*"/>
					<SupplierDocumentId>
						<Id><xsl:value-of select="/OrderBO/mstrOrdNbr" /></Id>
					</SupplierDocumentId>				
				</DocumentIds>
			</xsl:when>		
		</xsl:choose>
	</xsl:template>

	<xsl:template match="*[local-name()='Header']/default:DocumentIds/default:SupplierDocumentId">
	</xsl:template>
	
	<xsl:template name="AlternateDocumentIds">
		<xsl:choose>
			<xsl:when test="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:AlternateDocumentIds">
				<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:AlternateDocumentIds"/>
			</xsl:when>		
		</xsl:choose>
	</xsl:template>

	<xsl:template name="DocumentReferences">
		<xsl:choose>
			<xsl:when test="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds">
				<DocumentReferences>
					<RFQDocumentReference>
						<DocumentIds>
							<CustomerDocumentId><Id><xsl:value-of select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DocumentIds/default:CustomerDocumentId/default:Id"/></Id></CustomerDocumentId>
						</DocumentIds>						
					</RFQDocumentReference>
				</DocumentReferences>							
			</xsl:when>		
		</xsl:choose>
	</xsl:template>

	<xsl:template name="EffectivePeriod">
		<xsl:element name="{$EffectivePeriodTag}" >
			<xsl:attribute name="inclusive">1</xsl:attribute>
			<To><xsl:value-of select="$EffectivePeriod"/></To>
		</xsl:element>		
	</xsl:template>

	<xsl:template name="HeaderExtendedPrice">
		<ExtendedPrice>
			<xsl:for-each select="/OrderBO/parts[ not(problemItem)  ]" >
				<xsl:choose>
					<xsl:when test="position()=1 and itemPrice/currency!=''">
						<xsl:attribute name="currency"><xsl:value-of  select="itemPrice/currency/code"/></xsl:attribute>											
					</xsl:when>
				</xsl:choose>			
			</xsl:for-each>	
			<xsl:value-of select="/OrderBO/totalItemPrice" />
		</ExtendedPrice>		
	</xsl:template>

	<xsl:template name="HeaderTotalPrice">
		<TotalAmount>
			<xsl:for-each select="/OrderBO/parts[ not(problemItem)  ]" >
				<xsl:choose>
					<xsl:when test="position()=1 and itemPrice/currency!=''">			
						<xsl:attribute name="currency"><xsl:value-of  select="itemPrice/currency/code"/></xsl:attribute>											
					</xsl:when>
				</xsl:choose>
			</xsl:for-each>	
			<xsl:value-of select="/OrderBO/totalItemPrice" />
		</TotalAmount>
	</xsl:template>

	<xsl:template name="PaymentTerms">
		<aaia:PaymentTerms><TermId>2% Net 25th Prox</TermId></aaia:PaymentTerms>
	</xsl:template>

	<xsl:template name="DropShipInd">
		<!-- This used to be reflected, but it will need to be calculated eventually.  All IPO orders are drop shipped though, so we'll set 0 
		<xsl:value-of select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:DropShipInd"/>
		-->
		<DropShipInd>1</DropShipInd> 
	</xsl:template>

	<xsl:template name="BackOrderedInd">
		<BackOrderedInd>
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[ not(problemItem)  ]">
					<xsl:for-each select="/OrderBO/parts[ not(problemItem)  ]" >
						<xsl:choose>
							<xsl:when test="position()=1">
								<xsl:value-of select="backorder"/>				
							</xsl:when>
						</xsl:choose>
					</xsl:for-each>
				</xsl:when>	
				<xsl:otherwise>0</xsl:otherwise>
			</xsl:choose>	
		</BackOrderedInd>
	</xsl:template>

	<xsl:template name="HeaderParties">
		<Parties>
			<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:Parties/aaia:HostParty"/>
			<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:Parties/default:SoldToParty"/>			
			<xsl:call-template name="HeaderShipToParty"/>
			<xsl:call-template name="HeaderBillToParty"/>
		</Parties>
	</xsl:template>

	<xsl:template name="HeaderShipToParty">
		<xsl:for-each select="/OrderBO/shipToAcct">	
			<ShipToParty>
				<xsl:choose>
					<xsl:when test="not(accountCode) or accountCode = ''">
						<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:Parties/default:ShipToParty/default:PartyId"/>
					</xsl:when>
				</xsl:choose>
				<xsl:call-template name="AccountBO" />
			</ShipToParty>
		</xsl:for-each>
<!--		<ShipToParty>
			<xsl:choose>
				<xsl:when test="not(/OrderBO/shipToAcct/accountCode) or /OrderBO/shipToAcct/accountCode = ''">
					<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:Parties/default:ShipToParty/default:PartyId"/>
				</xsl:when>
			</xsl:choose>
		<xsl:apply-templates select="/OrderBO/shipToAcct/child::*"/>
			
		</ShipToParty>
-->	
	</xsl:template>

	<xsl:template name="HeaderBillToParty">
		<xsl:for-each select="/OrderBO/billToAcct">
			<BillToParty>
				<xsl:call-template name="AccountBO" />
			</BillToParty>
		</xsl:for-each>			
<!--	
		<BillToParty>
			<xsl:apply-templates select="/OrderBO/billToAcct/child::*"/>
		</BillToParty>
-->	
	</xsl:template>

	<xsl:template name="HeaderReasonCode">
		<ReasonCode><xsl:value-of select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:ReasonCode"/></ReasonCode>
	</xsl:template>
	<xsl:template name="TransportationTerm">
		<xsl:choose>
			<xsl:when test=" $requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:TransportationTerm/default:FreightTerms">
				<TransportationTerm>
					<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/*[local-name()='Header']/default:TransportationTerm/default:FreightTerms"/>
				</TransportationTerm>			
			</xsl:when>
		</xsl:choose>
	</xsl:template>
<!--	Header Data area sub templates END-->

<!--	Data Line area sub templates START-->
	<xsl:template name="DataAreaLine" >
		<xsl:for-each select="/OrderBO/parts">
			<xsl:variable name="index" select="position()"/>
			<aaia:Line>
				<xsl:apply-templates select="/OrderBO/parts[$index]/lineNumber"/>
				<xsl:call-template name="OrderItem">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="OrderQuantity">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="BackOrderQuantity">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="UnitPrice">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="ExtendedPrice">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="TotalAmount">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="Note">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="PromisedShipDate">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>				
				</xsl:call-template>
				<xsl:call-template name="Parties">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="LineUserArea">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
			</aaia:Line>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="OrderItem">
		<xsl:param name="index"  />
		<OrderItem>
			<aaia:ItemIds>
				<xsl:call-template name="LineCustomerItemId">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="LineSupplierCode">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="LineManufacturerCode">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="LineSupplierItemId">
					<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
			</aaia:ItemIds>
<!--			<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/aaia:Line[number($index)]/default:OrderItem/aaia:ItemIds"/>
			<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/aaia:Line[number($index)]/aaia:OrderItem/aaia:ItemIds"/>
-->			<Description>
				<xsl:value-of select="/OrderBO/parts[number($index)]/fullDescription"/>
			</Description>
			
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/problemItem/ipoMessage != '' or (/OrderBO/parts[number($index)]/problemItem/ipoMessage) ">
					<ItemStatus>
						<Change>
							<To>
								<xsl:value-of select="/OrderBO/parts[number($index)]/problemItem/ipoCode"/>
							</To>
							<Description>
								<xsl:value-of select="/OrderBO/parts[number($index)]/problemItem/ipoMessage"/>
							</Description>
						</Change>
					</ItemStatus>
				</xsl:when>
			</xsl:choose>
		</OrderItem>
	</xsl:template>

	<xsl:template name="LineCustomerItemId">
		<xsl:param name="index"  />
		<CustomerItemId>
			<Id><xsl:value-of select="/OrderBO/parts[number($index)]/displayPartNumber" /></Id>
		</CustomerItemId>	
	</xsl:template>

	<xsl:template name="LineSupplierCode">
		<xsl:param name="index"  />
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">aaia:SupplierCode</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="/OrderBO/parts[number($index)]/aaiaBrand"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="LineManufacturerCode">
		<xsl:param name="index"  />
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">aaia:ManufacturerCode</xsl:with-param>
			<xsl:with-param name="elementValue">
				<xsl:value-of select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/aaia:Line[number($index)]/*[local-name()='OrderItem']/aaia:ItemIds/aaia:ManufacturerCode"/>
			</xsl:with-param>
		</xsl:call-template>	
	</xsl:template>

	<xsl:template name="LineSupplierItemId">
		<xsl:param name="index"  />
		<SupplierItemId><Id><xsl:value-of select="/OrderBO/parts[number($index)]/partNumber" /></Id></SupplierItemId>
	</xsl:template>

	<xsl:template name="OrderQuantity">
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

	<xsl:template name="BackOrderQuantity">
		<xsl:param name="index"  />
		<xsl:choose >
			<xsl:when test="number(/OrderBO/parts[number($index)]/itemQty/backorderedQty) >0 ">
				<BackOrderedQuantity>
					<xsl:attribute name="uom">
						<xsl:choose>
							<xsl:when test="/OrderBO/parts[number($index)]/itemQty/qtyUOM!=''">
								<xsl:value-of select="/OrderBO/parts[number($index)]/itemQty/qtyUOM" />
							</xsl:when>
							<xsl:otherwise>EA</xsl:otherwise>
						</xsl:choose>
					</xsl:attribute>
					<xsl:value-of select="/OrderBO/parts[number($index)]/itemQty/backorderedQty" />
				</BackOrderedQuantity>		
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="UnitPrice">
		<xsl:param name="index"  />
		<xsl:element name="UnitPrice" >
			<xsl:element name="Amount" >
				<xsl:choose>
					<xsl:when test="/OrderBO/parts[number($index)]/itemPrice/currency!=''">
						<xsl:attribute name="currency"><xsl:value-of select="/OrderBO/parts[number($index)]/itemPrice/currency/code"/></xsl:attribute>
						<xsl:value-of select="format-number(/OrderBO/parts[number($index)]/itemPrice/displayPrice,'0.00')"/>
					</xsl:when>
				</xsl:choose>
			</xsl:element>
			<xsl:element name="PerQuantity"  >
				<xsl:attribute name="uom">EA</xsl:attribute>
				<xsl:text>1</xsl:text>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="ExtendedPrice">		
		<xsl:param name="index"  />
		<xsl:element name="ExtendedPrice" >
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/itemPrice/currency!=''">
					<xsl:attribute name="currency"><xsl:value-of select="/OrderBO/parts[number($index)]/itemPrice/currency/code"/></xsl:attribute>
					<xsl:value-of select="format-number(/OrderBO/parts[number($index)]/itemPrice/displayPrice * /OrderBO/parts[number($index)]/orderedQty,'0.00') "/>
				</xsl:when>
			</xsl:choose>
		</xsl:element>
	</xsl:template>

	<xsl:template name="TotalAmount">
		<xsl:param name="index"  />
		<!-- Extended price + additional chanrges-->
		<xsl:element name="TotalAmount" >
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/itemPrice/currency!=''">
					<xsl:attribute name="currency"><xsl:value-of select="/OrderBO/parts[number($index)]/itemPrice/currency/code"/></xsl:attribute>
					<xsl:value-of select="format-number(/OrderBO/parts[number($index)]/itemPrice/displayPrice * /OrderBO/parts[number($index)]/orderedQty,'0.00') "/>
				</xsl:when>
			</xsl:choose>
		</xsl:element>
	</xsl:template>

	<xsl:template name="Note">
		<xsl:param name="index"  />
		<xsl:choose>
			<xsl:when test="/OrderBO/parts[number($index)]/comment">
				<Note><xsl:value-of select=" /OrderBO/parts[number($index)]/comment"/></Note>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="PromisedShipDate">
		<xsl:param name="index"  />
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/inventory/selectedLocation='true' ">
					<PromisedShipDate>
						<xsl:value-of select="/OrderBO/parts[number($index)]/promisedShipDateIsoDate"/>
					</PromisedShipDate>
				</xsl:when>
			</xsl:choose>
	</xsl:template>

	<xsl:template name="Parties">
		<xsl:param name="index"  />
		<Parties>
			<xsl:choose>
				<xsl:when test="/OrderBO/parts[number($index)]/inventory/selectedLocation='true' ">
					<xsl:for-each select="/OrderBO/parts[number($index)]/inventory[selectedLocation='true']" >
						<ShipFromParty>
							<PartyId>
								<Id><xsl:value-of select="distributionCenter/code"/> </Id>
								<xsl:call-template name="ShipFromPartyScacCodeCustom">
									<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
								</xsl:call-template>
							</PartyId>
							<Name><xsl:value-of select="distributionCenter/name"/></Name>
							<Addresses>
								<xsl:for-each select="distributionCenter/address">
									<Address>
										<xsl:call-template name="AddressBO"/>
									</Address>
								</xsl:for-each>
							</Addresses>
<!--							<Addresses>
								<xsl:apply-templates select="distributionCenter/address"/>
							</Addresses>
-->						</ShipFromParty>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="ShipFromPartyCustom">
						<xsl:with-param name="index" ><xsl:value-of select="$index"/></xsl:with-param>
					</xsl:call-template>								
				</xsl:otherwise>
			</xsl:choose>
		</Parties>
	</xsl:template>

	<xsl:template name="ShipFromPartyCustom">
		<xsl:param name="index"  />
		<ShipFromParty>
			<PartyId>
				<xsl:call-template name="ShipFromPartyScacCodeCustom">
					<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>			
			</PartyId>
		</ShipFromParty>
	</xsl:template>

	<xsl:template name="ShipFromPartyCustom_LineUserArea_DC">
		<xsl:param name="index" />
		<ShipFromParty>
			<PartyId>
				<Id><xsl:value-of select="distributionCenter/code"/> </Id>
				<xsl:call-template name="ShipFromPartyScacCodeCustom">
					<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
			</PartyId>
			<Name><xsl:value-of select="distributionCenter/name"/></Name>
			<Addresses>
				<xsl:for-each select="distributionCenter/address">
					<Address>
						<xsl:call-template name="AddressBO"/>
					</Address>
				</xsl:for-each>
			</Addresses>
			<xsl:call-template name="AvailableQtyCustom">
				<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
		</ShipFromParty>
	</xsl:template>

	<xsl:template name="ShipFromPartyCustom_LineUserArea_TSC">
		<xsl:param name="index" />
		<ShipFromParty>
			<PartyId>
				<Id><xsl:value-of select="distributionCenter/code"/> </Id>
				<xsl:call-template name="ShipFromPartyScacCodeCustom">
					<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
				</xsl:call-template>
			</PartyId>
			<Name><xsl:value-of select="distributionCenter/name"/></Name>
			<Addresses>
				<xsl:for-each select="distributionCenter/address">
					<Address>
						<xsl:call-template name="AddressBO"/>
					</Address>
				</xsl:for-each>
			</Addresses>
			<xsl:call-template name="AvailableQtyCustom">
				<xsl:with-param name="index"><xsl:value-of select="$index"/></xsl:with-param>
			</xsl:call-template>
		</ShipFromParty>
	</xsl:template>

	<xsl:template name="ShipFromPartyScacCodeCustom">
		<xsl:param name="index"  />
		<SCAC>
			<xsl:value-of select="/OrderBO/parts[number($index)]/scacCode"/>
		</SCAC>		
	</xsl:template>

	<xsl:template name="AvailableQtyCustom">
		<xsl:param name="index" />
		<AvailableQty><xsl:value-of select="format-number(availableQty,'0.00') "/></AvailableQty>
	</xsl:template>
	
	<xsl:template name="LineUserArea">
		<xsl:param name="index"  />
		<xsl:element name="UserArea">
			<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/*[contains($allowedIpoTypes,$ipoType)]/aaia:Line[number($index)]/default:UserArea/child::*" />
		</xsl:element>
	</xsl:template>
<!--	Data Line area sub templates END-->	

</xsl:stylesheet>
