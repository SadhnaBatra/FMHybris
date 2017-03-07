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
         <active-pattern name="Sync Routing">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M2"/>
         <active-pattern name="Sync Routing Header">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M3"/>
         <active-pattern name="Route Operation">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M4"/>
         <active-pattern name="Item Data">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M5"/>
         <active-pattern name="Item">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M6"/>
         <active-pattern name="Assigned Operation Groups">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M7"/>
         <active-pattern name="Group Member">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M8"/>
         <active-pattern name="Operation">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M9"/>
         <active-pattern name="Step">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M10"/>
         <active-pattern name="Resources">
            <axsl:apply-templates/>
         </active-pattern>
         <axsl:apply-templates select="/" mode="M11"/>
      </schematron-output>
   </axsl:template>
   <axsl:template match="oa:Routing" priority="4000" mode="M2">
      <fired-rule id="" context="oa:Routing" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Header"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Header" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>Sync Routing must have a Header component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M2"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M2"/>
   <axsl:template match="oa:Routing/oa:Header" priority="4000" mode="M3">
      <fired-rule id="" context="oa:Routing/oa:Header" role=""/>
      <axsl:choose>
         <axsl:when test="oa:DocumentId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:DocumentId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Sync Routing Header must have a "DocumentId" and "Id" specified</text>
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
               <text>The Sync Routing Header must have a "DocumentId" and "Revision" specified</text>
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
               <text>The Sync Routing Header must have a "DocumentId" and "Type" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:RouteOperation"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:RouteOperation" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The Sync Routing Header must have a "RouteOperation" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M3"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M3"/>
   <axsl:template match="oa:Routing/oa:Header/oa:RouteOperation" priority="4000" mode="M4">
      <fired-rule id="" context="oa:Routing/oa:Header/oa:RouteOperation" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemData"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemData" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The RouteOperation must have at least one "ItemData" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M4"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M4"/>
   <axsl:template match="oa:Routing/oa:Header/oa:RouteOperation/oa:ItemData" priority="4000" mode="M5">
      <fired-rule id="" context="oa:Routing/oa:Header/oa:RouteOperation/oa:ItemData" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Item"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Item" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The ItemData must have an "Item" specified</text>
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
               <text>The ItemData must have a "ItemQuantity" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M5"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M5"/>
   <axsl:template match="oa:Routing/oa:Header/oa:RouteOperation/oa:ItemData/oa:Item" priority="4000" mode="M6">
      <fired-rule id="" context="oa:Routing/oa:Header/oa:RouteOperation/oa:ItemData/oa:Item" role=""/>
      <axsl:choose>
         <axsl:when test="oa:ItemId/oa:Id"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:ItemId/oa:Id" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>An Item must have an "ItemId" and "Id" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:Type"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Type" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>An Item must have an "Item" and "Type" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M6"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M6"/>
   <axsl:template match="oa:Routing/oa:Header/oa:AssignedOperationGroups" priority="4000" mode="M7">
      <fired-rule id="" context="oa:Routing/oa:Header/oa:AssignedOperationGroups" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationGroupName"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationGroupName" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The AssignedOperationGroups must have a "OperationGroupName" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:OperationGroupType"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationGroupType" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The AssignedOperationGroups must have a "OperationGroupType" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:choose>
         <axsl:when test="oa:GroupMember"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:GroupMember" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The AssignedOperationGroups must have a "GroupMember" component specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M7"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M7"/>
   <axsl:template match="oa:Routing/oa:Header/oa:AssignedOperationGroups/oa:GroupMember" priority="4000" mode="M8">
      <fired-rule id="" context="oa:Routing/oa:Header/oa:AssignedOperationGroups/oa:GroupMember" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>The GroupMember must have a "OperationId" specified</text>
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
               <text>The GroupMember must have a "OperationSequence" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M8"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M8"/>
   <axsl:template match="oa:Routing/oa:Line" priority="4000" mode="M9">
      <fired-rule id="" context="oa:Routing/oa:Line" role=""/>
      <axsl:choose>
         <axsl:when test="oa:OperationId"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:OperationId" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>An Operation Line Must have a "OperationId" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M9"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M9"/>
   <axsl:template match="oa:Line/oa:Step" priority="4000" mode="M10">
      <fired-rule id="" context="oa:Line/oa:Step" role=""/>
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
      <axsl:apply-templates mode="M10"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M10"/>
   <axsl:template match="oa:Resources/oa:MachineResource/oa:Machine" priority="4000" mode="M11">
      <fired-rule id="" context="oa:Resources/oa:MachineResource/oa:Machine" role=""/>
      <axsl:choose>
         <axsl:when test="oa:Id | oa:Classification"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:Id | oa:Classification" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A Machine Resource must have an "Id " or "Classification" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M11"/>
   </axsl:template>
   <axsl:template match="oa:Resources/oa:LaborResource/oa:Labor" priority="3999" mode="M11">
      <fired-rule id="" context="oa:Resources/oa:LaborResource/oa:Labor" role=""/>
      <axsl:choose>
         <axsl:when test="oa:EmployeeId | oa:Category"/>
         <axsl:otherwise>
            <failed-assert id="" test="oa:EmployeeId | oa:Category" role="">
               <axsl:attribute name="location">
                  <axsl:apply-templates select="." mode="schematron-get-full-path"/>
               </axsl:attribute>
               <text>A Labor Resource must have an "EmployeeId " or "Category" specified</text>
            </failed-assert>
         </axsl:otherwise>
      </axsl:choose>
      <axsl:apply-templates mode="M11"/>
   </axsl:template>
   <axsl:template match="oa:Resources/oa:ToolResource/oa:Tool" priority="3998" mode="M11">
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
      <axsl:apply-templates mode="M11"/>
   </axsl:template>
   <axsl:template match="text()" priority="-1" mode="M11"/>
   <axsl:template match="text()" priority="-1"/>
</axsl:stylesheet>