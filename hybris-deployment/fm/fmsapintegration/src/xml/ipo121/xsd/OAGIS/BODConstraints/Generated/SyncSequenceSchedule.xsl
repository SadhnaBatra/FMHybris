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
         <active-pattern name="Header Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Event Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Line Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:SequenceSchedule" priority="4000" mode="M2">
      <fired-rule id="" context="oa:SequenceSchedule" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Header component.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Event"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Event" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have at least one Event component.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentIds/oa:DocumentId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentIds/oa:DocumentId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a DocumentId to identify the document.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:LastModificationDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:LastModificationDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must indicate the last time the Planning Schedule was updated.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:EffectivePeriod"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EffectivePeriod" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must indicate the effective time period of the PlanningSchedule</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:ShipToParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:ShipToParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have identify a "ShipTo" Party</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:Event" priority="4000" mode="M4">
      <fired-rule id="" context="oa:Event" role=""/>
      <axsl:choose>
         <axsl:when test="oa:RequiredDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:RequiredDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Required DateTime Timestamp must be provided.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:Line" priority="4000" mode="M5">
      <fired-rule id="" context="oa:Line" role=""/>
      <axsl:choose>
         <axsl:when test="oa:SequenceNumber"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:SequenceNumber" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must identify the Sequence number of the line.</text>
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
               <text>Must have an Item.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:RequestedQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:RequestedQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have the number of Items Requested.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>