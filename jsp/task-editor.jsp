<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*, java.text.*"
			gets the context, form formValidation and message
   */
%>

<%@ include file="template-top-page.shtml"%>
<script>
function HideLayer()
{
 mtbSearch.click();
 toggle( 'searchLayer', 'taskNameSrch');

}
</script>
<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Task Security Editor";

	// getting the login name
	User user = (User)context.getSessionValue("user");
	int userid = user.getUserId();

	 // this will check the access right for a user
	 boolean saveVisible = false;
   boolean deleteVisible = false;
   boolean copyVisible = false;
   boolean newVisible = false;

	 saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
   deleteVisible = ((String)context.getDelivery("deleteVisible")).equals("true");
   copyVisible = ((String)context.getDelivery("copyVisible")).equals("true");
   newVisible = ((String)context.getDelivery("newVisible")).equals("true");
%>

<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include task-editor-javascript.js
  var form;
  var sort = "<%= MilestoneConstants.CMD_TASK_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TASK_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_TASKS%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TASK_EDIT_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_TASK_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_TASK_EDIT_SAVE_NEW%>";
  var copy = "<%= MilestoneConstants.CMD_TASK_EDIT_COPY%>";
  var editNew = "<%= MilestoneConstants.CMD_TASK_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_TASK_SEARCH%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";

  var multipleMessage = "";

<%
if (context.getDelivery("MultipleMessage") != null)
{
%>
multipleMessage = '<%= (String)context.getDelivery("MultipleMessage")%>';
<%
}
%>

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
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>task-editor-javascript.js"></script>

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
<body topmargin="0" leftmargin="0" onLoad="processLoad('taskName');">

<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>

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
	<td width="170">
	<span class="title">Tasks</span>
	</td>
  <TD width=85>
  </TD>
      <td width="85">
      <div align="center" id="copyDiv">
      <% if (copyVisible)
         {%>
	       <a href="JavaScript:submitCopy('Copy')"
          onMouseOver="Javascript:mtbCopy.over();return true;"
          onMouseOut="Javascript:mtbCopy.out()"
          onClick="Javascript:mtbCopy.click(); return true;">
          <img name="Copy" id="Copy" SRC="<%= inf.getImageDirectory() %>Copy_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbCopy = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Copy', "JavaScript:submitcopy( 'Copy' )", 66, 14 );
        </script>
        <%}%>
      </div>
    </td>
    <td width="85">
      <div align="center" id="saveDiv">
      <% if (saveVisible)
         {%>
	       <a href="JavaScript:submitSave('Save')"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
        <%}%>
      </div>
    </td>
    <td width=85>
      <div align="center" id="newDiv">
      <% if (newVisible)
         {%>
        <a href="JavaScript:submitNew()"
          onMouseOver="Javascript:mtbNew.over();return true;"
          onMouseOut="Javascript:mtbNew.out()"
          onClick="Javascript:mtbNew.click(); return true;">
          <img name="New" id="New" src="<%= inf.getImageDirectory() %>New_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', "JavaScript:submitNew()", 66, 14 );
        </script>
      <%}%>
      </div>
    </td>
    <td width="85" id="deleteDiv">
      <div align="center">
      <% if(deleteVisible)
       {%>
        <a href ="JavaScript:submitDelete('Delete')"
          onMouseOver="Javascript:mtbDelete.over();return true;"
          onMouseOut="Javascript:mtbDelete.out()"
          onClick="Javascript:mtbDelete.click(); return true;">
          <img name="Delete" id="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
           var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', "JavaScript:submitDelete( 'Delete' )", 66, 14);
        </script>
      <%}%>
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

