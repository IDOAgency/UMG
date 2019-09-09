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
   Vector suggestedTemplates = (Vector)context.getDelivery("suggestedTemplates");

   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Schedule Copy release Schedule";

	 // this will check the access right for a user
	 boolean saveVisible = false;
   boolean deleteVisible = false;

	 saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
   deleteVisible = ((String)context.getDelivery("deleteVisible")).equals("true");

   String releaseWeek = (String)context.getDelivery("releaseWeek");
   String typeConfig = (String)context.getDelivery("typeConfig");

	 boolean saveCommentVisible = false;
   saveCommentVisible = ((String)context.getDelivery("saveCommentVisible")).equals("true");

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

  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_COPY_RELEASE_SCHEDULE)%>";
  //schedule-copy-release-assign-schedule
  // JR - ITS 745
  var saveSelectionComments = "<%= MilestoneConstants.CMD_SCHEDULE_SAVE_SELECTION_COMMENTS %>";

  var copyReleaseAssignSchedule = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_COPY_RELEASE_ASSIGN_SCHEDULE)%>";
  var scheduleEditor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_EDITOR)%>";
  var deleteAllTasks = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_DELETE_ALL_TASK)%>";
  var assignTemplate = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_ASSIGN_TEMPLATE)%>";
  var sort = "<%=MilestoneConstants.CMD_SCHEDULE_SORT%>";
  var sortGroup = "<%=MilestoneConstants.CMD_SCHEDULE_GROUP%>";

  var sortTasks = "<%= MilestoneConstants.CMD_SCHEDULE_SCREEN_TASK_SORT%>";
  var recalc = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_RECALC)%>";
  var recalcAll = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_RECALC_ALL)%>";

function submitGroupXXX( alpha, colNo )
  {

  if( colNo == 0 || colNo == 7) // artist column
    document.forms[0].OrderBy.value = "0";
  if( colNo == 1 || colNo == 8 ) // title column
    document.forms[0].OrderBy.value = "1";

  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = sort;
  document.forms[0].submit();
} //end function submitGroup()

function submitGroup( alpha, colNo )
  {

  document.forms[0].OrderBy.value = colNo
  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = sortGroup;
  document.forms[0].submit();
} //end function submitGroup()

  function processLoad()
  {
    form = document.forms[0];
  }
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
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>schedule-copy-release-schedule-javascript.js"></script>

  <script type="text/javascript" language="JavaScript">
  function submitResize()
  {
    // call the right command for resizing
    parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_SCHEDULE_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
 } //end submitResize

 </script>

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("searchArray") %>
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>
<script for=document event=onkeydown language="JavaScript">
checkShortcut();
</script>

<body topmargin=0 leftmargin=0 onLoad="processLoad('streetDate');">

<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>

<!-- msc 12/13/01 -->
<input type="hidden" name="alphaGroupChr" value="">

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

<table width=900 cellpadding=0 border=0>
  <tr valign="middle" align="left">
    <td width=280 >
      <div align="left">
        <a href ="JavaScript:submitResize()"
          onMouseOver="Javascript:mtbToggle.over();return true;"
          onMouseOut="Javascript:mtbToggle.out()"
          onClick="Javascript:mtbToggle.click(); return true;">
          <img name="Toggle" id="Toggle" src="<%= inf.getImageDirectory() %>Toggle_On.gif" width=27 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbToggle = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Toggle', "JavaScript:submitResize()", 27, 14);
        </script>
      </div>
    </td>
    <td rowspan=2 width=10>
      <img src="<%= inf.getImageDirectory() %>pixelshim.gif" width=10>
    </td>
    <td width=170 >
      <SPAN class="title">Schedule</SPAN>
    </td>
    <td width=85></td>
    <td width=85></td>
    <td width=85>
      <div align="center">
			<%if(saveVisible)
        {%>
        <a href="JavaScript:submitSave('Save')"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript">
          var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
			<%}%>
      </div>
    </td>
    <td width=85></td>
    <td width=85>
      <div align="center">
			<%if(deleteVisible)
        {%>
        <a href ="JavaScript:submitDelete('Delete')"
          onMouseOver="Javascript:mtbDelete.over();return true;"
          onMouseOut="Javascript:mtbDelete.out()"
          onClick="Javascript:mtbDelete.click(); return true;">
          <img name="Delete" id="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript">
           var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', "JavaScript:submitDelete( 'Delete' )", 66, 14);
        </script>
			<%}%>
      </div>
    </td>
  </tr>

  <tr>
    <td valign="top">

