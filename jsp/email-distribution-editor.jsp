<%
	 /*
	     this include (template-top-page.jsp) does the following:
	     extends com.universal.milestone.MilestoneJSP
	     import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
		gets the context, form formValidation and message
           Modifications:
           2003-03-11 - msc - ITS-439 Change company to environment
           2003-08-20 - msc - Added releasing family logic to screen
	*/
%>
<%@ include file="template-top-page.shtml"%>
<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Email Distribution Editor";

	// getting the login name
	User user = (User)context.getSessionValue("user");

  // get the email distribution object from the session
  int distId = -1;
  EmailDistribution emailDistr = (EmailDistribution)context.getSessionValue("emailDistribution");
  if(emailDistr != null)
    distId = emailDistr.getDistributionId();

   Vector cAcl = Cache.getInstance().getEnvironmentList();  // msc its-439

   // msc 05/14/03 AFE 2003 Get environments by family name
   cAcl = Cache.getInstance().getEnvironmentsByFamily();

  Vector uAcl = null;
  String showAssigned = (String)context.getSessionValue("showAssignedEmail");
  if(showAssigned == null)
     showAssigned = "ASSIGNED";   // default value

  if(showAssigned.equalsIgnoreCase("ASSIGNED"))
  {
     if(emailDistr != null)
     {
        uAcl = EmailDistributionManager.getAssignedEnvironments(emailDistr.getDistributionId());
         if(uAcl != null)
           uAcl =  MilestoneHelper.buildAssignedEnvironments(uAcl);  // sort user environments by family
     }
  }

%>

<%@ include file="template-top-html.shtml" %>
<script>
var distId = "<%=distId%>"
function HideLayer()
{
 mtbSearch.click();
 toggle( 'searchLayer', 'nameSrch');
}
  function helpConext()
  {
    callHelp('Help/','User_Info.htm');
    mtbHelp.reset();
  }//end function helpContext

// msc 05/14/03 AFE 2003

var showAssigned = "<%=showAssigned%>";

function submitGetAccess(accessStr)
{
  // alert("submitGetAccess = " +  accessStr.value + "\n" +
  //       editor + "\n" );
  parent.top.bottomFrame.location = editor + "&showAssignedEmail=" + accessStr.value;
} //end function submitGetAccess()


</script>
<script>var disableShortCuts = false; </script>
<STYLE TYPE="text/css">
 TD.secure    {visibility=visible}
 TABLE.secure {visibility=visible}
 DIV.secure {visibility=visible}
</STYLE>
<script type="text/javascript" language="JavaScript">
//global variables needed in include division-editor-javascript.js
  var form;
  var sort = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SORT)%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_EMAIL_DISTRIBUTION_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_EMAIL_DISTRIBUTION%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_EMAIL_DISTRIBUTION_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SAVE%>";
  var copy = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_COPY%>";
  var saveNew = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_NEW%>";
  var search = "<%= MilestoneConstants.CMD_EMAIL_DISTRIBUTION_SEARCH%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";


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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>email-distribution-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">

