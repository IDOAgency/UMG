<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message

    *   2003-01-09 - lg - ITS 877 Added calendar group to getDayType method
    */
%>

<%@ include file="template-top-page.shtml"%>

<%
   Selection selections = (Selection)MilestoneHelper.getNotepadFromSession(MilestoneConstants.NOTEPAD_SCHEDULE, context).getSelected();
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Schedule Suggested Template screen";

   Vector suggestedTemplates = (Vector)context.getDelivery("suggestedTemplates");

	 // this will check the access right for a user
	 boolean saveVisible = false;
   boolean deleteVisible = false;

	 saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
   deleteVisible = ((String)context.getDelivery("deleteVisible")).equals("true");

  

   String releaseWeek = (String)context.getDelivery("releaseWeek");
   String typeConfig = (String)context.getDelivery("typeConfig");

   String deleteAllMessage = "";
   deleteAllMessage = (String)context.getDelivery("deleteAllMessage");

	 boolean saveCommentVisible = false;
   saveCommentVisible = ((String)context.getDelivery("saveCommentVisible")).equals("true");

   // release upgrade 8.1 hide buttons
    if(MilestoneConstants.IsGDRSactive)
   {
	if(selections.getIsDigital())
	{
		 saveVisible = false;
		 deleteVisible = false;
		 saveCommentVisible = false;
	}
  }	
   // release upgrade 8.1 if not an ADMIN hide buttons

   //Used by include-selection-notepad
    String editorName = MilestoneConstants.CMD_SCHEDULE_EDITOR;

	String prefixId = "";
	if (selections.getPrefixID() != null)
	{
		prefixId = selections.getPrefixID().getAbbreviation();
	}

	String selectionNo = "";
	if(selections.getSelectionNo() != null)
	{
		selectionNo = selections.getSelectionNo();
	}

%>

<%@ include file="template-top-html.shtml"%>

<script type="text/javascript" language="JavaScript">
  //global variables needed in include schedule-select-template-javascript.js
  var form;

  var editor = "<%= MilestoneConstants.CMD_SCHEDULE_SELECT_TEMPLATE%>";
  var scheduleEditor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_EDITOR)%>";
  var save = "<%= MilestoneConstants.CMD_SCHEDULE_SAVE %>";
  // JR - ITS 745
  var saveSelectionComments = "<%= MilestoneConstants.CMD_SCHEDULE_SAVE_SELECTION_COMMENTS %>";
  var deleteAllTasks = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_DELETE_ALL_TASK)%>";
  var assignTemplate = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_ASSIGN_TEMPLATE)%>";
  var search = "<%=MilestoneConstants.CMD_SCHEDULE_SELECTION_RELEASE_SEARCH%>";
  var copySchedule = "<%=MilestoneConstants.CMD_SCHEDULE_COPY_RELEASE_SCHEDULE%>";
  var taskEditor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_EDITOR)%>";
  var filter = "<%= MilestoneConstants.CMD_SCHEDULE_EDITOR%>";
  var sort = "<%=MilestoneConstants.CMD_SCHEDULE_SORT%>";
  var sortTasks = "<%=MilestoneConstants.CMD_SCHEDULE_SCREEN_TASK_SORT%>";
  var recalc = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_RECALC)%>";
  var recalcAll = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_RECALC_ALL)%>";

  function processLoad()
  {
    form = document.forms[0];
    parent.top.bottomFrame.focus();
  }
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
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-toggle.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-toggle-button.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-check-format.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-focus-field.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-check-length.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-check-convert-date.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-check-integer.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>include-jscript-check-shortcut.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%=inf.getHtmlDirectory()%>schedule-suggested-template-javascript.js"></script>


 <!-- Java script functions specific to this page -->
 <script type="text/javascript" language="JavaScript">

  function submitResize()
  {
    // call the right command for resizing
    parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_SCHEDULE_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
 } //end submitResize
 
 if(parent.top.menuFrame.document.all.MIschedule) {
                                                            //sOnColor
 	parent.top.menuFrame.document.all.MIschedule.style.color = 6750156;
                                                              //sOffColor
 	parent.top.menuFrame.document.all.MIselection.style.color = 65638;
 	parent.top.menuFrame.selectedMenuItem = "schedule";
 }
 </script>

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("searchArray") %>
</script>

