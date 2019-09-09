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
<%@ include file="callHelp.js"%>

<%
  boolean saveVisible = false;
  boolean copyVisible = false;
  boolean deleteVisible = false;
  boolean newVisible = false;
  boolean isAlert = false;
  boolean newOrCopy = false;
  boolean isParent = false;
  boolean isMac = false;

  newOrCopy = ((String)context.getDelivery("new-or-copy")).equals("true");
  saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
  copyVisible = ((String)context.getDelivery("copyVisible")).equals("true");
  deleteVisible = ((String)context.getDelivery("deleteVisible")).equals("true");
  newVisible = ((String)context.getDelivery("newVisible")).equals("true");
  isParent = ((String)context.getDelivery("is-parent")).equals("true");

  //System.out.println("IsGDRSactive...." + MilestoneConstants.IsGDRSactive);
  
   // release upgrade 8.1 hide buttons
   if(MilestoneConstants.IsGDRSactive)
   {
     newOrCopy = false;
     saveVisible = false;
     copyVisible = false;
     deleteVisible = false;
     newVisible = false;
     isParent = false;
   }
  // release upgrade 8.1 if not an ADMIN hide buttons

  //Get the selection object
  Selection currentSelection = (Selection)context.getSessionValue("Selection");
  //Selection currentSelection = (Selection)MilestoneHelper.getNotepadFromSession(MilestoneConstants.NOTEPAD_SELECTION, context).getSelected();

  String familyName = "";
  String otherContact= "";
  String retailCode = "";
  //String price = "";
  String price = (String)context.getDelivery("price");
  String releaseWeek = (String)context.getDelivery("releaseWeek");
  String numberOfUnits  = (String)context.getDelivery("number-of-units");
  String alertText = "";
  String oldSelectionNumber = "''";
  String alertMessage = (String)context.getDelivery("AlertMessage");
  String sendMailStatus = (String)context.getDelivery("SendMailStatus");

  // JP 9/9/03
  String diffMessage = (String)context.getDelivery("DiffMessage");
  Selection diffSelection = (Selection)context.getDelivery("updatedSelection");

  //Used by include-selection-notepad
  String editorName = MilestoneConstants.CMD_SELECTION_EDITOR;

  if(context.getDelivery("old-selection-no") != null)
    oldSelectionNumber = "'" + (String)context.getDelivery("old-selection-no") + "'";

  if(context.getDelivery("alert-box") != null)
  {
    alertText = (String)context.getDelivery("alert-box") ;
    isAlert = true;
  }

  releaseWeek = (String)context.getDelivery("releaseWeek");

	// check to see if selection object is not null and to see if the command is not
	// selection-edit-new
  if(!form.getRenderableValue("cmd").equals("selection-edit-new"))
  {

    if(currentSelection.getPriceCode() != null)
      retailCode =currentSelection.getPriceCode().getRetailCode();

    if (currentSelection.getFamily() != null)
    {
			familyName = currentSelection.getFamily().getName();
		}
  }

	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Selection Screen";

	// getting the user object from the session
	User user = (User)context.getSessionValue("user");

	//Are completion dates empty
	Boolean isOkToClose = new Boolean(false);

	if (context.getDelivery("isOkToClose") != null)
	  isOkToClose = (Boolean)context.getDelivery("isOkToClose");

%>

<% //-- includes the top header -- %>
<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include selection-editor-javascript.js
  var sort = "<%= MilestoneConstants.CMD_SELECTION_SORT%>";
  var sortGroup = "<%= MilestoneConstants.CMD_SELECTION_GROUP%>";

  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_SELECTION%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_EDIT_DELETE) %>";
  var copyCommand = "selection-edit-copy-digital";
  var copyDigitalCommand = "selection-edit-copy-digital";
// <%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_EDIT_COPY) %>;
  var save = "<%= MilestoneConstants.CMD_SELECTION_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_SELECTION_EDIT_SAVE%>";
  var saveCopy = "<%= MilestoneConstants.CMD_SELECTION_EDIT_SAVE%>";
  var editNew = "selection-edit-new-digital"; //<%= MilestoneConstants.CMD_SELECTION_EDIT_NEW%>;
  var search = "<%= MilestoneConstants.CMD_SELECTION_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";
	var alertText = "<%=alertText%>";
  var isAlert = <%=isAlert%>;
  var newOrCopy = <%= newOrCopy%>;
  var oldSelectionNumber = <%=oldSelectionNumber%>;
  var isParent = <%= isParent%>;
  var isOkToClose = <%=isOkToClose%>;
  var imageDir = "<%= inf.getImageDirectory()%>";
  var navCom = navigator.platform;
  if( navCom.toUpperCase() == "MACPPC" || navCom.substr(0,3).toUpperCase() == "MAC" ) {
   // <% isMac = true; %>
  }

// JR - ITS 832
function ReleasingFamilyChange(ctrl){

  // if object doesnot exist, return
  if(!document.all.Save)
    return;

  /*
  if (ctrl.value == "Canada") {
    document.all.Save.disabled = true;
  } else {
    document.all.Save.disabled = false;
  }
  */
  return;
}

function processLoad(pFocusField)
{
  focusField(pFocusField);
}

function helpConext()
  {
    callHelp('Help/','Selection.htm');
    mtbHelp.reset();
  }//end function helpContext

