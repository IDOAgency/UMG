<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
   */
%>

<%@ include file="template-top-page.shtml"%>

<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Report Config Editor";
%>

<%@ include file="template-top-html.shtml" %>
<script>
function HideLayer()
{
 mtbSearch.click();
 toggle( 'SearchLayer', 'FamilyDescriptionSearch');
}
</script>
<script type="text/javascript" language="JavaScript">
//global variables needed in include report-config-editor-javascript.js
  var sort = "<%= MilestoneConstants.CMD_REPORT_CONFIG_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_REPORT_CONFIG_EDITOR)%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_REPORT_CONFIG_EDIT_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_REPORT_CONFIG_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_REPORT_CONFIG_EDIT_SAVE_NEW%>";
  var editNew = "<%= MilestoneConstants.CMD_REPORT_CONFIG_EDIT_NEW%>";
  var search = "<%=MilestoneConstants.CMD_REPORT_CONFIG_SEARCH%>";

  //notepad variables
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_REPORT_CONFIG%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";

function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_REPORT_CONFIG_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";

} //end function submitResize()

function processLoad(pFocusField)
{
  focusField(pFocusField);
}
</script>

<%  // include js files holding javascript functions %>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>report-config-editor-javascript.js"></script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% /* start body */ %>
<body topmargin="0" leftmargin="0" onLoad="processLoad('Description');">

<%= form.renderStart() %>
<%= form.getElement("cmd")%>

<%if (form.getElement("OrderBy") != null)
    {%>
<%= form.getElement("OrderBy")%>
<%}%>

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

