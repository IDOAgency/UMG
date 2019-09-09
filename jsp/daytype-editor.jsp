<%
	/*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
	*/
%>

<%@ include file="template-top-page.shtml"%>
<script>
function HideLayer()
{
 mtbSearch.click();
 toggle( 'SearchLayer', 'GroupSearch' );
}
</script>
<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Day Type Editor";

	// getting the login name
	User user = (User)context.getSessionValue("user");
	int userid = user.getUserId();
%>

<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include daytype-editor-javascript.js
  var sort = "<%= MilestoneConstants.CMD_DAYTYPE_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_DAYTYPE_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_DAYTYPE%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_DAYTYPE_EDIT_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_DAYTYPE_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_DAYTYPE_EDIT_SAVE_NEW%>";
  var editNew = "<%= MilestoneConstants.CMD_DAYTYPE_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_DAYTYPE_SEARCH%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";

function processLoad(pFocusField)
{
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
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>daytype-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_DAYTYPE_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()
</script>



<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body topmargin="0" leftmargin="0" onLoad="processLoad('DayType');">

<%= form.renderStart() %>
<%= form.getElement("cmd") %>
<%= form.getElement("OrderBy") %>

<input type="hidden" name="parentForm" value="pfmForm"">


<%
String alertMessage = (String)context.getDelivery("AlertMessage");

if (alertMessage != null)
{
%>
 <script language="JavaScript">
alert('<%=alertMessage%>');
</script>
<%
}
%>

<table width="780" cellpadding="0" border="0">
<tr valign="middle" align="left">
   <td width="280">
      <!-- This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here. -->
      <%@ include file="include-newSelection.shtml" %>
   </td>
   <td rowspan="2" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>
   <td width="170"><span class="title">Day Type</span></td>
  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
		<div align="center" id="saveDiv">
		<a href ='JavaScript:submitSave( "Save" )'
		 onMouseOver="Javascript:mtbSave.over();return true;"
		 onMouseOut="Javascript:mtbSave.out()"
		 onClick="Javascript:mtbSave.click(); return true;">
 		<img name="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hpsace="0" vspace="0" align="absmiddle">
		</a>
		<script type="text/javascript" language="JavaScript">
  		var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', 'JavaScript:submitSave( "Save" )', 66, 14);
		</script>
		</div>
	</td>
	<td width="85">
		<div align="center" id="newDiv">
		<a href='JavaScript:submitNew( "New" )'
			onMouseOver="Javascript:mtbNew.over();return true;"
			onMouseOut="Javascript:mtbNew.out()"
			onClick="Javascript:mtbNew.click(); return true;">
 			<img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
		</a>
		<script type="text/javascript" language="JavaScript">
  		var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', 'JavaScript:submitNew( "New" )', 66, 14);
		</script>
		</div>
	</td>
	<td width="85">
		<div align="center" id="deleteDiv">
		<a href='JavaScript:submitDelete( "Delete" )'
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

<%
//notepad visibility check
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{%>

  	<% //===============start users navigation panel=========================== %>
  	<% /* This part of the code is made an include to make it easy to manage this page.
       	  The the java script needed resides in this page.
       	  The include just gets and puts the code here as it was written here.*/ %>
        <td valign="top">
       	 <%@ include file="include-daytype-security-notepad.shtml" %>
        </td>
 		<% //===============end selection navigation panel=========================== %>
<%} //end vis check %>




	<% //start task form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td valign="top" width="100%">
				<table width="100%">
				<tr>
					<td class="label">Group</td>
					<td><%= form.getElement("Group") %></td>
				</tr>
				<tr>
					<td class="label">Day Type</td>
					<td><%= form.getElement("DayType") %></td>
				</tr>
				<tr>
					<td class="label">Description</td>
					<td><%= form.getElement("Description") %></td>
				</tr>
				<tr>
					<td class="label">Special Date</td>
					<td><%= form.getElement("SpecialDate") %></td>
				</tr>
				</table>
			</td>
			</tr>
			<tr><td valign="bottom">
			<% if (form.getRenderableValue("cmd").equals("daytype-editor"))
				{
			%>
			<table><tr>
				<td class="label">Last Updated: </td>
						<td><%= form.getRenderableValue("lastUpdatedDate")%></td>
						<td><%= form.getRenderableValue("lastUpdatedBy")%></td>
			</tr></table>
			</table>
		<%} %>
		</td>
	</tr>
	<tr><td valign="bottom"></td></tr>
	</td>
</tr></table>
</td></tr></table>




<% //DIV definitions %>
<% //start search div %>
<div id="SearchLayer" class="search" onKeyPress="checkForEnter( 'submitSearch()' );" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0">
<tr><td>
	<table width="100%">
	<tr><th colspan="2">Day Type Search</th></tr>
	<tr>
		<td class="label">Group</td>
		<td><%= form.getElement("GroupSearch") %></td></tr>
		<tr>
			<td class="label">Special Date</td>
			<td><%= form.getElement("SpecialDateSearch") %></td>
		</tr>
		<tr>
			<td class="label">Day Type</td>
			<td><%= form.getElement("DayTypeSearch") %></td>
		</tr>
		<tr>
			<td class="label">Description</td>
			<td><%= form.getElement("DescriptionSearch") %></td>
		</tr>
		<tr>
			<td colspan=2><input type=button value="Submit Search" onClick="submitSearch()">
			<input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()"></td>
		</tr>
</table>
</td></tr></table>
</div>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>



<% //end search div %>
<% //END DIV DEFINITIONS %>

<%= form.renderEnd()%>


<%@ include file="include-bottom-html.shtml"%>