<%
//DO NOT MOVE RESIZE TO AN INCLUDE WITHOUT ASKING BRETT FIRST!!!!
%>
function submitResize()
{
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

function showEmailDistReleasingFamilyFrame(labelFamName, labelFamId,oObject)
{
    if(distId == -1)
    {
        alert("You can't assign an email distribution releasing families until new email user has been saved");
        return false
    }

     // move layer down
      if( window.event != null )
      {
       // mc 05/20/04  document.all.emailDistReleasingFamilyLayer.style.pixelTop = window.event.clientY;
       }
        showWaitMsg();  // mc its 966 show please wait message
        document.all.emailDistReleasingFamilyFrame.src = "<%=inf.getStandardDomain() + inf.getJspDirectory()%>"
                + "email-distribution-releasingFamilyFrame.jsp" + "?labelFamilyName=" + labelFamName + "&labelFamilyId=" + labelFamId;

        setInterval("repositionit()", 100); // mc 05/20/04 reposition the iframe as the scroll bar moves

} //end

//mc 05/20/04 reposition the releasing family iframe as the scroll bar moves
function repositionit()
{
  document.all.emailDistReleasingFamilyLayer.style.pixelTop = 60 + document.body.scrollTop;
}
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
    if(!disableShortCuts)   // msc
	checkShortcut();
</script>

<%
String focusField = "firstName";
if (form.getRenderableValue("cmd").equals("email-distribution-new"))
{
  focusField = "firstName";
}
%>

<body topmargin="0" leftmargin="0" onLoad="processLoad('<%=focusField%>');">
<%= form.renderStart()%>
<%=form.getElement("cmd")%>

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
		<div align="left" class=secure>
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
        <td width="170"><span class="title">Email Distribution</span></td>
        <td width="85"></td>

        <td width="85">
		<div align="left"  id="copyLayer" style="visibility:visible">
			<a href="JavaScript:submitCopy('Copy')"
          onMouseOver="Javascript:mtbCopy.over();return true;"
          onMouseOut="Javascript:mtbCopy.out()"
          onClick="Javascript:mtbCopy.click(); return true;">
          <img name="Copy" id="Copy" SRC="<%= inf.getImageDirectory() %>Copy_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript">
          var mtbCopy = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Copy', "JavaScript:submitCopy( 'Copy' )", 66, 14 );
        </script>
		</div>
	</td>

	<td width="85">
		<div align="center">
			<a href="JavaScript:submitSave('Save')"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript">
          var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
		</div>
	</td>

	<td class=secure width="85">
		<div align="center">
			<a href='JavaScript:submitNew( "New" )'
				onMouseOver="Javascript:mtbNew.over();return true;"
				onMouseOut="Javascript:mtbNew.out()"
				onClick="Javascript:mtbNew.click(); return true;">
        <img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
      </a>
      <script type="text/javascript" language="JavaScript">
      	var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', "JavaScript:submitNew( 'New' )", 66,14);
      </script>
		</div>
	</td>

	<td  class=secure width="85">
		<div align="center">
			<a href='JavaScript:submitDelete( "Delete" )'
 				onMouseOver="Javascript:mtbDelete.over();return true;"
 				onMouseOut="Javascript:mtbDelete.out()"
 				onClick="Javascript:mtbDelete.click(); return true;">
        <img name="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
      </a>
      <script type="text/javascript" language="JavaScript">
      	var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', 'JavaScript:submitDelete( "Delete" )', 66,14);
      </script>
		</div>
	</td>

<!--
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
-->

</tr>
<% if (formValidation != null)
     {%>
  <tr><td  class=secure colspan=6 height=10>&nbsp;</td></tr>
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
 <tr><td  class=secure colspan=6 height=10>&nbsp;</td></tr>
    <%}%>
<tr>
	<td valign="top">
  <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
  	<% /* ===============start email distribution navigation panel===========================
  	    This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here. */
		%>
       	 <%@ include file="include-email-distribution-notepad.shtml" %>

 		<% /* ===============end selection navigation panel=========================== */ %>
  <%}%>
	</td>

	<% //start email distribution
  %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="100%" height="100%" cellpadding="5" border="0">
		<tr>
			<td valign="top" width="100%">
				<table width="100%">
				<tr>
			<td nowrap class=secure ><b>First Name</b></td>
			<td colspan="2"><%=form.getElement("firstName")%></td>

                        <td nowrap class=secure ><b>Last Name</b></td>
                        <td colspan="2"><%= form.getElement("lastName") %></td>

	                <td class=secure><b><%=form.getElement("inactive")%></b></td>

                  </tr>

                            <tr>
                                    <td nowrap class=secure ><b>Email</b></td>
                                    <td colspan="3">
                                    <%= form.getElement("email") %></td>

                                    <td class="label">UMe Catalog</td>
    				    <td><%= form.getElement("Distribution") %></td>


                            </tr>

                            <tr>
                                  <td nowrap class=secure><b>Form Type</b></td>
                                    <td colspan="2" width="100%">
                                    <%= form.getElement("Pfm") %>
                                    <%= form.getElement("Bom") %>
                                    </td>

                                <td nowrap class=secure><b>Release Type</b></td>
                                    <td colspan="2" width="100%">
                                    <%= form.getElement("Promo") %>
                                    <%= form.getElement("Commercial") %>
                                    </td>
                            </tr>

                            <tr>
                                    <td class="label">Product Type</td>
                                   <td colspan="2" width="100%" >
                                      <%= form.getElement("ProductType") %>
                                    </td>
                            </tr>

                            </table>
                            <table width="100%"  class=secure >
                            <tr bgcolor="#ffffff">
                                    <td width="100%" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
                            </tr>
                            </table>
                            <table width="100%"  class=secure >
                            <tr>
                                    <td colspan="2" class="label">Email Distribution Environment</td>

                            </tr>
                            <tr>
                                  <%
                                    if(!showAssigned.equals("NEW") || distId != -1)
                                    {%>
	                 		<td colspan="1" class="label" width="25%">Family Display Mode</td>
					<td class="env" colspan="3" width="75%">
					<input type="radio" name="showAssigned0" onClick="submitGetAccess(this);" value="ASSIGNED" tabindex="10" title="Shows Families With Assigned Environments Only" >Assigned
					<input type="radio" name="showAssigned1" onClick="submitGetAccess(this);" value="ALL" tabindex="12" title="Shows All Familiies">All
			         	</td>
                                    <%}
                                    %>
                            </tr>
                 		<tr>
					<td class="label" colspan="1" width="25%" style="font-size: 8pt; font-weight:normal; color: red;">Assigned</td>
					<td class="label" colspan="1" width="25%" style="font-size: 8pt; font-weight:normal; color: black;">Unassigned</td>
				</tr>
                          	<tr>
					<td colspan="1" class="label" width="25%">&nbsp;</td>
					<td class="label" colspan="3" width="75%">&nbsp;</td>
				</tr>

        			<tr>
					<td colspan="1" class="label" width="25%">Label Family</td>
					<td class="label" colspan="1" width="25%">Releasing Family</td>
					<td class="label" colspan="2" width="50%"</td>
				</tr>
                            <%
                                String famName = "***";
                                int famRow = 0;
                                //out.println("<tr>");
                                String assigned = "font-size: 8pt; color: red;  font-family:";
                                String unAssigned = "font-size: 8pt; color: black;  font-family:";

                                 int c = 0;

                                 // if showing assigned environements only, use assigned environments vector
                                 if(showAssigned.equalsIgnoreCase("Assigned") && uAcl != null)
                                          cAcl = uAcl;

                                 for (int i=0; i < cAcl.size(); i++)
                                 {
                                      Environment environment = (Environment)cAcl.elementAt(i); //msc its-439

                                      // ****************************************
                                      // msc 05/01/03 ITS add family column
                                      // ****************************************
                                       if( !famName.equals(MilestoneHelper.getStructureName(environment.getParentID()) ) )
                                       {
                                           famName = MilestoneHelper.getStructureName(environment.getParentID());
                                           // releasing family logic - start

                                            // get releasing familes for this label family
                                             String releasingFamilyStr = "";
                                             Vector labelReleasingFamilies = null;

                                             if(emailDistr != null)
                                             {
                                                Hashtable releasingFamiliesHash = emailDistr.getReleasingFamily();

                                                if(releasingFamiliesHash != null)
                                                  labelReleasingFamilies =(Vector)releasingFamiliesHash.get(Integer.toString(environment.getParentID()));

                                                if(labelReleasingFamilies != null)
                                                {
                                                    releasingFamilyStr  = "";
                                                    for(int x=0; x < labelReleasingFamilies.size(); x++)
                                                    {
                                                        EmailDistributionReleasingFamily releasingFamily = (EmailDistributionReleasingFamily)labelReleasingFamilies.get(x);
                                                        if(releasingFamily != null)
                                                       {
                                                          releasingFamilyStr += releasingFamily.getFamilyAbbr();
                                                          releasingFamilyStr += "&nbsp;&nbsp&nbsp";
                                                       }
                                                   }
                                                }
                                                else // if not releasing family assigned, default to label family
                                                {
                                                   // skip UML and enterprise
                                                   if (!famName.trim().equalsIgnoreCase("UML") && !famName.equalsIgnoreCase("Enterprise")
                                                        && !famName.equalsIgnoreCase("eCommerce") )
                                                   {
                                                     // use abbreviation
                                                     if(environment.getParent() != null)
                                                        releasingFamilyStr = environment.getParent().getStructureAbbreviation();
                                                     else
                                                        releasingFamilyStr = famName;
                                                  }
                                                }
                                              }
                                            // releasing family logic - end

                                             out.println("</tr>");
                                            %>
                                             <tr  id="famHdr"  bgcolor="#cccccc" width="100%"   bcolor="#cccccc" cellspacing="0" cellpadding="0" border="0">

                                             <%
                                                // skip UML and enterprise
                                             if (!famName.trim().equalsIgnoreCase("UML") && !famName.equalsIgnoreCase("Enterprise")
                                                       && !famName.equalsIgnoreCase("eCommerce") )
                                             {%>

                                                <td colspan="1" width="25%" cellspacing="0"  cellpadding="0"  >
                                                 <a id="labelFamlilyAnchor" name="labelFamlilyAnchor" style="font-size: 8pt; font-weight: bold; color: purple; background-color: #cccccc; border: none; text-decoration: underline" title="Show Releasing Family Selection"
                                                  href="#<%=MilestoneHelper.getStructureName(environment.getParentID())%>" onClick="Javascript:showEmailDistReleasingFamilyFrame('<%=MilestoneHelper.getStructureName(environment.getParentID())%>','<%=environment.getParentID()%>',this)">
                                                    <%=MilestoneHelper.getStructureName(environment.getParentID())%>
                                                 </a>
                                                </td>

                                             <%} else {%>

                                               <td colspan="1" width="25%" cellspacing="0"  cellpadding="0"  >
                                                 <a id="labelFamlilyAnchor" name="labelFamlilyAnchor" style="font-size: 8pt; font-weight: bold; color: purple; background-color: #cccccc; border: none; text-decoration: underline" title=""
                                                  href="#<%=MilestoneHelper.getStructureName(environment.getParentID())%>" >
                                                    <%=MilestoneHelper.getStructureName(environment.getParentID())%>
                                                 </a>
                                               </td>


                                             <%}%>

                                               <td  colspan="3" width="75%" border="0" cellpadding="0" cellspacing="0"  bcolor="#cccccc">
                                              <a id="releasingFamilyAnchor" name="releasingFamilyAnchor" style="font-size: 8pt; font-weight: bold; color: purple; background-color: #cccccc; border: none; text-decoration: underline;" title="Show Releasing Family Selection"
                                                href="#" onClick="Javascript:showEmailDistReleasingFamilyFrame('<%=MilestoneHelper.getStructureName(environment.getParentID())%>','<%=environment.getParentID()%>',this)">
                                               <%=releasingFamilyStr%>
                                              </a>
                                              </td>

                                              <tr>
                                              <%

                                              famName = MilestoneHelper.getStructureName(environment.getParentID());
                                              c = 0;
                                              famRow++;

                                        }

                                          String rights = unAssigned;
                                         boolean isChecked = false;
                                         FormCheckBox formCheckBox =  (FormCheckBox)form.getElement( "uc" + String.valueOf(environment.getStructureID()));
                                         if(formCheckBox != null && formCheckBox.isChecked())
                                         {
                                            rights = assigned;
                                            isChecked = true;
                                         }
                                        // if showing assigned or all
                                       //  if(!showAssigned || (showAssigned && isChecked))
                                       // {
                                       if(environment.getActive() && !environment.getName().startsWith("Unknown"))  // show active environments only and no Unknowns
                                       {
                                            out.println("<td style=\"" + rights + "\" >");
                                            //out.println("<a href=\"#\" title=\"family: " + MilestoneHelper.getStructureName(environment.getStructureID()) + "\">");
                                            out.println(form.getElement( "uc" + String.valueOf(environment.getStructureID()) ));
                                            //out.println("</a>");
                                            out.println("</td>");
                                            c++;
                                       }
                                       // }
                                     if (c % 4 == 0)
                                     {
                                       out.println("</tr>\n<tr>");
                                     }
                                   }
                            %>
                            </table>

                            <div class=secure >

                            <table width="100%">
                            <tr bgcolor="#ffffff">
                                    <td width="100%" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
                            </tr>
                            </table>
                </div>
		</td>
	</tr>
	<tr><td valign="bottom"></td></tr>
	</td>

        <tr>
      	<td valign="bottom">
	    <% if (form.getRenderableValue("cmd").equals("email-distribution-editor"))
		{
		%>
		<table>
        	<tr>
			<td class="secure">Last Updated: </td>
			<td><%= form.getRenderableValue("lastUpdatedOn")%></td>
			<td><%= form.getRenderableValue("lastUpdatedBy")%></td>
		</tr>
        	</table>
				<%} %>
       </td>
       </tr>



