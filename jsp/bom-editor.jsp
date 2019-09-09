<%
	/*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message

		Development History
		- ?
		- 2004-04-27 lg ITS 541 Make "# of Units" label stand out more by bolding it and shifting
          subsequent fields to next column;
	*/
%>
<%@ include file="template-top-page.shtml"%>
<%@ include file="callHelp.js"%>

<%
	Bom bom = (Bom)context.getSessionValue("Bom");
	Selection currentSelection = (Selection)context.getSessionValue("Selection");

	//this variable checks to see if the user has right to the particular buttons on this page.
	boolean saveVisible = false;

	saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");

	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "BOM screen";

        //Used by include-selection-notepad
        String editorName = MilestoneConstants.CMD_BOM_EDITOR;

        // JP 10/16/02

        // JO 12/13/02
        // If the first 4 digits of the product number = "MLST" then
        // the form should not be marked final.
        int MLSTinProductNumber = form.getElement("SelectionNumber").getRenderableValue().toUpperCase().indexOf("TEMP"); // JR - ITS #529 MLST");
        boolean isFinal = false;
        String printOption = form.getStringValue("printOption");
        if (!printOption.equalsIgnoreCase("Draft") && (MLSTinProductNumber==-1) )
          isFinal = true;
        // JO 12/13/02
%>

<%@ include file="template-top-html.shtml"%>

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
  var form;
  var sort = "<%= MilestoneConstants.CMD_BOM_SORT%>";
  var sortGroup = "<%= MilestoneConstants.CMD_BOM_GROUP%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_BOM_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_SELECTION%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_BOM_EDITOR) %>";
  var save = "<%= MilestoneConstants.CMD_BOM_EDIT_SAVE%>";
  var saveSend = "<%= MilestoneConstants.CMD_BOM_EDIT_SAVE_SEND%>";
  var saveNew = "<%= MilestoneConstants.CMD_BOM_EDIT_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_BOM_EDITOR%>";
  var search = "<%= MilestoneConstants.CMD_BOM_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";
  var editCopy = "<%= MilestoneConstants.CMD_BOM_EDIT_COPY %>";
  var pasteCopy = "<%= MilestoneConstants.CMD_BOM_PASTE_COPY %>";
 /*
 // var pasteSave = "<%= MilestoneConstants.CMD_PFM_PASTE_SAVE %>";
 */
 var sendEmail = "<%= MilestoneConstants.CMD_BOM_SEND_EMAIL%>"


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


function helpConext()
  {
    callHelp('Help/','BOM_Form.htm');
    mtbHelp.reset();
  }//end function helpContext

