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
         <active-pattern name="Line Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="SubLine Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Schedule Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="Party Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="Contact Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:RequestForQuote" priority="4000" mode="M2">
      <fired-rule id="" context="oa:RequestForQuote" role=""/>
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
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentIds"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentIds" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have DocumentIds</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:Line" priority="4000" mode="M4">
      <fired-rule id="" context="oa:Line" role=""/>
      <axsl:choose>
         <axsl:when test="oa:LineNumber"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:LineNumber" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must identify the LineNumber of the Document</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Site"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Site" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Site</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OrderItem "/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OrderItem" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have an Orderitem.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:SubLine" priority="4000" mode="M5">
      <fired-rule id="" context="oa:SubLine" role=""/>
      <axsl:choose>
         <axsl:when test="oa:LineNumber"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:LineNumber" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must identify the LineNumber of the Document</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OrderItem "/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OrderItem" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have an Orderitem.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:Schedule" priority="4000" mode="M6">
      <fired-rule id="" context="oa:Schedule" role=""/>
      <axsl:choose>
         <axsl:when test="oa:LineNumber"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:LineNumber" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must identify the LineNumber of the Document</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:NeedDeliveryDate"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:NeedDeliveryDate" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have NeedDeliveryDate</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:RequiredQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:RequiredQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have RequiredQuantity</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="oa:Party | oa:SoldToParty" priority="4000" mode="M7">
      <fired-rule id="" context="oa:Party | oa:SoldToParty" role=""/>
      <axsl:choose>
         <axsl:when test="oa:PartyId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:PartyId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Party Identifier.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Name"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Name" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Party Name.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:Contact" priority="4000" mode="M8">
      <fired-rule id="" context="oa:Contact" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Person"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Person" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Contact must have a Person.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>