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
         <active-pattern name="Tax Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:PurchaseLedgerInvoice" priority="4000" mode="M2">
      <fired-rule id="" context="oa:PurchaseLedgerInvoice" role=""/>
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
         <axsl:when test="oa:Line"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Line" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have at least one Line component.</text>
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
         <axsl:when test="oa:DocumentReferences/oa:InvoiceDocumentReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentReferences/oa:InvoiceDocumentReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a reference to the unapproved Invoice</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Parties/oa:SupplierParty"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Parties/oa:SupplierParty" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have identify a "Supplier" Party</text>
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
         <axsl:when test="oa:EntryAmount"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EntryAmount" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have the entry amount of the Line.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:Tax" priority="4000" mode="M5">
      <fired-rule id="" context="oa:Tax" role=""/>
      <axsl:choose>
         <axsl:when test="oa:TaxAmount"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:TaxAmount" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Required to have the TaxAmount of the Tax.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>