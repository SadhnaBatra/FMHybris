<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.openapplications.org/oagis" xmlns:aaia="http://www.aftermarket.org/oagis" >

	<xsl:variable name="upper">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable> 
	<xsl:variable name="lower">abcdefghijklmnopqrstuvwxyz</xsl:variable> 
	<xsl:param name="currentTimeStamp"   />
	<xsl:param name="BODId"   />
	<xsl:param name="EffectivePeriod"   />
	<xsl:param name="nextBusinessdayFromTomorrow"  />
	<xsl:param name="SevenDaysFromToday"  />

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
			<xsl:with-param name="elementName">Country</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="country/isoCountryCode"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">PostalCode</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="zipOrPostalCode"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="createnameValueElement">
			<xsl:with-param name="elementName">StateOrProvince</xsl:with-param>
			<xsl:with-param name="elementValue"><xsl:value-of select="stateOrProv"/></xsl:with-param>
		</xsl:call-template>

	</xsl:template>

</xsl:stylesheet>
