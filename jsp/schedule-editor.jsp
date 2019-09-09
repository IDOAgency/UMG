<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*, java.text.*"
			gets the context, form formValidation and message
  *
  * Development history:
  *   2003-12-17 - lg - ITS 281 Added multi-complete dates editor
  *   2003-01-09 - lg - ITS 877 Added calendar group to getDayType method
  *   2004-03-01 - lg - ITS 527 Removed Done status 9/9/99 auto-population of completion date for UML tasks
  *   2004-04-15 - lg - ITS 974 Display error if attempt to launch multiple complete date
  *                     editor in a new, unsaved schedule;
  */
%>

<%@ include file="template-top-page.shtml"%>
<%@ include file="callHelp.js"%>
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
   deleteVisible = ((String)context.getDelivery("deleteVisible")).equals("true");
   recalcVisible = ((String)context.getDelivery("recalcClearCloseVisible")).equals("1");

	 boolean saveCommentVisible = false;
   saveCommentVisible = ((String)context.getDelivery("saveCommentVisible")).equals("true");

    //System.out.println("IsGDRSactive...." + MilestoneConstants.IsGDRSactive);
    
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

    //Used by include-selection-notepad
    String editorName = MilestoneConstants.CMD_SCHEDULE_EDITOR;
%>

<%@ include file="template-top-html.shtml"%>

