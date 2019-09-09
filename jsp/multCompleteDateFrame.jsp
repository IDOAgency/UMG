<%
 /**
  * Multiple Completion Date Editor Frame
  * This page contains:
  * - the Iframe for the page that contains the complete date rows and
  * - the Save, Cancel, Add buttons
  *
  * Development history:
  *   2003-12-17 - lg - ITS 281 - Created based on multSelectionFrame.jsp
  *
  */
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Multiple Complete Date screen";

%>

<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">

<script language="javascript">
// Validates multiple complete date fields and returns true if no errors and false
// otherwise; Called on Save in multiple complete date editor;
// Created based on checkFields in multSelectionFrame.jsp
function checkFieldsValid()
{
    var appError = false;
    if (MultCompleteDateEditor && MultCompleteDateEditor.multCompleteDateForm) {
        var formRef = MultCompleteDateEditor.multCompleteDateForm;
        var dataError = false;
		var formElemRef = null;
		var atLeastOneDt = false;
        var i = 0;
		// Check all complete date form fields are non-empty
        while (!dataError && i < formRef.elements.length) {
	    	formElemRef = formRef.elements[i];
	    	if (formElemRef.name.substr(0, 12) == "completeDate") {
				atLeastOneDt = true;
				if (formElemRef.value.length == 0) {
		    		alert("Complete date is required. Please enter a value.");
		    		dataError = true;
				}
	    	}
            i++;
        }

		var schedFormRefStr = GetScheduleFormRefString();
		var rowNo = <%=context.getIntDelivery("rowNo")%>;
        var statusVal = eval(schedFormRefStr + ".status" + rowNo + ".value");

		if (!dataError && statusVal == 'Done' && !atLeastOneDt) {
			alert("Complete date is required if the status is Done.");
			dataError = true;
		}

        if (dataError) {
			try {
	    		formElemRef.style.backgroundColor = "mistyrose";
	    		formElemRef.focus();
			}
			catch (e) {}
    	}
        else {
            formElemRef.style.backgroundColor = "white";
        }
    }
    else {
		appError = true;
    }

    if (appError) {
		alert("An error occurred while attempting to validate the complete dates entered.");
    }

    if (appError || dataError) {
		return false;
    }
    else {
		return true;
    }
}

// If Schedule form exists relative to this page, returns a string containing a
// reference to the Schedule form for use with the eval function
function GetScheduleFormRefString()
{
    var refStr = "";
    if (parent.top
        && parent.top.bottomFrame
        && parent.top.bottomFrame.document
        && parent.top.bottomFrame.document.ScheduleForm) {
        refStr = "parent.top.bottomFrame.document.ScheduleForm";
    }
    return refStr;
}

// Adds a new complete date in the multiple complete date editor
function submitAdd()
{
	parent.top.bottomFrame.MultCompleteDateFrame.MultCompleteDateEditor.submitAdd();
}

// Saves any changes in the multiple complete date editor
function submitSave()
{
    if( checkFieldsValid() ) {
        parent.top.bottomFrame.MultCompleteDateFrame.MultCompleteDateEditor.submitSave();
        hide();
    }
}

// Cancel any changes in multiple complete date editor
function submitCancel()
{
  parent.top.bottomFrame.MultCompleteDateFrame.MultCompleteDateEditor.submitCancel();
  hide();
}

// Hide multiple complete date editor
function hide()
{
  layer = eval(parent.top.bottomFrame.document.all["MultCompleteDateEditorLayer"]);
  layer.style.visibility = "Hidden";
}

</script>

<% //-- include files containing common javascript functions -- %>
</head>

<body topmargin=0 leftmargin=0 >
<table bgcolor="wheat" width="100%" height="100%" border="1" cellspacing="0" cellpadding="2">
<tr>
<td class="label" align="center"><%=(String)context.getDelivery("taskDesc")%></td>
</tr>

<tr valign="top">
<td>
  <table bgcolor="wheat" width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
   <td align="left">
  <%
	 // init is set to 1 to indicate first call to the editor (i.e. launched from Schedule form)
	 String init = (String)context.getDelivery("init");
	 // row number of the selected task on the Schedule form
     int rowNo = context.getIntDelivery("rowNo");
	 // task id that multiple complete dates belong to
	 int taskID = context.getIntDelivery("taskID");
	 // task completion date
	 String schedCompDt = (String)context.getDelivery("schedCompDt");
  %>
  <IFRAME STYLE="background-color: wheat" HEIGHT="130" width="100%" ID="MultCompleteDateEditor" Name="MultCompleteDateEditor" FRAMEBORDER=0 SCROLLING=auto
    SRC="<%= inf.getServletCmdURL(MilestoneConstants.CMD_SCHEDULE_MULT_COMPLETE_DATES_EDITOR)%>&rowNo=<%=rowNo%>&taskID=<%=taskID%>&schedCompDt=<%=schedCompDt%>&init=<%=init%>">
  </IFRAME>
   </td>
  </tr>

  <tr valign="top">
  <td>
  <%
  boolean saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
  if (saveVisible)
  {
  %>
    <input  id=SaveIFrame type="button" value="Save" onclick="javascript:submitSave()">&nbsp;&nbsp;
  <%
  }
  %>

  <input type="button" name="Cancel" value="Cancel" onclick="javascript:submitCancel()">&nbsp;&nbsp;

  <%
  if (saveVisible)
  {
  %>
    <input type="button" value="Add" onclick="javascript:submitAdd()">&nbsp;&nbsp;
  <%
  }
  %>
  </td>
  </tr>

  </table>

</td>
</tr>

</table>

<%@ include file="include-bottom-html.shtml"%>
