<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis"
xmlns:default="http://www.openapplications.org/oagis" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="default">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
<!--
	<xsl:variable  name="currentTimestamp" select="current-dateTime()" />
-->
	<xsl:variable  name="requestSource" select="document('referenceDocument')" />
	<xsl:variable  name="fmCommonDocument" select="document('CommonObjResponse')" />
	<xsl:variable name="rootNode"><xsl:value-of select="name($requestSource)"/></xsl:variable>

	<xsl:variable name="upper">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable> 
	<xsl:variable name="lower">abcdefghijklmnopqrstuvwxyz</xsl:variable> 
	<xsl:param name="currentTimeStamp"   />
	<xsl:param name="BODId"   />
	<xsl:param name="EffectivePeriod"   />
	<xsl:param name="nextBusinessdayFromTomorrow"  />
	<xsl:param name="environment"/>
	<xsl:variable name="ipoType"></xsl:variable>	
	<xsl:variable name="allowedIpoTypes">
		Quote
		PurchaseOrder
	</xsl:variable>	

	<xsl:variable name="allowedRootNodes">
		aaia:ProcessPurchaseOrder
		aaia:AddRequestForQuote
	</xsl:variable>	



	<xsl:template match="@*|node()" >
			<xsl:copy>
				<xsl:apply-templates select="@*|node()" />
			  </xsl:copy>	
	</xsl:template>
	<xsl:template match="*">
		<xsl:element name="{translate(substring(local-name(), 1, 1),$lower,$upper)}{substring(local-name(), 2, string-length(local-name()) - 1)}">
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
			<xsl:when test="$elementValue !=''">
				<xsl:element name="{$elementName}">
					<xsl:value-of select="$elementValue"/>
				</xsl:element>
			</xsl:when>
		</xsl:choose>		
	</xsl:template>
<!--	<xsl:template name="FMRootNode">
		<xsl:call-template name="ApplicationArea"/>
		<xsl:call-template name="DataArea"/>
	</xsl:template>
-->		
	<xsl:template match="/">
		<aaia:ConfirmBOD  xmlns:aaia="http://www.aftermarket.org/oagis"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.aftermarket.org/oagis ../BODs/AddQuote.xsd" revision="1.2.1" lang="en">
			<xsl:attribute name="environment" ><xsl:value-of select="$environment"/></xsl:attribute>
			<xsl:call-template name="ApplicationArea" />
			<xsl:call-template name="DataArea"/>
		</aaia:ConfirmBOD>		
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
		<DataArea>
			<BOD>
				<aaia:Header>
					<OriginalApplicationArea>
						<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:ApplicationArea/child::*" />
					</OriginalApplicationArea>
					<OriginalBODReference>
						<xsl:apply-templates select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/default:RequestForQuote/aaia:Header/default:DocumentIds" />				
						<xsl:element name="DocumentDate">
							<xsl:value-of select="$requestSource/*[contains($allowedRootNodes,$rootNode)]/default:DataArea/default:RequestForQuote/aaia:Header/default:DocumentDateTime"/>
						</xsl:element>
						<xsl:element name="Status">
							<xsl:element name="Code">REJECTED</xsl:element>
						</xsl:element>						
					</OriginalBODReference>
					<BODFailure>
						<ErrorMessage>
							<ReasonCode/>
							<Message><xsl:value-of select="/message" /></Message>
						</ErrorMessage>
					</BODFailure>
					
				</aaia:Header>
			</BOD>
		</DataArea>
	</xsl:template>
		<!--
	<xsl:template match="/aaia:ConfirmBOD/default:DataArea/default:BOD/aaia:Header/default:OriginalApplicationArea">
				<xsl:apply-templates select="$requestSource/aaia:AddRequestForQuote/default:ApplicationArea/child::*" />
	</xsl:template>
	<xsl:template match="/aaia:ConfirmBOD/default:DataArea/default:BOD/aaia:Header/default:OriginalBODReference">
				<xsl:element name="{local-name()}">
					<xsl:apply-templates select="$requestSource/aaia:AddRequestForQuote/default:DataArea/default:RequestForQuote/aaia:Header/default:DocumentIds" />				
					<xsl:element name="DocumentDate">
						<xsl:value-of select="$requestSource/aaia:AddRequestForQuote/default:DataArea/default:RequestForQuote/aaia:Header/default:DocumentDateTime"/>
					</xsl:element>
					<xsl:element name="Status">
						<xsl:element name="Code">REJECTED</xsl:element>
					</xsl:element>
					
				</xsl:element>	
	</xsl:template>

	<xsl:template match="/aaia:ConfirmBOD/default:DataArea/default:BOD/aaia:Header/default:BODFailure/default:ErrorMessage/default:Description">
					<xsl:apply-templates select="$fmCommonDocument/message" />
		
	</xsl:template>
-->
						
</xsl:stylesheet>