function processLoad(pFocusField)
{
  focusField(pFocusField);
  form = document.forms[0];
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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>bom-editor-javascript.js"></script>

<script type="text/javascript" language="JavaScript">

function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_BOM_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

function toggleSearch()
{
  toggle( 'SearchLayer', 'ArtistSearch' );
}


//jo - ITS#1101 - 6/21/2005 noUnitsOrTBS(this, numberUnitsIsZero, statusIsTBS)
function noUnitsOrTBS(ctrl, numberUnitsIsZero, statusIsTBS){
	if ( ctrl.value == 'Final') {
                if (numberUnitsIsZero){
		        alert("A BOM cannot be changed to 'Final' without any '# of Units'.");
                        if (document.forms[0].printOption[0])
		           document.forms[0].printOption[0].checked = true;
		        return false;
                }
                if (statusIsTBS){
                        alert("A product must be in 'Active' status with a street/ship date in order to change a BOM to Final");
                        if (document.forms[0].printOption[0])
                           document.forms[0].printOption[0].checked = true;
                        return false;
                }
	}
	return true;
}// end noProjectID

// jo - ITS# 520 3/18/2003
function checkFinalUnitsAndSubmit(){

	//If (MLSTinProductNumber!=-1) then draft so allow save regardless of number of units
	<% if (MLSTinProductNumber!=-1) { %>
		submitSave();
		return true;
	<% } else { %>

		//check if draft in radio button
		if(document.forms[0].printOption[0] != null && document.forms[0].printOption[0].checked == true){
			submitSave();
		} else {
			//radio button set to final so check units
         		<%
			String units = "";
			units = form.getRenderableValue("UnitsPerPackage");
			if ( units.equals("0") || units.equals("") ){ %>
				alert("A BOM cannot be changed to 'Final' without any '# of Units'.");
                                if (document.forms[0].printOption[0])
				  document.forms[0].printOption[0].checked = true;
			 <% } else { %>
				submitSave();
			 <% } %>
		 }

	 <% } %>

}


</script>

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("searchArray") %>
</script>

<link rel=stylesheet type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>
<script for=document event=onkeydown language="JavaScript">
checkShortcut();
</script>

<!-- JR - ITS #70 -->
<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>
<!-- JR - ITS #70 -->

<body topmargin="0" leftmargin="0" onLoad="processLoad('Date');">



<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>

<!-- msc 12/13/01 -->
<input type="hidden" name="alphaGroupChr" value="">

<!-- msc 08/28/03 -->
<%
if(form.getElement("selectionID") != null)
{
  %><%=form.getElement("selectionID")%>
<%
}
%>
<!-- msc 08/28/03 -->

<!-- msc 09/10/03 -->
<%= form.getElement("bomLastUpdateCheck") %>


<% if (form.getElement("copyPaste") != null)
 		{
 %>
		<%= form.getElement("copyPaste") %>
<%}%>

<%
String alertMessage = (String)context.getDelivery("AlertMessage");
String sendMailStatus = (String)context.getDelivery("SendMailStatus");
%>

<table width="740" cellpadding="0" border="0">
<tr valign="top" align="left">
  <td width="280">
      <!-- This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here. -->
      <%@ include file="include-newSelection.shtml" %>
  </td>

  <td rowspan="2" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>
  <td width="150"><span class="title">Bill of Materials</span></td>
  <td width="75">
  <%

     if (  !form.getStringValue("cmd").equals(MilestoneConstants.CMD_BOM_EDIT_NEW) &&
           !form.getStringValue("cmd").equals(MilestoneConstants.CMD_BOM_PASTE_COPY)
        )
     {%>
<div id="printWindow"  align="center">
<a href="home?cmd=<%=MilestoneConstants.CMD_BOM_PRINT_PDF%>">
<!--  PRINT TO PDF -->
<img name="Print" id="Print" src="<%=inf.getImageDirectory()%>Print_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
</a>
</div>
   <%}%>
	</td>
  <td width="75">
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


	<%} %>
		</div>
	</td>
	<td width="185">
         <div id="sendOptionDiv">
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
       	  The include just gets and puts the code here as it was written here. */%>
           <td valign="top">
       	   <%@ include file="include-selection-notepad.shtml" %>
           </td>
		<% //===============end selection navigation panel=========================== %>
	<% } //end if notepadWidth == 0 %>


	<% //start bom form %>

	<!-- JR - ITS #444 -->
	<% if (currentSelection.getIsDigital()) { %>
		<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" align="left" colspan="6" width="100%">
	<% } else { %>
		<td bgcolor="lavender" valign="top" colspan="6" width="100%">
	<% } %>
	<!-- JR - ITS #444 -->


     <table width="100%" height="100%" cellpadding="5" border="0">
      <tr>
        <td valign="top">
     	<table width=100% class="tightDetailList" border="0">

<!-- //  msc add modification to make copy/paste images hidden...this functionality now handled by new user fields -->
<!-- //  msc add modification to make copy/paste images hidden...this functionality now handled by new user fields -->
		<tr>
			<td valign="top">
				<table width="100%" class="tightDetailList">
<!-- move here for better alignment -->
<!-- // jo ITS #43 add Copy/Paste feature -->
			  <tr>
				<td colspan=3>
        <%if(form.getRenderableValue("copyPaste") != null && form.getRenderableValue("copyPaste").equals("copy"))
        {%>
        <img src="<%= inf.getImageDirectory() %>copy.gif" onclick="submitCopy();" onDblClick="submitCopy();">
      <%}
        else if(form.getRenderableValue("copyPaste") != null && form.getRenderableValue("copyPaste").equals("paste"))
        {%>
         <img src="<%= inf.getImageDirectory() %>paste.gif" onclick="submitPaste();" onDblClick="submitPaste();">
        <%}%>
                               </td>
