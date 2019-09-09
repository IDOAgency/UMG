<%
	/*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
	*/
%>

<%@ include file="template-top-page.shtml"%>

<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Label Editor";
%>

<%@ include file="template-top-html.shtml" %>
<script>
function HideLayer()
{
 mtbSearch.click();
 toggle( 'SearchLayer', 'EnvironmentDescriptionSearch');
}
</script>

<script type="text/javascript" language="JavaScript">
//global variables needed in include label-editor-javascript.js
  var sort = "<%= MilestoneConstants.CMD_LABEL_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_LABEL_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_LABEL%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_LABEL_EDIT_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_LABEL_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_LABEL_EDIT_SAVE_NEW%>";
  var editNew = "<%= MilestoneConstants.CMD_LABEL_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_LABEL_SEARCH%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";


<% if(context.getDelivery("AlertMessage") != null)
  {%>
    alert("<%=(String)context.getDelivery("AlertMessage")%>");
 <%}%>


function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_LABEL_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

</script>

<script>
var lRoot;
function Node( pID, pName, pChildren )
{
  this.ID = pID;
  this.Name = pName;
  this.Children = pChildren;
}//Node

<%= context.getDelivery("CorporateArrays") %>;

function initSelections()
{
   createChildren();
   if (document.forms[0].cmd.value == "label-edit-new")
   {
     for( i = 0; i < lRoot.Children.length; i++ )
     {
       var newOption = new Option( lRoot.Children[ i ].Name, "" + lRoot.Children[ i ].ID, i == 0, i == 0 );
       document.all.Parent3Selection.options[ i ] = newOption;
     }
     adjustSelection( document.all.Parent3Selection );
   }
}//end initSelections

function processLoad(pFocusField,pAltField)
{
  initSelections();

  //If label-editor - New - then
  //set focus field to be 'Parent3Selection'
pFocusField = pAltField;
  <% if (!form.getRenderableValue("cmd").equals("label-editor")) {%>
        pFocusField = "Parent3Selection";
  <%} %>
//  focusField(pFocusField,pAltField);
  // MSC 01/16/04 ITS 906 Check to see if the field is disabled
  objFld = eval("document.all." + pFocusField);
  if(objFld && objFld.disabled != true)
    focusField(pFocusField);
}

</script>

<% //include js files holding javascript functions %>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>label-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">
function fillAbbreviation()
{
  if( document.all.CorporateAbbreviation.value.length == 0 )
  {
    if( document.all.CorporateDescription.value.length >= 3 )
		{
      document.all.CorporateAbbreviation.value = document.all.CorporateDescription.value.substring( 0, 3 );
    }
		else
	  {
      document.all.CorporateAbbreviation.value = document.all.CorporateDescription.value;
    }
  }
} //end function fillAbbreviation()



// JP ENV AFE
function adjustSelection( pSelection ) {
  switch( pSelection.name ) {
    case "Parent3Selection":
        clearBox( document.all.Parent4Selection );
        if( lRoot.Children.length > 0 ) {
          fillSelection( document.all.Parent4Selection,
                         lRoot.Children[ document.all.Parent3Selection.selectedIndex ] );
        }
    case "Parent4Selection":
      clearBox( document.all.Parent2Selection );
      if( lRoot.Children.length > 0 ) {
        if( lRoot.Children[ document.all.Parent3Selection.selectedIndex ].Children.length > 0 ) {
            fillSelection( document.all.Parent2Selection,
                           lRoot.Children[ document.all.Parent3Selection.selectedIndex ].Children[ document.all.Parent4Selection.selectedIndex ] );
        }
      }
    case "Parent2Selection":
      clearBox( document.all.Parent1Selection );
      if( lRoot.Children.length > 0 ) {
        if( lRoot.Children[ document.all.Parent3Selection.selectedIndex ].Children.length > 0 )
        {
          if (lRoot.Children[ document.all.Parent3Selection.selectedIndex ].Children[ document.all.Parent4Selection.selectedIndex ].Children.length > 0)
          {
            fillSelection( document.all.Parent1Selection,
                           lRoot.Children[ document.all.Parent3Selection.selectedIndex ].Children[ document.all.Parent4Selection.selectedIndex ].Children[document.all.Parent2Selection.selectedIndex] );
          }
        }
      }
  }
}//end adjustSelection