<!-- JP - ITS #282 -->
<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>
<!-- JP - ITS #282 -->

<link rel="stylesheet" type="text/css" href="<%=inf.getHtmlDirectory()%>global.css" title="GlobalStyle">
</head>
<script for=document event=onkeydown language="JavaScript">
checkShortcut();
</script>

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

<body topmargin=0 leftmargin=0 onLoad="processLoad()">

<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>

<!-- msc 12/13/01 -->
<input type="hidden" name="alphaGroupChr" value="">

<table width=900 cellpadding=0 border=0>
  <tr valign="middle" align="left">
    <td width=280>
      <!-- This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here. -->
      <%@ include file="include-newSelection.shtml" %>
     </td>
    <td rowspan=2 width=10><img src="<%=inf.getImageDirectory()%>pixelshim.gif" width=10></td>
    <td width=170 >
      <SPAN class="title">Schedule</SPAN>
    </td>
    <td width=85></td>
    <td width=85></td>
    <td width=85>
      <div align="center" id="saveDiv">
			<%if(saveVisible)
        {%>
       <a href="JavaScript:submitSave('Save')"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" SRC="<%=inf.getImageDirectory()%>Save_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbSave = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
				<%}%>
      </div>
    </td>
    <td width=85>
      <div align="center" id="deleteDiv">
			<%if(deleteVisible)
        {%>
        <a href ="JavaScript:submitDelete('Delete')"
          onMouseOver="Javascript:mtbDelete.over();return true;"
          onMouseOut="Javascript:mtbDelete.out()"
          onClick="Javascript:mtbDelete.click(); return true;">
          <img name="Delete" id="Delete" src="<%=inf.getImageDirectory()%>Delete_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
           var mtbDelete = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Delete', "JavaScript:submitDelete( 'Delete' )", 66, 14);
        </script>
			<%}%>
      </div>
    </td>
  </tr>
  <tr>
<%//notepad visibility check
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{%>
    <td valign="top">
  <% /* ===============start selection navigation panel===========================
      This part of the code is made an include to make it easy to manage this page.
       The the java script needed resides in this page.
       The include just gets and puts the code here as it was written here.
     */
   %>
       <%@ include file="include-selection-notepad.shtml" %>

 <% /*===============end selection navigation panel=========================== */%>
    </td>
<%} //end notepad vis check
%>

	<!-- JR - ITS #444 -->
	<% if (selections.getIsDigital()) { %>
		<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" colspan="7" width="100%">
	<% } else { %>
		<td bgcolor="lavender" valign="top" colspan="7" width="100%">
	<% } %>
	<!-- JR - ITS #444 -->

      <table width="100%" height="100%" cellpadding=5 border=0>
        <tr>
          <td valign="top">
              <table width="100%" border=0>
                <tr>