function showImpactDate()
{
        // msc - required for the MAC computer
//        var navCom = navigator.platform;
//        if( navCom.toUpperCase() == "MACPPC" || navCom.substr(0,3).toUpperCase() == "MAC" ) {       // impact date validation
//            document.all.ImpactFrame.src = "<%= inf.getServletCmdURL("selection-impactDate-frame")%>"
//        }
        showWaitMsg();  // mc its 966 show please wait message
        document.all.ImpactFrame.src = "<%= inf.getServletCmdURL("selection-impactDate-frame")%>"
        layer = eval(document.all["ImpactLayer"]);
	layer.style.visibility = "Visible";
} //end

function showMultSelections()
{
    if (document.all.MultSelectionFrame)
    {
        showWaitMsg();  // mc its 966 show please wait message
        document.all.MultSelectionFrame.src = "<%= inf.getServletCmdURL("selection-multSelection-frame")%>"
        layer = eval(document.all["MultSelectionLayer"]);
	layer.style.visibility = "Visible";
    }
} //end

function showMultOtherContacts()
{
    if (document.all.MultOtherContactFrame)
    {
        showWaitMsg();  // mc its 966 show please wait message
        document.all.MultOtherContactFrame.src = "<%= inf.getServletCmdURL("selection-multOtherContact-frame")%>"
        layer = eval(document.all["MultOtherContactLayer"]);
	layer.style.visibility = "Visible";
    }
} //end


function showReviseProjectSearch()
{
  // Jp Digital AFE
  if(document.all.newSelectionDiv)
    document.all.newSelectionDiv.style.visibility = 'hidden';
  if(document.all.newDiv)
    document.all.newDiv.style.visibility = 'hidden';
  document.all.reviseDiv.style.visibility = 'hidden';
  document.all.copyDiv.style.visibility = 'hidden';
  document.all.deleteDiv.style.visibility = 'hidden';
  document.all.saveDiv.style.visibility = 'hidden';
  document.all.saveDivBottom.style.visibility = 'hidden';
  showWaitMsg();  // mc its 966 show please wait message
  document.all.ProjectSearchFrame.src = "<%= inf.getServletCmdURL("project-search-revise")%>"
}

// JP Digital AFE
function getReleasingFamilyList()
{
  document.all.ReleasingFamilyFrame.src = "<%= inf.getServletCmdURL("selection-get-releasing-families")%>&eID=" + document.all.environment.value;
}

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

  document.forms[0].OrderBy.value = colNo;
  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = sortGroup;
  document.forms[0].submit();
} //end function submitGroup()

//ITS 1045 - jo - 1/25/2005
function removeSpaces(field) {
    var x = field.value;
    field.value = x.replace(/^\s+/g, '').replace(/\s+$/g, '')
} //end function removeSpaces()

</script>

<% //-- include js files holding javascript functions --%>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>digital-selection-editor-javascript.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-auto-select.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-upc-ssg-sgc.js"></script>


<% //-- Java script functions specific to this page --%>
<script type="text/javascript" language="javascript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_SELECTION_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

</script>

<% //-- include calendar js file --%>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>

<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>


<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<!--body topmargin="0" leftmargin="0" onLoad="processLoad('streetDate');filterScheduleType(false);"-->
<% if( form.getRenderableValue("cmd").equals("selection-edit-new") ) { %>
<body topmargin="0" leftmargin="0" onLoad="filterScheduleType(true)" bgColor="#CCCCCC">
<%} else {%>
<body topmargin="0" leftmargin="0" bgColor="#CCCCCC" onLoad="">
<%}%>

<%= form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>
<%=form.getElement("isFocus")%>
<%=form.getElement("statusHidVal")%>
<%=form.getElement("hidTitleId")%>
<%=form.getElement("generateSelection")%>

<!-- msc 12/13/01 -->
<input type="hidden" name="alphaGroupChr" value="">

<!-- JP Digital AFE -->
<input type="hidden" name="isDigital" value="1">


