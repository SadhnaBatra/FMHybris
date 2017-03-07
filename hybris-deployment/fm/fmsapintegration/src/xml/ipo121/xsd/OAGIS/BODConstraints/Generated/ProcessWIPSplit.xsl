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
         <active-pattern name="Process WIPSplit">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="Process WIPSplitHeader">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Process WIPSplitLine">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="WIPSplitSource">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="WIPSplitDestination">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="Operation">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="Production Order">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:WIPSplit" priority="4000" mode="M2">
      <fired-rule id="" context="oa:WIPSplit" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPSplit must have a Header component specified</text>
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
               <text>The Process WIPSplit must have at least one Line component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:WIPSplit/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:WIPSplit/oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPSplit Header must have a "DocumentDateTime" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:WIPSplit/oa:Line" priority="4000" mode="M4">
      <fired-rule id="" context="oa:WIPSplit/oa:Line" role=""/>
      <axsl:choose>
         <axsl:when test="oa:WIPSplitSource"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:WIPSplitSource" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPSplit Line must have a "WIPSplitSource" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:WIPSplitDestination"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:WIPSplitDestination" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPSplit Line must have a "WIPSplitDestination" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:WIPSplit/oa:Line/oa:WIPSplitSource" priority="4000" mode="M5">
      <fired-rule id="" context="oa:WIPSplit/oa:Line/oa:WIPSplitSource" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ProductionOrderReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ProductionOrderReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The WIPSplitSource must have a "ProductionOrderReference" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OperationReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The WIPSplitSource must have an "OperationReference" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:WIPSplit/oa:Line/oa:WIPSplitDestination" priority="4000" mode="M6">
      <fired-rule id="" context="oa:WIPSplit/oa:Line/oa:WIPSplitDestination" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The WIPSplitDestination must have a "ItemQuantity" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ProductionOrderReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ProductionOrderReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The WIPSplitDestination must have a "ProductionOrderReference" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="oa:OperationReference" priority="4000" mode="M7">
      <fired-rule id="" context="oa:OperationReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>An Operation must have a "OperationId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:ProductionOrderReference" priority="4000" mode="M8">
      <fired-rule id="" context="oa:ProductionOrderReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A ProductionOrderReference must have a "DocumentId" and an "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>