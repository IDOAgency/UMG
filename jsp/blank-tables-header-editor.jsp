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
	String htmlTitle = "Tables Header Editor";

  //holds the value of the field that should get the focus
  String field = "";

  if ((form.getRenderableValue("cmd")).indexOf("new") != -1)
  {
    field = "Value";
  }
  else
  {
    field = "Description";
  }
%>

<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include tables-header-editor-javascript.js
  var form;
  var sort = "<%= MilestoneConstants.CMD_TABLES_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TABLES_HEADER_EDITOR)%>";
	var editorDetail = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TABLES_DETAIL_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_TABLE%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TABLES_HEADER_EDITOR) %>";
  var save = "<%= MilestoneConstants.CMD_TABLES_HEADER_EDIT_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_TABLES_HEADER_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_TABLES_HEADER_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";


function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_TABLE_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";

} //end function submitResize()


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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>blank-table-header-editor-javascript.js"></script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body bgcolor="white" text="black" topmargin="0" leftmargin="0" onLoad="processLoad('<%= field%>');">

<%= form.renderStart() %>
<%= form.getElement("cmd") %>

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
	<td width="170"><span class="title">Table</span></td>
  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
  <!--
    <div align="left">
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
   -->
	</td>
 	<td width="85"></td>
	<td width="85">
  <!--
	  <div align="center">
		<a href="JavaScript:submitDelete('Delete')"
		   onMouseOver="Javascript:mtbDelete.over();return true;"
		   onMouseOut="Javascript:mtbDelete.out()"
		   onClick="Javascript:mtbDelete.click(); return true;">
		   <img name="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
		</a>
		<script type="text/javascript" language="JavaScript">
  		  var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', 'JavaScript:submitDelete( "Delete" )',66,14);
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
  	<% //===============start users navigation panel=========================== %>
  	<% /* This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here.*/ %>
       	 <%@ include file="include-tables-header-security-notepad.shtml" %>

 		<% //===============end selection navigation panel=========================== %>
    <%}%>
	</td>

	<% //start tables header form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td width="100%" align="center"><b><h4>Milestone System<br><br><br>Please search for Table Headers on the left panel to this view </h4></b></td>
	</tr>
	<tr><td valign="bottom">
	</td></tr>
	</td>
</tr></table>
</td></tr></table>

    <% //DIV definitions %>
    <% //start search div %>
    <div class="search" id=SearchLayer onKeyPress="checkForEnter( 'submitSearch()' );" style="visibility:hidden">
    <table width="100%" border="1" cellspacing="0" cellpadding="1">
      <tr>
        <td>
          <table width="100%">
	        <tr>
              <th colspan="2">Table Header Search</th>
            </tr>
   	        <tr>
  		      <td class="label">Group Description</td>
   		      <td><%= form.getElement("DescriptionSearch") %></td>
  	       </tr>
  	       <tr>
             <td colspan="2"><INPUT type=submit value="Submit Search" onClick="submitSearch()"></td>
           </tr>
   	     </table>
       </td>
     </tr>
   </table>
   </div>
   <% //end search div %>
   <% //END DIV DEFINITIONS %>

   <%= form.renderEnd() %>

  <%@ include file="include-bottom-html.shtml"%>