<table width="768" cellpadding="0" cellspacing=0 border="0">
  <tr valign="center" align="left">
    <td rowspan=2 width=2><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width=2></td>
    <td width="265">
      <!-- This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here. -->
      <%@ include file="include-newSelection.shtml" %>
    </td>
    <td rowspan=2 width=10><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width=10></td>
    <td><span class="title">Selection</span></td>

    <!-- buttons -->
    <td width="85">
			<div align="center" id="saveDiv">
        <%if(saveVisible)
          {
            String command = form.getStringValue("cmd");%>
        <a href="JavaScript:submitSave('Save');"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" src="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
        </a>
        <script type="text/javascript" language="javascript">
          var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', "JavaScript:submitSave( 'Save&' )", 66, 14);
        </script>
        <%}%>
      </div>
		</td>


    <!-- revise -->
    <td width="85">
    <div align="center" id="reviseDiv">
        <%if(copyVisible)
        {%>
        <a href="JavaScript:showReviseProjectSearch()"
          onMouseOver="Javascript:mtbRevise.over();return true;"
          onMouseOut="Javascript:mtbRevise.out()"
          onClick="Javascript:mtbRevise.click(); return true;">
          <img name="Revise" id="Revise" src="<%= inf.getImageDirectory() %>Revise_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
        </a>
        <script type="text/javascript" language="javascript">
          var mtbRevise = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Revise', "JavaScript:submitRevise()", 66, 14);
        </script>
        <%}%>
      </div>
      </td>

    <td width="85">
			<div align="center" id="copyDiv">
        <%if(copyVisible)
        {%>
        <a href="JavaScript:submitCopyDigital()"
          onMouseOver="Javascript:mtbCopy.over();return true;"
          onMouseOut="Javascript:mtbCopy.out()"
          onClick="Javascript:mtbCopy.click(); return true;">
          <img name="Copy" id="Copy" src="<%= inf.getImageDirectory() %>Copy_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
        </a>
        <script type="text/javascript" language="javascript">
          var mtbCopy = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Copy', "JavaScript:submitCopyDigital()", 66, 14);
        </script>
        <%}%>
      </div>
		</td>
    <td width="85">
      <div align="center" id="deleteDiv">
        <%if(deleteVisible)
        {%>
        <a href ="JavaScript:submitDelete('Delete')"
          onMouseOver="Javascript:mtbDelete.over();return true;"
          onMouseOut="Javascript:mtbDelete.out()"
          onClick="Javascript:mtbDelete.click(); return true;">
          <img name="Delete" id="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width="66" height="14" border="0" hspace="0" vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="javascript">
           var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', "JavaScript:submitDelete('Delete')", 66, 14);
        </script>
        <%}%>
      </div>
    </td>
    <td>
      <div align="center" id="helpDiv">
        <a href ="JavaScript:helpConext()"
          onMouseOver="Javascript:mtbHelp.over();return true;"
          onMouseOut="Javascript:mtbHelp.out()"
          onClick="Javascript:mtbHelp.click(); return true;">
          <img name="Help" id="Help" src="<%=inf.getImageDirectory()%>Help_On.gif" width="27" height="14"  border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
           var mtbHelp = new ToggleButton(document, '<%=inf.getImageDirectory()%>', 'Help', "JavaScript:helpConext()",66, 14);
        </script>
      </div>

    </td>

  </tr>
  <% if (formValidation != null)
     {%>
  <tr><td colspan=8 height=10>&nbsp;</td></tr>
      <% Vector instructions = formValidation.getInstructions();
         for (int i = 0; i < instructions.size(); i++)
         {%>
 <tr>
   <td></td>
   <td></td>
   <td colspan=6 align="left">
           <%=  instructions.get(i)%>
   </td>
 </tr>
       <%}%>
 <tr><td colspan=8 height=10>&nbsp;</td></tr>
    <%}%>
  <tr>

  <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
    <td valign="top">
  <%  //===============start selection navigation panel=========================== %>
  <%  /* This part of the code is made an include to make it easy to manage this page.
         The the java script needed resides in this page.
         The include just gets and puts the code here as it was written here. */%>

       <%@ include file="include-selection-notepad.shtml" %>

    </td>
  <% //===============end selection navigation panel=========================== %>
  <% } //end if notepadWidth == 0 %>
	<% //start selection form %>
	<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" align="left" colspan=8 width="100%">

  <% //MAIN TABLE%>
  <table width="100%" bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" border="0" cellspacing=0 cellpadding=0 style="padding-bottom:1px;padding-top:1px;padding-right:2px;padding-left:2px">
  <!-- JR New Selection Screen Layout PNR driven 12/03/02 -->
	<!-- SECTION I -->
	<tr>
		<td><b>First Name&nbsp;&nbsp;</td>
		<td><%= form.getElement("artistFirstName") %></td>
		<td width=10>&nbsp</td>
		<td colspan=2 class="label" nowrap><%= form.getElement("newBundle") %></td>
	</tr>
	<tr>
		<td nowrap><b><font color=red>*</font> Last/Group Name&nbsp;&nbsp;</td>
		<td><%= form.getElement("artistLastName") %></td>
		<td>&nbsp</td>
                <td colspan=2> <%= form.getElement("pdIndicator") %><b>P&D / Dist.</b>&nbsp;&nbsp;&nbsp<%= form.getElement("priority") %><b>Priority</b></td>
	</tr>
	<tr>
		<td><b><font color=red>*</font> Bundle Title</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td colspan="4"><%= form.getElement("title") %></td>
	</tr>
	<tr bgcolor="white"><td colspan="5"></td></tr>

	<!-- SECTION II -->
	<tr>
		<td nowrap><b><b><font color=red>*</font>&nbsp;Digital Release Date</b></td>
		<td nowrap colspan=2>
			<%= form.getElement("digitalDate") %>
      <% if (form.getRenderableValue("dayType") != null)
         {%>
         <SPAN class=indicator>
         <%= form.getRenderableValue("dayType")%>
         </SPAN>
       <%}%>

			<%// this checks to see what image to display
				// if there are existing comments: display afile.gif
				// if no comments exist: display file.gif
				String comments = "";
				comments = form.getRenderableValue("comments");
				if ( comments != null && !comments.equals("") )
				{
			%>
			<img name="commentsImage" title="Selection Comments" src="<%= inf.getImageDirectory() %>afile.gif" border="0" onClick="toggle( 'commentLayer', 'comments' )">
			<%}
			  else
			 {
			%>
			<img name="commentsImage" title="Selection Comments" src="<%= inf.getImageDirectory() %>file.gif" border="0" onClick="toggle( 'commentLayer', 'comments' )">
			<%}%>
                </td>
                <td colspan=1>
			<b><font color=red>*</font>&nbsp;Status</b>
		</td>
                <td>
                  <%= form.getElement("status") %>
                </td>
	</tr>
	<tr>
		<td><b>Physical Street Date</b></td>
		<td nowrap><%= form.getElement("streetDate")%></td>
		<td>
		</td>
                <td>
		<%
    String linkStyle = "";
		if(!newOrCopy || newOrCopy) {  // msc, was !newOrCopy Only
                  if (currentSelection != null && currentSelection.getImpactDates() != null && currentSelection.getImpactDates().size() > 0)
		  {
		    linkStyle = "FONT-SIZE: 8pt;COLOR: purple;FONT-FAMILY: Arial,Helvetica;TEXT-DECORATION: underline";
		  }
		  else
		  {
		    linkStyle = "FONT-SIZE: 8pt;COLOR: black;FONT-FAMILY: Arial,Helvetica;TEXT-DECORATION: underline";
		  }
		%>

		<b><a id="anchorImpactDate" style="<%=linkStyle%>" href="javascript:showImpactDate()">
		<%} else {
			 linkStyle = "FONT-SIZE: 8pt;COLOR: black;FONT-FAMILY: Arial,Helvetica";
		%>

		<b>
		<% } %>
		Radio Impact Date
		</a></b>

		</td>
		<td><%= form.getElement("impactdate") %>&nbsp;&nbsp;</td>

	</tr>
	<tr bgcolor="white"><td colspan="6"></td></tr>

	<!-- SECTION III -->
	<tr>
	<td><b><font color=red>*</font> Releasing Family</b></td>
	<td><%=form.getElement("releasingFamily")%></td>
	<td>&nbsp</td>
	<td nowrap><b><font color=red>*</font> Oper Company</b></td>
        <td>
         <%= ((FormHidden)form.getElement("opercompany")).render() %><span id="opercompanydiv"><%= form.getElement("opercompany").getDisplayName() %></span>
        </td>

	</tr>
	<tr>
	<td><b><font color=red>*</font> Environment</td>
	<td align="left"><%= ((FormHidden)form.getElement("environment")).render() %>