<table width="780" cellpadding="0" border="0">
<tr valign="middle" align="left">
	<td width="280">
		<div align="left">
    	<a href ="JavaScript:submitResize()"
          onMouseOver="Javascript:mtbToggle.over();return true;"
          onMouseOut="Javascript:mtbToggle.out()"
          onClick="Javascript:mtbToggle.click(); return true;">
          <img name="Toggle" id="Toggle" src="<%= inf.getImageDirectory() %>Toggle_On.gif" width="27" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
    	</a>
      <script type="text/javascript" language="JavaScript">
      	var mtbToggle = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Toggle', "JavaScript:submitResize()", 27, 14);
			</script>
		</div>
	</td>
	<td rowspan="2" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>
	<td width="170"><span class="title">Report Config.</span></td>
  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
		<div align="center">
			<a href="JavaScript:submitSave('Save')"
			 onMouseOver="Javascript:mtbSave.over();return true;"
			 onMouseOut="Javascript:mtbSave.out()"
			 onClick="Javascript:mtbSave.click(); return true;">
			 <img name="Save" src="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
			</a>
			<script type="text/javascript" language="JavaScript">
			  var mtbSave = new ToggleButton(document,'<%= inf.getImageDirectory() %>','Save','JavaScript:submitSave("Save")',66,14);
			</script>
		</div>
	</td>
	<td width="85">
		<div align="center">
			<a href="JavaScript:submitNew('New')"
			 onMouseOver="Javascript:mtbNew.over();return true;"
			 onMouseOut="Javascript:mtbNew.out()"
			 onClick="Javascript:mtbNew.click(); return true;">
			 <img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
			</a>
			<script type="text/javascript" language="JavaScript">
			  var mtbNew = new ToggleButton(document,'<%= inf.getImageDirectory() %>','New','JavaScript:submitNew( "New" )',66,14);
			</script>
		</div>
	</td>
	<td width="85">
		<div align="center">
				<a href="JavaScript:submitDelete('Delete')"
			 onMouseOver="Javascript:mtbDelete.over();return true;"
			 onMouseOut="Javascript:mtbDelete.out()"
			 onClick="Javascript:mtbDelete.click(); return true;">
			 <img name="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
			</a>
			<script type="text/javascript" language="JavaScript">
			  var mtbDelete = new ToggleButton(document,'<%= inf.getImageDirectory() %>','Delete','JavaScript:submitDelete("Delete")',66,14);
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
	<td valign="top">
  <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel

  	   /* ===============start users navigation panel===========================
  	     This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here. */
	%>
       	 <%@ include file="include-report-config-security-notepad.shtml" %>

 		<% /* ===============end selection navigation panel=========================== */%>
    <%} //end notepad vis check
    %>
	</td>

 <% /* start release week form */ %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td valign="top" width="100%">
				<table width="100%">
				<tr>
					<td class="label">Name</td>
          <td>
						<% if (form.getRenderableValue("cmd").equals("report-config-editor"))
						 {
						%>
							<%= form.getRenderableValue("ReportName")%>
						<%}
							else
							{
						%>
							<%= form.getElement("ReportName") %>
						<%} %>
					</td>
				</tr>
				<tr>
					<td class="label">Description</td>
					<td>
							<%= form.getElement("Description") %>
					</td>
				</tr>
				<tr>
					<td class="label">Type</td>
					<td>
							<%= form.getElement("ReportType") %>
					</td>
				</tr>
				<tr>
					<td class="label">Format</td>
					<td>
						 <%= form.getElement("ReportFormat") %>
					</td>
				</tr>
				<% if (form.getRenderableValue("cmd").equals("report-config-editor"))
						 {
						%>
				<tr>
					<td class="label">Status</td>
					<td><%= form.getElement("ReportStatus") %></td>
				</tr>
				<%} %>
				<tr>
					<td class="label">Owner</td>
					<td><%= form.getElement("ReportOwner") %></td>
				</tr>
				<tr>
					<td class="label">Path</td>
					<td>
							<%= form.getElement("Path") %>
					</td>
				</tr>

				<tr>
					<td class="label">File Name</td>
					<td>
							<%= form.getElement("FileName") %>
					</td>
				</tr>
        <% if (form.getRenderableValue("cmd").equals("report-config-editor"))
					 {
			  %>
        <tr>
					<td class="label">SubReport Name</td>
					<td>
						 <%= form.getElement("SubRepName") %>
					</td>
				</tr>
        <%}%>
                                <tr>
                                         <td class="label">Product Type</td>
                                         <td>
                                                 <%= form.getElement("ProductType") %>
                                        </td>
                                </tr>
				<tr>
					<td colspan="2">

						<% if (form.getRenderableValue("cmd").equals("report-config-editor"))
						 {
						%>
						<table class="detailList" width="100%" border="1" cellspacing="0">
						<tr>
							<th>Ask for</th>
							<th>Short Description</th>
							<th>Type/Example</th>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("BeginDate") %></td>
							<td nowrap><a href="Javascript:showDescription( 'The start date of the date ran' )">Start date of the date range</a></td>
							<td nowrap>Date Input</td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("EndDate") %></td>
							<td nowrap><a href="Javascript:showDescription( 'The finish date of the date ra' )">End date of the date range</a></td>
							<td nowrap>Date Input</td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("Region") %></td>
							<td nowrap><a href="Javascript:showDescription( 'The region to which the releas' )">Region to which it belongs</a></td>
							<td nowrap>West/East/All</td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("Family") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Family' )">Family (owner) to select from</a></td>
							<td nowrap><%= form.getElement("UML") %></td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("Environment") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Environment')">Environment to select from</a></td>
							<td nowrap><%= form.getElement("UML3") %></td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("Company") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Company' )">Company to select from</a></td>
							<td nowrap><%= form.getElement("UML2") %></td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("Label") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Label' )">Label to select from</a></td>
							<td nowrap><%= form.getElement("PUB") %></td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("LabelContact") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Contact' )">Contact</a></td>
							<td nowrap><%= form.getElement("LabelContactDropDown")%></td>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("CompleteKeyTask") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Key Task' )">List only completed key tasks</a></td>
							<td nowrap>On / Off</td>
						</tr>
            <tr>
							<td nowrap><%= form.getElement("ParentsOnly") %></td>
							<td nowrap><a href="Javascript:showDescription( 'ParentsOnly' )">&nbsp;</a></td>
							<td nowrap>On / Off</td>
						</tr>
            <tr>
							<td nowrap><%= form.getElement("ReleaseType") %></TD>
							<td nowrap><a href="Javascript:showDescription( 'Release Type' )" >Release type to select from</a></td>
							<td nowrap>Commercial/Promotial/All</TD>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("Status") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Status' )" >Release status to select from</a></td>
							<td nowrap>Closed/Active/All</TD>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("Artist") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Artist' )" >Name of the artist to enter</a></td>
							<td nowrap>Text Input</TD>
						</tr>
						<tr>
							<td nowrap><%= form.getElement("TaskOwner") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Task Owner' )" >UML Dates</a></td>
							<td nowrap>UML/Label/All</td>
						</tr>
						<tr>
            <!-- it is the previous fututre1 -->
							<td nowrap><%= form.getElement("UmlContact") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Uml Contact' )" >&nbsp;</a></td>
							<td nowrap><%= form.getElement("UmlContactDropDown")%></td>
						</tr>
            <tr>
							<td nowrap><%= form.getElement("Future2") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Future use' )" >Future use</a></td>
							<td nowrap>On / Off</td>
						</tr>

            <tr>
							<td nowrap><%= form.getElement("BeginDueDate") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Begin due date' )" >&nbsp;</a></td>
							<td nowrap>Date Input</td>
						</tr>
            <tr>
							<td nowrap><%= form.getElement("EndDueDate") %></td>
							<td nowrap><a href="Javascript:showDescription( 'End due date' )" >&nbsp;</a></td>
							<td nowrap>Date Input</td>
						</tr>
            <tr>
							<td nowrap><%= form.getElement("BeginEffectiveDate") %></td>
							<td nowrap><a href="Javascript:showDescription( 'Begin effective date' )" >&nbsp;</a></td>
							<td nowrap>Date Input</td>
						</tr>
            <tr>
							<td nowrap><%= form.getElement("EndEffectiveDate") %></td>
							<td nowrap><a href="Javascript:showDescription( 'End effective date' )" >&nbsp;</a></td>
							<td nowrap>Date Input</td>
						</tr>

                                                 <!-- msc Item 1916 -->
						<tr>
							<td nowrap><%= form.getElement("Configuration") %></td>
							<!-- JR ITS - 277 -->
							<td><a href="Javascript:showDescription( 'Format' )">Format to select from</a></td>
							<td><%= form.getElement("configurationList") %></td>
						</tr>
                                                 <!-- msc Item 1916 -->


                        <!--jr Item 39 -->
						<tr>
							<!-- JR ITS - 277 -->
							<td nowrap><%= form.getElement("Subconfiguration") %></td>
							<td><a href="Javascript:showDescription( 'Sub-Format' )">Sub-Format to select from based upon the selected Format</a></td>
							<td>&nbsp;</td>
						</tr>
                        <!-- jr Item 39 -->

                                                <!-- jo Item 1850 -->
						<tr>
							<td nowrap><%= form.getElement("ScheduledReleases") %></td>
							<td><a href="Javascript:showDescription( 'Scheduled Releases' )">Only Releases with Schedules applied</a></td>
						        <td>On / Off</td>
                                                </tr>
                                                 <!-- jo Item 1850 -->

                        <!-- jr Item 37 -->
						<tr>
							<td nowrap><%= form.getElement("AddsMovesBoth") %></td>
							<td><a href="Javascript:showDescription( 'Adds Moves Both' )">&nbsp;</a></td>
						    <td>On / Off</td>
                        </tr>
                                               <!-- jo Item 476 -->
						<tr>
							<td nowrap><%= form.getElement("PhysicalProductActivity") %></td>
							<td><a href="Javascript:showDescription( 'Physical Product Activity' )">&nbsp;</a></td>
						    <td>On / Off</td>
                                              </tr>
                                               <!-- jo Item 476 -->

                                                <!-- jo Item 335
						<tr>
							<td><%//= form.getElement("ProductType") %></td>
							<td><a href="Javascript:showDescription( 'Product Type' )">Product Type</a></td>
						        <td>Physical / Digital/ Both</td>
                                                </tr>
                                                jo Item 335 -->

                                               <!-- mc Item 1069 -->
						<tr>
						<td nowrap><%= form.getElement("DistCo") %></td>
						<td nowrap><a href="Javascript:showDescription( 'Distribution Company' )" >Distribution Company</a></td>
						<td nowrap><%= form.getElement("DistCoNames")%></td>
                                                </tr>
                                               <!-- mc Item 1069 -->

						</table>
						<%}
							else
							{ %>
						<table class="detailList" width="100%" border="1" cellspacing="0">
						<tr><th colspan="2">Ask for</th><tr>
						<tr>
							<td><%= form.getElement("BeginDate") %></td>
							<td><%= form.getElement("EndDate") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("Region") %></td>
							<td><%= form.getElement("Family") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("Environment") %></td>
							<td><%= form.getElement("Company") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("Label") %></td>
							<td><%= form.getElement("LabelContact") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("CompleteKeyTask") %></td>
							<td><%= form.getElement("ParentsOnly") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("ReleaseType") %></td>
							<td><%= form.getElement("Status") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("Artist") %></td>
							<td><%= form.getElement("TaskOwner") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("UmlContact") %></td>
							<td><%= form.getElement("Future2") %></td>
						</tr>
						<tr>
							<td><%= form.getElement("BeginDueDate") %></td>
							<td><%= form.getElement("EndDueDate") %></td>
						</tr>
						<tr>
                            <!-- msc Item 1916 -->
                            <td><%= form.getElement("Configuration") %></td>
                             <!-- msc Item 1916 -->
                            <!-- JR ITS 277 -->
                            <td><%= form.getElement("Subconfiguration") %></td>
                             <!-- JR ITS 277 -->

						</tr>
						<tr>
							<!-- JR - ITS#37 -->
							<td><%= form.getElement("AddsMovesBoth") %></td>
                             <!--  // jo Item 1850 -->
							<td><%= form.getElement("ScheduledReleases") %></td>

							<!-- JR - ITS#37 -->
						</tr>
						<tr>
							<!-- jo - ITS# 476 -->
							<td><%= form.getElement("PhysicalProductActivity") %></td>
                                                        <!--  // jo Item 1850 -->
							<td>&nbsp;</td>
							<!-- jo - ITS# 476 -->
                                                <!--  // jo Item 1850 -->

						<!--<tr>
							<td><%//= form.getElement("ProductType") %></td>
							<td>&nbsp;</td>
                                                </tr>-->

                                              <!-- jo Item 476 -->
						<tr>
						<td><%= form.getElement("DistCo") %></td>
                                               </tr>
                                               <!-- jo Item 476 -->

						</table>
						<%} %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td valign="bottom">
	<table><tr>
	<% if (form.getRenderableValue("cmd").equals("report-config-editor"))
		 {
		%>
	<td class="label">Last Updated: </td>
  <td><%= form.getRenderableValue("lastUpdatedDate")%></td>
  <td><%= form.getRenderableValue("lastUpdatedBy")%></td>
	<%}%>

</tr></table>

	</td></tr>
	</td>
</tr></table>
</td></tr></table>

<%/* DIV definitions
     start search div */%>
<div class="search" id="SearchLayer" onKeyPress="checkForEnter('submitSearch()');" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr><td>
	<table width="100%">
	<tr><th colspan="2">Report Config Search</th></tr>
	<tr>
		<td class="label">Name</td>
		<td><%= form.getElement("ReportNameSearch") %></td>
	</tr>
	<tr>
		<td class="label">File Name</td>
		<td><%= form.getElement("FileNameSearch") %></td>
	</tr>
	<tr><td colspan="2"><INPUT type=button value="Submit Search" onClick="submitSearch()">
		  <colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
</td></tr>
	</table>
</td></tr></table>
</div>
<%/* end search div
     END DIV DEFINITIONS */%>

<%= form.renderEnd() %>

<%@ include file="include-bottom-html.shtml"%>


