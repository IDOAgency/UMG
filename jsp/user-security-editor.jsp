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

<%

        // set the debugging log for this object
        //ComponentLog  log = null;
        //log = context.getApplication().getLog(SecurityHandler.COMPONENT_CODE);

	// This variable holds the value to be placed in the <title> html tag for the include file
	// template-top-html.jsp.
	String htmlTitle = "User Security Editor";

	// getting the login name
	User user = (User)context.getSessionValue("user");
	int userid = user.getUserId();
	Acl acl = user.getAcl();

        Vector cAcl = Cache.getInstance().getEnvironmentList();
        // msc 05/01/03 ITS get environments by family name
	cAcl = Cache.getInstance().getEnvironmentsByFamily();

        int selectedUserIdSE = -1;
        User selectedItemSE = null;
        selectedItemSE = (User)context.getSessionValue("securityUser");
        if(selectedItemSE != null)
            selectedUserIdSE = selectedItemSE.getUserId();

        Vector uAcl = null;
        String showAssigned = (String)context.getSessionValue("showAssigned");
        if(showAssigned == null)
           showAssigned = "ASSIGNED";   // default value

        if(showAssigned.equalsIgnoreCase("ASSIGNED"))
        {
          //uAcl = MilestoneHelper.getUserEnvironments(selectedUserIdSE);
          if(selectedItemSE != null)
          {
            uAcl = selectedItemSE.getAssignedEnvironments();
            if(uAcl != null)
                uAcl =  MilestoneHelper.buildAssignedEnvironments(uAcl);  // sort user environments by family
           }
        }

      //MilestoneInfrastructure inf     = (MilestoneInfrastructure)context.getInfrastructure();
     // log.log(" UserId = " + selectedUserIdSE);
     // log.log("   << showAssigned " + showAssigned);
     // log.log("  Standard Domain = " +  inf.getStandardDomain());
     // log.log("  JSP Dir  = " + inf.getJspDirectory());
     // log.log("  servlet Url  = " + inf.getServletURL());
    //log.log(" command name " + form.getRenderableValue("cmd"));

%>
<%@ include file="template-top-html.shtml" %>
<script>
function HideLayer()
{
 mtbSearch.click();
 toggle( 'searchLayer', 'nameSrch');
}
  function helpConext()
  {
    callHelp('Help/','Reviewing_Editing_Your_User_Information.htm');
    mtbHelp.reset();
  }//end function helpContext
</script>

<!-- // msc @@@ -->
<% String secure = ((String)context.getSessionValue("userInfo"));
if(secure != null && secure.equals("true"))  { %>
  <script>
    var disableShortCuts = true;
    var isUserInfo = true;
  </script>
  <STYLE TYPE="text/css">
   TD.secure    {display=none}
   TABLE.secure {display=none}
   DIV.secure {display=none}
  </STYLE>
<% } else { %>
  <script>
   var disableShortCuts = false;
   var isUserInfo = false;
   </script>
  <STYLE TYPE="text/css">
   TD.secure    {visibility=visible}
   TABLE.secure {visibility=visible}
   DIV.secure {visibility=visible}
  </STYLE>
<% } %>
<!-- // msc @@@ -->

<script type="text/javascript" language="JavaScript">

var userId = "<%=selectedUserIdSE%>"
var showAssigned = "<%=showAssigned%>";

function submitGetAccess(accessStr)
{
  //alert("submitGetAccess = " +  userId + "   " + accessStr.value);
  parent.top.bottomFrame.location = editor + "&user-id=" + userId + "&showAssigned=" + accessStr.value;
} //end function submitGet()