function clearBox( pSelection ) {
  pSelection.options.length = 0;
}//end clearBox

function fillSelection( pSelection, pEntry ) {
  var lChildren = pEntry.Children;
  for( i = 0; i < lChildren.length; i++ ) {
    var newOption = new Option( lChildren[ i ].Name, lChildren[ i ].ID, i == 0, i == 0 );
    pSelection.options[ i ] = newOption;
  }
}//fill Selection

</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body
String isArchiId = form.getStringValue("archimedesId");//ITS 1073 - jo - 2/15/2005
%>
<body topmargin="0" leftmargin="0" onLoad="processLoad('CorporateDescription','Parent1Selection');">
<%= form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>

<table width="780" cellpadding="0" border="0">
  <tr valign="middle" align="left">
    <td width="280">
	  <div align="left">
    	<a href ="JavaScript:submitResize()"
          onMouseOver="Javascript:mtbToggle.over();return true;"
          onMouseOut="Javascript:mtbToggle.out()"
          onClick="Javascript:mtbToggle.click(); return true;">
          <img name="Toggle" id="Toggle" src="<%= inf.getImageDirectory() %>Toggle_On.gif" width="27" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
    	</a>
        <script type="text/javascript" language="JavaScript">
      	  var mtbToggle = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Toggle', "JavaScript:submitResize()", 27, 14);
		</script>
	  </div>
	</td>
	<td rowspan="2" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>
	<td width="85"><span class="title">Label</span></td>

        <td width="85"><div id="waitLayer" style="font-size: 10pt; font-weight:bold; color:red; visibility:hidden;">Please Wait</div></td>

  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
      <div align="center">
	    <a href="JavaScript:submitSave('Save')"
		   onMouseOver="Javascript:mtbSave.over();return true;"
		   onMouseOut="Javascript:mtbSave.out()"
		   onClick="Javascript:mtbSave.click(); return true;">
		   <img name="Save" src="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
	    </a>
	    <script type="text/javascript" language="JavaScript">
		 		var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', 'JavaScript:submitSave( "Save" )', 66,14);
	    </script>
	  </div>
	</td>
	<td width="85">
	  <div align="center">
		<a href='JavaScript:submitNew( "New" )'
		   onMouseOver="Javascript:mtbNew.over();return true;"
	  	   onMouseOut="Javascript:mtbNew.out()"
		   onClick="Javascript:mtbNew.click(); return true;">
		   <img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
		</a>
		<script type="text/javascript" language="JavaScript">
  		  var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', 'JavaScript:submitNew( "New" )', 66,14);
		</script>
	  </div>
	</td>
	<td width="85">
	  <div align="center">
		<a href='JavaScript:submitDelete( "Delete" )'
		   onMouseOver="Javascript:mtbDelete.over();return true;"
		   onMouseOut="Javascript:mtbDelete.out()"
		   onClick="Javascript:mtbDelete.click(); return true;">
		   <img name="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
		</a>
		<script type="text/javascript" language="JavaScript">
		  var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', 'JavaScript:submitDelete( "Delete" )', 66,14);
		</script>
	  </div>
	</td>
  </tr>
   <% if (formValidation != null)
     {%>
  <tr><td colspan=6 height=10>&nbsp;</td></tr>
      <% Vector instructions = formValidation.getInstructions();
         for (int i = 0; i < instructions.size(); i++)
         {%>
 <tr>
   <td></td>
   <td></td>
   <td colspan=4 align="left">
           <%=  instructions.get(i)%>
   </td>
 </tr>
       <%}%>
 <tr><td colspan=6 height=10>&nbsp;</td></tr>
    <%}%>
  <tr>
	<td valign="top">
  <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
  	<% //===============start users navigation panel=========================== %>
  	<% /* This part of the code is made an include to make it easy to manage this page.
       	  The the java script needed resides in this page.
       	  The include just gets and puts the code here as it was written here.*/ %>

       	 <%@ include file="include-label-security-notepad.shtml" %>

 		<% //===============end selection navigation panel=========================== %>
  <%}%>
	</td>

	<% //start family form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
	  <table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
		  <td valign="top" width="100%">
		    <table width="100%">
			  <tr>
					<td class="label">Family</td>
					<td>
						<% if (form.getRenderableValue("cmd").equals("label-editor"))
							{
						%>
							<%= form.getElement("Parent3Selection") %>
						<%}
							else
							{
						%>
							<%= form.getElement("Parent3Selection") %>
						<%} %>
					</td><td class="label">Active</td><td > <%= form.getElement("active") %></td>
			  </tr>
			  <tr class="label">
					<td class="label">Environment</td>
					<td>
						<% if (form.getRenderableValue("cmd").equals("label-editor"))
							{
						%>
                                                <%= form.getElement("Parent4Selection") %>
						<%}
							else
							{
						%>
                                                  <%= form.getElement("Parent4Selection") %>
						<%} %>
					</td>
			  </tr>
			  <tr class="label">
					<td class="label">Company</td>
					<td>
						<% if (form.getRenderableValue("cmd").equals("label-editor"))
							{
						%>
                                                <%= form.getElement("Parent2Selection") %>
						<%}
							else
							{
						%>
                                                  <%= form.getElement("Parent2Selection") %>
						<%} %>
					</td>
			  </tr>
			  </tr>
			  <tr class="label">
					<td class="label">Division</td>
					<td>
						<% if (form.getRenderableValue("cmd").equals("label-editor"))
							{
						%>
							<%= form.getElement("Parent1Selection") %>
						<%}
							else
							{
						%>
							<%= form.getElement("Parent1Selection") %>
						<%} %>
					</td>
			  </tr>
			  <tr>
					<td class="label">Label</td>
					<td><%= form.getElement("CorporateDescription") %></td>
			  </tr>
                          <tr>
					<td class="label">Distribution</td>
					<td><%= form.getElement("Distribution") %></td>
			  </tr>
                          <tr>
					<td class="label">Distribution Company</td><!--ITS 1073 - jo - 2/15/2005-->
					<td><%= form.getElement("DistributionCompany") %></td>
			  </tr>
                           <tr>
					<td class="label">Abbreviation</td>
					<td><%= form.getElement("CorporateAbbreviation") %></td>
			  </tr>
			</table>
		  </td>
	    </tr>

        <% if (form.getRenderableValue("cmd").equals("label-editor"))
				{
				%>
       <tr>
      	<td valign="bottom">
					<table>
        	<tr>
						<th class="label" style="color: blue; text-decoration: underline">Legacy SOP - Archimedes Coding</td>
					</tr>
        	<tr>
						<td class="label">Archimedes Id: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue;">
            <%= form.getRenderableValue("archimedesId")%></td>
					</tr>
        	<tr>
						<td class="label" >Entity Name: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue;">
            <%= form.getRenderableValue("entityName")%>
            </td>
					</tr>
        	<tr>
						<td class="label" >Entity Type: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue;">
            <%= form.getRenderableValue("entityType")%>
            </td>
					</tr>

        	<tr>
						<td class="label"  >Legacy Op Co: </td>
						<td  style="font-size: 8pt; font-weight: bold; color: blue; ">
            <%= form.getRenderableValue("legacyOpCo")%></td>
					</tr>
        	<tr>
						<td class="label"  >Legacy Op Unit: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue; ">
            <%= form.getRenderableValue("legacyOpUnit")%></td>
					</tr>
        	<tr>
						<td class="label" >Legacy Super Label: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue;">
             <%= form.getRenderableValue("legacySuperLabel")%></td>
					</tr>
        	<tr>
						<td class="label" >Legacy Sub Label: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue;">
            <%= form.getRenderableValue("legacySubLabel")%></td>
					</tr>
        	<tr>
						<td class="label" >Level Of Activity: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue;">
            <%= form.getRenderableValue("levelOfActivity")%></td>
					</tr>
         	<tr>
						<td class="label" >Production Group: </td>
						<td style="font-size: 8pt; font-weight: bold; color: blue;">
            <%= form.getRenderableValue("productionGroupCode")%></td>
					</tr>
         	<tr>
		<td class="label" >APNG Indicator: </td>
		<td style="font-size: 8pt; font-weight: bold; color: blue;">
            <%= form.getElement("APNGInd")%>
                </td>
		</tr>

         	<tr>
		<td class="label" >Dist Co:        </td>
		<td style="font-size: 8pt; font-weight: bold; color: blue;">
                    <% if(isArchiId != null && !isArchiId.equals("0"))
                    {%>
                    <%= form.getRenderableValue("DistCoName")%>
                    <% } %>
                </td>
		</tr>


        	</table>
 	      </td>
      </tr>
     <%}%>

	    <tr>
      	<td valign="bottom">
		    <% if (form.getRenderableValue("cmd").equals("label-editor"))
					{
				%>
					<table>
        	<tr>
						<td class="label">Last Updated: </td>
						<td><%= form.getRenderableValue("lastUpdatedDate")%></td>
						<td><%= form.getRenderableValue("lastUpdatedBy")%></td>
					</tr>
        	</table>
				<%} %>
 	      </td>
      </tr>
	  </td>
    </tr>
  </table>

  <% //DIV definitions %>
  <% //start search div %>
  <div id="SearchLayer" class="search" onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
    <table width="100%" border="1" cellspacing="0" cellpadding="1">
      <tr>
        <td bgcolor="wheat">
	      <table width="100%">
	        <tr bgcolor="#BBBBBB">
              <th colspan="2">Label Search</th>
            </tr>
	        <tr>
	                <td class="label">Family</td>
		        <td><%= form.getElement("FamilyDescriptionSearch") %></td>
	        </tr>
	        <tr>
		        <td class="label">Environment</td>
		      	<td><%= form.getElement("EnvironmentDescriptionSearch") %></td>
 	       </tr>
	       <tr>
		     	<td class="label">Company</td>
		     	<td><%= form.getElement("CompanyDescriptionSearch") %></td>
		   	 </tr>
	       <tr>
		     	<td class="label">Division</td>
		     	<td><%= form.getElement("DivisionDescriptionSearch") %></td>
	       </tr>
	       <tr>
		     	<td class="label">Label</td>
		     	<td><%= form.getElement("LabelDescriptionSearch") %></td>
	       </tr>
	       <tr>
		     	<td class="label">Entity</td>
		     	<td><%= form.getElement("EntityDescriptionSearch") %></td>
	       </tr>
	       <tr><td colspan=2><input type="button" value="Submit Search" onClick="submitSearch()">
	       <colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()"></
		</td></tr>
	     </table>
         </td>
       </tr>
    </table>
	</td>
