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
         <active-pattern name="Party Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Contact Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="Attachment Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="Tax Level">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:Payable" priority="4000" mode="M2">
      <fired-rule id="" context="oa:Payable" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Header.</text>
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
               <text>Must have a Line.</text>
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
               <text>Must have a DocumentId.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:DocumentDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a DocumentDateTime.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Total"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Total" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a Total Amount.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:DocumentRefeneces/oa:VoucherDocumentReference | oa:DocumentReferences/oa:InvoiceDocumentReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentRefeneces/oa:VoucherDocumentReference | oa:DocumentReferences/oa:InvoiceDocumentReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a DocumentRefence to the Voucher document or an approved Invoice.</text>
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
               <text>Must have a "Supplier" Party.</text>
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
               <text>Must have a Line Number.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:GLNominalAccount"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:GLNominalAccount" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Must have a GLNominalAccount.</text>
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
               <text>Must have a EntryAmount.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:Party" priority="4000" mode="M5">
      <fired-rule id="" context="oa:Party" role=""/>
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
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:Contact" priority="4000" mode="M6">
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
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="oa:Attachment" priority="4000" mode="M7">
      <fired-rule id="" context="oa:Attachment" role=""/>
      <axsl:choose>
         <axsl:when test="oa:EmbeddedData | oa:URI | oa:FileName | oa:ISBN"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EmbeddedData | oa:URI | oa:FileName | oa:ISBN" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Contact must have either EmbeddedData, URI, FileName or ISBN.</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:Tax" priority="4000" mode="M8">
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
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>