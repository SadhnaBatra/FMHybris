<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<axsl:stylesheet xmlns:axsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sch="http://www.ascc.net/xml/schematron" xmlns:oa="http://www.openapplications.org/oagis" version="1.0" oa:dummy-for-xmlns="">
   <axsl:output method="xml" omit-xml-declaration="no" standalone="yes" indent="yes"/>
   <axsl:template match="*|@*" mode="schematron-get-full-path">
      <axsl:apply-templates select="parent::*" mode="schematron-get-full-path"/>
      <axsl:text>/</axsl:text>
      <axsl:if test="count(. | ../@*) = count(../@*)">@</axsl:if>
      <axsl:value-of select="name()"/>
      <axsl:text>[</axsl:text>
      <axsl:value-of select="1+count(preceding-sibling::*[name()=name(current())])"/>
      <axsl:text>]</axsl:text>
   </axsl:template>
   <axsl:template match="/">
      <schematron-output title="Schematron Validator for OAGI Constraints" schemaVersion="" phase="#ALL">
         <ns uri="http://www.openapplications.org/oagis" prefix="oa"/>
         <active-pattern name="Noun Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:Credit" priority="4000" mode="M2">
      <fired-rule id="" context="oa:Credit" role=""/>
      <axsl:choose>
         <axsl:when test="oa:GLEntity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:GLEntity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a GLEntity.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OpenItemAmounts"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OpenItemAmounts" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a OpenItemAmounts.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OrderAmounts"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OrderAmounts" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a OrderAmounts.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a have a reference to a Party.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>