</tr>
</table>
</div>

   <% //end search div %>
   <% //END DIV DEFINITIONS%>
   <%= form.renderEnd()%>


<script>
// MSC 01/16/04 ITS 906 If Archimedes Structure donot allow modification
//////////////////////////////////////////////////////////////////////
// disable elements if this label is part of Archimedes Structure
/////////////////////////////////////////////////////////////////////
<%

 if(isArchiId != null && !isArchiId.equals("0"))
 {
 %>
   // hide the delete button
   if(document.all.Delete)
     document.all.Delete.style.visibility = "hidden";
   // disable the family,Environment,Division,Company,Label dropdowns
   if(document.all.Parent3Selection)
     document.all.Parent3Selection.disabled = true;
   if(document.all.Parent3Selection)
     document.all.Parent4Selection.disabled = true;
   if(document.all.Parent4Selection)
     document.all.Parent2Selection.disabled = true;
   if(document.all.Parent2Selection)
     document.all.Parent1Selection.disabled = true;
   if(document.all.Parent1Selection)
     document.all.Parent3Selection.disabled = true;
   if(document.all.CorporateDescription)
     document.all.CorporateDescription.disabled = true;
   if(document.all.Distribution)
   {
    document.all.Distribution[0].disabled = true;
    document.all.Distribution[1].disabled = true;
    document.all.Distribution[2].disabled = true;
   }
   if (document.all.DistributionCompany)//--ITS 1073 - jo - 2/15/2005
   {
    document.all.DistributionCompany.disabled = true;
   }
<%
 }
%>

</script>


<%@ include file="include-bottom-html.shtml"%>

</body>
</html>