<% if (selections != null && !selections.getIsDigital()) { %>
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- BEGIN PHYSICAL SCHEDULE SCREEN -->

                <!-- STEET/SHIP DATE, RELEASE WK/CYCLE, STATUS -->

					<td class="label" nowrap>Street/Ship Date <span class=indicator ></span></td>
					<td nowrap>

					<% if (selections != null && selections.getStreetDate() != null) {%>
								<%= MilestoneHelper.getFormatedDate(selections.getStreetDate())%>
								<SPAN class=indicator >
								<%= MilestoneHelper.getDayType(selections.getCalendarGroup(), selections.getStreetDate())%>
								</SPAN>
									&nbsp;&nbsp;
					<% } else { %>
								&nbsp;&nbsp;
								<SPAN class=indicator >
								</SPAN>
									&nbsp;&nbsp;
					<% } %>

					<% String releaseComment = "";
                      if (form.getRenderableValue("releaseComment") != null)
                        releaseComment = form.getRenderableValue("releaseComment");
                      if (releaseComment != null && !releaseComment.equals("")) {
					%>
					<img  title="Selection Comments" src="<%=inf.getImageDirectory()%>afile.gif" onClick="toggle( 'releaseCommentLayer', 'releaseComment' );">
					<% } else { %>
						<img  title="Selection Comments" src="<%=inf.getImageDirectory()%>file.gif" onClick="toggle( 'releaseCommentLayer', 'releaseComment' );">
					<% } %>
					</td>

					<td class="label" nowrap>Rls Wk/Cycle</td>
					<td nowrap><%= releaseWeek%></td>

					<td class="label" >Status</td>
					<td><%
					SelectionStatus status = null;
                    if (selections != null)
                    {
                       status = selections.getSelectionStatus();

                         if (status != null && status.getAbbreviation() != null)
                         {%>
                           <%= (status.getAbbreviation())%>
                    <%}
                    }%>
                    </td>
              </tr>

              <!-- Intl. Date, Special Pkg, Hold, P&D/Dist. -->
              <tr>
                <td class="label">Intl. Date</td>
                <td><%if (selections != null)
                      {
                       if(selections.getInternationalDate() != null)
                       { %>
                           <%= MilestoneHelper.getFormatedDate(selections.getInternationalDate())%>
                     <%}
                     }%>
                 </td>
                 <td></td>
                <td nowrap class="label" align=right>
                <% if (selections != null && selections.getSpecialPackaging())
                {%>
                  <input type=checkbox title="Special Pkg - Read-Only Field" checked disabled>
                <%}
                else
                {%>
                  <input type=checkbox title="Special Pkg - Read-Only Field" disabled>
                <%}%>
                Special Pkg
                </td>
                <td nowrap class="label">
                <% if (selections.getHoldSelection())
                {%>
					<input type=checkbox title="Hold - Read-Only Field" checked disabled>Hold
                <%}
                    else
                {%>
                    <input type=checkbox title="Hold - Read-Only Field" disabled>Hold
                <%}%>
                <%
                String holdReason = "";
                holdReason = form.getRenderableValue("holdReason");

                if (holdReason != null && !holdReason.equals(""))
                {%>
                    <img  title="Hold Comments" src="<%=inf.getImageDirectory()%>afile.gif" onClick="toggle( 'holdReasonLayer', 'holdReason');">
                <%}
                else
                {%>
                    <img  title="Hold Comments" src="<%=inf.getImageDirectory()%>file.gif" onClick="toggle( 'holdReasonLayer', 'holdReason');">
                <%}%>
                </td>
                <td nowrap class="label">
                <% if (selections != null && selections.getPressAndDistribution())
                   {%>
                   <input type=checkbox title="P&D / Dist. - Read-Only Field" checked disabled>
                 <%}
                   else
                   {%>
                     <input type=checkbox title="P&D / Dist. - Read-Only Field" disabled>
                 <%}%>
                 P&D / Dist.
                 </td>

              </tr>

              <!-- Artist, ProjectID -->
              <tr>
                <td class="label">Artist</td>
                <td colspan=3>
                  <%
                  if (selections != null)
                  {
                  if (selections.getArtist() != null)
                  {%>
                    <%= selections.getArtist()%>
                <%}
                }%>
                </td>
                <td class="label">Project ID</td>
                <td><%
                   if (selections != null)
                   {
                     if(String.valueOf(selections.getProjectID()) != null)
                       {%>
                         <%= String.valueOf(selections.getProjectID())%>
                     <%}
                     }%>
                </td>
              </tr>

              <!-- Title, Prefix -->
              <tr>
                <td class="label">Product Title</td>
                <td colspan=3>
                <%
                if (selections != null)
                {
                 if (selections.getTitle() != null)
                   {%>
                     <%= selections.getTitle()%>
                 <%}
                 }%>
                </td>
                <td class="label" nowrap rowspan=2 valign=bottom>Prefix/<BR>Local Prod #</td>
                <td nowrap rowspan=2 valign=bottom><%=prefixId%>&nbsp;<%=selectionNo%></td>
              </tr>

              <!-- Releasing Family, Local Prod # -->
              <tr>
				<td class="label" nowrap valign=bottom>Releasing Family</td>
				<td colspan=3 valign=bottom><%= ReleasingFamily.getName(selections.getReleaseFamilyId()) %></td>
              </tr>

              <!-- Label, UPC -->
              <tr>
                <td class="label">Label</td>
                <%
                   Label label = null;
                   if (selections != null)
                     label = selections.getLabel();%>
                <td colspan=3>
                <%if (label != null)
                  {%>
                    <%= label.getName()%>
                <%}%>
                </td>
                <td class="label">UPC</td>
                <td nowrap>
                <% if (selections != null && selections.getUpc() != null)
                   {%>
                      <%= selections.getUpc()%>
                 <%}%>
                </td>
              </tr>

              <!-- Imprint  -->
              <tr>
                <td class="label" >Imprint</td>
                <td colspan=3><%= selections.getImprint() %></td>

           <!-- Distribution Company -->
              <td class="label" >Dist Company</td>
              <%
               String distCoName = selections != null && selections.getLabel() != null ? MilestoneHelper_2.getLabelDistCo(selections.getLabel().getStructureID()) : "";
              %>
              <td colspan=3><%=distCoName%></td>

              </tr>

              <!-- TYPE FORMAT  -->
              <tr>
                <td class="label" >Type/Format</td>
                <%
                 if (selections != null)
                 {
                   ReleaseType releaseType = selections.getReleaseType();
                   if ( releaseType != null && releaseType.getAbbreviation() != null)
                   {
                 %>

                <td colspan=5><%= typeConfig%></td>
                  <%}
                 }%>
              </tr>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- END PHYSICAL SCHEDULE SCREEN -->

<% } else if (selections != null && selections.getIsDigital()) { %>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- BEGIN DIGITAL SCHEDULE SCREEN -->

                <!-- STEET/SHIP DATE, STATUS -->

					<td class="label" nowrap>Digital Release Date <span class=indicator ></span></td>
					<td nowrap>

					<% if (selections != null && selections.getDigitalRlsDate() != null) {%>
								<%= MilestoneHelper.getFormatedDate(selections.getDigitalRlsDate())%>
								<SPAN class=indicator >
								<%= MilestoneHelper.getDayType(selections.getCalendarGroup(), selections.getDigitalRlsDate())%>
								</SPAN>
									&nbsp;&nbsp;
					<% } else { %>
								&nbsp;&nbsp;
								<SPAN class=indicator >
								</SPAN>
									&nbsp;&nbsp;
					<% } %>

					<% String releaseComment = "";
                      if (form.getRenderableValue("releaseComment") != null)
                        releaseComment = form.getRenderableValue("releaseComment");
                      if (releaseComment != null && !releaseComment.equals("")) {
					%>
					<img  title="Selection Comments" src="<%=inf.getImageDirectory()%>afile.gif" onClick="toggle( 'releaseCommentLayer', 'releaseComment' );">
					<% } else { %>
						<img  title="Selection Comments" src="<%=inf.getImageDirectory()%>file.gif" onClick="toggle( 'releaseCommentLayer', 'releaseComment' );">
					<% } %>
					</td>

					<td class="label" nowrap></td>
					<td></td>

					<td class="label" >Status</td>
					<td><%
					SelectionStatus status = null;
                    if (selections != null)
                    {
                       status = selections.getSelectionStatus();

                         if (status != null && status.getAbbreviation() != null)
                         {%>
                           <%= (status.getAbbreviation())%>
                    <%}
                    }%>
                    </td>
              </tr>

              <!-- Artist, Priority, P&D/Dist. -->
              <tr>
                <td class="label">Artist</td>
                <td colspan=3>
                  <%
                  if (selections != null)
                  {
                  if (selections.getArtist() != null)
                  {%>
                    <%= selections.getArtist()%>
                <%}
                }%>
                </td>
                <td nowrap class="label">
                <% if (selections != null && selections.getPressAndDistribution())
                   {%>
                   <input type=checkbox title="P&D / Dist. - Read-Only Field" checked disabled>
                 <%}
                   else
                   {%>
                     <input type=checkbox title="P&D / Dist. - Read-Only Field" disabled>
                 <%}%>
                 P&D / Dist.
                 </td>
                <td nowrap class="label">
                <% if (selections != null && selections.getPriority())
                   {%>
                   <input type=checkbox title="Priority - Read-Only Field" checked disabled>
                 <%}
                   else
                   {%>
                     <input type=checkbox title="Priority - Read-Only Field" disabled>
                 <%}%>
                 Priority
                 </td>


              </tr>

              <!-- Title, ProjectID -->
              <tr>
                <td class="label">Bundle Title</td>
                <td colspan=3>
                <%
                if (selections != null)
                {
                 if (selections.getTitle() != null)
                   {%>
                     <%= selections.getTitle()%>
                 <%}
                 }%>
                </td>
                <td class="label">Project ID</td>
                <td><%
                   if (selections != null)
                   {
                     if(String.valueOf(selections.getProjectID()) != null)
                       {%>
                         <%= String.valueOf(selections.getProjectID())%>
                     <%}
                     }%>
                </td>
              </tr>

              <!-- Releasing Family, Prefix  -->
              <tr>
				<td class="label" nowrap>Releasing Family</td>
				<td colspan=3><%= ReleasingFamily.getName(selections.getReleaseFamilyId()) %></td>
                <td class="label" nowrap rowspan=2 valign=bottom>Phys. Prefix/<BR>Local Prod #</td>
                <td nowrap rowspan=2 valign=bottom><%=prefixId%>&nbsp;<%=selectionNo%></td>
              </tr>

              <!-- Label, Local Prod # -->
              <tr>
                <td class="label" valign=bottom>Label</td>
                <%
                   Label label = null;
                   if (selections != null)
                     label = selections.getLabel();%>
                <td colspan=3 valign=bottom>
                <%if (label != null)
                  {%>
                    <%= label.getName()%>
                <%}%>
                </td>
              </tr>

              <!-- Imprint, UPC -->
              <tr>
                <td class="label" >Imprint</td>
                <td colspan=3><%= selections.getImprint() %></td>
                <td class="label">UPC</td>
                <td nowrap>
                <% if (selections != null && selections.getUpc() != null)
                   {%>
                      <%= selections.getUpc()%>
                 <%}%>
                </td>
              </tr>

              <!-- TYPE FORMAT, GRid -->
              <tr>
                <td class="label" >Type/Format</td>
                <%
                 if (selections != null)
                 {
                   ReleaseType releaseType = selections.getReleaseType();
                   if ( releaseType != null && releaseType.getAbbreviation() != null)
                   {
                 %>

                <td colspan=3><%= typeConfig%></td>
                  <%}
                 }%>
                 <td class="label">GRid#</td>
                 <td><input type=text disabled value="<%= selections.getGridNumber() %>" class="ctrlMediumGrayBG"></td>
              </tr>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- END DIGITAL SCHEDULE SCREEN -->
<% } %>

              <tr bgcolor="white">
                <td colspan="6"></td>
              </tr>

              <tr>
                <td colspan=4 nowrap>
                   <a href ="JavaScript:submitClearDate( 'Clear' )"
                      onMouseOver="Javascript:mtbClear.over();return true;"
                      onMouseOut="Javascript:mtbClear.out()"
                      onClick="Javascript:mtbClear.click(); return true;">
                      <img name="Clear" id="Clear" src="<%=inf.getImageDirectory()%>Clear_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
                   </a>
                   <script type="text/javascript" language="JavaScript" >
                   <!--
                     var mtbClear = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Clear', "JavaScript:submitClearDate( 'Clear' )", 66, 14);
                   // -->
                   </script>

                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   <a href="JavaScript:submitRecalc( false, 'Recalc' )"
                      onMouseOver="Javascript:mtbRecalc.over();return true;"
                      onMouseOut="Javascript:mtbRecalc.out()"
                      onClick="Javascript:mtbRecalc.click(); return true;">
                      <img name="Recalc" src="<%=inf.getImageDirectory()%>Recalc_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
                   </a>

                   <script type="text/javascript" language="JavaScript" >
                   <!--
                      var mtbRecalc = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Recalc', "JavaScript:submitRecalc( false, 'Recalc')", 66, 14);
                   // -->
                   </script>
                </td>
                <td class="label">Task Owner</td>
                <td align="right" nowrap><img src="<%=inf.getImageDirectory()%>filter_access1.gif" border=0>
                  <%
                  String filterFlag = (String)context.getSessionValue("filterFlag");
                  if (filterFlag.equalsIgnoreCase("Yes"))
                  {%>
                  <%= form.getElement("filter")%>
               <%}
                   else
                   {
                 %>
                  <%= form.getRenderableValue("filter")%>
               <%}%>
                </td>
              </tr>
            </table>

            <!-- code for this page -->

           <table width=100% class="detailList" border=0 cellspacing=0 cellpadding=0>
             <tr>
                <th nowrap>Task Assignment</th>
                <th>Wks</th>
                <th>&nbsp;</th>
                <th nowrap>Due</th>
                <th nowrap>Complete</th>
                <th>&nbsp;</th>
                <th nowrap>Status</th>
                <th>&nbsp;</th>
                <th nowrap>Vendor</th>
             </tr>
             <tr>
               <td class="label" colspan=1 align="center" bgcolor="#eeeeee"></td>
               <td class="label" colspan=5 align="center" bgcolor="#eeeeee">New Release Schedule</td>
               <td class="label" colspan=3 align="center" bgcolor="#eeeeee"></td>
             </tr>
             <tr>
               <td colspan=1 align="center" bgcolor="#eeeeee"></td>
               <td colspan=5 align="center" bgcolor="#eeeeee">Milestone suggests the following template(s):</td>
               <td colspan=3 align="center" bgcolor="#eeeeee"></td>
             </tr>
             <tr>
               <td colspan=1 align="center" bgcolor="#eeeeee"></td>
               <td colspan=5 align="center" bgcolor="#eeeeee">
                 <table align="center" width=265 border=1 cellspacing=0 cellpadding=1>
                 <%
                 for (int j = 0; j < suggestedTemplates.size(); j++)
                 {
                 %>
                   <tr>
                     <td width="10%">
                     <% if (form.getElement("templateRadio" + String.valueOf(j)) != null)
                        {%>
                           <%= form.getElement("templateRadio" + String.valueOf(j))%>
                      <%}
                        else
                        {%>
                          &nbsp;
                      <%}%>
                     </td>
                     <td>
                      <% if (form.getRenderableValue("templateName" + String.valueOf(j)) != null &&
                             !form.getRenderableValue("templateName" + String.valueOf(j)).equals(""))
                        {%>
                           <%= form.getRenderableValue("templateName" + String.valueOf(j))%>
                      <%}
                        else
                        {%>
                          &nbsp;
                      <%}%>
                     </td>
                     <td>
                     <% if (form.getRenderableValue("templateOwner" + String.valueOf(j)) != null &&
                            !form.getRenderableValue("templateOwner" + String.valueOf(j)).equals(""))
                        {%>
                           <%= form.getRenderableValue("templateOwner" + String.valueOf(j))%>
                      <%}
                        else
                        {%>
                          &nbsp;
                      <%}%>
                     </td>
                   </tr>
                <%}%>
                   <tr>
                     <td align="center" colspan=3><i>Or</i></td>
                   </tr>
                   <tr>
                     <td colspan=2 >Select a Template</td>
                     <td align="center">
                       <a href="JavaScript:toggleSearch2()"><i>Search</i></a>
                     </td>
                   </tr>
                   <tr>
                     <td colspan=2>Copy a release schedule</td>
                     <td align=center>
                       <a href="JavaScript:toggleSearch3()"><i>Search</i></a>
                     </td>
                   </tr>
                 </table>
               </td>
               <td colspan=3 align=center bgcolor="#eeeeee">
             </tr>
           </table>
         </td>
       </tr>
       <tr>
         <td valign=bottom></td>
       </tr>
     </table>
     </td></tr></table>

     <div id="searchLayer2" class="search" onKeyPress="checkForEnter( 'submitTemplateSourceSearch()' );" style="width:214px; height:100px; z-index:2; left: 310px; top: 260px; visibility: hidden;"  onKeyPress="checkForEnter( submitTemplateSourceSearch );" >
     <table width="100%" border=1 cellspacing=0 cellpadding=1>
       <tr>
         <td>
          <table width="100%">
            <tr bgcolor="#BBBBBB">
              <th colspan=2>Schedule Template Search</th>
            </tr>
            <tr>
				<!-- JR - ITS# 683 -->
				<td class="label" >Template Name</td>
              <td>
                <%= form.getElement("TemplateNameSearch")%>
              </td>
            </tr>
            <!-- JR ITS - 277 -->
              <td class="label" nowrap >Format / Schedule Type</td>
              <td>
                <%= form.getElement("TemplateConfigurationSearch")%>
              </td>
            </tr>
            <tr>
              <td class="label" >Product Category</td>
              <td>
                <%= form.getElement("TemplateProductLineSearch")%>
              </td>
            </tr>
            <tr>
              <td colspan=2>
                <input type="button" name="SubmitSearch" value="Submit Search" onClick="submitTemplateSourceSearch()">
                <input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:toggleSearch2()">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    </div>
    <div id="searchReleaseSource" class="search" onKeyPress="checkForEnter( 'submitReleaseSourceSearch()' );" style="width:214px; height:180px; z-index:3; left: 310px; top: 260px; visibility: hidden;"  onKeyPress="checkForEnter( submitReleaseSourceSearch );">
    <table width="100%" border=1 cellspacing=0 cellpadding=1>
      <tr>
        <td>
          <table width="100%">
            <tr>
              <th colspan=2>Selection Search</th>
            </tr>
            <tr>
              <td>Artist</td>
              <td>
                <%= form.getElement("ArtistSearchSource")%>
              </td>
            </tr>
            <tr>
              <td>Title</td>
              <td>
                <%= form.getElement("TitleSearchSource")%>
              </td>
            </tr>
            <tr>
              <td>Prefix/ID</td>
              <td>
                <%= form.getElement("PrefixSearchSource")%>&nbsp;<%= form.getElement("SelectionSearchSource")%>
             </td>
            </tr>
            <tr>
              <td>UPC Code</td>
              <td>
                <%= form.getElement("UPCSearchSource")%>
              </td>
            </tr>
            <tr>
              <td>Street Date</td>
              <td>
                <%= form.getElement("StreetDateSearchSource")%>
             </td>
            </tr>
            <tr>
              <td>Label</td>
              <td>
                <%= form.getElement("LabelSearchSource")%>
              </td>
            </tr>
            <tr>
              <td colspan=2>Include Closed/Cancel
                <%= form.getElement("ShowAllSearchSource")%>
              </td>
            </tr>
            <tr>
              <td colspan=2>
