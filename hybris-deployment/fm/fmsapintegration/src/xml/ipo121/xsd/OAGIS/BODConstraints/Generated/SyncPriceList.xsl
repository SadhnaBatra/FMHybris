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
         <active-pattern name="Sync PriceList">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="Sync PriceList Header">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Publisher Party">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="List Price Break">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Price List Qualifier">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="Sync PriceList Line">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="Line Price Break">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:PriceList" priority="4000" mode="M2">
      <fired-rule id="" context="oa:PriceList" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Sync PriceList must have a Header component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:PriceList/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:PriceList/oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Sync PriceList Header must have a "DocumentId" and "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Business | oa:PublisherParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Business | oa:PublisherParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Sync PriceList Header must have a "Business" or "PublisherParty" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:PublisherParty" priority="4000" mode="M4">
      <fired-rule id="" context="oa:PublisherParty" role=""/>
      <axsl:choose>
         <axsl:when test="oa:PublisherParty/oa:PartyId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:PublisherParty/oa:PartyId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A "PublisherParty" must have an Identifier specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:PriceList/oa:Header/oa:ListPriceBreak" priority="4000" mode="M5">
      <fired-rule id="" context="oa:PriceList/oa:Header/oa:ListPriceBreak" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DiscountValue | oa:DiscountPercent"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DiscountValue | oa:DiscountPercent" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ListPriceBreak must have a "DiscountValue" or "DiscountPercent" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:PriceList/oa:Header/oa:PriceListQualifier" priority="4000" mode="M6">
      <fired-rule id="" context="oa:PriceList/oa:Header/oa:PriceListQualifier" role=""/>
      <axsl:choose>
         <axsl:when test="oa:CatalogDocumentReference | oa:Business | PublisherParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:CatalogDocumentReference | oa:Business | PublisherParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The PriceListQualifier must have a 'Catalog" or "Business" or "PublisherParty" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="oa:PriceList /oa:Line" priority="4000" mode="M7">
      <fired-rule id="" context="oa:PriceList /oa:Line" role=""/>
      <axsl:choose>
         <axsl:when test="oa:LineNumber"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:LineNumber" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The PriceList Line must have an "LineNumber" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:UnitPrice"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:UnitPrice" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The PriceList Line must have an "UnitPrice" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ItemId | oa:CommodityCode"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId | oa:CommodityCode" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The PriceList Line must have an "ItemId" or "CommodityCode" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:PriceList /oa:Line/oa:LinePriceBreak" priority="4000" mode="M8">
      <fired-rule id="" context="oa:PriceList /oa:Line/oa:LinePriceBreak" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DiscountValue | oa:DiscountPercent | oa:OverRidePrice"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DiscountValue | oa:DiscountPercent | oa:OverRidePrice" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The LinePriceBreak must have a "DiscountValue" or "DiscountPercent" or "OverRidePrice" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:PriceBreakQuantity | oa:PriceBreakAmount"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:PriceBreakQuantity | oa:PriceBreakAmount" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The LinePriceBreak must have a "PriceBreakQuantity" or a "PriceBreakAmount" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>