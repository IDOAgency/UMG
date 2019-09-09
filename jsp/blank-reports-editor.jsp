<%@ include file="template-top-page.shtml"%>
<%@ include file="callHelp.js"%>

<%  // This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Reports";
%>

<%@ include file="template-top-html.shtml" %>

<!-- JR - ITS 213 -->
<script>
function HideLayer()
{
 mtbSearch.click();
 //alert('hidding');
 toggle( 'SearchLayer', 'ReportNameSearch');
}
function submitSearch()
{
  toggle( 'SearchLayer', 'ReportNameSearch' );
  document.forms[0].cmd.value = "reports-search";
  document.forms[0].submit();
} //end function submitSearch()

function submitList( pType )
{
	if (document.forms[0].OrderByDirection.value == "0") {
		document.forms[0].OrderByDirection.value = "1";
	} else {
		document.forms[0].OrderByDirection.value = "0";
	}
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = "reports-sort";
  document.forms[0].submit();
} //end function submitList()

</script>
<!-- JR - ITS 213 -->


<!-- include js files holding javascript functions -->
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></SCRIPT>
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></SCRIPT>
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></SCRIPT>
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></SCRIPT>
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></SCRIPT>
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></SCRIPT>
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></SCRIPT>
<SCRIPT type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></SCRIPT>

<SCRIPT type="text/javascript" language="JavaScript">

<%=context.getDelivery("corporate-array")%>
  function helpConext()
  {
    callHelp('Help/','Reports.htm');
    mtbHelp.reset();
  }//end function helpContext


function submitResize()
{
  // call the right command for resizing
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_REPORTS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()


function toggleSearch()
{
	toggle( 'SearchLayer', 'ArtistSearch' );
}// end function toggleSearch
</script>

<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
<script type="text/javascript" language="JavaScript">
var count = 0;

function submitGet( pId )
{
  parent.top.bottomFrame.location = "home?cmd=reports-editor&report-id=" + pId;
} // end function submitGet()

</SCRIPT>


<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<!-- start body  onBlur="hidePrintStatus();" - JP -->
<body topmargin="0" leftmargin="0" onLoad="focusField( 'beginDate' );" >

<%=form.renderStart()%>
<%=form.getElement("cmd")%>

<!-- JR - ITS 213 -->
<%if (form.getElement("OrderBy") != null)
    {%>
<%= form.getElement("OrderBy")%>
<%}%>
<%if (form.getElement("OrderByDirection") != null)
    {%>
<%= form.getElement("OrderByDirection")%>
<%}%>

<table width=780 cellpadding=0 border=0>
  <tr valign="middle" align="left">
    <td width=280>

      <!-- This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here. -->
      <%@ include file="include-newSelection.shtml" %>

    </td>
    <td rowspan=2 width=10>
      <img src="<%= inf.getImageDirectory() %>pixelshim.gif" width=10>
    </td>
    <td width=170 >
      <SPAN class="title">Reports</SPAN>
    </td>
    <td width=85></td>
    <td width=85></td>
    <td width=85></td>
    <td width=85>
      <div align="center" id="saveDiv">
	    <a href="JavaScript:submitSave()"
		   onMouseOver="Javascript:mtbSave.over();return true;"
		   onMouseOut="Javascript:mtbSave.out()"
		   onClick="Javascript:mtbSave.click(); return true;">
		   <img name="Save" src="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
	    </a>
	    <script type="text/javascript" language="JavaScript">
		 		var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', 'JavaScript:submitSave( "Print" )', 66,14);
	    </script>
	  </div>

    </td>
    <td>
      <div align="center">
        <a href ="JavaScript:helpConext()"
          onMouseOver="Javascript:mtbHelp.over();return true;"
          onMouseOut="Javascript:mtbHelp.out()"
          onClick="Javascript:mtbHelp.click(); return true;">
          <img name="Help" id="Help" src="<%=inf.getImageDirectory()%>Help_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
           var mtbHelp = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Help', "JavaScript:helpConext()",66, 14);
        </script>
      </div>

    </td>


  </tr>

  <tr>

    <td valign="top">
<%//notepad visibility check
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{%>
  <% /* ===============start reports navigation panel===========================
       This part of the code is made an include to make it easy to manage this page.
       The the java script needed resides in this page.
       The include just gets and puts the code here as it was written here.
     */
  %>
       <%@ include file="include-reports-notepad.shtml" %>

 <% /* ===============end reports navigation panel=========================== */ %>
<%}%>
    </td>

 <% /* blank statement */ %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
	<table width="516" bgcolor="lavender">
    <tr><td align="center"><b><h4>Milestone System<br><br><br>Please search for Reports on the left panel to this view </h4></b></td></tr>
  </table>

  </td>
  </tr>

</table>

<!-- JR - ITS 213 - 03/26/03 -->
<%/* DIV definitions
     start search div */%>
<div class="search" id="SearchLayer" onKeyPress="checkForEnter('submitSearch()');" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr><td>
	<table width="100%">
	<tr><th colspan="2">Report Search</th></tr>
	<tr>
		<td class="label">Description</td>
		<td><%= form.getElement("ReportDescriptionSearch") %></td>
	</tr>
	<tr><td colspan="2"><INPUT type=button value="Submit Search" onClick="submitSearch()">
		  <colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
</td></tr>
	</table>
</td></tr></table>
</div>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>

<%/* end search div
     END DIV DEFINITIONS */%>
<!-- JR - ITS 213 - 03/26/03 -->

<%=form.renderEnd()%>


<%@ include file="include-bottom-html.shtml"%>

</body>