</tr></table>
</td></tr></table>




<% //DIV definitions %>
<% //start search div  %>
<div class="search" id="searchLayer" onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
<table width="100%" border="0" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr>
			<th colspan="2">Email Distribution Search</th>
		</tr>
		<tr>
			<td class="label">First Name</td>
			<td><%= form.getElement("firstNameSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Last Name</td>
			<td><%= form.getElement("lastNameSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Environment</td>
			<td><%= form.getElement("environmentSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Form Type</td>
			<td><%= form.getElement("formTypeSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Release Type</td>
			<td><%= form.getElement("releaseTypeSrch")%></td>
		</tr>
		<tr>
                	<td class="label">Product Type</td>
			<td><%= form.getElement("productTypeSrch")%></td>
		</tr>

		<tr>
			<td colspan="2"><input type=button value="Submit Search" onClick="submitSearch()">
     		  <colspan="2"><input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>
</div>

<div class="search" id="emailDistReleasingFamilyLayer" style="position:absolute; border: inset; visibility:hidden; width:620px;height:360px;z-index:150;left:300px;top:60px;">
        <IFRAME HEIGHT="480" width="100%" ID="emailDistReleasingFamilyFrame" name="emailDistReleasingFamilyFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>

<% //end search div %>
<%=form.renderEnd()%>

<script language="javascript">
if(typeof document.all.showAssigned0 == "object" && typeof document.all.showAssigned1 == "object")
{
    if(showAssigned == "ASSIGNED")
      document.all.showAssigned0.checked = true;
    else
      document.all.showAssigned1.checked = true;
}
</script>


<%@ include file="include-bottom-html.shtml"%>


