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
<script>
function HideLayer()
{
 mtbSearch.click();
 toggle( 'SearchLayer', 'FamilyDescriptionSearch');
}
</script>
<script type="text/javascript" language="JavaScript">
//global variables needed in include tables-header-editor-javascript.js
  var form;
  var sort = "<%= MilestoneConstants.CMD_TABLES_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TABLES_HEADER_EDITOR)%>";
  var detailEditor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TABLES_DETAIL_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_TABLE%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TABLES_HEADER_EDIT_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_TABLES_HEADER_EDIT_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_TABLES_HEADER_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_TABLES_HEADER_SEARCH%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";

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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>table-header-editor-javascript.js"></script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body topmargin="0" leftmargin="0" onLoad="processLoad('<%= field%>');">

<%= form.renderStart() %>
<%= form.getElement("cmd") %>

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
  //System.out.println("this far");
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
	<td width="170"><span class="title">Table</span></td>
  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
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
	</td>
   <%  if ( !(form.getRenderableValue("id").equals("26") ||
          form.getRenderableValue("id").equals("5") ||
          form.getRenderableValue("id").equals("21") ||
          form.getRenderableValue("id").equals("28") ||
          form.getRenderableValue("id").equals("23") ||
          form.getRenderableValue("id").equals("50") ||
          form.getRenderableValue("id").equals("55") ||
          form.getRenderableValue("id").equals("39")) )
     {%>
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
  <%}%>
	<td width="85">
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
		  <td valign="top" width="100%"><div class="formTitle">
		  <%=form.getRenderableValue("Title")%>
		  </div>
			 <table width="100%">
				<tr>
 		 <%if (form.getElement("Value") != null)
				 {
				 %>

				  <td class="label" >Value</td>
				  <td>
        <% if (form.getRenderableValue("cmd").equalsIgnoreCase("tables-header-edit-new") || form.getRenderableValue("cmd").equalsIgnoreCase("tables-header-edit-save-new") || form.getRenderableValue("cmd").equalsIgnoreCase("tables-header-edit-save"))
           {%>
              <%= form.getElement("Value")%>
         <%}
           else
           {%>
            <%=form.getElement("Value")%>
				    <%=form.getRenderableValue("Value")%>
         <%}%>
				  </td>
				  <%
				  }
				  %>

              <td class=secure><b><%=form.getElement("inactive")%></b></td>

				</tr>
				<tr>
				  <td class="label">Description</td>
				  <td><%= form.getElement("Description") %></td>
				</tr>
        <% if (form.getElement("SubConfiguration") != null)
           {%>
        <tr>
				<td><b>Parent</b></td>
				 <td><%= form.getElement("SubConfiguration") %></td>
                             <td class=secure><b><%=form.getElement("subdetinactive")%></b></td>
				</tr>

                                <tr>
                                <!-- JR ITS 277 -->
                                <td><b>Format</b></td>
                                 <td><%= form.getElement("configuration") %></td>
                                </tr>

          <%}
            else
            {%>
                          <% if (form.getElement("SubDescription") != null)
                           {%>
				<tr>

				  <td><b>Subdescription</b></td>
				  <td><%= form.getElement("SubDescription") %></td>
				</tr>
                          <%}%>
         <%}%>
        <% //it only will not be null for Prefix Code Table
           if (form.getElement("environments") != null)
           {%>
        <tr>
				  <td><b>Environment</b></td>
				  <td><%= form.getElement("environments") %></td>
				</tr>
        <%}%>
				<tr><td>Contact IT for details on storage.</td></tr>
			  </table>
		    </td>
	      </tr>


	      <tr><td valign="bottom"></td></tr>
	    </td>
      </tr>
    </table>

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
             <td colspan="2"><INPUT type=submit value="Submit Search" onClick="submitSearch()">
			  <colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
     		</td>
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

<%@ include file="include-bottom-html.shtml"%>


