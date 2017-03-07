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
         <active-pattern name="Sync Item Cross Reference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="Related Item Reference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Related Item">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Alternate ItemId Reference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
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
               <text>The ItemCrossReference must have an "ItemId" specified</text>
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
   <axsl:template match="oa:ItemCrossReference/oa:RelatedItemReference " priority="4000" mode="M3">
      <fired-rule id="" context="oa:ItemCrossReference/oa:RelatedItemReference " role=""/>
      <axsl:choose>
         <axsl:when test="oa:Relationship"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Relationship" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RelatedItemReference must have a "Relationship" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:RelatedItem"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:RelatedItem" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RelatedItemReference must have at least one "RelatedItem" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:ItemCrossReference/oa:RelatedItemReference/oa:RelatedItem " priority="4000" mode="M4">
      <fired-rule id="" context="oa:ItemCrossReference/oa:RelatedItemReference/oa:RelatedItem " role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RelatedItem must have an "ItemId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:ItemCrossReference/oa:AlternateItemIdReference" priority="4000" mode="M5">
      <fired-rule id="" context="oa:ItemCrossReference/oa:AlternateItemIdReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemIds"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemIds" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The AlternateItemIdReference must have an "ItemIds" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>