<span id="environmentdiv"><%= form.getElement("environment").getDisplayName() %></td></span>
	<td>&nbsp</td>
	<td><b><font color=red>*</font> Super Label</b></td>
	<td><%= ((FormHidden)form.getElement("superlabel")).render() %>
<span id="superlabeldiv"><%= form.getStringValue("superlabel") %></span>
</td>
	</tr>
	<tr>
		<td><b><font color=red>*</font> Company</td>
		<td><%= ((FormHidden)form.getElement("company")).render() %>
<span id="companydiv"><%= form.getElement("company").getDisplayName() %></span>
</td>
		<td>&nbsp</td>
		<td><b><font color=red>*</font> Sub Label</b></td>
		<td><%= ((FormHidden)form.getElement("sublabel")).render() %>
<span id="sublabeldiv"><%= form.getStringValue("sublabel") %></span>
</td>
	</tr>
	<tr>
		<td><b><font color=red>*</font> Division</td>
		<td><%= ((FormHidden)form.getElement("division")).render() %>
<span id="divisiondiv"><%= form.getElement("division").getDisplayName() %></span>
</td>
		<td>&nbsp</td>
		<td><b><font color=red>*</font> Imprint</b></td>
		<td><%= form.getElement("imprint") %></td>
	</tr>
	<tr>
		<td><b><font color=red>*</font> Label</td>
		<td><%= ((FormHidden)form.getElement("label")).render()%>