<!-- // jo add Copy/Paste feature -->
                               <td class="label" colspan=2>

                     <% if (MLSTinProductNumber!=-1) {%>
                     	Draft
		     <% } else { %>
			<%= form.getElement("printOption") %>
		     <% } %>
                                </td>

                                <td nowrap colspan=2>
                                <!-- JR - ITS 702 -->
                    <%= form.getElement("IsAdded") %> #&nbsp;&nbsp;&nbsp;
                    <%= form.getElement("ChangeNumber") %>
                                </td>
                                <td>
                                </td>
			  </tr>
              <!-- // msc 1030 _____________________________________ -->
                <tr class="divider">
        	  <td colspan="8" align="middle" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
                </tr>
              <!-- // msc 1030 ------------------------------------------->
<!-- move here for better alignment -->
				<tr>
					<td class="label" colspan="2">Date</td>
					<td><%= form.getElement("Date") %></td>
					<td class="label" colspan="6">
					</td>

				</tr>
				<tr class="divider">
					<td colspan="8" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
				</tr>
				<tr>
					<td class="label" colspan="2">Submitted By</td>
          <td><%= form.getElement("Submitter") %></td>
          <td class="label" colspan="2">Phone</td>
          <td colspan="3"><%= form.getElement("Phone") %></td>
				</tr>
        <tr>
        	<td class="label" colspan="2">Email</td>
          <td><%= form.getElement("Email") %></td>
          <td colspan="5">&nbsp;</td>
        </tr>
        <tr class="divider">
        	<td colspan="8" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
        </tr>

        <!-- JR ITS 572 -->
		<tr>
        	<td class="label" colspan="2">Artist</td>
        	<td><%= form.getRenderableValue("Artist") %></td>
        	<td class="label" colspan="2">Release Type</td>
        	<td colspan="4"><%= form.getRenderableValue("IsRetail") %></td>
		</tr>

		<tr>
        	<td class="label" colspan="2">Title</td>
        	<td><%= form.getRenderableValue("Title") %></td>
            <td class="label" colspan="2" nowrap>Street/Ship Date</td>
            <td nowrap colspan="4"><%= form.getRenderableValue("DueDate") %></td>
		</tr>

		<tr>
        	<td class="label" colspan="2" nowrap>Releasing Family</td>
        	<td><%= form.getRenderableValue("ReleasingComp") %></td>
<!-- JR - ITS 702 -->
        	<td class="label" colspan="2">Status</td>
        	<td><%= form.getRenderableValue("status") %></td>
		</tr>

		<tr>
        	<td class="label" colspan="2">Label</td>
        	<td><%= form.getRenderableValue("Label") %></td>
<!-- MC - ITS 969 Added UPC code to screen -->
<!--        	<td class="label" colspan="2"></td>
        	<td colspan="4"></td>  -->
		<td class="label" colspan="2" nowrap>UPC</td>
		<td align="left"><%= form.getRenderableValue("upc")%></td>
		</tr>

		<tr>
        	<td class="label" colspan="2">Imprint</td>
        	<td><%= form.getRenderableValue("imprint") %></td>
