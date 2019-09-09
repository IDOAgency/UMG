<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*, java.text.*"
			gets the context, form formValidation and message
  */
%>

<%@ include file="template-top-page.shtml"%>

<%
	boolean showTasks = false;
	/* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Schedule screen";

    //Used by include-selection-notepad
    String editorName = MilestoneConstants.CMD_SCHEDULE_EDITOR;
%>

<%@ include file="template-top-html.shtml"%>

<script type="text/javascript" language="JavaScript">
  //global variables needed in include schedule-editor-javascript.js
  var sort = "<%=MilestoneConstants.CMD_SCHEDULE_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_EDITOR)%>";
  var search = "<%=MilestoneConstants.CMD_SCHEDULE_SELECTION_RELEASE_SEARCH%>";
  var recalc = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_RECALC)%>";
  var clearDate = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_CLEAR)%>";
  var filter = "<%=MilestoneConstants.CMD_SCHEDULE_FILTER%>";

  function submitGroupXXX( alpha, colNo )
{
  if( colNo == 0 || colNo == 7) // artist column
    document.forms[0].OrderBy.value = "0";
  if( colNo == 1 || colNo == 8 ) // title column
    document.forms[0].OrderBy.value = "1";

  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SCHEDULE_SORT%>";
  document.forms[0].submit();
} //end function submitGroup()

  function submitGroup( alpha, colNo )
{
  document.forms[0].OrderBy.value = colNo;
  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SCHEDULE_GROUP%>";
  document.forms[0].submit();
} //end function submitGroup()



</script>


 <!-- These are js include files holding the global java script functions -->
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>blank-schedule-editor-javascript.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>

<script type="text/javascript" language="JavaScript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  <%//check if "selections" is to be shown or "tasks" for the nav menu
    if (showTasks == false) //show "selections"
    {%>
      parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_SCHEDULE_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
  <%}
    else
    {%>
      parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_SCHEDULE_TASKS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
  <%}%>
 } //end submitResize

</script>

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("searchArray") %>
</script>

<!-- JR - ITS #70 -->
<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>
<!-- JR - ITS #70 -->

<link rel="stylesheet" type="text/css" href="<%=inf.getHtmlDirectory()%>global.css" title="GlobalStyle">
</head>
<script for=document event=onkeydown language="JavaScript">
checkShortcut();

</script>

<body bgcolor="white" text="black" topmargin=0 leftmargin=0>
<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>

<!-- msc 12/13/01 -->
<input type="hidden" name="alphaGroupChr" value="">

<table width=780 cellpadding=0 border=0>
  <tr valign="middle" align="left">
    <td width=280 >
      <!-- This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here. -->
      <%@ include file="include-newSelection.shtml" %>
    </td>
    <td rowspan=2 width=10>
      <img src="<%=inf.getImageDirectory()%>pixelshim.gif" width=10>
    </td>
    <td width=170 >
      <SPAN class="title">Schedule</SPAN>
    </td>
    <td width=85></td>
    <td width=85></td>
    <td width=85></td>
    <td width=85></td>
    <td width=85></td>
  </tr>

  <tr>
    <td valign="top">
<%//notepad visibility check
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{
 %>
  <% /* ===============start selection navigation panel===========================
       This part of the code is made an include to make it easy to manage this page.
       The the java script needed resides in this page.
       The include just gets and puts the code here as it was written here.
     */
 %>
       <%@ include file="include-selection-notepad.shtml" %>

 <% /* ===============end selection navigation panel=========================== */

  } //end notepad vis check%>
    </td>

    <td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td width="100%" align="center"><b><h4>Milestone System<br><br><br>Please select a Release selection on the left panel to view </h4></b></td>
	</tr>
	<tr><td valign="bottom">
	</td></tr>
	</td>
</tr></table>
</td></tr></table>

 <!-- DIV definitions -->
<% //start search div %>
<%@ include file="selection-search-elements.shtml"%>
<% //end search div %>

<div id="commentLayer" class="search" style="width:300px; height:120px; z-index:3; left: 220px; top: 95px; visibility:hidden">
<table width="100%" border=1 cellspacing=0 cellpadding=1>
  <tr>
    <td>
      <table width="100%">
        <tr>
          <td class="label">
            Comments<br>
            <%=form.getElement("comments")%>
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" name="saveButton" id="saveButton" value="Save Comments"  onClick="Javascript:saveDetailData( 'commentLayer', 'comments')">&nbsp;&nbsp;
            <input type="button" name="closeButton" id="closeButton" value="Cancel" onClick="Javascript:toggle( 'commentLayer', 'comments' )">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<div id="viewCommentLayer" class="search" style="width:300px; height:120px; z-index:3; left: 220px; top: 95px; visibility:hidden;">
<table width="100%" border=1 cellspacing=0 cellpadding=1>
  <tr>
    <td>
      <table width="100%">
        <tr>
          <td class="label" >
            Comments<br>
            <%= form.getElement("viewComments")%>
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" name="closeButton" id="closeButton" value="Cancel" onClick="Javascript:toggle( 'viewCommentLayer', 'comments' )">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<div id="releaseCommentLayer" style="position:absolute; visibility:'hidden'; width:300px; height:120px; z-index:3; left: 100px; top: 120px;">
  <table width=290 border=1 cellspacing=0 cellpadding=1>
    <tr bgcolor="wheat">
      <td>
        <table width=290>
          <tr>
            <td class="label" >
              Comments<br>
               <%= form.getElement("releaseComment")%>
            </td>
          </tr>
          <tr>
            <td>
              <input type="button" name="closeButton" id="closeButtom" value="Cancel" onClick="Javascript:toggle( 'releaseCommentLayer', 'releaseComment' )">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="holdReasonLayer" style="position:absolute; visibility:hidden; width:300px; height:180px; z-index:3; left: 475px; top: 95px;">
<table width=290 border=1 cellspacing=0 cellpadding=1>
  <tr bgcolor="wheat">
    <td>
      <table width=290>
        <tr>
          <td class="label">
            Hold Reason<br>
             <%= form.getElement("holdReason")%>
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" name="closeHoldReason" id="closeHoldReason" value="Cancel" onClick="Javascript:toggle( 'holdReasonLayer', 'holdReason' )">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>

<DIV id=vendorLayer class="search" style="position:absolute; visibility:hidden; width:300px; height:80px; z-index:3; left: 220px; top: 95px;">
  <TABLE width="100%" border=1 cellspacing=0 cellpadding=1><TR><TD>
    <TABLE width="100%" >
      <TR><TD class="label" >Vendor</TD></TR>
      <TR><TD><INPUT name=vendorText size=50 maxlength="50"  onBlur="checkField( this )"></TD></TR>
      <TR><TD>
      <INPUT type=button name=saveButton value="Save"  onClick="saveDetailData( 'vendorLayer', 'vendorText', 'Save Release Schedule' )">&nbsp;&nbsp;
      <INPUT type=button name=closeButton value="Cancel"  onClick="toggle( 'vendorLayer', 'vendorText' )">
    </TD></TR></TABLE>
  </TD></TR></TABLE>
</DIV>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>


<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>