<span id="labeldiv"><%= form.getElement("label").getDisplayName() %></span>
</td>
		<td>&nbsp</td>
		<td><b> Config Code</b></td>
		<td><%= form.getElement("configcode") %></td>
	</tr>
	<tr bgcolor="white"><td colspan="5"></td></tr>

	<!-- SECTION IV -->
          <%
          String multSelLinkStyle = "";
          if (currentSelection != null && currentSelection.getMultSelections() != null && currentSelection.getMultSelections().size() > 0)
		  {
		    multSelLinkStyle = "FONT-SIZE: 8pt;COLOR: purple;FONT-FAMILY: Arial,Helvetica;TEXT-DECORATION: underline";
		  }
		  else
		  {
		    multSelLinkStyle = "FONT-SIZE: 8pt;COLOR: black;FONT-FAMILY: Arial,Helvetica;TEXT-DECORATION: underline";
		  }
		%>
	<tr>
		<td nowrap><b><font color=red>*</font> Project ID</b></td>
		<td><%= ((FormHidden)form.getElement("projectId")).render() %>
		<span id="projectIddiv"><%= form.getElement("projectId").getDisplayName() %></span></td>
		<td>&nbsp;</td>
		<td nowrap><b>GRid #</b></td>
		<td><%= form.getElement("gridNumber") %></td>

	</tr>
	<tr>
		<td><b>
                <a id="anchorMultSelectionsB" style="<%=multSelLinkStyle%>" href="javascript:showMultSelections()">
                UPC
                </a>
                </b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><%= form.getElement("UPC") %></td>
		<td>&nbsp;</td>
		<td>
                <b>Physical Prefix /<BR></b>
			<b>
                        <a id="anchorMultSelectionsA" style="<%=multSelLinkStyle%>" href="javascript:showMultSelections()">
                Local Prod #
                        </a>
                </b>&nbsp;&nbsp;&nbsp;
                </td>
                <td>
			<%= form.getElement("prefix") %>
			<%= form.getElement("selectionNo") %>
                </td>
	</tr>
        <!-- JR - ITS 743 -->
	<tr>
		<td nowrap><b>Sales Grouping Cd</b></td>
		<td><%= form.getElement("soundscan") %></td>
		<td>&nbsp;</td>
		<td></td>
		<td></td>
	<tr>
	<tr bgcolor="white"><td colspan="6"></td></tr>

	<!-- SECTION V -->
	<tr>
		<td><b><font color=red>*</font> Prod Category</td>
		<td><%= form.getElement("productLine") %></td>
		<td>&nbsp</td>
		<td><b><font color=red>*</font> Schedule Type</b></td>
		<td><%= form.getElement("configuration") %></td>
	</tr>
	<tr>
		<td nowrap><b><font color=red>*</font> Release Type</b></td>
		<td><%= form.getElement("releaseType") %></td>
		<td>&nbsp</td>
		<td><b><font color=red>*</font> Sub-Format</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><%= form.getElement("subConfiguration") %></td>
	</tr>
	<tr bgcolor="white"><td colspan="6"></td></tr>

	<!-- SECTION VI -->
	<tr>
		<td nowrap>
			<b>Dig. Price Code&nbsp;<%= form.getElement("test") %>
			<img src="<%= inf.getImageDirectory() %>filter_access1.gif" border=0>
		</td>
     	<td align="left"><%= form.getElement("priceCode") %></td>
		<td>&nbsp;</td>
                <td colspan=1 nowrap>
		</td>
                <td>
			<%= form.getElement("parentalIndicator") %><b>Parental Adv.</b>
                </td>
	</tr>
	<tr>
		<td nowrap><b>Label Contact</b>&nbsp;&nbsp;</td>
		<td><%= form.getElement("contactlist") %></td>
		<td>&nbsp</td>
		<td><b>Music Type</b></td>
		<td>&nbsp;<%= form.getElement("genre") %></td>
	</tr>
	<tr>
		<td>
                <%
                 String multOthContactLinkStyle = "";
                  if (currentSelection != null && currentSelection.getMultOtherContacts() != null && currentSelection.getMultOtherContacts().size() > 0)
		  {
		    multOthContactLinkStyle = "FONT-SIZE: 8pt;COLOR: purple;FONT-FAMILY: Arial,Helvetica;TEXT-DECORATION: underline";
		  }
		  else
		  {
		    multOthContactLinkStyle = "FONT-SIZE: 8pt;COLOR: black;FONT-FAMILY: Arial,Helvetica;TEXT-DECORATION: underline";
		  }
		%>
                <b>
                <a id="anchorMultOther" style="<%=multOthContactLinkStyle%>" href="javascript:showMultOtherContacts()">
                Other</a>
                </b>
        </td>
		<td><%=form.getElement("contact")%></td>
		<td>&nbsp</td>
		<td><b>Territory</b></td>
		<td>&nbsp;<%=form.getElement("territory")%></td>
	</tr>

	<tr bgcolor="white"><td colspan="6"></td></tr>

	<!-- ITS 1008 - jo - Removed Special Instructions tr>
        <td>
        <b>Special Instructions</b>
        </td>
        <td colspan=4>
		<%=form.getElement("specialInstructions")%>
        </td>
	</tr-->

  <!-- JR New Selection Screen Layout PNR driven 12/03/02 -->


</td></tr></table>

<table width="100%" bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>">
<tr><td align=center width="33%">
    <%if(saveVisible)
    {
      String command = form.getStringValue("cmd");%>
			<div id="saveDivBottom">
			<a href="JavaScript:submitSave('SaveDown');"
		 		onMouseOver="Javascript:mtbSaveDown.over();return true;"
		 		onMouseOut="Javascript:mtbSaveDown.out()"
 				onClick="Javascript:mtbSaveDown.click(); return true;">
 			<img name='Save' SRC="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
			</a>
                        </div>
			<script type="text/javascript" language="JavaScript" >
		  	var mtbSaveDown = new ToggleButton(document,'<%= inf.getImageDirectory() %>', 'Save', 'JavaScript:submitSave("SaveDown")', 66,14);
			</script>
		</td>

		<!-- JR - PNR AFE -->
		<td align=center width="33%">
                &nbsp;
		</td>
		<td align=center width="33%">
			<!--<a href="JavaScript:submitTempLPN('TempLPNDown');"
		 		onMouseOver="Javascript:mtbTempLPNDown.over();return true;"
		 		onMouseOut="Javascript:mtbTempLPNDown.out()"
 				onClick="Javascript:mtbTempLPNDown.click(); return true;">-->
 			<img name='tg' SRC="<%= inf.getImageDirectory() %>tg_Off.gif" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
			<!--</a>-->
			<script type="text/javascript" language="JavaScript" >
		  	//var mtbTempLPNDown = new ToggleButton(document,'<%= inf.getImageDirectory() %>', 'tg', 'JavaScript:submitTempLPN("TempLPNDown")', 66,14);
			</script>
		<!-- JR - PNR AFE -->


      <%}%>
