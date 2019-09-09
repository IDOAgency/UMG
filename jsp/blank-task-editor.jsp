<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*, java.text.*"
			gets the context, form formValidation and message
   */
%>

<%@ include file="template-top-page.shtml"%>

<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Task Security Editor";

	// getting the login name
	User user = (User)context.getSessionValue("user");
	int userid = user.getUserId();
%>

<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include task-editor-javascript.js
  var form;
  var sort = "<%= MilestoneConstants.CMD_TASK_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TASK_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_TASKS%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TASK_EDITOR) %>";
  var save = "<%= MilestoneConstants.CMD_TASK_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_TASK_EDIT_SAVE_NEW%>";
  var editNew = "<%= MilestoneConstants.CMD_TASK_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_TASK_SEARCH%>";
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
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>blank-task-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_TASKS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";

} //end function submitResize()
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body bgcolor="white" text="black" topmargin="0" leftmargin="0" onLoad="processLoad(focusField('taskName'));">

<%=form.renderStart()%>
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
	<td width="170"><span class="title">Tasks</span></td>
  <td width=85>
  <!--
    <div align="center">
      <a href ="JavaScript:submitSave('Save')"
         onMouseOver="Javascript:mtbSave.over();return true;"
         onMouseOut="Javascript:mtbSave.out()"
         onClick="Javascript:mtbSave.click(); return true;">
         <img name="Save" src="<%= inf.getImageDirectory() %>Save_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
      </a>
      <script type="text/javascript" language="JavaScript" >
        var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', 'JavaScript:submitSave( "Save" )', 66,14);
      </script>
    </div>
  -->
  </td>
  <td width=85>
    <div align="center">
      <a href ="JavaScript:submitNew('New')"
         onMouseOver="Javascript:mtbNew.over();return true;"
         onMouseOut="Javascript:mtbNew.out()"
         onClick="Javascript:mtbNew.click(); return true;">
        <img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
      </a>
      <script type="text/javascript" language="JavaScript" >
         var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', 'JavaScript:submitNew( "New" )', 66, 14);
      </script>
    </div>
  </td>
  <td width=85>
  <!--
    <div align="center">
      <a href="JavaScript:submitDelete('Delete')"
         onMouseOver="Javascript:mtbDelete.over();return true;"
         onMouseOut="Javascript:mtbDelete.out()"
         onClick="Javascript:mtbDelete.click(); return true;">
         <img name="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
      </a>
      <script type="text/javascript" language="JavaScript" >
         var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', 'JavaScript:submitDelete( "Delete" )', 66,14);
      </script>
    </div>
  -->
  </td>
</tr>
<tr>

	<td valign="top">
<%//notepad visibility check
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{%>
  	<%/* ===============start users navigation panel=========================== -->
  	     This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here.*/ %>

       	 <%@ include file="include-task-security-notepad.shtml" %>

 		<%// ===============end selection navigation panel=========================== %>
<%} //end notepad vis check %>
	</td>


	<% //start task form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="516" bgcolor="lavender">
    <tr><td align="center"><b><h4>Milestone System<br><br><br>Please select a Task selection on the left panel to view</h4></b></td></tr>
  </table>
		</td>
	</tr>
	<tr><td valign="bottom"></td></tr>
	</td>
</tr></table>
</td></tr></table>

<% //DIV definitions %>
<% //start search div %>
<div id="searchLayer" class="search" onKeyPress="checkForEnter( 'submitSearch()' );" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr><td>
	<table width="100%">
	<tr><th colspan="2">Task Search</th></tr>
	<tr>
		<td class="label">Task Name</td>
		<td><%=form.getElement("taskNameSrch")%></td>
	</tr>
	<tr><% // ITS 281 %>
		<td class="label">Abbreviation</td>
		<td><%=form.getElement("taskAbbrevSrch")%>&nbsp; (exact match)</td>
	</tr>
	<tr>
		<td class="label">Key Task</td>
		<td align="center"><%=form.getElement("keyTaskSrch")%>only</td>
	</tr>
	<tr>
		<td class="label">Owner</td>
		<td>
      <%=form.getElement("ownerSrch")%>
		</td>
	</tr>
	<tr>
		<td class="label">Department</td>
		<td>
      <%=form.getElement("departmentSrch")%>
		</td>
	</tr>
	<tr><% // ITS 281 %>
		<td class="label">Allow Mult Complete Dates</td>
		<td><%=form.getElement("allowMultCompleteDatesSrch")%></td>
	</tr>
	<tr>
		<td colspan="2"><INPUT type=button value="Submit Search" onClick="submitSearch()"></td>
	</tr>
	</table>
	</td>
</tr>
</table>
</div>
<% //end search div %>

<% //start comment div %>
<div id="commentLayer" class="search" style="position:absolute;width:276px; height:60px; z-index:3; left: 470px; top: 15px;visibility:hidden">
<table width="290" border="1" cellspacing="0" cellpadding="1">
<tr><td>
	<table width="290">
	<tr><td class="label">Comments</td></tr>
	<tr>
		<td>
		  <%=form.getElement("commentHelper")%>
		</td>
	</tr>
	<tr>
		<td>
      <input type="button" value="Save" onClick="pushValue( commentHelper, comments );submitSave()" >&nbsp;
      <input type="button" name="closeButton" value="Cancel" onClick="toggle( 'commentLayer', 'comments' )"></td>
	</tr>
	</table>
</td></tr>
</table>
</div>
 <% //end comment div %>
<% //END DIV DEFINITIONS %>

<%@ include file="include-bottom-html.shtml"%>


