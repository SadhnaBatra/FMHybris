<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:default="http://www.aaiasoa.net/IPOv2" xmlns:cmn="http://www.aaiasoa.net/IPOv2/Common">
	<xsl:output method="xml"  omit-xml-declaration="yes"/>

	<xsl:template match="/">     
		<xsl:choose>
			<xsl:when test="namespace-uri(/*)='http://www.aaiasoa.net/IPOv2'">2.0</xsl:when>
			<xsl:otherwise>1.2</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
