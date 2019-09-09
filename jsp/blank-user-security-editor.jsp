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
	String htmlTitle = "User Security Editor";

	// getting the login name
	User user = (User)context.getSessionValue("user");
	int userid = user.getUserId();
	Acl acl = user.getAcl();
	Vector cAcl = Cache.getInstance().getEnvironmentList();
	Vector uAcl = MilestoneHelper.getUserEnvironments(userid);
%>

<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include division-editor-javascript.js
  var form;
  var sort = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_SECURITY_SORT)%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_SECURITY_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_USER%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_SECURITY_EDITOR) %>";
  var save = "<%= MilestoneConstants.CMD_USER_SECURITY_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_USER_SECURITY_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_USER_SECURITY_NEW%>";
  var search = "<%= MilestoneConstants.CMD_USER_SECURITY_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";

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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>blank-user-security-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">

<%
//DO NOT MOVE RESIZE TO AN INCLUDE WITHOUT ASKING BRETT FIRST!!!!
%>
function submitResize()
{
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_USER_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<%
String focusField = "password";
if (form.getRenderableValue("cmd").equals("user-security-new"))
{
  focusField = "login";
}
%>


<body bgcolor="white" text="black" topmargin="0" leftmargin="0" onLoad="processLoad(focusField('<%=focusField%>'));">
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
  <td width="85">
  <!--
		<div align="center">
			<a href='JavaScript:submitLogOff( "LogOff" )'
				onMouseOver="Javascript:mtbLogoff_Button.over();return true;"
				onMouseOut="Javascript:mtbLogoff_Button.out()"
				onClick="Javascript:mtbLogoff_Button.click(); return true;">
				<img name="Logoff_Button" src="<%= inf.getImageDirectory() %>Logoff_Button_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
			</a>
			<script type="text/javascript" language="JavaScript">
				var mtbLogoff_Button = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Logoff_Button', "JavaScript:submitLogOff( 'LogOff' )", 66, 14);
			</script>
		</div>
  -->
	</td>
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
	<td width="85">
		<div align="center">
			<a href='JavaScript:submitNew( "New" )'
				onMouseOver="Javascript:mtbNew.over();return true;"
				onMouseOut="Javascript:mtbNew.out()"
				onClick="Javascript:mtbNew.click(); return true;">
        <img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
      </a>
      <script type="text/javascript" language="JavaScript">
      	var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', "JavaScript:submitNew( 'New' )", 66,14);
      </script>
		</div>
	</td>
	<td width="85">
  <!--
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
  -->
	</td>
</tr>
<tr>
	<td valign="top">
  <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
  	<!-- ===============start users navigation panel=========================== -->
  	<!-- This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here. -->

       	 <%@ include file="include-users-notepad.shtml" %>

 		<!-- ===============end selection navigation panel=========================== -->
  <%}%>
	</td>

	<% //start user form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="516" bgcolor="lavender">
    <tr><td align="center"><b><h4>Milestone System<br><br><br>No user selected. Please select one on the left hand list or redo a search</h4></b></td></tr>
  </table>
		</td>
	</tr>
	<tr><td valign="bottom"></td></tr>
	</td>
</tr></table>
</td></tr></table>




<% //DIV definitions %>
<% //start search div  %>
<div class="search" id="searchLayer" onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr>
			<th colspan="2">User Search</th>
		</tr>
		<tr>
			<td class="label">Login Name</td>
			<td><input type="text" name="nameSrch" size=20></td>
		</tr>
		<tr>
			<td class="label">User Name</td>
			<td><input type=text name="userNameSrch" size=20></td>
		</tr>

             	<tr>
              		<td nowrap><b>Employed by</b></td>
        		<td colspan="1" width="50%">
			<%= form.getElement("employedBySrc") %>
			</td>

                </tr>

	     	<tr>
		   	<td class="label" >Environment</td>
		   	<td><%= form.getElement("EnvironmentDescriptionSearch") %></td>
	     	</tr>

		<tr>
			<td colspan="2"><input type=button value="Submit Search" onClick="submitSearch()"></td>
		</tr>
		</table>
	</td>
</tr>
</table>
</div>


<% //end search div %>
<% //END DIV DEFINITIONS %>

<%=form.renderEnd()%>


<%@ include file="include-bottom-html.shtml"%>