<% //notepad visibility check
   if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
   {
  /* ===============start selection navigation panel===========================
       This part of the code is made an include to make it easy to manage this page.
       The the java script needed resides in this page.
       The include just gets and puts the code here as it was written here.
     */
  %>
       <%@ include file="include-selection-notepad.shtml" %>

<% /* ===============end selection navigation panel=========================== */ %>
<%}%>
    </td>

	<!-- JR - ITS #444 -->
	<% if (selections.getIsDigital()) { %>
		<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" colspan="8" width="100%">
	<% } else { %>
		<td bgcolor="lavender" valign="top" colspan="8" width="100%">
	<% } %>
	<!-- JR - ITS #444 -->

      <table width="100%" height="100%" cellpadding=5 border=0>
        <tr width="100%">
          <td valign="top" width="100%">
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

              <!-- JR - ITS 741  -->
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
                      <img name="Clear" id="Clear" src="<%= inf.getImageDirectory() %>Clear_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
                   </a>
                   <script type="text/javascript" language="JavaScript" >
                   <!--
                     var mtbClear = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Clear', "JavaScript:submitClearDate( 'Clear' )", 66, 14);
                   // -->
                   </script>

                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   <a href="JavaScript:submitRecalc( false, 'Recalc' )"
                      onMouseOver="Javascript:mtbRecalc.over();return true;"
                      onMouseOut="Javascript:mtbRecalc.out()"
                      onClick="Javascript:mtbRecalc.click(); return true;">
                      <img name="Recalc" src="<%= inf.getImageDirectory() %>Recalc_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
                   </a>

                   <script type="text/javascript" language="JavaScript" >
                   <!--
                      var mtbRecalc = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Recalc', "JavaScript:submitRecalc( false, 'Recalc')", 66, 14);
                   // -->
                   </script>
                </td>
                <td class="label">Task Owner</td>
                <td align="right" nowrap><img src="<%= inf.getImageDirectory() %>filter_access1.gif" border=0>
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

            <!-- code specific for this page -->
           	<table class="detailList" width="100%" border="0" cellspacing="0">
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
				        <td class="label" colspan="1" align="center" bgcolor="#eeeeee"></td>
				        <td class="label" colspan="5" align="center" bgcolor="#eeeeee">New Release Schedule</td>
				        <td class="label" colspan="3" align="center" bgcolor="#eeeeee"></td>
			        </tr>
              <tr>
				        <td colspan="1" align="center" bgcolor="#eeeeee"></td>
				        <td colspan="5" align="center" bgcolor="#eeeeee">Milestone suggests the following template(s):</td>
				        <td colspan="3" align="center" bgcolor="#eeeeee"></td>
			       </tr>
			       <tr>
				       <td colspan="1" align="center" bgcolor="#eeeeee"></td>
				       <td colspan="6" align="center" bgcolor="#eeeeee">
				         <table align="center" width="280" border="1" cellspacing="0" cellpadding="1">
					         <tr>
					           <th>&nbsp;</th>
					           <th>Artist</th>
					           <th>Title</th>
					           <th>UPC</th>
					           <th>Selection</th>
					           <th nowrap>Str Dt</th>
				           </tr>
                <%
                 for (int j = 0; j < suggestedTemplates.size(); j++)
                 {
                 %>
                   <tr>
                     <td>
                     <% if (form.getElement("selectionRadio" + String.valueOf(j)) != null)
                        {%>
                          <%= form.getElement("selectionRadio" + String.valueOf(j))%>
                      <%}
                        else
                        {%>
                          &nbsp;
                      <%}%>
                     </td>
                     <td nowrap width="98">
                     <% if (form.getRenderableValue("selectionArtist" + String.valueOf(j)) != null &&
                            !form.getRenderableValue("selectionArtist" + String.valueOf(j)).equals(""))
                        {%>
                           <%= form.getRenderableValue("selectionArtist" + String.valueOf(j))%>
                      <%}
                         else
                         {%>
                           &nbsp;
                       <%}%>
                     </td>
                     <td nowrap width="94">
                     <% if (form.getRenderableValue("selectionTitle" + String.valueOf(j)) != null &&
                            !form.getRenderableValue("selectionTitle" + String.valueOf(j)).equals(""))
                        {%>
                           <%= form.getRenderableValue("selectionTitle" + String.valueOf(j))%>
                      <%}
                         else
                         {%>
                           &nbsp;
                       <%}%>
                     </td>
                     <td nowrap width="24">
                     <% if (form.getRenderableValue("selectionUpc" + String.valueOf(j)) != null &&
                            !form.getRenderableValue("selectionUpc" + String.valueOf(j)).equals(""))
                        {%>
                           <%= form.getRenderableValue("selectionUpc" + String.valueOf(j))%>
                      <%}
                         else
                         {%>
                           &nbsp;
                       <%}%>
                     </td>
                     <td nowrap width="24">
                     <% if (form.getRenderableValue("selectionNo" + String.valueOf(j)) != null &&
                            !form.getRenderableValue("selectionNo" + String.valueOf(j)).equals(""))
                        {%>
                           <%= form.getRenderableValue("selectionNo" + String.valueOf(j))%>
                      <%}
                        else
                        {%>
                          &nbsp;
                      <%}%>
                     </td>
                     <td nowrap width="24">
                     <% if (form.getRenderableValue("selectionStreet" + String.valueOf(j)) != null &&
                            !form.getRenderableValue("selectionStreet" + String.valueOf(j)).equals(""))
                        {%>
                           <%= form.getRenderableValue("selectionStreet" + String.valueOf(j))%>
                      <%}
                        else
                        {%>
                          &nbsp;
                      <%}%>
                     </td>
                   </tr>
                <%}%>
				  </table>
			    </td>
				<td colspan="3" align="center" bgcolor="#eeeeee">
			  </tr>
		    </table>
          </td>
        </tr>
        <tr>
          <td valign="bottom"></td>
        </tr>
      </table>
   <!-- end code specific for this page -->

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

<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>
