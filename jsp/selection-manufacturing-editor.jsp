<%
	/*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message

    *   2003-01-09 - lg - ITS 877 Added calendar group to getDayType method
	*/
%>
<%@ include file="template-top-page.shtml"%>
<%@ include file="callHelp.js"%>


<%
	// holds the selected release from the nav menu that the user clicked and passes it to the hidden
	// field selectionID in order to be preserved when the form submits. The same with the action and
	// subAction. Also, defined here are those varaibles which are accessible from the entire page
	// when needed.
  String artistName = "";
  String title = "";
  String upc = "";
  String label = "";
  String status = "";
  String typeConfig = "";
  String unitsPerSet = "";
  String releaseWeek = "";
  String prefix = "";

  String projectId = "";
  String streetDate= "";
  String intlDate= "";
  String selectionNo = "";
  String pd = "";
  String releasingFamily = "";
  String hold = "";
  String specialPackage = "";
  Vector plants = new Vector();

  boolean saveVisible = false;
  saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");

  //Get the selection object
  Selection currentSelection = (Selection)context.getSessionValue("Selection");
  //Selection currentSelection = (Selection)MilestoneHelper.getNotepadFromSession(MilestoneConstants.NOTEPAD_SELECTION, context).getSelected();


  if(currentSelection != null)
  {
    artistName = currentSelection.getArtist();
    title = currentSelection.getTitle();
    upc = currentSelection.getUpc();
    label = (String)context.getDelivery("label");
    status = (String)context.getDelivery("status");
    typeConfig = (String)context.getDelivery("typeConfig");
    //unitsPerSet = Integer.toString(currentSelection.getNumberOfUnits());
    unitsPerSet = (String)context.getDelivery("numberOfUnits");


    if(currentSelection.getPrefixID() != null)
      prefix =  SelectionManager.getLookupObjectValue(currentSelection.getPrefixID());

    projectId = currentSelection.getProjectID();
    streetDate = MilestoneHelper.getFormatedDate(currentSelection.getStreetDate());
    intlDate = MilestoneHelper.getFormatedDate(currentSelection.getInternationalDate());
    selectionNo = currentSelection.getSelectionNo();
    releasingFamily = (String)context.getDelivery("releasingFamily");
    pd = "";

    if(currentSelection.getPressAndDistribution())
      pd = "P&D";

    specialPackage = "";
    if(currentSelection.getSpecialPackaging())
      specialPackage = "Special Pkg";

    hold= "";
    if(currentSelection.getHoldSelection())
      hold = "Hold";

    if (currentSelection.getManufacturingPlants() != null)
      plants = currentSelection.getManufacturingPlants();
  }

	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Manufacturing Screen";

    //Used by include-selection-notepad
    String editorName = MilestoneConstants.CMD_SELECTION_MANUFACTURING_EDITOR;
%>

<% //includes the top header %>

<%@ include file="template-top-html.shtml"%>

<script type="text/javascript" language="JavaScript">
//global variables needed in include bom-editor-javascript.js
  var form;
  var sort = "<%= MilestoneConstants.CMD_SELECTION_MANUFACTURING_SORT%>";
  var sortGroup = "<%= MilestoneConstants.CMD_SELECTION_MANUFACTURING_GROUP%>";

  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_MANUFACTURING_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_SELECTION%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_MANUFACTURING_EDITOR) %>";
  var save = "<%= MilestoneConstants.CMD_SELECTION_MANUFACTURING_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_SELECTION_MANUFACTURING_EDIT_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_SELECTION_MANUFACTURING_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_SELECTION_MANUFACTURING_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";


  var addPlant = "<%= MilestoneConstants.CMD_SELECTION_MANUFACTURING_PLANT_ADD %>";
  var deletePlant = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_MANUFACTURING_PLANT_DELETE) %>";

function submitGroupXXX(alpha, colNo )
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