</td></tr>
</table>

<% if (form.getRenderableValue("cmd").equals("selection-edit-new"))
	 {
%>
	 &nbsp;
<% }
	 else
	 {
%>
<% //start updated information which is only read-only %>
<table width="100%" bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" border=0>
  <tr bgcolor="white">
  	<td colspan="6"></td>
  </tr>
  <tr>
  	<td colspan="4">
  		<b>Last Updated:&nbsp;&nbsp</b>
  		<%= form.getRenderableValue("lastupdateddate") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%= context.getDelivery("lastUpdateUser")%>
  	</td>
		<td></td>
  </tr>
  <tr>
    <td colspan="4">
  		<b>Origin Date:&nbsp;&nbsp;&nbsp;</b>
  		<%= form.getRenderableValue("origindate") %>
  	</td>
  	<td colspan="1">
  		<b>Last Release Date Update:&nbsp;&nbsp</b>
  		<%= form.getRenderableValue("laststreetupdateddate") %>&nbsp;&nbsp;&nbsp;
  	</td>
  </tr>

  <%
  // JP 11/18/03

  if ( (form.getElement("archieDate") != null && form.getRenderableValue("archieDate").length() > 0)
      || (form.getElement("autoCloseDate") != null && form.getRenderableValue("autoCloseDate").length() > 0)
      || (form.getElement("lastLegacyUpdateDate") != null && form.getRenderableValue("lastLegacyUpdateDate").length() > 0))
  {
  %>
  <tr>
      <%if (form.getElement("archieDate") != null && form.getRenderableValue("archieDate").length() > 0)
        {%>
      <td colspan="4">
	<b>Last Archimedes Update:&nbsp;&nbsp;&nbsp;</b>
  		<%= form.getRenderableValue("archieDate") %>
      </td>
      <%}%>
      <% //ITS 42811
        if (form.getElement("lastLegacyUpdateDate") != null && form.getRenderableValue("lastLegacyUpdateDate").length() > 0)
        {%>
              <%if (form.getElement("archieDate") == null || form.getRenderableValue("archieDate").length()==0)
              {%>
                  <td colspan="4">&nbsp;</td>
              <%}%>
  	<td>
        <b>Last Legacy Update:&nbsp</b>
  		<%= form.getRenderableValue("lastLegacyUpdateDate") %>
  	</td>
      <%}%>
     </tr>
      <tr>
      <%if (form.getElement("autoCloseDate") != null && form.getRenderableValue("autoCloseDate").length() > 0)
        {%>
        <td colspan="3">
	<b>Auto-Closed:&nbsp;&nbsp;&nbsp;</b>
  		<%= form.getRenderableValue("autoCloseDate") %>
  	</td>
  	<td>
      </td>
     <%}%>

  </tr>

  <%
  }
  %>

</table>

<%} %>

<!-- JR - ITS 326 -->
<table width="100%" bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" border=0>
  <tr bgcolor="white">
  	<td></td>
  </tr>
  <tr>
	<td>
		<font color=red>* </font> Denotes Required Fields for all Digital Products <BR>
		<!--<font color=blue>* </font> Denotes Additional Required Fields to Generate Local Product #-->
	</td>
  </tr>
  <tr bgcolor="white">
    <td></td>
  </tr>
</table>
<!-- JR - ITS 326 -->

</td></tr></table>

<% //START DIV DEFINITIONS %>
<%@ include file="selection-search-elements.shtml"%>
<% //end search div %>


<%
String cmdString = form.getRenderableValue("cmd");
//System.out.println(cmdString);

boolean isNew = false;

if (cmdString.equalsIgnoreCase("selection-edit-new")
 || cmdString.equalsIgnoreCase("selection-edit-new-digital")
 || cmdString.equalsIgnoreCase("selection-edit-copy")
 || cmdString.equalsIgnoreCase("selection-edit-copy-digital") )
  isNew = true;
else
  isNew = false;

%>

<% //start comments div %>
<div id="commentLayer" class="search" style="visibility:hidden;width:250px;height:120px;z-index:3;left:100px;top:120px;">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr><td>
	<table width="100%">
	<tr>
		<td class="label">Comments<br><%= form.getElement("comments") %></td>
	</tr>
	<tr>
		<td>
			 <%if(saveVisible)
    {%>
			<input type="button" name="saveButton" value="Save Comments" onClick="return saveComments(<%=isNew%>);">&nbsp;&nbsp;
		<%}%>
			<input type="button" name="closeButton" value="Cancel" onClick="JavaScript:toggle( 'commentLayer', 'comments' );document.forms[0].artistFirstName.focus();">
		</td>
	</tr>
	</table>
</td></tr>
</table>
</div>
<% //end comments div %>


