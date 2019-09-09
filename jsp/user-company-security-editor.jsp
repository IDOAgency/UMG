<%
		/* this include (template-top-page.jsp) does the following:
			 extends com.universal.milestone.MilestoneJSP
			 import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			 gets the context, form formValidation and message */
%>
<%@ include file="template-top-page.shtml"%>

<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "User-Company Security Editor";

  //Retrieve delivery objects
  //Company company = (Company)context.getDelivery("currentCompany");
  Environment environment = (Environment)context.getDelivery("currentEnvironment");
  User user = (User)context.getDelivery("currentUser");
  Acl acl = null;
  if (user != null)
    acl = user.getAcl();
%>

<%@ include file="template-top-html.shtml" %>
<script>
function HideLayer()
{
 mtbSearch.click();
 toggle( 'searchLayer', 'nameSrch');
}
</script>

<script type="text/javascript" language="JavaScript">
//global variables needed in include user-company-security-editor-javascript.js
  var form;
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_USER_ENVIRONMENTS%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var save = "<%= MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SAVE%>";
  var search = "<%= MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SEARCH%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";
  var sort = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SORT)%>";

function processLoad(pFocusField)
{
  focusField(pFocusField);
  form = document.forms[0];
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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>user-company-security-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">

<%
//DO NOT MOVE RESIZE TO AN INCLUDE WITHOUT ASKING BRETT FIRST!!!!
%>
function submitResize()
{
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_USER_ENVIRONMENTS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

</script>

<head>
<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body topmargin="0" leftmargin="0" onLoad="processLoad('password');">

<%= form.renderStart()%>
<%=form.getElement("cmd")%>

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
	<td width="170"><span class="title">User</span></td>
  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
		<div align="center">
			<a href="JavaScript:submitSave('Save')"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript">
          var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
		</div>
	</td>
	<td width="85"></td>
	<td width="85"></td>
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
       	  The include just gets and puts the code here as it was written here. */ %>

       	 <%@ include file="include-user-company-notepad.shtml" %>

 		<% //===============end selection navigation panel=========================== %>
		<%}%>
	</td>

	<% //start user-company form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td valign="top">
				<table width="100%">
				<tr>
					<td class="label" width="110">User Name</td>
					<td><%=user.getName()%></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="label" width="110">Environment Name</td>
					<td><%=environment.getName()%></td>
					<td class="label">Family Name</td>
					<td align="left">
					<%
					    String familyName = MilestoneHelper.getStructureName(environment.getParentID());
					 %>
					<%=familyName%>
					</td>
				</tr>
				</table>
				<table width="100%">
				<tr bgcolor="#ffffff">
					<td width="100%" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
				</tr>
				</table>
				<table class="detailList" width="100%" border="1" cellpadding="1" cellspacing="0">
				<tr><th colspan="4">&nbsp;&nbsp;labels</th></tr>
				<tr>
					<td width="50%">&nbsp;Selection</td>
					<td>
					<%if (acl.getAccessSelection())
					  {%>
					    <%=form.getElement("1")%>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Schedule</td>
					<td>
					<%if (acl.getAccessSchedule())
					  {%>
					    <%= form.getElement("2") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Manufacturing</td>
					<td>
					<%if (acl.getAccessManufacturing())
					  {%>
					    <%= form.getElement("3") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;PFM</td>
					<td>
					<%if (acl.getAccessPfmForm())
					  {%>
					    <%= form.getElement("4") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;BOM</td>
					<td>
					<%if (acl.getAccessBomForm())
					  {%>
					    <%= form.getElement("5") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Report</td>
					<td>
					<%if (acl.getAccessReport())
					  {%>
					    <%= form.getElement("6") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<th colspan="4">&nbsp;&nbsp;admin</th>
				</tr>
				<tr>
					<td width="50%">&nbsp;Template</td>
					<td>
					<%if (acl.getAccessTemplate())
					  {%>
					    <%= form.getElement("7") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Task</td>
					<td>
					<%if (acl.getAccessTask())
					  {%>
					    <%= form.getElement("8") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Day Type</td>
					<td>
					<%if (acl.getAccessDayType())
					  {%>
					    <%= form.getElement("9") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<th colspan="4">&nbsp;&nbsp;security</th>
				</tr>
				<tr>
					<td width="50%">&nbsp;User</td>
					<td>
					<%if (acl.getAccessUser())
					  {%>
					    <%= form.getElement("10") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Family</td>
					<td>
					<%if (acl.getAccessFamily())
					  {%>
					    <%= form.getElement("11") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Environment</td>
					<td>
					<%if (acl.getAccessEnvironment())
					  {%>
					    <%= form.getElement("20") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Company</td>
					<td>
					<%if (acl.getAccessCompany())
					  {%>
					    <%= form.getElement("12") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Division</td>
					<td>
					<%if (acl.getAccessDivision())
					  {%>
					    <%= form.getElement("13") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Label</td>
					<td>
					<%if (acl.getAccessLabel())
					  {%>
					    <%= form.getElement("14") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<th colspan="4">&nbsp;&nbsp;utilities</th>
				</tr>
				<tr>
					<td width="50%">&nbsp;Table</td>
					<td>
					<%if (acl.getAccessTable())
					  {%>
					    <%= form.getElement("15") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Parameter</td>
					<td>
					<%if (acl.getAccessParameter())
					  {%>
					    <%= form.getElement("16") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Audit Trail</td>
					<td>
					<%if (acl.getAccessAuditTrail())
					  {%>
					    <%= form.getElement("17") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Report Config</td>
					<td>
					<%if (acl.getAccessReportConfig())
					  {%>
					    <%= form.getElement("18") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Price Code</td>
					<td>
					<%if (acl.getAccessPriceCode())
					  {%>
					    <%= form.getElement("19") %>
					<%}
					  else
					  {%>
					    Not Available
					<%}%>
					</td>
				</tr>
				</table>
			</TD>
		</TR>
		<TR>
			<TD valign="bottom"></td>
		</tr>
		</table>
</td></tr></table>

<% //DIV definitions %>
<% //start search div %>
<div class="search" id="searchLayer" onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr><th colspan="2">Environment Search</th></tr>
		<tr>
			<td class="label">Environment Name</td>
			<td><%= form.getElement("nameSrch")%></td>
		</tr>
		<tr><td colspan="2"><INPUT type=button value="Submit Search" onClick="submitSearch()">
	    <colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
		</td></tr>
		</table>
	</td>
</tr>
</table>
</div>

<% //end search div %>
<% //END DIV DEFINITIONS %>

<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>