function processLoad(pFocusField)
{
  //focusField(pFocusField);
  // JR - ITS 842
  parent.top.bottomFrame.focus();
  form = document.forms[0];
}
  function helpConext()
  {
    callHelp('Help/','Manufacturing.htm');
    mtbHelp.reset();
  }//end function helpContext

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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>selection-manufacturing-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script language="JavaScript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_MFG_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

function submitDel(id)
{
  parent.top.bottomFrame.location = deletePlant + "&plantId=" + id;
} //end function submitResize()

function submitAdd()
{
  document.forms[0].cmd.value = addPlant;
  document.forms[0].submit();
} //end function submitAdd()
</script>

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>

<!-- JR - ITS #70 -->
<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<%= context.getDelivery("selectionArrays") %>
</script>
<!-- JR - ITS #70 -->

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>

<body topmargin="0" leftmargin="0" onLoad="processLoad('po_qty');">

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

<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%=form.getElement("OrderBy")%>
<%=form.getElement("new")%>

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
	<td width="170"><span class="title">Manufacturing</span></td>
  <td width="85"></td>
  <td width="85"></td>
  <td width="85">
   <div align="center" id="saveDiv">
    <%if(saveVisible)
     {%>
			<a href="JavaScript:submitSave('Save')"
	    	onMouseOver="Javascript:mtbSave.over();return true;"
	      onMouseOut="Javascript:mtbSave.out()"
	      onClick="Javascript:mtbSave.click(); return true;">
	      <img name="Save" id="Save" src="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace=0 align="absmiddle">
			</a>
	    <script type="text/javascript" language="JavaScript">
				var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', 'JavaScript:submitSave( "Save" )', 66, 14);
	    </script>
      <%}%>
		</div>
	</td>
  <td width="85"></td>
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

  <%  }//end if %>


	<% //start selection manufacturing form %>
	<!-- JR - ITS #444 -->
	<% if (currentSelection.getIsDigital()) { %>
		<td bgcolor="<%=MilestoneConstants.DIGITAL_PINK%>" valign="top" align="left" colspan="6" width="100%">
	<% } else { %>
		<td bgcolor="lavender" valign="top" align="left" colspan="6" width="100%">
	<% } %>
	<!-- JR - ITS #444 -->

	<!-- JR - ITS #579 -->
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
    	<td valign="top">
      	<table width="100%">
        <tr>

