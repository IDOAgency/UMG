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
	String htmlTitle = "Division Editor";
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
//global variables needed in include division-editor-javascript.js
  var sort = "<%= MilestoneConstants.CMD_DIVISION_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_DIVISION_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_DIVISION%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_DIVISION_EDIT_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_DIVISION_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_DIVISION_EDIT_SAVE_NEW%>";
  var editNew = "<%= MilestoneConstants.CMD_DIVISION_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_DIVISION_SEARCH%>";
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
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_DIVISION_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()



function processLoad(pFocusField,pAltField)
{
  initSelections();
pFocusField = pAltField;
//if new
  <% if (!form.getRenderableValue("cmd").equals("division-editor")) {%>
        pFocusField = "Parent2Selection";

  <%} %>
  // MSC 01/16/04 ITS 906 Check to see if the field is disabled
  objFld = eval("document.all." + pFocusField);
  if(objFld && objFld.disabled != true)
    focusField(pFocusField);
 //focusField(pFocusField,pAltField);
}
</script>

<%// include js files holding javascript functions %>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>division-editor-javascript.js"></script>


<% // Java script functions specific to this page %>
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


function initSelections()
{
  createChildren();

   if (document.forms[0].cmd.value == "division-edit-new")
	 {

   	  for( i = 0; i < lRoot.Children.length; i++ )
		  {
			  var newOption = new Option( lRoot.Children[ i ].Name, "" + lRoot.Children[ i ].ID, i == 0, i == 0 );
     	  document.all.Parent2Selection.options[ i ] = newOption;
   	  }
   	  adjustSelection( document.all.Parent2Selection );
	 }
} //end function initSelections()

var lRoot;
var eRoot;

function Node( pID, pName, pChildren )
{
  this.ID = pID;
  this.Name = pName;
  this.Children = pChildren;
} //end function Node()

//getting the drop down information
<%= context.getDelivery("CorporateArraysNew") %>;
<%= context.getDelivery("CorporateArrays") %>;

function clearCombo(obj)
{
	var i;
  for(i=obj.options.length; i>=0; i--)
		obj.options[i] = null;
   	obj.selectedIndex = -1;
} //end function clearCombo()

function getCompanyID(id)
{
  for( i = 0; i < lRoot.Children.length; i++ )
  {
    for( b = 0; b < lRoot.Children[i].Children.length; b++ )
    {
      if (id == lRoot.Children[i].Children[b].Name)
        return (lRoot.Children[i].Children[b].ID);
    }
  }
  return 0;
}

function populateCompany(obj, index)
{
   var id = 0;

   id = getCompanyID(index);

   var j = 0;
   for(var i=0; i<a[id].length; i=i+2)
   {
     document.all.Parent1Selection.options[j] = new Option(a[id][i+1], a[id][i+1]);
     j++;
   }
   document.all.Parent1Selection.options[0].selected=true;
} //end function populateCompany()

function clickEnvironment(obj)
{
  clearCombo(document.all.Parent1Selection);
  populateCompany(document.all.Parent1Selection, obj[obj.selectedIndex].value);
  return true;
} //end function clickEnvironment()


function adjustSelection( pSelection )
{
  switch( pSelection.name )
  {
    case "Parent2Selection":
      clearBox( document.all.Parent3Selection );
      clearBox( document.all.Parent1Selection );
      if( lRoot.Children.length > 0 )
      {
      	fillSelection( document.all.Parent3Selection,
        lRoot.Children[ document.all.Parent2Selection.selectedIndex ] );

      	fillSelection( document.all.Parent1Selection,
        lRoot.Children[ document.all.Parent2Selection.selectedIndex ].Children[document.all.Parent3Selection.selectedIndex] );
      }
      break;
    case "Parent3Selection":
      clearBox( document.all.Parent1Selection );
      if( lRoot.Children.length > 0 )
      {
      	fillSelection( document.all.Parent1Selection,
        lRoot.Children[ document.all.Parent2Selection.selectedIndex ].Children[document.all.Parent3Selection.selectedIndex] );
      }
  }
} //end function adjustSelection()

function clearBox( pSelection )
{
  pSelection.options.length = 0;
} //end function clearBox()

function fillSelection( pSelection, pEntry )
{
  var lChildren = pEntry.Children;
  for( i = 0; i < lChildren.length; i++ )
	{
    var newOption = new Option( lChildren[ i ].Name, lChildren[ i ].ID, i == 0, i == 0 );
    pSelection.options[ i ] = newOption;
  }
} //end function fillSelection()

function toggleSearch()
{
  toggle( 'SearchLayer', 'EnvironmentDescriptionSearch' );
} //end function toggleSearch()
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body topmargin="0" leftmargin="0" onLoad="processLoad('CorporateDescription','Parent1Selection');">