<% //start hold reason layer %>
<div id="holdReasonLayer" style="position:absolute;visibility:hidden;width:300px;height:180px;z-index:3;left:300px;top:70px;">
<table width="290" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat"><td>
	<table width="290">
	<tr>
		<td class="label">Hold Reason<br><%= form.getElement("holdReason") %></td>
	</tr>
	<tr>
		<td>
			 <%if(saveVisible)
    		{%>
      <input type="button" name="saveHoldReason" value="Save Reason" onClick="return saveComments(<%=isNew%>);">&nbsp;&nbsp;
				<%}%>
      <input type="button" name="closeHoldReason" id="closeHoldReason" value="Cancel" onClick="Javascript:toggle( 'holdReasonLayer', 'holdReason' )">
    </td>
	</tr>
	</table>
</td></tr>
</table>
</div>
<% //end hold reason layer%>

<% //start packaging layer %>
<input type="hidden" name="pak" value="">
<div id="PackagingLayer" style="position:absolute;visibility:hidden;width:255px;height:60px;z-index:3;left:280px;top:120px;">
<table width="251" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat"><td>
	<table width="247">
	<tr>
		<td><b>Packaging</b><br><%= form.getElement("PackagingHelper") %></td>
	</tr>
	<tr>
		<td>
			 <%if(saveVisible)
    		{%>
			<input type="button" name="saveButton" value="Save Comments" onClick="package.value=PackagingHelper.value;return saveComments(<%=isNew%>);">&nbsp;&nbsp;
			<%}%>
			<input type="button" name="closeButton" value="Cancel" onClick="JavaScript:togglePackaging()">
		</td>
	</tr>
	</table>
</td></tr>
</table>
</div>


<% //start Territory layer %>
<input type="hidden" name="terr" value="">
<div id="TerritoryLayer" style="position:absolute;visibility:hidden;width:255px;height:60px;z-index:3;left:280px;top:170px;">
<table width="251" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat"><td>
	<table width="247">
	<tr>
		<td><b>Territory</b><br><%= form.getElement("TerritoryHelper") %></td>
	</tr>
	<tr>
		<td>
			 <%if(saveVisible)
    		{%>
			<input type="button" name="saveButton" value="Save Comments" onClick="territory.value=TerritoryHelper.value;return saveComments(<%=isNew%>);">&nbsp;&nbsp;
			<%}%>
			<input type="button" name="closeButton" value="Cancel" onClick="JavaScript:toggleTerritory()">
		</td>
	</tr>
	</table>
</td></tr>
</table>
</div>


<div class="search" id="ImpactLayer" style="position:absolute;visibility:hidden;width:325px;height:130px;z-index:10;left:380px;top:120px;">
        <IFRAME HEIGHT="172" width="100%" ID="ImpactFrame" name="ImpactFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
<!-- // msc fix MAC display delay problem
<!--        <IFRAME HEIGHT="172" width="100%" ID="ImpactFrame" name="ImpactFrame"  FRAMEBORDER=0 SCROLLING=NO SRC="<%= inf.getServletCmdURL("selection-impactDate-frame")%>">
        </IFRAME>  -->
</div>

<div class="search" id="MultSelectionLayer" style="position:absolute;visibility:hidden;width:410px;height:130px;z-index:10;left:280px;top:180px;">
        <IFRAME HEIGHT="172" width="100%" ID="MultSelectionFrame" name="MultSelectionFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>

<div class="search" id="MultOtherContactLayer" style="position:absolute;visibility:hidden;width:375px;height:130px;z-index:10;left:180px;top:180px;">
        <IFRAME HEIGHT="172" width="100%" ID="MultOtherContactFrame" name="MultOtherContactFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>

<script>
	document.all.artistFirstName.focus(); // jr streetDate.focus(); //msc
</script>


<% //end packaging layer %>

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
                      <input type="button" name="button_sendPfm" value="Send PFM" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDPFM%>');">&nbsp;&nbsp;
                    <%
                     }
                    %>

                   <%if ( (context.getDelivery("bomSend") != null && ((String)context.getDelivery("bomSend")).equalsIgnoreCase("true")) )
                     {
                   %>
                      <input type="button" name="button_sendBom" value="Send BOM" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDBOM%>');">&nbsp;&nbsp;
                    <%
                     }
                    %>

                   <%if ( (context.getDelivery("bomSend") != null && ((String)context.getDelivery("bomSend")).equalsIgnoreCase("true")) &&
                          (context.getDelivery("pfmSend") != null && ((String)context.getDelivery("pfmSend")).equalsIgnoreCase("true")) )
                     {
                   %>
                      <input type="button" name="button_sendPfmBom" value="Send PFM and BOM" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDPFMBOM%>');">&nbsp;&nbsp;
                    <%
                     }
                    %>

                    <input type="button" name="button_cancel" value="Cancel" onClick="JavaScript:sendPfmBom('<%=MilestoneConstants.CMD_SELECTION_SENDCANCEL%>');">&nbsp;&nbsp;
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

<% //copy dialog %>
<div class="search" id="CopyMessageLayer" style="visibility:hidden;left:5px;top:60px;">
<table width="410" border="1" cellspacing="0" cellpadding="5">
<tr><td>
	<table width="100%">
	<tr>
		<th colspan="2" align="center"><b>COPY ALERT</b></th>
	</tr>
	<tr>
		<td colspan="2" align="center">
		&nbsp;The corporate structure associated to this project in Archimedes does not match the corporate structure of the product you are attempting to copy.<BR>See below for differences:
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<%=diffMessage%>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="left">
		If you Accept, the corporate structure listed above will be applied to this product.<BR>If you Cancel, you will need to create a new product.
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<input type="button" name="accept" value="Accept" onClick="JavaScript:acceptChanges()" style="width:70px">&nbsp;
		<input type="button" name="cancel" value="Cancel" onClick="JavaScript:rejectChanges()" style="width:70px">
		</td>
	</tr>
	</table>
