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
         <active-pattern name="Process WIPMove">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="Process WIPMove Header">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Process WIPMove Line">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Move From Operation">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Move To Operation">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="Operation Reference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="Operation">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
         <active-pattern name="Step">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M9"/>
         <active-pattern name="Resources">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M10"/>
         <active-pattern name="Production Order Reference">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M11"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:WIPMove" priority="4000" mode="M2">
      <fired-rule id="" context="oa:WIPMove" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPMove must have a Header component specified</text>
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
               <text>The Process WIPMove must have at least one Line component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:WIPMove/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:WIPMove/oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentDateTime"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentDateTime" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPMove Header must must have a "DocumentDateTime" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:WIPMove/oa:Line" priority="4000" mode="M4">
      <fired-rule id="" context="oa:WIPMove/oa:Line" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ScrapQuantity | RejectedQuantity | WIPMoveQuantity"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ScrapQuantity | RejectedQuantity | WIPMoveQuantity" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPMove Line must have a "ScrapQuantity" or "RejectedQuantity" or "WIPMoveQuantity" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:MoveFromOperation"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:MoveFromOperation" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPMove Line must have a "MoveFromOperation" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:MoveToOperation"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:MoveToOperation" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Process WIPMove Line must have a "MoveToOperation" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:WIPMove/oa:Line/oa:MoveFromOperation" priority="4000" mode="M5">
      <fired-rule id="" context="oa:WIPMove/oa:Line/oa:MoveFromOperation" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationReference"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationReference" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The MoveFromOperation must have a "OperationReference" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:WIPMove/oa:Line/oa:MoveToOperation" priority="4000" mode="M6">
      <fired-rule id="" context="oa:WIPMove/oa:Line/oa:MoveToOperation" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationReference | oa:Operation"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationReference | oa:Operation" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The MoveToOperation must have a "OperationReference" or "Operation" specified</text>
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
               <text>An OperationReference must have a "OperationId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:Operation" priority="4000" mode="M8">
      <fired-rule id="" context="oa:Operation" role=""/>
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
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="oa:WIPMove/oa:Line/oa:MoveToOperation/oa:Step" priority="4000" mode="M9">
      <fired-rule id="" context="oa:WIPMove/oa:Line/oa:MoveToOperation/oa:Step" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A Step must have an "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:StepType"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:StepType" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A Step must have an "StepType" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M9"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M9"/>
   <axsl:template match="oa:Resources/oa:MachineResource/oa:Machine" priority="4000" mode="M10">
      <fired-rule id="" context="oa:Resources/oa:MachineResource/oa:Machine" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Id | oa:Classification"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Id | oa:Classification" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A Machine Resource must have a "Id" or "Classification" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M10"/>
   </axsl:template>
   <axsl:template match="oa:Resources/oa:LaborResource/oa:Labor" priority="3999" mode="M10">
      <fired-rule id="" context="oa:Resources/oa:LaborResource/oa:Labor" role=""/>
      <axsl:choose>
         <axsl:when test="oa:EmployeeId | oa:Category"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EmployeeId | oa:Category" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A Labor Resource must have a "EmployeeId" or "Category" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M10"/>
   </axsl:template>
   <axsl:template match="oa:Resources/oa:ToolResource/oa:Tool" priority="3998" mode="M10">
      <fired-rule id="" context="oa:Resources/oa:ToolResource/oa:Tool" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ToolId | oa:ToolClassification"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ToolId | oa:ToolClassification" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A Tool Resource must have a "ToolId" or "ToolClassification" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M10"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M10"/>
   <axsl:template match="oa:ProductionOrderReference" priority="4000" mode="M11">
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
      <axsl:apply-templates mode="M11"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M11"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>