<input type="button" name="SubmitSearch" value="Submit Search" onClick="submitReleaseSourceSearch()">
<input type="button" name="SubmitCancel3" id="SubmitCancel3" value="Cancel" onClick="Javascript:toggleSearch3()"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    </div>

  <!-- end code for this page -->

 <!-- start search div -->
<%@ include file="selection-search-elements.shtml"%>
  <!-- end search div -->

<div id="commentLayer" class="search" style="width:300px; height:80px; z-index:3; left: 350px; top: 95px; visibility:hidden">
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
<div id="viewCommentLayer" class="search" style="width:300px; height:80px; z-index:3; left: 350px; top: 95px; visibility:hidden;">
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
<div id="releaseCommentLayer" style="position:absolute; visibility:hidden; width:300px; height:180px; z-index:3; left: 350px; top: 95px;">
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
						<%if(saveVisible)
        		{%>
            <input type="button" name="saveButton" id="saveButton" value="Save Comments"  onClick="JavaScript:submitCommentsSave()">&nbsp;&nbsp;
						<%}%>
              <input type="button" name="closeButton" id="closeButtom" value="Cancel" onClick="Javascript:toggle( 'releaseCommentLayer', 'releaseComment' )">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="holdReasonLayer" style="position:absolute; visibility:hidden; width:300px; height:180px; z-index:3; left: 350px; top: 95px;">
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
	</td>
