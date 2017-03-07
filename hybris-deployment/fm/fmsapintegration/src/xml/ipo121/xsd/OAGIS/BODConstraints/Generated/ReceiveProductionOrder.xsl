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
         <active-pattern name="Receive Production Order">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="Receive Production Order Header">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Receive Production Order Line">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Item">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="OperationReference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:ProductionOrder" priority="4000" mode="M2">
      <fired-rule id="" context="oa:ProductionOrder" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Receive Production Order must have a Header component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Line"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Line" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Receive Production Order must have at least one Line component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:ProductionOrder/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:ProductionOrder/oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Header must have a "DocumentId" and "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:ProductionOrder/oa:Line" priority="4000" mode="M4">
      <fired-rule id="" context="oa:ProductionOrder/oa:Line" role=""/>
      <axsl:choose>
         <axsl:when test="oa:LineNumber"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:LineNumber" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Line must have a "LineNumber"</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OrderQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OrderQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Line must have an "OrderQuantity" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Item"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Item" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Line must have a "Item" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:ProductionOrder/oa:Line/oa:Item" priority="4000" mode="M5">
      <fired-rule id="" context="oa:ProductionOrder/oa:Line/oa:Item" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Item must have an "ItemId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:ProductionOrder/oa:Line/oa:OperationReference" priority="4000" mode="M6">
      <fired-rule id="" context="oa:ProductionOrder/oa:Line/oa:OperationReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The OperationReference must have an "OperationId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>