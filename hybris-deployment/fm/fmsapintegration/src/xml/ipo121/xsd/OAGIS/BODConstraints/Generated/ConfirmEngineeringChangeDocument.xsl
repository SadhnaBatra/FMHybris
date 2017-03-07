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
         <active-pattern name="Confirm Engineering Change Document">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="Confirm Engineering Change Document Header">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Engineering Change Revision">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Engineering Change Reviewer">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Revised BOM">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="BOMDocumentReference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="RouteDocumentReference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
         <active-pattern name="Revised BOM Component">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M9"/>
         <active-pattern name="Item">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M10"/>
         <active-pattern name="Revised Component Substitute">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M11"/>
         <active-pattern name="Revised Component Revision">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M12"/>
         <active-pattern name="Revised Reference Designator">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M13"/>
         <active-pattern name="Revised Route Header">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M14"/>
         <active-pattern name="Operation Reference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M15"/>
         <active-pattern name="Revised Route Operation">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M16"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:EngineeringChangeDocument" priority="4000" mode="M2">
      <fired-rule id="" context="oa:EngineeringChangeDocument" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Confirm Engineering Change Document must have a Header component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:Header" role=""/>
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
      <axsl:choose>
         <axsl:when test="oa:Site"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Site" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Header must have a Site specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:EngineeringChangeRevision"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EngineeringChangeRevision" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Header must have an "EngineeringChangeRevision" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:Header/oa:EngineeringChangeRevision" priority="4000" mode="M4">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:Header/oa:EngineeringChangeRevision" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Revision"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Revision" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The EngineeringChangeRevision must have a "Revision" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:CreationDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:CreationDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The EngineeringChangeRevision must have a "CreationDateTime" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:Header/oa:EngineeringChangeReviewer" priority="4000" mode="M5">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:Header/oa:EngineeringChangeReviewer" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Reviewer/oa:EmployeeId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Reviewer/oa:EmployeeId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The EngineeringChangeReviewer must have a "Reviewer" and "EmployeeId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Status"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Status" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The EngineeringChangeReviewer must have a "Status" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedBOM" priority="4000" mode="M6">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedBOM" role=""/>
      <axsl:choose>
         <axsl:when test="oa:EffectiveDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EffectiveDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedBOM must have an "EffectiveDateTime" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:BOMDocumentReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:BOMDocumentReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedBOM must have a "BOMDocumentReference" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="oa:BOMDocumentReference" priority="4000" mode="M7">
      <fired-rule id="" context="oa:BOMDocumentReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The BOMDocumentReference must have a "DocumentId" and an "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Type"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Type" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The BOMDocumentReference must have a "Type" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:RouteDocumentReference" priority="4000" mode="M8">
      <fired-rule id="" context="oa:RouteDocumentReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RouteDocumentReference must have a "DocumentId" and an "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Revision"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Revision" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RouteDocumentReference must have a "Revision" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent" priority="4000" mode="M9">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Item"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Item" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedBOMComponent must have an "Item" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:EffectivePeriod/oa:From"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EffectivePeriod/oa:From" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedBOMComponent must have a "From Date" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OperationSequence"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationSequence" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedBOMComponent must have an Operation Sequence specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M9"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M9"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:Item" priority="4000" mode="M10">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:Item" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Item must have an"ItemId" and an "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ItemId/oa:Revision"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId/oa:Revision" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Item must have a "Revision" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M10"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M10"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:RevisedComponentSubstitute" priority="4000" mode="M11">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:RevisedComponentSubstitute" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Item/oa:ItemId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Item/oa:ItemId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedComponentSubstitute must have an "Item" and "ItemId" and "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:EffectivePeriod/oa:From"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EffectivePeriod/oa:From" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedComponentSubstitute must have an "EffectivePeriod" and "FromDate" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:ItemQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedComponentSubstitute must have an "ItemQuantity" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M11"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M11"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:RevisedComponentRevision" priority="4000" mode="M12">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:RevisedComponentRevision" role=""/>
      <axsl:choose>
         <axsl:when test="oa:oa:ItemRevision"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:oa:ItemRevision" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedComponentRevision must have an "ItemRevision" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:EffectivePeriod/oa:From"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EffectivePeriod/oa:From" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedComponentRevision must have a "FromDate" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OperationSequence"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationSequence" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedComponentRevision must have an "OperationSequence" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M12"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M12"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:RevisedReferenceDesignator" priority="4000" mode="M13">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedBOM/oa:RevisedBOMComponent/oa:RevisedReferenceDesignator" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ReferenceDesignatorId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ReferenceDesignatorId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedReferenceDesignator must have an "ReferenceDesignatorId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M13"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M13"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedRouteHeader" priority="4000" mode="M14">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedRouteHeader" role=""/>
      <axsl:choose>
         <axsl:when test="oa:RouteDocumentReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:RouteDocumentReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RevisedRouteHeader must have a "Route Document Reference" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M14"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M14"/>
   <axsl:template match="oa:OperationReference" priority="4000" mode="M15">
      <fired-rule id="" context="oa:OperationReference" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationId | oaOperationGroupName"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationId | oaOperationGroupName" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>An OperationReference must have an "OperationId" or an "OperationGroupName" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OperationSequence"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationSequence" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>An OperationReference must have an "Operation Sequence" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M15"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M15"/>
   <axsl:template match="oa:EngineeringChangeDocument/oa:RevisedRouteHeader/oa:RevisedRouteOperation" priority="4000" mode="M16">
      <fired-rule id="" context="oa:EngineeringChangeDocument/oa:RevisedRouteHeader/oa:RevisedRouteOperation" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A RevisedRouteOperation must have a "Operation Reference" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:EffectivePeriod/oa:From"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EffectivePeriod/oa:From" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A RevisedRouteOperation must have a "From Date" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M16"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M16"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>