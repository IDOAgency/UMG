<%
	/*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
	*/
%>

<%@ page import="com.universal.milestone.MilestoneConstants"%>
<%@ include file="template-top-page.shtml"%>

<%
  String htmlTitle = "Selection Screen";

	// getting the user object from the session
	User user = (User)context.getSessionValue("user");

  //Used by include-selection-notepad
  String editorName = MilestoneConstants.CMD_SELECTION_EDITOR;

  String alertMessage = (String)context.getDelivery("BlankAlertMessage");
%>

<% //includes the top header %>
<%@ include file="template-top-html.shtml" %>

<script>var search = "<%= MilestoneConstants.CMD_SELECTION_SEARCH%>";</script>

<% //include js files holding javascript functions %>

<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>selection-editor-javascript.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-upc-ssg-sgc.js"></script>


<% //Java script functions specific to this page %>
<script type="text/javascript" language="javascript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_SELECTION_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

function toggleSearch()
{
	toggle( 'SearchLayer', 'ArtistSearch' );
} // end function toggleSearch

</script>


<% //javascript customized for this particular page %>
<script type="text/javascript" language="javascript">
function submitGet( preleaseId )
{
	parent.top.bottomFrame.location = "home?cmd=selection-editor&selection-id=" + preleaseId;
} //end function submitGet()

function submitSearch()
{
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SELECTION_SEARCH%>";

  //jo its 445 1/30/2003
  //set the hidden variables to the form values:
  document.forms[0].macArtistSearch.value = document.forms[0].ArtistSearch.value;
  document.forms[0].macTitleSearch.value = document.forms[0].TitleSearch.value;
  document.forms[0].macPrefixSearch.value = document.forms[0].PrefixSearch.value;
  document.forms[0].macSelectionSearch.value = document.forms[0].SelectionSearch.value;
  document.forms[0].macUPCSearch.value = document.forms[0].UPCSearch.value;
  document.forms[0].macStreetDateSearch.value = document.forms[0].StreetDateSearch.value;
  document.forms[0].macStreetEndDateSearch.value = document.forms[0].StreetEndDateSearch.value;
  document.forms[0].macCompanySearch.value = document.forms[0].CompanySearch[document.forms[0].CompanySearch.selectedIndex].value;
  document.forms[0].macLabelSearch.value = document.forms[0].LabelSearch[document.forms[0].LabelSearch.selectedIndex].value;
  document.forms[0].macContactSearch.value = document.forms[0].ContactSearch[document.forms[0].ContactSearch.selectedIndex].value;

 if (document.forms[0].SubconfigSearch.length>0){
	document.forms[0].macSubconfigSearch.value = document.forms[0].SubconfigSearch[document.forms[0].SubconfigSearch.selectedIndex].value;
 } else {
	document.forms[0].macSubconfigSearch.value = "";
 }
 //jo its 445 1/30/2003

  document.forms[0].submit();
} //end function submitSearch()

function submitList( pType )
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SELECTION_SORT%>";
  document.forms[0].submit();
} //end function submitList()
function submitGroupXXX( alpha, colNo )
{
  if( colNo == 0 || colNo == 7) // artist column
    document.forms[0].OrderBy.value = "0";
  if( colNo == 1 || colNo == 8 ) // title column
    document.forms[0].OrderBy.value = "1";

  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SELECTION_SORT%>";
  document.forms[0].submit();
} //end function submitGroup()
function submitGroup( alpha, colNo )
{
  document.forms[0].OrderBy.value = colNo;
document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SELECTION_GROUP%>";
  document.forms[0].submit();
} //end function submitGroup()
</script>

<!-- JR - ITS #70 -->
<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>
<!-- JR - ITS #70 -->

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("searchArray") %>
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body bgcolor="white" text="black" topmargin="0" leftmargin="0">
<%= form.renderStart()%>
<%=form.getElement("OrderBy")%>


<%=form.getElement("cmd")%>

<!-- msc 12/13/01 -->
<input type="hidden" name="alphaGroupChr" value="">

<table width="780" cellpadding="0" border="0">
  <tr valign="middle" align="left">
    <td width="280">
      <!-- This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here. -->
      <%@ include file="include-newSelection.shtml" %>
    </td>
    <td rowspan="2" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>
    <td width="170"><span class="title">Selection</span></td>
    <td width="85"></td>
    <td width="85"></td>
    <td width="85"></td>
    <td width="85"></td>
  </tr>
  <tr>
    <td valign="top">
    <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
  <% //===============start selection navigation panel=========================== %>
  <% /* This part of the code is made an include to make it easy to manage this page.
        The the java script needed resides in this page.
        The include just gets and puts the code here as it was written here.*/ %>

			 <%@ include file="include-selection-notepad.shtml" %>

  <% //===============end selection navigation panel=========================== %>
<%}%>
    </td>
	<% //start selection form %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td width="100%" align="center"><b><h4>Milestone System<br>
      <%
      // JP 10/28/02
      if (alertMessage != null)
      {
        out.println("<P>" + alertMessage);
      }
      else
      {
      %>
      <P>
      Please select a Release selection on the left panel to view
      <%
      }
      %>
      </h4></b></td>
	</tr>
	<tr><td valign="bottom">
	</td></tr>
	</td>
</tr></table>
</td></tr></table>

<% //START DIV DEFINITIONS %>

<% //start pfm/bom email sending div %>
<%
if ( (context.getDelivery("pfmSend") != null && ((String)context.getDelivery("pfmSend")).equalsIgnoreCase("true")) ||
     (context.getDelivery("bomSend") != null && ((String)context.getDelivery("bomSend")).equalsIgnoreCase("true")) )
{
%>
<div id="pfmBomLayer" class="search" style="visibility:visible;width:250px;height:60px;z-index:3;left:100px;top:120px;">
<table width="100%" height="100%" border="1" cellspacing="0" cellpadding="1">
<tr><td>
	<table width="100%" height="100%">
	<tr>
		<th>
                  <b>PFM/BOM Email Notifications</b>
		</th>
	</tr>
	<tr>
		<td align=center>
                       <%if ( (context.getDelivery("pfmSend") != null && ((String)context.getDelivery("pfmSend")).equalsIgnoreCase("true")) )
                         {
                       %>
			  <input type="button" name="sendPfm" value="Send PFM" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDPFM%>');">&nbsp;&nbsp;
                        <%
                         }
                        %>

                       <%if ( (context.getDelivery("bomSend") != null && ((String)context.getDelivery("bomSend")).equalsIgnoreCase("true")) )
                         {
                       %>
			  <input type="button" name="sendBom" value="Send BOM" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDBOM%>');">&nbsp;&nbsp;
                        <%
                         }
                        %>

                       <%if ( (context.getDelivery("bomSend") != null && ((String)context.getDelivery("bomSend")).equalsIgnoreCase("true")) &&
                              (context.getDelivery("pfmSend") != null && ((String)context.getDelivery("pfmSend")).equalsIgnoreCase("true")) )
                         {
                       %>
			  <input type="button" name="sendPfm" value="Send PFM and BOM" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDPFMBOM%>');">&nbsp;&nbsp;
                        <%
                         }
                        %>

			<input type="button" name="cancel" value="Cancel" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDCANCEL%>');">&nbsp;&nbsp;
		</td>
	</tr>
	</table>
</td></tr>
</table>
</div>


<%
}
%>
<% //end pfm/bom email sending div %>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>

<%@ include file="selection-search-elements.shtml"%>

<% //end search div %>

<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>