function showReleasingFamilyFrame(labelFamName, labelFamId,oObject)
{
    if(userId == -1)
    {
        alert("You can't assign releasing families until new user has been saved");
        return false
    }

     // move layer down
      if( window.event != null )
      {
        /*
         var oParent = oObject.offsetParent;
         var iOffsetTop = oObject.offsetTop;
         var iClientHeight = oParent.clientHeight;
         alert(" offsetPartent = " + oObjectParent  +  " offsetTop = " + iOffsetTop +
            " clientY = " + window.event.clientY);
              alert("top pos = " + window.event.y +  " screenY = " + window.event.screenY
                   + " clientY = " + window.event.clientY + " offsetY = " + window.event.offsetY
                + " offsetTop = " + window.event.offsetTop +
                  " offsetParent = " + window.event.offSetParent  );
       */
        // mc 05/20/04 document.all.releasingFamilyLayer.style.pixelTop = window.event.clientY;

       }

        /*
        //alert("<%=inf.getURLDirectoryPrefix()%>" + "releasingFamilyFrame.jsp");
        */
        showWaitMsg();  // mc its 966 show please wait message
        document.all.releasingFamilyFrame.src = "<%=inf.getStandardDomain() + inf.getJspDirectory()%>"
                + "releasingFamilyFrame.jsp" + "?labelFamilyName=" + labelFamName + "&labelFamilyId=" + labelFamId;

        setInterval("repositionit()", 100); // mc 05/20/04 reposition the iframe as the scroll bar moves

        //layer = eval(document.all["releasingFamilyLayer"]);
	//layer.style.visibility = "visible";

} //end

//mc 05/20/04 reposition the releasing family iframe as the scroll bar moves
function repositionit()
{
  document.all.releasingFamilyLayer.style.pixelTop = 60 + document.body.scrollTop;
}


function showRelFamLayer( pLayer, labelFamStr )
{
  layer = eval( document.all[ pLayer ] )
  // Check to see if the layer is found (it must be at least an object)
  if ( ( typeof layer ) != "object" )
  {
    alert( "Layer to show up is not available." );
    return;
  }

  document.all.labelFamName.value = labelFamStr;  // label family name
  document.all.userNameRelFam.value = document.all.fullname.value;

  // move layer down
  if( window.event != null )
  {
      // msc 1016 - required for the MAC computer
      var navCom = navigator.platform;
      if( navCom.toUpperCase() != "MACPPC" || navCom.substr(0,3).toUpperCase() != "MAC" ) {
         // alert("top pos = " + window.event.y +  " screenY = " + window.event.screenY
          //        + " clientY = " + window.event.clientY + " offsetY = " + window.event.offsetY);
          //if( window.event.y > 400)
         layer.style.pixelTop = window.event.screenY + (window.event.offsetY / 2)
      }
   }

  layer.style.visibility = "visible";
}  // End show release family

function hideRelFamLayer( pLayer )
{
  layer = eval( document.all[ pLayer ] )
  // Check to see if the layer is found (it must be at least an object)
  if ( ( typeof layer ) != "object" )
  {
    alert( "Layer to hide is not available." );
    return;
  }
  layer.style.visibility = "hidden";
}  // End show release family



</script>


<script type="text/javascript" language="JavaScript">
//global variables needed in include division-editor-javascript.js
  var form;
  var sort = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_SECURITY_SORT)%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_SECURITY_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_USER%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_USER_SECURITY_DELETE) %>";
  var save = "<%= MilestoneConstants.CMD_USER_SECURITY_SAVE%>";
  var copy = "<%= MilestoneConstants.CMD_USER_SECURITY_COPY%>";
  var saveNew = "<%= MilestoneConstants.CMD_USER_SECURITY_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_USER_SECURITY_NEW%>";
  var search = "<%= MilestoneConstants.CMD_USER_SECURITY_SEARCH%>";
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
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>user-security-editor-javascript.js"></script>

<% //Java script functions specific to this page %>
<script type="text/javascript" language="JavaScript">

