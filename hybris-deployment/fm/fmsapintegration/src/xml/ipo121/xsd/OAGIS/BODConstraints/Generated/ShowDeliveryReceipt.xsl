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
         <active-pattern name="Receipt Line Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Receipt Unit Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Receipt Item Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:DeliveryReceipt" priority="4000" mode="M2">
      <fired-rule id="" context="oa:DeliveryReceipt" role=""/>
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
   <axsl:template match="oa:DeliveryReceipt/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:DeliveryReceipt/oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a DeliveryReceipt identifier.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:DocumentDate"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentDate" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Shipment Document TimeStamp.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:ShipFromParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:ShipFromParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have identify a "ShipFrom" Party.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:ShipFromParty/oa:PartyId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:ShipFromParty/oa:PartyId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>"ShipFrom" Party must have an Identifier.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:ShipFromParty/oa:Addresses/oa:Address"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:ShipFromParty/oa:Addresses/oa:Address" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>"ShipFrom" Party Address.</text>
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
               <text>Must have identify a "ShipTo" Party.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:ShipToParty/oa:PartyId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:ShipToParty/oa:PartyId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>"ShipTo" Party must have an Identifier.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:ShipToParty/oa:Addresses/oa:Address"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:ShipToParty/oa:Addresses/oa:Address" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>"ShipTo" Party Address.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:CarrierParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:CarrierParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have identify a "Carrier" Party.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:CarrierParty/oa:PartyId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:CarrierParty/oa:PartyId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>"Carrier" Party must have an Identifier.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ReceivedDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ReceivedDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ReceivedDateTime must be present.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ShippedDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ShippedDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ShippedDateTime must be present.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:DocumentReferences/oa:ShipmentDocumentReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentReferences/oa:ShipmentDocumentReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The shippers shipment (ASN) must be referenced.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:ReceiptLine" priority="4000" mode="M4">
      <fired-rule id="" context="oa:ReceiptLine" role=""/>
      <axsl:choose>
         <axsl:when test="oa:LineNumber"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:LineNumber" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Line Number</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ItemIds/oa:ItemId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemIds/oa:ItemId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have an Item.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ReceiptQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ReceiptQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have received an number of Items.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:ReceiptUnit" priority="4000" mode="M5">
      <fired-rule id="" context="oa:ReceiptUnit" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ShippingTrackingId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ShippingTrackingId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a tracking number</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:CarrierParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:CarrierParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must indicate the Carrier that is transporting the unit.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:ShipFromParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:ShipFromParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must identify the party in which the unit is being shipped from.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:ReceiptItem" priority="4000" mode="M6">
      <fired-rule id="" context="oa:ReceiptItem" role=""/>
      <axsl:choose>
         <axsl:when test="oa:PackingQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:PackingQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have number of items on the Packing List.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ReceivedQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ReceivedQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have number of items received.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>