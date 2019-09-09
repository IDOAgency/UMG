<%
		/*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
		*/
%>
<%@ include file="template-top-page.shtml"%>
<%@ include file="callHelp.js"%>

<% 	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.`
	String htmlTitle = "PFM Form";

  //this variable checks to see if the user has right to the particular buttons on this page.
	boolean saveVisible = false;
	boolean copyVisible = false; // release upgrade 8.1 hide buttons

  boolean newPfm = false;

  if(form.getRenderableValue("cmd").equals("pfm-edit-new"))
  newPfm = true;

	saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
	copyVisible = ((String)context.getDelivery("saveVisible")).equals("true"); // release upgrade 8.1 hide buttons

    //Used by include-selection-notepad
    String editorName = MilestoneConstants.CMD_PFM_EDITOR;

    // JP 10/16/02
    boolean isFinal = false;
    String printOption = form.getStringValue("printOption");

    // JO 12/13/02
    // If the first 4 digits of the product number = "MLST" then
    // the form should not be marked final.
    //int MLSTinProductNumber = form.getElement("product_number").getRenderableValue().toUpperCase().indexOf("TEMP"); // JR - ITS #529 MLST");
    //System.out.println("***MLSTinProductNumber :[" + MLSTinProductNumber + "]");


    if (!printOption.equalsIgnoreCase("Draft")) // && (MLSTinProductNumber==-1) )
      isFinal = true;

   // release upgrade 8.1 hide buttons
   if(MilestoneConstants.IsGDRSactive)
   {
     saveVisible = false;
     copyVisible = false;
   }
    // release upgrade 8.1 if GDRS is active hide buttons
%>

<%@ include file="template-top-html.shtml" %>


<SCRIPT LANGUAGE="JavaScript">
<!--

var browserName = navigator.appName;
var browserVersion = navigator.appVersion;
var ieCheck = true;

if (browserVersion.indexOf("MSIE 5.5") > 0)
  ieCheck = true;
else if (browserVersion.indexOf("MSIE 4.") > 0 || browserVersion.indexOf("MSIE 5.") > 0)
  ieCheck = false;


// -->
</SCRIPT>


<script type="text/javascript" language="JavaScript">
//global variables needed in include bom-editor-javascript.js
  var sort = "<%= MilestoneConstants.CMD_PFM_SORT%>";
  var sortGroup = "<%= MilestoneConstants.CMD_PFM_GROUP%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_PFM_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_SELECTION%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_PFM_EDITOR) %>";
  var save = "<%= MilestoneConstants.CMD_PFM_EDIT_SAVE%>";
  var saveComment = "<%= MilestoneConstants.CMD_PFM_EDIT_SAVE_COMMENT%>";
  var editCopy = "<%= MilestoneConstants.CMD_PFM_EDIT_COPY %>";
  var pasteCopy = "<%= MilestoneConstants.CMD_PFM_PASTE_COPY %>";
  var pasteSave = "<%= MilestoneConstants.CMD_PFM_PASTE_SAVE %>";
  var editNew = "<%= MilestoneConstants.CMD_PFM_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_PFM_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";
  var printPdf = "<%= MilestoneConstants.CMD_PFM_PRINT_PDF%>";
  var printRtf = "<%= MilestoneConstants.CMD_PFM_PRINT_RTF%>";
  var printPdf4 = "<%= MilestoneConstants.CMD_PFM_PRINT_PDF4%>";
  var printRtf4 = "<%= MilestoneConstants.CMD_PFM_PRINT_RTF4%>";
  var sendEmail = "<%= MilestoneConstants.CMD_PFM_SEND_EMAIL%>"

function processLoad(pFocusField)
{
  focusField(pFocusField);
}

  function helpConext()
  {
    callHelp('Help/','PFM_Form.htm');
    mtbHelp.reset();
  }//end function helpContext

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

function enterCheck()
{
  if (window.event && window.event.keycode == 13)
  {
    document.all.SuccessLayer.style.visibility = "hidden";
    document.all.FailureLayer.style.visibility = "hidden";
  }
  else
  {
    return true;
  }
}

</script>



<% //include js files holding javascript functions %>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>pfm-editor-javascript.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-auto-select.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-upc-ssg-sgc.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">
function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_PFM_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

// JR - ITS 66 03/07/03
function noProjectID(ctrl){
	if (ctrl.value == 'Final') {
		alert("A PFM cannot be changed to 'Final' without a 'Project ID'.");
                if (document.forms[0].printOption[0])
		  document.forms[0].printOption[0].checked = true;
		//ctrl.value = 'Draft';
		return false;
	}
	return true;
}// end noProjectID


// jo - ITS# 520 3/18/2003
// JR - ITS# 602
// jo - ITS#1101 6/21/2005 - Add validation to ensure that a PFM that is 'TBS' or 'Active' with a blank date is not changed to 'Final'
// jo - ITS#1131 7/20/2005 - Consolidated code to use pfm-editor-javascript functions
// jo - ITS#49996 7/13/2006 - Added code to check for Sound Scan Grouping Code/Sales Grouping Code
function verifyFinalValues(ctrl){

    if (document.forms[0].printOption != null && document.forms[0].printOption[0]!='undefined')  {  // JR - ITS 716
    
     if (document.forms[0].printOption[1] && document.forms[0].printOption[1].checked == true){

      //Validate that the form fields have been completed before changing to Final
      if (!checkPFMFields("change",true)){
               return false;
      }

      //If the status is 'TBS'
      if ( '<%= form.getRenderableValue("status")%>'=='TBS' ){
                alert("A product must be in 'Active' status with a Digital Release Date in order to change a PFM to Final");
                if (document.forms[0].printOption[0])
                  document.forms[0].printOption[0].checked = true;
                return false;
      }

        //If 'Final' and Commercial: require that a UPC is entered.
	if (  ('<%= form.getRenderableValue("isPromo") %>'=='CO') && (document.forms[0].upc.value=='')  ) {
		alert("A PFM cannot be changed to 'Final' without a 'UPC' if the product is Commercial.");
                if (document.forms[0].printOption[0])
                  document.forms[0].printOption[0].checked = true;
		return false;
	}

        //If 'Final' and config_code is empty then alert and don't save
	if ( ( document.forms[0].config_code.value=='-1') ) {
		alert("A PFM cannot be changed to 'Final' without a Config Code.");
                if (document.forms[0].printOption[0])
		  document.forms[0].printOption[0].checked = true;
		return false;
	}

        //If 'Final' and project ID is 0000-00000 or empty then alert and don't save
	if (( '<%= form.getRenderableValue("projectID") %>'=='0000-00000' || '<%= form.getRenderableValue("projectID") %>'=='' ) ) {
		alert("A PFM cannot be changed to 'Final' without a Project ID or if the Project ID is '0000-00000'.\nPlease go to the selection screen and REVISE the project.");
                if (document.forms[0].printOption[0])
		  document.forms[0].printOption[0].checked = true;
		return false;
	}
	
		// If 'Final' and Commercial and Sound Scan Code is empty then alert and don't save
	if ( ('<%= form.getRenderableValue("isPromo") %>'=='CO') && document.forms[0].sound_scan_code.value=='' ) {
		alert("A PFM cannot be changed to 'Final' without a Sales Grouping Code.");
                if (document.forms[0].printOption[0])
		  document.forms[0].printOption[0].checked = true;
		return false;
	}

     }
    }
    return true;
}// end noUnits

// JR - ITS 616
function trim(inputString) {
   // Removes leading and trailing spaces from the passed string. Also removes
   // consecutive spaces and replaces it with one space. If something besides
   // a string is passed in (null, custom object, etc.) then return the input.
   if (typeof inputString != "string") { return inputString; }
   var retValue = inputString;
   var ch = retValue.substring(0, 1);
   while (ch == " ") { // Check for spaces at the beginning of the string
      retValue = retValue.substring(1, retValue.length);
      ch = retValue.substring(0, 1);
   }
   ch = retValue.substring(retValue.length-1, retValue.length);
   while (ch == " ") { // Check for spaces at the end of the string
      retValue = retValue.substring(0, retValue.length-1);
      ch = retValue.substring(retValue.length-1, retValue.length);
   }
   while (retValue.indexOf("  ") != -1) { // Note that there are two spaces in the string - look for multiple spaces within the string
      retValue = retValue.substring(0, retValue.indexOf("  ")) + retValue.substring(retValue.indexOf("  ")+1, retValue.length); // Again, there are two spaces in each of the strings
   }
   return retValue; // Return the trimmed string back to the user
}

// jo - ITS# 520 3/18/2003
// jo - ITS#49996 7/13/2006 - Added code to check for Sound Scan Grouping Code
function checkFinalUnitsAndSubmit(){

  // msc 09/25/03 ITS 108 validate UPC & Sound scan group codes
  if(!checkLengthUPC( document.all.upc.value, "UPC", 12, 14, "UPC", true, false ))
  {
     document.all.upc.focus();
     return;
  }
 
  if(!checkLengthUPC( document.all.sound_scan_code.value, "Sales Grouping code", 12, 14, "SSG", true, false ))
  {
     document.all.sound_scan_code.focus();
     return;
  }

        var strUPC = trim(document.forms[0].upc.value);
	// take '-' out
	var re = new RegExp ('-', 'gi') ;
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	// take ' ' out
	re = new RegExp (' ', 'gi') ;
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	// take '/' out
	re = new RegExp ('/', 'gi') ;
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}

	if( strUPC != "" && isNaN(strUPC) ) {
		alert("'UPC' must be a numeric field.");
		document.forms[0].upc.focus();
		return;
	}

        var strSSC = trim(document.forms[0].sound_scan_code.value);
	// take '-' out
	var re = new RegExp ('-', 'gi') ;
	if (strSSC != '') {
		strSSC = strSSC.replace(re,'');
	}
	// take ' ' out
	re = new RegExp (' ', 'gi') ;
	if (strSSC != '') {
		strSSC = strSSC.replace(re,'');
	}
	// take '/' out
	re = new RegExp ('/', 'gi') ;
	if (strSSC != '') {
		strSSC = strSSC.replace(re,'');
	}
	
	var isFinal = false;
	if ( (document.forms[0].printOption[1] && document.forms[0].printOption[1].checked == true)
		|| (document.forms[0].printOption.value == 'Final' && document.forms[0].printOption.checked) ) {
		isFinal = true;
    }

    if (document.forms[0].printOption != null) {  // JR - ITS 716
    
		if ( isFinal && ( document.forms[0].config_code.value=='-1') ) {
			alert("A PFM cannot be changed to 'Final' without a Config Code.");
	        if (document.forms[0].printOption[0])
			  	document.forms[0].printOption[0].checked = true;
		} else if ( isFinal && ( '<%= form.getRenderableValue("projectID") %>'=='0000-00000' || '<%= form.getRenderableValue("projectID") %>'=='' ) ) {
			alert("A PFM cannot be changed to 'Final' without a Project ID or if the Project ID is '0000-00000'.\nPlease go to the selection screen and REVISE the project.");
	        if (document.forms[0].printOption[0])
			  	document.forms[0].printOption[0].checked = true;
		} else if ( isFinal && ('<%= form.getRenderableValue("isPromo") %>'=='CO') &&(document.forms[0].upc.value=='')  ) {
			alert("A PFM cannot be changed to 'Final' without a 'UPC' if the product is Commercial.");
	        if (document.forms[0].printOption[0])
			  	document.forms[0].printOption[0].checked = true; 
		} else if ( isFinal && ('<%= form.getRenderableValue("isPromo") %>'=='CO') && (document.forms[0].sound_scan_code.value=='' ) ) {
			alert("A PFM cannot be changed to 'Final' without a Sales Grouping Code.");
	         if (document.forms[0].printOption[0])
			  	document.forms[0].printOption[0].checked = true;
		} 
		else 
		{
			submitSave('Save', true);
		}
    } 
    else 
    {
        submitSave('Save', true);
    }
}// end noUnits

</script>

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("searchArray") %>
</script>


<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<!-- JR - ITS #70 -->
<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>
<!-- JR - ITS #70 -->

<!-- JR - ITS 842 -->
<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body topmargin="0" leftmargin="0" onload="processLoad('prepared_by');" onKeyDown="enterCheck()">
<%= form.renderStart()%>
<%= form.getElement("cmd") %>

<input type="hidden" name="parentForm" value="pfmForm"">


<%
String alertMessage = (String)context.getDelivery("AlertMessage");
String sendMailStatus = (String)context.getDelivery("SendMailStatus");
%>

<% if (form.getElement("copyPaste") != null)
 		{
 %>
		<%= form.getElement("copyPaste") %>
<%}%>

<%= form.getElement("OrderBy") %>
<%= form.getElement("selectionID")%>

<!-- msc 09/10/03 -->
<%= form.getElement("pfmLastUpdateCheck") %>

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
	<td width="170"><span class="title">PFM Form</span></td>
	<td width="85">
  <% if (!form.getStringValue("cmd").equals(MilestoneConstants.CMD_PFM_EDIT_NEW) && !form.getStringValue("cmd").equals(MilestoneConstants.CMD_PFM_PASTE_COPY))
     {%>
	  <div id="printWindow" align=center>
		<!--<a href="JavaScript:submitPrintPdf('Print')"> -->
    <a href="home?cmd=<%=MilestoneConstants.CMD_PFM_PRINT_PDF%>">
   <!--    PRINT TO PDF msc ITS42 -->
    <img name="Print" id="Print" src="<%=inf.getImageDirectory()%>Print_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
    </a>

	  </div>
  <%}%>
	</td>
	<td width="85">
		<div align="center" id="saveDiv">
       <%if(saveVisible)
        {
          //JP 10/16/02
          String saveButton = "Save";
          if (isFinal)
            saveButton = "Save_Send";
        %>
      	   <a href="JavaScript:checkFinalUnitsAndSubmit();"
           onMouseOver="Javascript:toggleSaveHover('<%= inf.getImageDirectory() %>');return true;"
           onMouseOut="Javascript:toggleSaveHover('<%= inf.getImageDirectory() %>')">
           <img name="save" id="<%=saveButton%>" SRC="<%= inf.getImageDirectory() + saveButton%>_On.gif" height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
      <%}%>
	  </div>
	</td>
	<td width="185">
         <div align="center" id="sendOptionDiv">
           <%
           if (saveVisible && isFinal)
             out.print(form.getElement("sendOption"));
           %>
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

  <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
      <% //===============start selection navigation panel=========================== %>
       <% /* This part of the code is made an include to make it easy to manage this page.
             The the java script needed resides in this page.
             The include just gets and puts the code here as it was written here.*/ %>
        <td valign="top">
          <%@ include file="include-selection-notepad.shtml" %>
       </td>

     <% //===============end selection navigation panel=========================== %>
   <%}%>

	<% //start pfm form %>
	<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" align="left" colspan="6" width="100%">
  <table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td valign="top">
				<table width=100% class="tightDetailList" border="0">
        <tr>
		    <td>
        <%if(copyVisible)
        { 
         if(form.getRenderableValue("copyPaste") != null && form.getRenderableValue("copyPaste").equals("copy"))
         {%>
				  <img src="<%= inf.getImageDirectory() %>copy.gif" onclick="submitCopy();" onDblClick="submitCopy();">
        <%}
         else if(form.getRenderableValue("copyPaste") != null && form.getRenderableValue("copyPaste").equals("paste"))
         {%>
          <img src="<%= inf.getImageDirectory() %>paste.gif" onclick="submitPaste();" onDblClick="submitPaste();">
        <%}
        }
        %>
		 </td><td></td>

	<%//System.out.println("MLSTinProductNumber:[" + MLSTinProductNumber + "]");%>

		 <td class="label">
                     <!-- JR - ITS 694 -->
                     <% //if (MLSTinProductNumber!=-1) {%>
                     	<!--Draft-->
		     <% //} else { %>
			<%= form.getElement("printOption") %>
		     <% //} %>
                 </td>
                                <td nowrap>
                                <!-- JR - ITS 702 -->
                  <%= form.getElement("mode") %> #&nbsp;&nbsp;&nbsp;
                  <%= form.getElement("ChangeNumber") %>
                                </td>

                <!-- // msc 1030 ------------------------------------------->
                <tr class="divider">
        	  <td colspan="6" align="middle" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
                </tr>
              <!-- // msc 1030 ------------------------------------------->


		</tr>
		<tr>
		  <td class="label" nowrap>Prepared By:</td>
		  <td><%= form.getElement("prepared_by") %></td>
		  <td class="label" nowrap>Phone:</td>
		  <td><%= form.getElement("phone") %></td>
		</tr>
		<tr>
		  <td class="label" nowrap>Email:</td>
		  <td><%= form.getElement("email") %></td>
		  <td class="label" nowrap>Fax Number:</td>
		  <td><%= form.getElement("fax") %></td>
		</tr>
		<tr>
		  <td class="label" nowrap>Comments:</td>
		  <td class="label" colspan="3">
                        <!-- JR - ITS 701
			<%
			  // this checks to see what image to display
			  // if there are existing comments: display afile.gif
			  // if no comments exist: display file.gif
			  String comments = "";
			  comments = form.getRenderableValue("comments");
			  if (comments != null && !comments.equals(""))
			  {
			%>
			<img title="PFM Comments" src="<%= inf.getImageDirectory() %>afile.gif" border="0" onClick="toggle( 'CommentLayer', 'comments' );" >
			<%}
			  else
				{ %>
			<img title="PFM Comments" src="<%= inf.getImageDirectory() %>file.gif" border="0" onClick="toggle( 'CommentLayer', 'comments' );" >
			<%}%>
                        -->
                        <%= form.getElement("comments") %>
		  </td>

		</tr>
<!--		<tr><td colspan="5"><hr noshade></tr>
		<tr>  -->
                <!-- // msc 1030 ------------------------------------------->
                <tr class="divider">
        	  <td colspan="6" align="middle" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
                </tr>
              <!-- // msc 1030 ------------------------------------------->


		<!--  JR - ITS #444 - 04/28/03 -->
		<!--  Changing the displaying order to the PFM form -->
		<tr>
		  <td class="label" nowrap>Imprint:</td>
		  <td><%= form.getRenderableValue("imprint") %></td>
		  <td class="label" nowrap>UPC:</td>
		  <td><%= form.getElement("upc")%></td>
		</tr>

		<tr>
		  <td class="label" nowrap>Oper Company:</TD>
		  <td><%= form.getRenderableValue("operating_company") %></td>
		  <td class="label" nowrap>Sales Grouping Cd:</td>
		  <td><%= form.getElement("sound_scan_code") %></td>
		</tr>

                <!-- jo - ITS 1102 -->
		<tr>
		  <td class="label" nowrap>Physical Prefix/<br> Local Prod #:</td>
		  <td><%= form.getElement("product_number").getRenderableValue()%></td> <!--ITS 1132 - jo-->
		  <td class="label" nowrap>Music Ln:</TD>
		  <td><%= form.getElement("music_line") %></td>
		</tr>

		<tr>
		  <td class="label" nowrap>Config Cd:</td>
		  <td colspan="3"><%= form.getElement("config_code") %></td>
		</tr>
		<tr>
		  <td class="label" nowrap>Title:</td>
		  <td colspan="3"><%= form.getRenderableValue("title") %></td>
		</tr>
                <!-- JR - ITS 694 -->
		<tr>
		  <td class="label" nowrap>Artist:</td>
		  <td><%= form.getRenderableValue("artist") %></td>
                  <td class="label" nowrap>Rep. Owner:</TD>
		  <td><%= form.getElement("repertoire_owner") %></td>
		</tr>
		<tr>
                  <td class="label" nowrap>Release Date:</td>
		  <td><%= form.getRenderableValue("releaseDate")%><input type=hidden name="releaseDate" value="<%= form.getRenderableValue("releaseDate")%>"></td>
		  <td class="label" nowrap>Rep. Class:</td>
		  <td><%= form.getRenderableValue("repClass")%></td>
		</tr>
		<tr>
		  <td class="label" nowrap>Super Label:</td>
		  <td><%= form.getRenderableValue("super_label") %></td>
		  <td class="label" nowrap>Rtrn/Scrap Cd:</td>
		  <td><%=MilestoneHelper_2.getAbbrevAndDescription(form.getRenderableValue("return_code"), Cache.getReturnCodes())%></td><!--ITS 1131 jo -->
		</tr>
		<tr>
		  <td class="label" nowrap>Sub Label:</td>
		  <td colspan="3"><%= form.getRenderableValue("label_code") %></td>
		  <!-- Remedy Ticket #CRQ000000007449
          Date : 16-Feb-2012
          Implemented By : CP (Chandra Prakash Soni)
               	<td class="label" nowrap>Loose Pick Exempt:</td>  
		    <td>MilestoneHelper_2.getAbbrevAndDescription(form.getRenderableValue("loose_pick_exempt"), Cache.getLoosePickExempt())</td> --> <!--ITS 1131 jo -->		  
		</tr>
		<tr>
		  <td class="label" nowrap>Co. Cd:</td>
		  <td><%= form.getElement("company_code") %></td>
		  <td class="label" nowrap>IMI Exempt:</td>
		  <td><%= MilestoneHelper_2.getAbbrevAndDescription(form.getRenderableValue("guarantee_code"), Cache.getGuaranteeCodes()) %></td><!--ITS 1131 jo -->
		</tr>
		<tr>
		  <td class="label" nowrap>Proj ID:</td>
		  <td><%= form.getRenderableValue("projectID") %></td>
                  <td class="label" nowrap>Music Type:</td>
		  <td><%= form.getElement("music_type") %></td>
		</tr>
		<tr>
		  <td class="label" nowrap>PO Merge Cd:</td>
                  <td><%=MilestoneHelper_2.getAbbrevAndDescription(form.getRenderableValue("po_merge_code"), Cache.getPoMergeCodes())%></td><!--ITS 1131 jo -->
                  <td class="label" nowrap>Price Point:</td>
		  <td><%= form.getRenderableValue("price_point") %>:Promos</td>
		</tr>
		<tr>
		  <td class="label" nowrap># of Units:</td>
		  <td><%= form.getElement("units_per_set").getStartingValue() %></td>
                  <td class="label" nowrap>NARM Extract Ind:</td>
		  <td><%= MilestoneHelper_2.getAbbrevAndDescription(form.getRenderableValue("NARM"), Cache.getNarmExtracts()) %></td><!--ITS 1131 jo -->
		</tr>
		<tr>
		  <td class="label" nowrap>Sets per Crtn:</td>
		  <td><%= form.getElement("sets_per_carton").getStartingValue() %></td>
                  <td class="label" nowrap>Comp Sndtrack:</td>
		  <td><%= form.getElement("compilation_code") %></td>
		</tr>
		<tr>
		  <td class="label" nowrap>Imp. Ind:</td>
		  <td><%= form.getRenderableValue("ImportIndicator") %></td>
		  <td class="label" nowrap>Parental Adv:</td>
		  <td><%= form.getElement("ParentalAdv") %></td>
		</tr>
                <tr>
		  <td class="label" nowrap>GRid #:</td>
		  <td><%= form.getElement("gridnum") %></td>
		  <td class="label" nowrap>Dig. Price Code:</td>
		  <td><%= form.getRenderableValue("price_code") %></td>
		</tr>


                  <tr>
                  <td class="label" nowrap>Last Updated:</td>
                  <% //ITS 42811
                  if (form.getElement("lastLegacyUpdateDate") != null && form.getRenderableValue("lastLegacyUpdateDate").length() > 0)
                  {%>
                      <td>
			<%= form.getRenderableValue("lastUpdatedDate")%>&nbsp;<%= form.getRenderableValue("lastUpdatedBy")%>
		      </td>
                      <td colspan=2><b>Last Legacy Update:</b>&nbsp;&nbsp;<%= form.getRenderableValue("lastLegacyUpdateDate")%></td>
                  <%} else {%>
                      <td colspan=3>
			<%= form.getRenderableValue("lastUpdatedDate")%>&nbsp;<%= form.getRenderableValue("lastUpdatedBy")%>
		      </td>
                  <% } %>
                  </tr>


		<!-- JR - ITS #546 - Add Buttons to the bottom of the PFM form -->
        <tr class="divider">
			<td colspan="6" align="middle" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
        </tr>
		<tr colspan=6 align=right>
			<table width=100%>
			<tr>
				<td width=100></td>
				<td>
			<% if (!form.getStringValue("cmd").equals(MilestoneConstants.CMD_PFM_EDIT_NEW) && !form.getStringValue("cmd").equals(MilestoneConstants.CMD_PFM_PASTE_COPY))
				{%>
				<div id="printWindow2" align=center>
					<!--<a href="JavaScript:submitPrintPdf('Print')"> -->
				<a href="home?cmd=<%=MilestoneConstants.CMD_PFM_PRINT_PDF%>">
			<!--    PRINT TO PDF msc ITS42 -->
				<img name="Print" id="Print" src="<%=inf.getImageDirectory()%>Print_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
				</a>
				</div>
			<%}%>
				</td>
				<td>
		        	<div align="center" id="saveDivBottom">
				<%if(saveVisible)
					{
					//JP 10/16/02
					String saveButton = "Save";
					if (isFinal)
						saveButton = "Save_Send";
					%>
      				<a href="JavaScript:checkFinalUnitsAndSubmit();"
					onMouseOver="Javascript:toggleSaveHoverBottom('<%= inf.getImageDirectory() %>');return true;"
					onMouseOut="Javascript:toggleSaveHoverBottom('<%= inf.getImageDirectory() %>')">
					<img name="save2" id="<%=saveButton%>" SRC="<%= inf.getImageDirectory() + saveButton%>_On.gif" height=14 border=0 hspace=0 vspace=0 align="absmiddle">
					</a>
				<%}%>
				</div>
				</td>
				<td width=100></td>
			</tr>
			</table>
		</tr>
		<!-- JR - ITS #546 -->
		<!-- JR - ITS #444 - 04/28/03 -->

	  </table>
	</td>
  </tr>
</table>


<% //DIV definitions %>
<% //start search div %>
<%@ include file="selection-search-elements.shtml"%>
<% //end search div %>

<% //start comments div %>
<div id="CommentLayer" class="search" style="visibility:hidden;width:250px;height:80px;z-index:3;left:530px;top:10px;">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
  <td>
	<table width="100%">
	  <tr>
		<td class="label">Comments<br>
			<!--<%= form.getElement("comments") %>-->
		</td>
	  </tr>
	  <tr>
		<td>
    <%if(saveVisible)
      {%>
		  <input type="button" name="saveButton" value="Save Comments" onClick="saveComments()">&nbsp;&nbsp;
    <%}%>
		  <input type="button" name="closeButton" value="Cancel" onClick="JavaScript:toggle( 'CommentLayer', 'comments' )">
		</td>
	  </tr>
	</table>
  </td>
</tr>
</table>
</div>
<% //end comments div %>


<% //start targetPFMLayer %>
<div id="targetPfmLayer" class="search" style="position:absolute;visibility:hidden;width:250px;height:80px;z-index:3;left:300px;top:60px;">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
  <td>
	<table>
	  <tr>
		<td class="label">Choose a Target PFM Form to Copy to<br><p> <%= form.getElement("targetPfm") %></td>
	  </tr>
	  <tr>
		<td>
		  <input type="button" name="cp2TargetPfmButton" value="Copy" onClick="submitCopy();">&nbsp;&nbsp;
		  <input type="button" name="closeButton" value="Cancel" onClick="JavaScript:toggle( 'targetPfmLayer', '' )">
	   </td>
	  </tr>
	</table>
  </td>
</tr>
</table>
</div>
<% //end targetPFMLayer %>


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


<%@ include file="include-email-distribution.shtml"%>


</tr>
</table>

<% //END DIV DEFINITIONS %>


<input type="hidden" name="po_merge_code" value="<%= form.getRenderableValue("po_merge_code")%>">
<input type="hidden" name="return_code"   value="<%= form.getRenderableValue("return_code")%>">
<input type="hidden" name="price_code"    value="<%= form.getRenderableValue("price_code")%>">
<input type="hidden" name="price_point"   value="<%= form.getRenderableValue("price_point")%>">


<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>


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

