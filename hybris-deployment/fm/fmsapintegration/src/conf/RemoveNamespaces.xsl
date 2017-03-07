<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>
	<xsl:template match="*">
                <xsl:element name="{local-name()}">
                        <xsl:apply-templates select="@* | node()"/>
                </xsl:element>
        </xsl:template>

</xsl:stylesheet>
