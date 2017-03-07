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
         <active-pattern name="Sync ElectronicCatalog">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="ElectronicCatalog Header">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Catalog Classification Scheme">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Catalog Classification Master">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Catalog Classification Structure">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="Feature">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="ElectronicCatalog Item Line">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
         <active-pattern name="ElectronicCatalog Commodity Line">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M9"/>
         <active-pattern name="ElectronicCatalog Line Item Price">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M10"/>
         <active-pattern name="ElectronicCatalog Line Item Price Break">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M11"/>
         <active-pattern name="ElectronicCatalog Line Item Classification">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M12"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:ElectronicCatalog" priority="4000" mode="M2">
      <fired-rule id="" context="oa:ElectronicCatalog" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Sync ElectronicCatalog must have a Header component</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:ElectronicCatalog/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:ElectronicCatalog/oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ElectronicCatalog Header must have a "DocumentId" and "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:PublisherReference | oa:Business"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:PublisherReference | oa:Business" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ElectronicCatalog Header must have a "PublisherParty" or a "Business" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:ClassificationScheme" priority="4000" mode="M4">
      <fired-rule id="" context="oa:ClassificationScheme" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ClassificationSchemeId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ClassificationSchemeId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ClassificationScheme must have a "ClassificationSchemeId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:ClassificationMaster" priority="4000" mode="M5">
      <fired-rule id="" context="oa:ClassificationMaster" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ClassificationId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ClassificationId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ClassificationMaster must have a "ClassificationId" element.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:ClassificationStructure" priority="4000" mode="M6">
      <fired-rule id="" context="oa:ClassificationStructure" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ClassificationId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ClassificationId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Classification Structure must have at least one "ClassificationId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="oa:Feature" priority="4000" mode="M7">
      <fired-rule id="" context="oa:Feature" role=""/>
      <axsl:choose>
         <axsl:when test="oa:NameValue"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:NameValue" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a "NameValue" element.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:ElectronicCatalog/oa:CatalogItemLine" priority="4000" mode="M8">
      <fired-rule id="" context="oa:ElectronicCatalog/oa:CatalogItemLine" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Catalog Line must have an "ItemId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="oa:CatalogCommodityLine" priority="4000" mode="M9">
      <fired-rule id="" context="oa:CatalogCommodityLine" role=""/>
      <axsl:choose>
         <axsl:when test="oa:CommodityCode"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:CommodityCode" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Catalog Line must have a "CommodityCode" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M9"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M9"/>
   <axsl:template match="oa:ElectronicCatalog/oa:CatalogItemLine/oa:ItemPrice" priority="4000" mode="M10">
      <fired-rule id="" context="oa:ElectronicCatalog/oa:CatalogItemLine/oa:ItemPrice" role=""/>
      <axsl:choose>
         <axsl:when test="oa:UnitPrice | oa:PriceCode"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:UnitPrice | oa:PriceCode" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ItemPrice must have a "UnitPrice" or "PriceCode" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M10"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M10"/>
   <axsl:template match="oa:LinePriceBreak" priority="4000" mode="M11">
      <fired-rule id="" context="oa:LinePriceBreak" role=""/>
      <axsl:choose>
         <axsl:when test="oa:PriceCode | oa:DiscountValue | oa:DiscountPercent | OverRidePrice"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:PriceCode | oa:DiscountValue | oa:DiscountPercent | OverRidePrice" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The LinePriceBreak must have a "PriceCode" or "DiscountValue" or "DiscountPercent" or "OverRide Price" specified</text>
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
               <text>The LinePriceBreak must have a "PriceBreakQuantity" or "PriceBreakAmount" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M11"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M11"/>
   <axsl:template match="oa:ElectronicCatalog/oa:CatalogItemLine/oa:ItemClassification" priority="4000" mode="M12">
      <fired-rule id="" context="oa:ElectronicCatalog/oa:CatalogItemLine/oa:ItemClassification" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ClassificationStructureId | oa:ClassificationId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ClassificationStructureId | oa:ClassificationId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ItemClassification must have a "ClassificationStructureId" or "ClassificationId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M12"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M12"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>