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
	String htmlTitle = "Release Week Editor";
%>

<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed to include release-week-javascript.js
var sort = "<%= MilestoneConstants.CMD_RELEASE_WEEK_SORT%>";
var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_RELEASE_WEEK_EDITOR)%>";
var notepad = "<%= MilestoneConstants.NOTEPAD_RELEASEWEEK%>";
var cmd = "<%= form.getRenderableValue("cmd")%>";
var editNew = "<%= MilestoneConstants.CMD_RELEASE_WEEK_EDIT_NEW%>";
var search = "<%= MilestoneConstants.CMD_RELEASE_WEEK_SEARCH%>";
var next = "<%= inf.getServletCmdURL("notepad-next")%>";
var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";

function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_RELEASEWEEK_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";

} //end function submitResize()

</script>

<%
	//include js files holding javascript functions
%>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>blank-release-week-editor-javascript.js"></script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>


<%= form.renderStart() %>
<%= form.getElement("cmd") %>
<%= form.getElement("OrderBy") %>

<body bgcolor="white" text="black" topmargin="0" leftmargin="0">

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
	<td width="170"><span class="title">Release Week</span></td>
  <td width="85"></td>
	<td width="85"></td>
	<td width="85"></td>
	<td width="85">
		<div align="center">
			<a href="JavaScript:submitNew('New')"
			 onMouseOver="Javascript:mtbNew.over();return true;"
			 onMouseOut="Javascript:mtbNew.out()"
			 onClick="Javascript:mtbNew.click(); return true;">
			 <img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
			</a>
			<script type="text/javascript" language="JavaScript">
			  var mtbNew = new ToggleButton(document,'<%= inf.getImageDirectory() %>','New','JavaScript:submitNew( "New" )',66,14);
			</script>
		</div>
	</td>
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

       	 <%@ include file="include-release-week-security-notepad.shtml" %>

 		<% //===============end selection navigation panel=========================== %>
 <%}%>
	</td>

	<% //start release week form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td width="100%" align="center"><b><h4>Milestone System<br><br><br>Please search for Release Weeks on the left panel to this view </h4></b></td>
	</tr>
	<tr><td valign="bottom">
	</td></tr>
	</td>
</tr></table>
</td></tr></table>

<% //DIV definitions %>
<% //start search div %>
<div id="SearchLayer" class="search" onKeyPress="checkForEnter( 'submitSearch()' );" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0"><tr><td>
	<table width="100%">
	<tr><th colspan=2>Release Week Search</th></tr>
	<tr>
		<td class="label">Name</td>
		<td><%= form.getElement("NameSearch") %></td>
	</tr>
	<tr>
		<td class="label">Cycle</td>
		<td><%= form.getElement("CycleSearch") %></td>
	</tr>
	<tr>
		<td class="label">Start Date</td>
		<td><%= form.getElement("StartDateSearch") %></td>
	</tr>
	<tr>
		<td class="label">End Date</td>
		<td><%= form.getElement("EndDateSearch") %></td>
	</tr>
	<tr><td><INPUT type=button value="Submit Search" onClick="submitSearch()"></td></tr>
</table>
</td></tr></table>
</div>
<% //end search div %>
<% //END DIV DEFINITIONS %>

<%= form.renderEnd() %>

<%@ include file="include-bottom-html.shtml"%>