<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- BEGIN PHYSICAL MANUFACTURING SCREEN -->

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
                      if (form.getRenderableValue("comments") != null)
                        releaseComment = form.getRenderableValue("comments");
                      if (releaseComment != null && !releaseComment.equals("")) {
					%>
					<img  title="Selection Comments" src="<%=inf.getImageDirectory()%>afile.gif" onClick="toggle( 'commentLayer', 'releaseComment' );">
					<% } else { %>
						<img  title="Selection Comments" src="<%=inf.getImageDirectory()%>file.gif" onClick="toggle( 'commentLayer', 'releaseComment' );">
					<% } %>
					</td>

					<td class="label" nowrap></td>
					<td nowrap></td>

					<td class="label" >Status</td>
					<td><%
					SelectionStatus myStatus = null;
                    if (currentSelection != null)
                    {
                       myStatus = currentSelection.getSelectionStatus();

                         if (myStatus != null && myStatus.getAbbreviation() != null)
                         {%>
                           <%= (myStatus.getAbbreviation())%>
                    <%}
                    }%>
                    </td>
              </tr>

              <!-- Artist, Special Pkg, Hold, P&D/Dist. -->
              <tr>
                <td class="label">Artist</td>
                <td colspan=2>
                  <%
                  if (currentSelection != null)
                  {
                  if (currentSelection.getArtist() != null)
                  {%>
                    <%= currentSelection.getArtist()%>
                <%}
                }%>
                </td>
                <td nowrap class="label" align=right>
                <% if (currentSelection != null && currentSelection.getSpecialPackaging())
                {%>
                  <input type=checkbox title="Special Pkg - Read-Only Field" checked disabled>
                <%}
                else
                {%>
                  <input type=checkbox title="Special Pkg - Read-Only Field" disabled>
                <%}%>
                Special Pkg &nbsp;&nbsp;
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

              <!-- Title, ProjectID -->
              <tr>
                <td class="label">Title</td>
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

              <!-- Releasing Family, Prefix -->
              <tr>
				<td class="label" nowrap>Releasing Family</td>
				<td colspan=3><%= ReleasingFamily.getName(currentSelection.getReleaseFamilyId()) %></td>
				<td class="label" rowspan=2 valign=bottom nowrap>Prefix/<BR>Local Prod #</td>
				<td nowrap rowspan=2 valign=bottom><%=prefix%>&nbsp;<%=selectionNo%></td>
              </tr>

              <!-- Label, Local Prod # -->
              <tr>
                <td class="label" valign=bottom>Label</td>
                <%
                   Label myLabel = null;
                   if (currentSelection != null)
                     myLabel = currentSelection.getLabel();%>
                <td colspan=3 valign=bottom>
                <%if (myLabel != null)
                  {%>
                    <%= myLabel.getName()%>
                <%}%>
                </td>
              </tr>

              <!-- Imprint , UPC -->
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

              <!-- Distribution Company -->
              <tr>
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
<!-- END PHYSICAL MANUFACTURING SCREEN -->

		<tr bgcolor="white"><td colspan="7"></td></tr>
        <tr>
        	<td class="label">Mfg Comments</td>
          <td nowrap>
						<%
							// this checks to see what image to display
							// if there are existing mfg (order) comments: display afile.gif
							// if no mfg (order) comments exist: display file.gif
							String orderComments = "";
							orderComments = form.getRenderableValue("orderCommentHelper");

							if (orderComments != null && !orderComments.equals(""))
							{
						%>
						<img title="Manufacturing Comments" src="<%= inf.getImageDirectory() %>afile.gif" border="0" onClick="toggle( 'order_commentLayer', 'orderCommentHelper' )">
						<%}
						  else
							{
						%>
						<img title="Manufacturing Comments" src="<%= inf.getImageDirectory() %>file.gif" border="0" onClick="toggle( 'order_commentLayer', 'orderCommentHelper' )">
						<%}%>
					</td>
          <td></td>
        	<td><b>Distribution</b></td>
          <td><%= form.getElement("distribution") %></td>
        </tr>
        <tr>
        	<td><b>UML Contact</b></td>
          <td> <%= form.getElement("umlcontact") %></td>
          <td></td>

          <td><b># of Units </b></td>
          <td><%=unitsPerSet%></td>
       </tr>

<%
//////////////////////////////
%>
        <tr bgcolor="white"><td colspan="7"></td></tr>

        <tr >
        <td colspan=6 align="right">
<%if(saveVisible)
     {%>
	  <a href="JavaScript:submitAdd()"
	  onMouseOver="Javascript:mtbAddVendor.over();return true;"
	  onMouseOut="Javascript:mtbAddVendor.out()"
	  onClick="Javascript:mtbAddVendor.click(); return true;">
	  <img name="AddVendor" id="AddVendor" src="<%= inf.getImageDirectory() %>AddVendor_On.gif" width="96" height="14" border="0" hspace="0" vspace=0 align="absmiddle">
	 </a>
<% } %>
	 <script type="text/javascript" language="JavaScript">
	     var mtbAddVendor = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'AddVendor', 'JavaScript:submitAdd("True")', 96, 14);
	  </script>

        </td>
        </tr>

        <tr>
        <table border=1>
       <tr bgcolor="wheat">
       <td align="center" width="250"><b>Vendor/Plant</b></td>
       <td align="center" width="100"><b>P.O. Qty</b></td>
       <td align="center" class="label" width="100">Exploded Total</td>
       <td align="center" colspan=2 class="label" width="150">
       <table>
       <tr>
       <td align="center" class="label" width=80>
       Completed Qty
       </td>
       <td>
       </td>
       </table>
       </td>
      </tr>