<script type="text/javascript" language="JavaScript">
  //global variables needed in include schedule-editor-javascript.js
  var sort = "<%=MilestoneConstants.CMD_SCHEDULE_SORT%>";
  var sortGroup = "<%=MilestoneConstants.CMD_SCHEDULE_GROUP%>";
  var sortTasks = "<%= MilestoneConstants.CMD_SCHEDULE_SCREEN_TASK_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_EDITOR)%>";
  var deleteAllTasks = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_DELETE_ALL_TASK)%>";
  var taskEditor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_TASK_EDITOR)%>";
  var save = "<%= MilestoneConstants.CMD_SCHEDULE_SAVE %>";
  var search = "<%=MilestoneConstants.CMD_SCHEDULE_SELECTION_RELEASE_SEARCH%>";
  var recalc = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_RECALC)%>";
  var recalcAll = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_RECALC_ALL)%>";
  var clearDate = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_CLEAR)%>";
  var deleteTask = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_DELETE_TASK)%>";
  var filter = "<%=MilestoneConstants.CMD_SCHEDULE_FILTER%>";
  var deptFilter = "<%=MilestoneConstants.CMD_SCHEDULE_DEPT_FILTER%>";
  var close = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_CLOSE)%>";

  function submitGroupXXXX( alpha, colNo )
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

  document.forms[0].OrderBy.value = colNo;
  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = sortGroup;
  document.forms[0].submit();
  } //end function submitGroup()


  function helpConext()
  {
    callHelp('Help/','Schedule.htm');
    mtbHelp.reset();
  }//end function helpContext

  function processLoad(pFocusField)
  {
    //focusField(pFocusField);
    // JR - ITS 842
    parent.top.bottomFrame.focus();
  }

  // Apply business rules and check if ok to show the multiple complete dates editor
  // ITS 281 - created
  // ITS 974 - Display error if new, unsaved schedule;
  //           In the context of a new, unsaved schedule, the Selection object in
  //           session has CORRECT selection id - use this to test if a schedule
  //           has been saved in the database; The selection id of the Schedule
  //           object in session and of of the Selection's Schedule object in session
  //           are incorrect in this context;
  function isValidMultCompleteDateEditorRequest(rowNo)
  {
        var isValidRequest = true;
        if (document.forms[0]) {
            <%
   			Selection mcdSelection = (Selection)context.getSessionValue("Selection");
            // Display error is schedule has not been saved for the selection
            if (mcdSelection != null && !MilestoneHelper_2.hasSchedule(mcdSelection.getSelectionID()))
            {
            %>
                isValidRequest = false;
                alert("The schedule must be saved before entering multiple complete dates");
            <%
            }
            %>
            if (isValidRequest) {
                var cmd = "document.forms[0].completion" + rowNo;
                if (eval(cmd)) {
                    // get task completion date entered on form
                    completionDate = eval(cmd + ".value");
                    // get default task completion date (value when form loaded)
                    defaultCompletionDate = eval(cmd + ".defaultValue");
                    // get task status
                    var statusList = eval("document.forms[0].status" + rowNo);
                    var statusChanged = false;
                    var i = 1; // skip index 0, which is no status
                    // loop through status list, determine if default status is current selected status
                    while (!statusChanged && (i < statusList.length)) {
                        if ( (statusList.options[i].defaultSelected && !statusList.options[i].selected)
                            || (!statusList.options[i].defaultSelected && statusList.options[i].selected) ) {
                            statusChanged = true;
                        }
                        i++;
                    }
                    // if status has changed since form loaded, display error
                    if (statusChanged) {
                        alert("The task status has been changed. Please Save the new status before entering multiple complete dates.");
                        isValidRequest = false;
                    }

                    // if status is N/A, display error
                    var status = "";
                    if (eval("document.forms[0].status" + rowNo)) {
                        statusIndex = eval("document.forms[0].status" + rowNo + ".selectedIndex");
                        if (statusIndex >= 0) {
                            status = eval("document.forms[0].status" + rowNo + ".options[" + statusIndex + "].text");
                        }
                    }
                    if (status == "N/A") {
                        alert("Multiple complete dates cannot be entered if the task status is N/A. "
                            + "To enter a complete date, please select and save a different status or leave it blank.");
                        isValidRequest = false;
                    }
                }
            }
        }
        return isValidRequest;
  }

  // Displays multiple complete dates editor containing value in the Schedule
  // form task completion date field (may have been changed and is different
  // than db value) and all other multiple complete dates for the task
  // ITS 281 - created
  function showMultCompleteDateEditor(rowNo, taskID, taskDesc)
  {
	// Check request for editor passes Schedule form business rules
	if (isValidMultCompleteDateEditorRequest(rowNo)) {
    	var completionDate = "";
        var cmd = "document.forms[0].completion" + rowNo;
        if (eval(cmd)) {
        	// get task completion date entered on form
            var compDtCmd = cmd + ".value";
            completionDate = eval(compDtCmd);
            // call editor with row # clicked, task id, task completion date entered;
            // only time that param init set to 1 - indicates initial call to editor;
            document.all.MultCompleteDateFrame.src = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_MULT_COMPLETE_DATES_FRAME)%>&init=1&rowNo=" + rowNo + "&taskID=" + taskID + "&schedCompDt=" + completionDate + "&taskDesc=" + taskDesc;
            // display editor layer
            layer = eval(document.all["MultCompleteDateEditorLayer"]);
            if (layer != null) {
                layer.style.visibility = "Visible";
            }
        }
    } // if valid
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
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>schedule-editor-javascript.js"></script>
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
                                                            //sOnColor
 parent.top.menuFrame.document.all.MIschedule.style.color = 6750156;
                                                              //sOffColor
 parent.top.menuFrame.document.all.MIselection.style.color = 65638;
 parent.top.menuFrame.selectedMenuItem = "schedule";

 // ITS 1046 - jo - 2005-01-03
 function validateDate(pName)
 {
    pName.value = pName.value.replace(/\s/g, ""); //strip spaces from field value
    //now validate the date
    if(!checkDate( pName, "Due Date", true ))
        pName.focus();//not valid so set focus
 }  //end function validateDate()

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


<body topmargin=0 leftmargin=0 onLoad="processLoad( 'streetDate' )">
<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>
<%=form.getElement("OrderTasksBy")%>

<!--   added hidden field to store alpha group character field -->
<input type="hidden" name="alphaGroupChr" value="">

<%
String alertMessage = (String)context.getDelivery("AlertMessage");
String sendMailStatus = (String)context.getDelivery("SendMailStatus");
%>

<table width=900 cellpadding=0 border=0>
  <tr valign="middle" align="left">
    <td width=280>
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
          onMouseOut="Javascript:mtbSave.out();"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" src="<%=inf.getImageDirectory()%>Save_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbSave = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
				<%} %>
      </div>
    </td>
<td width=85></td>
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
				<%} %>
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