<%= form.renderStart()%>
<%= form.getElement("cmd") %>
<%= form.getElement("OrderBy")%>

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
	<td width="85"><span class="title">Division</span></td>

        <td width="85"><div id="waitLayer" style="font-size: 10pt; font-weight:bold; color:red; visibility:hidden;">Please Wait</div></td>

  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
	  <div align="center">
		<a href="JavaScript:submitSave('Save')"
		   onMouseOver="Javascript:mtbSave.over();return true;"
		   onMouseOut="Javascript:mtbSave.out()"
		   onClick="Javascript:mtbSave.click(); return true;">
		   <img name="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
		</a>
		<script type="text/javascript" language="JavaScript">
		  var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', 'JavaScript:submitSave( "Save" )', 66, 14);
		</script>
	  </div>
	</td>
	<td width="85">
	  <div align="center">
		<a href="JavaScript:submitNew('New')"
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
		<a href="JavaScript:submitDelete('Delete')"
		   onMouseOver="Javascript:mtbDelete.over();return true;"
		   onMouseOut="Javascript:mtbDelete.out()"
		   onClick="Javascript:mtbDelete.click(); return true;">
	 	   <img name="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
		</a>
		<script type="text/javascript" language="JavaScript">
  		  var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', 'JavaScript:submitDelete( "Delete" )', 66, 14);
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
  	<%  /* This part of the code is made an include to make it easy to manage this page.
       	   The the java script needed resides in this page.
       	   The include just gets and puts the code here as it was written here.*/ %>

    			<%@ include file="include-division-security-notepad.shtml" %>

 		<%// ===============end selection navigation panel=========================== %>
  <%}%>
	</td>

	<% //start Environment form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
	  <table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
		  <td valign="top" width="100%">
		    <table width="100%">
			  <tr>
					<td class="label">Family</td>
					<td>
						<% if (form.getRenderableValue("cmd").equals("division-editor") )
							{
						%>
                                                        <%= form.getElement("Parent2Selection") %>
						<%}

							else
							{%>
								<%= form.getElement("Parent2Selection") %>
							<%} %>
					</td><td class="label">Active</td><td > <%= form.getElement("active") %></td>
			  </tr>
			  <tr class="label">
					<td class="label">Environment</td>
					<td> <%= form.getElement("Parent3Selection") %></td>
			  </tr>

			  <tr class="label">
					<td class="label">Company</td>
					<td> <%= form.getElement("Parent1Selection") %></td>
			  </tr>
			  <tr>
					<td class="label">Division</td>
					<td><%= form.getElement("CorporateDescription") %></td>
			  </tr>
			  <tr>
					<td class="label">Abbreviation</td>
					<td><%= form.getElement("CorporateAbbreviation") %></td>
			  </tr>
				<% if (form.getRenderableValue("cmd").equals("division-editor"))
						 {
						%>
			  <tr>
					<td valign=top class="label">Label(s)</td>
           <%if(form.getElement("children") != null && ((FormDropDownMenu)form.getElement("children")).getMenuTextList().length > 0)
           {%>
					<td> <%= form.getElement("children") %></td>
          <%
          }
          else
          {%>
          <td>none</td>
          <%}%>
			  </tr>
				<%} %>
			</table>
	 	  </td>
	    </tr>
	    <tr>
       <td valign="bottom">

			 <% if (form.getRenderableValue("cmd").equals("division-editor"))
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
    	    <tr bgcolor="#BBBBBB"><th colspan="2">Division Search</th></tr>
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
    		    <td class="label" >Division</td>
		        <td><%= form.getElement("DivisionDescriptionSearch") %></td>
 	         </tr>

		 <tr>
			<td colspan=2>
			<input type="button" value="Submit Search" onClick="submitSearch()">
			<input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
		 	</td>
		 </tr>

	         <!--tr><td colspan="2"><input type="button" value="Submit Search" onClick="submitSearch()"></td></tr-->

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
// disable elements if this COS is part of Archimedes Structure
/////////////////////////////////////////////////////////////////////
<%
 String isArchiId = (String)context.getDelivery("isArchiId");
 if(isArchiId != null && !isArchiId.equals("0"))
 {
 %>
   // hide the delete button
   if(document.all.Delete)
     document.all.Delete.style.visibility = "hidden";
   // disable these elements
   if(document.all.Parent1Selection)
     document.all.Parent1Selection.disabled = true;
   if(document.all.Parent2Selection)
     document.all.Parent2Selection.disabled = true;
   if(document.all.Parent3Selection)
     document.all.Parent3Selection.disabled = true;
   if(document.all.CorporateDescription)
     document.all.CorporateDescription.disabled = true;
<%
 }
%>

</script>

<%@ include file="include-bottom-html.shtml"%>