<!-- JR - ITS 702 -->
            <td class="label" colspan="2" nowrap>Prefix/Local Prod #</td>
            <td nowrap width=120 colspan="4"><%= form.getRenderableValue("SelectionNumber") %></td>
		</tr>

		<tr>
        <!-- JR ITS 572 -->

        <tr class="divider">
         	<td colspan="8" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
        </tr>


				<%

					if (currentSelection.getSelectionConfig().getSelectionConfigurationAbbreviation().equals("CAS"))
					{
				%>
				<tr>
					<td class="label" colspan="2">CASSETTE</td>
					<% // ITS 541: bold # of Units, shift subsequent fields to next column%>
					<td>
						<b># of Units</b>&nbsp;&nbsp;<%= form.getRenderableValue("UnitsPerPackage") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td colspan="5">
				    Run Time(s) <%= form.getElement("Runtime") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%= form.getElement("UseNoShrinkWrap") %>Shrink Wrap
					</td>
				</tr>

        <% //include file %>
					<%@ include file="include-bom-cassette.shtml" %>
				<% //end include file %>

				<%}
					else if (currentSelection.getSelectionConfig().getSelectionConfigurationAbbreviation().equals("VIN"))
					{
				 %>
				 <tr>
					<td class="label" colspan="2">VINYL</td>
					<% // ITS 541: bold # of Units, shift subsequent fields to next column%>
					<td>
						<b># of Units</b>&nbsp;&nbsp;<%= form.getRenderableValue("UnitsPerPackage") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td colspan="5">
						<%//= form.getElement("Configuration") %>
						<%= form.getElement("UseNoShrinkWrap") %>Shrink Wrap
					</td>
				</tr>
        <% //include file for vinyl %>
					<%@ include file="include-bom-vinyl.shtml" %>
				<% //end include file %>


				<%}
                                  else
                                  {
                                        String DiskLabel = "DISC";
					if (currentSelection.getSelectionConfig().getSelectionConfigurationAbbreviation().equals("DVV")
                                            && (bom == null || !bom.getFormat().equalsIgnoreCase("CDO"))) // MSC ITS 112 check for CD override
					{
                                          DiskLabel = "DVD Video";
					}
				 %>
				<tr>
				<td class="label" colspan="2"><%=DiskLabel%></td>
				<% // ITS 541: bold # of Units, shift subsequent fields to next column%>
				<td>
					<b># of Units</b>&nbsp;&nbsp;<%= form.getRenderableValue("UnitsPerPackage") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td colspan="5">
					<%= form.getElement("HasSpineSticker") %>Spine Sticker &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%= form.getElement("UseNoShrinkWrap") %>Shrink Wrap
				</td>
			</tr>
       <% //include file for disc%>
				<%@ include file="include-bom-disc.shtml" %>
			<% //end include file %>
				<%} %>

				<tr>
					<td id="specialIntructionsBox" class="label" colspan="2">Special Instructions</td>
          <td colspan="6"><%= form.getElement("SpecialInstructions") %></td>
        </tr>
        </table>
			</td>
		</tr>
    <tr>
		  <td valign="bottom" colspan="5">
			<table>
			  <tr>
				<td class="label">Last Updated: </td>
				<td><%= form.getRenderableValue("lastUpdatedDate")%></td>
				<td><%= form.getRenderableValue("lastUpdatedBy")%></td>
                    <% //ITS 42811
                    if (form.getElement("lastLegacyUpdateDate") != null && form.getRenderableValue("lastLegacyUpdateDate").length() > 0)
                    {%>
                                <td class="label">Last Legacy Update: </td>
				<td><%= form.getRenderableValue("lastLegacyUpdateDate")%></td>
                    <%}%>
			  </tr>
			</table>
		  </td>
		</tr>
        <tr class="divider">
         	<td colspan="6" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
        </tr>

          </table>
	</td>
    </tr>

<!-- JR - ITS #544 -->
<tr>
	<td>
	<table width=100% border="0">
		<tr valign="top">
		  <td align="center">
		  <%

		     if (  !form.getStringValue("cmd").equals(MilestoneConstants.CMD_BOM_EDIT_NEW) &&
		           !form.getStringValue("cmd").equals(MilestoneConstants.CMD_BOM_PASTE_COPY)
		        )
		     {%>
		<div id="printWindow2"  align="center">
		<a href="home?cmd=<%=MilestoneConstants.CMD_BOM_PRINT_PDF%>">
		<!--  PRINT TO PDF -->
		<img name="Print" id="Print" src="<%=inf.getImageDirectory()%>Print_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
		</a>
		</div>
		   <%}%>
			</td>
		  <td align="center">
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

			<%} %>
				</div>
			</td>
		</tr>
	</table>
	</td>
   </tr>
 </table>

</tr>
</table>

<% //DIV definitions %>
<% //start search div %>
<%@ include file="selection-search-elements.shtml"%>
<% //end search div %>

<% // MSC ITS4 BOM/PFM email %>
<%@ include file="include-email-distribution.shtml"%>

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
		<input type="button" name="ok" value="OK" onClick="JavaScript:toggle( 'SuccessLayer', '' )" style="width:70px">
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
		<input type="button" name="ok" value="OK" onClick="JavaScript:toggle( 'FailureLayer', '' )" style="width:70px">
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


<% //end DIV DEFINITIONS %>

<!-- Store status and release date to check when changing from 'Draft' to 'Final' jo - ITS#1101 - 6/21/2005-->
<input type=hidden name="status" value="<%= form.getRenderableValue("status")%>">

<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>

<%

if (sendMailStatus != null)
{
  if (sendMailStatus.equalsIgnoreCase("1"))
  {
    out.println("<SCRIPT>");
    out.println("toggle( 'SuccessLayer', '' );");
    out.println("</SCRIPT>");
  }
  else
  {
    out.println("<SCRIPT>");
    out.println("toggle( 'FailureLayer', '' );");
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