<%
/*
//DO NOT MOVE RESIZE TO AN INCLUDE WITHOUT ASKING BRETT FIRST!!!!
*/
%>
function submitResize()
{
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_USER_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
    if(!disableShortCuts)   // msc
	checkShortcut();
    else // allow save function only
        checkShortcutUserInfo();
</script>

<%
String focusField = "password";
if (form.getRenderableValue("cmd").equals("user-security-new") ||
    form.getRenderableValue("cmd").equals("user-security-copy") )
{
  focusField = "login";
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


<table width="780" id="userInfoTable" cellpadding="0" cellspacing=0 border="0">
<tr valign="center" align="left">
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
	<td width="30"><span class="title">User</span></td>
        <td><div id="waitLayer" style="font-size: 10pt; font-weight:bold; color:red; visibility:hidden;">Please Wait</div></td>

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
		<div align="left">
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
	<td  width="85">
		<div align="left"  id="newLayer" style="visibility:visible">
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
	<td width="85" >
		<div align="left" id="deleteLayer" style="visibility:visible">
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


    <td>
      <div align="left">
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
  <tr><td  class=secure colspan=6 height=10>&nbsp;</td></tr>
      <% Vector instructions = formValidation.getInstructions();
         for (int i = 0; i < instructions.size(); i++)
         {%>
 <tr>
   <td></td>
   <td></td>
   <td colspan=4 align="left">
           <%=instructions.get(i)%>
   </td>
 </tr>
       <%}%>
 <tr><td  class=secure colspan=6 height=10>&nbsp;</td></tr>
    <%}%>
<tr>
  <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
	<td valign="top">
  	<% /* ===============start users navigation panel===========================
  	    This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here. */
		%>
       	 <%@ include file="include-users-notepad.shtml" %>

 		<% /* ===============end selection navigation panel=========================== */ %>
	</td>
  <%}%>

	<% //start user form
  %>
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">
		<table width="580" cellpadding="5" border="0">
		<tr>
			<td valign="top">
				<table>
				<tr>
					<td nowrap width=110><b>LogIn Name</b></td>
			<% if (form.getRenderableValue("cmd").equals("user-security-new") ||
                            form.getRenderableValue("cmd").equals("user-security-copy")  )
	    	 {
  		%>
					<td colspan="2"><%=form.getElement("login")%></td>
        <%}
        else
        {%>
					<td colspan="2"><%=form.getRenderableValue("login")%></td>
        <%}%>
		<!--	<td colspan="1"></td>  -->
                    <td class=secure><b><%=form.getElement("administrator")%></b></td>
	            <td class=secure><b><%=form.getElement("inactive")%></b></td>

				</tr>
				<tr>
					<td nowrap width=110><b>Password</b></td>
					<td colspan="1"><%= form.getElement("password") %></td>
					<td nowrap class=secure><b>Reports to&nbsp;&nbsp;</b></td>
					<td class=secure><%= form.getElement("reportto") %></td>
				</tr>
				<tr>
					<td nowrap width=110><b>User Name</b></td>
					<td colspan="1">
					<%= form.getElement("fullname") %></td>
					<td nowrap class=secure>
						<b>Location&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
					</td>
					<td  class=secure>
					 <%= form.getElement("location") %>
					 </td>
				</tr>
				<tr>
                                    <!--  // msc Item 1957 ---------------------------------- -->
                             		<td nowrap width=110>
                                        	<b>Email</b>
   					</td>
					<td nowrap>
					<%= form.getElement("email") %>
					</td>

					<td nowrap  class=secure ><b>Employed by</b></td>
					<td  class=secure colspan="1" width="50%">
					<%= form.getElement("employedby") %>
					</td>

 	 			</tr>
	                	<tr>
					<td nowrap width=110><b>Phone</b></td>
					<td colspan="1" width="50%">
					<%= form.getElement("phone") %>
					</td>
					<td  class=secure nowrap>
						<b>Owner Filter</b>
					</td>
					<td  class=secure nowrap>
					<%= form.getElement("filter") %> <%= form.getElement("IsModify") %>
					</td>
				</tr>
	                	<tr>
					<td nowrap width=110><b>Fax Number</b></td>
					<td colspan="1" width="50%">
					<%= form.getElement("fax") %>
					</td>
					<td nowrap  class=secure >
						<b>Dept. Filter</b>
					</td>
					<td nowrap  class=secure >
					<%= form.getElement("deptFilter") %> <%= form.getElement("IsModifyDept") %>
					</td>
				</tr>
                                <!--  // msc Item 1957 ---------------------------------- -->



				</table>
				<table width="100%" class="secure" >
				<tr bgcolor="#ffffff">
					<td width="100%" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
				</tr>
				</table >
			        <table class="secure" width="100%" >
                       		<tr>
					<td colspan="4" class="label">User Environments</td>
				</tr>
				<tr>
                                        <%
                                        if(!showAssigned.equals("NEW") || selectedUserIdSE != -1)
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
					<td class="label" colspan="1" width="25%" style="font-size: 8pt; font-weight:normal; color: red;">Selection Edit Rights</td>
					<td class="label" colspan="1" width="25%" style="font-size: 8pt; font-weight:normal; color: blue;">Selection View Rights</td>
					<td class="label" colspan="1" width="25%" style="font-size: 8pt; font-weight:normal; color: black;">Unassigned</td>
				</tr>

                         	<tr>
					<td colspan="1" class="label" width="25%">&nbsp;</td>
					<td class="label" colspan="3" width="75%">&nbsp;</td>
				</tr>

        			<tr>
					<td colspan="1" class="label" width="25%">Label Family</td>
					<td class="label" colspan="1" width="25%">Releasing Family</td>
					<td class="label" colspan="2" width="50%" style="color: purple;">* Denotes Default Releasing Family</td>
				</tr>


		                </table>
                                <table class="secure" width="100%"  class=secure >
				<%

                                    out.println("<tr class=\"env\" >");
                                    int c = 0; // msc ITS#305, bypass environment without company
                                    String famName = "***";
                                    int famRow = 0;

                                     //log.log("   << showAssigned 2 " + showAssigned);

                                      if(showAssigned.equalsIgnoreCase("Assigned") && uAcl != null)
                                          cAcl = uAcl;

                                        String readRights = "font-size: 8pt; color: blue; font-family:";
                                        String editRights = "font-size: 8pt; color: red;  font-family:";
                                        String unAssigned = "font-size: 8pt; color: black;  font-family:";


					for (int i=0; i < cAcl.size(); i++)
					{

					   Environment environment = (Environment)cAcl.elementAt(i);

                                           boolean isChecked = false;
                                           FormCheckBox formCheckBox =  (FormCheckBox)form.getElement( "ue" + String.valueOf(environment.getStructureID()));
                                           if(formCheckBox != null)
                                           {
                                               isChecked = true;
                                               // log.log(" << checked value = " + formCheckBox.isChecked());
                                           }

                                           // get Family Name
                                           String familyName = "";
                                           familyName = MilestoneHelper.getStructureName(environment.getParentID());

					  // ****************************************
					  // msc 05/01/03 ITS add family row
					  // ****************************************
					  if( !famName.equals(MilestoneHelper.getStructureName(environment.getParentID()) ) )
					  {
                                             // get releasing familes for this label family
                                             String releasingFamilyStr = "";
                                             Vector labelReleasingFamilies = null;

                                             if(selectedItemSE != null)
                                             {
                                                Hashtable releasingFamiliesHash = selectedItemSE.getReleasingFamily();

                                                if(releasingFamiliesHash != null)
                                                  labelReleasingFamilies =(Vector)releasingFamiliesHash.get(Integer.toString(environment.getParentID()));

                                                if(labelReleasingFamilies != null)
                                                {
                                                    releasingFamilyStr  = "";
                                                    for(int x=0; x < labelReleasingFamilies.size(); x++)
                                                    {
                                                        ReleasingFamily releasingFamily = (ReleasingFamily)labelReleasingFamilies.get(x);
                                                        if(releasingFamily != null)
                                                       {
                                                          releasingFamilyStr += releasingFamily.getFamilyAbbr();
                                                          if(releasingFamily.IsDefault())
                                                             releasingFamilyStr += "*";
                                                          releasingFamilyStr += "&nbsp;&nbsp&nbsp";
                                                       }
                                                   }
                                                }
                                                else // if not releasing family assigned, default to label family
                                                {
                                                   // skip UML and enterprise
                                                   if (!familyName.trim().equalsIgnoreCase("UML") && !familyName.equalsIgnoreCase("Enterprise")
                                                        && !familyName.equalsIgnoreCase("eCommerce") )
                                                   {
                                                     // use abbreviation
                                                     if(environment.getParent() != null)
                                                        releasingFamilyStr = environment.getParent().getStructureAbbreviation() + "*";
                                                     else
                                                        releasingFamilyStr = familyName + "*";
                                                  }
                                                }
                                              }

                                             out.println("</tr>");
                                              if(famRow == 0)
                                             {%>
                                             <%}%>

                                             <tr  id="famHdr"  bgcolor="#cccccc" width="100%"   bcolor="#cccccc" cellspacing="0" cellpadding="0" border="0">

                                             <%
                                                // skip UML and enterprise
                                             if (!familyName.trim().equalsIgnoreCase("UML") && !familyName.equalsIgnoreCase("Enterprise")
                                                       && !familyName.equalsIgnoreCase("eCommerce") )
                                             {%>

                                                <td colspan="1" width="25%" cellspacing="0"  cellpadding="0"  >
                                                 <a id="labelFamlilyAnchor" name="labelFamlilyAnchor" style="font-size: 8pt; font-weight: bold; color: purple; background-color: #cccccc; border: none; text-decoration: underline" title="Show Releasing Family Selection"
                                                  href="#<%=MilestoneHelper.getStructureName(environment.getParentID())%>" onClick="Javascript:showReleasingFamilyFrame('<%=MilestoneHelper.getStructureName(environment.getParentID())%>','<%=environment.getParentID()%>',this)">
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
                                                href="#" onClick="Javascript:showReleasingFamilyFrame('<%=MilestoneHelper.getStructureName(environment.getParentID())%>','<%=environment.getParentID()%>',this)">
                                               <%=releasingFamilyStr%>
                                              </a>
                                              </td>

                                              <tr>
                                              <%
                                              famName = MilestoneHelper.getStructureName(environment.getParentID());
                                              c = 0;
                                              famRow++;
					  }

					  if( environment.getCompanies() != null && environment.getCompanies().size() > 0)   // msc its 305
					  {

                                            boolean isUserValid = false;
                                            if(selectedItemSE != null)
                                            {
                                              CompanyAcl userAcl = MilestoneHelper.getScreenPermissions(environment,selectedItemSE);

                                              if(userAcl != null)
                                              {
                                                isUserValid = true;
                                                if(userAcl.getAccessSelection() == 2) // edit rights
                                                {
                                                  //log.log("  <<< edit rights");
                                                  out.println("<td style=\"" + editRights + "\" title=\"Selection Edit Rights\" >");
                                                  out.println(form.getElement( "ue" + String.valueOf(environment.getStructureID()) ));
                                                  out.println("</td>");
                                                  c++; // msc its#305
                                                }
                                                else
                                                {
                                                  //log.log("  <<< read rights");
                                                  out.println("<td style=\"" + readRights + "\" title=\"Selection View Rights\" >");
                                                  out.println(form.getElement( "ue" + String.valueOf(environment.getStructureID()) ));
                                                  out.println("</td>");
                                                  c++; // msc its#305
                                                }
                                             }
                                           }


                                            // *******************************
                                            // no user selected
                                            // *******************************
                                            if(!isUserValid)
                                            {
                                              //log.log("  <<< invalid user");
                                              out.println("<td style=\"" + unAssigned + "\" title=\"Unassigned Environment\" >");
                                              out.println(form.getElement( "ue" + String.valueOf(environment.getStructureID()) ));
                                              out.println("</td>");
                                              c++; // msc its#305
                                            }

                                            if (c % 4 == 0)  // was (i+1), ITS#305
                                            {
                                                  out.println("</tr>\n<tr class=\"env\">");
                                            }

                                          }

					}
					out.println("</span>");
		 		%>
				</table>

                                <div class=secure >

				<table width="100%" >
				<tr bgcolor="#ffffff">
					<td width="100%" height="2"><img src="<%= inf.getImageDirectory() %>pixelshim.gif"></td>
				</tr>
				</table>
				<b>General Menu Access</b>
				<table class="detailList" width="100%" border="1" cellpadding="1" cellspacing="0">
				<tr><th colspan="4">&nbsp;&nbsp;labels</th></tr>
				<tr>
					<td width="50%">&nbsp;Selection</td>
					<td><%= form.getElement("1") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Schedule</td>
					<td><%= form.getElement("2") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Manufacturing</td>
					<td><%= form.getElement("3") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;PFM</td>
					<td><%= form.getElement("4") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;BOM</td>
					<td><%= form.getElement("5") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Report</td>
					<td><%= form.getElement("6") %></td>
				</tr>
				<tr>
					<th colspan="4">&nbsp;&nbsp;admin</th>
				</tr>
				<tr>
					<td width="50%">&nbsp;Template</td>
					<td><%= form.getElement("7") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Task</td>
					<td><%= form.getElement("8") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Day Type</td>
					<td><%= form.getElement("9") %></td>
				</tr>
				<tr>
					<th colspan="4">&nbsp;&nbsp;security</th>
				</tr>
				<tr>
					<td width="50%">&nbsp;User</td>
					<td><%= form.getElement("10") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Family</td>
					<td><%= form.getElement("11") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Environment</td>
					<td><%= form.getElement("20") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Company</td>
					<td><%= form.getElement("12") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Division</td>
					<td><%= form.getElement("13") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Label</td>
					<td><%= form.getElement("14") %></td>
				</tr>
				<tr>
					<th colspan="4">&nbsp;&nbsp;utilities</th>
				</tr>
				<tr>
					<td width="50%">&nbsp;Table</td>
					<td><%= form.getElement("15") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Parameter</td>
					<td><%= form.getElement("16") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Audit Trail</td>
					<td><%= form.getElement("17") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Report Config</td>
					<td><%= form.getElement("18") %></td>
				</tr>
				<tr>
					<td width="50%">&nbsp;Price Code</td>
					<td><%= form.getElement("19") %></td>
				</tr>
			</table>
                    </div>
		</td>
	</tr>
	<tr><td valign="bottom"></td></tr>
	</td>
</tr></table>
</td></tr></table>

<%@ include file="user-preferences-editor.shtml"%>


<% //DIV definitions %>
<% //start search div  %>
<div class="search" id="searchLayer" onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr>
			<th colspan="2">User Search</th>
		</tr>
		<tr>
			<td class="label">Login Name</td>
			<td><%= form.getElement("nameSrch")%></td>
		</tr>
		<tr>
                	<td class="label">User Name</td>
			<td><%= form.getElement("userNameSrch")%></td>
		</tr>

        	<tr>
              		<td nowrap><b>Employed by</b></td>
        		<td colspan="1" width="50%">
			<%= form.getElement("employedBySrc") %>
			</td>
                </tr>

	     	<tr>
		   	<td class="label" >Environment</td>
		   	<td><%= form.getElement("EnvironmentDescriptionSearch") %></td>
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



<div class="search" id="releasingFamilyLayer" style="position:absolute; border: inset; visibility:hidden; width:620px;height:450px;z-index:150;left:300px;top:100px;">
        <IFRAME HEIGHT="450" width="100%" ID="releasingFamilyFrame" name="releasingFamilyFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>



<% //end search div %>
<% //END DIV DEFINITIONS %>

<%=form.renderEnd()%>

<script language="javascript">
if(typeof document.all.showAssigned0 == "object" && typeof document.all.showAssigned1 == "object")
{
    if(showAssigned == "ASSIGNED")
      document.all.showAssigned0.checked = true;
    else
      document.all.showAssigned1.checked = true;
}

// if new/copied user; mc its 985 hide these buttons if user info only
if(userId == -1 || isUserInfo)
{
    // hide buttons
    document.all.copyLayer.style.visibility = "hidden";
    document.all.newLayer.style.visibility = "hidden";
    document.all.deleteLayer.style.visibility = "hidden";
    // msc 03/19/04 this button is no longer used
    if(document.all.logoffLayer)
      document.all.logoffLayer.style.visibility = "hidden";

    // disable anchors
    /*
    document.all.labelFamlilyAnchor.style.textDecoration = "none";
    document.all.releasingFamilyAnchor.style.textDecoration = "none";
    document.all.labelFamlilyAnchor.link.onClick = "";
    document.all.releasingFamilyAnchor.link.onClick = "";
    document.all.labelFamlilyAnchor.link.href = "#";
    document.all.releasingFamilyAnchor.link.href = "#";
    */
}


</script>

<script type="text/javascript">
/*document.addEventListener("DOMContentLoaded", function(event) {
    if (document.getElementById('notePadMainTable') !== null) 
    {
   		 document.getElementById('userInfoTable').style.display = 'none'; 
    	
    }else{
    	   	document.getElementById('preferencesLayer').style.display = 'none';
    }
});
*/

function displayUserInfoTablePreferencesLayer()
{
	if (document.getElementById('notePadMainTable') !== null) 
	{
		document.getElementById('userInfoTable').style.display = 'none'; 
		
	}else{
		document.getElementById('preferencesLayer').style.display = 'none';
	}
}

window.onload = function() {
	if (document.addEventListener) {  // For all major browsers, except IE 8 and earlier
	    document.addEventListener("DOMContentLoaded", function(event) {
			displayUserInfoTablePreferencesLayer();
		});
	} else if (document.attachEvent) {  // For IE 8 and earlier versions
		document.attachEvent("DOMContentLoaded", function(event) {
			displayUserInfoTablePreferencesLayer();
		});
	}
}

</script>

<%@ include file="include-bottom-html.shtml"%>