</tr>
</table>
</div>
<div id="recalcLayer" style="position:absolute; visibility:hidden; width:400px; height:250px; z-index:10; left: 230px; top: 195px;">
  <table BGCOLOR="wheat" width="100%" border=2 cellspacing=0 cellpadding=1>
    <tr>
      <td>
        <table width="100%">
          <tr bgcolor="#C0C0C0">
            <td align=center>
            <font size=2 color="black"><b>Alert</b></font>
            </td>
          <tr>
          <tr height=40>
            <td align=center>
            <font size=2> This will permanently recalculate Release Due Dates. Confirm?</font>
            </td>
          <tr>
          <tr>
            <td align=center>
              <input type="button" name="Incomplete" value="Incomplete Tasks Only"  onClick="recalcLayer.style.visibility='hidden';mtbRecalc.reset();sendRecalc(true)">&nbsp;&nbsp;
              <input type="button" name="All" value="All Tasks"  onClick="recalcLayer.style.visibility='hidden';mtbRecalc.reset();sendRecalc(false)">&nbsp;&nbsp;
              <input type="button" name="Cancel" value="Cancel"  onClick="recalcLayer.style.visibility='hidden';mtbRecalc.reset()">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>


<%=form.renderEnd()%>

<script>
var swtOn = parent.top.menuFrame.sOnColor;
var swtOff = parent.top.menuFrame.sOffColor;

