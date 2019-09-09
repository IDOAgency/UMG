<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message

   *   2003-01-09 - lg - ITS 877 Added calendar group to getDayType method
   */
%>
<%@ include file="template-top-page.shtml"%>
<script>
function HideLayer()
{
 toggle( 'SearchLayer', 'TaskSearch' );
 mtbSearch.click();
}
</script>
<%
   //Selection currentSelection = (Selection)MilestoneHelper.getNotepadFromSession(MilestoneConstants.NOTEPAD_SCHEDULE, context).getSelected();
   Selection currentSelection = (Selection)context.getSessionValue("Selection");
   Schedule schedule = null;
   if (currentSelection != null)
     schedule = currentSelection.getSchedule();
   Vector scheduleRights = (Vector)context.getDelivery("scheduleRights");

	  // this will check the access right for a user
	 boolean saveVisible = false;
   boolean deleteVisible = false;
   boolean recalcVisible = false;

	 saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
   deleteVisible = ((String)context.getDelivery("deleteVisible")).equals("true");
   recalcVisible = ((String)context.getDelivery("recalcClearCloseVisible")).equals("1");

   boolean saveCommentVisible = false;
   saveCommentVisible = ((String)context.getDelivery("saveCommentVisible")).equals("true");

   // release upgrade 8.1 hide buttons
   if(MilestoneConstants.IsGDRSactive)
   {
	if(currentSelection.getIsDigital())
	{
		 saveVisible = false;
		 deleteVisible = false;
		 recalcVisible = false;
		 saveCommentVisible = false;
	}
  }	
   // release upgrade 8.1 if not an ADMIN hide buttons


   User user = (User)context.getSessionValue("user");
   String userName = user.getName();


   /*  msc its 999 Field scheduleAccess not long being used
   Acl acl = new Acl(userName);
   Vector companyAcl = acl.getCompanyAcl();

   for (int i = 0; i < companyAcl.size(); i++)
   {
     CompanyAcl scheduleAccess = (CompanyAcl)companyAcl.elementAt(i);
   }
   */

   String releaseWeek = (String)context.getDelivery("releaseWeek");
   String typeConfig = (String)context.getDelivery("typeConfig");

   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Schedule screen";

   //indicate if it is a key task
   boolean isKeyTask = true;
   //indicate who is the owner Lable or UML
   boolean isLabelOwner = true;

   // this variable determines if "selections" or "tasks" is to be shown
   //should come from the notepad
   boolean showTasks = false;

		String prefixId = "";
		if (currentSelection.getPrefixID() != null)
		{
			prefixId = currentSelection.getPrefixID().getAbbreviation();
		}

		String selectionNo = "";
		if(currentSelection.getSelectionNo() != null)
		{
			selectionNo = currentSelection.getSelectionNo();
		}

		String templateName = "";
		if(context.getDelivery("templateName") != null)
		{
			templateName = (String)context.getDelivery("templateName");
		}

%>

<%@ include file="template-top-html.shtml"%>