</td></tr>
</table>
</div>

<%
if (diffSelection != null)
{
%>
<script>
document.all.saveDiv.style.visibility = 'hidden';
document.all.saveDivBottom.style.visibility = 'hidden';
</script>

<script>
function acceptChanges()
{
  document.all.saveDiv.style.visibility = 'visible';
  document.all.saveDivBottom.style.visibility = 'visible';

  //Label
  labelText = "<%=diffSelection.getLabel().getName()%>";
  document.all.labeldiv.innerText = labelText;
  labelId = "<%=diffSelection.getLabel().getStructureID()%>";
  document.all.label.value = labelId;

  //Company
  companyText = "<%=diffSelection.getCompany().getName()%>";
  document.all.companydiv.innerText = companyText;
  companyId = "<%=diffSelection.getCompany().getStructureID()%>";
  document.all.company.value = companyId;

  //Division
  divisionText = "<%=diffSelection.getDivision().getName()%>";
  document.all.divisiondiv.innerText = divisionText;
  divisionId = "<%=diffSelection.getDivision().getStructureID()%>";
  document.all.division.value = divisionId;

  //Environment
  environmentText = "<%=diffSelection.getEnvironment().getName()%>";
  document.all.environmentdiv.innerText = environmentText;
  environmentId = "<%=diffSelection.getEnvironment().getStructureID()%>";
  document.all.environment.value = environmentId;

  //OpCo
  OperCompanyText = "<%=diffSelection.getOperCompany()%>";
  OperCompanyDisplayDivText = "<%=diffSelection.getOperCompany()%>";
  document.all.opercompany.value = OperCompanyText;
  document.all.opercompanydiv.innerText = OperCompanyDisplayDivText;

  //Super Label
  SuperLabelText = "<%=diffSelection.getSuperLabel()%>";
  document.all.superlabel.value = SuperLabelText;
  document.all.superlabeldiv.innerText = SuperLabelText;

  //Sub Label
  SubLabelText = "<%=diffSelection.getSubLabel()%>";
  document.all.sublabel.value = SubLabelText;
  document.all.sublabeldiv.innerText = SubLabelText;

  //Imprint
  ImprintText = "<%=diffSelection.getImprint()%>";
  document.all.imprint.value = ImprintText;

  // Retrieve releasing families
  // msc 10/03/03 donot update releasing family on a copy
  //document.all.ReleasingFamilyFrame.src = "<%= inf.getServletCmdURL("selection-get-releasing-families")%>&eID=" + document.all.environment.value;

  toggle( 'CopyMessageLayer', '' );
}

function rejectChanges()
{
  toggle( 'CopyMessageLayer', '' );
  parent.top.bottomFrame.location = editor;
}

</script>
<%
}
%>

<% //end copy dialog %>

<% //Releasing family process %>
<IFRAME style="visibility:hidden" HEIGHT="100" width="200" ID="ReleasingFamilyFrame" name="ReleasingFamilyFrame"  FRAMEBORDER=0 SCROLLING=AUTO>
</IFRAME>

<% //END DIV DEFINITIONS %>

<%=form.renderEnd()%>

<script>
var swtOn = parent.top.menuFrame.sOnColor;
var swtOff = parent.top.menuFrame.sOffColor;
// MSC Sitch menu frame selected tab Labels Selection
if(parent.top.menuFrame.mtbLabels)
  parent.top.menuFrame.mtbLabels.showLabelsSubMenu();
if(parent.top.menuFrame.document.all.MIselection)
{
 parent.top.menuFrame.document.all.MIselection.style.color = swtOn;
 parent.top.menuFrame.selectedMenuItem = "selection";
}

// JR release calendar
if(parent.top.menuFrame.document.all.MIreleasecalendar)
 parent.top.menuFrame.document.all.MIreleasecalendar.style.color = swtOff;


if(parent.top.menuFrame.document.all.MIschedule)
 parent.top.menuFrame.document.all.MIschedule.style.color = swtOff;
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


<%@ include file="template-bottom-html.shtml"%>


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
else if (context.getDelivery("DiffMessage") != null)
{
    out.println("<SCRIPT>");
    out.println("toggle( 'CopyMessageLayer', '' );");
    out.println("document.all.accept.focus();");
    out.println("</SCRIPT>");
}
else if (context.getDelivery("ProjectNumberMessage") != null)
{
    out.println("<SCRIPT>");
    out.println("alert('" + (String)context.getDelivery("ProjectNumberMessage") + "');");
    //JP 10/30/03
    //out.println("parent.top.bottomFrame.location = editor;");
    out.println("</SCRIPT>");
}
else
{
  if(context.getDelivery("alert-box") != null)
  {%>
    <script language=Javascript>
    alert("<%=(String)context.getDelivery("alert-box")%>");
    </script>
<%}

  if (alertMessage != null)
  {
  %>
    <script language=Javascript>
    alert('<%=alertMessage%>');
    </script>
  <%
  }
}
%>