<%//notepad visibility check
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{
     //check if "selections" is to be shown or "tasks" for the nav menu
       if (showTasks == false) //show "selections"
       {
 %>
  <td valign="top">
  <!-- ===============start selection navigation panel=========================== -->
  <!-- This part of the code is made an include to make it easy to manage this page.
       The the java script needed resides in this page.
       The include just gets and puts the code here as it was written here. -->
       <%@ include file="include-selection-notepad.shtml" %>
 <!-- ===============end selection navigation panel=========================== -->
  </td>
  <%  }//end if showTasks == false
    else
    {%>
  <td valign="top">
  <!-- ===============start tasks navigation panel=========================== -->
  <!-- the same as above include file-->
       <%@ include file="include-tasks-notepad.shtml" %>

 <!-- ===============end tasks navigation panel=========================== -->
  </td>
  <%}//end if else showTasks == false
   } //end notepad vis check%>


	<!-- JR - ITS #444 -->
	<% if (currentSelection.getIsDigital()) { %>
		<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" align="left" colspan="8" width="100%">
	<% } else { %>
		<td bgcolor="lavender" valign="top" align="left" colspan="8" width="100%">
	<% } %>
	<!-- JR - ITS #444 -->

	<!-- JR - ITS #580 -->
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
				<td class="label" valign=bottom rowspan=2 nowrap>Prefix/<BR>Local Prod #</td>
				<td rowspan=2 nowrap valign=bottom><%=prefixId%>&nbsp;<%=selectionNo%></td>
              </tr>

              <!-- Releasing Family, Local Prod # -->
              <tr>
				<td class="label" valign=bottom nowrap>Releasing Family</td>
				<td colspan=3 valign=bottom><%= ReleasingFamily.getName(currentSelection.getReleaseFamilyId()) %></td>

              </tr>

              <!-- Label, UPC -->
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
                <td class="label">UPC</td>
                <td nowrap>
                <% if (currentSelection != null && currentSelection.getUpc() != null)
                   {%>
                      <%= currentSelection.getUpc()%>
                 <%}%>
                </td>
              </tr>

              <!-- JR - ITS 741 -->
              <tr>
                <td class="label">Imprint</td>
                <td colspan=3>
                    <%= currentSelection.getImprint()%>
                </td>

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

              <!-- TYPE FORMAT, UPC -->
              <tr>
                <td class="label">Imprint</td>
                <td colspan=3>
                    <%= currentSelection.getImprint()%>
                </td>
                <td class="label">UPC</td>
                <td nowrap>
                <% if (currentSelection != null && currentSelection.getUpc() != null)
                   {%>
                      <%= currentSelection.getUpc()%>
                 <%}%>
                </td>
              </tr>
              <!-- GRid -->
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
              <!-- // msc item 1957 added department filter -->

              <tr>
                 <td class="label">Department</td>
                 <td align="left" nowrap colspan=3><img src="<%=inf.getImageDirectory()%>filter_access1.gif" border=0>
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
                <td></td>
              </tr>
              <!-- // msc item 1957 added department filter -->

              <tr bgcolor="white">
                <td colspan="6"></td>
              </tr>


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
                </td>
				<td colspan="3">
                    <div id="multCompleteDateLegendLayer" style="visibility:hidden">
                      	<table>
                        <tr>
                          	<td width="47%">+: Add Complete Dates</td>
                        	<td width="6%">&nbsp;</td>
                          	<td width="47%">M: Edit Complete Dates</td>
                        </tr>
		      			</table>
		    		</div>
				</td>
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
                <th>&nbsp;</th> <!-- ITS 281: multi-complete date indicator col -->
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
				// ITS 527 Added id umlTask* to identify UML tasks - used by submitSave()
				String umlSpanTag = "";
                if (isKeyTask)
                {
                  if (isLabelOwner)
                  {
                    fontColor = "class=\"nonUmlKey\"";
                  }
                  else
                  {
                    fontColor = "class=\"umlKey\"";
					umlSpanTag = "<span id='umlTask" + String.valueOf(i) + "'></span>";
                  }
                }
                else
                {
                  fontColor = "";
                }
               %>
                <td <%= fontColor%>><%=umlSpanTag%><%=task.getName()%></td>
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
                  <% // ITS 281:
                  if (form.getElement("completion" + String.valueOf(i)) != null)
                  {
						FormElement completionDate = form.getElement("completion" + String.valueOf(i));
				  %>
					<%=completionDate%>
				  <%
                  }
                  else
                  {
                  %>
                      &nbsp;
                  <%}%>
                </td>
                <td>
                <% // ITS 281: if task is allowed to have multiple complete dates, enable multiple
                   // complete dates editor link and show legend for indicators; id anchorMultCompleteDate*
                   // identifies tasks that allow multiple complete dates - used by submitSave();
                   if (task.getAllowMultCompleteDatesFlag())
                   {
                %>
                        <script language = "javascript">
                        layer = eval(document.all["multCompleteDateLegendLayer"]);
                        if (layer != null) {
                            layer.style.visibility = "Visible";
                        }
                        </script>
                        <a id="<%="anchorMultCompleteDate" + String.valueOf(i)%>" href="#" onClick="showMultCompleteDateEditor(<%=String.valueOf(i)%>,<%=task.getTaskID()%>,'<%=task.getName()%>')">
                  <%    // display mulitple complete dates indicator if multiple complete dates exist
                        if (task.getMultCompleteDates() != null && task.getMultCompleteDates().size() > 0)
                        { %>
                            <%=MilestoneConstants.MULT_COMPLETE_DATES_IND%>
                  <%    }
                        // display indicator that there is not multiple complete dates yet
                        else
                        { %>
                            <%=MilestoneConstants.NOT_MULT_COMPLETE_DATES_IND%>
                  <%    }
                   }
                   else
                   {
                %>
                    &nbsp;
                <% }
                %>
                </a>
                </td>
                <td>
                <%= form.getElement("comments" + String.valueOf(i))%>
                <% String comment = "";
                   comment = form.getRenderableValue("comments" + String.valueOf(i));
                   String onClickValue = "document.all.commentLayer.style.visibility='hidden';showDetailData( document.all.comments" + String.valueOf(i) + ", 'commentLayer', 'comments' );";

                   if (comment != null && comment.length() > 0 && !comment.equals(""))
                   {%>
                   <img  title="Task Comments" src="<%=inf.getImageDirectory()%>afile.gif" onClick="<%= onClickValue%>">
               <%}
                   else
                   {%>
                   <img  title="Task Comments" src="<%=inf.getImageDirectory()%>file.gif" onClick="<%= onClickValue%>">
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
                <td> <!-- ITS 281: multiple complete dates column - not editable for UML tasks -->
                  &nbsp;
		</td>
                <td>
                   <%= form.getElement("comments" + String.valueOf(i))%>
                   <%String comment = "";
                   comment = form.getRenderableValue("comments" + String.valueOf(i));
                   String onClickValue = "document.all.viewCommentLayer.style.visibility='hidden';showDetailData( document.all.comments" + String.valueOf(i) + ", 'viewCommentLayer', 'viewComments' );";

                   if (comment != null && !comment.equals(""))
                   { %>
                   <img  title="Task Comments" src="<%=inf.getImageDirectory()%>afile.gif" onClick="<%= onClickValue%>">
                 <%}
                   else
                   {%>
                   <img  title="Task Comments" src="<%=inf.getImageDirectory()%>file.gif" onClick="<%= onClickValue%>">
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

             <table border=0 width="100%" width="616" >

               <tr>
                 <td align="center" >
                 <div id="saveDivBottom">
                 <%if(saveVisible)
                  {%>
                   <a href="JavaScript:submitSave('Save2')"
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
                 <%}%>
                 </div>
                 </td>
               </tr>
             </table>
           </td>
         </tr>
          <tr>
            <td align="left" valign="bottom">
            <b>Template:</b>&nbsp;<%=templateName%>
            </td>
          </tr>
          <tr>
          <table width="616" border=0>
          <tr bgcolor="white" >
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

            <% //ITS 42811
            if (form.getElement("lastLegacyUpdateDate") != null && form.getRenderableValue("lastLegacyUpdateDate").length() > 0)
            {%>
                  <td class="label">Last Legacy Update: </td>
                  <td><%= form.getRenderableValue("lastLegacyUpdateDate")%></td>
            <%}%>

	  </tr>
          <tr bgcolor="white" >
            <td colspan="6"></td>
          </tr>
        </table>
     </td>
   </tr>
 </table>


 <!-- DIV definitions -->
<!-- start search div -->
<%@ include file="selection-search-elements.shtml"%>
<!-- end search div -->

<%
// Multiple Complete Dates Editor window
// This layer is intended to appear fixed over the Notepad area rather than relative to the
// the selected task so that users never have to scroll to see the entire layer;
// ITS 281 - created
%>
<div class="search" id="MultCompleteDateEditorLayer" style="position:absolute;visibility:hidden;width:200px;height:210px;z-index:10;left:85px;top:90px;">
    <IFRAME HEIGHT="210px" width="100%" ID="MultCompleteDateFrame" name="MultCompleteDateFrame" FRAMEBORDER="0" SCROLLING="NO" >
    </IFRAME>
</div>

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
						<%if(saveVisible)
        		{%>
            <input type="button" name="saveButton" id="saveButton" value="Save Comments"  onClick="Javascript:saveDetailData( 'commentLayer', 'comments')">&nbsp;&nbsp;
						<%}%>
            <input type="button" name="closeButton" id="closeButton" value="Cancel" onClick="Javascript:toggle( 'commentLayer', 'comments' )">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<div id="viewCommentLayer" class="search" style="position:absolute; width:250px; height:80px; z-index:3; left: 350px; top: 95px; visibility:hidden;">
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
		        	 <%if(saveCommentVisible)
                {%>
		        	<input type="button" name="saveButton" value="Save Comments" onClick="JavaScript:submitSave('Save')">&nbsp;&nbsp;
		        <%}%>
              <input type="button" name="closeButton" id="closeButtom" value="Cancel" onClick="Javascript:toggle( 'releaseCommentLayer', 'releaseComment' )">
		        </td>
            <td>
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
</div>

<div id="vendorLayer" class="search" style="position:absolute; visibility:hidden; width:250px; height:80px; z-index:3; left: 250px; top: 95px;">
  <table width="100%" border=1 cellspacing=0 cellpadding=1>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td class="label" >Vendor</td>
          </tr>
          <tr>
            <td>
              <input name="vendorText" size=50 maxlength="50"  onBlur="checkField( this )">
            </td>
          <tr>
          <tr>
            <td>
         <% if(saveVisible)
            {%>
              <input type="button" name="saveButton" value="Save"  onClick="saveDetailData( 'vendorLayer', 'vendorText', 'Save Release Schedule' )">&nbsp;&nbsp;
          <%}%>
              <input type="button" name="closeButton" value="Cancel"  onClick="toggle( 'vendorLayer', 'vendorText' )">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>


<div id="recalcLayer" style="position:absolute; visibility:hidden; width:400px; height:250px; z-index:10; left: 230px; top: 300px;">
  <table BGCOLOR="wheat" width="100%" border=2 cellspacing=0 cellpadding=1>
    <tr>
      <td>
        <table width="100%">
          <tr bgcolor="#C0C0C0">
            <td align=center>
            <font size=2 color="black"><b>Alert</b></font>
            </td>
          <tr>
          <tr height=35>
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

<% //start send mail sucess message %>
<div class="search" id="SuccessLayer" style="visibility:hidden;left:5px;top:60px;">
<table width="300" border="1" cellspacing="0" cellpadding="5" bgcolor="#BFFABE">
<tr><td>
	<table width="100%">
	<tr>
		<th colspan="2"  align="center"><b>Success</b></th>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<%=alertMessage%>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<input type="button" name="success" value="OK" onClick="JavaScript:toggle( 'SuccessLayer', '' )" style="width:70px">
		</td>
	</tr>
	</table>
</td></tr>
</table>
</div>
<% //end send mail sucess message %>

<% //start send mail sucess message %>
<div class="search" id="FailureLayer" style="visibility:hidden;left:5px;top:60px;">
<table width="300" border="1" cellspacing="0" cellpadding="5" bgcolor="#FFA4AD">
<tr><td>
	<table width="100%">
	<tr>
		<th colspan="2" align="center"><b>FAILURE</b></th>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<%=alertMessage%>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<input type="button" name="failure" value="OK" onClick="JavaScript:toggle( 'FailureLayer', '' )" style="width:70px">
		</td>
	</tr>
	</table>
</td></tr>
</table>
</div>
<% //end send mail sucess message %>


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

<%
if (sendMailStatus != null)
{
  if (sendMailStatus.equalsIgnoreCase("1"))
  {
    out.println("<SCRIPT>");
    out.println("toggle( 'SuccessLayer', '' );");
    out.println("document.all.success.focus();");
    out.println("</SCRIPT>");
  }
  else
  {
    out.println("<SCRIPT>");
    out.println("toggle( 'FailureLayer', '' );");
    out.println("document.all.failure.focus();");
    out.println("</SCRIPT>");
  }
}
else
{
  if (alertMessage != null)
  {
%>
  <script language="JavaScript">
    alert('<%=alertMessage%>');
  </script>
<%
  }
}
%>

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