// MSC Sitch menu frame selected tab Labels Selection
if(parent.top.menuFrame.mtbLabels)
  parent.top.menuFrame.mtbLabels.showLabelsSubMenu();
if(parent.top.menuFrame.document.all.MIschedule)
{
 parent.top.menuFrame.document.all.MIschedule.style.color = swtOn;
 parent.top.menuFrame.selectedMenuItem = "schedule";
}

// JR release calendar
if(parent.top.menuFrame.document.all.MIreleasecalendar)
 parent.top.menuFrame.document.all.MIreleasecalendar.style.color = swtOff;

if(parent.top.menuFrame.document.all.MIselection)
 parent.top.menuFrame.document.all.MIselection.style.color = swtOff;
if(parent.top.menuFrame.document.all.MIselectionmanufacturing)
 parent.top.menuFrame.document.all.MIselectionmanufacturing.style.color = swtOff;
if(parent.top.menuFrame.document.all.MIpfm)
 parent.top.menuFrame.document.all.MIpfm.style.color = swtOff;
if(parent.top.menuFrame.document.all.MIbom)
 parent.top.menuFrame.document.all.MIbom.style.color = swtOff;
if(parent.top.menuFrame.document.all.MIreports)
 parent.top.menuFrame.document.all.MIreports.style.color = swtOff;
if(parent.top.menuFrame.document.all.MItemplate)
 parent.top.menuFrame.document.all.MItemplate.style.color = swtOff;
if(parent.top.menuFrame.document.all.MItask)
 parent.top.menuFrame.document.all.MItask.style.color = swtOff;
if(parent.top.menuFrame.document.all.MIdaytype)
 parent.top.menuFrame.document.all.MIdaytype.style.color = swtOff;

</script>

<%@ include file="include-bottom-html.shtml"%>
