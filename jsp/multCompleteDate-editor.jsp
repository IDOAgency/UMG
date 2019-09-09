<%
 /*
  * Multiple Completion Date Editor
  * This page contains the complete date rows and is displayed within an Iframe.
  *
  * Development history:
  *   2003-12-17 - lg - ITS 281 - Created based on multSelection-editor.jsp
  *   2004-04-15 - lg - ITS 974 - Automatically save schedule form if Save in Multiple
  *              Complete Date editor changes the completion date on the Schedule form;
  *   2005-02-03 - jo - ITS 1046 - Added validateDate to remove spaces and validate complete dates
  */
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Multiple Complete Date Editor screen";

%>

<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">

 <!-- These are js include files holding the global java script functions -->
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>

<script language="javascript">

// Checks for valid completion date field;
// Called onBlur of complete date field;
// Created based on schedule-editor-javascript.js checkField();
function checkField( pField )
{
    var bReturn = true;

    if( pField.name.substring( 0, 'completeDate'.length ) == "completeDate" )
    {
        var now = new Date();
        var nowTime = now.getTime() ;
        var nextWeek = nowTime + 604800000;
        var testResult = checkDate( pField, "Completion Date", true );
        bReturn = bReturn && testResult ;
        if (testResult)
        {
            var compTimeDate = new Date(pField.value);
            var year = compTimeDate.getFullYear();
            if(year < 1970)
            {
                year = year + 100 ;compTimeDate.setYear(year);
            }
            var compTime  = compTimeDate.getTime() ;
            if (compTime > nowTime)
            {
                alert("The complete date is beyond the current day.");
                bReturn =  false;
            }
            else
            {
                bReturn = bReturn && true;
            }
        }
    }

    if( !bReturn )
    {
        pField.focus();
    }

    return bReturn;
}

// Call the save method for the Schedule form
// ITS 974 - created
function SaveScheduleForm()
{
	if (parent.parent && parent.parent.top && parent.parent.top.bottomFrame)
	{
		try
		{
			parent.parent.top.bottomFrame.submitSave();
		}
		catch (e)
		{
			alert("There was a problem updating the Schedule. Please retry saving any changes.")
		}
	}
}

function submitDelete( dateIndex )
{
  document.forms[0].dateIndex.value = dateIndex;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SCHEDULE_MULT_COMPLETE_DATES_DELETE%>";
  document.forms[0].submit();
}

function submitSave()
{
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SCHEDULE_MULT_COMPLETE_DATES_SAVE%>";
  document.forms[0].submit();
}

function submitAdd()
{
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SCHEDULE_MULT_COMPLETE_DATES_ADD%>";
  document.forms[0].submit();
}

function submitCancel()
{
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_SCHEDULE_MULT_COMPLETE_DATES_CANCEL%>";
  document.forms[0].submit();
}

// 2005-02-03 - jo - ITS 1046
function validateDate(pName)
{
    pName.value = pName.value.replace(/\s/g, ""); //strip spaces from field value
    //now validate the date
    if(!checkDate( pName, "Complete Date", true ))
        pName.focus();//not valid so set focus
}  //end function validateDate()

</script>

</head>

<body bgcolor="wheat" topmargin=0 leftmargin=0 >

<%= form.renderStart()%>
<%=form.getElement("cmd")%>
<input type="hidden" name="rowNo" value="<%=context.getIntDelivery("rowNo")%>">
<input type="hidden" name="taskID" value="<%=context.getIntDelivery("taskID")%>">
<input type="hidden" name="schedCompDt" value="<%=(String)context.getDelivery("schedCompDt")%>">
<input type="hidden" name="dateIndex" value="-1">
<input type="hidden" name="rows" value="<%= context.getIntDelivery("rows")%>">

<table width="186px" height="100%" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat" valign="top" >
<td>
    <table width="100%" border="1" cellspacing="0" cellpadding="1">
        <tr class=label valign="bottom" height="25" bgcolor="#CCCCCC">
            <td class=label align="left" width="20">del</td>
            <td class=label align="left">Complete Date</td>
		</tr>
    <%
		// Display all the form complete date rows
        int rows = context.getIntDelivery("rows");
        if (rows > 0) {
            for (int i = 0; i < rows; i++)
            {
    %>
        		<tr height="30">
   	     		<td align="left"><a href="javascript:submitDelete(<%=i%>)" >del</a></td>
    	     	<td align="left"><%=form.getElement("completeDate" + i)%></td>
        		</tr>
    <%
            }
        }
    %>
    </table>
</td>
</tr>
</table>


<%=form.renderEnd()%>

<script language="javascript">

// ----- MAIN EXECUTION ---------------------------------------------------------------------------
// Attempt to set focus to first multiple complete date field every time this form loads
try {
	document.forms[0].completeDate0.focus();
}
catch (e) {
	// do nothing
}

// If there is a save in the Multiple Complete Date editor, set the task completion
// date in the Schedule form and then submit the Schedule form to be saved; Without
// updating the completion date on the Schedule form, the old value would overwrite
// any new value when the Schedule form save is executed;
//
// Note: There used to code here to determine whether the Schedule form needed to be refreshed,
// saved, or left alone based on what changed but the logic became overly complicated. For
// example, the code needs to consider the completion date's default value (value when
// form is loaded), actual value (current, possibly changed value), and the completion date
// coming from the multiple completion date editor. In addition, the multiple complete
// dates indicator may need to be updating AND there are business rules based on the status
// and the due date; Doing a Save every time made it simpler and wouldn't miss a scenario;
//
// ITS 974 - The Schedule form needs to be saved, not just refreshed, so not to lose any
//           other changes the user might have made on the Schedule form;
try
{
	if (document.forms[0].cmd.value == "<%=MilestoneConstants.CMD_SCHEDULE_MULT_COMPLETE_DATES_SAVE%>")
	{
		// string representing reference to Schedule form
        var scheduleFormStr = "parent.parent.top.bottomFrame.document.ScheduleForm";
		// current task completion date
	    var curCompDt = "<%=(String)context.getDelivery("curCompDt")%>";
		// task row number on Schedule form
		var rowNo = <%=context.getIntDelivery("rowNo")%>;
		// string representing command to update task completion date on Schedule form
        var completionDateSetValueCmd =  scheduleFormStr + ".completion" + rowNo + ".value = '" + curCompDt + "'";
		// execute command
        eval(completionDateSetValueCmd);
        // save schedule
		SaveScheduleForm();
	}
}
catch (e)
{
	// do nothing
}

// ----- FUNCTIONS --------------------------------------------------------------------------------

// If Schedule document exists relative to this page, returns a
// reference to the Schedule document
function GetScheduleDocRef()
{
    var ref = null;
    if (parent.parent
        && parent.parent.top
        && parent.parent.top.bottomFrame
        && parent.parent.top.bottomFrame.document ) {
        ref = parent.parent.top.bottomFrame.document;
    }
    return ref;
}
</script>

<%@ include file="include-bottom-html.shtml"%>