<script type="text/javascript" language="JavaScript">
  //global variables needed in include schedule-task-editor-javascript.js
  var form;

  var sort = "<%=MilestoneConstants.CMD_SCHEDULE_TASK_SORT%>";
  var sortTasks = "<%= MilestoneConstants.CMD_SCHEDULE_TASK_SCREEN_TASK_SORT%>";
  var editor = "<%= MilestoneConstants.CMD_SCHEDULE_TASK_EDITOR%>";
  var save ="<%= MilestoneConstants.CMD_SCHEDULE_TASK_SAVE%>";
  var scheduleEditor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_EDITOR)%>";
  var deleteAllTasks = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_DELETE_ALL_TASK_IN_TASK_EDITOR)%>";
  var deleteTask = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_DELETE_TASK_EDITOR)%>";
  var addTask = "<%= MilestoneConstants.CMD_SCHEDULE_ADD_TASK%>";
  var search = "<%=MilestoneConstants.CMD_SCHEDULE_TASK_SEARCH%>";
  var recalc = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_RECALC)%>";
  var recalcAll = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_RECALC_ALL)%>";
  var clearDate = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_CLEAR)%>";
  var filter = "<%=MilestoneConstants.CMD_SCHEDULE_TASK_FILTER%>";
  var deptFilter = "<%=MilestoneConstants.CMD_SCHEDULE_TASK_DEPT_FILTER%>";  // msc item 1957
  var close = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_CLOSE)%>";

  function processLoad(pFocusField)
  {
    focusField(pFocusField);
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
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>schedule-task-editor-javascript.js"></script>

 <!-- Java script functions specific to this page -->
 <script type="text/javascript" language="JavaScript">
  function submitResize()
  {
    // call the right command for resizing
   //check if "selections" is to be shown or "tasks" for the nav menu
   <%if (showTasks == false) //show "selections"
   {%>
    parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_SCHEDULE_TASKS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
   <%}
   else
   {%>
    parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_TASKS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
   <%}%>

 } //end submitResize

 </script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>
<script for=document event=onkeydown language="JavaScript">
checkShortcut();
</script>

<body topmargin=0 leftmargin=0 onLoad="processLoad( 'streetDate' );">

<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>
<%=form.getElement("OrderTasksBy")%>

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
    <td width=85></td>
    <td width=85>
      <div align="center" id="deteleDiv">
			<%if(deleteVisible)
        {%>
        <a href ="JavaScript:submitDelete('Delete')"
          onMouseOver="Javascript:mtbDelete.over();return true;"
          onMouseOut="Javascript:mtbDelete.out()"
          onClick="Javascript:mtbDelete.click(); return true;">
          <img name="Delete" id="Delete" src="<%=inf.getImageDirectory()%>Delete_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript">
           var mtbDelete = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Delete', "JavaScript:submitDelete( 'Delete' )", 66, 14);
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

<%
//notepad visibility check
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{

       //check if "selections" is to be shown or "tasks" for the nav menu
       if (!showTasks) //show "selections"
       {%>
       <td valign="top">
       <%@ include file="include-schedule-task-notepad.shtml" %>
       </td>

  <%  }//end if showTasks == false
    else
    {%>
     <td valign="top">
       <%@ include file="include-tasks-notepad.shtml" %>
     </td>
  <%}
  }
  //end notepad vis check
  %>


	<!-- JR - ITS #444 -->
	<% if (currentSelection.getIsDigital()) { %>
		<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" align="left" colspan="8" width="100%">
	<% } else { %>
		<td bgcolor="lavender" valign="top" colspan="8" align="left" width="100%">
	<% } %>
	<!-- JR - ITS #444 -->

     <%if(currentSelection == null)
      {%>
       <center><h4>Milestone System<br><br><br>Please search for Selections on the left panel to this view </h4></b></center>
    <%}
      else
      {%>
      <table width="100%" height="100%" cellpadding=5 border=0>
        <tr width="100%">
          <td valign="top" width="100%">
              <table width="100%" border=0>
                <tr>

<% if (currentSelection != null && !currentSelection.getIsDigital()) { %>
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- BEGIN PHYSICAL SCHEDULE SCREEN -->

                <!-- STEET/SHIP DATE, RELEASE WK/CYCLE, STATUS -->

					<td class="label" nowrap>Street/Ship Date <span class=indicator ></span></td>
					<td nowrap>

					<% if (currentSelection != null && currentSelection.getStreetDate() != null) {%>
								<%= MilestoneHelper.getFormatedDate(currentSelection.getStreetDate())%>
								<SPAN class=indicator >
								<%= MilestoneHelper.getDayType(currentSelection.getCalendarGroup(), currentSelection.getStreetDate())%>
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
                    if (currentSelection != null)
                    {
                       status = currentSelection.getSelectionStatus();

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
                <td><%if (currentSelection != null)
                      {
                       if(currentSelection.getInternationalDate() != null)
                       { %>
                           <%= MilestoneHelper.getFormatedDate(currentSelection.getInternationalDate())%>
                     <%}
                     }%>
                 </td>
                 <td></td>
                <td nowrap class="label" align=right>
                <% if (currentSelection != null && currentSelection.getSpecialPackaging())
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
                <% if (currentSelection.getHoldSelection())
                {%>
					<input type=checkbox title="Hold - Read-Only Field" checked disabled>Hold
                <%}
                    else
                {%>
                    <input type=checkbox title="Hold- Read-Only Field" disabled>Hold
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
                <% if (currentSelection != null && currentSelection.getPressAndDistribution())
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
                  if (currentSelection != null)
                  {
                  if (currentSelection.getArtist() != null)
                  {%>
                    <%= currentSelection.getArtist()%>
                <%}
                }%>
                </td>
                <td class="label">Project ID</td>
                <td><%
                   if (currentSelection != null)
                   {
                     if(String.valueOf(currentSelection.getProjectID()) != null)
                       {%>
                         <%= String.valueOf(currentSelection.getProjectID())%>
                     <%}
                     }%>
                </td>
              </tr>

              <!-- Title, Prefix -->
              <tr>
                <td class="label">Product Title</td>
                <td colspan=3>
                <%
                if (currentSelection != null)
                {
                 if (currentSelection.getTitle() != null)
                   {%>
                     <%= currentSelection.getTitle()%>
                 <%}
                 }%>
                </td>
                <td class="label" nowrap rowspan=2 valign=bottom>Prefix/<BR>Local Prod #</td>
                <td nowrap rowspan=2 valign=bottom><%=prefixId%>&nbsp;<%=selectionNo%></td>
              </tr>

              <!-- Releasing Family, Local Prod # -->
              <tr>
				<td class="label" nowrap valign=bottom>Releasing Family</td>
				<td colspan=3 valign=bottom><%= ReleasingFamily.getName(currentSelection.getReleaseFamilyId()) %></td>
              </tr>

              <!-- Label, UPC -->
              <tr>
                <td class="label">Label</td>
                <%
                   Label label = null;
                   if (currentSelection != null)
                     label = currentSelection.getLabel();%>
                <td colspan=3>
                <%if (label != null)
                  {%>
                    <%= label.getName()%>
                <%}%>
                </td>
                <td class="label">UPC</td>
                <td nowrap>
                <% if (currentSelection != null && currentSelection.getUpc() != null)
                   {%>
                      <%= currentSelection.getUpc()%>
                 <%}%>
                </td>
              </tr>

              <!-- Imprint  -->
              <tr>
                <td class="label" >Imprint</td>
                <td colspan=3><%= currentSelection.getImprint() %></td>

              <!-- Distribution Company -->
              <td class="label" >Dist Company</td>
              <%
               String distCoName = currentSelection != null && currentSelection.getLabel() != null ? MilestoneHelper_2.getLabelDistCo(currentSelection.getLabel().getStructureID()) : "";
              %>
              <td colspan=3><%=distCoName%></td>

              </tr>

              <!-- TYPE FORMAT  -->
              <tr>
                <td class="label" >Type/Format</td>
                <%
                 if (currentSelection != null)
                 {
                   ReleaseType releaseType = currentSelection.getReleaseType();
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

<% } else if (currentSelection != null && currentSelection.getIsDigital()) { %>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- BEGIN DIGITAL SCHEDULE SCREEN -->

                <!-- STEET/SHIP DATE, STATUS -->

					<td class="label" nowrap>Digital Release Date <span class=indicator ></span></td>
					<td nowrap>

					<% if (currentSelection != null && currentSelection.getDigitalRlsDate() != null) {%>
								<%= MilestoneHelper.getFormatedDate(currentSelection.getDigitalRlsDate())%>
								<SPAN class=indicator >
								<%= MilestoneHelper.getDayType(currentSelection.getCalendarGroup(), currentSelection.getDigitalRlsDate())%>
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
                    if (currentSelection != null)
                    {
                       status = currentSelection.getSelectionStatus();

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
                  if (currentSelection != null)
                  {
                  if (currentSelection.getArtist() != null)
                  {%>
                    <%= currentSelection.getArtist()%>
                <%}
                }%>
                </td>
                <td nowrap class="label">
                <% if (currentSelection != null && currentSelection.getPressAndDistribution())
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
                <% if (currentSelection != null && currentSelection.getPriority())
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
                if (currentSelection != null)
                {
                 if (currentSelection.getTitle() != null)
                   {%>
                     <%= currentSelection.getTitle()%>
                 <%}
                 }%>
                </td>
                <td class="label">Project ID</td>
                <td><%
                   if (currentSelection != null)
                   {
                     if(String.valueOf(currentSelection.getProjectID()) != null)
                       {%>
                         <%= String.valueOf(currentSelection.getProjectID())%>
                     <%}
                     }%>
                </td>
              </tr>

              <!-- Releasing Family, Prefix  -->
              <tr>
				<td class="label" nowrap>Releasing Family</td>
				<td colspan=3><%= ReleasingFamily.getName(currentSelection.getReleaseFamilyId()) %></td>
                <td class="label" nowrap rowspan=2 valign=bottom>Phys. Prefix/<BR>Local Prod #</td>
                <td nowrap rowspan=2 valign=bottom><%=prefixId%>&nbsp;<%=selectionNo%></td>
              </tr>

              <!-- Label, Local Prod # -->
              <tr>
                <td class="label" valign=bottom>Label</td>
                <%
                   Label label = null;
                   if (currentSelection != null)
                     label = currentSelection.getLabel();%>
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
                <td colspan=3><%= currentSelection.getImprint() %></td>
                <td class="label">UPC</td>
                <td nowrap>
                <% if (currentSelection != null && currentSelection.getUpc() != null)
                   {%>
                      <%= currentSelection.getUpc()%>
                 <%}%>
                </td>
              </tr>
              <!-- TYPE FORMAT, GRid -->
              <tr>
                <td class="label" >Type/Format</td>
                <%
                 if (currentSelection != null)
                 {
                   ReleaseType releaseType = currentSelection.getReleaseType();
                   if ( releaseType != null && releaseType.getAbbreviation() != null)
                   {
                 %>

                <td colspan=3><%= typeConfig%></td>
                  <%}
                 }%>
                 <td class="label">GRid#</td>
                 <td><input type=text disabled value="<%= currentSelection.getGridNumber() %>" class="ctrlMediumGrayBG"></td>
              </tr>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- END DIGITAL SCHEDULE SCREEN -->
<% } %>

              <tr bgcolor="white">
                <td colspan="6"></td>
              </tr>

              <!-- // msc added department filter -->

              <tr>
                  <td class="label">Department</td>
                 <td colspan=3 align="left" nowrap><img src="<%=inf.getImageDirectory()%>filter_access1.gif" border=0>
                  <%
                  String deptFilterFlag = (String)context.getSessionValue("deptFilterFlag");
                  if (deptFilterFlag != null && deptFilterFlag.equalsIgnoreCase("Yes"))
                  {%>
                  <%= form.getElement("deptFilter")%>
               <%}
                   else
                   {
                 %>
                  <%= form.getRenderableValue("deptFilter")%>
               <%}%>
                </td>

                </td>
                 <td class="label">Task Owner</td>
                <td colspan=3 align="right" nowrap><img src="<%=inf.getImageDirectory()%>filter_access1.gif" border=0>
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
               <!-- // msc added department filter -->


              <tr>
                <td colspan=3 nowrap>
              <%if (recalcVisible)
                {
              %>

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

                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   <a href="JavaScript:submitClose(false)"
                      onMouseOver="Javascript:mtbClose.over();return true;"
                      onMouseOut="Javascript:mtbClose.out()"
                      onClick="Javascript:mtbClose.click(); return true;">
                      <img name="Close" src="<%=inf.getImageDirectory()%>Close_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
                   </a>

                   <script type="text/javascript" language="JavaScript" >
                   <!--
                      var mtbClose = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Close', "JavaScript:submitClose(false)", 66, 14);
                   // -->
                   </script>
             <%
             }
             %>


              </tr>
            </table>

            <table class="detailList" width="100%" border=1 cellspacing=0>
              <tr>
               <!-- The following links will display the data acording to field to sort by-->
                <th nowrap><a href="Javascript:submitDetailList(0);">Task Assignment</a></th>
                <th><a href="Javascript:submitDetailList(1);">Wks</a></th>
                <th>&nbsp;</th>
                <th nowrap><a href="Javascript:submitDetailList(2);" >Due</th>
                <th nowrap><a href="Javascript:submitDetailList(3);" >Complete</th>
                <th>&nbsp;</th>
                <th nowrap><a href="Javascript:submitDetailList(4);" >Status</th>
                <th>&nbsp;</th>
                <th nowrap><a href="Javascript:submitDetailList(5);" >Vendor</th>
              </tr>

              <%

                 Vector taskList = null;
                 if (schedule != null)
                   taskList = schedule.getTasks();

                 if (taskList != null)
                 {
                  for (int i = 0; i < taskList.size(); i++)
                  {
                    ScheduledTask task = (ScheduledTask)taskList.elementAt(i);
                 %>
              <tr>
              <%  String fontColor = "";

                isKeyTask = task.getIsKeytask();
                isLabelOwner = !MilestoneHelper.isUml(task);
                if (isKeyTask)
                {
                  if (isLabelOwner)
                  {
                    fontColor = "class=\"nonUmlKey\"";
                  }
                  else
                  {
                    fontColor = "class=\"umlKey\"";
                  }
                }
                else
                {
                  fontColor = "";
                }
               %>
                <td <%= fontColor%>><%=task.getName()%></td>
            <%
               int editable = Integer.parseInt((String)scheduleRights.elementAt(i));
               if (editable == MilestoneConstants.READ_WRITE)
               {%>
                <td align="right">
                   <% if (form.getElement("wksToRelease" + String.valueOf(i)) != null)
                      {%>
                         <%= form.getElement("wksToRelease" + String.valueOf(i))%>
                     <%}
                       else
                       {%>
                       &nbsp;
                     <%}%>
                </td>
                <td><SPAN class=indicator >
                   <% if (form.getRenderableValue("dayType" + String.valueOf(i)) != null &&
                          !form.getRenderableValue("dayType" + String.valueOf(i)).equals(""))
                      {%>
                         <%=form.getRenderableValue("dayType" + String.valueOf(i))%>
                    <%}
                      else
                      {%>
                        &nbsp;
                    <%}%>
                    </SPAN>
                </td>
                <td>
                  <% if(form.getElement("duedate" + String.valueOf(i)) != null)
                     {%>
                        <%= form.getElement("duedate" + String.valueOf(i))%>
                   <%}
                     else
                     {%>
                     &nbsp;
                   <%}%>
                </td>
                <td>
                  <% if (form.getElement("completion" + String.valueOf(i)) != null)
                     {%>
                        <%= form.getElement("completion" + String.valueOf(i))%>
                   <%}
                     else
                     {%>
                     &nbsp;
                   <%}%>
                </td>
                <td>
                <%= form.getElement("comments" + String.valueOf(i))%>
                <% String comment = "";
                   comment = form.getRenderableValue("comments" + String.valueOf(i));
                   String onClickValue = "document.all.viewCommentLayer.style.visibility='hidden';showDetailData( document.all.comments" + String.valueOf(i) + ", 'commentLayer', 'comments' );";

                   if (comment != null && comment.length() > 0 && !comment.equals(""))
                   {%>
                   <img src="<%=inf.getImageDirectory()%>afile.gif" onClick="<%= onClickValue%>">
               <%}
                   else
                   {%>
                   <img src="<%=inf.getImageDirectory()%>file.gif" onClick="<%= onClickValue%>">
               <%}%>

                </td>
                <td>
                <% if (form.getElement("status" + String.valueOf(i)) != null)
                   {%>
                      <%=form.getElement("status" + String.valueOf(i))%>
                 <%}%>
                </td>
                <td>
                <% if (form.getRenderableValue("completion" + String.valueOf(i)) == null ||
                         form.getRenderableValue("completion" + String.valueOf(i)).length() == 0 )
                   {%>
                     <a href="JavaScript:submitUnassign(<%=task.getTaskID()%>)">del</a>
                 <%}
                   else
                   {%>
                     &nbsp;
                 <%}%>
                </td>
                <td>
                  <% if (form.getElement("vendor" + String.valueOf(i)) != null)
                     {%>
                        <%=form.getElement("vendor" + String.valueOf(i))%>
                   <%}
                     else
                     {%>
                     &nbsp;
                   <%}%>
                </td>
            <%}
              else   //no edit rights
              {%>
                <td align="right">
                  <% if (form.getRenderableValue("wksToRelease" + i) != null)
                     { %>
                         <%=form.getRenderableValue("wksToRelease" + i)%>
                   <%}
                     else
                     {%>
                       &nbsp;
                   <%}%>
                </td>
                <td><SPAN class=indicator >
                   <% if (form.getRenderableValue("dayType" + String.valueOf(i)) != null &&
                          !form.getRenderableValue("dayType" + String.valueOf(i)).equals(""))
                      { %>
                          <%=form.getRenderableValue("dayType" + String.valueOf(i))%>
                     <%}
                       else
                       {%>
                         &nbsp;
                      <%}%>
                    </SPAN>
                </td>
                <td>
                  <% if (task.getDueDate() != null)
                     {%>
                       <%= MilestoneHelper.getFormatedDate(task.getDueDate())%>
                   <%}
                     else
                     {%>
                      &nbsp;
                   <%}%>
                </td>
                <td>
                    <%if (task.getCompletionDate() != null && !task.getCompletionDate().equals(""))
                    {%>
                    <%= MilestoneHelper.getFormatedDate(task.getCompletionDate())%>
                  <%}
                    else
                    {%>
                    &nbsp;
                  <%}%>
                </td>
                <td>
                   <%= form.getElement("comments" + String.valueOf(i))%>
                   <%String comment = "";
                   comment = form.getRenderableValue("comments" + String.valueOf(i));
                   String onClickValue = "document.all.viewCommentLayer.style.visibility='hidden';showDetailData( document.all.comments" + String.valueOf(i) + ", 'viewCommentLayer', 'comments' );";

                   if (comment != null && !comment.equals(""))
                   {%>
                   <img src="<%=inf.getImageDirectory()%>afile.gif" onClick="<%= onClickValue%>">
                 <%}
                   else
                   {%>
                   <img src="<%=inf.getImageDirectory()%>file.gif" onClick="<%= onClickValue%>">
                 <%}%>

                </td>
                <td>
                 <% if (task.getScheduledTaskStatus() != null && !task.getScheduledTaskStatus().equals(""))
                    {%>
                      <%=String.valueOf(task.getScheduledTaskStatus())%>
                  <%}
                    else
                    {%>
                      &nbsp;
                  <%}%>
                </td>
                <td>&nbsp;</td>
                <td>
                <% if (task.getVendor() != null && !task.getVendor().equals(""))
                {%>
                    <%=task.getVendor()%>
               <%}
                 else
                 {%>
                   &nbsp;
               <%}%>
                </td>
            <%}%>
              </tr>
             <%}//end for loop
             }//end if
             %>
             </table>

             <table border=0 width="100%" >

               <tr>
                 <td align="center" >
                   <a href="JavaScript:submitSave( 'Save2' )"
                      onMouseOver="Javascript:mtbSave2.over();return true;"
                      onMouseOut="Javascript:mtbSave2.out()"
                      onClick="Javascript:mtbSave2.click(); return true;">
                      <img name="Save" id="Save" src="<%=inf.getImageDirectory()%>Save_On.gif" width=66 height=14 border=0 HSPACE=0 vspace=0 align="absmiddle">
                   </a>
                   <script type="text/javascript" language="JavaScript" >
                   <!--
                     var mtbSave2 = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Save', "JavaScript:submitSave( 'Save2' )", 66, 14);
                   // -->
                   </script>
                 </td>
             <%}//end currentSelection null check%>
               </tr>
             </table>
           </td>
         </tr>
          <tr>
            <td align="left" valign="bottom">
            <b>Template:</b>&nbsp;<%=templateName%>
            </td>
          </tr>

         <!-- // msc Item 1842 start updated information which is only read-only -->
         <%
         String bColor = "lavender";
     	 if (currentSelection.getIsDigital())
	   bColor = MilestoneConstants.DIGITAL_PINK;
	 %>
          <table width="616" bgcolor="<%=bColor%>" border=0>
            <tr bgcolor="white">
              <td colspan="6"></td>
              </tr>
              <tr>
              <td colspan="2">
  		<b>Last Updated:&nbsp;&nbsp</b>
  		<%= form.getRenderableValue("lastSchedUpdatedDate") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%= context.getDelivery("lastSchedUpdateUser")%>
              </td>

             <%if (form.getElement("autoCloseDate") != null && form.getRenderableValue("autoCloseDate").length() > 0)
             {%>
               <td colspan="2">
         	<b>Auto-Closed:&nbsp;&nbsp;&nbsp;</b>
  		<%= form.getRenderableValue("autoCloseDate") %>
              </td>
             <%}%>

              </tr>
              <tr bgcolor="white">
                <td colspan="6"></td>
              </tr>
          </table>
         <!-- // msc Item 1842 start updated information which is only read-only -->





       </table>
     </td>
   </tr>
 </table>

 <!-- DIV definitions -->
<!-- start search div -->
<div id="SearchLayer" class="search" onKeyPress="checkForEnter( 'submitSearch()' );" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr><th colspan="2">Task Search</th></tr>
		<tr>
			<td>Task Name</td>
			<td><%= form.getElement("TaskNameSearch") %></td>
		</tr>
		<tr>
			<td>Key Task</td>
			<td align="center"><%= form.getElement("KeyTaskSearch") %></td>
		</tr>
		<tr>
			<td>Owner</td>
			<td><%= form.getElement("TaskOwnerSearch") %></td>
		</tr>
		<tr>
			<td>Department</td>
			<td><%= form.getElement("TaskDepartmentSearch") %></td>
		</tr><tr><td colspan="2"><input type="button" name="SubmitSearch" value="Submit Search" onClick="submitSearch()">
			<colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
			</td>
			</tr>
		</table>
</td></tr></table>
</div>
<!-- end search div -->

<div id="commentLayer" class="search" style="position:absolute; width:250px; height:80px; z-index:3; left: 160px; top: 65px; visibility:hidden">
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
<div id="viewCommentLayer" class="search" style="position:absolute; width:250px; height:80px; z-index:3; left: 220px; top: 95px; visibility:hidden;">
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
<div id="releaseCommentLayer" style="position:absolute; visibility:'hidden'; width:300px; height:180px; z-index:3; left: 100px; top: 120px;">
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
            <input type="button" name="saveButton" id="saveButton" value="Save Comments"  onClick="JavaScript:submitSave('Save')">&nbsp;&nbsp;
						<%}%>
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

<DIV id=vendorLayer class="search" style="position:absolute; visibility:hidden; width:250px; height:80px; z-index:3; left: 250px; top: 95px;">
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

<%
String recalcDate = (String)context.getDelivery("recalc-date");

if (recalcDate != null && recalcDate.equalsIgnoreCase("true"))
{%>
<script language="JavaScript">
recalcLayer.style.visibility='visible';
</script>
<%
}%>

<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>