<%//notepad visibility check
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{%>
  	<%/* ===============start users navigation panel=========================== -->
  	     This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here.*/ %>
         <td valign="top">
       	 <%@ include file="include-task-security-notepad.shtml" %>
         </td>
 		<%// ===============end selection navigation panel=========================== %>
<%} //end notepad vis check %>



	<% //start task form %>
	<td bgcolor="lavender" valign="top"  align="left" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td valign="top" width="100%">
				<table width="100%">
				<%
				if (context.getDelivery("isNewTask") == null)
				{
				%>
				<tr>
					<td class="label"></td>
					<td nowrap>
			      <% // this checks to see what image to display
				        // if there are existing comments: display afile.gif
				        // if no comments exist: display file.gif
				        String comments = "";
				        comments = form.getRenderableValue("comments");
				        if (comments != null && !comments.equals(""))
				        {%>
			             <img src="<%= inf.getImageDirectory() %>afile.gif" border="0" onClick="toggle( 'commentLayer', 'comments' )">
			        <%}
			          else
				        {%>
			             <img src="<%= inf.getImageDirectory() %>file.gif" border="0" onClick="toggle( 'commentLayer', 'comments' )">
			        <%}%>
					  </td>
				</tr>
				<%
				}
				%>
				<tr>
					<td width="25%" class="label">Task Name</td>
					<td><%= form.getElement("taskName")%></td><td class="label">Active</td>
					<td><%= form.getElement("activeFlag")%></td>
				</tr>
				<tr>
					<td class="label">Key Task</td>
					<td><%= form.getElement("keyTask")%></td>
				</tr>
				<tr>
					<td class="label">Wks to Rls</td>
					<td><%= form.getElement("weeks2Rel")%></td>
				</tr>
				<tr>
					<td class="label">Day of Week</td>
					<td>
						<%= form.getElement("dayOfWeek")%>
					</td>
				</tr>
				<tr>
					<td width="25%" class="label">Week Adjustment</td>
					<td><%= form.getElement("weekAdjustment")%></td>
				</tr>
				<tr>
					<td class="label">Department</td>
					<td>
						<%= form.getElement("department")%>
					</td>
				</tr>
				<tr>
					<td class="label">Category</td>
					<td><%= form.getElement("category")%></td>
				</tr>
				<tr>
					<td class="label">Owner</td>
					<td>
						<%= form.getElement("owner")%>
					</td>
				</tr>
				<tr> <!-- ITS 281 -->
					<td class="label">Allow Multiple Complete Dates</td>
					<td>
						<%= form.getElement("allowMultCompleteDatesFlag")%>
					</td>
				</tr>
				<tr>
					<td class="label">Task Abbreviation</td>
					<td>
						<%= form.getElement("taskAbbreviation")%>
					</td>
				</tr>
				<tr>
					<td class="label">Task Description</td>
					<td colspan="3"><%= form.getElement("taskDescription")%></td>
				</tr>
        <% //if it is a new entry do not display those fields
           if (!(form.getRenderableValue("cmd")).equalsIgnoreCase(MilestoneConstants.CMD_TASK_EDIT_NEW))
           {%>
				<tr>
					<td valign="top" class="label"> Template Names / Type</td>
					<td><%= form.getElement("templateNames")%></td>
				</tr>
				<tr>
				 <td valign="bottom" colspan="5">
					<table>
					<tr>
						<td class="label">Last Updated: </td>
						<td><%= form.getRenderableValue("lastUpdatedDate")%></td>
						<td><%= form.getRenderableValue("lastUpdatedBy")%></td>
					</tr>
					</table>
			   </td>
			</tr>
      <%}%>
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
    <% // ITS 281 - added search by task abbreviation; note: the value is not displayed
       // in the results so only an exact match (no wildcards) would be useful
    %>
	<tr>
		<td class="label">Abbreviation</td>
		<td><%=form.getElement("taskAbbrevSrch")%>&nbsp; (exact match)</td>
	</tr>
	<tr>
		<td class="label">Key Task</td>
		<td align="left"><%=form.getElement("keyTaskSrch")%></td>
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
	<tr>
		<td class="label">Inactive</td>
		<td>
      <%=form.getElement("inactiveSrch")%>
		</td>
	</tr>
	<tr><% // ITS 281 %>
		<td class="label">Allow Mult Complete Dates</td>
		<td><%=form.getElement("allowMultCompleteDatesSrch")%></td>
	</tr>
	<tr>
		<td colspan="2">
      <input type="button" value="Submit Search" onClick="submitSearch()">
	<colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
    </td>
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
		  <%=form.getElement("comments")%>
		</td>
	</tr>
	<tr>
		<td>
 <% if (saveVisible)
    {%>
      <input type="button" value="Save"  onClick="submitSave('')" >&nbsp;
  <%}%>
      <input type="button" name="closeButton" value="Cancel" onClick="toggle( 'commentLayer', 'comments' )"></td>
	</tr>
	</table>
</td></tr>
</table>
</div>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>


 <% //end comment div %>
<% //END DIV DEFINITIONS %>

<%@ include file="include-bottom-html.shtml"%>


