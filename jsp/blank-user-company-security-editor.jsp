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

<script type="text/javascript" language="JavaScript">
//global variables needed in include user-company-security-editor-javascript.js
  var form;
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_USER_ENVIRONMENTS%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var save = "<%= MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SAVE%>";
  var search = "<%= MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";
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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>blank-user-company-security-editor-javascript.js"></script>

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

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body bgcolor="white" text="black" topmargin="0" leftmargin="0" onLoad="processLoad(focusField('password'));">

<%= form.renderStart()%>
<%=form.getElement("cmd")%>

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
  <!--
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
  -->
	</td>
	<td width="85"></td>
	<td width="85"></td>
</tr>
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
			<td width="100%" align="center"><b><h4>Milestone System<br><br><br>Please search for User-Companies on the left panel to this view </h4></b></td>
	</tr>
	<tr><td valign="bottom">
	</td></tr>
	</td>
</tr></table>
</td></tr></table>

<% //DIV definitions %>
<% //start search div %>
<div class="search" id=searchLayer onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr><th colspan="2">Environment Search</th></tr>
		<tr>
			<td class="label">Environment Name</td>
			<td><INPUT type=text name="nameSrch" size=20></td>
		</tr>
		<tr><td colspan="2"><INPUT type=button value="Submit Search" onClick="submitSearch()"></td></tr>
		</table>
	</td>
</tr>
</table>
</div>


<% //end search div %>
<% //END DIV DEFINITIONS %>


<%=form.renderEnd()%>


<%@ include file="include-bottom-html.shtml"%>