<%
for (int plantCount = 0; plantCount < plants.size(); plantCount++)
{
      Plant plant = (Plant)plants.get(plantCount);
      int explodedTotal = 0;
	if( plant.getOrderQty() > 0 && currentSelection.getNumberOfUnits() > 0)
	  explodedTotal = plant.getOrderQty() * currentSelection.getNumberOfUnits();

%>
        <tr>
          <td><%= form.getElement("plant" + plantCount) %></td>
          <td><%= form.getElement("po_qty"+ plantCount) %></td>
          <td><%=explodedTotal%></td>
          <td><%= form.getElement("completed_qty"+ plantCount) %></td>
<% if(saveVisible)
     {%>
        <td><a href="javascript:submitDel(<%=plantCount%>);">del</a></td>
<%} %>
        </tr>
<%
}
%>

       </table>
<%
////////////////////////////////
%>

		</td>
	</tr>




		<tr><td valign="bottom"></td></tr>
    <tr>
		  <td valign="bottom" colspan="5">
			<table>
			  <tr>
				<td class="label">Last Updated: </td>
				<td><%= context.getDelivery("lastUpdateDate")%></td>
				<td><%= context.getDelivery("lastUpdateUser")%></td>
			  </tr>
			</table>
		  </td>
		</tr>


		</table>
	</td>
</tr>
</table>

<% //START DIV DEFINITIONS %>
<% //start search div %>
<%@ include file="selection-search-elements.shtml"%>
<% //end search div %>

<% //start comments div %>
<div id="commentLayer" class="search" style="visibility:hidden;width:250px; height:80px; z-index:3; left:100px; top:120px;">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr><td>
	<table width="100%">
	<tr>
		<td class="label">Comments<br><%= form.getElement("comments") %></td>
	</tr>
	<tr>
		<td>
			<!--<input type="button" name="saveButton" value="Save Comments" onClick="saveComments();">&nbsp;&nbsp;-->
			<input type="button" name="closeButton" value="Cancel" onClick="JavaScript:toggle( 'commentLayer', 'comments' );">
</td></tr></table>
</td></tr></table>
</div>
<% //end comments div %>

<% //start hold reason layer %>
<div id="holdReasonLayer" style="position:absolute; visibility:hidden; width:300px; height:180px; z-index:3; left:100px; top:150px;">
<table width="290" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat"><td>
<table width="290">
<tr>
	<td class="label">Hold Reason<BR><%= form.getElement("holdReason")%></td>
</tr>
<tr>
	<td><input type="button" name="closeHoldReason" id="closeHoldReason" value="Cancel" onClick="JavaScript:toggle('holdReasonLayer','holdReason')">
	</td>
</tr>
</table>
</td></tr></table>
</div>
<% //end hold reason layer %>

<% //start order comment layer %>
<div id="order_commentLayer" style="position:absolute; visibility:hidden; width:255px; height:60px; z-index:3; left: 100px; top: 70px;">
<table width="251" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat">
	<td>
		<table width="247">
		<tr>
			<td><b>Mfg Comments</b><br><%= form.getElement("orderCommentHelper") %></td>
		</tr>
		<tr>
			<td>
        <%if(saveVisible)
        {%>
				  <input type="button" name="saveOrderComment" value="Save Comments" onClick="saveOrderComments()">&nbsp;&nbsp;
      <%}%>
        <input type="button" name="closeButton" value="Cancel" onClick="toggle( 'order_commentLayer', 'orderCommentHelper' )">
      </td>
    </tr>
		</table>
	</td>
</tr>
</table>
</div>

<div style="visibility:hidden">
<!--<%= form.getElement("orderCommentHelper") %>-->
</div>

<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>



<% //end order comment layer %>
<% //END DIV DEFINITIONS %>

<%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>