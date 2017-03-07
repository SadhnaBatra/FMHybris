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
         <active-pattern name="Show Item Cross Reference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:ItemCrossReference" priority="4000" mode="M2">
      <fired-rule id="" context="oa:ItemCrossReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Show ItemCrossReference must have an "ItemId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:RelatedItemReference | oa:AlternateItemIdReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:RelatedItemReference | oa:AlternateItemIdReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ItemCrossReference must have a "RelatedItemReference" or an "AlternateItemIdReference" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>