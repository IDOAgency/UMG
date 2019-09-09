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
	String htmlTitle = "Email Distribution Editor";

	// getting the login name
	User user = (User)context.getSessionValue("user");
	int userid = user.getUserId();
	Acl acl = user.getAcl();
	Vector cAcl = Cache.getInstance().getCompanyList();

%>

<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include division-editor-javascript.js
  var form;
  var sort = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SORT)%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_EMAIL_DISTRIBUTION_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_EMAIL_DISTRIBUTION%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_EMAIL_DISTRIBUTION_EDITOR) %>";
  var save = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_NEW%>";
  var search = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SEARCH%>";
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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>blank-email-distribution-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">

<%
//DO NOT MOVE RESIZE TO AN INCLUDE WITHOUT ASKING BRETT FIRST!!!!
%>
function submitResize()
{
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<%
String focusField = "firstName";
if (form.getRenderableValue("cmd").equals("email-distribution-new"))
{
  focusField = "firstName";
}
%>

<body bgcolor="white" text="black" topmargin="0" leftmargin="0" onLoad="processLoad(focusField('<%=focusField%>'));">
<%= form.renderStart()%>
<%=form.getElement("cmd")%>

<table width="780" cellpadding="0" border="0">
<tr valign="middle" align="left">


        <td width="280" colspan="1" >
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

         <td rowspan="2" align="left" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>

        <td width="170"><span class="title">Email Distribution</span></td>

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


</tr>

 <tr>


        <td valign="top" width="280" colspan="1">
             <%
             if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
             {  // display the navigation panel
            %>
         	<!-- ===============start email dist navigation panel=========================== -->
        	<!-- This part of the code is made an include to make it easy to manage this page.
           	 The the java script needed resides in this page.
           	 The include just gets and puts the code here as it was written here. -->


                <%@ include file="include-email-distribution-notepad.shtml" %>

     		<!-- ===============end selection navigation panel=========================== -->
            <%}%>
       </td>

       <td bgcolor="lavender" valign="top" width="100%" colspan="2">
    	<table width="516" bgcolor="lavender">
             <tr><td align="center"><b><h4>Milestone System<br><br><br>No Email Distribution selected. Please select one on the left hand list or redo a search</h4></b></td></tr>
         </table>
        </td>

	</tr>

	<tr><td valign="bottom"></td></tr>
	</td>
        </tr>

</tr>


</table>




<% //DIV definitions %>
<% //start search div  %>
<div class="search" id="searchLayer" onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
<table width="100%" border="0" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
               <tr>
			<th colspan="2">Email Distribution Search</th>
		</tr>
		<tr>
			<td class="label">First Name</td>
			<td><%= form.getElement("firstNameSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Last Name</td>
			<td><%= form.getElement("lastNameSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Environment</td>
			<td><%= form.getElement("environmentSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Form Type</td>
			<td><%= form.getElement("formTypeSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Release Type</td>
			<td><%= form.getElement("releaseTypeSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Product Type</td>
			<td><%= form.getElement("productTypeSrch")%></td>
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


